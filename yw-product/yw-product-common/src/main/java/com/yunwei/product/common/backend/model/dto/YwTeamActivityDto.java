package com.yunwei.product.common.backend.model.dto;

import com.yunwei.product.common.model.YwTeamActivity;

/**
 * 
* @ClassName: YwTeamActivity 
* @Description: 拼团活动配置表 
* @author zhangjh
* @date 2018年3月20日 下午4:26:12 
*
 */
public class YwTeamActivityDto extends YwTeamActivity{

	private String price;// 商品价格
	private String url;// 商品图片
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
}
