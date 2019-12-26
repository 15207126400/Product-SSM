package com.yunwei.product.front.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.common.dao.QhzTeacherDao;
import com.yunwei.product.common.model.QhzTeacher;
import com.yunwei.product.front.service.QhzTeacherService;

@Service
public class QhzTeacherServiceImpl extends IBaseServiceImpl<QhzTeacher> implements QhzTeacherService {
	private static Logger logger = Logger.getLogger(QhzTeacherServiceImpl.class);
	@Autowired
	private QhzTeacherDao qhzTeacherDao;

	@Override
	protected IBaseDao<QhzTeacher> getBaseDao() {
		return qhzTeacherDao;
	}
	
	

}

