package com.yunwei.product.common.model;

import java.math.BigDecimal;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.util.DateUtil;

/**
 * 老师信息模块
* @ClassName: Teacher
* @Description: TODO(TODO) 
* @author yanf
* @date 2019年07月16日 上午11:29:22
*
 */
public class QhzTeacher {

   private String id; // 编号
   private String name; // 姓名
   private String photo; // 照片
   private String card; // 身份证
   private String age; // 年龄
   private String sex; // 性别
   private String phone; // 手机号
   private String address; // 住址
   private String post; // 职位
   private String dept; // 部门
   private String brief; // 简介

	public String getId() {
	    return this.id;
	}
	
	public void setId(String id) {
	    this.id = id;
	}
    public String getName() {
	    return this.name;
	}
	
	public void setName(String name) {
	    this.name = name;
	}
    public String getPhoto() {
	    return this.photo;
	}
	
	public void setPhoto(String photo) {
	    this.photo = photo;
	}
    public String getCard() {
	    return this.card;
	}
	
	public void setCard(String card) {
	    this.card = card;
	}
    public String getAge() {
	    return this.age;
	}
	
	public void setAge(String age) {
	    this.age = age;
	}
    public String getSex() {
	    return this.sex;
	}
	
	public void setSex(String sex) {
	    this.sex = sex;
	}
    public String getPhone() {
	    return this.phone;
	}
	
	public void setPhone(String phone) {
	    this.phone = phone;
	}
    public String getAddress() {
	    return this.address;
	}
	
	public void setAddress(String address) {
	    this.address = address;
	}
    public String getPost() {
	    return this.post;
	}
	
	public void setPost(String post) {
	    this.post = post;
	}
    public String getDept() {
	    return this.dept;
	}
	
	public void setDept(String dept) {
	    this.dept = dept;
	}
    public String getBrief() {
	    return this.brief;
	}
	
	public void setBrief(String brief) {
	    this.brief = brief;
	}
	
}
