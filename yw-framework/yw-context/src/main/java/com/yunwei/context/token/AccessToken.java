package com.yunwei.context.token;

/**
 * AccessToken处理器
* @ClassName: AccessTokenHandle 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2019年2月18日 下午2:23:17 
*
 */
public interface AccessToken {

	/**
	 * 获取执行token[接口调用凭证]
	 * @param appid 小程序唯一凭证，即 AppID，可在「微信公众平台 - 设置 - 开发设置」页中获得。（需要已经成为开发者，且帐号没有异常状态）
	 * @param secret 小程序唯一凭证密钥，即 AppSecret，获取方式同 appid
	 * @return
	 */
	public String getAccessToken(String appid,String secret);
}
