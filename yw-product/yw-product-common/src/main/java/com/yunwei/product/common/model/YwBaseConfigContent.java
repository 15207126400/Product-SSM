package com.yunwei.product.common.model;

/**
 * 基本配置内容
* @ClassName: YwBaseConfigContent 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年9月26日 下午5:00:18 
*
 */
public class YwBaseConfigContent {

	private String value;// 不显示值
	private String option;// 显示值
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getOption() {
		return option;
	}
	public void setOption(String option) {
		this.option = option;
	}
	
	
}
