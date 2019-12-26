package com.yunwei.context.payment;

import com.yunwei.context.payment.support.WxPayConfig;

/**
 * 支付服务相关接口
* @ClassName: PayService 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2019年2月22日 下午4:39:06 
*
 */
public interface PayService extends SceneBasePay,BasePayBill,BasePayComment,BasePayOrder,BasePayRefund{

	/**
	 * 微信配置设置
	 * @param wxPayConfig
	 */
	public void setWxPayConfig(WxPayConfig wxPayConfig);
	
}
