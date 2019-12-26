package com.yunwei.product.backend.service;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.serivce.IBaseSerivce;
import com.yunwei.product.common.backend.model.dto.YwPointsDto;
import com.yunwei.product.common.model.YwPoints;

/**
 * 积分中心service
* @ClassName: YwPointsService 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年07月26日 下午12:01:41
*
 */
public interface YwPointsService extends IBaseSerivce<YwPoints>{
	public List<YwPointsDto> queryUnionMemberList(Map<String,Object> paramMap);

}
