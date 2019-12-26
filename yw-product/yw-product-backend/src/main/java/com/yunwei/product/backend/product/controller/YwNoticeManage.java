package com.yunwei.product.backend.product.controller;

import java.util.Date;
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

import com.yunwei.common.user.SysUser;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.product.backend.service.YwNoticeService;
import com.yunwei.product.common.model.YwNotice;

/**
 * 公告栏信息
 * @author zhangz
 *
 */
@Controller
@RequestMapping("/product/ywNotice")
public class YwNoticeManage {
	
	@Autowired
	private YwNoticeService ywNoticeService;

	@RequestMapping
	public String ywNoticeList(Model model){
		/*List<YwNotice> ywNotice = ywNoticeService.queryList(null);
		model.addAttribute("ywNotice", ywNotice);*/
		
		return "/product/notice/ywNoticeList";
	}
	
	/**
	 * 查询字典集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public Map<String, Object> getList(YwNotice ywNotice,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
		map.putAll(MapUtil.toMap(ywNotice));
		List<YwNotice> list = ywNoticeService.queryPage(map);
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
	public Paginator getPaginator(YwNotice ywNotice,@RequestParam(defaultValue = "10") int pageSize){
		int count = ywNoticeService.queryTotals(ywNotice);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(YwNotice ywNotice,String op_type,Model model){
		model.addAttribute("op_type", op_type);
		if(StringUtils.equals(op_type, "2")){
			model.addAttribute("ywNotice", ywNoticeService.query(ywNotice));
		} else {
			model.addAttribute("ywNotice", ywNotice);
		}
		
		return "/product/notice/ywNoticeEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(SysUser sysUser, YwNotice ywNotice, String op_type){
		Map<String,Object> map = new HashMap<String, Object>();
		if(StringUtils.equals(op_type, "1")){
			ywNotice.setCreate_name(sysUser.getUser_name());
			ywNotice.setCreate_time(new Date());
			ywNoticeService.insert(ywNotice);
			map.put("error_no", "0");
			map.put("error_info", "添加成功");
		} else {
			ywNotice.setUpdate_name(sysUser.getUser_name());
			ywNotice.setUpdate_time(new Date());
			ywNoticeService.update(ywNotice);
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
	public Map<String,Object> delete(YwNotice ywNotice){
		Map<String,Object> map = new HashMap<String, Object>();
		ywNoticeService.delete(ywNotice);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping("deleteBatch")
	@ResponseBody
	public Map<String,Object> deleteBatch(YwNotice ywNotice,String noc_ids){
		Map<String,Object> map = new HashMap<String, Object>();
		String[] strings = noc_ids.split(",");
		Map<String, String[]> strMap = new HashMap<String, String[]>();
		strMap.put("noc_id",strings);
		ywNoticeService.deleteBatch(strMap);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}	
}
