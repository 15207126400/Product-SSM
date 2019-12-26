package com.yunwei.product.backend.websocket.serivce.impl;

import java.io.IOException;
import java.util.List;

import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwei.product.backend.websocket.serivce.YwUserCustomerWebSocketSerivce;
import com.yunwei.product.backend.websocket.session.YwUserCustomerWebSocketSession;

/**
 * 云维客户websocketservice(主要用于即时信息发送)
* @ClassName: YwUserCustomerWebSocketSerivce 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年10月15日 下午2:14:29 
*
 */
@Service
public class YwUserCustomerWebSocketSerivceImpl implements YwUserCustomerWebSocketSerivce{
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private YwUserCustomerWebSocketSession ywUserCustomerWebSocketSession;

	@Override
	public void sendMessage(String user_id, String message) {
		Session session = ywUserCustomerWebSocketSession.getSession(user_id);
		if (session == null || !session.isOpen()) {
			logger.debug("操作员operator_no={}的websocket连接已断开，不向其发送消息", user_id);
			return;
		}

		Basic remoteEndpoint = session.getBasicRemote();
		try {
			remoteEndpoint.sendText(message);
		} catch (Exception e) {
			logger.error("向操作员operator_no={}推送消息发生错误[{}]", user_id, e.getMessage());
		}
	}

	@Override
	public void sendMessage(List<?> user_ids, String message) {
		for (Object user_id : user_ids) {
			if (user_id == null) {
				continue;
			}
			sendMessage(user_id.toString(), message);
		}
	}

	
}
