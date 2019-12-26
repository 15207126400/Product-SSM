package com.yunwei.product.infobase.serivce.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.common.dao.YwTeamFoundDao;
import com.yunwei.product.common.model.YwMember;
import com.yunwei.product.common.model.YwOrder;
import com.yunwei.product.common.model.YwTeamFollow;
import com.yunwei.product.common.model.YwTeamFound;
import com.yunwei.product.infobase.service.YwMemberService;
import com.yunwei.product.infobase.service.YwOrderService;
import com.yunwei.product.infobase.service.YwTeamActivityService;
import com.yunwei.product.infobase.service.YwTeamFollowService;
import com.yunwei.product.infobase.service.YwTeamFoundService;

@Service
public class YwTeamFoundImpl extends IBaseServiceImpl<YwTeamFound> implements YwTeamFoundService {
	
	@Autowired
	private YwTeamFoundDao ywTeamFoundDao;
	@Autowired
	private YwMemberService ywMemberService;
	@Autowired
	private YwTeamActivityService ywTeamActivityService;
	@Autowired
	private YwOrderService ywOrderService;
	@Autowired
	private YwTeamFollowService ywTeamFollowService;
	@Override
	protected IBaseDao<YwTeamFound> getBaseDao() {
		return ywTeamFoundDao;
	}

	@Override
	public int insert(YwTeamFound ywTeamFound) {
		// 获取团长用户头像
		YwMember ywMember = ywMemberService.queryByOpenid(ywTeamFound.getUser_id());
		ywTeamFound.setNickname(ywMember.getNickname());
		ywTeamFound.setHead_pic(ywMember.getAvatarUrl());
		ywTeamFound.setFound_time(new Date());
		ywTeamFoundDao.insert(ywTeamFound);
		return 0;
	}

	@Override
	public boolean isSuccess(YwTeamFound ywTeamFound,String pay_flag) {
		// 查询开团信息
		ywTeamFound = this.query(ywTeamFound);
		String found_join = ywTeamFound.getFound_join();
		if(StringUtils.equals(ywTeamFound.getNeed(), found_join)){
			return true;
		} else {
			// 支付成功后增加参团人数
			if(StringUtils.equals(pay_flag, "true")){
				// 修改开团信息
				int count = Integer.valueOf(found_join).intValue();
				// 自动加1
				count ++ ;
				ywTeamFound.setFound_join(String.valueOf(count));
				// 判断是否成团
				if(StringUtils.equals(ywTeamFound.getNeed(), String.valueOf(count))){
//					ywTeamFound.setFound_end_time(new Date());
					ywTeamFound.setStatus("2"); //成团成功
					
					// 将订单状态修改成待发货
					updateOrder(ywTeamFound,null);
					
					// 将参团订单改待发货
					updateTeamFollow(ywTeamFound);
				}
				this.update(ywTeamFound);
				return false;
			}
			return false;
		}
	}
	
	/**
	 * 将参团订单改待发货
	 * @param ywTeamFound
	 * @return
	 */
	private int updateTeamFollow(YwTeamFound ywTeamFound){
		YwTeamFollow ywTeamFollow = new YwTeamFollow();
		ywTeamFollow.setFound_id(ywTeamFound.getFound_id());
		ywTeamFollow.setStatus("1");
		List<YwTeamFollow> teamFollows = ywTeamFollowService.queryList(ywTeamFollow);
		
		if(teamFollows.size() > 0){
			for(YwTeamFollow follow : teamFollows){
				// 更新参团信息表(成团成功)
				follow.setStatus("2");
				ywTeamFollowService.update(follow);
				// 更新订单表
				updateOrder(null,follow);
			}
		}
		return 0;
	}
	
	/**
	 * 修改订单状态为待发货
	 * @param ywTeamFound
	 * @return
	 */
	private int updateOrder(YwTeamFound ywTeamFound,YwTeamFollow ywTeamFollow){
		if(ywTeamFollow == null){
			YwOrder ywOrder = new YwOrder();
			ywOrder.setOrder_sn(ywTeamFound.getOrder_id());
			ywOrder.setOrder_status("2");// 待发货
			ywOrderService.update(ywOrder);
		} else {
			YwOrder ywOrder = new YwOrder();
			ywOrder.setOrder_sn(ywTeamFollow.getOrder_id());
			ywOrder.setOrder_status("2");// 待发货
			ywOrderService.update(ywOrder);
		}
		return 0;
	}

	@Override
	public List<YwTeamFound> queryTimeoutList(YwTeamFound ywTeamFound) {
		
		return ywTeamFoundDao.queryTimeoutList(ywTeamFound);
	}

	//@Transactional
	@Override
	public List<YwTeamFound> updateFoundStatus(YwTeamFound ywTeamFound) {
		if(StringUtils.equals(ywTeamFound.getStatus(), "1")){
			
			// 先插入一条待退款的记录
//	    	YwRefund ywRefund = new YwRefund();
//	    	ywRefund.setRefund_sn(OrderUtil.getRefundSn());// 退款编号
//	    	ywRefund.setRefund_createtime(new Date());
//	    	ywRefund.setOrder_sn(ywTeamFound.getOrder_id());
//	    	ywRefund.setRefund_price(ywTeamFound.getPrice());
//	    	ywRefund.setUser_id(ywTeamFound.getUser_id());
//	    	ywRefund.setRefund_status("0");// 待退款
//	    	ywRefund.setRefund_type("2");// 团购退款
//	    	ywRefund.setRefund_remark("开团订单退款");
//	    	ywRefundService.insert(ywRefund);
	    	YwOrder ywOrder = new YwOrder();
	    	ywOrder.setOrder_sn(ywTeamFound.getOrder_id());
	    	ywOrder = ywOrderService.query(ywOrder);
	    	// 修改订单订单状态为退款中
	    	updateOrderToRefund(ywOrder);
	    	
	    	// 判断是否有参团人信息
	    	YwTeamFollow ywTeamFollow = new YwTeamFollow();
	    	ywTeamFollow.setFound_id(ywTeamFound.getFound_id());
	    	List<YwTeamFollow> teamFollows = ywTeamFollowService.queryList(ywTeamFollow);
	    	if(teamFollows.size() > 0){
	    		for(YwTeamFollow teamFollow : teamFollows){
	    			// 先插入一条待退款的记录
//	    	    	YwRefund refund = new YwRefund();
//	    	    	refund.setRefund_sn(OrderUtil.getRefundSn());// 退款编号
//	    	    	refund.setRefund_createtime(new Date());
//	    	    	refund.setOrder_sn(teamFollow.getOrder_id());
//	    	    	refund.setRefund_price(ywTeamFound.getPrice());
//	    	    	refund.setUser_id(teamFollow.getFollow_user_id());
//	    	    	refund.setRefund_status("0");// 待退款
//	    	    	refund.setRefund_type("2");// 团购退款
//	    	    	refund.setRefund_remark("参团订单退款");
//	    	    	ywRefundService.insert(refund);
	    	    	
	    	    	// 修改参团人信息(成团失败)
	    	    	teamFollow.setStatus("3");
	    	    	ywTeamFollowService.update(teamFollow);
	    	    	
	    	    	// 修改订单订单状态为退款中
	    	    	YwOrder order = new YwOrder();
	    	    	order.setOrder_sn(teamFollow.getOrder_id());
	    	    	order = ywOrderService.query(order);
	    	    	updateOrderToRefund(order);
	    		}
	    	}
	    	
	    	// 修改开团人信息(开团失败)
			ywTeamFound.setStatus("3");
			this.update(ywTeamFound);
		}
		return null;
	}
	
	
	 /**
     * 修改订单状态(退款中)
     * @param ywRefund
     */
    private void updateOrderToRefund(YwOrder ywOrder){
		//拼团失败退款中
    	ywOrder.setOrder_status("5");
		ywOrderService.update(ywOrder);
    }
	

}
