package com.yunwei.context.sys.usercenter.controller;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.yunwei.common.annotation.NoLogin;
import com.yunwei.common.util.DateUtil;
import com.yunwei.context.sys.usercenter.model.YwUser;
import com.yunwei.context.sys.usercenter.model.form.LoginForm;
import com.yunwei.context.sys.usercenter.service.YwUserService;

/**
 * 用户登录服务
* @ClassName: UsersLoginController 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年9月17日 下午4:28:11 
*
 */
@NoLogin
@Controller
public class UserLoginController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private YwUserService ywUserService;

	/**
	 * 登录页面跳转
	 * @param request
	 * @return
	 */
	@NoLogin
	@RequestMapping("login")
	public String login(HttpServletRequest request) {
		// 判断用户是否登录
		Object user = request.getSession().getAttribute("user");  
    	if(user != null) {
    		return "operatorMain";
	    } else {
	    	return "operatorLogin";
	    }
	}
	
	/**
	 * 登录服务
	 * @param model
	 * @param ywUser
	 * @param loginForm
	 * @param request
	 * @return
	 */
	@NoLogin
	@RequestMapping("operatorLogin")
	public String userLogin(Model model,YwUser ywUser,LoginForm loginForm,HttpServletRequest request){
		// 判断用户是否登录
		Object user = request.getSession().getAttribute("user");  
    	if(user != null) {
    		return "operatorMain";
	    } else {
	    	if(StringUtils.equals(loginForm.getSubsys_id(), "1")){
	    		// 商城系统登录
	    		return ywUserService.shopSystemLogin(model, ywUser, request);
	    	} else if(StringUtils.equals(loginForm.getSubsys_id(), "2")){
	    		// 考试系统登录
	    		return ywUserService.examSystemLogin(model, ywUser, request);
	    	}
	    	return "operatorLogin";
	     }
	}
	
	/**
	 * 退出登录
	 * @param request
	 * @return
	 */
	@NoLogin
	@RequestMapping("operatorLoginOut")
	public String loginOut(HttpServletRequest request) {
		//清除session中的用户信息
		request.getSession().removeAttribute("user");
		
        return "operatorLogin";
	}
	
}
