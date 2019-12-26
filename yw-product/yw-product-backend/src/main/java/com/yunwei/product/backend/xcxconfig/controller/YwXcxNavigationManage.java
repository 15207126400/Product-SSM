package com.yunwei.product.backend.xcxconfig.controller;

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

import com.yunwei.product.backend.service.YwXcxNavigationService;
import com.yunwei.product.common.model.YwXcxNavigation;
import com.yunwei.common.user.SysUser;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.common.cache.CacheManager;
import com.yunwei.common.exception.BizException;
import com.yunwei.context.sys.usercenter.model.YwUserCustomer;
import com.yunwei.context.sys.usercenter.model.YwUserCustomerXcx;
import com.yunwei.context.sys.usercenter.service.YwUserCustomerService;
import com.yunwei.context.sys.usercenter.service.YwUserCustomerXcxService;

/**
 * 小程序跳转导航配置表控制层
* @ClassName: YwXcxNavigationManage 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年10月29日 下午17:49:22
*
 */
@Controller
@RequestMapping("/xcxconfig/ywXcxNavigation")
public class YwXcxNavigationManage {
	private static Logger logger = Logger.getLogger(YwXcxNavigationManage.class);
	@Autowired
	private YwXcxNavigationService ywXcxNavigationService;
	@Autowired
	private YwUserCustomerService ywUserCustomerService;
	@Autowired
	private YwUserCustomerXcxService ywUserCustomerXcxService;
	@Autowired
	private CacheManager cacheManager;
 
	@RequestMapping
	public String ywXcxNavigationList(Model model){

		return "/xcxconfig/xcxnavigation/ywXcxNavigationList";
	}
	
	/**
	 * 查询字典集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public List<Map<String, Object>> getList(SysUser sysUser,YwXcxNavigation ywXcxNavigation,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
//		Map<String, Object> map = new HashMap<String, Object>();
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
//		map.put("begin", start);
//		map.put("end", pageSize); // 设置每页显示几条数据
//		map.putAll(MapUtil.toMap(ywXcxNavigation));
		List<YwXcxNavigation> list = ywXcxNavigationService.queryListPage(ywXcxNavigation,start,end);
		return MapUtil.toMapList(list);
	}
	
	/**
	 * 查询分页情况
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("getPaginator")
	@ResponseBody
	public Paginator getPaginator(YwXcxNavigation ywXcxNavigation,@RequestParam(defaultValue = "10") int pageSize){
		int count = ywXcxNavigationService.queryTotals(ywXcxNavigation);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(YwXcxNavigation ywXcxNavigation,String op_type,Model model){
		model.addAttribute("op_type", op_type);
		List<YwUserCustomerXcx> ywUserCustomerXcx = ywUserCustomerXcxService.queryList(new YwUserCustomerXcx());
		model.addAttribute("ywUserCustomerXcx", ywUserCustomerXcx);
		
		if(StringUtils.equals(op_type, "2")){
			model.addAttribute("ywXcxNavigation", ywXcxNavigationService.query(ywXcxNavigation));
		} else {
			model.addAttribute("ywXcxNavigation", ywXcxNavigation);
		}
		
		return "/xcxconfig/xcxnavigation/ywXcxNavigationEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(SysUser sysUser,YwXcxNavigation ywXcxNavigation,String op_type){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			if(StringUtils.equals(op_type, "1")){
				YwXcxNavigation xcxNavigation = new YwXcxNavigation();
				xcxNavigation.setUser_id(sysUser.getUser_id());
				xcxNavigation.setWx_appid(ywXcxNavigation.getWx_appid());
				xcxNavigation.setNav_path(ywXcxNavigation.getNav_path());
				xcxNavigation.setStatus("1");
				xcxNavigation = ywXcxNavigationService.query(xcxNavigation);
				if(xcxNavigation == null){
					ywXcxNavigationService.insert(ywXcxNavigation);
					map.put("error_no", "0");
					map.put("error_info", "添加成功");
				}else{
					throw new BizException("跳转导航["+ xcxNavigation.getNav_name() +"]路径地址已经添加，不能重复添加");
				}
				
			} else {
				ywXcxNavigationService.update(ywXcxNavigation);
				map.put("error_no", "0");
				map.put("error_info", "修改成功");
			}
			//cacheManager.refreshOne("ywXcxNavigationCache");
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
	public Map<String,Object> delete(YwXcxNavigation ywXcxNavigation){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			ywXcxNavigationService.delete(ywXcxNavigation);
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
	public Map<String,Object> deleteBatch(YwXcxNavigation ywXcxNavigation){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			String[] id = ywXcxNavigation.getId().split(",");
			Map<String, String[]> strMap = new HashMap<String, String[]>();
			strMap.put("id",id);
			ywXcxNavigationService.deleteBatch(strMap);
			map.put("error_no", "0");
			map.put("error_info", "删除成功");
			
		} catch (Exception e) {
		    logger.info(e);
			throw new BizException(e);
		}
		return map;
	}	
}
