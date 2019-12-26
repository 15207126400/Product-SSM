package com.yunwei.common.user;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能说明: 系统用户<br>
 * 系统版本: v1.0<br>
 * 开发人员: @author zhangjh<br>
 * 开发时间: 2018年3月8日<br>
 */
public class SysUser implements Serializable {

	public static final String YW_USER_SESSION = "_yw_user";// 云维系统用户

	private String user_type;// 用户类型(1、系统管理员，2、操作员)
	private String user_id;// 用户编号
	private String user_code;// 
	private String user_name;// 用户登录名称
	private String user_avg;// 用户头像
	private String mobile;
	private String phone_tel;// 用户登录手机号
	private String id_no;// 登录证件号
	private String password;// 登录密码
	private String branch_id;// 机构编号(超市或者其他机构编号)
	private String order_count;// 订单消息数量
	private String css_name;// css名称(用户个性化需求)[默认为main]
	private String sys_admin_id;// 系统管理员编号[当前客户为具体操作员时保留的系统管理员编号]

	private String op_station;
	private Map<String, String> terminalInfo;

	/** 上次登录的时间 */
	private Date last_login_time;
	/** 最后一个写cookie的时间，用来判断cookie是否超过24小时失效时间 */
	private Date update_cookie_time;

	private String sessionId;
	private Map<String, String> user_info;// 用户辅助信息

	private Map<String, String> bind_accounts;// 绑定账户

	private String last_login_ip;// 上次登录的ip地址

	private String subsys_no;// 子系统编号
	private String device_flag;
	private String app_id;

	public String getApp_id() {
		return app_id;
	}

	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}

	public String getDevice_flag() {
		return device_flag;
	}

	public void setDevice_flag(String device_flag) {
		this.device_flag = device_flag;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_code() {
		return user_code;
	}

	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOp_station() {
		return op_station;
	}

	public void setOp_station(String op_station) {
		this.op_station = op_station;
	}

	public Date getLast_login_time() {
		return last_login_time;
	}

	public void setLast_login_time(Date last_login_time) {
		this.last_login_time = last_login_time;
	}

	public Map<String, String> getUser_info() {
		if (user_info == null) {
			user_info = new HashMap<String, String>();
		}
		return user_info;
	}

	public void setUser_info(Map<String, String> user_info) {
		this.user_info = user_info;
	}

	public Map<String, String> getBind_accounts() {
		if (bind_accounts == null) {
			bind_accounts = new HashMap<String, String>();
		}
		return bind_accounts;
	}

	public void setBind_accounts(Map<String, String> bind_accounts) {
		this.bind_accounts = bind_accounts;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * @return the last_login_ip
	 */
	public String getLast_login_ip() {
		return last_login_ip;
	}

	/**
	 * @param last_login_ip the last_login_ip to set
	 */
	public void setLast_login_ip(String last_login_ip) {
		this.last_login_ip = last_login_ip;
	}

	/**
	 * @return the update_cookie_time
	 */
	public Date getUpdate_cookie_time() {
		return update_cookie_time;
	}

	/**
	 * @param update_cookie_time the update_cookie_time to set
	 */
	public void setUpdate_cookie_time(Date update_cookie_time) {
		this.update_cookie_time = update_cookie_time;
	}

	public String getSubsys_no() {
		return subsys_no;
	}

	public void setSubsys_no(String subsys_no) {
		this.subsys_no = subsys_no;
	}

	
	public Map<String, String> getTerminalInfo() {
		return terminalInfo;
	}

	
	public void setTerminalInfo(Map<String, String> terminalInfo) {
		this.terminalInfo = terminalInfo;
	}

	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	public String getPhone_tel() {
		return phone_tel;
	}

	public void setPhone_tel(String phone_tel) {
		this.phone_tel = phone_tel;
	}

	public String getId_no() {
		return id_no;
	}

	public void setId_no(String id_no) {
		this.id_no = id_no;
	}

	public static String getYwUserSession() {
		return YW_USER_SESSION;
	}
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(String branch_id) {
		this.branch_id = branch_id;
	}

	public String getOrder_count() {
		return order_count;
	}

	public void setOrder_count(String order_count) {
		this.order_count = order_count;
	}

	public String getCss_name() {
		return css_name;
	}

	public void setCss_name(String css_name) {
		this.css_name = css_name;
	}

	public String getSys_admin_id() {
		return sys_admin_id;
	}

	public void setSys_admin_id(String sys_admin_id) {
		this.sys_admin_id = sys_admin_id;
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
