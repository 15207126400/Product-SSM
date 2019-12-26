package com.yunwei.common.util;

import com.alibaba.fastjson.JSONObject;
import com.yunwei.common.annotation.Alias;
import com.yunwei.common.annotation.Base64Decode;
import com.yunwei.common.annotation.DateFormat;
import com.yunwei.common.annotation.NoSerialize;

import org.apache.camel.util.CaseInsensitiveMap;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * 功能说明: Map工具类<br>
 * 系统版本: v1.0<br>
 * 开发人员: @author zhangjh<br>
 * 开发时间: 2018-3-6<br>
 */
public class MapUtil extends MapUtils {

	private final static Logger logger = LoggerFactory.getLogger(MapUtil.class);

	public static <T> T map2Object(Class<T> clazz, Map<String, ?> map) {
		T object = null;
		try {
			object = clazz.newInstance();
			Map<String, Object> tMap = new HashMap<String, Object>();
			tMap.putAll(map);
			toObject(clazz, object, tMap, false);
		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		}
		return object;
	}
	
	private static void toObject(Class<?> clazz, Object object, Map<String, ?> map, boolean ignoreCase) {
		Field[] fields = clazz.getDeclaredFields();
		if (fields != null && fields.length > 0) {
			if (ignoreCase) {
				// 不区分大小写
				map = new CaseInsensitiveMap(map);
			}
			for (int i = 0; i < fields.length; i++) {
				try {
					Alias alias = fields[i].getAnnotation(Alias.class);
					String fieldName = fields[i].getName();
					if ("serialVersionUID".equals(fieldName)) {
						continue;
					} else if ("class".equals(fieldName)) {
						continue;
					}
					Object fieldValue = map.get(fieldName);
					if (fieldValue == null && alias != null) {
						// 适配别名
						fieldValue = map.get(alias.value());
					}
					if (fieldValue != null) {
						if (Date.class.isAssignableFrom(fields[i].getType()) && fieldValue instanceof String) {
							fieldValue = DateUtil.parse((String)fieldValue);
						}
						if (fieldValue != null) {
							BeanUtils.setProperty(object, fieldName, fieldValue);
						}
					}

				} catch (IllegalAccessException e) {
					logger.error(e.getMessage(), e);
				} catch (InvocationTargetException e) {
					logger.error(e.getMessage(), e);
				} catch (SecurityException e) {
					logger.error(e.getMessage(), e);
				} catch (ParseException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		if (clazz.getSuperclass() != null) {
			toObject(clazz.getSuperclass(), object, map, ignoreCase);
		}

	}
	
	@SuppressWarnings("rawtypes")
	public static List<Map<String, Object>> toMapList(Collection collection) {
		List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
		if (collection != null && !collection.isEmpty()) {
			for (Object object : collection) {
				Map<String, Object> map = new HashMap<String, Object>();
				// 增加对返回List<Map<String,Object>>类型的结果集支持Excel导出
				if (object instanceof HashMap<?, ?>) {
					retList.add((Map<String, Object>)object);
				} else {
					toMap(object.getClass(), object, map);
					retList.add(map);
				}
			}
		}
		return retList;
	}

	/**
	 * 将对象转成&lt;String, Object&gt;，支持别名，支持日期格式化(DateFormat注解)
	 * @param object
	 * @return
	 */
	public static Map<String, Object> toMap(Object object) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (object == null) {
			return map;
		}

		if (object instanceof Map) {
			return (Map<String, Object>)object;
		}
		toMap(object.getClass(), object, map);
		return map;
	}
	
	/**
	 * 将对象转成&lt;String, Object&gt;，支持别名，支持日期格式化(DateFormat注解)[dao层参数map]
	 * @param object
	 * @return
	 */
	public static Map<String, Object> toDaoMap(Object object) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (object == null) {
			return map;
		}

		if (object instanceof Map) {
			return (Map<String, Object>)object;
		}
		toDaoMap(object.getClass(), object, map);
		return map;
	}
	
	private static void toDaoMap(Class<?> clazz, Object object, Map<String, Object> map) {
		Field[] fields = clazz.getDeclaredFields();
		if (fields != null && fields.length > 0) {
			for (int i = 0; i < fields.length; i++) {
				NoSerialize noSerialize = fields[i].getAnnotation(NoSerialize.class);
				if (noSerialize != null) {
					continue;
				}
				Alias alias = fields[i].getAnnotation(Alias.class);
				String fieldName = fields[i].getName();
				if ("serialVersionUID".equals(fieldName)) {
					continue;
				} else if ("class".equals(fieldName)) {
					continue;
				}
				try {
					// 获取字段值
					Object value = PropertyUtils.getProperty(object, fieldName);
//					// 日期格式化
//					if (value != null && value instanceof Date) {
//						DateFormat dateFormat = fields[i].getAnnotation(DateFormat.class);
//						value = DateUtil.format((Date)value, dateFormat == null ? DateUtil.DATE_TIME_FORMAT : dateFormat.value());
//					}
//					// 判断字段是否需要base64解码（主要用于微信昵称方面）
//					if(fields[i].getAnnotation(Base64Decode.class) != null){
//						if(StringUtils.isNotBlank((String)value)){
//							value = Base32.decode((String)value);
//						}
//					}
					
					// 如果value为null，则设置成空字符串。否则返回到前台的是null字符串
					map.put(fieldName, value == null ? "" : value);
					if (alias != null) {
						map.put(alias.value(), value == null ? "" : value);
					}
				} catch (IllegalAccessException e) {
					logger.error(e.getMessage(), e);
				} catch (InvocationTargetException e) {
					logger.error(e.getMessage(), e);
				} catch (NoSuchMethodException e) {
					// 忽略get方法不存在错误
					// logger.error(e.getMessage(), e);
				}
			}
		}
		if (clazz.getSuperclass() != null) {
			toDaoMap(clazz.getSuperclass(), object, map);
		}
	}

	private static void toMap(Class<?> clazz, Object object, Map<String, Object> map) {
		Field[] fields = clazz.getDeclaredFields();
		if (fields != null && fields.length > 0) {
			for (int i = 0; i < fields.length; i++) {
				NoSerialize noSerialize = fields[i].getAnnotation(NoSerialize.class);
				if (noSerialize != null) {
					continue;
				}
				Alias alias = fields[i].getAnnotation(Alias.class);
				String fieldName = fields[i].getName();
				if ("serialVersionUID".equals(fieldName)) {
					continue;
				} else if ("class".equals(fieldName)) {
					continue;
				}
				try {
					// 获取字段值
					Object value = PropertyUtils.getProperty(object, fieldName);
					// 日期格式化
					if (value != null && value instanceof Date) {
						DateFormat dateFormat = fields[i].getAnnotation(DateFormat.class);
						value = DateUtil.format((Date)value, dateFormat == null ? DateUtil.DATE_TIME_FORMAT : dateFormat.value());
					}
					// 判断字段是否需要base64解码（主要用于微信昵称方面）
					if(fields[i].getAnnotation(Base64Decode.class) != null){
						if(StringUtils.isNotBlank((String)value)){
							value = Base32.decode((String)value);
						}
					}
					
					// 如果value为null，则设置成空字符串。否则返回到前台的是null字符串
					map.put(fieldName, value == null ? "" : value);
					if (alias != null) {
						map.put(alias.value(), value == null ? "" : value);
					}
				} catch (IllegalAccessException e) {
					logger.error(e.getMessage(), e);
				} catch (InvocationTargetException e) {
					logger.error(e.getMessage(), e);
				} catch (NoSuchMethodException e) {
					// 忽略get方法不存在错误
					// logger.error(e.getMessage(), e);
				}
			}
		}
		if (clazz.getSuperclass() != null) {
			toMap(clazz.getSuperclass(), object, map);
		}
	}
	
	/**
	 * 将对象转成Map&lt;String, String&gt;，支持别名，支持日期格式化(DateFormat注解)
	 * @param object
	 * @return
	 */
	public static Map<String, String> toMapString(Object object) {
		Map<String, Object> map = toMap(object);
		Map<String, String> mapString = new HashMap<String, String>();
		for (Entry<String, Object> entry : map.entrySet()) {
			mapString.put(entry.getKey(), String.valueOf(entry.getValue()));
		}
		return mapString;
	}

	/**
	 * 将对象转成Map&lt;String, String&gt;，支持别名，支持日期格式化(DateFormat注解)
	 * 如果对象属性包含数组，则把数组value改为 value,value,value的格式
	 * @param object
	 * @return
	 */
	public static Map<String, String> beanIncludeArray2Map(Object object) {
		Map<String, Object> map = toMap(object);
		Map<String, String> mapString = new HashMap<String, String>();
		for (Entry<String, Object> entry : map.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (null == value || StringUtils.isBlank(value.toString())) {
				continue;
			}
			if (value instanceof String[]) {
				String[] v = (String[])value;
				for (int i = 0; i < v.length; i++) {
					if (i == 0) {
						value = v[i] + ",";
					} else if (i == v.length - 1) {
						value = value + v[i];
					} else {
						value = value + v[i] + ",";
					}
				}
			}
			if (key.contains("date") && value instanceof String) {
				value = value.toString().replaceAll("-", "");
			}
			mapString.put(entry.getKey(), String.valueOf(value));
		}
		return mapString;
	}

	/**
	 * 将后面一个Map合并到前面一个Map中，合并过程中如果有重复的将忽略
	 * @return
	 */
	public static Map<String, Object> mergeMap(Map<String, Object> mainMap, Map<String, Object> subMap) {
		for (Entry<String, Object> entry : subMap.entrySet()) {
			if (!mainMap.containsKey(entry.getKey())) {
				mainMap.put(entry.getKey(), entry.getValue());
			}
		}
		return mainMap;
	}

	/**
	 * clone一个map，并按key进行过滤
	 * @param from
	 * @param filter
	 * @return
	 */
	public static Map<String, Object> cloneByFilter(Map<String, ?> from, String[] filter) {
		Map<String, Object> target = new HashMap<String, Object>();
		for (String item : filter) {
			target.put(item, ObjectUtils.defaultIfNull(from.get(item), ""));
		}
		return target;
	}

	/**
	 * clone一个List，并按key进行过滤
	 * @param from
	 * @param filter
	 * @return
	 */
	public static List<Map<String, Object>> cloneByFilter(List<Map<String, ?>> from, String[] filter) {
		Map<String, Object> target = new HashMap<String, Object>();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (Map<String, ?> map : from) {
			for (String item : filter) {
				target.put(item, ObjectUtils.defaultIfNull(map.get(item), ""));
				result.add(target);
			}
		}
		return result;
	}

	/**
	 * 将String转为Map
	 * @param value
	 * @return
	 * 		开发人员: @author huadi<br>
	 *         开发时间: 2015年10月14日<br>
	 */
	public static Map<String, Map<String, String>> parseStr(String value) {
		Map<String, Map<String, String>> taskMap = new HashMap<String, Map<String, String>>();
		if (StringUtils.isNotBlank(value) && !StringUtils.equals(value, "{}")) {
			JSONObject obj = JSONObject.parseObject(value);
			Map<String, String> map = MapUtil.toMapString(obj);
			if (MapUtils.isNotEmpty(map)) {
				for (String key : map.keySet()) {
					if (StringUtils.isNotBlank(key)) {
						String orgin = MapUtils.getString(map, key, "");
						if (StringUtils.isNotBlank(orgin) && !StringUtils.equals(orgin, "{}")) {
							JSONObject innerObj = JSONObject.parseObject(orgin);
							Map<String, String> personMap = MapUtil.toMapString(innerObj);
							taskMap.put(key, personMap);
						}
					}
				}
			}
		}
		return taskMap;
	}

	/**
	 * 方法名称:transMapToString
	 * 传入参数:map
	 * 返回值:String 形如 username'chenziwen^password'1234
	 */
	public static String transMapToString(Map map) {
		java.util.Map.Entry entry;
		StringBuffer sb = new StringBuffer();
		for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext();) {
			entry = (java.util.Map.Entry)iterator.next();
			sb.append(entry.getKey().toString()).append("'").append(null == entry.getValue() ? "" : entry.getValue().toString()).append(iterator.hasNext() ? "^" : "");
		}
		return sb.toString();
	}

	/**
	 * 方法名称:transStringToMap
	 * 传入参数:mapString 形如 username'chenziwen^password'1234
	 * 返回值:Map
	 */
	public static Map transStringToMap(String mapString) {
		Map map = new HashMap();
		java.util.StringTokenizer items;
		for (StringTokenizer entrys = new StringTokenizer(mapString, "^"); entrys.hasMoreTokens(); map.put(items.nextToken(), items.hasMoreTokens() ? ((Object)(items.nextToken())) : null))
			items = new StringTokenizer(entrys.nextToken(), "'");
		return map;
	}

	/**
	 * 使用 Map按key进行排序
	 * @param map
	 * @return
	 */
	public static Map<String, Object> sortMapByKey(Map<String, Object> map) {
		if (map == null || map.isEmpty()) {
			return null;
		}
		Map<String, Object> sortMap = new TreeMap<String, Object>(new Comparator<String>() {

			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		sortMap.putAll(map);
		return sortMap;
	}

	/**
	 * 使用 Map按value进行排序
	 * @param map
	 * @return
	 */
	public static Map<String, Object> sortMapByValue(Map<String, Object> map) {
		if (map == null || map.isEmpty()) {
			return null;
		}
		Map<String, Object> sortedMap = new LinkedHashMap<String, Object>();
		List<Map.Entry<String, Object>> entryList = new ArrayList<Map.Entry<String, Object>>(map.entrySet());
		Collections.sort(entryList, new Comparator<Entry<String, Object>>() {

			public int compare(Entry<String, Object> o1, Entry<String, Object> o2) {
				return String.valueOf(o1.getValue()).compareTo(String.valueOf(o2.getValue()));
			}
		});

		Iterator<Map.Entry<String, Object>> iter = entryList.iterator();
		Map.Entry<String, Object> tmpEntry = null;
		while (iter.hasNext()) {
			tmpEntry = iter.next();
			sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
		}
		return sortedMap;
	}

	/**
	 * Map<String,Object> 转化为 Map<String,String>
	 * @param objMap
	 * @return
	 */
	public static Map<String, Object> convertToStringMap(Map<String, Object> objMap) {
		Map<String, Object> tempMap = new HashMap<String, Object>();
		for (Map.Entry<String, Object> entry : objMap.entrySet()) {
			tempMap.put(entry.getKey(), null != entry.getValue() ? entry.getValue().toString() : "");
		}
		return tempMap;
	}
}
