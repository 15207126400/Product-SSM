package com.yunwei.product.backend.autotask;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.yunwei.common.autotask.EasyAutotaskExecutor;
import com.yunwei.common.util.DateUtil;
import com.yunwei.context.config.cache.YwQuartzjobCache;
import com.yunwei.product.backend.service.YwCouponCollectiondetailsService;
import com.yunwei.product.common.model.YwCouponCollectiondetails;

/**
 * 优惠券定时任务(定时查询已领取优惠券是否过期,若过期则修改状态)
* @ClassName: YwCouponTimeoutAuto 
* @Description: TODO(TODO) 
* @author 晏飞
* @date 2019年2月25日 上午11:28:43 
*
 */
@Lazy(false)
@Component("ywCouponTimeoutAutoTask")
public class YwCouponTimeoutAutoTask extends EasyAutotaskExecutor{
	private static Logger logger = Logger.getLogger(YwCouponTimeoutAutoTask.class);
	@Autowired
	private YwQuartzjobCache ywQuartzjobCache;
	@Autowired
	private YwCouponCollectiondetailsService ywCouponCollectiondetailsService;
	
	/** 
     * cron表达式：* * * * * *（共6位，使用空格隔开，具体如下）  
     * cron表达式：*(秒0-59) *(分钟0-59) *(小时0-23) *(日期1-31) *(月份1-12或是JAN-DEC) *(星期1-7或是SUN-SAT)  
     * 注意： 30 * * * * * 表示每分钟的第30秒执行，而（*斜杠30）表示每30秒执行 
     *  
     * */ 

	@Override
	public void execute() {
		logger.info("-------------------------优惠券遍历开始-----------------------------");
		//查询已领取优惠券
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("details_status", "0");
		List<YwCouponCollectiondetails> ywCouponCollectiondetailses = ywCouponCollectiondetailsService.queryList(paramMap);
		logger.info("查询的已领取优惠券数量:{"+ ywCouponCollectiondetailses.size() +"}");
		for (YwCouponCollectiondetails ywCouponCollectiondetails : ywCouponCollectiondetailses) {
			int days = DateUtil.getIntervalDay(new Date(), ywCouponCollectiondetails.getCoupon_endtime());
			//如果结束时间与当前时间之差小于0,表示该优惠券已过期,修改状态
			if(days < 0){
				ywCouponCollectiondetails.setDetails_status("2");
				ywCouponCollectiondetailsService.update(ywCouponCollectiondetails);
			}
		}
		logger.info("-------------------------超时订单遍历结束-----------------------------");
	}
	
	@Override
	public String getCron() {
		//每天的0点、13点、18点、21点都执行一次
		return ywQuartzjobCache.getCron("ywCouponTimeoutAutoTask", "0 30 0 * * ?");
	}

	@Override
	public boolean inuse() {
		return ywQuartzjobCache.inuse("ywCouponTimeoutAutoTask", true);
	}

	
	
}
