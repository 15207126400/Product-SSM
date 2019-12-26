package com.yunwei.context.sys.usercenter.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yunwei.common.annotation.NoLogin;
import com.yunwei.common.util.OrderUtil;
import com.yunwei.context.sys.usercenter.model.YwUser;
import com.yunwei.context.sys.usercenter.service.YwUserService;

/**
 * 用户申请体验信息服务
* @ClassName: UserTryInfoController 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年9月17日 下午5:21:52 
*
 */
@Controller
public class UserTryInfoController {

	@Autowired
	private YwUserService ywUserService;
	
	/**
	 * 申请体验账号跳转路径
	*
	*@return
	*ModelAndView
	 */
	@NoLogin
	@RequestMapping("operatorTryInfo")
	public ModelAndView experienceAccountUserPath(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("operatorTryInfo");
		
		return mav;
	}
	
	/**
	 * 申请体验账号 , 保存数据
	*
	*@param request
	*@param ywUser
	*@return
	*Map<String,Object>
	 */
	@NoLogin
	@RequestMapping("operatorTryInfoSubmit")
	@ResponseBody
	public Map<String,Object> experienceAccountUser(HttpServletRequest request , YwUser ywUser){
		Map<String,Object> map = new HashMap<String, Object>();
		ywUser.setUser_id(OrderUtil.getOrderNo());
		ywUser.setCreate_datetime(new Date());
		ywUserService.insert(ywUser);
		map.put("error_no", "0");
		map.put("error_info", "申请成功,请等待管理员授权");
		
		return map;
	}
}
