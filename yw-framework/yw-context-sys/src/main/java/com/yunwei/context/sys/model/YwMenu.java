package com.yunwei.context.sys.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
* @ClassName: YwMenu 
* @Description: 用户菜单表 
* @author 晏飞
* @date 2018年3月9日 下午4:08:41 
*
 */
public class YwMenu {
	private String mu_id;				//菜单编号
	private String mu_name;			//菜单名称
	private String mu_parentid ;    //父菜单编号
	private String mu_url ;			//菜单url
	private String mu_level;        // 菜单级别
	private String mu_issub_node;   // 是否有子节点
	private String mu_sort;			//菜单排序
	private String mu_remark;		//菜单备注
	private String mu_icon;         //菜单图片名称
	private List<YwMenu> child_menus = new ArrayList<YwMenu>();; // 菜单集合
	public String getMu_id() {
		return mu_id;
	}
	public void setMu_id(String mu_id) {
		this.mu_id = mu_id;
	}
	public String getMu_name() {
		return mu_name;
	}
	public void setMu_name(String mu_name) {
		this.mu_name = mu_name;
	}
	
	public String getMu_url() {
		return mu_url;
	}
	public void setMu_url(String mu_url) {
		this.mu_url = mu_url;
	}
	public String getMu_sort() {
		return mu_sort;
	}
	public void setMu_sort(String mu_sort) {
		this.mu_sort = mu_sort;
	}
	public String getMu_remark() {
		return mu_remark;
	}
	public void setMu_remark(String mu_remark) {
		this.mu_remark = mu_remark;
	}
	public String getMu_parentid() {
		return mu_parentid;
	}
	public void setMu_parentid(String mu_parentid) {
		this.mu_parentid = mu_parentid;
	}
	public String getMu_level() {
		return mu_level;
	}
	public void setMu_level(String mu_level) {
		this.mu_level = mu_level;
	}
	public String getMu_issub_node() {
		return mu_issub_node;
	}
	public void setMu_issub_node(String mu_issub_node) {
		this.mu_issub_node = mu_issub_node;
	}
	public List<YwMenu> getChild_menus() {
		return child_menus;
	}
	public void setChild_menus(List<YwMenu> child_menus) {
		this.child_menus = child_menus;
	}
	public String getMu_icon() {
		return mu_icon;
	}
	public void setMu_icon(String mu_icon) {
		this.mu_icon = mu_icon;
	}
	
	
	
}
