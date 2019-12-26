package com.yunwei.product.common.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.util.DateUtil;

/**
 * 积分明细
* @ClassName: YwPointsDetail
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年07月26日 下午14:04:33
*
 */
public class YwPointsDetail {

   private String detail_id; // 积分明细编号
   private String user_id; // 用户编号
   private String detail_type; // 积分明细类型(1,兑换商品 ; 2,兑换优惠券 ; 3,签到 ; 4,订单)
   private String detail_status; // 积分明细状态(1,未生效 ; 2,生效)
   private String points_id; // 积分编号
   private String order_sn; // 订单编号
   private String detail_points_change; // 变动积分
   private Date detail_createtime; // 积分明细创建时间
   private Date detail_updatetime; // 积分明细更新时间

    public String getDetail_id() {
	    return this.detail_id;
	}
	
	public void setDetail_id(String detail_id) {
	    this.detail_id = detail_id;
	}
    public String getUser_id() {
	    return this.user_id;
	}
	
	public void setUser_id(String user_id) {
	    this.user_id = user_id;
	}
    public String getDetail_type() {
	    return this.detail_type;
	}
	
	public void setDetail_type(String detail_type) {
	    this.detail_type = detail_type;
	}
    public String getDetail_status() {
	    return this.detail_status;
	}
	
	public void setDetail_status(String detail_status) {
	    this.detail_status = detail_status;
	}
    public String getPoints_id() {
	    return this.points_id;
	}
	
	public void setPoints_id(String points_id) {
	    this.points_id = points_id;
	}
    public String getOrder_sn() {
	    return this.order_sn;
	}
	
	public void setOrder_sn(String order_sn) {
	    this.order_sn = order_sn;
	}
    public String getDetail_points_change() {
	    return this.detail_points_change;
	}
	
	public void setDetail_points_change(String detail_points_change) {
	    this.detail_points_change = detail_points_change;
	}
	@JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
    public Date getDetail_createtime() {
	    return this.detail_createtime;
	}
	public void setDetail_createtime(Date detail_createtime) {
	    this.detail_createtime = detail_createtime;
	}
	@JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
    public Date getDetail_updatetime() {
	    return this.detail_updatetime;
	}
	public void setDetail_updatetime(Date detail_updatetime) {
	    this.detail_updatetime = detail_updatetime;
	}
	
}
