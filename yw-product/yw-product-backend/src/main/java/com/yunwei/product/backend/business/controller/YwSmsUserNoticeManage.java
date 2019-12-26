package com.yunwei.product.backend.business.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunwei.common.cache.CacheManager;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.context.sys.usercenter.model.YwUser;
import com.yunwei.product.backend.service.YwSmsNoticeService;
import com.yunwei.product.common.model.YwSmsNotice;

/**
 * 短信通知(用户)
 * @author zhangz
 *
 */
@Controller
@RequestMapping("/business/ywSmsNotice")
public class YwSmsUserNoticeManage {
	
	@Autowired
	private YwSmsNoticeService ywSmsNoticeService;
	@Autowired
	private CacheManager cacheManager;

	@RequestMapping
	public String ywSmsNoticeList(Model model,YwUser ywUser,HttpServletRequest request){
		YwUser user = (YwUser) request.getSession().getAttribute("user");
		YwSmsNotice ywSmsNotice = new YwSmsNotice();
		ywSmsNotice.setUser_id(user.getUser_id());
		YwSmsNotice smsNotice = ywSmsNoticeService.query(ywSmsNotice);
		model.addAttribute("ywSmsNotice",smsNotice);
		
		return "/sms/smsnotice/ywSmsNoticeList";
	}
	
	/**
	 * 查询字典集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public Map<String, Object> getList(YwUser ywUser,YwSmsNotice ywSmsNotice,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
		map.putAll(MapUtil.toMap(ywSmsNotice));
		List<YwSmsNotice> list = ywSmsNoticeService.queryPage(map);
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
	public Paginator getPaginator(YwSmsNotice ywSmsNotice,@RequestParam(defaultValue = "10") int pageSize){
		int count = ywSmsNoticeService.queryTotals(ywSmsNotice);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(YwSmsNotice ywSmsNotice,String op_type,Model model){
		model.addAttribute("op_type", op_type);
		if(StringUtils.equals(op_type, "2")){
			model.addAttribute("ywSmsNotice", ywSmsNoticeService.query(ywSmsNotice));
		} else {
			model.addAttribute("ywSmsNotice", ywSmsNotice);
		}
		
		return "/sms/smsnotice/ywSmsNoticeEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(@Valid YwSmsNotice ywSmsNotice,String op_type){
		Map<String,Object> map = new HashMap<String, Object>();
		if(StringUtils.equals(op_type, "1")){
			ywSmsNoticeService.insert(ywSmsNotice);
			map.put("error_no", "0");
			map.put("error_info", "添加成功");
		} else {
			ywSmsNoticeService.update(ywSmsNotice);
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
	public Map<String,Object> delete(YwSmsNotice ywSmsNotice){
		Map<String,Object> map = new HashMap<String, Object>();
		ywSmsNoticeService.delete(ywSmsNotice);
		map.put("error_no", "0");
		return map;
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping("deleteBatch")
	@ResponseBody
	public Map<String,Object> deleteBatch(YwSmsNotice ywSmsNotice,String sms_ids){
		Map<String,Object> map = new HashMap<String, Object>();
		String[] strings = sms_ids.split(",");
		Map<String, String[]> strMap = new HashMap<String, String[]>();
		strMap.put("sms_id",strings);
		ywSmsNoticeService.deleteBatch(strMap);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}	
	
}
