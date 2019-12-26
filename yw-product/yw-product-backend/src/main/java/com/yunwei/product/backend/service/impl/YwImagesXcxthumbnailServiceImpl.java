package com.yunwei.product.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.log4j.Logger;
import com.yunwei.product.backend.service.YwImagesXcxthumbnailService;
import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.common.dao.YwImagesXcxthumbnailDao;
import com.yunwei.product.common.model.YwImagesXcxthumbnail;


@Service
public class YwImagesXcxthumbnailServiceImpl extends IBaseServiceImpl<YwImagesXcxthumbnail> implements YwImagesXcxthumbnailService{
	
    private static Logger logger = Logger.getLogger(YwImagesXcxthumbnailServiceImpl.class);
	@Autowired
	private YwImagesXcxthumbnailDao ywImagesXcxthumbnailDao;

	@Override
	protected IBaseDao<YwImagesXcxthumbnail> getBaseDao() {
		return ywImagesXcxthumbnailDao;
	}
	
	
	
	
}
