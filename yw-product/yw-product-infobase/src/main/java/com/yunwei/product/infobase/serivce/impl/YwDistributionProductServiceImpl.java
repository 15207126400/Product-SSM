package com.yunwei.product.infobase.serivce.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.common.dao.YwDistributionProductDao;
import com.yunwei.product.common.model.YwDistributionProduct;
import com.yunwei.product.common.model.YwProduct;
import com.yunwei.product.infobase.service.YwDistributionProductService;
import com.yunwei.product.infobase.service.YwProductService;
@Service
public class YwDistributionProductServiceImpl extends IBaseServiceImpl<YwDistributionProduct> implements YwDistributionProductService {

	@Autowired
	private YwDistributionProductDao ywDistributionProductDao;
	@Autowired
	private YwProductService ywProductService;

	@Override
	protected IBaseDao<YwDistributionProduct> getBaseDao() {
		return ywDistributionProductDao;
	}

	@Transactional
	@Override
	public int insertAndUpdateProduct(YwDistributionProduct ywDistributionProduct) {
		// 查询商品
		YwProduct ywProduct = ywProductService.queryById(ywDistributionProduct.getProduct_id());
		if(ywProduct != null){
			// 设置商品名称
			ywDistributionProduct.setProduct_name(ywProduct.getTitle());
		}
		this.insert(ywDistributionProduct);
		return 0;
	}
	
	
}
