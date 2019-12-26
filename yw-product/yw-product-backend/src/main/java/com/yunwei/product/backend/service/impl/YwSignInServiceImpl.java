package com.yunwei.product.backend.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import com.yunwei.product.backend.service.YwSignInService;
import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.common.backend.model.dto.YwSignInDto;
import com.yunwei.product.common.dao.YwSignInDao;
import com.yunwei.product.common.model.YwSignIn;


@Service
public class YwSignInServiceImpl extends IBaseServiceImpl<YwSignIn> implements YwSignInService{
	
    private static Logger logger = Logger.getLogger(YwSignInServiceImpl.class);
	@Autowired
	private YwSignInDao ywSignInDao;

	@Override
	protected IBaseDao<YwSignIn> getBaseDao() {
		return ywSignInDao;
	}

	@Override
	public List<YwSignInDto> queryUnionMemberList(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return ywSignInDao.queryUnionMemberList(paramMap);
	}
	
	
	
	
}
