package com.yunwei.product.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.backend.service.YwSmsBuyService;
import com.yunwei.product.common.dao.YwSmsBuyDao;
import com.yunwei.product.common.model.YwSmsBuy;


@Service
public class YwSmsBuyImpl extends IBaseServiceImpl<YwSmsBuy> implements YwSmsBuyService {
	
	@Autowired
	private YwSmsBuyDao ywSmsBuyDao;

	@Override
	protected IBaseDao<YwSmsBuy> getBaseDao() {
		return ywSmsBuyDao;
	}
	
	
	
}
