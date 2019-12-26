package com.yunwei.product.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.backend.service.YwXcxNavigationService;
import com.yunwei.product.common.dao.YwXcxNavigationDao;
import com.yunwei.product.common.model.YwXcxNavigation;


@Service
public class YwXcxNavigationServiceImpl extends IBaseServiceImpl<YwXcxNavigation> implements YwXcxNavigationService{
	
    private static Logger logger = Logger.getLogger(YwXcxNavigationServiceImpl.class);
	@Autowired
	private YwXcxNavigationDao ywXcxNavigationDao;

	@Override
	protected IBaseDao<YwXcxNavigation> getBaseDao() {
		return ywXcxNavigationDao;
	}
	
	
	
	
}
