package com.yunwei.product.backend.sales.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yunwei.common.exception.BizException;
import com.yunwei.common.user.SysUser;
import com.yunwei.common.util.DateUtil;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.product.backend.service.YwSeckillActivityService;
import com.yunwei.product.backend.service.YwSeckillTimeService;
import com.yunwei.product.common.model.YwSeckillTime;

/**
 * 系统字典控制器
 * @author zhangz
 *
 */
@Controller
@RequestMapping("/sales/ywSeckillTime")
public class YwSeckillTimeManage {
	
	@Autowired
	private YwSeckillTimeService ywSeckillTimeService;

	@RequestMapping
	public String ywSeckillTimeList(Model model){
//		String string =  PropertiesUtils.get("func.org.locate.scope");
		return "/sales/seckilltime/ywSeckillTimeList";
	}
	
	/**
	 * 查询字典集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public Map<String, Object> getList(SysUser sysUser,YwSeckillTime ywSeckillTime,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
		map.putAll(MapUtil.toMap(ywSeckillTime));
		List<YwSeckillTime> list = ywSeckillTimeService.queryPage(map);
		map.put("error_no", "0");
		map.put("error_info", "查询成功");
		map.put("resultList", list);
		return map;
	}
	
	/**
	 * 查询秒杀时间集合
	 * @param ywSeckillTime
	 * @return
	 */
	@RequestMapping("getSeckillTimeList")
	@ResponseBody
	public List<YwSeckillTime> getSeckillTimeList(SysUser sysUser,YwSeckillTime ywSeckillTime){
		return ywSeckillTimeService.queryList(ywSeckillTime); 
	}
	
	/**
	 * 查询分页情况
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("getPaginator")
	@ResponseBody
	public Paginator getPaginator(SysUser sysUser,YwSeckillTime ywSeckillTime,@RequestParam(defaultValue = "10") int pageSize){
		int count = ywSeckillTimeService.queryTotals(ywSeckillTime);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(YwSeckillTime ywSeckillTime,String op_type,Model model){
		model.addAttribute("op_type", op_type);
		if(StringUtils.equals(op_type, "2")){
			ywSeckillTime = ywSeckillTimeService.query(ywSeckillTime);
			model.addAttribute("ywSeckillTime", ywSeckillTime);
		} else {
			model.addAttribute("ywSeckillTime", ywSeckillTime);
		}
		
		return "/sales/seckilltime/ywSeckillTimeEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(@RequestParam(value = "file", required = false) MultipartFile file, SysUser sysUser ,@Valid YwSeckillTime ywSeckillTime,String op_type){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			// 判断文件是否为空
			if(file != null && file.getSize() != 0){
				ywSeckillTime.setTime_title_url(ywSeckillTimeService.uploadSeckillTimeImage(file, ywSeckillTime));
			}
			
			if(StringUtils.equals(op_type, "1")){
				// 查询日期下的时间点是否已经添加
				YwSeckillTime seckillTime = new YwSeckillTime();
				seckillTime.setSeckill_date(ywSeckillTime.getSeckill_date());
				seckillTime.setSeckill_starttime(ywSeckillTime.getSeckill_starttime());
				seckillTime = ywSeckillTimeService.query(seckillTime);
				if(seckillTime == null){
					ywSeckillTimeService.insert(ywSeckillTime);
					map.put("error_no", "0");
					map.put("error_info", "添加成功");
				} else {
					throw new BizException("您在开始日期["+ DateUtil.format(ywSeckillTime.getSeckill_date(), DateUtil.DATE_FORMAT) +"]下已添加该时间["+ ywSeckillTime.getSeckill_starttime() +"]");
				}
				
			} else {
				ywSeckillTimeService.update(ywSeckillTime);
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
	public Map<String,Object> delete(YwSeckillTime ywSeckillTime){
		Map<String,Object> map = new HashMap<String, Object>();
		ywSeckillTimeService.delete(ywSeckillTime);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping("deleteBatch")
	@ResponseBody
	public Map<String,Object> deleteBatch(YwSeckillTime ywSeckillTime){
		Map<String,Object> map = new HashMap<String, Object>();
		String[] time_id = ywSeckillTime.getTime_id().split(",");
		Map<String, String[]> strMap = new HashMap<String, String[]>();
		strMap.put("time_id",time_id);
		ywSeckillTimeService.deleteBatch(strMap);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}	
}
