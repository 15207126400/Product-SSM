package com.yunwei.product.common.backend.model.form;

import com.yunwei.product.common.model.YwOrder;

/**
 * 订单查询form(针对后台)
* @ClassName: YwOrderForm 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年6月22日 下午3:27:10 
*
 */
public class YwOrderForm extends YwOrder{

	private String begin_datetime;// 开始时间
	private String end_datetime;// 结束时间
	public String getBegin_datetime() {
		return begin_datetime;
	}
	public void setBegin_datetime(String begin_datetime) {
		this.begin_datetime = begin_datetime;
	}
	public String getEnd_datetime() {
		return end_datetime;
	}
	public void setEnd_datetime(String end_datetime) {
		this.end_datetime = end_datetime;
	}
	
	
}
