package com.yunwei.context.sms;

import java.util.Map;

/**
 * 短信模板base基本接口
* @ClassName: BaseSmsTemplate 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年6月4日 下午3:41:47 
*
 */
public interface BaseSmsTemplate {

	/**
	 * 添加短信（或语音）模板
	 * @param title(模板名称)
	 * @param text(模板内容)
	 * @param type(短信类型，Enum{0：普通短信, 1：营销短信})
	 * @return
	 */
	public Map<String,Object> addTemplate(String title,String text,String type);
	
	/**
	 * 添加短信（或语音）模板
	 * @param title(模板名称)
	 * @param text(模板内容)
	 * @param type(短信类型，Enum{0：普通短信, 1：营销短信})
	 * @param remark(模板备注，比如申请原因，使用场景等)
	 * @return
	 */
	public Map<String,Object> addTemplate(String title,String text,String type,String remark);
	
	
	/**
	 * 修改短信（或语音）模板
	 * @param tpl_id(待修改的模板的模板 id)
	 * @param title(新的模板名称)
	 * @param text(新的模板内容)
	 * @param type(短信类型，Enum{0：普通短信, 1：营销短信})
	 * @return
	 */
	public Map<String,Object> updateTemplate(String tpl_id,String title,String text,String type);
	
	/**
	 * 修改短信（或语音）模板
	 * @param tpl_id(待修改的模板的模板 id)
	 * @param title(新的模板名称)
	 * @param text(新的模板内容)
	 * @param type(短信类型，Enum{0：普通短信, 1：营销短信})
	 * @param remark(新的模板备注，比如申请原因，使用场景等)
	 * @return
	 */
	public Map<String,Object> updateTemplate(String tpl_id,String title,String text,String type,String remark);
	
	/**
	 * 删除短信模板
	 * @param tpl_id(待删除的模板 id 数组)
	 * @return
	 */
	public Map<String,Object> deleteTemplate(Integer[] tpl_id);
	
	/**
	 * 短信模板状态查询
	 * @param tpl_id(待删除的模板 id 数组)
	 * @return
	 */
	public Map<String,Object> getTemplate(Integer[] tpl_id);
	
	/**
	 * 短信模板状态查询(针对分页查询)
	 * @param max(一次拉取的条数，最多 50)
	 * @param offset(拉取的偏移量，初始为 0，如果要多次拉取，需赋值为上一次的 offset 与 max 字段的和)
	 * @return
	 */
	public Map<String,Object> getTemplate(String max,String offset);
}
