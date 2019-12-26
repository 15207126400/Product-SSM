package com.yunwei.product.common.dao;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.product.common.backend.model.dto.YwPointsDetailDto;
import com.yunwei.product.common.backend.model.dto.YwPointsDto;
import com.yunwei.product.common.model.YwPointsDetail;

/**
 * 积分明细Dao
* @ClassName: YwPointsDetailDao 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年07月26日 下午14:04:33
*
 */
public interface YwPointsDetailDao extends IBaseDao<YwPointsDetail>{
	public List<YwPointsDetailDto> queryUnionMemberList(Map<String,Object> paramMap);
	
	// 动态排序查询
	public List<YwPointsDetail> orderByQueryList(Map<String,Object> paramMap);

}
