package com.yunwei.product.common.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.product.common.backend.model.dto.YwOrderDto;
import com.yunwei.product.common.model.OrderCountProcedure;
import com.yunwei.product.common.model.OrderPriceProcedure;
import com.yunwei.product.common.model.YwOrder;

/**
 * 
* @ClassName: ShopPaymentDao 
* @Description: 订单接口 
* @author 晏飞
* @date 2017年12月26日 下午8:13:45 
*
 */
public interface YwOrderDao extends IBaseDao<YwOrder>{
	public int queryTotals(YwOrderDto ywOrderDto);		
	
	public List<YwOrder> queryUnionList(Map<String,Object> paramMap);		// 订单信息
	
//	public List<YwOrderDto> queryUnionListByMap(Map<String,Object> paramMap);		// 包含用户昵称的联合查询
	
	public List<YwOrderDto> queryUnionPageListByMap(Map<String,Object> paramMap);		// 包含用户昵称的联合分页查询
	
	public OrderPriceProcedure getPrice(String branch_id);// 调用存储过程查询已支付金额的交易额
	
	public List<OrderCountProcedure> getCount(String branch_id);// 调用存储过程查询已支付金额的订单数量
	
	public List<YwOrder> importExcelByOrder(Map<String,Object> paramMap);		// 导出excel表单数据关联查询
	
}
