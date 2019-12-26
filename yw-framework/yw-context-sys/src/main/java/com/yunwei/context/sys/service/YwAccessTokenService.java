package com.yunwei.context.sys.service;

/**
 * 云维access_token服务
* @ClassName: YwAccessTokenService 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2019年2月19日 上午11:34:11 
*
 */
public interface YwAccessTokenService {
	
	/**
	 * 获取access_token
	 * @param appid 小程序编号
	 * @return
	 */
	public String getAccessTokenByAppId(String appid);
	
	/**
	 * 获取access_token
	 * @param user_id 用户编号
	 * @return
	 */
	public String getAccessTokenByUserId(String user_id);
}
