package com.yunwei.context.sys.usercenter.service;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.serivce.IBaseSerivce;
import com.yunwei.context.sys.usercenter.model.YwUser;
import com.yunwei.context.sys.usercenter.model.YwUserCustomer;
import com.yunwei.context.sys.usercenter.model.dto.YwUserCustomerAndYwUserDto;

/**
 * 客户信息service
* @ClassName: YwUserCustomerService 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年09月17日 下午14:42:52
*
 */
public interface YwUserCustomerService extends IBaseSerivce<YwUserCustomer>{
	
	/**
	 * 用户信息联合查询(基础信息表和客户信息表)
	*
	*@param map		传入的参数
	*@return		联合查询对象
	*YwUserCustomerDto		
	 */
	public List<YwUserCustomerAndYwUserDto> queryUnionYwUserListByMap(Map<String, Object> map);

	/**
	 * 更新客户和操作员基本信息
	 * @param ywUser
	 * @param ywUserCustomer
	 * @return
	 */
	public int updateCustomerAndOperator(YwUser ywUser,YwUserCustomer ywUserCustomer);

}
