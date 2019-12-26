package com.yunwei.context.config.cache;

import org.springframework.stereotype.Component;

import com.yunwei.common.cache.PropertiesCache;



/**
 * 券商显示功能开关
* @ClassName: FuncConfigCache 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年6月26日 下午2:36:06 
*
 */
@Component
public class FuncConfigCache extends PropertiesCache {

	@Override
	public String getId() {
		return "func";
	}

	@Override
	public String[] getPropertiesNames() {
		return new String[] {"func.properties"};
	}

}
