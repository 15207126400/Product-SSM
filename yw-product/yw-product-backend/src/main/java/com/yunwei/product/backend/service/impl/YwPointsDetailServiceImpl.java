package com.yunwei.product.backend.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import com.yunwei.product.backend.service.YwPointsDetailService;
import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.common.backend.model.dto.YwPointsDetailDto;
import com.yunwei.product.common.dao.YwPointsDetailDao;
import com.yunwei.product.common.model.YwPointsDetail;


@Service
public class YwPointsDetailServiceImpl extends IBaseServiceImpl<YwPointsDetail> implements YwPointsDetailService{
	
    private static Logger logger = Logger.getLogger(YwPointsDetailServiceImpl.class);
	@Autowired
	private YwPointsDetailDao ywPointsDetailDao;

	@Override
	protected IBaseDao<YwPointsDetail> getBaseDao() {
		return ywPointsDetailDao;
	}

	@Override
	public List<YwPointsDetailDto> queryUnionMemberList(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return ywPointsDetailDao.queryUnionMemberList(paramMap);
	}
	
	
	
	
}
