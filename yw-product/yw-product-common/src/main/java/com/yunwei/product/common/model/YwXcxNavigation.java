package com.yunwei.product.common.model;

import java.math.BigDecimal;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.util.DateUtil;

/**
 * 小程序跳转导航配置表
* @ClassName: YwXcxNavigation
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年10月29日 下午17:49:22
*
 */
public class YwXcxNavigation {

   private String id; // 编号
   private String user_id;  // 用户编号
   private String wx_appid; // 微信小程序appid编号
   private String scene_type; // 应用场景
   private String nav_name; // 导航跳转名称
   private String nav_path; // 导航跳转路径
   private String status; // 状态

	public String getId() {
	    return this.id;
	}
	
	public void setId(String id) {
	    this.id = id;
	}
    public String getNav_name() {
	    return this.nav_name;
	}
	
	public void setNav_name(String nav_name) {
	    this.nav_name = nav_name;
	}
    public String getNav_path() {
	    return this.nav_path;
	}
	
	public void setNav_path(String nav_path) {
	    this.nav_path = nav_path;
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
	* @return wx_appid 
	*/
	public String getWx_appid() {
		return wx_appid;
	}

	/** 
	* @param wx_appid 要设置的 wx_appid 
	*/
	public void setWx_appid(String wx_appid) {
		this.wx_appid = wx_appid;
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
	* @return user_id 
	*/
	public String getUser_id() {
		return user_id;
	}

	/** 
	* @param user_id 要设置的 user_id 
	*/
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
}
