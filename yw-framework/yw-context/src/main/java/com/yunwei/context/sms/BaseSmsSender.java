package com.yunwei.context.sms;

import java.util.Map;


/**
 * base基本短信发送接口
* @ClassName: BaseSmsSender 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年6月4日 下午2:23:10 
*
 */
public interface BaseSmsSender{

	/**
	 * 指定模板单发短信
	 * @param tpl_id(短信模板id)
	 * @param params(短信模板变量参数)
	 * @param tel(短信发送对象号码)
	 * @return
	 */
	public Map<String,Object> sendSingle(String tpl_id,String[] params,String tel);
	
	/**
	 * 指定模板单发短信
	 * @param tpl_id(短信模板id)
	 * @param params(短信模板变量参数)
	 * @param tel(短信发送对象号码)
	 * @param sign(短信签名，如果使用默认签名，该字段可缺省)["云维科技"]
	 * @return
	 */
	public Map<String,Object> sendSingle(String tpl_id,String[] params,String tel,String sign);
	
	/**
	 * 单发短信
	 * @param msg(短信消息，utf8 编码，需要匹配审核通过的模板内容)
	 * @param tel(短信发送对象号码)
	 * @param type(短信发送类型[短信类型，Enum{0: 普通短信, 1: 营销短信}（注意：要按需填值，不然会影响到业务的正常使用）])
	 * @return
	 */
	public Map<String,Object> sendSingle(String msg,String tel,String type);
	
	/**
	 * 指定模板群发短信
	 * @param tpl_id(短信模板id)
	 * @param params(短信模板变量参数)
	 * @param tels(短信群发送对象号码)
	 * @return
	 */
	public Map<String,Object> senderMulti(String tpl_id,String[] params,String[] tels);
	
	/**
	 * 指定模板群发短信
	 * @param tpl_id(短信模板id)
	 * @param params(短信模板变量参数)
	 * @param tels(短信群发送对象号码)
	 * @param sign(短信签名，如果使用默认签名，该字段可缺省)["云维科技"]
	 * @return
	 */
	public Map<String,Object> senderMulti(String tpl_id,String[] params,String[] tels,String sign);
	
	/**
	 * 单发短信
	 * @param msg(短信消息，utf8 编码，需要匹配审核通过的模板内容)
	 * @param tel(短信群发送对象号码)
	 * @param type(短信发送类型[短信类型，Enum{0: 普通短信, 1: 营销短信}（注意：要按需填值，不然会影响到业务的正常使用）])
	 * @return
	 */
	public Map<String,Object> senderMulti(String msg,String[] tels,String type);
	
	/**
	 * 拉取单个手机短信状态
	 * @param begin_time(拉取起始时间，unix 时间戳（时间：秒）)
	 * @param end_time(拉取的截止时间，unix 时间戳（时间：秒）)
	 * @param max(拉取最大条数，最多 100)
	 * @param mobile(手机号码)
	 * @param type(拉取类型，Enum{0: 短信下发状态, 1: 短信回复})
	 * @return
	 */
	public Map<String,Object> pullStatusSingle(String begin_time,String end_time,String max,String mobile,String type);
	
	
	/**
	 * 拉取短信状态
	 * @param max(拉取最大条数，最多 100)
	 * @param type(拉取类型，Enum{0: 短信下发状态, 1: 短信回复})
	 * @return
	 */
	public Map<String,Object> pullStatusMulti(String max,String type);
	
	/**
	 * 短信回复
	 * @param mobile(手机号码)
	 * @param sign(短信签名)
	 * @param text(用户回复的内容)
	 * @return
	 */
	public Map<String,Object> smsCallback(String mobile,String sign,String text);
}
