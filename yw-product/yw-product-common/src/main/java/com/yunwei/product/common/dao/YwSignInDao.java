package com.yunwei.product.common.dao;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.product.common.backend.model.dto.YwDistributorDto;
import com.yunwei.product.common.backend.model.dto.YwSignInDto;
import com.yunwei.product.common.model.YwSignIn;

/**
 * 签到Dao
* @ClassName: YwSignInDao 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年07月25日 下午16:32:34
*
 */
public interface YwSignInDao extends IBaseDao<YwSignIn>{
	
	public List<YwSignInDto> queryUnionMemberList(Map<String,Object> paramMap);

}
