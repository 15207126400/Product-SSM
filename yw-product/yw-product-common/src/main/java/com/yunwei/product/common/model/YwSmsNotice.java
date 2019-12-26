package com.yunwei.product.common.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.util.DateUtil;

/**
 * 云维短信通知信息表
* @ClassName: YwSmsNotice 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年5月22日 下午6:26:31 
*
 */
public class YwSmsNotice {

	private String sms_id; // 短信信息id
	private Date sms_createtime;// 短信信息创建时间
	private Date sms_updatetime;// 短信信息更新时间
	private String sms_type;// 短信通知类型
	private String sms_status;// 短信通知状态（0：待发送，1：发送成功，2：发送失败）
	private String user_id;// 用户openid
	private String sms_mobile;//短信通知手机号码
	private String sms_content;// 短信通知内容
	private String sms_template_id;// 短信模板id
	private String sms_remark;// 短信备注
	private String sms_error_no;// 短信错误编号
	private String sms_error_info;// 短信错误信息
	private String sms_isfee;// 短信是否已经收费
	public String getSms_id() {
		return sms_id;
	}
	public void setSms_id(String sms_id) {
		this.sms_id = sms_id;
	}
	@JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
	public Date getSms_createtime() {
		return sms_createtime;
	}
	public void setSms_createtime(Date sms_createtime) {
		this.sms_createtime = sms_createtime;
	}
	@JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
	public Date getSms_updatetime() {
		return sms_updatetime;
	}
	public void setSms_updatetime(Date sms_updatetime) {
		this.sms_updatetime = sms_updatetime;
	}
	public String getSms_type() {
		return sms_type;
	}
	public void setSms_type(String sms_type) {
		this.sms_type = sms_type;
	}
	public String getSms_status() {
		return sms_status;
	}
	public void setSms_status(String sms_status) {
		this.sms_status = sms_status;
	}
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getSms_mobile() {
		return sms_mobile;
	}
	public void setSms_mobile(String sms_mobile) {
		this.sms_mobile = sms_mobile;
	}
	public String getSms_content() {
		return sms_content;
	}
	public void setSms_content(String sms_content) {
		this.sms_content = sms_content;
	}
	public String getSms_template_id() {
		return sms_template_id;
	}
	public void setSms_template_id(String sms_template_id) {
		this.sms_template_id = sms_template_id;
	}
	public String getSms_error_no() {
		return sms_error_no;
	}
	public void setSms_error_no(String sms_error_no) {
		this.sms_error_no = sms_error_no;
	}
	public String getSms_error_info() {
		return sms_error_info;
	}
	public void setSms_error_info(String sms_error_info) {
		this.sms_error_info = sms_error_info;
	}
	public String getSms_isfee() {
		return sms_isfee;
	}
	public void setSms_isfee(String sms_isfee) {
		this.sms_isfee = sms_isfee;
	}
	public String getSms_remark() {
		return sms_remark;
	}
	public void setSms_remark(String sms_remark) {
		this.sms_remark = sms_remark;
	}
	
	
}
