package com.yunwei.product.backend.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import com.yunwei.product.backend.service.YwPointsExchangeService;
import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.common.backend.model.dto.YwPointsExchangeDto;
import com.yunwei.product.common.dao.YwPointsExchangeDao;
import com.yunwei.product.common.model.YwPointsExchange;


@Service
public class YwPointsExchangeServiceImpl extends IBaseServiceImpl<YwPointsExchange> implements YwPointsExchangeService{
	
    private static Logger logger = Logger.getLogger(YwPointsExchangeServiceImpl.class);
	@Autowired
	private YwPointsExchangeDao ywPointsExchangeDao;

	@Override
	protected IBaseDao<YwPointsExchange> getBaseDao() {
		return ywPointsExchangeDao;
	}

	@Override
	public List<YwPointsExchangeDto> queryUnionMemberList(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return ywPointsExchangeDao.queryUnionMemberList(paramMap);
	}
	
	
	
	
}
