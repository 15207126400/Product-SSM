package com.yunwei.product.common.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.util.DateUtil;

/**
 * 
* @ClassName: ShopAddress 
* @Description: TODO(商品类别) 
* @author 晏飞
* @date 2017年11月17日 下午6:57:16 
*
 */
public class YwProductClassify {
	private String classify_id;
	private String classify_name;
	private String classify_url;
	private Date classify_createtime;
	private Date classify_updatetime;
	private String parent_id;
	private String classify_status;
	private String classify_order;
	private String classify_level;
	
	public String getClassify_id() {
		return classify_id;
	}
	public void setClassify_id(String classify_id) {
		this.classify_id = classify_id;
	}
	public String getClassify_name() {
		return classify_name;
	}
	public void setClassify_name(String classify_name) {
		this.classify_name = classify_name;
	}
	public String getClassify_status() {
		return classify_status;
	}
	public void setClassify_status(String classify_status) {
		this.classify_status = classify_status;
	}
	public String getClassify_url() {
		return classify_url;
	}
	public void setClassify_url(String classify_url) {
		this.classify_url = classify_url;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	public String getClassify_order() {
		return classify_order;
	}
	public void setClassify_order(String classify_order) {
		this.classify_order = classify_order;
	}
	/** 
	* @return classify_level 
	*/
	public String getClassify_level() {
		return classify_level;
	}
	/** 
	* @param classify_level 要设置的 classify_level 
	*/
	public void setClassify_level(String classify_level) {
		this.classify_level = classify_level;
	}
	@JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
	public Date getClassify_createtime() {
		return classify_createtime;
	}
	/** 
	* @param classify_createtime 要设置的 classify_createtime 
	*/
	public void setClassify_createtime(Date classify_createtime) {
		this.classify_createtime = classify_createtime;
	}
	@JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
	public Date getClassify_updatetime() {
		return classify_updatetime;
	}
	/** 
	* @param classify_updatetime 要设置的 classify_updatetime 
	*/
	public void setClassify_updatetime(Date classify_updatetime) {
		this.classify_updatetime = classify_updatetime;
	}
	
	
	
}
