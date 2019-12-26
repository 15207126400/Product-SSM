package com.yunwei.product.backend.product.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunwei.common.user.SysUser;
import com.yunwei.common.util.Base32;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.product.backend.service.YwMemberService;
import com.yunwei.product.backend.service.YwOrderPaymentService;
import com.yunwei.product.common.backend.model.dto.YwOrderPaymentDto;
import com.yunwei.product.common.backend.model.form.YwOrderPaymentForm;
import com.yunwei.product.common.model.YwMember;
import com.yunwei.product.common.model.YwOrderPayment;

/**
 * 
* @ClassName: YwOrderPaymentManage 
* @Description: TODO(TODO) 订单支付信息
* @author zhangjh
* @date 2018年3月30日 下午12:08:53 
*
 */
@Controller
@RequestMapping("/product/ywOrderPayment")
public class YwOrderPaymentManage {
	
	@Autowired
	private YwOrderPaymentService ywOrderPaymentService;
	@Autowired
	private YwMemberService ywMemberService;

	@RequestMapping
	public String ywOrderPaymentList(Model model){
		return "/product/orderpayment/ywOrderPaymentList";
	}
	
	/**
	 * 查询字典集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public Map<String, Object> getList(SysUser sysUser,YwOrderPayment ywOrderPayment,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		List<YwOrderPaymentDto> ywOrderPaymentDtos = null;
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
		ywOrderPayment.setBranch_id(sysUser.getBranch_id());
		map.putAll(MapUtil.toMap(ywOrderPayment));
		ywOrderPaymentDtos = ywOrderPaymentService.queryUnionMemberList(map);
		List<YwOrderPaymentDto> list = new ArrayList<YwOrderPaymentDto>();
		for (YwOrderPaymentDto ywOrderPaymentDto : ywOrderPaymentDtos) {
			ywOrderPaymentDto.setNickname(Base32.decode(ywOrderPaymentDto.getNickname()));
			list.add(ywOrderPaymentDto);
			if(list.size() == pageSize) break;
		}
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
	public Paginator getPaginator(SysUser sysUser,YwOrderPayment ywOrderPayment,@RequestParam(defaultValue = "10") int pageSize){
		ywOrderPayment.setBranch_id(sysUser.getBranch_id());
		int count = ywOrderPaymentService.queryTotals(ywOrderPayment);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(YwOrderPayment ywOrderPayment,String op_type,Model model){
		model.addAttribute("op_type", op_type);
		if(StringUtils.equals(op_type, "2")){
			ywOrderPayment = ywOrderPaymentService.query(ywOrderPayment);
			model.addAttribute("ywOrderPayment", ywOrderPayment);
			YwMember ywMember = ywMemberService.queryByOpenid(ywOrderPayment.getOpen_id());
			if(ywMember != null){
				model.addAttribute("nickname",Base32.decode(ywMember.getNickname()));
			}
		} else {
			model.addAttribute("ywOrderPayment", ywOrderPayment);
		}
		
		return "/product/orderpayment/ywOrderPaymentEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(YwOrderPayment ywOrderPayment,String op_type){
		Map<String,Object> map = new HashMap<String, Object>();
		if(StringUtils.equals(op_type, "1")){
			ywOrderPaymentService.insert(ywOrderPayment);
			map.put("error_no", "0");
			map.put("error_info", "添加成功");
		} else {
			ywOrderPaymentService.update(ywOrderPayment);
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
	public Map<String,Object> delete(YwOrderPayment ywOrderPayment){
		Map<String,Object> map = new HashMap<String, Object>();
		ywOrderPaymentService.delete(ywOrderPayment);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping("deleteBatch")
	@ResponseBody
	public Map<String,Object> deleteBatch(YwOrderPayment ywOrderPayment,YwOrderPaymentForm ywOrderPaymentForm){
		Map<String,Object> map = new HashMap<String, Object>();
		String[] order_pay_id = ywOrderPaymentForm.getOrder_pay_id().split(",");
		String[] order_sn = ywOrderPaymentForm.getOrder_sn().split(",");
		
		Map<String, String[]> strMap = new HashMap<String, String[]>();
		strMap.put("order_pay_id",order_pay_id);
		strMap.put("order_sn",order_sn);
		ywOrderPaymentService.deleteBatch(strMap);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}	
}
