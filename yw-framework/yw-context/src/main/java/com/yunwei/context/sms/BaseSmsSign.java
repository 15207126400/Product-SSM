package com.yunwei.context.sms;

import java.util.Map;

/**
 * 短信签名base接口
* @ClassName: BaseSmsSign 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年6月4日 下午3:56:26 
*
 */
public interface BaseSmsSign {
	
	/**
	 * 添加短信签名
	 * @param text(签名内容，不带【】，例如：【云维科技】这个签名，这里填"云维科技")
	 * @return
	 */
	public Map<String,Object> addSign(String text);

	/**
	 * 添加短信签名
	 * @param text(签名内容，不带【】，例如：【云维科技】这个签名，这里填"云维科技")
	 * @param remark(签名备注，比如申请原因，使用场景等)
	 * @return
	 */
	public Map<String,Object> addSign(String text,String remark);
	
	/**
	 * 修改短信签名
	 * @param sign_id(待修改的签名对应的签名 id)
	 * @param text(新的签名内容，不带【】，例如：改为【云维科技】这个签名，这里填"云维科技")
	 * @return
	 */
	public Map<String,Object> updateSign(String sign_id,String text);
	
	/**
	 * 修改短信签名
	 * @param sign_id(待修改的签名对应的签名 id)
	 * @param text(新的签名内容，不带【】，例如：改为【云维科技】这个签名，这里填"云维科技")
	 * @param remark(签名备注，比如申请原因，使用场景等)
	 * @return
	 */
	public Map<String,Object> updateSign(String sign_id,String text,String remark);
	
	/**
	 * 删除短信签名
	 * @param sign_id[签名 id，也可以通过值指定一个 "sign_id"：123]
	 * @return
	 */
	public Map<String,Object> deleteSign(Integer[] sign_id);
	
	/**
	 * 短信签名状态查询
	 * @param sign_id[签名 id，也可以通过值指定一个 "sign_id"：123]
	 * @return
	 */
	public Map<String,Object> getSign(Integer[] sign_id);
}
