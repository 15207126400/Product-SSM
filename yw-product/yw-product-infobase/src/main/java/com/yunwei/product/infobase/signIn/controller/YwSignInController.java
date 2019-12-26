package com.yunwei.product.infobase.signIn.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunwei.common.util.DateUtil;
import com.yunwei.product.common.backend.model.dto.YwCouponDto;
import com.yunwei.product.common.model.YwCoupon;
import com.yunwei.product.common.model.YwCouponCollectiondetails;
import com.yunwei.product.common.model.YwPoints;
import com.yunwei.product.common.model.YwPointsDetail;
import com.yunwei.product.common.model.YwSignIn;
import com.yunwei.product.common.model.YwSignInRule;
import com.yunwei.product.infobase.constant.ConstantFunctionsFront;
import com.yunwei.product.infobase.service.YwCouponCollectiondetailsService;
import com.yunwei.product.infobase.service.YwCouponService;
import com.yunwei.product.infobase.service.YwPointsDetailService;
import com.yunwei.product.infobase.service.YwPointsService;
import com.yunwei.product.infobase.service.YwSignInRuleService;
import com.yunwei.product.infobase.service.YwSignInService;

/**
 * 
* @ClassName: YwSignInController 
* @Description: TODO(签到) 
* @author 晏飞
* @date 2018年8月9日 下午3:26:17 
*
 */
@Controller
public class YwSignInController {
	@Autowired
	private YwSignInService ywSignInService;
	@Autowired
	private YwSignInRuleService ywSignInRuleService;
	@Autowired
	private YwPointsService ywPointsService;
	@Autowired
	private YwCouponCollectiondetailsService ywCouponCollectiondetailsService;
	@Autowired
	private YwCouponService ywCouponService;
	@Autowired
	private YwPointsDetailService ywPointsDetailService;
	
	/**
	 * 查询用户签到状态
	*
	*@param ywSignIn
	*@return
	*Map<String,Object>
	 */
	@RequestMapping(ConstantFunctionsFront.YW_SIGN_00001)
	@ResponseBody
	public Map<String, Object> querySignInStatus(YwSignIn ywSignIn){
		Map<String, Object> map = new HashMap<String, Object>();
		YwSignIn signIn = ywSignInService.query(ywSignIn);
		if(signIn != null){
			if(signIn.getSignIn_last_date() != null){
				Date today = DateUtil.getNowDateShort(new Date());
				// 获取两个日期之间间隔的天数
				int date = DateUtil.getIntervalDay(signIn.getSignIn_last_date(), today);
				
				if(date < 1){																	// 上次签到日期为当天 , 说明今天已签到
					signIn.setSignIn_status("1");
					ywSignInService.update(signIn);
				}else if(date == 1 && StringUtils.equals(signIn.getSignIn_status(), "1")){	// 上次签到日期为昨天 , 且昨天成功签到 , 不清除连续签到天数
					signIn.setSignIn_status("0");
					ywSignInService.update(signIn);
				}else{																			// 否则超过一天未签到 , 清除连续签到天数
					signIn.setSignIn_continue_day("0");
					signIn.setSignIn_status("0");
					ywSignInService.update(signIn);
				}
			}else{
				signIn.setSignIn_continue_day("0");
				signIn.setSignIn_status("0");
				ywSignInService.update(signIn);
			}
			// 连续签到日期延后一天 , 查询明日奖励
			YwSignInRule last_SignInRule = new YwSignInRule();
			String signIn_continue_day = signIn.getSignIn_continue_day();
			int last_one_day = Integer.valueOf(signIn_continue_day) + 1;
			if(StringUtils.equals(signIn_continue_day, "7")){
				last_SignInRule.setRule_continue_days("1");
				last_SignInRule = ywSignInRuleService.query(last_SignInRule);
				if(last_SignInRule != null){
					map.put("last_SignInRule", last_SignInRule);
				}
				map.put("ywSignIn", signIn);
			}else{
				last_SignInRule.setRule_continue_days(String.valueOf(last_one_day));
				last_SignInRule = ywSignInRuleService.query(last_SignInRule);
				if(last_SignInRule != null){
					map.put("last_SignInRule", last_SignInRule);
				}
				map.put("ywSignIn", signIn);
			}
		}else{
			YwSignIn newSignIn = new YwSignIn();
			newSignIn.setUser_id(ywSignIn.getUser_id());
			newSignIn.setSignIn_continue_day("0");
			newSignIn.setSignIn_status("0");
			ywSignInService.insert(newSignIn);
			// 连续签到日期延后一天 , 查询明日奖励
			YwSignInRule last_SignInRule = new YwSignInRule();
			String signIn_continue_day = newSignIn.getSignIn_continue_day();
			int last_one_day = Integer.valueOf(signIn_continue_day) + 1;
			last_SignInRule.setRule_continue_days(String.valueOf(last_one_day));
			last_SignInRule = ywSignInRuleService.query(last_SignInRule);
			if(last_SignInRule != null){
				map.put("last_SignInRule", last_SignInRule);
			}
			map.put("ywSignIn", newSignIn);
			
			return map;
		}
		return map;
	}

	/**
	 * 用户签到
	*
	*@param ywSignIn
	*@return
	*Map<String,Object>
	 */
	@RequestMapping(ConstantFunctionsFront.YW_SIGN_00002)
	@ResponseBody
	public Map<String, Object> saveSignInStatus(YwSignIn ywSignIn){
		Map<String, Object> map = new HashMap<String, Object>();
		ywSignIn = ywSignInService.query(ywSignIn);
		// 连续签到天数
		int signIn_continue_day = Integer.valueOf(ywSignIn.getSignIn_continue_day())+1;
		
		ywSignIn.setSignIn_last_date(new Date());
		if(StringUtils.equals(ywSignIn.getSignIn_continue_day(), "7")){
			ywSignIn.setSignIn_continue_day("1");
		}else{
			ywSignIn.setSignIn_continue_day(String.valueOf(signIn_continue_day));
		}
		ywSignIn.setSignIn_status("1");
		ywSignIn.setSignIn_updatetime(new Date());
		ywSignInService.update(ywSignIn);
		
		// 获取签到规则表数据  , 匹配签到天数所对应的奖励 , 并发放至账号
		YwSignInRule ywSignInRule = new YwSignInRule();
		ywSignInRule.setRule_continue_days(ywSignIn.getSignIn_continue_day());
		ywSignInRule = ywSignInRuleService.query(ywSignInRule);
		
		if(ywSignInRule != null){
			// 判断奖励类型
			if(StringUtils.equals(ywSignInRule.getRule_type(), "1")){
				// 如果奖励是积分类型 , 获取该用户积分表 , 录入对应的奖励积分
				ywPointsService.addPointsByUser(ywSignInRule.getRule_reward_points(), ywSignIn.getUser_id());
				// 同时积分明细表生成一条记录
				ywPointsDetailService.addPointsDetail("3", ywSignIn.getUser_id(), "+" + ywSignInRule.getRule_reward_points(), "");
				map.put("receive_points", ywSignInRule.getRule_reward_points());	// 返回获赠积分
			}else if(StringUtils.equals(ywSignInRule.getRule_type(), "2")){
				// 如果奖励是优惠券类型 , 获取该用户优惠券领取表 , 插入对应的优惠券
				ywCouponCollectiondetailsService.couponCollection(ywSignInRule.getRule_reward_coupon(), ywSignIn.getUser_id());
				YwCoupon ywCoupon = ywCouponService.query(Integer.valueOf(ywSignInRule.getRule_reward_coupon()));
				//YwCouponDto ywCoupon = ywCouponService.queryById(Integer.valueOf(ywSignInRule.getRule_reward_coupon()));
				map.put("ywCoupon", ywCoupon);										// 返回获赠优惠券
			}
		}
		map.put("ywSignIn", ywSignIn);							// 返回签到信息
		map.put("rule_type", ywSignInRule.getRule_type());		// 获赠类型
			
		return map;
	}

	
}
