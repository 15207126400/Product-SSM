package com.yunwei.product.backend.service;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.serivce.IBaseSerivce;
import com.yunwei.product.common.model.YwProductAttributeRelation;


/**
 * 云维字典
 * @author zhangjh
 *
 */
public interface YwProductAttributeRelationService extends IBaseSerivce<YwProductAttributeRelation>{
	
	public List<YwProductAttributeRelation> queryList(YwProductAttributeRelation ywProductAttributeRelation);
	
	public int update(YwProductAttributeRelation ywProductAttributeRelation);
	
	public int insert(YwProductAttributeRelation ywProductAttributeRelation);

	public List<YwProductAttributeRelation> queryPage(Map<String, Object> map);

	public int queryTotals(YwProductAttributeRelation ywProductAttributeRelation);

	public YwProductAttributeRelation query(YwProductAttributeRelation ywProductAttributeRelation);

	public int delete(YwProductAttributeRelation ywProductAttributeRelation);

}
