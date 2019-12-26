package com.yunwei.context.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwei.common.exception.BizException;
import com.yunwei.context.message.template.TemplateMessage;
import com.yunwei.context.message.template.manage.TemplateMessageManage;
import com.yunwei.context.sys.service.YwAccessTokenService;
import com.yunwei.context.sys.service.YwTemplateMessageService;

/**
 * 云维模板消息服务
* @ClassName: YwTemplateMessageServiceImpl 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2019年2月19日 下午2:57:00 
*
 */
@Service
public class YwTemplateMessageServiceImpl implements YwTemplateMessageService{

	private static Logger logger = Logger.getLogger(YwTemplateMessageServiceImpl.class);
	
	@Autowired
	private YwAccessTokenService ywAccessTokenService;
	@Autowired
	private TemplateMessageManage templateMessageManage;
	@Autowired
	private TemplateMessage templateMessage;
	
	@Override
	public void sendRefundSuccessMessage(String appid, String touser,
			String form_id, Map<String, Map<String, Object>> data) {
		
		this.sendRefundSuccessMessage(appid, touser, form_id, data, "");
	}
	@Override
	public void sendRefundSuccessMessage(String appid, String touser,
			String form_id, Map<String, Map<String, Object>> data,
			String emphasis_keyword) {
		this.sendRefundSuccessMessage(appid, touser, form_id, data, emphasis_keyword, "");
	}
	@Override
	public void sendRefundSuccessMessage(String appid, String touser,
			String form_id, Map<String, Map<String, Object>> data,
			String emphasis_keyword, String page) {
		String access_token = ywAccessTokenService.getAccessTokenByAppId(appid);
		String template_id = templateFilter(access_token,"退款成功通知");
		
		templateMessage.send(access_token, touser, template_id, form_id, data, emphasis_keyword, page);
	}
	@Override
	public void sendRefundApplyMessage(String appid, String touser,
			String form_id, Map<String, Map<String, Object>> data) {
		this.sendRefundRefuseMessage(appid, touser, form_id, data, "");
	}
	@Override
	public void sendRefundApplyMessage(String appid, String touser,
			String form_id, Map<String, Map<String, Object>> data,
			String emphasis_keyword) {
		this.sendRefundRefuseMessage(appid, touser, form_id, data, emphasis_keyword, "");
	}
	@Override
	public void sendRefundApplyMessage(String appid, String touser,
			String form_id, Map<String, Map<String, Object>> data,
			String emphasis_keyword, String page) {
		String access_token = ywAccessTokenService.getAccessTokenByAppId(appid);
		String template_id = templateFilter(access_token,"退款申请通知");
		
		templateMessage.send(access_token, touser, template_id, form_id, data, emphasis_keyword, page);
	}
	@Override
	public void sendRefundRefuseMessage(String appid, String touser,
			String form_id, Map<String, Map<String, Object>> data) {
		this.sendRefundRefuseMessage(appid, touser, form_id, data, "");
	}
	@Override
	public void sendRefundRefuseMessage(String appid, String touser,
			String form_id, Map<String, Map<String, Object>> data,
			String emphasis_keyword) {
		this.sendRefundRefuseMessage(appid, touser, form_id, data, "","");
	}
	@Override
	public void sendRefundRefuseMessage(String appid, String touser,
			String form_id, Map<String, Map<String, Object>> data,
			String emphasis_keyword, String page) {
		String access_token = ywAccessTokenService.getAccessTokenByAppId(appid);
		String template_id = templateFilter(access_token,"退款拒绝通知");
		
		templateMessage.send(access_token, touser, template_id, form_id, data, emphasis_keyword, page);
	}
	@Override
	public void sendOrderPaySuccessMessage(String appid, String touser,
			String form_id, Map<String, Map<String, Object>> data) {
		this.sendOrderPaySuccessMessage(appid, touser, form_id, data, "");
	}
	@Override
	public void sendOrderPaySuccessMessage(String appid, String touser,
			String form_id, Map<String, Map<String, Object>> data,
			String emphasis_keywor) {
		this.sendOrderPaySuccessMessage(appid, touser, form_id, data, "","");
	}
	@Override
	public void sendOrderPaySuccessMessage(String appid, String touser,
			String form_id, Map<String, Map<String, Object>> data,
			String emphasis_keyword, String page) {
		String access_token = ywAccessTokenService.getAccessTokenByAppId(appid);
		String template_id = templateFilter(access_token,"订单支付成功通知");
		
		templateMessage.send(access_token, touser, template_id, form_id, data, emphasis_keyword, page);
	}
	@Override
	public void sendOrderDeliveryMessage(String appid, String touser,
			String form_id, Map<String, Map<String, Object>> data) {
		this.sendOrderDeliveryMessage(appid, touser, form_id, data, "");
	}
	@Override
	public void sendOrderDeliveryMessage(String appid, String touser,
			String form_id, Map<String, Map<String, Object>> data,
			String emphasis_keywor) {
		this.sendOrderDeliveryMessage(appid, touser, form_id, data, "","");
	}
	@Override
	public void sendOrderDeliveryMessage(String appid, String touser,
			String form_id, Map<String, Map<String, Object>> data,
			String emphasis_keyword, String page) {
		String access_token = ywAccessTokenService.getAccessTokenByAppId(appid);
		String template_id = templateFilter(access_token,"订单发货提醒");
		
		templateMessage.send(access_token, touser, template_id, form_id, data, emphasis_keyword, page);
	}
	@Override
	public void sendOrderCancelMessage(String appid, String touser,
			String form_id, Map<String, Map<String, Object>> data) {
		this.sendOrderCancelMessage(appid, touser, form_id, data, "");
	}
	@Override
	public void sendOrderCancelMessage(String appid, String touser,
			String form_id, Map<String, Map<String, Object>> data,
			String emphasis_keywor) {
		this.sendOrderCancelMessage(appid, touser, form_id, data, "","");
	}
	@Override
	public void sendOrderCancelMessage(String appid, String touser,
			String form_id, Map<String, Map<String, Object>> data,
			String emphasis_keyword, String page) {
		String access_token = ywAccessTokenService.getAccessTokenByAppId(appid);
		String template_id = templateFilter(access_token,"订单取消通知");
		
		templateMessage.send(access_token, touser, template_id, form_id, data, emphasis_keyword, page);
	}
	@Override
	public void sendOrdeCompletionMessage(String appid, String touser,
			String form_id, Map<String, Map<String, Object>> data) {
		this.sendOrdeCompletionMessage(appid, touser, form_id, data, "");
	}
	@Override
	public void sendOrdeCompletionMessage(String appid, String touser,
			String form_id, Map<String, Map<String, Object>> data,
			String emphasis_keywor) {
		this.sendOrdeCompletionMessage(appid, touser, form_id, data, "","");
	}
	@Override
	public void sendOrdeCompletionMessage(String appid, String touser,
			String form_id, Map<String, Map<String, Object>> data,
			String emphasis_keyword, String page) {
		String access_token = ywAccessTokenService.getAccessTokenByAppId(appid);
		String template_id = templateFilter(access_token,"订单完成通知");
		
		templateMessage.send(access_token, touser, template_id, form_id, data, emphasis_keyword, page);
	}
	
	
	/**
	 * 模板过滤
	 * @param access_token
	 * @param title
	 * @return
	 */
	private String templateFilter(String access_token,String title){
        List<Map<String, Object>> maps = templateMessageManage.getTemplateList(access_token, 0, 20);
		if(CollectionUtils.isEmpty(maps)){
			logger.error("模板消息未配置,请在微信小程序内配置");
			throw new BizException("模板消息未配置");
		}
		String template_id = "";
		for(Map<String, Object> map : maps){
			if(StringUtils.equals(title, map.get("title").toString())){
				template_id = map.get("template_id").toString();
			}
		}
		return template_id;
	}
	
}

