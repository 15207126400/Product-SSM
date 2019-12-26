package com.yunwei.product.common.model;

import java.util.Date;

/**
 * 
* @ClassName: YwRefund 
* @Description: TODO(TODO) 
* @author 云维退款信息表
* @date 2018年5月17日 下午4:05:01 
*
 */
public class YwRefund {
	
	private String refund_id; // 退款id
	private String refund_sn; // 退款编号
	private String order_sn; // 订单编号
	private String refund_price; // 退款金额
	private String user_id; // 退款申请人
	private Date refund_createtime; // 退款创建时间
	private Date refund_updatetime; // 退款更新时间
	private String operator_no; // 退款审核人编号
	private String refund_type; // 退款类型
	private String refund_status; // 退款状态
	private String refund_remark; // 退款备注
	private String refund_error_no;// 退款错误编号（微信返回）
	private String refund_error_info;// 退款错误信息（微信返回）
	public String getRefund_id() {
		return refund_id;
	}
	public void setRefund_id(String refund_id) {
		this.refund_id = refund_id;
	}
	public String getRefund_sn() {
		return refund_sn;
	}
	public void setRefund_sn(String refund_sn) {
		this.refund_sn = refund_sn;
	}
	public String getOrder_sn() {
		return order_sn;
	}
	public void setOrder_sn(String order_sn) {
		this.order_sn = order_sn;
	}
	public String getRefund_price() {
		return refund_price;
	}
	public void setRefund_price(String refund_price) {
		this.refund_price = refund_price;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	public Date getRefund_createtime() {
		return refund_createtime;
	}
	public void setRefund_createtime(Date refund_createtime) {
		this.refund_createtime = refund_createtime;
	}
	public Date getRefund_updatetime() {
		return refund_updatetime;
	}
	public void setRefund_updatetime(Date refund_updatetime) {
		this.refund_updatetime = refund_updatetime;
	}
	public String getOperator_no() {
		return operator_no;
	}
	public void setOperator_no(String operator_no) {
		this.operator_no = operator_no;
	}
	public String getRefund_type() {
		return refund_type;
	}
	public void setRefund_type(String refund_type) {
		this.refund_type = refund_type;
	}
	public String getRefund_status() {
		return refund_status;
	}
	public void setRefund_status(String refund_status) {
		this.refund_status = refund_status;
	}
	public String getRefund_remark() {
		return refund_remark;
	}
	public void setRefund_remark(String refund_remark) {
		this.refund_remark = refund_remark;
	}
	public String getRefund_error_no() {
		return refund_error_no;
	}
	public void setRefund_error_no(String refund_error_no) {
		this.refund_error_no = refund_error_no;
	}
	public String getRefund_error_info() {
		return refund_error_info;
	}
	public void setRefund_error_info(String refund_error_info) {
		this.refund_error_info = refund_error_info;
	}
    
	
}
