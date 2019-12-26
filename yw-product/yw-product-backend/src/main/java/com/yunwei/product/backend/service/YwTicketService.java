package com.yunwei.product.backend.service;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.serivce.IBaseSerivce;
import com.yunwei.product.common.model.YwTicket;

/**
 * 
* @ClassName: YwTicketService 
* @Description: 抽奖配置表 
* @author 晏飞
* @date 2018年5月16日 下午3:11:17 
*
 */
public interface YwTicketService extends IBaseSerivce<YwTicket>{
	
	/**
	 * 随机查询随机条数的数据
	*
	*@return
	*List<YwTicket>
	 */
	public List<YwTicket> queryByRand();
}
