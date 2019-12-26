package com.yunwei.product.common.model;

import java.util.Date;

/**
 * 签到规则
* @ClassName: YwSignInRule
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年07月26日 上午10:37:39
*
 */
public class YwSignInRule {

   private String rule_id; // 签到规则编号
   private String rule_continue_days; // 连续签到天数
   private String rule_type; // 1,积分 ; 2,优惠券
   private String rule_reward_points; // 奖励积分
   private String rule_reward_coupon; // 奖励优惠券编号
   private String rule_status; // 签到规则状态(0,未启用 ; 1,启用)

    public String getRule_id() {
	    return this.rule_id;
	}
	
	public void setRule_id(String rule_id) {
	    this.rule_id = rule_id;
	}
    public String getRule_continue_days() {
	    return this.rule_continue_days;
	}
	
	public void setRule_continue_days(String rule_continue_days) {
	    this.rule_continue_days = rule_continue_days;
	}
    public String getRule_type() {
	    return this.rule_type;
	}
	
	public void setRule_type(String rule_type) {
	    this.rule_type = rule_type;
	}
    
    public String getRule_reward_points() {
		return rule_reward_points;
	}

	public void setRule_reward_points(String rule_reward_points) {
		this.rule_reward_points = rule_reward_points;
	}

	public String getRule_reward_coupon() {
		return rule_reward_coupon;
	}

	public void setRule_reward_coupon(String rule_reward_coupon) {
		this.rule_reward_coupon = rule_reward_coupon;
	}

	public String getRule_status() {
	    return this.rule_status;
	}
	
	public void setRule_status(String rule_status) {
	    this.rule_status = rule_status;
	}
	
}
