package com.yunwei.common.autotask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.yunwei.common.util.SpringContext;



/**
 * 简单轮询任务抽象类
* @ClassName: EasyAutotaskExecutor 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年5月18日 上午11:06:36 
*
 */
public abstract class EasyAutotaskExecutor {

	@Autowired
	private ApplicationContext context;

	/**
	 * 任务计划
	 * @return
	 */
	public abstract String getCron();

	/**
	 * 是否启用
	 * @return
	 */
	public abstract boolean inuse();

	/**
	 * 任务执行
	 */
	public abstract void execute();

	/**
	 * 是否是单例轮询任务。
	 * <p>
	 * 如果可以在多个机器上都启动该定时任务，子类需要复写返回false
	 * </p>
	 * @author yejg
	 * @return
	 */
	public boolean isSingleton() {
		boolean useConfigCache = SpringContext.useConfigCache(context);
		if (!useConfigCache) {
			// 不使用缓存、或者没有redis，则不支持单例
			return false;
		}
		return true;
	}
}
