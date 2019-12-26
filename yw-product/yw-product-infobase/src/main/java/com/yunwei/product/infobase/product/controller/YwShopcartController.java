package com.yunwei.product.infobase.product.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yunwei.common.exception.BizException;
import com.yunwei.product.common.model.YwProduct;
import com.yunwei.product.common.model.YwShopcart;
import com.yunwei.product.infobase.constant.ConstantFunctionsFront;
import com.yunwei.product.infobase.constant.ContantErrorNo;
import com.yunwei.product.infobase.service.YwMemberService;
import com.yunwei.product.infobase.service.YwProductService;
import com.yunwei.product.infobase.service.YwShopcartService;
/**
 * 
* @ClassName: YwShopcartController 
* @Description: TODO(购物车管理) 
* @author 晏飞
* @date 2017年11月18日 下午5:02:24 
*
 */
@Controller
public class YwShopcartController {
	@Autowired
	private YwShopcartService ywShopcartService;
	@Autowired
	private YwMemberService YwMemberService;
	@Autowired
	private YwProductService ywProductService;
	@Autowired
	private HttpServletRequest request;
	
	/**
	 * 新增购物车
	*
	*@param request
	*@param response
	*@return
	*JSONObject
	 */
	@RequestMapping(ConstantFunctionsFront.YW_POT_00013)
    @ResponseBody
    public Map<String, Object> addPaycat(YwShopcart ywShopcart){
		
		Map<String, Object> map = new HashMap<String, Object>();
		int num = 0;
		Map<String, Object> reqmap = new HashMap<String, Object>();
		reqmap.put("createBy", ywShopcart.getCreateBy());
		List<YwShopcart> shopcartList = ywShopcartService.queryAllPaycat(reqmap);
		for (YwShopcart ywShopcart2 : shopcartList) {
			if(StringUtils.equals(ywShopcart.getSku_id(), ywShopcart2.getSku_id())){
				num ++ ;
				break;
			}
		}
		if(num > 0){
			map.put("code", 002);
			map.put("msg", "请勿重复添加");
		}else{
			//获取客户端传递过来的数据
			ywShopcartService.addPaycat(ywShopcart);
			map.put("code", 001);
			map.put("msg", "添加成功");
		}
		
		return map;
	}
	
	/**
	 * 查询购物车
	*
	*@param request
	*@param response
	*@return
	*JSONObject
	 */
	@RequestMapping(ConstantFunctionsFront.YW_POT_00018)
    @ResponseBody
    public Map<String, Object> queryAllPaycat(String openid , String db_name){
		Map<String, Object> map = new HashMap<String, Object>();
		List<YwShopcart> list = new ArrayList<YwShopcart>();
			Map<String, Object> opmap = new HashMap<String, Object>();
			opmap.put("createBy", openid);
			// 兼容超市小程序,关联查询机构sku表中数据,若未传入表名,则关联查询原商品sku表
			if(StringUtils.isNotBlank(db_name)){
				opmap.put("db_name", "yw_branch_product_sku");
				List<YwShopcart> ywShopcarts = ywShopcartService.queryAllPaycat(opmap);
				for (YwShopcart ywShopcart : ywShopcarts) {
					if(StringUtils.isNotBlank(ywShopcart.getSku_stock()) && Integer.parseInt(ywShopcart.getSku_stock()) > 0){
						// 通过机构商品的商品编号查找原商品信息
						YwProduct ywProduct = ywProductService.queryById(ywShopcart.getShopid());
						ywShopcart.setUrl(ywProduct.getProduct_url());
						list.add(ywShopcart);
					} else {
						Map<String, Object> paramMap = new HashMap<String, Object>();
						paramMap.put("id", ywShopcart.getId());
						ywShopcartService.deletePaycat(paramMap);
					}
				}
				if(list.size() > 0){
					map.put("list", list);
					boolean isHave = true;
					map.put("isHave", isHave);
				}else{
					boolean isHave = false;
					map.put("isHave", isHave);
				}
			} else {
				List<YwShopcart> ywShopcarts = ywShopcartService.queryAllPaycat(opmap);
				for (YwShopcart ywShopcart : ywShopcarts) {
					if(StringUtils.isNotBlank(ywShopcart.getSku_stock()) && Integer.parseInt(ywShopcart.getSku_stock()) > 0){
						YwProduct ywProduct = ywProductService.queryById(ywShopcart.getShopid());
						ywShopcart.setUrl(ywProduct.getProduct_url());
						list.add(ywShopcart);
					} else {
						Map<String, Object> paramMap = new HashMap<String, Object>();
						paramMap.put("id", ywShopcart.getId());
						ywShopcartService.deletePaycat(paramMap);
					}
				}
				if(list.size() > 0){
					map.put("list", list);
					boolean isHave = true;
					map.put("isHave", isHave);
				}else{
					boolean isHave = false;
					map.put("isHave", isHave);
				}
			}
			return map;
		}
	
	/**
	 * 删除购物车数据
	*
	*@param request
	*@param response
	*@return
	*JSONObject
	 */
	@RequestMapping(ConstantFunctionsFront.YW_POT_00019)
    @ResponseBody
    public Map<String, Object> deletePaycat(int id){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("id", id);
			ywShopcartService.deletePaycat(map);
		} catch (Exception e) {
			throw new BizException("删除失败");
		}
		
		return map;
	}
	
}	 

