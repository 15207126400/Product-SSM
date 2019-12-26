package com.yunwei.product.backend.signIn.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunwei.product.backend.service.YwMemberService;
import com.yunwei.product.backend.service.YwSignInService;
import com.yunwei.product.common.backend.model.dto.YwDistributorDto;
import com.yunwei.product.common.backend.model.dto.YwSignInDto;
import com.yunwei.product.common.model.YwMember;
import com.yunwei.product.common.model.YwSignIn;
import com.yunwei.common.exception.BizException;
import com.yunwei.common.user.SysUser;
import com.yunwei.common.util.Base32;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;

/**
 * 签到控制层
* @ClassName: YwSignInManage 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年07月25日 下午16:32:34
*
 */
@Controller
@RequestMapping("/signin/ywSignIn")
public class YwSignInManage {
	private static Logger logger = Logger.getLogger(YwSignInManage.class);
	@Autowired
	private YwSignInService ywSignInService;
	@Autowired
	private YwMemberService ywMemberService;
 
	@RequestMapping
	public String ywSignInList(Model model){

		return "/signin/signin/ywSignInList";
	}
	
	/**
	 * 查询字典集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public List<Map<String, Object>> getList(SysUser sysUser,YwSignIn ywSignIn,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
		List<YwSignInDto> ywSignInDtos = null;
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
		map.putAll(MapUtil.toMap(ywSignIn));
		ywSignInDtos = ywSignInService.queryUnionMemberList(map);
		maps = MapUtil.toMapList(ywSignInDtos);
		return maps;
		
	}
	
	/**
	 * 查询分页情况
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("getPaginator")
	@ResponseBody
	public Paginator getPaginator(YwSignIn ywSignIn,@RequestParam(defaultValue = "10") int pageSize){
		int count = ywSignInService.queryTotals(ywSignIn);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(SysUser sysUser,YwSignIn ywSignIn,String op_type,Model model){
		model.addAttribute("op_type", op_type);
		if(StringUtils.equals(op_type, "2")){
			ywSignIn = ywSignInService.query(ywSignIn);
			YwMember member = ywMemberService.queryByOpenid(ywSignIn.getUser_id());
			
			model.addAttribute("ywSignIn", ywSignIn);
			model.addAttribute("nickname",Base32.decode(member.getNickname()));
		} else {
			model.addAttribute("ywSignIn", ywSignIn);
		}
		
		return "/signin/signin/ywSignInEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(YwSignIn ywSignIn,String op_type){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			if(StringUtils.equals(op_type, "1")){
				ywSignInService.insert(ywSignIn);
				map.put("error_no", "0");
				map.put("error_info", "添加成功");
			} else {
				ywSignInService.update(ywSignIn);
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
	public Map<String,Object> delete(YwSignIn ywSignIn){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			ywSignInService.delete(ywSignIn);
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
	public Map<String,Object> deleteBatch(YwSignIn ywSignIn){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			String[] signIn_id = ywSignIn.getSignIn_id().split(",");
			Map<String, String[]> strMap = new HashMap<String, String[]>();
			strMap.put("signIn_id",signIn_id);
			ywSignInService.deleteBatch(strMap);
			map.put("error_no", "0");
			map.put("error_info", "删除成功");
		} catch (Exception e) {
			throw new BizException(e);
		}
		return map;
	}	
}
