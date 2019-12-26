package com.yunwei.product.common.model;

import java.math.BigDecimal;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.util.DateUtil;

/**
 * 沙龙注册用户信息表
* @ClassName: SalonAccount
* @Description: TODO(TODO) 
* @author yanf
* @date 2019年06月21日 下午15:38:55
*
 */
public class QhzSalonAccount {

   private String id; // 
   private String openid; // openid
   private String name; // 姓名
   private String tel; // 手机号
   private String card; // 身份证
   private String company; // 公司名
   private String adviser; // 学习顾问
   private String child_name; // 孩子姓名
   private String child_card; // 孩子身份证
   private Date create_datetime; // 创建时间
   private Date update_datetime; // 更新时间
   private String existing_ids; //已报课程
   private String sc_price; //已报课程
   
   private String existing_names; //已报课程名称

	public String getId() {
	    return this.id;
	}
	
	public void setId(String id) {
	    this.id = id;
	}
    public String getOpenid() {
	    return this.openid;
	}
	
	public void setOpenid(String openid) {
	    this.openid = openid;
	}
    public String getName() {
	    return this.name;
	}
	
	public void setName(String name) {
	    this.name = name;
	}
    public String getTel() {
	    return this.tel;
	}
	
	public void setTel(String tel) {
	    this.tel = tel;
	}
    public String getCard() {
	    return this.card;
	}
	
	public void setCard(String card) {
	    this.card = card;
	}
    public String getCompany() {
	    return this.company;
	}
	
	public void setCompany(String company) {
	    this.company = company;
	}
    public String getAdviser() {
	    return this.adviser;
	}
	
	public void setAdviser(String adviser) {
	    this.adviser = adviser;
	}
    public String getChild_name() {
	    return this.child_name;
	}
	
	public void setChild_name(String child_name) {
	    this.child_name = child_name;
	}
    public String getChild_card() {
	    return this.child_card;
	}
	
	public void setChild_card(String child_card) {
	    this.child_card = child_card;
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

	/** 
	* @return existing_ids 
	*/
	public String getExisting_ids() {
		return existing_ids;
	}

	/** 
	* @param existing_ids 要设置的 existing_ids 
	*/
	public void setExisting_ids(String existing_ids) {
		this.existing_ids = existing_ids;
	}

	/** 
	* @return existing_names 
	*/
	public String getExisting_names() {
		return existing_names;
	}

	/** 
	* @param existing_names 要设置的 existing_names 
	*/
	public void setExisting_names(String existing_names) {
		this.existing_names = existing_names;
	}

	/** 
	* @return sc_price 
	*/
	public String getSc_price() {
		return sc_price;
	}

	/** 
	* @param sc_price 要设置的 sc_price 
	*/
	public void setSc_price(String sc_price) {
		this.sc_price = sc_price;
	}
	
}
