package com.yunwei.product.backend.product.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunwei.product.backend.service.YwProductImageService;
import com.yunwei.product.common.model.YwProductImage;
import com.yunwei.common.user.SysUser;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.common.exception.BizException;

/**
 * 商品图片控制层（主要存类似轮播图）
* @ClassName: YwProductImageManage 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年08月29日 下午16:28:58
*
 */
@Controller
@RequestMapping("/product/ywProductImage")
public class YwProductImageManage {
	private static Logger logger = Logger.getLogger(YwProductImageManage.class);
	@Autowired
	private YwProductImageService ywProductImageService;
 
	@RequestMapping
	public String ywProductImageList(Model model){

		return "/product/productimage/ywProductImageList";
	}
	
	/**
	 * 查询字典集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public List<Map<String, Object>> getList(SysUser sysUser,YwProductImage ywProductImage,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
		map.putAll(MapUtil.toMap(ywProductImage));
		List<YwProductImage> list = ywProductImageService.queryPage(map);
		return MapUtil.toMapList(list);
	}
	
	/**
	 * 查询分页情况
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("getPaginator")
	@ResponseBody
	public Paginator getPaginator(YwProductImage ywProductImage,@RequestParam(defaultValue = "10") int pageSize){
		int count = ywProductImageService.queryTotals(ywProductImage);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(SysUser sysUser,YwProductImage ywProductImage,String op_type,Model model){
		model.addAttribute("op_type", op_type);
		if(StringUtils.equals(op_type, "2")){
			model.addAttribute("ywProductImage", ywProductImageService.query(ywProductImage));
		} else {
			model.addAttribute("ywProductImage", ywProductImage);
		}
		
		return "/product/productimage/ywProductImageEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(YwProductImage ywProductImage,String op_type){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			if(StringUtils.equals(op_type, "1")){
				ywProductImageService.insert(ywProductImage);
				map.put("error_no", "0");
				map.put("error_info", "添加成功");
			} else {
				ywProductImageService.update(ywProductImage);
				map.put("error_no", "0");
				map.put("error_info", "修改成功");
			}
		} catch (Exception e) {
		    logger.info(e);
			throw new BizException(e);
		}
		return map;
	}
	
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Map<String,Object> delete(YwProductImage ywProductImage){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			ywProductImageService.delete(ywProductImage);
			map.put("error_no", "0");
			map.put("error_info", "删除成功");
		} catch (Exception e) {
		    logger.info(e);
			throw new BizException(e);
		}
		return map;
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping("deleteBatch")
	@ResponseBody
	public Map<String,Object> deleteBatch(YwProductImage ywProductImage){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			String[] id = ywProductImage.getId().split(",");
			Map<String, String[]> strMap = new HashMap<String, String[]>();
			strMap.put("id",id);
			ywProductImageService.deleteBatch(strMap);
			map.put("error_no", "0");
			map.put("error_info", "删除成功");
		} catch (Exception e) {
		    logger.info(e);
			throw new BizException(e);
		}
		return map;
	}	
}
