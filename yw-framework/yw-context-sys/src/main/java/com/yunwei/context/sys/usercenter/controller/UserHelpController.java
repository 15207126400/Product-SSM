package com.yunwei.context.sys.usercenter.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 用户帮助服务
* @ClassName: UserHelpController 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年9月17日 下午5:25:43 
*
 */
@Controller
@RequestMapping
public class UserHelpController {

	/**
	 * 帮助跳转页面
	*@return
	*ModelAndView
	 */
	@RequestMapping("operatorHelp")
	public String operatorHelp(String help_flag){
		switch(help_flag){	
		case "0":						
            return "help/operatorHelp"; 		// 通用帮助文档
            
        case "1":						
            return "help/operatorProductHelp"; 		// 商品管理帮助页面
            
        case "2":
        	return "help/operatorSaleHelp";			// 营销管理帮助页面
        	
        case "3":
        	return "help/operatorSmsHelp";			// 短信管理帮助页面
        	
        case "4":
        	return "help/operatorMemberHelp";			// 会员管理帮助页面
        	
        case "5":
        	return "help/operatorBusinessHelp";			// 商家管理帮助页面
        	
        case "6":
        	return "help/operatorPointHelp";			// 积分管理帮助页面
        	
        case "7":
        	return "help/operatorSigninHelp";			// 签到管理帮助页面
        	
        case "8":
        	return "help/operatorBaseconfigHelp";			// 配置中心管理帮助页面
        	
        case "9":
        	return "help/operatorXcxconfigHelp";			// 小程序版本管理帮助页面
        	
        case "10":
        	return "help/operatorImportHelp";			// 导入管理帮助页面
        	
        default :
        	return "help/operatorHelp";				// 默认跳转通用帮助文档
        }
	}
	
}
