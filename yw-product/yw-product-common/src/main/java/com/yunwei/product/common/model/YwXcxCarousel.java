package com.yunwei.product.common.model;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.util.DateUtil;

/**
 * 小程序轮播图
* @ClassName: YwXcxCarousel
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年08月23日 下午16:04:12
*
 */
public class YwXcxCarousel extends YwXcxNavigation{

   private String id; // 编号
   private String carousel_type; // 轮播图类型
   private String carousel_name; // 轮播图名称
   private String carousel_url; // 轮播图路径
   private Date create_datetime; // 创建时间
   private Date update_datetime; // 更新时间
   private int sort; // 排序
   private String navigation; // 跳转导航
   private String status; // 状态

	public String getId() {
	    return this.id;
	}
	
	public void setId(String id) {
	    this.id = id;
	}
    public String getCarousel_type() {
	    return this.carousel_type;
	}
	
	public void setCarousel_type(String carousel_type) {
	    this.carousel_type = carousel_type;
	}
    public String getCarousel_name() {
	    return this.carousel_name;
	}
	
	public void setCarousel_name(String carousel_name) {
	    this.carousel_name = carousel_name;
	}
    public String getCarousel_url() {
	    return this.carousel_url;
	}
	
	public void setCarousel_url(String carousel_url) {
	    this.carousel_url = carousel_url;
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
	
    public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getStatus() {
	    return this.status;
	}
	
	public void setStatus(String status) {
	    this.status = status;
	}

	/** 
	* @return navigation 
	*/
	public String getNavigation() {
		return navigation;
	}

	/** 
	* @param navigation 要设置的 navigation 
	*/
	public void setNavigation(String navigation) {
		this.navigation = navigation;
	}
	
}
