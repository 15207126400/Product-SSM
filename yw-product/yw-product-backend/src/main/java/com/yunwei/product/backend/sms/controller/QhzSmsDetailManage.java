package com.yunwei.product.backend.sms.controller;

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

import com.yunwei.product.backend.service.QhzSmsConfigService;
import com.yunwei.product.backend.service.QhzSmsDetailService;
import com.yunwei.product.common.model.QhzSmsConfig;
import com.yunwei.product.common.model.QhzSmsDetail;
import com.yunwei.common.user.SysUser;
import com.yunwei.common.util.DateUtil;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.common.util.RedMapleSmsUtil;
import com.yunwei.common.exception.BizException;

/**
 * 沙龙注册短信发送信息控制层
* @ClassName: QhzSmsDetailManage 
* @Description: TODO(TODO) 
* @author yanf
* @date 2019年07月04日 上午11:38:45
*
 */
@Controller
@RequestMapping("/qhz_sms_detail/qhzSmsDetail")
public class QhzSmsDetailManage {
	private static Logger logger = Logger.getLogger(QhzSmsDetailManage.class);
	@Autowired
	private QhzSmsDetailService qhzSmsDetailService;
	@Autowired
	private QhzSmsConfigService qhzSmsConfigService;
 
	@RequestMapping
	public String qhzSmsDetailList(Model model){

		return "/sms/detail/qhzSmsDetailList";
	}
	
	/**
	 * 查询列表集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public List<Map<String, Object>> getList(SysUser sysUser,QhzSmsDetail qhzSmsDetail,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
		map.putAll(MapUtil.toMap(qhzSmsDetail));
		List<QhzSmsDetail> list = qhzSmsDetailService.queryPage(map);
		return MapUtil.toMapList(list);
	}
	
	/**
	 * 查询分页情况
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("getPaginator")
	@ResponseBody
	public Paginator getPaginator(QhzSmsDetail qhzSmsDetail,@RequestParam(defaultValue = "10") int pageSize){
		int count = qhzSmsDetailService.queryTotals(qhzSmsDetail);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(SysUser sysUser,QhzSmsDetail qhzSmsDetail,String op_type,Model model){
		model.addAttribute("op_type", op_type);
		if(StringUtils.equals(op_type, "2")){
			model.addAttribute("qhzSmsDetail", qhzSmsDetailService.query(qhzSmsDetail));
		} else {
			model.addAttribute("qhzSmsDetail", qhzSmsDetail);
		}
		
		return "/sms/detail/qhzSmsDetailEdit";
	}
	
	/**
	 * 发送短信
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(QhzSmsDetail qhzSmsDetail,String op_type){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			
			//发送短信
			String result = qhzSmsDetailService.sendSms(qhzSmsDetail);
			
			//保存信息
			qhzSmsDetail.setMessage(result);
			qhzSmsDetail.setCreateTime(new Date());
			qhzSmsDetailService.insert(qhzSmsDetail);
			map.put("error_no", "0");
			map.put("error_info", "提交成功");
			
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
	public Map<String,Object> delete(QhzSmsDetail qhzSmsDetail){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			qhzSmsDetailService.delete(qhzSmsDetail);
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
	public Map<String,Object> deleteBatch(QhzSmsDetail qhzSmsDetail){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			String[] id = qhzSmsDetail.getId().split(",");
			Map<String, String[]> strMap = new HashMap<String, String[]>();
			strMap.put("id",id);
			qhzSmsDetailService.deleteBatch(strMap);
			map.put("error_no", "0");
			map.put("error_info", "删除成功");
		} catch (Exception e) {
		    logger.info(e);
			throw new BizException(e);
		}
		return map;
	}
}
