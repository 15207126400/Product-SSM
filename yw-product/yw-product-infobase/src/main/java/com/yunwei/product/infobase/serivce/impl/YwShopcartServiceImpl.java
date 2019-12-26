package com.yunwei.product.infobase.serivce.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.common.dao.YwShopcartDao;
import com.yunwei.product.common.model.YwShopcart;
import com.yunwei.product.infobase.service.YwShopcartService;
/**
 * 
* @ClassName: ShopDetailDao 
* @Description: TODO(购物车接口实现层) 
* @author 晏飞
* @date 2017年11月18日 下午5:09:49 
*
 */
@Service
public class YwShopcartServiceImpl extends IBaseServiceImpl<YwShopcart> implements YwShopcartService {

	@Autowired
    private YwShopcartDao ywShopcartDao;
	
	@Override
	protected IBaseDao<YwShopcart> getBaseDao() {
		return ywShopcartDao;
	}

	public int addPaycat(YwShopcart ywShopcart) {
		
		return ywShopcartDao.addPaycat(ywShopcart);
	}

	public List<YwShopcart> queryAllPaycat(Map<String, Object> map) {
		
		return ywShopcartDao.queryAllPaycat(map);
	}

	public YwShopcart queryByPaycat(int id) {
		
		return ywShopcartDao.queryByPaycat(id);
	}

	public int deletePaycat(Map<String, Object> map){
		
		return ywShopcartDao.deletePaycat(map);
	}

	public YwShopcart queryByOpenid(int id) {
		
		return ywShopcartDao.queryByOpenid(id);
	}

	

	

}
