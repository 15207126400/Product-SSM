package com.yunwei.context.sys.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwei.context.payment.PayService;
import com.yunwei.context.payment.support.WxPayConfig;
import com.yunwei.context.payment.support.WxPayService;
import com.yunwei.context.sys.cache.YwUserCustomerXcxCache;
import com.yunwei.context.sys.service.YwPayService;
import com.yunwei.context.sys.usercenter.service.YwUserCustomerXcxService;
import com.yunwei.context.sys.usercenter.util.WxPayUtil;

/**
 * 云维支付服务接口实现
* @ClassName: YwWxPayServiceImpl 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2019年2月22日 下午5:41:02 
*
 */
@Service
public class YwPayServiceImpl implements YwPayService{

	@Autowired
	private PayService payService;
	@Autowired
	private YwUserCustomerXcxService ywUserCustomerXcxService;
	@Autowired
	private YwUserCustomerXcxCache ywUserCustomerXcxCache;
	
	@Override
	public Map<String, Object> createNATIVEOrder(String order_sn,String total_fee) {
		payService.setWxPayConfig(this.getWxPayConfig());
		return payService.createNATIVEOrder(order_sn, total_fee);
	}

	@Override
	public Map<String, Object> createNATIVEOrder(String order_sn,
			String total_fee, String body) {
		payService.setWxPayConfig(this.getWxPayConfig());
		return payService.createNATIVEOrder(order_sn, total_fee, body);
	}

	@Override
	public Map<String, Object> createJSAPIOrder(String order_sn,
			String total_fee, String openid) {
		payService.setWxPayConfig(this.getWxPayConfig());
		return payService.createJSAPIOrder(order_sn, total_fee, openid);
	}

	@Override
	public Map<String, Object> createJSAPIOrder(String order_sn,
			String total_fee, String openid, String body) {
		payService.setWxPayConfig(this.getWxPayConfig());
		return payService.createNATIVEOrder(order_sn, total_fee, body);
	}

	@Override
	public Map<String, Object> createOrder(Map<String, Object> request_map) {
		payService.setWxPayConfig(this.getWxPayConfig());
		return payService.createOrder(request_map);
	}

	@Override
	public byte[] downloadBill(String bill_date, String bill_type,
			String tar_type) {
		payService.setWxPayConfig(this.getWxPayConfig());
		return payService.downloadBill(bill_date, bill_type, tar_type);
	}

	@Override
	public byte[] downloadRundFlow(String bill_date, String account_type,
			String tar_type) {
		payService.setWxPayConfig(this.getWxPayConfig());
		return payService.downloadRundFlow(bill_date, account_type, tar_type);
	}

	@Override
	public Map<String, Object> batchQueryComment(String begin_time,
			String end_time, String offset) {
		payService.setWxPayConfig(this.getWxPayConfig());
		return payService.batchQueryComment(begin_time, end_time, offset);
	}

	@Override
	public Map<String, Object> batchQueryComment(String begin_time,
			String end_time, String offset, String limit) {
		payService.setWxPayConfig(this.getWxPayConfig());
		return payService.batchQueryComment(begin_time, end_time, offset, limit);
	}

	@Override
	public Map<String, Object> orderQuery(String order_sn) {
		payService.setWxPayConfig(this.getWxPayConfig());
		return payService.orderQuery(order_sn);
	}

	@Override
	public Map<String, Object> orderQuery(Map<String, Object> request_map) {
		payService.setWxPayConfig(this.getWxPayConfig());
		return payService.orderQuery(request_map);
	}

	@Override
	public Map<String, Object> closeOrder(String order_sn) {
		payService.setWxPayConfig(this.getWxPayConfig());
		return payService.closeOrder(order_sn);
	}

	@Override
	public Map<String, Object> refund(String order_sn, String out_refund_no,
			String total_fee, String refund_fee) {
		payService.setWxPayConfig(this.getWxPayConfig());
		return payService.refund(order_sn, out_refund_no, total_fee, refund_fee);
	}

	@Override
	public Map<String, Object> refund(String order_sn, String out_refund_no,
			String total_fee, String refund_fee, String refund_desc) {
		payService.setWxPayConfig(this.getWxPayConfig());
		return payService.refund(order_sn, out_refund_no, total_fee, refund_fee, refund_desc);
	}

	@Override
	public Map<String, Object> refund(Map<String, Object> request_map) {
		payService.setWxPayConfig(this.getWxPayConfig());
		return payService.refund(request_map);
	}

	@Override
	public Map<String, Object> refundQuery(String order_sn) {
		payService.setWxPayConfig(this.getWxPayConfig());
		return payService.refundQuery(order_sn);
	}

	@Override
	public Map<String, Object> refundQuery(String order_sn, int offset) {
		payService.setWxPayConfig(this.getWxPayConfig());
		return payService.refundQuery(order_sn, offset);
	}

	@Override
	public Map<String, Object> refundQuery(Map<String, Object> request_map) {
		payService.setWxPayConfig(this.getWxPayConfig());
		return payService.refundQuery(request_map);
	}

	/**
	 * 获取云维微信支付配置
	 * @return
	 */
	private WxPayConfig getWxPayConfig(){
		WxPayConfig payConfig = WxPayUtil.getWxPaymentConfig();
		return payConfig;
	}

	@Override
	public void setWxPayConfig(WxPayConfig wxPayConfig) {
		
	}

	
	
}
