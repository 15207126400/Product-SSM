package com.yunwei.product.backend.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.backend.service.YwProductCollectionService;
import com.yunwei.product.common.dao.YwProductCollectionDao;
import com.yunwei.product.common.model.YwProductCollection;
/**
 * 
* @ClassName: ShopDetailDao 
* @Description: TODO(收藏接口实现层) 
* @author 晏飞
* @date 2017年11月18日 下午5:09:49 
*
 */
@Service
public class YwProductCollectionServiceImpl extends IBaseServiceImpl<YwProductCollection> implements YwProductCollectionService {
	@Autowired
	private YwProductCollectionDao ywProductCollectionDao;

	@Override
	protected IBaseDao<YwProductCollection> getBaseDao() {
		return ywProductCollectionDao;
	}
	
	@Override
	public int addIslike(YwProductCollection ywProductCollection) {
		// TODO Auto-generated method stub
		return ywProductCollectionDao.addIslike(ywProductCollection);
	}

	@Override
	public List<YwProductCollection> queryIslike(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return ywProductCollectionDao.queryIslike(map);
	}


	@Override
	public int deleteIslike(int id) {
		// TODO Auto-generated method stub
		return ywProductCollectionDao.deleteIslike(id);
	}

	@Override
	public int deleteIslikeById(int id) {
		// TODO Auto-generated method stub
		return ywProductCollectionDao.deleteIslikeById(id);
	}

	@Override
	public int findsum(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return ywProductCollectionDao.findsum(map);
	}

	@Override
	public YwProductCollection queryByIslike(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return ywProductCollectionDao.queryByIslike(map);
	}

	

}
