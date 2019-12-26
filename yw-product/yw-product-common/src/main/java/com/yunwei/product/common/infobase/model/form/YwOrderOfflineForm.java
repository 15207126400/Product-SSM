package com.yunwei.product.common.infobase.model.form;

import java.util.Date;

/**
 * 线下订单form表单
* @ClassName: YwOrderOfflineForm 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年11月12日 下午2:12:14 
*
 */
public class YwOrderOfflineForm {

	   private int offline_type; // 线下订单类型（0、客户扫码，1、商家扫码）
	   private String branch_id; // 机构编号（超市或者其他机构）
	   private String branch_name; // 机构名称
	   private String openid; // 用户openid
	   private String nickname; // 用户昵称
	   private String offline_totalprice;// 线下消费金额
	   private String order_totalprice; // 订单金额
	   private String order_discount_price; // 订单优惠金额
	   private String order_nodiscount_price;// 不参与优惠的金额
	   private String offline_remark; // 线下订单备注
	   private String offline_status; // 线下订单状态（0，待支付，1、支付成功，2、支付失败）
	   private String order_pay_type;// 支付类型
	   private String order_pay_method;// 支付方式
	public int getOffline_type() {
		return offline_type;
	}
	public void setOffline_type(int offline_type) {
		this.offline_type = offline_type;
	}
	public String getBranch_id() {
		return branch_id;
	}
	public void setBranch_id(String branch_id) {
		this.branch_id = branch_id;
	}
	public String getBranch_name() {
		return branch_name;
	}
	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getOffline_totalprice() {
		return offline_totalprice;
	}
	public void setOffline_totalprice(String offline_totalprice) {
		this.offline_totalprice = offline_totalprice;
	}
	public String getOrder_totalprice() {
		return order_totalprice;
	}
	public void setOrder_totalprice(String order_totalprice) {
		this.order_totalprice = order_totalprice;
	}
	public String getOrder_discount_price() {
		return order_discount_price;
	}
	public void setOrder_discount_price(String order_discount_price) {
		this.order_discount_price = order_discount_price;
	}
	public String getOrder_nodiscount_price() {
		return order_nodiscount_price;
	}
	public void setOrder_nodiscount_price(String order_nodiscount_price) {
		this.order_nodiscount_price = order_nodiscount_price;
	}
	public String getOffline_remark() {
		return offline_remark;
	}
	public void setOffline_remark(String offline_remark) {
		this.offline_remark = offline_remark;
	}
	public String getOffline_status() {
		return offline_status;
	}
	public void setOffline_status(String offline_status) {
		this.offline_status = offline_status;
	}
	public String getOrder_pay_type() {
		return order_pay_type;
	}
	public void setOrder_pay_type(String order_pay_type) {
		this.order_pay_type = order_pay_type;
	}
	public String getOrder_pay_method() {
		return order_pay_method;
	}
	public void setOrder_pay_method(String order_pay_method) {
		this.order_pay_method = order_pay_method;
	}
	   
	   
	   
}
