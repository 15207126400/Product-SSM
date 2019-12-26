package com.yunwei.product.infobase.serivce.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.common.util.NumberUtil;
import com.yunwei.product.common.backend.model.dto.YwDistributorWithdrawalrecordDto;
import com.yunwei.product.common.dao.YwDistributorWithdrawalrecordDao;
import com.yunwei.product.common.model.YwDistributorWithdrawalrecord;
import com.yunwei.product.infobase.service.YwDistributorWithdrawalrecordService;

@Service
public class YwDistributorWithdrawalrecordImpl extends IBaseServiceImpl<YwDistributorWithdrawalrecord> implements YwDistributorWithdrawalrecordService {
	
	@Autowired
	private YwDistributorWithdrawalrecordDao ywDistributorWithdrawalrecordDao;

	@Override
	protected IBaseDao<YwDistributorWithdrawalrecord> getBaseDao() {
		return ywDistributorWithdrawalrecordDao;
	}
	
	/**
	 * 查询用户已提现金额总和
	 */
	@Override
	public double queryWithdrawalTotals(YwDistributorWithdrawalrecord ywDistributorWithdrawalrecord) {
		List<YwDistributorWithdrawalrecord> withdrawalrecords =  this.queryList(ywDistributorWithdrawalrecord);
		if(CollectionUtils.isNotEmpty(withdrawalrecords)){
			// 求提现总金额
			double temTotals = 0.00;
			for(YwDistributorWithdrawalrecord withdrawalrecord:withdrawalrecords){
				
				temTotals = Double.valueOf(NumberUtil.add(temTotals, Double.valueOf(withdrawalrecord.getDis_wid_money())));
			}
			return temTotals;
		}
		return 0;
	}

	@Override
	public List<YwDistributorWithdrawalrecordDto> queryUnionMemberList(Map<String, Object> paramMap) {
		return ywDistributorWithdrawalrecordDao.queryUnionMemberList(paramMap);
	}

	

}
