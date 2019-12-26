package com.yunwei.product.common.dao;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.product.common.model.YwTeamActivity;

/**
 * 拼团活动
 * @author zhangjh
 *
 */
public interface YwTeamActivityDao extends IBaseDao<YwTeamActivity>{
	
	
	public List<YwTeamActivity> queryUnionProductList(Map<String,Object> paramMap);

}
