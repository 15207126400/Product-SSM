package com.yunwei.product.common.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.annotation.Base64Decode;
import com.yunwei.common.util.DateUtil;

/**
 * 
* @ClassName: YwTeamFollow 
* @Description: 参团表 
* @author zhangjh
* @date 2018年3月20日 下午4:33:10 
*
 */
public class YwTeamFollow {

	private String   follow_id             ; // 
	private String	follow_user_id        ; // '参团会员id',
	@Base64Decode
	private String	follow_user_nickname  ; // '参团会员昵称',
	private String	follow_user_head_pic  ; // '会员头像',
	private Date	follow_time    ; // '参团时间',
	private String	order_id       ; // '订单id',
	private String	found_id       ; // '开团ID',
	private String	found_user_id  ; // '开团人user_id',
	private String	team_id       ; // '拼团活动id',
	private String	status      ; // '参团状态0:待拼单(表示已下单但是未支付)1拼单成功(已支付)2成团成功3成团失败',
	private String	is_win      ; // '抽奖团是否中奖',

	public String getFollow_id() {
		return follow_id;
	}
	public void setFollow_id(String follow_id) {
		this.follow_id = follow_id;
	}
	public String getFollow_user_id() {
		return follow_user_id;
	}
	public void setFollow_user_id(String follow_user_id) {
		this.follow_user_id = follow_user_id;
	}
	public String getFollow_user_nickname() {
		return follow_user_nickname;
	}
	public void setFollow_user_nickname(String follow_user_nickname) {
		this.follow_user_nickname = follow_user_nickname;
	}
	public String getFollow_user_head_pic() {
		return follow_user_head_pic;
	}
	public void setFollow_user_head_pic(String follow_user_head_pic) {
		this.follow_user_head_pic = follow_user_head_pic;
	}
	@JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
	public Date getFollow_time() {
		return follow_time;
	}
	public void setFollow_time(Date follow_time) {
		this.follow_time = follow_time;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getFound_id() {
		return found_id;
	}
	public void setFound_id(String found_id) {
		this.found_id = found_id;
	}
	public String getFound_user_id() {
		return found_user_id;
	}
	public void setFound_user_id(String found_user_id) {
		this.found_user_id = found_user_id;
	}
	public String getTeam_id() {
		return team_id;
	}
	public void setTeam_id(String team_id) {
		this.team_id = team_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIs_win() {
		return is_win;
	}
	public void setIs_win(String is_win) {
		this.is_win = is_win;
	}
	
}
