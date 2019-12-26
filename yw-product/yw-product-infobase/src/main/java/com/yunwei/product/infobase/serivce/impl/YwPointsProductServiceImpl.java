package com.yunwei.product.infobase.serivce.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.log4j.Logger;
import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.common.dao.YwPointsProductDao;
import com.yunwei.product.infobase.service.YwPointsProductService;
import com.yunwei.product.common.model.YwPointsProduct;

@Service
public class YwPointsProductServiceImpl extends IBaseServiceImpl<YwPointsProduct> implements YwPointsProductService {
	private static Logger logger = Logger.getLogger(YwPointsProductServiceImpl.class);
	@Autowired
	private YwPointsProductDao ywPointsProductDao;

	@Override
	protected IBaseDao<YwPointsProduct> getBaseDao() {
		return ywPointsProductDao;
	}
	
	

}

