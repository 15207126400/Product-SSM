package com.yunwei.product.infobase.serivce.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.common.backend.model.dto.YwDistributorSalestatusDto;
import com.yunwei.product.common.dao.YwDistributorSalestatusDao;
import com.yunwei.product.common.model.YwDistributorSalestatus;
import com.yunwei.product.infobase.service.YwDistributorSalestatusService;

@Service
public class YwDistributorSalestatusImpl extends IBaseServiceImpl<YwDistributorSalestatus> implements YwDistributorSalestatusService {
	
	@Autowired
	private YwDistributorSalestatusDao ywDistributorSalestatusDao;

	@Override
	protected IBaseDao<YwDistributorSalestatus> getBaseDao() {
		return ywDistributorSalestatusDao;
	}

	@Override
	public List<YwDistributorSalestatusDto> queryUnionMemberList(Map<String, Object> paramMap) {
		return ywDistributorSalestatusDao.queryUnionMemberList(paramMap);
	}

	
}
