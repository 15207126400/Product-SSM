package com.yunwei.context.message.template.manage;

import java.util.List;
import java.util.Map;

/**
 * 模板消息管理
* @ClassName: TemplateMessageManage 
* @Description: TODO(TODO) 
* @author 晏飞
* @date 2019年2月18日 下午4:32:06 
*
 */
public interface TemplateMessageManage {
	/**
	 * 获取小程序模板库标题列表
	*
	*@param access_token	接口调用凭证
	*@param offset			用于分页,表示从offset开始,从 0 开始计数
	*@param count			用于分页,表示拉取count条记录,最大为 20
	*@return				
	*Map<String,Object>		JSON数据
	 */
	public Map<String, Object> getTemplateLibraryList(String access_token , int offset , int count);
	
	/**
	 * 获取模板库某个模板标题下关键词库
	*
	*@param access_token	接口调用凭证
	*@param id				模板标题id,可通过接口获取,也可登录小程序后台查看获取
	*@return				
	*Map<String,Object>		JSON数据
	 */
	public Map<String, Object> getTemplateLibraryById(String access_token , String id);
	
	/**
	 * 组合模板并添加至帐号下的个人模板库
	*
	*@param access_token	接口调用凭证
	*@param id				模板标题id,可通过接口获取,也可登录小程序后台查看获取
	*@param keyword_id_list	开发者自行组合好的模板关键词列表,关键词顺序可以自由搭配（例如[3,5,4]或[4,5,3]）,最多支持10个关键词组合
	*@return				
	*String					模板编号 template_id
	 */
	public String addTemplate(String access_token , String id , String[] keyword_id_list);
	
	/**
	 * 获取帐号下已存在的模板列表
	*
	*@param access_token	接口调用凭证
	*@param offset			用于分页,表示从offset开始,从 0 开始计数
	*@param count			用于分页,表示拉取count条记录,最大为 20,最后一页的list长度可能小于请求的count
	*@return
	*Map<String,Object>		JSON数据
	 */
	public List<Map<String,Object>> getTemplateList(String access_token , int offset , int count);
	
	/**
	 * 删除帐号下的某个模板
	*
	*@param access_token	接口调用凭证
	*@param template_id		要删除的模板id
	*void
	 */
	public void deleteTemplate(String access_token , String template_id);
}
