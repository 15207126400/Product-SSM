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

import com.yunwei.product.backend.service.YwMemberMessageService;
import com.yunwei.product.common.model.YwMemberMessage;
import com.yunwei.common.user.SysUser;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.common.exception.BizException;

/**
 * 会员模板消息发送信息控制层
* @ClassName: YwMemberMessageManage 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2019年02月21日 下午14:38:56
*
 */
@Controller
@RequestMapping("/member/ywMemberMessage")
public class YwMemberMessageManage {
	private static Logger logger = Logger.getLogger(YwMemberMessageManage.class);
	@Autowired
	private YwMemberMessageService ywMemberMessageService;
 
	@RequestMapping
	public String ywMemberMessageList(Model model){

		return "/member/membermessage/ywMemberMessageList";
	}
	
	/**
	 * 查询列表集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public List<Map<String, Object>> getList(SysUser sysUser,YwMemberMessage ywMemberMessage,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.putAll(MapUtil.toMap(ywMemberMessage));
		List<YwMemberMessage> list = ywMemberMessageService.queryListPage(map,start,pageSize);
		return MapUtil.toMapList(list);
	}
	
	/**
	 * 查询分页情况
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("getPaginator")
	@ResponseBody
	public Paginator getPaginator(YwMemberMessage ywMemberMessage,@RequestParam(defaultValue = "10") int pageSize){
		int count = ywMemberMessageService.queryTotals(ywMemberMessage);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(SysUser sysUser,YwMemberMessage ywMemberMessage,String op_type,Model model){
		model.addAttribute("op_type", op_type);
		if(StringUtils.equals(op_type, "2")){
			model.addAttribute("ywMemberMessage", ywMemberMessageService.query(ywMemberMessage));
		} else {
			model.addAttribute("ywMemberMessage", ywMemberMessage);
		}
		
		return "/member/membermessage/ywMemberMessageEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(YwMemberMessage ywMemberMessage,String op_type){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			if(StringUtils.equals(op_type, "1")){
				ywMemberMessageService.insert(ywMemberMessage);
				map.put("error_no", "0");
				map.put("error_info", "添加成功");
			} else {
				ywMemberMessageService.update(ywMemberMessage);
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
	public Map<String,Object> delete(YwMemberMessage ywMemberMessage){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			ywMemberMessageService.delete(ywMemberMessage);
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
	public Map<String,Object> deleteBatch(YwMemberMessage ywMemberMessage){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			String[] id = ywMemberMessage.getId().split(",");
			Map<String, String[]> strMap = new HashMap<String, String[]>();
			strMap.put("id",id);
			ywMemberMessageService.deleteBatch(strMap);
			map.put("error_no", "0");
			map.put("error_info", "删除成功");
		} catch (Exception e) {
		    logger.info(e);
			throw new BizException(e);
		}
		return map;
	}	
}
