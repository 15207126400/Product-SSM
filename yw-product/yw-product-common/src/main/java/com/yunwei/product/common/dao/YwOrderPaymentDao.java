package com.yunwei.product.common.dao;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.product.common.backend.model.dto.YwOrderPaymentDto;
import com.yunwei.product.common.model.YwOrderPayment;

/**
 * 云维字典dao
 * @author zhangjh
 *
 */
public interface YwOrderPaymentDao extends IBaseDao<YwOrderPayment>{
	
	public List<YwOrderPaymentDto> queryUnionMemberList(Map<String,Object> paramMap);
}
