package com.yunwei.context.config.cache;

import org.springframework.stereotype.Component;

import com.yunwei.common.cache.PropertiesCache;



/**
 * 前中后台域名配置
* @ClassName: ConnConfigCache 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年6月26日 下午2:35:50 
*
 */
@Component
public class ConnConfigCache extends PropertiesCache {

	@Override
	public String getId() {
		return "conn";
	}

	@Override
	public String[] getPropertiesNames() {
		return new String[] {"connection.properties"};
	}

}
