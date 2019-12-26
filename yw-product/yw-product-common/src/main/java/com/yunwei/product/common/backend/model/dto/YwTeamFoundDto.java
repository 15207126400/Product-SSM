package com.yunwei.product.common.backend.model.dto;

import java.util.ArrayList;
import java.util.List;

import com.yunwei.product.common.model.YwTeamFollow;
import com.yunwei.product.common.model.YwTeamFound;

public class YwTeamFoundDto extends YwTeamFound{

	private List<YwTeamFollow> teamFollows = new ArrayList<YwTeamFollow>();

	public List<YwTeamFollow> getTeamFollows() {
		return teamFollows;
	}

	public void setTeamFollows(List<YwTeamFollow> teamFollows) {
		this.teamFollows = teamFollows;
	}
	
	
}
