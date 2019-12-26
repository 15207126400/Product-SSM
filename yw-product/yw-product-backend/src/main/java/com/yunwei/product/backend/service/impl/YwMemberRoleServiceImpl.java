package com.yunwei.product.backend.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.backend.service.YwMemberRoleService;
import com.yunwei.product.common.dao.YwMemberRoleDao;
import com.yunwei.product.common.model.YwMemberRole;
@Service
public class YwMemberRoleServiceImpl extends IBaseServiceImpl<YwMemberRole> implements YwMemberRoleService {
	@Autowired
	private YwMemberRoleDao ywMemberRoleDao;
	
	@Override
	protected IBaseDao<YwMemberRole> getBaseDao() {
		return ywMemberRoleDao;
	}
	@Override
	public List<YwMemberRole> queryAll(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return ywMemberRoleDao.queryAll(map);
	}

	@Override
	public YwMemberRole queryById(int id) {
		// TODO Auto-generated method stub
		return ywMemberRoleDao.queryById(id);
	}

	@Override
	public int addYwMemberRole(YwMemberRole ywMemberrole) {
		// TODO Auto-generated method stub
		return ywMemberRoleDao.addYwMemberRole(ywMemberrole);
	}

	@Override
	public int updateYwMemberRole(YwMemberRole ywMemberrole) {
		// TODO Auto-generated method stub
		return ywMemberRoleDao.updateYwMemberRole(ywMemberrole);
	}

	@Override
	public int deleteYwMemberRole(int id) {
		// TODO Auto-generated method stub
		return ywMemberRoleDao.deleteYwMemberRole(id);
	}

	@Override
	public int findsum(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return ywMemberRoleDao.findsum(map);
	}

	@Override
	public YwMemberRole queryByLevelname(String levelname) {
		// TODO Auto-generated method stub
		return ywMemberRoleDao.queryByLevelname(levelname);
	}

	


}
