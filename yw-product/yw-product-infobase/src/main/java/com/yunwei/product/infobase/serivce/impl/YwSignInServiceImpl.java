package com.yunwei.product.infobase.serivce.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.log4j.Logger;
import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.common.dao.YwSignInDao;
import com.yunwei.product.infobase.service.YwSignInService;
import com.yunwei.product.common.model.YwSignIn;

@Service
public class YwSignInServiceImpl extends IBaseServiceImpl<YwSignIn> implements YwSignInService {
	private static Logger logger = Logger.getLogger(YwSignInServiceImpl.class);
	@Autowired
	private YwSignInDao ywSignInDao;

	@Override
	protected IBaseDao<YwSignIn> getBaseDao() {
		return ywSignInDao;
	}
	
	

}

