package com.yunwei.product.backend.product.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.product.backend.service.YwProductClassifyService;
import com.yunwei.product.common.model.YwProductClassify;

/**
 * 
* @ClassName: YwProductClassifyManage 
* @Description: TODO(TODO) 商品分类控制器
* @author zhangjh
* @date 2018年3月26日 下午4:33:53 
*
 */
@Controller
@RequestMapping("/product/ywProductClassify")
public class YwProductClassifyManage {
	
	@Autowired
	private YwProductClassifyService ywProductClassifyService;

	@RequestMapping
	public String ywProductClassifyList(Model model){
		return "product/productclassify/ywProductClassifyList";
	}
	
	/**
	 * 查询字典集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public Map<String, Object> getList(YwProductClassify ywProductClassify,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
		map.putAll(MapUtil.toMap(ywProductClassify));
		List<YwProductClassify> list = ywProductClassifyService.queryPage(map);
		//List<YwProductClassify> list = ywProductClassifyService.queryList(ywProductClassify);
		map.put("error_no", "0");
		map.put("error_info", "查询成功");
		map.put("resultList", list);
		return map;
	}
	
	/**
	 * 查询分页情况
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("getPaginator")
	@ResponseBody
	public Paginator getPaginator(YwProductClassify ywProductClassify,@RequestParam(defaultValue = "10") int pageSize){
		int count = ywProductClassifyService.queryTotals(ywProductClassify);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 选择菜单级别数据获取
	*
	*@param ywProductClassify
	*@return
	*Map<String,Object>
	 */
	@RequestMapping("getClassifyLevel")
	@ResponseBody
	public Map<String, Object> getClassifyLevel(YwProductClassify ywProductClassify){
		Map<String, Object> map = new HashMap<String, Object>();
		List<YwProductClassify> classifyList = ywProductClassifyService.queryList(ywProductClassify);
		map.put("classifyList", classifyList);
		
		return map;
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(YwProductClassify ywProductClassify,String op_type,Model model){
		YwProductClassify classify = new YwProductClassify();
		classify.setClassify_level("1");
		classify.setClassify_status("1");
		List<YwProductClassify> classifyList = ywProductClassifyService.queryList(classify);
		if(CollectionUtils.isNotEmpty(classifyList)){
			model.addAttribute("classifyList", classifyList);
		}
		model.addAttribute("op_type", op_type);
		
		if(StringUtils.equals(op_type, "2")){
			model.addAttribute("ywProductClassify", ywProductClassifyService.query(ywProductClassify));
		} else {
			model.addAttribute("ywProductClassify", ywProductClassify);
		}
		
		return "/product/productclassify/ywProductClassifyEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(@RequestParam(value = "file", required = false) MultipartFile file,YwProductClassify ywProductClassify,String op_type){
		Map<String,Object> map = new HashMap<String, Object>();
		if(StringUtils.equals(op_type, "1")){
			// 查询分类是否存在
			YwProductClassify classify = new YwProductClassify();
			classify.setClassify_name(ywProductClassify.getClassify_name());
			classify = ywProductClassifyService.query(classify);
			if(null == classify){
				// 判断文件是否为空
				if(file != null && file.getSize() != 0){
					ywProductClassify.setClassify_url(ywProductClassifyService.uploadClassifyImage(file, ywProductClassify));
				}
				ywProductClassify.setClassify_createtime(new Date());
				ywProductClassifyService.insert(ywProductClassify);
				map.put("error_no", "0");
				map.put("error_info", "添加成功");
			} else {
				map.put("error_no", "-1");
				map.put("error_info", "分类已存在，不能重复添加");
			}
		} else {
			// 判断文件是否为空
			if(file != null && file.getSize() != 0){
				ywProductClassify.setClassify_url(ywProductClassifyService.uploadClassifyImage(file, ywProductClassify));
			}
			ywProductClassify.setClassify_updatetime(new Date());
			ywProductClassifyService.update(ywProductClassify);
			map.put("error_no", "0");
			map.put("error_info", "修改成功");
		}
		return map;
	}
	
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Map<String,Object> delete(YwProductClassify ywProductClassify){
		Map<String,Object> map = new HashMap<String, Object>();
		ywProductClassifyService.delete(ywProductClassify);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping("deleteBatch")
	@ResponseBody
	public Map<String,Object> deleteBatch(YwProductClassify ywProductClassify,String classify_ids){
		Map<String,Object> map = new HashMap<String, Object>();
		String[] strings = classify_ids.split(",");
		Map<String, String[]> strMap = new HashMap<String, String[]>();
		strMap.put("classify_id",strings);
		ywProductClassifyService.deleteBatch(strMap);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}	
}
