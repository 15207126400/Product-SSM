package com.yunwei.context.config.sys.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.sql.visitor.functions.Substring;
import com.yunwei.common.cache.CacheManager;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.context.sys.cache.YwMenuCache;
import com.yunwei.context.sys.model.YwMenu;
import com.yunwei.context.sys.model.YwRole;
import com.yunwei.context.sys.service.YwRoleService;

/**
 * 系统角色
 * @author zhangz
 *
 */
@Controller
@RequestMapping("/system/ywRole")
public class YwRoleManagerController {
	
	@Autowired
	private YwRoleService ywRoleService;
	@Autowired
	private YwMenuCache ywMenuCache;
	@Autowired
	private CacheManager cacheManager;

	@RequestMapping
	public String ywRoleList(Model model){
		return "/system/role/ywRoleList";
	}
	
	/**
	 * 查询字典集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public Map<String, Object> getList(YwRole ywRole,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		
		Map<String, Object> map = new HashMap<String, Object>();
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
		map.putAll(MapUtil.toMap(ywRole));
		List<YwRole> list = ywRoleService.queryPage(map);
		for (YwRole ywRole2 : list) {
		 Set<String> newlist = new HashSet<String>();
			if(StringUtils.isNotBlank(ywRole2.getMu_ids())){
				String[] mu_ids = ywRole2.getMu_ids().split(",");
				for (String mu_id : mu_ids) {
					YwMenu menu = ywMenuCache.getConfigData().get(mu_id);
					newlist.add(menu.getMu_name());
				}
				ywRole2.setMu_names(StringUtils.strip(newlist.toString(),"[]"));
			}
		}
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
	public Paginator getPaginator(YwRole ywRole,@RequestParam(defaultValue = "10") int pageSize){
		
		int count = ywRoleService.queryTotals(ywRole);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(YwRole ywRole,String op_type,Model model){
		
		model.addAttribute("op_type", op_type);
		if(StringUtils.equals(op_type, "2")){
			model.addAttribute("ywRole", ywRoleService.query(ywRole));
		} else {
			model.addAttribute("ywRole", ywRole);
		}
		
		return "/system/role/ywRoleEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(YwRole ywRole,String op_type){
		
		Map<String,Object> map = new HashMap<String, Object>();
		if(StringUtils.equals(op_type, "1")){
			ywRoleService.insert(ywRole);
			map.put("error_no", "0");
			map.put("error_info", "添加成功");
		} else {
			ywRoleService.update(ywRole);
			map.put("error_no", "0");
			map.put("error_info", "修改成功");
		}
		cacheManager.refreshOne("ywRoleCache");
		return map;
	}
	
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Map<String,Object> delete(YwRole ywRole){
		
		Map<String,Object> map = new HashMap<String, Object>();
		ywRoleService.delete(ywRole);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		cacheManager.refreshOne("ywRoleCache");
		return map;
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping("deleteBatch")
	@ResponseBody
	public Map<String,Object> deleteBatch(YwRole ywRole,String ro_ids){
		Map<String,Object> map = new HashMap<String, Object>();
		String[] strings = ro_ids.split(",");
		Map<String, String[]> strMap = new HashMap<String, String[]>();
		strMap.put("ro_id",strings);
		ywRoleService.deleteBatch(strMap);
		cacheManager.refreshOne("ywRoleCache");
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}	
	
	/**
	 * 获取菜单树形结构
	 * @return
	 */
	@RequestMapping("getMenus")
	@ResponseBody
	public Map<String,Object> getMenus(YwRole ywRole){
		
		YwMenu menu = new YwMenu();
		List<YwMenu> menus = ywRoleService.getMenus(ywRole);
		menu.setMu_id("0");
		menu.setMu_name("系统菜单");
		menu.setChild_menus(menus);
		return MapUtil.toMap(menu);
	}
}
