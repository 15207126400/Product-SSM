package com.yunwei.product.common.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 
* @ClassName: ShopDetail 
* @Description: TODO(商城首页商品展示----图片,内容,价格,购买人数) 
* @author 晏飞
* @date 2017年11月2日 下午2:12:47 
*
 */
public class YwProduct {
	private String id;
	private String product_code;	//商品编码
	private String url;         // 商品主图
	private String content;		// 图文描述
	private String product_virtualprice;//商品虚拟价格
	private String price;		//价格
	private	String title;		//标题
	private String shopType;	//商品类型
	private String shopType2;	//二级商品类型
	private String shopTypeName;	//商品类型名称
	private String shopTypeName2;	//二级商品类型名称
	private String market_code;	//营销类型 (标志)
	private String product_sort;	//商品排序
	private String product_markets;	//商品营销字符串
	private String keyword;		//特色描述 (第二描述)
	private String sold;		//已售件数
	private String stock;		//库存
	private String isLike;		//收藏状态
	private String product_carriage;	//运费设置
	private String product_virtualsales;	//虚拟销量
	private String status;
	private String branch_id;// 机构编号
	private String starting_amount;// 起拍量

	private String product_barcode;//商品条形码
	private String[] image_url;
	private String attr_name;//属性名称
	private String[] attr_value;//属性值
	private String[] sku_values;// sku数据
	private List<YwProductAttributename> ywProductAttributenames = new ArrayList<YwProductAttributename>();//商品属性
	private List<YwProductSku> ywProductSkus = new ArrayList<YwProductSku>(); //商品sku
	private List<YwProductImage> ywImages = new ArrayList<YwProductImage>(); //商品图片
	
	/**  兼容部分  **/
	private String product_url;// 商品兼容图片
	private String classify_name;// 商品类型名称
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProduct_code() {
		return product_code;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getShopType() {
		return shopType;
	}
	public void setShopType(String shopType) {
		this.shopType = shopType;
	}
	public String[] getImage_url() {
		return image_url;
	}
	public void setImage_url(String[] image_url) {
		this.image_url = image_url;
	}
	public String getProduct_virtualprice() {
		return product_virtualprice;
	}
	public void setProduct_virtualprice(String product_virtualprice) {
		this.product_virtualprice = product_virtualprice;
	}
	public String getProduct_carriage() {
		return product_carriage;
	}
	public void setProduct_carriage(String product_carriage) {
		this.product_carriage = product_carriage;
	}
	public String getMarket_code() {
		return market_code;
	}
	public void setMarket_code(String market_code) {
		this.market_code = market_code;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getSold() {
		return sold;
	}
	public void setSold(String sold) {
		this.sold = sold;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	
	public String getAttr_name() {
		return attr_name;
	}
	public void setAttr_name(String attr_name) {
		this.attr_name = attr_name;
	}
	
	public String[] getAttr_value() {
		return attr_value ;
	}
	public void setAttr_value(String[] attr_value) {
		this.attr_value = attr_value;
	}
	public String[] getSku_values() {
		return sku_values;
	}
	public void setSku_values(String[] sku_values) {
		this.sku_values = sku_values;
	}
	public List<YwProductAttributename> getYwProductAttributenames() {
		return ywProductAttributenames;
	}
	public void setYwProductAttributenames(
			List<YwProductAttributename> ywProductAttributenames) {
		this.ywProductAttributenames = ywProductAttributenames;
	}
	public List<YwProductSku> getYwProductSkus() {
		return ywProductSkus;
	}
	public void setYwProductSkus(List<YwProductSku> ywProductSkus) {
		this.ywProductSkus = ywProductSkus;
	}
	public String getProduct_barcode() {
		return product_barcode;
	}
	public void setProduct_barcode(String product_barcode) {
		this.product_barcode = product_barcode;
	}
	public String getIsLike() {
		return isLike;
	}
	public void setIsLike(String isLike) {
		this.isLike = isLike;
	}
	public List<YwProductImage> getYwImages() {
		return ywImages;
	}
	public void setYwImages(List<YwProductImage> ywImages) {
		this.ywImages = ywImages;
	}
	public String getProduct_sort() {
		return product_sort;
	}
	public void setProduct_sort(String product_sort) {
		this.product_sort = product_sort;
	}
	public String getProduct_markets() {
		return product_markets;
	}
	public void setProduct_markets(String product_markets) {
		this.product_markets = product_markets;
	}
	public String getProduct_virtualsales() {
		return product_virtualsales;
	}
	public void setProduct_virtualsales(String product_virtualsales) {
		this.product_virtualsales = product_virtualsales;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProduct_url() {
		return StringUtils.isEmpty(product_url) ? getUrl() : product_url;
	}
	public void setProduct_url(String product_url) {
		this.product_url = product_url;
	}
	public String getBranch_id() {
		return branch_id;
	}
	public void setBranch_id(String branch_id) {
		this.branch_id = branch_id;
	}
	public String getStarting_amount() {
		return starting_amount;
	}
	public void setStarting_amount(String starting_amount) {
		this.starting_amount = starting_amount;
	}
	/** 
	* @return shopType2 
	*/
	public String getShopType2() {
		return shopType2;
	}
	/** 
	* @param shopType2 要设置的 shopType2 
	*/
	public void setShopType2(String shopType2) {
		this.shopType2 = shopType2;
	}
	/** 
	* @return shopTypeName 
	*/
	public String getShopTypeName() {
		return shopTypeName;
	}
	/** 
	* @param shopTypeName 要设置的 shopTypeName 
	*/
	public void setShopTypeName(String shopTypeName) {
		this.shopTypeName = shopTypeName;
	}
	/** 
	* @return shopTypeName2 
	*/
	public String getShopTypeName2() {
		return shopTypeName2;
	}
	/** 
	* @param shopTypeName2 要设置的 shopTypeName2 
	*/
	public void setShopTypeName2(String shopTypeName2) {
		this.shopTypeName2 = shopTypeName2;
	}
	public String getClassify_name() {
		return classify_name;
	}
	public void setClassify_name(String classify_name) {
		this.classify_name = classify_name;
	}
	
}
