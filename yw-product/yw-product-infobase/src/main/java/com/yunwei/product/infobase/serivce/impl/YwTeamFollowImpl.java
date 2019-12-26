package com.yunwei.product.infobase.serivce.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.common.dao.YwTeamFollowDao;
import com.yunwei.product.common.model.YwTeamFollow;
import com.yunwei.product.infobase.service.YwTeamFollowService;

@Service
public class YwTeamFollowImpl extends IBaseServiceImpl<YwTeamFollow> implements YwTeamFollowService {
	
	@Autowired
	private YwTeamFollowDao ywTeamFollowDao;

	@Override
	protected IBaseDao<YwTeamFollow> getBaseDao() {
		return ywTeamFollowDao;
	}

	
}
