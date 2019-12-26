package com.yunwei.product.common.infobase.model.dto;

import com.yunwei.product.common.model.YwPointsExchange;
/**
 * 
* @ClassName: YwPointsExchangeByOrderItemsDto 
* @Description: TODO(兑换记录 当兑换类型为商品时 , 兑换记录表与订单项表的联合查询DTO) 
* @author 晏飞
* @date 2018年8月14日 下午4:12:56 
*
 */
public class YwPointsExchangeByOrderItemsDto extends YwPointsExchange{
	private String order_ite_count;
	private String order_ite_price;
	private String order_ite_img;
	private String order_ite_name;
	private String order_ite_sku;
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
