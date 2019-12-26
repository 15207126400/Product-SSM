package com.yunwei.product.common.dao;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.product.common.model.YwMember;
/**
 * 
* @ClassName: ShopDetailDao 
* @Description: TODO(用户信息dao层接口) 
* @author 晏飞
* @date 2017年11月18日 下午5:09:49 
*
 */

public interface YwMemberDao extends IBaseDao<YwMember>{
	//小程序查询所有用户信息
	public List<YwMember> queryAll(Map<String, Object> map);
	
	//查看指定openid
	public List<YwMember> queryAllOpenId(Map<String, Object> map);

	//新增openid
	public int addOpenId(Object openid);
	
	//新增用户信息
	public int addUserInfo(YwMember ywMember);
	
	//查询openid存在数量
	public int queryByOpenidCount(String openid);
	
	//查询指定openid
	public YwMember queryByOpenid(String openid);
	
	//修改数据库openid信息
	public int updateByOpenid(YwMember ywMember);
	
	//支付成功后计算积分
	public int updateByPoints(YwMember ywMember);
	
	//查询会员总数
	public int findsum(Map<String, Object> map);
	
	//修改用户会员等级
	public int updateByLevel(YwMember ywMember);
	
	//修改注册会员真实信息
	public int updateByRegUser(YwMember ywMember);
	
	//修改会员名称
	public int updateByLevelname(YwMember ywMember);
}
