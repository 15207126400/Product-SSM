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

import com.yunwei.common.util.Base32;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.product.backend.service.YwDistributorSalestatusService;
import com.yunwei.product.common.backend.model.dto.YwDistributorSalestatusDto;
import com.yunwei.product.common.model.YwDistributorSalestatus;

/**
 * 分销销售记录
 * @author zhangz
 *
 */
@Controller
@RequestMapping("/sales/ywDistributorSalestatus")
public class YwDistributorSalestatusManage {
	
	@Autowired
	private YwDistributorSalestatusService ywDistributorSalestatusService;

	@RequestMapping
	public String ywDistributorSalestatusList(Model model){
		return "/sales/distributorsalestatus/ywDistributorSalestatusList";
	}
	
	/**
	 * 查询字典集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public Map<String, Object> getList(YwDistributorSalestatus ywDistributorSalestatus,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		List<YwDistributorSalestatusDto> ywDistributorSalestatusDtos = null;
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
		map.putAll(MapUtil.toMap(ywDistributorSalestatus));
		ywDistributorSalestatusDtos = ywDistributorSalestatusService.queryUnionMemberList(map);
		List<YwDistributorSalestatusDto> list = new ArrayList<YwDistributorSalestatusDto>();
		for (YwDistributorSalestatusDto ywDistributorSalestatusDto : ywDistributorSalestatusDtos) {
			ywDistributorSalestatusDto.setShare_nickname(Base32.decode(ywDistributorSalestatusDto.getShare_nickname()));
			ywDistributorSalestatusDto.setBuy_nickname(Base32.decode(ywDistributorSalestatusDto.getBuy_nickname()));
			list.add(ywDistributorSalestatusDto);
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
	public Paginator getPaginator(YwDistributorSalestatus ywDistributorSalestatus,@RequestParam(defaultValue = "10") int pageSize){
		int count = ywDistributorSalestatusService.queryTotals(ywDistributorSalestatus);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(YwDistributorSalestatus ywDistributorSalestatus,String op_type,Model model){
		model.addAttribute("op_type", op_type);
		if(StringUtils.equals(op_type, "2")){
			model.addAttribute("ywDistributorSalestatus", ywDistributorSalestatusService.query(ywDistributorSalestatus));
		} else {
			model.addAttribute("ywDistributorSalestatus", ywDistributorSalestatus);
		}
		
		return "/sales/distributorsalestatus/ywDistributorSalestatusEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(YwDistributorSalestatus ywDistributorSalestatus,String op_type){
		Map<String,Object> map = new HashMap<String, Object>();
		if(StringUtils.equals(op_type, "1")){
			ywDistributorSalestatusService.insert(ywDistributorSalestatus);
			map.put("error_no", "0");
			map.put("error_info", "添加成功");
		} else {
			ywDistributorSalestatusService.update(ywDistributorSalestatus);
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
	public Map<String,Object> delete(YwDistributorSalestatus ywDistributorSalestatus){
		Map<String,Object> map = new HashMap<String, Object>();
		ywDistributorSalestatusService.delete(ywDistributorSalestatus);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping("deleteBatch")
	@ResponseBody
	public Map<String,Object> deleteBatch(YwDistributorSalestatus ywDistributorSalestatus,String dis_st_ids){
		Map<String,Object> map = new HashMap<String, Object>();
		String[] strings = dis_st_ids.split(",");
		Map<String, String[]> strMap = new HashMap<String, String[]>();
		strMap.put("dis_st_id",strings);
		ywDistributorSalestatusService.deleteBatch(strMap);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}
}
