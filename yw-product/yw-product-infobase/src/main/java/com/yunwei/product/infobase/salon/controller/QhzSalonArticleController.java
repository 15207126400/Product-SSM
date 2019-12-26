package com.yunwei.product.infobase.salon.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunwei.common.exception.BizException;
import com.yunwei.product.common.model.QhzArticle;
import com.yunwei.product.infobase.constant.ConstantFunctionsFront;
import com.yunwei.product.infobase.service.QhzArticleService;

/**
 * 启恒智沙龙注册文章接口
* @ClassName: QhzSalonArticleController 
* @Description: TODO(TODO) 
* @author 晏飞
* @date 2019年7月19日 上午8:52:34 
*
 */
@Controller
public class QhzSalonArticleController {
	@Autowired
	private QhzArticleService qhzArticleService;
	
	/**
	 * 根据条件查询文章列表信息
	*
	*@param qhzArticle
	*@return
	*Map<String,Object>
	 */
	@RequestMapping(ConstantFunctionsFront.QHZ_SL041)
	@ResponseBody
	public Map<String, Object> queryArticle(QhzArticle qhzArticle){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			qhzArticle.setScene_type("1");
			qhzArticle.setStatus("1");
			List<QhzArticle> qhzArticles = qhzArticleService.queryList(qhzArticle);
			map.put("qhzArticles", qhzArticles);
			
			return map;
		} catch (Exception e) {
			throw new BizException("【根据条件查询文章列表信息】 : " + e);
		}
	}
	
	/**
	 * 查询指定文章信息
	*
	*@param qhzArticle
	*@return
	*Map<String,Object>
	 */
	@RequestMapping(ConstantFunctionsFront.QHZ_SL042)
	@ResponseBody
	public Map<String, Object> queryArticleById(QhzArticle qhzArticle){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			qhzArticle = qhzArticleService.query(qhzArticle);
			//阅读量+1
			int num = 0;
			num = Integer.valueOf(qhzArticle.getRead_num()) + 1;
			qhzArticle.setRead_num(String.valueOf(num));
			qhzArticleService.update(qhzArticle);
			map.put("qhzArticle", qhzArticle);
			
			return map;
		} catch (Exception e) {
			throw new BizException("【查询指定文章信息】 : " + e);
		}
		
	}
}
