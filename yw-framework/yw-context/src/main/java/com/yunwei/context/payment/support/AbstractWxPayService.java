package com.yunwei.context.payment.support;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.yunwei.common.exception.BizException;
import com.yunwei.common.middleware.wx.SisapService;
import com.yunwei.context.payment.util.WXUtil;

/**
 * 支付业务基本抽象实现
* @ClassName: AbstractWxPayService 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年5月31日 下午3:07:35 
*
 */
public abstract class AbstractWxPayService{
	private static Logger logger = Logger.getLogger(AbstractWxPayService.class);
	
	@Autowired
	protected SisapService sisapService;
	
	/**
	 * 支付前准备
	 */
	@SuppressWarnings("unchecked")
	private <T> T payBefore(Map<String, Object> payParamMap){
		/*if(StringUtils.equals(getSignType().toUpperCase(), "MD5")){
			payParamMap.put("sign", createMd5Sign(payParamMap));
		} else {
			payParamMap.put("sign", createMd5Sign(payParamMap));// 默认MD5签名
		}*/
		
		payParamMap.put("sign", createMd5Sign(payParamMap));// 默认MD5签名
		// 创建请求参数
		return (T) createXmlRequestParam(payParamMap);
	}
	
	/**
	 * 创建签名
	 */
	private String createMd5Sign(Map<String, Object> payParamMap){
    	return WXUtil.createSign_ChooseWXPay("UTF-8", new TreeMap<Object, Object>(payParamMap), getSignKey());
    }
	
    /**
     * 创建支付xml请求参数
     */
	private String createXmlRequestParam(Map<String, Object> payParamMap){
		
		return WXUtil.getRequestXml(new TreeMap<Object, Object>(payParamMap));
	}
	
	public final Map<String, Object> execute(String request_url,Map<String, Object> payParamMap) {
		// 支付请求
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 创建微信支付预订单
			byte[] bs = executeBinary(request_url,payParamMap);
			
			// 解析支付响应后参数
			result = payAfter(new String(bs,"UTF-8"));
			// 判断支付是否执行成功
			if(StringUtils.equals(result.get("return_code").toString(), "FAIL") || StringUtils.equals(result.get("result_code").toString(), "FAIL")){
				throw new BizException(result.toString());
			}
		} catch (Exception e) {
			logger.info("【"+ getPayName() +"】解析响应参数失败："+ e.getMessage() +"");
			throw new BizException(e);
		} 
		return result;
	}
	
	public final byte[] executeBinary(String request_url,Map<String, Object> payParamMap) {
		String xmlParam = payBefore(payParamMap);
		return sisapService.executeBinary(request_url, xmlParam);
	}
	
	/**
	 * 支付后准备(解析响应的数据)
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> payAfter(String response_param){
		try {
			// 默认格式为xml
			return WXUtil.doXMLParse(response_param);
		} catch (Exception e) {
			logger.info("【"+ getPayName() +"】解析响应的数据失败："+ e.getMessage() +"");
			throw new BizException(e);
		} 
	}
	
	/**
	 * 设置参数格式
	 * @return
	 */
	protected abstract String getParameterType();
	
	/**
	 * 设置支付名称
	 * @return
	 */
	protected abstract String getPayName();
	
	/**
	 * 设置签名类型
	 */
	protected abstract String getSignType();
	
	/**
	 * 设置签名key(商户秘钥)
	 */
	protected abstract String getSignKey();
}
