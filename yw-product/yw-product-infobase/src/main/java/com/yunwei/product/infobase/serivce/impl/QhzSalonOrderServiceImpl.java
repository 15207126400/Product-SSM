package com.yunwei.product.infobase.serivce.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.common.middleware.ISisapService;
import com.yunwei.product.common.dao.QhzSalonOrderDao;
import com.yunwei.product.common.model.QhzSalonOrder;
import com.yunwei.product.infobase.service.QhzSalonOrderService;

@Service
public class QhzSalonOrderServiceImpl extends IBaseServiceImpl<QhzSalonOrder> implements QhzSalonOrderService {
	private static Logger logger = Logger.getLogger(QhzSalonOrderServiceImpl.class);
	@Autowired
	private QhzSalonOrderDao qhzSalonOrderDao;
	@Autowired
	private ISisapService iSisapService;

	@Override
	protected IBaseDao<QhzSalonOrder> getBaseDao() {
		return qhzSalonOrderDao;
	}

	@Override
	public void orderMessageSend(String app_id, String order_sn) {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("app_id", app_id);
		params.put("order_sn", order_sn);
		iSisapService.call("http://39.100.96.124", "/product/ywOrder/messages", params);
		
	}
	
	

}

