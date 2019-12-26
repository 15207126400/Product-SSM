package com.yunwei.product.infobase.product.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunwei.common.exception.BizException;
import com.yunwei.product.common.model.YwNotice;
import com.yunwei.product.infobase.constant.ConstantFunctionsFront;
import com.yunwei.product.infobase.service.YwNoticeService;

/**
 * 
* @ClassName: YwNoticeController 
* @Description: 公告栏信息
* @author 晏飞
* @date 2018年5月16日 下午12:44:13 
*
 */
@Controller
public class YwNoticeController {

	@Autowired
	private YwNoticeService ywNoticeService;
	
	@RequestMapping(ConstantFunctionsFront.YW_POT_00004)
	@ResponseBody
	public List<YwNotice> queryNotice(){
		List<YwNotice> ywNotice = null;
		try {
			ywNotice = ywNoticeService.queryList(new YwNotice());
		} catch (Exception e) {
			throw new BizException("没有数据");
		}
		
		return ywNotice;
	}
}
