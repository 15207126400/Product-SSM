package com.yunwei.product.common.dao;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.product.common.backend.model.dto.YwSeckillActivityDto;
import com.yunwei.product.common.backend.model.dto.YwSeckillActivityInfobaseDto;
import com.yunwei.product.common.model.YwSeckillActivity;

/**
 * 秒杀活动dao
 * @author zhangjh
 *
 */
public interface YwSeckillActivityDao extends IBaseDao<YwSeckillActivity>{

	public List<YwSeckillActivityInfobaseDto> queryUnionSeckillTimeList(Map<String,Object> paramMap);
	
	public List<YwSeckillActivityDto> queryUnionSeckillTimeAndProductPage(Map<String, Object> map);
}
