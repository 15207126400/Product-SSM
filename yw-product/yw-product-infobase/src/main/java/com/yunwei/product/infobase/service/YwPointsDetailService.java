package com.yunwei.product.infobase.service;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.serivce.IBaseSerivce;
import com.yunwei.product.common.model.YwPointsDetail;

/**
 * 积分明细serivce
* @ClassName: YwPointsDetailInfobaseService 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年07月26日 下午14:04:33
*
 */
public interface YwPointsDetailService extends IBaseSerivce<YwPointsDetail>{
	
	/**
	 * 生成积分明细
	*
	*@param type			生成明细的类型(1,兑换商品 ; 2,兑换优惠券 ; 3,签到 ; 4,订单)
	*@param user_id			用户openid
	*@param points			积分
	*@param order_sn		订单号(类型为1和4时存在)
	*@return				0,未生效     1,生效
	*int
	 */
	public int addPointsDetail(String type , String user_id , String points , String order_sn);
	
	
	/**
	 * 修改积分明细状态
	*
	*@param order_sn	订单号
	*@param status		状态(1,未生效  2,生效)
	*@return
	*int
	 */
	public int updateByStatus(String order_sn , String status);
	
	/**
	 * 动态排序
	*
	*@param paramMap		传入的参数 (columnName 列名 ; sort 排序方式)
	*@return
	*List<YwPointsDetail>
	 */
	public List<YwPointsDetail> orderByQueryList(Map<String,Object> paramMap);

}

