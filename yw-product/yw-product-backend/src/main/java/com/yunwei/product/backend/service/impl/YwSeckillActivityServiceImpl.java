package com.yunwei.product.backend.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.context.service.PictureService;
import com.yunwei.product.backend.service.YwImagesBackthumbnailService;
import com.yunwei.product.backend.service.YwImagesService;
import com.yunwei.product.backend.service.YwImagesXcxthumbnailService;
import com.yunwei.product.backend.service.YwSeckillActivityService;
import com.yunwei.product.common.backend.model.dto.YwSeckillActivityDto;
import com.yunwei.product.common.dao.YwSeckillActivityDao;
import com.yunwei.product.common.model.YwImages;
import com.yunwei.product.common.model.YwImagesBackthumbnail;
import com.yunwei.product.common.model.YwImagesXcxthumbnail;
import com.yunwei.product.common.model.YwSeckillActivity;


@Service
public class YwSeckillActivityServiceImpl extends IBaseServiceImpl<YwSeckillActivity> implements YwSeckillActivityService{
	

	@Autowired
	private YwSeckillActivityDao ywSeckillActivityDao;
	@Autowired
	private PictureService pictureService;
	@Autowired
	private YwImagesService ywImagesService;
	@Autowired
	private YwImagesBackthumbnailService ywImagesBackthumbnailService;
	@Autowired
	private YwImagesXcxthumbnailService ywImagesXcxthumbnailService;

	@Override
	protected IBaseDao<YwSeckillActivity> getBaseDao() {
		return ywSeckillActivityDao;
	}
	
	@Override
	public List<YwSeckillActivityDto> queryUnionSeckillTimeAndProductPage(Map<String, Object> map) {
		
		return ywSeckillActivityDao.queryUnionSeckillTimeAndProductPage(map);
	}
	
	@Override
	public YwSeckillActivity query(YwSeckillActivity t) {
		YwSeckillActivity ywSeckillActivity  = super.query(t);
		if(ywSeckillActivity != null && StringUtils.isNotBlank(ywSeckillActivity.getSeckill_url())){
			YwImagesBackthumbnail ywImagesBackthumbnail = new YwImagesBackthumbnail();
			ywImagesBackthumbnail.setImage_id(ywSeckillActivity.getSeckill_url());
			ywImagesBackthumbnail.setThumbnail_type("1");
			ywImagesBackthumbnail = ywImagesBackthumbnailService.query(ywImagesBackthumbnail);
			if(ywImagesBackthumbnail != null){
				ywSeckillActivity.setSeckill_url(ywImagesBackthumbnail.getThumbnail_url());
			}
		}
		
		return ywSeckillActivity;
	}
	
	@Override
	public String uploadSeckillActivityImage(MultipartFile uploadFile,YwSeckillActivity ywSeckillActivity) {
		// 原图上传
		String imageUrl = pictureService.uploadOriginalImage(uploadFile);
		
		// 保存原图
		YwImages ywImages = new YwImages();
		ywImages.setImage_url(imageUrl);
		ywImages.setImage_createtime(new Date());
		ywImages.setImage_type("3"); // 秒杀图片
		ywImagesService.insert(ywImages);
		
		// 原图编号
		String image_id = String.valueOf(ywImages.getImage_id());
		
		// 上传并保存缩略图
		uploadThumbnail(image_id,uploadFile,ywSeckillActivity);
		return image_id;
	}
	
	/**
	 * 保存缩略图(异步保存)
	 * @param image_id
	 * @param uploadFile
	 */
	private void uploadThumbnail(final String image_id,final MultipartFile uploadFile,final YwSeckillActivity ywSeckillActivity){
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
				ywImagesBackthumbnail.setThumbnail_name(ywSeckillActivity.getSeckill_name());
				ywImagesBackthumbnail.setThumbnail_type("1");
				ywImagesBackthumbnail.setCreate_datetime(new Date());
				ywImagesBackthumbnailService.insert(ywImagesBackthumbnail);
				
				// 保存小程序缩略图
				YwImagesXcxthumbnail ywImagesXcxthumbnail = new YwImagesXcxthumbnail();
				ywImagesXcxthumbnail.setImage_id(image_id);
				ywImagesXcxthumbnail.setThumbnail_url(imageXcxUrl);
				ywImagesXcxthumbnail.setThumbnail_name(ywSeckillActivity.getSeckill_name());
				ywImagesXcxthumbnail.setThumbnail_type("1");
				ywImagesXcxthumbnail.setCreate_datetime(new Date());
				ywImagesXcxthumbnailService.insert(ywImagesXcxthumbnail);
			}

		}.start();
	}
	
	
}
