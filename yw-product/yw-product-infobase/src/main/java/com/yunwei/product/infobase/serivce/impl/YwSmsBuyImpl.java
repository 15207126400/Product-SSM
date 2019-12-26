package com.yunwei.product.infobase.serivce.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.common.dao.YwSmsBuyDao;
import com.yunwei.product.common.model.YwSmsBuy;
import com.yunwei.product.infobase.service.YwSmsBuyService;


@Service
public class YwSmsBuyImpl extends IBaseServiceImpl<YwSmsBuy> implements YwSmsBuyService {
	
	@Autowired
	private YwSmsBuyDao ywSmsBuyDao;

	@Override
	protected IBaseDao<YwSmsBuy> getBaseDao() {
		return ywSmsBuyDao;
	}
	
	
	
}
