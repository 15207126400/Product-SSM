package com.yunwei.context.payment;

import java.util.Map;

/**
 * 业务相关的base支付订单退款接口
* @ClassName: BaseBusinessPaymentRefund 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年5月31日 下午2:02:21 
*
 */
public interface BasePayRefund {

	/**
	 * 支付订单退款申请
	 * @param order_sn(订单号)
	 * @param out_refund_no(退款单号)
	 * @param total_fee(订单金额)
	 * @param refund_fee(退款金额)
	 * @return
	 */
	public Map<String,Object> refund(String order_sn,String out_refund_no,String total_fee,String refund_fee);
	
	
	/**
	 * 支付订单退款申请
	 * @param order_sn(订单号)
	 * @param out_refund_no(退款单号)
	 * @param total_fee(订单金额)
	 * @param refund_fee(退款金额)
	 * @param refund_desc(退款原因)
	 * @return
	 */
	public Map<String,Object> refund(String order_sn,String out_refund_no,String total_fee,String refund_fee,String refund_desc);
	
	/**
	 * 支付订单退款申请
	 * @param request_map(退款参数)
	 */
	public Map<String,Object> refund(Map<String, Object> request_map);
	
	/**
	 * 支付订单退款查询
	 * @param order_sn(订单号)
	 * @return
	 */
	public Map<String,Object> refundQuery(String order_sn);
	
	/**
	 * 支付订单退款查询
	 * @param order_sn(订单号)
	 * @param offset(偏移量)[偏移量，当部分退款次数超过10次时可使用，表示返回的查询结果从这个偏移量开始取记录]
	 * @return
	 */
	public Map<String,Object> refundQuery(String order_sn,int offset);
	
	/**
	 * 支付订单退款查询
	 * @param request_map(退款查询参数)
	 * @return
	 */
	public Map<String,Object> refundQuery(Map<String, Object> request_map);
}
