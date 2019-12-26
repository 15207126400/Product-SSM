package com.yunwei.common.base.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
/**
 * baesdao通用dao查询
* @ClassName: IbaseDao 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年5月23日 下午12:10:49 
*
 */
public interface IBaseDao<T> {
    
    public List<T> queryList(Map<String,Object> paramMap);
    
    public List<T> queryListPage(Map<String,Object> paramMap);
    
    public List<T> queryUnionList(Map<String,Object> paramMap);  
    
    public List<T> queryUnionListPage(Map<String,Object> paramMap);
    
    public int queryTotals(Map<String,Object> paramMap);
	
	public int update(Map<String, Object> paramMap);
	
	public int updateBatch(@Param("fields") String[] fields,@Param("lists") List<T> list);
	
	public int insert(T t);
	
	public int insertBatch(List<T> list);
	
	public int delete(Map<String, Object> paramMap);
	
	public int deleteBatch(Map<String,String[]> paramMap);
	
	
}
