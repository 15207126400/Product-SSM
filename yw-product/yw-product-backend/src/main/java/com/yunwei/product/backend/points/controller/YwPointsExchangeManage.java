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
import com.yunwei.product.backend.service.YwPointsExchangeService;
import com.yunwei.product.backend.service.YwProductService;
import com.yunwei.product.common.backend.model.dto.YwPointsExchangeDto;
import com.yunwei.product.common.model.YwMember;
import com.yunwei.product.common.model.YwPointsExchange;
import com.yunwei.common.user.SysUser;
import com.yunwei.common.util.Base32;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.common.exception.BizException;

/**
 * 积分兑换控制层
* @ClassName: YwPointsExchangeManage 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年07月26日 下午17:08:01
*
 */
@Controller
@RequestMapping("/points/ywPointsExchange")
public class YwPointsExchangeManage {
	private static Logger logger = Logger.getLogger(YwPointsExchangeManage.class);
	@Autowired
	private YwPointsExchangeService ywPointsExchangeService;
	@Autowired
	private YwMemberService ywMemberService;
	@Autowired
	private YwProductService ywProductService;
 
	@RequestMapping
	public String ywPointsExchangeList(Model model){

		return "/points/pointsexchange/ywPointsExchangeList";
	}
	
	/**
	 * 查询字典集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public List<Map<String, Object>> getList(SysUser sysUser,YwPointsExchange ywPointsExchange,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> maps = new ArrayList<Map<String,Object>>();
		List<YwPointsExchangeDto> ywPointsExchangeDtos = null;
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
		map.putAll(MapUtil.toMap(ywPointsExchange));
		ywPointsExchangeDtos = ywPointsExchangeService.queryUnionMemberList(map);
		maps = MapUtil.toMapList(ywPointsExchangeDtos);
		return maps;
	}
	
	/**
	 * 查询分页情况
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("getPaginator")
	@ResponseBody
	public Paginator getPaginator(YwPointsExchange ywPointsExchange,@RequestParam(defaultValue = "10") int pageSize){
		int count = ywPointsExchangeService.queryTotals(ywPointsExchange);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(SysUser sysUser,YwPointsExchange ywPointsExchange,String op_type,Model model){
		model.addAttribute("products",ywProductService.queryPage(null));
		model.addAttribute("op_type", op_type);
		if(StringUtils.equals(op_type, "2")){
			ywPointsExchange = ywPointsExchangeService.query(ywPointsExchange);
			YwMember member = ywMemberService.queryByOpenid(ywPointsExchange.getUser_id());
			
			model.addAttribute("nickname", Base32.decode(member.getNickname()));
			model.addAttribute("ywPointsExchange", ywPointsExchange);
		} else {
			model.addAttribute("ywPointsExchange", ywPointsExchange);
		}
		
		return "/points/pointsexchange/ywPointsExchangeEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(YwPointsExchange ywPointsExchange,String op_type){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			if(StringUtils.equals(op_type, "1")){
				ywPointsExchangeService.insert(ywPointsExchange);
				map.put("error_no", "0");
				map.put("error_info", "添加成功");
			} else {
				ywPointsExchangeService.update(ywPointsExchange);
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
	public Map<String,Object> delete(YwPointsExchange ywPointsExchange){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			ywPointsExchangeService.delete(ywPointsExchange);
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
	public Map<String,Object> deleteBatch(YwPointsExchange ywPointsExchange){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			String[] exchange_id = ywPointsExchange.getExchange_id().split(",");
			Map<String, String[]> strMap = new HashMap<String, String[]>();
			strMap.put("exchange_id",exchange_id);
			ywPointsExchangeService.deleteBatch(strMap);
			map.put("error_no", "0");
			map.put("error_info", "删除成功");
		} catch (Exception e) {
			throw new BizException(e);
		}
		return map;
	}	
}
