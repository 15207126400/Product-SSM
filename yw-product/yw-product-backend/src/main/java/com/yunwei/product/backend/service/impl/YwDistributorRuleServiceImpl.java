package com.yunwei.product.backend.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.backend.service.YwDistributorRuleService;
import com.yunwei.product.common.dao.YwDistributorRuleDao;
import com.yunwei.product.common.model.YwDistributorRule;
@Service
public class YwDistributorRuleServiceImpl extends IBaseServiceImpl<YwDistributorRule> implements YwDistributorRuleService {

	@Autowired
	private YwDistributorRuleDao ywDistributorRuleDao;
	
	@Override
	protected IBaseDao<YwDistributorRule> getBaseDao() {
		return ywDistributorRuleDao;
	}
	
	@Override
	public YwDistributorRule queryLast(YwDistributorRule ywDistributorRule) {
		List<YwDistributorRule> list = super.queryList(ywDistributorRule);
		if(list != null && list.size() > 0){
			return list.get(list.size()-1);
		}
		return new YwDistributorRule();
	}



	

}
