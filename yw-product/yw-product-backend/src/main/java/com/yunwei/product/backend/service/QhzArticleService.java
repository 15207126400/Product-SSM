package com.yunwei.product.backend.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.yunwei.common.base.serivce.IBaseSerivce;
import com.yunwei.product.common.model.QhzArticle;
import com.yunwei.product.common.model.YwProduct;

/**
 * 文章信息模块service
* @ClassName: QhzArticleService 
* @Description: TODO(TODO) 
* @author yanf
* @date 2019年07月16日 下午15:34:20
*
 */
public interface QhzArticleService extends IBaseSerivce<QhzArticle>{
	
	public Map<String,Object> uploadEditorImage(MultipartFile skuFile,QhzArticle qhzArticle);
}
