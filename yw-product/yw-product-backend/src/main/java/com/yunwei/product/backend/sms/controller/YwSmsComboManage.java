package com.yunwei.product.backend.sms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunwei.common.cache.CacheManager;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.context.sys.usercenter.model.YwUser;
import com.yunwei.product.backend.service.YwSmsComboService;
import com.yunwei.product.common.model.YwSmsCombo;

/**
 * 短信套餐
 * @author zhangz
 *
 */
@Controller
@RequestMapping("/sms/ywSmsCombo")
public class YwSmsComboManage {
	
	@Autowired
	private YwSmsComboService ywSmsComboService;
	@Autowired
	private CacheManager cacheManager;

	@RequestMapping
	public String ywSmsComboList(Model model){
		return "/sms/smscombo/ywSmsComboList";
	}
	
	/**
	 * 查询字典集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public Map<String, Object> getList(YwUser ywUser,YwSmsCombo ywSmsCombo,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
//		map.put("begin", start);
//		map.put("end", pageSize); // 设置每页显示几条数据
//		map.putAll(MapUtil.toMap(ywSmsCombo));
		List<YwSmsCombo> list = ywSmsComboService.queryListPage(ywSmsCombo,start,end);
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
	public Paginator getPaginator(YwSmsCombo ywSmsCombo,@RequestParam(defaultValue = "10") int pageSize){
		int count = ywSmsComboService.queryTotals(ywSmsCombo);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(YwSmsCombo ywSmsCombo,String op_type,Model model){
		model.addAttribute("op_type", op_type);
		if(StringUtils.equals(op_type, "2")){
			model.addAttribute("ywSmsCombo", ywSmsComboService.query(ywSmsCombo));
		} else {
			model.addAttribute("ywSmsCombo", ywSmsCombo);
		}
		
		return "/sms/smscombo/ywSmsComboEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(@Valid YwSmsCombo ywSmsCombo,String op_type){
		Map<String,Object> map = new HashMap<String, Object>();
		if(StringUtils.equals(op_type, "1")){
			ywSmsComboService.insert(ywSmsCombo);
			map.put("error_no", "0");
			map.put("error_info", "添加成功");
		} else {
			ywSmsComboService.update(ywSmsCombo);
			map.put("error_no", "0");
			map.put("error_info", "修改成功");
		}
		return map;
	}
	
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Map<String,Object> delete(YwSmsCombo ywSmsCombo){
		Map<String,Object> map = new HashMap<String, Object>();
		ywSmsComboService.delete(ywSmsCombo);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping("deleteBatch")
	@ResponseBody
	public Map<String,Object> deleteBatch(YwSmsCombo ywSmsCombo,String combo_ids){
		Map<String,Object> map = new HashMap<String, Object>();
		String[] strings = combo_ids.split(",");
		Map<String, String[]> strMap = new HashMap<String, String[]>();
		strMap.put("combo_id",strings);
		ywSmsComboService.deleteBatch(strMap);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}
}
