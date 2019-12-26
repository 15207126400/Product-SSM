package com.yunwei.product.infobase.serivce.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.common.dao.YwSeckillRecordDao;
import com.yunwei.product.common.model.YwSeckillRecord;
import com.yunwei.product.infobase.service.YwSeckillRecordService;

@Service
public class YwSeckillRecordServiceImpl extends IBaseServiceImpl<YwSeckillRecord> implements YwSeckillRecordService {
	
	@Autowired
	private YwSeckillRecordDao ywSeckillRecordDao;

	@Override
	protected IBaseDao<YwSeckillRecord> getBaseDao() {
		return ywSeckillRecordDao;
	}
	
	

}
