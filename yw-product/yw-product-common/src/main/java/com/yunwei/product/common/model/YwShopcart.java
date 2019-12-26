package com.yunwei.product.common.model;
/**
 * 
* @ClassName: ShopPaycar 
* @Description: TODO(购物车数据表) 
* @author 晏飞
* @date 2017年11月16日 上午11:17:59 
*
 */
public class YwShopcart {
	private int id;
	private String title;
	private String url;
	private String price;
	private String createBy;
	private String shopid;
	private boolean isSelect;
	private String count;
	private String sku_id;
	private String sku_attr;
	private String product_carriage;
	private String starting_amount;
	private String sku_stock;
	private String branch_id;
	private String branch_name;
	private String branch_logo;
	
	
	public String getBranch_name() {
		return branch_name;
	}
	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}
	public String getBranch_logo() {
		return branch_logo;
	}
	public void setBranch_logo(String branch_logo) {
		this.branch_logo = branch_logo;
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
	public void setSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public boolean getIsSelect() {
		return isSelect;
	}
	public String getSku_id() {
		return sku_id;
	}
	public void setSku_id(String sku_id) {
		this.sku_id = sku_id;
	}
	public void setIsSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}
	public String getProduct_carriage() {
		return product_carriage;
	}
	public void setProduct_carriage(String product_carriage) {
		this.product_carriage = product_carriage;
	}
	/** 
	* @return starting_amount 
	*/
	public String getStarting_amount() {
		return starting_amount;
	}
	/** 
	* @param starting_amount 要设置的 starting_amount 
	*/
	public void setStarting_amount(String starting_amount) {
		this.starting_amount = starting_amount;
	}
	/** 
	* @return sku_stock 
	*/
	public String getSku_stock() {
		return sku_stock;
	}
	/** 
	* @param sku_stock 要设置的 sku_stock 
	*/
	public void setSku_stock(String sku_stock) {
		this.sku_stock = sku_stock;
	}
	/** 
	* @return branch_id 
	*/
	public String getBranch_id() {
		return branch_id;
	}
	/** 
	* @param branch_id 要设置的 branch_id 
	*/
	public void setBranch_id(String branch_id) {
		this.branch_id = branch_id;
	}
}
