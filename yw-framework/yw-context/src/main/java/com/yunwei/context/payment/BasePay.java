package com.yunwei.context.payment;

import java.util.Map;


/**
 * 业务相关的base订单接口
* @ClassName: BusinessBasePayment 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年5月31日 下午1:27:29 
*
 */
public interface BasePay{

	/**
	 * 创建订单
	 * @param request_url
	 * @param payParamMap
	 * @return
	 */
	public Map<String,Object> createOrder(Map<String, Object> request_map);
	
}
