package com.yunwei.product.infobase.serivce.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.common.dao.YwProductClassifyDao;
import com.yunwei.product.common.model.YwProductClassify;
import com.yunwei.product.infobase.service.YwProductClassifyService;

@Service
public class YwProductClassifyImpl extends IBaseServiceImpl<YwProductClassify> implements YwProductClassifyService {
	
	@Autowired
	private YwProductClassifyDao ywProductClassifyDao;

	@Override
	protected IBaseDao<YwProductClassify> getBaseDao() {
		return ywProductClassifyDao;
	}
}
