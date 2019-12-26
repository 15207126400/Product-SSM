package com.yunwei.product.infobase.serivce.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.common.backend.model.dto.YwCommissionrecordDto;
import com.yunwei.product.common.dao.YwDistributorCommissionrecordDao;
import com.yunwei.product.common.model.YwDistributorCommissionrecord;
import com.yunwei.product.infobase.service.YwDistributorCommissionrecordService;

@Service
public class YwDistributorCommissionrecordImpl extends IBaseServiceImpl<YwDistributorCommissionrecord> implements YwDistributorCommissionrecordService {
	
	@Autowired
	private YwDistributorCommissionrecordDao ywDistributorCommissionrecordDao;


	@Override
	protected IBaseDao<YwDistributorCommissionrecord> getBaseDao() {
		return ywDistributorCommissionrecordDao;
	}
	
	@Override
	public List<YwCommissionrecordDto> queryUnionMemberList(Map<String, Object> paramMap) {
		return ywDistributorCommissionrecordDao.queryUnionMemberList(paramMap);
	}





}
