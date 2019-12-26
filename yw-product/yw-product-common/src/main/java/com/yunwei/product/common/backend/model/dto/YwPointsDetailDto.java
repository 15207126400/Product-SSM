package com.yunwei.product.common.backend.model.dto;

import com.yunwei.common.annotation.Base64Decode;
import com.yunwei.product.common.model.YwPointsDetail;

public class YwPointsDetailDto extends YwPointsDetail{
	@Base64Decode
	private String nickname;

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	
}
