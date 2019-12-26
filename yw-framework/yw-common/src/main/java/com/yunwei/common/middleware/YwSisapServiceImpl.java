package com.yunwei.common.middleware;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.yunwei.common.user.SysUser;
import com.yunwei.common.util.CookieUtil;
import com.yunwei.common.util.DebugHelper;
import com.yunwei.common.util.HttpClientUtil;
import com.yunwei.common.util.RequestUtil;



/**
 * 功能说明: sisap中台请求服务类<br>
 * 系统版本: v1.0<br>
 * 开发人员: @author kongdy<br>
 * 开发时间: 2015年7月15日<br>
 */
@Service
public class YwSisapServiceImpl implements ISisapService {

	Logger logger = org.slf4j.LoggerFactory.getLogger(ISisapService.class);

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
	 * 二进制方式请求中台服务
	 * @param functionId
	 * @param params
	 * @return
	 */
	public byte[] callBinary(URL url, Map<String, Object> params) {
		return callBinary(url, params, null);
	}

	/**
	 * 二进制方式请求中台服务
	 * @param functionId
	 * @param params
	 * @return
	 */
	private byte[] callBinary(URL url, Map<String, Object> params, SysUser user) {

		byte[] content = null;
		try {
			List<Header> headers = getSisapCookieHeaders(user);
			content = getConnection().httpPost(url.toString(), params, headers);
			dealResponseHeaders(headers);
		} catch (ClientProtocolException e) {
			logger.error("调用中台服务{}失败，请求参数：{}", url, DebugHelper.filterField(params));
		} catch (IOException e) {
			logger.error("调用中台服务{}失败，请求参数：{}", url, DebugHelper.filterField(params), e);
		}
		return content;
	}

	/**
	 * 二进制方式请求中台服务
	 * @param functionId
	 * @param params
	 * @return
	 */
	public byte[] callBinary(String functionId, Map<String, Object> params) {
		return callBinary(functionId, params, null);
	}

	/**
	 * 二进制方式请求中台服务
	 * @param functionId
	 * @param params
	 * @return
	 */
	public byte[] callBinary(String functionId, Map<String, Object> params, SysUser user) {

//		String SISAP_URL = PropertiesUtils.get("sisap.webservice.url", "");
		/*String SISAP_URL = "locahost:8080";
		if (!StringUtils.endsWith(SISAP_URL, "/")) {
			SISAP_URL = SISAP_URL + "/";
		}*/
		try {
			logger.info("请求中台Url地址:" + functionId);
			return callBinary(new URL(functionId), params, user);
		} catch (MalformedURLException e) {
			logger.error("sisap.webservice.url配置格式错误", e);
		}
		return null;
	}

	/**
	 * 回写cookie处理
	 * @param headers
	 */
	private void dealResponseHeaders(List<Header> headers) {
		HttpServletRequest request = RequestUtil.getRequest();
		if (request == null) {
			return;
		}

		SysUser user = (SysUser)request.getAttribute(SysUser.YW_USER_SESSION);
		if (user == null) {
			user = new SysUser();
		}

		String domain = CookieUtil.getDomain(request);
//		String sisap = PropertiesUtils.get("sisap.cookie.domain");
		String sisap = "localhost:8080";
		List<Header> tmpHeader = new ArrayList<Header>();
		for (Header header : headers) {
			if ("Set-Cookie".equalsIgnoreCase(header.getName())) {
				if (StringUtils.isNotEmpty(domain)) {
					String value = header.getValue().replace("Domain=" + sisap, "Domain=" + domain);
					tmpHeader.add(new BasicHeader(header.getName(), value));
				} else {
					String value = header.getValue().replace("Domain=" + sisap, "");
					tmpHeader.add(new BasicHeader(header.getName(), value));
				}
			}
		}
		request.setAttribute(SysUser.YW_USER_SESSION, user);
		request.setAttribute("sisapHeaders", tmpHeader);// 记录中台返回的cookie
	}

	/**
	 * 请求中台服务，打包结果集
	 * @param functionId
	 * @param params
	 * @return
	 */
	public SisapResult call(String functionId, Map<String, Object> params) {
		return call(functionId, params, null);
	}

	/**
	 * 请求中台服务，打包结果集
	 * @param sisap_url 指定URL
	 * @param functionId
	 * @param params
	 * @return
	 */
	public SisapResult call(String sisap_url, String functionId, Map<String, Object> params) {
		if (!StringUtils.endsWith(sisap_url, "/")) {
			sisap_url = sisap_url + "/";
		}
		try {
			byte[] content = callBinary(new URL(sisap_url + functionId + ".json"), params);
			if (content != null) {
				return new SisapResult(new String(content, "UTF-8"));
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("调用中台接口{}失败，请求参数：{}", functionId, DebugHelper.filterField(params));
		} catch (MalformedURLException e) {
			logger.error("sisap_url格式错误", e);
		}
		return new SisapResult();
	}

	/**
	 * 获取中台需要的cookie信息
	 * @return
	 */
	private List<Header> getSisapCookieHeaders(SysUser user) {
		List<Header> headers = new ArrayList<Header>();
		if (user == null) {
			HttpServletRequest request = RequestUtil.getRequest();
			if (request == null) {
				return headers;
			}
			// 获取nosession中的用户信息
			user = (SysUser) request.getAttribute(SysUser.YW_USER_SESSION);
		}
		if (user != null) {
			try {
				Map<String, String> tokenMap = CookieUtil.split2TokenMap(SysUser.YW_USER_SESSION, user);
				for (Map.Entry<String, String> entry : tokenMap.entrySet()) {
					headers.add(new BasicHeader("Cookie", entry.getKey() + "=" + entry.getValue()));
				}
			} catch (IOException e) {
				logger.error("user转换cookie发生错误", e);
			}
		}
		return headers;
	}

	@Override
	public SisapResult call(String functionId, Map<String, Object> params, SysUser user) {
		byte[] content = callBinary(functionId, params, user);

		try {
			if (content != null) {
				return new SisapResult(new String(content, "UTF-8"));
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("调用中台接口{}失败，请求参数：{}", functionId, DebugHelper.filterField(params));
		}
		return new SisapResult();
	}

	@Override
	public SisapResult call(String sisap_url, String functionId, Map<String, Object> params, SysUser user) {
		if (!StringUtils.endsWith(sisap_url, "/")) {
			sisap_url = sisap_url + "/";
		}
		try {
			byte[] content = callBinary(new URL(sisap_url + functionId + ".json"), params, user);
			if (content != null) {
				return new SisapResult(new String(content, "UTF-8"));
			}
		} catch (UnsupportedEncodingException | MalformedURLException e) {
			logger.error("调用中台接口{}失败，请求参数：{}", sisap_url + functionId + ".json", DebugHelper.filterField(params));
		}
		return null;
	}
}
