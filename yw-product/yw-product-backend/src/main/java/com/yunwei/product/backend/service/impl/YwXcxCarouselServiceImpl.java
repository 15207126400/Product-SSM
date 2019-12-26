package com.yunwei.product.backend.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.yunwei.product.backend.service.YwImagesBackthumbnailService;
import com.yunwei.product.backend.service.YwImagesService;
import com.yunwei.product.backend.service.YwImagesXcxthumbnailService;
import com.yunwei.product.backend.service.YwXcxCarouselService;
import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.context.service.PictureService;
import com.yunwei.product.common.dao.YwXcxCarouselDao;
import com.yunwei.product.common.model.YwImages;
import com.yunwei.product.common.model.YwImagesBackthumbnail;
import com.yunwei.product.common.model.YwImagesXcxthumbnail;
import com.yunwei.product.common.model.YwXcxCarousel;


@Service
public class YwXcxCarouselServiceImpl extends IBaseServiceImpl<YwXcxCarousel> implements YwXcxCarouselService{
	
    private static Logger logger = Logger.getLogger(YwXcxCarouselServiceImpl.class);
	@Autowired
	private YwXcxCarouselDao ywXcxCarouselDao;
	@Autowired
	private PictureService pictureService;
	@Autowired
	private YwImagesService ywImagesService;
	@Autowired
	private YwImagesBackthumbnailService ywImagesBackthumbnailService;
	@Autowired
	private YwImagesXcxthumbnailService ywImagesXcxthumbnailService;

	@Override
	protected IBaseDao<YwXcxCarousel> getBaseDao() {
		return ywXcxCarouselDao;
	}

	@Override
	public YwXcxCarousel query(YwXcxCarousel t) {
		YwXcxCarousel ywXcxCarousel = super.query(t);
		// 查询缩略图
		YwImagesBackthumbnail ywImagesBackthumbnail = new YwImagesBackthumbnail();
		ywImagesBackthumbnail.setImage_id(ywXcxCarousel.getCarousel_url());
		ywImagesBackthumbnail.setThumbnail_type("1");
		ywImagesBackthumbnail = ywImagesBackthumbnailService.query(ywImagesBackthumbnail);
		if(ywImagesBackthumbnail != null){
			ywXcxCarousel.setCarousel_url(ywImagesBackthumbnail.getThumbnail_url());
		}
		
		return ywXcxCarousel;
	}
	
	//@Transactional
	@Override
	public String uploadCarouselImage(MultipartFile uploadFile,YwXcxCarousel ywXcxCarousel) {
		// 原图上传
		String imageUrl = pictureService.uploadOriginalImage(uploadFile);
		
		// 保存原图
		YwImages ywImages = new YwImages();
		ywImages.setImage_url(imageUrl);
		ywImages.setImage_createtime(new Date());
		ywImages.setImage_type("2");
		ywImagesService.insert(ywImages);
		
		// 原图编号
		String image_id = String.valueOf(ywImages.getImage_id());
		
		// 上传并保存缩略图
		//uploadThumbnail(image_id,uploadFile,ywXcxCarousel);
		return image_id;
	}
	
	/**
	 * 保存缩略图(异步保存)
	 * @param image_id
	 * @param uploadFile
	 */
	private void uploadThumbnail(final String image_id,final MultipartFile uploadFile,final YwXcxCarousel ywXcxCarousel){
		new Thread() {

			public void run() {
				// 上传后台缩略图
				String imageBackUrl = pictureService.uploadBackThumbnail(uploadFile, 100, 100);
				
				// 上传小程序缩略图
				String imageXcxUrl = "";
				if(StringUtils.equals(ywXcxCarousel.getCarousel_type(), "1")){// 首页轮播图
					imageXcxUrl = pictureService.uploadXcxThumbnail(uploadFile, 0, 0);
				} else if(StringUtils.equals(ywXcxCarousel.getCarousel_type(), "2")){// 团购轮播图
					imageXcxUrl = pictureService.uploadXcxThumbnail(uploadFile, 0, 0);
				} else {
					imageXcxUrl = pictureService.uploadXcxThumbnail(uploadFile); // 原图大小压缩
				}
				
				// 保存后台缩略图
				YwImagesBackthumbnail ywImagesBackthumbnail = new YwImagesBackthumbnail();
				ywImagesBackthumbnail.setImage_id(image_id);
				ywImagesBackthumbnail.setThumbnail_url(imageBackUrl);
				ywImagesBackthumbnail.setThumbnail_name(ywXcxCarousel.getCarousel_name());
				ywImagesBackthumbnail.setThumbnail_type("1");
				ywImagesBackthumbnail.setCreate_datetime(new Date());
				ywImagesBackthumbnailService.insert(ywImagesBackthumbnail);
				
				// 保存小程序缩略图
				YwImagesXcxthumbnail ywImagesXcxthumbnail = new YwImagesXcxthumbnail();
				ywImagesXcxthumbnail.setImage_id(image_id);
				ywImagesXcxthumbnail.setThumbnail_url(imageXcxUrl);
				ywImagesXcxthumbnail.setThumbnail_name(ywXcxCarousel.getCarousel_name());
				ywImagesXcxthumbnail.setThumbnail_type("1");
				ywImagesXcxthumbnail.setCreate_datetime(new Date());
				ywImagesXcxthumbnailService.insert(ywImagesXcxthumbnail);
			}

		}.start();
	}

	@Override
	public String queryByImageId(String id) {
		
		return ywXcxCarouselDao.queryByImageId(id);
	}
	
	
}
