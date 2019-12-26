package com.yunwei.context.sys.usercenter.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.yunwei.common.base.serivce.IBaseSerivce;
import com.yunwei.context.sys.usercenter.model.YwUser;

/**
 * 用户信息service
* @ClassName: YwUserService 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年09月17日 下午14:31:51
*
 */
public interface YwUserService extends IBaseSerivce<YwUser>{
	
	/**
	 * 商城系统登录
	 * @param model
	 * @param ywUser
	 * @return
	 */
	public String shopSystemLogin(Model model,YwUser ywUser,HttpServletRequest request);
	
	/**
	 * 考试系统登录
	 * @param ywUser
	 * @param request
	 * @return
	 */
	public String examSystemLogin(Model model, YwUser ywUser,HttpServletRequest request);

}
