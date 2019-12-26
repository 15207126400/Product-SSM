package com.yunwei.product.common.interceptor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;
import com.yunwei.common.annotation.ResubmitToken;

/**
 * 防止数据重复提交拦截器
* @ClassName: ResubmitInterceptor 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年6月7日 下午6:20:50 
*
 */
public class ResubmitInterceptor extends HandlerInterceptorAdapter{

//	@Override  
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {  
//        if (handler instanceof HandlerMethod) {  
//            HandlerMethod handlerMethod = (HandlerMethod) handler;  
//            Method method = handlerMethod.getMethod();  
//            ResubmitToken annotation = method.getAnnotation(ResubmitToken.class);  
//            if (annotation != null) {
//            	// 通过url和请求参数是否相同判断是否进行拦截
//                if(repeatDataValidator(request))//如果重复相同数据  
//                    return false;  
//                else   
//                    return true;  
//            }  
//            return true;  
//        } else {  
//            return super.preHandle(request, response, handler);  
//        }  
//    }  
//	/** 
//	 * 验证同一个url数据是否相同提交  ,相同返回true 
//	 * @param httpServletRequest 
//	 * @return 
//	 */  
//	public boolean repeatDataValidator(HttpServletRequest request)  
//		{  
//		    // 将请求参数装换为json字符串
//		    String params=JSONObject.toJSONString(request.getParameterMap());  
//		    // 返回请求路径
//		    String url=request.getRequestURI();  
//		    Map<String,String> map=new HashMap<String,String>();  
//		    map.put(url, params);  
//		    String nowUrlParams=map.toString();//  
//		      
//		    Object preUrlParams=request.getSession().getAttribute("repeatData");  
//		    if(preUrlParams==null)//如果上一个数据为null,表示还没有访问页面  
//		    {  
//		    	request.getSession().setAttribute("repeatData", nowUrlParams);  
//		        return false;  
//		    }  
//		    else//否则，已经访问过页面  
//		    {  
//		        if(preUrlParams.toString().equals(nowUrlParams))//如果上次url+数据和本次url+数据相同，则表示城府添加数据  
//		        {  
//		            return true;  
//		        }  
//		        else//如果上次 url+数据 和本次url加数据不同，则不是重复提交  
//		        {  
//		        	request.getSession().setAttribute("repeatData", nowUrlParams);  
//		            return false;  
//		        }  
//		          
//		    }  
//		}
	
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            ResubmitToken annotation = method.getAnnotation(ResubmitToken.class);
            if (annotation != null) {
                boolean needSaveSession = annotation.save();
                if (needSaveSession) {
                    request.getSession(false).setAttribute("ResubmitToken", UUID.randomUUID().toString());
                }
                boolean needRemoveSession = annotation.remove();
                if (needRemoveSession) {
                    if (isRepeatSubmit(request)) {
                        return false;
                    }
                    request.getSession(false).removeAttribute("ResubmitToken");
                }
            }
            return true;
        } else {
            return super.preHandle(request, response, handler);
        }
    }

    private boolean isRepeatSubmit(HttpServletRequest request) {
        String serverToken = (String) request.getSession(false).getAttribute("ResubmitToken");
        if (serverToken == null) {
            return true;
        }
        String clinetToken = request.getParameter("ResubmitToken");
        if (clinetToken == null) {
            return true;
        }
        if (!serverToken.equals(clinetToken)) {
            return true;
        }
        return false;
    }

}
