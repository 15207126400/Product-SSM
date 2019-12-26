package com.yunwei.product.infobase.salon.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunwei.common.exception.BizException;
import com.yunwei.product.common.model.QhzCurriculum;
import com.yunwei.product.infobase.constant.ConstantFunctionsFront;
import com.yunwei.product.infobase.service.QhzCurriculumService;

/**
 * 启恒智沙龙注册课程接口
* @ClassName: qhzCurriculumController 
* @Description: TODO(TODO) 
* @author 晏飞
* @date 2019年6月22日 上午9:03:24 
*
 */
@Controller
public class QhzSalonCurriculumController {
	
	@Autowired
	private QhzCurriculumService qhzCurriculumService;
	
	/**
	 * 查询课程信息
	*
	*@return
	*Map<String,Object>
	 */
	@RequestMapping(ConstantFunctionsFront.QHZ_SL011)
	@ResponseBody
	public Map<String, Object> queryCurriculum(String existing_ids){
		Map<String, Object> map = new HashMap<String, Object>();
		List<QhzCurriculum> qhzSalonCurricus1 = new ArrayList<QhzCurriculum>();
		List<QhzCurriculum> qhzSalonCurricus2 = new ArrayList<QhzCurriculum>();
		try {
			//原始课程数据
			QhzCurriculum curriculum = new QhzCurriculum();
			curriculum.setScene_type("1");
			curriculum.setStatus("1");
			List<QhzCurriculum> qhzCurriculumList = qhzCurriculumService.queryList(curriculum);
			
			//处理是否购买后的课程数据
			List<QhzCurriculum> curriculumList = new ArrayList<QhzCurriculum>();
			//如果已经购买过的课程,做界面置灰处理
			if(qhzCurriculumList.size() > 0){
				for(QhzCurriculum qhzCurriculum : qhzCurriculumList){
					qhzCurriculum = getHaveFlag(qhzCurriculum,existing_ids);
					curriculumList.add(qhzCurriculum);
				}
				
				//数据分组
				for(QhzCurriculum qhzCurriculum : curriculumList){
					if(qhzCurriculum.getType().equals("1")){
						qhzSalonCurricus1.add(qhzCurriculum);
					} else {
						qhzSalonCurricus2.add(qhzCurriculum);
					}
				}
			}
			
			map.put("qhzSalonCurricus1", qhzSalonCurricus1);
			map.put("qhzSalonCurricus2", qhzSalonCurricus2);
			
			return map;
		} catch (Exception e) {
			throw new BizException("【查询课程信息】 : " + e);
		}
	}
	
	/**
	 * 处理是否已购
	*
	*@return
	*qhzCurriculum
	 */
	public QhzCurriculum getHaveFlag(QhzCurriculum qhzCurriculum , String existing_ids){
		String[] ids = existing_ids.split(",");
		for (String s : ids) {
			if(s.equals(qhzCurriculum.getId())){
				qhzCurriculum.setHaveFlag(true);
			}
		}
		return qhzCurriculum;
	}
	
}
