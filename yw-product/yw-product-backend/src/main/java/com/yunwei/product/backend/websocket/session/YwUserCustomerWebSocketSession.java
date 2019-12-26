package com.yunwei.product.backend.websocket.session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.Session;

import org.springframework.stereotype.Component;

/**
 * 云维客户websocket的会话session(主要用于保存客户即时会话信息状态)
* @ClassName: YwUserCustomerWebSocketSession 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年10月15日 下午1:41:04 
*
 */
@Component
public class YwUserCustomerWebSocketSession {

	
	private Map<String, Session> map = new ConcurrentHashMap<String, Session>();

	/**
	 * 根据操作员的user_id获取Session
	 * @param operator_no
	 * @return
	 */
	public Session getSession(String user_id) {
		return map.get(user_id);
	}

	/**
	 * 将操作员及其对应的Session添加至集合中
	 * @param operator_no 操作员的user_id
	 * @param session
	 */
	public void add(String user_id, Session session) {
		map.put(user_id, session);
	}

	/**
	 * 操作员断开连接，将其移除
	 * @param operator_no
	 */
	public void remove(String user_id) {
		map.remove(user_id);
	}
}
