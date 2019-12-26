package com.yunwei.product.backend.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.backend.service.YwProductAttributeRelationService;
import com.yunwei.product.common.dao.YwProductAttributeRelationDao;
import com.yunwei.product.common.model.YwProductAttributeRelation;

@Service
public class YwProductAttributeRelationImpl extends IBaseServiceImpl<YwProductAttributeRelation> implements YwProductAttributeRelationService {
	
	@Autowired
	private YwProductAttributeRelationDao ywProductAttributeRelationDao;

	@Override
	protected IBaseDao<YwProductAttributeRelation> getBaseDao() {
		return ywProductAttributeRelationDao;
	}
	
	@Override
	public List<YwProductAttributeRelation> queryList(YwProductAttributeRelation ywProductAttributeRelation) {
		return ywProductAttributeRelationDao.queryList(ywProductAttributeRelation);
	}

	@Override
	public int update(YwProductAttributeRelation ywProductAttributeRelation) {
		ywProductAttributeRelationDao.update(ywProductAttributeRelation);
		return 0;
	}

	@Override
	public int insert(YwProductAttributeRelation ywProductAttributeRelation) {
		ywProductAttributeRelationDao.insert(ywProductAttributeRelation);
		return 0;
	}

	@Override
	public List<YwProductAttributeRelation> queryPage(Map<String, Object> map) {
		return ywProductAttributeRelationDao.queryPage(map);
	}

	@Override
	public int queryTotals(YwProductAttributeRelation ywProductAttributeRelation) {
		return ywProductAttributeRelationDao.queryTotals(ywProductAttributeRelation);
	}

	@Override
	public YwProductAttributeRelation query(YwProductAttributeRelation ywProductAttributeRelation) {
		List<YwProductAttributeRelation> list = ywProductAttributeRelationDao.queryList(ywProductAttributeRelation);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return new YwProductAttributeRelation();
	}

	@Override
	public int delete(YwProductAttributeRelation ywProductAttributeRelation) {
		ywProductAttributeRelationDao.delete(ywProductAttributeRelation);
		return 0;
	}

	

}
