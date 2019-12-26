package com.yunwei.product.infobase.serivce.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.common.dao.YwProductSkuDao;
import com.yunwei.product.common.model.YwProductSku;
import com.yunwei.product.infobase.service.YwProductSkuService;

@Service
public class YwProductSkuImpl extends IBaseServiceImpl<YwProductSku> implements YwProductSkuService {
	
	@Autowired
	private YwProductSkuDao ywProductSkuDao;
	
	@Override
	protected IBaseDao<YwProductSku> getBaseDao() {
		// TODO Auto-generated method stub
		return ywProductSkuDao;
	}

	@Override
	public List<YwProductSku> queryList(YwProductSku ywProductSku) {
		return ywProductSkuDao.queryList(ywProductSku);
	}

	@Override
	public int update(YwProductSku ywProductSku) {
		ywProductSkuDao.update(ywProductSku);
		return 0;
	}

	@Override
	public int insert(YwProductSku ywProductSku) {
		ywProductSkuDao.insert(ywProductSku);
		return 0;
	}

	@Override
	public List<YwProductSku> queryPage(Map<String, Object> map) {
		return ywProductSkuDao.queryPage(map);
	}

	@Override
	public int queryTotals(YwProductSku ywProductSku) {
		return ywProductSkuDao.queryTotals(ywProductSku);
	}

	@Override
	public YwProductSku query(YwProductSku ywProductSku) {
		List<YwProductSku> list = ywProductSkuDao.queryList(ywProductSku);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return new YwProductSku();
	}

	@Override
	public int delete(YwProductSku ywProductSku) {
		ywProductSkuDao.delete(ywProductSku);
		return 0;
	}

	@Override
	public int insertBatch(List<YwProductSku> productSkus) {
		
		return ywProductSkuDao.insertBatch(productSkus);
	}



}
