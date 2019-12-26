package com.yunwei.context.sms;

import java.util.Map;

/***
 * 短信统计base基本接口
* @ClassName: BaseSmsStatistics 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年6月4日 下午4:06:28 
*
 */
public interface BaseSmsStatistics {
	
	/**
	 * 发送数据统计
	 * @param begin_date
	 * @param end_date
	 * @return
	 */
	public Map<String,Object> sendStatistics(String begin_date,String end_date);
	
	/**
	 * 回执数据统计
	 * @param begin_date
	 * @param end_date
	 * @return
	 */
	public Map<String,Object> callbackStatistics(String begin_date,String end_date);

}
