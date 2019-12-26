package com.yunwei.product.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.backend.service.YwOrderOfflineService;
import com.yunwei.product.common.dao.YwOrderOfflineDao;
import com.yunwei.product.common.model.YwOrderOffline;


@Service
public class YwOrderOfflineServiceImpl extends IBaseServiceImpl<YwOrderOffline> implements YwOrderOfflineService{
	
    private static Logger logger = Logger.getLogger(YwOrderOfflineServiceImpl.class);
	@Autowired
	private YwOrderOfflineDao ywOrderOfflineDao;

	@Override
	protected IBaseDao<YwOrderOffline> getBaseDao() {
		return ywOrderOfflineDao;
	}
	
	
	
	
}
