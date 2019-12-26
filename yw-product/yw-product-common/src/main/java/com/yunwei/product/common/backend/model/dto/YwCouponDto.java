package com.yunwei.product.common.backend.model.dto;

import com.yunwei.product.common.model.YwCoupon;

public class YwCouponDto extends YwCoupon{
	private String classify_id;
	private String classify_name;
	
	public String getClassify_id() {
		return classify_id;
	}
	public void setClassify_id(String classify_id) {
		this.classify_id = classify_id;
	}
	public String getClassify_name() {
		return classify_name;
	}
	public void setClassify_name(String classify_name) {
		this.classify_name = classify_name;
	}
	
	
}
