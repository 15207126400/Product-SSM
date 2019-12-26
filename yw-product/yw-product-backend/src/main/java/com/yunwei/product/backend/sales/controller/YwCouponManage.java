package com.yunwei.product.backend.sales.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunwei.product.backend.service.YwCouponService;
import com.yunwei.product.backend.service.YwMemberRoleService;
import com.yunwei.product.backend.service.YwProductClassifyService;
import com.yunwei.product.backend.service.YwProductService;
import com.yunwei.product.common.model.YwCoupon;
import com.yunwei.product.common.model.YwProduct;
import com.yunwei.product.common.model.YwProductClassify;
import com.yunwei.common.user.SysUser;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.common.exception.BizException;

/**
 * 优惠券配置信息控制层
* @ClassName: YwCouponManage 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2019年01月03日 下午14:56:40
*
 */
@Controller
@RequestMapping("/sales/ywCoupon")
public class YwCouponManage {
	private static Logger logger = Logger.getLogger(YwCouponManage.class);
	@Autowired
	private YwCouponService ywCouponService;
	@Autowired
	private YwMemberRoleService ywMemberRoleService;
	@Autowired
	private YwProductClassifyService ywProductClassifyService;
	@Autowired
	private YwProductService ywProductService;
 
	@RequestMapping
	public String ywCouponList(Model model){
		

		return "/sales/coupon/ywCouponList";
	}
	
	/**
	 * 查询列表集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public List<Map<String, Object>> getList(SysUser sysUser,YwCoupon ywCoupon,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
		map.putAll(MapUtil.toMap(ywCoupon));
		List<YwCoupon> list = ywCouponService.queryPage(map);
		return MapUtil.toMapList(list);
	}
	
	/**
	 * 查询分页情况
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("getPaginator")
	@ResponseBody
	public Paginator getPaginator(YwCoupon ywCoupon,@RequestParam(defaultValue = "10") int pageSize){
		int count = ywCouponService.queryTotals(ywCoupon);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(SysUser sysUser,YwCoupon ywCoupon,String op_type,Model model){
		model.addAttribute("op_type", op_type);
		// 查询会员等级
	    model.addAttribute("ywMemberRoles", ywMemberRoleService.queryList());
		if(StringUtils.equals(op_type, "2")){
			ywCoupon = ywCouponService.query(ywCoupon);
			model.addAttribute("ywCoupon",ywCoupon);
			// 判断是否查询商品分类还是查询商品
			if(StringUtils.equals(ywCoupon.getCoupon_use_scope_type(), "1")){
				// 查询商品分类(目前由于商品分类在一张表里存在上下级关系,需查找商品直接所属的分类编号)
				//model.addAttribute("ywProductClassifys", ywProductClassifyService.queryList());
				List<YwProduct> ywProduct = ywProductService.queryAll(null);
				Set<String> product_ids = new HashSet<String>();
				List<YwProductClassify> ywProductClassifys = new ArrayList<YwProductClassify>();
				for (YwProduct product : ywProduct) {
					product_ids.add(product.getShopType());
				}
				if(product_ids.size() > 0){
					for (String product_id : product_ids) {
						YwProductClassify ywProductClassify = new YwProductClassify();
						ywProductClassify.setClassify_id(product_id);
						ywProductClassifys.add(ywProductClassifyService.query(ywProductClassify));
					}
				}
				model.addAttribute("ywProductClassifys", ywProductClassifys);
			} else if(StringUtils.equals(ywCoupon.getCoupon_use_scope_type(), "2")){
				// 查询商品
				model.addAttribute("ywProducts", ywProductService.queryList());
			}
		} else {
			model.addAttribute("ywCoupon", ywCoupon);
		}
		
		return "/sales/coupon/ywCouponEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(YwCoupon ywCoupon,String op_type){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			if(StringUtils.equals(op_type, "1")){
				ywCouponService.insert(ywCoupon);
				map.put("error_no", "0");
				map.put("error_info", "添加成功");
			} else {
				ywCouponService.update(ywCoupon);
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
	public Map<String,Object> delete(YwCoupon ywCoupon){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			ywCouponService.delete(ywCoupon);
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
	public Map<String,Object> deleteBatch(YwCoupon ywCoupon){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			String[] id = ywCoupon.getId().split(",");
			Map<String, String[]> strMap = new HashMap<String, String[]>();
			strMap.put("id",id);
			ywCouponService.deleteBatch(strMap);
			map.put("error_no", "0");
			map.put("error_info", "删除成功");
		} catch (Exception e) {
		    logger.info(e);
			throw new BizException(e);
		}
		return map;
	}	
}
