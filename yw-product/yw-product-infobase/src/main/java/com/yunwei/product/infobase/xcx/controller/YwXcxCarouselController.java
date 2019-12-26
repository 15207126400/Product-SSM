package com.yunwei.product.infobase.xcx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunwei.product.common.model.YwXcxCarousel;
import com.yunwei.product.infobase.constant.ConstantFunctionsFront;
import com.yunwei.product.infobase.service.YwXcxCarouselService;

/**
 * 小程序轮播图控制类
* @ClassName: YwXcxCarouselController 
* @Description: TODO(TODO) 
* @author 晏飞
* @date 2019年7月27日 上午11:18:54 
*
 */
@Controller
public class YwXcxCarouselController {
	@Autowired
	private YwXcxCarouselService ywXcxCarouselService;
	
	@RequestMapping(ConstantFunctionsFront.YW_CAL_00000)
	@ResponseBody
	public List<YwXcxCarousel> queryUser(YwXcxCarousel ywXcxCarousel){
		ywXcxCarousel.setStatus("1");
		ywXcxCarouselService.queryList(ywXcxCarousel);
		
		return ywXcxCarouselService.queryList(ywXcxCarousel);
	}
}
