package com.yunwei.product.common.backend.model.dto;

import com.yunwei.common.annotation.Base64Decode;
import com.yunwei.product.common.model.YwDistributorWithdrawalrecord;

public class YwDistributorWithdrawalrecordDto extends YwDistributorWithdrawalrecord{
	private String openid;
	@Base64Decode
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
