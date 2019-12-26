package com.yunwei.product.common.backend.model.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.util.DateUtil;
import com.yunwei.product.common.model.YwSeckillActivity;

/**
 * 
* @ClassName: YwSeckillActivityDto 
* @Description: 秒杀活动与商品联合查询
* @author 晏飞
* @date 2018年7月3日 上午11:13:23 
*
 */
public class YwSeckillActivityDto extends YwSeckillActivity{
	private String title; // 商品名称
	private String price; // 商品价格
	private Date seckill_date;
	private String seckill_starttime;
	private String seckill_endtime;

	private String start_datetime;// 显示开始时间
	
	public String getStart_datetime() {
		return start_datetime;
	}

	public void setStart_datetime(String start_datetime) {
		this.start_datetime = start_datetime;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	
}
