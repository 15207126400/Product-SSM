package com.yunwei.product.backend.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.backend.service.YwImagesService;
import com.yunwei.product.common.dao.YwImagesDao;
import com.yunwei.product.common.model.YwImages;

@Service
public class YwImageServiceImpl extends IBaseServiceImpl<YwImages> implements YwImagesService {
	
	@Autowired
	private YwImagesDao ywImagesDao;
	
	@Override
	protected IBaseDao<YwImages> getBaseDao() {
		// TODO Auto-generated method stub
		return ywImagesDao;
	}

	@Override
	public List<YwImages> queryList(YwImages ywImages) {
		return ywImagesDao.queryList(ywImages);
	}

	@Override
	public int update(YwImages ywImages) {
		ywImagesDao.update(ywImages);
		return 0;
	}

	@Override
	public int insert(YwImages ywImages) {
		ywImagesDao.insert(ywImages);
		return 0;
	}

	@Override
	public List<YwImages> queryPage(Map<String, Object> map) {
		return ywImagesDao.queryPage(map);
	}

	@Override
	public int queryTotals(YwImages ywImages) {
		return ywImagesDao.queryTotals(ywImages);
	}

	@Override
	public YwImages query(YwImages ywImages) {
		List<YwImages> list = ywImagesDao.queryList(ywImages);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return new YwImages();
	}

	@Override
	public int delete(YwImages ywImages) {
		ywImagesDao.delete(ywImages);
		return 0;
	}

	@Override
	public int insertBatch(List<YwImages> list) {
		// TODO Auto-generated method stub
		return ywImagesDao.insertBatch(list);
	}

	@Override
	public int insertBatchByMenDian(List<YwImages> list) {
		// TODO Auto-generated method stub
		return ywImagesDao.insertBatchByMenDian(list);
	}

	@Override
	public List<YwImages> queryListPage(YwImages t, int begin, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public YwImages queryUnion(YwImages t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<YwImages> queryUnionList(YwImages t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<YwImages> queryUnionListPage(YwImages t, int begin, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateBatch(List<YwImages> list) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateBatch(String[] fields, List<YwImages> list) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateBatch(List<YwImages> list, String... fields) {
		// TODO Auto-generated method stub
		return 0;
	}



	

}
