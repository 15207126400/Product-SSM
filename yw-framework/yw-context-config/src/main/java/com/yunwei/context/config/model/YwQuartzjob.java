package com.yunwei.context.config.model;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.util.DateUtil;


/**
 * 定时任务表
* @ClassName: YwQuartzjob 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年6月28日 下午1:39:46 
*
 */
public class YwQuartzjob {

	@NotBlank
	private String job_name;// 任务名称
	
	private String job_group;// 任务分组
	
	private String description;// 详细描述
	
	private String concurrent_flag;// 并发标志
	
	private String job_class;// 任务事件
	
	private String trigger_time;// 触发时间
	
	private java.util.Date update_datetime;// 更新日期时间
	
	private String valid_flag;// 有效标志

	public String getJob_name(){
		return job_name;
	};
	
	public void setJob_name(String job_name){
		this.job_name = job_name;
	};
	public String getJob_group(){
		return job_group;
	};
	
	public void setJob_group(String job_group){
		this.job_group = job_group;
	};
	public String getDescription(){
		return description;
	};
	
	public void setDescription(String description){
		this.description = description;
	};
	public String getConcurrent_flag(){
		return concurrent_flag;
	};
	
	public void setConcurrent_flag(String concurrent_flag){
		this.concurrent_flag = concurrent_flag;
	};
	public String getJob_class(){
		return job_class;
	};
	
	public void setJob_class(String job_class){
		this.job_class = job_class;
	};
	public String getTrigger_time(){
		return trigger_time;
	};
	
	public void setTrigger_time(String trigger_time){
		this.trigger_time = trigger_time;
	};
	@JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
	public java.util.Date getUpdate_datetime(){
		return update_datetime;
	};
	
	public void setUpdate_datetime(java.util.Date update_datetime){
		this.update_datetime = update_datetime;
	};
	public String getValid_flag(){
		return valid_flag;
	};
	
	public void setValid_flag(String valid_flag){
		this.valid_flag = valid_flag;
	};
}