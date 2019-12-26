package com.yunwei.context.sys.model;
/**
 * 
* @ClassName: YwRole 
* @Description: 用户角色表
* @author 晏飞
* @date 2018年3月9日 下午4:12:23 
*
 */
public class YwRole {
	private String ro_id;			//角色编号
	private String ro_name;		//角色名称
	private String ro_status;	//角色状态
	private String ro_remark;	//角色备注
	private String mu_ids;		//菜单字符串
	private String mu_names;	//角色名称
	public String getRo_id() {
		return ro_id;
	}
	public void setRo_id(String ro_id) {
		this.ro_id = ro_id;
	}
	public String getRo_name() {
		return ro_name;
	}
	public void setRo_name(String ro_name) {
		this.ro_name = ro_name;
	}
	public String getRo_status() {
		return ro_status;
	}
	public void setRo_status(String ro_status) {
		this.ro_status = ro_status;
	}
	public String getRo_remark() {
		return ro_remark;
	}
	public void setRo_remark(String ro_remark) {
		this.ro_remark = ro_remark;
	}
	public String getMu_ids() {
		return mu_ids;
	}
	public void setMu_ids(String mu_ids) {
		this.mu_ids = mu_ids;
	}
	public String getMu_names() {
		return mu_names;
	}
	public void setMu_names(String mu_names) {
		this.mu_names = mu_names;
	}
	
	
}
