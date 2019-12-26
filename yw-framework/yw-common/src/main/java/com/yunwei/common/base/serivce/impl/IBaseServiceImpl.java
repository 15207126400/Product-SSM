package com.yunwei.common.base.serivce.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.IBaseSerivce;
import com.yunwei.common.util.MapUtil;

/**
 * IBaseService 抽象实现方法，事务添加请到具体业务实现中添加
* @ClassName: IBaseServiceImpl 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年5月23日 下午12:09:12 
*
 */
public abstract class IBaseServiceImpl<T> implements IBaseSerivce<T>{
	
	/**
	 * 具体业务类去实现
	 * @return
	 */
	protected abstract IBaseDao<T> getBaseDao();
	
	/**
	 * 具体业务类个性化实现(获取主键:默认主键为id)[]
	 * @return
	 */
	protected String getPrimaryKey(){
		return "id";
	}

	@Override
	public T query(int id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(getPrimaryKey(), id);
		return this.query(paramMap);
	}
	
	@Override
	public T query(String id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(getPrimaryKey(), id);
		return this.query(paramMap);
	}
	
	@Override
	public T query(T t) {
		Map<String, Object> paramMap =  MapUtil.toDaoMap(t);
		return query(paramMap);
	}
	
	@Override
	public T query(Map<String, Object> paramMap) {
		List<T> list = getBaseDao().queryList(paramMap);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public List<T> queryList() {
		Map<String, Object> paramMap =  new HashMap<String, Object>();
		return this.queryList(paramMap);
	}
	
	@Override
	public List<T> queryList(T t) {
		Map<String, Object> paramMap =  MapUtil.toDaoMap(t);
		return getBaseDao().queryList(paramMap);
	}
	
	
	
	@Override
	public List<T> queryList(Map<String, Object> paramMap) {
		
		return getBaseDao().queryList(paramMap);
	}
	
	@Override
	public List<T> queryPage(Map<String, Object> paramMap) {
		return getBaseDao().queryListPage(paramMap);
	}
	
	@Override
	public List<T> queryListPage(T t, int begin, int end) {
		Map<String, Object> paramMap = MapUtil.toDaoMap(t);
		return this.queryListPage(paramMap, begin, end);
	}
	
	@Override
	public List<T> queryListPage(Map<String, Object> paramMap, int begin,int end) {
		paramMap.put("begin", begin);
		paramMap.put("end", end);
		return getBaseDao().queryListPage(paramMap);
	}
	
	@Override
	public T queryUnion(int id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(getPrimaryKey(), id);
		return this.queryUnion(paramMap);
	}
	
	@Override
	public T queryUnion(String id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(getPrimaryKey(), id);
		return this.queryUnion(paramMap);
	}
	
	@Override
	public T queryUnion(T t) {
		Map<String, Object> paramMap = MapUtil.toDaoMap(t);
		return this.queryUnion(paramMap);
	}
	
	@Override
	public T queryUnion(Map<String, Object> paramMap) {
		List<T> list = this.queryUnionList(paramMap);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public List<T> queryUnionList(T t) {
		Map<String, Object> paramMap = MapUtil.toDaoMap(t);
		return this.queryUnionList(paramMap);
	}
	
	
	@Override
	public List<T> queryUnionList(Map<String, Object> paramMap) {
		return getBaseDao().queryUnionList(paramMap);
	}
	
	@Override
	public List<T> queryUnionListPage(T t, int begin, int end) {
		Map<String, Object> paramMap = MapUtil.toDaoMap(t);
		paramMap.put("begin", begin);
		paramMap.put("end", end);
		return this.queryListPage(paramMap, begin, end);
	}
	
	@Override
	public List<T> queryUnionListPage(Map<String, Object> paramMap, int begin,int end) {
		paramMap.put("begin", begin);
		paramMap.put("end", end);
		return getBaseDao().queryUnionListPage(paramMap);
	}
	
	@Override
	public int queryTotals(T t) {
		Map<String, Object> paramMap = MapUtil.toDaoMap(t);
		return this.queryTotals(paramMap);
	}

	@Override
	public int queryTotals(Map<String, Object> paramMap) {
		return getBaseDao().queryTotals(paramMap);
	}
	
	@Override
	public int update(T t) {
		Map<String, Object> paramMap = MapUtil.toDaoMap(t);
		return this.update(paramMap);
	}
	
	@Override
	public int update(Map<String, Object> paramMap) {
		
		return getBaseDao().update(paramMap);
	}
	
	@Override
	public int updateBatch(List<T> list) {
		if(CollectionUtils.isNotEmpty(list)){
			for(T t : list){
				this.update(t);
			}
		}
		return 0;
	}
	
	@Override
	public int updateBatch(List<T> list, String... fields) {
		return this.updateBatch(fields,list);
	}
	
	@Override
	public int updateBatch(String[] fields, List<T> list) {
		return getBaseDao().updateBatch(fields,list);
	}

	@Override
	public int insert(T t) {
		return getBaseDao().insert(t);
	}

	@Override
	public int insertBatch(List<T> list) {
		return getBaseDao().insertBatch(list);
	}
	
	
	
	@Override
	public int delete(int id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(getPrimaryKey(), id);
		return this.delete(paramMap);
	}
	
	@Override
	public int delete(T t) {
		Map<String, Object> paramMap = MapUtil.toDaoMap(t);
		return this.delete(paramMap);
	}
	
	@Override
	public int delete(Map<String, Object> paramMap) {
		
		return getBaseDao().delete(paramMap);
	}
	
	@Override
	public int deleteBatch(int... ids) {
		Map<String, String[]> paramMap = new HashMap<String, String[]>();
		int count = ids.length;
		if(count > 0){
			String[] strings = {}; 
			for(int i = 0 ; i < count ; i++){
				strings[i] = String.valueOf(ids[i]);
			}
			paramMap.put(getPrimaryKey(), strings);
			
			// 批量删除
			this.deleteBatch(paramMap);
		}
		return 0;
	}
	
	@Override
	public int deleteBatch(Map<String, String[]> paramMap) {
		return getBaseDao().deleteBatch(paramMap);
	}

}
