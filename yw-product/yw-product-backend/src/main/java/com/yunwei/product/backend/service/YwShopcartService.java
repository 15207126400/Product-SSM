package com.yunwei.product.backend.service;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.serivce.IBaseSerivce;
import com.yunwei.product.common.model.YwShopcart;

/**
 * 
* @ClassName: ShopDetailDao 
* @Description: TODO(购物车) 
* @author 晏飞
* @date 2017年11月18日 下午5:09:49 
*
 */
public interface YwShopcartService extends IBaseSerivce<YwShopcart>{
	
		//加入购物车
		public int addPaycat(YwShopcart ywShopcart);
		
		//查询购物车信息
		public List<YwShopcart> queryAllPaycat(Map<String, Object> map);
		
		//根据id查询
		public YwShopcart queryByPaycat(int id);
		
		//根据openid查询
		public YwShopcart queryByOpenid(int id);
		
		//删除购物车商品
		public int deletePaycat(Map<String, Object> map);
	
}
