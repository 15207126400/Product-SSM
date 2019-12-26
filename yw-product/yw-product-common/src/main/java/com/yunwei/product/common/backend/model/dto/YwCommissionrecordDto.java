package com.yunwei.product.common.backend.model.dto;

import com.yunwei.common.annotation.Base64Decode;
import com.yunwei.product.common.model.YwDistributorCommissionrecord;

/**
 * 
* @ClassName: YwCommissionrecordDto 
* @Description: 分销佣金记录查询(联合查询佣金记录及分销商信息)
* @author 晏飞
* @date 2018年4月28日 下午2:33:15 
*
 */
public class YwCommissionrecordDto extends YwDistributorCommissionrecord{
	private String openid;
	@Base64Decode
	private String nickname;
	private String dis_id;
	private String dis_level;
	
	
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
	public String getDis_level() {
		return dis_level;
	}
	public void setDis_level(String dis_level) {
		this.dis_level = dis_level;
	}
	public String getDis_id() {
		return dis_id;
	}
	public void setDis_id(String dis_id) {
		this.dis_id = dis_id;
	}
	
	
}
