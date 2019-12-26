package com.yunwei.product.backend.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunwei.product.backend.service.YwBaseconfigService;
import com.yunwei.product.common.model.YwBaseConfigContent;
import com.yunwei.product.common.model.YwBaseconfig;
import com.yunwei.common.user.SysUser;
import com.yunwei.common.util.FastJsonUtil;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.common.exception.BizException;

/**
 * 基本配置信息控制层
* @ClassName: YwBaseconfigManage 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年09月25日 下午15:56:52
*
 */
@Controller
@RequestMapping("/system/ywBaseconfig")
public class YwBaseconfigManage {
	private static Logger logger = Logger.getLogger(YwBaseconfigManage.class);
	@Autowired
	private YwBaseconfigService ywBaseconfigService;
 
	@RequestMapping
	public String ywBaseconfigList(Model model,YwBaseconfig ywBaseconfig,String op_type){
		// 查询所有配置信息
		List<YwBaseconfig> baseconfigs = ywBaseconfigService.queryList(ywBaseconfig);
		if(CollectionUtils.isNotEmpty(baseconfigs)){
			for(YwBaseconfig baseconfig : baseconfigs){
				if(!StringUtils.equals(baseconfig.getConfig_type(), "1")){
					List<YwBaseConfigContent> ywBaseConfigContents = FastJsonUtil.parseList(baseconfig.getConfig_content(), YwBaseConfigContent.class);
					baseconfig.setYwBaseConfigContents(ywBaseConfigContents);
				}
			}
		}
		model.addAttribute("ywBaseconfigs", baseconfigs);
		return "/system/baseconfig/ywBaseconfigEdit";
	}
	
	/**
	 * 查询字典集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public List<Map<String, Object>> getList(SysUser sysUser,YwBaseconfig ywBaseconfig,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
//		map.put("begin", start);
//		map.put("end", pageSize); // 设置每页显示几条数据
//		map.putAll(MapUtil.toMap(ywBaseconfig));
		List<YwBaseconfig> list = ywBaseconfigService.queryListPage(ywBaseconfig,start,end);
		return MapUtil.toMapList(list);
	}
	
	/**
	 * 查询分页情况
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("getPaginator")
	@ResponseBody
	public Paginator getPaginator(YwBaseconfig ywBaseconfig,@RequestParam(defaultValue = "10") int pageSize){
		int count = ywBaseconfigService.queryTotals(ywBaseconfig);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(SysUser sysUser,YwBaseconfig ywBaseconfig,String op_type,Model model){
		model.addAttribute("op_type", op_type);
		if(StringUtils.equals(op_type, "2")){
			model.addAttribute("ywBaseconfig", ywBaseconfigService.query(ywBaseconfig));
		} else {
			model.addAttribute("ywBaseconfig", ywBaseconfig);
		}
		
		return "/system/baseconfig/ywBaseconfigEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(@RequestBody List<YwBaseconfig> ywBaseconfigs){
		Map<String,Object> map = new HashMap<String, Object>();
		try {   
				ywBaseconfigService.updateBatch(ywBaseconfigs);
				map.put("error_no", "0");
				map.put("error_info", "保存成功");
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
	public Map<String,Object> delete(YwBaseconfig ywBaseconfig){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			ywBaseconfigService.delete(ywBaseconfig);
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
	public Map<String,Object> deleteBatch(YwBaseconfig ywBaseconfig){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			String[] id = ywBaseconfig.getId().split(",");
			Map<String, String[]> strMap = new HashMap<String, String[]>();
			strMap.put("id",id);
			ywBaseconfigService.deleteBatch(strMap);
			map.put("error_no", "0");
			map.put("error_info", "删除成功");
		} catch (Exception e) {
		    logger.info(e);
			throw new BizException(e);
		}
		return map;
	}	
}
