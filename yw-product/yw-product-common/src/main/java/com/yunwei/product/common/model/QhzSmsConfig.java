package com.yunwei.product.common.model;

import java.math.BigDecimal;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.util.DateUtil;

/**
 * 沙龙注册短信系统配置
* @ClassName: SmsConfig
* @Description: TODO(TODO) 
* @author yanf
* @date 2019年07月04日 上午11:16:46
*
 */
public class QhzSmsConfig {

   private String id; // 
   private String type; // 短信通道类型(1提醒短信 2营销短信)
   private String url; // 请求路径
   private String userid; // 企业id
   private String account; // 账号
   private String password; // 密码
   private String action; // 发送任务命令
   private String sign; // 短信前缀公司签名(必须)

	public String getId() {
	    return this.id;
	}
	
	public void setId(String id) {
	    this.id = id;
	}
    public String getType() {
	    return this.type;
	}
	
	public void setType(String type) {
	    this.type = type;
	}
    public String getUrl() {
	    return this.url;
	}
	
	public void setUrl(String url) {
	    this.url = url;
	}
    public String getUserid() {
	    return this.userid;
	}
	
	public void setUserid(String userid) {
	    this.userid = userid;
	}
    public String getAccount() {
	    return this.account;
	}
	
	public void setAccount(String account) {
	    this.account = account;
	}
    public String getPassword() {
	    return this.password;
	}
	
	public void setPassword(String password) {
	    this.password = password;
	}
    public String getAction() {
	    return this.action;
	}
	
	public void setAction(String action) {
	    this.action = action;
	}

	/** 
	* @return sign 
	*/
	public String getSign() {
		return sign;
	}

	/** 
	* @param sign 要设置的 sign 
	*/
	public void setSign(String sign) {
		this.sign = sign;
	}
	
}
