package com.yunwei.product.common.dao;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.product.common.model.YwXcxCarousel;

/**
 * 小程序轮播图Dao
* @ClassName: YwXcxCarouselDao 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年08月23日 下午16:04:12
*
 */
public interface YwXcxCarouselDao extends IBaseDao<YwXcxCarousel>{
	
	public String queryByImageId(String id);
}
