package com.yunwei.product.infobase.serivce.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.common.dao.YwPointsExchangeDao;
import com.yunwei.product.infobase.service.YwPointsExchangeService;
import com.yunwei.product.common.infobase.model.dto.YwPointsExchangeByCouponDto;
import com.yunwei.product.common.infobase.model.dto.YwPointsExchangeByOrderItemsDto;
import com.yunwei.product.common.model.YwPointsExchange;

@Service
public class YwPointsExchangeServiceImpl extends IBaseServiceImpl<YwPointsExchange> implements YwPointsExchangeService {
	private static Logger logger = Logger.getLogger(YwPointsExchangeServiceImpl.class);
	@Autowired
	private YwPointsExchangeDao ywPointsExchangeDao;

	@Override
	protected IBaseDao<YwPointsExchange> getBaseDao() {
		return ywPointsExchangeDao;
	}

	@Override
	public int addPointsExchange(String type, String user_id, String points, String order_sn, String product_id , String status) {
		YwPointsExchange ywPointsExchange = new YwPointsExchange();
		ywPointsExchange.setUser_id(user_id);
		ywPointsExchange.setExchange_type(type);
		if(StringUtils.equals(type, "1")){							// 如果是兑换商品的记录  , 需保存订单号
			ywPointsExchange.setOrder_sn(order_sn);
		}
		ywPointsExchange.setPoints(points);
		ywPointsExchange.setStatus(status); 						// (0,兑换中 1,兑换成功 2,兑换失败)
		ywPointsExchange.setProduct_id(product_id);
		ywPointsExchange.setCreate_datetime(new Date());
		
		return ywPointsExchangeDao.insert(ywPointsExchange);
	}

	@Override
	public YwPointsExchangeByOrderItemsDto queryUnionByOrderitems(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return ywPointsExchangeDao.queryUnionByOrderitems(paramMap);
	}

	@Override
	public YwPointsExchangeByCouponDto queryUnionByCoupon(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return ywPointsExchangeDao.queryUnionByCoupon(paramMap);
	}
	
	@Override
	public List<YwPointsExchange> orderByQueryList(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return ywPointsExchangeDao.orderByQueryList(paramMap);
	}

}

