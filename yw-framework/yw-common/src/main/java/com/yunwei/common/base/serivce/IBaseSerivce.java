package com.yunwei.common.base.serivce;

import java.util.List;
import java.util.Map;

/**
 * baseserivce通用serivce查询
* @ClassName: IBaseSerivce 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年5月23日 上午11:59:37 
*
 */
public interface IBaseSerivce<T> {
	
	/**
     * 单表单数据查询(参数：主键)
     * @param id
     * @return
     */
	public T query(int id);
	
	/**
     * 单表单数据查询(参数：主键)
     * @param id
     * @return
     */
	public T query(String id);
	
	/**
     * 单表单数据查询(参数：原始对象)
     * @param map
     * @return
     */
	public T query(T t);
	
	/**
	 * 单表数据查询(参数：map)
	 * @param paramMap
	 * @return
	 */
	public T query(Map<String, Object> paramMap);
	
	/**
     * 单表多数据查询(参数：原始对象)
     * @param map
     * @return
     */
    public List<T> queryList();
	
	/**
     * 单表多数据查询(参数：原始对象)
     * @param map
     * @return
     */
    public List<T> queryList(T t);
    
    /**
     * 单表多数据查询(参数：map)
     * @param map
     * @return
     */
    public List<T> queryList(Map<String, Object> paramMap);
    
    /**
     * 单表分页查询数据(参数：map)
     * @param begin (开始数据)
     * @param end (结束数据)
     * @param map
     * @return
     */
    @Deprecated
    public List<T> queryPage(Map<String, Object> paramMap);
    
    
    /**
     * 单表分页查询数据(参数：map)
     * @param begin (开始数据)
     * @param end (结束数据)
     * @param map
     * @return
     */
    public List<T> queryListPage(T t,int begin,int end);
    
    /**
     * 单表分页查询数据(参数：map)
     * @param begin (开始数据)
     * @param end (结束数据)
     * @param map
     * @return
     */
    public List<T> queryListPage(Map<String, Object> paramMap,int begin,int end);
    
    /**
     * 多表联合查询单数据(参数：主键)
     * @param id
     * @return
     */
	public T queryUnion(int id);
	
	/**
     * 多表联合查询单数据(参数：主键)
     * @param id
     * @return
     */
	public T queryUnion(String id);
    
    /**
     * 多表联合查询单数据(参数：map)
     * @param map
     * @return
     */
    public T queryUnion(T t);
   
    
    /**
     * 多表联合查询单数据(参数：map)
     * @param map
     * @return
     */
    public T queryUnion(Map<String,Object> paramMap);
    
    /**
     * 多表联合查询多数据查询(参数：map)
     * @param map
     * @return
     */
    public List<T> queryUnionList(T t);
    
    /**
     * 多表联合查询多数据查询(参数：map)
     * @param map
     * @return
     */
    public List<T> queryUnionList(Map<String,Object> paramMap);
    
    /**
     * 多表联合查询多数据分页查询(参数：map)
     * @param map
     * @param begin (开始数据)
     * @param end (结束数据)
     * @return
     */
    public List<T> queryUnionListPage(T t,int begin,int end);
    
    /**
     * 多表联合查询多数据分页查询(参数：map)
     * @param map
     * @param begin (开始数据)
     * @param end (结束数据)
     * @return
     */
    public List<T> queryUnionListPage(Map<String,Object> paramMap,int begin,int end);
    
    /**
     * 查询总条数(参数：原始对象)
     * @param t
     * @return
     */
    public int queryTotals(T t);
    
    /**
     * 查询总条数(参数：map)
     * @param map
     * @return
     */
    public int queryTotals(Map<String,Object> paramMap);
	
    /**
     * 更新
     * @param t
     * @return
     */
	public int update(T t);
	
	/**
     * 更新
     * @param t
     * @return
     */
	public int update(Map<String,Object> paramMap);
	
	
	/**
	 * 批量更新
	 * @param list
	 * @return
	 */
	public int updateBatch(List<T> list);
	
	/**
	 * 批量更新
	 * @param fields 需要更新的字段
	 * @param list
	 * @return
	 */
	public int updateBatch(String[] fields,List<T> list);
	
	/**
	 * 批量更新
	 * @param fields 需要更新的字段(可变参数字段[数组形式])
	 * @param list
	 * @return
	 */
	public int updateBatch(List<T> list,String... fields);
	
	/**
	 * 增加
	 * @param t
	 * @return
	 */
	public int insert(T t);
	
	/**
	 * 批量增加
	 * @param list
	 * @return
	 */
	public int insertBatch(List<T> list);
	
	/**
	 * 删除(参数：主键)
	 * @param id
	 * @return
	 */
	public int delete(int id);
	
	/**
	 * 删除(参数：原始对象)
	 * @param t
	 * @return
	 */
	public int delete(T t);
	
	/**
	 * 删除(参数：map)
	 * @param t
	 * @return
	 */
	public int delete(Map<String,Object> paramMap);

	
	/**
	 * 批量删除(参数：可变参数)根据主键批量删除
	 * @param ids
	 * @return
	 */
	public int deleteBatch(int... ids);
	
	/**
	 * 批量删除(参数：map)[例：map.put("id",new String[]{"1", "2","3"})]
	 * @param map
	 * @return
	 */
	public int deleteBatch(Map<String,String[]> paramMap);

}
