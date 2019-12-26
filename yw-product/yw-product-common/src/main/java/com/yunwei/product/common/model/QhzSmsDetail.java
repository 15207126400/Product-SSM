package com.yunwei.product.common.model;

import java.math.BigDecimal;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.util.DateUtil;

/**
 * 沙龙注册短信发送信息
* @ClassName: SmsDetail
* @Description: TODO(TODO) 
* @author yanf
* @date 2019年07月04日 上午11:38:45
*
 */
public class QhzSmsDetail {

   private String id; // 编号
   private String type; // 短信通道类型(1提醒短信 2营销短信)
   private String mobile; // 全部被叫号码
   private String content; // 发送内容
   private Date sendTime; // 定时发送时间
   private String message; // 发送返回状态
   private Date createTime; // 创建时间

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
    public String getMobile() {
	    return this.mobile;
	}
	
	public void setMobile(String mobile) {
	    this.mobile = mobile;
	}
    public String getContent() {
	    return this.content;
	}
	
	public void setContent(String content) {
	    this.content = content;
	}
    @JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
    public Date getSendTime() {
	    return this.sendTime;
	}
	
	public void setSendTime(Date sendTime) {
	    this.sendTime = sendTime;
	}
    public String getMessage() {
	    return this.message;
	}
	
	public void setMessage(String message) {
	    this.message = message;
	}

	/** 
	* @return createTime 
	*/
	@JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
	public Date getCreateTime() {
		return createTime;
	}

	/** 
	* @param createTime 要设置的 createTime 
	*/
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
