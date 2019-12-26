package com.yunwei.product.backend.service;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.serivce.IBaseSerivce;
import com.yunwei.product.common.model.YwProductSku;


/**
 * 云维字典
 * @author zhangjh
 *
 */
public interface YwProductSkuService extends IBaseSerivce<YwProductSku>{
	
	public List<YwProductSku> queryList(YwProductSku ywProductSku);
	
	public int update(YwProductSku ywProductSku);
	
	public int insert(YwProductSku ywProductSku);

	public List<YwProductSku> queryPage(Map<String, Object> map);

	public int queryTotals(YwProductSku ywProductSku);

	public YwProductSku query(YwProductSku ywProductSku);

	public int delete(YwProductSku ywProductSku);

	public int insertBatch(List<YwProductSku> productSkus);

}
