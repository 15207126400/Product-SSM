package com.yunwei.product.backend.system.controller;

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

import com.yunwei.common.annotation.NoLogin;
import com.yunwei.common.util.Paginator;
import com.yunwei.product.backend.service.YwTicketService;
import com.yunwei.product.common.model.YwTicket;

/**
 * 
* @ClassName: YwTicketManage 
* @Description: 抽奖人员配置表
* @author 晏飞
* @date 2018年5月16日 下午3:15:30 
*
 */

@Controller
@RequestMapping("/system/ywTicket")
public class YwTicketManage {

	@Autowired
	private YwTicketService ywTicketService;
	
	/**
	 * 抽奖页面跳转
	 * @param model
	 * @return
	 */
	@NoLogin
	@RequestMapping("ticketShow.htm")
	public String ticketShow(Model model){
		/*List<YwTicket> ywTickets = new ArrayList<YwTicket>();
		//查询遍历所有人员数据,查看是否有设置内定中奖人员
		List<YwTicket> ywTicketList = ywTicketService.queryList(new YwTicket());
		for(YwTicket item : ywTicketList){
			if(item.getTicket_flag().equals("1")){
				ywTickets.add(item);
			}
		}
		//如果没有数据则查询随机条数的数据返回页面
		if(ywTickets.size() == 0){
			ywTickets = ywTicketService.queryByRand();
		}
		model.addAttribute("ywTickets", ywTickets);*/
		
		model.addAttribute("ywTickets" , ywTicketService.queryList(new YwTicket()));
		
		
		return "/system/ticket/ywTicket";
	}
	
	@RequestMapping
	public String ywTicketList(){
		
		return "/system/ticket/ywTicketList";
	}
	
	/**
	 * 查询字典集合
	 * @param gwsystem
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public Map<String, Object> getList(YwTicket ywTicket,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
//		map.put("begin", start);
//		map.put("end", pageSize); // 设置每页显示几条数据
//		map.putAll(MapUtil.toMap(ywTicket));
		List<YwTicket> list = ywTicketService.queryListPage(ywTicket,start,end);
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
	public Paginator getPaginator(YwTicket ywTicket,@RequestParam(defaultValue = "10") int pageSize){
		int count = ywTicketService.queryTotals(ywTicket);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(YwTicket ywTicket,String op_type,Model model){
		model.addAttribute("op_type", op_type);
		if(StringUtils.equals(op_type, "2")){
			model.addAttribute("ywTicket", ywTicketService.query(ywTicket));
		} else {
			model.addAttribute("ywTicket", ywTicket);
		}
		
		return "/system/ticket/ywTicketEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(YwTicket ywTicket,String op_type){
		Map<String,Object> map = new HashMap<String, Object>();
		if(StringUtils.equals(op_type, "1")){
			ywTicketService.insert(ywTicket);
			map.put("error_no", "0");
			map.put("error_info", "添加成功");
		} else {
			ywTicketService.update(ywTicket);
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
	public Map<String,Object> delete(YwTicket ywTicket){
		Map<String,Object> map = new HashMap<String, Object>();
		ywTicketService.delete(ywTicket);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping("deleteBatch")
	@ResponseBody
	public Map<String,Object> deleteBatch(YwTicket ywTicket,String ticket_ids){
		Map<String,Object> map = new HashMap<String, Object>();
		String[] strings = ticket_ids.split(",");
		Map<String, String[]> strMap = new HashMap<String, String[]>();
		strMap.put("ticket_id",strings);
		ywTicketService.deleteBatch(strMap);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}
	
}
