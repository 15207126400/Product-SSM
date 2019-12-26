package com.yunwei.product.backend.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.backend.service.YwMemberService;
import com.yunwei.product.common.dao.YwMemberDao;
import com.yunwei.product.common.model.YwMember;
/**
 * 
* @ClassName: ShopDetailDao 
* @Description: TODO(用户唯一身份接口实现层) 
* @author 晏飞
* @date 2017年11月18日 下午5:09:49 
*
 */
@Service
public class YwMemberServiceImpl extends IBaseServiceImpl<YwMember> implements YwMemberService {

	@Autowired
    private YwMemberDao ywMemberDao;
	
	@Override
	protected IBaseDao<YwMember> getBaseDao() {
		return ywMemberDao;
	}
	public List<YwMember> queryAllOpenId(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return ywMemberDao.queryAllOpenId(map);
	}

	public int addOpenId(Object openid) {
		// TODO Auto-generated method stub
		return ywMemberDao.addOpenId(openid);
	}

	@Override
	public int queryByOpenidCount(String openid) {
		// TODO Auto-generated method stub
		return ywMemberDao.queryByOpenidCount(openid);
	}

	@Override
	public YwMember queryByOpenid(String openid) {
		// TODO Auto-generated method stub
		return ywMemberDao.queryByOpenid(openid);
	}

	@Override
	public int updateByOpenid(YwMember ywMember) {
		// TODO Auto-generated method stub
		return ywMemberDao.updateByOpenid(ywMember);
	}

	@Override
	public int addUserInfo(YwMember ywMember) {
		// TODO Auto-generated method stub
		return ywMemberDao.addUserInfo(ywMember);
	}

	@Override
	public int updateByPoints(YwMember ywMember) {
		// TODO Auto-generated method stub
		return ywMemberDao.updateByPoints(ywMember);
	}

	@Override
	public int findsum(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return ywMemberDao.findsum(map);
	}

	@Override
	public int updateByLevel(YwMember ywMember) {
		// TODO Auto-generated method stub
		return ywMemberDao.updateByLevel(ywMember);
	}

	@Override
	public int updateByRegUser(YwMember ywMember) {
		// TODO Auto-generated method stub
		return ywMemberDao.updateByRegUser(ywMember);
	}

	@Override
	public int updateByLevelname(YwMember ywMember) {
		// TODO Auto-generated method stub
		return ywMemberDao.updateByLevelname(ywMember);
	}

	@Override
	public List<YwMember> queryAll(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return ywMemberDao.queryAll(map);
	}

	

}
