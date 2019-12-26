package com.yunwei.context.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.yunwei.context.qrcode.QrcodeService;
import com.yunwei.context.sys.service.YwAccessTokenService;
import com.yunwei.context.sys.service.YwQrcodeService;

@Service
public class YwQrcodeServiceImpl implements YwQrcodeService{

	@Autowired
	private YwAccessTokenService ywAccessTokenService;
	@Autowired
	private QrcodeService qrcodeService;
	
	@Override
	public byte[] getWXACode(String appid, String path, int width,
			boolean auto_color, JSONObject line_color, boolean is_hyaline) {
        String access_token = this.getAccessToken(appid);
		return qrcodeService.getWXACode(access_token, path, width, auto_color, line_color, is_hyaline);
	}

	@Override
	public byte[] getWXACode(String appid, String path) {
		return this.getWXACode(appid, path, 430, false, null, false);
	}

	@Override
	public byte[] getWXACodeUnlimit(String appid, String scene, String page,
			int width, boolean auto_color, JSONObject line_color,
			boolean is_hyaline) {
		String access_token = this.getAccessToken(appid);
		return qrcodeService.getWXACodeUnlimit(access_token, scene, page, width, auto_color, line_color, is_hyaline);
	}

	@Override
	public byte[] getWXACodeUnlimit(String appid, String scene, String page) {
		return this.getWXACodeUnlimit(appid, scene, page, 430, false, null, false);
	}

	@Override
	public byte[] createwxaqrcode(String appid, String path, int width) {
		String access_token = this.getAccessToken(appid);
		return qrcodeService.createwxaqrcode(access_token, path, width);
	}

	@Override
	public byte[] createwxaqrcode(String appid, String path) {
		return this.createwxaqrcode(appid, path, 430);
	}
	
	/**
	 * 获取token
	 * @param appid
	 * @return
	 */
	private String getAccessToken(String appid){
		String access_token = ywAccessTokenService.getAccessTokenByAppId(appid);
		return access_token;
	}

}
