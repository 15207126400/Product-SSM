package com.yunwei.context.sms.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.qcloudsms.SmsSenderUtil;
import com.yunwei.common.middleware.wx.SisapService;
import com.yunwei.context.sms.SmsService;
import com.yunwei.context.sms.util.WxSmsUtil;

/**
 * 微信短信服务默认支持
* @ClassName: WxSmsSupport 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年6月4日 下午4:44:52 
*
 */
@Service
public class WxSmsService implements SmsService{

	// 短信应用SDK AppID
	private static final int  appid = 1400095371;
	
	// 短信应用SDK AppKey
	private static final String appkey = "99c6a41d739bd0690a322d23a180eddd";
	
	// 短信国际号(默认国内)
	private static final String nationcode = "86";
	
	// 签名
	private static final String sign = "云维科技"; // NOTE: 这里的签名"腾讯云"只是一个示例，真实的签名需要在短信控制台中申请，另外签名参数使用的是`签名内容`，而不是`签名ID`
	
	/***  url链接常量  ***/
	private final static String sms_send_single = "https://yun.tim.qq.com/v5/tlssmssvr/sendsms";   //单发短信
	private final static String sms_send_multi = "https://yun.tim.qq.com/v5/tlssmssvr/sendmultisms2";   //群发短信
	private final static String sms_callback = "https://yun.tim.qq.com/sms/smscallback";   //短信回复
	private final static String sms_pull_status_multi = "https://yun.tim.qq.com/v5/tlssmssvr/pullstatus";   //拉取短信状态
	private final static String sms_pull_status_single = "https://yun.tim.qq.com/v5/tlssmssvr/pullstatus4mobile";   //拉取单个手机短信状态
	private final static String sms_add_template = "https://yun.tim.qq.com/v5/tlssmssvr/add_template";   //添加短信模板
	private final static String sms_update_template = "https://yun.tim.qq.com/v5/tlssmssvr/mod_template";   //修改短信模板
	private final static String sms_delete_template = "https://yun.tim.qq.com/v5/tlssmssvr/del_template";   //删除短信模板
	private final static String sms_get_template = "https://yun.tim.qq.com/v5/tlssmssvr/get_template";   //短信模板状态查询
	private final static String sms_add_sign = "https://yun.tim.qq.com/v5/tlssmssvr/add_sign";   //添加短信签名
	private final static String sms_update_sign = "https://yun.tim.qq.com/v5/tlssmssvr/mod_sign";   //修改短信签名
	private final static String sms_delete_sign = "https://yun.tim.qq.com/v5/tlssmssvr/del_sign";   //删除短信模板
	private final static String sms_get_sign = "https://yun.tim.qq.com/v5/tlssmssvr/get_sign";   //短信签名状态查询
	private final static String sms_send_statistics = "https://yun.tim.qq.com/v5/tlssmssvr/pullsendstatus";   //发送数据统计
	private final static String sms_callback_statistics = "https://yun.tim.qq.com/v5/tlssmssvr/pullcallbackstatus";   //回执数据统计

	@Autowired
	private SisapService sisapService;
	
	@Override
	public Map<String, Object> sendStatistics(String begin_date, String end_date) {
		Map<String, Object> request_map = new HashMap<String, Object>();
		request_map.put("begin_date", begin_date);
		request_map.put("end_date", end_date);
		return smsExecute(sms_send_statistics, request_map);
	}

	@Override
	public Map<String, Object> callbackStatistics(String begin_date,String end_date) {
		Map<String, Object> request_map = new HashMap<String, Object>();
		request_map.put("begin_date", begin_date);
		request_map.put("end_date", end_date);
		return smsExecute(sms_callback_statistics, request_map);
	}

	@Override
	public Map<String, Object> addTemplate(String title, String text,String type) {
		
		return addTemplate(title, text, type, "");
	}

	@Override
	public Map<String, Object> addTemplate(String title, String text,String type, String remark) {
		Map<String, Object> request_map = new HashMap<String, Object>();
		request_map.put("title", title);
		request_map.put("text", text);
		request_map.put("type", type);
		request_map.put("remark", remark);
		return smsExecute(sms_add_template, request_map);
	}

	@Override
	public Map<String, Object> updateTemplate(String tpl_id, String title,String text, String type) {
		
		return updateTemplate(tpl_id, title, text, type, "");
	}

	@Override
	public Map<String, Object> updateTemplate(String tpl_id, String title,String text, String type, String remark) {
		Map<String, Object> request_map = new HashMap<String, Object>();
		request_map.put("tpl_id", tpl_id);
		request_map.put("title", title);
		request_map.put("text", text);
		request_map.put("type", type);
		request_map.put("remark", remark);
		return smsExecute(sms_update_template, request_map);
	}

	@Override
	public Map<String, Object> deleteTemplate(Integer[] tpl_id) {
		Map<String, Object> request_map = new HashMap<String, Object>();
		request_map.put("tpl_id", tpl_id);
		return smsExecute(sms_delete_template, request_map);
	}

	@Override
	public Map<String, Object> getTemplate(Integer[] tpl_id) {
		Map<String, Object> request_map = new HashMap<String, Object>();
		request_map.put("tpl_id", tpl_id);
		return smsExecute(sms_get_template, request_map);
	}

	@Override
	public Map<String, Object> getTemplate(String max, String offset) {
		Map<String, Object> request_map = new HashMap<String, Object>();
		Map<String, Object> page_map = new HashMap<String, Object>();
		page_map.put("max", max);
		page_map.put("offset", offset);
		request_map.put("tpl_page", page_map);
		return smsExecute(sms_get_template, request_map);
	}

	@Override
	public Map<String, Object> addSign(String text) {
		return addSign(text, "");
	}

	@Override
	public Map<String, Object> addSign(String text, String remark) {
		Map<String, Object> request_map = new HashMap<String, Object>();
		request_map.put("text", text);
		request_map.put("remark", remark);
		return smsExecute(sms_add_sign, request_map);
	}

	@Override
	public Map<String, Object> updateSign(String sign_id, String text) {
		
		return updateSign(sign_id, text, "");
	}

	@Override
	public Map<String, Object> updateSign(String sign_id, String text,String remark) {
		Map<String, Object> request_map = new HashMap<String, Object>();
		request_map.put("sign_id", sign_id);
		request_map.put("text", text);
		request_map.put("remark", remark);
		return smsExecute(sms_update_sign, request_map);
	}

	@Override
	public Map<String, Object> deleteSign(Integer[] sign_id) {
		Map<String, Object> request_map = new HashMap<String, Object>();
		request_map.put("sign_id", sign_id);
		return smsExecute(sms_delete_sign, request_map);
	}

	@Override
	public Map<String, Object> getSign(Integer[] sign_id) {
		Map<String, Object> request_map = new HashMap<String, Object>();
		request_map.put("sign_id", sign_id);
		return smsExecute(sms_get_sign, request_map);
	}

	@Override
	public Map<String, Object> sendSingle(String tpl_id, String[] params,String tel) {
		return sendSingle(tpl_id, params, tel, sign);
	}
	
	@Override
	public Map<String, Object> sendSingle(String tpl_id, String[] params,String tel,String sign) {
		Map<String, Object> request_map = new HashMap<String, Object>();
		request_map.put("tpl_id", tpl_id);
		request_map.put("params", params);
		request_map.put("tel", tel);
		request_map.put("sign", sign);
		return smsExecute(sms_send_single, request_map);
	}

	@Override
	public Map<String, Object> sendSingle(String msg, String tel, String type) {
		Map<String, Object> request_map = new HashMap<String, Object>();
		request_map.put("msg", msg);
		request_map.put("tel", tel);
		request_map.put("type", type);
		return smsExecute(sms_send_single, request_map);
	}

	@Override
	public Map<String, Object> senderMulti(String tpl_id, String[] params,String[] tels) {
		return senderMulti(tpl_id, params, tels, sign);
	}

	@Override
	public Map<String, Object> senderMulti(String tpl_id, String[] params,String[] tels,String sign) {
		Map<String, Object> request_map = new HashMap<String, Object>();
		request_map.put("tpl_id", tpl_id);
		request_map.put("params", params);
		request_map.put("tels", tels);
		return smsExecute(sms_send_multi, request_map);
	}
	
	@Override
	public Map<String, Object> senderMulti(String msg, String[] tels, String type) {
		Map<String, Object> request_map = new HashMap<String, Object>();
		request_map.put("msg", msg);
		request_map.put("tels", tels);
		request_map.put("type", type);
		return smsExecute(sms_send_multi, request_map);
	}

	@Override
	public Map<String, Object> pullStatusSingle(String begin_time,String end_time, String max, String mobile, String type) {
		Map<String, Object> request_map = new HashMap<String, Object>();
		request_map.put("begin_time", begin_time);
		request_map.put("end_time", end_time);
		request_map.put("max", max);
		request_map.put("mobile", mobile);
		request_map.put("type", type);
		return smsExecute(sms_pull_status_single, request_map);
	}

	@Override
	public Map<String, Object> pullStatusMulti(String max, String type) {
		Map<String, Object> request_map = new HashMap<String, Object>();
		request_map.put("max", max);
		request_map.put("type", type);
		return smsExecute(sms_pull_status_multi, request_map);
	}

	@Override
	public Map<String, Object> smsCallback(String mobile, String sign,String text) {
		Map<String, Object> request_map = new HashMap<String, Object>();
		request_map.put("mobile", mobile);
		request_map.put("sign", sign);
		request_map.put("text", text);
		return smsExecute(sms_callback, request_map);
	}
	
	/**
	 * 短信操作之前处理
	 * @param request_url(请求url)
	 * @param request_map(请求参数)
	 * @return
	 */
	private Map<String,Object> smsExecute(String request_url,Map<String, Object> request_map){
		long time = WxSmsUtil.getCurrentTime();
		long random = WxSmsUtil.getRandom();
		// 请求url参数
		StringBuilder builder = new StringBuilder();
		builder.append(request_url).append("?sdkappid=").append(appid).append("&random=").append(random);
		// 判断是否需要电话号码签名
		if(StringUtils.isNotEmpty((String)request_map.get("tel"))){
			request_map.put("sig", WxSmsUtil.calculateSignature(appkey, random, time, (String)request_map.get("tel")));
			request_map.put("time", time);
			request_map.put("extend", SmsSenderUtil.isNotEmpty((String)request_map.get("extend")) ? (String)request_map.get("extend") : "");
            request_map.put("ext", SmsSenderUtil.isNotEmpty((String)request_map.get("ext")) ? (String)request_map.get("ext") : "");
            Map<String,Object> telMap = new HashMap<String, Object>();
            telMap.put("nationcode", nationcode);
            telMap.put("mobile", (String)request_map.get("tel"));
            request_map.put("tel", telMap);
            
		} else if(ArrayUtils.isNotEmpty((String[])request_map.get("tels"))){
			// 群发短信参数拼接
			request_map.put("sig", WxSmsUtil.calculateSignature(appkey, random, time, (String[])request_map.get("tels")));
			request_map.put("time", time);
			request_map.put("extend", SmsSenderUtil.isNotEmpty((String)request_map.get("extend")) ? (String)request_map.get("extend") : "");
            request_map.put("ext", SmsSenderUtil.isNotEmpty((String)request_map.get("ext")) ? (String)request_map.get("ext") : "");
            request_map.put("tel", toTel(nationcode, (String[])request_map.get("tels")));
		} else {
			request_map.put("sig", WxSmsUtil.calculateSignature(appkey, random, time));
			request_map.put("time", time);
		}
		return sisapService.execute(request_url, JSONObject.toJSONString(request_map));
	}
	
	/**
	 * 群发号码拼接
	 * @param nationCode
	 * @param phoneNumbers
	 * @return
	 */
	private ArrayList<JSONObject> toTel(String nationCode, String[] phoneNumbers) {
        ArrayList<JSONObject> phones = new ArrayList<JSONObject>();
        for (String phoneNumber: phoneNumbers) {
            JSONObject phone = new JSONObject();
            phone.put("nationcode", nationCode);
            phone.put("mobile", phoneNumber);
            phones.add(phone);
        }
        return phones;
    }

	
}
