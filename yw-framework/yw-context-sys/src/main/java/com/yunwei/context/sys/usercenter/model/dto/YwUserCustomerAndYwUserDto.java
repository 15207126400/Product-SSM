package com.yunwei.context.sys.usercenter.model.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.util.DateUtil;
import com.yunwei.context.sys.usercenter.model.YwUserCustomer;

public class YwUserCustomerAndYwUserDto extends YwUserCustomer{
	
	private Date pay_datetime; // 付费时间
	private Date expire_datetime; // 到期时间
	
	@JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
	public Date getPay_datetime() {
		return pay_datetime;
	}
	public void setPay_datetime(Date pay_datetime) {
		this.pay_datetime = pay_datetime;
	}
	@JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
	public Date getExpire_datetime() {
		return expire_datetime;
	}
	public void setExpire_datetime(Date expire_datetime) {
		this.expire_datetime = expire_datetime;
	}
	

}
