package com.yunwei.product.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.backend.service.QhzTeacherService;
import com.yunwei.product.common.dao.QhzTeacherDao;
import com.yunwei.product.common.model.QhzTeacher;


@Service
public class QhzTeacherServiceImpl extends IBaseServiceImpl<QhzTeacher> implements QhzTeacherService{
	
    private static Logger logger = Logger.getLogger(QhzTeacherServiceImpl.class);
	@Autowired
	private QhzTeacherDao qhzTeacherDao;

	@Override
	protected IBaseDao<QhzTeacher> getBaseDao() {
		return qhzTeacherDao;
	}
	
	@Override
	protected String getPrimaryKey() {
	
		return "id";
	}
	
	
	
	
}
