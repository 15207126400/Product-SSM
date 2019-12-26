package com.yunwei.product.front.controller;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yunwei.common.annotation.NoLogin;
import com.yunwei.common.util.MapUtil;
import com.yunwei.product.common.model.QhzArticle;
import com.yunwei.product.common.model.QhzCourse;
import com.yunwei.product.common.model.QhzCurriculum;
import com.yunwei.product.common.model.QhzTeacher;
import com.yunwei.product.common.model.YwNotice;
import com.yunwei.product.common.model.YwXcxCarousel;
import com.yunwei.product.front.service.QhzArticleService;
import com.yunwei.product.front.service.QhzCurriculumService;
import com.yunwei.product.front.service.QhzTeacherService;
import com.yunwei.product.front.service.YwImagesService;
import com.yunwei.product.front.service.YwNoticeService;
import com.yunwei.product.front.service.YwXcxCarouselService;

/**
 * 微信端网页
* @ClassName: indexController 
* @Description: TODO(TODO) 
* @author 晏飞
* @date 2019年6月10日 下午4:37:34 
*
 */
@Controller
@RequestMapping
public class indexController {
	
	@Autowired
	private YwXcxCarouselService ywXcxCarouselService;
	@Autowired
	private YwImagesService ywImagesService;
	@Autowired
	private QhzArticleService qhzArticleService;
	@Autowired
	private QhzTeacherService qhzTeacherService;
	@Autowired
	private QhzCurriculumService qhzCurriculumService;
	@Autowired
	private YwNoticeService ywNoticeService;
	
	/**
	 * 跳转首页
	 */
	@NoLogin
	@RequestMapping("index")
	public String goIndex(Model model){
		//查询轮播图
		YwXcxCarousel ywXcxCarousel = new YwXcxCarousel();
		ywXcxCarousel.setCarousel_type("2");
		ywXcxCarousel.setStatus("1");
		model.addAttribute("ywXcxCarousels",ywXcxCarouselService.queryList(ywXcxCarousel));
		
		//查询并组装老师数据
		model.addAttribute("qhzTeachers",qhzTeacherService.queryList());
		
		//查询公告
		YwNotice ywNotice = new YwNotice();
		ywNotice.setScene_type("2");
		ywNotice.setStatus("1");
		model.addAttribute("ywNotices",ywNoticeService.queryList(ywNotice));
		
		return "index";
	}
	
	/**
	 * 跳转线上课堂
	 */
	@NoLogin
	@RequestMapping("school")
	public String goSchool(Model model){ return "school"; }
	
	/**
	 * 跳转线上课堂子界面
	 */
	@NoLogin
	@RequestMapping("school_one")
	public String goSchoolOne(Model model , String op_type){
		model.addAttribute("op_type",op_type);
		
		return "school_one";
	}	
	
	/**
	 * 跳转精品课程
	 */
	@NoLogin
	@RequestMapping("course")
	public String goCourse(Model model){
		QhzCurriculum qhzCurriculum = new QhzCurriculum();
		qhzCurriculum.setScene_type("2");
		qhzCurriculum.setStatus("1");
		List<QhzCurriculum> qhzCurriculums = qhzCurriculumService.queryList(qhzCurriculum);
		
		//组装分页数据
		model.addAttribute("qhzCurriculums",qhzCurriculums);
		
		return "course";
	}
	
	/**
	 * 跳转客户案例(文章)
	 */
	@NoLogin
	@RequestMapping("case")
	public String goCase(Model model){
		QhzArticle qhzArticle = new QhzArticle();
		qhzArticle.setScene_type("2");
		qhzArticle.setType("3");
		qhzArticle.setStatus("1");
		
		model.addAttribute("qhzArticles",qhzArticleService.queryList(qhzArticle));
		
		return "case";
	}
	
	/**
	 * 跳转名师介绍
	 */
	@NoLogin
	@RequestMapping("teacher")
	public String goTeacher(Model model){ return "teacher"; }
	
	/**
	 * 跳转关于我们
	 */
	@NoLogin
	@RequestMapping("about")
	public String goAbout(){ return "about"; }
	
	/**
	 * 跳转诚聘英才
	 */
	@NoLogin
	@RequestMapping("job")
	public String goJob(){ return "job"; }
	
	@NoLogin
	@RequestMapping("job2")
	public String goJob2(){ return "job2"; }
	
	@NoLogin
	@RequestMapping("job3")
	public String goJob3(){ return "job3"; }
	
	@NoLogin
	@RequestMapping("job4")
	public String goJob4(){ return "job4"; }
	
}
