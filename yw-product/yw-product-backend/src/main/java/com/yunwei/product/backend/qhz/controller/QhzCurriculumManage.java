package com.yunwei.product.backend.qhz.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yunwei.product.backend.service.QhzCurriculumService;
import com.yunwei.product.backend.service.QhzTeacherService;
import com.yunwei.product.common.model.QhzCurriculum;
import com.yunwei.common.user.SysUser;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.common.exception.BizException;
import com.yunwei.context.service.PictureService;
import com.yunwei.context.service.VideoService;

/**
 * 沙龙注册课程信息表控制层
* @ClassName: QhzCurriculumManage 
* @Description: TODO(TODO) 
* @author yanf
* @date 2019年06月21日 下午16:01:29
*
 */
@Controller
@RequestMapping("/qhz_curriculum/qhzCurriculum")
public class QhzCurriculumManage {
	private static Logger logger = Logger.getLogger(QhzCurriculumManage.class);
	@Autowired
	private QhzCurriculumService qhzCurriculumService;
	@Autowired
	private QhzTeacherService qhzTeacherService;
	@Autowired
	private PictureService pictureService;
	
 
	@RequestMapping
	public String qhzCurriculumList(Model model){

		return "/qhz/curriculum/qhzCurriculumList";
	}
	
	/**
	 * 查询列表集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public List<Map<String, Object>> getList(SysUser sysUser,QhzCurriculum qhzCurriculum,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
		map.putAll(MapUtil.toMap(qhzCurriculum));
		List<QhzCurriculum> list = qhzCurriculumService.queryUnionListPage(map,start,pageSize);
		return MapUtil.toMapList(list);
	}
	
	/**
	 * 查询分页情况
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("getPaginator")
	@ResponseBody
	public Paginator getPaginator(QhzCurriculum qhzCurriculum,@RequestParam(defaultValue = "10") int pageSize){
		int count = qhzCurriculumService.queryTotals(qhzCurriculum);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(SysUser sysUser,QhzCurriculum qhzCurriculum,String op_type,Model model){
		model.addAttribute("op_type", op_type);
		if(StringUtils.equals(op_type, "2")){
			model.addAttribute("qhzCurriculum", qhzCurriculumService.query(qhzCurriculum));
		} else {
			model.addAttribute("qhzCurriculum", qhzCurriculum);
		}
		model.addAttribute("qhzTeachers",qhzTeacherService.queryList());
		
		return "/qhz/curriculum/qhzCurriculumEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(SysUser sysUser , @RequestParam(value = "file", required = false) MultipartFile file , QhzCurriculum qhzCurriculum,String op_type){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			if(file != null && file.getSize() != 0){
				qhzCurriculum.setImg(pictureService.uploadOriginalImage(file));
			}
			if(StringUtils.equals(op_type, "1")){
				qhzCurriculum.setCreate_name(sysUser.getUser_name());
				qhzCurriculum.setCreate_time(new Date());
				qhzCurriculumService.insert(qhzCurriculum);
				map.put("error_no", "0");
				map.put("error_info", "添加成功");
			} else {
				qhzCurriculum.setUpdate_name(sysUser.getUser_name());
				qhzCurriculum.setUpdate_datetime(new Date());
				qhzCurriculumService.update(qhzCurriculum);
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
	public Map<String,Object> delete(QhzCurriculum qhzCurriculum){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			qhzCurriculumService.delete(qhzCurriculum);
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
	public Map<String,Object> deleteBatch(QhzCurriculum qhzCurriculum){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			String[] id = qhzCurriculum.getId().split(",");
			Map<String, String[]> strMap = new HashMap<String, String[]>();
			strMap.put("id",id);
			qhzCurriculumService.deleteBatch(strMap);
			map.put("error_no", "0");
			map.put("error_info", "删除成功");
		} catch (Exception e) {
		    logger.info(e);
			throw new BizException(e);
		}
		return map;
	}
}
