package com.yunwei.product.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.backend.service.QhzSmsConfigService;
import com.yunwei.product.common.dao.QhzSmsConfigDao;
import com.yunwei.product.common.model.QhzSmsConfig;


@Service
public class QhzSmsConfigServiceImpl extends IBaseServiceImpl<QhzSmsConfig> implements QhzSmsConfigService{
	
    private static Logger logger = Logger.getLogger(QhzSmsConfigServiceImpl.class);
	@Autowired
	private QhzSmsConfigDao qhzSmsConfigDao;

	@Override
	protected IBaseDao<QhzSmsConfig> getBaseDao() {
		return qhzSmsConfigDao;
	}
	
	@Override
	protected String getPrimaryKey() {
	
		return "id";
	}
	
	
	
	
}
