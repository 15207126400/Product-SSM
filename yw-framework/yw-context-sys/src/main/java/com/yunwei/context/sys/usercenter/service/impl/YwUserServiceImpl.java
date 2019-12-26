package com.yunwei.context.sys.usercenter.service.impl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.apache.log4j.Logger;

import com.yunwei.context.sys.usercenter.dao.YwUserDao;
import com.yunwei.context.sys.usercenter.model.YwUser;
import com.yunwei.context.sys.usercenter.service.YwUserService;
import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.common.util.DateUtil;


@Service
public class YwUserServiceImpl extends IBaseServiceImpl<YwUser> implements YwUserService{
	
    private static Logger logger = Logger.getLogger(YwUserServiceImpl.class);
	@Autowired
	private YwUserDao ywUserDao;

	@Override
	protected IBaseDao<YwUser> getBaseDao() {
		return ywUserDao;
	}

	@Override
	public String shopSystemLogin(Model model, YwUser ywUser,HttpServletRequest request) {
		ywUser = super.query(ywUser);
    	if(ywUser != null){
			// 判断时间是否为空
			if(null != ywUser.getExpire_datetime()){
				// 判断用户账号是否过期
				boolean ss = DateUtil.isTimeIn(new Date() , ywUser.getPay_datetime() , ywUser.getExpire_datetime());
				System.err.println("ss:" + ss);
				if(ss == true){		// 若账号未过期
					// 登陆成功 将数据放入session中
					request.getSession().setAttribute("user",ywUser);
					return "operatorMain";
				}else{
					// 账号已过期
					model.addAttribute("msg","提示 : 该账号已过期,请续费后再使用!");
					return "operatorLogin";
				}
			}else {	
				// 体验账号 , 等待后台工作人员授权
				model.addAttribute("msg","提示 : 请耐心等待工作人员授权!");
				return "operatorLogin";
			}
		}else{
			model.addAttribute("msg","提示 : 用户名或者密码输入错误!");
			return "operatorLogin";
		}
	}

	@Override
	public String examSystemLogin(Model model, YwUser ywUser, HttpServletRequest request) {
		ywUser = super.query(ywUser);
    	if(ywUser != null){
    		// 登陆成功 将数据放入session中
			request.getSession().setAttribute("user",ywUser);
			return "operatorMain";
		}else{
			model.addAttribute("msg","提示 : 身份证号或考生姓名或密码输入错误!");
			return "operatorLogin";
		}
	}
	
	
	
	
}
