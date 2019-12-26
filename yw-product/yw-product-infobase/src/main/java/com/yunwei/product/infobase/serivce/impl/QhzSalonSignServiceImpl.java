package com.yunwei.product.infobase.serivce.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.common.exception.BizException;
import com.yunwei.common.util.DateUtil;
import com.yunwei.context.message.template.support.WxTemplateMessage;
import com.yunwei.context.payment.util.WxModelUtil;
import com.yunwei.context.sys.usercenter.model.YwUserCustomerXcx;
import com.yunwei.context.sys.usercenter.service.YwUserCustomerXcxService;
import com.yunwei.context.token.support.WxAccessToken;
import com.yunwei.product.common.dao.QhzSalonSignDao;
import com.yunwei.product.common.model.QhzSalonMeeting;
import com.yunwei.product.common.model.QhzSalonSign;
import com.yunwei.product.infobase.service.QhzSalonMeetingService;
import com.yunwei.product.infobase.service.QhzSalonSignService;

@Service
public class QhzSalonSignServiceImpl extends IBaseServiceImpl<QhzSalonSign> implements QhzSalonSignService {
	private static Logger logger = Logger.getLogger(QhzSalonSignServiceImpl.class);
	@Autowired
	private QhzSalonSignDao qhzSalonSignDao;
	@Autowired
	private QhzSalonMeetingService qhzSalonMeetingService;
	@Autowired
	private YwUserCustomerXcxService ywUserCustomerXcxService;
	@Autowired
	private WxAccessToken wxAccessToken;
	@Autowired
	private WxTemplateMessage wxTemplateMessage;
	
	private static final String SGIN_PREFIX = "QHZ";

	@Override
	protected IBaseDao<QhzSalonSign> getBaseDao() {
		return qhzSalonSignDao;
	}

	/**
	 * 签到
	 */
	@Override
	@Transactional
	public QhzSalonSign toSginMeeting(QhzSalonSign qhzSalonSign , String price , String wx_appid , String form_id) {
		//查询该会议编号下的所有签到信息
		QhzSalonMeeting qhzSalonMeeting = qhzSalonMeetingService.query(qhzSalonSign.getMeeting_id());
		//保存入场签到码
		String datetime = DateUtil.format(new Date() , DateUtil.DATE_FORMAT_NO_DELIMITER);
		String strDay = datetime.substring(4, 8);
		if(StringUtils.isBlank(qhzSalonMeeting.getPeople_surplus_num())){
			throw new BizException("【-------已签到人数为空-------】");
		}
		String sgin_code = createCode(strDay, qhzSalonMeeting.getPeople_surplus_num());
		//获取当天已签到人数
		qhzSalonSign.setSgin_code(sgin_code);
		qhzSalonSign.setSgin_time(new Date());
		//如果会议价格为0,修改支付状态为免费
		if(price.equals("0")){
			qhzSalonSign.setPay_status("3");
		} else {
			qhzSalonSign.setPay_status("1");
		}
		
		int num = qhzSalonSignDao.insert(qhzSalonSign);
		
		if(num > 0){
			Long s_num = (long)0;
			//如果设置了会场限制人数,则更新已签到人数
			if(Integer.parseInt(qhzSalonMeeting.getPeople_num()) > 0){
				s_num = Long.parseLong(qhzSalonMeeting.getPeople_surplus_num()) + 1;
				qhzSalonMeeting.setPeople_surplus_num(String.valueOf(s_num));
				qhzSalonMeetingService.update(qhzSalonMeeting);
			}
			toThreadMessage(qhzSalonSign, qhzSalonMeeting , wx_appid , form_id);
		}
		return super.query(qhzSalonSign.getId());
	}

	/**
	 * 异步发送模板消息
	*
	*@param qhzSalonSign
	*@param qhzSalonMeeting
	*@param wx_appid
	*@param form_id
	*void
	 */
	private void toThreadMessage(QhzSalonSign qhzSalonSign , QhzSalonMeeting qhzSalonMeeting , String wx_appid , String form_id) {
		// 异步发送模板消息
		new Thread(){
			public void run() {
				//动态获取access_token
				String access_token = "";
				YwUserCustomerXcx ywUser = new YwUserCustomerXcx();
				if(StringUtils.isNotBlank(wx_appid)){
					ywUser.setApp_id(wx_appid);
					ywUser = ywUserCustomerXcxService.query(ywUser);
					access_token = wxAccessToken.getAccessToken(ywUser.getApp_id(), ywUser.getApp_secret());
				}else{
					access_token = wxAccessToken.getAccessToken(WxModelUtil.APPID, WxModelUtil.SECRET);
				}
				
				//发送模板消息
				JSONObject data = new JSONObject();
				JSONObject keyword1 = new JSONObject();
				keyword1.put("value", WxModelUtil.BODY);	
				data.put("keyword1", keyword1);
				JSONObject keyword2 = new JSONObject();
				keyword2.put("value", qhzSalonMeeting.getName());
				data.put("keyword2", keyword2);
				JSONObject keyword3 = new JSONObject();
				keyword3.put("value", qhzSalonSign.getName());
				data.put("keyword3", keyword3);
				JSONObject keyword4 = new JSONObject();
				keyword4.put("value", qhzSalonSign.getPhone());
				data.put("keyword4", keyword4);
				JSONObject keyword5 = new JSONObject();
				keyword5.put("value", qhzSalonMeeting.getAddress());
				data.put("keyword5", keyword5);
				JSONObject keyword6 = new JSONObject();
				String start_day = DateUtil.format(qhzSalonMeeting.getStart_day() , DateUtil.DATE_FORMAT);
				String end_day = DateUtil.format(qhzSalonMeeting.getEnd_day() , DateUtil.DATE_FORMAT);
				keyword6.put("value", start_day + "~" + end_day);
				data.put("keyword6", keyword6);
				JSONObject keyword7 = new JSONObject();
				keyword7.put("value", qhzSalonSign.getSgin_code());
				data.put("keyword7", keyword7);
				
				wxTemplateMessage.send(access_token, qhzSalonSign.getOpenid(), wxTemplateMessage.sgin_template_id, form_id, data, "keyword7.DATA");
			};
		}.start();
	}
	
	/**
	 * 生成四位数入场签到码
	 */
	public static String createCode(String strDay , String surplusNum){
		String length = "";
		int num = Integer.parseInt(surplusNum) + 1;
		
		if(String.valueOf(num).length() == 1){
			length = "000" + String.valueOf(num);
		} else if(String.valueOf(num).length() == 2){
			length = "00" + String.valueOf(num);
		} else if(String.valueOf(num).length() == 3){
			length = "0" + String.valueOf(num);
		} else {
			length = surplusNum;
		}
		
		return SGIN_PREFIX + strDay + length;
	}	
	

}

