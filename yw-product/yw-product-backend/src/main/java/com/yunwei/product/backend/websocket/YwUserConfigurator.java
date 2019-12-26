package com.yunwei.product.backend.websocket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import com.yunwei.common.user.SysUser;
import com.yunwei.context.sys.usercenter.model.YwUser;


/**
 * 系统用户配置，设置到websocketsession中
* @ClassName: SysUserConfigurator 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年10月15日 下午12:49:19 
*
 */
public class YwUserConfigurator extends SpringConfigurator {

	private Logger logger = LoggerFactory.getLogger(getClass());

	public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
//		List<String> cookies = request.getHeaders().get("cookie");
//		Map<String, String> cookieMap = new HashMap<String, String>();
//		for (String cookie : cookies) {
//			for (String c : cookie.split(";")) {
//				int index = c.indexOf("=");
//				if (index > 0) {
//					cookieMap.put(StringUtils.trim(c.substring(0, index)), StringUtils.trim(c.substring(index + 1)));
//				}
//			}
//		}
//		if (cookieMap.containsKey(SysUser.YW_USER_SESSION)) {
			try {
				//CrhUser user = CookieUtil.readUserFromBase64Str(cookieMap.get(CrhUser.CRH_USER_SESSION));
				HttpSession session = (HttpSession) request.getHttpSession();
				YwUser user = (YwUser) session.getAttribute("user");
				sec.getUserProperties().put(SysUser.YW_USER_SESSION, user);
				logger.info("用户信息：{}", user.toString());
			} catch (Exception e) {
				logger.error("websocket握手发生错误", e);
			}
//		}

	}
}
