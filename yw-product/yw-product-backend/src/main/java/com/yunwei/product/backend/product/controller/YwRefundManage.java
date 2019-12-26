package com.yunwei.product.backend.product.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.product.backend.service.YwRefundService;
import com.yunwei.product.common.model.YwRefund;

/**
 * 
* @ClassName: YwRefundManage 
* @Description: 订单管理 
* @author zhangjh
* @date 2018年3月17日 下午2:50:21 
*
 */
@Controller
@RequestMapping("/product/ywRefund")
public class YwRefundManage {
	
	@Autowired
	private YwRefundService ywRefundService;
	
	/**
	 * 订单模块
	 */
	//查询订单信息
	@RequestMapping
	public String query_payment(Integer number, Model model){
		
		return "product/refund/ywRefundList";
	}
	
	/**
	 * 查询字典集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public Map<String, Object> getList(YwRefund ywRefund,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
		map.putAll(MapUtil.toMap(ywRefund));
		List<YwRefund> list = ywRefundService.queryPage(map);
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
	public Paginator getPaginator(YwRefund ywRefund,@RequestParam(defaultValue = "10") int pageSize){
		int count = ywRefundService.queryTotals(ywRefund);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(YwRefund ywRefund,String op_type,Model model){
		model.addAttribute("op_type", op_type);
		if(StringUtils.equals(op_type, "2")){
			model.addAttribute("ywRefund", ywRefundService.query(ywRefund));
		} else {
			model.addAttribute("ywRefund", ywRefund);
		}
		
		return "product/refund/ywRefundEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(YwRefund ywRefund,String op_type){
		Map<String,Object> map = new HashMap<String, Object>();
		if(StringUtils.equals(op_type, "1")){
			ywRefundService.insert(ywRefund);
			map.put("error_no", "0");
			map.put("error_info", "添加成功");
		} else {
			ywRefundService.update(ywRefund);
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
	public Map<String,Object> delete(YwRefund ywRefund){
		Map<String,Object> map = new HashMap<String, Object>();
		ywRefundService.delete(ywRefund);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}
	
}
