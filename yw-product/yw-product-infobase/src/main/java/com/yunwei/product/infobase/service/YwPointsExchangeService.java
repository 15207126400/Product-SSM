package com.yunwei.product.infobase.service;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.serivce.IBaseSerivce;
import com.yunwei.product.common.infobase.model.dto.YwPointsExchangeByCouponDto;
import com.yunwei.product.common.infobase.model.dto.YwPointsExchangeByOrderItemsDto;
import com.yunwei.product.common.model.YwPointsDetail;
import com.yunwei.product.common.model.YwPointsExchange;

/**
 * 积分兑换serivce
* @ClassName: YwPointsExchangeInfobaseService 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年07月26日 下午17:08:01
*
 */
public interface YwPointsExchangeService extends IBaseSerivce<YwPointsExchange>{
	/**
	 * 生成积分兑换记录
	*
	*@param type				兑换的商品类型(1,商品类型 ; 2,优惠券类型 ; 3,其他类型)
	*@param user_id				用户编号
	*@param points				消费积分
	*@param order_sn			订单号		(类型商品时记录订单号 , 类型为优惠券时记录领取表id)
	*@param product_id			商品编号
	*@param status				兑换状态
	*							(0,兑换中  1,兑换成功  2,兑换失败   
	*								兑换商品初始值为0,兑换中 , 订单发货成功后更改为1
	*								兑换优惠券初始值为0 , 成功插入优惠券至用户账户时更改状态为1)
	*@return
	*int						0,生成失败  1,生成成功
	 */
	public int addPointsExchange(String type , String user_id , String points , String order_sn , String product_id , String status);

	/**
	 * 兑换表与订单项表的联合查询
	*
	*@param paramMap		传入的参数 (columnName 列名 ; sort 排序方式)
	*@return
	*YwPointsExchangeByOrderItemsDto
	 */
	public YwPointsExchangeByOrderItemsDto queryUnionByOrderitems(Map<String,Object> paramMap);
	
	/**
	 * 兑换表与优惠券表的联合查询
	*
	*@param paramMap		传入的参数 (columnName 列名 ; sort 排序方式)
	*@return
	*YwPointsExchangeByCouponDto
	 */
	public YwPointsExchangeByCouponDto queryUnionByCoupon(Map<String,Object> paramMap);
	
	
	/**
	 * 动态排序
	*
	*@param paramMap		传入的参数 (columnName 列名 ; sort 排序方式)
	*@return
	*List<YwPointsDetail>
	 */
	public List<YwPointsExchange> orderByQueryList(Map<String,Object> paramMap);
	
}

