package com.yunwei.product.infobase.service;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.serivce.IBaseSerivce;
import com.yunwei.product.common.backend.model.dto.YwDistributorSalestatusDto;
import com.yunwei.product.common.model.YwDistributorSalestatus;


/**
 * 分销销售记录
 * @author zhangjh
 *
 */
public interface YwDistributorSalestatusService extends IBaseSerivce<YwDistributorSalestatus>{
	
	public List<YwDistributorSalestatusDto> queryUnionMemberList(Map<String,Object> paramMap);
}
