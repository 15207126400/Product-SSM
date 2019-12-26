package com.yunwei.product.front.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.context.service.PictureService;
import com.yunwei.product.common.dao.YwXcxCarouselDao;
import com.yunwei.product.common.model.YwImages;
import com.yunwei.product.common.model.YwImagesBackthumbnail;
import com.yunwei.product.common.model.YwImagesXcxthumbnail;
import com.yunwei.product.common.model.YwXcxCarousel;
import com.yunwei.product.front.service.YwImagesService;
import com.yunwei.product.front.service.YwXcxCarouselService;


@Service
public class YwXcxCarouselServiceImpl extends IBaseServiceImpl<YwXcxCarousel> implements YwXcxCarouselService{
	
    private static Logger logger = Logger.getLogger(YwXcxCarouselServiceImpl.class);
	@Autowired
	private YwXcxCarouselDao ywXcxCarouselDao;
	@Autowired
	private PictureService pictureService;
	@Autowired
	private YwImagesService ywImagesService;

	@Override
	protected IBaseDao<YwXcxCarousel> getBaseDao() {
		return ywXcxCarouselDao;
	}

	@Override
	public YwXcxCarousel query(YwXcxCarousel t) {
		YwXcxCarousel ywXcxCarousel = super.query(t);
		// 查询缩略图
		YwImages ywImages = new YwImages();
		ywImages.setImage_id(Integer.parseInt(ywXcxCarousel.getCarousel_url()));
		ywImages.setImage_type("2");
		ywImages = ywImagesService.query(ywImages);
		if(ywImages != null){
			ywXcxCarousel.setCarousel_url(ywImages.getImage_url());
		}
		return ywXcxCarousel;
	}
	
}
