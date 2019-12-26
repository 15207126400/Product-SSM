package com.yunwei.product.common.dao;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.product.common.backend.model.dto.YwPointsDetailDto;
import com.yunwei.product.common.backend.model.dto.YwPointsExchangeDto;
import com.yunwei.product.common.infobase.model.dto.YwPointsExchangeByCouponDto;
import com.yunwei.product.common.infobase.model.dto.YwPointsExchangeByOrderItemsDto;
import com.yunwei.product.common.model.YwPointsDetail;
import com.yunwei.product.common.model.YwPointsExchange;

/**
 * 积分兑换Dao
* @ClassName: YwPointsExchangeDao 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年07月26日 下午17:08:01
*
 */
public interface YwPointsExchangeDao extends IBaseDao<YwPointsExchange>{
	public List<YwPointsExchangeDto> queryUnionMemberList(Map<String,Object> paramMap);
	
	// 兑换表与订单项表的联合查询
	public YwPointsExchangeByOrderItemsDto queryUnionByOrderitems(Map<String,Object> paramMap);
	
	// 兑换表与优惠券表的联合查询
	public YwPointsExchangeByCouponDto queryUnionByCoupon(Map<String,Object> paramMap);
	
	// 动态排序查询
	public List<YwPointsExchange> orderByQueryList(Map<String,Object> paramMap);
}
