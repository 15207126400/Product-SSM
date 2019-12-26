package com.yunwei.product.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.backend.service.YwSmsComboService;
import com.yunwei.product.common.dao.YwSmsComboDao;
import com.yunwei.product.common.model.YwSmsCombo;


@Service
public class YwSmsComboImpl extends IBaseServiceImpl<YwSmsCombo> implements YwSmsComboService {
	
	@Autowired
	private YwSmsComboDao ywSmsComboDao;

	@Override
	protected IBaseDao<YwSmsCombo> getBaseDao() {
		return ywSmsComboDao;
	}


}
