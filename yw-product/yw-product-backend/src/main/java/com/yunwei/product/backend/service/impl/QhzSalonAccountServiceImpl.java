package com.yunwei.product.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.backend.service.QhzSalonAccountService;
import com.yunwei.product.common.dao.QhzSalonAccountDao;
import com.yunwei.product.common.model.QhzSalonAccount;


@Service
public class QhzSalonAccountServiceImpl extends IBaseServiceImpl<QhzSalonAccount> implements QhzSalonAccountService{
	
    private static Logger logger = Logger.getLogger(QhzSalonAccountServiceImpl.class);
	@Autowired
	private QhzSalonAccountDao qhzSalonAccountDao;

	@Override
	protected IBaseDao<QhzSalonAccount> getBaseDao() {
		return qhzSalonAccountDao;
	}
	
	@Override
	protected String getPrimaryKey() {
	
		return "id";
	}
	
	
	
	
}
