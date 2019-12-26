package com.yunwei.product.backend.service;

import java.util.List;
import java.util.Map;
import com.yunwei.common.base.serivce.IBaseSerivce;
import com.yunwei.common.user.SysUser;
import com.yunwei.product.common.backend.model.dto.YwOrderDto;
import com.yunwei.product.common.model.OrderCountProcedure;
import com.yunwei.product.common.model.OrderPriceProcedure;
import com.yunwei.product.common.model.YwOrder;


public interface YwOrderService extends IBaseSerivce<YwOrder>{
	
	public int queryTotals(YwOrderDto ywOrderDto);
	
	public int queryTotalsByMap(Map<String,Object> paramMap);
	
	public List<YwOrder> queryUnionList(Map<String,Object> paramMap);
	
//	public List<YwOrderDto> queryUnionListByMap(Map<String,Object> paramMap);		// 包含用户昵称的联合查询
	
	public List<YwOrderDto> queryUnionPageListByMap(Map<String,Object> paramMap);	
	
	public int updateOrderStatus(YwOrder ywOrder);
	
	public Map<String, Object> orderSendOutSendMsg(SysUser sysUser,YwOrder ywOrder);
	
	/**
	 * 用户确认收货后计入积分
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
	 * 发货后的(待收货)订单根据传入天数自动确认收货
	*
	*@param YwOrder			订单对象
	*@param days			天数
	 */
	public void updateStatusByDateToSendProduct(YwOrder ywOrder , int days);

    /**
     * 创建扫码订单
     * @param order_sn
     * @param total_fee
     * @param body
     * @return
     */
    public Map<String,Object> createNATIVEOrder(String order_sn,String total_fee, String body);

	
	/**
	 * 调用存储过程查询已支付订单量与交易额
	*
	*@return
	*Map<String,Object>
	 */
	public Map<String, Object> getOrderAndOfflineStatis(String branch_id); 
	
	/**
	 * 导出excel表单数据关联查询
	*
	*@param paramMap
	*@return
	*List<YwOrder>
	 */
	public List<YwOrder> importExcelByOrder(Map<String,Object> paramMap);
	
}
