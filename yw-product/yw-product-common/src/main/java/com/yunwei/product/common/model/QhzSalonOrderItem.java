package com.yunwei.product.common.model;

import java.math.BigDecimal;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.util.DateUtil;

/**
 * 沙龙注册订单项信息表
* @ClassName: SalonOrderItem
* @Description: TODO(TODO) 
* @author yanf
* @date 2019年06月25日 下午16:33:24
*
 */
public class QhzSalonOrderItem {

   private String id; // 编号
   private String order_sn; // 订单号
   private String order_name; // 课程名称
   private String order_price; // 课程单价
   private String curriculum_id; // 课程编号

	public String getId() {
	    return this.id;
	}
	
	public void setId(String id) {
	    this.id = id;
	}
    public String getOrder_sn() {
	    return this.order_sn;
	}
	
	public void setOrder_sn(String order_sn) {
	    this.order_sn = order_sn;
	}
    public String getOrder_name() {
	    return this.order_name;
	}
	
	public void setOrder_name(String order_name) {
	    this.order_name = order_name;
	}
    public String getOrder_price() {
	    return this.order_price;
	}
	
	public void setOrder_price(String order_price) {
	    this.order_price = order_price;
	}
    public String getCurriculum_id() {
	    return this.curriculum_id;
	}
	
	public void setCurriculum_id(String curriculum_id) {
	    this.curriculum_id = curriculum_id;
	}
	
}
