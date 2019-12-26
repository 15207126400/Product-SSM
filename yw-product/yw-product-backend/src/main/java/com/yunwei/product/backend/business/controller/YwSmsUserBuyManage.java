package com.yunwei.product.backend.business.controller;

import java.util.Date;
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

import com.alibaba.fastjson.JSONObject;
import com.yunwei.common.cache.CacheManager;
import com.yunwei.common.exception.BizException;
import com.yunwei.common.user.SysUser;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.NumberUtil;
import com.yunwei.common.util.OrderUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.context.payment.util.WxModelUtil;
import com.yunwei.context.sys.usercenter.model.YwUser;
import com.yunwei.product.backend.service.YwOrderPaymentService;
import com.yunwei.product.backend.service.YwOrderService;
import com.yunwei.product.backend.service.YwSmsBuyService;
import com.yunwei.product.backend.service.YwSmsComboService;
import com.yunwei.product.common.model.YwOrderPayment;
import com.yunwei.product.common.model.YwSmsBuy;
import com.yunwei.product.common.model.YwSmsCombo;

/**
 * 短信购买(用户)
 * @author zhangz
 *
 */
@Controller
@RequestMapping("/business/ywSmsBuy")
public class YwSmsUserBuyManage {
	
	@Autowired
	private YwSmsBuyService ywSmsBuyService;
	@Autowired
	private YwSmsComboService ywSmsComboService;
	@Autowired
	private YwOrderService ywOrderService;
	@Autowired
	private CacheManager cacheManager;
	@Autowired
	private YwOrderPaymentService ywOrderPaymentService;

	@RequestMapping
	public String ywSmsBuyList(Model model,YwUser ywUser,HttpServletRequest request){
		YwUser user = (YwUser) request.getSession().getAttribute("user");
		YwSmsBuy ywSmsBuy = new YwSmsBuy();
		ywSmsBuy.setUser_id(user.getUser_id());
		YwSmsBuy smsBuy = ywSmsBuyService.query(ywSmsBuy);
		model.addAttribute("ywSmsBuy", smsBuy);
		
		return "/business/smsbuy/ywSmsBuyList";
	}
	
	/**
	 * 查询字典集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public Map<String, Object> getList(YwUser ywUser,YwSmsBuy ywSmsBuy,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
		map.putAll(MapUtil.toMap(ywSmsBuy));
		List<YwSmsBuy> list = ywSmsBuyService.queryPage(map);
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
	public Paginator getPaginator(YwSmsBuy ywSmsBuy,@RequestParam(defaultValue = "10") int pageSize){
		int count = ywSmsBuyService.queryTotals(ywSmsBuy);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(YwSmsBuy ywSmsBuy,String op_type,Model model){
		model.addAttribute("op_type", op_type);
		if(StringUtils.equals(op_type, "2")){
			model.addAttribute("ywSmsBuy", ywSmsBuyService.query(ywSmsBuy));
		} else {
			List<YwSmsCombo> ywSmsCombo = ywSmsComboService.queryList(new YwSmsCombo());
			model.addAttribute("ywSmsBuy", ywSmsBuy);
			model.addAttribute("ywSmsCombos", ywSmsCombo);
		}
		
		return "/business/smsbuy/ywSmsBuyEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(@Valid YwSmsBuy ywSmsBuy,String op_type){
		Map<String,Object> map = new HashMap<String, Object>();
		if(StringUtils.equals(op_type, "1")){
			ywSmsBuyService.insert(ywSmsBuy);
			map.put("error_no", "0");
			map.put("error_info", "添加成功");
		} else {
			ywSmsBuyService.update(ywSmsBuy);
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
	public Map<String,Object> delete(YwSmsBuy ywSmsBuy){
		Map<String,Object> map = new HashMap<String, Object>();
		ywSmsBuyService.delete(ywSmsBuy);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping("deleteBatch")
	@ResponseBody
	public Map<String,Object> deleteBatch(YwSmsBuy ywSmsBuy,String buy_ids){
		Map<String,Object> map = new HashMap<String, Object>();
		String[] strings = buy_ids.split(",");
		Map<String, String[]> strMap = new HashMap<String, String[]>();
		strMap.put("buy_id",strings);
		ywSmsBuyService.deleteBatch(strMap);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}	
	
	/**
	 * 创建短信订单
	 */
	@RequestMapping("createOrder")
	@ResponseBody
	public Map<String, Object> createOrder(SysUser sysUser,YwSmsBuy ywSmsBuy,HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			// 保存短信购买订单信息
			ywSmsBuy.setUser_id(sysUser.getUser_id());
			ywSmsBuy.setOrder_sn(OrderUtil.getOrderNo());
			ywSmsBuy.setBuy_status("0");
			ywSmsBuy.setBuy_usenumber("0");
			ywSmsBuy.setBuy_createtime(new Date());
			ywSmsBuyService.insert(ywSmsBuy);
			// 创建微信订单号
			map = ywOrderService.createNATIVEOrder(ywSmsBuy.getOrder_sn(), NumberUtil.decimalBlankFormat(Double.valueOf(ywSmsBuy.getBuy_price())*100), "【云维科技】-短信套餐购买");
			
			// 将微信订单信息更新到支付信息表
			YwOrderPayment ywOrderPayment = new YwOrderPayment();
			ywOrderPayment.setOpen_id(sysUser.getUser_id());
			ywOrderPayment.setOrder_pay_price(ywSmsBuy.getBuy_price());
			ywOrderPayment.setOrder_sn(ywSmsBuy.getOrder_sn());
			ywOrderPayment.setOrder_pay_createtime(new Date());
			ywOrderPayment.setOrder_pay_remark(JSONObject.toJSONString(map));
			ywOrderPayment.setOrder_pay_returnUrl(WxModelUtil.notify_url);
			ywOrderPayment.setOrder_pay_status("0");
			ywOrderPayment.setOrder_pay_method("2");
			ywOrderPaymentService.insert(ywOrderPayment);
			// 返回订单号
			map.put("order_sn", ywSmsBuy.getOrder_sn());
		} catch (Exception e) {
			throw new BizException(e);
		}
		return map;
	}
	
	
	/**
	 * 跳转短信支付页面
	 */
	@RequestMapping("hrefToPayPath")
	public String hrefToPayPath(YwSmsBuy ywSmsBuy , Model model){
		if(StringUtils.isEmpty(ywSmsBuy.getOrder_sn())){
			model.addAttribute("ywSmsBuy",ywSmsBuy);
		}else{
			YwOrderPayment ywOrderPayment = new YwOrderPayment();
			ywOrderPayment.setOrder_sn(ywSmsBuy.getOrder_sn());
			ywOrderPayment = ywOrderPaymentService.query(ywOrderPayment);
			if(StringUtils.isNotEmpty(ywOrderPayment.getOrder_pay_remark())){
				// 根据订单号查找支付信息 , 取出支付备注字段中的微信端支付参数 , 将参数字符串转换为json对象 , 
				JSONObject aaa = JSONObject.parseObject(ywOrderPayment.getOrder_pay_remark());
				// 取出该字段中的code_url值(二维码支付) , 并返回给页面 , 直接展示上次未支付完成的二维码
				String code_url = aaa.getString("code_url");
				model.addAttribute("code_url",code_url);
			}
			YwSmsBuy smsBuy = new YwSmsBuy();
			smsBuy.setOrder_sn(ywSmsBuy.getOrder_sn());
			model.addAttribute("ywSmsBuy",ywSmsBuyService.query(smsBuy));
		}
		return "/business/smsbuy/ywSmsOrder";
		
	}
	
}
