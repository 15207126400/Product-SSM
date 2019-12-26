package com.yunwei.product.common.infobase.model.form;

/**
 * 秒杀记录信息form
* @ClassName: YwSeckillRecordForm 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年7月6日 上午10:39:10 
*
 */
public class YwSeckillRecordForm {

	private String record_id;				// 秒杀记录编号
	private String order_sn;				// 订单编号
	private String record_status;			// 秒杀记录状态
	public String getRecord_id() {
		return record_id;
	}
	public void setRecord_id(String record_id) {
		this.record_id = record_id;
	}
	public String getOrder_sn() {
		return order_sn;
	}
	public void setOrder_sn(String order_sn) {
		this.order_sn = order_sn;
	}
	public String getRecord_status() {
		return record_status;
	}
	public void setRecord_status(String record_status) {
		this.record_status = record_status;
	}
	
	
}
