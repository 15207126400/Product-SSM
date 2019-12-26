package com.yunwei.product.backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.backend.service.YwTicketService;
import com.yunwei.product.common.dao.YwTicketDao;
import com.yunwei.product.common.model.YwTicket;
@Service
public class YwTicketServiceImpl extends IBaseServiceImpl<YwTicket> implements YwTicketService {
	
	@Autowired
	private YwTicketDao ywTicketDao;
	@Override
	protected IBaseDao<YwTicket> getBaseDao() {
		return ywTicketDao;
	}
	
	@Override
	public List<YwTicket> queryByRand() {
		
		return ywTicketDao.queryByRand();
	}
}
