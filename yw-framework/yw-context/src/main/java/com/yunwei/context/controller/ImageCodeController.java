package com.yunwei.context.controller;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunwei.common.annotation.NoLogin;
import com.yunwei.common.exception.BizException;
import com.yunwei.context.service.IImageCodeService;


/**
 * 功能说明: 图片验证码<br>
 * 系统版本: v1.0<br>
 * 开发人员: @author kongdy<br>
 * 开发时间: 2015年10月30日<br>
 */
@Controller
@RequestMapping
public class ImageCodeController {

	@Autowired
	IImageCodeService verifyCodeService;

	/**
	 * 获取验证码
	 * @param request
	 * @param response
	 * @param session
	 */
	@NoLogin
	@RequestMapping("imageCode.img")
	public void verifyCode(HttpServletRequest request, HttpServletResponse response,HttpSession session) {
		String verifyCode="";
		try {
			//String loginIp = RequestUtil.getIpAddress(request); 
			// 生成随机验证码内容
			verifyCode= verifyCodeService.getRandString();
//			String sessionId=CookieUtil.getCookie(request, CookieUtil.COOKIE_CRH_SESSION_ID);
//			// 存入redis，以便集群服务器均可获取，3分钟过期
			// 存入session,3分钟过期
			verifyCodeService.saveValidateCode(verifyCode, session);

			response.setContentType("image/png;charset=UTF-8");// 解决chrome下图片验证码显示不出来的问题
			response.setHeader("Pragma", "No-cache");
	        response.setHeader("Cache-Control", "no-cache");
	        response.setDateHeader("Expires", 0);
	        // 生成图片
			BufferedImage image =  verifyCodeService.createVerifyCodeImage(verifyCode);
			ImageIO.write(image, "png", response.getOutputStream());
	        response.getOutputStream().flush();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
		//return null;
	}
	
	/**
	 * 校验验证码
	 * @param request
	 * @param response
	 * @param session
	 * @param verifyCode
	 * @return
	 */
	@NoLogin
	@RequestMapping("checkVerifycode.json")
	@ResponseBody
	public Map<String, Object> checkVerifycode(HttpServletRequest request, HttpServletResponse response,HttpSession session,@Valid @NotBlank String verifyCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(verifyCodeService.checkVerifycode(verifyCode, session)){
				return map;
			} else {
				throw new BizException("验证码校验错误");
			}

		} catch (Exception e) {
			throw new BizException(e);
		}
	}
}
