package com.yunwei.product.backend.salon.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.I0Itec.zkclient.DataUpdater;
import org.apache.log4j.Logger;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.yunwei.product.backend.service.QhzSalonMeetingService;
import com.yunwei.product.backend.service.QhzSalonSignService;
import com.yunwei.product.backend.service.impl.ExcelExportServiceImpl;
import com.yunwei.product.common.model.QhzSalonMeeting;
import com.yunwei.product.common.model.QhzSalonSign;
import com.yunwei.product.common.model.YwProduct;
import com.yunwei.common.user.SysUser;
import com.yunwei.common.util.DateUtil;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.common.exception.BizException;
import com.yunwei.context.sys.cache.YwDictionaryCache;
import com.yunwei.context.sys.model.YwDictionary;

/**
 * 沙龙注册签到信息模块控制层
* @ClassName: QhzSalonSignManage 
* @Description: TODO(TODO) 
* @author yanf
* @date 2019年07月18日 上午09:21:30
*
 */
@Controller
@RequestMapping("/qhz_salon_sign/qhzSalonSign")
public class QhzSalonSignManage {
	private static Logger logger = Logger.getLogger(QhzSalonSignManage.class);
	@Autowired
	private QhzSalonSignService qhzSalonSignService;
	@Autowired
	private QhzSalonMeetingService qhzSalonMeetingService;
	@Autowired
	private YwDictionaryCache ywDictionaryCache;
	@Autowired
	private ExcelExportServiceImpl excelExportServiceImpl;
 
	@RequestMapping
	public String qhzSalonSignList(Model model){
		model.addAttribute("qhzSalonMeetings",qhzSalonMeetingService.queryList());

		return "/salon/sign/qhzSalonSignList";
	}
	
	/**
	 * 查询列表集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public Map<String, Object> getList(SysUser sysUser,QhzSalonSign qhzSalonSign,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
		map.putAll(MapUtil.toMap(qhzSalonSign));
		List<QhzSalonSign> list = qhzSalonSignService.queryUnionListPage(map,start,pageSize);
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
	public Paginator getPaginator(QhzSalonSign qhzSalonSign,@RequestParam(defaultValue = "10") int pageSize){
		int count = qhzSalonSignService.queryTotals(qhzSalonSign);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(SysUser sysUser,QhzSalonSign qhzSalonSign,String op_type,Model model){
		model.addAttribute("op_type", op_type);
		if(StringUtils.equals(op_type, "2")){
			qhzSalonSign = qhzSalonSignService.query(qhzSalonSign);
			//查询会议名称
			QhzSalonMeeting qhzSalonMeeting = qhzSalonMeetingService.query(qhzSalonSign.getMeeting_id());
			if(qhzSalonMeeting != null){
				qhzSalonSign.setMeeting_name(qhzSalonMeeting.getName());
			}
			model.addAttribute("qhzSalonSign", qhzSalonSign);
		} else {
			model.addAttribute("qhzSalonSign", qhzSalonSign);
		}
		
		return "/salon/sign/qhzSalonSignEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(QhzSalonSign qhzSalonSign,String op_type){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			if(StringUtils.equals(op_type, "1")){
				qhzSalonSignService.insert(qhzSalonSign);
				map.put("error_no", "0");
				map.put("error_info", "添加成功");
			} else {
				qhzSalonSignService.update(qhzSalonSign);
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
	public Map<String,Object> delete(QhzSalonSign qhzSalonSign){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			qhzSalonSignService.delete(qhzSalonSign);
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
	public Map<String,Object> deleteBatch(QhzSalonSign qhzSalonSign){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			String[] id = qhzSalonSign.getId().split(",");
			Map<String, String[]> strMap = new HashMap<String, String[]>();
			strMap.put("id",id);
			qhzSalonSignService.deleteBatch(strMap);
			map.put("error_no", "0");
			map.put("error_info", "删除成功");
		} catch (Exception e) {
		    logger.info(e);
			throw new BizException(e);
		}
		return map;
	}
	
	/**
	 * 导出excel表格
	 */
	@RequestMapping("exportExcel")
	public void btnSendOutGoods(SysUser sysUser,QhzSalonSign qhzSalonSign,HttpServletRequest request,HttpServletResponse response) throws Exception{
		JSONObject jsonObject = new JSONObject();
		String excel_name = "签到信息统计表单";
		String sheet_name = "签到信息";
		// 如果支付状态不为空 , 查询该数据字典对应的名称
		String pay_status = "";
		long start = System.currentTimeMillis();
		List<String> columnNames = new ArrayList<String>();
		columnNames.add("签到编号");
		columnNames.add("签到人姓名");
		columnNames.add("签到人联系电话");
		columnNames.add("签到人身份证");
		columnNames.add("学习顾问");
		columnNames.add("行业");
		columnNames.add("年营业额(单位:万)");
		columnNames.add("职位");
		columnNames.add("票务编号");
		columnNames.add("签到时间");
		columnNames.add("签到会议编号");
		columnNames.add("签到入场号");
		columnNames.add("入场费支付状态");
		columnNames.add("备注");
		
		List<String[]> dataList = new ArrayList<String[]>();
		// 根据条件查询数据
		Map<String, Object> paramMap = MapUtil.toMap(qhzSalonSign);
		List<QhzSalonSign> salonSign = qhzSalonSignService.queryList(paramMap);
		if(salonSign.size() > 0){
			for(int j=0 ; j< salonSign.size() ; j++){
				QhzSalonSign sign = salonSign.get(j);
				
				if(StringUtils.isNotBlank(sign.getPay_status())){
					YwDictionary dic_market_code = ywDictionaryCache.getDictionary("1094", sign.getPay_status());
					pay_status = dic_market_code.getDic_subvalue();
				}
				
				String[] strings = {
						sign.getId(),
						sign.getName(),
						sign.getPhone(),
						sign.getCard(),
						sign.getAdviser(),
						sign.getIndustry(),
						sign.getTurnover(),
						sign.getPosition(),
						sign.getTicket_number(),
						DateUtil.format(sign.getSgin_time(), DateUtil.DATE_TIME_FORMAT),
						sign.getMeeting_id(),
						sign.getSgin_code(),
						pay_status,
						sign.getRemark()
				};
				dataList.add(strings);
				jsonObject.put("timeMillis",(System.currentTimeMillis() - start)/1000);
			}
		}
		excelExportServiceImpl.exportWithResponse(excel_name, sheet_name, columnNames, dataList, request , response);
	}	
}
