package com.yunwei.product.common.dao;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.product.common.backend.model.dto.YwDistributorDto;
import com.yunwei.product.common.model.YwDistributor;

/**
 * 分销商信息
 * @author zhangjh
 *
 */
public interface YwDistributorDao extends IBaseDao<YwDistributor>{

	public List<YwDistributorDto> queryUnionMemberList(Map<String,Object> paramMap);
}
