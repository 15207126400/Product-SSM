package com.yunwei.product.backend.autotask;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.yunwei.common.autotask.EasyAutotaskExecutor;
import com.yunwei.common.util.MapUtil;
import com.yunwei.context.config.cache.YwQuartzjobCache;
import com.yunwei.product.backend.service.YwOrderService;
import com.yunwei.product.backend.service.YwProductSkuService;
import com.yunwei.product.common.model.YwOrder;

/**
 * 订单定时任务(去掉过期订单,恢复库存)
* @ClassName: YwOrderAutoTask 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年5月11日 下午3:36:16 
*
 */
@Lazy(false)
@Component("ywOrderTimeoutAutoTask")
public class YwOrderTimeoutAutoTask extends EasyAutotaskExecutor{
	
	private static Logger logger = Logger.getLogger(YwOrderTimeoutAutoTask.class);
	@Autowired
	private YwQuartzjobCache ywQuartzjobCache;
	@Autowired
	private YwOrderService ywOrderService;
	@Autowired
	private YwProductSkuService ywProductSkuService;
	
	/** 
     * cron表达式：* * * * * *（共6位，使用空格隔开，具体如下）  
     * cron表达式：*(秒0-59) *(分钟0-59) *(小时0-23) *(日期1-31) *(月份1-12或是JAN-DEC) *(星期1-7或是SUN-SAT)  
     * 注意： 30 * * * * * 表示每分钟的第30秒执行，而（*斜杠30）表示每30秒执行 
     *  
     * */  
//    @Scheduled(cron="*/30 * * * * *")
    @Override
    public void execute(){
    	logger.info("-------------------------超时订单遍历开始-----------------------------");
    	// 查询待付款的订单 开
    	YwOrder ywOrder = new YwOrder();
    	ywOrder.setOrder_status("1");
    	ywOrder.setOrder_updatetime(new Date());// 设置到期时间(订单30分钟到期)
    	Map<String, Object>  paramMap = MapUtil.toMap(ywOrder);
    	List<YwOrder> orders = ywOrderService.queryUnionList(paramMap);
    	
    	// 通过当前时间大于过期时间来判断订单是否过期
    	logger.info("超时的订单数量:{"+ orders.size() +"}");
    	if(orders.size() > 0){
    		for(YwOrder order : orders){
        		if(order.getOrder_updatetime().getTime() < System.currentTimeMillis()){
        			// 订单超时状态
        			order.setOrder_status("11");
        			ywOrderService.updateOrderStatus(order);
        		}
        	}
    	}
    	
    	logger.info("-------------------------超时订单遍历结束-----------------------------");
    }

    @Override
	public String getCron() {
		return ywQuartzjobCache.getCron("ywOrderTimeoutAutoTask", "0 10 0 * * ?");
	}

	@Override
	public boolean inuse() {
		return ywQuartzjobCache.inuse("ywOrderTimeoutAutoTask", true);
	}

}
