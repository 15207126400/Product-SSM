package com.yunwei.product.infobase.sales.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yunwei.common.cache.CacheManager;
import com.yunwei.common.exception.BizException;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.context.service.PictureService;
import com.yunwei.product.common.model.YwTeamFound;
import com.yunwei.product.infobase.service.YwTeamFoundService;


/**
 * 系统字典控制器
 * @author zhangz
 *
 */
@Controller
@RequestMapping("/sales/ywTeamFound")
public class YwTeamFoundController {
	
	@Autowired
	private YwTeamFoundService ywTeamFoundService;
	@Autowired
	private CacheManager cacheManager;
	
	@Autowired  
    private PictureService pictureService;

	/**
	 * 图片链接前缀
	 */
	private static final String FRONTPATH = "https://xcx.whywxx.com/yongyou/image/home/detail";
	
	// 日志打印
    Logger logger = Logger.getLogger(YwTeamFoundController.class);

	@RequestMapping
	public String ywTeamFoundList(Model model){
		return "/sales/teamfound/ywTeamFoundList";
	}
	
	/**
	 * 查询字典集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public Map<String, Object> getList(YwTeamFound ywTeamFound,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
		map.putAll(MapUtil.toMap(ywTeamFound));
		List<YwTeamFound> list = ywTeamFoundService.queryPage(map);
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
	public Paginator getPaginator(YwTeamFound ywTeamFound,@RequestParam(defaultValue = "10") int pageSize){
		int count = ywTeamFoundService.queryTotals(ywTeamFound);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(YwTeamFound ywTeamFound,String op_type,Model model){
		model.addAttribute("op_type", op_type);
		if(StringUtils.equals(op_type, "2")){
			model.addAttribute("ywTeamFound", ywTeamFoundService.query(ywTeamFound));
		} else {
			model.addAttribute("ywTeamFound", ywTeamFound);
		}
		
		return "/sales/teamfound/ywTeamFoundEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(@RequestParam(value = "file", required = false) MultipartFile file,YwTeamFound ywTeamFound,String op_type){
		Map<String,Object> resMap = new HashMap<String, Object>();
		try {
			if(file != null && file.getSize() != 0){
				String ftpPath = pictureService.uploadOriginalImageBySftp(file);
				String posPath = ftpPath.substring(20);
				String url = FRONTPATH + posPath;
				logger.info("*******图片上传成功*******");
				ywTeamFound.setHead_pic(url);
				resMap.put("url", url);
			}
			if(StringUtils.equals(op_type, "1")){
				ywTeamFoundService.insert(ywTeamFound);
				resMap.put("error_no", "0");
				resMap.put("error_info", "添加成功");
			} else {
				ywTeamFoundService.update(ywTeamFound);
				resMap.put("error_no", "0");
				resMap.put("error_info", "修改成功");
			}
		} catch (Exception e) {
			throw new BizException(e);
		}
		return resMap;
	}
	
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Map<String,Object> delete(YwTeamFound ywTeamFound){
		Map<String,Object> map = new HashMap<String, Object>();
		ywTeamFoundService.delete(ywTeamFound);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}
}
