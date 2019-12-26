package com.yunwei.product.infobase.service;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.serivce.IBaseSerivce;
import com.yunwei.common.user.SysUser;
import com.yunwei.product.common.backend.model.dto.YwOrderDto;
import com.yunwei.product.common.model.YwOrder;


public interface YwOrderService extends IBaseSerivce<YwOrder>{
	
	public int queryTotals(YwOrderDto ywOrderDto);
	
	public int queryTotalsByMap(Map<String,Object> paramMap);
	
	public List<YwOrder> queryUnionList(Map<String,Object> paramMap);
	
//	public List<YwOrderDto> queryUnionListByMap(Map<String,Object> paramMap);		// 包含用户昵称的联合查询
	
	public List<YwOrderDto> queryUnionPageListByMap(Map<String,Object> paramMap);	
	
	public int updateOrderStatus(YwOrder ywOrder);
	
	public Map<String, Object> OrderSendOutSendMsg(SysUser sysUser,YwOrder ywOrder);
	
	/**
	 * 用户确认收货后计入消费金额
	*
	*@param openid	用户身份标识	
	*@param order_sn  订单号
	*@return  0 计入失败
	*		  1 计入成功
	 */
	public int orderAddPrice(String openid,String order_sn);
	
	/**
	 * 用户确认收货后分别计入一级,二级(若存在的话)分销商佣金
	*
	*@param order_sn	订单号
	 */
	public void orderAddCommission(String order_sn);
	
	
	/**
	 * 订单消息发送
	 * @param app_id
	 * @param order_sn
	 */
	public void orderMessageSend(String app_id,String order_sn);
	
	/**
	 * 根据机构编号查询今日成交额与订单量 , 以及全部的成交额及订单量
	*
	*@param branch_id	机构编号
	*@return
	*Map<String,Object>
	 */
	public Map<String, Object> getOrderAndOfflineStatis(String branch_id);
	
}
