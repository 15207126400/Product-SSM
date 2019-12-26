package com.yunwei.product.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.backend.service.QhzSalonOrderItemService;
import com.yunwei.product.common.dao.QhzSalonOrderItemDao;
import com.yunwei.product.common.model.QhzSalonOrderItem;


@Service
public class QhzSalonOrderItemServiceImpl extends IBaseServiceImpl<QhzSalonOrderItem> implements QhzSalonOrderItemService{
	
    private static Logger logger = Logger.getLogger(QhzSalonOrderItemServiceImpl.class);
	@Autowired
	private QhzSalonOrderItemDao qhzSalonOrderItemDao;

	@Override
	protected IBaseDao<QhzSalonOrderItem> getBaseDao() {
		return qhzSalonOrderItemDao;
	}
	
	@Override
	protected String getPrimaryKey() {
	
		return "id";
	}
	
	
	
	
}
