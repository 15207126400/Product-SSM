package com.yunwei.product.backend.salon.controller;

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

import com.yunwei.product.backend.service.QhzSalonMeetingService;
import com.yunwei.product.common.model.QhzSalonMeeting;
import com.yunwei.common.user.SysUser;
import com.yunwei.common.util.DateUtil;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.common.util.RedisClientUtil;
import com.yunwei.common.exception.BizException;

/**
 * 沙龙注册会议信息模块控制层
* @ClassName: QhzSalonMeetingManage 
* @Description: TODO(TODO) 
* @author yanf
* @date 2019年07月18日 上午09:13:19
*
 */
@Controller
@RequestMapping("/qhz_salon_meeting/qhzSalonMeeting")
public class QhzSalonMeetingManage {
	private static Logger logger = Logger.getLogger(QhzSalonMeetingManage.class);
	@Autowired
	private QhzSalonMeetingService qhzSalonMeetingService;
 
	@RequestMapping
	public String qhzSalonMeetingList(Model model){

		return "/salon/meeting/qhzSalonMeetingList";
	}
	
	/**
	 * 查询列表集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public Map<String, Object> getList(SysUser sysUser,QhzSalonMeeting qhzSalonMeeting,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
		//查询条件
		if(StringUtils.isNotBlank(qhzSalonMeeting.getName())){
			map.put("name", qhzSalonMeeting.getName());
		}
		if(StringUtils.isNotBlank(qhzSalonMeeting.getAddress())){
			map.put("address", qhzSalonMeeting.getAddress());
		}
		String start_day = DateUtil.format(qhzSalonMeeting.getStart_day(), DateUtil.DATE_FORMAT);
		if(StringUtils.isNotBlank(start_day)){
			map.put("start_day", start_day);
		}
		//map.putAll(MapUtil.toMap(qhzSalonMeeting));
		List<QhzSalonMeeting> list = qhzSalonMeetingService.queryPage(map);
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
	public Paginator getPaginator(QhzSalonMeeting qhzSalonMeeting,@RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		//查询条件
		if(StringUtils.isNotBlank(qhzSalonMeeting.getName())){
			map.put("name", qhzSalonMeeting.getName());
		}
		if(StringUtils.isNotBlank(qhzSalonMeeting.getAddress())){
			map.put("address", qhzSalonMeeting.getAddress());
		}
		String start_day = DateUtil.format(qhzSalonMeeting.getStart_day(), DateUtil.DATE_FORMAT);
		if(StringUtils.isNotBlank(start_day)){
			map.put("start_day", start_day);
		}
		
		int count = qhzSalonMeetingService.queryTotals(map);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(SysUser sysUser,QhzSalonMeeting qhzSalonMeeting,String op_type,Model model){
		model.addAttribute("op_type", op_type);
		if(StringUtils.equals(op_type, "2")){
			model.addAttribute("qhzSalonMeeting", qhzSalonMeetingService.query(qhzSalonMeeting));
		} else {
			model.addAttribute("qhzSalonMeeting", qhzSalonMeeting);
		}
		
		return "/salon/meeting/qhzSalonMeetingEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(QhzSalonMeeting qhzSalonMeeting,String op_type){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			if(StringUtils.equals(op_type, "1")){
				qhzSalonMeetingService.insert(qhzSalonMeeting);
				map.put("error_no", "0");
				map.put("error_info", "添加成功");
			} else {
				qhzSalonMeetingService.update(qhzSalonMeeting);
				map.put("error_no", "0");
				map.put("error_info", "修改成功");
			}
		} catch (Exception e) {
		    logger.info(e);
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
	public Map<String,Object> delete(QhzSalonMeeting qhzSalonMeeting){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			qhzSalonMeetingService.delete(qhzSalonMeeting);
			map.put("error_no", "0");
			map.put("error_info", "删除成功");
		} catch (Exception e) {
		    logger.info(e);
			throw new BizException(e);
		}
		return map;
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping("deleteBatch")
	@ResponseBody
	public Map<String,Object> deleteBatch(QhzSalonMeeting qhzSalonMeeting){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			String[] id = qhzSalonMeeting.getId().split(",");
			Map<String, String[]> strMap = new HashMap<String, String[]>();
			strMap.put("id",id);
			qhzSalonMeetingService.deleteBatch(strMap);
			map.put("error_no", "0");
			map.put("error_info", "删除成功");
		} catch (Exception e) {
		    logger.info(e);
			throw new BizException(e);
		}
		return map;
	}
}
