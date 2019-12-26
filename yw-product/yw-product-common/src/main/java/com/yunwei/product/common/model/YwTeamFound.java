package com.yunwei.product.common.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.annotation.Base64Decode;
import com.yunwei.common.util.DateUtil;

/**
 * 
* @ClassName: YwTeamFound 
* @Description: 开团表
* @author zhangjh
* @date 2018年3月20日 下午4:36:54 
*
 */
public class YwTeamFound {

	private String   found_id        ; // 
	private Date	found_time      ; // '开团时间',
	private Date	found_end_time  ; // '成团截止时间',
	private String	user_id         ; // '团长id',
	private String	team_id         ; // '拼团活动id',
	@Base64Decode
	private String	nickname        ; // '团长用户名昵称',
	private String	head_pic        ; // '团长头像',
	private String	order_id        ; // '团长订单id',
	private String	found_join            ; // '已参团人数',
	private String	need            ; // '需多少人成团',
	private String	price           ; // '拼团价格',
	private String	product_price   ; // '商品原价',
	private String	status          ; // '拼团状态0:待开团(表示已下单但是未支付)1:已经开团(团长已支付)2:拼团成功,3拼团失败',
	private String	bonus_status    ; // '团长佣金领取状态：0无1领取',
	
	public String getFound_id() {
		return found_id;
	}
	public void setFound_id(String found_id) {
		this.found_id = found_id;
	}
	@JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
	public Date getFound_time() {
		return found_time;
	}
	public void setFound_time(Date found_time) {
		this.found_time = found_time;
	}
	@JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
	public Date getFound_end_time() {
		return found_end_time;
	}
	public void setFound_end_time(Date found_end_time) {
		this.found_end_time = found_end_time;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getTeam_id() {
		return team_id;
	}
	public void setTeam_id(String team_id) {
		this.team_id = team_id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getHead_pic() {
		return head_pic;
	}
	public void setHead_pic(String head_pic) {
		this.head_pic = head_pic;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getFound_join() {
		return found_join;
	}
	public void setFound_join(String found_join) {
		this.found_join = found_join;
	}
	public String getNeed() {
		return need;
	}
	public void setNeed(String need) {
		this.need = need;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getProduct_price() {
		return product_price;
	}
	public void setProduct_price(String product_price) {
		this.product_price = product_price;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBonus_status() {
		return bonus_status;
	}
	public void setBonus_status(String bonus_status) {
		this.bonus_status = bonus_status;
	}
	
}
