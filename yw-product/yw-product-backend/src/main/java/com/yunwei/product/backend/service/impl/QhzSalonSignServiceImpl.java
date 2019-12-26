package com.yunwei.product.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.backend.service.QhzSalonSignService;
import com.yunwei.product.common.dao.QhzSalonSignDao;
import com.yunwei.product.common.model.QhzSalonSign;


@Service
public class QhzSalonSignServiceImpl extends IBaseServiceImpl<QhzSalonSign> implements QhzSalonSignService{
	
    private static Logger logger = Logger.getLogger(QhzSalonSignServiceImpl.class);
	@Autowired
	private QhzSalonSignDao qhzSalonSignDao;

	@Override
	protected IBaseDao<QhzSalonSign> getBaseDao() {
		return qhzSalonSignDao;
	}
	
	@Override
	protected String getPrimaryKey() {
	
		return "id";
	}
	
	
	
	
}
