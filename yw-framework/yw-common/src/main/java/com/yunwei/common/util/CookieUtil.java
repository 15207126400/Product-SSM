package com.yunwei.common.util;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunwei.common.user.SysUser;
import com.yunwei.common.utils.encrypt.EncodeUtils;
import com.yunwei.common.utils.encrypt.Md5Encrypt;

/**
 * 功能说明: cookie读写工具类<br>
 * 系统版本: v1.0<br>
 * 开发人员: @author yejg<br>
 * 开发时间: 2015年7月25日<br>
 */
public class CookieUtil {

	private static Logger logger = LoggerFactory.getLogger(CookieUtil.class);

	// public static final String crh_oos_domain = PropertiesUtils.get("oos.cookie.domain");
	public static final String COOKIE_CHANNEL_KEY = "_chnl_";
	public static final String COOKIE_CRH_SESSION_ID = "CRH_SESSION_ID";
	private static Map<String, Integer> cookies = null;

	static {
		cookies = new HashMap<String, Integer>();
		cookies.put(SysUser.YW_USER_SESSION, -1);
		cookies.put("failFlag", 7);
		// 2014-6-23 zhudp 渠道推广cookie
		cookies.put(COOKIE_CHANNEL_KEY, 7);
		cookies.put("idUploadSubmit", 1);
	}

	/**
	 * 从所有cookie中找到名为name的cookie，返回其value
	 * @param req
	 * @param name
	 * @return
	 */
	public static String getCookie(HttpServletRequest req, String name) {
		try {
			Cookie[] reqCookies = req.getCookies();
			if (reqCookies != null) {
				for (Cookie cookie : reqCookies) {
					if (name.equals(cookie.getName())) {
						return URLDecoder.decode(cookie.getValue(), "UTF-8");
					}
				}
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error("解析cookie出现异常:" + e);
			return null;
		}
		return null;
	}

	/**
	 * cookie 加密
	 * @param userProfile
	 */
	public static byte[] encryptionCookie(String str) {
		// 此处需要修改
		String key = Md5Encrypt.md5("dad");
		try {
			byte[] cookieSteing = EncodeUtils.encode(key, str.getBytes());
			return cookieSteing;
		} catch (Exception e) {
			logger.error("加密失败" + e);
			return null;
		}
	}

	/**
	 * 标准版cookie 加密
	 * @param userProfile
	 */
	public static String encryptionCookieStd(String str) {
		// 此处需要修改
		String key = Md5Encrypt.md5("dad");
		String cookieSteing = AuthCodeUtil.authcodeEncode(str, key);
		return cookieSteing;
	}

	/**
	 * cookie 解密
	 * @param orgcookie
	 * @return UserProfile null:解析失败或者密码错误
	 */
	public static String decryptCookie(byte[] orgcookie) {
		try {
			String key = Md5Encrypt.md5("dad");

			byte[] cookieStringDecode = EncodeUtils.decode(key, orgcookie);
			return new String(cookieStringDecode);
		} catch (Exception e) {
			logger.error("解密失败" + e);
			return null;
		}

	}

	/**
	 * 根据实际URL请求，获取cookie域配置
	 * @param request
	 * @return
	 */
	public static String getDomain(HttpServletRequest request) {
		if (request == null) {
			request = RequestUtil.getRequest();
		}
		if (request != null) {
			String realHost = request.getHeader("Real-Host");
			if (StringUtils.isEmpty(realHost)) {
				// 如果没有加Real-Host，则不支持domain
				return null;
			}
//			String sisap = PropertiesUtils.get("sisap.cookie.domain");
//			String oss = PropertiesUtils.get("oss.cookie.domain");
//			String oos = PropertiesUtils.get("oos.cookie.domain");
			String sisap = "/";
			String oss = "/";
			String oos = "/";
			if (StringUtils.isNotEmpty(sisap) && StringUtils.contains(realHost, sisap)) {
				return sisap;
			} else if (StringUtils.isNotEmpty(oss) && StringUtils.contains(realHost, oss)) {
				return oss;
			} else if (StringUtils.isNotEmpty(oos) && StringUtils.contains(realHost, oos)) {
				return oos;
			}
		}
		return null;
	}

	/**
	 * 增加一个cookie
	 * @param response HttpServletResponse对象
	 * @param name 新增的cookie名
	 * @param value 新增的cookie值
	 * @param isRemmeber
	 */
	public static void setCookie(HttpServletResponse response, String name, String value, Boolean isRemmeber) {

		int time = 0;
		if (cookies.containsKey(name)) {
			if (cookies.get(name) > 0 && isRemmeber) {
				time = cookies.get(name) * 24 * 3600;
			}
		}
		setCookie(response, name, value, time);
	}

	/****
	 * 网厅cookie 设置
	 * @param response
	 * @param k
	 * @param v
	 * @param time second
	 */
	public static void setCookie(HttpServletResponse response, String k, String v, int time) {
		try {

			StringBuffer cookieContent = new StringBuffer();
			// cookie Id
			cookieContent.append(k);
			cookieContent.append("=");
			cookieContent.append(URLEncoder.encode(v, "UTF-8"));
			cookieContent.append(";");
			// cookie path
			cookieContent.append("Path=/;");

			String domain = getDomain(null);
			if (StringUtils.isNotEmpty(domain)) {
				// cookie domain
				cookieContent.append("Domain=");
				cookieContent.append(domain);
				cookieContent.append(";");
			}
			// 存活时间
			if (time > 0) {
				cookieContent.append("Max-Age=");
				cookieContent.append(time);
				cookieContent.append(";");
			}
			// 防止客户端js 读取cookie值
			cookieContent.append("HTTPOnly");
			response.setHeader("Pragma", "no-cache");
			response.addHeader("Cache-Control", "must-revalidate");
			response.addHeader("Cache-Control", "no-cache");
			response.addHeader("Cache-Control", "no-store");
			response.setDateHeader("Expires", 0);
			response.addHeader("Set-Cookie", cookieContent.toString());

		} catch (Exception e) {
			logger.error("设置cookie异常：", e);
		}
	}

	/**
	 * 读cookie，返回UserProfile对象(或者null)
	 * @param request
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static SysUser readUser(HttpServletRequest request, String key) throws Exception {
		if (request == null || StringUtils.isEmpty(key)) {
			return null;
		}
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			// 1.读cookie
			Map<String, String> params = new HashMap<String, String>();
			for (Cookie cookie : cookies) {
				String name = cookie.getName();
				String value = cookie.getValue();
				params.put(name, value);
			}
			// 2.组装切片
			String base64String = ParamUtil.mergeParam(key, params);
			return readUserFromBase64Str(base64String);
		}
		return null;
	}

	/**
	 * 从base64字符串中反序列化得到UserProfile
	 * @param base64Str
	 * @return
	 * @throws Exception
	 */
	public static SysUser readUserFromBase64Str(String base64Str) throws Exception {
		if (StringUtils.isNotEmpty(base64Str)) {
			// 3.解码
			byte[] buffer = Base64.decodeBase64(base64Str);
			// 4.解密
			String plainStr = decryptCookie(buffer);

			try {
				return FastJsonUtil.parseObject(plainStr, SysUser.class);
			} catch (Exception e) {
				logger.error("解析失败", e);
			}
		}
		return null;
	}

	/**
	 * 写 cookie
	 * @param response
	 * @param key
	 * @param object
	 * @throws IOException
	 */
	public static void writeObject(HttpServletRequest request, HttpServletResponse response, String key, Object object) throws IOException {
		Map<String, String> tokenMap = split2TokenMap(key, object);
		String domain = getDomain(request);
		// 写cookie
		if (tokenMap != null && !tokenMap.isEmpty()) {
			Set<Map.Entry<String, String>> entryMap = tokenMap.entrySet();
			Iterator<Map.Entry<String, String>> it = entryMap.iterator();
			while (it.hasNext()) {
				Entry<String, String> entry = it.next();
				Cookie tmpCookie = new Cookie(entry.getKey(), entry.getValue());
				tmpCookie.setPath("/");
				tmpCookie.setHttpOnly(true);
//				String max_age = PropertiesUtils.get("cookie.max.age");
				String max_age = "60";
				if (StringUtils.isNoneBlank(max_age)) {
					try {
						tmpCookie.setMaxAge(Integer.parseInt(max_age) * 60);
					} catch (NumberFormatException ex) {
						logger.error(ex.getMessage());
					}
				}
				if (StringUtils.isNotEmpty(domain)) {
					tmpCookie.setDomain(domain);
				}
				response.addCookie(tmpCookie);
			}
		}
	}

	/**
	 * 将对象序列化、加密、做切片，返回map
	 * @param key cookie的name("_crh_user")
	 * @param object UserProfile对象
	 * @return
	 * @throws IOException
	 */
	public static Map<String, String> split2TokenMap(String key, Object object) throws IOException {
		// 1.转换：序列化为二进制
		String jsonStr = FastJsonUtil.toJSONString(object);
		// 2.加密
		byte[] encStr = encryptionCookie(jsonStr);
		// 3.编码
		String base64String = Base64.encodeBase64String(encStr);

		// 4.切片
		Map<String, String> tokenMap = ParamUtil.splitParam(key, base64String);
		return tokenMap;
	}

	/**
	 * 清除指定name的cookie
	 * @param request
	 * @param response
	 * @param cookieName
	 */
	public static void clearCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			String domain = getDomain(request);
			for (Cookie cookie : cookies) {
				if (StringUtils.equals(cookieName, cookie.getName())) {
					cookie.setMaxAge(0);
					cookie.setPath("/");
					if (StringUtils.isNotEmpty(domain)) {
						cookie.setDomain(domain);
					}
					cookie.setValue("");
					response.addCookie(cookie);
				}
			}
		}
	}

	/**
	 * 清除网厅cookie
	 * @param request
	 * @param response
	 * @param cookieName
	 */
	public static void clearWtCookie(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Integer> cookies = new HashMap<String, Integer>();
		cookies.put("ver", 7);
		cookies.put("pwd", 7);
		cookies.put("uid", 7);
		cookies.put("lastLoginTime", 7);
		cookies.put("macaddr", 7);
		cookies.put("usr", 7);
		String domain = getDomain(request);
		Set<Entry<String, Integer>> set = cookies.entrySet();
		for (Entry<String, Integer> entry : set) {
			Cookie cookie = new Cookie(entry.getKey(), "");
			cookie.setPath("/");
			if (StringUtils.isNotEmpty(domain)) {
				cookie.setDomain(domain);
			}
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
	}

	/**
	 * 解析西南统一登录sso cookie
	 * @author woody
	 * @param request
	 * @return
	 * @throws SSOException
	 */
//	public static Map<String, String> getSsoCookie(HttpServletRequest request) throws BizException {
//		Map<String, String> dataRow = new HashMap<>();
//		DES des = new DES(Key.getSSOKey());
//		String cookieStr = getCookie(request, "sso_cookie");
//		if (cookieStr != null && cookieStr.indexOf("|") > 0) {
//			String[] str = cookieStr.split("\\|");
//			String tmp = des.decrypt(str[0], "utf-8");
//			if (tmp != null) {
//				String[] items = tmp.split("\\|");
//				for (int i = 0; i < items.length; i++) {
//					if (items[i].indexOf(":") > 0) {
//						String[] item = items[i].split(":");
//						if (item.length == 1) {
//							dataRow.put(item[0], "");
//						} else {
//							dataRow.put(item[0], item[1]);
//						}
//					}
//				}
//			}
//			// 判断时间戳
//			if (str[1].length() > 0 && !str[1].equals(dataRow.get("time"))) {
//				throw new BizException("-1", "Cookie密文无效！");
//			}
//		}
//		return dataRow;
//	}
}