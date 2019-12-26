package com.yunwei.product.backend.websocket.serivce;

import java.util.List;

/**
 * 云维客户websocketservice(主要用于即时信息发送)
* @ClassName: YwUserCustomerWebSocketSerivce 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年10月15日 下午2:14:29 
*
 */
public interface YwUserCustomerWebSocketSerivce {

	/**
	 * 给单个操作员发消息
	 * @param operator_no
	 * @param message
	 */
	void sendMessage(String user_id, String message);
	
	/**
	 * 给操作员群发消息
	 * @param operator_nos
	 * @param message
	 */
	void sendMessage(List<?> user_ids, String message);
}
