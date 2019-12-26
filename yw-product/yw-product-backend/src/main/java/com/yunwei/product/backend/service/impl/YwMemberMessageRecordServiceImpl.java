package com.yunwei.product.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.backend.service.YwMemberMessageRecordService;
import com.yunwei.product.common.dao.YwMemberMessageRecordDao;
import com.yunwei.product.common.model.YwMemberMessageRecord;


@Service
public class YwMemberMessageRecordServiceImpl extends IBaseServiceImpl<YwMemberMessageRecord> implements YwMemberMessageRecordService{
	
    private static Logger logger = Logger.getLogger(YwMemberMessageRecordServiceImpl.class);
	@Autowired
	private YwMemberMessageRecordDao ywMemberMessageRecordDao;

	@Override
	protected IBaseDao<YwMemberMessageRecord> getBaseDao() {
		return ywMemberMessageRecordDao;
	}
	
	@Override
	protected String getPrimaryKey() {
	
		return "id";
	}
	
	
	
	
}
