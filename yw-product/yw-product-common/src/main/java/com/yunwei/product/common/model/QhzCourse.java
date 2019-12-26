package com.yunwei.product.common.model;

import java.math.BigDecimal;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.util.DateUtil;

/**
 * 课程详细信息
* @ClassName: Course
* @Description: TODO(TODO) 
* @author yanf
* @date 2019年08月10日 上午10:17:34
*
 */
public class QhzCourse {

   private String id; // 编号
   private String title; // 课程标题
   private String img; // 课程图片
   private String video; // 课程视频
   private String video_time_length; // 视频播放时长
   private String epis; // 课程所属集数
   private String curriculum_id; // 课程父编号
   private String teacher_id; // 老师编号
   private String resume; // 课程简述
   private String content; // 课程详情(富文本)
   private String create_name; // 创建人
   private Date create_time; // 创建时间
   private String update_name; // 更新人
   private Date update_time; // 修改时间
   private String unit_price; // 单价(现价)
   private String fic_price; // 虚拟价格(原价)
   private String click_num; // 总点击量(播放次数)
   private String fic_click_num; // 虚拟点击量
   private String audition; // 是否可以试听(1.可以 2.不可以)
   private String status; // 启用状态(1.启用 2.禁用)
   
   private String curriculum_name;	//课程名称
   private String teacher_name;	//老师名字
   
	public String getId() {
	    return this.id;
	}
	
	public void setId(String id) {
	    this.id = id;
	}
    public String getTitle() {
	    return this.title;
	}
	
	public void setTitle(String title) {
	    this.title = title;
	}
    public String getImg() {
	    return this.img;
	}
	
	public void setImg(String img) {
	    this.img = img;
	}
    public String getVideo() {
	    return this.video;
	}
	
	public void setVideo(String video) {
	    this.video = video;
	}
    public String getVideo_time_length() {
	    return this.video_time_length;
	}
	
	public void setVideo_time_length(String video_time_length) {
	    this.video_time_length = video_time_length;
	}
    public String getEpis() {
	    return this.epis;
	}
	
	public void setEpis(String epis) {
	    this.epis = epis;
	}
    public String getCurriculum_id() {
	    return this.curriculum_id;
	}
	
	public void setCurriculum_id(String curriculum_id) {
	    this.curriculum_id = curriculum_id;
	}
    public String getTeacher_id() {
	    return this.teacher_id;
	}
	
	public void setTeacher_id(String teacher_id) {
	    this.teacher_id = teacher_id;
	}
    public String getResume() {
	    return this.resume;
	}
	
	public void setResume(String resume) {
	    this.resume = resume;
	}
    public String getContent() {
	    return this.content;
	}
	
	public void setContent(String content) {
	    this.content = content;
	}
    public String getCreate_name() {
	    return this.create_name;
	}
	
	public void setCreate_name(String create_name) {
	    this.create_name = create_name;
	}
    @JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
    public Date getCreate_time() {
	    return this.create_time;
	}
	
	public void setCreate_time(Date create_time) {
	    this.create_time = create_time;
	}
    public String getUpdate_name() {
	    return this.update_name;
	}
	
	public void setUpdate_name(String update_name) {
	    this.update_name = update_name;
	}
    @JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
    public Date getUpdate_time() {
	    return this.update_time;
	}
	
	public void setUpdate_time(Date update_time) {
	    this.update_time = update_time;
	}
    public String getUnit_price() {
	    return this.unit_price;
	}
	
	public void setUnit_price(String unit_price) {
	    this.unit_price = unit_price;
	}
    public String getFic_price() {
	    return this.fic_price;
	}
	
	public void setFic_price(String fic_price) {
	    this.fic_price = fic_price;
	}
    public String getClick_num() {
	    return this.click_num;
	}
	
	public void setClick_num(String click_num) {
	    this.click_num = click_num;
	}
    public String getFic_click_num() {
	    return this.fic_click_num;
	}
	
	public void setFic_click_num(String fic_click_num) {
	    this.fic_click_num = fic_click_num;
	}
    public String getAudition() {
	    return this.audition;
	}
	
	public void setAudition(String audition) {
	    this.audition = audition;
	}
    public String getStatus() {
	    return this.status;
	}
	
	public void setStatus(String status) {
	    this.status = status;
	}

	/** 
	* @return curriculum_name 
	*/
	public String getCurriculum_name() {
		return curriculum_name;
	}

	/** 
	* @param curriculum_name 要设置的 curriculum_name 
	*/
	public void setCurriculum_name(String curriculum_name) {
		this.curriculum_name = curriculum_name;
	}

	/** 
	* @return teacher_name 
	*/
	public String getTeacher_name() {
		return teacher_name;
	}

	/** 
	* @param teacher_name 要设置的 teacher_name 
	*/
	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}
	
}
