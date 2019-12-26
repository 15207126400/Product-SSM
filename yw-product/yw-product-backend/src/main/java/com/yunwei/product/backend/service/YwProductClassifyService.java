package com.yunwei.product.backend.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.yunwei.common.base.serivce.IBaseSerivce;
import com.yunwei.product.common.model.YwProductClassify;
import com.yunwei.product.common.model.YwSeckillActivity;

/**
 * 
* @ClassName: ShopDetailDao 
* @Description: TODO(分类接口) 
* @author 晏飞
* @date 2017年11月18日 下午5:09:49 
*
 */
public interface YwProductClassifyService extends IBaseSerivce<YwProductClassify>{

	/**
	 * 分类上传图片接口
	*
	*@param uploadFile
	*@param ywProductClassify
	*@return
	*String
	 */
	public String uploadClassifyImage(MultipartFile uploadFile,YwProductClassify ywProductClassify);
}
