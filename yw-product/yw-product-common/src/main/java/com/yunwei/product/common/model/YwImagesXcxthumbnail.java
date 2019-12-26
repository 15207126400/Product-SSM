package com.yunwei.product.common.model;

import java.math.BigDecimal;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.util.DateUtil;

/**
 * 小程序缩略图中心
* @ClassName: YwImagesXcxthumbnail
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年08月24日 下午14:52:12
*
 */
public class YwImagesXcxthumbnail {

   private String id; // 主键
   private String thumbnail_name; // 缩略图名称
   private String thumbnail_type; // 缩略图类型
   private String thumbnail_url; // 缩略图路径
   private String image_id; // 原图编号
   private Date create_datetime; // 创建时间
   private Date update_datetime; // 更新时间
   private String status; // 状态（1：启用，2、禁用）

	public String getId() {
	    return this.id;
	}
	
	public void setId(String id) {
	    this.id = id;
	}
    public String getThumbnail_name() {
	    return this.thumbnail_name;
	}
	
	public void setThumbnail_name(String thumbnail_name) {
	    this.thumbnail_name = thumbnail_name;
	}
    public String getThumbnail_type() {
	    return this.thumbnail_type;
	}
	
	public void setThumbnail_type(String thumbnail_type) {
	    this.thumbnail_type = thumbnail_type;
	}
    public String getThumbnail_url() {
	    return this.thumbnail_url;
	}
	
	public void setThumbnail_url(String thumbnail_url) {
	    this.thumbnail_url = thumbnail_url;
	}
    public String getImage_id() {
	    return this.image_id;
	}
	
	public void setImage_id(String image_id) {
	    this.image_id = image_id;
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
    public String getStatus() {
	    return this.status;
	}
	
	public void setStatus(String status) {
	    this.status = status;
	}
	
}
