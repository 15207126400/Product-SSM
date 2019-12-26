package com.yunwei.context.sys.usercenter.interceptor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.yunwei.common.annotation.NoLogin;
import com.yunwei.common.annotation.PersonalityIndex;
import com.yunwei.common.exception.UserTimeoutException;
import com.yunwei.common.user.SysUser;
import com.yunwei.common.util.RedisClientUtil;
import com.yunwei.common.util.SpringContext;
import com.yunwei.common.view.freemarker.LayoutModel;
import com.yunwei.context.sys.usercenter.model.YwUser;

/**
 * 系统用户登录拦截器服务类
* @ClassName: LoginInterceptor 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年4月17日 下午2:34:01 
*
 */
public class LoginInterceptor implements HandlerInterceptor {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		// 判断方法上是否有免登录注解
	    if(handler instanceof HandlerMethod){  
            logger.debug("获取到handler,判断Nologin注解是否存在");  
            HandlerMethod hm = (HandlerMethod) handler;  
            Method method = hm.getMethod();
            // 获取方法上的自定义注解（NoLogin）
            NoLogin noLogin = method.getAnnotation(NoLogin.class);  
            if(noLogin != null){  
                logger.debug("NoLogin注解存在,不进行拦截");
                return true; 
            } else {
            	YwUser user = (YwUser) request.getSession().getAttribute("user");  
            	if(user != null) {
            		// 存储到request中,参数自动注入
            		SysUser sysUser = new SysUser();
            		sysUser.setUser_id(user.getUser_id());
            		sysUser.setUser_name(user.getUser_name());
            		sysUser.setUser_avg(user.getUser_avg());
            		sysUser.setPhone_tel(user.getPhone_tel());
            		sysUser.setId_no(user.getId_no());
            		sysUser.setPassword(user.getPassword());
            		sysUser.setUser_type(user.getUser_type());
            		sysUser.setOrder_count(RedisClientUtil.get(user.getUser_id()));
            		sysUser.setCss_name(user.getCss_name());
        	    	request.setAttribute(SysUser.YW_USER_SESSION, sysUser);
                    LayoutModel.addAttribute("user", sysUser);
        	    	// 存储用户到全局变量，后面切换数据库用
        	    	request.getServletContext().setAttribute("user", user);
        	    	return true;  
        	    } else {
        	    	throw createUserTimeoutException();
        	    }
            }
        }  
	    
	    return true;
	    
	}
	
	protected UserTimeoutException createUserTimeoutException(){
		Map<String, Object> infos=new HashMap<String, Object>();
		infos.put("error_type","-8023");
		UserTimeoutException userTimeoutException=new UserTimeoutException("-1", "用户未登录或已超时");
		userTimeoutException.setInfos(infos);
		return userTimeoutException;
	}
    
    public void postHandle(HttpServletRequest request, HttpServletResponse response,Object handler, ModelAndView modelAndView) throws Exception {
    	// 判断方法是否有个性化首页注解
    	if(handler instanceof HandlerMethod){ 
    		HandlerMethod hm = (HandlerMethod) handler;  
            Method method = hm.getMethod();
            PersonalityIndex personalityIndex = method.getAnnotation(PersonalityIndex.class);
            if(personalityIndex != null){
            	// 根据不同用户来选择不同首页
            	YwUser user = (YwUser) request.getSession().getAttribute("user");
            	SysUser sysUser = (SysUser) request.getAttribute(SysUser.YW_USER_SESSION);
            	
            	if(!(StringUtils.equals(user.getUser_id(), "YW-20180313092309320") || StringUtils.equals(sysUser.getSys_admin_id(), "YW-20180313092309320"))){
            		modelAndView.setViewName("index/default/operatorMainRight");
            	} 
            }
    	}

    }
    
    public void afterCompletion(HttpServletRequest request,HttpServletResponse response, Object handler, Exception exception)throws Exception {
         
    }
    
}
