package com.yunwei.context.sys.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.context.sys.dao.YwSystemNoticeDao;
import com.yunwei.context.sys.model.YwSystemNotice;
import com.yunwei.context.sys.service.YwSystemNoticeService;
@Service
public class YwSystemNoticeServiceImpl extends IBaseServiceImpl<YwSystemNotice> implements YwSystemNoticeService {
	
	 @Autowired
     private YwSystemNoticeDao ywSystemNoticeDao;

	@Override
	protected IBaseDao<YwSystemNotice> getBaseDao() {
		return ywSystemNoticeDao;
	}

}
