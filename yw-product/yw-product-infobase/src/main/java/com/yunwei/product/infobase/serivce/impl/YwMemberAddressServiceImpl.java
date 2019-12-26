package com.yunwei.product.infobase.serivce.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.common.dao.YwMemberAddressDao;
import com.yunwei.product.common.model.YwMemberAddress;
import com.yunwei.product.infobase.service.YwMemberAddressService;
/**
 * 
* @ClassName: ShopDetailDao 
* @Description: TODO(地址管理接口实现层) 
* @author 晏飞
* @date 2017年11月18日 下午5:09:49 
*
 */
@Service
public class YwMemberAddressServiceImpl extends IBaseServiceImpl<YwMemberAddress> implements YwMemberAddressService {

	@Autowired
    @Qualifier("ywMemberAddressDao")
    private YwMemberAddressDao ywMemberAddressDao;
	
	@Override
	protected IBaseDao<YwMemberAddress> getBaseDao() {
		// TODO Auto-generated method stub
		return ywMemberAddressDao;
	}

	public int addAddress(YwMemberAddress ywMemberAddress) {
		// TODO Auto-generated method stub
		return ywMemberAddressDao.addAddress(ywMemberAddress);
	}

	public List<YwMemberAddress> queryAddress(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return ywMemberAddressDao.queryAddress(map);
	}

	public int updateAddress(YwMemberAddress ywMemberAddress) {
		// TODO Auto-generated method stub
		return ywMemberAddressDao.updateAddress(ywMemberAddress);
	}

	public YwMemberAddress queryByAddress(int id) {
		// TODO Auto-generated method stub
		return ywMemberAddressDao.queryByAddress(id);
	}

	@Override
	public int deleteAddress(int id) {
		// TODO Auto-generated method stub
		return ywMemberAddressDao.deleteAddress(id);
	}

	@Override
	public int updateDefault(YwMemberAddress ywMemberAddress) {
		// TODO Auto-generated method stub
		return ywMemberAddressDao.updateDefault(ywMemberAddress);
	}



}
