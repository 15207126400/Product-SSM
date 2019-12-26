package com.yunwei.product.backend.member.controller;

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

import com.yunwei.product.backend.service.YwMemberMessageRecordService;
import com.yunwei.product.common.model.YwMemberMessageRecord;
import com.yunwei.common.user.SysUser;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.common.exception.BizException;

/**
 * 会员模板消息发送记录信息控制层
* @ClassName: YwMemberMessageRecordManage 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2019年02月21日 下午14:52:29
*
 */
@Controller
@RequestMapping("/member/ywMemberMessageRecord")
public class YwMemberMessageRecordManage {
	private static Logger logger = Logger.getLogger(YwMemberMessageRecordManage.class);
	@Autowired
	private YwMemberMessageRecordService ywMemberMessageRecordService;
 
	@RequestMapping
	public String ywMemberMessageRecordList(Model model){

		return "member/membermessagerecord/ywMemberMessageRecordList";
	}
	
	/**
	 * 查询列表集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public List<Map<String, Object>> getList(SysUser sysUser,YwMemberMessageRecord ywMemberMessageRecord,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.putAll(MapUtil.toMap(ywMemberMessageRecord));
		List<YwMemberMessageRecord> list = ywMemberMessageRecordService.queryListPage(map,start,pageSize);
		return MapUtil.toMapList(list);
	}
	
	/**
	 * 查询分页情况
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("getPaginator")
	@ResponseBody
	public Paginator getPaginator(YwMemberMessageRecord ywMemberMessageRecord,@RequestParam(defaultValue = "10") int pageSize){
		int count = ywMemberMessageRecordService.queryTotals(ywMemberMessageRecord);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(SysUser sysUser,YwMemberMessageRecord ywMemberMessageRecord,String op_type,Model model){
		model.addAttribute("op_type", op_type);
		if(StringUtils.equals(op_type, "2")){
			model.addAttribute("ywMemberMessageRecord", ywMemberMessageRecordService.query(ywMemberMessageRecord));
		} else {
			model.addAttribute("ywMemberMessageRecord", ywMemberMessageRecord);
		}
		
		return "/member/membermessagerecord/ywMemberMessageRecordEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(YwMemberMessageRecord ywMemberMessageRecord,String op_type){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			if(StringUtils.equals(op_type, "1")){
				ywMemberMessageRecordService.insert(ywMemberMessageRecord);
				map.put("error_no", "0");
				map.put("error_info", "添加成功");
			} else {
				ywMemberMessageRecordService.update(ywMemberMessageRecord);
				map.put("error_no", "0");
				map.put("error_info", "修改成功");
			}
		} catch (Exception e) {
		    logger.error("新增或修改失败",e);
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
	public Map<String,Object> delete(YwMemberMessageRecord ywMemberMessageRecord){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			ywMemberMessageRecordService.delete(ywMemberMessageRecord);
			map.put("error_no", "0");
			map.put("error_info", "删除成功");
		} catch (Exception e) {
		    logger.error("删除失败",e);
			throw new BizException(e);
		}
		return map;
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping("deleteBatch")
	@ResponseBody
	public Map<String,Object> deleteBatch(YwMemberMessageRecord ywMemberMessageRecord){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			String[] id = ywMemberMessageRecord.getId().split(",");
			Map<String, String[]> strMap = new HashMap<String, String[]>();
			strMap.put("id",id);
			ywMemberMessageRecordService.deleteBatch(strMap);
			map.put("error_no", "0");
			map.put("error_info", "删除成功");
		} catch (Exception e) {
		    logger.error("批量删除失败",e);
			throw new BizException(e);
		}
		return map;
	}	
}
