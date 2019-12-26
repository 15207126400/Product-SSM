package com.yunwei.product.backend.business.controller;

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

import com.yunwei.product.backend.service.YwWithdrawService;
import com.yunwei.product.common.model.YwWithdraw;
import com.yunwei.common.user.SysUser;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.common.exception.BizException;

/**
 * 商家提现申请表控制层
* @ClassName: YwWithdrawManage 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2019年03月12日 下午17:31:49
*
 */
@Controller
@RequestMapping("/yw_withdraw/ywWithdraw")
public class YwWithdrawManage {
	private static Logger logger = Logger.getLogger(YwWithdrawManage.class);
	@Autowired
	private YwWithdrawService ywWithdrawService;
 
	@RequestMapping
	public String ywWithdrawList(Model model){

		return "/business/withdraw/ywWithdrawList";
	}
	
	/**
	 * 查询列表集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public List<Map<String, Object>> getList(SysUser sysUser,YwWithdraw ywWithdraw,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
		map.putAll(MapUtil.toMap(ywWithdraw));
		List<YwWithdraw> list = ywWithdrawService.queryPage(map);
		return MapUtil.toMapList(list);
	}
	
	/**
	 * 查询分页情况
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("getPaginator")
	@ResponseBody
	public Paginator getPaginator(YwWithdraw ywWithdraw,@RequestParam(defaultValue = "10") int pageSize){
		int count = ywWithdrawService.queryTotals(ywWithdraw);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(SysUser sysUser,YwWithdraw ywWithdraw,String op_type,Model model){
		model.addAttribute("op_type", op_type);
		if(StringUtils.equals(op_type, "2")){
			model.addAttribute("ywWithdraw", ywWithdrawService.query(ywWithdraw));
		} else {
			model.addAttribute("ywWithdraw", ywWithdraw);
		}
		
		return "/business/withdraw/ywWithdrawEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(YwWithdraw ywWithdraw,String op_type){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			if(StringUtils.equals(op_type, "1")){
				ywWithdrawService.insert(ywWithdraw);
				map.put("error_no", "0");
				map.put("error_info", "添加成功");
			} else {
				ywWithdrawService.update(ywWithdraw);
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
	public Map<String,Object> delete(YwWithdraw ywWithdraw){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			ywWithdrawService.delete(ywWithdraw);
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
	public Map<String,Object> deleteBatch(YwWithdraw ywWithdraw){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			/*String[] id = ywWithdraw.getId().split(",");
			Map<String, String[]> strMap = new HashMap<String, String[]>();
			strMap.put("id",id);
			ywWithdrawService.deleteBatch(strMap);
			map.put("error_no", "0");
			map.put("error_info", "删除成功");*/
		} catch (Exception e) {
		    logger.info(e);
			throw new BizException(e);
		}
		return map;
	}	
}
