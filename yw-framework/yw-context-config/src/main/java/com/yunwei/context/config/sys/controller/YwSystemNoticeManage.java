package com.yunwei.context.config.sys.controller;

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

import com.yunwei.common.cache.CacheManager;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.context.sys.model.YwSystemNotice;
import com.yunwei.context.sys.service.YwSystemNoticeService;
import com.yunwei.context.sys.usercenter.model.YwUserCustomer;
import com.yunwei.context.sys.usercenter.service.YwUserCustomerService;
import com.yunwei.context.sys.usercenter.service.YwUserService;

/**
 * 系统公告
 * @author zhangz
 *
 */
@Controller
@RequestMapping("/system/ywSystemNotice")
public class YwSystemNoticeManage {
	
	@Autowired
	private YwSystemNoticeService ywSystemNoticeService;
	@Autowired
	private YwUserCustomerService ywUserCustomerService;
	@Autowired
	private CacheManager cacheManager;

	@RequestMapping
	public String ywSystemNoticeList(Model model){
		return "/system/notice/ywNoticeList";
	}
	
	/**
	 * 查询字典集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public Map<String, Object> getList(YwSystemNotice ywSystemNotice,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
		map.putAll(MapUtil.toMap(ywSystemNotice));
		List<YwSystemNotice> list = ywSystemNoticeService.queryPage(map);
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
	public Paginator getPaginator(YwSystemNotice ywSystemNotice,@RequestParam(defaultValue = "10") int pageSize){
		int count = ywSystemNoticeService.queryTotals(ywSystemNotice);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(YwSystemNotice ywSystemNotice,String op_type,Model model){
		model.addAttribute("op_type", op_type);
		model.addAttribute("userlist",ywUserCustomerService.queryList(new YwUserCustomer()));
		if(StringUtils.equals(op_type, "2")){
			model.addAttribute("ywSystemNotice", ywSystemNoticeService.query(ywSystemNotice));
		} else {
			model.addAttribute("ywSystemNotice", ywSystemNotice);
		}
		
		return "/system/notice/ywNoticeEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(YwSystemNotice ywSystemNotice,String op_type){
		Map<String,Object> map = new HashMap<String, Object>();
		if(StringUtils.equals(op_type, "1")){
			ywSystemNotice.setNotice_create_time(new Date());
			ywSystemNoticeService.insert(ywSystemNotice);
			map.put("error_no", "0");
			map.put("error_info", "添加成功");
		} else {
			ywSystemNoticeService.update(ywSystemNotice);
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
	public Map<String,Object> delete(YwSystemNotice ywSystemNotice){
		Map<String,Object> map = new HashMap<String, Object>();
		ywSystemNoticeService.delete(ywSystemNotice);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping("deleteBatch")
	@ResponseBody
	public Map<String,Object> deleteBatch(YwSystemNotice ywSystemNotice,String notice_ids){
		Map<String,Object> map = new HashMap<String, Object>();
		String[] strings = notice_ids.split(",");
		Map<String, String[]> strMap = new HashMap<String, String[]>();
		strMap.put("notice_id",strings);
		ywSystemNoticeService.deleteBatch(strMap);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}	
}
