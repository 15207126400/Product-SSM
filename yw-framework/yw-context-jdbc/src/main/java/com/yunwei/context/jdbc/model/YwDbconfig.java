package com.yunwei.context.jdbc.model;

/**
 * 云维数据库配置模型
 * @author zhangjh
 *
 */
public class YwDbconfig {
	private String db_id; // 数据库编号
	private String db_name;// 数据库名称
	private String db_type;// 数据库类型
	private String db_driver;// 数据库驱动类
	private String db_protocol;// 数据库协议
	private String db_port;// 数据库端口号
	private String db_username;// 数据库用户登录名称
	private String db_password;// 数据库用户登录密码
	private String user_id;// 用户编号
	private String wx_appid;// 小程序编号
	private int db_status;// 数据库状态
	private int db_function_type;// 数据库作用类型
	private String db_sqlimport_versiontype;// sql数据库导入版本类型
	private String db_function_remark;// 数据库作用类型备注
	public String getDb_id() {
		return db_id;
	}
	public void setDb_id(String db_id) {
		this.db_id = db_id;
	}
	public String getDb_name() {
		return db_name;
	}
	public void setDb_name(String db_name) {
		this.db_name = db_name;
	}
	public String getDb_type() {
		return db_type;
	}
	public void setDb_type(String db_type) {
		this.db_type = db_type;
	}
	public String getDb_driver() {
		return db_driver;
	}
	public void setDb_driver(String db_driver) {
		this.db_driver = db_driver;
	}
	public String getDb_protocol() {
		return db_protocol;
	}
	public void setDb_protocol(String db_protocol) {
		this.db_protocol = db_protocol;
	}
	public String getDb_port() {
		return db_port;
	}
	public void setDb_port(String db_port) {
		this.db_port = db_port;
	}
	public String getDb_username() {
		return db_username;
	}
	public void setDb_username(String db_username) {
		this.db_username = db_username;
	}
	public String getDb_password() {
		return db_password;
	}
	public void setDb_password(String db_password) {
		this.db_password = db_password;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getWx_appid() {
		return wx_appid;
	}
	public void setWx_appid(String wx_appid) {
		this.wx_appid = wx_appid;
	}
	public int getDb_status() {
		return db_status;
	}
	public void setDb_status(int db_status) {
		this.db_status = db_status;
	}
	public int getDb_function_type() {
		return db_function_type;
	}
	public void setDb_function_type(int db_function_type) {
		this.db_function_type = db_function_type;
	}
	public String getDb_function_remark() {
		return db_function_remark;
	}
	public void setDb_function_remark(String db_function_remark) {
		this.db_function_remark = db_function_remark;
	}
	public String getDb_sqlimport_versiontype() {
		return db_sqlimport_versiontype;
	}
	public void setDb_sqlimport_versiontype(String db_sqlimport_versiontype) {
		this.db_sqlimport_versiontype = db_sqlimport_versiontype;
	}
	
    
}
