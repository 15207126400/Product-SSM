package com.yunwei.product.common.backend.model.form;

import com.yunwei.product.common.model.YwOrder;

/**
 * 订单商品表单
* @ClassName: orderForm 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年4月26日 下午6:54:54 
*
 */
public class YwOrderProductForm{
   
	private String id;
    private String  title;
    private String  url;
    private String  price;
    private String count;
    private String sku_attr;
    private String sku_id;
    private String sku_stock;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	
	public String getSku_attr() {
		return sku_attr;
	}
	public void setSku_attr(String sku_attr) {
		this.sku_attr = sku_attr;
	}
	public String getSku_id() {
		return sku_id;
	}
	public void setSku_id(String sku_id) {
		this.sku_id = sku_id;
	}
	public String getSku_stock() {
		return sku_stock;
	}
	public void setSku_stock(String sku_stock) {
		this.sku_stock = sku_stock;
	}
    
    
}
