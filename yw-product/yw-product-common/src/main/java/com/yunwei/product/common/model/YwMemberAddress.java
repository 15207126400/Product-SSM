package com.yunwei.product.common.model;
/**
 * 
* @ClassName: ShopAddress 
* @Description: TODO(地址管理) 
* @author 晏飞
* @date 2017年11月13日 下午3:57:16 
*
 */
public class YwMemberAddress {
	private int id;
	private String tel;
	private String name;
	private String sex;
	private String address;		//省市区
	private String door;		//详细地址
	private String createBy;	//用户唯一编码
	private String defaultAddress;
	
	public String getDefaultAddress() {
		return defaultAddress;
	}
	public void setDefaultAddress(String defaultAddress) {
		this.defaultAddress = defaultAddress;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDoor() {
		return door;
	}
	public void setDoor(String door) {
		this.door = door;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	
	
}
