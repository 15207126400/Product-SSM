package com.yunwei.product.infobase.serivce.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.common.dao.YwWithdrawDao;
import com.yunwei.product.common.model.YwWithdraw;
import com.yunwei.product.infobase.service.YwWithdrawService;

@Service
public class YwWithdrawServiceImpl extends IBaseServiceImpl<YwWithdraw> implements YwWithdrawService {
	private static Logger logger = Logger.getLogger(YwWithdrawServiceImpl.class);
	@Autowired
	private YwWithdrawDao ywWithdrawDao;

	@Override
	protected IBaseDao<YwWithdraw> getBaseDao() {
		return ywWithdrawDao;
	}
	
	

}

