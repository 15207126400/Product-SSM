package com.yunwei.product.infobase.serivce.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.common.exception.BizException;
import com.yunwei.common.util.Base32;
import com.yunwei.common.util.DateUtil;
import com.yunwei.common.util.RedisClientUtil;
import com.yunwei.context.payment.util.WxModelUtil;
import com.yunwei.context.sys.usercenter.model.YwUser;
import com.yunwei.context.sys.usercenter.model.YwUserCustomerXcx;
import com.yunwei.context.sys.usercenter.service.YwUserCustomerXcxService;
import com.yunwei.context.sys.usercenter.service.YwUserService;
import com.yunwei.product.common.dao.YwMemberDao;
import com.yunwei.product.common.model.YwCoupon;
import com.yunwei.product.common.model.YwCouponCollectiondetails;
import com.yunwei.product.common.model.YwDistributor;
import com.yunwei.product.common.model.YwMember;
import com.yunwei.product.common.model.YwMemberRole;
import com.yunwei.product.common.model.YwPoints;
import com.yunwei.product.common.model.YwWallet;
import com.yunwei.product.infobase.service.YwCouponCollectiondetailsService;
import com.yunwei.product.infobase.service.YwDistributorService;
import com.yunwei.product.infobase.service.YwMemberRoleService;
import com.yunwei.product.infobase.service.YwMemberService;
import com.yunwei.product.infobase.service.YwPointsService;
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
	@Autowired
	private YwUserCustomerXcxService ywUserCustomerXcxService;
	@Autowired
	private YwDistributorService ywDistributorService;
	@Autowired
	private YwPointsService ywPointsService;
	@Autowired
	private YwMemberRoleService ywMemberRoleService;
	@Autowired
	private YwCouponCollectiondetailsService ywCouponCollectiondetailsService;
	
	//向微信服务器请求openid的url
	public final static String OPENID_URL = "https://api.weixin.qq.com/sns/jscode2session";
	
	//默认微信头像
	public final static String DEFAULT_AVATAR_URL = "https://www.qhzhlkj.com/files/image/qhz/wxChat/member/default_avatarUrl.png";
	
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
	
	@Override
	public Map<String, Object> gainedByOpenid(String wx_appid, String code) {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuilder sb = new StringBuilder(OPENID_URL);	
		
		try {
			if(StringUtils.isNotBlank(wx_appid)){
				YwUserCustomerXcx ywUser = new YwUserCustomerXcx();
				ywUser.setApp_id(wx_appid);
				ywUser = ywUserCustomerXcxService.query(ywUser);
				sb.append(String.format("?appid=%s", ywUser.getApp_id()));			
				sb.append(String.format("&secret=%s", ywUser.getApp_secret()));
			}else{
				//使用String.format时要加上%s才能使变量值接上前面的字符串
				sb.append(String.format("?appid=%s", WxModelUtil.APPID));				
				sb.append(String.format("&secret=%s", WxModelUtil.SECRET));
			}
			sb.append(String.format("&js_code=%s", code));
			sb.append(String.format("&grant_type=%s", "authorization_code"));
			//发送请求
			String rec=null;
	        HttpGet get=new HttpGet(sb.toString());
	        
	            HttpResponse resp = HttpClients.createDefault().execute(get);
	            HttpEntity entity=resp.getEntity();
	            rec= EntityUtils.toString(entity);
	            
	            JSONObject json = JSONObject.fromObject(rec);		//将字符串转为json对象
	            Object objOpenid = json.get("openid");
	            Object objSessionKey = json.get("session_key");
	            //格式转换
	            String openid = objOpenid.toString();
	            String session_key = objSessionKey.toString();
	            
	            //根据openid查询用户信息
	            YwMember ywMember = ywMemberDao.queryByOpenid(openid);
	            if(null != ywMember){
	            	//更新实体信息
	            	ywMember.setSessionKey(session_key);
	            	ywMember.setCreateTime(new Date());
	            	ywMemberDao.updateByOpenid(ywMember);
	            	
	            	//生成存储实体对象的key
	            	String key = RedisClientUtil.getModelKeyAlias("YwMember" , openid);
	            	Boolean result = RedisClientUtil.exists(key);
		            if(result){
	            		//从缓存中取
		            	YwMember member = (YwMember)RedisClientUtil.getObj(key);
		            	map.put("phone", member.getPhone());
		            	map.put("openid", member.getOpenid());
		            	map.put("level", member.getLevel());
		            	map.put("is_auditing", member.getIs_auditing());
		            	map.put("msg", "redis缓存中存在该openid");
	            	} else {
            			ywMember.setSessionKey(session_key);
		            	ywMember.setCreateTime(new Date());
		            	ywMemberDao.updateByOpenid(ywMember);
		            	map.put("phone", ywMember.getPhone());
		            	map.put("openid", ywMember.getOpenid());
		            	map.put("level", ywMember.getLevel());
		            	map.put("is_auditing", ywMember.getIs_auditing());
		            	//插入缓存中
		            	RedisClientUtil.add(key, ywMember);
		            	map.put("msg", "数据库中存在该openid");
	            	}
	            } else {
        			YwMember member = new YwMember();
        			member.setOpenid(openid);
        			member.setSessionKey(session_key);
        			member.setCreateTime(new Date());
        			member.setAc_price("0.00");
        			member.setLevel("1");
        			member.setIs_auditing("0");
        			// 存储用户默认昵称及默认头像
    				String name = "用户";
    				int math = (int)((Math.random()*9+1)*1000);
    				String newName = name + math;
    				member.setNickname(Base32.encode(newName));
    				member.setAvatarUrl(DEFAULT_AVATAR_URL);	
	            	ywMemberDao.insert(member);
	            	String key = RedisClientUtil.getModelKeyAlias("YwMember", openid);
	            	map.put("phone", member.getPhone());
	            	map.put("openid", openid);
	            	map.put("level", member.getLevel());
	            	map.put("is_auditing", member.getIs_auditing());
	            	//插入缓存中
	            	RedisClientUtil.add(key, member);
	            	map.put("msg", "数据库中没有该用户,插入新的openid");
        		}
		} catch (Exception e) {
			throw new BizException(e);
		}
		return map;
	}
	
	
	@Override
	public Map<String, Object> saveByNickname(YwMember ywMember) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(null != ywMember){
			//获取数据库中用户信息
			YwMember user = ywMemberDao.queryByOpenid(ywMember.getOpenid());		
			//昵称符号进行编码,取出时进行解码
			String name = Base32.encode(ywMember.getNickname());		
			user.setNickname(name);
			user.setAvatarUrl(ywMember.getAvatarUrl());
			ywMemberDao.addUserInfo(user);
			//插入缓存中
			String key = RedisClientUtil.getModelKeyAlias("YwMember", ywMember.getOpenid());
        	RedisClientUtil.add(key, user);
        	// 解码得到字符串
			String newname = Base32.decode(name);
			map.put("nickName", newname);
			map.put("avatarUrl", ywMember.getAvatarUrl());
		}
		return map;
	}
	
	@Override
	public Map<String, Object> queryUserInfo(YwMember ywMember) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 查询会员信息
		YwMember user = ywMemberDao.queryByOpenid(ywMember.getOpenid());
		//获取用户手机号
		if(user.getPhone() != null){
			map.put("phone", user.getPhone());
		}
		
		// 查询用户是否注册为分销商 , 返回页面 , 页面根据该状态显示对应的界面状态(个人中心需用到)
		YwDistributor ywDistributor = new YwDistributor();
		ywDistributor.setDis_id(ywMember.getOpenid());
		YwDistributor distributor = ywDistributorService.query(ywDistributor);
		// 如果分销商信息为空 , 更改分销商申请状态为未申请
		if(null == distributor){
			map.put("dis_status", "0");
		}else{
			// 如果分销商信息不为空 , 取数据库对应的分销商申请状态
			map.put("dis_status", distributor.getDis_status());
		}
		
		// 获取用户会员等级
		String level = user.getLevel();
		// 获取该用户积分(新增积分中心 , 积分从积分中心表中取)
		YwPoints ywPoints = new YwPoints();
		ywPoints.setUser_id(ywMember.getOpenid());
		ywPoints = ywPointsService.query(ywPoints);
		int points = 0;
		if(ywPoints != null){
			points = Integer.valueOf(ywPoints.getPoints_totals());
		}
		
		// 根据会员级别 , 取对应的折扣福利
		YwMemberRole ywMemberRole = ywMemberRoleService.queryById(Integer.valueOf(level));
		if((ywMemberRole.getLevel()).equals("1")){
			map.put("nickname", Base32.decode(user.getNickname()));
			map.put("avatarUrl", user.getAvatarUrl());
			if(StringUtils.equals(user.getAvatarUrl(), DEFAULT_AVATAR_URL)){
				boolean authority = false;
				map.put("authority", authority);
			}else{
				boolean authority = true;
				map.put("authority", authority);
			}
			map.put("msg", "当前用户不是会员");
			boolean bean = false;
			map.put("bean", bean);
		}else{
			List<YwMemberRole> YwMemberrole = ywMemberRoleService.queryList();
			//小程序每次查询用户身份信息时首先判断该用户积分是否满足升级条件
			for (YwMemberRole YwMemberRole2 : YwMemberrole) {
				if(Integer.valueOf(user.getAc_price()) >= Integer.valueOf(YwMemberRole2.getLevelpoints())){
					//同时修改数据库对应的用户身份
					user.setLevel(String.valueOf(YwMemberRole2.getId()));
					user.setDiscount(Float.valueOf(YwMemberRole2.getDiscount()));
					ywMemberDao.updateByLevel(user);
				}
			}
			//获取会员名称对应的会员权限表id , 返回小程序用于标识不同等级会员的不同展示信息
			float discount = user.getDiscount()*10;	//享受折扣  例如:0.99*10=9.9折
			for (YwMemberRole YwMemberRole2 : YwMemberrole) {
				if(user.getLevel().equals(String.valueOf(YwMemberRole2.getId()))){
					map.put("level", YwMemberRole2.getLevel());
					map.put("levelname", YwMemberRole2.getLevelname());
				}
			}
			// 会员当前积分
			map.put("points", points);		
			// 会员享受折扣
			map.put("discount", discount);		
			map.put("realname", user.getRealname());
			map.put("phone", user.getPhone());
			map.put("address", user.getAddress());
			map.put("shopname", user.getShopname());
			map.put("nickname", Base32.decode(user.getNickname()));
			map.put("avatarUrl", user.getAvatarUrl());
			if(StringUtils.equals(user.getAvatarUrl(), DEFAULT_AVATAR_URL)){
				boolean authority = false;
				map.put("authority", authority);
			}else{
				boolean authority = true;
				map.put("authority", authority);
			}
			boolean bean = true;
			map.put("bean", bean);
		}
		map.put("is_auditing", user.getIs_auditing());
		
		return map;
	}
	
	@Override
	public int registerByMember(YwMember ywMember , String auditing) {
		Map<String, Object> map = new HashMap<String, Object>();
		YwMember user = ywMemberDao.queryByOpenid(ywMember.getOpenid());
		int num = 0;
		
		// 需要审核 , 会员级别为'游客' , 审核状态为待审核 , 后台审核通过后更改状态
		if(StringUtils.equals("2", auditing)){	
			user.setRealname(ywMember.getRealname());
			user.setPhone(ywMember.getPhone());
			user.setAddress(ywMember.getAddress());
			user.setIs_auditing("1");
			user.setShopname(ywMember.getShopname());
			num = ywMemberDao.updateByRegUser(user);
			//num = super.update(user);
			// 注册成功后修改对应数据
			if(num > 0){
				// 修改用户会员名称,默认会员等级为1的会员级别(游客)
				YwMemberRole role = ywMemberRoleService.queryByLevelname("1");
				user.setLevel(String.valueOf(role.getId()));
				ywMemberDao.updateByLevelname(user);
			}
		}else {		// 不需要审核
			user.setRealname(ywMember.getRealname());
			user.setPhone(ywMember.getPhone());
			user.setAddress(ywMember.getAddress());
			user.setIs_auditing("2");
			user.setShopname(ywMember.getShopname());
			num = ywMemberDao.updateByRegUser(user);
			// 注册成功后修改对应数据
			if(num > 0){
				// 修改用户会员名称,注册成功后为普通会员,会员级别为2
				YwMemberRole role = ywMemberRoleService.queryByLevelname("2");
				user.setLevel(String.valueOf(role.getId()));
				ywMemberDao.updateByLevelname(user);
			}
		}
		
		
		// 创建用户积分
		YwPoints ywPoints = new YwPoints();
		ywPoints.setUser_id(ywMember.getOpenid());
		ywPoints.setPoints_totals("0");
		ywPoints.setPoints_createtime(new Date());
		ywPointsService.insert(ywPoints);
		
		// 赠送优惠券
		//ywCouponCollectiondetailsService.giftByCoupon(1, openid);
		
		return num;
	}

	

}
