package com.yunwei.product.backend.sales.controller;

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

import com.yunwei.product.backend.service.YwCouponCollectiondetailsService;
import com.yunwei.product.backend.service.YwMemberService;
import com.yunwei.product.common.model.YwCouponCollectiondetails;
import com.yunwei.product.common.model.YwMember;
import com.yunwei.common.user.SysUser;
import com.yunwei.common.util.Base32;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.common.exception.BizException;

/**
 * 优惠券领取明细控制层
* @ClassName: YwCouponCollectiondetailsManage 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2019年01月03日 下午14:47:10
*
 */
@Controller
@RequestMapping("/sales/ywCouponCollectiondetails")
public class YwCouponCollectiondetailsManage {
	private static Logger logger = Logger.getLogger(YwCouponCollectiondetailsManage.class);
	@Autowired
	private YwCouponCollectiondetailsService ywCouponCollectiondetailsService;
	@Autowired
	private YwMemberService ywMemberService;
 
	@RequestMapping
	public String ywCouponCollectiondetailsList(Model model){

		return "/sales/couponcollectiondetails/ywCouponCollectiondetailsList";
	}
	
	/**
	 * 查询列表集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public List<Map<String, Object>> getList(SysUser sysUser,YwCouponCollectiondetails ywCouponCollectiondetails,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
		map.putAll(MapUtil.toMap(ywCouponCollectiondetails));
		List<YwCouponCollectiondetails> list = ywCouponCollectiondetailsService.queryPage(map);
		for (YwCouponCollectiondetails ywCouponCollectiondetail : list) {
			YwMember ywMember = ywMemberService.queryByOpenid(ywCouponCollectiondetail.getOpenid());
			ywCouponCollectiondetail.setOpenid(Base32.decode(ywMember.getNickname()));
		}
		
		return MapUtil.toMapList(list);
	}
	
	/**
	 * 查询分页情况
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("getPaginator")
	@ResponseBody
	public Paginator getPaginator(YwCouponCollectiondetails ywCouponCollectiondetails,@RequestParam(defaultValue = "10") int pageSize){
		int count = ywCouponCollectiondetailsService.queryTotals(ywCouponCollectiondetails);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(SysUser sysUser,YwCouponCollectiondetails ywCouponCollectiondetails,String op_type,Model model){
		model.addAttribute("op_type", op_type);
		if(StringUtils.equals(op_type, "2")){
			model.addAttribute("ywCouponCollectiondetails", ywCouponCollectiondetailsService.query(ywCouponCollectiondetails));
		} else {
			model.addAttribute("ywCouponCollectiondetails", ywCouponCollectiondetails);
		}
		
		return "/sales/couponcollectiondetails/ywCouponCollectiondetailsEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(YwCouponCollectiondetails ywCouponCollectiondetails,String op_type){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			if(StringUtils.equals(op_type, "1")){
				ywCouponCollectiondetailsService.insert(ywCouponCollectiondetails);
				map.put("error_no", "0");
				map.put("error_info", "添加成功");
			} else {
				ywCouponCollectiondetailsService.update(ywCouponCollectiondetails);
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
	public Map<String,Object> delete(YwCouponCollectiondetails ywCouponCollectiondetails){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			ywCouponCollectiondetailsService.delete(ywCouponCollectiondetails);
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
	public Map<String,Object> deleteBatch(YwCouponCollectiondetails ywCouponCollectiondetails){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			String[] id = ywCouponCollectiondetails.getId().split(",");
			Map<String, String[]> strMap = new HashMap<String, String[]>();
			strMap.put("id",id);
			ywCouponCollectiondetailsService.deleteBatch(strMap);
			map.put("error_no", "0");
			map.put("error_info", "删除成功");
		} catch (Exception e) {
		    logger.info(e);
			throw new BizException(e);
		}
		return map;
	}	
}
