package com.yunwei.product.backend.member.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.fabric.xmlrpc.base.Member;
import com.yunwei.common.util.Base32;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.common.util.RedisClientUtil;
import com.yunwei.product.backend.service.YwBaseconfigService;
import com.yunwei.product.backend.service.YwMemberRoleService;
import com.yunwei.product.backend.service.YwMemberService;
import com.yunwei.product.common.model.YwBaseconfig;
import com.yunwei.product.common.model.YwMember;
import com.yunwei.product.common.model.YwMemberRole;

/**
 * 
* @ClassName: YwMemberManage 
* @Description: 会员管理
* @author zhangjh
* @date 2018年3月17日 下午2:47:48 
*
 */
@Controller
@RequestMapping("/member/ywMember")
public class YwMemberManage {
	
	@Autowired
	private YwMemberService memberService;
	@Autowired
	private YwMemberRoleService ywMemberRoleService;
	@Autowired
	private YwBaseconfigService ywBaseconfigService;
	
	/**
	 * 会员信息模块
	 */
	@RequestMapping
	public String query_member(YwMember YwMember , Model model) {
		YwBaseconfig ywBaseconfig = new YwBaseconfig();
		ywBaseconfig.setConfig_id("member.delivery.auditing");
		ywBaseconfig = ywBaseconfigService.query(ywBaseconfig);
		model.addAttribute("auditing", ywBaseconfig.getConfig_value());
		
		// 查询会员级别信息
		model.addAttribute("ywMemberRoles", ywMemberRoleService.queryList());
		
		return "member/ywMemberList";
	}
	
	/**
	 * 查询会员集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public Map<String, Object> getList(YwMember YwMember ,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
		map.putAll(MapUtil.toMap(YwMember));
		List<YwMember> list = memberService.queryPage(map);
		map.put("error_no", "0");
		map.put("error_info", "查询成功");
		map.put("resultList", MapUtil.toMapList(list));
		return map;
	}
	
	/**
	 * 查询分页情况
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("getPaginator")
	@ResponseBody
	public Paginator getPaginator(YwMember ywMember,@RequestParam(defaultValue = "10") int pageSize){
		int count = memberService.queryTotals(ywMember);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 会员详情查看
	 */
	@RequestMapping("edit")
	public String edit(String openid ,String op_type, Model model){
		model.addAttribute("op_type", op_type);
		if(StringUtils.equals(op_type, "2") || StringUtils.equals(op_type, "3")){
			YwMember member = memberService.queryByOpenid(openid);
			member.setNickname(Base32.decode(member.getNickname()));
			
			// 基本配置信息(是否需要审核)
			YwBaseconfig ywBaseconfig = new YwBaseconfig();
			ywBaseconfig.setConfig_id("member.delivery.auditing");
			ywBaseconfig = ywBaseconfigService.query(ywBaseconfig);
			model.addAttribute("auditing", ywBaseconfig.getConfig_value());
			
			model.addAttribute("member", member);
			model.addAttribute("ywMemberRoles", ywMemberRoleService.queryList());
		} else {
			model.addAttribute("member", new YwMember());
		}
		
		return "/member/ywMemberEidt";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(YwMember ywMember,String op_type,String auditing_falg){
		Map<String,Object> map = new HashMap<String, Object>();
		if(StringUtils.equals(op_type, "1")){
			memberService.insert(ywMember);
			map.put("error_no", "0");
			map.put("error_info", "添加成功");
		} else if(StringUtils.equals(op_type, "3")){
			// 审核通过
			if(StringUtils.equals(auditing_falg, "2")){
				ywMember.setLevel("2");
				ywMember.setIs_auditing(auditing_falg);
				ywMember.setNickname(Base32.encode(ywMember.getNickname()));
				memberService.update(ywMember);
			}else if(StringUtils.equals(auditing_falg, "3")){
				ywMember.setLevel("1");
				ywMember.setIs_auditing(auditing_falg);
				ywMember.setNickname(Base32.encode(ywMember.getNickname()));
				memberService.update(ywMember);
			}else{
				ywMember.setNickname(Base32.encode(ywMember.getNickname()));
				memberService.update(ywMember);
			}
		}else{
			ywMember.setNickname(Base32.encode(ywMember.getNickname()));
			memberService.update(ywMember);
		}
		map.put("error_no", "0");
		map.put("error_info", "修改成功");
		
		return map;
	}
	
	/**
	 * 删除会员信息(同时清除缓存)
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Map<String, Object> delete(YwMember ywMember){
		Map<String, Object> map = new HashMap<String, Object>();
		YwMember member = memberService.query(Integer.valueOf(ywMember.getId()));
		memberService.delete(ywMember);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		
		if(null != member){
			String key = RedisClientUtil.getModelKeyAlias("YwMember", member.getOpenid());
			RedisClientUtil.del(key);
		}
		return map;
	}
	
	/**
	 * 批量删除(批量不删除缓存)
	 */
	@RequestMapping("deleteBatch")
	@ResponseBody
	public Map<String,Object> deleteBatch(YwMember ywMember,String ids){
		Map<String,Object> map = new HashMap<String, Object>();
		String[] strings = ids.split(",");
		Map<String, String[]> strMap = new HashMap<String, String[]>();
		strMap.put("id",strings);
		memberService.deleteBatch(strMap);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}	

}
