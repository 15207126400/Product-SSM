package com.yunwei.product.backend.service;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.serivce.IBaseSerivce;
import com.yunwei.product.common.model.YwTeamFound;


/**
 * 云维字典
 * @author zhangjh
 *
 */
public interface YwTeamFoundService extends IBaseSerivce<YwTeamFound>{
	
	
	public boolean isSuccess(YwTeamFound ywTeamFound, String pay_flag);
	
	public List<YwTeamFound> queryTimeoutList(YwTeamFound ywTeamFound);
	
	public List<YwTeamFound> updateFoundStatus(YwTeamFound ywTeamFound);

}
