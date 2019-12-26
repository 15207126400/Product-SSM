package com.yunwei.context.sys.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwei.common.exception.BizException;
import com.yunwei.context.sys.service.YwAccessTokenService;
import com.yunwei.context.sys.usercenter.model.YwUserCustomerXcx;
import com.yunwei.context.sys.usercenter.service.YwUserCustomerXcxService;
import com.yunwei.context.token.AccessToken;

@Service
public class YwAccessTokenServiceImpl implements YwAccessTokenService {
	
	private static Logger logger = Logger.getLogger(YwAccessTokenServiceImpl.class);
	
	@Autowired
	private YwUserCustomerXcxService ywUserCustomerXcxService;
    @Autowired
    private AccessToken accessToken;
	
	@Override
	public String getAccessTokenByUserId(String user_id) {
		// 获取小程序配置信息
		YwUserCustomerXcx ywUserCustomerXcx = new YwUserCustomerXcx();
		ywUserCustomerXcx.setUser_id(user_id);
		ywUserCustomerXcx = ywUserCustomerXcxService.query(ywUserCustomerXcx);
		if(ywUserCustomerXcx == null){
			logger.error("小程序配置信息不存在");
			throw new BizException("小程序配置信息不存在");
		}
		String access_token = accessToken.getAccessToken(ywUserCustomerXcx.getApp_id(), ywUserCustomerXcx.getApp_secret());
		return access_token;
	}
	
	@Override
	public String getAccessTokenByAppId(String appid) {
		// 获取小程序配置信息
		YwUserCustomerXcx ywUserCustomerXcx = new YwUserCustomerXcx();
		ywUserCustomerXcx.setApp_id(appid);
		ywUserCustomerXcx = ywUserCustomerXcxService.query(ywUserCustomerXcx);
		if(ywUserCustomerXcx == null){
			logger.error("小程序配置信息不存在");
			throw new BizException("小程序配置信息不存在");
		}
		String access_token = accessToken.getAccessToken(ywUserCustomerXcx.getApp_id(), ywUserCustomerXcx.getApp_secret());
		return access_token;
	}
}

