package com.yunwei.context.message.template.manage.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.entity.ContentType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.yunwei.common.exception.BizException;
import com.yunwei.common.middleware.wx.SisapService;
import com.yunwei.common.util.FastJsonUtil;
import com.yunwei.common.util.HttpClientUtil;
import com.yunwei.common.util.MapUtil;
import com.yunwei.context.message.template.manage.TemplateMessageManage;

/**
 * 微信消息模板管理
* @ClassName: WxTemplateMessageManage 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2019年2月19日 上午9:56:50 
*
 */
@Service
public class WxTemplateMessageManage implements TemplateMessageManage{

	private static Logger logger = Logger.getLogger(WxTemplateMessageManage.class);
	private static final String getTemplateLibraryList_url = "https://api.weixin.qq.com/cgi-bin/wxopen/template/library/list";
	private static final String getTemplateLibraryById_url = "https://api.weixin.qq.com/cgi-bin/wxopen/template/library/get";
	private static final String addTemplate_url = "https://api.weixin.qq.com/cgi-bin/wxopen/template/add";
	private static final String getTemplateList_url = "https://api.weixin.qq.com/cgi-bin/wxopen/template/list";
	private static final String deleteTemplate_url = "https://api.weixin.qq.com/cgi-bin/wxopen/template/del";

	@Autowired
	private SisapService sisapService;
	
	@Override
	public Map<String, Object> getTemplateLibraryList(String access_token,
			int offset, int count) {
		// 拼接请求url
		StringBuffer buffer = new StringBuffer();
					buffer.append(getTemplateLibraryList_url);
					buffer.append("?access_token=");
					buffer.append(access_token);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("offset", count);
		jsonObject.put("count", count);
		return sisapService.execute(buffer.toString(), jsonObject.toJSONString());
	}

	@Override
	public Map<String, Object> getTemplateLibraryById(String access_token,
			String id) {
		// 拼接请求url
		StringBuffer buffer = new StringBuffer();
					buffer.append(getTemplateLibraryById_url);
					buffer.append("?access_token=");
					buffer.append(access_token);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		return sisapService.execute(buffer.toString(), jsonObject.toJSONString());
	}

	@Override
	public String addTemplate(String access_token, String id,
			String[] keyword_id_list) {
		// 拼接请求url
		StringBuffer buffer = new StringBuffer();
					buffer.append(addTemplate_url);
					buffer.append("?access_token=");
					buffer.append(access_token);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		jsonObject.put("keyword_id_list", keyword_id_list);
		return sisapService.execute(buffer.toString(), jsonObject.toJSONString()).get("template_id").toString();
	}

	@Override
	public List<Map<String, Object>> getTemplateList(String access_token,
			int offset, int count) {
		// 拼接请求url
		StringBuffer buffer = new StringBuffer();
					buffer.append(getTemplateList_url);
					buffer.append("?access_token=");
					buffer.append(access_token);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("offset", offset);
		jsonObject.put("count", count);
		String jsonString = sisapService.execute(buffer.toString(), jsonObject.toJSONString()).get("list").toString();
		return FastJsonUtil.parseObject(JSONObject.parseArray(jsonString));
	}

	@Override
	public void deleteTemplate(String access_token, String template_id) {
		// 拼接请求url
		StringBuffer buffer = new StringBuffer();
					buffer.append(deleteTemplate_url);
					buffer.append("?access_token=");
					buffer.append(access_token);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("template_id", template_id);
		sisapService.execute(buffer.toString(), jsonObject.toJSONString());
	}
}
