package com.yunwei.product.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.log4j.Logger;
import com.yunwei.product.backend.service.YwSignInRuleService;
import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.common.dao.YwSignInRuleDao;
import com.yunwei.product.common.model.YwSignInRule;


@Service
public class YwSignInRuleServiceImpl extends IBaseServiceImpl<YwSignInRule> implements YwSignInRuleService{
	
    private static Logger logger = Logger.getLogger(YwSignInRuleServiceImpl.class);
	@Autowired
	private YwSignInRuleDao ywSignInRuleDao;

	@Override
	protected IBaseDao<YwSignInRule> getBaseDao() {
		return ywSignInRuleDao;
	}
	
	
	
	
}
