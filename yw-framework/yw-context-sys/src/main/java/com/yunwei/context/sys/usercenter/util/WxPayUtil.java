package com.yunwei.context.sys.usercenter.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.yunwei.common.exception.BizException;
import com.yunwei.common.util.RequestUtil;
import com.yunwei.common.util.SpringContext;
import com.yunwei.context.payment.support.WxPayConfig;
import com.yunwei.context.sys.cache.YwUserCustomerXcxCache;
import com.yunwei.context.sys.usercenter.model.YwUser;
import com.yunwei.context.sys.usercenter.model.YwUserCustomerXcx;

/**
 * 云维支付工具类
* @ClassName: WxPaymentUtil 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年9月20日 下午3:49:11 
*
 */
public class WxPayUtil {

	private static Logger logger = Logger.getLogger(WxPayUtil.class);
	
	/**
	 * 得到微信支付条件配置信息
	 * @return
	 */
	public static WxPayConfig getWxPaymentConfig(){
		// 从数据库缓存中获取小程序信息
		HttpServletRequest request = RequestUtil.getRequest();
		YwUserCustomerXcxCache YwUserCustomerXcxCache = (YwUserCustomerXcxCache) SpringContext.getBean("ywUserCustomerXcxCache");
		YwUserCustomerXcx YwUserCustomerXcx= null;
		if(null != request){
	    	Object user = request.getSession().getAttribute("user");
	    	YwUser ywUser = (YwUser) user;
	    	if(user != null){
	    		YwUserCustomerXcx = YwUserCustomerXcxCache.getYwUserCustomerXcx(ywUser.getUser_id());
	    	} else {
	    		// 小程序切换数据库
	    		String wx_appid = request.getParameter("wx_appid");
	    		if(StringUtils.isBlank(wx_appid)){
	    			// 该wx_appid从拦截器里面获取
	    			wx_appid = (String) request.getAttribute("wx_appid");
	    		}
	    		YwUserCustomerXcx = YwUserCustomerXcxCache.getYwUserCustomerXcxByWxAppid(wx_appid);
	    	}
	    } else {
	    	// 解决定时器执行没有切换数据库的问题
	    	WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
	    	ServletContext servletContext = webApplicationContext.getServletContext();
	    	YwUser ywUser = (YwUser) servletContext.getAttribute("user");
	    	if(ywUser != null){
	    		YwUserCustomerXcx = YwUserCustomerXcxCache.getYwUserCustomerXcx(ywUser.getUser_id());
	    	} else {
	    		logger.info("用户没有登录，定时器无法根据用户获取小程序配置信息");
	    	}
	    }
		
		// 判断YwUserCustomerXcx是否为空
		if(YwUserCustomerXcx == null){
			logger.info("小程序配置信息为空，请检查小程序后台配置信息是否存在或者已停用");
			throw new BizException("小程序配置信息为空，请检查小程序后台配置信息是否存在或者已停用");
		}
	     
		// 装换成支付条件配置信息
		WxPayConfig wxPaymentConfig = new WxPayConfig();
		try {
			BeanUtils.copyProperties(wxPaymentConfig, YwUserCustomerXcx);
		} catch (Exception e) {
			logger.info("bean对象copy失败：{}",e);
			throw new BizException("bean对象copy失败：{}",e);
		}
		return wxPaymentConfig;
	}
}
