package com.yunwei.product.common.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
* @ClassName: YwProductAttributename 
* @Description: 商品属性值表
* @author zhangjh
* @date 2018年3月26日 下午3:36:10 
*
 */
public class YwProductAttributename {

	private String attrname_id;//商品属性名编号
	private String attrname_name;//商品属性名编号
	private String classify_id;//商品分类编号
	private String product_id;//商品编号
	private String parent_id;//商品属性名父编号
	private List<YwProductAttributevalue> ywProductAttributevalues = new ArrayList<YwProductAttributevalue>();
	public String getAttrname_id() {
		return attrname_id;
	}
	public void setAttrname_id(String attrname_id) {
		this.attrname_id = attrname_id;
	}
	public String getAttrname_name() {
		return attrname_name;
	}
	public void setAttrname_name(String attrname_name) {
		this.attrname_name = attrname_name;
	}
	public String getClassify_id() {
		return classify_id;
	}
	public void setClassify_id(String classify_id) {
		this.classify_id = classify_id;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public List<YwProductAttributevalue> getYwProductAttributevalues() {
		return ywProductAttributevalues;
	}
	public void setYwProductAttributevalues(
			List<YwProductAttributevalue> ywProductAttributevalues) {
		this.ywProductAttributevalues = ywProductAttributevalues;
	}
	
}
