package com.yunwei.product.backend.sales.controller;

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
import com.yunwei.product.backend.service.YwSeckillRecordService;
import com.yunwei.product.common.model.YwSeckillRecord;

/**
 * 系统数据库配置控制器
 * @author zhangz
 *
 */
@Controller
@RequestMapping("/sales/ywSeckillRecord")
public class YwSeckillRecordManage {
	
	@Autowired
	private YwSeckillRecordService ywSeckillRecordService;

	@RequestMapping
	public String YwSeckillRecordList(Model model){
		return "/sales/seckillrecord/ywSeckillRecordList";
	}
	
	/**
	 * 查询字典集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public List<Map<String, Object>> getList(SysUser sysUser , YwSeckillRecord ywSeckillRecord,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
		map.putAll(MapUtil.toMap(ywSeckillRecord));
		List<YwSeckillRecord> list = ywSeckillRecordService.queryPage(map);
//		map.put("error_no", "0");
//		map.put("error_info", "查询成功");
		return MapUtil.toMapList(list);
	}
	
	/**
	 * 查询分页情况
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("getPaginator")
	@ResponseBody
	public Paginator getPaginator(SysUser sysUser , YwSeckillRecord ywSeckillRecord,@RequestParam(defaultValue = "10") int pageSize){
		int count = ywSeckillRecordService.queryTotals(ywSeckillRecord);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(YwSeckillRecord ywSeckillRecord,String op_type,Model model){
		model.addAttribute("op_type", op_type);
		if(StringUtils.equals(op_type, "2")){
			model.addAttribute("ywSeckillRecord", ywSeckillRecordService.query(ywSeckillRecord));
		} else {
			model.addAttribute("ywSeckillRecord", ywSeckillRecord);
		}
		
		return "/sales/seckillrecord/ywSeckillRecordEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(SysUser sysUser ,YwSeckillRecord ywSeckillRecord,String op_type){
		Map<String,Object> map = new HashMap<String, Object>();
		if(StringUtils.equals(op_type, "1")){
			ywSeckillRecord.setRecord_createtime(new Date());
			ywSeckillRecordService.insert(ywSeckillRecord);
			map.put("error_no", "0");
			map.put("error_info", "添加成功");
		} else {
			ywSeckillRecord.setRecord_updatetime(new Date());
			ywSeckillRecordService.update(ywSeckillRecord);
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
	public Map<String,Object> delete(YwSeckillRecord ywSeckillRecord){
		Map<String,Object> map = new HashMap<String, Object>();
		ywSeckillRecordService.delete(ywSeckillRecord);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping("deleteBatch")
	@ResponseBody
	public Map<String,Object> deleteBatch(YwSeckillRecord ywSeckillRecord,String db_ids){
		Map<String,Object> map = new HashMap<String, Object>();
		String[] strings = db_ids.split(",");
		Map<String, String[]> strMap = new HashMap<String, String[]>();
		strMap.put("db_id",strings);
		ywSeckillRecordService.deleteBatch(strMap);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}	
}
