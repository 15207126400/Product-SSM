package com.yunwei.product.common.dao;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.product.common.backend.model.dto.YwCommissionrecordDto;
import com.yunwei.product.common.model.YwDistributorCommissionrecord;

/**
 * 分销佣金
 * @author zhangjh
 *
 */
public interface YwDistributorCommissionrecordDao extends IBaseDao<YwDistributorCommissionrecord>{
	
	
	public List<YwCommissionrecordDto> queryUnionMemberList(Map<String,Object> paramMap);

}
