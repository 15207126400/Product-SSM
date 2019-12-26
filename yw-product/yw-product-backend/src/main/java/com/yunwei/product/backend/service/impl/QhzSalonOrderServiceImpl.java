package com.yunwei.product.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.backend.service.QhzSalonOrderService;
import com.yunwei.product.common.dao.QhzSalonOrderDao;
import com.yunwei.product.common.model.QhzSalonOrder;


@Service
public class QhzSalonOrderServiceImpl extends IBaseServiceImpl<QhzSalonOrder> implements QhzSalonOrderService{
	
    private static Logger logger = Logger.getLogger(QhzSalonOrderServiceImpl.class);
	@Autowired
	private QhzSalonOrderDao qhzSalonOrderDao;

	@Override
	protected IBaseDao<QhzSalonOrder> getBaseDao() {
		return qhzSalonOrderDao;
	}
	
	@Override
	protected String getPrimaryKey() {
	
		return "id";
	}
	
	
	
	
}
