package com.yunwei.common.middleware.wx;

import java.util.Map;

import com.yunwei.common.util.HttpClientUtil;

/**
 * 中台服务
* @ClassName: SisapService 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2019年2月22日 下午12:04:30 
*
 */
public interface SisapService {

	/**
	 * 中台服务调用(表单提交方式【get请求方式】)
	 * @param request_url
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> execute(String request_url);
	
	/**
	 * 中台服务调用(表单提交方式【post请求方式】)
	 * @param request_url
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> execute(String request_url,Map<String, Object> paramMap);
	
	/**
	 * 中台服务调用(json提交方式)
	 * @param request_url
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> execute(String request_url,String json);
	
	/**
	 * 中台服务调用(表单提交方式【返回二进制】)
	 * @param request_url
	 * @param paramMap
	 * @return
	 */
	public byte[] executeBinary(String request_url,Map<String, Object> paramMap);
	
	/**
	 * 中台服务调用(xml提交方式【返回二进制】)
	 * @param request_url
	 * @param paramMap
	 * @return
	 */
	public byte[] executeBinary(String request_url,String xml);
	
	/**
	 * 设置连接实例
	 * @param conn
	 */
	public void setConn(HttpClientUtil conn);
}
