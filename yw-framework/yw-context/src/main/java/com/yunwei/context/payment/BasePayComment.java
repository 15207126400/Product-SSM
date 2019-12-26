package com.yunwei.context.payment;

import java.util.Map;

/**
 * 业务相关的base支付订单评价接口
* @ClassName: BaseBusinessPaymentComment 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年5月31日 下午2:37:17 
*
 */
public interface BasePayComment {

	/**
	 * 支付订单评价查询
	 * @param begin_time(begin_time)[按用户评论时间批量拉取的起始时间，格式为yyyyMMddHHmmss]
	 * @param end_time(end_time)[按用户评论时间批量拉取的结束时间，格式为yyyyMMddHHmmss]
	 * @param offset(位移)[指定从某条记录的下一条开始返回记录。接口调用成功时，会返回本次查询最后一条数据的offset。商户需要翻页时，应该把本次调用返回的offset 作为下次调用的入参。注意offset是评论数据在微信支付后台保存的索引，未必是连续的]
	 * @return
	 */
	public Map<String,Object> batchQueryComment(String begin_time,String end_time,String offset);
	
	/**
	 * 支付订单评价查询
	 * @param begin_time(begin_time)[按用户评论时间批量拉取的起始时间，格式为yyyyMMddHHmmss]
	 * @param end_time(end_time)[按用户评论时间批量拉取的结束时间，格式为yyyyMMddHHmmss]
	 * @param offset(位移)[指定从某条记录的下一条开始返回记录。接口调用成功时，会返回本次查询最后一条数据的offset。商户需要翻页时，应该把本次调用返回的offset 作为下次调用的入参。注意offset是评论数据在微信支付后台保存的索引，未必是连续的]
	 * @param limit(一次拉取的条数, 最大值是200，默认是200)
	 * @return
	 */
	public Map<String,Object> batchQueryComment(String begin_time,String end_time,String offset,String limit);
}
