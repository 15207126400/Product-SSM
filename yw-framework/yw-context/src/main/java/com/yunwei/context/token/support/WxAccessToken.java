package com.yunwei.context.token.support;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwei.common.exception.BizException;
import com.yunwei.common.middleware.wx.SisapService;
import com.yunwei.context.token.AccessToken;

/**
 * 微信access_token服务
* @ClassName: WxAccessToken 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2019年2月18日 下午3:17:21 
*
 */
@Service
public class WxAccessToken implements AccessToken{
	
	private static final String access_token_url = "https://api.weixin.qq.com/cgi-bin/token";
	
	private static final String grant_type = "client_credential"; 
	
	private static Logger logger = Logger.getLogger(WxAccessToken.class);
	
	@Autowired
	private SisapService sisapService;
	
	@Override
	public String getAccessToken(String appid, String secret) {
		logger.info("获取【access_token】请求开始，请求参数：appid["+ appid +"]：secret["+ secret +"]");
		StringBuffer buffer = new StringBuffer();
					buffer.append(access_token_url);
					buffer.append("?grant_type=");
					buffer.append(grant_type);
					buffer.append("&appid=");
					buffer.append(appid);
					buffer.append("&secret=");
					buffer.append(secret);
		Map<String, Object> map = sisapService.execute(buffer.toString());
		if(StringUtils.isBlank(map.get("access_token").toString())){
			logger.error("access_token为空，获取失败");
			throw new BizException(map.toString());
		}
		return map.get("access_token").toString();
	}

}
