package com.yunwei.product.backend.autotask;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yunwei.common.autotask.EasyAutotaskExecutor;
import com.yunwei.context.config.cache.YwQuartzjobCache;
import com.yunwei.product.backend.service.YwOrderService;
import com.yunwei.product.backend.service.YwProductSkuService;
import com.yunwei.product.backend.service.YwRefundService;
import com.yunwei.product.backend.service.YwTeamFollowService;
import com.yunwei.product.backend.service.YwTeamFoundService;
import com.yunwei.product.common.model.YwTeamFound;

/**
 * 开团超时任务自动退款定时器
* @ClassName: YwTeamFoundAutoTask 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年5月17日 下午4:39:29 
*
 */
@Lazy(false)
@Component("ywTeamFoundTimeoutAutoTask")
public class YwTeamFoundTimeoutAutoTask extends EasyAutotaskExecutor{
	
	private static Logger logger = Logger.getLogger(YwTeamFoundTimeoutAutoTask.class);
	@Autowired
	private YwQuartzjobCache ywQuartzjobCache;
	@Autowired
	private YwTeamFoundService ywTeamFoundService;
	@Autowired
	private YwRefundService ywRefundService;
	@Autowired
	private YwTeamFollowService ywTeamFollowService;
	@Autowired
	private YwOrderService ywOrderService;
	@Autowired
	private YwProductSkuService ywProductSkuService;
	
	/** 
     * cron表达式：* * * * * *（共6位，使用空格隔开，具体如下）  
     * cron表达式：*(秒0-59) *(分钟0-59) *(小时0-23) *(日期1-31) *(月份1-12或是JAN-DEC) *(星期1-7或是SUN-SAT)  
     * 注意： 30 * * * * * 表示每分钟的第30秒执行，而（*斜杠30）表示每30秒执行 
     * */  
//    @Scheduled(cron="*/30 * * * * *")
    @Override
    public void execute(){
    	logger.info("-------------------------超时开团遍历开始-----------------------------");
    	// 查询开团失败的集合
    	YwTeamFound ywTeamFound = new YwTeamFound();
    	ywTeamFound.setFound_end_time(new Date());
    	ywTeamFound.setStatus("1");// 开团成功，团购时间超时
    	List<YwTeamFound> teamFounds = ywTeamFoundService.queryTimeoutList(ywTeamFound);
    	
    	// 通过当前时间大于过期时间来判断订单是否过期
    	logger.info("超时的开团数量:{"+ teamFounds.size() +"}");
    	if(teamFounds.size() > 0){
    		for(YwTeamFound teamFound : teamFounds){
    			// 在退款记录表生成退款记录
    			ywTeamFoundService.updateFoundStatus(teamFound);
        	}
    	}
    	logger.info("-------------------------超时开团遍历结束-----------------------------");
    }

    @Override
	public String getCron() {
		return ywQuartzjobCache.getCron("ywTeamFoundTimeoutAutoTask", "0 20 0 * * ?");
	}

	@Override
	public boolean inuse() {
		return ywQuartzjobCache.inuse("ywTeamFoundTimeoutAutoTask", true);
	}
    
    
}
