package com.yunwei.product.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.backend.service.QhzCourseService;
import com.yunwei.product.common.dao.QhzCourseDao;
import com.yunwei.product.common.model.QhzCourse;


@Service
public class QhzCourseServiceImpl extends IBaseServiceImpl<QhzCourse> implements QhzCourseService{
	
    private static Logger logger = Logger.getLogger(QhzCourseServiceImpl.class);
	@Autowired
	private QhzCourseDao qhzCourseDao;

	@Override
	protected IBaseDao<QhzCourse> getBaseDao() {
		return qhzCourseDao;
	}
	
	@Override
	protected String getPrimaryKey() {
	
		return "id";
	}
	
	
	
	
}
