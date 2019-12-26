package com.yunwei.common.autotask;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.MethodInvokingRunnable;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

import com.yunwei.common.util.DateUtil;
import com.yunwei.common.util.RedisClientUtil;
import com.yunwei.common.util.WebServerUtil;

/**
 * 简单轮询任务执行器，线程池可配置
* @ClassName: SimpleScheduledBeanPostProcessor 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年6月28日 下午3:09:45 
*
 */
@Component
public class SimpleScheduledBeanPostProcessor extends ScheduledAnnotationBeanPostProcessor {

	// @Autowired(required = false)
	@Resource(name = "taskScheduler")
	private ThreadPoolTaskScheduler scheduler;

	private StringValueResolver embeddedValueResolver;

	private ApplicationContext applicationContext;

	private final ScheduledTaskRegistrar registrar = new ScheduledTaskRegistrar();

	private final Map<Runnable, String> cronTasks = new HashMap<Runnable, String>();

	public Object postProcessAfterInitialization(final Object bean, String beanName) {
		// 删除已修改的定时任务
		Runnable run = null;
		for (Runnable key : cronTasks.keySet()) {
			MethodInvokingRunnable x = (MethodInvokingRunnable)key;
			ScheduledBean scheduledBean1 = (ScheduledBean)x.getTargetObject();
			if (StringUtils.equals(scheduledBean1.getExecutorName(), beanName)) {
				run = key;
			}
		}
		if (run != null) {
			cronTasks.remove(run);
		}
		Class<?> targetClass = AopUtils.getTargetClass(bean);
		if (EasyAutotaskExecutor.class.isAssignableFrom(targetClass)) {
			EasyAutotaskExecutor executor = (EasyAutotaskExecutor)bean;
			if (!executor.inuse()) {// 轮询任务未启用
				return bean;
			}
			String cron = executor.getCron();
			if (embeddedValueResolver != null) {
				cron = embeddedValueResolver.resolveStringValue(cron);
			}
			ScheduledBean scheduledBean = new ScheduledBean(applicationContext, beanName);
			MethodInvokingRunnable runnable = new MethodInvokingRunnable();
			runnable.setTargetObject(scheduledBean);
			runnable.setTargetMethod("execute");
			runnable.setArguments(new Object[0]);
			try {
				runnable.prepare();
			} catch (Exception ex) {
				throw new IllegalStateException("failed to prepare task", ex);
			}
			cronTasks.put(runnable, cron);
		}
		return bean;
	}

	public void onApplicationEvent(ContextRefreshedEvent event) {
		super.onApplicationEvent(event);
		if (event.getApplicationContext() == this.applicationContext) {
			if (this.scheduler != null) {
				this.registrar.setScheduler(this.scheduler);
			}
			this.registrar.setCronTasks(cronTasks);
			this.registrar.afterPropertiesSet();
		}
	}

	public void setEmbeddedValueResolver(StringValueResolver resolver) {
		this.embeddedValueResolver = resolver;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public boolean reSetPostProcess(String beanName) {
		try {
			if (!applicationContext.containsBean(beanName)) {
				return false;
			}
			// 重新加入定时任务
			Object bean = applicationContext.getBean(beanName);
			postProcessAfterInitialization(bean, beanName);
			this.registrar.destroy();
			if (this.scheduler != null) {
				this.registrar.setScheduler(this.scheduler);
			}
			this.registrar.setCronTasks(cronTasks);
			logger.info("reSetPostProcess当前定时任务列表" + cronTasks.toString());
			this.registrar.afterPropertiesSet();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isAutoBeanName(String beanName) {
		return applicationContext.containsBean(beanName);
	}
}

class ScheduledBean {

	String executorName;
	ApplicationContext applicationContext;

	protected static final String PREFIX = "AutoTaskServers_";
	private final Logger logger = LoggerFactory.getLogger(ScheduledBean.class);

	public ScheduledBean(ApplicationContext applicationContext, String executor) {
		this.executorName = executor;
		this.applicationContext = applicationContext;
	}

	public void execute() {
		Object obj = applicationContext.getBean(executorName);
//		String server_address = "";
//		try {
//			server_address = WebServerUtil.getServiceAddress(applicationContext);
//			logger.debug("轮询机{}配置路径为{}", executorName, server_address);
//		} catch (Exception e) {
//			logger.error("获取{}所在服务器地址出错，忽略该机器上的轮询机", executorName, e);
//			return;
//		}

		String cron = "";// 定时任务执行时间
		boolean isSingleton = false;// 定时任务是否是单例

		if (obj instanceof EasyAutotaskExecutor) {
			EasyAutotaskExecutor executor = (EasyAutotaskExecutor)obj;
			cron = executor.getCron();
			isSingleton = executor.isSingleton();
		} else if (obj instanceof Proxy) {
			try {
				Method getCronMethod = EasyAutotaskExecutor.class.getMethod("getCron");
				cron = (String)Proxy.getInvocationHandler(obj).invoke(obj, getCronMethod, null);

				Method isSingletonMethod = EasyAutotaskExecutor.class.getMethod("isSingleton");
				isSingleton = (Boolean)Proxy.getInvocationHandler(obj).invoke(obj, isSingletonMethod, null);
			} catch (Throwable e) {
				logger.error(e.getMessage(), e);
				return;
			}
		}

		if (isSingleton) {
//			logger.debug("轮询机{}是单例模式(不能同时启动多个)", executorName);
//			boolean isSelfMain = setAutoServerStatus(cron, server_address);
//			if (isSelfMain) {
//				logger.debug("{}开始执行定时任务{}", server_address, executorName);
//				executeTask(obj);
//			} else {
//				logger.debug("{}未设置为主轮询机，不执行轮询任务{}", server_address, executorName);
//			}
			executeTask(obj);
		} else {
//			logger.debug("轮询机{}不是单例模式(可以同时启动多个)，即将启动{}上的轮询机", executorName, server_address);
			executeTask(obj);
		}
	}

	/**
	 * 执行定时任务
	 * @author yejg
	 * @param obj
	 */
	private void executeTask(Object obj) {
		if (obj instanceof EasyAutotaskExecutor) {
			((EasyAutotaskExecutor)obj).execute();
		} else if (obj instanceof Proxy) {
			try {
				Method method = EasyAutotaskExecutor.class.getMethod("execute");
				Proxy.getInvocationHandler(obj).invoke(obj, method, null);
			} catch (Throwable e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * 部署多个轮询机时，确保只有一个机器上的轮询任务在执行，避免重复执行
	 * 
	 * <pre>
	 * 抢占式，谁先抢到谁执行。
	 * 1、先试着去抢占：如果redis上不存在该轮询机，就把自身设置到redis（使用setnx方法）
	 * 2、查询redis上的配置，看配置的是不是自己：
	 *   如果是自己：则把有效期更新一下 （使用set方法） ==> 然后自己要执行轮询任务
	 *   如果不是自己：什么的都不做
	 * </pre>
	 * @author yejg
	 * @param cron
	 * @param server_address
	 * @return
	 */
	private boolean setAutoServerStatus(String cron, String server_address) {
		boolean setSelf2Main = false;
		int seconds = getExpireTime(cron);

		// Redis上无此key，那么调用setnx（不存在则写入）
		logger.debug("抢占式写入,setnx(不存在则写入):新建[{}={}]到redis服务器,存活时间为{}秒", executorName, server_address, seconds + 1);
		RedisClientUtil.setnx(executorName, server_address, seconds);

		String currAutoServerUrl = RedisClientUtil.get(executorName);
		logger.debug("Redis上暂存的[{}]类型的轮询机配置为{}", executorName, currAutoServerUrl);
		if (StringUtils.equals(server_address, currAutoServerUrl)) {
			// 更新
			logger.debug("set:设置[{}={}]到redis服务器,存活时间为{}秒", executorName, server_address, seconds + 1);
			RedisClientUtil.set(executorName, server_address, seconds);
			setSelf2Main = true;
		} else {
			setSelf2Main = false;
		}

		return setSelf2Main;
	}

	/**
	 * 计算存活时间
	 * @author yejg
	 * @param cron
	 * @return
	 */
	private int getExpireTime(String cron) {
		// 计算缓存存活时间
		long current = System.currentTimeMillis();
		CronTrigger cronTrigger = new CronTrigger(cron);
		// 轮询机下次启动时间
		Date date = cronTrigger.nextExecutionTime(new SimpleTriggerContext());

		logger.debug("当前时间：" + DateUtil.format(new Date(current), DateUtil.TIMESTAMP_FORMAT));
		logger.debug("下次启动时间：" + DateUtil.format(date, DateUtil.TIMESTAMP_FORMAT));

		int seconds = (int)Math.ceil((date.getTime() - current) / 1000.0);
		logger.debug("距离下次启动时间还有{}秒", seconds);

		return seconds;
	}

	public String getExecutorName() {
		return executorName;
	}

}