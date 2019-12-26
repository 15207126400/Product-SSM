package com.yunwei.product.common.dao;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.product.common.model.YwOrderItem;


/**
 * 云维字典dao
 * @author zhangjh
 *
 */
public interface YwOrderItemDao extends IBaseDao<YwOrderItem>{
	
    public List<YwOrderItem> queryList(YwOrderItem ywOrderItem);
	
	public int update(YwOrderItem ywOrderItem);
	
	public int insert(YwOrderItem ywOrderItem);

	public List<YwOrderItem> queryPage(Map<String,Object> map);

	public int queryTotals(YwOrderItem ywOrderItem);

	public int delete(YwOrderItem ywOrderItem);
	
    public int insertBatch(List<YwOrderItem> list);

}
