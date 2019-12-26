package com.yunwei.product.backend.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.yunwei.product.backend.service.YwImagesBackthumbnailService;
import com.yunwei.product.backend.service.YwImagesService;
import com.yunwei.product.backend.service.YwImagesXcxthumbnailService;
import com.yunwei.product.backend.service.YwPointsProductService;
import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.common.util.MapUtil;
import com.yunwei.context.service.PictureService;
import com.yunwei.product.common.dao.YwPointsProductDao;
import com.yunwei.product.common.model.YwDistributionProduct;
import com.yunwei.product.common.model.YwImages;
import com.yunwei.product.common.model.YwImagesBackthumbnail;
import com.yunwei.product.common.model.YwImagesXcxthumbnail;
import com.yunwei.product.common.model.YwPointsProduct;


@Service
public class YwPointsProductServiceImpl extends IBaseServiceImpl<YwPointsProduct> implements YwPointsProductService{
	
    private static Logger logger = Logger.getLogger(YwPointsProductServiceImpl.class);
	@Autowired
	private YwPointsProductDao ywPointsProductDao;
	@Autowired
	private PictureService pictureService;
	@Autowired
	private YwImagesService ywImagesService;
	@Autowired
	private YwImagesBackthumbnailService ywImagesBackthumbnailService;
	@Autowired
	private YwImagesXcxthumbnailService ywImagesXcxthumbnailService;

	@Override
	protected IBaseDao<YwPointsProduct> getBaseDao() {
		return ywPointsProductDao;
	}

	@Override
	public YwPointsProduct query(YwPointsProduct t) {
		Map<String, Object> map = new HashMap<String, Object>();
		map = MapUtil.toMap(t);
		map.put("thumbnail_type", "1");
		YwPointsProduct ywPointsProduct = super.query(map);
		
		if(ywPointsProduct != null && StringUtils.isNotBlank(ywPointsProduct.getPoints_pro_img())){
			YwImagesBackthumbnail ywImagesBackthumbnail = new YwImagesBackthumbnail();
			ywImagesBackthumbnail.setImage_id(ywPointsProduct.getPoints_pro_img());
			ywImagesBackthumbnail.setThumbnail_type("1");
			ywImagesBackthumbnail = ywImagesBackthumbnailService.query(ywImagesBackthumbnail);
			if(ywImagesBackthumbnail != null){
				ywPointsProduct.setPoints_pro_img(ywImagesBackthumbnail.getThumbnail_url());
			}
		}
		
		return ywPointsProduct;
	}
	
	@Override
	public String uploadPointsProductImage(MultipartFile uploadFile,YwPointsProduct ywPointsProduct) {
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
		uploadThumbnail(image_id,uploadFile,ywPointsProduct);
		
		return image_id;
	}
	
	/**
	 * 保存缩略图(异步保存)
	 * @param image_id
	 * @param uploadFile
	 */
	private void uploadThumbnail(final String image_id,final MultipartFile uploadFile,final YwPointsProduct ywPointsProduct){
		new Thread() {

			public void run() {
				// 上传后台缩略图
				String imageBackUrl = pictureService.uploadBackThumbnail(uploadFile, 100, 100);
				
				// 上传小程序缩略图
				String imageXcxUrl = pictureService.uploadXcxThumbnail(uploadFile, 159, 140); 
				
				// 保存后台缩略图
				YwImagesBackthumbnail ywImagesBackthumbnail = new YwImagesBackthumbnail();
				ywImagesBackthumbnail.setImage_id(image_id);
				ywImagesBackthumbnail.setThumbnail_url(imageBackUrl);
				ywImagesBackthumbnail.setThumbnail_name(ywPointsProduct.getPoints_pro_title());
				ywImagesBackthumbnail.setThumbnail_type("1");
				ywImagesBackthumbnail.setCreate_datetime(new Date());
				ywImagesBackthumbnailService.insert(ywImagesBackthumbnail);
				
				// 保存小程序缩略图
				YwImagesXcxthumbnail ywImagesXcxthumbnail = new YwImagesXcxthumbnail();
				ywImagesXcxthumbnail.setImage_id(image_id);
				ywImagesXcxthumbnail.setThumbnail_url(imageXcxUrl);
				ywImagesXcxthumbnail.setThumbnail_name(ywPointsProduct.getPoints_pro_title());
				ywImagesXcxthumbnail.setThumbnail_type("2");
				ywImagesXcxthumbnail.setCreate_datetime(new Date());
				ywImagesXcxthumbnailService.insert(ywImagesXcxthumbnail);
			}

		}.start();
	}
	
	
}
