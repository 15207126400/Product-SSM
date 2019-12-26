package com.yunwei.product.backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.backend.service.YwMemberTokenService;
import com.yunwei.product.common.dao.YwMemberTokenDao;
import com.yunwei.product.common.model.YwMemberToken;
/**
 * 
* @ClassName: ywMemberTokenServiceImpl 
* @Description: token接口实现类 
* @author 晏飞
* @date 2017年11月27日 上午10:16:01 
*
 */

@Service
public class YwMemberTokenServiceImpl extends IBaseServiceImpl<YwMemberToken> implements YwMemberTokenService {

	@Autowired
	private YwMemberTokenDao ywMemberTokenDao;
	
	@Override
	protected IBaseDao<YwMemberToken> getBaseDao() {
		// TODO Auto-generated method stub
		return ywMemberTokenDao;
	}

	public List<YwMemberToken> queryAllToken() {
		// TODO Auto-generated method stub
		return ywMemberTokenDao.queryAllToken();
	}

	@Override
	public int addToken(YwMemberToken ywMemberToken) {
		// TODO Auto-generated method stub
		return ywMemberTokenDao.addToken(ywMemberToken);
	}

	@Override
	public int updateToken(YwMemberToken ywMemberToken) {
		// TODO Auto-generated method stub
		return ywMemberTokenDao.updateToken(ywMemberToken);
	}

	@Override
	public YwMemberToken queryByToken(int id) {
		// TODO Auto-generated method stub
		return ywMemberTokenDao.queryByToken(id);
	}



}
