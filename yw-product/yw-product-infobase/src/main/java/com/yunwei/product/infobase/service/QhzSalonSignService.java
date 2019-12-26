package com.yunwei.product.infobase.service;

import java.util.Map;

import com.yunwei.common.base.serivce.IBaseSerivce;
import com.yunwei.product.common.model.QhzSalonSign;

/**
 * 沙龙注册签到信息模块serivce
* @ClassName: QhzSalonSignInfobaseService 
* @Description: TODO(TODO) 
* @author yanf
* @date 2019年07月18日 上午09:21:30
*
 */
public interface QhzSalonSignService extends IBaseSerivce<QhzSalonSign>{
	
	/**
	 * 签到
	*
	*@param qhzSalonSign
	*@param price
	*@param wx_appid
	*@param form_id
	*@return
	*QhzSalonSign
	 */
	public QhzSalonSign toSginMeeting(QhzSalonSign qhzSalonSign , String price , String wx_appid , String form_id);
}

