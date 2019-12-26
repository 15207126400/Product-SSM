package com.yunwei.product.infobase.serivce.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.common.dao.QhzSalonMeetingDao;
import com.yunwei.product.common.model.QhzSalonMeeting;
import com.yunwei.product.infobase.service.QhzSalonMeetingService;

@Service
public class QhzSalonMeetingServiceImpl extends IBaseServiceImpl<QhzSalonMeeting> implements QhzSalonMeetingService {
	private static Logger logger = Logger.getLogger(QhzSalonMeetingServiceImpl.class);
	@Autowired
	private QhzSalonMeetingDao qhzSalonMeetingDao;

	@Override
	protected IBaseDao<QhzSalonMeeting> getBaseDao() {
		return qhzSalonMeetingDao;
	}
	
	

}

