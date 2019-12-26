package com.yunwei.product.backend.websocket;

import javax.annotation.PostConstruct;
import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import com.alibaba.fastjson.JSON;
import com.yunwei.common.exception.BizException;
import com.yunwei.common.user.SysUser;
import com.yunwei.common.util.RedisClientUtil;
import com.yunwei.context.sys.usercenter.model.YwUser;
import com.yunwei.context.sys.usercenter.service.YwUserCustomerXcxService;
import com.yunwei.product.backend.websocket.session.YwUserCustomerWebSocketSession;



/**
 * 云维客户websocket(主要用于即时连接)
* @ClassName: YwUserCustomerWebSocket 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年10月15日 上午10:47:30 
*
 */
@ServerEndpoint(value="/system/websocket/usercustomer.ws",configurator = YwUserConfigurator.class)
public class YwUserCustomerWebSocket {
	
	private static Logger logger = LoggerFactory.getLogger(YwUserCustomerWebSocket.class);
	
	@Autowired
	private YwUserCustomerWebSocketSession ywUserCustomerWebSocketSession;
	
	private static final String HEARTBEAT = "heartbeat";

	@PostConstruct
	public void init() {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	
	@OnMessage
	public void onMessage(String message, final Session session){
		logger.debug("sessionId={}的websocket连接收到消息[{}]", session.getId(), message);
		if (HEARTBEAT.equals(message)) {
			return;
		}
		
		try {
			YwUser user = (YwUser)session.getUserProperties().get(SysUser.YW_USER_SESSION);
			if (user == null) {
				throw new BizException("-1", "用户未登录或已超时");
			}
			
			String user_id = user.getUser_id();
			if (StringUtils.isBlank(user_id)) {
				throw new BizException("-1", "传入参数为有误" + "[user_id]");
			}
//
//			addEmpOnlineToRedis(operator_no);
			logger.info("操作员user_id={}已经连接后台websocket服务器", user_id);
//
//			sessionCollection.add(operator_no, session);
//			publishOperatorLogin(operator_no);
			// 添加会话session到缓存中
			ywUserCustomerWebSocketSession.add(user_id, session);
//
//			cacheManager.refreshOneWithOthers(operatorinfoCache.getId());
//			RedisClientUtil.set(Fields.ONLINEEMP + user.getUser_id(), JSON.toJSONString(user));
			
			//ywUserCustomerXcxService.query(t);
		} catch (BizException e) {
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	@OnOpen
	public void onOpen(Session session, EndpointConfig config){
		logger.debug("sessionId={}建立websocket连接", session.getId());
		try {
			YwUser user = (YwUser)session.getUserProperties().get(SysUser.YW_USER_SESSION);
			if (user == null) {
				throw new BizException("-1", "用户未登录或已超时");
			}
		} catch (BizException e) {
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	@OnClose
	public void onClose(Session session, CloseReason reason) {
		logger.debug("sessionId={}断开websocket连接,原因[{}]", session.getId(), reason.toString());
		try {
			YwUser user = (YwUser)session.getUserProperties().get(SysUser.YW_USER_SESSION);
			if (user == null) {
				throw new BizException("-1", "用户未登录或已超时");
			}
			ywUserCustomerWebSocketSession.remove(user.getUser_id());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@OnError
	public void onError(Session session, Throwable t) {
		logger.error("sessionId={}的websocket连接出现异常[{}]", session.getId(), t.getMessage());
	}
	
	/**
	 * 添加坐席在线人数到Redis
	 * @param operator_no
	 */
	private void addEmpOnlineToRedis(String user_id) throws Exception {
		RedisClientUtil.addToRedisSetById("user_ids", (double)System.currentTimeMillis(), user_id);
	}
}
