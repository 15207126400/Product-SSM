package com.yunwei.product.common.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunwei.common.util.DateUtil;

/**
 * 
* @ClassName: ywDistributionProduct 
* @Description: 分销商品表 
* @author 晏飞
* @date 2018年3月15日 下午3:29:02 
*
 */
public class YwDistributionProduct {
	private String dis_pro_id;// 分销商品编号
	private String product_id; 		  // '商品编号'
	private String product_name;//商品名称
	private String dis_pro_onescale; // '一级分销佣金比例',
	private String dis_pro_twoscale; // '二级分销佣金比例',
	private String dis_pro_showlogo; // '是否显示分销类logo',
	private Date dis_pro_createtime; // '发布时间',
	private Date dis_pro_endtime; // '到期时间',
	private String dis_pro_status; // '分销商品状态(0已下架,1上架中)',
	private String dis_pro_discounts; // '是否享受优惠(0否,1是)',
	private String dis_pro_saleatotal; // 分销商品销售总量
	
	public String getDis_pro_id() {
		return dis_pro_id;
	}
	public void setDis_pro_id(String dis_pro_id) {
		this.dis_pro_id = dis_pro_id;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getDis_pro_showlogo() {
		return dis_pro_showlogo;
	}
	public void setDis_pro_showlogo(String dis_pro_showlogo) {
		this.dis_pro_showlogo = dis_pro_showlogo;
	}
	@JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
	public Date getDis_pro_createtime() {
		return dis_pro_createtime;
	}
	public void setDis_pro_createtime(Date dis_pro_createtime) {
		this.dis_pro_createtime = dis_pro_createtime;
	}
	@JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT,timezone = "GMT+8")
	@DateTimeFormat(pattern=DateUtil.DATE_TIME_FORMAT)
	public Date getDis_pro_endtime() {
		return dis_pro_endtime;
	}
	public void setDis_pro_endtime(Date dis_pro_endtime) {
		this.dis_pro_endtime = dis_pro_endtime;
	}
	public String getDis_pro_status() {
		return dis_pro_status;
	}
	public void setDis_pro_status(String dis_pro_status) {
		this.dis_pro_status = dis_pro_status;
	}
	public String getDis_pro_discounts() {
		return dis_pro_discounts;
	}
	public void setDis_pro_discounts(String dis_pro_discounts) {
		this.dis_pro_discounts = dis_pro_discounts;
	}
	public String getDis_pro_onescale() {
		return dis_pro_onescale;
	}
	public void setDis_pro_onescale(String dis_pro_onescale) {
		this.dis_pro_onescale = dis_pro_onescale;
	}
	public String getDis_pro_twoscale() {
		return dis_pro_twoscale;
	}
	public void setDis_pro_twoscale(String dis_pro_twoscale) {
		this.dis_pro_twoscale = dis_pro_twoscale;
	}
	public String getDis_pro_saleatotal() {
		return dis_pro_saleatotal;
	}
	public void setDis_pro_saleatotal(String dis_pro_saleatotal) {
		this.dis_pro_saleatotal = dis_pro_saleatotal;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	
	
}
