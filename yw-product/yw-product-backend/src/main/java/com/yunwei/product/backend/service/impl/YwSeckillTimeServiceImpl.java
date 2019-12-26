package com.yunwei.product.backend.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.common.util.DateUtil;
import com.yunwei.context.service.PictureService;
import com.yunwei.product.backend.service.YwImagesBackthumbnailService;
import com.yunwei.product.backend.service.YwImagesService;
import com.yunwei.product.backend.service.YwImagesXcxthumbnailService;
import com.yunwei.product.backend.service.YwSeckillTimeService;
import com.yunwei.product.common.dao.YwSeckillTimeDao;
import com.yunwei.product.common.model.YwImages;
import com.yunwei.product.common.model.YwImagesBackthumbnail;
import com.yunwei.product.common.model.YwImagesXcxthumbnail;
import com.yunwei.product.common.model.YwSeckillActivity;
import com.yunwei.product.common.model.YwSeckillTime;


@Service
public class YwSeckillTimeServiceImpl extends IBaseServiceImpl<YwSeckillTime> implements YwSeckillTimeService{
	

	@Autowired
	private YwSeckillTimeDao ywSeckillTimeDao;
	@Autowired
	private PictureService pictureService;
	@Autowired
	private YwImagesService ywImagesService;
	@Autowired
	private YwImagesBackthumbnailService ywImagesBackthumbnailService;
	@Autowired
	private YwImagesXcxthumbnailService ywImagesXcxthumbnailService;

	@Override
	protected IBaseDao<YwSeckillTime> getBaseDao() {
		return ywSeckillTimeDao;
	}
	
	@Override
	public YwSeckillTime query(YwSeckillTime t) {
		YwSeckillTime ywSeckillTime =  super.query(t);
		if(ywSeckillTime != null && StringUtils.isNotBlank(ywSeckillTime.getTime_title_url())){
			YwImagesBackthumbnail ywImagesBackthumbnail = new YwImagesBackthumbnail();
			ywImagesBackthumbnail.setImage_id(ywSeckillTime.getTime_title_url());
			ywImagesBackthumbnail.setThumbnail_type("1");
			ywImagesBackthumbnail = ywImagesBackthumbnailService.query(ywImagesBackthumbnail);
			if(ywImagesBackthumbnail != null){
				ywSeckillTime.setTime_title_url(ywImagesBackthumbnail.getThumbnail_url());
			}
			
		}
		return ywSeckillTime;
	}

	@Override
	public List<String> querySeckillDates(YwSeckillTime ywSeckillTime) {
		List<YwSeckillTime> seckillTimes = super.queryList(ywSeckillTime);
		Set<String> tempSet = new HashSet<String>();
		if(CollectionUtils.isNotEmpty(seckillTimes)){
			for(YwSeckillTime seckillTime : seckillTimes){
				// 利用set集合进行去重(取出不同的开始日期)
				tempSet.add(DateUtil.format(seckillTime.getSeckill_date(), DateUtil.DATE_FORMAT));
			}
		}
		return new ArrayList<String>(tempSet);
	}

	@Override
	public String uploadSeckillTimeImage(MultipartFile uploadFile,YwSeckillTime ywSeckillTime) {
		// 原图上传
		String imageUrl = pictureService.uploadOriginalImage(uploadFile);
		
		// 保存原图
		YwImages ywImages = new YwImages();
		ywImages.setImage_url(imageUrl);
		ywImages.setImage_createtime(new Date());
		ywImages.setImage_type("5"); // 秒杀时间主题图片
		ywImagesService.insert(ywImages);
		
		// 原图编号
		String image_id = String.valueOf(ywImages.getImage_id());
		
		// 上传并保存缩略图
		uploadThumbnail(image_id,uploadFile,ywSeckillTime);
		return image_id;
	}

	/**
	 * 保存缩略图(异步保存)
	 * @param image_id
	 * @param uploadFile
	 */
	private void uploadThumbnail(final String image_id,final MultipartFile uploadFile,final YwSeckillTime ywSeckillTime){
		new Thread() {

			public void run() {
				// 上传后台缩略图
				String imageBackUrl = pictureService.uploadBackThumbnail(uploadFile, 100, 100);
				
				// 上传小程序缩略图
				String imageXcxUrl = pictureService.uploadXcxThumbnail(uploadFile, 0, 0); 
				
				// 保存后台缩略图
				YwImagesBackthumbnail ywImagesBackthumbnail = new YwImagesBackthumbnail();
				ywImagesBackthumbnail.setImage_id(image_id);
				ywImagesBackthumbnail.setThumbnail_url(imageBackUrl);
//				ywImagesBackthumbnail.setThumbnail_name(ywSeckillActivity.getSeckill_name());
				ywImagesBackthumbnail.setThumbnail_type("1");
				ywImagesBackthumbnail.setCreate_datetime(new Date());
				ywImagesBackthumbnailService.insert(ywImagesBackthumbnail);
				
				// 保存小程序缩略图
				YwImagesXcxthumbnail ywImagesXcxthumbnail = new YwImagesXcxthumbnail();
				ywImagesXcxthumbnail.setImage_id(image_id);
				ywImagesXcxthumbnail.setThumbnail_url(imageXcxUrl);
//				ywImagesXcxthumbnail.setThumbnail_name(ywSeckillActivity.getSeckill_name());
				ywImagesXcxthumbnail.setThumbnail_type("8");
				ywImagesXcxthumbnail.setCreate_datetime(new Date());
				ywImagesXcxthumbnailService.insert(ywImagesXcxthumbnail);
			}

		}.start();
	}
	
	
}
