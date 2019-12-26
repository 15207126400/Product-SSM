package com.yunwei.product.common.model;

import java.math.BigDecimal;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.util.DateUtil;

/**
 * 优惠券领取明细
* @ClassName: YwCouponCollectiondetails
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2019年01月03日 下午14:47:10
*
 */
public class YwCouponCollectiondetails {

   private String id; // 编号
   private String openid; // 用户openid
   private String coupon_id; // 优惠券编号
   private String coupon_price; // 优惠券面额
   private String coupon_function_type; // 优惠券作用类型(1满减卷,2折扣劵,3抵扣劵,4免邮券)
   private String coupon_use_condition_type; // 优惠券使用条件类型（0、不限制，1、限制）
   private String coupon_use_condition; // 优惠券使用条件（满n元可用）
   private String coupon_use_scope_type; // 优惠券使用范围类型（0、全场,1、单商品，2、全品类）
   private String coupon_use_scope; // 优惠券使用范围
   private Date coupon_starttime; // 优惠券有效时间
   private Date coupon_endtime; // 优惠券失效时间
   private Date starttime; // 领取时间
   private Date endtime; // 使用时间
   private String details_status; // 优惠券明细状态（0：未使用，1、已使用，2、已过期）
   private String discount_price;//折扣价(临时字段)
   

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
    public String getCoupon_id() {
	    return this.coupon_id;
	}
	
	public void setCoupon_id(String coupon_id) {
	    this.coupon_id = coupon_id;
	}
    public String getCoupon_price() {
	    return this.coupon_price;
	}
	
	public void setCoupon_price(String coupon_price) {
	    this.coupon_price = coupon_price;
	}
    public String getCoupon_function_type() {
	    return this.coupon_function_type;
	}
	
	public void setCoupon_function_type(String coupon_function_type) {
	    this.coupon_function_type = coupon_function_type;
	}
    public String getCoupon_use_condition_type() {
	    return this.coupon_use_condition_type;
	}
	
	public void setCoupon_use_condition_type(String coupon_use_condition_type) {
	    this.coupon_use_condition_type = coupon_use_condition_type;
	}
    public String getCoupon_use_condition() {
	    return this.coupon_use_condition;
	}
	
	public void setCoupon_use_condition(String coupon_use_condition) {
	    this.coupon_use_condition = coupon_use_condition;
	}
    public String getCoupon_use_scope_type() {
	    return this.coupon_use_scope_type;
	}
	
	public void setCoupon_use_scope_type(String coupon_use_scope_type) {
	    this.coupon_use_scope_type = coupon_use_scope_type;
	}
    public String getCoupon_use_scope() {
	    return this.coupon_use_scope;
	}
	
	public void setCoupon_use_scope(String coupon_use_scope) {
	    this.coupon_use_scope = coupon_use_scope;
	}
    @JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
    public Date getCoupon_starttime() {
	    return this.coupon_starttime;
	}
	
	public void setCoupon_starttime(Date coupon_starttime) {
	    this.coupon_starttime = coupon_starttime;
	}
    @JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
    public Date getCoupon_endtime() {
	    return this.coupon_endtime;
	}
	
	public void setCoupon_endtime(Date coupon_endtime) {
	    this.coupon_endtime = coupon_endtime;
	}
    @JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
    public Date getStarttime() {
	    return this.starttime;
	}
	
	public void setStarttime(Date starttime) {
	    this.starttime = starttime;
	}
    @JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
    public Date getEndtime() {
	    return this.endtime;
	}
	
	public void setEndtime(Date endtime) {
	    this.endtime = endtime;
	}
    public String getDetails_status() {
	    return this.details_status;
	}
	
	public void setDetails_status(String details_status) {
	    this.details_status = details_status;
	}

	/** 
	* @return discount_price 
	*/
	public String getDiscount_price() {
		return discount_price;
	}

	/** 
	* @param discount_price 要设置的 discount_price 
	*/
	public void setDiscount_price(String discount_price) {
		this.discount_price = discount_price;
	}
	
}
