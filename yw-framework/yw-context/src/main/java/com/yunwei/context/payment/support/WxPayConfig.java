package com.yunwei.context.payment.support;

/**
 * 微信支付条件对象(并设置默认值)
* @ClassName: WxPaymentConfig 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年9月20日 下午3:22:58 
*
 */
public class WxPayConfig {
	   private String xcx_type = "1"; // 小程序类型（1、微信，2、支付宝）（支付类型）
	   private String app_id = "wx7eb46168a3284ee0"; // 小程序ID
	   private String app_secret = "051af4318431fc608029ac569c9dc77c"; // 小程序密钥
	   private String mch_id = "1494978182"; // 商户号(支付相关)[证书密码]
	   private String xcx_pay_key = "8619835zxc8619835zxc8619835zxc22"; // 支付秘钥（主要用于签名用）
	   private String sign_type = "1"; // 参数签名类型（1、MD5，2、）
	   private String param_type = "1"; // 参数类型(支付请求参数)（1、xml，2、json）
	   private String xcx_order_ip = "111.230.177.61"; // 小程序创建订单的IP地址
	   private String xcx_order_body = "账号1494978182-云维科技"; // 小程序订单描述
	   private String cert_path = "/default/apiclient_cert.p12"; // 证书路径
	   private String cert_type = "1"; // 证书类型（1、PKCS12，2、）
	   //private String pay_success_return_url = "https://xcx.whywxx.com/yw-product-infobase/YW_POT_00024.json";	//云维支付成功回调
	   private String pay_success_return_url = "https://www.qhzhlkj.com/yw-product-infobase/YW_POT_00024.json";	//启恒智支付成功回调
	   
	public String getXcx_type() {
		return xcx_type;
	}
	public void setXcx_type(String xcx_type) {
		this.xcx_type = xcx_type;
	}
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}
	public String getApp_secret() {
		return app_secret;
	}
	public void setApp_secret(String app_secret) {
		this.app_secret = app_secret;
	}
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
	public String getXcx_pay_key() {
		return xcx_pay_key;
	}
	public void setXcx_pay_key(String xcx_pay_key) {
		this.xcx_pay_key = xcx_pay_key;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getParam_type() {
		return param_type;
	}
	public void setParam_type(String param_type) {
		this.param_type = param_type;
	}
	public String getXcx_order_ip() {
		return xcx_order_ip;
	}
	public void setXcx_order_ip(String xcx_order_ip) {
		this.xcx_order_ip = xcx_order_ip;
	}
	public String getXcx_order_body() {
		return xcx_order_body;
	}
	public void setXcx_order_body(String xcx_order_body) {
		this.xcx_order_body = xcx_order_body;
	}
	public String getCert_path() {
		return cert_path;
	}
	public void setCert_path(String cert_path) {
		this.cert_path = cert_path;
	}
	public String getCert_type() {
		return cert_type;
	}
	public void setCert_type(String cert_type) {
		this.cert_type = cert_type;
	}
	public String getPay_success_return_url() {
		return pay_success_return_url;
	}
	public void setPay_success_return_url(String pay_success_return_url) {
		this.pay_success_return_url = pay_success_return_url;
	}
	   
	   
}
