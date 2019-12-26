package com.yunwei.product.common.infobase.model.dto;

import com.yunwei.product.common.model.YwPointsExchange;

public class YwPointsExchangeByCouponDto extends YwPointsExchange{
	private String coupon_name;
	private String coupon_scene_type;
	private String coupon_function_type;
	private String coupon_use_scope_type;
	private String coupon_use_scope;
	private String coupon_price;
	public String getCoupon_name() {
		return coupon_name;
	}
	public void setCoupon_name(String coupon_name) {
		this.coupon_name = coupon_name;
	}
	public String getCoupon_scene_type() {
		return coupon_scene_type;
	}
	public void setCoupon_scene_type(String coupon_scene_type) {
		this.coupon_scene_type = coupon_scene_type;
	}
	public String getCoupon_function_type() {
		return coupon_function_type;
	}
	public void setCoupon_function_type(String coupon_function_type) {
		this.coupon_function_type = coupon_function_type;
	}
	public String getCoupon_use_scope_type() {
		return coupon_use_scope_type;
	}
	public void setCoupon_use_scope_type(String coupon_use_scope_type) {
		this.coupon_use_scope_type = coupon_use_scope_type;
	}
	public String getCoupon_use_scope() {
		return coupon_use_scope;
	}
	public void setCoupon_use_scope(String coupon_use_scope) {
		this.coupon_use_scope = coupon_use_scope;
	}
	public String getCoupon_price() {
		return coupon_price;
	}
	public void setCoupon_price(String coupon_price) {
		this.coupon_price = coupon_price;
	}
	

}
