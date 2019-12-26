package com.yunwei.product.common.backend.model.dto;

import com.yunwei.product.common.model.YwTeamActivity;
import com.yunwei.product.common.model.YwTeamFollow;

/**
 * 
* @ClassName: YwTeamActivity 
* @Description: 拼团活动配置表 
* @author zhangjh
* @date 2018年3月20日 下午4:26:12 
*
 */
public class YwTeamFollowDto extends YwTeamFollow{

	private String openid;
	private String found_user_nickname;
	private String found_user_avatarUrl;
	
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getFound_user_nickname() {
		return found_user_nickname;
	}
	public void setFound_user_nickname(String found_user_nickname) {
		this.found_user_nickname = found_user_nickname;
	}
	public String getFound_user_avatarUrl() {
		return found_user_avatarUrl;
	}
	public void setFound_user_avatarUrl(String found_user_avatarUrl) {
		this.found_user_avatarUrl = found_user_avatarUrl;
	}
	
}
