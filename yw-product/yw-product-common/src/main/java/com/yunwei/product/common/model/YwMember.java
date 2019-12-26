package com.yunwei.product.common.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.annotation.Base64Decode;
import com.yunwei.common.util.DateUtil;
import com.yunwei.product.common.backend.model.dto.YwMemberDto;


public class YwMember extends YwMemberDto implements Serializable{
	private String id;
	private String openid;
	private String sessionKey;
	private Date createTime;
	private String ac_price;
	@Base64Decode
	private String nickname;
	private String avatarUrl;
	private String level;
	private float discount;
	private String realname;
	private String phone;
	private String address;
	private String is_auditing;
	private String shopname;
	
	/** 店铺和昵称组合字段 **/
	private String shopnickname;
	
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getSessionKey() {
		return sessionKey;
	}
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}
	public float getDiscount() {
		return discount;
	}
	public void setDiscount(float d) {
		this.discount = d;
	}
	@JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getAvatarUrl() {
		return avatarUrl;
	}
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	public String getAc_price() {
		return ac_price;
	}
	public void setAc_price(String ac_price) {
		this.ac_price = ac_price;
	}
	public String getIs_auditing() {
		return is_auditing;
	}
	public void setIs_auditing(String is_auditing) {
		this.is_auditing = is_auditing;
	}
	public String getShopname() {
		return shopname;
	}
	public void setShopname(String shopname) {
		this.shopname = shopname;
	}
	public String getShopnickname() {
		StringBuffer buffer = new StringBuffer();
		if(StringUtils.isNotEmpty(this.getShopname())){
			buffer.append("(");
			buffer.append(this.getShopname());
			buffer.append(")");
		} 
		buffer.append(this.getNickname());
		return buffer.toString();
	}
	public void setShopnickname(String shopnickname) {
		this.shopnickname = shopnickname;
	}
	
}
