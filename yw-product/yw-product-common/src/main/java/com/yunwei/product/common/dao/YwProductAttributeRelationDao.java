package com.yunwei.product.common.dao;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.IBaseSerivce;
import com.yunwei.product.common.model.YwProductAttributeRelation;


/**
 * 云维字典dao
 * @author zhangjh
 *
 */
public interface YwProductAttributeRelationDao extends IBaseDao<YwProductAttributeRelation>{
	
    public List<YwProductAttributeRelation> queryList(YwProductAttributeRelation YwProductAttributeRelation);
	
	public int update(YwProductAttributeRelation YwProductAttributeRelation);
	
	public int insert(YwProductAttributeRelation YwProductAttributeRelation);

	public List<YwProductAttributeRelation> queryPage(Map<String,Object> map);

	public int queryTotals(YwProductAttributeRelation YwProductAttributeRelation);

	public int delete(YwProductAttributeRelation YwProductAttributeRelation);

}
