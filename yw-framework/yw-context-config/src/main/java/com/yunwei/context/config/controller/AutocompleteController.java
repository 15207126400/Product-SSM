package com.yunwei.context.config.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunwei.context.config.service.IAutocompleteService;



/**
 * 类似于百度搜索框自动填充动能
* @ClassName: AutocompleteAction 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年5月23日 下午5:20:15 
*
 */
@Controller
@RequestMapping
public class AutocompleteController {

	Logger logger = LoggerFactory.getLogger(AutocompleteController.class);
	
	@Autowired
	private IAutocompleteService autocompleteService;

	/**
	 * 根据指定的条件查询指定表的指定列数据，模糊查询
	 * @author caiy
	 * @param query 查询条件
	 * @param size 结果条数 可为空 默认10条
	 * @param tableName 表名 不能为空
	 * @param column 列名 不能为空
	 * @return
	 */
	@RequestMapping("autocomplete.json")
	@ResponseBody
	public Map<String,Object> query(String query,String size,String tableName,String column) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("suggestions", autocompleteService.getResultList(query, size, tableName, column));
		
		return map;
	}
}
