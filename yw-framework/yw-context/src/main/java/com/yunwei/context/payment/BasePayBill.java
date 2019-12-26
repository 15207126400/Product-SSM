package com.yunwei.context.payment;

/**
 * 业务相关的base支付订单账单接口
* @ClassName: BusinessBasePayment 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年5月31日 下午1:27:29 
*
 */
public interface BasePayBill {

	/**
	 * 支付订单账单下载
	 * @param bill_date(对账单日期)[下载对账单的日期，格式：20140603]
	 * @param bill_type(账单类型)[ ALL，返回当日所有订单信息，默认值
								SUCCESS，返回当日成功支付的订单
								REFUND，返回当日退款订单
								RECHARGE_REFUND，返回当日充值退款订单]
	 * @param tar_type(压缩账单)[非必传参数，固定值：GZIP，返回格式为.gzip的压缩包账单。不传则默认为数据流形式。]							
	 * @return
	 */
	public byte[] downloadBill(String bill_date,String bill_type,String tar_type);
	
	/**
	 * 支付订单资金账单下载
	 * @param request_url(账单下载url)
	 * @param bill_date(对账单日期)[下载对账单的日期，格式：20140603]
	 * @param bill_type(账单类型)[ 账单的资金来源账户：
								Basic  基本账户
								Operation 运营账户
								Fees 手续费账户]
	 * @param tar_type(压缩账单)[非必传参数，固定值：GZIP，返回格式为.gzip的压缩包账单。不传则默认为数据流形式。]							
	 * @return
	 */
	public byte[] downloadRundFlow(String bill_date,String account_type,String tar_type);
}
