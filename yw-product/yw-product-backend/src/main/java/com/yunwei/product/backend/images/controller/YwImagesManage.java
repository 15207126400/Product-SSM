package com.yunwei.product.backend.images.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import com.yunwei.common.exception.BizException;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.context.service.PictureService;
import com.yunwei.product.backend.service.YwImagesService;
import com.yunwei.product.common.model.YwImages;
import com.yunwei.product.common.model.YwImagesForm;

/**
 * 图片中心控制器
 * @author zhangz
 *
 */
@Controller
@RequestMapping("/system/ywImages")
public class YwImagesManage {
	
	@Autowired
	private YwImagesService ywImagesService;
	@Autowired
	private PictureService pictureService;

	@RequestMapping
	public String ywImagesList(Model model){
		return "/system/Images/ywImagesList";
	}
	
	/**
	 * 查询字典集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public Map<String, Object> getList(YwImages ywImages,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
		map.putAll(MapUtil.toMap(ywImages));
		List<YwImages> list = ywImagesService.queryPage(map);
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
	public Paginator getPaginator(YwImages ywImages,@RequestParam(defaultValue = "10") int pageSize){
		int count = ywImagesService.queryTotals(ywImages);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(YwImages ywImages,String op_type,Model model){
		model.addAttribute("op_type", op_type);
		if(StringUtils.equals(op_type, "2")){
			model.addAttribute("ywImages", ywImagesService.query(ywImages));
		} else {
			model.addAttribute("ywImages", ywImages);
		}
		
		return "/system/Images/ywImagesEdit";
	}
	
	// 上传图片返回路径
	@RequestMapping("img_upload")
	@ResponseBody
	public Map<String, Object> img_upload(
			@RequestParam(value = "file", required = false) MultipartFile file) {
		Map<String, Object> newmap = new HashMap<String, Object>();
		try {
			if(file != null && file.getSize() > 0){
				newmap.put("image_url", pictureService.uploadOriginalImage(file));
			}
		} catch (Exception e) {
			throw new BizException(e);
		}
		
		return newmap;
	}
	
	/**
	 * 富文本图片上传
	 * @param upfile
	 * @return
	 */
	@RequestMapping("ueditor_file_upload")
	@ResponseBody
	public Map<String, Object> ueditor_file_upload(
			@RequestParam(value = "upfile", required = false) MultipartFile upfile) {
		Map<String, Object> newmap = new HashMap<String, Object>();
		try {
			if(upfile != null && upfile.getSize() > 0){
				newmap.put("url", pictureService.uploadOriginalImage(upfile));
				newmap.put("state","SUCCESS");
				//newmap.put("url","upload/demo.jpg");
//					newmap.put("title","demo.jpg");
//					newmap.put("original","demo.jpg");
			}
		} catch (Exception e) {
			throw new BizException(e);
		}
		return newmap;
	}
	
	/**
	 * 列出富文本编辑上传的图片
	 * @param action
	 * @param start
	 * @param size
	 * @param noCache
	 * @return
	 */
	@RequestMapping("listimage")
	@ResponseBody
	public Map<String, Object> listimage(String action,String start,String size,String noCache) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<YwImages> images = ywImagesService.queryList(new YwImages());
			List<YwImagesForm> forms = new ArrayList<YwImagesForm>();
			for(YwImages image: images){
				YwImagesForm  form = new YwImagesForm();
				if(StringUtils.isNotBlank(image.getImage_url())){
					form.setUrl(image.getImage_url());
				}
				forms.add(form);
			}
			map.put("state", "SUCCESS");
			map.put("total", images.size());
			map.put("start", 0);
			map.put("list", forms);
		} catch (Exception e) {
			throw new BizException(e);
		}
		return map;
	}
	
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(YwImages ywImages,String op_type){
		Map<String,Object> map = new HashMap<String, Object>();
		if(StringUtils.equals(op_type, "1")){
			ywImagesService.insert(ywImages);
			map.put("error_no", "0");
			map.put("error_info", "添加成功");
		} else {
			ywImagesService.update(ywImages);
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
	public Map<String,Object> delete(YwImages ywImages){
		Map<String,Object> map = new HashMap<String, Object>();
		ywImagesService.delete(ywImages);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}
}
