package com.yunwei.product.backend.autotask;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.yunwei.common.autotask.EasyAutotaskExecutor;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.NumberUtil;
import com.yunwei.context.config.cache.YwQuartzjobCache;
import com.yunwei.context.sys.service.YwPayService;
import com.yunwei.product.backend.service.YwDistributorCommissionrecordService;
import com.yunwei.product.backend.service.YwMemberService;
import com.yunwei.product.backend.service.YwOrderService;
import com.yunwei.product.backend.service.YwRefundService;
import com.yunwei.product.backend.service.YwWalletService;
import com.yunwei.product.common.model.YwOrder;
import com.yunwei.product.common.model.YwRefund;

/**
 * 退款定时任务
* @ClassName: YwRefundAutoTask 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年5月18日 下午3:29:37 
*
 */
@Lazy(false)
@Component("ywRefundAutoTask")
public class YwRefundAutoTask extends EasyAutotaskExecutor{
	private static Logger logger = Logger.getLogger(YwOrderTimeoutAutoTask.class);
	@Autowired
	private YwQuartzjobCache ywQuartzjobCache;
	@Autowired
	private YwRefundService ywRefundService;
	@Autowired
	private YwOrderService ywOrderService;
	@Autowired
	private YwDistributorCommissionrecordService ywDistributorCommissionrecordService;
	@Autowired
	private YwWalletService ywWalletService;
	@Autowired
	private YwMemberService ywMemberService;
	@Autowired
	private YwPayService ywPayService;
	
	/** 
     * cron表达式：* * * * * *（共6位，使用空格隔开，具体如下）  
     * cron表达式：*(秒0-59) *(分钟0-59) *(小时0-23) *(日期1-31) *(月份1-12或是JAN-DEC) *(星期1-7或是SUN-SAT)  
     * 注意： 30 * * * * * 表示每分钟的第30秒执行，而（*斜杠30）表示每30秒执行 
     *  
     * */  
//    @Scheduled(cron="*/30 * * * * *")
	@Override
	public void execute() {
		logger.info("-------------------------退款订单遍历开始-----------------------------");
		// 开始团购退款
    	YwRefund ywRefund = new YwRefund();
    	List<YwRefund> refunds = ywRefundService.queryList(ywRefund);
    	logger.info("需要退款的订单数量:{"+ refunds.size() +"}");
    	if(refunds.size() > 0){
    		for(YwRefund refund : refunds){
    			// 退款关联操作
    			orderRefund(refund);
    		}
    	}
    	logger.info("-------------------------退款订单遍历结束-----------------------------");
		
	}
	
	/**
     * 退款
     * @param ywTeamFound
     */
    private void orderRefund(YwRefund ywRefund){
    	try {
    		// 退款接口调用
    		Map<String, Object> responseMap = ywPayService.refund(ywRefund.getOrder_sn(), ywRefund.getRefund_sn(), NumberUtil.decimalBlankFormat(Double.valueOf(ywRefund.getRefund_price())*100), NumberUtil.decimalBlankFormat(Double.valueOf(ywRefund.getRefund_price())*100));
    		// Map<String, String> responseMap = WeiXinAtcion.wechatRefund(ywRefund.getOrder_sn(), ywRefund.getRefund_sn(), Double.valueOf(ywRefund.getRefund_price()), Double.valueOf(ywRefund.getRefund_price()));
    		if(StringUtils.equals(responseMap.get("result_code").toString(), "SUCCESS")){
    			ywRefund.setRefund_status("1");// 退款成功
    			ywRefund.setRefund_updatetime(new Date());// 退款成功时间
    			ywRefund.setRefund_error_no(responseMap.get("result_code").toString());
    			ywRefund.setRefund_error_info("退款成功");
    			
    			// 修改订单状态
    			this.updateOrder(ywRefund);
    		} else {
    			ywRefund.setRefund_status("2");// 退款失败
    			ywRefund.setRefund_error_no(responseMap.get("result_code").toString());
    			ywRefund.setRefund_error_info(responseMap.get("err_code_des").toString());
    		}
    		ywRefundService.update(ywRefund);
    	} catch (NumberFormatException e) {
			logger.info("退款失败：{"+ e.getMessage() +"}");
			ywRefund.setRefund_status("2");// 退款失败
			ywRefund.setRefund_error_info(e.getMessage());
			ywRefundService.update(ywRefund);
		} catch (Exception e) {
			logger.info("退款失败：{"+ e.getMessage() +"}");
			ywRefund.setRefund_status("2");// 退款失败
			ywRefund.setRefund_error_info(e.getMessage());
			ywRefundService.update(ywRefund);
		}
    }
    
    /**
     * 修改订单状态
     * @param ywRefund
     */
    private void updateOrder(YwRefund ywRefund){
    	YwOrder ywOrder = new YwOrder();
    	ywOrder.setOrder_sn(ywRefund.getOrder_sn());
    	Map<String, Object>  paramMap = MapUtil.toMap(ywOrder);
    	List<YwOrder> orders = ywOrderService.queryUnionList(paramMap);
    	if(orders.size() > 0){
    		YwOrder order = orders.get(0);
    		//退款成功状态
			order.setOrder_status("6");
			ywOrderService.updateOrderStatus(order);
    	}
    }

	@Override
	public String getCron() {
		return ywQuartzjobCache.getCron("ywRefundAutoTask", "0 0 0 * * ?");
	}

	@Override
	public boolean inuse() {
		return ywQuartzjobCache.inuse("ywRefundAutoTask", true);
	}
    

}
