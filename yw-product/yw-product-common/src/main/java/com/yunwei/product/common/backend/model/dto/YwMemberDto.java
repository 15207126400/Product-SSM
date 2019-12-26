package com.yunwei.product.common.backend.model.dto;

import com.yunwei.product.common.model.YwMember;

public class YwMemberDto{
	private String id;
	private String levelname;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLevelname() {
		return levelname;
	}
	public void setLevelname(String levelname) {
		this.levelname = levelname;
	}
}
