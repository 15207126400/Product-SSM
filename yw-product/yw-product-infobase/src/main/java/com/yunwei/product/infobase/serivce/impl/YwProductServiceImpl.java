package com.yunwei.product.infobase.serivce.impl;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.common.exception.BizException;
import com.yunwei.context.sys.service.YwAccessTokenService;
import com.yunwei.context.sys.usercenter.model.YwUserCustomerXcx;
import com.yunwei.context.sys.usercenter.service.YwUserCustomerXcxService;
import com.yunwei.context.token.support.WxAccessToken;
import com.yunwei.product.common.constants.DicConstant;
import com.yunwei.product.common.dao.YwProductDao;
import com.yunwei.product.common.model.YwImages;
import com.yunwei.product.common.model.YwProduct;
import com.yunwei.product.common.model.YwProductAttributename;
import com.yunwei.product.common.model.YwProductAttributevalue;
import com.yunwei.product.common.model.YwProductSku;
import com.yunwei.product.infobase.service.YwProductAttributenameService;
import com.yunwei.product.infobase.service.YwProductAttributevalueService;
import com.yunwei.product.infobase.service.YwProductService;
import com.yunwei.product.infobase.service.YwProductSkuService;
/**
 * 
* @ClassName: YwProductDao 
* @Description: TODO(商品接口实现层) 
* @author 晏飞
* @date 2017年11月18日 下午5:09:49 
*
 */
@Service
public class YwProductServiceImpl extends IBaseServiceImpl<YwProduct> implements YwProductService {

	@Autowired
    private YwProductDao ywProductDao;
	@Autowired
	private YwUserCustomerXcxService ywUserCustomerXcxService;
	
	
	@Autowired
	private YwProductAttributenameService ywProductAttributenameService;
	@Autowired
	private YwProductAttributevalueService ywProductAttributevalueService;
	@Autowired
	private YwProductSkuService ywProductSkuService;
	@Autowired
	private YwAccessTokenService ywAccessTokenService;
	
	@Override
	protected IBaseDao<YwProduct> getBaseDao() {
		return ywProductDao;
	}
	
	//查询所有商品
	public List<YwProduct> queryList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return ywProductDao.queryList(map);
	}

	//根据id查找商品
	public YwProduct queryById(String id) {
		// TODO Auto-generated method stub
		return ywProductDao.queryById(id);
	}

	public List<YwProduct> queryByTitle(YwProduct ywProduct) {
		// TODO Auto-generated method stub
		return ywProductDao.queryByTitle(ywProduct);
	}

	//@Transactional
	@Override
	public int addYwProduct(YwProduct ywProduct) {
		// 保存商品基本信息
		int num = ywProductDao.addYwProduct(ywProduct);
		
		// 添加商品其他信息
		insertProductOther(ywProduct);
		return num;
	}
	
	/**
	 * 添加商品其他信息
	 * @return
	 */
	private int insertProductOther(YwProduct ywProduct){
		// 保存属性名称并生成属性名称对象集合
		List<YwProductAttributename> attributenames = null;
		if(StringUtils.isNotBlank(ywProduct.getAttr_name())){
			attributenames = insertAttributeName(ywProduct.getAttr_name(),ywProduct.getShopType(),String.valueOf(ywProduct.getId()));
		}
		
		// 创建属性值对象集合
		List<YwProductAttributevalue>  attributevalues = null;
		String[] attrValues = ywProduct.getAttr_value();
		if(attrValues != null && attrValues.length > 0 && CollectionUtils.isNotEmpty(attributenames)){
			 attributevalues = createListAttributeValue(attributenames,attrValues);
		}
		
		// sku组合对象
		List<YwProductSku> productSkus = new ArrayList<YwProductSku>();
		String[] skuList = ywProduct.getSku_values();
		// 如果只添加了单条规格属性
		
		if(skuList != null && skuList.length > 0){
			for (String sku : skuList) {
					String[] temSku = sku.split(";");
					String sku_value = temSku[0];
					String sku_other = temSku[1];
					// 创建sku集合
					createSku(productSkus,ywProduct.getAttr_name(),sku_value,sku_other,String.valueOf(ywProduct.getId()));
			}
		} 
		
		
		// 批量插入属性值
		if(CollectionUtils.isNotEmpty(attributevalues)){
			ywProductAttributevalueService.insertBatch(attributevalues);
		}
		// 批量插入sku
		if(CollectionUtils.isNotEmpty(productSkus)){
			ywProductSkuService.insertBatch(productSkus);
		}
		
		// 批量插入图片
		productImgInsertBatch(ywProduct);
		
		return 0;
	}
	
	/**
	 * 保存属性名称并返回
	 * @param skuName
	 * @param skuValue
	 */
	private List<YwProductAttributename> insertAttributeName(String attr_name,String classify_id,String product_id){
		// 添加属性名称和属性值
		List<YwProductAttributename> attributenames = new ArrayList<YwProductAttributename>();
		String[] attrNames = attr_name.split(",");
		for(int i = 0 ; i < attrNames.length ; i ++){
			// 保存属性名称
			YwProductAttributename attributename = new YwProductAttributename();
			attributename.setClassify_id(classify_id);
			attributename.setAttrname_name(attrNames[i]);
			attributename.setProduct_id(product_id);
			ywProductAttributenameService.insert(attributename);
			attributenames.add(attributename);
		}
		
		return attributenames;
	}
	
	/**
	 * 生成属性值对象保存到集合中
	 * @param skuName
	 * @param skuValue
	 */
	private List<YwProductAttributevalue> createListAttributeValue(List<YwProductAttributename> attributenames,String[] attrValues){
		// 添加属性名称和属性值
		List<YwProductAttributevalue> attributevalues = new ArrayList<YwProductAttributevalue>();
		
		for(int i = 0 ; i < attributenames.size() ; i ++){
			// 生成属性值对象
			String[] values = attrValues[i].split("-");
			for (String value : values) {
				YwProductAttributevalue attributevalue = new YwProductAttributevalue();
				attributevalue.setAttrname_id(attributenames.get(i).getAttrname_id());
				attributevalue.setAttrvalue_name(value);
				attributevalues.add(attributevalue);
			}
		}
		// 批量保存属性值
		//ywProductAttributenameService.insertBatch(attributevalues);
		return attributevalues;
	}
	
	/**
	 * 创建sku对象保存到集合中
	 * @param productSkus
	 * @param sku_name
	 * @param sku_value
	 * @param sku_other
	 * @param product_id
	 * @return
	 */
	private List<YwProductSku> createSku(List<YwProductSku> productSkus,String sku_name,String sku_value,String sku_other,String product_id){
		String[] attrNames = sku_name.split(",");
		String[] attrValues = sku_value.split("-");
		String[] attrOther = sku_other.split("-");
		YwProductSku productSku = new YwProductSku();
		StringBuffer skuBuffer = new StringBuffer();
		for(int i = 0 ; i < attrNames.length ; i ++){
			
			skuBuffer.append(attrNames[i]);
			skuBuffer.append("-");
			skuBuffer.append(attrValues[i]);
			skuBuffer.append(";");
		}
		skuBuffer.deleteCharAt(skuBuffer.length() - 1);
		productSku.setProduct_id(product_id);
		productSku.setSku_attr(skuBuffer.toString().trim());
		if(attrOther.length == 4){
			productSku.setSku_image(attrOther[0]);
			productSku.setSku_price(attrOther[1]);
			productSku.setSku_virtualprice(attrOther[2]);
			productSku.setSku_stock(attrOther[3]);
		} else if(attrOther.length == 3){
			productSku.setSku_image(attrOther[0]);
			productSku.setSku_price(attrOther[1]);
			productSku.setSku_virtualprice(attrOther[2]);
			productSku.setSku_stock("");
		} else if(attrOther.length == 2){
			productSku.setSku_image(attrOther[0]);
			productSku.setSku_price(attrOther[1]);
			productSku.setSku_virtualprice("");
			productSku.setSku_stock("");
		} else if(attrOther.length == 1){
			productSku.setSku_image(attrOther[0]);
			productSku.setSku_price("");
			productSku.setSku_virtualprice("");
			productSku.setSku_stock("");
		}else {
			productSku.setSku_image("");
			productSku.setSku_price("");
			productSku.setSku_virtualprice("");
			productSku.setSku_stock("");
		}
		productSkus.add(productSku);
		return productSkus;
	}
	

	public int deleteYwProduct(int id) {
		
		return ywProductDao.deleteYwProduct(id);
	}

	public int updateYwProduct(YwProduct ywProduct) {
		
		return ywProductDao.updateYwProduct(ywProduct);
	}

	public int findsum(Map<String, Object> map) {
		
		return ywProductDao.findsum(map);
	}

	@Override
	public List<YwProduct> queryAll(Map<String, Object> map) {
		
		return ywProductDao.queryAll(map);
	}


	@Override
	public List<YwProduct> queryPage(Map<String, Object> map) {
		
		return super.queryPage(map);
	}


	@Override
	public int queryTotals(YwProduct ywProduct) {
		
		return super.queryTotals(ywProduct);
	}

	@Override
	//产品图片批量插入
	public Map<String, Object> productImgInsertBatch(YwProduct ywProduct){
//		String[] imgs = ywProduct.getImage_url();
//		List<YwImages> list = new ArrayList<YwImages>();
//		Map<String, Object> map = new HashMap<String, Object>();
//		if(ArrayUtils.isNotEmpty(imgs)){
//			for (String string : imgs) {
//				YwImages img = new YwImages();
//				img.setImage_url(string);
//				img.setProduct_id(String.valueOf(ywProduct.getId()));
//				img.setImage_type(DicConstant.YW_IMAGES_TYPE_VALUE);
//				list.add(img);
//			}
//			int num = ywImagesService.insertBatch(list);
//			if(num > 0){
//				map.put("code", "001");
//				map.put("msg", "插入成功");
//			}
//		}
		
		return null;
	}
	
	/**
	 * 删除商品属性
	 * @param ywProduct
	 * @return
	 */
	private int deleteProductAttr(YwProduct ywProduct){
		YwProductAttributename ywProductAttributename = new YwProductAttributename();
		ywProductAttributename.setProduct_id(String.valueOf(ywProduct.getId()));
		ywProductAttributenameService.delete(ywProductAttributename);
		return 0;
	}
	
	/**
	 * 删除商品sku
	 * @param ywProduct
	 * @return
	 */
	private int deleteProductSku(YwProduct ywProduct){
		YwProductSku ywProductSku = new YwProductSku();
		ywProductSku.setProduct_id(String.valueOf(ywProduct.getId()));
		ywProductSkuService.delete(ywProductSku);
		return 0;
	}

	@Override
	public String getToken(String wx_appid) {
		String access_token = null;
		
		try {
			ywAccessTokenService.getAccessTokenByAppId(wx_appid);
		} catch (Exception e) {
			throw new BizException("token码获取失败");
		}
		return access_token;
	}


}
