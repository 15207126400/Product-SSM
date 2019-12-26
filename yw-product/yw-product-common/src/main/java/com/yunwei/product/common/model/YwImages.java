package com.yunwei.product.common.model;

import java.util.Date;

/**
 * 系统字典
 * @author zhangjh
 *
 */
public class YwImages {
	private int image_id;// 图片id
	private String image_url;// 图片url
	private String image_type;// 图片类型
	private String product_id;// 商品编号
	private String image_order;// 图片顺序
	private Date image_createtime;// 图片创建时间
	private Date image_updatetime;// 图片更新时间
	private String image_remark;// 图片备注
	private String image_status;// 图片状态
	private String gw_product_id;// 
	private String gw_newcenters_id;// 图片状态
	
	
	public int getImage_id() {
		return image_id;
	}
	public void setImage_id(int image_id) {
		this.image_id = image_id;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public String getImage_type() {
		return image_type;
	}
	public void setImage_type(String image_type) {
		this.image_type = image_type;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getImage_order() {
		return image_order;
	}
	public void setImage_order(String image_order) {
		this.image_order = image_order;
	}
	public Date getImage_createtime() {
		return image_createtime;
	}
	public void setImage_createtime(Date image_createtime) {
		this.image_createtime = image_createtime;
	}
	public Date getImage_updatetime() {
		return image_updatetime;
	}
	public void setImage_updatetime(Date image_updatetime) {
		this.image_updatetime = image_updatetime;
	}
	public String getImage_remark() {
		return image_remark;
	}
	public void setImage_remark(String image_remark) {
		this.image_remark = image_remark;
	}
	public String getImage_status() {
		return image_status;
	}
	public void setImage_status(String image_status) {
		this.image_status = image_status;
	}
	public String getGw_product_id() {
		return gw_product_id;
	}
	public void setGw_product_id(String gw_product_id) {
		this.gw_product_id = gw_product_id;
	}
	public String getGw_newcenters_id() {
		return gw_newcenters_id;
	}
	public void setGw_newcenters_id(String gw_newcenters_id) {
		this.gw_newcenters_id = gw_newcenters_id;
	}
	
	
}
