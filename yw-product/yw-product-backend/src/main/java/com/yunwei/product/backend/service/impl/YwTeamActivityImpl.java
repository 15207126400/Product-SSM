package com.yunwei.product.backend.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.product.backend.service.YwProductService;
import com.yunwei.product.backend.service.YwTeamActivityService;
import com.yunwei.product.common.backend.model.dto.YwTeamActivityDto;
import com.yunwei.product.common.dao.YwProductDao;
import com.yunwei.product.common.dao.YwTeamActivityDao;
import com.yunwei.product.common.model.YwProduct;
import com.yunwei.product.common.model.YwTeamActivity;

@Service
public class YwTeamActivityImpl extends IBaseServiceImpl<YwTeamActivity> implements YwTeamActivityService {
	
	@Autowired
	private YwTeamActivityDao ywTeamActivityDao;
	@Autowired
	private YwProductService ywProductService;
	@Autowired
	private YwProductDao ywProductDao;
	
	@Override
	protected IBaseDao<YwTeamActivity> getBaseDao() {
		return ywTeamActivityDao;
	}
	@Override
	public List<YwTeamActivity> queryUnionProductList(Map<String, Object> paramMap) {
		
		return ywTeamActivityDao.queryUnionProductList(paramMap);
	}
	
	//@Transactional
	@Override
	public int teamActivityInsertAndProductUpdate(YwTeamActivity ywTeamActivity) {
		// 查询商品
		YwProduct ywProduct = ywProductService.queryById(ywTeamActivity.getProduct_id());
		if(ywProduct != null){
			// 设置商品名称
			ywTeamActivity.setProduct_name(ywProduct.getTitle());
		}
		
		// 添加团购活动
		this.insert(ywTeamActivity);
		
		String product_markets = ywProduct.getProduct_markets();
		JSONObject jsonObject = new JSONObject();
		if(StringUtils.isBlank(product_markets)){
			jsonObject.put("1", "团购");
		} else {
			jsonObject = JSONObject.parseObject(product_markets);
			if(!jsonObject.containsKey("1")){
				jsonObject.put("1", "团购");
			}
		}
		ywProduct.setProduct_markets(jsonObject.toJSONString());
		
		// 更新商品
		ywProductService.update(ywProduct);
		return 0;
	}
	
	//@Transactional
	@Override
	public int teamActivityUpdateAndProductUpdate(YwTeamActivity ywTeamActivity) {
		// 查询商品
		YwProduct ywProduct = ywProductService.queryById(ywTeamActivity.getProduct_id());
		if(ywProduct != null){
			// 设置商品名称
			ywTeamActivity.setProduct_name(ywProduct.getTitle());
		}
		
		// 添加团购活动
		this.update(ywTeamActivity);
		
		String product_markets = ywProduct.getProduct_markets();
		JSONObject jsonObject = new JSONObject();
		if(StringUtils.isBlank(product_markets)){
			jsonObject.put("1", "团购");
		} else {
			jsonObject = JSONObject.parseObject(product_markets);
			if(!jsonObject.containsKey("1")){
				jsonObject.put("1", "团购");
			}
		}
		ywProduct.setProduct_markets(jsonObject.toJSONString());
		
		// 更新商品
		ywProductService.update(ywProduct);
		return 0;
	}

}
