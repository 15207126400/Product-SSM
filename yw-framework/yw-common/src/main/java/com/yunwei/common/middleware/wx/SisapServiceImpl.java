package com.yunwei.common.middleware.wx;

import java.util.ArrayList;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.entity.ContentType;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.yunwei.common.exception.BizException;
import com.yunwei.common.util.HttpClientUtil;
import com.yunwei.common.util.MapUtil;

/**
 * 中台调用服务类
* @ClassName: AbstractSisapService 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2019年2月22日 下午12:46:49 
*
 */
@Service
public class SisapServiceImpl implements SisapService{

	private static Logger logger = Logger.getLogger(SisapServiceImpl.class);
	
	private HttpClientUtil conn;

	private HttpClientUtil getConnection() {
		if (conn == null) {
			conn = HttpClientUtil.getInstance();
			conn.setContent_type(ContentType.MULTIPART_FORM_DATA.withCharset(Consts.UTF_8));
			conn.setAutomaticClose(false);
		}
		return conn;
	}
	
	/**
	 * 留给子类默认实现
	 * @param conn
	 */
	@Override
	public void setConn(HttpClientUtil conn){
		this.conn = conn;
	}
	
	@Override
	public Map<String, Object> execute(String request_url,Map<String, Object> paramMap) {
		byte[] bs = this.executeBinary(request_url, paramMap);
		JSONObject jsonResult = null;
		try {
			jsonResult = JSONObject.parseObject(new String(bs,"UTF-8"));
		} catch (Exception e) {
			logger.error("发送请求失败：",e);
			throw new BizException(e);
		}
		
		return MapUtil.toMap(jsonResult);
	}
	
	@Override
	public byte[] executeBinary(String request_url, Map<String, Object> paramMap) {
		byte[] bs = null;
		try {
			logger.info("发送请求开始");
			bs = this.getConnection().httpPost(request_url, paramMap,new ArrayList<Header>());
			logger.info("发送请求成功,响应参数："+ new String(bs,"UTF-8") +"");
			return bs;
		} catch (Exception e) {
			logger.error("发送请求失败：",e);
			throw new BizException(e);
		} 
	}
	
	@Override
	public Map<String, Object> execute(String request_url, String json) {
		byte[] bs = null;
		try {
			logger.info("发送请求开始");
			bs = this.getConnection().httpPost(request_url, json,ContentType.APPLICATION_JSON,new ArrayList<Header>());
			logger.info("发送请求成功,响应参数："+ new String(bs,"UTF-8") +"");
			JSONObject jsonResult = JSONObject.parseObject(new String(bs,"UTF-8"));
			return MapUtil.toMap(jsonResult);
		} catch (Exception e) {
			logger.error("发送请求失败：",e);
			throw new BizException(e);
		} 
	}

	@Override
	public byte[] executeBinary(String request_url, String xml) {
		byte[] bs = null;
		try {
			logger.info("发送请求开始");
			bs = this.getConnection().httpPost(request_url, xml,new ArrayList<Header>());
			logger.info("发送请求成功,响应参数："+ new String(bs,"UTF-8") +"");
			return bs;
		} catch (Exception e) {
			logger.error("发送请求失败：",e);
			throw new BizException(e);
		} 
	}

	@Override
	public Map<String, Object> execute(String request_url) {
		byte[] bs = null;
		try {
			logger.info("发送请求开始");
			bs = this.getConnection().httpGet(request_url, null);
			logger.info("发送请求成功,响应参数："+ new String(bs,"UTF-8") +"");
			JSONObject jsonResult = JSONObject.parseObject(new String(bs,"UTF-8"));
			return MapUtil.toMap(jsonResult);
		} catch (Exception e) {
			logger.error("发送请求失败：",e);
			throw new BizException(e);
		} 
	}
	
}
