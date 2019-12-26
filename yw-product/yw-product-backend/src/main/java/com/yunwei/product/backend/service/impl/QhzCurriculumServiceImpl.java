package com.yunwei.product.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.backend.service.QhzCurriculumService;
import com.yunwei.product.common.dao.QhzCurriculumDao;
import com.yunwei.product.common.model.QhzCurriculum;


@Service
public class QhzCurriculumServiceImpl extends IBaseServiceImpl<QhzCurriculum> implements QhzCurriculumService{
	
    private static Logger logger = Logger.getLogger(QhzCurriculumServiceImpl.class);
	@Autowired
	private QhzCurriculumDao qhzCurriculumDao;

	@Override
	protected IBaseDao<QhzCurriculum> getBaseDao() {
		return qhzCurriculumDao;
	}
	
	@Override
	protected String getPrimaryKey() {
	
		return "id";
	}
	
	
	
	
}
