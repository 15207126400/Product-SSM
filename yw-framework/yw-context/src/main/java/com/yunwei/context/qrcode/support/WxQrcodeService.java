package com.yunwei.context.qrcode.support;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.yunwei.common.middleware.wx.SisapService;
import com.yunwei.context.qrcode.QrcodeService;

@Service
public class WxQrcodeService implements QrcodeService{

    private static Logger logger = Logger.getLogger(WxQrcodeService.class);
	
	private final static String getWXACode_url =  "https://api.weixin.qq.com/wxa/getwxacode";// 获取小程序码
	
	private final static String getWXACodeUnlimit_url =  "https://api.weixin.qq.com/wxa/getwxacodeunlimit";// 获取小程序码
	
	private final static String createwxaqrcode_url =  "https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode";// 获取小程序二维码

	@Autowired
	private SisapService sisapService;
	
	@Override
	public byte[] getWXACode(String access_token, String path, int width,
			boolean auto_color, JSONObject line_color, boolean is_hyaline) {
		// 拼接请求url
		StringBuffer buffer = new StringBuffer();
					buffer.append(getWXACode_url);
					buffer.append("?access_token=");
					buffer.append(access_token);
		// 拼接请求参数
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("path", path);
		jsonObject.put("width", width);
		jsonObject.put("auto_color", auto_color);
		jsonObject.put("line_color", line_color);
		jsonObject.put("is_hyaline", is_hyaline);
					
		return sisapService.executeBinary(buffer.toString(),jsonObject.toJSONString());
	}

	@Override
	public byte[] getWXACodeUnlimit(String access_token, String scene,
			String page, int width, boolean auto_color, JSONObject line_color,
			boolean is_hyaline) {
		// 拼接请求url
		StringBuffer buffer = new StringBuffer();
					buffer.append(getWXACodeUnlimit_url);
					buffer.append("?access_token=");
					buffer.append(access_token);
		// 拼接请求参数
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("scene", scene);
		jsonObject.put("page", page);
		jsonObject.put("width", width);
		jsonObject.put("auto_color", auto_color);
		jsonObject.put("line_color", line_color);
		jsonObject.put("is_hyaline", is_hyaline);
					
		return sisapService.executeBinary(buffer.toString(),jsonObject.toJSONString());
	}

	@Override
	public byte[] createwxaqrcode(String access_token, String path, int width) {
		// 拼接请求url
		StringBuffer buffer = new StringBuffer();
					buffer.append(createwxaqrcode_url);
					buffer.append("?access_token=");
					buffer.append(access_token);
		// 拼接请求参数
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("path", path);
		jsonObject.put("width", width);
					
		return sisapService.executeBinary(buffer.toString(),jsonObject.toJSONString());
	}
	
}
