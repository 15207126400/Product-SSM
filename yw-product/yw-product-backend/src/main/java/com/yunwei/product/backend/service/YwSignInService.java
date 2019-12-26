package com.yunwei.product.backend.service;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.serivce.IBaseSerivce;
import com.yunwei.product.common.backend.model.dto.YwSignInDto;
import com.yunwei.product.common.model.YwSignIn;

/**
 * 签到service
* @ClassName: YwSignInService 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年07月25日 下午16:32:34
*
 */
public interface YwSignInService extends IBaseSerivce<YwSignIn>{
	public List<YwSignInDto> queryUnionMemberList(Map<String,Object> paramMap);

}
