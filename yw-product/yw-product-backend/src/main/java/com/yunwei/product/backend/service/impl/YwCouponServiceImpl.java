package com.yunwei.product.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.backend.service.YwCouponService;
import com.yunwei.product.common.dao.YwCouponDao;
import com.yunwei.product.common.model.YwCoupon;


@Service
public class YwCouponServiceImpl extends IBaseServiceImpl<YwCoupon> implements YwCouponService{
	
    private static Logger logger = Logger.getLogger(YwCouponServiceImpl.class);
	@Autowired
	private YwCouponDao ywCouponDao;

	@Override
	protected IBaseDao<YwCoupon> getBaseDao() {
		return ywCouponDao;
	}
	
	@Override
	protected String getPrimaryKey() {
	
		return "id";
	}
	
	
	
	
}
