package com.yunwei.product.backend.sales.controller;

import java.util.ArrayList;
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
import com.yunwei.common.util.Base32;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.product.backend.service.YwDistributorCommissionrecordService;
import com.yunwei.product.common.backend.model.dto.YwCommissionrecordDto;
import com.yunwei.product.common.model.YwDistributorCommissionrecord;

/**
 * 佣金记录
 * @author zhangz
 *
 */
@Controller
@RequestMapping("/sales/ywDistributorCommissionrecord")
public class YwDistributorCommissionrecordManage {
	
	@Autowired
	private YwDistributorCommissionrecordService ywDistributorCommissionrecordService;
	@Autowired
	private CacheManager cacheManager;

	@RequestMapping
	public String ywDistributorCommissionrecordList(Model model){
		return "/sales/distributorcommissionrecord/ywDistributorCommissionrecordList";
	}
	
	/**
	 * 查询字典集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public Map<String, Object> getList(YwDistributorCommissionrecord ywDistributorCommissionrecord,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		List<YwCommissionrecordDto> ywCommissionrecordDtos = null;
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
		map.putAll(MapUtil.toMap(ywDistributorCommissionrecord));
		ywCommissionrecordDtos = ywDistributorCommissionrecordService.queryUnionMemberList(map);
		List<YwCommissionrecordDto> list = new ArrayList<YwCommissionrecordDto>();
		for (YwCommissionrecordDto ywCommissionrecordDto : ywCommissionrecordDtos) {
			ywCommissionrecordDto.setNickname(Base32.decode(ywCommissionrecordDto.getNickname()));
			list.add(ywCommissionrecordDto);
			if(list.size() == pageSize) break;
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
	public Paginator getPaginator(YwDistributorCommissionrecord ywDistributorCommissionrecord,@RequestParam(defaultValue = "10") int pageSize){
		int count = ywDistributorCommissionrecordService.queryTotals(ywDistributorCommissionrecord);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(YwDistributorCommissionrecord ywDistributorCommissionrecord,String op_type,Model model){
		model.addAttribute("op_type", op_type);
		if(StringUtils.equals(op_type, "2")){
			model.addAttribute("ywDistributorCommissionrecord", ywDistributorCommissionrecordService.query(ywDistributorCommissionrecord));
		} else {
			model.addAttribute("ywDistributorCommissionrecord", ywDistributorCommissionrecord);
		}
		
		return "/sales/distributorcommissionrecord/ywDistributorCommissionrecordEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(YwDistributorCommissionrecord ywDistributorCommissionrecord,String op_type){
		Map<String,Object> map = new HashMap<String, Object>();
		if(StringUtils.equals(op_type, "1")){
			ywDistributorCommissionrecordService.insert(ywDistributorCommissionrecord);
			map.put("error_no", "0");
			map.put("error_info", "添加成功");
		} else {
			ywDistributorCommissionrecordService.update(ywDistributorCommissionrecord);
			map.put("error_no", "0");
			map.put("error_info", "修改成功");
		}
		cacheManager.refreshAllCache();
		return map;
	}
	
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Map<String,Object> delete(YwDistributorCommissionrecord ywDistributorCommissionrecord){
		Map<String,Object> map = new HashMap<String, Object>();
		ywDistributorCommissionrecordService.delete(ywDistributorCommissionrecord);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		cacheManager.refreshAllCache();
		return map;
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping("deleteBatch")
	@ResponseBody
	public Map<String,Object> deleteBatch(YwDistributorCommissionrecord ywDistributorCommissionrecord,String dis_com_ids){
		Map<String,Object> map = new HashMap<String, Object>();
		String[] strings = dis_com_ids.split(",");
		Map<String, String[]> strMap = new HashMap<String, String[]>();
		strMap.put("dis_com_id",strings);
		ywDistributorCommissionrecordService.deleteBatch(strMap);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}	
}
