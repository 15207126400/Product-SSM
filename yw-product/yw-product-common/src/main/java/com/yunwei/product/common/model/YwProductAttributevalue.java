package com.yunwei.product.common.model;

/**
 * 
* @ClassName: YwProductAttributevalue 
* @Description: TODO(TODO) 商品属性值表
* @author zhangjh
* @date 2018年3月26日 下午3:40:19 
*
 */
public class YwProductAttributevalue {

	private String attrvalue_id;//商品属性值编号
	private String attrvalue_name;//商品属性值名称
	private String attrname_id;//商品属性名编号
	public String getAttrvalue_id() {
		return attrvalue_id;
	}
	public void setAttrvalue_id(String attrvalue_id) {
		this.attrvalue_id = attrvalue_id;
	}
	public String getAttrvalue_name() {
		return attrvalue_name;
	}
	public void setAttrvalue_name(String attrvalue_name) {
		this.attrvalue_name = attrvalue_name;
	}
	public String getAttrname_id() {
		return attrname_id;
	}
	public void setAttrname_id(String attrname_id) {
		this.attrname_id = attrname_id;
	}
	
}
