package com.yunwei.product.backend.xcx.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yunwei.product.backend.service.YwXcxCarouselService;
import com.yunwei.product.backend.service.YwXcxNavigationService;
import com.yunwei.product.common.model.YwXcxCarousel;
import com.yunwei.product.common.model.YwXcxNavigation;
import com.yunwei.common.user.SysUser;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.common.cache.CacheManager;
import com.yunwei.common.exception.BizException;
import com.yunwei.context.service.PictureService;

/**
 * 小程序轮播图控制层
* @ClassName: YwXcxCarouselManage 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年08月23日 下午16:04:12
*
 */
@Controller
@RequestMapping("/xcx/ywXcxCarousel")
public class YwXcxCarouselManage {
	private static Logger logger = Logger.getLogger(YwXcxCarouselManage.class);
	@Autowired
	private YwXcxCarouselService ywXcxCarouselService;
	@Autowired  
    private PictureService pictureService;
	@Autowired
	private YwXcxNavigationService ywXcxNavigationService;
	@Autowired
	private CacheManager cacheManager;
	
	@RequestMapping
	public String ywXcxCarouselList(Model model){

		return "/xcx/xcxcarousel/ywXcxCarouselList";
	}
	
	/**
	 * 查询字典集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public List<Map<String, Object>> getList(SysUser sysUser,YwXcxCarousel ywXcxCarousel,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		List<YwXcxCarousel> list = ywXcxCarouselService.queryListPage(ywXcxCarousel,start,end);
		return MapUtil.toMapList(list);
	}
	
	/**
	 * 查询分页情况
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("getPaginator")
	@ResponseBody
	public Paginator getPaginator(YwXcxCarousel ywXcxCarousel,@RequestParam(defaultValue = "10") int pageSize){
		int count = ywXcxCarouselService.queryTotals(ywXcxCarousel);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(SysUser sysUser,YwXcxCarousel ywXcxCarousel,String op_type,Model model){
		model.addAttribute("op_type", op_type);
		YwXcxNavigation ywXcxNavigation = new YwXcxNavigation();
		ywXcxNavigation.setUser_id(sysUser.getUser_id());
		ywXcxNavigation.setStatus("1");
		model.addAttribute("ywXcxNavigations", ywXcxNavigationService.queryList(ywXcxNavigation));
		if(StringUtils.equals(op_type, "2")){
			model.addAttribute("ywXcxCarousel", ywXcxCarouselService.query(ywXcxCarousel));
		} else {
			model.addAttribute("ywXcxCarousel", ywXcxCarousel);
		}
		
		return "/xcx/xcxcarousel/ywXcxCarouselEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(SysUser sysUser,@RequestParam(value = "file", required = false) MultipartFile file,YwXcxCarousel ywXcxCarousel,String op_type){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			// 判断文件是否为空
			if(file != null && file.getSize() != 0){
				ywXcxCarousel.setCarousel_url(ywXcxCarouselService.uploadCarouselImage(file, ywXcxCarousel));
			}
			if(StringUtils.equals(op_type, "1")){
				ywXcxCarousel.setCreate_datetime(new Date());
				ywXcxCarouselService.insert(ywXcxCarousel);
				map.put("error_no", "0");
				map.put("error_info", "添加成功");
			} else {
				if(StringUtils.isBlank(ywXcxCarousel.getCarousel_url())){
					String carousel_url = ywXcxCarouselService.queryByImageId(ywXcxCarousel.getId());
					ywXcxCarousel.setCarousel_url(carousel_url);
				}
				ywXcxCarousel.setUpdate_datetime(new Date());
				ywXcxCarouselService.update(ywXcxCarousel);
				map.put("error_no", "0");
				map.put("error_info", "修改成功");
			}
			cacheManager.refreshOne("ywXcxCarouselCache");
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
	public Map<String,Object> delete(YwXcxCarousel ywXcxCarousel){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			ywXcxCarouselService.delete(ywXcxCarousel);
			map.put("error_no", "0");
			map.put("error_info", "删除成功");
			
			cacheManager.refreshOne("ywXcxCarouselCache");
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
	public Map<String,Object> deleteBatch(YwXcxCarousel ywXcxCarousel){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			String[] id = ywXcxCarousel.getId().split(",");
			Map<String, String[]> strMap = new HashMap<String, String[]>();
			strMap.put("id",id);
			ywXcxCarouselService.deleteBatch(strMap);
			map.put("error_no", "0");
			map.put("error_info", "删除成功");
		} catch (Exception e) {
		    logger.info(e);
			throw new BizException(e);
		}
		return map;
	}	
}
