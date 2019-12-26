package com.yunwei.product.infobase.serivce.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.common.dao.YwRefundDao;
import com.yunwei.product.common.model.YwRefund;
import com.yunwei.product.infobase.service.YwRefundService;



@Service
public class YwRefundImpl extends IBaseServiceImpl<YwRefund> implements YwRefundService{
	
	@Autowired
	private YwRefundDao ywRefundDao;

	@Override
	protected IBaseDao<YwRefund> getBaseDao() {
		return  ywRefundDao;
	}

	
	

}
