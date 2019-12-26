package com.yunwei.product.common.dao;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.product.common.backend.model.dto.YwTeamFollowDto;
import com.yunwei.product.common.model.YwOrder;
import com.yunwei.product.common.model.YwTeamFollow;

/**
 * 参团信息
 * @author zhangjh
 *
 */
public interface YwTeamFollowDao extends IBaseDao<YwTeamFollow>{
	
	public List<YwTeamFollowDto> queryUnionMemberList(Map<String,Object> paramMap);
}
