package com.yunwei.common.factory;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yunwei.common.util.SpringContext;


/**
 * 配置化工厂基类
* @ClassName: ConfigedFactory 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年5月31日 下午1:09:31 
* 
* @param <T>
 */
public abstract class ConfigedFactory<T> {
	
	private Logger logger = LoggerFactory.getLogger(ConfigedFactory.class);

	@Autowired
	private SpringContext springContext;

	protected T getBean() {
//		String config = PropertiesUtils.get(getConfigKey());
		String config = "";
		T bean = (T)springContext.getBean(getConfigMapping().get(config));
		if (bean == null) {
			logger.error("系统配置项[{}={}],但获取不到相应的实现类,请检查系统配置", getConfigKey(), config);
			throw new NullPointerException("系统配置有误导致空指针异常");
		}
		return bean;
	}
	
	/**
	 * 默认配置为空,取默认值
	 * @author hufeng
	 * @param defaultConfigKey
	 * @return
	 */
	protected T getBean(String defaultConfigValue) {
//		String config = PropertiesUtils.get(getConfigKey());
		String config = "";
		if (StringUtils.isBlank(config)) {
			logger.info("当前系统配置的{}为空,默认赋值为[{}]", getConfigKey(), defaultConfigValue);
			config = defaultConfigValue;
		}

		T bean = (T)springContext.getBean(getConfigMapping().get(config));
		if (bean == null) {
			logger.error("系统配置项[{}={}],但获取不到相应的实现类,请检查系统配置", getConfigKey(), config);
			throw new NullPointerException("系统配置有误导致空指针异常");
		}
		return bean;
	}

	protected abstract String getConfigKey();

	protected abstract Map<String, Class> getConfigMapping();

}
