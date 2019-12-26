package com.yunwei.product.common.dao;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.product.common.model.YwMember;
import com.yunwei.product.common.model.YwMemberRole;

/**
 * 
* @ClassName: ywMemberRoleDao 
* @Description: 会员权限接口 
* @author 晏飞
* @date 2018年1月24日 下午1:47:36 
*
 */
public interface YwMemberRoleDao extends IBaseDao<YwMemberRole>{
	//查询会员权限
	public List<YwMemberRole> queryAll(Map<String, Object> map);
	
	//通过id查找会员权限
	public YwMemberRole queryById(int id);
	
	//添加会员权限
	public int addYwMemberRole(YwMemberRole ywMemberRole);
	
	//修改会员权限
	public int updateYwMemberRole(YwMemberRole ywMemberRole);
	
	//删除会员权限
	public int deleteYwMemberRole(int id);
	
	//查询会员权限总数
	public int findsum(Map<String, Object> map);
	
	//根据会员名称查找
	public YwMemberRole queryByLevelname(String levelname);
	
}
