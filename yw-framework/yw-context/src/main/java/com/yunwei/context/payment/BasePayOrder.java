package com.yunwei.context.payment;

import java.util.Map;

/**
 * 业务相关的base支付订单接口
* @ClassName: BaseBusinessPaymentOrder 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年5月31日 下午1:53:41 
*
 */
public interface BasePayOrder {

	/**
	 * 支付订单查询
	 * @param order_sn(订单号)
	 * @return
	 */
	public Map<String,Object> orderQuery(String order_sn);
	
	/**
	 * 支付订单查询
	 * @param request_map(订单查询参数)
	 * @return
	 */
	public Map<String,Object> orderQuery(Map<String, Object> request_map);
	
	/**
	 * 支付订单关闭
	 * @param order_sn(订单号)
	 * @return
	 */
	public Map<String,Object> closeOrder(String order_sn);
}
