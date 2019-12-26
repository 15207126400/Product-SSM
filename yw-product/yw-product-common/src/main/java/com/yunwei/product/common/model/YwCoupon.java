package com.yunwei.product.common.model;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.util.DateUtil;

/**
 * 优惠券配置信息
* @ClassName: YwCoupon
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2019年01月07日 上午10:16:57
*
 */
public class YwCoupon {

   private String id; // 编号
   private String coupon_name; // 优惠券名称
   private String coupon_type; // 优惠券类型
   private String coupon_scene_type; // 优惠券场景类型
   private String coupon_function_type; // 优惠券作用类型(1、满减卷,2、折扣劵,3、抵扣劵,4、免邮券)
   private String coupon_use_scope_type; // 优惠券使用范围类型（0、全场,1、单商品，2、单品类）
   private String coupon_use_scope; // 优惠券使用范围
   private String coupon_price; // 优惠券面额（金额，折扣）
   private String coupon_use_condition_type; // 优惠券使用条件类型（0、不限制，1、限制）
   private String coupon_use_condition; // 优惠券使用条件（满n元可用）
   private String coupon_collection_condition_type; // 优惠券领取条件类型（0、游客，1、会员）
   private String coupon_collection_condition; // 优惠券领取条件（会员等级）
   private String coupon_count; // 优惠券数量
   private String coupon_limit_count_type; // 优惠券限制领取次数类型（0、不限制，1、限制次数）
   private String coupon_limit_day; // 优惠券限制天数领取
   private String coupon_limit_count; // 优惠券限制天数领取次数
   private String coupon_time_type; // 优惠券时间类型（0、固定时间，1、单日开始有效，2、次日开始有效）
   private Date coupon_starttime; // 优惠券固定有效开始时间
   private Date coupon_endtime; // 优惠券固定失效结束时间
   private String coupon_time_day; // 优惠券有效天数
   private String coupon_received_count; // 优惠券已经领取的次数
   private String coupon_introduce; // 优惠券使用说明
   private String coupon_status; // 优惠券状态（0、未发布，1、发布）

	public String getId() {
	    return this.id;
	}
	
	public void setId(String id) {
	    this.id = id;
	}
    public String getCoupon_name() {
	    return this.coupon_name;
	}
	
	public void setCoupon_name(String coupon_name) {
	    this.coupon_name = coupon_name;
	}
    public String getCoupon_type() {
	    return this.coupon_type;
	}
	
	public void setCoupon_type(String coupon_type) {
	    this.coupon_type = coupon_type;
	}
    public String getCoupon_scene_type() {
	    return this.coupon_scene_type;
	}
	
	public void setCoupon_scene_type(String coupon_scene_type) {
	    this.coupon_scene_type = coupon_scene_type;
	}
    public String getCoupon_function_type() {
	    return this.coupon_function_type;
	}
	
	public void setCoupon_function_type(String coupon_function_type) {
	    this.coupon_function_type = coupon_function_type;
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
    public String getCoupon_price() {
	    return this.coupon_price;
	}
	
	public void setCoupon_price(String coupon_price) {
	    this.coupon_price = coupon_price;
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
    public String getCoupon_collection_condition_type() {
	    return this.coupon_collection_condition_type;
	}
	
	public void setCoupon_collection_condition_type(String coupon_collection_condition_type) {
	    this.coupon_collection_condition_type = coupon_collection_condition_type;
	}
    public String getCoupon_collection_condition() {
	    return this.coupon_collection_condition;
	}
	
	public void setCoupon_collection_condition(String coupon_collection_condition) {
	    this.coupon_collection_condition = coupon_collection_condition;
	}
    public String getCoupon_count() {
	    return this.coupon_count;
	}
	
	public void setCoupon_count(String coupon_count) {
	    this.coupon_count = coupon_count;
	}
    public String getCoupon_limit_count_type() {
	    return this.coupon_limit_count_type;
	}
	
	public void setCoupon_limit_count_type(String coupon_limit_count_type) {
	    this.coupon_limit_count_type = coupon_limit_count_type;
	}
    public String getCoupon_limit_day() {
	    return this.coupon_limit_day;
	}
	
	public void setCoupon_limit_day(String coupon_limit_day) {
	    this.coupon_limit_day = coupon_limit_day;
	}
    public String getCoupon_limit_count() {
	    return this.coupon_limit_count;
	}
	
	public void setCoupon_limit_count(String coupon_limit_count) {
	    this.coupon_limit_count = coupon_limit_count;
	}
    public String getCoupon_time_type() {
	    return this.coupon_time_type;
	}
	
	public void setCoupon_time_type(String coupon_time_type) {
	    this.coupon_time_type = coupon_time_type;
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
    public String getCoupon_time_day() {
	    return this.coupon_time_day;
	}
	
	public void setCoupon_time_day(String coupon_time_day) {
	    this.coupon_time_day = coupon_time_day;
	}
	public String getCoupon_received_count() {
	    return this.coupon_received_count;
	}
	
	public void setCoupon_received_count(String coupon_received_count) {
	    this.coupon_received_count = coupon_received_count;
	}
    public String getCoupon_introduce() {
	    return this.coupon_introduce;
	}
	
	public void setCoupon_introduce(String coupon_introduce) {
	    this.coupon_introduce = coupon_introduce;
	}
    public String getCoupon_status() {
	    return this.coupon_status;
	}
	
	public void setCoupon_status(String coupon_status) {
	    this.coupon_status = coupon_status;
	}
	
}
