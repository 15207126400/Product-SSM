package com.yunwei.product.backend.service;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.serivce.IBaseSerivce;
import com.yunwei.product.common.model.YwOrderItem;


/**
 * 云维字典
 * @author zhangjh
 *
 */
public interface YwOrderItemService extends IBaseSerivce<YwOrderItem>{
	
	public List<YwOrderItem> queryList(YwOrderItem ywOrderItem);
	
	public int update(YwOrderItem ywOrderItem);
	
	public int insert(YwOrderItem ywOrderItem);

	public List<YwOrderItem> queryPage(Map<String, Object> map);

	public int queryTotals(YwOrderItem ywOrderItem);

	public YwOrderItem query(YwOrderItem ywOrderItem);

	public int delete(YwOrderItem ywOrderItem);
	
	public int insertBatch(List<YwOrderItem> list);

}
