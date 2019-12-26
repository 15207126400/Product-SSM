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

import com.yunwei.product.backend.service.QhzCourseService;
import com.yunwei.product.backend.service.QhzCurriculumService;
import com.yunwei.product.backend.service.QhzTeacherService;
import com.yunwei.product.common.model.QhzCourse;
import com.yunwei.common.user.SysUser;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.common.exception.BizException;
import com.yunwei.context.service.PictureService;
import com.yunwei.context.service.VideoService;

/**
 * 课程详细信息控制层
* @ClassName: QhzCourseManage 
* @Description: TODO(TODO) 
* @author yanf
* @date 2019年08月10日 上午10:17:34
*
 */
@Controller
@RequestMapping("/qhz_course/qhzCourse")
public class QhzCourseManage {
	private static Logger logger = Logger.getLogger(QhzCourseManage.class);
	@Autowired
	private QhzCourseService qhzCourseService;
	@Autowired
	private QhzCurriculumService qhzCurriculumService;
	@Autowired
	private QhzTeacherService qhzTeacherService;
	@Autowired
	private PictureService pictureService;
	@Autowired
	private VideoService videoService;
 
	@RequestMapping
	public String qhzCourseList(Model model){
		
		model.addAttribute("qhzCurriculums",qhzCurriculumService.queryList());	//课程信息
		
		model.addAttribute("qhzTeachers",qhzTeacherService.queryList());		//老师信息
		

		return "/qhz/course/qhzCourseList";
	}
	
	/**
	 * 查询列表集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public List<Map<String, Object>> getList(SysUser sysUser,QhzCourse qhzCourse,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
		map.putAll(MapUtil.toMap(qhzCourse));
		List<QhzCourse> list = qhzCourseService.queryUnionListPage(map,start,pageSize);
		return MapUtil.toMapList(list);
	}
	
	/**
	 * 查询分页情况
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("getPaginator")
	@ResponseBody
	public Paginator getPaginator(QhzCourse qhzCourse,@RequestParam(defaultValue = "10") int pageSize){
		int count = qhzCourseService.queryTotals(qhzCourse);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(SysUser sysUser,QhzCourse qhzCourse,String op_type,Model model){
		model.addAttribute("op_type", op_type);
		if(StringUtils.equals(op_type, "2")){
			model.addAttribute("qhzCourse", qhzCourseService.query(qhzCourse));
		} else {
			model.addAttribute("qhzCourse", qhzCourse);
		}
		
		//课程信息集合
		model.addAttribute("qhzCurriculums" , qhzCurriculumService.queryList());
		//老师数据集合
		model.addAttribute("qhzTeachers" , qhzTeacherService.queryList());
		
		return "/qhz/course/qhzCourseEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(SysUser sysUser , @RequestParam(value = "file", required = false) MultipartFile file ,QhzCourse qhzCourse,String op_type){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			if(file != null && file.getSize() != 0){
				qhzCourse.setImg(pictureService.uploadOriginalImage(file));
			}
			if(StringUtils.equals(op_type, "1")){
				qhzCourse.setCreate_name(sysUser.getUser_name());
				qhzCourse.setCreate_time(new Date());
				qhzCourseService.insert(qhzCourse);
				map.put("error_no", "0");
				map.put("error_info", "添加成功");
			} else {
				qhzCourse.setUpdate_name(sysUser.getUser_name());
				qhzCourse.setUpdate_time(new Date());
				qhzCourseService.update(qhzCourse);
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
	public Map<String,Object> delete(QhzCourse qhzCourse){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			qhzCourseService.delete(qhzCourse);
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
	public Map<String,Object> deleteBatch(QhzCourse qhzCourse){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			String[] id = qhzCourse.getId().split(",");
			Map<String, String[]> strMap = new HashMap<String, String[]>();
			strMap.put("id",id);
			qhzCourseService.deleteBatch(strMap);
			map.put("error_no", "0");
			map.put("error_info", "删除成功");
		} catch (Exception e) {
		    logger.info(e);
			throw new BizException(e);
		}
		return map;
	}
	
	/**
	 * 视频文件上传
	*
	*@param video
	*@return
	*Map<String,Object>
	 */
	@RequestMapping("uploadVideo")
	@ResponseBody
	public Map<String,Object> uploadVideo(@RequestParam("videoFile") MultipartFile videoFile){
		Map<String,Object> map = new HashMap<String,Object>();
		if(videoFile != null && videoFile.getSize() != 0){
			Map resultMap = videoService.uploadVideo(videoFile);
			map.put("url", resultMap.get("url").toString());
		}
		
		return map;
	}
	
	/**
	 * 删除视频文件
	*
	*@param qhzCourse
	*void
	 */
	@RequestMapping("deleteVideo")
	@ResponseBody
	public void deleteVideo(QhzCourse qhzCourse){
		qhzCourse = qhzCourseService.query(qhzCourse);
		if(qhzCourse != null){
			qhzCourse.setVideo("");
			qhzCourseService.update(qhzCourse);
		}
	}
}

