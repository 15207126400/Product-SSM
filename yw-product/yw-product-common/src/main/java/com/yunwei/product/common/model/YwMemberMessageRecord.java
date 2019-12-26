package com.yunwei.product.common.model;

import java.math.BigDecimal;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.util.DateUtil;

/**
 * 会员模板消息发送记录信息
* @ClassName: YwMemberMessageRecord
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2019年02月21日 下午14:52:29
*
 */
public class YwMemberMessageRecord {

   private String id; // 编号
   private String openid; // 消息用户
   private String formid; // 消息编号
   private String prepay_id; // 微信预订单编号（可以发三条模板消息）
   private String message_type; // 消息类型
   private String message_content; // 消息内容
   private String order_sn; // 订单编号
   private String branch_id; // 机构编号
   private Date createtime; // 创建时间
   private Date updatetime; // 更新时间
   private String message_status; // 消费发送状态(0：未发送，1：发送成功，2：发送失败)
   private String message_errorinfo; // 消息发送错误信息

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
    @JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
    public Date getCreatetime() {
	    return this.createtime;
	}
	
	public void setCreatetime(Date createtime) {
	    this.createtime = createtime;
	}
    @JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
    public Date getUpdatetime() {
	    return this.updatetime;
	}
	
	public void setUpdatetime(Date updatetime) {
	    this.updatetime = updatetime;
	}
    public String getMessage_status() {
	    return this.message_status;
	}
	
	public void setMessage_status(String message_status) {
	    this.message_status = message_status;
	}
    public String getMessage_errorinfo() {
	    return this.message_errorinfo;
	}
	
	public void setMessage_errorinfo(String message_errorinfo) {
	    this.message_errorinfo = message_errorinfo;
	}
	
}
