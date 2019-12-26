package com.yunwei.product.backend.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.yunwei.common.base.serivce.IBaseSerivce;
import com.yunwei.product.common.backend.model.dto.YwSeckillActivityDto;
import com.yunwei.product.common.model.YwSeckillActivity;


/**
 * 云维字典
 * @author zhangjh
 *
 */
public interface YwSeckillActivityService extends IBaseSerivce<YwSeckillActivity>{
	
	/**
	 * 联合查询秒杀活动表关联的秒杀时间表和商品表信息
	 * @return
	 */
	public List<YwSeckillActivityDto> queryUnionSeckillTimeAndProductPage(Map<String, Object> map);
	
	public String uploadSeckillActivityImage(MultipartFile uploadFile,YwSeckillActivity ywSeckillActivity);
}
