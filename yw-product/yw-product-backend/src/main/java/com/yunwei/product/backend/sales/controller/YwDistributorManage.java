package com.yunwei.product.backend.sales.controller;

import java.util.ArrayList;
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

import com.yunwei.common.util.Base32;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.product.backend.service.YwDistributorService;
import com.yunwei.product.backend.service.YwMemberService;
import com.yunwei.product.common.backend.model.dto.YwDistributorDto;
import com.yunwei.product.common.model.YwDistributor;
import com.yunwei.product.common.model.YwMember;

/**
 * 分销商信息
 * @author zhangz
 *
 */
@Controller
@RequestMapping("/sales/ywDistributor")
public class YwDistributorManage {
	
	@Autowired
	private YwDistributorService ywDistributorService;
	@Autowired
	private YwMemberService ywMemberService;

	@RequestMapping
	public String ywDistributorList(Model model){
		return "/sales/distributor/ywDistributorList";
	}
	
	/**
	 * 查询字典集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public List<Map<String, Object>> getList(YwDistributor ywDistributor,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
		List<YwDistributorDto> ywDistributorDtos = null;
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
		map.putAll(MapUtil.toMap(ywDistributor));
		ywDistributorDtos = ywDistributorService.queryUnionMemberList(map);
		maps = MapUtil.toMapList(ywDistributorDtos);
		return maps;
	}
	
	/**
	 * 查询分页情况
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("getPaginator")
	@ResponseBody
	public Paginator getPaginator(YwDistributor ywDistributor,@RequestParam(defaultValue = "10") int pageSize){
		int count = ywDistributorService.queryTotals(ywDistributor);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("edit")
	public String edit(YwDistributor ywDistributor,String op_type,Model model) throws Exception{
		model.addAttribute("op_type", op_type);
		if(StringUtils.equals(op_type, "2")){
			YwDistributor distributor = ywDistributorService.query(ywDistributor);
			YwMember ywMember = ywMemberService.queryByOpenid(distributor.getDis_id());
			if(StringUtils.isNotBlank(distributor.getDis_parentid())){
				YwMember pid_ywMember = ywMemberService.queryByOpenid(distributor.getDis_parentid());
				model.addAttribute("pid_nickname",Base32.decode(pid_ywMember.getNickname()));
			}
			model.addAttribute("nickname", Base32.decode(ywMember.getNickname()));
			model.addAttribute("ywDistributor", distributor);
		} else {
			model.addAttribute("ywDistributor", new YwDistributor());
		}
		
		return "/sales/distributor/ywDistributorEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(YwDistributor ywDistributor,String op_type){
		Map<String,Object> map = new HashMap<String, Object>();
		if(StringUtils.equals(op_type, "1")){
			ywDistributorService.insert(ywDistributor);
			map.put("error_no", "0");
			map.put("error_info", "添加成功");
		} else {
			ywDistributor.setDis_audit_endtime(new Date());
			ywDistributorService.update(ywDistributor);
			map.put("error_no", "0");
			map.put("error_info", "审核成功");
		}
		return map;
	}
	
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Map<String,Object> delete(YwDistributor ywDistributor){
		Map<String,Object> map = new HashMap<String, Object>();
		ywDistributorService.delete(ywDistributor);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping("deleteBatch")
	@ResponseBody
	public Map<String,Object> deleteBatch(YwDistributor ywDistributor,String dis_ids){
		Map<String,Object> map = new HashMap<String, Object>();
		String[] strings = dis_ids.split(",");
		Map<String, String[]> strMap = new HashMap<String, String[]>();
		strMap.put("dis_id",strings);
		ywDistributorService.deleteBatch(strMap);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}	
}
