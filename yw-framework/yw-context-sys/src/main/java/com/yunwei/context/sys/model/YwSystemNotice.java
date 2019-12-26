package com.yunwei.context.sys.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.util.DateUtil;

/**
 * 
* @ClassName: YwSystemNotice 
* @Description: 系统公告 
* @author 晏飞
* @date 2018年5月31日 下午4:24:05 
*
 */
public class YwSystemNotice {
	private String notice_id;					//公告编号
	private String notice_type;				//公告类型
	private String notice_content;				//公告内容
	private Date notice_create_time;			//公告发布时间
	private String user_id;					//公告指定接收对象(用户编号)
	private String notice_status;				//公告状态(0,待发布 1,已发布)
	public String getNotice_id() {
		return notice_id;
	}
	public void setNotice_id(String notice_id) {
		this.notice_id = notice_id;
	}
	public String getNotice_type() {
		return notice_type;
	}
	public void setNotice_type(String notice_type) {
		this.notice_type = notice_type;
	}
	public String getNotice_content() {
		return notice_content;
	}
	public void setNotice_content(String notice_content) {
		this.notice_content = notice_content;
	}
	@JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
	public Date getNotice_create_time() {
		return notice_create_time;
	}
	public void setNotice_create_time(Date notice_create_time) {
		this.notice_create_time = notice_create_time;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getNotice_status() {
		return notice_status;
	}
	public void setNotice_status(String notice_status) {
		this.notice_status = notice_status;
	}
	
	
}
