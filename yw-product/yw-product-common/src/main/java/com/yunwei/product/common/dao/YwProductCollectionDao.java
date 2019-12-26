package com.yunwei.product.common.dao;


import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.product.common.model.YwProductCollection;

/**
 * 
* @ClassName: ShopDetailDao 
* @Description: TODO(收藏dao层接口) 
* @author 晏飞
* @date 2017年11月18日 下午5:09:49 
*
 */
public interface YwProductCollectionDao extends IBaseDao<YwProductCollection>{
	//收藏商品
	public int addIslike(YwProductCollection ywProductPollection);
	
	//查询所有收藏
	public List<YwProductCollection> queryIslike(Map<String, Object> map);
	
	//根据id查看
	public YwProductCollection queryByIslike(Map<String, Object> map);
	
	//删除收藏 --- 根据商品id
	public int deleteIslike(int id);
	
	//删除收藏 --- 根据收藏表id
	public int deleteIslikeById(int id);
	
	//查看某商品是否已收藏
	public int findsum(Map<String, Object> map);
}
