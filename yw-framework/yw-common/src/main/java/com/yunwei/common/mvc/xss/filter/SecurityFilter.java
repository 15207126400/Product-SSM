package com.yunwei.common.mvc.xss.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.yunwei.common.exception.BizException;

/**
 * 功能说明: 安全过滤器<br>
 * 系统版本: v1.0<br>
 * 开发人员: @author kongdy<br>
 * 开发时间: 2015年10月8日<br>
 */
public class SecurityFilter implements Filter {

	@Override
	public void destroy() {}

	/**
	 * now the doFilter will filter the request ,using the Wrapper class to wrap the request
	 * and in the wrapper class, it will handle the XSS issue
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		XSSHttpServletRequestWrapper xssRequest = new XSSHttpServletRequestWrapper((HttpServletRequest)request);
		chain.doFilter(xssRequest, response);
		checkCsrf(xssRequest);
	}

	private void checkCsrf(HttpServletRequest request) {
		String origin = request.getHeader("Origin");
		String referer = request.getHeader("Referer");
		if (origin == null && referer == null) {
			return;
		}
//		CrhUser user = null;
//		try {
//			user = CookieUtil.readUser(request, CrhUser.CRH_USER_SESSION);
//		} catch (Exception e) {}
//		if (user == null || StringUtils.isEmpty(user.getUser_id())) {
//			return;
//		}
//		String[] hosts = new String[] {PropertiesUtils.get("oos.webaddr"), PropertiesUtils.get("sisap.webservice.url"), PropertiesUtils.get("oss.webaddr"), PropertiesUtils.get("wt.webaddr"),
//				PropertiesUtils.get("snp.webaddr"), PropertiesUtils.get("config.webaddr")};
		if (StringUtils.isNotEmpty(referer) && StringUtils.isNotEmpty(origin) && !StringUtils.startsWith(referer, origin)) {
			// origin、referer不一致
			throw new BizException("403", "请求不合法，访问被拒绝");
		}
//		for (String host : hosts) {
//			if (StringUtils.lastIndexOf(host, ":") > 5) {
//				host = host.substring(0, host.lastIndexOf(":"));
//			}
//			if (StringUtils.isNotEmpty(origin) && StringUtils.startsWith(origin, host)) {
//				return;
//			}
//			if (StringUtils.isNotEmpty(referer) && StringUtils.startsWith(referer, host)) {
//				return;
//			}
//		}
//		throw new BizException("403", "请求不合法，访问被拒绝");
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {}
}