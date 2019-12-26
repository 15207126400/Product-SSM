package com.yunwei.context.payment.support;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.yunwei.common.util.EnvironmentUtils;
import com.yunwei.common.util.HttpClientUtil;
import com.yunwei.context.payment.PayService;
import com.yunwei.context.payment.support.WxPayConfig;
import com.yunwei.context.payment.util.WXUtil;

/**
 * 微信支付相关业务实现
* @ClassName: WxBusinessPayment 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年5月31日 下午4:32:01 
*
 */
@Service
public class WxPayService extends AbstractWxPayService implements PayService{
	private static Logger logger = Logger.getLogger(WxPayService.class);

	/***  url链接常量  ***/
	private final static String order_create_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";   //微信创建订单接口
	private final static String order_query_url = "https://api.mch.weixin.qq.com/pay/orderquery";   //微信订单查询
	private final static String order_close_url = "https://api.mch.weixin.qq.com/pay/closeorder";   //微信订单关闭
	private final static String order_refund_url = "https://api.mch.weixin.qq.com/secapi/pay/refund";   //微信退款申请
	private final static String order_refund_query_url = "https://api.mch.weixin.qq.com/pay/refundquery";   //微信退款查询
	private final static String order_download_bill = "https://api.mch.weixin.qq.com/pay/downloadbill";   //微信对账单下载
	private final static String order_download_fund_flow = "https://api.mch.weixin.qq.com/pay/downloadfundflow";   //微信资金账单下载
	private final static String order_batch_query_comment = "https://api.mch.weixin.qq.com/billcommentsp/batchquerycomment";   //微信订单评价查询
	
	/**  支付需要基本参数  **/
	private static String sign_type = "MD5";// 参数签名类型
	private final static String cert_prefix = "cert";
	private Map<String, Object> paramMap = new HashMap<String, Object>();// 支付参数map
	private WxPayConfig wxPayConfig = new WxPayConfig();// 支付条件配置信息[默认为本地支付]

	@Override
	public Map<String, Object> refund(String order_sn, String out_refund_no,String total_fee, String refund_fee) {
		return refund(order_sn, out_refund_no, total_fee, refund_fee, "");
	}

	@Override
	public Map<String, Object> refund(String order_sn, String out_refund_no,String total_fee, String refund_fee, String refund_desc) {
		Map<String, Object> request_map = new HashMap<String, Object>();
		request_map.put("out_trade_no", order_sn);
		request_map.put("out_refund_no", out_refund_no);
		request_map.put("total_fee", total_fee);
		request_map.put("refund_fee", refund_fee);
		request_map.put("refund_desc", refund_desc);
		return refund(request_map);
	}

	@Override
	public Map<String, Object> refund(Map<String, Object> request_map) {
		// 加载证书
		String cert_type = "";
		if(StringUtils.equals(wxPayConfig.getCert_type(), "1")){
			cert_type = "PKCS12";
		}
		sisapService.setConn(HttpClientUtil.getInstance(EnvironmentUtils.getWebClassPath()+cert_prefix+wxPayConfig.getCert_path(), wxPayConfig.getMch_id(), cert_type));
		request_map.putAll(paramMap);
		request_map.put("op_user_id",wxPayConfig.getMch_id());
		request_map.remove("sign_type");
		return super.execute(order_refund_url, request_map);
	}

	@Override
	public Map<String, Object> refundQuery(String order_sn) {
		
		return refundQuery(order_sn, 0);
	}

	@Override
	public Map<String, Object> refundQuery(String order_sn, int offset) {
		Map<String, Object> request_map = new HashMap<String, Object>();
		request_map.put("out_trade_no", order_sn);
		request_map.put("offset", offset);
		
		return refundQuery(request_map);
	}

	@Override
	public Map<String, Object> refundQuery(Map<String, Object> request_map) {
		// 加载证书
		String cert_type = "";
		if(StringUtils.equals(wxPayConfig.getCert_type(), "1")){
			cert_type = "PKCS12";
		}
		sisapService.setConn(HttpClientUtil.getInstance(EnvironmentUtils.getWebClassPath()+cert_prefix+wxPayConfig.getCert_path(), wxPayConfig.getMch_id(), cert_type));
		request_map.putAll(paramMap);
		return super.execute(order_refund_query_url, request_map);
	}

	@Override
	public Map<String, Object> orderQuery(String order_sn) {
		Map<String, Object> request_map = new HashMap<String, Object>();
		request_map.put("out_trade_no", order_sn);
		return orderQuery(request_map);
	}

	@Override
	public Map<String, Object> orderQuery(Map<String, Object> request_map) {
		request_map.putAll(paramMap);
		return super.execute(order_query_url, request_map);
	}

	@Override
	public Map<String, Object> closeOrder(String order_sn) {
		Map<String, Object> request_map = new HashMap<String, Object>();
		request_map.put("out_trade_no", order_sn);
		request_map.putAll(paramMap);
		return super.execute(order_close_url, request_map);
	}

	@Override
	public Map<String, Object> batchQueryComment(String begin_time,String end_time, String offset) {
		
		return batchQueryComment(begin_time, end_time, offset, "200");
	}

	@Override
	public Map<String, Object> batchQueryComment(String begin_time,String end_time, String offset, String limit) {
		Map<String, Object> request_map = new HashMap<String, Object>();
		request_map.put("begin_time", begin_time);
		request_map.put("end_time", end_time);
		request_map.put("offset", offset);
		request_map.put("limit", limit);
		request_map.putAll(paramMap);
		return super.execute(order_batch_query_comment, request_map);
	}

	@Override
	public byte[] downloadBill(String bill_date, String bill_type,String tar_type) {
		Map<String, Object> request_map = new HashMap<String, Object>();
		request_map.put("bill_date", bill_date);
		request_map.put("bill_type", bill_type);
		request_map.put("tar_type", tar_type);
		request_map.putAll(paramMap);
		byte[] bs = super.executeBinary(order_download_bill, request_map);
		return bs;
	}

	@Override
	public byte[] downloadRundFlow(String bill_date, String account_type,String tar_type) {
		Map<String, Object> request_map = new HashMap<String, Object>();
		request_map.put("bill_date", bill_date);
		request_map.put("account_type", account_type);
		request_map.put("tar_type", tar_type);
		request_map.putAll(paramMap);
		byte[] bs = super.executeBinary(order_download_fund_flow, request_map);
		return bs;
	}

	@Override
	public Map<String, Object> createNATIVEOrder(String order_sn,String total_fee) {
		
		return createNATIVEOrder(order_sn, total_fee, "");
	}

	@Override
	public Map<String, Object> createNATIVEOrder(String order_sn,String total_fee, String body) {
		Map<String, Object> request_map = new HashMap<String, Object>();
		request_map.put("out_trade_no", order_sn);
		request_map.put("trade_type", "NATIVE");
		request_map.put("total_fee", total_fee);
		request_map.put("product_id", String.valueOf(System.currentTimeMillis()));
		request_map.put("body", body);
		return createOrder(request_map);
	}

	@Override
	public Map<String, Object> createJSAPIOrder(String order_sn,String total_fee,String openid) {
		
		return createJSAPIOrder(order_sn, total_fee, openid, "");
	}
	
	@Override
	public Map<String, Object> createJSAPIOrder(String order_sn,String total_fee,String openid, String body) {
		Map<String, Object> request_map = new HashMap<String, Object>();
		request_map.put("out_trade_no", order_sn);
		request_map.put("trade_type", "JSAPI");
		request_map.put("openid", openid);
		request_map.put("total_fee", total_fee);
		request_map.put("body", body);
		
		// 组装响应参数
		Map<String, Object> resultMap = createOrder(request_map);
		String nonceStr  =WXUtil.generate();
		Long   timeStamp = System.currentTimeMillis() / 1000;
		SortedMap<Object, Object> params = new TreeMap<Object, Object>();
		
		params.put("appId", wxPayConfig.getApp_id());
		params.put("nonceStr", nonceStr);
		params.put("package", "prepay_id=" + resultMap.get("prepay_id")); 
		params.put("signType", "MD5"); 
		params.put("timeStamp", timeStamp); 
		
		String paySign = WXUtil.createSign_ChooseWXPay("UTF-8", params, wxPayConfig.getXcx_pay_key());
		resultMap.put("paySign", paySign);
		resultMap.put("timeStamp", timeStamp + "");
		resultMap.put("nonceStr", nonceStr);
		resultMap.put("signType", "MD5");
		
		resultMap.put("package", "prepay_id=" + resultMap.get("prepay_id"));
		resultMap.put("return_code", "SUCCESS");
		return resultMap;
	}
	
	@Override
	public Map<String, Object> createOrder(Map<String, Object> request_map) {
		request_map.putAll(paramMap);
		if(request_map.get("notify_url") == null){
			request_map.put("notify_url", wxPayConfig.getPay_success_return_url()); // 回调url
		}
		if(request_map.get("body") == null || StringUtils.isBlank(request_map.get("body").toString())){
			request_map.put("body",wxPayConfig.getXcx_order_body()); // 商品描述
		}
		request_map.put("spbill_create_ip", wxPayConfig.getXcx_order_ip());// APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。
		return super.execute(order_create_url, request_map);
	}

	@Override
	protected String getParameterType() {
		if(StringUtils.equals(wxPayConfig.getParam_type(), "1")){
			return "xml";
		} else if(StringUtils.equals(wxPayConfig.getParam_type(), "2")){
			return "json";
		}
		return "xml";
	}

	@Override
	protected String getPayName() {
		if(StringUtils.equals(wxPayConfig.getXcx_type(), "1")){
			return "微信支付";
		} else if(StringUtils.equals(wxPayConfig.getXcx_type(), "2")){
			return "支付宝支付";
		}
		return "微信支付";
	}

	@Override
	protected String getSignType() {
		return (String) paramMap.get("sign_type");
	}

	@Override
	protected String getSignKey() {
		return wxPayConfig.getXcx_pay_key();
	}

	/**
	 * 设置支付条件参数
	 * @param wxPayConfig
	 */
	@Override
	public void setWxPayConfig(WxPayConfig wxPayConfig) {
		paramMap.put("mch_id",wxPayConfig.getMch_id()); // 商户号
		paramMap.put("appid",wxPayConfig.getApp_id()); // appid
		paramMap.put("nonce_str", WXUtil.generate()); // 随机字符串
		// 设置参数签名类型
		if(StringUtils.equals(wxPayConfig.getSign_type(), "1")){
			paramMap.put("sign_type", sign_type);
		}
		this.wxPayConfig = wxPayConfig;
	}
	
}
