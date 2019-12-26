package com.yunwei.product.backend.service;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.serivce.IBaseSerivce;
import com.yunwei.product.common.model.YwDistributorRule;


/**
 * 
* @ClassName: YwDistributorRuleService 
* @Description: 商家设置分销规则的信息表  
* @author 晏飞
* @date 2018年3月15日 下午3:43:03 
*
 */
public interface YwDistributorRuleService extends IBaseSerivce<YwDistributorRule>{
	
	public YwDistributorRule queryLast(YwDistributorRule ywDistributorRule);

}
