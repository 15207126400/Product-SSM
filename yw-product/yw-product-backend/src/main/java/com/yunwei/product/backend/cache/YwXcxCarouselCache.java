package com.yunwei.product.backend.cache;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.yunwei.common.cache.ICache;
import com.yunwei.common.util.RedisClientUtil;
import com.yunwei.product.backend.service.YwXcxCarouselService;
import com.yunwei.product.common.model.YwXcxCarousel;

/**
 * 	轮播图缓存实现类
* @ClassName: YwXcxCarouselCache 
* @Description: TODO(TODO) 
* @author 晏飞
* @date 2018年8月29日 下午3:57:54 
*
 */
@Component("ywXcxCarouselCache")
public class YwXcxCarouselCache implements ICache<YwXcxCarousel>{
	
	private static Map<String,YwXcxCarousel> configData= new LinkedHashMap<String, YwXcxCarousel>();
	
	@Autowired
	private YwXcxCarouselService ywXcxCarouselService;
	
	private final static String XCXCONFIG_KEY = "carousel";

	@Override
	public int getOrder() {
		
		return 0;
	}

	@Override
	public void refresh() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "1");
    	map.put("db_name", "yw_images_xcxthumbnail");
    	map.put("thumbnail_type", "1");
		// 先从数据库中查出所需的数据
		List<YwXcxCarousel> ywXcxCarousel = ywXcxCarouselService.queryList(map);
		// 遍历
		for (YwXcxCarousel item : ywXcxCarousel) {
			// 将数据存入本地缓存 (map)
			configData.put(item.getId(), item);
		}
		// 将文件加载到redis缓存中，供接口服务器使用 (远程缓存服务器)
		RedisClientUtil.set(XCXCONFIG_KEY, JSON.toJSONString(ywXcxCarousel));
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return "ywXcxCarouselCache";
	}

	@Override
	public Map<String, YwXcxCarousel> getConfigData() {
		// TODO Auto-generated method stub
		return configData;
	}

}
