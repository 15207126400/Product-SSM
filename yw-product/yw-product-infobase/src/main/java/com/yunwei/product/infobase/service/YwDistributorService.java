package com.yunwei.product.infobase.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;

import com.yunwei.common.base.serivce.IBaseSerivce;
import com.yunwei.product.common.backend.model.dto.YwDistributorDto;
import com.yunwei.product.common.model.YwDistributionProduct;
import com.yunwei.product.common.model.YwDistributor;
import com.yunwei.product.common.model.YwOrder;

/**
 * 云维字典
 * @author zhangjh
 *
 */
public interface YwDistributorService extends IBaseSerivce<YwDistributor>{
	public List<YwDistributorDto> queryUnionMemberList(Map<String,Object> paramMap);
	
	public YwDistributorDto queryParent(YwDistributor ywDistributor);
	
	public YwDistributor queryChilds(YwDistributor ywDistributor);
	
	public int createYwDisProduct(YwDistributor distributor, YwOrder ywOrder,YwDistributionProduct ywDistributionProduct);
	
	//没有上级分销商的情况生成分销记录
	public int createNotDisPid(YwDistributor ywDistributor ,YwOrder ywOrder);
	
	//有上级分销商的情况生成分销记录
	public int createDisPid(YwDistributor ywDistributor ,YwOrder ywOrder);
	
	//生成销售记录
	public int createDisSas(YwDistributor ywDistributor ,YwOrder ywOrder);

}
