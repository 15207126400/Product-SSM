package com.yunwei.product.common.model;

import java.util.Date;

/**
 * 钱包表
 * @author zhangjh
 *
 */
public class YwWallet {

	   private String wa_id            ; // '钱包编号',
	   private String wa_type ;// 钱包类型
	   private String user_id               ; // '用户编号',
	   private String wa_moeny         ; // '钱包余额',
	   private Date wa_createtime    ; // '包创建时间',
	   private Date wa_updatetime    ; // '钱包更新时间',
	public String getWa_id() {
		return wa_id;
	}
	public void setWa_id(String wa_id) {
		this.wa_id = wa_id;
	}
	public String getWa_type() {
		return wa_type;
	}
	public void setWa_type(String wa_type) {
		this.wa_type = wa_type;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getWa_moeny() {
		return wa_moeny;
	}
	public void setWa_moeny(String wa_moeny) {
		this.wa_moeny = wa_moeny;
	}
	public Date getWa_createtime() {
		return wa_createtime;
	}
	public void setWa_createtime(Date wa_createtime) {
		this.wa_createtime = wa_createtime;
	}
	public Date getWa_updatetime() {
		return wa_updatetime;
	}
	public void setWa_updatetime(Date wa_updatetime) {
		this.wa_updatetime = wa_updatetime;
	}

	   
}
