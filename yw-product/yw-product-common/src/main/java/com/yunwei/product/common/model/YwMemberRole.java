package com.yunwei.product.common.model;
/**
 * 
* @ClassName: ShopUserRole 
* @Description: 会员权限实体类 
* @author 晏飞
* @date 2018年1月24日 下午1:45:12 
*
 */
public class YwMemberRole {
	private String id;
	private String level;
	private String levelname;
	private String levelpoints;
	private String discount;
	private String proportion;
	private String iflevel;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getLevelname() {
		return levelname;
	}
	public void setLevelname(String levelname) {
		this.levelname = levelname;
	}
	public String getLevelpoints() {
		return levelpoints;
	}
	public void setLevelpoints(String levelpoints) {
		this.levelpoints = levelpoints;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getIflevel() {
		return iflevel;
	}
	public void setIflevel(String iflevel) {
		this.iflevel = iflevel;
	}
	public String getProportion() {
		return proportion;
	}
	public void setProportion(String proportion) {
		this.proportion = proportion;
	}
}
