package com.yunwei.product.infobase.service;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.serivce.IBaseSerivce;
import com.yunwei.product.common.backend.model.dto.YwCommissionrecordDto;
import com.yunwei.product.common.model.YwDistributorCommissionrecord;


/**
 * 云维字典
 * @author zhangjh
 *
 */
public interface YwDistributorCommissionrecordService extends IBaseSerivce<YwDistributorCommissionrecord>{
	
	
	public List<YwCommissionrecordDto> queryUnionMemberList(Map<String,Object> paramMap);

}
