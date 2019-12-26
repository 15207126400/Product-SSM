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
import com.yunwei.product.common.dao.YwPointsDetailDao;
import com.yunwei.product.infobase.service.YwPointsDetailService;
import com.yunwei.product.common.model.YwPointsDetail;

@Service
public class YwPointsDetailServiceImpl extends IBaseServiceImpl<YwPointsDetail> implements YwPointsDetailService {
	private static Logger logger = Logger.getLogger(YwPointsDetailServiceImpl.class);
	@Autowired
	private YwPointsDetailDao ywPointsDetailDao;

	@Override
	protected IBaseDao<YwPointsDetail> getBaseDao() {
		return ywPointsDetailDao;
	}

	@Override
	public int addPointsDetail(String type, String user_id, String points , String order_sn) {
		YwPointsDetail ywPointsDetail = new YwPointsDetail();
		ywPointsDetail.setUser_id(user_id);
		ywPointsDetail.setDetail_type(type);
		ywPointsDetail.setDetail_points_change(points);
		ywPointsDetail.setDetail_createtime(new Date());
		if(StringUtils.equals(type, "1") || StringUtils.equals(type, "4") || StringUtils.equals(type, "5")){
			ywPointsDetail.setOrder_sn(order_sn);
		}
		if(StringUtils.equals(type, "3") || StringUtils.equals(type, "2") || StringUtils.equals(type, "5")){
			ywPointsDetail.setDetail_status("2");			// 签到获取的积分直接生效
		}else{
			ywPointsDetail.setDetail_status("1");			// 生成时未生效 , 待特定条件满足后更改状态为生效
		}
		
		int num = ywPointsDetailDao.insert(ywPointsDetail);
		
		return num;
	}
	

	@Override
	public List<YwPointsDetail> orderByQueryList(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return ywPointsDetailDao.orderByQueryList(paramMap);
	}

	@Override
	public int updateByStatus(String order_sn, String status) {
		int num = 0;
		YwPointsDetail ywPointsDetail = new YwPointsDetail();
		ywPointsDetail.setOrder_sn(order_sn);
		ywPointsDetail = this.query(ywPointsDetail);
		if(ywPointsDetail != null){
			ywPointsDetail.setDetail_status(status);
			ywPointsDetail.setDetail_updatetime(new Date());
			num = super.update(ywPointsDetail);
		}
		
		return num;
	}

}

