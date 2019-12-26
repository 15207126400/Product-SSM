package com.yunwei.product.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.log4j.Logger;
import com.yunwei.product.backend.service.YwProductImageService;
import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.common.dao.YwProductImageDao;
import com.yunwei.product.common.model.YwProductImage;


@Service
public class YwProductImageServiceImpl extends IBaseServiceImpl<YwProductImage> implements YwProductImageService{
	
    private static Logger logger = Logger.getLogger(YwProductImageServiceImpl.class);
	@Autowired
	private YwProductImageDao ywProductImageDao;

	@Override
	protected IBaseDao<YwProductImage> getBaseDao() {
		return ywProductImageDao;
	}
	
	
	
	
}
