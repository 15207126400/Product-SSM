package com.yunwei.context.sys.usercenter.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import com.yunwei.context.sys.usercenter.dao.YwUserCustomerDao;
import com.yunwei.context.sys.usercenter.model.YwUser;
import com.yunwei.context.sys.usercenter.model.YwUserCustomer;
import com.yunwei.context.sys.usercenter.model.dto.YwUserCustomerAndYwUserDto;
import com.yunwei.context.sys.usercenter.service.YwUserCustomerService;
import com.yunwei.context.sys.usercenter.service.YwUserService;
import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;


@Service
public class YwUserCustomerServiceImpl extends IBaseServiceImpl<YwUserCustomer> implements YwUserCustomerService{
	
    private static Logger logger = Logger.getLogger(YwUserCustomerServiceImpl.class);
	@Autowired
	private YwUserCustomerDao ywUserCustomerDao;
	@Autowired
	private YwUserService ywUserService;

	@Override
	protected IBaseDao<YwUserCustomer> getBaseDao() {
		return ywUserCustomerDao;
	}
	
	@Override
	public int updateCustomerAndOperator(YwUser ywUser,YwUserCustomer ywUserCustomer) {
		// 更新客户信息
		super.update(ywUserCustomer);
		return 0;
	}

	@Override
	public List<YwUserCustomerAndYwUserDto> queryUnionYwUserListByMap(Map<String, Object> map) {
		map.put("table_name", "yw_user");
		List<YwUserCustomer> list = ywUserCustomerDao.queryUnionList(map);
		List<YwUserCustomerAndYwUserDto> newList = new ArrayList<YwUserCustomerAndYwUserDto>();
		if(CollectionUtils.isNotEmpty(list)){
			for (YwUserCustomer ywUserCustomer : list) {
				YwUserCustomerAndYwUserDto ywUserCustomerAndYwUserDto = new YwUserCustomerAndYwUserDto();
				try {
					ywUserCustomerAndYwUserDto.setPay_datetime(ywUserCustomer.getYwUser().getPay_datetime());
					ywUserCustomerAndYwUserDto.setExpire_datetime(ywUserCustomer.getYwUser().getExpire_datetime());
					ConvertUtils.register(new DateConverter(null), java.util.Date.class);
					BeanUtils.copyProperties(ywUserCustomerAndYwUserDto, ywUserCustomer);
				} catch (Exception e) {
					e.printStackTrace();
				}
				newList.add(ywUserCustomerAndYwUserDto);
			}
		}
		return newList;
	}
	
	
	
}
