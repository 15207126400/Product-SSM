package com.yunwei.product.common.model;

import javax.validation.constraints.Digits;

import org.apache.commons.lang3.StringUtils;

/**
 * 
* @ClassName: YwProductSku 
* @Description: TODO(TODO) 商品sku表
* @author zhangjh
* @date 2018年3月26日 下午3:41:40 
*
 */
public class YwProductSku {

	private String sku_id;//商品sku编号
	private String product_id;//商品编号
	private String sku_attr;//sku属性
	private String sku_image;//sku图片
	@Digits(fraction = 2, integer = 9,message="{价格必须是数字}")
	private String sku_price;//sku价格
	@Digits(fraction = 2, integer = 9)
	private String sku_virtualprice;//sku虚拟价格
	@Digits(fraction = 0, integer = 9)
	private String sku_stock;//sku库存
	private String sku_sales;//sku销量
	private String image_id;//
	private String product_name;//商品名称
	
	/**  兼容部分 **/
	private String product_sku_image;// 商品sku兼容图片
	
	public String getSku_id() {
		return sku_id;
	}
	public void setSku_id(String sku_id) {
		this.sku_id = sku_id;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getSku_attr() {
		return sku_attr;
	}
	public void setSku_attr(String sku_attr) {
		this.sku_attr = sku_attr;
	}
	public String getSku_price() {
		return sku_price;
	}
	public void setSku_price(String sku_price) {
		this.sku_price = sku_price;
	}
	public String getSku_stock() {
		return sku_stock;
	}
	public void setSku_stock(String sku_stock) {
		this.sku_stock = sku_stock;
	}
	public String getSku_sales() {
		return sku_sales;
	}
	public void setSku_sales(String sku_sales) {
		this.sku_sales = sku_sales;
	}
	public String getSku_virtualprice() {
		return sku_virtualprice;
	}
	public void setSku_virtualprice(String sku_virtualprice) {
		this.sku_virtualprice = sku_virtualprice;
	}
	public String getSku_image() {
		return StringUtils.isEmpty(product_sku_image)? sku_image : getProduct_sku_image();
	}
	public void setSku_image(String sku_image) {
		this.sku_image = sku_image;
	}
	public String getImage_id() {
		return image_id;
	}
	public void setImage_id(String image_id) {
		this.image_id = image_id;
	}
	public String getProduct_sku_image() {
		return product_sku_image;
	}
	public void setProduct_sku_image(String product_sku_image) {
		this.product_sku_image = product_sku_image;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	
	
}
