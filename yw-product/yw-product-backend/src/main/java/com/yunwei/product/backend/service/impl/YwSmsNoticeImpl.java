package com.yunwei.product.backend.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.context.sms.support.WxSmsService;
import com.yunwei.context.sys.model.YwSystemNotice;
import com.yunwei.context.sys.service.YwSystemNoticeService;
import com.yunwei.product.backend.service.YwSmsBuyService;
import com.yunwei.product.backend.service.YwSmsNoticeService;
import com.yunwei.product.common.dao.YwSmsNoticeDao;
import com.yunwei.product.common.model.YwSmsBuy;
import com.yunwei.product.common.model.YwSmsNotice;

/**
 * 短信通知消息记录
* @ClassName: YwSmsNoticeImpl 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年5月23日 下午3:00:52 
*
 */
@Service
public class YwSmsNoticeImpl extends IBaseServiceImpl<YwSmsNotice> implements YwSmsNoticeService {
	
	private final Logger logger = LoggerFactory.getLogger(YwSmsNoticeImpl.class);
	
	private final static String tpl_id = "125457";// 订单发货短信模板
	
     @Autowired
     private YwSmsNoticeDao ywSmsNoticeDao;
     @Autowired
     private WxSmsService wxSmsSupport;
     @Autowired
     private YwSmsBuyService ywSmsBuyService;
     @Autowired
     private YwSystemNoticeService ywSystemNoticeService;

	@Override
	protected IBaseDao<YwSmsNotice> getBaseDao() {
		return ywSmsNoticeDao;
	}

	@Override
	public int orderSendOutSms(String user_id,String tel, String[] params) {
		// 判断商家是否购买短信套餐
		YwSmsBuy ywSmsBuy = new YwSmsBuy();
		ywSmsBuy.setUser_id(user_id);
		YwSmsBuy smsBuy = ywSmsBuyService.query(ywSmsBuy);
		// 如果有数据 , 说明商家购买了短信套餐
		if(smsBuy != null && StringUtils.equals(smsBuy.getBuy_status(), "1")){
			int surplusNum = Integer.valueOf(smsBuy.getBuy_number()) - Integer.valueOf(smsBuy.getBuy_usenumber());
			// 如果套餐剩余短信条数不为0 , 则可以发送短信
			if(surplusNum > 0){
				// 发送短信后返回的map
				Map<String, Object> map = wxSmsSupport.sendSingle(tpl_id, params, tel);
				
				// 成功发送短信后更新短信记录表数据
				YwSmsNotice ywSmsNotice = new YwSmsNotice();
				ywSmsNotice.setUser_id(user_id);
				ywSmsNotice.setSms_mobile(tel);
				ywSmsNotice.setSms_createtime(new Date());
				ywSmsNotice.setSms_content("您好，您的订单:" + params[0] + "已发货，请尽快收货，谢谢~~");
				ywSmsNotice.setSms_template_id("125457");
				ywSmsNotice.setSms_isfee(map.get("fee").toString());
				ywSmsNotice.setSms_type("1");
				ywSmsNotice.setSms_error_no(map.get("result").toString());
				ywSmsNotice.setSms_error_info(map.get("errMsg").toString());
				// 通过返回的map判断是否成功发送短信
				if(StringUtils.equals(map.get("result").toString(), "0")){
					// 发送短信成功 , 插入短信消费记录
					ywSmsNotice.setSms_status("1");
					ywSmsNotice.setSms_remark("短信发送成功");
					this.insert(ywSmsNotice);
					// 短信发送成功后 , 更改短信购买表中的数据
					int newNumber = Integer.valueOf(smsBuy.getBuy_usenumber()) + 1;
					smsBuy.setBuy_usenumber(String.valueOf(newNumber));
					smsBuy.setBuy_updatetime(new Date());
					ywSmsBuyService.update(smsBuy);
				}else{
					// 发送短信失败 , 插入短信消费记录
					ywSmsNotice.setSms_status("2");
					ywSmsNotice.setSms_remark("短信发送失败");
					this.insert(ywSmsNotice);
				}
			}else{
				logger.info("用户[{}]短信已使用完",user_id);
				// 插入系统通知形成公告
				YwSystemNotice ywSystemNotice = new YwSystemNotice();
				ywSystemNotice.setUser_id(user_id);
				ywSystemNotice.setNotice_type("2");// 短信未购买
				YwSystemNotice notice = ywSystemNoticeService.query(ywSystemNotice);
				if(notice == null){
					ywSystemNotice.setNotice_content("您的短信套餐已使用完，请及时购买短信套餐，以免影响业务操作");
					ywSystemNotice.setNotice_create_time(new Date());
					ywSystemNoticeService.insert(ywSystemNotice);
				}
			}
		}else{
			logger.info("用户[{}]未购买短信套餐",user_id);
			// 插入系统通知形成公告
			YwSystemNotice ywSystemNotice = new YwSystemNotice();
			ywSystemNotice.setUser_id(user_id);
			ywSystemNotice.setNotice_type("1");// 短信未购买
			YwSystemNotice notice = ywSystemNoticeService.query(ywSystemNotice);
			if(notice == null){
				ywSystemNotice.setNotice_content("您还未购买短信套餐，请购买短信套餐，以免影响业务操作");
				ywSystemNotice.setNotice_create_time(new Date());
				ywSystemNoticeService.insert(ywSystemNotice);
			}
			 
			
		}
		return 0;
	}
	
}
