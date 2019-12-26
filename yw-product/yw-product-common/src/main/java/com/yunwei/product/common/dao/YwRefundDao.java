package com.yunwei.product.common.dao;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.product.common.model.YwRefund;


/**
 * 云维字典dao
 * @author zhangjh
 *
 */
public interface YwRefundDao extends IBaseDao<YwRefund>{
	
    public List<YwRefund> queryList(YwRefund ywRefund);
	
	public int update(YwRefund ywRefund);
	
	public int insert(YwRefund ywRefund);

	public List<YwRefund> queryPage(Map<String,Object> map);

	public int queryTotals(YwRefund ywRefund);

	public int delete(YwRefund ywRefund);

}
