package com.yunwei.product.backend.signIn.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunwei.product.backend.service.YwCouponService;
import com.yunwei.product.backend.service.YwSignInRuleService;
import com.yunwei.product.common.model.YwSignInRule;
import com.yunwei.common.exception.BizException;
import com.yunwei.common.user.SysUser;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;

/**
 * 签到规则控制层
* @ClassName: YwSignInRuleManage 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年07月26日 上午10:37:39
*
 */
@Controller
@RequestMapping("/system/ywSignInRule")
public class YwSignInRuleManage {
	private static Logger logger = Logger.getLogger(YwSignInRuleManage.class);
	@Autowired
	private YwSignInRuleService ywSignInRuleService;
	@Autowired
	private YwCouponService ywCouponService;
 
	@RequestMapping
	public String ywSignInRuleList(Model model){

		return "/signin/signinrule/ywSignInRuleList";
	}
	
	/**
	 * 查询字典集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public List<Map<String, Object>> getList(SysUser sysUser,YwSignInRule ywSignInRule,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
//		map.put("begin", start);
//		map.put("end", pageSize); // 设置每页显示几条数据
//		map.putAll(MapUtil.toMap(ywSignInRule));
		List<YwSignInRule> list = ywSignInRuleService.queryListPage(ywSignInRule,start,end);
		return MapUtil.toMapList(list);
	}
	
	/**
	 * 查询分页情况
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("getPaginator")
	@ResponseBody
	public Paginator getPaginator(YwSignInRule ywSignInRule,@RequestParam(defaultValue = "10") int pageSize){
		int count = ywSignInRuleService.queryTotals(ywSignInRule);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(SysUser sysUser,YwSignInRule ywSignInRule,String op_type,Model model){
		// 查询优惠券
		model.addAttribute("coupons", ywCouponService.queryList());
		
		model.addAttribute("op_type", op_type);
		if(StringUtils.equals(op_type, "2")){
			model.addAttribute("ywSignInRule", ywSignInRuleService.query(ywSignInRule));
		} else {
			model.addAttribute("ywSignInRule", ywSignInRule);
		}
		
		return "/signin/signinrule/ywSignInRuleEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(YwSignInRule ywSignInRule,String op_type){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			if(StringUtils.equals(op_type, "1")){
				ywSignInRuleService.insert(ywSignInRule);
				map.put("error_no", "0");
				map.put("error_info", "添加成功");
			} else {
				if(StringUtils.equals("1", ywSignInRule.getRule_type())){
					ywSignInRule.setRule_reward_coupon("null");
				}else{
					ywSignInRule.setRule_reward_points("null");
				}
				ywSignInRuleService.update(ywSignInRule);
				map.put("error_no", "0");
				map.put("error_info", "修改成功");
			}
		} catch (Exception e) {
			throw new BizException(e);
		}
		return map;
	}
	
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Map<String,Object> delete(YwSignInRule ywSignInRule){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			ywSignInRuleService.delete(ywSignInRule);
			map.put("error_no", "0");
			map.put("error_info", "删除成功");
		} catch (Exception e) {
			throw new BizException(e);
		}
		return map;
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping("deleteBatch")
	@ResponseBody
	public Map<String,Object> deleteBatch(YwSignInRule ywSignInRule){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			String[] rule_id = ywSignInRule.getRule_id().split(",");
			Map<String, String[]> strMap = new HashMap<String, String[]>();
			strMap.put("rule_id",rule_id);
			ywSignInRuleService.deleteBatch(strMap);
			map.put("error_no", "0");
			map.put("error_info", "删除成功");
		} catch (Exception e) {
			throw new BizException(e);
		}
		return map;
	}	
}
