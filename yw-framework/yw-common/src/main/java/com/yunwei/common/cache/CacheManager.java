package com.yunwei.common.cache;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;
import com.yunwei.common.util.PropertiesUtils;
import com.yunwei.common.util.SpringContext;

/**
 * 功能说明: 缓存管理器<br>
 * 系统版本: v1.0<br>
 * 开发人员: @author zhangjh<br>
 * 开发时间: 2018年3月8日<br>
 */
@Lazy(false)
@Component("cacheManager")
public class CacheManager {

	private final Logger logger = LoggerFactory.getLogger(CacheManager.class);

	@Autowired
	private ApplicationContext context;
	@Autowired
	private SpringContext springContext;
	
	

	// 全局变量，只允许在init中赋值一次
	private static int wait_times = 0;

	@PostConstruct
	private void init() throws Exception {
		
       
		loadAndStartAutotask();
	}

	@PreDestroy
	private void destroy() {
		
	}

	/**
	 * 加载配置，并启动轮询，自动重新注册
	 */
	private void loadAndStartAutotask() {
		new Thread() {

			public void run() {
				refreshAllCache();
			}

		}.start();
	}
	
	/**
	 * 从数据库获取刷新全部缓存
	 */
	public void refreshAllCache() {
		/*
		 * 根据order顺序刷新，先刷新被引用项
		 */
		List<Map.Entry<String, ICache>> list = springContext.getOrderedBeans(ICache.class);
		if (list == null || list.isEmpty()) {
			if (wait_times > 3) {
				throw new RuntimeException("context初始化不正常，请检查配置");
			}
			logger.info("context未初始化，等待10秒加载缓存");
			try {
				Thread.sleep(10 * 1000);// context未初始化，等待10秒
			} catch (InterruptedException e) {
				logger.error("线程休眠发生错误", e);
			}
			wait_times++;
			refreshAllCache();
		}
		wait_times = 0;
		for (Map.Entry<String, ICache> entry : list) {
			try {
				refreshOne(entry.getValue().getId());
			} catch (Exception e) {
				logger.error("更新缓存发生错误", e);
			}
		}
	}

	/**
	 * 指定刷新
	 * @param cacheName
	 * @param isMain 是否为主配置服务器
	 * @return
	 */
	public boolean refreshOne(String cacheName) {
		ICache<?> cache = findCacheBeanByName(cacheName);

		   try {
			   if (cache instanceof PropertiesCache) {
					cacheName = cache.getId();
					PropertiesCache pcache = (PropertiesCache)cache;
					pcache.onBeforeRefresh();
					Map map = pcache.getConfigData();
					map.clear();
//					for (String file : pcache.getPropertiesNames()) {
////						if (isMain) {
////							loadFilesToRedis(file);
////						}
//						String jsonStr = RedisClientUtil.get(CACHE_CONFIG_KEY + file);
//						Properties prop = JSON.parseObject(jsonStr, Properties.class);
//						if (prop != null) {
//							map.putAll(prop);
//						}
//					}
					// 更新Properties文件，清除老配置重新加载
					PropertiesUtils.clear();
					pcache.onAfterRefresh();
//				} else if (cache instanceof XmlCache) {
//					cacheName = cache.getId();
//					XmlCache pcache = (XmlCache)cache;
//					pcache.onBeforeRefresh();
//					Map map = pcache.getConfigData();
//					map.clear();
//					List<String> filePathName = pcache.getXmlPath();
//					for (int i = 0; i < filePathName.size(); i++) {
//						// 不需要是主配置服务器
//						loadXMLToRedis(filePathName.get(i));
//					}
//					// 更新Properties文件，清除老配置重新加载
//					// PropertiesUtils.clear();
//					pcache.onAfterRefresh();
				} else {
					if (cache != null) {
						cache.refresh();
					}
				}
				if (cache != null) {
					cache.refresh();
				}
			logger.info("刷新缓存[" + cacheName + "]成功！");
			return true;
		} catch (Exception e) {
			logger.error("刷新缓存[" + cacheName + "]发生错误！", e);
			return false;
		}

	}

	

	/**
	 * 按cache名称或文件名称取缓存bean
	 * @param cacheName
	 * @return
	 */
	private ICache findCacheBeanByName(String cacheName) {
		Map<String, ICache> beans = SpringContext.getBeansOfType(ICache.class);
		for (Map.Entry<String, ICache> entry : beans.entrySet()) {
			if (cacheName.equals(entry.getKey())) {
				return entry.getValue();
			}
		}
		return null;
	}
}
