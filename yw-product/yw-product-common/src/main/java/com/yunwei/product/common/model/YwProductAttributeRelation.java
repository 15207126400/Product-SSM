package com.yunwei.product.common.model;

/**
 * 
* @ClassName: YwProductAttributeRelation 
* @Description: TODO(TODO) 商品属性关联信息表
* @author zhangjh
* @date 2018年3月26日 下午3:37:37 
*
 */
public class YwProductAttributeRelation {

	private String prod_attr_id;//商品属性关联编号
	private String product_id;//商品编号
	private String attrname_id;//属性名编号
	private String attrvalue_id;//属性值编号
	public String getProd_attr_id() {
		return prod_attr_id;
	}
	public void setProd_attr_id(String prod_attr_id) {
		this.prod_attr_id = prod_attr_id;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getAttrname_id() {
		return attrname_id;
	}
	public void setAttrname_id(String attrname_id) {
		this.attrname_id = attrname_id;
	}
	public String getAttrvalue_id() {
		return attrvalue_id;
	}
	public void setAttrvalue_id(String attrvalue_id) {
		this.attrvalue_id = attrvalue_id;
	}
	
	
	
}
