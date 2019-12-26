package com.yunwei.product.common.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mysql.fabric.xmlrpc.base.Array;
import com.yunwei.common.annotation.Base64Decode;
import com.yunwei.common.util.DateUtil;
import com.yunwei.product.common.backend.model.dto.YwTeamFoundDto;
import com.yunwei.product.common.backend.model.form.YwOrderProductForm;

/**
 * 
* @ClassName: ShopOrder 
* @Description: 订单实体类
* @author 晏飞
* @date 2017年12月28日 上午11:25:06 
*
 */
public class YwOrder {
	private int order_id;
	private String order_type;
	private String order_flag;
	private String openid;
	private String share_openid;
	private String order_sn;
	private Date order_createtime;
	private Date order_updatetime;
	private Date order_paytime;
	private Date order_sendtime;
	private Date order_successtime;
	private String order_status;
	private String couponid;
	private String order_name;
	private String order_tel;
	private String order_address;
	private String order_totalprice;
	private String order_remark;
	private String transport_sn;
	private String order_points;
	private String order_import_status = "0";
	private String order_delivery_method = "2";
	private String branch_id;
	private String order_delivery_fee;// 配送费
	private String branch_name;		// 机构名称
	private String branch_logo;		// 机构logo
	
	
	public String getBranch_name() {
		return branch_name;
	}
	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}
	public String getBranch_logo() {
		return branch_logo;
	}
	public void setBranch_logo(String branch_logo) {
		this.branch_logo = branch_logo;
	}
	// 订单项信息
	private List<YwOrderItem> ywOrderItems = new ArrayList<YwOrderItem>();
	
	// 订单支付信息
	private YwOrderPayment ywOrderPayment;
	
	// 订单商品表单数据
	private List<YwOrderProductForm> productForms = new ArrayList<YwOrderProductForm>();
	
	// 开团信息
	private YwTeamFound teamFoundData = new YwTeamFound();
	
	// 参团信息
	private YwTeamFollow teamFollowData = new YwTeamFollow();
	
	// 开团信息(包含参团信息)
	private YwTeamFoundDto teamFoundDto = new YwTeamFoundDto();
	
	// 秒杀记录信息
	private YwSeckillRecord seckillRecordData = new YwSeckillRecord();
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public String getOrder_type() {
		return order_type;
	}
	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getOrder_sn() {
		return order_sn;
	}
	public void setOrder_sn(String order_sn) {
		this.order_sn = order_sn;
	}
	@JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_FORMAT)
	public Date getOrder_createtime() {
		return order_createtime;
	}
	public void setOrder_createtime(Date order_createtime) {
		this.order_createtime = order_createtime;
	}
	@JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
	public Date getOrder_updatetime() {
		return order_updatetime;
	}
	public void setOrder_updatetime(Date order_updatetime) {
		this.order_updatetime = order_updatetime;
	}
	public String getOrder_status() {
		return order_status;
	}
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}
	public String getCouponid() {
		return couponid;
	}
	public void setCouponid(String couponid) {
		this.couponid = couponid;
	}
	public String getOrder_address() {
		return order_address;
	}
	public void setOrder_address(String order_address) {
		this.order_address = order_address;
	}
	public String getOrder_name() {
		return order_name;
	}
	public void setOrder_name(String order_name) {
		this.order_name = order_name;
	}
	public String getOrder_tel() {
		return order_tel;
	}
	public void setOrder_tel(String order_tel) {
		this.order_tel = order_tel;
	}
	public String getOrder_totalprice() {
		return order_totalprice;
	}
	public void setOrder_totalprice(String order_totalprice) {
		this.order_totalprice = order_totalprice;
	}
	public String getOrder_remark() {
		return order_remark;
	}
	public void setOrder_remark(String order_remark) {
		this.order_remark = order_remark;
	}
	@JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
	public Date getOrder_paytime() {
		return order_paytime;
	}
	public void setOrder_paytime(Date order_paytime) {
		this.order_paytime = order_paytime;
	}
	@JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
	public Date getOrder_sendtime() {
		return order_sendtime;
	}
	public void setOrder_sendtime(Date order_sendtime) {
		this.order_sendtime = order_sendtime;
	}
	@JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
	public Date getOrder_successtime() {
		return order_successtime;
	}
	public void setOrder_successtime(Date order_successtime) {
		this.order_successtime = order_successtime;
	}
	public String getTransport_sn() {
		return transport_sn;
	}
	public void setTransport_sn(String transport_sn) {
		this.transport_sn = transport_sn;
	}
	public List<YwOrderItem> getYwOrderItems() {
		return ywOrderItems;
	}
	public void setYwOrderItems(List<YwOrderItem> ywOrderItems) {
		this.ywOrderItems = ywOrderItems;
	}
	public List<YwOrderProductForm> getProductForms() {
		return productForms;
	}
	public void setProductForms(List<YwOrderProductForm> productForms) {
		this.productForms = productForms;
	}
	public YwTeamFound getTeamFoundData() {
		return teamFoundData;
	}
	public void setTeamFoundData(YwTeamFound teamFoundData) {
		this.teamFoundData = teamFoundData;
	}
	public YwTeamFollow getTeamFollowData() {
		return teamFollowData;
	}
	public void setTeamFollowData(YwTeamFollow teamFollowData) {
		this.teamFollowData = teamFollowData;
	}
	public YwOrderPayment getYwOrderPayment() {
		return ywOrderPayment;
	}
	public void setYwOrderPayment(YwOrderPayment ywOrderPayment) {
		this.ywOrderPayment = ywOrderPayment;
	}
	public String getOrder_flag() {
		return order_flag;
	}
	public void setOrder_flag(String order_flag) {
		this.order_flag = order_flag;
	}
	public YwTeamFoundDto getTeamFoundDto() {
		return teamFoundDto;
	}
	public void setTeamFoundDto(YwTeamFoundDto teamFoundDto) {
		this.teamFoundDto = teamFoundDto;
	}
	public String getShare_openid() {
		return share_openid;
	}
	public void setShare_openid(String share_openid) {
		this.share_openid = share_openid;
	}
	public String getOrder_points() {
		return order_points;
	}
	public void setOrder_points(String order_points) {
		this.order_points = order_points;
	}
	public YwSeckillRecord getSeckillRecordData() {
		return seckillRecordData;
	}
	public void setSeckillRecordData(YwSeckillRecord seckillRecordData) {
		this.seckillRecordData = seckillRecordData;
	}
	public String getOrder_import_status() {
		return order_import_status;
	}
	public void setOrder_import_status(String order_import_status) {
		this.order_import_status = order_import_status;
	}
	public String getOrder_delivery_method() {
		return order_delivery_method;
	}
	public void setOrder_delivery_method(String order_delivery_method) {
		this.order_delivery_method = order_delivery_method;
	}
	public String getBranch_id() {
		return branch_id;
	}
	public void setBranch_id(String branch_id) {
		this.branch_id = branch_id;
	}
	public String getOrder_delivery_fee() {
		return order_delivery_fee;
	}
	public void setOrder_delivery_fee(String order_delivery_fee) {
		this.order_delivery_fee = order_delivery_fee;
	}
	
	
}
