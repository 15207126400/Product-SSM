package com.yunwei.product.common.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.annotation.DateFormat;
import com.yunwei.common.util.DateUtil;

/**
 * 分销人员信息表
 * @author zhangjh
 *
 */
public class YwDistributor {
	
   private String dis_id             ;  //'分销商编号',
   private String dis_name           ;  //'分销商名称',
   private String dis_type           ;  //'分销商类型',
   private String dis_parentid       ;  //'分销商上级编号',
   private String dis_status         ;  //'分销商状态',
   private String dis_level          ;  //'分销商等级',
   private String dis_receipt_type   ;  //'分销商收款类型',
   private String dis_receipt_method ;  //'分销商收款方式',
   private String dis_receipt_account;  //'分销商收款账号',
   private String dis_mobile         ;  //'分销商手机号码',
   private String dis_qq             ;  //'分销商qq',
   private String dis_wxcode         ;  //'分销商微信',
   private String dis_address        ;  //'分销商家庭住址',
   private String dis_scale          ;  //'分销商佣金比例',
   @DateFormat(DateUtil.DATE_TIME_FORMAT)
   private Date dis_apply_time     ;  // '分销商申请时间',
   @DateFormat(DateUtil.DATE_TIME_FORMAT)
   private Date dis_audit_begintime;  //'审核开始时间',
   @DateFormat(DateUtil.DATE_TIME_FORMAT)
   private Date dis_audit_endtime  ;  //'审核结束时间',
   private String dis_audit_name     ;  //'审核员',
   private String dis_audit_remark;//审核备注
   private String dis_age;//分销商年龄
   private String dis_sex;//分销商性别
   private String dis_apply_reason;//分销商申请原因
   
public String getDis_id() {
	return dis_id;
}
public void setDis_id(String dis_id) {
	this.dis_id = dis_id;
}
public String getDis_name() {
	return dis_name;
}
public void setDis_name(String dis_name) {
	this.dis_name = dis_name;
}
public String getDis_type() {
	return dis_type;
}
public void setDis_type(String dis_type) {
	this.dis_type = dis_type;
}
public String getDis_parentid() {
	return dis_parentid;
}
public void setDis_parentid(String dis_parentid) {
	this.dis_parentid = dis_parentid;
}
public String getDis_status() {
	return dis_status;
}
public void setDis_status(String dis_status) {
	this.dis_status = dis_status;
}
public String getDis_level() {
	return dis_level;
}
public void setDis_level(String dis_level) {
	this.dis_level = dis_level;
}
public String getDis_receipt_type() {
	return dis_receipt_type;
}
public void setDis_receipt_type(String dis_receipt_type) {
	this.dis_receipt_type = dis_receipt_type;
}
public String getDis_receipt_method() {
	return dis_receipt_method;
}
public void setDis_receipt_method(String dis_receipt_method) {
	this.dis_receipt_method = dis_receipt_method;
}
public String getDis_receipt_account() {
	return dis_receipt_account;
}
public void setDis_receipt_account(String dis_receipt_account) {
	this.dis_receipt_account = dis_receipt_account;
}
public String getDis_mobile() {
	return dis_mobile;
}
public void setDis_mobile(String dis_mobile) {
	this.dis_mobile = dis_mobile;
}
public String getDis_qq() {
	return dis_qq;
}
public void setDis_qq(String dis_qq) {
	this.dis_qq = dis_qq;
}
public String getDis_wxcode() {
	return dis_wxcode;
}
public void setDis_wxcode(String dis_wxcode) {
	this.dis_wxcode = dis_wxcode;
}
public String getDis_address() {
	return dis_address;
}
public void setDis_address(String dis_address) {
	this.dis_address = dis_address;
}
public String getDis_scale() {
	return dis_scale;
}
public void setDis_scale(String dis_scale) {
	this.dis_scale = dis_scale;
}
@JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
public Date getDis_apply_time() {
	return dis_apply_time;
}
public void setDis_apply_time(Date dis_apply_time) {
	this.dis_apply_time = dis_apply_time;
}
@JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
public Date getDis_audit_begintime() {
	return dis_audit_begintime;
}
public void setDis_audit_begintime(Date dis_audit_begintime) {
	this.dis_audit_begintime = dis_audit_begintime;
}
@JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
public Date getDis_audit_endtime() {
	return dis_audit_endtime;
}
public void setDis_audit_endtime(Date dis_audit_endtime) {
	this.dis_audit_endtime = dis_audit_endtime;
}
public String getDis_audit_name() {
	return dis_audit_name;
}
public void setDis_audit_name(String dis_audit_name) {
	this.dis_audit_name = dis_audit_name;
}
public String getDis_audit_remark() {
	return dis_audit_remark;
}
public void setDis_audit_remark(String dis_audit_remark) {
	this.dis_audit_remark = dis_audit_remark;
}
public String getDis_age() {
	return dis_age;
}
public void setDis_age(String dis_age) {
	this.dis_age = dis_age;
}
public String getDis_sex() {
	return dis_sex;
}
public void setDis_sex(String dis_sex) {
	this.dis_sex = dis_sex;
}
public String getDis_apply_reason() {
	return dis_apply_reason;
}
public void setDis_apply_reason(String dis_apply_reason) {
	this.dis_apply_reason = dis_apply_reason;
}


}
