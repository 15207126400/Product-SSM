package com.yunwei.product.common.model;

import java.math.BigDecimal;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.util.DateUtil;

/**
 * 沙龙注册会议信息模块
* @ClassName: SalonMeeting
* @Description: TODO(TODO) 
* @author yanf
* @date 2019年07月18日 上午09:13:19
*
 */
public class QhzSalonMeeting {

   private String id; // 会议编号
   private String name; // 会议名称
   private Date start_day; // 开始日期
   private Date end_day; // 结束日期
   private String address; // 会议地址
   private String people_num; // 会议限制人数
   private String people_surplus_num; // 会议签到人数
   private String price; // 会议价格
   private String is_ticket; // 是否有票务编码(0.无 1.有)
   private String status; // 启用状态(1启用 2禁用)
   
   private String code;

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
    @JsonFormat(pattern = DateUtil.DATE_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_FORMAT)
    public Date getStart_day() {
	    return this.start_day;
	}
	
	public void setStart_day(Date start_day) {
	    this.start_day = start_day;
	}
    public String getAddress() {
	    return this.address;
	}
	
	public void setAddress(String address) {
	    this.address = address;
	}
    public String getPeople_num() {
	    return this.people_num;
	}
	
	public void setPeople_num(String people_num) {
	    this.people_num = people_num;
	}
    public String getPrice() {
	    return this.price;
	}
	
	public void setPrice(String price) {
	    this.price = price;
	}

	/** 
	* @return end_day 
	*/
	@JsonFormat(pattern = DateUtil.DATE_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_FORMAT)
	public Date getEnd_day() {
		return end_day;
	}

	/** 
	* @param end_day 要设置的 end_day 
	*/
	public void setEnd_day(Date end_day) {
		this.end_day = end_day;
	}

	/** 
	* @return people_surplus_num 
	*/
	public String getPeople_surplus_num() {
		return people_surplus_num;
	}

	/** 
	* @param people_surplus_num 要设置的 people_surplus_num 
	*/
	public void setPeople_surplus_num(String people_surplus_num) {
		this.people_surplus_num = people_surplus_num;
	}

	/** 
	* @return status 
	*/
	public String getStatus() {
		return status;
	}

	/** 
	* @param status 要设置的 status 
	*/
	public void setStatus(String status) {
		this.status = status;
	}

	/** 
	* @return code 
	*/
	public String getCode() {
		return code;
	}

	/** 
	* @param code 要设置的 code 
	*/
	public void setCode(String code) {
		this.code = code;
	}

	/** 
	* @return is_ticket 
	*/
	public String getIs_ticket() {
		return is_ticket;
	}

	/** 
	* @param is_ticket 要设置的 is_ticket 
	*/
	public void setIs_ticket(String is_ticket) {
		this.is_ticket = is_ticket;
	}


}
