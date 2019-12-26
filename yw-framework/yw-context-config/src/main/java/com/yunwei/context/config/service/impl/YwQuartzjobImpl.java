package com.yunwei.context.config.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.context.config.dao.YwQuartzjobDao;
import com.yunwei.context.config.model.YwQuartzjob;
import com.yunwei.context.config.service.YwQuartzjobService;


@Service
public class YwQuartzjobImpl extends IBaseServiceImpl<YwQuartzjob> implements YwQuartzjobService{
	

	@Autowired
	private YwQuartzjobDao ywQuartzjobDao;

	@Override
	protected IBaseDao<YwQuartzjob> getBaseDao() {
		return ywQuartzjobDao;
	}
	
	
	
	
}
