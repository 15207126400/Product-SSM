package com.yunwei.product.backend.service;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.serivce.IBaseSerivce;
import com.yunwei.product.common.model.YwMemberAddress;

/**
 * 
* @ClassName: ShopDetailDao 
* @Description: TODO(地址接口) 
* @author 晏飞
* @date 2017年11月18日 下午5:09:49 
*
 */
public interface YwMemberAddressService extends IBaseSerivce<YwMemberAddress>{

		//地址保存
		public int addAddress(YwMemberAddress ywMemberAddress);
		
		//查询所有地址
		public List<YwMemberAddress> queryAddress(Map<String, Object> map);
		
		//根据id查询地址信息
		public YwMemberAddress queryByAddress(int id);
		
		//修改地址信息
		public int updateAddress(YwMemberAddress ywMemberAddress);
		
		//删除地址信息
		public int deleteAddress(int id);
		
		//修改默认地址
		public int updateDefault(YwMemberAddress ywMemberAddress);
}
