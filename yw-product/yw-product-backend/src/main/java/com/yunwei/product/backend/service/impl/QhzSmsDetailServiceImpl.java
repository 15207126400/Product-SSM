package com.yunwei.product.backend.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.DocumentHelper;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.common.exception.BizException;
import com.yunwei.common.util.DateUtil;
import com.yunwei.common.util.HttpRequestUtil;
import com.yunwei.product.backend.service.QhzSmsConfigService;
import com.yunwei.product.backend.service.QhzSmsDetailService;
import com.yunwei.product.common.dao.QhzSmsConfigDao;
import com.yunwei.product.common.dao.QhzSmsDetailDao;
import com.yunwei.product.common.model.QhzSmsConfig;
import com.yunwei.product.common.model.QhzSmsDetail;


@Service
public class QhzSmsDetailServiceImpl extends IBaseServiceImpl<QhzSmsDetail> implements QhzSmsDetailService{
	
    private static Logger logger = Logger.getLogger(QhzSmsDetailServiceImpl.class);
	@Autowired
	private QhzSmsDetailDao qhzSmsDetailDao;
	@Autowired
	private QhzSmsConfigService qhzSmsConfigService;

	@Override
	protected IBaseDao<QhzSmsDetail> getBaseDao() {
		return qhzSmsDetailDao;
	}
	
	@Override
	protected String getPrimaryKey() {
	
		return "id";
	}

	@Override
	public String sendSms(QhzSmsDetail qhzSmsDetail) {
		Map<String, String> map = new HashMap<String, String>();
		String root = "";
		try {
			//获取短信系统配置信息
			QhzSmsConfig qhzSmsConfig = new QhzSmsConfig();
			qhzSmsConfig.setType(qhzSmsDetail.getType());
			qhzSmsConfig = qhzSmsConfigService.query(qhzSmsConfig);		//通过通道编号查询指定短信通道系统信息
			if(qhzSmsConfig != null){
				//发送短信请求路径
				String sendUrl = qhzSmsConfig.getUrl();
				
				//拼接短信账户信息部分
				map.put("action", qhzSmsConfig.getAction());
				map.put("userid", qhzSmsConfig.getUserid());
				map.put("account", qhzSmsConfig.getAccount());
				map.put("password", qhzSmsConfig.getPassword());
				//拼接短信发送信息部分
				map.put("mobile", qhzSmsDetail.getMobile());
				map.put("content", qhzSmsDetail.getContent() + qhzSmsConfig.getSign());
				String sendTime = DateUtil.format(qhzSmsDetail.getSendTime(), DateUtil.DATE_TIME_FORMAT);
				if(StringUtils.isNotBlank(sendTime)){
					map.put("sendTime", sendTime);
					map.put("&extno=", "");
				} else {
					map.put("&sendTime=&extno=", "");
				}
				
				//返回对应的xml字符串
				String result = HttpRequestUtil.doPost(sendUrl, map);
				//xml解析并返回指定标签对应的值,返回短信发送状态
				org.dom4j.Document document = DocumentHelper.parseText(result);
				org.dom4j.Element rootel = document.getRootElement();
				root = rootel.elementText("message");
			}
			
			return root;
		} catch (Exception e) {
			throw new BizException("【短信发送】 : " + e);
		}
	}
	
	
	
	
}
