package com.yunwei.product.common.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.util.DateUtil;

/**
 * 基本配置信息
* @ClassName: YwBaseconfig
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年09月25日 下午15:56:52
*
 */
public class YwBaseconfig {

   private String id; // 主键
   private String config_id; // 配置编号
   private String config_name; // 配置名称
   private String config_type; // 配置类型（1、text,2、radio,3、checkbox,4、select,默认为1）
   private String config_content; // 配置内容（json字符串）
   private String config_value; // 配置值
   private String config_default_value; // 配置默认值
   private String config_remark; // 配置备注
   private String config_parse; // 配置解析
   
   private List<YwBaseConfigContent> ywBaseConfigContents = new ArrayList<YwBaseConfigContent>();

	public String getId() {
	    return this.id;
	}
	
	public void setId(String id) {
	    this.id = id;
	}
    public String getConfig_id() {
	    return this.config_id;
	}
	
	public void setConfig_id(String config_id) {
	    this.config_id = config_id;
	}
    public String getConfig_name() {
	    return this.config_name;
	}
	
	public void setConfig_name(String config_name) {
	    this.config_name = config_name;
	}
    public String getConfig_type() {
	    return this.config_type;
	}
	
	public void setConfig_type(String config_type) {
	    this.config_type = config_type;
	}
    public String getConfig_content() {
	    return this.config_content;
	}
	
	public void setConfig_content(String config_content) {
	    this.config_content = config_content;
	}
    public String getConfig_value() {
	    return this.config_value;
	}
	
	public void setConfig_value(String config_value) {
	    this.config_value = config_value;
	}
    public String getConfig_default_value() {
	    return this.config_default_value;
	}
	
	public void setConfig_default_value(String config_default_value) {
	    this.config_default_value = config_default_value;
	}
    public String getConfig_remark() {
	    return this.config_remark;
	}
	
	public void setConfig_remark(String config_remark) {
	    this.config_remark = config_remark;
	}
    public String getConfig_parse() {
	    return this.config_parse;
	}
	
	public void setConfig_parse(String config_parse) {
	    this.config_parse = config_parse;
	}

	public List<YwBaseConfigContent> getYwBaseConfigContents() {
		return ywBaseConfigContents;
	}

	public void setYwBaseConfigContents(
			List<YwBaseConfigContent> ywBaseConfigContents) {
		this.ywBaseConfigContents = ywBaseConfigContents;
	}

	
}
