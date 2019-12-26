package com.yunwei.product.backend.service;

import org.springframework.web.multipart.MultipartFile;

import com.yunwei.common.base.serivce.IBaseSerivce;
import com.yunwei.product.common.model.YwDistributionProduct;
import com.yunwei.product.common.model.YwPointsProduct;

/**
 * 积分商品service
* @ClassName: YwPointsProductService 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年07月26日 下午15:50:54
*
 */
public interface YwPointsProductService extends IBaseSerivce<YwPointsProduct>{
	
	/**
	 * 上传图片处理(异步上传原图,后台缩略图,小程序缩略图)
	*
	*@param uploadFile				文件
	*@param ywPointsProduct			积分商品对象
	*@return
	*String							图片路径
	 */
	public String uploadPointsProductImage(MultipartFile uploadFile,YwPointsProduct ywPointsProduct);
}
