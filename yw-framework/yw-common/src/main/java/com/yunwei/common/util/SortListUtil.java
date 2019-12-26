package com.yunwei.common.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能说明: list排序的工具类<br>
 * 系统版本: v1.0<br>
 * 开发人员: @author yejg<br>
 * 开发时间: 2015年8月29日<br>
 */
public class SortListUtil {

	private static Logger logger = LoggerFactory.getLogger(SortListUtil.class);

	/** 升序 */
	public static final String ASC = "asc";
	/** 降序 */
	public static final String DESC = "desc";

	/**
	 * 对list中的元素按升序排列.
	 * 
	 * <pre>
	 * eg:SortListUtil.sort(list, "age")	把list中的对象按照age值的升序排列
	 * </pre>
	 * @param list 排序集合
	 * @param field 排序字段
	 */
	public static void sort(List<?> list, final String field) {
		sort(list, field, null);
	}

	/**
	 * 对list中的元素进行排序.
	 * 
	 * <pre>
	 * eg:SortListUtil.sort(list, "age", SortListUtil.DESC)	把list中的对象按照age值的降序排列
	 * </pre>
	 * @param list 排序集合
	 * @param field 排序字段
	 * @param sort 排序方式: desc:降序, asc:升序。默认升序
	 * @see SortListUtil#ASC
	 * @see SortListUtil#DESC
	 */
	public static void sort(List<?> list, final String field, final String sort) {
		sort(list, new String[] {field}, new String[] {sort});
	}

	/**
	 * 对list中的元素按fields和sorts进行排序
	 * 
	 * <pre>
	 * eg:
	 * SortListUtil.sort(list, new String[] {"name", "id"}, new String[] {SortListUtil.ASC, SortListUtil.DESC});
	 * 先按name正序,name相同时再按id倒序
	 * </pre>
	 * @param list 排序集合
	 * @param fields 指定多个排序字段
	 * @param sorts 指定多个排序方式，如果sorts[i]为空则默认按升序排列
	 */
	public static void sort(List<?> list, String[] fields, String[] sorts) {
		sort(list,fields,sorts,null);
	}

	/**
	 * 指定参数排序
	 * @param list	排序集合
	 * @param fields	指定多个排序字段
	 * @param sorts	指定多个排序方式，如果sorts[i]为空则默认按升序排列
     * @param conClz	指定类型比较
     */
	public static void sort(List<?> list, final String[] fields, String[] sorts, final Class<?>[] conClz) {
		if (fields != null && fields.length > 0) {
			for (int i = fields.length - 1; i >= 0; i--) {
				final String field = fields[i];
				String tmpSort = ASC;
				if (sorts != null && sorts.length > i && sorts[i] != null) {
					tmpSort = sorts[i];
				}
				final String sort = tmpSort;
				final int finalI = i;
				Collections.sort(list, new Comparator<Object>() {

					public int compare(Object a, Object b) {
						int compareValue = 0;
						try {
							Class<?> type = PropertyUtils.getPropertyType(a, field);
							Object va = PropertyUtils.getProperty(a, field);
							Object vb = PropertyUtils.getProperty(b, field);
							if(va!=null && vb!=null){
								if(conClz!=null && conClz.length==fields.length && conClz[finalI]!=null){
									if(conClz[finalI]==Integer.class){
										Long vaInt=null;
										Long vbInt=null;
										try{
											vaInt=Long.parseLong(va.toString());
										}catch (Exception e){
											logger.info("数据类型异常",e.getMessage());
										}
										try{
											vbInt=Long.parseLong(vb.toString());
										}catch (Exception e){
											logger.info("数据类型异常",e.getMessage());
										}
										if(vaInt!=null && vbInt!=null){
											compareValue=vaInt.compareTo(vbInt);
										}else{
											compareValue=compareObj(vaInt,vbInt);
										}
									}
								}else if (Comparable.class.isAssignableFrom(type)) {
									compareValue = ((Comparable<Comparable<?>>)va).compareTo((Comparable<?>)vb);
								} else {
									if(type == int.class){
										compareValue = (Integer)va - (Integer)vb;
									} else {
										compareValue = String.valueOf(va).compareTo(String.valueOf(vb));
									}
								}
							}else{
								compareValue=compareObj(va,vb);
							}
						} catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
							logger.error(e.getMessage(), e);
							return 0;
						}

						if (StringUtils.equalsIgnoreCase(sort, DESC)) {
							return -compareValue;
						} else {
							return compareValue;
						}
					}
				});
			}
		}
	}

	private static int compareObj(Object source,Object target){
		int compareValue = 0;
		if (source == null && target != null) {
			compareValue = -1;
		} else if (source != null && target == null) {
			compareValue = 1;
		} else if (source == null && target == null) {
			compareValue = 0;
		}
		return compareValue;
	}
}
