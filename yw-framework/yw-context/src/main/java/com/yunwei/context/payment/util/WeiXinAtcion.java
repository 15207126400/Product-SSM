package com.yunwei.context.payment.util;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class WeiXinAtcion {
	public static final WeiXinAtcion me = new WeiXinAtcion();
	/**
	 * 退款函数，该方法可以对曾经部分�?款的订单进行再次�?�?
	 * @param out_trade_no 商户订单号
	 * @param total_fee1 退款对应的订单的金额（以元为单位）
	 * @param refund_fee1 计划退款的金额（以“元”为单位）
	 * @throws Exception 
	 */
	public static Map<String, String> wechatRefund(String out_trade_no,String out_refund_no,double all_total_fee,double refund_fee) throws Exception{
		DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
		SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
		packageParams.put("appid",WxModelUtil.APPID);
		packageParams.put("mch_id",WxModelUtil.MCH_ID);
		packageParams.put("op_user_id",WxModelUtil.MCH_ID);
		packageParams.put("nonce_str", WXUtil.generate());
		packageParams.put("out_trade_no", out_trade_no);
		packageParams.put("out_refund_no", out_refund_no);
		packageParams.put("total_fee",decimalFormat.format(all_total_fee*100));
		packageParams.put("refund_fee", decimalFormat.format(refund_fee*100));
		String sign = WXUtil.createSign_ChooseWXPay("UTF-8", packageParams, WxModelUtil.KEY);
		packageParams.put("sign", sign);
		String XML = WXUtil.getRequestXml(packageParams);
		return WXUtil.doRefund("https://api.mch.weixin.qq.com/secapi/pay/refund", XML,WxModelUtil.MCH_ID);
	
	}
		

		/**
		 * 生成微信订单
		 * @param openid
		 * @param period_number
		 * @param merchant_id
		 * @param num
		 * @return
		 */
		public SortedMap<Object, Object> weixinPlay(String mch_id,String appid,String key,String openid,
				double total_fee,String out_trade_no,String notify_url,String body,String ip) throws UnsupportedEncodingException, DocumentException {
			DecimalFormat decimalFormat = new DecimalFormat("###################.###########");  
			SortedMap<Object, Object> paymentPo = new TreeMap<Object, Object>();
			paymentPo.put("mch_id",  mch_id);
			paymentPo.put("appid",appid);
			paymentPo.put("nonce_str", WXUtil.generate());
			paymentPo.put("trade_type", "JSAPI");
			paymentPo.put("openid", openid);
			paymentPo.put("total_fee",decimalFormat.format(total_fee*100));	//微信以分为单�?,此处设置*100可实现按元计�? String.valueOf(total_fee*100)
			paymentPo.put("out_trade_no",out_trade_no);
			paymentPo.put("notify_url", notify_url);
			paymentPo.put("body",body);
			paymentPo.put("spbill_create_ip", ip);
			
			String sign = WXUtil.createSign_ChooseWXPay("UTF-8", paymentPo, key);
			System.out.println("paymentPo�?"+paymentPo);
			System.out.println("sign�?"+sign);
			
			paymentPo.put("sign", sign);
			String param = WXUtil.getRequestXml(paymentPo);
			System.out.println("param:"+param);
			String request = WXUtil.httpRequest("https://api.mch.weixin.qq.com/pay/unifiedorder", "POST", param);
			Map<String, String> map = new HashMap<String, String>();         // 将解析结果存储在HashMap�?
			InputStream in = new ByteArrayInputStream(request.getBytes());
			SAXReader reader = new SAXReader();                              // 读取输入�?
			Document document = reader.read(in);
		
			Element root = document.getRootElement();                        // 得到xml根元�?
			@SuppressWarnings("unchecked")                                   // 得到根元素的�?有子节点
			List<Element> elementList = root.elements();
			for (Element element : elementList) {
				map.put(element.getName(), element.getText());
			}
			System.out.println("第一次签名结�? : " + map.get("return_msg"));
			SortedMap<Object, Object> result = new TreeMap<Object, Object>();
				
			if (map.get("return_code").equals("SUCCESS")) {                  // 业务结果
				String nonceStr  =WXUtil.generate();
				Long   timeStamp = System.currentTimeMillis() / 1000;
				SortedMap<Object, Object> params = new TreeMap<Object, Object>();
				
				params.put("appId", appid);
				params.put("nonceStr", nonceStr);
				params.put("package", "prepay_id=" + map.get("prepay_id")); 
				params.put("signType", "MD5"); 
				params.put("timeStamp", timeStamp); 
				
				String paySign = WXUtil.createSign_ChooseWXPay("UTF-8", params, key);
				result.put("paySign", paySign);
				result.put("timeStamp", timeStamp + "");
				result.put("nonceStr", nonceStr);
				result.put("signType", "MD5");
				
				result.put("package", "prepay_id=" + map.get("prepay_id"));
				result.put("return_code", "SUCCESS");
			}else {
				result.put("return_code", "Fail");
				result.put("return_msg", map.get("return_msg"));
			}
			return result;
		}
		
		/**
		 * 支付成功的回调函数
		 * @param request
		 * @param response
		 * @throws Exception
		 */
	    public void weixinpay_notify(HttpServletRequest request,HttpServletResponse response) throws Exception{  
			InputStream inputStream ;  
	        StringBuffer sb = new StringBuffer();  
	        inputStream = request.getInputStream();  
	        String s ;  
	        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));  
	        while ((s = in.readLine()) != null){  
	            sb.append(s);  
	        }  
	        in.close();  
	        inputStream.close();  
	        Map<String, String> m = new HashMap<String, String>();  
	        m = WXUtil.doXMLParse(sb.toString());  
	        SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();        
	        Iterator it = m.keySet().iterator();  
	        while (it.hasNext()) {  
	            String parameter = (String) it.next();  
	            String parameterValue = m.get(parameter);  
	            String v = "";  
	            if(null != parameterValue) {  
	                v = parameterValue.trim();  
	            }  
	            packageParams.put(parameter, v);  
	        }  
	        String key = ""; //秘钥 
	        if(WXUtil.isTenpaySign("UTF-8", packageParams,key)) {  
	            String resXml = "";  
	            if("SUCCESS".equals((String)packageParams.get("result_code"))){  
	               
	            	//得到返回的参�?
	            	String openid = (String)packageParams.get("openid");  
	                String transaction_id = (String)packageParams.get("transaction_id");  
	                String out_trade_no = (String)packageParams.get("out_trade_no");  
	                String total_fee = (String)packageParams.get("total_fee");  
	                Float fee= Float.parseFloat(total_fee)/100;
	                //这里可以写你�?要的业务
	            
	                resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"  
	                        + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";  
	                BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());  
	                out.write(resXml.getBytes());  
	                out.flush();  
	                out.close();
	            } else {  
	              System.out.println("回调失败");
	            }  
	        } else{  
	        	System.out.println("回调失败");
	        } 
	    }
	    
}
