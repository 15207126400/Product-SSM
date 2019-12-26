package com.yunwei.product.backend.product.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.yunwei.common.annotation.NoLogin;
import com.yunwei.common.exception.BizException;
import com.yunwei.common.user.SysUser;
import com.yunwei.common.util.Base32;
import com.yunwei.common.util.DateUtil;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.NumberUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.common.util.RedisClientUtil;
import com.yunwei.context.sys.cache.YwDictionaryCache;
import com.yunwei.context.sys.cache.YwUserCustomerXcxCache;
import com.yunwei.context.sys.model.YwDictionary;
import com.yunwei.context.sys.usercenter.model.YwUser;
import com.yunwei.context.sys.usercenter.model.YwUserCustomer;
import com.yunwei.context.sys.usercenter.model.YwUserCustomerXcx;
import com.yunwei.context.sys.usercenter.service.YwUserCustomerService;
import com.yunwei.context.sys.usercenter.service.YwUserService;
import com.yunwei.product.backend.service.YwBaseconfigService;
import com.yunwei.product.backend.service.YwCouponCollectiondetailsService;
import com.yunwei.product.backend.service.YwCouponService;
import com.yunwei.product.backend.service.YwMemberService;
import com.yunwei.product.backend.service.YwOrderItemService;
import com.yunwei.product.backend.service.YwOrderPaymentService;
import com.yunwei.product.backend.service.YwOrderService;
import com.yunwei.product.backend.service.YwSmsBuyService;
import com.yunwei.product.backend.service.impl.ExcelExportServiceImpl;
import com.yunwei.product.backend.websocket.serivce.YwUserCustomerWebSocketSerivce;
import com.yunwei.product.common.backend.model.dto.YwOrderDto;
import com.yunwei.product.common.backend.model.form.YwOrderForm;
import com.yunwei.product.common.model.OrderCountProcedure;
import com.yunwei.product.common.model.OrderPriceProcedure;
import com.yunwei.product.common.model.YwBaseconfig;
import com.yunwei.product.common.model.YwCoupon;
import com.yunwei.product.common.model.YwMember;
import com.yunwei.product.common.model.YwOrder;
import com.yunwei.product.common.model.YwOrderItem;

/**
 * 
* @ClassName: YwOrderManage 
* @Description: 订单管理 
* @author zhangjh
* @date 2018年3月17日 下午2:50:21 
*
 */
@Controller
@RequestMapping("/product/ywOrder")
public class YwOrderManage {
	
	@Autowired
	private YwOrderPaymentService ywOrderPaymentService;
	@Autowired
	private YwMemberService ywMemberService;
	@Autowired
	private YwOrderService ywOrderService;
	@Autowired
	private YwOrderItemService ywOrderItemService;
	@Autowired
	private ExcelExportServiceImpl excelExportServiceImpl;
	@Autowired
	private YwDictionaryCache ywDictionaryCache;
	@Autowired
	private YwUserService ywUserService;
	@Autowired
	private YwUserCustomerService ywUserCustomerService;
	@Autowired
	private YwBaseconfigService ywBaseconfigService;
	@Autowired
	private YwUserCustomerXcxCache ywUserCustomerXcxCache;
	@Autowired
	private YwCouponService ywCouponService;
	@Autowired
	private YwCouponCollectiondetailsService ywCouponCollectiondetailsService;
	@Autowired
	private YwSmsBuyService ywSmsBuyService;
	@Autowired
	private YwUserCustomerWebSocketSerivce ywUserCustomerWebSocketSerivce;
	
	/**
	 * 订单模块
	 */
	@RequestMapping
	public String query_payment(SysUser sysUser,Model model,String order_status){
		YwUserCustomer ywUser = new YwUserCustomer();
		ywUser.setUser_id(sysUser.getUser_id());
		model.addAttribute("ywUser", ywUserCustomerService.query(ywUser));
		
		// 保存订单状态信息
		model.addAttribute("order_status", order_status);
		// 获取基本配置信息(自动确认收货天数)
		YwBaseconfig ywBaseconfig = new YwBaseconfig();
		ywBaseconfig.setConfig_id("order.delivery.days");
		ywBaseconfig = ywBaseconfigService.query(ywBaseconfig);
		if(ywBaseconfig != null){
			// 跳转订单列表页面前 , 查看并处理待收货订单 , 超过日期的订单自动确认收货
			YwOrder ywOrder = new YwOrder();
			ywOrder.setOrder_status("3");
			List<YwOrder> ywOrderList = ywOrderService.queryList(ywOrder);
			for(YwOrder item : ywOrderList){
				if(StringUtils.equals("3", item.getOrder_status())){
					ywOrderService.updateStatusByDateToSendProduct(item, Integer.valueOf(ywBaseconfig.getConfig_value()));
				}
			}
		}
		
		return "product/order/ywOrderList";
	}
	
	/**
	 * 查询字典集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public Map<String, Object> getList(SysUser sysUser,YwOrderForm ywOrderForm,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		//List<YwOrderDto> ywOrderDtos = null;
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
		ywOrderForm.setBranch_id(sysUser.getBranch_id());
		map.putAll(MapUtil.toMap(ywOrderForm));
		List<YwOrderDto> ywOrderDtos = ywOrderService.queryUnionPageListByMap(map);
		List<YwOrderDto> list = new ArrayList<YwOrderDto>();
		for (YwOrderDto yworderDto : ywOrderDtos) {
			/*Map<String, Object> resultMap = new HashMap<String, Object>();
			YwMember ywmember = ywMemberService.queryByOpenid(yworderDto.getOpenid());
			YwMember share_ywmember =ywMemberService.queryByOpenid(yworderDto.getShare_openid());
			resultMap.put("yworderDto", yworderDto);
			resultMap.put("nickname", Base32.decode(ywmember.getNickname()));
			resultMap.put("share_ywmember", Base32.decode(share_ywmember.getNickname()));*/
			
			yworderDto.setShare_nickname(Base32.decode(yworderDto.getShare_nickname()));
			yworderDto.setNickname(Base32.decode(yworderDto.getNickname()));
			list.add(yworderDto);
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
	public Paginator getPaginator(SysUser sysUser,YwOrderForm ywOrderForm,@RequestParam(defaultValue = "10") int pageSize){
		ywOrderForm.setBranch_id(sysUser.getBranch_id());
        int count = ywOrderService.queryTotalsByMap(MapUtil.toMap(ywOrderForm));
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(SysUser sysUser , YwOrder ywOrder,String op_type,Model model,HttpServletRequest request){
		model.addAttribute("op_type", op_type);
		//Object user = request.getSession().getAttribute("user");
		
		YwUserCustomer ywUserCustomer = new YwUserCustomer();
		ywUserCustomer.setUser_id(sysUser.getUser_id());
		ywUserCustomer = ywUserCustomerService.query(ywUserCustomer);
		ywOrder = ywOrderService.query(ywOrder);
		if(StringUtils.equals(op_type, "2")){
			YwMember ywMember = ywMemberService.queryByOpenid(ywOrder.getOpenid());
			if(StringUtils.isNotBlank(ywOrder.getShare_openid())){
				YwMember share_ywMember = ywMemberService.queryByOpenid(ywOrder.getShare_openid());
				model.addAttribute("share_nickname", Base32.decode(share_ywMember.getNickname()));
			}
			// 查出对应的订单项 , 做数据类型格式转换
			YwOrderItem ywOrderItem = new YwOrderItem();
			ywOrderItem.setOrder_sn(ywOrder.getOrder_sn());
			List<YwOrderItem> ywOrderItems = ywOrderItemService.queryList(ywOrderItem);
			List<Object> list = new ArrayList<Object>();
			for (YwOrderItem ywOrderItem2 : ywOrderItems) {
				Map<String, Object> map = new HashMap<String, Object>();
				String product_id = ywOrderItem2.getProduct_id();
				String order_ite_name = ywOrderItem2.getOrder_ite_name();
				String order_ite_sku = ywOrderItem2.getOrder_ite_sku();
				String order_ite_count = NumberUtil.round2(ywOrderItem2.getOrder_ite_count());
				String order_ite_price = NumberUtil.round2(ywOrderItem2.getOrder_ite_price());
				String order_ite_totalPrice = NumberUtil.mul(Double.valueOf(order_ite_price), Double.valueOf(order_ite_count));
				map.put("product_id", product_id);
				map.put("order_ite_name", order_ite_name);
				map.put("order_ite_sku", order_ite_sku);
				map.put("order_ite_count", order_ite_count);
				map.put("order_ite_price", order_ite_price);
				map.put("order_ite_totalPrice", order_ite_totalPrice);
				list.add(map);
			}
			if(StringUtils.isNotBlank(ywOrder.getCouponid())){
				YwCoupon ywCoupon = ywCouponService.query(Integer.valueOf(ywOrder.getCouponid()));
//				if(StringUtils.equals(ywCoupon.getCoupontype(), "1")){
//					model.addAttribute("couponName", "满" + ywCoupon.getManprice() + "减" + ywCoupon.getJianprice() + "元劵");
//					model.addAttribute("couponPrice", ywCoupon.getJianprice());
//				} else if(StringUtils.equals(ywCoupon.getCoupontype(), "2")){
//					model.addAttribute("couponName", ywCoupon.getZhekou() + "折扣劵");
//					String sum = NumberUtil.div(Double.valueOf(ywOrder.getOrder_totalprice()), Double.valueOf(ywCoupon.getZhekou()));
//					String couponPrice = NumberUtil.mul(10.0, Double.valueOf(sum));
//					model.addAttribute("couponPrice", couponPrice);
//				} else if(StringUtils.equals(ywCoupon.getCoupontype(), "3")){
//					model.addAttribute("couponName", ywCoupon.getDikou() + "元抵扣劵");
//					model.addAttribute("couponPrice", ywCoupon.getDikou());
//				}
				
			}
			model.addAttribute("ywOrder", ywOrder);
			model.addAttribute("user", ywUserCustomer);
			model.addAttribute("orderItem", list);
			if(ywMember != null){
				model.addAttribute("nickname", Base32.decode(ywMember.getNickname()));
			}
			return "product/order/ywOrderEdit";
		} else if(StringUtils.equals(op_type, "3")){
			model.addAttribute("ywOrder", ywOrderService.query(ywOrder));
			return "product/order/ywOrderSendProduct";
		}else {
			model.addAttribute("ywOrder", ywOrder);
			return "product/order/ywOrderEdit";
		}
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(SysUser sysUser,YwOrder ywOrder,String op_type){
		Map<String,Object> map = new HashMap<String, Object>();
		ywOrder.setBranch_id(sysUser.getBranch_id());
		if(StringUtils.equals(op_type, "1")){
			ywOrderService.insert(ywOrder);
			map.put("error_no", "0");
			map.put("error_info", "添加成功");
		} else {
			// 如果是发货 , 请求发送短信
			if(StringUtils.equals(ywOrder.getOrder_status(), "3")){
				ywOrderService.orderSendOutSendMsg(sysUser, ywOrder);
//				YwOrder order = ywOrderService.query(ywOrder);
//				order.setTransport_sn(ywOrder.getTransport_sn());
//				order.setOrder_status(ywOrder.getOrder_status());
//				order.setOrder_updatetime(new Date());
//				ywOrderService.update(order);
				map.put("error_no", "0");
				map.put("error_info", "发货成功");
			}else{
				ywOrder.setOrder_updatetime(new Date());
				ywOrderService.update(ywOrder);
				map.put("error_no", "0");
				map.put("error_info", "修改成功");
			}
		}
		return map;
	}
	
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Map<String,Object> delete(YwOrder ywOrder){
		Map<String,Object> map = new HashMap<String, Object>();
		ywOrderService.delete(ywOrder);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping("deleteBatch")
	@ResponseBody
	public Map<String,Object> deleteBatch(YwOrder ywOrder,String order_sns){
		Map<String,Object> map = new HashMap<String, Object>();
		String[] strings = order_sns.split(",");
		Map<String, String[]> strMap = new HashMap<String, String[]>();
		strMap.put("order_sn",strings);
		ywOrderService.deleteBatch(strMap);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}	
	
	/**
	 * 支付成功回调推送消息
	*@param request
	*@param response
	*@throws Exception
	*void
	 */
	@NoLogin
	@RequestMapping("messages")
    @ResponseBody
	public void messages(String app_id,String order_sn,HttpServletRequest request) throws Exception{
		// 查询小程序信息
		YwUserCustomerXcx ywUserCustomerXcx = ywUserCustomerXcxCache.getYwUserCustomerXcxByWxAppid(app_id);
		
		// 查询客户登录信息
		YwUser user = new YwUser();
		user.setUser_id(ywUserCustomerXcx.getUser_id());
		user = ywUserService.query(user);
		request.getSession().setAttribute("user", user);
		
		// 查询订单待发货信息
		YwOrder ywOrder = new YwOrder();
		ywOrder.setOrder_status("2");
		int order_count = ywOrderService.queryTotals(ywOrder);
		
		// 将待发货订单数量存储到redis中
		RedisClientUtil.set(ywUserCustomerXcx.getUser_id(), String.valueOf(order_count));
		// 发送订单信息
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("order_sn", order_sn);
		jsonObject.put("order_count", order_count);
		ywUserCustomerWebSocketSerivce.sendMessage(ywUserCustomerXcx.getUser_id(), jsonObject.toJSONString());
	}
	
	/**
	 * 新导出Excel表格
	 * @param ywOrder
	 * @param response 
	 * @throws Exception
	 */
	@RequestMapping("exportExcel")
	public void btnSendOutGoods(SysUser sysUser,YwOrderForm ywOrderForm,HttpServletRequest request,HttpServletResponse response) throws Exception{
		JSONObject jsonObject = new JSONObject();
		String excel_name = "客户订单统计表单";
		String sheet_name = "订单统计";
		long start = System.currentTimeMillis();
		List<String> columnNames = new ArrayList<String>();
		columnNames.add("订单号");
		columnNames.add("订单类型");
		columnNames.add("商品名称");
		columnNames.add("规格属性");
		columnNames.add("数量");
		columnNames.add("单价");
		columnNames.add("总金额");
		columnNames.add("收货人");
		columnNames.add("联系电话");
		columnNames.add("收货地址");
		columnNames.add("订单状态");
		columnNames.add("物流编号");
		columnNames.add("创建时间");
		columnNames.add("订单备注");
		List<String[]> dataList = new ArrayList<String[]>();
		// 根据条件查询数据
		Map<String, Object> paramMap = MapUtil.toMap(ywOrderForm);
		
		List<YwOrder> orders= ywOrderService.importExcelByOrder(paramMap);
		if(orders.size() > 0){
			for(int j = 0; j < orders.size(); j++){
	        	YwOrder order = orders.get(j);
	        	List<YwOrderItem> orderItems = order.getYwOrderItems();
				String rer_ite_total = "0.00";							// 总金额
				
				for (int i = 0; i < orderItems.size(); i++) {
					YwOrderItem orderItem = orderItems.get(i);
					if(StringUtils.isNotBlank(orderItem.getOrder_ite_price()) && StringUtils.isNotBlank(orderItem.getOrder_ite_count())){
						rer_ite_total = NumberUtil.mul(Double.parseDouble(orderItem.getOrder_ite_price()), Double.parseDouble(orderItem.getOrder_ite_count()));
					}
					String order_type = "";						// 订单类型
					String order_status = "";					// 订单类型
					if(StringUtils.isNotBlank(order.getOrder_type())){
						YwDictionary dic_order_type = ywDictionaryCache.getDictionary("1016", order.getOrder_type());
						order_type = dic_order_type.getDic_subvalue();
					}
					if(StringUtils.isNotBlank(order.getOrder_status())){
						YwDictionary dic_order_status = ywDictionaryCache.getDictionary("1017", order.getOrder_status());
						order_status = dic_order_status.getDic_subvalue();
					}
					
					
					String[] strings = {
							order.getOrder_sn(),
							order_type,
							orderItem.getOrder_ite_name(),
							orderItem.getOrder_ite_sku(),
							orderItem.getOrder_ite_count(),
							orderItem.getOrder_ite_price(),
							String.valueOf(rer_ite_total),
							order.getOrder_name(),
							order.getOrder_tel(),
							order.getOrder_address(),
							order_status,
							order.getTransport_sn(),
							DateUtil.format(order.getOrder_createtime(), DateUtil.DATE_TIME_FORMAT),
							order.getOrder_remark()
					};
					dataList.add(strings);
					jsonObject.put("rowCount", i+1);
					jsonObject.put("timeMillis",(System.currentTimeMillis() - start)/1000);
		    		ywUserCustomerWebSocketSerivce.sendMessage(sysUser.getUser_id(), jsonObject.toJSONString());
				}
			}
        	jsonObject.put("exportFlag", 1);
    		ywUserCustomerWebSocketSerivce.sendMessage(sysUser.getUser_id(), jsonObject.toJSONString());
		}
		System.out.println("查询耗时："+ (System.currentTimeMillis() - start)/1000 + "秒");
		excelExportServiceImpl.exportWithResponse(excel_name, sheet_name, columnNames, dataList,request , response);
	}
	
	
	
	/**
	 * 调用存储过程查询已支付订单量与交易额
	*
	*@return
	*Map<String,Object>
	 */
	@RequestMapping("getOrderAndOfflineStatis")
	@ResponseBody
	public Map<String, Object> getOrderAndOfflineStatis(SysUser sysUser){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Map<String, Object> prarmMap = ywOrderService.getOrderAndOfflineStatis(sysUser.getBranch_id() != null ?sysUser.getBranch_id() : "");
			OrderPriceProcedure orderPriceList = (OrderPriceProcedure) prarmMap.get("orderPriceList");
			List<OrderCountProcedure> orderCountList = (List<OrderCountProcedure>) prarmMap.get("orderCountList");
			map.put("orderPriceList", orderPriceList);
			map.put("orderCountList", orderCountList);
			
		} catch (Exception e) {
			throw new BizException(e);
		}
		
		
		return map;
	}
	
}
