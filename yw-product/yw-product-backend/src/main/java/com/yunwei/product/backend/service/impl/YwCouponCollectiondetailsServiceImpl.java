package com.yunwei.product.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.backend.service.YwCouponCollectiondetailsService;
import com.yunwei.product.common.dao.YwCouponCollectiondetailsDao;
import com.yunwei.product.common.model.YwCouponCollectiondetails;


@Service
public class YwCouponCollectiondetailsServiceImpl extends IBaseServiceImpl<YwCouponCollectiondetails> implements YwCouponCollectiondetailsService{
	
    private static Logger logger = Logger.getLogger(YwCouponCollectiondetailsServiceImpl.class);
	@Autowired
	private YwCouponCollectiondetailsDao ywCouponCollectiondetailsDao;

	@Override
	protected IBaseDao<YwCouponCollectiondetails> getBaseDao() {
		return ywCouponCollectiondetailsDao;
	}
	
	@Override
	protected String getPrimaryKey() {
	
		return "id";
	}
	
	
	
	
}
