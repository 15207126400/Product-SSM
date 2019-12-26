package com.yunwei.product.common.dao;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.product.common.model.YwTeamFound;

/**
 * 开团信息
 * @author zhangjh
 *
 */
public interface YwTeamFoundDao extends IBaseDao<YwTeamFound>{
	
	
	public List<YwTeamFound> queryTimeoutList(YwTeamFound ywTeamFound);

}
