package com.yunwei.product.infobase.service;

import com.yunwei.common.base.serivce.IBaseSerivce;
import com.yunwei.product.common.model.YwDistributionProduct;


/**
 * 
* @ClassName: YwDistributionProductDao 
* @Description: 分销商品表  
* @author 晏飞
* @date 2018年3月15日 下午3:32:46 
*
 */
public interface YwDistributionProductService extends IBaseSerivce<YwDistributionProduct>{
	
	/**
	 * 添加并更新商品
	 * @param ywDistributionProduct
	 * @return
	 */
	public int insertAndUpdateProduct(YwDistributionProduct ywDistributionProduct);
	
}
