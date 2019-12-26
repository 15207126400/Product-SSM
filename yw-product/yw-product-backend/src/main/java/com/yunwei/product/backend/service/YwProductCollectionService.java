package com.yunwei.product.backend.service;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.serivce.IBaseSerivce;
import com.yunwei.product.common.model.YwProductCollection;

/**
 * 
* @ClassName: ShopDetailDao 
* @Description: TODO(收藏接口) 
* @author 晏飞
* @date 2017年11月18日 下午5:09:49 
*
 */
public interface YwProductCollectionService extends IBaseSerivce<YwProductCollection>{

	// 收藏商品
	public int addIslike(YwProductCollection ywProductPollection);

	// 查询所有收藏
	public List<YwProductCollection> queryIslike(Map<String, Object> map);

	// 根据id查看
	public YwProductCollection queryByIslike(Map<String, Object> map);

	// 删除收藏
	public int deleteIslike(int id);

	// 删除收藏 --- 根据收藏表id
	public int deleteIslikeById(int id);

	// 查看某商品是否已收藏
	public int findsum(Map<String, Object> map);
}
