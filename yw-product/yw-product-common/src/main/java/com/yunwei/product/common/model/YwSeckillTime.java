package com.yunwei.product.common.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.util.DateUtil;

/**
 * 秒杀时间配置表
* @ClassName: YwSeckillTime 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年7月2日 下午2:28:41 
*
 */
public class YwSeckillTime {

	private String time_id; // 秒杀时间编号
	private String time_title_url; // 秒杀时间主题url
	private Date seckill_date; // 秒杀日期
	private String seckill_starttime; // 秒杀开始时间点
	private String seckill_endtime; // 秒杀结束时间
	private String time_status; // 秒杀时间状态（0：启动，1：禁用）
	
	public String getTime_id() {
		return time_id;
	}
	public void setTime_id(String time_id) {
		this.time_id = time_id;
	}
	@JsonFormat(pattern = DateUtil.DATE_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_FORMAT)
	public Date getSeckill_date() {
		return seckill_date;
	}
	public void setSeckill_date(Date seckill_date) {
		this.seckill_date = seckill_date;
	}
	public String getSeckill_starttime() {
		return seckill_starttime;
	}
	public void setSeckill_starttime(String seckill_starttime) {
		this.seckill_starttime = seckill_starttime;
	}
	public String getSeckill_endtime() {
		return seckill_endtime;
	}
	public void setSeckill_endtime(String seckill_endtime) {
		this.seckill_endtime = seckill_endtime;
	}
	public String getTime_status() {
		return time_status;
	}
	public void setTime_status(String time_status) {
		this.time_status = time_status;
	}
	public String getTime_title_url() {
		return time_title_url;
	}
	public void setTime_title_url(String time_title_url) {
		this.time_title_url = time_title_url;
	}
	
	
}
