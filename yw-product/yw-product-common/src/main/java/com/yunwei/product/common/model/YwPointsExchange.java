package com.yunwei.product.common.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.util.DateUtil;

/**
 * 积分兑换
* @ClassName: YwPointsExchange
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年07月26日 下午17:08:01
*
 */
public class YwPointsExchange {

   private String exchange_id;  
   private String user_id; 					// 用户编号
   private String exchange_type; 			// 商品类型(1,商品类型 ; 2,优惠券类型 ; 3,其他类型)
   private String order_sn;					// 订单编号
   private String points; 					// 消费积分
   private String price; 					// 消费金额(没有可不填)
   private String product_id; 				//  商品编号
   private Date create_datetime; 			// 兑换时间
   private String status; 					// 兑换状态(0,兑换中 ; 1,兑换成功 ; 2,兑换失败)

    public String getExchange_id() {
	    return this.exchange_id;
	}
	
	public void setExchange_id(String exchange_id) {
	    this.exchange_id = exchange_id;
	}
    public String getUser_id() {
	    return this.user_id;
	}
	
	public void setUser_id(String user_id) {
	    this.user_id = user_id;
	}
    public String getExchange_type() {
	    return this.exchange_type;
	}
	public void setExchange_type(String exchange_type) {
	    this.exchange_type = exchange_type;
	}
    public String getOrder_sn() {
		return order_sn;
	}

	public void setOrder_sn(String order_sn) {
		this.order_sn = order_sn;
	}

	public String getPoints() {
	    return this.points;
	}
	
	public void setPoints(String points) {
	    this.points = points;
	}
    public String getPrice() {
	    return this.price;
	}
	
	public void setPrice(String price) {
	    this.price = price;
	}
    public String getProduct_id() {
	    return this.product_id;
	}
	
	public void setProduct_id(String product_id) {
	    this.product_id = product_id;
	}
	@JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
    public Date getCreate_datetime() {
	    return this.create_datetime;
	}
	public void setCreate_datetime(Date create_datetime) {
	    this.create_datetime = create_datetime;
	}
    public String getStatus() {
	    return this.status;
	}
	
	public void setStatus(String status) {
	    this.status = status;
	}
	
}
