package com.yunwei.product.common.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.util.DateUtil;

/**
 * 积分中心
* @ClassName: YwPoints
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年07月26日 下午12:01:41
*
 */
public class YwPoints {

   private String points_id; // 积分编号
   private String points_totals; // 积分总数
   private String user_id; // 用户编号
   private Date points_createtime; // 积分创建时间
   private Date points_updatetime; // 积分更新时间

    public String getPoints_id() {
	    return this.points_id;
	}
	
	public void setPoints_id(String points_id) {
	    this.points_id = points_id;
	}
    public String getPoints_totals() {
	    return this.points_totals;
	}
	
	public void setPoints_totals(String points_totals) {
	    this.points_totals = points_totals;
	}
    public String getUser_id() {
	    return this.user_id;
	}
	
	public void setUser_id(String user_id) {
	    this.user_id = user_id;
	}
	@JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
    public Date getPoints_createtime() {
	    return this.points_createtime;
	}
	public void setPoints_createtime(Date points_createtime) {
	    this.points_createtime = points_createtime;
	}
	@JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
    public Date getPoints_updatetime() {
	    return this.points_updatetime;
	}
	public void setPoints_updatetime(Date points_updatetime) {
	    this.points_updatetime = points_updatetime;
	}
	
}
