package com.yunwei.product.infobase.serivce.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.common.dao.YwImagesBackthumbnailDao;
import com.yunwei.product.common.model.YwImagesBackthumbnail;
import com.yunwei.product.infobase.service.YwImagesBackthumbnailService;


@Service
public class YwImagesBackthumbnailServiceImpl extends IBaseServiceImpl<YwImagesBackthumbnail> implements YwImagesBackthumbnailService{
	
    private static Logger logger = Logger.getLogger(YwImagesBackthumbnailServiceImpl.class);
	@Autowired
	private YwImagesBackthumbnailDao ywImagesBackthumbnailDao;

	@Override
	protected IBaseDao<YwImagesBackthumbnail> getBaseDao() {
		return ywImagesBackthumbnailDao;
	}
	
	
	
	
}
