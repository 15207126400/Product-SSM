package com.yunwei.product.common.backend.model.dto;

import com.yunwei.product.common.model.YwOrderPayment;

public class YwOrderPaymentDto extends YwOrderPayment{
	private String openid;
	private String nickname;
	
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
}
