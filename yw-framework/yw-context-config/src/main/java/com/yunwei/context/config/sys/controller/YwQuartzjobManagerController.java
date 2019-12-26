package com.yunwei.context.config.sys.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunwei.common.cache.CacheManager;
import com.yunwei.common.exception.BizException;
import com.yunwei.common.user.SysUser;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.context.config.model.YwQuartzjob;
import com.yunwei.context.config.service.YwQuartzjobService;

/**
 * 定时任务控制器
* @ClassName: YwQuartzjobManage 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年6月28日 下午1:51:58 
*
 */
@Controller
@RequestMapping("/system/ywQuartzjob")
public class YwQuartzjobManagerController {
	private static Logger logger = Logger.getLogger(YwQuartzjobManagerController.class);
	@Autowired
	private YwQuartzjobService ywQuartzjobService;
	@Autowired
	private CacheManager cacheManager;

	@RequestMapping
	public String ywQuartzjobList(Model model){
//		String string =  PropertiesUtils.get("func.org.locate.scope");
		return "/system/quartzjob/ywQuartzjobList";
	}
	
	/**
	 * 查询字典集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public Map<String, Object> getList(SysUser sysUser,YwQuartzjob ywQuartzjob,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
		map.putAll(MapUtil.toMap(ywQuartzjob));
		List<YwQuartzjob> list = ywQuartzjobService.queryPage(map);
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
	public Paginator getPaginator(YwQuartzjob ywQuartzjob,@RequestParam(defaultValue = "10") int pageSize){
		int count = ywQuartzjobService.queryTotals(ywQuartzjob);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(SysUser sysUser,YwQuartzjob ywQuartzjob,String op_type,Model model){
		model.addAttribute("op_type", op_type);
		if(StringUtils.equals(op_type, "2")){
			model.addAttribute("ywQuartzjob", ywQuartzjobService.query(ywQuartzjob));
		} else {
			model.addAttribute("ywQuartzjob", ywQuartzjob);
		}
		
		return "/system/quartzjob/ywQuartzjobEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(@Valid YwQuartzjob ywQuartzjob,String op_type){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			ywQuartzjob.setUpdate_datetime(new Date());
			if(StringUtils.equals(op_type, "1")){
				ywQuartzjobService.insert(ywQuartzjob);
				map.put("error_no", "0");
				map.put("error_info", "添加成功");
			} else {
				ywQuartzjobService.update(ywQuartzjob);
				map.put("error_no", "0");
				map.put("error_info", "修改成功");
			}
			cacheManager.refreshOne("ywQuartzjobCache");
		} catch (Exception e) {
			logger.info("添加或修改失败：{}",e);
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
	public Map<String,Object> delete(YwQuartzjob ywQuartzjob){
		Map<String,Object> map = new HashMap<String, Object>();
		ywQuartzjobService.delete(ywQuartzjob);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		cacheManager.refreshOne("ywQuartzjobCache");
		return map;
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping("deleteBatch")
	@ResponseBody
	public Map<String,Object> deleteBatch(YwQuartzjob ywQuartzjob){
		Map<String,Object> map = new HashMap<String, Object>();
		String[] job_name = ywQuartzjob.getJob_name().split(",");
		String[] description = ywQuartzjob.getDescription().split(",");
		Map<String, String[]> strMap = new HashMap<String, String[]>();
		strMap.put("job_name",job_name);
		strMap.put("description",description);
		ywQuartzjobService.deleteBatch(strMap);
		cacheManager.refreshOne("ywQuartzjobCache");
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}	
}
