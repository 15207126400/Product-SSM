package com.yunwei.product.front.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.common.dao.YwNoticeDao;
import com.yunwei.product.common.model.YwNotice;
import com.yunwei.product.front.service.YwNoticeService;
@Service
public class YwNoticeServiceImpl extends IBaseServiceImpl<YwNotice> implements YwNoticeService {
	
	@Autowired
	private YwNoticeDao ywNoticeDao;

	@Override
	protected IBaseDao<YwNotice> getBaseDao() {
		return ywNoticeDao;
	}


}
