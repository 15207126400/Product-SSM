package com.yunwei.product.backend.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.log4j.Logger;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.context.service.PictureService;
import com.yunwei.product.backend.service.QhzArticleService;
import com.yunwei.product.backend.service.YwImagesService;
import com.yunwei.product.common.dao.QhzArticleDao;
import com.yunwei.product.common.model.QhzArticle;
import com.yunwei.product.common.model.YwImages;


@Service
public class QhzArticleServiceImpl extends IBaseServiceImpl<QhzArticle> implements QhzArticleService{
	
    private static Logger logger = Logger.getLogger(QhzArticleServiceImpl.class);
	@Autowired
	private QhzArticleDao qhzArticleDao;
	@Autowired
	private YwImagesService ywImagesService;
	@Autowired
	private PictureService pictureService;

	@Override
	protected IBaseDao<QhzArticle> getBaseDao() {
		return qhzArticleDao;
	}
	
	@Override
	protected String getPrimaryKey() {
	
		return "id";
	}

	@Override
	public Map<String, Object> uploadEditorImage(MultipartFile upfile,QhzArticle qhzArticle) {
		Map<String,Object> map = new HashMap<String, Object>();
		// 原图上传
		String imageUrl = pictureService.uploadOriginalImage(upfile);
		// 保存原图
		YwImages ywImages = new YwImages();
		ywImages.setImage_url(imageUrl);
		ywImages.setImage_createtime(new Date());
		ywImages.setImage_type("1"); // 商品图片
		ywImagesService.insert(ywImages);
		
		String image_id = String.valueOf(ywImages.getImage_id());
		
		map.put("url", imageUrl);
		map.put("image_id", image_id);
		map.put("title",image_id);
		
		return map;
	}
	
	
	
	
}
