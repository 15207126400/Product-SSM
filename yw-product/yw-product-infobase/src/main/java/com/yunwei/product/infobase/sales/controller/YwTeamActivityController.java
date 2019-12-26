package com.yunwei.product.infobase.sales.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunwei.common.exception.BizException;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.context.service.PictureService;
import com.yunwei.product.common.model.YwTeamActivity;
import com.yunwei.product.common.model.YwTeamFollow;
import com.yunwei.product.common.model.YwTeamFound;
import com.yunwei.product.infobase.constant.ConstantFunctionsFront;
import com.yunwei.product.infobase.service.YwProductService;
import com.yunwei.product.infobase.service.YwTeamActivityService;
import com.yunwei.product.infobase.service.YwTeamFollowService;
import com.yunwei.product.infobase.service.YwTeamFoundService;

/**
 * 系统字典控制器
 * @author zhangz
 *
 */
@Controller
public class YwTeamActivityController {
	
	@Autowired
	private YwTeamActivityService ywTeamActivityService;
	@Autowired
	private YwProductService ywProductService;
	@Autowired  
    private PictureService pictureService;
	@Autowired
	private YwTeamFoundService ywTeamFoundService;
	@Autowired
	private YwTeamFollowService ywTeamFollowService;
	
	/**
	 * 团购活动商品列表信息查询
	 */
	@RequestMapping(ConstantFunctionsFront.YW_TEAM_00200)
	@ResponseBody
	//@ResponseBodyFilter
	public List<Map<String, Object>> queryTeamProducts(YwTeamActivity ywTeamActivity,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int pageSize){
		List<Map<String, Object>> maps = new ArrayList<Map<String,Object>>();
		List<YwTeamActivity> teamActivitys = null;
		try {
			if (page <= 0) {
				page = 1;// 第一页
			}
			int start = (page - 1) * pageSize;
			Map<String, Object> map = MapUtil.toMap(ywTeamActivity);
			map.put("status", "2");
			map.put("begin", start);
			map.put("end", pageSize); // 设置每页显示几条数据
			teamActivitys = ywTeamActivityService.queryUnionProductList(map);
		} catch (Exception e) {
			throw new BizException(e);
		}
		maps = MapUtil.toMapList(teamActivitys);
		return maps;
	}
	
	/**
	 * 团购活动商品列表分页信息查询
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(ConstantFunctionsFront.YW_TEAM_00206)
	@ResponseBody
	public Paginator getPaginator(YwTeamActivity ywTeamActivity,@RequestParam(defaultValue = "10") int pageSize){
		int count = ywTeamActivityService.queryTotals(ywTeamActivity);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 添加开团信息
	 */
	@RequestMapping(ConstantFunctionsFront.YW_TEAM_00201)
	@ResponseBody
	//@ResponseBodyFilter
	public Map<String, Object> addTeamFound(YwTeamFound ywTeamFound){
		Map<String, Object> maps = new HashMap<String,Object>();
		try {
			// 查询开团信息是否存在
			YwTeamFound found = ywTeamFoundService.query(ywTeamFound);
			if(found == null){
				ywTeamFoundService.insert(ywTeamFound);
			} else {
				throw new BizException("201", "开团信息已经存在");
			}
			maps = MapUtil.toMap(ywTeamFound);
		} catch (Exception e) {
			throw new BizException(e);
		}
		
		return maps;
	}
	
	/**
	 * 修改开团信息
	 */
	@RequestMapping(ConstantFunctionsFront.YW_TEAM_00202)
	@ResponseBody
	//@ResponseBodyFilter
	public Map<String, Object> updateTeamFound(YwTeamFound ywTeamFound){
		Map<String, Object> maps = new HashMap<String,Object>();
		try {
			// 查询开团信息是否存在
			YwTeamFound found = ywTeamFoundService.query(ywTeamFound);
			if(found != null){
				ywTeamFoundService.update(ywTeamFound);
			} else {
				throw new BizException("202", "开团信息不存在");
			}
		} catch (Exception e) {
			throw new BizException(e);
		}
		return maps;
	}
	
	/**
	 * 添加参团信息
	 */
	@RequestMapping(ConstantFunctionsFront.YW_TEAM_00203)
	@ResponseBody
	//@ResponseBodyFilter
	public Map<String, Object> addTeamFollow(YwTeamFollow ywTeamFollow){
		Map<String, Object> maps = new HashMap<String,Object>();
		try {
			// 查询参团信息是否存在
			YwTeamFollow follow = ywTeamFollowService.query(ywTeamFollow);
			if(follow == null){
				ywTeamFollowService.update(ywTeamFollow);
			} else {
				throw new BizException("203", "参团信息已经存在");
			}
		} catch (Exception e) {
			throw new BizException(e);
		}
		return maps;
	}
	
	/**
	 * 修改参团信息
	 */
	@RequestMapping(ConstantFunctionsFront.YW_TEAM_00204)
	@ResponseBody
	//@ResponseBodyFilter
	public Map<String, Object> updateTeamFollow(YwTeamFollow ywTeamFollow){
		Map<String, Object> maps = new HashMap<String,Object>();
		try {
			// 查询参团信息是否存在
			YwTeamFollow teamFollow = new YwTeamFollow();
			teamFollow.setFollow_id(ywTeamFollow.getFollow_id());
			YwTeamFollow follow = ywTeamFollowService.query(teamFollow);
			if(follow != null){
				ywTeamFollowService.update(ywTeamFollow);
			} else {
				throw new BizException("204", "参团信息不存在");
			}
		} catch (Exception e) {
			throw new BizException(e);
		}
		return maps;
	}
	
	/**
	 * 开团列表信息查询
	 */
	@RequestMapping(ConstantFunctionsFront.YW_TEAM_00205)
	@ResponseBody
	//@ResponseBodyFilter
	public List<Map<String, Object>> queryTeamFounds(YwTeamFound ywTeamFound){
		List<Map<String, Object>> maps = new ArrayList<Map<String,Object>>();
		List<YwTeamFound> ywTeamFounds = null;
		try {
			ywTeamFounds = ywTeamFoundService.queryList(ywTeamFound);
		} catch (Exception e) {
			throw new BizException(e);
		}
		maps = MapUtil.toMapList(ywTeamFounds);
		return maps;
	}
	
	/**
	 * 判断开团是否成功
	 */
	@RequestMapping(ConstantFunctionsFront.YW_TEAM_00207)
	@ResponseBody
	//@ResponseBodyFilter
	public Map<String, Object> teamFoundIsSuccess(YwTeamFound ywTeamFound,String pay_flag){
		Map<String, Object> map = new HashMap<String,Object>();
		boolean isSuccess = false;
		try {
			isSuccess = ywTeamFoundService.isSuccess(ywTeamFound,pay_flag);
		} catch (Exception e) {
			throw new BizException(e);
		}
		map.put("isSuccess", isSuccess);
		return map;
	}
	
	/**
	 * 参团列表信息查询
	 */
	@RequestMapping(ConstantFunctionsFront.YW_TEAM_00208)
	@ResponseBody
	//@ResponseBodyFilter
	public List<Map<String, Object>> queryTeamFollows(YwTeamFollow ywTeamFollow){
		List<Map<String, Object>> maps = new ArrayList<Map<String,Object>>();
		List<YwTeamFollow> ywTeamFollows = null;
		List<YwTeamFollow> ywTeamFollows2 = new ArrayList<YwTeamFollow>();
		try {
			ywTeamFollows = ywTeamFollowService.queryList(ywTeamFollow);
			if(ywTeamFollows.size() > 0){
				for(YwTeamFollow teamFollow :ywTeamFollows){
					if(!StringUtils.equals(teamFollow.getStatus(), "0")){
						ywTeamFollows2.add(teamFollow);
					}
				}
			}
		} catch (Exception e) {
			throw new BizException(e);
		}
		maps = MapUtil.toMapList(ywTeamFollows2);
		return maps;
	}
	
	/**
	 * 参团信息查询
	 */
	@RequestMapping(ConstantFunctionsFront.YW_TEAM_00209)
	@ResponseBody
	//@ResponseBodyFilter
	public Map<String, Object> queryTeamFollow(YwTeamFollow ywTeamFollow){
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			ywTeamFollow = ywTeamFollowService.query(ywTeamFollow);
		} catch (Exception e) {
			throw new BizException(e);
		}
		map = MapUtil.toMap(ywTeamFollow);
		return map;
	}
	
	/**
	 * 开团信息查询
	 */
	@RequestMapping(ConstantFunctionsFront.YW_TEAM_00210)
	@ResponseBody
	//@ResponseBodyFilter
	public Map<String, Object> queryTeamFound(YwTeamFound ywTeamFound){
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			ywTeamFound = ywTeamFoundService.query(ywTeamFound);
		} catch (Exception e) {
			throw new BizException(e);
		}
		map = MapUtil.toMap(ywTeamFound);
		return map;
	}
	
	/**
	 * 团购活动信息查询
	 */
	@RequestMapping(ConstantFunctionsFront.YW_TEAM_00211)
	@ResponseBody
	//@ResponseBodyFilter
	public Map<String, Object> queryTeamActivity(YwTeamActivity ywTeamActivity){
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			ywTeamActivity= ywTeamActivityService.query(ywTeamActivity);
		} catch (Exception e) {
			throw new BizException(e);
		}
		map = MapUtil.toMap(ywTeamActivity);
		return map;
	}
}
