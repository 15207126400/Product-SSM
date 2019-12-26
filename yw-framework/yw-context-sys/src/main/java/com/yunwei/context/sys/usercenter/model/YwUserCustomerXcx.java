package com.yunwei.context.sys.usercenter.model;

import java.math.BigDecimal;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.util.DateUtil;

/**
 * 客户小程序信息
* @ClassName: YwUserCustomerXcx
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年09月17日 下午15:17:41
*
 */
public class YwUserCustomerXcx {

   private String id; // 主键编号
   private String user_id; // 用户编号
   private String xcx_name; // 小程序名称
   private String xcx_type; // 小程序类型（1、微信，2、支付宝）
   private String app_id; // 小程序ID
   private String app_secret; // 小程序密钥
   private String mch_id; // 商户号(支付相关)
   private String xcx_pay_key; // 支付秘钥（主要用于签名用）
   private String sign_type; // 参数签名类型（1、MD5，2、）
   private String param_type; // 参数类型(支付请求参数)（1、xml，2、json）
   private String xcx_order_ip; // 小程序创建订单的IP地址
   private String xcx_order_body; // 小程序订单描述
   private String cert_path; // 证书路径
   private String cert_type; // 证书类型（1、PKCS12，2、）
   private Date xcx_createtime; // 创建时间
   private Date xcx_updatetime; // 更新时间
   private String xcx_status; // 小程序状态（1：启用，2、禁用）

    public String getId() {
	    return this.id;
	}
	
	public void setId(String id) {
	    this.id = id;
	}
    public String getUser_id() {
	    return this.user_id;
	}
	
	public void setUser_id(String user_id) {
	    this.user_id = user_id;
	}
    public String getXcx_type() {
	    return this.xcx_type;
	}
	
	public void setXcx_type(String xcx_type) {
	    this.xcx_type = xcx_type;
	}
    public String getApp_id() {
	    return this.app_id;
	}
	
	public void setApp_id(String app_id) {
	    this.app_id = app_id;
	}
    public String getApp_secret() {
	    return this.app_secret;
	}
	
	public void setApp_secret(String app_secret) {
	    this.app_secret = app_secret;
	}
    public String getMch_id() {
	    return this.mch_id;
	}
	
	public void setMch_id(String mch_id) {
	    this.mch_id = mch_id;
	}
    public String getXcx_pay_key() {
	    return this.xcx_pay_key;
	}
	
	public void setXcx_pay_key(String xcx_pay_key) {
	    this.xcx_pay_key = xcx_pay_key;
	}
    public String getSign_type() {
	    return this.sign_type;
	}
	
	public void setSign_type(String sign_type) {
	    this.sign_type = sign_type;
	}
    public String getParam_type() {
	    return this.param_type;
	}
	
	public void setParam_type(String param_type) {
	    this.param_type = param_type;
	}
    public String getXcx_order_ip() {
	    return this.xcx_order_ip;
	}
	
	public void setXcx_order_ip(String xcx_order_ip) {
	    this.xcx_order_ip = xcx_order_ip;
	}
    public String getXcx_order_body() {
	    return this.xcx_order_body;
	}
	
	public void setXcx_order_body(String xcx_order_body) {
	    this.xcx_order_body = xcx_order_body;
	}
    public String getCert_path() {
	    return this.cert_path;
	}
	
	public void setCert_path(String cert_path) {
	    this.cert_path = cert_path;
	}
    public String getCert_type() {
	    return this.cert_type;
	}
	
	public void setCert_type(String cert_type) {
	    this.cert_type = cert_type;
	}
    @JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
    public Date getXcx_createtime() {
	    return this.xcx_createtime;
	}
	
	public void setXcx_createtime(Date xcx_createtime) {
	    this.xcx_createtime = xcx_createtime;
	}
    @JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
    public Date getXcx_updatetime() {
	    return this.xcx_updatetime;
	}
	
	public void setXcx_updatetime(Date xcx_updatetime) {
	    this.xcx_updatetime = xcx_updatetime;
	}
    public String getXcx_status() {
	    return this.xcx_status;
	}
	
	public void setXcx_status(String xcx_status) {
	    this.xcx_status = xcx_status;
	}

	public String getXcx_name() {
		return xcx_name;
	}

	public void setXcx_name(String xcx_name) {
		this.xcx_name = xcx_name;
	}
	
}
