package com.yunwei.product.infobase.salon.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.yunwei.common.exception.BizException;
import com.yunwei.common.util.DateUtil;
import com.yunwei.common.util.NumberUtil;
import com.yunwei.common.util.OrderUtil;
import com.yunwei.context.message.template.support.WxTemplateMessage;
import com.yunwei.context.payment.util.WXUtil;
import com.yunwei.context.payment.util.WxModelUtil;
import com.yunwei.context.sys.cache.YwUserCustomerXcxCache;
import com.yunwei.context.sys.service.YwPayService;
import com.yunwei.context.sys.usercenter.model.YwUserCustomerXcx;
import com.yunwei.context.sys.usercenter.service.YwUserCustomerXcxService;
import com.yunwei.context.token.support.WxAccessToken;
import com.yunwei.product.common.model.QhzSalonAccount;
import com.yunwei.product.common.model.QhzCurriculum;
import com.yunwei.product.common.model.QhzSalonOrder;
import com.yunwei.product.common.model.QhzSalonOrderItem;
import com.yunwei.product.common.model.QhzSmsDetail;
import com.yunwei.product.common.model.YwCouponCollectiondetails;
import com.yunwei.product.common.model.YwOrder;
import com.yunwei.product.common.model.YwOrderOffline;
import com.yunwei.product.common.model.YwOrderPayment;
import com.yunwei.product.common.model.YwSmsBuy;
import com.yunwei.product.infobase.constant.ConstantFunctionsFront;
import com.yunwei.product.infobase.product.controller.YwOrderPaymentController;
import com.yunwei.product.infobase.service.QhzSalonAccountService;
import com.yunwei.product.infobase.service.QhzCurriculumService;
import com.yunwei.product.infobase.service.QhzSalonOrderItemService;
import com.yunwei.product.infobase.service.QhzSalonOrderService;
import com.yunwei.product.infobase.service.QhzSmsDetailService;
import com.yunwei.product.infobase.service.YwOrderPaymentService;

/**
 * 沙龙注册订单管理
* @ClassName: QhzSalonOrderController 
* @Description: TODO(TODO) 
* @author 晏飞
* @date 2019年6月25日 上午10:54:26 
*
 */
@Controller
public class QhzSalonOrderController {
	
	private static Logger logger = Logger.getLogger(QhzSalonOrderController.class);

	@Autowired
	private QhzSalonOrderService qhzSalonOrderService;
	@Autowired
	private QhzSalonAccountService qhzSalonAccountService;
	@Autowired
	private QhzSalonOrderItemService qhzSalonOrderItemService;
	@Autowired
	private QhzCurriculumService qhzSalonCurriculumService;
	@Autowired
	private YwPayService ywPayService;
	@Autowired
	private YwUserCustomerXcxCache ywUserCustomerXcxCache;
	@Autowired
	private QhzSmsDetailService qhzSmsDetailService;
	@Autowired
	private YwOrderPaymentService ywOrderPaymentService;
	@Autowired
	private YwUserCustomerXcxService ywUserCustomerXcxService;
	@Autowired
	private WxAccessToken wxAccessToken;
	@Autowired
	private WxTemplateMessage wxTemplateMessage;
	
	/**
	 * 创建订单
	*
	*@return
	*Map<String,Object>
	 */
	@RequestMapping(ConstantFunctionsFront.QHZ_SL021)
	@ResponseBody
	public String createOrder(QhzSalonOrder qhzSalonOrder , String curriculumIds){
		Map<String, Object> accountMap = new HashMap<String, Object>();
		try {
			accountMap.put("openid", qhzSalonOrder.getOpenid());
			//获取用户信息
			QhzSalonAccount account = qhzSalonAccountService.query(accountMap);
			String order_sn = OrderUtil.getOrderNo();
			qhzSalonOrder.setOrder_sn(order_sn);
			qhzSalonOrder.setOrder_name(account.getName());
			qhzSalonOrder.setOrder_tel(account.getTel());
			//存储受益人信息,当受益人信息为空时说明受益人为自己
			if(StringUtils.isBlank(qhzSalonOrder.getOrder_benefit_card())){
				qhzSalonOrder.setOrder_benefit_name(account.getName());
				qhzSalonOrder.setOrder_benefit_card(account.getCard());
			}
			qhzSalonOrder.setStatus("1");
			qhzSalonOrder.setOrder_adviser(account.getAdviser());
			//奖学金,暂定为没有
			qhzSalonOrder.setOrder_sc_price("0.00");
			qhzSalonOrder.setCreate_datetime(new Date());
			qhzSalonOrderService.insert(qhzSalonOrder);
			//保存订单项数据
			packageOrderItem(curriculumIds , order_sn);
			
			// 创建支付信息
			YwOrderPayment ywOrderPayment = new YwOrderPayment();
			ywOrderPayment.setOpen_id(qhzSalonOrder.getOpenid());
			ywOrderPayment.setOrder_pay_price(qhzSalonOrder.getOrder_total());
			ywOrderPayment.setOrder_sn(qhzSalonOrder.getOrder_sn());
			ywOrderPayment.setOrder_pay_createtime(new Date());
			ywOrderPayment.setOrder_pay_returnUrl(WxModelUtil.notify_url);
			ywOrderPayment.setOrder_pay_status("0");
			ywOrderPaymentService.insert(ywOrderPayment);
			
			return order_sn;
		} catch (Exception e) {
			throw new BizException("【创建订单】 : " + e);
		}
	}
	
	/**
	 * 查询用户订单(报名记录)
	*
	*@param openid
	*@return
	*Map<String,Object>
	 */
	@RequestMapping(ConstantFunctionsFront.QHZ_SL022)
	@ResponseBody
	public Map<String, Object> queryOrderByOpenid(String openid){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			//查询订单
			QhzSalonOrder qhzSalonOrder = new QhzSalonOrder();
			qhzSalonOrder.setOpenid(openid);
			List<QhzSalonOrder> order = qhzSalonOrderService.queryList(qhzSalonOrder);
			List<QhzSalonOrder> list = new ArrayList<QhzSalonOrder>();
			for (QhzSalonOrder oo : order) {
				//查询订单项信息
				QhzSalonOrderItem qhzSalonOrderItem = new QhzSalonOrderItem();
				qhzSalonOrderItem.setOrder_sn(oo.getOrder_sn());
				List<QhzSalonOrderItem> orderItems = qhzSalonOrderItemService.queryList(qhzSalonOrderItem);
				oo.setOrderItemList(orderItems);
				list.add(oo);
			}
			map.put("list", list);
		} catch (Exception e) {
			throw new BizException("【查询用户订单】: " + e);
		}
		
		return map;
	}
	
	/**
	 * 保存订单项数据
	*
	*void
	 */
	public void packageOrderItem(String curriculumIds , String order_sn){
		//保存订单项数据
		if(StringUtils.isNotBlank(curriculumIds)){
			String[] str = curriculumIds.split(",");
			List<QhzSalonOrderItem> qhzSalonOrderItems = new ArrayList<QhzSalonOrderItem>();
			
			for(String s : str){
				//获取课程信息
				QhzCurriculum curriculum = qhzSalonCurriculumService.query(Integer.valueOf(s));
				//组装到map
				QhzSalonOrderItem qhzSalonOrderItem = new QhzSalonOrderItem();
				qhzSalonOrderItem.setOrder_sn(order_sn);
				qhzSalonOrderItem.setOrder_name(curriculum.getTitle());
				qhzSalonOrderItem.setOrder_price(curriculum.getPrice());
				qhzSalonOrderItem.setCurriculum_id(s);
				qhzSalonOrderItems.add(qhzSalonOrderItem);
			}
			qhzSalonOrderItemService.insertBatch(qhzSalonOrderItems);
		}
	}
	
	/**
	 * 用户发起支付请求 后台处理（创建支付请求条件）
	*
	*@param qhzSalonOrder
	*@return
	*@throws UnsupportedEncodingException
	*@throws DocumentException
	*@throws ParseException
	*Map<String,Object>
	 */
	@RequestMapping(ConstantFunctionsFront.QHZ_SL023)
    @ResponseBody
	public Map<String, Object> weixinOrderCreate(QhzSalonOrder qhzSalonOrder) throws UnsupportedEncodingException, DocumentException, ParseException{
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			String out_trade_no = qhzSalonOrder.getOrder_sn();
			double order_totalprice = Double.parseDouble(qhzSalonOrder.getOrder_total());		//微信按分计算单位,需乘100设置为以元单位
			Map<String, Object> data = ywPayService.createJSAPIOrder(out_trade_no, NumberUtil.decimalBlankFormat(order_totalprice*100), qhzSalonOrder.getOpenid());
			map.put("data", data);
			map.put("order_totalprice", order_totalprice);
			
			//将微信订单信息保存到支付信息表
			YwOrderPayment ywOrderPayment = new YwOrderPayment();
			ywOrderPayment.setOrder_sn(out_trade_no);
			ywOrderPayment.setOrder_pay_remark(JSONObject.toJSONString(data));
			ywOrderPayment.setOrder_pay_method("2");
			ywOrderPaymentService.update(ywOrderPayment);
			
			System.out.println("支付条件信息 : " + data);
		} catch (Exception e) {
			throw new BizException("【创建订单】: " + e);
		}
		return map;
	}
	
	/**
	 * 发送订单支付成功模板消息
	*
	*void
	 */
	@RequestMapping(ConstantFunctionsFront.QHZ_SL024)
    @ResponseBody
	public void payment_result(String wx_appid , String order_sn , String prepay_id){
		try {
			System.out.println("--------wx_appid------- : " + wx_appid);
			System.out.println("--------order_sn------- : " + order_sn);
 			System.out.println("--------prepay_id------- : " + prepay_id);
 			
 			//动态获取access_token
 			String access_token = "";
 			YwUserCustomerXcx ywUser = new YwUserCustomerXcx();
 			
 			if(StringUtils.isNotBlank(wx_appid)){
 				ywUser.setApp_id(wx_appid);
 				ywUser = ywUserCustomerXcxService.query(ywUser);
 				if(ywUser != null){
 					access_token = wxAccessToken.getAccessToken(ywUser.getApp_id(), ywUser.getApp_secret());
 				} else {
 					access_token = wxAccessToken.getAccessToken(WxModelUtil.APPID, WxModelUtil.SECRET);
 				}
 			}
 			
 			//获取沙龙订单信息
 			QhzSalonOrder salonOrder = new QhzSalonOrder();
 			salonOrder.setOrder_sn(order_sn);
 			salonOrder = qhzSalonOrderService.query(salonOrder);
 			if(salonOrder != null){
 				//发送模板消息
     			JSONObject data = new JSONObject();
     			JSONObject keyword1 = new JSONObject();
     			keyword1.put("value", order_sn);	
     			data.put("keyword1", keyword1);
     			JSONObject keyword2 = new JSONObject();
     			keyword2.put("value",  WxModelUtil.BODY);
     			data.put("keyword2", keyword2);
     			JSONObject keyword3 = new JSONObject();
     			keyword3.put("value", salonOrder.getOrder_total());
     			data.put("keyword3", keyword3);
     			JSONObject keyword4 = new JSONObject();
     			keyword4.put("value", salonOrder.getOrder_curriculums());
     			data.put("keyword4", keyword4);
     			JSONObject keyword5 = new JSONObject();
     			keyword5.put("value", salonOrder.getOrder_name());
     			data.put("keyword5", keyword5);
     			JSONObject keyword6 = new JSONObject();
     			keyword6.put("value", salonOrder.getOrder_tel());
     			data.put("keyword6", keyword6);
     			JSONObject keyword7 = new JSONObject();
     			keyword7.put("value", salonOrder.getOrder_benefit_name());
     			data.put("keyword7", keyword7);
     			JSONObject keyword8 = new JSONObject();
     			keyword8.put("value", salonOrder.getOrder_benefit_card());
     			data.put("keyword8", keyword8);
     			JSONObject keyword9 = new JSONObject();
     			String datetime = DateUtil.format(new Date() , DateUtil.DATE_TIME_FORMAT);
     			keyword9.put("value", datetime);
     			data.put("keyword9", keyword9);
     			
     			wxTemplateMessage.orderSend(access_token, salonOrder.getOpenid(), wxTemplateMessage.pay_template_id, prepay_id, data, "");
 			}
		} catch (Exception e) {
			throw new BizException("【异步发送订单支付成功模板消息异常】: " + e);
		}
		 
    }
	

}
