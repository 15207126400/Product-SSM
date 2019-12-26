package com.yunwei.product.infobase.service;

import java.util.Map;

import com.yunwei.common.base.serivce.IBaseSerivce;
import com.yunwei.product.common.model.YwCoupon;
import com.yunwei.product.common.model.YwCouponCollectiondetails;



/**
 * 优惠券领取信息
* @ClassName: YwCouponCollectiondetailsService 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2019年1月18日 上午11:16:06 
*
 */
public interface YwCouponCollectiondetailsService extends IBaseSerivce<YwCouponCollectiondetails>{

	/**
	 * 优惠券领取
	 * @param openid
	 * @param coupon_id
	 * @return
	 */
	public int couponCollection(String openid, String coupon_id);
}
