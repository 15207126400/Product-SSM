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
import com.yunwei.context.sys.model.YwMenu;
import com.yunwei.context.sys.service.YwMenuService;

/**
 * 系统数据库配置控制器
 * @author zhangz
 *
 */
@Controller
@RequestMapping("/system/ywMenu")
public class YwMenuManagerController {
	
	@Autowired
	private YwMenuService ywMenuService;
	@Autowired
	private CacheManager cacheManager;

	@RequestMapping
	public String ywMenuList(Model model){
		return "/system/menu/ywMenuList";
	}
	
	/**
	 * 查询字典集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public Map<String, Object> getList(YwMenu ywMenu,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
		map.putAll(MapUtil.toMap(ywMenu));
		List<YwMenu> list = ywMenuService.queryListPage(map);
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
	public Paginator getPaginator(YwMenu ywMenu,@RequestParam(defaultValue = "10") int pageSize){
		int count = ywMenuService.queryTotals(ywMenu);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(YwMenu ywMenu,String op_type,Model model){
		// 查询父菜单
		YwMenu menu = new YwMenu();
		menu.setMu_level("1");
		List<YwMenu> menus = ywMenuService.queryList(menu);
		model.addAttribute("menus", menus);
		model.addAttribute("op_type", op_type);
		if(StringUtils.equals(op_type, "2")){
			model.addAttribute("ywMenu", ywMenuService.query(ywMenu));
		} else {
			model.addAttribute("ywMenu", ywMenu);
		}
		
		return "/system/menu/ywMenuEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(YwMenu ywMenu,String op_type){
		Map<String,Object> map = new HashMap<String, Object>();
		if(StringUtils.equals(op_type, "1")){
			ywMenuService.insert(ywMenu);
			map.put("error_no", "0");
			map.put("error_info", "添加成功");
		} else {
			ywMenuService.update(ywMenu);
			map.put("error_no", "0");
			map.put("error_info", "修改成功");
		}
		cacheManager.refreshOne("ywMenuCache");
		return map;
	}
	
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Map<String,Object> delete(YwMenu ywMenu){
		Map<String,Object> map = new HashMap<String, Object>();
		ywMenuService.delete(ywMenu);
		cacheManager.refreshOne("ywMenuCache");
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}
	
	/**
	 * 批量删除
	 * @return
	 */
	@RequestMapping("deleteBatch")
	@ResponseBody
	public Map<String,Object> deleteBatch(YwMenu ywMenu,String mu_ids){
		Map<String,Object> map = new HashMap<String, Object>();
		String[] strings = mu_ids.split(",");
		ywMenuService.deleteBatch(strings);
		cacheManager.refreshOne("ywMenuCache");
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}
}
