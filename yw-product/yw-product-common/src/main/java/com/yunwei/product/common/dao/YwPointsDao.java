package com.yunwei.product.common.dao;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.product.common.backend.model.dto.YwPointsDto;
import com.yunwei.product.common.backend.model.dto.YwSignInDto;
import com.yunwei.product.common.model.YwPoints;

/**
 * 积分中心Dao
* @ClassName: YwPointsDao 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年07月26日 下午12:01:41
*
 */
public interface YwPointsDao extends IBaseDao<YwPoints>{
	public List<YwPointsDto> queryUnionMemberList(Map<String,Object> paramMap);

}
