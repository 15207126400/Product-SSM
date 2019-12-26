package com.yunwei.product.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.backend.service.YwMemberMessageService;
import com.yunwei.product.common.dao.YwMemberMessageDao;
import com.yunwei.product.common.model.YwMemberMessage;


@Service
public class YwMemberMessageServiceImpl extends IBaseServiceImpl<YwMemberMessage> implements YwMemberMessageService{
	
    private static Logger logger = Logger.getLogger(YwMemberMessageServiceImpl.class);
	@Autowired
	private YwMemberMessageDao ywMemberMessageDao;

	@Override
	protected IBaseDao<YwMemberMessage> getBaseDao() {
		return ywMemberMessageDao;
	}
	
	@Override
	protected String getPrimaryKey() {
	
		return "id";
	}
	
	
	
	
}
