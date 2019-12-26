package com.yunwei.product.common.model;

import java.math.BigDecimal;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.util.DateUtil;

/**
 * 会员模板消息发送信息
* @ClassName: YwMemberMessage
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2019年02月21日 下午14:38:56
*
 */
public class YwMemberMessage {

   private String id; // 编号
   private String openid; // 消息用户
   private String formid; // 消息编号
   private String prepay_id; // 微信预订单编号（可以发三条模板消息）
   private String message_count; // 消息剩余次数（默认1次）
   private String message_type; // 消息类型
   private String message_content; // 消息内容
   private String order_sn; // 订单编号
   private String branch_id; // 机构编号

	public String getId() {
	    return this.id;
	}
	
	public void setId(String id) {
	    this.id = id;
	}
    public String getOpenid() {
	    return this.openid;
	}
	
	public void setOpenid(String openid) {
	    this.openid = openid;
	}
    public String getFormid() {
	    return this.formid;
	}
	
	public void setFormid(String formid) {
	    this.formid = formid;
	}
    public String getPrepay_id() {
	    return this.prepay_id;
	}
	
	public void setPrepay_id(String prepay_id) {
	    this.prepay_id = prepay_id;
	}
    public String getMessage_count() {
	    return this.message_count;
	}
	
	public void setMessage_count(String message_count) {
	    this.message_count = message_count;
	}
    public String getMessage_type() {
	    return this.message_type;
	}
	
	public void setMessage_type(String message_type) {
	    this.message_type = message_type;
	}
    public String getMessage_content() {
	    return this.message_content;
	}
	
	public void setMessage_content(String message_content) {
	    this.message_content = message_content;
	}
    public String getOrder_sn() {
	    return this.order_sn;
	}
	
	public void setOrder_sn(String order_sn) {
	    this.order_sn = order_sn;
	}
    public String getBranch_id() {
	    return this.branch_id;
	}
	
	public void setBranch_id(String branch_id) {
	    this.branch_id = branch_id;
	}
	
}
