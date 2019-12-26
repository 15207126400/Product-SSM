package com.yunwei.product.backend.business.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yunwei.common.user.SysUser;
import com.yunwei.context.sys.service.YwRoleService;
import com.yunwei.context.sys.usercenter.model.YwUser;
import com.yunwei.context.sys.usercenter.model.YwUserCustomer;
import com.yunwei.context.sys.usercenter.model.YwUserCustomerXcx;
import com.yunwei.context.sys.usercenter.service.YwUserCustomerService;
import com.yunwei.context.sys.usercenter.service.YwUserCustomerXcxService;
import com.yunwei.context.sys.usercenter.service.YwUserService;

/**
 * 
* @ClassName: YwBusinessManage 
* @Description: 商家管理 
* @author zhangjh
* @date 2018年3月17日 下午2:54:39 
*
 */
@Controller
@RequestMapping("/business/ywBusiness")
public class YwBusinessManage {

	@Autowired
	private YwUserService ywUserService;
	@Autowired
	private YwRoleService ywRoleService;
	@Autowired
	private YwUserCustomerService ywUserCustomerService;
	@Autowired
	private YwUserCustomerXcxService ywUserCustomerXcxService;
	
	/**
	 * 修改个人信息跳转
	 * @return
	 */
	@RequestMapping
	public String getBusinessList(Model model,SysUser sysUser ,HttpServletRequest request){
		// 获取基本信息
		YwUser ywUser = new YwUser();
		ywUser.setUser_id(sysUser.getUser_id());
		model.addAttribute("ywUser",ywUserService.query(ywUser));
		
		// 获取客户信息
		YwUserCustomer ywUserCustomer = new YwUserCustomer();
		ywUserCustomer.setUser_id(sysUser.getUser_id());
		model.addAttribute("ywUserCustomer", ywUserCustomerService.query(ywUserCustomer));
		
		// 获取客户小程序信息
		YwUserCustomerXcx ywUserCustomerXcx = new YwUserCustomerXcx();
		ywUserCustomerXcx.setUser_id(sysUser.getUser_id());
		model.addAttribute("ywUserCustomerXcx", ywUserCustomerXcxService.query(ywUserCustomerXcx));
		
		return "business/ywBusinessEdit";
	}
	
	/**
	 * 修改
	 * @return
	 */
	@RequestMapping("insertBusiness")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(YwUserCustomer ywUserCustomer,Model model,HttpServletRequest request){
		Map<String,Object> map = new HashMap<String, Object>();
		ywUserCustomerService.update(ywUserCustomer);
		map.put("error_no", "0");
		map.put("error_info", "修改成功");
		return map;
	}
}
