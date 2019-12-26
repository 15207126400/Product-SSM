package com.yunwei.product.infobase.serivce.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.common.dao.YwProductAttributevalueDao;
import com.yunwei.product.common.model.YwProductAttributevalue;
import com.yunwei.product.infobase.service.YwProductAttributevalueService;

@Service
public class YwProductAttributevalueImpl extends IBaseServiceImpl<YwProductAttributevalue> implements YwProductAttributevalueService {
	
	@Autowired
	private YwProductAttributevalueDao ywProductAttributevalueDao;
	
	@Override
	protected IBaseDao<YwProductAttributevalue> getBaseDao() {
		return ywProductAttributevalueDao;
	}

	@Override
	public List<YwProductAttributevalue> queryList(YwProductAttributevalue ywProductAttributevalue) {
		return ywProductAttributevalueDao.queryList(ywProductAttributevalue);
	}

	@Override
	public int update(YwProductAttributevalue ywProductAttributevalue) {
		ywProductAttributevalueDao.update(ywProductAttributevalue);
		return 0;
	}

	@Override
	public int insert(YwProductAttributevalue ywProductAttributevalue) {
		ywProductAttributevalueDao.insert(ywProductAttributevalue);
		return 0;
	}

	@Override
	public List<YwProductAttributevalue> queryPage(Map<String, Object> map) {
		return ywProductAttributevalueDao.queryPage(map);
	}

	@Override
	public int queryTotals(YwProductAttributevalue ywProductAttributevalue) {
		return ywProductAttributevalueDao.queryTotals(ywProductAttributevalue);
	}

	@Override
	public YwProductAttributevalue query(YwProductAttributevalue ywProductAttributevalue) {
		List<YwProductAttributevalue> list = ywProductAttributevalueDao.queryList(ywProductAttributevalue);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return new YwProductAttributevalue();
	}

	@Override
	public int delete(YwProductAttributevalue ywProductAttributevalue) {
		ywProductAttributevalueDao.delete(ywProductAttributevalue);
		return 0;
	}

	@Override
	public int insertBatch(List<YwProductAttributevalue> attributevalues) {
		
		return ywProductAttributevalueDao.insertBatch(attributevalues);
	}

	

}
