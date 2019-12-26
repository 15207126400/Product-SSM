package com.yunwei.product.common.dao;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.product.common.model.YwShopcart;

/**
 * 
* @ClassName: ShopDetailDao 
* @Description: TODO(购物车dao层接口) 
* @author 晏飞
* @date 2017年11月18日 下午5:09:49 
*
 */
public interface YwShopcartDao extends IBaseDao<YwShopcart>{
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
