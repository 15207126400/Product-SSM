package com.yunwei.product.common.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.util.DateUtil;

/**
 * 订单成交量实体类
* @ClassName: OrderCountProcedure 
* @Description: TODO(TODO) 
* @author 晏飞
* @date 2018年11月22日 下午4:44:51 
*
 */
public class OrderCountProcedure {
	private Date order_paytime;
	private int orderCount;
	private int offlineCount;
	
	@JsonFormat(pattern = DateUtil.DATE_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_FORMAT)
	public Date getOrder_paytime() {
		return order_paytime;
	}
	public void setOrder_paytime(Date order_paytime) {
		this.order_paytime = order_paytime;
	}
	public int getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}
	public int getOfflineCount() {
		return offlineCount;
	}
	public void setOfflineCount(int offlineCount) {
		this.offlineCount = offlineCount;
	}
	
	
}
