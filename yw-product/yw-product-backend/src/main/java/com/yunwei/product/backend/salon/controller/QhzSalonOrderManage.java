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

import com.yunwei.product.backend.service.QhzSalonOrderService;
import com.yunwei.product.common.model.QhzSalonOrder;
import com.yunwei.common.user.SysUser;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.common.exception.BizException;

/**
 * 沙龙注册订单信息表控制层
* @ClassName: QhzSalonOrderManage 
* @Description: TODO(TODO) 
* @author yanf
* @date 2019年06月25日 上午10:57:04
*
 */
@Controller
@RequestMapping("/qhz_salon_order/qhzSalonOrder")
public class QhzSalonOrderManage {
	private static Logger logger = Logger.getLogger(QhzSalonOrderManage.class);
	@Autowired
	private QhzSalonOrderService qhzSalonOrderService;
 
	@RequestMapping
	public String qhzSalonOrderList(Model model){

		return "/salon/order/qhzSalonOrderList";
	}
	
	/**
	 * 查询列表集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public List<Map<String, Object>> getList(SysUser sysUser,QhzSalonOrder qhzSalonOrder,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
		map.putAll(MapUtil.toMap(qhzSalonOrder));
		List<QhzSalonOrder> list = qhzSalonOrderService.queryPage(map);
		return MapUtil.toMapList(list);
	}
	
	/**
	 * 查询分页情况
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("getPaginator")
	@ResponseBody
	public Paginator getPaginator(QhzSalonOrder qhzSalonOrder,@RequestParam(defaultValue = "10") int pageSize){
		int count = qhzSalonOrderService.queryTotals(qhzSalonOrder);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(SysUser sysUser,QhzSalonOrder qhzSalonOrder,String op_type,Model model){
		model.addAttribute("op_type", op_type);
		if(StringUtils.equals(op_type, "2")){
			model.addAttribute("qhzSalonOrder", qhzSalonOrderService.query(qhzSalonOrder));
		} else {
			model.addAttribute("qhzSalonOrder", qhzSalonOrder);
		}
		
		return "/salon/order/qhzSalonOrderEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(QhzSalonOrder qhzSalonOrder,String op_type){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			if(StringUtils.equals(op_type, "1")){
				qhzSalonOrderService.insert(qhzSalonOrder);
				map.put("error_no", "0");
				map.put("error_info", "添加成功");
			} else {
				qhzSalonOrderService.update(qhzSalonOrder);
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
	public Map<String,Object> delete(QhzSalonOrder qhzSalonOrder){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			qhzSalonOrderService.delete(qhzSalonOrder);
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
	public Map<String,Object> deleteBatch(QhzSalonOrder qhzSalonOrder){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			String[] id = qhzSalonOrder.getId().split(",");
			Map<String, String[]> strMap = new HashMap<String, String[]>();
			strMap.put("id",id);
			qhzSalonOrderService.deleteBatch(strMap);
			map.put("error_no", "0");
			map.put("error_info", "删除成功");
		} catch (Exception e) {
		    logger.info(e);
			throw new BizException(e);
		}
		return map;
	}
}
