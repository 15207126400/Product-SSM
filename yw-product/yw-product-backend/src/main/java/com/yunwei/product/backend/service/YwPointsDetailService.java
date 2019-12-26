package com.yunwei.product.backend.service;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.serivce.IBaseSerivce;
import com.yunwei.product.common.backend.model.dto.YwPointsDetailDto;
import com.yunwei.product.common.model.YwPointsDetail;

/**
 * 积分明细service
* @ClassName: YwPointsDetailService 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年07月26日 下午14:04:33
*
 */
public interface YwPointsDetailService extends IBaseSerivce<YwPointsDetail>{
	public List<YwPointsDetailDto> queryUnionMemberList(Map<String,Object> paramMap);

}
