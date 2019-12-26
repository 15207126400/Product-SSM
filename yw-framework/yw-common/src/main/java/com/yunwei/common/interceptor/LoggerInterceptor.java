package com.yunwei.common.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.yunwei.common.exception.BizException;
import com.yunwei.common.util.DebugHelper;
import com.yunwei.common.util.RequestUtil;



/**
 * 
* @ClassName: LoggerInterceptor 
* @Description: TODO(TODO) 日志打印拦截器
* @author zhangjh
* @date 2018年4月12日 下午2:26:10 
*
 */
@Component
public class LoggerInterceptor extends HandlerInterceptorAdapter {

	Logger logger = Logger.getLogger(LoggerInterceptor.class);

	private int requestParamSize=2000;
//	@Autowired
//	private FuncNoConfigCache funcNoCache;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String requestUri = request.getRequestURI();
		if (requestUri != null && requestUri.endsWith(".json") && handler instanceof HandlerMethod && logger.isInfoEnabled()) {
			HandlerMethod method = (HandlerMethod)handler;
			Map<String, String> params = RequestUtil.request2Map(request);
			Map debugMap = DebugHelper.filterField(params);
//			String funcName = funcNoCache.getFuncName(request.getRequestURI());
			logger.info(String.format("HttpRequest[%s:%s:%s]:%s[%s]:%s", request.getRemoteHost(), request.getRemotePort(), request.getRequestedSessionId(), request.getRequestURI(), request.getRequestURI(), debugMap));
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		// request.getAttribute("resMap_forInterceptor");
		// logger.info("---------------------------------------postHandle");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		// 输出HTTP返回信息
		String requestUri = request.getRequestURI();
		if (requestUri != null && requestUri.endsWith(".json") && handler instanceof HandlerMethod && logger.isInfoEnabled()) {
			HttpServletRequest req = RequestUtil.getRequest();
			Map resultMap = (Map)req.getAttribute("resMap_forInterceptor");
			Map debugMap = DebugHelper.filterField(resultMap);
//			String funcName = funcNoCache.getFuncName(request.getRequestURI());
			String debugParam="";
			if(debugMap!=null && (debugParam=debugMap.toString()).length() > requestParamSize){
				debugParam=debugParam.substring(0,requestParamSize)+"......";
			}
			// 有异常就显示错误级别抛出
			if(ex instanceof BizException){
				logger.error(String.format("HttpResponse[%s:%s:%s]:%s[%s]:%s", req.getRemoteHost(), req.getRemotePort(), req.getRequestedSessionId(), req.getRequestURI(), request.getRequestURI(), debugParam));
			} else if(ex != null){
				logger.error(String.format("HttpResponse[%s:%s:%s]:%s[%s]:%s", req.getRemoteHost(), req.getRemotePort(), req.getRequestedSessionId(), req.getRequestURI(), request.getRequestURI(), debugParam));
			} else {
				logger.info(String.format("HttpResponse[%s:%s:%s]:%s[%s]:%s", req.getRemoteHost(), req.getRemotePort(), req.getRequestedSessionId(), req.getRequestURI(), request.getRequestURI(), debugParam));
			}
			
		}
	}
}
