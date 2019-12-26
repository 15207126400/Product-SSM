package com.yunwei.common.view.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.RequestUtil;
import com.yunwei.common.util.SpringContext;


/**
 * 
* @ClassName: JsonResultBodyAdvice 
* @Description: TODO(TODO) 通用json结果返回处理
* @author zhangjh
* @date 2018年4月12日 下午1:50:38 
*
 */
@ControllerAdvice(basePackages = "com.yunwei")
@Order(0)
public class JsonResultBodyAdvice implements ResponseBodyAdvice<Object> {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		Map<String, ? extends JsonResultBodyAdvice> jsonAdvices = SpringContext.getBeansOfType(getClass());
		if (jsonAdvices.size() > 1) {
			return false;
		}
		return MappingJackson2HttpMessageConverter.class.isAssignableFrom(converterType);
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {

		Map<String, Object> bodyMap = packJsonResult(body, returnType.getMethod().getAnnotation(ResponseBodyFilter.class));
		putDefaultErrorInfo(bodyMap);

		rewriteSession2Cookie(response);
		
		// 这里把返回的结果设置到request中，供拦截器使用【使用ResponseBody的时候，拦截器postHandler中无法取到返回结果】
		HttpServletRequest req = RequestUtil.getRequest();
		req.setAttribute("resMap_forInterceptor", bodyMap);
		
		return bodyMap;
	}

	/**
	 * json结果集打包
	 * @param body
	 * @param filter
	 * @return
	 * @throws IOException
	 */
	private Map<String, Object> packJsonResult(Object body, ResponseBodyFilter filter) {
		Map<String, Object> bodyMap = new HashMap<String, Object>();
		if (body instanceof String || body instanceof Boolean || body instanceof Integer) {
			bodyMap.put("error_info", body);
		} else if (body instanceof List) {
			if (filter != null && !ArrayUtils.isEmpty(filter.value())) {
				List<Map> list = new ArrayList<Map>();
				for (Object obj : (List)body) {
					// 如果obj是list类型，太深了，没办法支持别名过滤了[现在返回的值应该也没有list里面套list的]
					if (obj instanceof List) {
						list.addAll((List)obj);
					} else if (obj instanceof Map) {
						list.add(MapUtil.cloneByFilter((Map)obj, filter.value()));
					} else {
						list.add(MapUtil.cloneByFilter(MapUtil.toMap(obj), filter.value()));
					}
				}
				bodyMap.put("resultList", list);
			} else {
				// 直接返回List<Object>的时候，Object需要通过MapUtil.toMap转一下，因为在toMap中我们加了别名、日期格式化支持
				List<Map<String, Object>> tmpListMap = new ArrayList<Map<String, Object>>();
				List<?> bodyList = (List<?>)body;
				for (Object object : bodyList) {
					tmpListMap.add(MapUtil.toMap(object));
				}
				bodyMap.put("resultList", tmpListMap);
			}
		} else if (body instanceof Map) {
			// Map<String, ?> map = (Map)body;
			if (filter != null && !ArrayUtils.isEmpty(filter.value())) {
				bodyMap.putAll(MapUtil.cloneByFilter((Map)body, filter.value()));
			} else {
				bodyMap.putAll((Map)body);
			}
		} else {
			Map<String, Object> tmpMap = MapUtil.toMap(body);
			if (filter != null && !ArrayUtils.isEmpty(filter.value())) {
				bodyMap.putAll(MapUtil.cloneByFilter(tmpMap, filter.value()));
			} else {
				bodyMap.putAll(tmpMap);
			}
		}
		return bodyMap;
	}

	private void putDefaultErrorInfo(Map<String, Object> bodyMap) {
		if (!bodyMap.containsKey("error_no")) {
			bodyMap.put("error_no", String.valueOf(0));
		}
		if (!bodyMap.containsKey("error_info")) {
			bodyMap.put("error_info", "");
		}
	}

	/**
	 * 更新session中的user到cookie
	 * @param outputMessage
	 */
	protected void rewriteSession2Cookie(HttpOutputMessage outputMessage) {
//		HttpServletRequest request = RequestUtil.getRequest();
//		String sessionName = CrhUser.CRH_USER_SESSION;
//
//		CrhUser user = (CrhUser)request.getAttribute(sessionName);
//		if (user != null) {
//			try {
//				user.setUpdate_cookie_time(new Date());
//				CookieUtil.writeObject(request, ((ServletServerHttpResponse)outputMessage).getServletResponse(), sessionName, user);
//			} catch (IOException e) {
//				logger.error("回写cookie发生错误", e);
//			}
//		} else {
//			CookieUtil.clearCookie(request, ((ServletServerHttpResponse)outputMessage).getServletResponse(), sessionName);
//		}

	}
	
	/**
//	 * 打印日志
//	 * @author yejg
//	 * @param bodyMap
//	 */
//	protected void printResponseResult(Map<String, Object> bodyMap) {
//		
//	}

}
