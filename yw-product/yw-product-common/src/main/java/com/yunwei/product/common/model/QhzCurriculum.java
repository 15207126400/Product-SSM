package com.yunwei.product.common.model;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.util.DateUtil;

/**
 * 课程合集信息表
* @ClassName: SalonCurriculum
* @Description: TODO(TODO) 
* @author yanf
* @date 2019年06月21日 下午16:01:29
*
 */
public class QhzCurriculum {

   private String id; // 
   private String type; // 课程类型(1事业课程 2亲子课程)
   private String scene_type; // 应用场景(1沙龙小程序 2官网 3其他)
   private String img; // 课程图片
   private String title; // 课程名称
   private String price; // 课程价格
   private String old_price; // 课程原价
   private String buy_num; // 购买量
   private String teacher_id; // 老师编号
   private String content; // 课程描述
   private String create_name;	//创建人
   private Date create_time; // 创建时间
   private String update_name;	//更新人
   private Date update_time; // 更新时间
   private String status; // 启用状态(1启用 2禁用)
   
   private String teacher_name;	//老师名字
   
   private boolean haveFlag;

	public String getId() {
	    return this.id;
	}
	
	public void setId(String id) {
	    this.id = id;
	}
    public String getType() {
	    return this.type;
	}
	
	public void setType(String type) {
	    this.type = type;
	}
    public String getTitle() {
	    return this.title;
	}
	
	public void setTitle(String title) {
	    this.title = title;
	}
    public String getPrice() {
	    return this.price;
	}
	
	public void setPrice(String price) {
	    this.price = price;
	}
    @JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
    public Date getCreate_time() {
	    return this.create_time;
	}
	
	public void setCreate_time(Date create_time) {
	    this.create_time = create_time;
	}
    @JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
    public Date getUpdate_time() {
	    return this.update_time;
	}
	
	public void setUpdate_datetime(Date update_time) {
	    this.update_time = update_time;
	}
    public String getStatus() {
	    return this.status;
	}
	
	public void setStatus(String status) {
	    this.status = status;
	}

	/** 
	* @return haveFlag 
	*/
	public boolean isHaveFlag() {
		return haveFlag;
	}

	/** 
	* @param haveFlag 要设置的 haveFlag 
	*/
	public void setHaveFlag(boolean haveFlag) {
		this.haveFlag = haveFlag;
	}

	/** 
	* @return content 
	*/
	public String getContent() {
		return content;
	}

	/** 
	* @param content 要设置的 content 
	*/
	public void setContent(String content) {
		this.content = content;
	}

	/** 
	* @return img 
	*/
	public String getImg() {
		return img;
	}

	/** 
	* @param img 要设置的 img 
	*/
	public void setImg(String img) {
		this.img = img;
	}

	/** 
	* @return scene_type 
	*/
	public String getScene_type() {
		return scene_type;
	}

	/** 
	* @param scene_type 要设置的 scene_type 
	*/
	public void setScene_type(String scene_type) {
		this.scene_type = scene_type;
	}

	/** 
	* @return old_price 
	*/
	public String getOld_price() {
		return old_price;
	}

	/** 
	* @param old_price 要设置的 old_price 
	*/
	public void setOld_price(String old_price) {
		this.old_price = old_price;
	}

	/** 
	* @return buy_num 
	*/
	public String getBuy_num() {
		return buy_num;
	}

	/** 
	* @param buy_num 要设置的 buy_num 
	*/
	public void setBuy_num(String buy_num) {
		this.buy_num = buy_num;
	}

	/** 
	* @return teacher_id 
	*/
	public String getTeacher_id() {
		return teacher_id;
	}

	/** 
	* @param teacher_id 要设置的 teacher_id 
	*/
	public void setTeacher_id(String teacher_id) {
		this.teacher_id = teacher_id;
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

	/** 
	* @return create_name 
	*/
	public String getCreate_name() {
		return create_name;
	}

	/** 
	* @param create_name 要设置的 create_name 
	*/
	public void setCreate_name(String create_name) {
		this.create_name = create_name;
	}

	/** 
	* @return update_name 
	*/
	public String getUpdate_name() {
		return update_name;
	}

	/** 
	* @param update_name 要设置的 update_name 
	*/
	public void setUpdate_name(String update_name) {
		this.update_name = update_name;
	}
	
}
