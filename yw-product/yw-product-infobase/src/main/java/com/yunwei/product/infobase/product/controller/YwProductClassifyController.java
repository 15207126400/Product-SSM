package com.yunwei.product.infobase.product.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunwei.common.exception.BizException;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.product.common.model.YwProduct;
import com.yunwei.product.common.model.YwProductClassify;
import com.yunwei.product.infobase.constant.ConstantFunctionsFront;
import com.yunwei.product.infobase.service.YwCouponCollectiondetailsService;
import com.yunwei.product.infobase.service.YwCouponService;
import com.yunwei.product.infobase.service.YwImagesService;
import com.yunwei.product.infobase.service.YwMemberRoleService;
import com.yunwei.product.infobase.service.YwMemberService;
import com.yunwei.product.infobase.service.YwProductClassifyService;
import com.yunwei.product.infobase.service.YwProductCollectionService;
import com.yunwei.product.infobase.service.YwProductService;
import com.yunwei.product.infobase.service.YwProductSkuService;
/**
 * 
* @ClassName: YwProductController 
* @Description: TODO(商品详情 --- 收藏状态 --- 分类展示) 
* @author 晏飞
* @date 2017年11月18日 下午5:00:32 
*
 */
@Controller
public class YwProductClassifyController {
	@Autowired
	private YwProductService ywProductService;		//商品详情接口
	@Autowired
	private YwProductClassifyService ywProductClassifyService;		//商品分类接口
	@Autowired
	private YwProductCollectionService ywProductCollectionService;		//商品收藏接口
	@Autowired
	private YwMemberService suService;			//会员接口
	@Autowired
	private YwMemberRoleService surService;		//会员权限接口
	@Autowired
	private YwCouponService scfService;			//发放优惠券接口
	@Autowired
	private YwCouponCollectiondetailsService sclService;			//领取优惠券接口
	@Autowired
	private YwProductSkuService ywProductSkuService;	//sku
	@Autowired
	private YwImagesService ywImagesService;
	
	
	
	 /**
	  * 分类列表查询
	  */
	 @RequestMapping(ConstantFunctionsFront.YW_CLA_00600)
	 @ResponseBody
	 public Map<String, Object> queryAllYwProductClassify(){
		 Map<String, Object> map = new HashMap<String, Object>();
		 List<YwProductClassify> levelOneClassify = new ArrayList<YwProductClassify>();
		 List<YwProductClassify> levelTwoClassify = new ArrayList<YwProductClassify>();
		 YwProductClassify classify = new YwProductClassify();
		 classify.setClassify_status("1");
		 List<YwProductClassify> ywProductClassify = ywProductClassifyService.queryList(classify);
		 if(CollectionUtils.isNotEmpty(ywProductClassify)){		
			 for (YwProductClassify item : ywProductClassify) {
				 if(StringUtils.equals("1", item.getClassify_level())){
					 levelOneClassify.add(item);
				 }else if(StringUtils.equals("2", item.getClassify_level())){
					 levelTwoClassify.add(item);
				 }
			}
		 }
		 
		 // 如果有2级菜单 , 返回2级分类菜单数据
		 if(CollectionUtils.isNotEmpty(levelTwoClassify)){
			 map.put("levelTwoClassify", levelTwoClassify);
		 }
		 map.put("levelOneClassify", levelOneClassify);
		 
		 return map;
	  }
	 
	 /**
		 * 查询该分类是否有二级菜单
		 * 
		 * @param pageSize
		 * @return
		 */
		@RequestMapping(ConstantFunctionsFront.YW_CLA_00602)
		@ResponseBody
		public boolean queryByClassifyParent(String classify_id) {
			boolean isHaveP = false;
			YwProductClassify ywProductClassify = new YwProductClassify();
			ywProductClassify.setClassify_status("1");
			ywProductClassify.setClassify_level("2");
			ywProductClassify.setParent_id(classify_id);
			List<YwProductClassify> ywProductClassifys = ywProductClassifyService.queryList(ywProductClassify);
			
			if(CollectionUtils.isNotEmpty(ywProductClassifys)){
				isHaveP = true;
			}
			return isHaveP;
		}
	 
		/**
		 * 分类二级菜单查询
		 */
		@RequestMapping(ConstantFunctionsFront.YW_CLA_00604)
		@ResponseBody
		public List<Map<String, Object>> queryClassifyByLevel(YwProductClassify ywProductClassify,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int pageSize) {
			List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
			List<YwProductClassify> ywProductClassifys = null;
			ywProductClassify.setClassify_status("1");
			ywProductClassify.setClassify_level("2");
			try {
				if (page <= 0) {
					page = 1;// 第一页
				}
				int start = (page - 1) * pageSize;
				Map<String, Object> map = MapUtil.toMap(ywProductClassify);
				map.put("begin", start);
				map.put("end", pageSize); // 设置每页显示几条数据
				//ywProducts = ywProductService.queryPage(map);
				ywProductClassifys = ywProductClassifyService.queryPage(map);
			} catch (Exception e) {
				throw new BizException(e);
			}
			maps = MapUtil.toMapList(ywProductClassifys);
			return maps;
		}

		/**
		 * 查询特定状态的分类二级菜单分页信息查询
		 * 
		 * @param pageSize
		 * @return
		 */
		@RequestMapping(ConstantFunctionsFront.YW_CLA_00605)
		@ResponseBody
		public Paginator getPaginatorByLevel(YwProductClassify ywProductClassify,@RequestParam(defaultValue = "10") int pageSize) {
			ywProductClassify.setClassify_status("1");
			ywProductClassify.setClassify_level("2");
			int count = ywProductClassifyService.queryTotals(ywProductClassify);
			return new Paginator(1, pageSize, count);
		}

	/**
	 * 分类商品查询
	 */
	@RequestMapping(ConstantFunctionsFront.YW_CLA_00601)
	@ResponseBody
	public List<Map<String, Object>> queryPaymentByType(YwProduct ywProduct,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int pageSize) {
		List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
		List<YwProduct> ywProducts = null;
		ywProduct.setStatus("1");
		try {
			if (page <= 0) {
				page = 1;// 第一页
			}
			int start = (page - 1) * pageSize;
			Map<String, Object> map = MapUtil.toMap(ywProduct);
			map.put("thumbnail_type", "3");
			map.put("begin", start);
			map.put("end", pageSize); // 设置每页显示几条数据
			//ywProducts = ywProductService.queryPage(map);
			ywProducts = ywProductService.queryPage(map);
		} catch (Exception e) {
			throw new BizException(e);
		}
		maps = MapUtil.toMapList(ywProducts);
		return maps;
	}

	/**
	 * 查询特定状态的分类分页信息查询
	 * 
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(ConstantFunctionsFront.YW_CLA_00603)
	@ResponseBody
	public Paginator getPaginator(YwProduct ywProduct,@RequestParam(defaultValue = "10") int pageSize) {
		ywProduct.setStatus("1");
		int count = ywProductService.queryTotals(ywProduct);
		return new Paginator(1, pageSize, count);
	}

}

