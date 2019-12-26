package com.yunwei.context.config.sys.controller;

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

import com.yunwei.common.cache.CacheManager;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.context.jdbc.datasource.DBContextHolder;
import com.yunwei.context.jdbc.model.YwDbconfig;
import com.yunwei.context.jdbc.service.YwDbconfigService;
import com.yunwei.context.sys.usercenter.model.YwUser;
import com.yunwei.context.sys.usercenter.model.YwUserCustomer;
import com.yunwei.context.sys.usercenter.service.YwUserCustomerService;
import com.yunwei.context.sys.usercenter.service.YwUserService;

/**
 * 系统数据库配置控制器
 * @author zhangz
 *
 */
@Controller
@RequestMapping("/system/ywDbconfig")
public class YwDbconfigManagerController {
	
	@Autowired
	private YwDbconfigService ywDbconfigService;
	@Autowired
	private YwUserService ywUserService;
	@Autowired
	private YwUserCustomerService ywUserCustomerService;
	@Autowired
	private CacheManager cacheManager;

	@RequestMapping
	public String ywDbconfigList(Model model){
		return "/system/dbconfig/ywDbconfigList";
	}
	
	/**
	 * 查询字典集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public Map<String, Object> getList(YwDbconfig ywDbconfig,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
		map.putAll(MapUtil.toMap(ywDbconfig));
		List<YwDbconfig> list = ywDbconfigService.queryPage(map);
//		map.put("error_no", "0");
//		map.put("error_info", "查询成功");
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
	public Paginator getPaginator(YwDbconfig ywDbconfig,@RequestParam(defaultValue = "10") int pageSize){
		int count = ywDbconfigService.queryTotals(ywDbconfig);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(YwDbconfig ywDbconfig,String op_type,Model model){
		// 查询用户信息
		model.addAttribute("ywUsers", ywUserCustomerService.queryList(new YwUserCustomer()));
		model.addAttribute("op_type", op_type);
		if(StringUtils.equals(op_type, "2")){
			model.addAttribute("ywDbconfig", ywDbconfigService.query(ywDbconfig));
		} else {
			model.addAttribute("ywDbconfig", ywDbconfig);
		}
		
		return "/system/dbconfig/ywDbconfigEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(YwDbconfig ywDbconfig,String op_type){
		Map<String,Object> map = new HashMap<String, Object>();
		if(StringUtils.equals(op_type, "1")){
			ywDbconfigService.insert(ywDbconfig);
			map.put("error_no", "0");
			map.put("error_info", "添加成功");
		} else {
			ywDbconfigService.update(ywDbconfig);
			map.put("error_no", "0");
			map.put("error_info", "修改成功");
		}
		// 清除动态数据源
		DBContextHolder.clearDBType();
		cacheManager.refreshOne("ywDbconfigCache");
		return map;
	}
	
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Map<String,Object> delete(YwDbconfig ywDbconfig){
		Map<String,Object> map = new HashMap<String, Object>();
		ywDbconfigService.delete(ywDbconfig);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		// 清除动态数据源
	    DBContextHolder.clearDBType();
		cacheManager.refreshOne("ywDbconfigCache");
		return map;
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping("deleteBatch")
	@ResponseBody
	public Map<String,Object> deleteBatch(YwDbconfig ywDbconfig,String db_ids){
		Map<String,Object> map = new HashMap<String, Object>();
		String[] strings = db_ids.split(",");
		Map<String, String[]> strMap = new HashMap<String, String[]>();
		strMap.put("db_id",strings);
		ywDbconfigService.deleteBatch(strMap);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		// 清除动态数据源
	    DBContextHolder.clearDBType();
	    cacheManager.refreshOne("ywDbconfigCache");
		return map;
	}	
}
