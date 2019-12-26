package com.yunwei.context.sys.usercenter.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import com.yunwei.context.sys.usercenter.dao.YwUserCustomerXcxDao;
import com.yunwei.context.sys.usercenter.model.YwUserCustomerXcx;
import com.yunwei.context.sys.usercenter.service.YwUserCustomerXcxService;
import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;


@Service
public class YwUserCustomerXcxServiceImpl extends IBaseServiceImpl<YwUserCustomerXcx> implements YwUserCustomerXcxService{
	
    private static Logger logger = Logger.getLogger(YwUserCustomerXcxServiceImpl.class);
	@Autowired
	private YwUserCustomerXcxDao ywUserCustomerXcxDao;

	@Override
	protected IBaseDao<YwUserCustomerXcx> getBaseDao() {
		return ywUserCustomerXcxDao;
	}
	
	
	
	
}
