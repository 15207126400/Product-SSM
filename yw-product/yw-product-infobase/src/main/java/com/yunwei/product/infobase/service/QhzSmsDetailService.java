package com.yunwei.product.infobase.service;

import com.yunwei.common.base.serivce.IBaseSerivce;
import com.yunwei.product.common.model.QhzSmsConfig;
import com.yunwei.product.common.model.QhzSmsDetail;

/**
 * 沙龙注册短信发送信息service
* @ClassName: QhzSmsDetailService 
* @Description: TODO(TODO) 
* @author yanf
* @date 2019年07月04日 上午11:38:45
*
 */
public interface QhzSmsDetailService extends IBaseSerivce<QhzSmsDetail>{
	
	/**
	 * 发送短信
	*
	*@param qhzSmsDetail		短信内容
	*@return
	*String						返回提示信息
	 */
	public String sendSms(QhzSmsDetail qhzSmsDetail);
}
