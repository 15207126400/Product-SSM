package com.yunwei.product.infobase.serivce.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.common.backend.model.dto.YwSeckillActivityInfobaseDto;
import com.yunwei.product.common.dao.YwSeckillActivityDao;
import com.yunwei.product.common.model.YwSeckillActivity;
import com.yunwei.product.infobase.service.YwSeckillActivityService;

@Service
public class YwSeckillActivityServiceImpl extends IBaseServiceImpl<YwSeckillActivity> implements YwSeckillActivityService {
	
	@Autowired
	private YwSeckillActivityDao ywSeckillActivityDao;

	@Override
	protected IBaseDao<YwSeckillActivity> getBaseDao() {
		return ywSeckillActivityDao;
	}

	@Override
	public List<YwSeckillActivityInfobaseDto> queryUnionSeckillTimeList(
			Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return ywSeckillActivityDao.queryUnionSeckillTimeList(paramMap);
	}
	
	
	

}
