package com.yunwei.product.infobase.product.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yunwei.common.exception.BizException;
import com.yunwei.common.util.DateUtil;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.NumberUtil;
import com.yunwei.common.util.OrderUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.context.message.template.support.WxTemplateMessage;
import com.yunwei.context.payment.support.WxPayService;
import com.yunwei.context.payment.util.WXUtil;
import com.yunwei.context.payment.util.WxModelUtil;
import com.yunwei.context.sys.cache.YwUserCustomerXcxCache;
import com.yunwei.context.sys.service.YwPayService;
import com.yunwei.context.sys.usercenter.model.YwUserCustomerXcx;
import com.yunwei.context.token.support.WxAccessToken;
import com.yunwei.product.common.model.QhzSalonOrder;
import com.yunwei.product.common.model.YwCouponCollectiondetails;
import com.yunwei.product.common.model.YwMember;
import com.yunwei.product.common.model.YwMemberAddress;
import com.yunwei.product.common.model.YwMemberRole;
import com.yunwei.product.common.model.YwOrder;
import com.yunwei.product.common.model.YwOrderItem;
import com.yunwei.product.common.model.YwOrderOffline;
import com.yunwei.product.common.model.YwOrderPayment;
import com.yunwei.product.common.model.YwPoints;
import com.yunwei.product.common.model.YwPointsDetail;
import com.yunwei.product.common.model.YwRefund;
import com.yunwei.product.common.model.YwSmsBuy;
import com.yunwei.product.infobase.constant.ConstantFunctionsFront;
import com.yunwei.product.infobase.service.QhzSalonOrderService;
import com.yunwei.product.infobase.service.YwCouponCollectiondetailsService;
import com.yunwei.product.infobase.service.YwMemberAddressService;
import com.yunwei.product.infobase.service.YwMemberRoleService;
import com.yunwei.product.infobase.service.YwMemberService;
import com.yunwei.product.infobase.service.YwOrderItemService;
import com.yunwei.product.infobase.service.YwOrderOfflineService;
import com.yunwei.product.infobase.service.YwOrderPaymentService;
import com.yunwei.product.infobase.service.YwOrderService;
import com.yunwei.product.infobase.service.YwPointsDetailService;
import com.yunwei.product.infobase.service.YwPointsExchangeService;
import com.yunwei.product.infobase.service.YwPointsService;
import com.yunwei.product.infobase.service.YwRefundService;
import com.yunwei.product.infobase.service.YwShopcartService;
import com.yunwei.product.infobase.service.YwSmsBuyService;

/**
 * 
* @ClassName: YwOrderPaymentController 
* @Description: 小程序订单及支付功能 
* @author 晏飞
* @date 2017年12月26日 下午3:39:23 
*
*/
@Controller
public class YwOrderPaymentController {
	
	private static Logger logger = Logger.getLogger(YwOrderPaymentController.class);
	@Autowired
	private YwMemberService ywMemberService;
	@Autowired
	private YwMemberRoleService ywMemberRoleService;
	@Autowired
	private YwOrderItemService ywOrderItemService;
	@Autowired
	private YwOrderPaymentService ywOrderPaymentService;
	@Autowired
	private YwOrderService ywOrderService;
	@Autowired
	private YwShopcartService ywShopcartService;
	@Autowired
	private YwMemberAddressService ywMemberAddressService;
	@Autowired
	private YwCouponCollectiondetailsService ywCouponCollectiondetailsService;
	@Autowired
	private YwSmsBuyService ywSmsBuyService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private YwUserCustomerXcxCache ywUserCustomerXcxCache;
	@Autowired
	private YwPointsDetailService ywPointsDetailService;
	@Autowired
	private YwPointsService ywPointsService;
	@Autowired
	private YwOrderOfflineService ywOrderOfflineService;
	@Autowired
	private YwRefundService ywRefundService;
	@Autowired
	private YwPayService ywPayService;
	
	@Autowired
	private QhzSalonOrderService qhzSalonOrderService;	//启恒智沙龙系统订单
	@Autowired
	private WxTemplateMessage wxTemplateMessage;
	@Autowired
	private WxAccessToken wxAccessToken;
	
	
	/**
	 * 创建线上订单
	 * @throws ParseException 
	 */
	@RequestMapping(ConstantFunctionsFront.YW_POT_00015)
	@ResponseBody
	public Map<String, Object> saveOrderDetail(@RequestBody YwOrder ywOrder) throws ParseException{
		Map<String, Object> map = new HashMap<String, Object>();
			
		try {
			ywOrderService.insert(ywOrder);	
			map.put("order_sn", ywOrder.getOrder_sn());
			map.put("teamFoundData", ywOrder.getTeamFoundData());
			map.put("teamFollowData", ywOrder.getTeamFollowData());
			map.put("seckillRecordData", ywOrder.getSeckillRecordData());
		} catch (Exception e) {
			logger.error("订单提交失败，请稍后提交", e);
			throw new BizException("订单提交失败，请稍后提交",e);
		}
		return map;
	}
	
	/**
	 * 购物车创建订单时,删除购物车选中项
	 * @throws ParseException 
	 */
	@RequestMapping(ConstantFunctionsFront.YW_POT_00020)
    @ResponseBody
	public Map<String, Object> saveOrderPaycat(String openid,String ids){
			JSONArray data = JSONArray.parseArray(ids);
		try {
			for (int i = 0; i < data.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("createBy", openid);
				map.put("id", data.get(i).toString());
				ywShopcartService.deletePaycat(map);
				
			}
		} catch (Exception e) {
			throw new BizException(e);
		}
		return null;
	}
	
	
	/**
	 * 查询地址信息 (默认地址--->用户信息记录到订单库中)
	 */
	@RequestMapping(ConstantFunctionsFront.YW_POT_00022)
    @ResponseBody
	public Map<String, Object> queryAddForDefult(String createBy){
		Map<String, Object> addmap = new HashMap<String, Object>();
		addmap.put("createBy", createBy);
		addmap.put("defaultAddress", "1");
		List<YwMemberAddress> addresslist = ywMemberAddressService.queryAddress(addmap);
		Map<String, Object> map = new HashMap<String, Object>();
		if (addresslist != null && addresslist.size() > 0) {
			boolean have = true;
			map.put("add", addresslist.get(0));
			map.put("have", have);
			
		}else{
			boolean have = false;
			map.put("have", have);
		}
		return map;
	}
	
	/**
	 * 查询订单商品信息
	 */
	@RequestMapping(ConstantFunctionsFront.YW_POT_00021)
    @ResponseBody
	public List<YwOrderItem> queryOrderDetail(YwOrderItem ywOrderItem){
		List<YwOrderItem> orderItem = ywOrderItemService.queryList(ywOrderItem);
		
		return orderItem;
	}
	

	/**
	 * 用户发起支付请求 后台处理（创建支付请求条件）
	*
	*@return 
	 * @throws ParseException 
	 */
	@RequestMapping(ConstantFunctionsFront.YW_POT_00023)
    @ResponseBody
	public Map<String, Object> weixinOrderCreate(YwOrder ywOrder) throws UnsupportedEncodingException, DocumentException, ParseException{
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			String out_trade_no = ywOrder.getOrder_sn();
			double order_totalprice = Double.parseDouble(ywOrder.getOrder_totalprice());		//微信按分计算单位,需乘100设置为以元单位
			Map<String, Object> data = ywPayService.createJSAPIOrder(out_trade_no, NumberUtil.decimalBlankFormat(order_totalprice*100), ywOrder.getOpenid());
			map.put("data", data);
			map.put("order_totalprice", order_totalprice);
			
			// 将微信订单信息更新到支付信息表
			YwOrderPayment ywOrderPayment = new YwOrderPayment();
			ywOrderPayment.setOrder_sn(ywOrder.getOrder_sn());
			ywOrderPayment.setOrder_pay_remark(JSONObject.toJSONString(data));
			ywOrderPayment.setOrder_pay_method("2");
			ywOrderPaymentService.update(ywOrderPayment);
			System.out.println("支付条件信息 : " + data);
		} catch (Exception e) {
			throw new BizException("支付信息创建失败",e);
		}
		
		return map;
	}
	
	/**
	 * 支付成功回调
	*
	*@param request
	*@param response
	*@throws Exception
	*void
	 */
	@RequestMapping(ConstantFunctionsFront.YW_POT_00024)
    @ResponseBody
	public void payment_result(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		InputStream inputStream ;  
        StringBuffer sb = new StringBuffer();  
        inputStream = request.getInputStream();  
        String s ;  
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));  
        while ((s = in.readLine()) != null){  
            sb.append(s);  
        }  
        in.close();  
        inputStream.close();  
        Map<String, String> m = new HashMap<String, String>();  
        m = WXUtil.doXMLParse(sb.toString());  
        SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();        
        Iterator it = m.keySet().iterator();  
        while (it.hasNext()) {  
            String parameter = (String) it.next();  
            String parameterValue = m.get(parameter);  
            String v = "";  
            if(null != parameterValue) {  
                v = parameterValue.trim();  
            }  
            packageParams.put(parameter, v);  
        }
        String app_id = (String) request.getAttribute("wx_appid");
		YwUserCustomerXcx ywUserXcx = ywUserCustomerXcxCache.getYwUserCustomerXcxByWxAppid(app_id);
        // String key = WxModelUtil.KEY; //秘钥 
		logger.info("支付成功回调app_id="+app_id);
        if(WXUtil.isTenpaySign("UTF-8", packageParams,ywUserXcx.getXcx_pay_key())) {  
            String resXml = "";  
            if("SUCCESS".equals((String)packageParams.get("result_code"))){  
            	logger.info("支付成功回调result_code="+(String)packageParams.get("result_code"));
            	//得到返回的参数
                String openid = (String)packageParams.get("openid");  
                String transaction_id = (String)packageParams.get("transaction_id");  
                String out_trade_no = (String)packageParams.get("out_trade_no");  
                String total_fee = (String)packageParams.get("total_fee"); 
                
                //云维系统支付回调业务操作
                YwOrder ywOrder = new YwOrder();
                ywOrder.setOrder_sn(out_trade_no);
                YwOrder order = ywOrderService.query(ywOrder);
                if(order != null){
                	// 为线上订单
                	if(StringUtils.equals(order.getOrder_status(), "1")){
                    	//支付成功后,将订单信息修改为支付成功状态,并保留订单生成与结束时间,一并存到数据库
                        YwOrderPayment orderPaymet = new YwOrderPayment();
                        orderPaymet.setOrder_sn(out_trade_no);
                        YwOrderPayment ywOrderPayment = ywOrderPaymentService.query(orderPaymet);
                        ywOrderPayment.setOrder_pay_updatetime(new Date());
                        ywOrderPayment.setOrder_pay_status("1");			//修改支付状态为成功
                        ywOrderPayment.setOrder_pay_errorNo((String)packageParams.get("result_code"));
                        ywOrderPayment.setOrder_pay_errorInfo((String)packageParams.get("return_msg"));
                        ywOrderPayment.setOrder_pay_code((String)packageParams.get("transaction_id"));// 微信支付单号
                        ywOrderPaymentService.update(ywOrderPayment);
                        
                       
                        System.out.println("-------------------订单支付回调-------------------订单状态："+order.getOrder_status());
                        // 判断是否为团购订单
                        if(StringUtils.equals(order.getOrder_type(), "2")){
                        	if(StringUtils.equals(order.getOrder_status(), "1")){
                        		ywOrder.setOrder_status("10");	// 待分享
                        	} else {
                        		ywOrder.setOrder_status(order.getOrder_status());	// 状态不变
                        	}
                        } else {
                        	// 避免微信多次回调修改团购订单的问题
                    		ywOrder.setOrder_status("2");	// 待发货
                        }
                        
                        if(StringUtils.equals(order.getOrder_type(), "4")){
                        	// 如果订单类型为4 , 说明是短信订单
                        	YwSmsBuy ywSmsBuy = new YwSmsBuy();
                        	ywSmsBuy.setOrder_sn(out_trade_no);
                        	ywSmsBuy.setBuy_status("2");
                        	ywSmsBuy.setBuy_updatetime(new Date());
                        	ywSmsBuyService.update(ywSmsBuy);
                        }
                        //修改订单信息
                        ywOrder.setOrder_paytime(ywOrderPayment.getOrder_pay_updatetime());  			//修改支付时间
                        ywOrderService.update(ywOrder);
                        System.out.println("-------------------订单更新后-------------------订单状态："+ywOrder.getOrder_status());
                        if(!ywOrder.getCouponid().equals("0")){
                        	//获取优惠券编号 , 将使用状态修改为已经使用
                            int couponid = Integer.parseInt(ywOrder.getCouponid());
                            YwCouponCollectiondetails coupon = ywCouponCollectiondetailsService.query(couponid);
                            coupon.setDetails_status("1");
                            ywCouponCollectiondetailsService.update(coupon);
                        }
                        
                        // 异步发送订单消息到后台服务器
                        new Thread(){
                        	public void run() {
                        		ywOrderService.orderMessageSend(app_id, out_trade_no);
                        	};
                        }.start();
                        
                        resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"  
                                + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";  
                        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());  
                        out.write(resXml.getBytes());  
                        out.flush();  
                        out.close();
                    }
                }
                
                //启恒智沙龙注册支付回调业务操作
                QhzSalonOrder qhzSalonOrder = new QhzSalonOrder();
                qhzSalonOrder.setOrder_sn(out_trade_no);
                QhzSalonOrder salonOrder = qhzSalonOrderService.query(qhzSalonOrder);
                if(salonOrder != null){
                	salonOrder.setStatus("2");
                	salonOrder.setUpdate_datetime(new Date());
                    int num = qhzSalonOrderService.update(salonOrder);
                    System.out.println("-------------------订单更新后-------------------订单状态："+salonOrder.getStatus());
                    if(num > 0){
                    	//支付成功后,将订单信息修改为支付成功状态,并保留订单生成与结束时间,一并存到数据库
                        YwOrderPayment orderPaymet = new YwOrderPayment();
                        orderPaymet.setOrder_sn(out_trade_no);
                        YwOrderPayment ywOrderPayment = ywOrderPaymentService.query(orderPaymet);
                        ywOrderPayment.setOrder_pay_updatetime(new Date());
                        ywOrderPayment.setOrder_pay_status("1");			//修改支付状态为成功
                        ywOrderPayment.setOrder_pay_errorNo((String)packageParams.get("result_code"));
                        ywOrderPayment.setOrder_pay_errorInfo((String)packageParams.get("return_msg"));
                        ywOrderPayment.setOrder_pay_code((String)packageParams.get("transaction_id"));// 微信支付单号
                        ywOrderPaymentService.update(ywOrderPayment);
                    }
                }
            } else {  
              System.out.println("【支付回调失败】");
            }  
        } else{  
            System.out.println("【支付秘钥有误】");
        }
	}


	/**
	 * 查询订单列表信息
	 */
	@RequestMapping(ConstantFunctionsFront.YW_POT_00026)
	@ResponseBody
	public List<Map<String, Object>> queryPaymentByType(YwOrder ywOrder ,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int pageSize){
		List<Map<String, Object>> maps = new ArrayList<Map<String,Object>>();
		List<YwOrder> ywOrders = null;
		
		try {
			if (page <= 0) {
				page = 1;// 第一页
			}
			int start = (page - 1) * pageSize;
			Map<String, Object> map = MapUtil.toMap(ywOrder);
			map.put("begin", start);
			map.put("end", pageSize); // 设置每页显示几条数据
			ywOrders = ywOrderService.queryUnionList(map);
		} catch (Exception e) {
			logger.error("请求列表查询错误", e);
			throw new BizException(e);
		}
		maps = MapUtil.toMapList(ywOrders);
		return maps;
	}
	
	/**
	 * 查询订单总数
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(ConstantFunctionsFront.YW_POT_00029)
	@ResponseBody
	public Paginator getPaginator(YwOrder ywOrder,@RequestParam(defaultValue = "10") int pageSize){
		
		int count = ywOrderService.queryTotals(ywOrder);
		return new Paginator(1, pageSize, count);
	}	
	

	/**
	 * 编辑订单
	 */
	@RequestMapping(ConstantFunctionsFront.YW_POT_00027)
	@ResponseBody
	public Map<String, Object> submitOrder(YwOrder ywOrder){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(StringUtils.equals(ywOrder.getOrder_status(), "7")){
				ywOrder.setOrder_successtime(new Date());
			}
			ywOrderService.update(ywOrder);
			// 获取该订单
			YwOrder order = ywOrderService.query(ywOrder);
			
			if(StringUtils.equals(ywOrder.getOrder_status(), "7")){
				// 用户确认收货 , 计入积分
				//ywOrderService.orderAddPoints(ywOrder.getOpenid(), ywOrder.getOrder_sn());
				
				if(StringUtils.equals(order.getOrder_type(), "1")){				// 如果是普通订单 , 增加积分至用户账号
					// 用户确认收货后 , 计入该笔订单消费的金额
					ywOrderService.orderAddPrice(ywOrder.getOpenid(), order.getOrder_sn());
					// 计入积分
					ywPointsService.addPointsByUser(order.getOrder_points(), order.getOpenid());
					// 修改积分明细状态为已生效
					ywPointsDetailService.updateByStatus(order.getOrder_sn(), "2");
				}else if(StringUtils.equals(order.getOrder_type(), "5")){		// 如果是积分订单 , 扣除账户对应积分
					// 扣除积分
					double points = Double.valueOf(order.getOrder_totalprice());
					int point = (int)points;
					ywPointsService.deductPointsByUser(String.valueOf(point), order.getOpenid());
					// 修改积分明细状态为已生效
					ywPointsDetailService.updateByStatus(order.getOrder_sn(), "2");
				}else if(StringUtils.equals(ywOrder.getOrder_type(), "3")){		// 如果是分销订单
					// 用户确认收货后 , 计入该笔订单消费的金额
					ywOrderService.orderAddPrice(ywOrder.getOpenid(), order.getOrder_sn());
					// 用户确认收货 , 计入所获佣金
					ywOrderService.orderAddCommission(ywOrder.getOrder_sn());
				}
				
				map.put("error_info", "确认收货成功");
			} else if(StringUtils.equals(ywOrder.getOrder_status(), "8")){
				map.put("error_info", "取消订单成功 ");
			} else if(StringUtils.equals(ywOrder.getOrder_status(), "9")){
				map.put("error_info", "删除订单成功");
			} else if(StringUtils.equals(ywOrder.getOrder_status(), "2")){
				map.put("error_info", "取消退款成功");
			} else if(StringUtils.equals(ywOrder.getOrder_status(), "12")){
				map.put("error_info", "退款申请成功");
			}
		} catch (Exception e) {
			throw new BizException(e);
		}
		
		return map;
	}
	
	/**
	 * 查询订单详情
	 */
	@RequestMapping(ConstantFunctionsFront.YW_POT_00031)
	@ResponseBody
	public Map<String, Object> queryByOrder(YwOrder ywOrder){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("order_sn", ywOrder.getOrder_sn());
			ywOrder = ywOrderService.queryUnionList(paramMap).get(0);
			// 查询订单地址信息
			/*if(StringUtils.isNotEmpty(ywOrder.getOrder_address())){
				YwMemberAddress address = ywMemberAddressService.queryByAddress(Integer.valueOf(ywOrder.getOrder_address()));
				map.put("address", address);
			}*/
			Map<String, Object> addressMap = new HashMap<String, Object>();
			addressMap.put("name", ywOrder.getOrder_name());
			addressMap.put("address", ywOrder.getOrder_address());
			addressMap.put("tel", ywOrder.getOrder_tel());
			map.put("address", addressMap);
			map.put("ywOrder", ywOrder);
		} catch (Exception e) {
			throw new BizException(e);
		}
		return map;
	}
	
	/**
	 * 批量修改订单状态
	 */
	@RequestMapping(ConstantFunctionsFront.YW_POT_00053)
	@ResponseBody
	public Map<String,Object> orderBatch(YwOrder ywOrder , String order_sns){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			String[] strings = order_sns.split(",");
			List<YwOrder> orders = new ArrayList<YwOrder>();
			if(strings.length > 0){
				for(String order_sn : strings){
					YwOrder order = new YwOrder();
					order.setOrder_sn(order_sn);
					order.setOrder_status(ywOrder.getOrder_status());
					orders.add(order);
				}
			}
			
			ywOrderService.updateBatch(orders,new String[]{"order_status"});
			
			if(StringUtils.equals(ywOrder.getOrder_status(), "5")){
				List<YwRefund> refunds = new ArrayList<YwRefund>();
				for(String order_sn : strings){
					YwOrder order = new YwOrder();
					order.setOrder_sn(order_sn);
					order = ywOrderService.query(order);
					YwRefund refund = new YwRefund();
					refund.setRefund_sn(OrderUtil.getOrderNo());
					refund.setOrder_sn(order_sn);
					refund.setRefund_price(order.getOrder_totalprice());
					refund.setUser_id(order.getOpenid());
					refund.setRefund_type(ywOrder.getOrder_type());
					refund.setRefund_status("0");
					refund.setRefund_createtime(new Date());
					
					refunds.add(refund);
				}
				ywRefundService.insertBatch(refunds);
			}
			
		} catch (Exception e) {
			logger.error("订单批量更新失败 ", e);
			throw new BizException("订单批量更新失败");
		}
		map.put("error_info", "更新成功");
		return map;
		
	}
	
	
}
