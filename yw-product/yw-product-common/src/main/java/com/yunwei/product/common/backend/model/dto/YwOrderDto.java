package com.yunwei.product.common.backend.model.dto;

import java.util.ArrayList;
import java.util.List;

import com.yunwei.product.common.model.YwOrder;
import com.yunwei.product.common.model.YwOrderItem;

public class YwOrderDto extends YwOrder{
	private String openid;
	private String nickname;
	private String share_openid;
	private String share_nickname;
	private String[] productList;		// 商品字符串数组
	private List<YwOrderItem> YwOrderItemList = new ArrayList<YwOrderItem>();		// 商品规格集合
	
	public String[] getProductList() {
		return productList;
	}
	public void setProductList(String[] productList) {
		this.productList = productList;
	}
	public List<YwOrderItem> getYwOrderItemList() {
		return YwOrderItemList;
	}
	public void setYwOrderItemList(List<YwOrderItem> ywOrderItemList) {
		YwOrderItemList = ywOrderItemList;
	}
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
	public String getShare_openid() {
		return share_openid;
	}
	public void setShare_openid(String share_openid) {
		this.share_openid = share_openid;
	}
	public String getShare_nickname() {
		return share_nickname;
	}
	public void setShare_nickname(String share_nickname) {
		this.share_nickname = share_nickname;
	}
	
	
}
