package com.yunwei.product.common.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.util.DateUtil;

/**
 * 分销销售状况记录表
 * @author zhangz
 *
 */
public class YwDistributorSalestatus {
	  private String dis_st_id;			// 分销商销售记录编号
	  private String product_id;		// '商品id',
	  private String dis_id               ;// '分销人id',
	  private String dis_level            ;// '分销人等级',
	  private String dis_st_amont         ;// '分销商品销售数量',
	  private String dis_st_moeny         ;// '分销商品销售金额',
	  private Date dis_st_datetime      ;// '分销商品销售时间',
	  private String dis_st_buyer         ;// '分销商品购买人',
	  private String dis_st_palce         ;// '分销商品销售地点'
	  
	public String getDis_st_id() {
		return dis_st_id;
	}
	public void setDis_st_id(String dis_st_id) {
		this.dis_st_id = dis_st_id;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getDis_id() {
		return dis_id;
	}
	public void setDis_id(String dis_id) {
		this.dis_id = dis_id;
	}
	public String getDis_level() {
		return dis_level;
	}
	public void setDis_level(String dis_level) {
		this.dis_level = dis_level;
	}
	public String getDis_st_amont() {
		return dis_st_amont;
	}
	public void setDis_st_amont(String dis_st_amont) {
		this.dis_st_amont = dis_st_amont;
	}
	public String getDis_st_moeny() {
		return dis_st_moeny;
	}
	public void setDis_st_moeny(String dis_st_moeny) {
		this.dis_st_moeny = dis_st_moeny;
	}
	@JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
	public Date getDis_st_datetime() {
		return dis_st_datetime;
	}
	public void setDis_st_datetime(Date dis_st_datetime) {
		this.dis_st_datetime = dis_st_datetime;
	}
	public String getDis_st_buyer() {
		return dis_st_buyer;
	}
	public void setDis_st_buyer(String dis_st_buyer) {
		this.dis_st_buyer = dis_st_buyer;
	}
	public String getDis_st_palce() {
		return dis_st_palce;
	}
	public void setDis_st_palce(String dis_st_palce) {
		this.dis_st_palce = dis_st_palce;
	}
	  
	  

}
