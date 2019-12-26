package com.yunwei.product.backend.service;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.serivce.IBaseSerivce;
import com.yunwei.product.common.backend.model.dto.YwTeamActivityDto;
import com.yunwei.product.common.model.YwTeamActivity;


/**
 * 拼团活动
 * @author zhangjh
 *
 */
public interface YwTeamActivityService extends IBaseSerivce<YwTeamActivity>{
	
	public List<YwTeamActivity> queryUnionProductList(Map<String,Object> paramMap);
	
	public int teamActivityInsertAndProductUpdate(YwTeamActivity ywTeamActivity);
	
	public int teamActivityUpdateAndProductUpdate(YwTeamActivity ywTeamActivity);

}
