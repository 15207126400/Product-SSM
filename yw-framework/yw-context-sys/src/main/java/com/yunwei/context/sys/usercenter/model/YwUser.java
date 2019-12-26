package com.yunwei.context.sys.usercenter.model;

import java.math.BigDecimal;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.util.DateUtil;

/**
 * 用户基本信息
* @ClassName: YwUser
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年09月18日 上午10:19:13
*
 */
public class YwUser {

   private String user_id; // 用户编号
   private String user_type; // 用户类型
   private String user_name; // 登录用户名
   private String user_avg; // 用户头像
   private String phone_tel; // 登录手机号
   private String id_no; // 登录证件号
   private String password; // 登录密码
   private String secret_key; // 秘钥串
   private String bind_mac; // 绑定地址
   private String bind_ip; // 绑定IP
   private String user_status; // 用户状态
   private String login_errors; // 连续登入失败次数
   private String password_question; // 密码提示问题（忘记密码用）
   private String password_answer; // 密码提示答案（忘记密码用）
   private String user_level; // 用户级别
   private Date create_datetime; // 创建时间
   private Date update_datetime; // 更新时间
   private Date pay_datetime; // 付费时间
   private Date expire_datetime; // 到期时间
   private String css_name;// css名称(主题更换，默认main)

    public String getUser_id() {
	    return this.user_id;
	}
	
	public void setUser_id(String user_id) {
	    this.user_id = user_id;
	}
    public String getUser_type() {
	    return this.user_type;
	}
	
	public void setUser_type(String user_type) {
	    this.user_type = user_type;
	}
    public String getUser_name() {
	    return this.user_name;
	}
	
	public void setUser_name(String user_name) {
	    this.user_name = user_name;
	}
    public String getPhone_tel() {
	    return this.phone_tel;
	}
	
	public void setPhone_tel(String phone_tel) {
	    this.phone_tel = phone_tel;
	}
    public String getId_no() {
	    return this.id_no;
	}
	
	public void setId_no(String id_no) {
	    this.id_no = id_no;
	}
    public String getPassword() {
	    return this.password;
	}
	
	public void setPassword(String password) {
	    this.password = password;
	}
    public String getSecret_key() {
	    return this.secret_key;
	}
	
	public void setSecret_key(String secret_key) {
	    this.secret_key = secret_key;
	}
    public String getBind_mac() {
	    return this.bind_mac;
	}
	
	public void setBind_mac(String bind_mac) {
	    this.bind_mac = bind_mac;
	}
    public String getBind_ip() {
	    return this.bind_ip;
	}
	
	public void setBind_ip(String bind_ip) {
	    this.bind_ip = bind_ip;
	}
    public String getUser_status() {
	    return this.user_status;
	}
	
	public void setUser_status(String user_status) {
	    this.user_status = user_status;
	}
    public String getLogin_errors() {
	    return this.login_errors;
	}
	
	public void setLogin_errors(String login_errors) {
	    this.login_errors = login_errors;
	}
    public String getPassword_question() {
	    return this.password_question;
	}
	
	public void setPassword_question(String password_question) {
	    this.password_question = password_question;
	}
    public String getPassword_answer() {
	    return this.password_answer;
	}
	
	public void setPassword_answer(String password_answer) {
	    this.password_answer = password_answer;
	}
    public String getUser_level() {
	    return this.user_level;
	}
	
	public void setUser_level(String user_level) {
	    this.user_level = user_level;
	}
    @JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
    public Date getCreate_datetime() {
	    return this.create_datetime;
	}
	
	public void setCreate_datetime(Date create_datetime) {
	    this.create_datetime = create_datetime;
	}
    @JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
    public Date getUpdate_datetime() {
	    return this.update_datetime;
	}
	
	public void setUpdate_datetime(Date update_datetime) {
	    this.update_datetime = update_datetime;
	}
    @JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
    public Date getPay_datetime() {
	    return this.pay_datetime;
	}
	
	public void setPay_datetime(Date pay_datetime) {
	    this.pay_datetime = pay_datetime;
	}
    @JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
    public Date getExpire_datetime() {
	    return this.expire_datetime;
	}
	
	public void setExpire_datetime(Date expire_datetime) {
	    this.expire_datetime = expire_datetime;
	}

	public String getCss_name() {
		return css_name != null ? css_name:"main";
	}

	public void setCss_name(String css_name) {
		this.css_name = css_name;
	}

	/** 
	* @return user_avg 
	*/
	public String getUser_avg() {
		return user_avg;
	}

	/** 
	* @param user_avg 要设置的 user_avg 
	*/
	public void setUser_avg(String user_avg) {
		this.user_avg = user_avg;
	}
	
}
