package com.yunwei.product.infobase.service;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.serivce.IBaseSerivce;
import com.yunwei.product.common.model.YwMember;

/**
 * 
* @ClassName: ShopDetailDao 
* @Description: TODO(用户接口) 
* @author 晏飞
* @date 2017年11月18日 下午5:09:49 
*
 */
public interface YwMemberService extends IBaseSerivce<YwMember>{
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
		
		/**
		 * 向微信请求获取用户的openid
		*
		*@param wx_appid	小程序的appid , 切换数据源
		*@param code		用户登录小程序时的临时码 , 换取openid的必备属性
		*@return			
		*Map<String,Object>	返回数据库存储状态并返回对应的openid
		 */
		public Map<String, Object> gainedByOpenid(String wx_appid , String code);
		
		/**
		 * 微信小程序用户授权保存昵称及头像
		*
		*@param ywMember	用户对象
		*@return			
		*Map<String,Object>	返回获取到的用户昵称和头像
		 */
		public Map<String, Object> saveByNickname(YwMember ywMember);
		
		/**
		 * 查询会员信息
		*
		*@param ywMember	用户对象
		*@return
		*Map<String,Object>	返回用户的基本信息
		 */
		public Map<String, Object> queryUserInfo(YwMember ywMember);
		
		/**
		 * 注册会员
		*
		*@param ywMember	用户对象
		*@return auditing	是否需要审核标识(1,否 2,是)
		*int				0,注册失败  1,注册成功
		 */
		public int registerByMember(YwMember ywMember , String auditing);
		
		
}
