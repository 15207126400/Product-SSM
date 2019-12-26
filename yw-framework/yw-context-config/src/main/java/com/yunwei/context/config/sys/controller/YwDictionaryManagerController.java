package com.yunwei.context.config.sys.controller;

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
import com.yunwei.common.user.SysUser;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.context.sys.model.YwDictionary;
import com.yunwei.context.sys.model.form.YwDictionaryForm;
import com.yunwei.context.sys.service.YwDictionaryService;

/**
 * 系统字典控制器
* @ClassName: YwDictionaryManage 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年7月2日 下午5:13:53 
*
 */
@Controller
@RequestMapping("/system/ywDictionary")
public class YwDictionaryManagerController {
	
	@Autowired
	private YwDictionaryService ywDictionaryService;
	@Autowired
	private CacheManager cacheManager;

	@RequestMapping
	public String ywDictionaryList(Model model){
//		String string =  PropertiesUtils.get("func.org.locate.scope");
		return "/system/dictionary/ywDictionaryList";
	}
	
	/**
	 * 查询字典集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public Map<String, Object> getList(SysUser sysUser,YwDictionary ywDictionary,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
		map.putAll(MapUtil.toMap(ywDictionary));
		List<YwDictionary> list = ywDictionaryService.queryPage(map);
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
	public Paginator getPaginator(YwDictionary ywDictionary,@RequestParam(defaultValue = "10") int pageSize){
		int count = ywDictionaryService.queryTotals(ywDictionary);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(SysUser sysUser,YwDictionary ywDictionary,String op_type,Model model){
		model.addAttribute("op_type", op_type);
		if(StringUtils.equals(op_type, "2")){
			model.addAttribute("ywDictionary", ywDictionaryService.query(ywDictionary));
		} else {
			model.addAttribute("ywDictionary", ywDictionary);
		}
		
		return "/system/dictionary/ywDictionaryEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(@Valid YwDictionary ywDictionary,String op_type){
		Map<String,Object> map = new HashMap<String, Object>();
		if(StringUtils.equals(op_type, "1")){
			ywDictionaryService.insert(ywDictionary);
			map.put("error_no", "0");
			map.put("error_info", "添加成功");
		} else {
			ywDictionaryService.update(ywDictionary);
			map.put("error_no", "0");
			map.put("error_info", "修改成功");
		}
		cacheManager.refreshOne("ywDictionaryCache");
		return map;
	}
	
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Map<String,Object> delete(YwDictionary ywDictionary){
		Map<String,Object> map = new HashMap<String, Object>();
		ywDictionaryService.delete(ywDictionary);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		cacheManager.refreshOne("ywDictionaryCache");
		return map;
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping("deleteBatch")
	@ResponseBody
	public Map<String,Object> deleteBatch(YwDictionary ywDictionary,YwDictionaryForm ywDictionaryForm){
		Map<String,Object> map = new HashMap<String, Object>();
		String[] dic_key = ywDictionaryForm.getDic_key().split(",");
		String[] dic_subkey = ywDictionaryForm.getDic_subkey().split(",");
		Map<String, String[]> strMap = new HashMap<String, String[]>();
		strMap.put("dic_key",dic_key);
		strMap.put("dic_subkey",dic_subkey);
		ywDictionaryService.deleteBatch(strMap);
		cacheManager.refreshOne("ywDictionaryCache");
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}	
}
