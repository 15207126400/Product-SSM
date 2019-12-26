package com.yunwei.common.util;

import javax.xml.bind.Element;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentHelper;
import org.springframework.stereotype.Service;

import com.sun.xml.txw2.Document;
import com.yunwei.common.exception.BizException;
import com.yunwei.common.util.HttpRequestUtil;

/**
 * 红枫叶短信接口实现类
* @ClassName: RedMapleSmsServiceImpl 
* @Description: TODO(TODO) 
* @author 晏飞
* @date 2019年7月1日 下午3:29:39 
*
 */
@Service
public class RedMapleSmsUtil {
	
	public static final String SEND_PATH = "http://120.24.223.146:8888/sms.aspx";											//短信发送
	public static final String OVERAGE_PATH = "http://120.24.223.146:8888/sms.aspx?action=overage";							//余额及已发送量查询接口
	public static final String CHECKKEYWORD_PATH = "http://120.24.223.146:8888/sms.aspx?action=action=checkkeyword";		//非法关键词查询
	public static final String QUERY_PATH = "http://120.24.223.146:8888/statusApi.aspx?action=query";						//状态报告接口
	
	public static final String HOLIDAY_PARAM = "action=send&userid=8543&account=qhz&password=sms123456";					//祝福验证码通道账户信息通用参数
	public static final String MARKETING_PARAM = "action=send&userid=3454&account=qhzsms&password=123456";					//营销短信通道账户信息通用参数
	
	/**
	 * 短信发送
	*
	*@param type	短信通道类型(1祝福短信,实时发送,内容为提示信息  2营销短信,需审核非实时,内容为营销类型)
	*@param sb		参数
	*@return
	*String
	 */
	public static String send(int type , String mobile , String content , String sendTime) {
		StringBuffer sb = new StringBuffer();
		try {
			//短信通道类型判断
			if(type == 1){
				sb.append(HOLIDAY_PARAM);
			} else {
				sb.append(MARKETING_PARAM);
			}
			
			//接收短信手机号
			if(StringUtils.isNotBlank(mobile)){
				sb.append("&mobile="+mobile);
			} 
			
			//短信内容
			if(StringUtils.isNotBlank(content)){
				sb.append("&content="+content);
			}
			
			if(StringUtils.isNotBlank(sendTime)){
				sb.append("&sendTime="+sendTime);
				sb.append("&extno=");
			} else {
				sb.append("&sendTime=&extno=");
			}
			
			//返回对应的xml字符串
			String result = HttpRequestUtil.sendPost(SEND_PATH, sb.toString());
			//xml解析并返回指定标签对应的值,返回短信发送状态
			org.dom4j.Document document = DocumentHelper.parseText(result);
			org.dom4j.Element rootel = document.getRootElement();
			
			return rootel.elementText("message");
		} catch (Exception e) {
			throw new BizException("【短信发送】 : " + e);
		}
	}

	public static String overage() {
		// TODO Auto-generated method stub
		return null;
	}

	public static String checkkeyword(String content) {
		// TODO Auto-generated method stub
		return null;
	}

	public static String query() {
		// TODO Auto-generated method stub
		return null;
	}

}
