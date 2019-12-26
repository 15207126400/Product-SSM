package com.yunwei.product.common.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.util.DateUtil;

/**
 * 
* @ClassName: YwNotice 
* @Description: 公告栏信息表 
* @author 晏飞
* @date 2018年5月15日 下午4:34:19 
*
 */
public class YwNotice {
	private String noc_id;
	private String scene_type;
	private String noc_content;
	private String create_name;
	private Date create_time;
	private String update_name;
	private Date update_time;
	private String status;
	
	public String getNoc_id() {
		return noc_id;
	}
	public void setNoc_id(String noc_id) {
		this.noc_id = noc_id;
	}
	public String getNoc_content() {
		return noc_content;
	}
	public void setNoc_content(String noc_content) {
		this.noc_content = noc_content;
	}
	/** 
	* @return scene_type 
	*/
	public String getScene_type() {
		return scene_type;
	}
	/** 
	* @param scene_type 要设置的 scene_type 
	*/
	public void setScene_type(String scene_type) {
		this.scene_type = scene_type;
	}
	/** 
	* @return create_name 
	*/
	public String getCreate_name() {
		return create_name;
	}
	/** 
	* @param create_name 要设置的 create_name 
	*/
	public void setCreate_name(String create_name) {
		this.create_name = create_name;
	}
	/** 
	* @return create_time 
	*/
	@JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
	public Date getCreate_time() {
		return create_time;
	}
	/** 
	* @param create_time 要设置的 create_time 
	*/
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	/** 
	* @return update_name 
	*/
	public String getUpdate_name() {
		return update_name;
	}
	/** 
	* @param update_name 要设置的 update_name 
	*/
	public void setUpdate_name(String update_name) {
		this.update_name = update_name;
	}
	/** 
	* @return update_time 
	*/
	@JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
	public Date getUpdate_time() {
		return update_time;
	}
	/** 
	* @param update_time 要设置的 update_time 
	*/
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
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
	
	
}
