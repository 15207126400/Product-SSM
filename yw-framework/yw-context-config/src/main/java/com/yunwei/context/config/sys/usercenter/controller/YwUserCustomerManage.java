package com.yunwei.context.config.sys.usercenter.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunwei.context.sys.cache.YwRoleCache;
import com.yunwei.context.sys.model.YwRole;
import com.yunwei.context.sys.service.YwRoleService;
import com.yunwei.context.sys.usercenter.model.YwUser;
import com.yunwei.context.sys.usercenter.model.YwUserCustomer;
import com.yunwei.context.sys.usercenter.model.dto.YwUserCustomerAndYwUserDto;
import com.yunwei.context.sys.usercenter.service.YwUserCustomerService;
import com.yunwei.context.sys.usercenter.service.YwUserService;
import com.yunwei.common.user.SysUser;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.OrderUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.common.exception.BizException;

/**
 * 客户信息控制层
* @ClassName: YwUserCustomerManage 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年09月17日 下午15:51:05
*
 */
@Controller
@RequestMapping("/system/ywUserCustomer")
public class YwUserCustomerManage {
	private static Logger logger = Logger.getLogger(YwUserCustomerManage.class);
	@Autowired
	private YwUserCustomerService ywUserCustomerService;
	@Autowired
	private YwRoleService ywRoleService;
	@Autowired
	private YwRoleCache ywRoleCache;
	@Autowired
	private YwUserService ywUserService;
 
	@RequestMapping
	public String ywUserCustomerList(Model model){

		return "/system/usercustomer/ywUserCustomerList";
	}
	
	/**
	 * 查询用户集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public Map<String, Object> getList(YwUserCustomer ywUserCustomer,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
		map.putAll(MapUtil.toMap(ywUserCustomer));
		List<YwUserCustomerAndYwUserDto> list = ywUserCustomerService.queryUnionYwUserListByMap(map);
		for (YwUserCustomer ywUserCustomer2 : list) {
		Set<String> setlist = new HashSet<String>();
			if(StringUtils.isNotBlank(ywUserCustomer2.getRo_ids())){
				String[] roles = ywUserCustomer2.getRo_ids().split(",");
				for (String string : roles) {
					YwRole role = ywRoleCache.getConfigData().get(string);
					if(null != role){
						setlist.add(role.getRo_name());
					}
				}
				ywUserCustomer2.setRo_names(StringUtils.strip(setlist.toString(),"[]"));
			}
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
	public Paginator getPaginator(YwUserCustomer ywUserCustomer,@RequestParam(defaultValue = "10") int pageSize){
		
		int count = ywUserCustomerService.queryTotals(ywUserCustomer);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(YwUserCustomer ywUserCustomer,String op_type,Model model){
		
		model.addAttribute("op_type", op_type);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ro_status", "1");
		List<YwRole> ywRoles = ywRoleService.queryList(map);
		model.addAttribute("ywRoles", ywRoles);
		if(StringUtils.equals(op_type, "2")){
			YwUser ywUser = new YwUser();
			ywUser.setUser_id(ywUserCustomer.getUser_id());
			model.addAttribute("ywUserCustomer", ywUserCustomerService.query(ywUserCustomer));
			model.addAttribute("ywUser", ywUserService.query(ywUser));
		} else {
			model.addAttribute("ywUserCustomer", ywUserCustomer);
		}
		
		return "/system/usercustomer/ywUserCustomerEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(YwUser ywUser , YwUserCustomer ywUserCustomer,String op_type){
		
		Map<String,Object> map = new HashMap<String, Object>();
		//根据性别显示不同的用户默认头像
		if("1".equals(ywUserCustomer.getSex())){
			ywUser.setUser_avg("https://www.qhzhlkj.com/files/image/qhz/avg/man_avg1.png");
		} else {
			ywUser.setUser_avg("https://www.qhzhlkj.com/files/image/qhz/avg/woman_avg1.png");
		}
		if(StringUtils.equals(op_type, "1")){
			// 添加用户基本信息
			ywUser.setCreate_datetime(new Date());
			ywUser.setUser_id("YW-"+OrderUtil.getOrderNo());
			ywUserService.insert(ywUser);
			
			// 添加客户信息
			ywUserCustomer.setUser_id(ywUser.getUser_id());
			ywUserCustomer.setCreate_datetime(new Date());
			ywUserCustomerService.insert(ywUserCustomer);
			map.put("error_no", "0");
			map.put("error_info", "添加成功");
		} else {
			// 更新用户基本信息
			ywUser.setUpdate_datetime(new Date());
			ywUserService.update(ywUser);
			
			// 更新客户信息
			ywUserCustomer.setUpdate_datetime(new Date());
			ywUserCustomerService.updateCustomerAndOperator(ywUser,ywUserCustomer);
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
	public Map<String,Object> delete(YwUserCustomer ywUserCustomer){
		
		Map<String,Object> map = new HashMap<String, Object>();
		ywUserCustomerService.delete(ywUserCustomer);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping("deleteBatch")
	@ResponseBody
	public Map<String,Object> deleteBatch(YwUserCustomer ywUserCustomer,String UserCustomer_ids){
		Map<String,Object> map = new HashMap<String, Object>();
		String[] strings = UserCustomer_ids.split(",");
		Map<String, String[]> strMap = new HashMap<String, String[]>();
		strMap.put("UserCustomer_id",strings);
		ywUserCustomerService.deleteBatch(strMap);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}	
	
	/***
	 * 修改用户头像
	 */
	@RequestMapping("changeAvg")
	@ResponseBody
	public void changeAvg(SysUser sysUser , String avg_img){
		List<YwUser> ywUsers = ywUserService.queryList();
		for(YwUser ywUser : ywUsers){
			if((sysUser.getUser_id()).equals(ywUser.getUser_id())){
				ywUser.setUpdate_datetime(new Date());
				ywUser.setUser_avg(avg_img);
				ywUserService.update(ywUser);
			}
		}
	}
}
