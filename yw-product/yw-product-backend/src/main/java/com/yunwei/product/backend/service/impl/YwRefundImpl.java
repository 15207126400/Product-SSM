package com.yunwei.product.backend.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.backend.service.YwRefundService;
import com.yunwei.product.common.dao.YwRefundDao;
import com.yunwei.product.common.model.YwRefund;


@Service
@Scope("prototype")
public class YwRefundImpl extends IBaseServiceImpl<YwRefund> implements YwRefundService {
	
	@Autowired
	private YwRefundDao ywRefundDao;

	@Override
	protected IBaseDao<YwRefund> getBaseDao() {
		return ywRefundDao;
	}
	
	@Override
	public List<YwRefund> queryList(YwRefund ywRefund) {
		return ywRefundDao.queryList(ywRefund);
	}

	@Override
	public int update(YwRefund ywRefund) {
		ywRefundDao.update(ywRefund);
		return 0;
	}

	@Override
	public int insert(YwRefund ywRefund) {
		ywRefundDao.insert(ywRefund);
		return 0;
	}

	@Override
	public List<YwRefund> queryPage(Map<String, Object> map) {
		return ywRefundDao.queryPage(map);
	}

	@Override
	public int queryTotals(YwRefund ywRefund) {
		return ywRefundDao.queryTotals(ywRefund);
	}

	@Override
	public YwRefund query(YwRefund ywRefund) {
		List<YwRefund> list = ywRefundDao.queryList(ywRefund);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return new YwRefund();
	}

	@Override
	public int delete(YwRefund ywRefund) {
		ywRefundDao.delete(ywRefund);
		return 0;
	}

	

}
