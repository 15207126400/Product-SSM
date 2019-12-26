package com.yunwei.product.infobase.salon.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.yunwei.common.exception.BizException;
import com.yunwei.common.util.DateUtil;
import com.yunwei.common.util.RedisClientUtil;
import com.yunwei.context.message.template.support.WxTemplateMessage;
import com.yunwei.context.payment.util.WxModelUtil;
import com.yunwei.context.sys.usercenter.model.YwUserCustomerXcx;
import com.yunwei.context.sys.usercenter.service.YwUserCustomerXcxService;
import com.yunwei.context.token.support.WxAccessToken;
import com.yunwei.product.common.model.QhzSalonMeeting;
import com.yunwei.product.common.model.QhzSalonSign;
import com.yunwei.product.infobase.constant.ConstantFunctionsFront;
import com.yunwei.product.infobase.service.QhzSalonMeetingService;
import com.yunwei.product.infobase.service.QhzSalonSignService;

/**
 * 沙龙注册会议信息控制层
* @ClassName: QhzSalonMeetingController 
* @Description: TODO(TODO) 
* @author 晏飞
* @date 2019年7月18日 上午10:05:00 
*
 */
@Controller
public class QhzSalonMeetingController {
	@Autowired
	private QhzSalonMeetingService qhzSalonMeetingService;
	@Autowired
	private QhzSalonSignService qhzSalonSignService;
	
	
	private Lock lock = new ReentrantLock();					//同步锁,确保修改剩余人数时数据准确性
	
	
	/**
	 * 会议信息列表查询(只查询启用状态为1,且当前时间在开始时间和结束时间之间的会议信息)
	*
	*@return
	*Map<String,Object>
	 */
	@RequestMapping(ConstantFunctionsFront.QHZ_SL031)
	@ResponseBody
	public List<QhzSalonMeeting> queryMeetingByToday(String openid , String name){
		List<QhzSalonMeeting> list = new ArrayList<QhzSalonMeeting>();				//符合时间段内的会议列表信息
		List<QhzSalonMeeting> resultList = new ArrayList<QhzSalonMeeting>();		//处理签到状态后的会议列表信息
		try {
			//查询所有启用的会议信息
			QhzSalonMeeting qhzSalonMeeting = new QhzSalonMeeting();
			qhzSalonMeeting.setStatus("1");
			if(StringUtils.isNotBlank(name)){
				qhzSalonMeeting.setName(name);
			}
			List<QhzSalonMeeting> meetingList = qhzSalonMeetingService.queryList(qhzSalonMeeting);
			for(QhzSalonMeeting meeting : meetingList){
				//判断当前时间是否在开始时间和结束时间之间
				boolean bool = DateUtil.isDayIn(new Date(), meeting.getStart_day(), meeting.getEnd_day());
				if(bool){
					list.add(meeting);
				}
			}
			
			// 如果签到集合有数据,说明有人进行了签到
			if(list.size() > 0){
				for(int i=0; i<list.size(); i++){
					QhzSalonMeeting qsm = list.get(i);
					//标识已签到的会议
					QhzSalonSign qhzSalonSign = new QhzSalonSign();
					qhzSalonSign.setOpenid(openid);
					qhzSalonSign.setMeeting_id(qsm.getId());
					QhzSalonSign sign = qhzSalonSignService.query(qhzSalonSign); 
					if(null != sign){
						qsm.setCode("1");
					} else {
						qsm.setCode("2");
					}
					resultList.add(qsm);
				} 
			} 
			return resultList;
		} catch (Exception e) {
			throw new BizException("【会议信息列表查询】 : " + e);
		}
	}

	
	/**
	 * 签到
	*
	*@param address
	*@return
	*Map<String,Object>
	 */
	@RequestMapping(ConstantFunctionsFront.QHZ_SL032)
	@ResponseBody
	public Map<String, Object> sginMeeting(QhzSalonSign qhzSalonSign , String wx_appid , String form_id , String price){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			//同步锁
			lock.lock();
			//判断是否有票务编号,不允许票务编号有重复
			if(StringUtils.isNotBlank(qhzSalonSign.getTicket_number())){
				QhzSalonSign sign = new QhzSalonSign();
				sign.setTicket_number(qhzSalonSign.getTicket_number());
				sign = qhzSalonSignService.query(sign);
				if(null != sign){
					//表示该票务编号已经签到过,无法再签到
					map.put("code", -1);
					return map;
				}
			}
			
			//判断是否获取到用户信息,若没有获取到返回提示
			if(StringUtils.isBlank(qhzSalonSign.getName())){
				map.put("code", -2);
				return map;
			}
			
			//签到
			qhzSalonSign = qhzSalonSignService.toSginMeeting(qhzSalonSign , price , wx_appid , form_id);

			//返回数据
			return queryAdmissionCode(qhzSalonSign);
		} catch (Exception e) {
			throw new BizException("【签到】 : " + e);
		} finally {
			//释放锁
			lock.unlock();
		}
	}

	
	/**
	 * 查看入场码信息
	*
	*@param address
	*@return
	*Map<String,Object>
	 */
	@RequestMapping(ConstantFunctionsFront.QHZ_SL033)
	@ResponseBody
	public Map<String, Object> queryAdmissionCode(QhzSalonSign qhzSalonSign){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("qhzSalonSign", qhzSalonSignService.query(qhzSalonSign));
		map.put("qhzSalonMeeting", qhzSalonMeetingService.query(qhzSalonSign.getMeeting_id()));
		
		return map;
	}
	
}
