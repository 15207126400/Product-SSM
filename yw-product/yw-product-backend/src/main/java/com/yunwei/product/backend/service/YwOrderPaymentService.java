package com.yunwei.product.backend.service;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.serivce.IBaseSerivce;
import com.yunwei.product.common.backend.model.dto.YwOrderPaymentDto;
import com.yunwei.product.common.model.YwOrderPayment;

/**
 * 云维字典
 * @author zhangjh
 *
 */
public interface YwOrderPaymentService extends IBaseSerivce<YwOrderPayment>{
	
	public List<YwOrderPaymentDto> queryUnionMemberList(Map<String,Object> paramMap);
}
