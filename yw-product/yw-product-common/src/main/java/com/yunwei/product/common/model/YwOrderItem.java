package com.yunwei.product.common.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;

/**
 * 
* @ClassName: YwOrderItem 
* @Description: TODO(TODO) 订单项信息
* @author zhangjh
* @date 2018年3月30日 上午11:18:49 
*
 */

public class YwOrderItem {
   private String order_sn;
   private String product_id;
   private String order_ite_count;
   private String order_ite_price;
   private String order_ite_img;
   private String order_ite_name;
   private String order_ite_sku;
public String getOrder_sn() {
	return order_sn;
}
public void setOrder_sn(String order_sn) {
	this.order_sn = order_sn;
}
public String getProduct_id() {
	return product_id;
}
public void setProduct_id(String product_id) {
	this.product_id = product_id;
}
public String getOrder_ite_count() {
	return order_ite_count;
}
public void setOrder_ite_count(String order_ite_count) {
	this.order_ite_count = order_ite_count;
}
public String getOrder_ite_price() {
	return order_ite_price;
}
public void setOrder_ite_price(String order_ite_price) {
	this.order_ite_price = order_ite_price;
}
public String getOrder_ite_img() {
	return order_ite_img;
}
public void setOrder_ite_img(String order_ite_img) {
	this.order_ite_img = order_ite_img;
}
public String getOrder_ite_name() {
	return order_ite_name;
}
public void setOrder_ite_name(String order_ite_name) {
	this.order_ite_name = order_ite_name;
}
public String getOrder_ite_sku() {
	return order_ite_sku;
}
public void setOrder_ite_sku(String order_ite_sku) {
	this.order_ite_sku = order_ite_sku;
}
   
}
