package com.yunwei.product.backend.service.impl;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.common.exception.BizException;
import com.yunwei.context.service.PictureService;
import com.yunwei.context.sys.usercenter.model.YwUserCustomerXcx;
import com.yunwei.context.sys.usercenter.service.YwUserCustomerXcxService;
import com.yunwei.product.backend.service.YwImagesBackthumbnailService;
import com.yunwei.product.backend.service.YwImagesService;
import com.yunwei.product.backend.service.YwImagesXcxthumbnailService;
import com.yunwei.product.backend.service.YwProductAttributenameService;
import com.yunwei.product.backend.service.YwProductAttributevalueService;
import com.yunwei.product.backend.service.YwProductImageService;
import com.yunwei.product.backend.service.YwProductService;
import com.yunwei.product.backend.service.YwProductSkuService;
import com.yunwei.product.common.constants.DicConstant;
import com.yunwei.product.common.dao.YwProductDao;
import com.yunwei.product.common.model.YwDistributionProduct;
import com.yunwei.product.common.model.YwImages;
import com.yunwei.product.common.model.YwImagesBackthumbnail;
import com.yunwei.product.common.model.YwImagesXcxthumbnail;
import com.yunwei.product.common.model.YwProduct;
import com.yunwei.product.common.model.YwProductAttributename;
import com.yunwei.product.common.model.YwProductAttributevalue;
import com.yunwei.product.common.model.YwProductImage;
import com.yunwei.product.common.model.YwProductSku;
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
	private YwImagesService ywImagesService;
	@Autowired
	private YwProductAttributenameService ywProductAttributenameService;
	@Autowired
	private YwProductAttributevalueService ywProductAttributevalueService;
	@Autowired
	private YwProductSkuService ywProductSkuService;
	@Autowired
	private PictureService pictureService;
	@Autowired
	private YwImagesBackthumbnailService ywImagesBackthumbnailService;
	@Autowired
	private YwImagesXcxthumbnailService ywImagesXcxthumbnailService;
	@Autowired
	private YwProductImageService ywProductImageService;
	
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


//	@Override
//	public List<YwProduct> queryListPage(Map<String, Object> map) {
//		
//		return ywProductDao.queryListPage(map);
//	}


	@Override
	public int queryTotals(YwProduct ywProduct) {
		
		return super.queryTotals(ywProduct);
	}

	@Override
	//产品图片批量插入
	public Map<String, Object> productImgInsertBatch(YwProduct ywProduct){
		String[] imgs = ywProduct.getImage_url();
		List<YwProductImage> list = new ArrayList<YwProductImage>();
		Map<String, Object> map = new HashMap<String, Object>();
		if(ArrayUtils.isNotEmpty(imgs)){
			for (String image_id : imgs) {
				YwProductImage image = new YwProductImage();
				image.setImage_id(image_id);
				image.setProduct_id(ywProduct.getId());
				image.setCreate_datetime(new Date());
				image.setSort(0);
				image.setStatus("1");
				list.add(image);
			}
			int num = ywProductImageService.insertBatch(list);
			if(num > 0){
				map.put("code", "001");
				map.put("msg", "插入成功");
			}
		}
		
		return map;
	}

	@Override
	public int updateProductAndOther(YwProduct ywProduct) {
		// 删除商品属性
		deleteProductAttr(ywProduct);
		// 删除商品sku
		deleteProductSku(ywProduct);
		
		//修改商品
		super.update(ywProduct);
		
		// 添加商品其他信息
		insertProductOther(ywProduct);
		return 0;
	}
	
	
	public int updateByProduct(YwProduct ywProduct){
		
		return super.update(ywProduct);
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
			YwUserCustomerXcx ywUserXcx = new YwUserCustomerXcx();
			ywUserXcx.setApp_id(wx_appid);
			ywUserXcx = ywUserCustomerXcxService.query(ywUserXcx);
			if(ywUserXcx != null){
				HttpGet httpGet = new HttpGet(
						"https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential"
								+ "&appid="
								+ ywUserXcx.getApp_id()
								+ "&secret="
								+ ywUserXcx.getApp_secret());
				// 发送请求
				HttpClient httpClient = HttpClients.createDefault();
				HttpResponse res = httpClient.execute(httpGet);
				HttpEntity entity = res.getEntity();
				String rec = EntityUtils.toString(entity);
				// 字符串转换对象
				JSONObject json = JSONObject.parseObject(rec);
				access_token = json.get("access_token").toString();
			}
		} catch (Exception e) {
			throw new BizException("token码获取失败");
		}
		return access_token;
	}

	@Override
	public YwProduct queryByProductCode(String product_code) {
		// TODO Auto-generated method stub
		return ywProductDao.queryByProductCode(product_code);
				
	}

	@Override
	public int updateByExcel(YwProduct ywProduct) {
		// TODO Auto-generated method stub
		return super.update(ywProduct);
	}

	@Override
	public String uploadProductImage(MultipartFile uploadFile,YwProduct ywProduct) {
		// 原图编号
		String image_id = uploadProductOriginalImage(uploadFile);
		
		// 上传并保存缩略图
		//uploadThumbnail(image_id,uploadFile,ywProduct,0);
		return image_id;
	}

	/**
	 * 商品原图上传并保存
	 * @param uploadFile
	 * @return
	 */
	private String uploadProductOriginalImage(MultipartFile uploadFile){
		// 原图上传
		String imageUrl = pictureService.uploadOriginalImageBySftp(uploadFile);
		
		// 保存原图
		YwImages ywImages = new YwImages();
		ywImages.setImage_url(imageUrl);
		ywImages.setImage_createtime(new Date());
		ywImages.setImage_type("1"); // 商品图片
		ywImagesService.insert(ywImages);
		
		return String.valueOf(ywImages.getImage_id());
	}
	
	/**
	 * 保存缩略图(异步保存)
	 * @param image_id
	 * @param uploadFile
	 * @param ywProduct
	 * @param flag(0:主图：1：轮播图，2：商品sku图片)
	 */
	private void uploadThumbnail(final String image_id,final MultipartFile uploadFile,final YwProduct ywProduct,final int flag){
		new Thread() {

			public void run() {
				if(flag == 0){
					// 上传后台缩略图
					String imageBackUrl = pictureService.uploadOriginalImageBySftp(uploadFile);
					
					// 上传小程序首页缩略图
					String imageXcxIndexUrl = pictureService.uploadOriginalImageBySftp(uploadFile); 
					
					// 上传小程序商品分类列表缩略图
					String imageXcxClassifyListUrl = pictureService.uploadOriginalImageBySftp(uploadFile); 
					
					// 保存后台缩略图
					YwImagesBackthumbnail ywImagesBackthumbnail = new YwImagesBackthumbnail();
					ywImagesBackthumbnail.setImage_id(image_id);
					ywImagesBackthumbnail.setThumbnail_url(imageBackUrl);
					ywImagesBackthumbnail.setThumbnail_name(ywProduct.getTitle());
					ywImagesBackthumbnail.setThumbnail_type("1");
					ywImagesBackthumbnail.setCreate_datetime(new Date());
					ywImagesBackthumbnailService.insert(ywImagesBackthumbnail);
					
					// 保存小程序首页缩略图
					YwImagesXcxthumbnail ywImagesXcxthumbnail = new YwImagesXcxthumbnail();
					ywImagesXcxthumbnail.setImage_id(image_id);
					ywImagesXcxthumbnail.setThumbnail_url(imageXcxIndexUrl);
					ywImagesXcxthumbnail.setThumbnail_name(ywProduct.getTitle());
					ywImagesXcxthumbnail.setThumbnail_type("1"); // 首页
					ywImagesXcxthumbnail.setCreate_datetime(new Date());
					ywImagesXcxthumbnailService.insert(ywImagesXcxthumbnail);
					
					// 保存小程分类列表缩略图
					ywImagesXcxthumbnail.setThumbnail_url(imageXcxClassifyListUrl);
					ywImagesXcxthumbnail.setThumbnail_type("3");// 商品分类列表
					ywImagesXcxthumbnail.setCreate_datetime(new Date());
					ywImagesXcxthumbnailService.insert(ywImagesXcxthumbnail);
				} else if(flag == 1){
					// 上传后台商品轮播缩略图
					String imageBackProductCarouselUrl = pictureService.uploadOriginalImageBySftp(uploadFile);
					
					// 上传小程序商品轮播缩略图
					String imageXcxProductCarouselUrl = pictureService.uploadOriginalImageBySftp(uploadFile);
					
					// 保存后台缩略图
					YwImagesBackthumbnail ywImagesBackthumbnail = new YwImagesBackthumbnail();
					ywImagesBackthumbnail.setImage_id(image_id);
					ywImagesBackthumbnail.setThumbnail_url(imageBackProductCarouselUrl);
					ywImagesBackthumbnail.setThumbnail_name(ywProduct.getTitle());
					ywImagesBackthumbnail.setThumbnail_type("3");
					ywImagesBackthumbnail.setCreate_datetime(new Date());
					ywImagesBackthumbnailService.insert(ywImagesBackthumbnail);
					
					// 保存小程序商品轮播缩略图
					YwImagesXcxthumbnail ywImagesXcxthumbnail = new YwImagesXcxthumbnail();
					ywImagesXcxthumbnail.setImage_id(image_id);
					ywImagesXcxthumbnail.setThumbnail_url(imageXcxProductCarouselUrl);
					ywImagesXcxthumbnail.setThumbnail_name(ywProduct.getTitle());
					ywImagesXcxthumbnail.setThumbnail_type("4"); // 商品详情轮播缩略图
					ywImagesXcxthumbnail.setCreate_datetime(new Date());
					ywImagesXcxthumbnailService.insert(ywImagesXcxthumbnail);
					
				} else if(flag == 2){
					
					
					// 上传小程序商品sku缩略图(等比例压缩)
					String imageXcxProductSkuUrl = pictureService.uploadOriginalImageBySftp(uploadFile);
					
					
					
					// 保存小程序商品sku缩略图
					YwImagesXcxthumbnail ywImagesXcxthumbnail = new YwImagesXcxthumbnail();
					ywImagesXcxthumbnail.setImage_id(image_id);
					ywImagesXcxthumbnail.setThumbnail_url(imageXcxProductSkuUrl);
					ywImagesXcxthumbnail.setThumbnail_name(ywProduct.getTitle());
					ywImagesXcxthumbnail.setThumbnail_type("7"); // 小程序商品sku缩略图
					ywImagesXcxthumbnail.setCreate_datetime(new Date());
					ywImagesXcxthumbnailService.insert(ywImagesXcxthumbnail);
				}
				
			}

		}.start();
	}

	@Override
	public String uploadProductCarouselImage(MultipartFile carouselFile,YwProduct ywProduct) {
		// 原图编号
		String image_id = uploadProductOriginalImage(carouselFile);
		
		// 上传轮播缩略图
		uploadThumbnail(image_id,carouselFile,ywProduct,1);
		return image_id;
	}

	@Override
	public Map<String,Object> uploadProductSkuImage(MultipartFile skuFile,YwProduct ywProduct) {
		Map<String,Object> map = new HashMap<String, Object>();
		// 原图上传
		String imageUrl = pictureService.uploadOriginalImageBySftp(skuFile);
		
		// 保存原图
		YwImages ywImages = new YwImages();
		ywImages.setImage_url(imageUrl);
		ywImages.setImage_createtime(new Date());
		ywImages.setImage_type("1"); // 商品图片
		ywImagesService.insert(ywImages);
		
		String image_id = String.valueOf(ywImages.getImage_id());
		
		// 上传后台商品sku缩略图(等比例压缩)
		String imageBackProductSkuUrl = pictureService.uploadOriginalImageBySftp(skuFile);
		
		// 保存后台商品sku缩略图
		YwImagesBackthumbnail ywImagesBackthumbnail = new YwImagesBackthumbnail();
		ywImagesBackthumbnail.setImage_id(image_id);
		ywImagesBackthumbnail.setThumbnail_url(imageBackProductSkuUrl);
		ywImagesBackthumbnail.setThumbnail_name(ywProduct.getTitle());
		ywImagesBackthumbnail.setThumbnail_type("4"); // 后台商品sku缩略图
		ywImagesBackthumbnail.setCreate_datetime(new Date());
		ywImagesBackthumbnailService.insert(ywImagesBackthumbnail);
		// 上传小程序商品sku缩略图
		uploadThumbnail(image_id,skuFile,ywProduct,2);
		
		
		map.put("url", imageBackProductSkuUrl);
		map.put("image_id", image_id);
		map.put("title",image_id);
		return map;
	}

	@Override
	public List<YwProduct> importExcelByProduct(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return ywProductDao.importExcelByProduct(map);
	}


}
