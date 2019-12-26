package com.yunwei.product.infobase.serivce.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.common.dao.YwBaseconfigDao;
import com.yunwei.product.common.model.YwBaseconfig;
import com.yunwei.product.infobase.service.YwBaseconfigService;


@Service
public class YwBaseconfigServiceImpl extends IBaseServiceImpl<YwBaseconfig> implements YwBaseconfigService{
	
    private static Logger logger = Logger.getLogger(YwBaseconfigServiceImpl.class);
	@Autowired
	private YwBaseconfigDao ywBaseconfigDao;

	@Override
	protected IBaseDao<YwBaseconfig> getBaseDao() {
		return ywBaseconfigDao;
	}
	
	
	
	
}
