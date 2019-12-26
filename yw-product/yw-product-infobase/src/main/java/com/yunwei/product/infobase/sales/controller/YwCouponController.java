package com.yunwei.product.infobase.sales.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;









import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunwei.common.exception.BizException;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.NumberUtil;
import com.yunwei.common.util.SortListUtil;
import com.yunwei.product.common.model.YwCoupon;
import com.yunwei.product.common.model.YwCouponCollectiondetails;
import com.yunwei.product.common.model.YwMember;
import com.yunwei.product.common.model.YwMemberRole;
import com.yunwei.product.common.model.YwProduct;
import com.yunwei.product.common.model.YwProductClassify;
import com.yunwei.product.infobase.constant.ConstantFunctionsFront;
import com.yunwei.product.infobase.service.YwCouponCollectiondetailsService;
import com.yunwei.product.infobase.service.YwCouponService;
import com.yunwei.product.infobase.service.YwMemberRoleService;
import com.yunwei.product.infobase.service.YwMemberService;
import com.yunwei.product.infobase.service.YwProductClassifyService;
import com.yunwei.product.infobase.service.YwProductService;

/**
 * 优惠券接口
* @ClassName: YwCouponController 
* @Description: 优惠券 
* @author 晏飞
* @date 2018年1月11日 上午11:04:26 
*
 */
@Controller
public class YwCouponController {
	
	@Autowired
	private YwCouponService ywCouponService;
	@Autowired
	private YwCouponCollectiondetailsService ywCouponCollectiondetailsService;
	@Autowired
	private YwMemberService suService;
	@Autowired
	private YwProductService ywProductService;
	@Autowired
	private YwProductClassifyService ywProductClassifyService;
	@Autowired
	private YwMemberService ywMemberService;
	@Autowired
	private YwMemberRoleService ywMemberRoleService;
	
	private static Logger logger = Logger.getLogger(YwCouponController.class);
	
	/**
	 * 优惠券信息查询（根据场景的类型展示）
	 */
	@RequestMapping(ConstantFunctionsFront.YW_COUPON_00001)
    @ResponseBody
    public List<Map<String, Object>> queryCouponList(String coupon_scene_type,@Valid @NotBlank String openid){
		List<Map<String, Object>> responseMap = new ArrayList<Map<String,Object>>();
		try {
			YwCoupon ywCoupon = new YwCoupon();
			ywCoupon.setCoupon_scene_type(coupon_scene_type);
			List<YwCoupon> ywCoupons = ywCouponService.queryList(ywCoupon);
			
			// 查询优惠券领取信息
			YwCouponCollectiondetails collectiondetail = new YwCouponCollectiondetails();
			collectiondetail.setOpenid(openid);
			List<YwCouponCollectiondetails>  collectiondetailList = ywCouponCollectiondetailsService.queryList(collectiondetail);
			
			// 判断优惠券是否领取过
            if(CollectionUtils.isNotEmpty(ywCoupons)){
            	for(YwCoupon coupon : ywCoupons){
            		Map<String, Object> map = MapUtil.toMap(coupon);
        			// 获取优惠券的使用范围
            		if(StringUtils.equals(coupon.getCoupon_use_scope_type(), "0")){
            			map.put("coupon_use_scope_content", "使用范围没有限制");
            		} else if(StringUtils.equals(coupon.getCoupon_use_scope_type(), "1")){
            			YwProductClassify ywProductClassify = new YwProductClassify();
            			ywProductClassify.setClassify_id(coupon.getCoupon_use_scope());
            			ywProductClassify = ywProductClassifyService.query(ywProductClassify);
            			map.put("coupon_use_scope_content", ywProductClassify.getClassify_name() + "分类可用");
            		} else {
            			YwProduct ywProduct = ywProductService.queryById(coupon.getCoupon_use_scope());
            			map.put("coupon_use_scope_content", ywProduct.getTitle() + "商品可用");
            		}
            		
            		// 获取优惠券的领取条件
            		if(StringUtils.equals(coupon.getCoupon_collection_condition_type(), "0")){
            			map.put("coupon_collection_condition_content", "不限制");
            		} else {
            			List<YwMemberRole> ywMemberRoles = ywMemberRoleService.queryList();
            			for(YwMemberRole ywMemberRole : ywMemberRoles){
            				if(StringUtils.equals(coupon.getCoupon_collection_condition(), ywMemberRole.getId())){
                				map.put("coupon_collection_condition_content", ywMemberRole.getLevelname() + "可领");
                			}
            			}
            		}
            		
            		map.put("coupon_collection_status", 0);// 默认未领取
            		if(CollectionUtils.isNotEmpty(collectiondetailList)){
            			for(YwCouponCollectiondetails collection : collectiondetailList){
            				if(StringUtils.equals(coupon.getId(), collection.getCoupon_id())){
            					map.put("coupon_collection_status", 1);// 已领取
            				}
            			}
            		}
            		responseMap.add(map);
            	}
            }
		} catch (Exception e) {
			logger.error("优惠券信息查询失败", e);
			throw new BizException(e);
		}
		
		return responseMap;
	}
	
	/**
	 * 优惠券领取
	 */
	@RequestMapping(ConstantFunctionsFront.YW_COUPON_00002)
    @ResponseBody
    public Map<String, Object> couponCollection(@Valid @NotBlank String openid,@Valid @NotBlank String coupon_id){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 添加优惠券领取信息
			ywCouponCollectiondetailsService.couponCollection(openid,coupon_id);
		} catch (Exception e) {
			logger.error("优惠券领取失败", e);
			throw new BizException(e);
		}
		return map;
	}
	
	/**
	 * 优惠券领取信息查询
	 * @throws ParseException 
	 */
	@RequestMapping(ConstantFunctionsFront.YW_COUPON_00003)
    @ResponseBody
    public List<YwCouponCollectiondetails> queryCouponCollections(@Valid @NotBlank String openid,String details_status) throws ParseException{
		List<YwCouponCollectiondetails> collectiondetails = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("openid", openid);
			paramMap.put("details_status", details_status);
			collectiondetails = ywCouponCollectiondetailsService.queryList(paramMap);
			for (YwCouponCollectiondetails collectiondetail : collectiondetails) {
				if(StringUtils.isNotBlank(collectiondetail.getCoupon_use_scope_type())){
					if(StringUtils.equals(collectiondetail.getCoupon_use_scope_type(), "0")){
						collectiondetail.setCoupon_use_scope("使用范围没有限制");
					} else if(StringUtils.equals(collectiondetail.getCoupon_use_scope_type(), "1")){
						YwProductClassify ywProductClassify = new YwProductClassify();
            			ywProductClassify.setClassify_id(collectiondetail.getCoupon_use_scope());
            			ywProductClassify = ywProductClassifyService.query(ywProductClassify);
            			collectiondetail.setCoupon_use_scope(ywProductClassify.getClassify_name() + "分类可用");
					} else if(StringUtils.equals(collectiondetail.getCoupon_use_scope_type(), "2")){
						YwProduct ywProduct = ywProductService.queryById(collectiondetail.getCoupon_use_scope());
						collectiondetail.setCoupon_use_scope(ywProduct.getTitle() + "商品可用");
					}
				}
			}
		} catch (Exception e) {
			logger.error("优惠券领取信息查询失败", e);
			throw new BizException(e);
		}
		
		return collectiondetails;
	}
	
	/**
	 * 优惠券默认使用信息查询(优惠金额最大的优惠券)
	 */
	@RequestMapping(ConstantFunctionsFront.YW_COUPON_00004)
    @ResponseBody
    public Map<String, Object> queryCouponDefaultUse(@Valid @NotBlank String openid ,@Valid @NotBlank String order_totalprice,String product_ids){
		Map<String, Object> reponseMap = new HashMap<String, Object>();
		try {
			// 查询优惠券领取未使用信息
			YwCouponCollectiondetails ywCouponCollectiondetails = new YwCouponCollectiondetails();
			ywCouponCollectiondetails.setOpenid(openid);
			ywCouponCollectiondetails.setDetails_status("0");
			List<YwCouponCollectiondetails> collectiondetails = ywCouponCollectiondetailsService.queryList(ywCouponCollectiondetails);
			
			// 根据优惠券价格过滤
			collectiondetails = this.couponByPriceFilter(collectiondetails,order_totalprice);
			
			// 根据使用范围过滤
			collectiondetails = this.couponByUseScopeFilter(collectiondetails, product_ids);
			
			// 根据价格排序(升序排序:默认)
			SortListUtil.sort(collectiondetails, "discount_price");
			if(collectiondetails.size() > 0){
				reponseMap = MapUtil.toMap(collectiondetails.get(0));
			} 
			
		} catch (Exception e) {
			logger.error("优惠券默认使用信息查询失败", e);
			throw new BizException(e);
		}
		return reponseMap;
	}
	
	/**
	 * 通过价格筛选出可使用的优惠券
	 * @param collectiondetails
	 * @param order_totalprice
	 * @return
	 */
	private List<YwCouponCollectiondetails> couponByPriceFilter(List<YwCouponCollectiondetails> collectiondetails, String order_totalprice){
		// 筛选出可使用的优惠券
		List<YwCouponCollectiondetails> list = new ArrayList<YwCouponCollectiondetails>();
		if(CollectionUtils.isNotEmpty(collectiondetails)){
			for(YwCouponCollectiondetails collection : collectiondetails){
				// 排除免邮优惠券(免邮劵不作为默认使用类型优惠券)
				if(!StringUtils.equals(collection.getCoupon_function_type(), "4")){
					float coupon_use_condition = Float.valueOf(collection.getCoupon_use_condition()); 
					float coupon_price = Float.valueOf(collection.getCoupon_price());
					String coupon_use_condition_type = collection.getCoupon_use_condition_type();
					// 根据优惠券作用类型,分别计算出优惠后的价格(除免邮劵之外)
					if(StringUtils.equals(collection.getCoupon_function_type(), "1") || StringUtils.equals(collection.getCoupon_function_type(), "3")){
						float discount_price = Float.valueOf(order_totalprice) - coupon_price;
						collection.setDiscount_price(String.valueOf(discount_price));
					} else if(StringUtils.equals(collection.getCoupon_function_type(), "2")){
						float discount_price = Float.valueOf(order_totalprice) * (coupon_price / 10);
						collection.setDiscount_price(String.valueOf(discount_price));
					}
				    if(StringUtils.equals(coupon_use_condition_type, "0")){
				    	list.add(collection);
				    } else if(StringUtils.equals(coupon_use_condition_type, "1") && (coupon_use_condition <= Float.valueOf(order_totalprice))){
				    	list.add(collection);
				    }
				}
			}
	    }
		return list;
	}
	
	/**
	 * 通过使用范围筛选出可使用的优惠券
	 * @param collectiondetails
	 * @param product_ids
	 * @return
	 */
	private List<YwCouponCollectiondetails> couponByUseScopeFilter(List<YwCouponCollectiondetails> collectiondetails, String product_ids){
		// 筛选出可使用的优惠券
		List<YwCouponCollectiondetails> list = new ArrayList<YwCouponCollectiondetails>();
		if(CollectionUtils.isNotEmpty(collectiondetails)){
			for(YwCouponCollectiondetails collection : collectiondetails){
				// 优惠券使用范围类型
				String coupon_use_scope_type = collection.getCoupon_use_scope_type();
				String coupon_use_scope = collection.getCoupon_use_scope();
				if(StringUtils.equals(coupon_use_scope_type, "0")){
					// 全场
					list.add(collection);
				} else if(StringUtils.equals(coupon_use_scope_type, "1")){
					// 单品类
					String[] productIdArray = product_ids.split(",");
					boolean useFlag = true;// 可使用
					for(String product_id : productIdArray){
						// 查询商品信息
						YwProduct ywProduct = ywProductService.queryById(product_id);	
						if(!StringUtils.equals(ywProduct.getShopType(), coupon_use_scope)){
							useFlag = false;// 不可使用
						}
					}
					if(useFlag){
						list.add(collection);
					}
				} else if(StringUtils.equals(coupon_use_scope_type, "2")){
					// 单商品
					if(StringUtils.equals(coupon_use_scope, product_ids)){
						list.add(collection);// 可使用
					}
				}
			}
				
	    }
		return list;
	}

	/**
	 * 优惠券领取后是否满足使用条件信息查询
	 */
	@RequestMapping(ConstantFunctionsFront.YW_COUPON_00005)
    @ResponseBody
    public List<Map<String, Object>> queryCouponCollectionEnable(@Valid @NotBlank String openid ,@Valid @NotBlank String order_totalprice,String product_ids){
		List<Map<String, Object>> maps = new ArrayList<Map<String,Object>>();
		try {
			// 查询优惠券领取未使用信息
			YwCouponCollectiondetails ywCouponCollectiondetails = new YwCouponCollectiondetails();
			ywCouponCollectiondetails.setOpenid(openid);
			ywCouponCollectiondetails.setDetails_status("0");
			List<YwCouponCollectiondetails> collectiondetails = ywCouponCollectiondetailsService.queryList(ywCouponCollectiondetails);
			
			// 判断优惠券是否可以使用
			if(CollectionUtils.isNotEmpty(collectiondetails)){
				for(YwCouponCollectiondetails collection : collectiondetails){
					Map<String, Object> map = MapUtil.toMap(collection);
					map.put("coupon_use_status","1");// 可使用
					
					// 优惠券使用条件(0:不限制，限制)
					String coupon_use_condition_type = collection.getCoupon_use_condition_type();
					float coupon_use_condition = Float.valueOf(collection.getCoupon_use_condition());
					if(StringUtils.equals(coupon_use_condition_type, "1") && (coupon_use_condition > Float.valueOf(order_totalprice))){
						map.put("coupon_use_status","0");// 不可使用
					} 
					
					// 优惠券使用范围类型
					String coupon_use_scope_type = collection.getCoupon_use_scope_type();
					String coupon_use_scope = collection.getCoupon_use_scope();
					if(StringUtils.equals(coupon_use_scope_type, "1")){
						// 单品类(判断商品是否在该分类中)
						String[] productIdArray = product_ids.split(",");
						boolean useFlag = true;// 可使用
						for(String product_id : productIdArray){
							// 查询商品信息
							YwProduct ywProduct = ywProductService.queryById(product_id);
							if(ywProduct != null){
								if(StringUtils.isNotBlank(coupon_use_scope)){
									if(!StringUtils.equals(ywProduct.getShopType(), coupon_use_scope)){
										useFlag = false;// 不可使用
									}
								}
							}
						}
						
						if(!useFlag){
							map.put("coupon_use_status","0");// 不可使用
						}
						
						// 返回可使用范围信息
						YwProductClassify ywProductClassify = new YwProductClassify();
						ywProductClassify.setClassify_id(coupon_use_scope);
						ywProductClassify = ywProductClassifyService.query(ywProductClassify);
						map.put("coupon_use_scope_content", ywProductClassify.getClassify_name() + "分类可用");
						
					} else if(StringUtils.equals(coupon_use_scope_type, "2")){
						// 单商品
						if(!StringUtils.equals(coupon_use_scope, product_ids)){
							map.put("coupon_use_status","0");// 不可使用
						}
						// 返回可使用范围信息
						YwProduct ywProduct = ywProductService.queryById(coupon_use_scope);
						map.put("coupon_use_scope_content", ywProduct.getTitle() + "商品可用");
					}
					maps.add(map);
					
				}
			}
		} catch (Exception e) {
			logger.error("优惠券领取后是否满足使用条件信息查询失败", e);
			throw new BizException(e);
		}
		return maps;
	}
	
//	/**
//	 * 使用优惠券
//	 */
//	@RequestMapping(ConstantFunctionsFront.YW_COUPON_00006)
//    @ResponseBody
//    public JSONObject applyByCoupon(){
//		
//		return obj;
//	}
}
