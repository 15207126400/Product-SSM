package com.yunwei.context.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.context.sys.dao.YwDictionaryDao;
import com.yunwei.context.sys.model.YwDictionary;
import com.yunwei.context.sys.service.YwDictionaryService;


@Service
public class YwDictionaryImpl extends IBaseServiceImpl<YwDictionary> implements YwDictionaryService{
	

	@Autowired
	private YwDictionaryDao ywDictionaryDao;

	@Override
	protected IBaseDao<YwDictionary> getBaseDao() {
		return ywDictionaryDao;
	}
	
	
	
	
}
