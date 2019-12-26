package com.yunwei.product.backend.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.backend.service.YwTeamFollowService;
import com.yunwei.product.common.dao.YwTeamFollowDao;
import com.yunwei.product.common.model.YwTeamFollow;

@Service
public class YwTeamFollowImpl extends IBaseServiceImpl<YwTeamFollow> implements YwTeamFollowService {
	
	@Autowired
	private YwTeamFollowDao ywTeamFollowDao;

	@Override
	protected IBaseDao<YwTeamFollow> getBaseDao() {
		return ywTeamFollowDao;
	}

	
}
