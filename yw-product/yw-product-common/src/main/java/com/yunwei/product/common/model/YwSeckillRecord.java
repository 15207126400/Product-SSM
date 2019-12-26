package com.yunwei.product.common.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.annotation.Base64Decode;
import com.yunwei.common.util.DateUtil;

/**
 * 
* @ClassName: YwSeckillRecord 
* @Description: 秒杀记录
* @author 晏飞
* @date 2018年7月2日 下午2:28:46 
*
 */
public class YwSeckillRecord {
	private String record_id;				// 秒杀记录编号
	private Date record_createtime;			// 秒杀记录创建时间
	private Date record_updatetime;			// 秒杀记录更新时间
	private String record_totalprice;		// 秒杀记录总价
	private String seckill_id;				// 秒杀活动编号
	private String seckill_num;				// 秒杀数量
	private String seckill_price;			// 秒杀价格
	private String product_price;			// 商品原价
	private String order_sn;				// 订单编号
	private String user_id;					// 用户编号
	@Base64Decode
	private String user_nickname;			// 秒杀用户昵称
	private String user_headurl;			// 秒杀用户头像
	private String user_palce;				// 用户秒杀地点
	private String record_status;			// 秒杀记录状态
	
	public String getRecord_id() {
		return record_id;
	}
	public void setRecord_id(String record_id) {
		this.record_id = record_id;
	}
	@JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
	public Date getRecord_createtime() {
		return record_createtime;
	}
	public void setRecord_createtime(Date record_createtime) {
		this.record_createtime = record_createtime;
	}
	@JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
	public Date getRecord_updatetime() {
		return record_updatetime;
	}
	public void setRecord_updatetime(Date record_updatetime) {
		this.record_updatetime = record_updatetime;
	}
	public String getRecord_totalprice() {
		return record_totalprice;
	}
	public void setRecord_totalprice(String record_totalprice) {
		this.record_totalprice = record_totalprice;
	}
	public String getSeckill_id() {
		return seckill_id;
	}
	public void setSeckill_id(String seckill_id) {
		this.seckill_id = seckill_id;
	}
	public String getSeckill_num() {
		return seckill_num;
	}
	public void setSeckill_num(String seckill_num) {
		this.seckill_num = seckill_num;
	}
	public String getSeckill_price() {
		return seckill_price;
	}
	public void setSeckill_price(String seckill_price) {
		this.seckill_price = seckill_price;
	}
	public String getProduct_price() {
		return product_price;
	}
	public void setProduct_price(String product_price) {
		this.product_price = product_price;
	}
	public String getOrder_sn() {
		return order_sn;
	}
	public void setOrder_sn(String order_sn) {
		this.order_sn = order_sn;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_nickname() {
		return user_nickname;
	}
	public void setUser_nickname(String user_nickname) {
		this.user_nickname = user_nickname;
	}
	public String getUser_headurl() {
		return user_headurl;
	}
	public void setUser_headurl(String user_headurl) {
		this.user_headurl = user_headurl;
	}
	public String getUser_palce() {
		return user_palce;
	}
	public void setUser_palce(String user_palce) {
		this.user_palce = user_palce;
	}
	public String getRecord_status() {
		return record_status;
	}
	public void setRecord_status(String record_status) {
		this.record_status = record_status;
	}
	
}
