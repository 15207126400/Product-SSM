package com.yunwei.product.common.model;

import java.math.BigDecimal;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.util.DateUtil;

/**
 * 文章信息模块
* @ClassName: Article
* @Description: TODO(TODO) 
* @author yanf
* @date 2019年07月16日 下午15:34:20
*
 */
public class QhzArticle {

   private String id; // 
   private String type; // 类型(1经典推荐 2时下热点 3客户案例 4公司介绍 5员工风采)
   private String scene_type; // (1沙龙小程序 2官网 3其他)
   private String title; // 标题
   private String img; // 图片
   private String content; // 内容
   private String create_name; // 发布人
   private Date create_time; // 发布时间
   private String update_name; // 更新人
   private Date update_time; //更新时间
   private String read_num; // 阅读数
   private String help_num; // 点赞数
   private String comment; // 评论
   private String status; // 评论

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
    public String getImg() {
	    return this.img;
	}
	
	public void setImg(String img) {
	    this.img = img;
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
    public String getRead_num() {
	    return this.read_num;
	}
	
	public void setRead_num(String read_num) {
	    this.read_num = read_num;
	}
    public String getHelp_num() {
	    return this.help_num;
	}
	
	public void setHelp_num(String help_num) {
	    this.help_num = help_num;
	}
    public String getComment() {
	    return this.comment;
	}
	
	public void setComment(String comment) {
	    this.comment = comment;
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

	/** 
	* @return update_time 
	*/
	@JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
	public Date getUpdate_time() {
		return update_time;
	}

	/** 
	* @param update_time 要设置的 update_time 
	*/
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	/** 
	* @return status 
	*/
	public String getStatus() {
		return status;
	}

	/** 
	* @param status 要设置的 status 
	*/
	public void setStatus(String status) {
		this.status = status;
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
	
}
