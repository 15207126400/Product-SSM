package com.yunwei.product.backend.service;

import java.util.List;

import com.yunwei.common.base.serivce.IBaseSerivce;
import com.yunwei.product.common.model.YwMemberToken;

/**
 * 
* @ClassName: ywMemberTokenDao 
* @Description: TODO(token接口) 
* @author 晏飞
* @date 2017年11月27日 下午5:09:49 
*
 */
public interface YwMemberTokenService extends IBaseSerivce<YwMemberToken>{

		//查询所有token信息
		public List<YwMemberToken> queryAllToken();

		//保存token信息
		public int addToken(YwMemberToken ywMemberToken);
		
		//根据id查询token信息
		public YwMemberToken queryByToken(int id);
		
		//修改token信息
		public int updateToken(YwMemberToken ywMemberToken);
}
