package com.yunwei.product.backend.points.controller;

import java.util.ArrayList;
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

import com.yunwei.product.backend.service.YwMemberService;
import com.yunwei.product.backend.service.YwPointsDetailService;
import com.yunwei.product.common.backend.model.dto.YwPointsDetailDto;
import com.yunwei.product.common.model.YwMember;
import com.yunwei.product.common.model.YwPointsDetail;
import com.yunwei.common.user.SysUser;
import com.yunwei.common.util.Base32;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.common.exception.BizException;

/**
 * 积分明细控制层
* @ClassName: YwPointsDetailManage 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年07月26日 下午14:04:33
*
 */
@Controller
@RequestMapping("/points/ywPointsDetail")
public class YwPointsDetailManage {
	private static Logger logger = Logger.getLogger(YwPointsDetailManage.class);
	@Autowired
	private YwPointsDetailService ywPointsDetailService;
	@Autowired
	private YwMemberService ywMemberService;
 
	@RequestMapping
	public String ywPointsDetailList(Model model){

		return "/points/pointsdetail/ywPointsDetailList";
	}
	
	/**
	 * 查询字典集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public List<Map<String, Object>> getList(SysUser sysUser,YwPointsDetail ywPointsDetail,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> maps = new ArrayList<Map<String,Object>>();
		List<YwPointsDetailDto> ywPointsDetailDtos = null;
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
		map.putAll(MapUtil.toMap(ywPointsDetail));
		ywPointsDetailDtos = ywPointsDetailService.queryUnionMemberList(map);
		maps = MapUtil.toMapList(ywPointsDetailDtos);
		return maps;
	}
	
	/**
	 * 查询分页情况
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("getPaginator")
	@ResponseBody
	public Paginator getPaginator(YwPointsDetail ywPointsDetail,@RequestParam(defaultValue = "10") int pageSize){
		int count = ywPointsDetailService.queryTotals(ywPointsDetail);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(SysUser sysUser,YwPointsDetail ywPointsDetail,String op_type,Model model){
		model.addAttribute("op_type", op_type);
		if(StringUtils.equals(op_type, "2")){
			ywPointsDetail = ywPointsDetailService.query(ywPointsDetail);
			YwMember member = ywMemberService.queryByOpenid(ywPointsDetail.getUser_id());
			
			model.addAttribute("nickname", Base32.decode(member.getNickname()));
			model.addAttribute("ywPointsDetail", ywPointsDetail);
		} else {
			model.addAttribute("ywPointsDetail", ywPointsDetail);
		}
		
		return "/points/pointsdetail/ywPointsDetailEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(YwPointsDetail ywPointsDetail,String op_type){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			if(StringUtils.equals(op_type, "1")){
				ywPointsDetailService.insert(ywPointsDetail);
				map.put("error_no", "0");
				map.put("error_info", "添加成功");
			} else {
				ywPointsDetailService.update(ywPointsDetail);
				map.put("error_no", "0");
				map.put("error_info", "修改成功");
			}
		} catch (Exception e) {
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
	public Map<String,Object> delete(YwPointsDetail ywPointsDetail){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			ywPointsDetailService.delete(ywPointsDetail);
			map.put("error_no", "0");
			map.put("error_info", "删除成功");
		} catch (Exception e) {
			throw new BizException(e);
		}
		return map;
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping("deleteBatch")
	@ResponseBody
	public Map<String,Object> deleteBatch(YwPointsDetail ywPointsDetail){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			String[] detail_id = ywPointsDetail.getDetail_id().split(",");
			Map<String, String[]> strMap = new HashMap<String, String[]>();
			strMap.put("detail_id",detail_id);
			ywPointsDetailService.deleteBatch(strMap);
			map.put("error_no", "0");
			map.put("error_info", "删除成功");
		} catch (Exception e) {
			throw new BizException(e);
		}
		return map;
	}	
}
