package com.yunwei.context.config.cache;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yunwei.common.autotask.SimpleScheduledBeanPostProcessor;
import com.yunwei.common.cache.ICache;
import com.yunwei.context.config.model.YwQuartzjob;
import com.yunwei.context.config.service.YwQuartzjobService;

/**
 * 功能说明: 定时任务管理缓存
 * 系统版本: v1.0<br>
 * 开发人员: @author kongdy<br>
 * 开发时间: 2015年7月31日<br>
 */
@Component("ywQuartzjobCache")
public class YwQuartzjobCache implements ICache<YwQuartzjob> {

	private static final long serialVersionUID = 1L;

	private Map<String, YwQuartzjob> configData = new LinkedHashMap<String, YwQuartzjob>();

	@Autowired
	private YwQuartzjobService ywQuartzjobService;
	@Autowired
	SimpleScheduledBeanPostProcessor simpleScheduledBeanPostProcessor;
	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public void refresh() throws Exception {
		List<YwQuartzjob> list = ywQuartzjobService.queryList(new YwQuartzjob());
		if (list != null && list.size() > 0) {
			configData.clear();
			for (YwQuartzjob quartzjob : list) {
					String job_name = quartzjob.getJob_name();
				configData.put(job_name, quartzjob);
			}
		}
		for (YwQuartzjob quartzjob : list) {
			  simpleScheduledBeanPostProcessor.reSetPostProcess(quartzjob.getJob_name());
		}
	}

	@Override
	public String getId() {
		return "quartzjobCache";
	}
	
	/**
	 * 定时任务执行时间获取
	 * @param job_name
	 * @param trigger_time
	 * @return
	 */
	public String getCron(String job_name, String trigger_time){
		YwQuartzjob job = getConfigData().get(job_name);
		if(job==null){
			return trigger_time;
		}
		return job.getTrigger_time();
	}
	
	/**
	 * 定时任务是否生效
	 * @param job_name
	 * @param inuse
	 * @return
	 */
	public boolean inuse(String job_name, boolean inuse){
		YwQuartzjob job = getConfigData().get(job_name);
		if(job==null){
			return inuse;
		}
		return "1".equals(job.getValid_flag());
	}

	/**
	 * Object 为 Quartzjob
	 */
	@Override
	public Map<String, YwQuartzjob> getConfigData() {
		return configData;
	}

}
