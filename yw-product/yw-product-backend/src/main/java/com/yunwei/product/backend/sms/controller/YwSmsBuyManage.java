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
import com.yunwei.product.backend.service.YwSmsBuyService;
import com.yunwei.product.common.model.YwSmsBuy;

/**
 * 短信购买(平台)
 * @author zhangz
 *
 */
@Controller
@RequestMapping("/sms/ywSmsBuy")
public class YwSmsBuyManage {
	
	@Autowired
	private YwSmsBuyService ywSmsBuyService;
	@Autowired
	private CacheManager cacheManager;

	@RequestMapping
	public String ywSmsBuyList(Model model){
		System.out.println("跳转短信购买");
		return "/sms/smsbuy/ywSmsBuyList";
	}
	
	/**
	 * 查询字典集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public Map<String, Object> getList(YwUser ywUser,YwSmsBuy ywSmsBuy,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
//		map.put("begin", start);
//		map.put("end", pageSize); // 设置每页显示几条数据
//		map.putAll(MapUtil.toMap(ywSmsBuy));
		List<YwSmsBuy> list = ywSmsBuyService.queryListPage(ywSmsBuy,start,end);
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
	public Paginator getPaginator(YwSmsBuy ywSmsBuy,@RequestParam(defaultValue = "10") int pageSize){
		int count = ywSmsBuyService.queryTotals(ywSmsBuy);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(YwSmsBuy ywSmsBuy,String op_type,Model model){
		model.addAttribute("op_type", op_type);
		if(StringUtils.equals(op_type, "2")){
			model.addAttribute("ywSmsBuy", ywSmsBuyService.query(ywSmsBuy));
		} else {
			model.addAttribute("ywSmsBuy", ywSmsBuy);
		}
		
		return "/sms/smsbuy/ywSmsBuyEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(@Valid YwSmsBuy ywSmsBuy,String op_type){
		Map<String,Object> map = new HashMap<String, Object>();
		if(StringUtils.equals(op_type, "1")){
			ywSmsBuyService.insert(ywSmsBuy);
			map.put("error_no", "0");
			map.put("error_info", "添加成功");
		} else {
			ywSmsBuyService.update(ywSmsBuy);
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
	public Map<String,Object> delete(YwSmsBuy ywSmsBuy){
		Map<String,Object> map = new HashMap<String, Object>();
		ywSmsBuyService.delete(ywSmsBuy);
		map.put("error_no", "0");
		return map;
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping("deleteBatch")
	@ResponseBody
	public Map<String,Object> deleteBatch(YwSmsBuy ywSmsBuy,String buy_ids){
		Map<String,Object> map = new HashMap<String, Object>();
		String[] strings = buy_ids.split(",");
		Map<String, String[]> strMap = new HashMap<String, String[]>();
		strMap.put("buy_id",strings);
		ywSmsBuyService.deleteBatch(strMap);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}	
	
}
