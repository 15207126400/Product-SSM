package com.yunwei.product.common.model;
/**
 * 
* @ClassName: YwProductCollection 
* @Description: 收藏表 
* @author 晏飞
* @date 2018年4月3日 上午10:34:25 
*
 */
public class YwProductCollection {
	private int id;
	private String url;
	private String title;
	private String price;
	private String createBy;
	private String shopid;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getShopid() {
		return shopid;
	}
	public void setShopid(String shopid) {
		this.shopid = shopid;
	}
	
	
}
