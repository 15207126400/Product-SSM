package com.yunwei.product.common.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.util.DateUtil;

/**
 * 积分商品
* @ClassName: YwPointsProduct
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年07月26日 下午15:50:53
*
 */
public class YwPointsProduct {

   private String points_pro_id; // 积分商品编号
   private String points_pro_title; // 积分商品名称
   private String points_pro_img; // 积分商品图片
   private String points_pro_type; // 积分商品类型(1,商品 ; 2,优惠券)
   private String points_pro_needs; // 兑换所需积分
   private String points_pro_stock; // 目前为虚拟库存(只针对商品类型)
   private String points_pro_sale_num; // 已兑换数量
   private String product_id; // 商品编号
   private String coupon_id; // 商品编号
   private Date points_pro_createtime; // 创建时间
   private Date points_pro_updatetime; // 更新时间
   private String showpage_status; 		// 是否首页展示(0,不展示 1,展示)
   private String status; // 积分商品状态(0,未启用 ; 1,启用)

    public String getPoints_pro_id() {
	    return this.points_pro_id;
	}
	
	public void setPoints_pro_id(String points_pro_id) {
	    this.points_pro_id = points_pro_id;
	}
    public String getPoints_pro_title() {
	    return this.points_pro_title;
	}
	
	public void setPoints_pro_title(String points_pro_title) {
	    this.points_pro_title = points_pro_title;
	}
    public String getPoints_pro_img() {
	    return this.points_pro_img;
	}
	
	public void setPoints_pro_img(String points_pro_img) {
	    this.points_pro_img = points_pro_img;
	}
    public String getPoints_pro_type() {
	    return this.points_pro_type;
	}
	
	public void setPoints_pro_type(String points_pro_type) {
	    this.points_pro_type = points_pro_type;
	}
    public String getPoints_pro_needs() {
	    return this.points_pro_needs;
	}
	
	public void setPoints_pro_needs(String points_pro_needs) {
	    this.points_pro_needs = points_pro_needs;
	}
    public String getPoints_pro_stock() {
	    return this.points_pro_stock;
	}
	
	public void setPoints_pro_stock(String points_pro_stock) {
	    this.points_pro_stock = points_pro_stock;
	}
    public String getPoints_pro_sale_num() {
	    return this.points_pro_sale_num;
	}
	
	public void setPoints_pro_sale_num(String points_pro_sale_num) {
	    this.points_pro_sale_num = points_pro_sale_num;
	}
	public String getCoupon_id() {
		return coupon_id;
	}

	public void setCoupon_id(String coupon_id) {
		this.coupon_id = coupon_id;
	}
    public String getProduct_id() {
	    return this.product_id;
	}
	
	public void setProduct_id(String product_id) {
	    this.product_id = product_id;
	}
	@JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
    public Date getPoints_pro_createtime() {
	    return this.points_pro_createtime;
	}
	
	public void setPoints_pro_createtime(Date points_pro_createtime) {
	    this.points_pro_createtime = points_pro_createtime;
	}
	@JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
    public Date getPoints_pro_updatetime() {
	    return this.points_pro_updatetime;
	}
	
	public void setPoints_pro_updatetime(Date points_pro_updatetime) {
	    this.points_pro_updatetime = points_pro_updatetime;
	}
    public String getStatus() {
	    return this.status;
	}
	
	public void setStatus(String status) {
	    this.status = status;
	}

	public String getShowpage_status() {
		return showpage_status;
	}

	public void setShowpage_status(String showpage_status) {
		this.showpage_status = showpage_status;
	}
	
}
