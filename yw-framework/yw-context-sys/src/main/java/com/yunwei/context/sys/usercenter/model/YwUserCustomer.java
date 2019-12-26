package com.yunwei.context.sys.usercenter.model;

import java.math.BigDecimal;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.util.DateUtil;

/**
 * 客户信息
* @ClassName: YwUserCustomer
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年09月18日 上午11:37:25
*
 */
public class YwUserCustomer {

   private int id; // 主键
   private String user_id; // 用户编号
   private String cust_name; // 客户名称
   private String cust_type; // 客户类型
   private String id_no; // 身份证号码
   private String mobile_tel; // 手机号码
   private String phone_tel; // 座机号码
   private String header_url; // 客户头像
   private String wx_code; // 微信号
   private String qq_code; // QQ号
   private String e_mail; // 邮箱地址
   private String sex; // 性别
   private String city; // 地区
   private String address; // 详细地址
   private String birthday; // 出生日期
   private String cust_status; // 客户状态
   private String industry_type; // 行业
   private String company_name; // 公司名称
   private String company_site; // 公司网站
   private Date create_datetime; // 创建时间
   private Date update_datetime; // 更新时间
   private String ro_ids; // 角色编号字符串
   
   private String ro_names;// 
   
   private YwUser ywUser;

	public int getId() {
	    return this.id;
	}
	
	public void setId(int id) {
	    this.id = id;
	}
    public String getUser_id() {
	    return this.user_id;
	}
	
	public void setUser_id(String user_id) {
	    this.user_id = user_id;
	}
    public String getCust_name() {
	    return this.cust_name;
	}
	
	public void setCust_name(String cust_name) {
	    this.cust_name = cust_name;
	}
    public String getCust_type() {
	    return this.cust_type;
	}
	
	public void setCust_type(String cust_type) {
	    this.cust_type = cust_type;
	}
    public String getId_no() {
	    return this.id_no;
	}
	
	public void setId_no(String id_no) {
	    this.id_no = id_no;
	}
    public String getMobile_tel() {
	    return this.mobile_tel;
	}
	
	public void setMobile_tel(String mobile_tel) {
	    this.mobile_tel = mobile_tel;
	}
    public String getPhone_tel() {
	    return this.phone_tel;
	}
	
	public void setPhone_tel(String phone_tel) {
	    this.phone_tel = phone_tel;
	}
    public String getHeader_url() {
	    return this.header_url;
	}
	
	public void setHeader_url(String header_url) {
	    this.header_url = header_url;
	}
    public String getWx_code() {
	    return this.wx_code;
	}
	
	public void setWx_code(String wx_code) {
	    this.wx_code = wx_code;
	}
    public String getQq_code() {
	    return this.qq_code;
	}
	
	public void setQq_code(String qq_code) {
	    this.qq_code = qq_code;
	}
    public String getE_mail() {
	    return this.e_mail;
	}
	
	public void setE_mail(String e_mail) {
	    this.e_mail = e_mail;
	}
    public String getSex() {
	    return this.sex;
	}
	
	public void setSex(String sex) {
	    this.sex = sex;
	}
    public String getCity() {
	    return this.city;
	}
	
	public void setCity(String city) {
	    this.city = city;
	}
    public String getAddress() {
	    return this.address;
	}
	
	public void setAddress(String address) {
	    this.address = address;
	}
    public String getBirthday() {
	    return this.birthday;
	}
	
	public void setBirthday(String birthday) {
	    this.birthday = birthday;
	}
    public String getCust_status() {
	    return this.cust_status;
	}
	
	public void setCust_status(String cust_status) {
	    this.cust_status = cust_status;
	}
    public String getIndustry_type() {
	    return this.industry_type;
	}
	
	public void setIndustry_type(String industry_type) {
	    this.industry_type = industry_type;
	}
    public String getCompany_name() {
	    return this.company_name;
	}
	
	public void setCompany_name(String company_name) {
	    this.company_name = company_name;
	}
    public String getCompany_site() {
	    return this.company_site;
	}
	
	public void setCompany_site(String company_site) {
	    this.company_site = company_site;
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
    public String getRo_ids() {
	    return this.ro_ids;
	}
	
	public void setRo_ids(String ro_ids) {
	    this.ro_ids = ro_ids;
	}

	public String getRo_names() {
		return ro_names;
	}

	public void setRo_names(String ro_names) {
		this.ro_names = ro_names;
	}

	public YwUser getYwUser() {
		return ywUser;
	}

	public void setYwUser(YwUser ywUser) {
		this.ywUser = ywUser;
	}
	
}
