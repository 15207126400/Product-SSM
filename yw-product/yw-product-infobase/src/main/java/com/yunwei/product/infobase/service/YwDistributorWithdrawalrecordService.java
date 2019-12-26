package com.yunwei.product.infobase.service;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.serivce.IBaseSerivce;
import com.yunwei.product.common.backend.model.dto.YwDistributorWithdrawalrecordDto;
import com.yunwei.product.common.model.YwDistributorWithdrawalrecord;


/**
 * 提现记录
 * @author zhangjh
 *
 */
public interface YwDistributorWithdrawalrecordService extends IBaseSerivce<YwDistributorWithdrawalrecord>{
	
	
	public double queryWithdrawalTotals(YwDistributorWithdrawalrecord ywDistributorWithdrawalrecord);
	
	public List<YwDistributorWithdrawalrecordDto> queryUnionMemberList(Map<String,Object> paramMap);

}
