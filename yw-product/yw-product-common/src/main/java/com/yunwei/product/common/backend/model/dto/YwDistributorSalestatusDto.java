package com.yunwei.product.common.backend.model.dto;

import com.yunwei.product.common.model.YwDistributorSalestatus;

public class YwDistributorSalestatusDto extends YwDistributorSalestatus{
	private String y_openid;
	private String share_nickname;
	private String m_openid;
	private String buy_nickname;
	
	public String getY_openid() {
		return y_openid;
	}
	public void setY_openid(String y_openid) {
		this.y_openid = y_openid;
	}
	public String getShare_nickname() {
		return share_nickname;
	}
	public void setShare_nickname(String share_nickname) {
		this.share_nickname = share_nickname;
	}
	public String getM_openid() {
		return m_openid;
	}
	public void setM_openid(String m_openid) {
		this.m_openid = m_openid;
	}
	public String getBuy_nickname() {
		return buy_nickname;
	}
	public void setBuy_nickname(String buy_nickname) {
		this.buy_nickname = buy_nickname;
	}
	
	
}
