package com.yunwei.product.common.dao;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.product.common.model.YwProductSku;


/**
 * 云维字典dao
 * @author zhangjh
 *
 */
public interface YwProductSkuDao extends IBaseDao<YwProductSku>{
	
    public List<YwProductSku> queryList(YwProductSku ywProductSku);
	
	public int update(YwProductSku ywProductSku);
	
	public int insert(YwProductSku ywProductSku);

	public List<YwProductSku> queryPage(Map<String,Object> map);

	public int queryTotals(YwProductSku ywProductSku);

	public int delete(YwProductSku ywProductSku);

	public int insertBatch(List<YwProductSku> productSkus);

}
