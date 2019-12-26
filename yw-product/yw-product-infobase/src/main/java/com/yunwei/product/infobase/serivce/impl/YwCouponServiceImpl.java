package com.yunwei.product.infobase.serivce.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.common.util.DateUtil;
import com.yunwei.product.common.backend.model.dto.YwCouponDto;
import com.yunwei.product.common.dao.YwCouponDao;
import com.yunwei.product.common.model.YwCoupon;
import com.yunwei.product.common.model.YwCouponCollectiondetails;
import com.yunwei.product.infobase.service.YwCouponService;
@Service
public class YwCouponServiceImpl extends IBaseServiceImpl<YwCoupon> implements YwCouponService {

	@Autowired
	private YwCouponDao ywCouponDao;
	
	@Override
	protected IBaseDao<YwCoupon> getBaseDao() {
		
		return ywCouponDao;
	}


	
}
