package com.yunwei.product.infobase.service;

import java.util.HashMap;
import java.util.Map;

import com.yunwei.common.base.serivce.IBaseSerivce;
import com.yunwei.product.common.model.YwPoints;

/**
 * 积分中心serivce
* @ClassName: YwPointsInfobaseService 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年07月26日 下午12:01:41
*
 */
public interface YwPointsService extends IBaseSerivce<YwPoints>{
	/**
	 * 增加积分到指定账户
	*
	*@param points	增加的积分
	*@param openid	用户openid
	*@return
	*int	返回操作的状态 0,未生效    1,生效
	 */
	public int addPointsByUser(String points , String openid);
	

	/**
	 * 扣除指定账户的积分
	*
	*@param points	扣除的积分
	*@param openid	用户openid
	*@return
	*int	返回操作的状态 0,未生效    1,生效
	 */
	public int deductPointsByUser(String points , String openid);
}

