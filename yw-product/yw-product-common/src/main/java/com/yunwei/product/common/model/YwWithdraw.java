package com.yunwei.product.common.model;

import java.math.BigDecimal;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.util.DateUtil;

/**
 * 商家提现申请表
* @ClassName: YwWithdraw
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2019年03月12日 下午17:31:49
*
 */
public class YwWithdraw {

   private String id; // 编号
   private String withdraw_price; // 提现金额
   private String withdraw_type; // 提现方式  1.微信(线上) 2.微信(线下) 3.支付宝(线下) 4.银行卡(线下)
   private String withdraw_identity; // 收款账号
   private String withdraw_status; // 提现状态(1.待处理 2.已处理)
   private Date withdraw_createtime; // 创建时间
   private Date withdraw_updatetime; // 更新时间

	public String getId() {
	    return this.id;
	}
	
	public void setId(String id) {
	    this.id = id;
	}
    public String getWithdraw_price() {
	    return this.withdraw_price;
	}
	
	public void setWithdraw_price(String withdraw_price) {
	    this.withdraw_price = withdraw_price;
	}
    public String getWithdraw_type() {
	    return this.withdraw_type;
	}
	
	public void setWithdraw_type(String withdraw_type) {
	    this.withdraw_type = withdraw_type;
	}
    public String getWithdraw_identity() {
	    return this.withdraw_identity;
	}
	
	public void setWithdraw_identity(String withdraw_identity) {
	    this.withdraw_identity = withdraw_identity;
	}
    public String getWithdraw_status() {
	    return this.withdraw_status;
	}
	
	public void setWithdraw_status(String withdraw_status) {
	    this.withdraw_status = withdraw_status;
	}
    @JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
    public Date getWithdraw_createtime() {
	    return this.withdraw_createtime;
	}
	
	public void setWithdraw_createtime(Date withdraw_createtime) {
	    this.withdraw_createtime = withdraw_createtime;
	}
    @JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
    public Date getWithdraw_updatetime() {
	    return this.withdraw_updatetime;
	}
	
	public void setWithdraw_updatetime(Date withdraw_updatetime) {
	    this.withdraw_updatetime = withdraw_updatetime;
	}
	
}
