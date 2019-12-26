package com.yunwei.product.infobase.service;

import com.yunwei.common.base.serivce.IBaseSerivce;
import com.yunwei.product.common.model.QhzSalonOrder;

/**
 * 沙龙注册订单信息表serivce
* @ClassName: QhzSalonOrderInfobaseService 
* @Description: TODO(TODO) 
* @author yanf
* @date 2019年06月25日 上午10:57:05
*
 */
public interface QhzSalonOrderService extends IBaseSerivce<QhzSalonOrder>{
	
	/**
	 * 订单消息发送
	 * @param app_id
	 * @param order_sn
	 */
	public void orderMessageSend(String app_id,String order_sn);
}

