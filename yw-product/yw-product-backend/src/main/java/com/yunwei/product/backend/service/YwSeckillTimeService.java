package com.yunwei.product.backend.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.yunwei.common.base.serivce.IBaseSerivce;
import com.yunwei.product.common.model.YwSeckillTime;


/**
 * 云维字典
 * @author zhangjh
 *
 */
public interface YwSeckillTimeService extends IBaseSerivce<YwSeckillTime>{
	
	/**
	 * 查询秒杀活动开始日期
	 * @param ywSeckillTime
	 * @return
	 */
	public List<String> querySeckillDates(YwSeckillTime ywSeckillTime);

	/**
	 * 时间主题图片上传
	 * @param file
	 * @param ywSeckillTime
	 * @return
	 */
	public String uploadSeckillTimeImage(MultipartFile uploadFile,YwSeckillTime ywSeckillTime);
	
	

}
