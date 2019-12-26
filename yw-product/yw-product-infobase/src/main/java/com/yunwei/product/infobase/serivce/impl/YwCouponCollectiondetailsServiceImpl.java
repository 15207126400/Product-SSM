package com.yunwei.product.infobase.serivce.impl;


import org.apache.commons.lang3.StringUtils;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.common.util.DateUtil;
import com.yunwei.common.exception.BizException;
import com.yunwei.product.common.dao.YwCouponCollectiondetailsDao;
import com.yunwei.product.common.model.YwCoupon;
import com.yunwei.product.common.model.YwCouponCollectiondetails;
import com.yunwei.product.infobase.service.YwCouponCollectiondetailsService;
import com.yunwei.product.infobase.service.YwCouponService;
import com.yunwei.product.infobase.service.YwMemberService;
@Service
public class YwCouponCollectiondetailsServiceImpl extends IBaseServiceImpl<YwCouponCollectiondetails> implements YwCouponCollectiondetailsService {
	@Autowired
	private YwCouponCollectiondetailsDao ywCouponCollectiondetailsDao;
	@Autowired
	private YwCouponService ywCouponService;
	@Autowired
	private YwMemberService ywMemberService;
	
	@Override
	protected IBaseDao<YwCouponCollectiondetails> getBaseDao() {
		
		return ywCouponCollectiondetailsDao;
	}

	@Override
	public int couponCollection(String openid, String coupon_id) {
		// 查询优惠券
		YwCoupon ywCoupon = ywCouponService.query(coupon_id);
		if(ywCoupon == null){
			throw new BizException("-1", "优惠券不存在");
		}
		
		YwCouponCollectiondetails collectiondetails = new YwCouponCollectiondetails();
		collectiondetails.setOpenid(openid);
		collectiondetails.setDetails_status("0");
		collectiondetails.setStarttime(new Date());
		collectiondetails.setCoupon_function_type(ywCoupon.getCoupon_function_type());
		collectiondetails.setCoupon_id(ywCoupon.getId());
		collectiondetails.setCoupon_price(ywCoupon.getCoupon_price());
		collectiondetails.setCoupon_use_condition(ywCoupon.getCoupon_use_condition());
		collectiondetails.setCoupon_use_condition_type(ywCoupon.getCoupon_use_condition_type());
		collectiondetails.setCoupon_use_scope(ywCoupon.getCoupon_use_scope());
		collectiondetails.setCoupon_use_scope_type(ywCoupon.getCoupon_use_scope_type());
		
		// 判断优惠券有效期类型
		String coupon_time_type = ywCoupon.getCoupon_time_type();
		if(StringUtils.equals(coupon_time_type,"0")){
			// 固定日期
			collectiondetails.setCoupon_starttime(ywCoupon.getCoupon_starttime());
			collectiondetails.setCoupon_endtime(ywCoupon.getCoupon_endtime());
		} else if(StringUtils.equals(coupon_time_type,"1")){
			// 当日开始有效
			collectiondetails.setCoupon_starttime(new Date());
			collectiondetails.setCoupon_endtime(DateUtil.addDay(new Date(), Integer.valueOf(ywCoupon.getCoupon_time_day())));
		} else if(StringUtils.equals(coupon_time_type,"2")){
			// 次日开始有效
			collectiondetails.setCoupon_starttime(DateUtil.addDay(new Date(), 1));
			collectiondetails.setCoupon_endtime(DateUtil.addDay(new Date(), Integer.valueOf(ywCoupon.getCoupon_time_day())));
		}
		super.insert(collectiondetails);
		return 0;
	}
	


}
