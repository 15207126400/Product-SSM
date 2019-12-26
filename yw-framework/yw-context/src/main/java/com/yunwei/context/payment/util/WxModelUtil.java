package com.yunwei.context.payment.util;

import java.util.Date;


public class WxModelUtil {
	/**
	 * 商城小程序微信端数据
	 */
	public final static String APPID = "wxe4529fae86cb2d68";    
	public final static String SECRET = "bf718aee247ccb600ac60ffa0bbe9c92";
	public final static String MCH_ID = "1391026002";    //商户号，将该值赋值给partner
	public final static String KEY = "234dfasdfdf23234kjas8ifadf239kka";    //微信支付商户平台登录�?--》�?�API安全�?--》�?�API密钥�?--“设置密钥�?�（设置之后的那个�?�就是partnerkey�?32位）
	public static String openid = "";    //openid
	public static String IP = "39.100.96.124";    //ip地址
	public static String BODY = "启恒智互联科技"; //描述
	public static int total_fee = 0;     //支付金额
	public static String notify_url = "https://xcx.whywxx.com/yw-product-infobase/YW_POT_00024.json";   //回调链接
	//public static Date data = new Date();		//订单创建时间
	//public static String out_trade_no = "SH" + DateUtil.dateFormatSSS.format(data); 
	
	/**
	 * 门店小程序微信端数据
	 */
	public final static String MD_APPID = "wxaa34b6046780985d";    
	public final static String MD_SECRET = "";		//TDDO
    
}
