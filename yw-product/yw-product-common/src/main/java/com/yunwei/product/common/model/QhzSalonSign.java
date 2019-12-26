package com.yunwei.product.common.model;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.util.DateUtil;

/**
 * 沙龙注册签到信息模块
* @ClassName: SalonSign
* @Description: TODO(TODO) 
* @author yanf
* @date 2019年07月18日 上午09:21:30
*
 */
public class QhzSalonSign {

   private String id; // 签到编号
   private String openid; // openid
   private String name; // 签到人姓名
   private String phone; // 签到人联系电话
   private String card; // 签到人身份证
   private String adviser; // 签到人学习顾问
   private String industry; // 行业
   private String turnover; // 年营业额
   private String position; // 职位
   private String ticket_number; // 票务编号
   private Date sgin_time; // 签到时间
   private String meeting_id; // 签到会议编号
   private String sgin_code; // 签到入场号
   private String pay_status; // 入场费支付状态(1.未支付 2.已支付)
   private String remark; // 备注
   
   private String meeting_name;

	public String getId() {
	    return this.id;
	}
	
	public void setId(String id) {
	    this.id = id;
	}
    public String getName() {
	    return this.name;
	}
	
	public void setName(String name) {
	    this.name = name;
	}
    public String getPhone() {
	    return this.phone;
	}
	
	public void setPhone(String phone) {
	    this.phone = phone;
	}
    public String getCard() {
	    return this.card;
	}
	
	public void setCard(String card) {
	    this.card = card;
	}
    @JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
    public Date getSgin_time() {
	    return this.sgin_time;
	}
	
	public void setSgin_time(Date sgin_time) {
	    this.sgin_time = sgin_time;
	}
    public String getMeeting_id() {
	    return this.meeting_id;
	}
	
	public void setMeeting_id(String meeting_id) {
	    this.meeting_id = meeting_id;
	}
    public String getSgin_code() {
	    return this.sgin_code;
	}
	
	public void setSgin_code(String sgin_code) {
	    this.sgin_code = sgin_code;
	}

	/** 
	* @return openid 
	*/
	public String getOpenid() {
		return openid;
	}

	/** 
	* @param openid 要设置的 openid 
	*/
	public void setOpenid(String openid) {
		this.openid = openid;
	}

	/** 
	* @return pay_status 
	*/
	public String getPay_status() {
		return pay_status;
	}

	/** 
	* @param pay_status 要设置的 pay_status 
	*/
	public void setPay_status(String pay_status) {
		this.pay_status = pay_status;
	}

	/** 
	* @return meeting_name 
	*/
	public String getMeeting_name() {
		return meeting_name;
	}

	/** 
	* @param meeting_name 要设置的 meeting_name 
	*/
	public void setMeeting_name(String meeting_name) {
		this.meeting_name = meeting_name;
	}

	/** 
	* @return remark 
	*/
	public String getRemark() {
		return remark;
	}

	/** 
	* @param remark 要设置的 remark 
	*/
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/** 
	* @return industry 
	*/
	public String getIndustry() {
		return industry;
	}

	/** 
	* @param industry 要设置的 industry 
	*/
	public void setIndustry(String industry) {
		this.industry = industry;
	}

	/** 
	* @return turnover 
	*/
	public String getTurnover() {
		return turnover;
	}

	/** 
	* @param turnover 要设置的 turnover 
	*/
	public void setTurnover(String turnover) {
		this.turnover = turnover;
	}

	/** 
	* @return position 
	*/
	public String getPosition() {
		return position;
	}

	/** 
	* @param position 要设置的 position 
	*/
	public void setPosition(String position) {
		this.position = position;
	}

	/** 
	* @return ticket_number 
	*/
	public String getTicket_number() {
		return ticket_number;
	}

	/** 
	* @param ticket_number 要设置的 ticket_number 
	*/
	public void setTicket_number(String ticket_number) {
		this.ticket_number = ticket_number;
	}

	/** 
	* @return adviser 
	*/
	public String getAdviser() {
		return adviser;
	}

	/** 
	* @param adviser 要设置的 adviser 
	*/
	public void setAdviser(String adviser) {
		this.adviser = adviser;
	}
	
}
