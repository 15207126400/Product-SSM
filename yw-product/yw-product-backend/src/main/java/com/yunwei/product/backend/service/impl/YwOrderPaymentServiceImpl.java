package com.yunwei.product.backend.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.backend.service.YwOrderPaymentService;
import com.yunwei.product.common.backend.model.dto.YwOrderPaymentDto;
import com.yunwei.product.common.dao.YwOrderPaymentDao;
import com.yunwei.product.common.model.YwOrderPayment;

@Service
public class YwOrderPaymentServiceImpl extends IBaseServiceImpl<YwOrderPayment> implements YwOrderPaymentService {
	
	@Autowired
	private YwOrderPaymentDao ywOrderPaymentDao;

	@Override
	protected IBaseDao<YwOrderPayment> getBaseDao() {
		return ywOrderPaymentDao;
	}

	@Override
	public List<YwOrderPaymentDto> queryUnionMemberList(Map<String, Object> paramMap) {
		return ywOrderPaymentDao.queryUnionMemberList(paramMap);
	}

}
