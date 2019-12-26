package com.yunwei.product.backend.cache;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.yunwei.common.cache.ICache;
import com.yunwei.common.util.RedisClientUtil;
import com.yunwei.product.backend.service.YwXcxNavigationService;
import com.yunwei.product.common.model.YwXcxNavigation;

@Component("ywXcxNavigationCache")
public class YwXcxNavigationCache implements ICache<YwXcxNavigation> {

	private static Map<String,YwXcxNavigation> configData= new LinkedHashMap<String, YwXcxNavigation>();
	
	@Autowired
	private YwXcxNavigationService ywXcxNavigationService;
	
	private final static String XCXCONFIG_KEY = "navigation";
	
	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public void refresh() throws Exception {
		List<YwXcxNavigation> XcxNavigations = ywXcxNavigationService.queryList(new YwXcxNavigation());
		if(!CollectionUtils.isEmpty(XcxNavigations)){
			for(YwXcxNavigation XcxNavigation : XcxNavigations){
				configData.put(XcxNavigation.getId(), XcxNavigation);
			}
		}
		
		// 将文件加载到redis缓存中，供接口服务器使用
		RedisClientUtil.set(XCXCONFIG_KEY, JSON.toJSONString(XcxNavigations));
	}

	/**
	 * 根据角色id获取角色资源
	 * @param ro_id
	 * @return
	 */
	public YwXcxNavigation XcxNavigationResource(String ro_id){
		return configData.get(ro_id);
	}
	
	@Override
	public String getId() {
		return "YwXcxNavigationCache";
	} 

	@Override
	public Map<String, YwXcxNavigation> getConfigData() {
		return configData;
	}

}
