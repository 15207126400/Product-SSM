package com.yunwei.product.backend.sales.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.product.backend.service.YwDistributorRuleService;
import com.yunwei.product.common.model.YwDistributorRule;

/**
 * 商家设置分销规则
 * @author zhangz
 *
 */
@Controller
@RequestMapping("/sales/ywDistributorRule")
public class YwDistributorRuleManage {
	
	@Autowired
	private YwDistributorRuleService ywDistributorRuleService;

	@RequestMapping
	public String ywDistributorRuleList(YwDistributorRule ywDistributorRule,String op_type,Model model){
		
		return "/sales/distributorrule/ywDistributorRuleList";
	}
	
	/**
	 * 查询字典集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public Map<String, Object> getList(YwDistributorRule ywDistributorRule,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
		map.putAll(MapUtil.toMap(ywDistributorRule));
		List<YwDistributorRule> list = ywDistributorRuleService.queryPage(map);
		map.put("error_no", "0");
		map.put("error_info", "查询成功");
		map.put("resultList", list);
		return map;
	}
	
	/**
	 * 查询分页情况
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("getPaginator")
	@ResponseBody
	public Paginator getPaginator(YwDistributorRule ywDistributorRule,@RequestParam(defaultValue = "10") int pageSize){
		int count = ywDistributorRuleService.queryTotals(ywDistributorRule);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(YwDistributorRule ywDistributorRule,String op_type,Model model){
		model.addAttribute("op_type", op_type);
		if(StringUtils.equals(op_type, "2")){
			ywDistributorRuleService.update(ywDistributorRule);
			model.addAttribute("ywDistributorRule", ywDistributorRuleService.query(ywDistributorRule));
		} else {
			model.addAttribute("ywDistributorRule", ywDistributorRule);
		}
		
		return "/sales/distributorrule/ywDistributorRuleEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(YwDistributorRule ywDistributorRule,String op_type){
		Map<String,Object> map = new HashMap<String, Object>();
		if(StringUtils.isBlank(ywDistributorRule.getRule_id())){
			ywDistributorRuleService.insert(ywDistributorRule);
			map.put("error_no", "0");
			map.put("error_info", "保存成功");
		} else {
			ywDistributorRuleService.update(ywDistributorRule);
			map.put("error_no", "0");
			map.put("error_info", "保存成功");
		}
		return map;
	}
	
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Map<String,Object> delete(YwDistributorRule ywDistributorRule){
		Map<String,Object> map = new HashMap<String, Object>();
		ywDistributorRuleService.delete(ywDistributorRule);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping("deleteBatch")
	@ResponseBody
	public Map<String,Object> deleteBatch(YwDistributorRule ywDistributorRule,String rule_ids){
		Map<String,Object> map = new HashMap<String, Object>();
		String[] strings = rule_ids.split(",");
		Map<String, String[]> strMap = new HashMap<String, String[]>();
		strMap.put("rule_id",strings);
		ywDistributorRuleService.deleteBatch(strMap);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}	
}
