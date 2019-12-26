package com.yunwei.product.infobase.serivce.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.common.dao.YwProductAttributenameDao;
import com.yunwei.product.common.model.YwProductAttributename;
import com.yunwei.product.common.model.YwProductAttributevalue;
import com.yunwei.product.infobase.service.YwProductAttributenameService;

@Service
public class YwProductAttributenameImpl extends IBaseServiceImpl<YwProductAttributename> implements YwProductAttributenameService {
	
	@Autowired
	private YwProductAttributenameDao ywProductAttributenameDao;

	@Override
	protected IBaseDao<YwProductAttributename> getBaseDao() {
		return ywProductAttributenameDao;
	}
	
	@Override
	public List<YwProductAttributename> queryList(YwProductAttributename ywProductAttributename) {
		return ywProductAttributenameDao.queryList(ywProductAttributename);
	}

	@Override
	public int update(YwProductAttributename ywProductAttributename) {
		ywProductAttributenameDao.update(ywProductAttributename);
		return 0;
	}

	@Override
	public int insert(YwProductAttributename ywProductAttributename) {
		ywProductAttributenameDao.insert(ywProductAttributename);
		return 0;
	}

	@Override
	public List<YwProductAttributename> queryPage(Map<String, Object> map) {
		return ywProductAttributenameDao.queryPage(map);
	}

	@Override
	public int queryTotals(YwProductAttributename ywProductAttributename) {
		return ywProductAttributenameDao.queryTotals(ywProductAttributename);
	}

	@Override
	public YwProductAttributename query(YwProductAttributename ywProductAttributename) {
		List<YwProductAttributename> list = ywProductAttributenameDao.queryList(ywProductAttributename);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return new YwProductAttributename();
	}

	@Override
	public int delete(YwProductAttributename ywProductAttributename) {
		ywProductAttributenameDao.delete(ywProductAttributename);
		return 0;
	}

	@Override
	public int insertValueBatch(List<YwProductAttributevalue> attributevalues) {
		
		return ywProductAttributenameDao.insertValueBatch(attributevalues);
	}

	

}
