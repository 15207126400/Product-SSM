package com.yunwei.product.common.model;
/**
 * 
* @ClassName: ywDistributorRule 
* @Description: 商家设置分销规则的信息表
* @author 晏飞
* @date 2018年3月15日 下午3:13:17 
*
 */
public class YwDistributorRule {
	 private String rule_id  			;		//'编号',
	 private String rule_level_type		;		//'分销等级',
	 private String rule_scale_one  	;		//'一级分销比例',
	 private String rule_scale_two  	;		//'二级分销比例',
	 private String rule_scale_three 	;		//'三级分销比例',
	 private String rule_door_type  	;		//'门槛类型',
	 private String rule_door_price  	;		//'门槛条件',
	 private String rule_pay_type    	;		//'付款类型',
	 private String rule_discounts   	;		//'优惠是否产生佣金',
	 private String rule_cash_count  	;		//'每月可提现次数',
	 private String rule_cash_money  	;		//'达到多少金额才能提现',
	 private String rule_levellock_day   ;		//'关系锁定天数',
	 private String rule_QRcode_url   	;		//'推广二维码',
	public String getRule_id() {
		return rule_id;
	}
	public void setRule_id(String rule_id) {
		this.rule_id = rule_id;
	}
	public String getRule_level_type() {
		return rule_level_type;
	}
	public void setRule_level_type(String rule_level_type) {
		this.rule_level_type = rule_level_type;
	}
	public String getRule_scale_one() {
		return rule_scale_one;
	}
	public void setRule_scale_one(String rule_scale_one) {
		this.rule_scale_one = rule_scale_one;
	}
	public String getRule_scale_two() {
		return rule_scale_two;
	}
	public void setRule_scale_two(String rule_scale_two) {
		this.rule_scale_two = rule_scale_two;
	}
	public String getRule_scale_three() {
		return rule_scale_three;
	}
	public void setRule_scale_three(String rule_scale_three) {
		this.rule_scale_three = rule_scale_three;
	}
	public String getRule_door_type() {
		return rule_door_type;
	}
	public void setRule_door_type(String rule_door_type) {
		this.rule_door_type = rule_door_type;
	}
	public String getRule_door_price() {
		return rule_door_price;
	}
	public void setRule_door_price(String rule_door_price) {
		this.rule_door_price = rule_door_price;
	}
	public String getRule_pay_type() {
		return rule_pay_type;
	}
	public void setRule_pay_type(String rule_pay_type) {
		this.rule_pay_type = rule_pay_type;
	}
	public String getRule_discounts() {
		return rule_discounts;
	}
	public void setRule_discounts(String rule_discounts) {
		this.rule_discounts = rule_discounts;
	}
	public String getRule_cash_count() {
		return rule_cash_count;
	}
	public void setRule_cash_count(String rule_cash_count) {
		this.rule_cash_count = rule_cash_count;
	}
	public String getRule_cash_money() {
		return rule_cash_money;
	}
	public void setRule_cash_money(String rule_cash_money) {
		this.rule_cash_money = rule_cash_money;
	}
	public String getRule_levellock_day() {
		return rule_levellock_day;
	}
	public void setRule_levellock_day(String rule_levellock_day) {
		this.rule_levellock_day = rule_levellock_day;
	}
	public String getRule_QRcode_url() {
		return rule_QRcode_url;
	}
	public void setRule_QRcode_url(String rule_QRcode_url) {
		this.rule_QRcode_url = rule_QRcode_url;
	}
	 
	 
}
