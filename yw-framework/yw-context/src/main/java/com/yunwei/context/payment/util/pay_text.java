package com.yunwei.context.payment.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONObject;
import com.yunwei.common.util.DateUtil;
import com.yunwei.common.util.HttpClientUtil;
import com.yunwei.context.payment.support.WxPayService;

public class pay_text {
	/**
	 * 生成统一下单
	 * @throws Exception 
	 */
/*public static void main1(String[] args) throws Exception {	
		String appid = "";    //微信小程�?--》�?�开发�?�ID�?
		String mch_id = "";    //商户号，将该值赋值给partner
		String key = "";    //微信支付商户平台登录�?--》�?�API安全�?--》�?�API密钥�?--“设置密钥�?�（设置之后的那个�?�就是partnerkey�?32位）
	    String openid = "";    //openid
	    String ip = "";    //ip地址
	    String body = "测试"; //描述
		int total_fee = 1;     //支付金额
		Date data = new Date();
	    String notify_url = "http://www.weixin.qq.com/wxpay/pay.php";   //回调链接
		String out_trade_no = "SH" + DateUtil.dateFormatSSS.format(data); 
		
		System.out.println("支付订单�?:"+out_trade_no);
		  
	    Map<Object, Object> map=WeiXinAtcion.me.weixinPlay(mch_id, appid, key, openid, total_fee, out_trade_no, notify_url, body, ip);
        System.out.println("paySign:"+map.get("paySign"));
        System.out.println("timeStamp:"+map.get("timeStamp"));
        System.out.println("nonceStr:"+map.get("nonceStr"));
        System.out.println("package:"+map.get("package"));
	}

	*//**
	 * 生成统一下单
	 * 
	 * @throws Exception
	 *//*
	public static void main2(String[] args) throws Exception {
		String out_trade_no = ""; // �?款订�?
		int all_total_fee = 1; // 订单金额
		int refund_fee = 0; // �?款金�?
		String appid = ""; // 微信小程�?--》�?�开发�?�ID�?
		String mch_id = ""; // 商户号，将该值赋值给partner
		String key = ""; // 微信支付商户平台登录�?--》�?�API安全�?--》�?�API密钥�?--“设置密钥�?�（设置之后的那个�?�就是partnerkey�?32位）

		Map<String, String> refundmap = WeiXinAtcion.me.wechatRefund(mch_id, appid, key, out_trade_no, all_total_fee, refund_fee);
		if (refundmap.get("return_code").equals("SUCCESS")) {
			if (refundmap.get("result_code").equals("FAIL")) {
				System.out.println("�?款失�?:原因" + refundmap.get("err_code_des"));
			} else {
				System.out.println("�?款成�?");
			}
		} else {
			System.out.println("�?款失�?:原因" + refundmap.get("return_ms"));

		}
	}*/
	public static void main(String[] args) throws Exception {
		fun();
	}
	
	public static void fun(){
		WxPayService wxPayService = new WxPayService();
		String appid = "wxe4529fae86cb2d68";
		String mch_id = "1391026002";
		String nonce_str = WXUtil.generate();
		
		//查账日期
		Date date = DateUtil.parse("2019-07-04 00:00:00", DateUtil.DATE_TIME_FORMAT);
		String datetime = DateUtil.format(date, DateUtil.DATE_FORMAT_NO_DELIMITER);
		byte[] bytes = wxPayService.downloadBill(datetime, "ALL", "");
		System.out.println(bytes);
        
	}
	
	public static void main3() throws Exception {
		
//		Map<String, String> map = WeiXinAtcion.wechatRefund("18050919390001" , WXUtil.generate(), 1 , 1 );
//		
//		System.out.println(map);
		HttpClientUtil httpClientUtil = HttpClientUtil.getInstance();
		/*HttpClient client = new HttpClient();
		PostMethod post = new PostMethod("http://api.sms.cn/sms/"); 
		post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");//在头文件中设置转码
		NameValuePair[] data ={ new NameValuePair("uid", "send"),new NameValuePair("uid", "本站用户名"),new NameValuePair("pwd", "接口安全秘钥"),new NameValuePair("mobile","手机号码"),new NameValuePair("content","验证码：8888【sms】")};
		post.setRequestBody(data);

		client.executeMethod(post);
		Header[] headers = post.getResponseHeaders();
		int statusCode = post.getStatusCode();
		System.out.println("statusCode:"+statusCode);
		for(Header h : headers)
		{
			System.out.println(h.toString());
		}
		String result = new String(post.getResponseBodyAsString().getBytes("utf-8")); 
		System.out.println(result); //打印返回消息状态
		post.releaseConnection();*/
		 Map<String, Object> params = new HashMap<String, Object>();
		 params.put("ac", "send");
		 params.put("uid", "zjh131420");
		 params.put("pwd", "d91564d427d5528118c29a529e30a90d");
		 params.put("template", "430177");
		 params.put("mobile", "17720504555,19945018645");
		 JSONObject jsonObject = new JSONObject();
		 jsonObject.put("order_sn", "1234567890123，测试编码方式，");
		 params.put("content", jsonObject);
		 byte[]  bs = httpClientUtil.httpPost("http://api.sms.cn/sms/", params, null);
		 String result = new String(bs,"GBK"); 
		 System.out.println(JSONObject.parseObject(result).get("message")); //打印返回消息状态
	}

}
