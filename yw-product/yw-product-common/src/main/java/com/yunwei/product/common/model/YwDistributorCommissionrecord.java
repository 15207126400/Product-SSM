package com.yunwei.product.common.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.util.DateUtil;

/**
 * 分销商佣金记录表
 * @author zjh
 *
 */
public class YwDistributorCommissionrecord {

	private String dis_com_type;//佣金类型
	private String dis_com_id           ; // '分销商佣金记录编号',
	private String user_id               ; // '收益者编号',
	private String create_user_id		;	//佣金来源(创建人编号)
	private String order_id             ; // '订单编号',
	private String dis_com_money        ; // '分销商佣金余额',
	private Date dis_com_createtime   ; // '分销商佣金余额创建时间',
	private Date dis_com_updatetime   ; // '分销商佣金更新时间',
	private String dis_com_status       ; // '分销商佣金状态 '
	private String dis_com_remark;//佣金备注
	public String getDis_com_id() {
		return dis_com_id;
	}
	public void setDis_com_id(String dis_com_id) {
		this.dis_com_id = dis_com_id;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getDis_com_money() {
		return dis_com_money;
	}
	public void setDis_com_money(String dis_com_money) {
		this.dis_com_money = dis_com_money;
	}
	
	@JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
	public Date getDis_com_createtime() {
		return dis_com_createtime;
	}
	public void setDis_com_createtime(Date dis_com_createtime) {
		this.dis_com_createtime = dis_com_createtime;
	}
	@JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
	public Date getDis_com_updatetime() {
		return dis_com_updatetime;
	}
	public void setDis_com_updatetime(Date dis_com_updatetime) {
		this.dis_com_updatetime = dis_com_updatetime;
	}
	public String getDis_com_status() {
		return dis_com_status;
	}
	public void setDis_com_status(String dis_com_status) {
		this.dis_com_status = dis_com_status;
	}
	public String getDis_com_type() {
		return dis_com_type;
	}
	public void setDis_com_type(String dis_com_type) {
		this.dis_com_type = dis_com_type;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getCreate_user_id() {
		return create_user_id;
	}
	public void setCreate_user_id(String create_user_id) {
		this.create_user_id = create_user_id;
	}
	public String getDis_com_remark() {
		return dis_com_remark;
	}
	public void setDis_com_remark(String dis_com_remark) {
		this.dis_com_remark = dis_com_remark;
	}
	   
	   
}
