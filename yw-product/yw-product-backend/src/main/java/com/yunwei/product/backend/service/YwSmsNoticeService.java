package com.yunwei.product.backend.service;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.serivce.IBaseSerivce;
import com.yunwei.product.common.model.YwSmsNotice;


/**
 * 云维字典
 * @author zhangjh
 *
 */
public interface YwSmsNoticeService extends IBaseSerivce<YwSmsNotice>{

	/**
	 * 订单发货发送短信
	 * @param user_id(商家user_id)
	 * @param tel(通知对象手机号码)
	 * @param params(短信模板参数)
	 * @return
	 */
	public int orderSendOutSms(String user_id,String tel,String[] params);

}
