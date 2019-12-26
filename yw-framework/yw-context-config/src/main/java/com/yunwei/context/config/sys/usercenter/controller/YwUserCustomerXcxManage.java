package com.yunwei.context.config.sys.usercenter.controller;

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

import com.yunwei.context.sys.usercenter.model.YwUserCustomer;
import com.yunwei.context.sys.usercenter.model.YwUserCustomerXcx;
import com.yunwei.context.sys.usercenter.service.YwUserCustomerService;
import com.yunwei.context.sys.usercenter.service.YwUserCustomerXcxService;
import com.yunwei.common.user.SysUser;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.common.exception.BizException;

/**
 * 客户小程序信息控制层
* @ClassName: YwUserCustomerXcxManage 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年09月17日 下午15:48:20
*
 */
@Controller
@RequestMapping("/system/ywUserCustomerXcx")
public class YwUserCustomerXcxManage {
	private static Logger logger = Logger.getLogger(YwUserCustomerXcxManage.class);
	@Autowired
	private YwUserCustomerXcxService ywUserCustomerXcxService;
	@Autowired
	private YwUserCustomerService ywUserCustomerService;
 
	@RequestMapping
	public String ywUserCustomerXcxList(Model model){

		return "/system/usercustomerxcx/ywUserCustomerXcxList";
	}
	
	/**
	 * 查询字典集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public List<Map<String, Object>> getList(SysUser sysUser,YwUserCustomerXcx ywUserCustomerXcx,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
		map.putAll(MapUtil.toMap(ywUserCustomerXcx));
		List<YwUserCustomerXcx> list = ywUserCustomerXcxService.queryPage(map);
		return MapUtil.toMapList(list);
	}
	
	/**
	 * 查询分页情况
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("getPaginator")
	@ResponseBody
	public Paginator getPaginator(YwUserCustomerXcx ywUserCustomerXcx,@RequestParam(defaultValue = "10") int pageSize){
		int count = ywUserCustomerXcxService.queryTotals(ywUserCustomerXcx);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(SysUser sysUser,YwUserCustomerXcx ywUserCustomerXcx,String op_type,Model model){
		model.addAttribute("op_type", op_type);
		model.addAttribute("ywUserCustomer", ywUserCustomerService.queryList(new YwUserCustomer()));
		if(StringUtils.equals(op_type, "2")){
			model.addAttribute("ywUserCustomerXcx", ywUserCustomerXcxService.query(ywUserCustomerXcx));
		} else {
			model.addAttribute("ywUserCustomerXcx", ywUserCustomerXcx);
		}
		
		return "/system/usercustomerxcx/ywUserCustomerXcxEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(YwUserCustomerXcx ywUserCustomerXcx,String op_type){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			if(StringUtils.equals(op_type, "1")){
				ywUserCustomerXcxService.insert(ywUserCustomerXcx);
				map.put("error_no", "0");
				map.put("error_info", "添加成功");
			} else {
				ywUserCustomerXcxService.update(ywUserCustomerXcx);
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
	public Map<String,Object> delete(YwUserCustomerXcx ywUserCustomerXcx){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			ywUserCustomerXcxService.delete(ywUserCustomerXcx);
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
	public Map<String,Object> deleteBatch(YwUserCustomerXcx ywUserCustomerXcx){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			String[] id = ywUserCustomerXcx.getId().split(",");
			Map<String, String[]> strMap = new HashMap<String, String[]>();
			strMap.put("id",id);
			ywUserCustomerXcxService.deleteBatch(strMap);
			map.put("error_no", "0");
			map.put("error_info", "删除成功");
		} catch (Exception e) {
		    logger.info(e);
			throw new BizException(e);
		}
		return map;
	}	
}
