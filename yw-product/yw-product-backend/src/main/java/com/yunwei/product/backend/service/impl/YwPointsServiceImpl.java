package com.yunwei.product.backend.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import com.yunwei.product.backend.service.YwPointsService;
import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.common.backend.model.dto.YwPointsDto;
import com.yunwei.product.common.dao.YwPointsDao;
import com.yunwei.product.common.model.YwPoints;


@Service
public class YwPointsServiceImpl extends IBaseServiceImpl<YwPoints> implements YwPointsService{
	
    private static Logger logger = Logger.getLogger(YwPointsServiceImpl.class);
	@Autowired
	private YwPointsDao ywPointsDao;

	@Override
	protected IBaseDao<YwPoints> getBaseDao() {
		return ywPointsDao;
	}

	@Override
	public List<YwPointsDto> queryUnionMemberList(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return ywPointsDao.queryUnionMemberList(paramMap);
	}
	
	
	
	
}
