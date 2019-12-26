package com.yunwei.product.common.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.util.DateUtil;

/**
 * 沙龙注册订单信息表
* @ClassName: SalonOrder
* @Description: TODO(TODO) 
* @author yanf
* @date 2019年06月25日 上午10:57:04
*
 */
public class QhzSalonOrder {

   private String id; // 编号
   private String openid; // openid
   private String order_sn; // 订单号
   private String order_type; // 订单类型(1报名订单
   private String order_total; // 订单总价
   private String order_realtotal; // 实际支付金额
   private String order_curriculums; // 所报课程信息
   private String order_adviser; // 学习顾问
   private String order_sc_price; // 奖学金
   private Date create_datetime; // 创建时间
   private Date update_datetime; // 更新时间
   private String order_name; // 订单创建人
   private String order_tel; // 订单创建人联系电话
   private String order_benefit_name; // 订单受益人
   private String order_benefit_card; // 订单受益人身份证
   private String order_address; // 订单创建人联系地址
   private String order_remark; // 订单备注
   private String status; // 支付状态(1待支付 2已支付)
   
   //课程订单项
   private List<QhzSalonOrderItem> orderItemList = new ArrayList<QhzSalonOrderItem>();

	public String getId() {
	    return this.id;
	}
	
	public void setId(String id) {
	    this.id = id;
	}
    public String getOpenid() {
	    return this.openid;
	}
	
	public void setOpenid(String openid) {
	    this.openid = openid;
	}
    public String getOrder_sn() {
	    return this.order_sn;
	}
	
	public void setOrder_sn(String order_sn) {
	    this.order_sn = order_sn;
	}
    public String getOrder_type() {
	    return this.order_type;
	}
	
	public void setOrder_type(String order_type) {
	    this.order_type = order_type;
	}
    public String getOrder_total() {
	    return this.order_total;
	}
	
	public void setOrder_total(String order_total) {
	    this.order_total = order_total;
	}
    public String getOrder_realtotal() {
	    return this.order_realtotal;
	}
	
	public void setOrder_realtotal(String order_realtotal) {
	    this.order_realtotal = order_realtotal;
	}
    public String getOrder_curriculums() {
	    return this.order_curriculums;
	}
	
	public void setOrder_curriculums(String order_curriculums) {
	    this.order_curriculums = order_curriculums;
	}
    @JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
    public Date getCreate_datetime() {
	    return this.create_datetime;
	}
	
	public void setCreate_datetime(Date create_datetime) {
	    this.create_datetime = create_datetime;
	}
    @JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
    public Date getUpdate_datetime() {
	    return this.update_datetime;
	}
	
	public void setUpdate_datetime(Date update_datetime) {
	    this.update_datetime = update_datetime;
	}
    public String getOrder_name() {
	    return this.order_name;
	}
	
	public void setOrder_name(String order_name) {
	    this.order_name = order_name;
	}
    public String getOrder_tel() {
	    return this.order_tel;
	}
	
	public void setOrder_tel(String order_tel) {
	    this.order_tel = order_tel;
	}
    public String getOrder_address() {
	    return this.order_address;
	}
	
	public void setOrder_address(String order_address) {
	    this.order_address = order_address;
	}
    public String getOrder_remark() {
	    return this.order_remark;
	}
	
	public void setOrder_remark(String order_remark) {
	    this.order_remark = order_remark;
	}
    public String getStatus() {
	    return this.status;
	}
	
	public void setStatus(String status) {
	    this.status = status;
	}

	/** 
	* @return order_adviser 
	*/
	public String getOrder_adviser() {
		return order_adviser;
	}

	/** 
	* @param order_adviser 要设置的 order_adviser 
	*/
	public void setOrder_adviser(String order_adviser) {
		this.order_adviser = order_adviser;
	}

	/** 
	* @return order_sc_price 
	*/
	public String getOrder_sc_price() {
		return order_sc_price;
	}

	/** 
	* @param order_sc_price 要设置的 order_sc_price 
	*/
	public void setOrder_sc_price(String order_sc_price) {
		this.order_sc_price = order_sc_price;
	}

	/** 
	* @return orderItemList 
	*/
	public List<QhzSalonOrderItem> getOrderItemList() {
		return orderItemList;
	}

	/** 
	* @param orderItemList 要设置的 orderItemList 
	*/
	public void setOrderItemList(List<QhzSalonOrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}

	/** 
	* @return order_benefit_name 
	*/
	public String getOrder_benefit_name() {
		return order_benefit_name;
	}

	/** 
	* @param order_benefit_name 要设置的 order_benefit_name 
	*/
	public void setOrder_benefit_name(String order_benefit_name) {
		this.order_benefit_name = order_benefit_name;
	}

	/** 
	* @return order_benefit_card 
	*/
	public String getOrder_benefit_card() {
		return order_benefit_card;
	}

	/** 
	* @param order_benefit_card 要设置的 order_benefit_card 
	*/
	public void setOrder_benefit_card(String order_benefit_card) {
		this.order_benefit_card = order_benefit_card;
	}


	
}
