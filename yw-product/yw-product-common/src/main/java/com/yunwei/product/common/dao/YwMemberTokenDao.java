package com.yunwei.product.common.dao;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.product.common.model.YwMemberToken;

/**
 * 
* @ClassName: ywMemberTokenDao 
* @Description: TODO(token请求接口) 
* @author 晏飞
* @date 2017年11月18日 下午5:09:49 
*
 */
public interface YwMemberTokenDao extends IBaseDao<YwMemberToken>{
	
	//查看全部token信息
	public List<YwMemberToken> queryAllToken();

	//保存token信息
	public int addToken(YwMemberToken ywMemberToken);
	
	//根据id查询token信息
	public YwMemberToken queryByToken(int id);
	
	//修改token信息
	public int updateToken(YwMemberToken ywMemberToken);
}
