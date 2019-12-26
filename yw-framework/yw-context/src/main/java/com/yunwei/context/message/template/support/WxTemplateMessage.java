package com.yunwei.context.message.template.support;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.yunwei.common.middleware.wx.SisapService;
import com.yunwei.context.message.template.TemplateMessage;

/**
 * 微信模板消息
* @ClassName: WxTemplateMessage 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2019年2月18日 下午4:41:51 
*
 */
@Service
public class WxTemplateMessage implements TemplateMessage {
	//请求模板信息
	public static final String template_message_send_url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send";
	//签到成功模板编号
	public static final String sgin_template_id = "mbn8l6z_N2BSF4eRRiu2DpzWxxIwYBJZwScoDZeBa1g";
	//订单支付成功模板编号
	public static final String pay_template_id = "srF6YTF_13jTZ7cDsT8RJuUVFxPB-RskaFXzqbO0WuM";
	
	private static Logger logger = Logger.getLogger(WxTemplateMessage.class);
	
	@Autowired
	private SisapService sisapService;
	
	@Override
	public void send(String access_token,String touser, String template_id, String form_id,
			JSONObject data) {
		this.send(access_token,touser, template_id, form_id, data, "");
	}
	
	@Override
	public void send(String access_token, String touser, String template_id,
			String form_id, Map<String, Map<String, Object>> data) {
		
		this.send(access_token,touser, template_id, form_id, data, "");
	}

	
	@Override
	public void send(String access_token,String touser, String template_id, String form_id,
			JSONObject data, String emphasis_keyword) {
		this.send(access_token,touser, template_id, form_id, data, emphasis_keyword, "");
	}
	
	@Override
	public void send(String access_token, String touser, String template_id,
			String form_id, Map<String, Map<String, Object>> data,
			String emphasis_keyword) {
		this.send(access_token,touser, template_id, form_id, data, emphasis_keyword, "");
	}
	
	@Override
	public void send(String access_token,String touser, String template_id, String form_id,
			JSONObject data, String emphasis_keyword,
			String page) {
		// 拼接请求url
		StringBuffer buffer = new StringBuffer();
					buffer.append(template_message_send_url);
					buffer.append("?access_token=");
					buffer.append(access_token);
		// 拼接请求参数
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("touser", touser);
		jsonObject.put("template_id", template_id);
		jsonObject.put("page", page);
		jsonObject.put("form_id", form_id);
		jsonObject.put("data", data);
		jsonObject.put("emphasis_keyword", emphasis_keyword);
		
		System.out.println("【发送模板信息】 : " + buffer.toString() + ",josn数据 : " + jsonObject.toJSONString());
		sisapService.execute(buffer.toString(),jsonObject.toJSONString());
	}
	
	@Override
	public void send(String access_token, String touser, String template_id,
			String form_id, Map<String, Map<String, Object>> data,
			String emphasis_keyword, String page) {
		// 拼接请求url
		StringBuffer buffer = new StringBuffer();
					buffer.append(template_message_send_url);
					buffer.append("?access_token=");
					buffer.append(access_token);
		// 拼接请求参数
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("touser", touser);
		jsonObject.put("template_id", template_id);
		jsonObject.put("page", page);
		jsonObject.put("form_id", form_id);
		jsonObject.put("data", data);
		jsonObject.put("emphasis_keyword", emphasis_keyword);
		
		System.out.println("【发送模板信息】 : " + buffer.toString() + ",josn数据 : " + jsonObject.toJSONString());
		sisapService.execute(buffer.toString(),jsonObject.toJSONString());
	}

	@Override
	public void orderSend(String access_token, String touser,String template_id, String prepay_id, JSONObject data,String emphasis_keyword) {
		// 拼接请求url
		StringBuffer buffer = new StringBuffer();
					buffer.append(template_message_send_url);
					buffer.append("?access_token=");
					buffer.append(access_token);
		// 拼接请求参数
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("touser", touser);
		jsonObject.put("template_id", template_id);
		jsonObject.put("form_id", prepay_id);
		jsonObject.put("data", data);
		jsonObject.put("emphasis_keyword", emphasis_keyword);
		
		sisapService.execute(buffer.toString(),jsonObject.toJSONString());
	}


	
	
}
