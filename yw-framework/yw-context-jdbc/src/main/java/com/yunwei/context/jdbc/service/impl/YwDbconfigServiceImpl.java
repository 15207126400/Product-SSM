package com.yunwei.context.jdbc.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.context.jdbc.dao.YwDbconfigDao;
import com.yunwei.context.jdbc.model.YwDbconfig;
import com.yunwei.context.jdbc.service.YwDbconfigService;

@Service
public class YwDbconfigServiceImpl extends IBaseServiceImpl<YwDbconfig> implements YwDbconfigService {
	
	@Autowired
	private YwDbconfigDao ywDbconfigDao;

	@Override
	protected IBaseDao<YwDbconfig> getBaseDao() {
		return ywDbconfigDao;
	}

	
}
