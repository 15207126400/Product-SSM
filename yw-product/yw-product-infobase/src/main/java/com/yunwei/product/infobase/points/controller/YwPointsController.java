package com.yunwei.product.infobase.points.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunwei.common.exception.BizException;
import com.yunwei.common.util.Base32;
import com.yunwei.common.util.DateUtil;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.NumberUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.product.common.backend.model.dto.YwCouponDto;
import com.yunwei.product.common.infobase.model.dto.YwPointsExchangeByCouponDto;
import com.yunwei.product.common.infobase.model.dto.YwPointsExchangeByOrderItemsDto;
import com.yunwei.product.common.model.YwCoupon;
import com.yunwei.product.common.model.YwCouponCollectiondetails;
import com.yunwei.product.common.model.YwDistributor;
import com.yunwei.product.common.model.YwMember;
import com.yunwei.product.common.model.YwMemberAddress;
import com.yunwei.product.common.model.YwMemberRole;
import com.yunwei.product.common.model.YwOrder;
import com.yunwei.product.common.model.YwOrderItem;
import com.yunwei.product.common.model.YwPoints;
import com.yunwei.product.common.model.YwPointsDetail;
import com.yunwei.product.common.model.YwPointsExchange;
import com.yunwei.product.common.model.YwPointsProduct;
import com.yunwei.product.common.model.YwProduct;
import com.yunwei.product.common.model.YwProductCollection;
import com.yunwei.product.common.model.YwSignIn;
import com.yunwei.product.infobase.constant.ConstantFunctionsFront;
import com.yunwei.product.infobase.service.YwCouponCollectiondetailsService;
import com.yunwei.product.infobase.service.YwCouponService;
import com.yunwei.product.infobase.service.YwDistributorService;
import com.yunwei.product.infobase.service.YwMemberAddressService;
import com.yunwei.product.infobase.service.YwMemberRoleService;
import com.yunwei.product.infobase.service.YwMemberService;
import com.yunwei.product.infobase.service.YwOrderItemService;
import com.yunwei.product.infobase.service.YwPointsDetailService;
import com.yunwei.product.infobase.service.YwPointsExchangeService;
import com.yunwei.product.infobase.service.YwPointsProductService;
import com.yunwei.product.infobase.service.YwPointsService;
import com.yunwei.product.infobase.service.YwProductCollectionService;
import com.yunwei.product.infobase.service.YwProductService;

/**
 * 
* @ClassName: YwPointsController 
* @Description: TODO(积分) 
* @author 晏飞
* @date 2017年11月15日 下午3:31:01 
*
 */

@Controller
public class YwPointsController {
	@Autowired
	private YwMemberService ywMemberService;
	@Autowired
	private YwPointsService ywPointsService;
	@Autowired
	private YwPointsDetailService ywPointsDetailService;
	@Autowired
	private YwPointsProductService ywPointsProductService;
	@Autowired
	private YwCouponService ywCouponService;
	@Autowired
	private YwCouponCollectiondetailsService ywCouponCollectiondetailsService;
	@Autowired
	private YwPointsExchangeService ywPointsExchangeService;
	@Autowired
	private YwOrderItemService ywOrderItemService;
	
	// 日志打印
	Logger logger = Logger.getLogger(YwPointsController.class);
	
	/**
	 * 查询积分明细
	*
	*@param ywPointsDetail
	*@return
	*List<YwPointsDetail>
	 */
	@RequestMapping(ConstantFunctionsFront.YW_POINT_00001)
	@ResponseBody
	public List<Map<String, Object>> queryPointsDetail(YwPointsDetail ywPointsDetail,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int pageSize){
		List<Map<String, Object>> maps = new ArrayList<Map<String,Object>>();
		List<YwPointsDetail> ywPointsDetails = null;
		
		try {
			if (page <= 0) {
				page = 1;// 第一页
			}
			int start = (page - 1) * pageSize;
			Map<String, Object> map = MapUtil.toMap(ywPointsDetail);
			map.put("begin", start);
			map.put("end", pageSize); // 设置每页显示几条数据
			ywPointsDetails = ywPointsDetailService.queryPage(map);
		} catch (Exception e) {
			logger.error("查询积分明细list集合", e);
			throw new BizException(e);
		}
		maps = MapUtil.toMapList(ywPointsDetails);
		
		return maps;
	}
	
	/**
	 * 查询积分明细总数
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(ConstantFunctionsFront.YW_POINT_00002)
	@ResponseBody
	public Paginator getPaginator(YwPointsDetail ywPointsDetail,@RequestParam(defaultValue = "10") int pageSize){
		int count = 0;
		try {
			count = ywPointsDetailService.queryTotals(ywPointsDetail);
		} catch (Exception e) {
			logger.error("查询积分明细list总数", e);
			throw new BizException(e);
		}
		return new Paginator(1, pageSize, count);
	}	
	
	
	
	/**
	 * 查询积分商品
	*
	*@return		积分商品结果集
	 */
	@RequestMapping(ConstantFunctionsFront.YW_POINT_00004)
	@ResponseBody
	public Map<String, Object> queryByPointsProudct(YwPointsProduct ywPointsProduct){
		Map<String, Object> allMaps = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		List<Object> productList = new ArrayList<Object>();
		List<Object> couponList = new ArrayList<Object>();
		try {
			Map<String, Object> picMap = MapUtil.toMap(ywPointsProduct);
			picMap.put("db_name", "yw_images_xcxthumbnail");
			picMap.put("thumbnail_type", "2");
			
			//List<YwPointsProduct> ywPointsProductList = ywPointsProductService.queryList(ywPointsProduct);
			List<YwPointsProduct> ywPointsProductList = ywPointsProductService.queryList(picMap);
			for (YwPointsProduct pointsProduct : ywPointsProductList) {
				if(StringUtils.equals(pointsProduct.getPoints_pro_type(), "1")){					// 如果是商品类型
					productList.add(pointsProduct);
				}else if(StringUtils.equals(pointsProduct.getPoints_pro_type(), "2")){				// 如果是优惠券类型
					// 需要查出对应优惠券的类型及面额
					YwCoupon ywCoupon = new YwCoupon();
					ywCoupon.setId(pointsProduct.getCoupon_id());
					ywCoupon = ywCouponService.query(ywCoupon);
					if(ywCoupon != null){
						pointsProduct.setPoints_pro_title(ywCoupon.getCoupon_price());
						couponList.add(pointsProduct);
					}
				}
			}
			map.put("productList", productList);
			map.put("couponList", couponList);
			allMaps.put("data", map);
			
			return allMaps;
		} catch (Exception e) {
			throw new BizException(e);
		}
		
	}
	
	/**
	 * 查询积分兑换历史列表
	 */
	@RequestMapping(ConstantFunctionsFront.YW_POINT_00005)
	@ResponseBody
	public Map<String, Object> queryPointsExchange(YwPointsExchange wPointsExchange){
		Map<String, Object> map = new HashMap<String, Object>();
		List<YwPointsExchangeByOrderItemsDto> orderItemList = new ArrayList<YwPointsExchangeByOrderItemsDto>();
		List<YwPointsExchangeByCouponDto> couponList = new ArrayList<YwPointsExchangeByCouponDto>();
		try {
			List<YwPointsExchange> wPointsExchangeList = ywPointsExchangeService.queryList(wPointsExchange);
			for (YwPointsExchange ywPointsExchange : wPointsExchangeList) {
				// 如果类型为1 , 表示兑换的商品 , 需要查询关联的订单项表
				if(StringUtils.equals(ywPointsExchange.getExchange_type(), "1")){
					Map<String, Object> orderItemMap = new HashMap<String, Object>();
					orderItemMap.put("exchange_id", ywPointsExchange.getExchange_id());
					YwPointsExchangeByOrderItemsDto orderItem = ywPointsExchangeService.queryUnionByOrderitems(orderItemMap);
					orderItemList.add(orderItem);
				}else{
					Map<String, Object> couponMap = new HashMap<String, Object>();
					couponMap.put("exchange_id", ywPointsExchange.getExchange_id());
					YwPointsExchangeByCouponDto coupon = ywPointsExchangeService.queryUnionByCoupon(couponMap);
					couponList.add(coupon);
				}
			}
			map.put("orderItemList", orderItemList);
			map.put("couponList", couponList);
			
			return map;
		} catch (Exception e) {
			throw new BizException(e);
		}
		
	}
	
	/**
	 * 兑换优惠券
	*
	*@param openid
	*@param coupon_id
	*@param points
	*@return
	*Map<String,Object>
	 */
	@RequestMapping(ConstantFunctionsFront.YW_POINT_00007)
	@ResponseBody
	public Map<String, Object> saveChangeCoupon(String openid , String coupon_id , String points){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 扣除用户账户积分
			int num = ywPointsService.deductPointsByUser(points, openid);
			if(num > 0){
				// 插入优惠券到指定账户
				ywCouponCollectiondetailsService.couponCollection(openid,coupon_id);
				// 生成积分明细
				ywPointsDetailService.addPointsDetail("2", openid, "-" + points, "");
				// 生成积分兑换记录
				ywPointsExchangeService.addPointsExchange("2", openid, points, "", coupon_id, "1");
			}
		} catch (Exception e) {
			throw new BizException(e);
		}
		
		return map;
	}

}
