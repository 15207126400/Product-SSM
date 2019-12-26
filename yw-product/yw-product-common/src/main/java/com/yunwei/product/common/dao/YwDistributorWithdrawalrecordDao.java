package com.yunwei.product.common.dao;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.product.common.backend.model.dto.YwDistributorWithdrawalrecordDto;
import com.yunwei.product.common.model.YwDistributorWithdrawalrecord;

/**
 * 云维字典dao
 * @author zhangjh
 *
 */
public interface YwDistributorWithdrawalrecordDao extends IBaseDao<YwDistributorWithdrawalrecord>{
	
	
	public List<YwDistributorWithdrawalrecordDto> queryUnionMemberList(Map<String,Object> paramMap);	

}
