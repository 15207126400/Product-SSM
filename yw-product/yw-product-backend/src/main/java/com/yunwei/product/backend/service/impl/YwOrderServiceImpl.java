package com.yunwei.product.backend.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.common.exception.BizException;
import com.yunwei.common.user.SysUser;
import com.yunwei.common.util.DateUtil;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.NumberUtil;
import com.yunwei.common.util.OrderUtil;
import com.yunwei.common.util.RedisClientUtil;
import com.yunwei.context.payment.support.WxPayService;
import com.yunwei.context.payment.util.WxModelUtil;
import com.yunwei.context.sms.support.WxSmsService;
import com.yunwei.context.sys.service.YwPayService;
import com.yunwei.product.backend.service.YwDistributorCommissionrecordService;
import com.yunwei.product.backend.service.YwMemberService;
import com.yunwei.product.backend.service.YwOrderItemService;
import com.yunwei.product.backend.service.YwOrderPaymentService;
import com.yunwei.product.backend.service.YwOrderService;
import com.yunwei.product.backend.service.YwProductSkuService;
import com.yunwei.product.backend.service.YwRefundService;
import com.yunwei.product.backend.service.YwSeckillRecordService;
import com.yunwei.product.backend.service.YwSmsBuyService;
import com.yunwei.product.backend.service.YwSmsNoticeService;
import com.yunwei.product.backend.service.YwTeamFollowService;
import com.yunwei.product.backend.service.YwTeamFoundService;
import com.yunwei.product.backend.service.YwWalletService;
import com.yunwei.product.backend.websocket.serivce.YwUserCustomerWebSocketSerivce;
import com.yunwei.product.common.backend.model.dto.YwOrderDto;
import com.yunwei.product.common.backend.model.dto.YwTeamFoundDto;
import com.yunwei.product.common.backend.model.form.YwOrderImportSqlserverForm;
import com.yunwei.product.common.backend.model.form.YwOrderProductForm;
import com.yunwei.product.common.dao.YwOrderDao;
import com.yunwei.product.common.model.OrderCountProcedure;
import com.yunwei.product.common.model.OrderPriceProcedure;
import com.yunwei.product.common.model.YwDistributorCommissionrecord;
import com.yunwei.product.common.model.YwMember;
import com.yunwei.product.common.model.YwOrder;
import com.yunwei.product.common.model.YwOrderItem;
import com.yunwei.product.common.model.YwOrderPayment;
import com.yunwei.product.common.model.YwProductSku;
import com.yunwei.product.common.model.YwRefund;
import com.yunwei.product.common.model.YwSeckillRecord;
import com.yunwei.product.common.model.YwTeamFollow;
import com.yunwei.product.common.model.YwTeamFound;
@Service
public class YwOrderServiceImpl extends IBaseServiceImpl<YwOrder> implements YwOrderService {

	private final Logger logger = LoggerFactory.getLogger(YwOrderServiceImpl.class);
	
	@Autowired
	private YwOrderDao ywOrderDao;
	@Autowired
	private YwOrderItemService ywOrderItemService;
	@Autowired
	private YwProductSkuService ywProductSkuService;
    @Autowired
    private YwTeamFoundService ywTeamFoundService;
    @Autowired
    private YwMemberService ywMemberService;
    @Autowired
    private YwOrderPaymentService ywOrderPaymentService;
    @Autowired
    private YwTeamFollowService ywTeamFollowService;
    @Autowired
    private YwRefundService ywRefundService;
    @Autowired
    private YwSmsBuyService ywSmsBuyService;
    @Autowired
    private WxSmsService wxSmsSupport;
    @Autowired
    private YwSmsNoticeService ywSmsNoticeService;
    @Autowired
    private YwDistributorCommissionrecordService ywDistributorCommissionrecordService;
    @Autowired
    private YwWalletService ywWalletService;
    @Autowired
    private YwPayService ywPayService;
    @Autowired
    private YwUserCustomerWebSocketSerivce ywUserCustomerWebSocketSerivce;
    
    @Override
	protected IBaseDao<YwOrder> getBaseDao() {
    	
		return ywOrderDao;
	}
    
	public int queryTotals(YwOrderDto ywOrderDto){
		
		return ywOrderDao.queryTotals(ywOrderDto);
	}

	@Override
	public int update(YwOrder ywOrder) {
		
		super.update(ywOrder);
		// 在退款表插入一条退款记录
		if(StringUtils.equals(ywOrder.getOrder_status(), "5")){
			// 查询订单
			ywOrder = this.query(ywOrder);
			// 先插入一条待退款的记录
	    	YwRefund ywRefund = new YwRefund();
	    	ywRefund.setRefund_sn(OrderUtil.getRefundSn());// 退款编号
	    	ywRefund.setRefund_createtime(new Date());
	    	ywRefund.setOrder_sn(ywOrder.getOrder_sn());
	    	ywRefund.setRefund_price(ywOrder.getOrder_totalprice());
	    	ywRefund.setUser_id(ywOrder.getOpenid());
	    	ywRefund.setRefund_status("0");// 待退款
	    	if(StringUtils.equals(ywOrder.getOrder_type(), "1")){
	    		ywRefund.setRefund_type("1");// 普通商品退款
	    		ywRefund.setRefund_remark("普通订单退款");
	    	} else if(StringUtils.equals(ywOrder.getOrder_type(), "2")){
	    		if(StringUtils.equals(ywOrder.getOrder_flag(), "1")){
	    			ywRefund.setRefund_type("2");// 团购商品退款
		    		ywRefund.setRefund_remark("参团订单退款");
	    		} else {
	    			ywRefund.setRefund_type("2");// 团购商品退款
		    		ywRefund.setRefund_remark("开团订单退款");
	    		}
	    	} else if(StringUtils.equals(ywOrder.getOrder_type(), "3")){
	    		ywRefund.setRefund_type("3");// 分销商品退款
	    		ywRefund.setRefund_remark("分销订单退款");
	    	}
	    	
	    	ywRefundService.insert(ywRefund);
		}
		return 0;
	}
	
	/**
	 * 商家发货并发送短信提醒用户
	 */
	//@Transactional
	@Override
	public Map<String, Object> orderSendOutSendMsg(SysUser sysUser,YwOrder ywOrder){
		ywOrder.setOrder_sendtime(new Date());
		this.update(ywOrder);
		
		// 查询订单
		YwOrder order = new YwOrder(); 
		order.setOrder_sn(ywOrder.getOrder_sn());
		ywOrder = this.query(order);
		String[] params ={ywOrder.getOrder_sn()};
		
		// 查询用户信息
		YwMember ywMember = ywMemberService.queryByOpenid(ywOrder.getOpenid());
		if(ywMember == null || StringUtils.isBlank(ywMember.getPhone())){
			logger.info("用户[{}]手机号码为空，无法发送短信",ywMember.getOpenid());
			// throw new BizException("用户["+ ywMember.getOpenid() +"]手机号码为空，无法发送短信");
		}
		ywSmsNoticeService.orderSendOutSms(sysUser.getUser_id(), ywMember.getPhone(), params);
		
		// 推送订单待发货消息
		new Thread(){
           public void run() {
        	   // 查询待发货订单总数
        	   YwOrder order = new YwOrder(); 
       		   order.setOrder_status("2");
       		   Map<String, Object> paramMap = MapUtil.toMap(order);
        	   int order_count = ywOrderDao.queryTotals(paramMap);
        	   // 将订单信息待发货数量保存到redis中
        	   RedisClientUtil.set(sysUser.getUser_id(), String.valueOf(order_count));
        	   JSONObject jsonObject = new JSONObject();
        	   jsonObject.put("order_count", order_count);
        	   ywUserCustomerWebSocketSerivce.sendMessage(sysUser.getUser_id(), jsonObject.toJSONString());
           };			
		}.start();
		return null;
	}


	@Override
	public List<YwOrder> queryUnionList(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		List<YwOrder> orders = ywOrderDao.queryUnionList(paramMap);
		// 遍历订单判断是否为团购订单
		for(YwOrder order : orders){
			if(StringUtils.equals(order.getOrder_type(), "2")){
				if(StringUtils.equals(order.getOrder_status(), "2")){
					if(StringUtils.equals(order.getOrder_flag(), "1")){
						// 拼团订单(查询团长信息);
						YwTeamFollow ywTeamFollow = new YwTeamFollow();
						ywTeamFollow.setOrder_id(order.getOrder_sn());
						ywTeamFollow = ywTeamFollowService.query(ywTeamFollow);
						
						// 查询团长信息
						YwTeamFound ywTeamFound = new YwTeamFound();
						ywTeamFound.setFound_id(ywTeamFollow.getFound_id());
						ywTeamFound = ywTeamFoundService.query(ywTeamFound);
						YwTeamFoundDto dto = new YwTeamFoundDto();
						try {
							BeanUtils.copyProperties(dto, ywTeamFound);
							order.setTeamFoundDto(dto);
							YwTeamFollow teamFollow = new YwTeamFollow();
							teamFollow.setFound_id(ywTeamFollow.getFound_id());
							teamFollow.setStatus("2");
							List<YwTeamFollow> teamFollows = ywTeamFollowService.queryList(teamFollow);
							order.getTeamFoundDto().setTeamFollows(teamFollows);
						} catch (Exception e) {
							System.out.println("对象拷贝错误");
						} 
					} else {
						// 查询参团人信息(必须在发货才显示参团人信息)
						YwTeamFound ywTeamFound = new YwTeamFound();
						ywTeamFound.setOrder_id(order.getOrder_sn());
						ywTeamFound = ywTeamFoundService.query(ywTeamFound);
						if(ywTeamFound != null){
							
							YwTeamFoundDto dto = new YwTeamFoundDto();
							try {
								BeanUtils.copyProperties(dto, ywTeamFound);
								order.setTeamFoundDto(dto);
								YwTeamFollow ywTeamFollow = new YwTeamFollow();
								ywTeamFollow.setFound_id(ywTeamFound.getFound_id());
								ywTeamFollow.setStatus("2");
								List<YwTeamFollow> teamFollows = ywTeamFollowService.queryList(ywTeamFollow);
								order.getTeamFoundDto().setTeamFollows(teamFollows);
							} catch (Exception e) {
								System.out.println("对象拷贝错误");
							} 
						}
					}
					
				} else if(StringUtils.equals(order.getOrder_status(), "10")){
					if(StringUtils.equals(order.getOrder_flag(), "1")){
						// 拼团订单(查询团长信息);
						YwTeamFollow ywTeamFollow = new YwTeamFollow();
						ywTeamFollow.setOrder_id(order.getOrder_sn());
						ywTeamFollow = ywTeamFollowService.query(ywTeamFollow);
						
						// 查询团长信息
						YwTeamFound ywTeamFound = new YwTeamFound();
						ywTeamFound.setFound_id(ywTeamFollow.getFound_id());
						ywTeamFound = ywTeamFoundService.query(ywTeamFound);
						YwTeamFoundDto dto = new YwTeamFoundDto();
						try {
							BeanUtils.copyProperties(dto, ywTeamFound);
							order.setTeamFoundDto(dto);
//							YwTeamFollow teamFollow = new YwTeamFollow();
//							teamFollow.setFound_id(ywTeamFound.getFound_id());
//							List<YwTeamFollow> teamFollows = ywTeamFollowService.queryList(teamFollow);
//							order.getTeamFoundDto().setTeamFollows(teamFollows);
						} catch (Exception e) {
							System.out.println("对象拷贝错误");
						} 
					} else {
						// 查询参团人信息(必须在发货才显示参团人信息)
						YwTeamFound ywTeamFound = new YwTeamFound();
						ywTeamFound.setOrder_id(order.getOrder_sn());
						ywTeamFound = ywTeamFoundService.query(ywTeamFound);
						if(ywTeamFound != null){
							
							YwTeamFoundDto dto = new YwTeamFoundDto();
							try {
								BeanUtils.copyProperties(dto, ywTeamFound);
								order.setTeamFoundDto(dto);
//								YwTeamFollow ywTeamFollow = new YwTeamFollow();
//								ywTeamFollow.setFound_id(ywTeamFound.getFound_id());
//								List<YwTeamFollow> teamFollows = ywTeamFollowService.queryList(ywTeamFollow);
//								order.getTeamFoundDto().setTeamFollows(teamFollows);
							} catch (Exception e) {
								System.out.println("对象拷贝错误");
							} 
						}
					}
				}
				
			}
		}
		return orders;
	}
	
	public List<YwOrderDto> queryUnionPageListByMap(Map<String,Object> paramMap){
		
		return ywOrderDao.queryUnionPageListByMap(paramMap);
	}

	/**
 	 *  更新订单同时并更新库存
	 */
    //@Transactional
	@Override
	public int updateOrderStatus(YwOrder ywOrder) {
		//更新订单
		this.update(ywOrder);
		
		// 释放库存
		List<YwOrderItem> orderItems = ywOrder.getYwOrderItems();
		if(orderItems.size() > 0){
			for(YwOrderItem orderItem : orderItems){
				// 没有订单项直接跳出循环
				if(StringUtils.isBlank(orderItem.getProduct_id())){
				    continue;
				}
				// 查询商品sku
				YwProductSku ywProductSku = new YwProductSku();
				ywProductSku.setProduct_id(orderItem.getProduct_id());
				ywProductSku.setSku_attr(orderItem.getOrder_ite_sku());
				ywProductSku = ywProductSkuService.query(ywProductSku);
				
				// 更新商品库存
				YwProductSku sku = new YwProductSku();
				sku.setSku_id(ywProductSku.getSku_id());
				sku.setSku_stock(String.valueOf(Integer.parseInt(ywProductSku.getSku_stock()) + Integer.parseInt(orderItem.getOrder_ite_count())));// 减商品库存
				ywProductSkuService.update(sku);
			}
		}
		return 0;
	}

    /**
	 * 用户确认收货后计入消费金额
	*
	*@param openid	用户身份标识	
	*@param order_sn  订单号
	*@return  0 积分未计入成功
	*		  1 积分计入成功
	 */
	public int orderAddPrice(String openid,String order_sn){
		YwMember ywMember = ywMemberService.queryByOpenid(openid);
		YwOrder ywOrder = new YwOrder();
		ywOrder.setOrder_sn(order_sn);
		ywOrder = this.query(ywOrder);
		String ac_price = NumberUtil.add(Double.valueOf(ywMember.getAc_price()), Double.valueOf(ywOrder.getOrder_totalprice()));
//        int price = (int) ac_price;
		ywMember.setAc_price(ac_price);
        int num = ywMemberService.update(ywMember);
			
        return num;
	}
	
	/**
	 * 用户确认收货后分别计入一级,二级(若存在的话)分销商佣金
	*
	*@param order_sn	订单号
	 */
	public void orderAddCommission(String order_sn){
		YwDistributorCommissionrecord ywDistributorCommissionrecord = new YwDistributorCommissionrecord();
		ywDistributorCommissionrecord.setOrder_id(order_sn);
		List<YwDistributorCommissionrecord> ywDistributorCommissionrecordlist = ywDistributorCommissionrecordService.queryList(ywDistributorCommissionrecord);
		for (YwDistributorCommissionrecord list : ywDistributorCommissionrecordlist) {
			// 修改佣金状态为已启用
			list.setDis_com_status("1");
			ywDistributorCommissionrecordService.update(list);
			// 计入佣金至钱包 
			ywWalletService.walletAddCommission(list.getUser_id(),Double.valueOf(list.getDis_com_money()));
		}
	}

	@Override
	public int queryTotalsByMap(Map<String, Object> paramMap) {
		
		return ywOrderDao.queryTotals(paramMap);
	}

//	@Override
//	public List<YwOrderDto> queryUnionListByMap(Map<String, Object> paramMap) {
//		
//		return ywOrderDao.queryUnionListByMap(paramMap);
//	}


	@Override
	public Map<String, Object> createNATIVEOrder(String order_sn,String total_fee, String body) {
		// 创建微信小程序支付订单
		Map<String, Object>  map = ywPayService.createNATIVEOrder(order_sn, total_fee, body);
		
		return map;
	}

	@Override
	public void updateStatusByDateToSendProduct(YwOrder ywOrder, int days) {
		// 避免程序报错,当发货时间不为空时继续往下处理
		if(ywOrder.getOrder_sendtime() != null){
			int day = DateUtil.getIntervalDay(ywOrder.getOrder_sendtime(),new Date());
			if(day >= days){
				ywOrder.setOrder_status("7");
				ywOrder.setOrder_successtime(new Date());
				super.update(ywOrder);
			}
		}
	}

	@Override
	public Map<String, Object> getOrderAndOfflineStatis(String branch_id) {
		Map<String, Object> map = new HashMap<String, Object>();
		OrderPriceProcedure orderPriceList = ywOrderDao.getPrice(branch_id);
		List<OrderCountProcedure> orderCountList = ywOrderDao.getCount(branch_id);
		map.put("orderPriceList", orderPriceList);
		map.put("orderCountList", orderCountList);
		
		return map;
	}

	@Override
	public List<YwOrder> importExcelByOrder(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return ywOrderDao.importExcelByOrder(paramMap);
	}
}
