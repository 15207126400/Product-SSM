package com.yunwei.product.infobase.serivce.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.common.dao.QhzCurriculumDao;
import com.yunwei.product.common.model.QhzCurriculum;
import com.yunwei.product.infobase.service.QhzCurriculumService;


@Service
public class QhzCurriculumServiceImpl extends IBaseServiceImpl<QhzCurriculum> implements QhzCurriculumService{
	
    private static Logger logger = Logger.getLogger(QhzCurriculumServiceImpl.class);
	@Autowired
	private QhzCurriculumDao qhzSalonCurriculumDao;

	@Override
	protected IBaseDao<QhzCurriculum> getBaseDao() {
		return qhzSalonCurriculumDao;
	}
	
	@Override
	protected String getPrimaryKey() {
	
		return "id";
	}
	
	
	
	
}
