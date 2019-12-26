package com.yunwei.product.infobase.serivce.impl;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.common.middleware.ISisapService;
import com.yunwei.common.middleware.YwSisapServiceImpl;
import com.yunwei.common.user.SysUser;
import com.yunwei.common.util.NumberUtil;
import com.yunwei.common.util.OrderUtil;
import com.yunwei.context.payment.support.WxPayService;
import com.yunwei.context.payment.util.WxModelUtil;
import com.yunwei.context.sms.support.WxSmsService;
import com.yunwei.context.sys.service.YwPayService;
import com.yunwei.product.common.backend.model.dto.YwOrderDto;
import com.yunwei.product.common.backend.model.dto.YwTeamFoundDto;
import com.yunwei.product.common.backend.model.form.YwOrderProductForm;
import com.yunwei.product.common.dao.YwOrderDao;
import com.yunwei.product.common.model.OrderCountProcedure;
import com.yunwei.product.common.model.OrderPriceProcedure;
import com.yunwei.product.common.model.YwDistributorCommissionrecord;
import com.yunwei.product.common.model.YwMember;
import com.yunwei.product.common.model.YwMemberRole;
import com.yunwei.product.common.model.YwOrder;
import com.yunwei.product.common.model.YwOrderItem;
import com.yunwei.product.common.model.YwOrderPayment;
import com.yunwei.product.common.model.YwProductSku;
import com.yunwei.product.common.model.YwSeckillRecord;
import com.yunwei.product.common.model.YwTeamFollow;
import com.yunwei.product.common.model.YwTeamFound;
import com.yunwei.product.infobase.service.YwDistributorCommissionrecordService;
import com.yunwei.product.infobase.service.YwMemberRoleService;
import com.yunwei.product.infobase.service.YwMemberService;
import com.yunwei.product.infobase.service.YwOrderItemService;
import com.yunwei.product.infobase.service.YwOrderPaymentService;
import com.yunwei.product.infobase.service.YwOrderService;
import com.yunwei.product.infobase.service.YwPointsDetailService;
import com.yunwei.product.infobase.service.YwPointsExchangeService;
import com.yunwei.product.infobase.service.YwPointsService;
import com.yunwei.product.infobase.service.YwProductSkuService;
import com.yunwei.product.infobase.service.YwSeckillRecordService;
import com.yunwei.product.infobase.service.YwSmsBuyService;
import com.yunwei.product.infobase.service.YwTeamFollowService;
import com.yunwei.product.infobase.service.YwTeamFoundService;
import com.yunwei.product.infobase.service.YwWalletService;
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
    private YwSmsBuyService ywSmsBuyService;
    @Autowired
    private WxSmsService wxSmsSupport;
    @Autowired
    private YwDistributorCommissionrecordService ywDistributorCommissionrecordService;
    @Autowired
    private YwWalletService ywWalletService;
    @Autowired
    private YwSeckillRecordService ywSeckillRecordService;
    @Autowired
	private YwPointsService ywPointsService;
	@Autowired
	private YwPointsDetailService ywPointsDetailService;
	@Autowired
	private YwPointsExchangeService ywPointsExchangeService;
	@Autowired
	private YwMemberRoleService ywMemberRoleService;
	@Autowired
	private YwPayService ywPayService;
	@Autowired
	private ISisapService iSisapService;
    
    @Override
	protected IBaseDao<YwOrder> getBaseDao() {
		return ywOrderDao;
	}
    
	public int queryTotals(YwOrderDto ywOrderDto){
		return ywOrderDao.queryTotals(ywOrderDto);
	}

	@Override
	public int update(YwOrder ywOrder) {
		// TODO Auto-generated method stub
		super.update(ywOrder);
//		// 在退款表插入一条退款记录
//		if(StringUtils.equals(ywOrder.getOrder_status(), "5")){
//			// 查询订单
//			ywOrder = this.query(ywOrder);
//			// 先插入一条待退款的记录
//	    	YwRefund ywRefund = new YwRefund();
//	    	ywRefund.setRefund_sn(OrderUtil.getRefundSn());// 退款编号
//	    	ywRefund.setRefund_createtime(new Date());
//	    	ywRefund.setOrder_sn(ywOrder.getOrder_sn());
//	    	ywRefund.setRefund_price(ywOrder.getOrder_totalprice());
//	    	ywRefund.setUser_id(ywOrder.getOpenid());
//	    	ywRefund.setRefund_status("0");// 待退款
//	    	if(StringUtils.equals(ywOrder.getOrder_type(), "1")){
//	    		ywRefund.setRefund_type("1");// 普通商品退款
//	    		ywRefund.setRefund_remark("普通订单退款");
//	    	} else if(StringUtils.equals(ywOrder.getOrder_type(), "2")){
//	    		if(StringUtils.equals(ywOrder.getOrder_flag(), "1")){
//	    			ywRefund.setRefund_type("2");// 团购商品退款
//		    		ywRefund.setRefund_remark("参团订单退款");
//	    		} else {
//	    			ywRefund.setRefund_type("2");// 团购商品退款
//		    		ywRefund.setRefund_remark("开团订单退款");
//	    		}
//	    	} else if(StringUtils.equals(ywOrder.getOrder_type(), "3")){
//	    		ywRefund.setRefund_type("3");// 分销商品退款
//	    		ywRefund.setRefund_remark("分销订单退款");
//	    	}
//	    	
//	    	ywRefundService.insert(ywRefund);
//		}
		return 0;
	}
	
	/**
	 * 商家发货并发送短信提醒用户
	 */
//	@Transactional
	@Override
	public Map<String, Object> OrderSendOutSendMsg(SysUser sysUser,YwOrder ywOrder){
//		ywOrder.setOrder_sendtime(new Date());
//		this.update(ywOrder);
//		
////		// 查询订单
////		YwOrder order = new YwOrder(); 
////		order.setOrder_sn(ywOrder.getOrder_sn());
////		ywOrder = this.query(order);
////		String[] params ={ywOrder.getOrder_sn()};
////		
////		// 查询用户信息
////		YwMember ywMember = ywMemberService.queryByOpenid(ywOrder.getOpenid());
////		if(ywMember == null || StringUtils.isBlank(ywMember.getPhone())){
////			logger.info("用户[{}]手机号码为空，无法发送短信",ywMember.getOpenid());
////			throw new BizException("用户["+ ywMember.getOpenid() +"]手机号码为空，无法发送短信");
////		}
////		ywSmsNoticeService.orderSendOutSms(sysUser.getUser_id(), ywMember.getPhone(), params);
		return null;
	}


	@Transactional
	@Override
	public int insert(YwOrder ywOrder) {
		ywOrder.setOrder_sn(OrderUtil.getOrderNo());
		// 如果是购物车创建订单 , 默认为普通订单
		if(ywOrder.getOrder_type() == null){
			ywOrder.setOrder_type("1");
		}
		// 获取用户信息 , 为后续拿到用户对应的积分获取比例
        YwMember member = ywMemberService.queryByOpenid(ywOrder.getOpenid());
        YwMemberRole ywMemberRole = new YwMemberRole();
        ywMemberRole.setId(member.getLevel());
        YwMemberRole role = ywMemberRoleService.query(ywMemberRole);
        String points = NumberUtil.mul(Double.valueOf(ywOrder.getOrder_totalprice()), Double.valueOf(role.getProportion()));
       // int point = (int) points;			// 订单获取的积分
        
		
		if(StringUtils.equals(ywOrder.getOrder_type(), "5")){			// 如果是积分订单 , 状态存为待发货
			ywOrder.setOrder_status("2");
			ywOrder.setOrder_points("0");
		}else if(StringUtils.equals(ywOrder.getOrder_type(), "1") || StringUtils.equals(ywOrder.getOrder_type(), "3")){
			ywOrder.setOrder_status("1");
			ywOrder.setOrder_points(points);
			// 生成积分明细
	        ywPointsDetailService.addPointsDetail("4", ywOrder.getOpenid(), "+" + points, ywOrder.getOrder_sn());
		}else{
			ywOrder.setOrder_status("1");
		}
		
		ywOrder.setOrder_createtime(new Date());
		// 其他订单30分钟到期
		ywOrder.setOrder_updatetime(DateUtils.addMinutes(new Date(), 30));// 设置到期时间(订单30分钟到期)
		
		// 创建订单
		ywOrderDao.insert(ywOrder);
		
		// 生成订单项数据
		List<YwOrderProductForm> productForms = ywOrder.getProductForms();
		List<YwOrderItem> orderItems = new ArrayList<YwOrderItem>();
		// 需要判断是否为购物车提交的订单  , 如果是购物车  , 保存的订单项商品id为购物车的shopid
		if(CollectionUtils.isNotEmpty(productForms)){
			for(YwOrderProductForm form:productForms){
				YwOrderItem item = new YwOrderItem();
				item.setOrder_sn(ywOrder.getOrder_sn());
				item.setProduct_id(form.getId());
				item.setOrder_ite_sku(form.getSku_attr());
				item.setOrder_ite_count(form.getCount());
				item.setOrder_ite_price(form.getPrice());
				item.setOrder_ite_name(form.getTitle());
				item.setOrder_ite_img(form.getUrl());
				orderItems.add(item);
				
				// 查询商品sku(如果机构编号不为空,说明查询机构sku表)
				if(StringUtils.isNotBlank(ywOrder.getBranch_id())){			
					YwProductSku ywProductSku = ywProductSkuService.query(form.getSku_id());
					ywProductSku.setSku_stock(String.valueOf(Long.parseLong(ywProductSku.getSku_stock()) - Long.parseLong(form.getCount())));// 减商品库存
					ywProductSkuService.update(ywProductSku);
				} else {
					YwProductSku ywProductSku = new YwProductSku();
					ywProductSku.setSku_id(form.getSku_id());
					ywProductSku = ywProductSkuService.query(ywProductSku);
					// 更新商品库存
					//ywProductSku.setSku_stock(String.valueOf(Integer.parseInt(ywProductSku.getSku_stock()) - Integer.parseInt(form.getCount())));// 减商品库存
					ywProductSku.setSku_stock(String.valueOf(Long.parseLong(ywProductSku.getSku_stock()) - Long.parseLong(form.getCount())));// 减商品库存
					ywProductSkuService.update(ywProductSku);
				}
			}
			
			// 创建订单项
			ywOrderItemService.insertBatch(orderItems);
		}
		
		
		// 创建支付信息
		YwOrderPayment ywOrderPayment = new YwOrderPayment();
		ywOrderPayment.setOpen_id(ywOrder.getOpenid());
		ywOrderPayment.setOrder_pay_price(ywOrder.getOrder_totalprice());
		ywOrderPayment.setOrder_sn(ywOrder.getOrder_sn());
		ywOrderPayment.setOrder_pay_createtime(new Date());
		ywOrderPayment.setOrder_pay_remark(ywOrder.getOrder_remark());
		ywOrderPayment.setOrder_pay_returnUrl(WxModelUtil.notify_url);
		ywOrderPayment.setOrder_pay_status("0");
		ywOrderPayment.setBranch_id(ywOrder.getBranch_id());
		ywOrderPaymentService.insert(ywOrderPayment);
		
		// 选择订单类型
		chooseOrderType(ywOrder);
		
		return 0;
	}
	
	/**
	 * 选择订单类型
	 * @param ywOrder
	 * @return
	 */
	private int chooseOrderType(YwOrder ywOrder){
		
		// 判断订单类型
		if(StringUtils.equals(ywOrder.getOrder_type(), "1")){
			
		} else if(StringUtils.equals(ywOrder.getOrder_type(), "2")){
			if(StringUtils.isBlank(ywOrder.getTeamFollowData().getFound_id())){
				// 增加开团信息
				YwTeamFound ywTeamFound = ywOrder.getTeamFoundData();
				ywTeamFound.setOrder_id(ywOrder.getOrder_sn());
				// 获取团长用户头像
				YwMember ywMember = ywMemberService.queryByOpenid(ywTeamFound.getUser_id());
				ywTeamFound.setNickname(ywMember.getNickname());
				ywTeamFound.setHead_pic(ywMember.getAvatarUrl());
				ywTeamFound.setFound_time(new Date());
				ywOrder.getTeamFoundData().setFound_id(ywTeamFound.getFound_id());
				ywTeamFoundService.insert(ywTeamFound);
			} else {
			    // 增加参团信息
				YwTeamFollow ywTeamFollow = ywOrder.getTeamFollowData();
				ywTeamFollow.setOrder_id(ywOrder.getOrder_sn());
				// 获取参团用户头像
				YwMember ywMember = ywMemberService.queryByOpenid(ywTeamFollow.getFollow_user_id());
				ywTeamFollow.setFollow_user_nickname(ywMember.getNickname());
				ywTeamFollow.setFollow_user_head_pic(ywMember.getAvatarUrl());
				ywTeamFollow.setFollow_time(new Date());
				ywOrder.getTeamFollowData().setFollow_id(ywTeamFollow.getFollow_id());
				ywTeamFollowService.insert(ywTeamFollow);
			}
			
		} else if(StringUtils.equals(ywOrder.getOrder_type(), "3")){
		    
		} else if(StringUtils.equals(ywOrder.getOrder_type(), "4")){
			// 添加秒杀记录信息
			YwSeckillRecord ywSeckillRecord = ywOrder.getSeckillRecordData();
			ywSeckillRecord.setOrder_sn(ywOrder.getOrder_sn());
			ywSeckillRecord.setRecord_createtime(new Date());
			ywSeckillRecord.setRecord_totalprice(ywOrder.getOrder_totalprice());
			ywSeckillRecord.setSeckill_num(ywOrder.getProductForms().get(0).getCount());
			// 获取参团用户头像
			YwMember ywMember = ywMemberService.queryByOpenid(ywSeckillRecord.getUser_id());
			ywSeckillRecord.setUser_nickname(ywMember.getNickname());
			ywSeckillRecord.setUser_headurl(ywMember.getAvatarUrl());
			ywSeckillRecordService.insert(ywSeckillRecord);
			ywOrder.setSeckillRecordData(ywSeckillRecord);;
		}else if(StringUtils.equals(ywOrder.getOrder_type(), "5")){
			// 扣除积分操作放到用户确认收货后执行
			//int num = ywPointsService.deductPointsByUser(ywOrder.getOrder_totalprice(), ywOrder.getOpenid());
			
			// 生成积分明细
			ywPointsDetailService.addPointsDetail("1", ywOrder.getOpenid(), "-" + ywOrder.getOrder_totalprice(), ywOrder.getOrder_sn());
			// 从订单项中取商品id
			String product_id = "";
			for (int i = 0; i < ywOrder.getProductForms().size(); i++) {
				product_id = ywOrder.getProductForms().get(i).getId();
			}
			// 生成积分兑换记录
			ywPointsExchangeService.addPointsExchange("1", ywOrder.getOpenid(), ywOrder.getOrder_totalprice(), ywOrder.getOrder_sn(), product_id , "0");
		}
		return 0;
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
				// 查询商品sku
				YwProductSku ywProductSku = new YwProductSku();
				ywProductSku.setProduct_id(orderItem.getProduct_id());
				ywProductSku.setSku_attr(orderItem.getOrder_ite_sku());
				ywProductSku = ywProductSkuService.query(ywProductSku);
				
				// 更新商品库存
				ywProductSku.setSku_stock(String.valueOf(Integer.parseInt(ywProductSku.getSku_stock()) + Integer.parseInt(orderItem.getOrder_ite_count())));// 减商品库存
				ywProductSkuService.update(ywProductSku);
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
//         int price = Integer.valueOf(i) ac_price;
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

	@Override
	public void orderMessageSend(String app_id, String order_sn) {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("app_id", app_id);
		params.put("order_sn", order_sn);
		iSisapService.call("http://xcx.whywxx.com", "/product/ywOrder/messages", params);
		
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

	public static void main(String[] args) {
		ISisapService iSisapService = new YwSisapServiceImpl();
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("app_id", "wx7eb46168a3284ee0");
		params.put("order_sn", "123456789");
		iSisapService.call("http://xcx.whywxx.com", "/product/ywOrder/messages", params);
	}
}
