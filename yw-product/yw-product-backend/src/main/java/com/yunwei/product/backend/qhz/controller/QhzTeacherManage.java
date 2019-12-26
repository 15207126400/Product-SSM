package com.yunwei.product.backend.qhz.controller;

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

import com.yunwei.product.backend.service.QhzTeacherService;
import com.yunwei.product.common.model.QhzTeacher;
import com.yunwei.common.user.SysUser;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.common.exception.BizException;
import com.yunwei.context.service.PictureService;

/**
 * 老师信息模块控制层
* @ClassName: QhzTeacherManage 
* @Description: TODO(TODO) 
* @author yanf
* @date 2019年07月16日 上午11:29:22
*
 */
@Controller
@RequestMapping("/qhz_teacher/qhzTeacher")
public class QhzTeacherManage {
	private static Logger logger = Logger.getLogger(QhzTeacherManage.class);
	@Autowired
	private QhzTeacherService qhzTeacherService;
	@Autowired
	private PictureService pictureService;
 
	@RequestMapping
	public String qhzTeacherList(Model model){

		return "/qhz/teacher/qhzTeacherList";
	}
	
	/**
	 * 查询列表集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public List<Map<String, Object>> getList(SysUser sysUser,QhzTeacher qhzTeacher,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
		map.putAll(MapUtil.toMap(qhzTeacher));
		List<QhzTeacher> list = qhzTeacherService.queryPage(map);
		return MapUtil.toMapList(list);
	}
	
	/**
	 * 查询分页情况
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("getPaginator")
	@ResponseBody
	public Paginator getPaginator(QhzTeacher qhzTeacher,@RequestParam(defaultValue = "10") int pageSize){
		int count = qhzTeacherService.queryTotals(qhzTeacher);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(SysUser sysUser,QhzTeacher qhzTeacher,String op_type,Model model){
		model.addAttribute("op_type", op_type);
		if(StringUtils.equals(op_type, "2")){
			model.addAttribute("qhzTeacher", qhzTeacherService.query(qhzTeacher));
		} else {
			model.addAttribute("qhzTeacher", qhzTeacher);
		}
		
		return "/qhz/teacher/qhzTeacherEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(@RequestParam(value = "file", required = false) MultipartFile file , QhzTeacher qhzTeacher , String op_type){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			if(file != null && file.getSize() != 0){
				qhzTeacher.setPhoto(pictureService.uploadOriginalImage(file));
			}
			if(StringUtils.equals(op_type, "1")){
				qhzTeacherService.insert(qhzTeacher);
				map.put("error_no", "0");
				map.put("error_info", "添加成功");
			} else {
				qhzTeacherService.update(qhzTeacher);
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
	public Map<String,Object> delete(QhzTeacher qhzTeacher){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			qhzTeacherService.delete(qhzTeacher);
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
	public Map<String,Object> deleteBatch(QhzTeacher qhzTeacher){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			String[] id = qhzTeacher.getId().split(",");
			Map<String, String[]> strMap = new HashMap<String, String[]>();
			strMap.put("id",id);
			qhzTeacherService.deleteBatch(strMap);
			map.put("error_no", "0");
			map.put("error_info", "删除成功");
		} catch (Exception e) {
		    logger.info(e);
			throw new BizException(e);
		}
		return map;
	}
}
