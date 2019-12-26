package com.yunwei.context.sys.service;

import java.util.Map;

/**
 * 退款模板消息服务接口
* @ClassName: YwRefundTemplateMessageSerivce 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2019年2月19日 下午2:39:26 
*
 */
public interface YwRefundTemplateMessageSerivce{

	/**
	 * 	退款成功通知
	 * @param appid
	 * @param touser
	 * @param form_id
	 * @param data
	 */
	public void sendRefundSuccessMessage(String appid,String touser,String form_id,Map<String,Map<String,Object>> data);
	
	/**
	 * 	退款成功通知
	 * @param appid
	 * @param touser
	 * @param form_id
	 * @param data
	 * @param emphasis_keywor
	 */
	public void sendRefundSuccessMessage(String appid,String touser,String form_id,Map<String,Map<String,Object>> data,String emphasis_keywor);
	
	/**
	 * 	退款成功通知
	 * @param appid
	 * @param touser
	 * @param form_id
	 * @param data
	 * @param emphasis_keyword
	 * @param page
	 */
	public void sendRefundSuccessMessage(String appid,String touser,String form_id,Map<String,Map<String,Object>> data,String emphasis_keyword,String page);
	
	/**
	 * 	退款申请通知
	 * @param appid
	 * @param touser
	 * @param form_id
	 * @param data
	 */
	public void sendRefundApplyMessage(String appid,String touser,String form_id,Map<String,Map<String,Object>> data);
	
	/**
	 * 	退款申请通知
	 * @param appid
	 * @param touser
	 * @param form_id
	 * @param data
	 * @param emphasis_keywor
	 */
	public void sendRefundApplyMessage(String appid,String touser,String form_id,Map<String,Map<String,Object>> data,String emphasis_keywor);
	
	/**
	 * 	退款申请通知
	 * @param appid
	 * @param touser
	 * @param form_id
	 * @param data
	 * @param emphasis_keyword
	 * @param page
	 */
	public void sendRefundApplyMessage(String appid,String touser,String form_id,Map<String,Map<String,Object>> data,String emphasis_keyword,String page);
	
	/**
	 * 退款拒绝通知
	 * @param appid
	 * @param touser
	 * @param form_id
	 * @param data
	 */
	public void sendRefundRefuseMessage(String appid,String touser,String form_id,Map<String,Map<String,Object>> data);
	
	/**
	 * 退款拒绝通知
	 * @param appid
	 * @param touser
	 * @param form_id
	 * @param data
	 * @param emphasis_keywor
	 */
	public void sendRefundRefuseMessage(String appid,String touser,String form_id,Map<String,Map<String,Object>> data,String emphasis_keywor);
	
	/**
	 * 退款拒绝通知
	 * @param appid
	 * @param touser
	 * @param form_id
	 * @param data
	 * @param emphasis_keyword
	 * @param page
	 */
	public void sendRefundRefuseMessage(String appid,String touser,String form_id,Map<String,Map<String,Object>> data,String emphasis_keyword,String page);
}
