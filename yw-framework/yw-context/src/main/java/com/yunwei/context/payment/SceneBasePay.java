package com.yunwei.context.payment;

import java.util.Map;


/**
 * 场景订单服务接口
* @ClassName: WxBaseBusinessPayment 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年5月31日 下午4:21:47 
*
 */
public interface SceneBasePay extends BasePay{

	/**
	 * 创建扫码订单[pc端支付]
	 * @param order_sn(订单号)
	 * @param total_fee(总金额)
	 * @return
	 */
	public Map<String,Object> createNATIVEOrder(String order_sn,String total_fee);
	
	/**
	 * 创建扫码订单[pc端支付]
	 * @param order_sn(订单号)
	 * @param total_fee(总金额)
	 * @param body(商品描述)[商品描述交易字段格式根据不同的应用场景按照以下格式：
                          APP——需传入应用市场上的APP名字-实际商品名称，天天爱消除-游戏充值。]
	 * @return
	 */
	public Map<String,Object> createNATIVEOrder(String order_sn,String total_fee,String body);
	
	
	/**
	 * 创建JSAPI订单[移动端支付]
	 * @param order_sn(订单号)
	 * @param total_fee(总金额)
	 * @param openid(trade_type=JSAPI时（即公众号支付），此参数必传，此参数为微信用户在商户对应appid下的唯一标识。openid如何获取，可参考【获取openid】。企业号请使用【企业号OAuth2.0接口】获取企业号内成员userid，再调用【企业号userid转openid接口】进行转换)
	 * @return
	 */
	public Map<String,Object> createJSAPIOrder(String order_sn,String total_fee,String openid);
	
	/**
	 * 创建JSAPI订单[移动端支付]
	 * @param order_sn(订单号)
	 * @param total_fee(总金额)
	 * @param openid(trade_type=JSAPI时（即公众号支付），此参数必传，此参数为微信用户在商户对应appid下的唯一标识。openid如何获取，可参考【获取openid】。企业号请使用【企业号OAuth2.0接口】获取企业号内成员userid，再调用【企业号userid转openid接口】进行转换)
	 * @param body(商品描述)[商品描述交易字段格式根据不同的应用场景按照以下格式：
                          APP——需传入应用市场上的APP名字-实际商品名称，天天爱消除-游戏充值。]
	 * @return
	 */
	public Map<String,Object> createJSAPIOrder(String order_sn,String total_fee,String openid,String body);
	
	
}
