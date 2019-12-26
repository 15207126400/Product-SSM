package com.yunwei.product.common.model;

import java.math.BigDecimal;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.util.DateUtil;

/**
 * 商品图片
* @ClassName: YwProductImage
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年08月29日 下午16:28:58
*
 */
public class YwProductImage {

   private String id; // 编号
   private String product_id; // 商品编号
   private String image_id; // 原图编号
   private Date create_datetime; // 创建时间
   private Date update_datetime; // 更新时间
   private int sort; // 排序
   private String status; // 状态（1：启用，2：禁用）
   
   private String image_url;
   
	public String getId() {
	    return this.id;
	}
	
	public void setId(String id) {
	    this.id = id;
	}
    public String getProduct_id() {
	    return this.product_id;
	}
	
	public void setProduct_id(String product_id) {
	    this.product_id = product_id;
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
	public int getSort() {
	    return this.sort;
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

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	
}
