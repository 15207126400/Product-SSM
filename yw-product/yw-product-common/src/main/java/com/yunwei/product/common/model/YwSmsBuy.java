package com.yunwei.product.common.model;


import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.util.DateUtil;

/**
 * 短信购买信息表
* @ClassName: YwSmsBuy 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年5月23日 上午11:38:06 
*
 */
public class YwSmsBuy {

	private String buy_id;// 短信购买编号
	private String user_id;// 短信购买编号
	private String combo_id;// 短信购买编号
	private String buy_number;// 短信购买编号
	private String buy_usenumber;// 短信购买编号
	private String buy_price;// 短信购买编号
	private Date buy_createtime;// 短信购买编号
	private Date buy_updatetime;// 短信购买编号
	private String order_sn;// 短信购买编号
	private String buy_status;// 短信购买编号
	private String buy_remark;// 短信购买编号
	
	public String getBuy_id() {
		return buy_id;
	}
	public void setBuy_id(String buy_id) {
		this.buy_id = buy_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getCombo_id() {
		return combo_id;
	}
	public void setCombo_id(String combo_id) {
		this.combo_id = combo_id;
	}
	public String getBuy_number() {
		return buy_number;
	}
	public void setBuy_number(String buy_number) {
		this.buy_number = buy_number;
	}
	public String getBuy_usenumber() {
		return buy_usenumber;
	}
	public void setBuy_usenumber(String buy_usenumber) {
		this.buy_usenumber = buy_usenumber;
	}
	public String getBuy_price() {
		return buy_price;
	}
	public void setBuy_price(String buy_price) {
		this.buy_price = buy_price;
	}
	@JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
	public Date getBuy_createtime() {
		return buy_createtime;
	}
	public void setBuy_createtime(Date buy_createtime) {
		this.buy_createtime = buy_createtime;
	}
	@JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
	public Date getBuy_updatetime() {
		return buy_updatetime;
	}
	public void setBuy_updatetime(Date buy_updatetime) {
		this.buy_updatetime = buy_updatetime;
	}
	public String getOrder_sn() {
		return order_sn;
	}
	public void setOrder_sn(String order_sn) {
		this.order_sn = order_sn;
	}
	public String getBuy_status() {
		return buy_status;
	}
	public void setBuy_status(String buy_status) {
		this.buy_status = buy_status;
	}
	public String getBuy_remark() {
		return buy_remark;
	}
	public void setBuy_remark(String buy_remark) {
		this.buy_remark = buy_remark;
	}
	
}
