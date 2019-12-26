package com.yunwei.product.backend.service;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.serivce.IBaseSerivce;
import com.yunwei.product.common.backend.model.dto.YwPointsExchangeDto;
import com.yunwei.product.common.model.YwPointsExchange;

/**
 * 积分兑换service
* @ClassName: YwPointsExchangeService 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年07月26日 下午17:08:01
*
 */
public interface YwPointsExchangeService extends IBaseSerivce<YwPointsExchange>{
	public List<YwPointsExchangeDto> queryUnionMemberList(Map<String,Object> paramMap);

}
