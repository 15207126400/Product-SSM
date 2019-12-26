package com.yunwei.product.backend.points.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunwei.product.backend.service.YwMemberService;
import com.yunwei.product.backend.service.YwPointsService;
import com.yunwei.product.common.backend.model.dto.YwPointsDto;
import com.yunwei.product.common.model.YwMember;
import com.yunwei.product.common.model.YwPoints;
import com.yunwei.common.user.SysUser;
import com.yunwei.common.util.Base32;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.common.exception.BizException;

/**
 * 积分中心控制层
* @ClassName: YwPointsManage 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年07月26日 下午12:01:40
*
 */
@Controller
@RequestMapping("/points/ywPoints")
public class YwPointsManage {
	private static Logger logger = Logger.getLogger(YwPointsManage.class);
	@Autowired
	private YwPointsService ywPointsService;
	@Autowired
	private YwMemberService ywMemberService;
 
	@RequestMapping
	public String ywPointsList(Model model){

		return "/points/points/ywPointsList";
	}
	
	/**
	 * 查询字典集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public List<Map<String, Object>> getList(SysUser sysUser,YwPoints ywPoints,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> maps = new ArrayList<Map<String,Object>>();
		List<YwPointsDto> ywPointsDtos = null;
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
		map.putAll(MapUtil.toMap(ywPoints));
		ywPointsDtos = ywPointsService.queryUnionMemberList(map);
		maps = MapUtil.toMapList(ywPointsDtos);
		return maps;
	}
	
	/**
	 * 查询分页情况
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("getPaginator")
	@ResponseBody
	public Paginator getPaginator(YwPoints ywPoints,@RequestParam(defaultValue = "10") int pageSize){
		int count = ywPointsService.queryTotals(ywPoints);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(SysUser sysUser,YwPoints ywPoints,String op_type,Model model){
		model.addAttribute("op_type", op_type);
		if(StringUtils.equals(op_type, "2")){
			ywPoints = ywPointsService.query(ywPoints);
			YwMember member = ywMemberService.queryByOpenid(ywPoints.getUser_id());
			
			model.addAttribute("ywPoints", ywPoints);
			model.addAttribute("nickname",Base32.decode(member.getNickname()));
		} else {
			model.addAttribute("ywPoints", ywPoints);
		}
		
		return "/points/points/ywPointsEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(YwPoints ywPoints,String op_type){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			if(StringUtils.equals(op_type, "1")){
				ywPointsService.insert(ywPoints);
				map.put("error_no", "0");
				map.put("error_info", "添加成功");
			} else {
				ywPointsService.update(ywPoints);
				map.put("error_no", "0");
				map.put("error_info", "修改成功");
			}
		} catch (Exception e) {
			throw new BizException(e);
		}
		return map;
	}
	
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Map<String,Object> delete(YwPoints ywPoints){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			ywPointsService.delete(ywPoints);
			map.put("error_no", "0");
			map.put("error_info", "删除成功");
		} catch (Exception e) {
			throw new BizException(e);
		}
		return map;
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping("deleteBatch")
	@ResponseBody
	public Map<String,Object> deleteBatch(YwPoints ywPoints){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			String[] points_id = ywPoints.getPoints_id().split(",");
			Map<String, String[]> strMap = new HashMap<String, String[]>();
			strMap.put("points_id",points_id);
			ywPointsService.deleteBatch(strMap);
			map.put("error_no", "0");
			map.put("error_info", "删除成功");
		} catch (Exception e) {
			throw new BizException(e);
		}
		return map;
	}	
}
