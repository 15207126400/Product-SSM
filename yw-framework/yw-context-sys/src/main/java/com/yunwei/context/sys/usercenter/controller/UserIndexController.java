package com.yunwei.context.sys.usercenter.controller;

import java.util.ArrayList;
import java.util.Date;
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
import com.yunwei.common.annotation.NoLogin;
import com.yunwei.common.annotation.PersonalityIndex;
import com.yunwei.common.user.SysUser;
import com.yunwei.common.util.DateUtil;
import com.yunwei.common.util.OrderUtil;
import com.yunwei.common.view.json.ResponseBodyFilter;
import com.yunwei.context.sys.model.YwDictionary;
import com.yunwei.context.sys.model.YwSystemNotice;
import com.yunwei.context.sys.service.YwDictionaryService;
import com.yunwei.context.sys.service.YwSystemNoticeService;
import com.yunwei.context.sys.usercenter.model.YwUser;
import com.yunwei.context.sys.usercenter.model.YwUserCustomer;
import com.yunwei.context.sys.usercenter.service.YwUserCustomerService;
import com.yunwei.context.sys.usercenter.service.YwUserService;

/**
 * 首页内容加载服务
* @ClassName: IndexController 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年9月17日 下午5:24:59 
*
 */
@Controller
@RequestMapping
public class UserIndexController {
	@Autowired
	private YwUserCustomerService ywUserCustomerService;
	@Autowired
	private YwSystemNoticeService ywSystemNoticeService;
	@Autowired
	private YwUserService ywUserService;
	
    /**
     * 内容加载
     * @return
     */
	@RequestMapping("operatorMain")
	public String operatorMain() {
		return "operatorMain";
	}

	/**
	 * 内容头部加载
	 * @param sysUser
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("operatorMainTop")
	public String operatorMainTop(SysUser sysUser,HttpServletRequest request,Model model) {
		YwUserCustomer ywUserCustomer = new YwUserCustomer();
		ywUserCustomer.setUser_id(sysUser.getUser_id());
		ywUserCustomer = ywUserCustomerService.query(ywUserCustomer);
		model.addAttribute("user",ywUserCustomer);
		//获取用户基本账号信息,显示用户头像
		List<YwUser> ywUsers = ywUserService.queryList();
		for(YwUser ywUser : ywUsers){
			if((sysUser.getUser_id()).equals(ywUser.getUser_id())){
				model.addAttribute("ywUser",ywUser);
			}
		}
		
		//获取平台内部公告
		List<YwSystemNotice> systemNotices = new ArrayList<YwSystemNotice>();
		YwSystemNotice ysn = new YwSystemNotice();
		ysn.setNotice_status("1");
		List<YwSystemNotice> ywSystemNotices = ywSystemNoticeService.queryList(ysn);
		if(ywSystemNotices.size() > 0){
			for(YwSystemNotice ywSystemNotice : ywSystemNotices){
				if("0".equals(ywSystemNotice.getUser_id()) || (sysUser.getUser_id()).equals(ywSystemNotice.getUser_id())){
					systemNotices.add(ywSystemNotice);
				}
			}
		}
		
		model.addAttribute("systemNotices" , systemNotices);
		
		return "operatorMainTop";
	}

	/**
	 * 内容左边加载
	 * @param sysUser
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("operatorMainLeft")
	public String operatorMainLeft(SysUser sysUser,HttpServletRequest request,Model model) {
		model.addAttribute("user",sysUser);
		getCustomerOrOperator(sysUser,model);
		return "operatorMainLeft";

	}

	/**
	 * 内容右边加载(默认加载首页)
	 * @param sysUser
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("operatorMainRight")
	@PersonalityIndex
	public String operatorMainRight(SysUser sysUser,HttpServletRequest request,Model model) {
//		model.addAttribute("user",sysUser);
//		getCustomerOrOperator(sysUser,model);
		model.addAttribute("user",sysUser);
		return "operatorMainRight";
	}
	
	
	/**
	 * 查询客户或者客户下的操作员信息
	 * @param sysUser
	 * @param model
	 */
	private void getCustomerOrOperator(SysUser sysUser,Model model){
		// 查询客户信息
		if(StringUtils.equals(sysUser.getUser_type(), "1")){
			YwUserCustomer customer = new YwUserCustomer();
			customer.setUser_id(sysUser.getUser_id());
			customer = ywUserCustomerService.query(customer);
			model.addAttribute("customer",customer);
		}
	}

}
