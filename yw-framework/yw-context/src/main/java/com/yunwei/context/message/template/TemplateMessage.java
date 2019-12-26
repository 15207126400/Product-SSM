package com.yunwei.context.message.template;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * 模板消息服务接口
* @ClassName: TemplateMessage 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2019年2月18日 下午4:21:41 
*
 */
public interface TemplateMessage {

	/**
	 * 模板消息发送
	 * @param access_token 接口调用凭证
	 * @param touser 接收者（用户）的 openid
	 * @param template_id 所需下发的模板消息的id
	 * @param form_id 表单提交场景下，为 submit 事件带上的 formId；支付场景下，为本次支付的 prepay_id
	 * @param data 模板内容，不填则下发空模板"data": {
									    "keyword1": {
									      "value": "339208499"
									    },
									    "keyword2": {
									      "value": "2015年01月05日 12:30"
									    },
									    "keyword3": {
									      "value": "腾讯微信总部"
									    },
									    "keyword4": {
									      "value": "广州市海珠区新港中路397号"
									    }
									  }
	 */
	public void send(String access_token,String touser,String template_id,String form_id,Map<String, Map<String, Object>> data);
	
	/**
	 * 模板消息发送
	 * @param access_token 接口调用凭证
	 * @param touser 接收者（用户）的 openid
	 * @param template_id 所需下发的模板消息的id
	 * @param form_id 表单提交场景下，为 submit 事件带上的 formId；支付场景下，为本次支付的 prepay_id
	 * @param data 模板内容，不填则下发空模板"data": {
									    "keyword1": {
									      "value": "339208499"
									    },
									    "keyword2": {
									      "value": "2015年01月05日 12:30"
									    },
									    "keyword3": {
									      "value": "腾讯微信总部"
									    },
									    "keyword4": {
									      "value": "广州市海珠区新港中路397号"
									    }
									  }
	 */
	public void send(String access_token,String touser,String template_id,String form_id,JSONObject data);
	
	/**
	 * 模板消息发送
	 * @param access_token 接口调用凭证
	 * @param touser 接收者（用户）的 openid
	 * @param template_id 所需下发的模板消息的id
	 * @param form_id 表单提交场景下，为 submit 事件带上的 formId；支付场景下，为本次支付的 prepay_id
	 * @param data 模板内容，不填则下发空模板"data": {
									    "keyword1": {
									      "value": "339208499"
									    },
									    "keyword2": {
									      "value": "2015年01月05日 12:30"
									    },
									    "keyword3": {
									      "value": "腾讯微信总部"
									    },
									    "keyword4": {
									      "value": "广州市海珠区新港中路397号"
									    }
									  }
	 * @param 模板需要放大的关键词，不填则默认无放大 emphasis_keyword": "keyword1.DATA"								  
	 */
	public void send(String access_token,String touser,String template_id,String form_id,Map<String, Map<String, Object>> data,String emphasis_keyword);
	
	/**
	 * 模板消息发送
	 * @param access_token 接口调用凭证
	 * @param touser 接收者（用户）的 openid
	 * @param template_id 所需下发的模板消息的id
	 * @param form_id 表单提交场景下，为 submit 事件带上的 formId；支付场景下，为本次支付的 prepay_id
	 * @param data 模板内容，不填则下发空模板"data": {
									    "keyword1": {
									      "value": "339208499"
									    },
									    "keyword2": {
									      "value": "2015年01月05日 12:30"
									    },
									    "keyword3": {
									      "value": "腾讯微信总部"
									    },
									    "keyword4": {
									      "value": "广州市海珠区新港中路397号"
									    }
									  }
	 * @param 模板需要放大的关键词，不填则默认无放大 emphasis_keyword": "keyword1.DATA"								  
	 */
	public void send(String access_token,String touser,String template_id,String form_id,JSONObject data,String emphasis_keyword);
		
	/**
	 * 模板消息发送
	 * @param access_token 接口调用凭证
	 * @param touser 接收者（用户）的 openid
	 * @param template_id 所需下发的模板消息的id
	 * @param form_id 表单提交场景下，为 submit 事件带上的 formId；支付场景下，为本次支付的 prepay_id
	 * @param data 模板内容，不填则下发空模板"data": {
									    "keyword1": {
									      "value": "339208499"
									    },
									    "keyword2": {
									      "value": "2015年01月05日 12:30"
									    },
									    "keyword3": {
									      "value": "腾讯微信总部"
									    },
									    "keyword4": {
									      "value": "广州市海珠区新港中路397号"
									    }
									  }
	 * @param 模板需要放大的关键词，不填则默认无放大 emphasis_keyword: "keyword1.DATA"		
	 * @param 点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例index?foo=bar）。该字段不填则模板无跳转。					  
	 */
	public void send(String access_token,String touser,String template_id,String form_id,Map<String, Map<String, Object>> data,String emphasis_keyword,String page);	
	
	/**
	 * 模板消息发送
	 * @param access_token 接口调用凭证
	 * @param touser 接收者（用户）的 openid
	 * @param template_id 所需下发的模板消息的id
	 * @param form_id 表单提交场景下，为 submit 事件带上的 formId；支付场景下，为本次支付的 prepay_id
	 * @param data 模板内容，不填则下发空模板"data": {
									    "keyword1": {
									      "value": "339208499"
									    },
									    "keyword2": {
									      "value": "2015年01月05日 12:30"
									    },
									    "keyword3": {
									      "value": "腾讯微信总部"
									    },
									    "keyword4": {
									      "value": "广州市海珠区新港中路397号"
									    }
									  }
	 * @param 模板需要放大的关键词，不填则默认无放大 emphasis_keyword: "keyword1.DATA"		
	 * @param 点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例index?foo=bar）。该字段不填则模板无跳转。					  
	 */
	public void send(String access_token,String touser,String template_id,String form_id,JSONObject data,String emphasis_keyword,String page);
	
	/**
	 * 支付场景发送模板消息
	*
	*@param access_token	接口调用凭证
	*@param touser	接收者（用户）的 openid
	*@param template_id	所需下发的模板消息的id
	*@param prepay_id	支付场景下，为本次支付的 prepay_id
	*@param data	模板内容，不填则下发空模板"data": {
									    "keyword1": {
									      "value": "339208499"
									    },
									    "keyword2": {
									      "value": "2015年01月05日 12:30"
									    },
									    "keyword3": {
									      "value": "腾讯微信总部"
									    },
									    "keyword4": {
									      "value": "广州市海珠区新港中路397号"
									    }
									  }
	*@param emphasis_keyword	模板需要放大的关键词，不填则默认无放大 emphasis_keyword: "keyword1.DATA"
	*void
	 */
	public void orderSend(String access_token,String touser,String template_id,String prepay_id,JSONObject data,String emphasis_keyword);
}
