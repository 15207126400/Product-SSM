package com.yunwei.context.sms;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.yunwei.common.util.DateUtil;
import com.yunwei.common.util.HttpRequestUtil;
import com.yunwei.common.util.RedMapleSmsUtil;
import com.yunwei.context.sms.support.WxSmsService;


public class YwSmsTest {

	private static int appid = 1400095371;
	private static String appkey = "99c6a41d739bd0690a322d23a180eddd";
	// 需要发送短信的手机号码
	private static String[] phoneNumbers = {"18202772194"};

	// 短信模板ID，需要在短信应用中申请
	private static int templateId = 125457; // NOTE: 这里的模板ID`7839`只是一个示例，真实的模板ID需要在短信控制台中申请

	// 签名
	private static String smsSign = "启恒智互联科技"; // NOTE: 这里的签名"腾讯云"只是一个示例，真实的签名需要在短信控制台中申请，另外签名参数使用的是`签名内容`，而不是`签名ID`
	
	public static void main(String[] args) {
		
		/*try {
		 	//java自带短信sdk实现短信发送
			String[] params = {"5678"};
		    SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
		    // 签名参数未提供或者为空时，会使用默认签名发送短信
		    SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumbers[0],templateId, params, smsSign, "22", "123");  
		    System.out.print(result);
		    
		} catch (Exception e) {
			e.printStackTrace();
		} */

		//红枫叶短信发送
		/*StringBuffer sb = new StringBuffer();
		sb.append("&mobile=15207126400");
		sb.append("&content=【启恒智互联科技有限公司】军运会即将到来,与您一起共创未来!" + DateUtil.getCurrentTime(DateUtil.DATE_TIME_FORMAT));
		sb.append("&sendTime=");
		sb.append("&extno=");
		
		String result = RedMapleSmsUtil.send(RedMapleSmsUtil.SEND_PATH, sb);
		System.out.println("短信发送结果 : " + result);*/
		
		String mobile = "15207126400";				
		String content = "【启恒智】恭喜您报课成功!";				
		String sendTime = "2019-07-01 17:13:00";
		
		String url = "http://120.24.223.146:8888/sms.aspx";
		StringBuffer sb = new StringBuffer();
		sb.append("&mobile="+mobile);
		sb.append("&content="+content);
		sb.append("&sendTime="+"");
		sb.append("&extno="+"");
		//红枫叶短信工具类发送
		String result = RedMapleSmsUtil.send(1,mobile,content,"");
		System.out.println("短信发送结果 : " + result);
		
		//营销短信
		//String param = "action=send&userid=3454&account=qhzsms&password=123456&mobile=15207126400,18202772194&content=【启恒智】军运会即将到来,与您一起共创未来!&sendTime=&extno=";
		//节日祝福
		//String param = "action=send&userid=8543&account=qhz&password=sms123456&mobile=15207126400,18202772194&content=【启恒智】恭喜您注册报名成功!&sendTime=&extno=";
		/*String param = "action=send&userid=8543&account=qhz&password=sms123456&mobile="+mobile+"&content="+conent+"&sendTime=2019-07-01 17:13:00&extno=";
		System.out.println("param : " + param);
		String result = HttpRequestUtil.sendPost(url, param);
		System.out.println("短信发送结果 : " + result);*/
	}
}
