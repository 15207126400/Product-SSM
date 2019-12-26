package com.yunwei.context.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yunwei.common.annotation.NoLogin;


/**
 * 异常页面控制器
 * @author zhangjh
 *
 */
@Controller
@RequestMapping
public class BizExceptionController {

	@NoLogin
	@RequestMapping("404.htm")
	public String Page404(){
		
		return "404error";
	}
	
	@NoLogin
	@RequestMapping("500.htm")
	public String Page500(){
		return "500error";
	}
}
