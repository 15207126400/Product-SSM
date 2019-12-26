package com.yunwei.product.backend.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.context.service.PictureService;
import com.yunwei.product.backend.service.YwDistributionProductService;
import com.yunwei.product.backend.service.YwImagesBackthumbnailService;
import com.yunwei.product.backend.service.YwImagesService;
import com.yunwei.product.backend.service.YwImagesXcxthumbnailService;
import com.yunwei.product.backend.service.YwProductService;
import com.yunwei.product.common.dao.YwDistributionProductDao;
import com.yunwei.product.common.model.YwDistributionProduct;
import com.yunwei.product.common.model.YwImages;
import com.yunwei.product.common.model.YwImagesBackthumbnail;
import com.yunwei.product.common.model.YwImagesXcxthumbnail;
import com.yunwei.product.common.model.YwProduct;
import com.yunwei.product.common.model.YwSeckillActivity;
@Service
public class YwDistributionProductServiceImpl extends IBaseServiceImpl<YwDistributionProduct> implements YwDistributionProductService {

	@Autowired
	private YwDistributionProductDao ywDistributionProductDao;
	@Autowired
	private YwProductService ywProductService;
	@Autowired
	private PictureService pictureService;
	@Autowired
	private YwImagesService ywImagesService;
	@Autowired
	private YwImagesBackthumbnailService ywImagesBackthumbnailService;
	@Autowired
	private YwImagesXcxthumbnailService ywImagesXcxthumbnailService;

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

	@Override
	public String uploadDistributionProductImage(MultipartFile uploadFile,YwDistributionProduct ywDistributionProduct) {
		// 原图上传
		String imageUrl = pictureService.uploadOriginalImage(uploadFile);
		
		// 保存原图
		YwImages ywImages = new YwImages();
		ywImages.setImage_url(imageUrl);
		ywImages.setImage_createtime(new Date());
		ywImages.setImage_type("4"); // 分销商品图片
		ywImagesService.insert(ywImages);
		
		// 原图编号
		String image_id = String.valueOf(ywImages.getImage_id());
		
		// 上传并保存缩略图
		uploadThumbnail(image_id,uploadFile,ywDistributionProduct);
		return image_id;
	}
	
	/**
	 * 保存缩略图(异步保存)
	 * @param image_id
	 * @param uploadFile
	 */
	private void uploadThumbnail(final String image_id,final MultipartFile uploadFile,final YwDistributionProduct ywDistributionProduct){
		new Thread() {

			public void run() {
				// 上传后台缩略图
				String imageBackUrl = pictureService.uploadBackThumbnail(uploadFile, 100, 100);
				
				// 上传小程序缩略图
				String imageXcxUrl = pictureService.uploadXcxThumbnail(uploadFile, 138, 125); 
				
				// 保存后台缩略图
				YwImagesBackthumbnail ywImagesBackthumbnail = new YwImagesBackthumbnail();
				ywImagesBackthumbnail.setImage_id(image_id);
				ywImagesBackthumbnail.setThumbnail_url(imageBackUrl);
				ywImagesBackthumbnail.setThumbnail_name(ywDistributionProduct.getProduct_name());
				ywImagesBackthumbnail.setThumbnail_type("1");
				ywImagesBackthumbnail.setCreate_datetime(new Date());
				ywImagesBackthumbnailService.insert(ywImagesBackthumbnail);
				
				// 保存小程序缩略图
				YwImagesXcxthumbnail ywImagesXcxthumbnail = new YwImagesXcxthumbnail();
				ywImagesXcxthumbnail.setImage_id(image_id);
				ywImagesXcxthumbnail.setThumbnail_url(imageXcxUrl);
				ywImagesXcxthumbnail.setThumbnail_name(ywDistributionProduct.getProduct_name());
				ywImagesXcxthumbnail.setThumbnail_type("1");
				ywImagesXcxthumbnail.setCreate_datetime(new Date());
				ywImagesXcxthumbnailService.insert(ywImagesXcxthumbnail);
			}

		}.start();
	}
}
