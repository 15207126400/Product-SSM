package com.yunwei.context.sys.model;

import javax.validation.Valid;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

/**
 * 系统字典
 * @author zhangjh
 *
 */
public class YwDictionary {
	private String dic_key;// 字典项
	private String dic_value;// 字典值
	private String dic_subkey;// 字典子项
	private String dic_subvalue;// 字典子项值
	private String dic_remark;// 字典备注
	private int dic_status;// 字典状态
	public String getDic_key() {
		return dic_key;
	}
	public void setDic_key(String dic_key) {
		this.dic_key = dic_key;
	}
	public String getDic_value() {
		return dic_value;
	}
	public void setDic_value(String dic_value) {
		this.dic_value = dic_value;
	}
	public String getDic_subkey() {
		return dic_subkey;
	}
	public void setDic_subkey(String dic_subkey) {
		this.dic_subkey = dic_subkey;
	}
	public String getDic_subvalue() {
		return dic_subvalue;
	}
	public void setDic_subvalue(String dic_subvalue) {
		this.dic_subvalue = dic_subvalue;
	}
	public String getDic_remark() {
		return dic_remark;
	}
	public void setDic_remark(String dic_remark) {
		this.dic_remark = dic_remark;
	}
	public int getDic_status() {
		return dic_status;
	}
	public void setDic_status(int dic_status) {
		this.dic_status = dic_status;
	}
	
	
}
