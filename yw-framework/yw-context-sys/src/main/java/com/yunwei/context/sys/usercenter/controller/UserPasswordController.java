package com.yunwei.context.sys.usercenter.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yunwei.common.annotation.NoLogin;
import com.yunwei.common.user.SysUser;
import com.yunwei.common.view.json.ResponseBodyFilter;
import com.yunwei.context.sys.model.YwDictionary;
import com.yunwei.context.sys.service.YwDictionaryService;
import com.yunwei.context.sys.usercenter.model.YwUser;
import com.yunwei.context.sys.usercenter.service.YwUserService;

/**
 * 密码管理服务
* @ClassName: UserPasswordController 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年9月17日 下午4:29:11 
*
 */
@Controller
public class UserPasswordController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private YwUserService ywUserService;
	@Autowired
	private YwDictionaryService ywDictionaryService;
	
	/**
	 * 登录状态下重置密码页面跳转
	*@param ywUser
	*@return
	*ModelAndView
	 */
	@RequestMapping("operatorResetPassword")
	public ModelAndView resetPasswordPath(SysUser sysUser){
		ModelAndView mav = new ModelAndView();
		YwUser ywUser = new YwUser();
		ywUser.setUser_id(sysUser.getUser_id());
		mav.addObject("ywUser", ywUserService.query(ywUser));
		mav.setViewName("operatorResetPassword");
		
		return mav;
	}
	
	/**
	 * 非登录状态下重置密码页面跳转(找回密码)
	*@param ywUser
	*@return
	*ModelAndView
	 */
	@NoLogin
	@RequestMapping("operatorResetPassword2")
	public ModelAndView resetPasswordPath(String user_name){
		ModelAndView mav = new ModelAndView();
		YwUser ywUser = new YwUser();
		ywUser.setUser_name(user_name);
		mav.addObject("ywUser", ywUserService.query(ywUser));
		mav.setViewName("operatorResetPassword");
		
		return mav;
	}
	
	/**
	 * 修改密码
	*@param ywUser
	*@return
	*Map<String,Object>
	 */
	@NoLogin
	@RequestMapping("operatorResetPasswordSubmit")
	@ResponseBody
	public Map<String,Object> resetPassword(YwUser ywUser , Model model){
		Map<String,Object> map = new HashMap<String, Object>();
		int num = ywUserService.update(ywUser);
		if(num > 0){
			map.put("num", "1");
		}else{
			map.put("num", "0");
		}
		
		return map;
	}
	

	/**
	 * 找回密码路径跳转
	*@return
	*ModelAndView
	 */
	@NoLogin
	@RequestMapping("operatorForgetPassword")
	public ModelAndView forgetPsdPath(){
		ModelAndView mav = new ModelAndView();
		mav.addObject("op_type", "0");
		mav.setViewName("operatorForgetPassword");
		
		return mav;
	}
	
	/**
	 * 验证账号  , 返回对应的密码找回提问内容
	*@param request
	*@param ywUser
	*@return
	*Map<String,Object>
	 */
	@NoLogin
	@RequestMapping("operatorForgetPasswordSubmit")
	@ResponseBody
	//@ResponseBodyFilter(value = {"password_question"})
	public Map<String,Object> forgetPsdUsername(YwUser ywUser){
		Map<String,Object> map = new HashMap<String, Object>();
		ywUser = ywUserService.query(ywUser);
		if(ywUser != null){
			if(StringUtils.isNotEmpty(ywUser.getPassword_question())){
				YwDictionary ywDictionary = new YwDictionary();
				ywDictionary.setDic_key("1049");
				ywDictionary.setDic_subkey(ywUser.getPassword_question());
				ywDictionary = ywDictionaryService.query(ywDictionary);
				map.put("password_question", ywDictionary.getDic_subvalue());
				map.put("user_id", ywUser.getUser_id());
			}else{
				map.put("password_question", "1");
			}
		}else{
			map.put("password_question", "2");
		}
		
		return map;
	}
	
	/**
	 * 校正用户提交的问题答案是否匹配  , 若匹配则跳转重置密码页面
	*@param ywUser
	*@return
	*Map<String,Object>
	 */
	@NoLogin
	@RequestMapping(value="forget_psd")
	@ResponseBody
	public Map<String,Object> forgetPsd(YwUser ywUser , Model model){
		Map<String,Object> map = new HashMap<String, Object>();
		YwUser user = new YwUser();
		List<YwUser> userlist = ywUserService.queryList();
		for(YwUser item : userlist){
			if(item.getUser_id().equals(ywUser.getUser_id())){
				user = item;
			}
		}
		
		if(StringUtils.equals(ywUser.getPassword_answer(), user.getPassword_answer())){
			map.put("username", ywUser.getUser_name());
		}else{
			map.put("username", "-1");
		}
		return map;
	}
	
}
