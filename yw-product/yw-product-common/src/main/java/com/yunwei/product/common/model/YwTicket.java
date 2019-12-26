package com.yunwei.product.common.model;
/**
 * 
* @ClassName: YwTicket 
* @Description: 抽奖实体类 
* @author 晏飞
* @date 2018年5月16日 下午3:01:11 
*
 */
public class YwTicket {
	private String ticket_id;		//抽奖人员编号
	private String ticket_name;		//抽奖人员名字
	private String school_name;		//学校名称
	private String ticket_flag;		//是否设置必抽中(0,未设置必中  1,设置必中)
	
	
	public String getTicket_id() {
		return ticket_id;
	}
	public void setTicket_id(String ticket_id) {
		this.ticket_id = ticket_id;
	}
	public String getTicket_name() {
		return ticket_name;
	}
	public void setTicket_name(String ticket_name) {
		this.ticket_name = ticket_name;
	}
	public String getSchool_name() {
		return school_name;
	}
	public void setSchool_name(String school_name) {
		this.school_name = school_name;
	}
	public String getTicket_flag() {
		return ticket_flag;
	}
	public void setTicket_flag(String ticket_flag) {
		this.ticket_flag = ticket_flag;
	}
	
}
