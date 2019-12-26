package com.yunwei.product.common.dao;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.product.common.model.YwTicket;

/**
 * 抽奖配置表
 * @author zhangjh
 *
 */
public interface YwTicketDao extends IBaseDao<YwTicket>{
	
	public List<YwTicket> queryByRand();
}
