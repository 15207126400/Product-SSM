package com.yunwei.product.common.dao;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.product.common.model.YwMemberAddress;

/**
 * 
* @ClassName: ywMemberAddressDao 
* @Description: TODO(地址管理dao层接口) 
* @author 晏飞
* @date 2017年11月18日 下午5:07:18 
*
 */
public interface YwMemberAddressDao extends IBaseDao<YwMemberAddress>{
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
