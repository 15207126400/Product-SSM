package com.yunwei.product.common.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.util.DateUtil;

/**
 * 分销商提现记录
 * @author zhangjh
 *
 */
public class YwDistributorWithdrawalrecord {
       
	   private String dis_wid_type;// 提现记录类型
	   private String dis_wid_id           ; // '分销商提现记录编号',
	   private String user_id               ; // '分销商编号',
	   private String dis_wid_money        ; // '分销商提现金额',
	   private Date dis_wid_createtime   ; // '分销商提现时间',
	   private Date dis_wid_updatetime   ; // '分销商提现更新时间',
	   private String dis_wid_status       ; // '分销商提现状态'
	   private String dis_wid_remark;//提现记录备注
	public String getDis_wid_id() {
		return dis_wid_id;
	}
	public void setDis_wid_id(String dis_wid_id) {
		this.dis_wid_id = dis_wid_id;
	}
	public String getDis_wid_money() {
		return dis_wid_money;
	}
	public void setDis_wid_money(String dis_wid_money) {
		this.dis_wid_money = dis_wid_money;
	}
	@JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
	public Date getDis_wid_createtime() {
		return dis_wid_createtime;
	}
	public void setDis_wid_createtime(Date dis_wid_createtime) {
		this.dis_wid_createtime = dis_wid_createtime;
	}
	@JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
	public Date getDis_wid_updatetime() {
		return dis_wid_updatetime;
	}
	public void setDis_wid_updatetime(Date dis_wid_updatetime) {
		this.dis_wid_updatetime = dis_wid_updatetime;
	}
	public String getDis_wid_status() {
		return dis_wid_status;
	}
	public void setDis_wid_status(String dis_wid_status) {
		this.dis_wid_status = dis_wid_status;
	}
	public String getDis_wid_type() {
		return dis_wid_type;
	}
	public void setDis_wid_type(String dis_wid_type) {
		this.dis_wid_type = dis_wid_type;
	}
	public String getDis_wid_remark() {
		return dis_wid_remark;
	}
	public void setDis_wid_remark(String dis_wid_remark) {
		this.dis_wid_remark = dis_wid_remark;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	   
}
