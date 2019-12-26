package com.yunwei.product.common.dao;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.IBaseSerivce;
import com.yunwei.product.common.model.YwProductAttributevalue;


/**
 * 云维字典dao
 * @author zhangjh
 *
 */
public interface YwProductAttributevalueDao extends IBaseDao<YwProductAttributevalue>{
	
    public List<YwProductAttributevalue> queryList(YwProductAttributevalue ywProductAttributevalue);
	
	public int update(YwProductAttributevalue ywProductAttributevalue);
	
	public int insert(YwProductAttributevalue ywProductAttributevalue);

	public List<YwProductAttributevalue> queryPage(Map<String,Object> map);

	public int queryTotals(YwProductAttributevalue ywProductAttributevalue);

	public int delete(YwProductAttributevalue ywProductAttributevalue);

	public int insertBatch(List<YwProductAttributevalue> attributevalues);

}
