package com.yunwei.product.backend.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.backend.service.YwOrderItemService;
import com.yunwei.product.common.dao.YwOrderItemDao;
import com.yunwei.product.common.model.YwOrderItem;

@Service
public class YwOrderItemImpl extends IBaseServiceImpl<YwOrderItem> implements YwOrderItemService {
	
	@Autowired
	private YwOrderItemDao ywOrderItemDao;

	@Override
	protected IBaseDao<YwOrderItem> getBaseDao() {
		// TODO Auto-generated method stub
		return ywOrderItemDao;
	}

	@Override
	public List<YwOrderItem> queryList(YwOrderItem ywOrderItem) {
		return ywOrderItemDao.queryList(ywOrderItem);
	}

	@Override
	public int update(YwOrderItem ywOrderItem) {
		ywOrderItemDao.update(ywOrderItem);
		return 0;
	}

	@Override
	public int insert(YwOrderItem ywOrderItem) {
		ywOrderItemDao.insert(ywOrderItem);
		return 0;
	}

	@Override
	public List<YwOrderItem> queryPage(Map<String, Object> map) {
		return ywOrderItemDao.queryPage(map);
	}

	@Override
	public int queryTotals(YwOrderItem ywOrderItem) {
		return ywOrderItemDao.queryTotals(ywOrderItem);
	}

	@Override
	public YwOrderItem query(YwOrderItem ywOrderItem) {
		List<YwOrderItem> list = ywOrderItemDao.queryList(ywOrderItem);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return new YwOrderItem();
	}

	@Override
	public int delete(YwOrderItem ywOrderItem) {
		ywOrderItemDao.delete(ywOrderItem);
		return 0;
	}

	@Override
	public int insertBatch(List<YwOrderItem> list) {
		
		return ywOrderItemDao.insertBatch(list);
	}


}
