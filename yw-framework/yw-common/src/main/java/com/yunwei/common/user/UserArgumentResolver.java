package com.yunwei.common.user;

import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 
* @ClassName: UserArgumentResolver 
* @Description: TODO(TODO) iuser自定义参数自动注入
* @author zhangjh
* @date 2018年4月12日 下午2:07:09 
*
 */
@Component("userArgumentResolver")
@Order(1)
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

	public boolean supportsParameter(MethodParameter paramMethodParameter) {
		return SysUser.class.isAssignableFrom(paramMethodParameter.getParameterType());
	}

	public Object resolveArgument(MethodParameter paramMethodParameter, ModelAndViewContainer paramModelAndViewContainer, NativeWebRequest paramNativeWebRequest,
			WebDataBinderFactory paramWebDataBinderFactory) throws Exception {
		// 获取在拦截器注入request中的用户
		SysUser sysUser = (SysUser) paramNativeWebRequest.getAttribute(SysUser.YW_USER_SESSION, RequestAttributes.SCOPE_REQUEST);
		
		return sysUser;
	}

}
