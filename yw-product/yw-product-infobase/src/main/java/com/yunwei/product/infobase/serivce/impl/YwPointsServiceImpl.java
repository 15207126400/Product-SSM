package com.yunwei.product.infobase.serivce.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.common.dao.YwPointsDao;
import com.yunwei.product.infobase.service.YwPointsDetailService;
import com.yunwei.product.infobase.service.YwPointsService;
import com.yunwei.product.common.model.YwPoints;
import com.yunwei.product.common.model.YwPointsDetail;

@Service
public class YwPointsServiceImpl extends IBaseServiceImpl<YwPoints> implements YwPointsService {
	private static Logger logger = Logger.getLogger(YwPointsServiceImpl.class);
	@Autowired
	private YwPointsDao ywPointsDao;
	@Autowired
	private YwPointsDetailService ywPointsDetailService;

	@Override
	protected IBaseDao<YwPoints> getBaseDao() {
		return ywPointsDao;
	}

	@Override
	public int addPointsByUser(String points, String openid) {
		int num = 0;
		
		YwPoints ywPoints = new YwPoints();
		ywPoints.setUser_id(openid);
		ywPoints = super.query(ywPoints);
		if(ywPoints != null){
			int points_totals = Integer.valueOf(ywPoints.getPoints_totals()) + Integer.valueOf(points);
			ywPoints.setPoints_totals(String.valueOf(points_totals));
			ywPoints.setPoints_updatetime(new Date());
			num = super.update(ywPoints);
		} else {
			YwPoints newPoints = new YwPoints();
			newPoints.setUser_id(openid);
			newPoints.setPoints_totals(String.valueOf(points));
			newPoints.setPoints_createtime(new Date());
			num = super.insert(newPoints);
		}
		
		return num;
	}
	
	
	public int deductPointsByUser(String points , String openid){
		int num = 0;
		// 扣除用户积分余额
		YwPoints ywPoints = new YwPoints();
		ywPoints.setUser_id(openid);
		ywPoints = this.query(ywPoints);
		if(ywPoints != null){
			// 积分余额
			int points_totals = Integer.valueOf(ywPoints.getPoints_totals());
			// 所需积分
			int price_totals = Integer.valueOf(points);
			if(points_totals >= price_totals){
				int new_points_totals = points_totals - price_totals;
				ywPoints.setPoints_totals(String.valueOf(new_points_totals));
				ywPoints.setPoints_updatetime(new Date());
				num = super.update(ywPoints);
			}
		}
		return num;
	}


}

