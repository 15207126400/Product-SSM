package com.yunwei.product.common.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.util.DateUtil;

/**
 * 
* @ClassName: ShopPayment 
* @Description: 支付信息实体类 
* @author 晏飞
* @date 2017年12月26日 下午8:05:55 
*
 */
public class YwOrderPayment {
	private String   order_pay_id         ; //  '支付编号',
	private String order_pay_price      ; //  '支付金额',
	private String order_pay_status     ; //  '支付状态',
	private String order_sn             ; //  '订单号',
	private String order_pay_type = "0";// 支付类型
	private String order_pay_method     ; //  '支付方式',
	private Date order_pay_createtime ; //  '支付创建时间',
	private Date order_pay_updatetime ; //  '支付更新时间',
	private String order_pay_remark     ; //  '支付备注',
	private String order_pay_url        ; //  '支付url',
	private String order_pay_returnUrl  ; //  '支付回调url',
	private String order_pay_code       ; //  '支付单号',
	private String open_id              ; //  '用户openid',
	private String order_pay_errorNo    ; //  '支付错误编号',
	private String order_pay_errorInfo  ; //  '支付错误信息',
	private String branch_id;// 机构编号
	public String getOrder_pay_id() {
		return order_pay_id;
	}
	public void setOrder_pay_id(String order_pay_id) {
		this.order_pay_id = order_pay_id;
	}
	public String getOrder_pay_price() {
		return order_pay_price;
	}
	public void setOrder_pay_price(String order_pay_price) {
		this.order_pay_price = order_pay_price;
	}
	public String getOrder_pay_status() {
		return order_pay_status;
	}
	public void setOrder_pay_status(String order_pay_status) {
		this.order_pay_status = order_pay_status;
	}
	public String getOrder_sn() {
		return order_sn;
	}
	public void setOrder_sn(String order_sn) {
		this.order_sn = order_sn;
	}
	public String getOrder_pay_method() {
		return order_pay_method;
	}
	public void setOrder_pay_method(String order_pay_method) {
		this.order_pay_method = order_pay_method;
	}
	@JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
	public Date getOrder_pay_createtime() {
		return order_pay_createtime;
	}
	public void setOrder_pay_createtime(Date order_pay_createtime) {
		this.order_pay_createtime = order_pay_createtime;
	}
	@JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
	public Date getOrder_pay_updatetime() {
		return order_pay_updatetime;
	}
	public void setOrder_pay_updatetime(Date order_pay_updatetime) {
		this.order_pay_updatetime = order_pay_updatetime;
	}
	public String getOrder_pay_remark() {
		return order_pay_remark;
	}
	public void setOrder_pay_remark(String order_pay_remark) {
		this.order_pay_remark = order_pay_remark;
	}
	public String getOrder_pay_url() {
		return order_pay_url;
	}
	public void setOrder_pay_url(String order_pay_url) {
		this.order_pay_url = order_pay_url;
	}
	public String getOrder_pay_returnUrl() {
		return order_pay_returnUrl;
	}
	public void setOrder_pay_returnUrl(String order_pay_returnUrl) {
		this.order_pay_returnUrl = order_pay_returnUrl;
	}
	public String getOrder_pay_code() {
		return order_pay_code;
	}
	public void setOrder_pay_code(String order_pay_code) {
		this.order_pay_code = order_pay_code;
	}
	public String getOpen_id() {
		return open_id;
	}
	public void setOpen_id(String open_id) {
		this.open_id = open_id;
	}
	public String getOrder_pay_errorNo() {
		return order_pay_errorNo;
	}
	public void setOrder_pay_errorNo(String order_pay_errorNo) {
		this.order_pay_errorNo = order_pay_errorNo;
	}
	public String getOrder_pay_errorInfo() {
		return order_pay_errorInfo;
	}
	public void setOrder_pay_errorInfo(String order_pay_errorInfo) {
		this.order_pay_errorInfo = order_pay_errorInfo;
	}
	public String getBranch_id() {
		return branch_id;
	}
	public void setBranch_id(String branch_id) {
		this.branch_id = branch_id;
	}
	public String getOrder_pay_type() {
		return order_pay_type;
	}
	public void setOrder_pay_type(String order_pay_type) {
		this.order_pay_type = order_pay_type;
	}
	
	
	
}
