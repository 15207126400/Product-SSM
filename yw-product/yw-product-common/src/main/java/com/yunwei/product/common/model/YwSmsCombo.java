package com.yunwei.product.common.model;

/**
 * 短信套餐信息表
* @ClassName: YwSmsCombo 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年5月23日 上午11:35:06 
*
 */
public class YwSmsCombo {

	private String combo_id;// 短信套餐编号
	private String combo_type;// 短信套餐类型
	private String combo_number;// 短信套餐条数（单位：千）
	private String combo_unitprice;// 短信套餐单价（单位：分 ）
	private String combo_remark;// 短信套餐备注
	private String combo_status;// 短信套餐状态
	public String getCombo_id() {
		return combo_id;
	}
	public void setCombo_id(String combo_id) {
		this.combo_id = combo_id;
	}
	public String getCombo_type() {
		return combo_type;
	}
	public void setCombo_type(String combo_type) {
		this.combo_type = combo_type;
	}
	public String getCombo_number() {
		return combo_number;
	}
	public void setCombo_number(String combo_number) {
		this.combo_number = combo_number;
	}
	public String getCombo_unitprice() {
		return combo_unitprice;
	}
	public void setCombo_unitprice(String combo_unitprice) {
		this.combo_unitprice = combo_unitprice;
	}
	public String getCombo_remark() {
		return combo_remark;
	}
	public void setCombo_remark(String combo_remark) {
		this.combo_remark = combo_remark;
	}
	public String getCombo_status() {
		return combo_status;
	}
	public void setCombo_status(String combo_status) {
		this.combo_status = combo_status;
	}
	
	
}
