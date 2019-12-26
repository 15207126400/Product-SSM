package com.yunwei.product.common.model;
/**
 * 订单交易金额实体类
* @ClassName: OrderPriceProcedure 
* @Description: TODO(TODO) 
* @author 晏飞
* @date 2018年11月21日 上午10:11:37 
*
 */
public class OrderPriceProcedure {
	private double order_price;//线上交易总金额
	private double today_order_price;//今日线上交易总金额
	private double offline_price;//线下交易总金额
	private double today_offline_price;//今日线下交易总金额
	
	
	public double getOrder_price() {
		return order_price;
	}
	public void setOrder_price(double order_price) {
		this.order_price = order_price;
	}
	public double getOffline_price() {
		return offline_price;
	}
	public void setOffline_price(double offline_price) {
		this.offline_price = offline_price;
	}
	public double getToday_order_price() {
		return today_order_price;
	}
	public void setToday_order_price(double today_order_price) {
		this.today_order_price = today_order_price;
	}
	public double getToday_offline_price() {
		return today_offline_price;
	}
	public void setToday_offline_price(double today_offline_price) {
		this.today_offline_price = today_offline_price;
	}
	
	
}
