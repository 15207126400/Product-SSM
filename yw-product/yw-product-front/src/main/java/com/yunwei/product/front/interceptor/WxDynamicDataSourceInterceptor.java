package com.yunwei.product.front.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


/**
 * 数据源动态切换拦截器
 * @author zhangz
 *
 */
public class WxDynamicDataSourceInterceptor extends HandlerInterceptorAdapter{

	/**
	 * 请求拦截前的操作
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// 解决小程序wx_appid无法获取的特殊情况
		String wx_appid = request.getParameter("wx_appid");
		if(StringUtils.isNoneBlank(wx_appid)){
			request.getServletContext().setAttribute("wx_appid", wx_appid);
		} else {
			request.setAttribute("wx_appid", request.getServletContext().getAttribute("wx_appid"));
		}
		return true;
	}

	/**
	 * 请求拦截中的操作
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	}

	/**
	 * 请求拦截后的操作
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)throws Exception {
	}

	
	
	
	
}
