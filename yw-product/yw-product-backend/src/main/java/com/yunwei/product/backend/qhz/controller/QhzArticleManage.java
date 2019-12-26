package com.yunwei.product.backend.qhz.controller;

import java.util.Date;
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
import org.springframework.web.multipart.MultipartFile;

import com.yunwei.product.backend.service.QhzArticleService;
import com.yunwei.product.common.model.QhzArticle;
import com.yunwei.product.common.model.YwProduct;
import com.yunwei.common.user.SysUser;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.common.exception.BizException;
import com.yunwei.context.service.PictureService;

/**
 * 文章信息模块控制层
* @ClassName: QhzArticleManage 
* @Description: TODO(TODO) 
* @author yanf
* @date 2019年07月16日 下午15:34:20
*
 */
@Controller
@RequestMapping("/qhz_article/qhzArticle")
public class QhzArticleManage {
	private static Logger logger = Logger.getLogger(QhzArticleManage.class);
	@Autowired
	private QhzArticleService qhzArticleService;
	@Autowired
	private PictureService pictureService;
 
	@RequestMapping
	public String qhzArticleList(Model model){

		return "/qhz/article/qhzArticleList";
	}
	
	/**
	 * 查询列表集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public List<Map<String, Object>> getList(SysUser sysUser,QhzArticle qhzArticle,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
		map.putAll(MapUtil.toMap(qhzArticle));
		List<QhzArticle> list = qhzArticleService.queryPage(map);
		return MapUtil.toMapList(list);
	}
	
	/**
	 * 查询分页情况
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("getPaginator")
	@ResponseBody
	public Paginator getPaginator(QhzArticle qhzArticle,@RequestParam(defaultValue = "10") int pageSize){
		int count = qhzArticleService.queryTotals(qhzArticle);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(SysUser sysUser,QhzArticle qhzArticle,String op_type,Model model){
		model.addAttribute("op_type", op_type);
		if(StringUtils.equals(op_type, "2")){
			model.addAttribute("qhzArticle", qhzArticleService.query(qhzArticle));
		} else {
			model.addAttribute("qhzArticle", qhzArticle);
		}
		
		return "/qhz/article/qhzArticleEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(SysUser sysUser , @RequestParam(value = "file", required = false) MultipartFile file , QhzArticle qhzArticle , String op_type){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			if(file != null && file.getSize() != 0){
				qhzArticle.setImg(pictureService.uploadOriginalImage(file));
			}
			if(StringUtils.equals(op_type, "1")){
				qhzArticle.setCreate_name(sysUser.getUser_name());
				qhzArticle.setCreate_time(new Date());
				qhzArticleService.insert(qhzArticle);
				map.put("error_no", "0");
				map.put("error_info", "添加成功");
			} else {
				qhzArticle.setUpdate_name(sysUser.getUser_name());
				qhzArticle.setUpdate_time(new Date());
				qhzArticleService.update(qhzArticle);
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
	public Map<String,Object> delete(QhzArticle qhzArticle){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			qhzArticleService.delete(qhzArticle);
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
	public Map<String,Object> deleteBatch(QhzArticle qhzArticle){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			String[] id = qhzArticle.getId().split(",");
			Map<String, String[]> strMap = new HashMap<String, String[]>();
			strMap.put("id",id);
			qhzArticleService.deleteBatch(strMap);
			map.put("error_no", "0");
			map.put("error_info", "删除成功");
		} catch (Exception e) {
		    logger.info(e);
			throw new BizException(e);
		}
		return map;
	}	

}
