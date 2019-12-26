package com.yunwei.product.infobase.service;


import java.util.List;
import java.util.Map;

import com.yunwei.common.base.serivce.IBaseSerivce;
import com.yunwei.product.common.backend.model.dto.YwSeckillActivityInfobaseDto;
import com.yunwei.product.common.model.YwSeckillActivity;

/**
 * 云维用户
 * @author zhangjh
 *
 */
public interface YwSeckillActivityService extends IBaseSerivce<YwSeckillActivity>{
	
	public List<YwSeckillActivityInfobaseDto> queryUnionSeckillTimeList(Map<String,Object> paramMap);

}
