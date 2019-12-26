package com.yunwei.product.common.model;

import java.math.BigDecimal;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.util.DateUtil;

/**
 * 线下订单
* @ClassName: YwOrderOffline
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年11月09日 下午15:39:34
*
 */
public class YwOrderOffline {

   private String id; // 主键
   private String order_sn; // 订单编号
   private String offline_type; // 线下订单类型（0、客户扫码，1、商家扫码）
   private String branch_id; // 机构编号（超市或者其他机构）
   private String branch_name; // 机构名称
   private String openid; // 用户openid
   private String nickname; // 用户昵称
   private String offline_totalprice;// 线下消费金额
   private String order_totalprice; // 订单金额
   private String order_discount_price; // 订单优惠金额
   private String order_nodiscount_price;// 不参与优惠的金额
   private Date create_datetime; // 创建时间
   private Date update_datetime; // 更新时间
   private String offline_remark; // 线下订单备注
   private String offline_status; // 线下订单状态（0，待支付，1、支付成功，2、支付失败）
   private String branch_logo; 	// 超市logo
   

	public String getId() {
	    return this.id;
	}
	
	public void setId(String id) {
	    this.id = id;
	}
    public String getOrder_sn() {
	    return this.order_sn;
	}
	
	public void setOrder_sn(String order_sn) {
	    this.order_sn = order_sn;
	}
	public String getOffline_type() {
	    return this.offline_type;
	}
	
	public void setOffline_type(String offline_type) {
	    this.offline_type = offline_type;
	}
    public String getBranch_id() {
	    return this.branch_id;
	}
	
	public void setBranch_id(String branch_id) {
	    this.branch_id = branch_id;
	}
    
    public String getBranch_name() {
		return branch_name;
	}

	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}

	public String getOpenid() {
	    return this.openid;
	}
	
	public void setOpenid(String openid) {
	    this.openid = openid;
	}
    public String getNickname() {
	    return this.nickname;
	}
	
	public void setNickname(String nickname) {
	    this.nickname = nickname;
	}
    public String getOrder_totalprice() {
	    return this.order_totalprice;
	}
	
	public void setOrder_totalprice(String order_totalprice) {
	    this.order_totalprice = order_totalprice;
	}
    public String getOrder_discount_price() {
	    return this.order_discount_price;
	}
	
	public void setOrder_discount_price(String order_discount_price) {
	    this.order_discount_price = order_discount_price;
	}
    @JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
    public Date getCreate_datetime() {
	    return this.create_datetime;
	}
	
	public void setCreate_datetime(Date create_datetime) {
	    this.create_datetime = create_datetime;
	}
    @JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
    public Date getUpdate_datetime() {
	    return this.update_datetime;
	}
	
	public void setUpdate_datetime(Date update_datetime) {
	    this.update_datetime = update_datetime;
	}
    public String getOffline_remark() {
	    return this.offline_remark;
	}
	
	public void setOffline_remark(String offline_remark) {
	    this.offline_remark = offline_remark;
	}
	public String getOffline_status() {
	    return this.offline_status;
	}
	
	public void setOffline_status(String offline_status) {
	    this.offline_status = offline_status;
	}

	public String getOrder_nodiscount_price() {
		return order_nodiscount_price;
	}

	public void setOrder_nodiscount_price(String order_nodiscount_price) {
		this.order_nodiscount_price = order_nodiscount_price;
	}

	public String getOffline_totalprice() {
		return offline_totalprice;
	}

	public void setOffline_totalprice(String offline_totalprice) {
		this.offline_totalprice = offline_totalprice;
	}

	/** 
	* @return branch_logo 
	*/
	public String getBranch_logo() {
		return branch_logo;
	}

	/** 
	* @param branch_logo 要设置的 branch_logo 
	*/
	public void setBranch_logo(String branch_logo) {
		this.branch_logo = branch_logo;
	}
	
}
