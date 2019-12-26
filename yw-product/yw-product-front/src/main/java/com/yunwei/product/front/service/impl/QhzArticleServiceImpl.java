package com.yunwei.product.front.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.common.dao.QhzArticleDao;
import com.yunwei.product.common.model.QhzArticle;
import com.yunwei.product.front.service.QhzArticleService;

@Service
public class QhzArticleServiceImpl extends IBaseServiceImpl<QhzArticle> implements QhzArticleService {
	private static Logger logger = Logger.getLogger(QhzArticleServiceImpl.class);
	@Autowired
	private QhzArticleDao qhzArticleDao;

	@Override
	protected IBaseDao<QhzArticle> getBaseDao() {
		return qhzArticleDao;
	}
	
	

}

