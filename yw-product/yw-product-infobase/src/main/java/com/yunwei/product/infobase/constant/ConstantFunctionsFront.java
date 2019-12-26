package com.yunwei.product.infobase.constant;
/**
 * 
* @ClassName: ConstantFunctionsFront 
* @Description: 小程序接口实体类
* @author 晏飞
* @date 2018年3月7日 上午11:14:40 
*
 */
public class ConstantFunctionsFront {
	/** 启恒智沙龙注册接口 **/
	public static final String QHZ_SL001 = "QHZ_SL001.json";	//根据openid查询用户
	public static final String QHZ_SL002 = "QHZ_SL002.json";	//保存用户信息
	public static final String QHZ_SL003 = "QHZ_SL003.json";	//报名提交账户信息(补全新的信息)
	public static final String QHZ_SL011 = "QHZ_SL011.json";	//查询所有课程信息
	public static final String QHZ_SL021 = "QHZ_SL021.json";	//创建订单
	public static final String QHZ_SL022 = "QHZ_SL022.json";	//查询用户订单(报名记录)
	public static final String QHZ_SL023 = "QHZ_SL023.json";	//订单支付结算
	public static final String QHZ_SL024 = "QHZ_SL024.json";	//异步发送订单支付成功模板消息
	public static final String QHZ_SL031 = "QHZ_SL031.json";	//会议综合查询(如果已有签到会议返回签到信息,否则返回当天会议信息列表)
	public static final String QHZ_SL032 = "QHZ_SL032.json";	//会议签到
	public static final String QHZ_SL033 = "QHZ_SL033.json";	//查看入场码
	public static final String QHZ_SL041 = "QHZ_SL041.json";	//根据条件查询文章列表信息
	public static final String QHZ_SL042 = "QHZ_SL042.json";	//查询指定文章信息
	
	/**轮播图接口(00000)**/
	public static final String YW_CAL_00000 = "YW_CAL_00000.json";	//首页轮播图
	
	/** 用户模块接口 **/
	public static final String YW_OPENID_00000 = "YW_OPENID_00000";	//获取用户唯一标示Openid
	public static final String YW_OPENID_00001 = "YW_OPENID_00001";	//保存用户微信信息(昵称,头像)
	public static final String YW_OPENID_00002 = "YW_OPENID_00002";	//注册会员
	public static final String YW_OPENID_00003 = "YW_OPENID_00003";	//会员信息查询(包括积分)
	public static final String YW_OPENID_00004 = "YW_OPENID_00004";	//获取用户信息 , 计算折扣
	public static final String YW_OPENID_00005 = "YW_OPENID_00005";	//获取用户微信绑定手机号码
	public static final String YW_OPENID_00006 = "YW_OPENID_00006";	//修改用户基本信息
	
	/**用户签到模块(新接入)**/
	public static final String YW_SIGN_00001 = "YW_SIGN_00001";	//查询用户签到状态
	public static final String YW_SIGN_00002 = "YW_SIGN_00002";	//用户签到
	
	/**用户积分模块(新接入)**/
	public static final String YW_POINT_00001 = "YW_POINT_00001";	//查询用户积分明细
	public static final String YW_POINT_00002 = "YW_POINT_00002";	//查询积分分页
	public static final String YW_POINT_00004 = "YW_POINT_00004";	//查询积分商品
	public static final String YW_POINT_00005 = "YW_POINT_00005";	//查询积分兑换记录
	public static final String YW_POINT_00007 = "YW_POINT_00007";	//积分兑换优惠券
	
	/** 地址模块接口 **/
	public static final String YW_ADDRESS_00002 = "YW_ADDRESS_00002";	//展示地址信息
	public static final String YW_ADDRESS_00003 = "YW_ADDRESS_00003";	//根据id查询地址信息
	public static final String YW_ADDRESS_00004 = "YW_ADDRESS_00004";	//新增地址信息
	public static final String YW_ADDRESS_00005 = "YW_ADDRESS_00005";	//修改地址信息
	public static final String YW_ADDRESS_00006 = "YW_ADDRESS_00006";	//删除地址信息
	public static final String YW_ADDRESS_00007 = "YW_ADDRESS_00007";	//修改默认地址
	
	/** 优惠券模块接口 **/
	public static final String YW_COUPON_00001 = "YW_COUPON_00001";		//优惠券信息查询（根据场景的类型展示）
	public static final String YW_COUPON_00002 = "YW_COUPON_00002";		//优惠券领取信息插入
	public static final String YW_COUPON_00003 = "YW_COUPON_00003";		//优惠券领取信息查询
	public static final String YW_COUPON_00004 = "YW_COUPON_00004";		//优惠券默认使用信息查询(优惠金额最大的优惠券)
	public static final String YW_COUPON_00005 = "YW_COUPON_00005";		//优惠券领取后是否满足使用条件信息查询
	public static final String YW_COUPON_00006 = "YW_COUPON_00006";		//使用优惠券
	
	
	/** 产品模块接口(00001-00099)**/
	public static final String YW_POT_00001 = "YW_POT_00001.json";	//首页热门商品展示
	public static final String YW_POT_00002 = "YW_POT_00002.json";	//首页热门商品图片展示1
	public static final String YW_POT_00003 = "YW_POT_00003.json";	//首页热门商品图片展示2
	public static final String YW_POT_00004 = "YW_POT_00004.json";	//首页公告栏展示
	public static final String YW_POT_00010 = "YW_POT_00010.json";	//商品详情展示
	public static final String YW_POT_00011 = "YW_POT_00011.json";	//查询sku最终定价
	public static final String YW_POT_00012 = "YW_POT_00012.json";	//收藏(取消收藏)商品
	public static final String YW_POT_00013 = "YW_POT_00013.json";	//加入购物车
	public static final String YW_POT_00014 = "YW_POT_00014.json";	//第一次分享获赠优惠券
	public static final String YW_POT_00015 = "YW_POT_00015.json";	//结算商品
	public static final String YW_POT_00016 = "YW_POT_00016.json";	//查看已收藏的商品
	public static final String YW_POT_00017 = "YW_POT_00017.json";	//删除已收藏的商品
	public static final String YW_POT_00018 = "YW_POT_00018.json";	//查看购物车商品
	public static final String YW_POT_00019 = "YW_POT_00019.json";	//删除购物车商品
	public static final String YW_POT_00020 = "YW_POT_00020.json";	//购物车创建订单时,删除购物车选中项
	public static final String YW_POT_00021 = "YW_POT_00021.json";	//查询商品页订单信息
	public static final String YW_POT_00022 = "YW_POT_00022.json";	//订单页面默认地址信息展示
	public static final String YW_POT_00023 = "YW_POT_00023.json";	//订单支付结算
	public static final String YW_POT_00024 = "YW_POT_00024.json";	//订单支付成功回调
	public static final String YW_POT_00025 = "YW_POT_00025.json";	//支付成功计入积分
	public static final String YW_POT_00026 = "YW_POT_00026.json";	//查询支付订单信息
	public static final String YW_POT_00027 = "YW_POT_00027.json";	//确认收货 ---> 更改订单信息状态为已完成(7)
	public static final String YW_POT_00028 = "YW_POT_00028.json";	//商品搜索
	public static final String YW_POT_00029 = "YW_POT_00029.json";	//查询特定状态的支付分页信息查询
	public static final String YW_POT_00030 = "YW_POT_00030.json";	//生成二维码
	public static final String YW_POT_00031 = "YW_POT_00031.json";	//查询订单详情
	public static final String YW_POT_00050 = "YW_POT_00050.json";	//根据类型查询商品列表
	public static final String YW_POT_00051 = "YW_POT_00051.json";	//根据类型查询商品列表(分页)
	public static final String YW_POT_00052 = "YW_POT_00052.json";	//根据机构编号查询该机构今日成交金额,订单量及总共成交金额及订单量
	public static final String YW_POT_00053 = "YW_POT_00053.json";	//批量修改订单状态
	
	/**
	 * 机构商品模块
	 */
	public static final String YW_POT_00054 = "YW_POT_00054.json";	//根据类型查询商品列表
	public static final String YW_POT_00055 = "YW_POT_00055.json";	//查询商品总数
	public static final String YW_POT_00056 = "YW_POT_00056.json";	//首页新品展示
	public static final String YW_POT_00057 = "YW_POT_00057.json";	//首页热门展示(大图三张)
	public static final String YW_POT_00058 = "YW_POT_00058.json";	//首页主打展示(大图三张)
	public static final String YW_POT_00059 = "YW_POT_00059.json";	//商品详情
	public static final String YW_POT_00060 = "YW_POT_00060.json";	//根据用户选择的规格属性获取sku表最终价格
	public static final String YW_POT_00061 = "YW_POT_00061.json";	//修改商品收藏状态
	public static final String YW_POT_00062 = "YW_POT_00062.json";	//查看收藏页商品
	public static final String YW_POT_00063 = "YW_POT_00063.json";	//删除已收藏
	public static final String YW_POT_00064 = "YW_POT_00064.json";	//按照商品标题搜索对应商品
	public static final String YW_POT_00065 = "YW_POT_00065.json";	//生成二维码
	
	/*** 分销模块接口（00100-00199） ***/
	public static final String YW_DIS_00100 = "YW_DIS_00100.json"; // 申请分销商
	public static final String YW_DIS_00101 = "YW_DIS_00101.json"; // 分销商信息查询
	public static final String YW_DIS_00102 = "YW_DIS_00102.json"; // 上级分销商查询
	public static final String YW_DIS_00103 = "YW_DIS_00103.json"; // 下级分销商信息查询
	public static final String YW_DIS_00104 = "YW_DIS_00104.json"; // 分销商佣金余额
	public static final String YW_DIS_00105 = "YW_DIS_00105.json"; // 分销商订单总数
	public static final String YW_DIS_00106 = "YW_DIS_00106.json"; // 分销商提现总数
	public static final String YW_DIS_00107 = "YW_DIS_00107.json"; // 分销商提现明细
	public static final String YW_DIS_00108 = "YW_DIS_00108.json"; // 按指定类型查询佣金订单
	public static final String YW_DIS_00109 = "YW_DIS_00109.json"; // 分销商品支付成功后处理
	public static final String YW_DIS_00110 = "YW_DIS_00110.json"; // 页面初始化默认访问所有佣金订单数据
	public static final String YW_DIS_00111 = "YW_DIS_00111.json"; // 查询分销佣金记录列表
	public static final String YW_DIS_00113 = "YW_DIS_00113.json"; // 查询数据分页信息
	
	
	/*** 团购模块接口（00200-00399） ***/
	public static final String YW_TEAM_00200 = "YW_TEAM_00200.json"; // 团购活动商品列表信息查询
	public static final String YW_TEAM_00201 = "YW_TEAM_00201.json"; // 添加开团信息
	public static final String YW_TEAM_00202 = "YW_TEAM_00202.json"; // 修改开团信息
	public static final String YW_TEAM_00203 = "YW_TEAM_00203.json"; // 添加参团信息
	public static final String YW_TEAM_00204 = "YW_TEAM_00204.json"; // 修改参团信息
	public static final String YW_TEAM_00205 = "YW_TEAM_00205.json"; // 开团列表信息查询
	public static final String YW_TEAM_00206 = "YW_TEAM_00206.json"; // 团购活动商品列表分页信息查询
	public static final String YW_TEAM_00207 = "YW_TEAM_00207.json"; // 判断开团是否成功
	public static final String YW_TEAM_00208 = "YW_TEAM_00208.json"; // 参团列表信息查询
	public static final String YW_TEAM_00209 = "YW_TEAM_00209.json"; // 参团信息查询
	public static final String YW_TEAM_00210 = "YW_TEAM_00210.json"; // 开团信息查询
	public static final String YW_TEAM_00211 = "YW_TEAM_00211.json"; // 团购活动信息查询
	
	/*** 秒杀模块接口（00400-00499） ***/
	public static final String YW_SECKILL_00400 = "YW_SECKILL_00400.json"; 	// 秒杀活动时间列表信息查询
	public static final String YW_SECKILL_00401 = "YW_SECKILL_00401.json"; 	// 根据时间点查询秒杀活动商品数据
	public static final String YW_SECKILL_00402 = "YW_SECKILL_00402.json";// 秒杀记录信息修改
	public static final String YW_SECKILL_00403 = "YW_SECKILL_00403.json";// 秒杀记录信息查询
	public static final String YW_SECKILL_00404 = "YW_SECKILL_00404.json";// 秒杀活动信息查询
	
	/*** 商品分类接口（00600-00699） ***/
	public static final String YW_CLA_00600 = "YW_CLA_00600.json"; // 分类列表查询
	public static final String YW_CLA_00601 = "YW_CLA_00601.json"; // 分类商品查询
	public static final String YW_CLA_00602 = "YW_CLA_00602.json"; // 查询该分类是否有二级菜单
	public static final String YW_CLA_00603 = "YW_CLA_00603.json"; // 查询特定状态的分类分页信息查询
	public static final String YW_CLA_00604 = "YW_CLA_00604.json"; // 分类二级菜单查询
	public static final String YW_CLA_00605 = "YW_CLA_00605.json"; // 查询特定状态的分类二级菜单分页信息查询
	
	
	/**支付模块接口(00800-00899)**/
	public static final String YW_OFFLINE_00001 = "YW_OFFLINE_00001.json"; // 线下订单生成
	public static final String YW_OFFLINE_00002 = "YW_OFFLINE_00002.json"; // 线下订单支付记录列表查询
	public static final String YW_OFFLINE_00003 = "YW_OFFLINE_00003.json"; // 线下订单支付记录分页
	
	/**提现模块接口(00900-00999)**/
	public static final String YW_DIS_00900 = "YW_DIS_00900.json"; 	// 提现明细页面默认查询全部
	public static final String YW_DIS_00901 = "YW_DIS_00901.json"; 	// 按指定状态查询提现明细
	public static final String YW_DIS_00902 = "YW_DIS_00902.json";  // 查询提现记录列表
	public static final String YW_DIS_00903 = "YW_DIS_00903.json";  // 提现申请
	
	/**钱包模块接口(01000-01099)**/
	public static final String YW_DIS_01000 = "YW_DIS_01000.json"; 	// 查询钱包余额
	
	/**商家模块信息查询接口**/
	public static final String YW_USER_00000 = "YW_USER_00000.json"; 	// 用户信息查询
	
	/**  小程序配置模块接口  **/
	public static final String YW_CONFIG_00000 = "YW_CONFIG_00000.json";// 小程序配置信息查询
	
	/** 机构模块接口 **/
	public static final String YW_BRAN_00000 = "YW_BRAN_00000.json";	// 机构列表查询
	public static final String YW_BRAN_00001 = "YW_BRAN_00001.json";	// 查询机构城市信息
	public static final String YW_BRAN_00002 = "YW_BRAN_00002.json";	// 根据机构编号查询机构信息
	
	
	/** 基本配置接口 **/
	public static final String YW_BASE_00000 = "YW_BASE_00000.json";	// 基本配置列表查询
	
	
}
