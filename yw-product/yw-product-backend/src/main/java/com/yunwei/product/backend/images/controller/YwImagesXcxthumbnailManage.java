package com.yunwei.product.backend.images.controller;

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

import com.yunwei.product.backend.service.YwImagesXcxthumbnailService;
import com.yunwei.product.common.model.YwImagesXcxthumbnail;
import com.yunwei.common.user.SysUser;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.common.exception.BizException;

/**
 * 小程序缩略图中心控制层
* @ClassName: YwImagesXcxthumbnailManage 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年08月24日 下午14:52:12
*
 */
@Controller
@RequestMapping("/images/ywImagesXcxthumbnail")
public class YwImagesXcxthumbnailManage {
	private static Logger logger = Logger.getLogger(YwImagesXcxthumbnailManage.class);
	@Autowired
	private YwImagesXcxthumbnailService ywImagesXcxthumbnailService;
 
	@RequestMapping
	public String ywImagesXcxthumbnailList(Model model){

		return "/images/imagesxcxthumbnail/ywImagesXcxthumbnailList";
	}
	
	/**
	 * 查询字典集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public List<Map<String, Object>> getList(SysUser sysUser,YwImagesXcxthumbnail ywImagesXcxthumbnail,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
		map.putAll(MapUtil.toMap(ywImagesXcxthumbnail));
		List<YwImagesXcxthumbnail> list = ywImagesXcxthumbnailService.queryPage(map);
		return MapUtil.toMapList(list);
	}
	
	/**
	 * 查询分页情况
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("getPaginator")
	@ResponseBody
	public Paginator getPaginator(YwImagesXcxthumbnail ywImagesXcxthumbnail,@RequestParam(defaultValue = "10") int pageSize){
		int count = ywImagesXcxthumbnailService.queryTotals(ywImagesXcxthumbnail);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(SysUser sysUser,YwImagesXcxthumbnail ywImagesXcxthumbnail,String op_type,Model model){
		model.addAttribute("op_type", op_type);
		if(StringUtils.equals(op_type, "2")){
			model.addAttribute("ywImagesXcxthumbnail", ywImagesXcxthumbnailService.query(ywImagesXcxthumbnail));
		} else {
			model.addAttribute("ywImagesXcxthumbnail", ywImagesXcxthumbnail);
		}
		
		return "/images/imagesxcxthumbnail/ywImagesXcxthumbnailEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(YwImagesXcxthumbnail ywImagesXcxthumbnail,String op_type){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			if(StringUtils.equals(op_type, "1")){
				ywImagesXcxthumbnailService.insert(ywImagesXcxthumbnail);
				map.put("error_no", "0");
				map.put("error_info", "添加成功");
			} else {
				ywImagesXcxthumbnailService.update(ywImagesXcxthumbnail);
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
	public Map<String,Object> delete(YwImagesXcxthumbnail ywImagesXcxthumbnail){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			ywImagesXcxthumbnailService.delete(ywImagesXcxthumbnail);
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
	public Map<String,Object> deleteBatch(YwImagesXcxthumbnail ywImagesXcxthumbnail){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			String[] id = ywImagesXcxthumbnail.getId().split(",");
			Map<String, String[]> strMap = new HashMap<String, String[]>();
			strMap.put("id",id);
			ywImagesXcxthumbnailService.deleteBatch(strMap);
			map.put("error_no", "0");
			map.put("error_info", "删除成功");
		} catch (Exception e) {
		    logger.info(e);
			throw new BizException(e);
		}
		return map;
	}	
}
