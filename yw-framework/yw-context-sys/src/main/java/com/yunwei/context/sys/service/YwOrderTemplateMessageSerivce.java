package com.yunwei.context.sys.service;

import java.util.Map;

/**
 * 订单模板消息服务接口
* @ClassName: YwOrderTemplateMessageSerivce 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2019年2月19日 下午2:38:18 
*
 */
public interface YwOrderTemplateMessageSerivce{

	/**
	 * 订单支付成功通知
	 * @param appid
	 * @param touser
	 * @param form_id
	 * @param data
	 */
	public void sendOrderPaySuccessMessage(String appid,String touser,String form_id,Map<String,Map<String,Object>> data);
	
	/**
	 * 订单支付成功通知
	 * @param appid
	 * @param touser
	 * @param form_id
	 * @param data
	 * @param emphasis_keywor
	 */
	public void sendOrderPaySuccessMessage(String appid,String touser,String form_id,Map<String,Map<String,Object>> data,String emphasis_keywor);
	
	/**
	 * 订单支付成功通知
	 * @param appid
	 * @param touser
	 * @param form_id
	 * @param data
	 * @param emphasis_keyword
	 * @param page
	 */
	public void sendOrderPaySuccessMessage(String appid,String touser,String form_id,Map<String,Map<String,Object>> data,String emphasis_keyword,String page);
	
	/**
	 * 订单发货提醒
	 * @param appid
	 * @param touser
	 * @param form_id
	 * @param data
	 */
	public void sendOrderDeliveryMessage(String appid,String touser,String form_id,Map<String,Map<String,Object>> data);
	
	/**
	 * 订单发货提醒
	 * @param appid
	 * @param touser
	 * @param form_id
	 * @param data
	 * @param emphasis_keywor
	 */
	public void sendOrderDeliveryMessage(String appid,String touser,String form_id,Map<String,Map<String,Object>> data,String emphasis_keywor);
	
	/**
	 * 订单发货提醒
	 * @param appid
	 * @param touser
	 * @param form_id
	 * @param data
	 * @param emphasis_keyword
	 * @param page
	 */
	public void sendOrderDeliveryMessage(String appid,String touser,String form_id,Map<String,Map<String,Object>> data,String emphasis_keyword,String page);
	
	
	/**
	 * 	订单取消通知
	 * @param appid
	 * @param touser
	 * @param form_id
	 * @param data
	 */
	public void sendOrderCancelMessage(String appid,String touser,String form_id,Map<String,Map<String,Object>> data);
	
	/**
	 * 	订单取消通知
	 * @param appid
	 * @param touser
	 * @param form_id
	 * @param data
	 */
	public void sendOrderCancelMessage(String appid,String touser,String form_id,Map<String,Map<String,Object>> data,String emphasis_keywor);
	
	/**
	 * 	订单取消通知
	 * @param appid
	 * @param touser
	 * @param form_id
	 * @param data
	 */
	public void sendOrderCancelMessage(String appid,String touser,String form_id,Map<String,Map<String,Object>> data,String emphasis_keyword,String page);
	
	/**
	 * 订单完成通知
	 * @param appid
	 * @param touser
	 * @param form_id
	 * @param data
	 */
	public void sendOrdeCompletionMessage(String appid,String touser,String form_id,Map<String,Map<String,Object>> data);
	
	/**
	 * 订单完成通知
	 * @param appid
	 * @param touser
	 * @param form_id
	 * @param data
	 * @param emphasis_keywor
	 */
	public void sendOrdeCompletionMessage(String appid,String touser,String form_id,Map<String,Map<String,Object>> data,String emphasis_keywor);
	
	/**
	 * 订单完成通知
	 * @param appid
	 * @param touser
	 * @param form_id
	 * @param data
	 * @param emphasis_keyword
	 * @param page
	 */
	public void sendOrdeCompletionMessage(String appid,String touser,String form_id,Map<String,Map<String,Object>> data,String emphasis_keyword,String page);
}
