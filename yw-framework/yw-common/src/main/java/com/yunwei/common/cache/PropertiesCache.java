package com.yunwei.common.cache;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunwei.common.util.EnvironmentUtils;


/**
 * 功能说明: 使用properties文件的统一缓存基类<br>
 * 系统版本: v1.0<br>
 * 开发人员: @author kongdy<br>
 * 开发时间: 2015年7月22日<br>
 */
public abstract class PropertiesCache implements ICache<Properties> {

	private static final long serialVersionUID = 1L;

	private final Logger logger = LoggerFactory.getLogger(PropertiesCache.class);

	private Properties config = new Properties();

	/**
	 * 因jdbc使用需要优先加载
	 */
	public int getOrder() {
		return -100;
	}

	public synchronized void refresh() throws Exception {
		config = new Properties();
		String[] filenames = getPropertiesNames();
		boolean loadone = false;
		for (int i = 0; i < filenames.length; i++) {
			InputStream configIs = null;
			try {
				// 以“/”跟路径方式拼装配置文件路径，EnvironmentUtils工具类要求
				String filePath = EnvironmentUtils.getFileAbsolutePath(String.format("/%s/%s", getId(), filenames[i]));
				configIs = new FileInputStream(filePath);
				config.load(configIs);
				loadone = true;
			} catch (Exception e) {
				if (!loadone && i + 1 == filenames.length) {
					throw e;
				}
				logger.error(String.format("未找到配置文件[%s]", filenames[i]), e);
			} finally {
				if (configIs != null) {
					try {
						configIs.close();
					} catch (IOException e) {
						logger.error("关闭文件流错误", e);
					}
				}
			}
		}
	}

	public String getConfigValue(String config_key) {
		if (this.config != null) {
			return (String)getConfigData().get(config_key);
		}
		return null;
	}

	public void setConfig(Properties config) {
		this.config = config;
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	public Map getConfigData() {
		return config;
	}

	public abstract String[] getPropertiesNames();
	
	/**
	 * 在刷新该缓存前调用
	 */
	public void onBeforeRefresh() {}

	/**
	 * 在刷新该缓存后调用
	 */
	public void onAfterRefresh() {}
}
