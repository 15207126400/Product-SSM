package com.yunwei.product.backend.member.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.fabric.xmlrpc.base.Member;
import com.yunwei.common.exception.BizException;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.product.backend.product.controller.YwProductManage;
import com.yunwei.product.backend.service.YwMemberRoleService;
import com.yunwei.product.backend.service.YwMemberService;
import com.yunwei.product.common.model.YwMemberRole;
import com.yunwei.product.common.model.YwProduct;

/**
 * 
* @ClassName: YwMemberManage 
* @Description: 会员等级
* @author zhangjh
* @date 2018年3月17日 下午2:47:48 
*
 */
@Controller
@RequestMapping("/member/ywMemberRole")
public class YwMemberRoleManage {
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private YwMemberService memberService;
	@Autowired
	private YwMemberRoleService memberRoleService;
	
	// 日志打印
    Logger logger = Logger.getLogger(YwProductManage.class);
	
	/**
	 * 会员权限
	 */
	@RequestMapping
	public String role(Model model) {
		
		return "/member/ywMemberRoleList";
	}
	
	/**
	 * 会员权限查询页面 (查看数据库已设定的数据)
	 */
	@RequestMapping("getList")
	@ResponseBody
	public Map<String, Object> role_getList(YwMemberRole YwMemberRole ,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
		map.putAll(MapUtil.toMap(YwMemberRole));
		List<YwMemberRole> list = memberRoleService.queryPage(map);
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
	public Paginator role_getPaginator(YwMemberRole ywMemberRole,@RequestParam(defaultValue = "10") int pageSize){
		int count = memberRoleService.queryTotals(ywMemberRole);
		return new Paginator(1, pageSize, count);
	}
	
	
	/**
	 * 查询所有会员等级集合
	 * @param ywMemberRole
	 * @return
	 */
	@RequestMapping("getLists")
	@ResponseBody
	public List<YwMemberRole> getLists(YwMemberRole ywMemberRole){
		List<YwMemberRole> ywMemberRoles = new ArrayList<YwMemberRole>();
		try {
			ywMemberRoles = memberRoleService.queryList();
		} catch (Exception e) {
			logger.error("查询会员等级集合失败", e);
			throw new BizException(e);
		}
		return ywMemberRoles;
	}
	
	/**
	 * 删除会员权限
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Map<String, Object> delete_member_role(int id) {
		Map<String, Object> map = new HashMap<String, Object>();
		memberRoleService.deleteYwMemberRole(id);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		
		return map;
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping("deleteBatch")
	@ResponseBody
	public Map<String,Object> deleteBatch(YwMemberRole ywMemberRole,String ids){
		Map<String,Object> map = new HashMap<String, Object>();
		String[] strings = ids.split(",");
		Map<String, String[]> strMap = new HashMap<String, String[]>();
		strMap.put("id",strings);
		memberRoleService.deleteBatch(strMap);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}	
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(YwMemberRole ywMemberRole,String op_type,Model model){
		
		model.addAttribute("op_type", op_type);
		if(StringUtils.equals(op_type, "2")){
			model.addAttribute("ywMemberRole", memberRoleService.query(ywMemberRole));
		} else {
			model.addAttribute("ywMemberRole", ywMemberRole);
		}
		
		return "/member/ywMemberRoleEidt";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(YwMemberRole ywMemberRole,String op_type){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			YwMemberRole memberRole = memberRoleService.queryByLevelname(ywMemberRole.getLevel());
			if(memberRole == null){
				if(StringUtils.equals(op_type, "1")){
					memberRoleService.insert(ywMemberRole);
					map.put("error_no", "0");
					map.put("error_info", "添加成功");
				} else {
					memberRoleService.update(ywMemberRole);
					map.put("error_no", "0");
					map.put("error_info", "修改成功");
				}
			} else {
				map.put("error_no", "0");
				map.put("error_info", "该会员级别已存在,请修改后重新提交");
			}
			
		} catch (Exception e) {
			throw new BizException(e);
		}
		return map;
	}
	
	/**
	 * 判断是否存在该会员级别
	 * @return
	 */
	@RequestMapping("judgeIsLevel")
	@ResponseBody
	public Map<String,Object> judgeIsLevel(String level){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			YwMemberRole memberRole = memberRoleService.queryByLevelname(level);
			if(memberRole != null){
				map.put("level_flag", 0);
			} else {
				map.put("level_flag", 1);
			}
		} catch (Exception e) {
			throw new BizException(e);
		}
		return map;
	}
}
