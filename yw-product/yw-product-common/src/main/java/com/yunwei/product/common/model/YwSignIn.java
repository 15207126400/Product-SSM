package com.yunwei.product.common.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.util.DateUtil;

/**
 * 签到
* @ClassName: YwSignIn
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年07月25日 下午16:32:34
*
 */
public class YwSignIn {

   private String signIn_id; // 签到编号
   private String user_id; // 用户编号
   private Date signIn_last_date; // 上次签到日期
   private String signIn_continue_day; // 连续签到天数
   private String signIn_status; // 签到状态(0,未签 ; 1,已签) 
   private Date signIn_updatetime; // 修改时间

    public String getSignIn_id() {
	    return this.signIn_id;
	}
	
	public void setSignIn_id(String signIn_id) {
	    this.signIn_id = signIn_id;
	}
    public String getUser_id() {
	    return this.user_id;
	}
	
	public void setUser_id(String user_id) {
	    this.user_id = user_id;
	}
	@JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_FORMAT)
    public Date getSignIn_last_date() {
	    return this.signIn_last_date;
	}
	
	public void setSignIn_last_date(Date signIn_last_date) {
	    this.signIn_last_date = signIn_last_date;
	}
    public String getSignIn_continue_day() {
	    return this.signIn_continue_day;
	}
	
	public void setSignIn_continue_day(String signIn_continue_day) {
	    this.signIn_continue_day = signIn_continue_day;
	}
    public String getSignIn_status() {
	    return this.signIn_status;
	}
	
	public void setSignIn_status(String signIn_status) {
	    this.signIn_status = signIn_status;
	}
	@JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
    public Date getSignIn_updatetime() {
	    return this.signIn_updatetime;
	}
	
	public void setSignIn_updatetime(Date signIn_updatetime) {
	    this.signIn_updatetime = signIn_updatetime;
	}
	
}
