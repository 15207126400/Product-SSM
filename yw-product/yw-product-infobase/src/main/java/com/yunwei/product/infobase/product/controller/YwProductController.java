package com.yunwei.product.infobase.product.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
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
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.IOUtils;
import com.yunwei.common.exception.BizException;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.NumberUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.context.service.PictureService;
import com.yunwei.context.sys.service.YwQrcodeService;
import com.yunwei.product.common.model.YwImages;
import com.yunwei.product.common.model.YwProduct;
import com.yunwei.product.common.model.YwProductCollection;
import com.yunwei.product.common.model.YwProductImage;
import com.yunwei.product.common.model.YwProductSku;
import com.yunwei.product.infobase.constant.ConstantFunctionsFront;
import com.yunwei.product.infobase.service.YwCouponCollectiondetailsService;
import com.yunwei.product.infobase.service.YwCouponService;
import com.yunwei.product.infobase.service.YwImagesService;
import com.yunwei.product.infobase.service.YwMemberRoleService;
import com.yunwei.product.infobase.service.YwMemberService;
import com.yunwei.product.infobase.service.YwProductClassifyService;
import com.yunwei.product.infobase.service.YwProductCollectionService;
import com.yunwei.product.infobase.service.YwProductImageService;
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
public class YwProductController {
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
	@Autowired
	private PictureService pictureService;
	@Autowired
	private YwProductImageService ywProductImageService;
	@Autowired
	private YwQrcodeService ywQrcodeService;
	
	/**
	 * 图片链接前缀
	 */
	private static final String FRONTPATH = "https://xcx.whywxx.com/yongyou/image/home/detail";
	
	/**
	 * 根据类型查询商品列表
	*
	*@param request
	*@param response
	*@return
	*JSONObject
	 */
	@RequestMapping(ConstantFunctionsFront.YW_POT_00050)
    @ResponseBody
    public List<Map<String, Object>> queryYwProduct(YwProduct ywProduct,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int pageSize){
		List<Map<String, Object>> maps = new ArrayList<Map<String,Object>>();
		List<YwProduct> ywProducts = null;
		
		
		try {
			if (page <= 0) {
				page = 1;// 第一页
			}
			int start = (page - 1) * pageSize;
			Map<String, Object> map = new HashMap<String, Object>();
			//map.put("market_code", ywProduct.getMarket_code());
			//map.put("branch_id", ywProduct.getBranch_id());
			//map.put("status", "1");
			ywProduct.setStatus("1");
			map = MapUtil.toMap(ywProduct);
			map.put("thumbnail_type", "1");
			map.put("begin", start);
			map.put("end", pageSize); // 设置每页显示几条数据
			ywProducts = ywProductService.queryPage(map);
			for(YwProduct item : ywProducts){
				if(StringUtils.isBlank(item.getUrl()) || NumberUtil.isNumber(item.getUrl())){
					item.setUrl(item.getProduct_url());
				}
			}
		} catch (Exception e) {
			throw new BizException(e);
		}
		
		maps = MapUtil.toMapList(ywProducts);
		return maps;
	}
	
	/**
	 * 查询商品总数
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(ConstantFunctionsFront.YW_POT_00051)
	@ResponseBody
	public Paginator getPaginator(YwProduct ywProduct,@RequestParam(defaultValue = "10") int pageSize){
		
		int count = ywProductService.queryTotals(ywProduct);
		return new Paginator(1, pageSize, count);
	}	
	
	/**
	 * 首页新品展示
	*
	*@param request
	*@param response
	*@return
	*JSONObject
	 */
	@RequestMapping(ConstantFunctionsFront.YW_POT_00001)
    @ResponseBody
    public List<YwProduct> ywProduct(String market_code , String branch_id){
		Map<String, Object> typemap = new HashMap<String, Object>();
		typemap.put("market_code", market_code);
		typemap.put("branch_id", branch_id);
		typemap.put("status", "1");
		typemap.put("thumbnail_type", "1");
		List<YwProduct> newYwProductList = new ArrayList<YwProduct>();
		List<YwProduct> ywProductList = ywProductService.queryPage(typemap);
		int num = 0;
		for(YwProduct item : ywProductList){
			if(StringUtils.isBlank(item.getUrl()) || NumberUtil.isNumber(item.getUrl())){
				item.setUrl(item.getProduct_url());
			}
			newYwProductList.add(item);
			num ++;
			if(num == 16){		// 首页最多展示8条数据
				break;
			}
		}
		
		return newYwProductList;
	}
	
	/**
	 * 首页热门展示(大图三张)
	*
	*@param request
	*@param response
	*@return
	*JSONObject
	 */
	@RequestMapping(ConstantFunctionsFront.YW_POT_00002)
    @ResponseBody
    public JSONObject detail_img1(String market_code , String branch_id){
		Map<String, Object> typemap = new HashMap<String, Object>();
		typemap.put("market_code", market_code);
		typemap.put("branch_id", branch_id);
		typemap.put("status", "1");
		List<YwProduct> sdlist = ywProductService.queryPage(typemap);
		List<Object> list = new ArrayList<Object>();
		JSONObject json = new JSONObject();
		
		for (int i = 0; i < sdlist.size(); i++) {
			String id = String.valueOf(sdlist.get(i).getId());
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("db_name", "yw_images_xcxthumbnail");
			paramMap.put("thumbnail_type", "4");// 小程序轮播缩略图类型
			paramMap.put("product_id", id);
			List<YwProductImage> productImageslist = ywProductImageService.queryList(paramMap);
			if(CollectionUtils.isNotEmpty(productImageslist)){
				if(productImageslist.size() >= 3){
					for (int j = 0; j < 3; j++) {
						Map<String,Object> map = new HashMap<String,Object>();
						if(j == 0){
							String image_id = sdlist.get(i).getUrl();
							String product_id = sdlist.get(i).getId();
							String url = sdlist.get(i).getProduct_url();
							int index = j;
							map.put("image_id", image_id);
							map.put("product_id", product_id);
							map.put("url", url);
							map.put("index", index);
						}else{
							String image_id = productImageslist.get(j).getImage_id();
							String product_id = productImageslist.get(j).getProduct_id();
							String url = productImageslist.get(j).getImage_url();
							int index = j;
							map.put("image_id", image_id);
							map.put("product_id", product_id);
							map.put("url", url);
							map.put("index", index);
						}
						YwProduct product = sdlist.get(i);
						map.put("product", product);
						list.add(map);
						json.put("list", list);
					}
					if(list.size() >= 3){
						break;
					}
				}
			}else{
				// 图片兼容
				YwImages ywImages = new YwImages();
				ywImages.setProduct_id(id);
				List<YwImages> ywImagesList = ywImagesService.queryList(ywImages);
				if(ywImagesList.size() >= 3){
					for (int j = 0; j < 3; j++) {
						Map<String,Object> map = new HashMap<String,Object>();
						if(j == 0){
							String image_id = sdlist.get(i).getUrl();
							String product_id = sdlist.get(i).getId();
							String url = sdlist.get(i).getProduct_url();
							int index = j;
							map.put("image_id", image_id);
							map.put("product_id", product_id);
							map.put("url", url);
							map.put("index", index);
						}else{
							int image_id = ywImagesList.get(j).getImage_id();
							String product_id = ywImagesList.get(j).getProduct_id();
							String url = ywImagesList.get(j).getImage_url();
							int index = j;
							map.put("image_id", image_id);
							map.put("product_id", product_id);
							map.put("url", url);
							map.put("index", index);
						}
						YwProduct product = sdlist.get(i);
						map.put("product", product);
						list.add(map);
						json.put("list", list);
					}
					if(list.size() >= 3){
						break;
					}
				}
			}
		} 
		
		return json;
	}
	
	/**
	 * 首页主打展示(大图三张)
	*
	*@param request
	*@param response
	*@return
	*JSONObject
	 */
	@RequestMapping(ConstantFunctionsFront.YW_POT_00003)
    @ResponseBody
    public JSONObject detail_img2(String market_code , String branch_id){
		Map<String, Object> typemap = new HashMap<String, Object>();
		typemap.put("market_code", market_code);
		typemap.put("branch_id", branch_id);
		typemap.put("status", "1");
		List<YwProduct> sdlist = ywProductService.queryPage(typemap);
		List<Object> list = new ArrayList<Object>();
		JSONObject json = new JSONObject();
		
		for (int i = 0; i < sdlist.size(); i++) {
			String id = String.valueOf(sdlist.get(i).getId());
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("db_name", "yw_images_xcxthumbnail");
			paramMap.put("thumbnail_type", "4");// 小程序轮播缩略图类型
			paramMap.put("product_id", id);
			List<YwProductImage> productImageslist = ywProductImageService.queryList(paramMap);
			if(CollectionUtils.isNotEmpty(productImageslist)){
				if(productImageslist.size() >= 3){
					for (int j = 0; j < 3; j++) {
						Map<String,Object> map = new HashMap<String,Object>();
						if(j == 0){
							String image_id = sdlist.get(i).getUrl();
							String product_id = sdlist.get(i).getId();
							String url = sdlist.get(i).getProduct_url();
							int index = j;
							map.put("image_id", image_id);
							map.put("product_id", product_id);
							map.put("url", url);
							map.put("index", index);
						}else{
							String image_id = productImageslist.get(j).getImage_id();
							String product_id = productImageslist.get(j).getProduct_id();
							String url = productImageslist.get(j).getImage_url();
							int index = j;
							map.put("image_id", image_id);
							map.put("product_id", product_id);
							map.put("url", url);
							map.put("index", index);
						}
						YwProduct product = sdlist.get(i);
						map.put("product", product);
						list.add(map);
						json.put("list", list);
					}
					if(list.size() >= 3){
						break;
					}
				}
			}else{
				// 图片兼容
				YwImages ywImages = new YwImages();
				ywImages.setProduct_id(id);
				List<YwImages> ywImagesList = ywImagesService.queryList(ywImages);
				if(ywImagesList.size() >= 3){
					for (int j = 0; j < 3; j++) {
						Map<String,Object> map = new HashMap<String,Object>();
						if(j == 0){
							String image_id = sdlist.get(i).getUrl();
							String product_id = sdlist.get(i).getId();
							String url = sdlist.get(i).getProduct_url();
							int index = j;
							map.put("image_id", image_id);
							map.put("product_id", product_id);
							map.put("url", url);
							map.put("index", index);
						}else{
							int image_id = ywImagesList.get(j).getImage_id();
							String product_id = ywImagesList.get(j).getProduct_id();
							String url = ywImagesList.get(j).getImage_url();
							int index = j;
							map.put("image_id", image_id);
							map.put("product_id", product_id);
							map.put("url", url);
							map.put("index", index);
						}
						YwProduct product = sdlist.get(i);
						map.put("product", product);
						list.add(map);
						json.put("list", list);
					}
					if(list.size() >= 3){
						break;
					}
				}
			}
		}
		
		return json;
	}
	
	/**
	 * 商品详情
	 */
	@RequestMapping(ConstantFunctionsFront.YW_POT_00010)
    @ResponseBody
    public Map<String, Object> detail(String id , String openid){
		Map<String, Object> map = new HashMap<String, Object>();
		YwProduct ywProduct = ywProductService.queryById(id);
		try {
			// 查询商品轮播图
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("db_name", "yw_images_xcxthumbnail");
			paramMap.put("thumbnail_type", "4");// 小程序轮播缩略图类型
			paramMap.put("product_id", ywProduct.getId());
			List<YwProductImage> imageslist = ywProductImageService.queryList(paramMap);
			if(CollectionUtils.isNotEmpty(imageslist)){
				ywProduct.setYwImages(imageslist);
				map = MapUtil.toMap(ywProduct);
			}else{
				YwImages ywImages = new YwImages();
				ywImages.setProduct_id(ywProduct.getId());
				List<YwImages> ywImagesList = ywImagesService.queryList(ywImages);
				map = MapUtil.toMap(ywProduct);
				map.put("ywImages", ywImagesList);
			}
			
			// 查询商品是否收藏
			Map<String, Object> dataMap = new HashMap<String, Object>();
			YwProductCollection ywProductCollection = null;
			dataMap.put("shopid", id);
			dataMap.put("createBy", openid);
			ywProductCollection = ywProductCollectionService.queryByIslike(dataMap);
			if(null == ywProductCollection){
				map.put("isLike", 0);
			}else{
				map.put("isLike", 1);
			}
		} catch (Exception e) {
			throw new BizException("该商品未收藏");
		}
		return map;
	}
	
	/**
	 * 根据用户选择的规格属性获取sku表最终价格
	 */
	@RequestMapping(ConstantFunctionsFront.YW_POT_00011)
    @ResponseBody
    public YwProductSku queryProductSku(YwProductSku ywProductSku){
		YwProductSku sku = null;
		try {
			sku = ywProductSkuService.query(ywProductSku);
		} catch (Exception e) {
			throw new BizException(e);
		}
		
		return sku;
	}
	
	/**
	 * 第一次分享获赠优惠券(fenxiang)
	 
	@RequestMapping(ConstantFunctionsFront.YW_POT_00014)
	@ResponseBody
	public Map<String, Object> fenxiang(String openid){
		
		Map<String, Object> map = new HashMap<String, Object>();
		//首先判断该用户之前是否已经领取过分享获赠的优惠券
		Map<String, Object> typemap = new HashMap<String, Object>();
		typemap.put("openid", openid);
		typemap.put("couponid", "2");
		int num = sclService.findCouponTypeBySum(typemap);
		if(num == 0){		//说明之前没有领取过
			//用户第一次分享商品后获赠优惠券
			YwCoupon coupon = scfService.queryById(2);
			if(null != coupon){
				if(coupon.getCount() > 0){
					//获取优惠券领取表实例
					YwCouponCollectiondetails couponlingqv = new YwCouponCollectiondetails();
					couponlingqv.setStarttime(new Date());
					couponlingqv.setEndtime(DateUtil.addDay(new Date(), coupon.getYouxiaoqi()));
					couponlingqv.setCoupontype(coupon.getCoupontype());
					couponlingqv.setLingqvtype("1");
					couponlingqv.setShiyongtype("0");
					couponlingqv.setIshave("0");
					couponlingqv.setManprice(coupon.getManprice());
					couponlingqv.setJianprice(coupon.getJianprice());
					couponlingqv.setCouponid("2");
					couponlingqv.setOpenid(openid);
					sclService.addYwCouponCollectiondetails(couponlingqv);
					coupon.setCount(coupon.getCount()-1);
					coupon.setPeoplenumber(coupon.getPeoplenumber()+1);
					scfService.updateCouponFafangByCount(coupon);
					map.put("msg", "您获赠优惠券");
					boolean ok = true;
					map.put("ok", ok);
				}
			}
		}else{	//领取过无法再领取
			map.put("msg", "该类优惠已经领取过");
			boolean ok = false;
			map.put("ok", ok);
		}
		return map;
	}
	*/
	
	/**
	 * 修改商品收藏状态
	 */
	@RequestMapping(ConstantFunctionsFront.YW_POT_00012)
    @ResponseBody
    public Map<String, Object> updateIsLike(String openid , String shopid , int isLike){
		
		Map<String, Object> map = new HashMap<String, Object>();
		//页面传过来的值是false,说明用户正在执行取消收藏状态
		if(isLike == 0){
			ywProductCollectionService.deleteIslike(Integer.valueOf(shopid));
			map.put("isLike", 0);
		}else{
			//执行收藏操作
			YwProduct ywProduct = ywProductService.queryById(shopid);
			String title = ywProduct.getTitle();
			String url = ywProduct.getProduct_url();
			String price = ywProduct.getPrice();
			
			YwProductCollection YwProductCollection = new YwProductCollection();
			YwProductCollection.setUrl(url);
			YwProductCollection.setTitle(title);
			YwProductCollection.setPrice(price);
			YwProductCollection.setCreateBy(openid);
			YwProductCollection.setShopid(shopid);
			ywProductCollectionService.addIslike(YwProductCollection);
			map.put("isLike", 1);
		}
		return map;
	}
	
	/**
	 * 查看收藏页商品
	 */
	@RequestMapping(ConstantFunctionsFront.YW_POT_00016)
	@ResponseBody
	public Map<String, Object> queryByLike(String openid){
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> ismap = new HashMap<String, Object>();
		ismap.put("createBy", openid);
		List<YwProductCollection> ywProductCollection = ywProductCollectionService.queryIslike(ismap);
		if(ywProductCollection.size() > 0){
			map.put("resultList", ywProductCollection);
			boolean isHave = true;
			map.put("isHave", isHave);
		}else{
			boolean isHave = false;
			map.put("isHave", isHave);
		}
		return map;
	}
	
	/**
	 * 删除已收藏
	 */
	@RequestMapping(ConstantFunctionsFront.YW_POT_00017)
	@ResponseBody
	public void isLikeByFalse(String openid,String id) {
		int newid = Integer.parseInt(id); 		
		Map<String, Object> ismap = new HashMap<String, Object>();
		ismap.put("createBy", openid);
		List<YwProductCollection> islikelist = ywProductCollectionService.queryIslike(ismap);
		for (YwProductCollection islist : islikelist) {
			if (islist.getId() == newid) {
				//删除收藏表数据后 修改对应商品编号的收藏状态
				String shopid = islist.getShopid();
				YwProduct ywProduct = ywProductService.queryById(shopid);
				ywProduct.setIsLike("0");
				ywProductService.updateYwProduct(ywProduct);
				ywProductCollectionService.deleteIslikeById(newid);
			}
		}
	}
	
	/**
	 * 按照商品标题搜索对应商品
	 */
	 @RequestMapping(ConstantFunctionsFront.YW_POT_00028)
	 @ResponseBody
	 public List<Map<String,Object>> queryByTitle(YwProduct ywProduct){
		 List<YwProduct> sdlist = ywProductService.queryByTitle(ywProduct);
		 if(sdlist.size() == 0){
			 throw new BizException("商品数据不存在");
		 }
		  return MapUtil.toMapList(sdlist);
	 	}
	 
	 
	/**
	 * 生成二维码
	 */
	@RequestMapping(ConstantFunctionsFront.YW_POT_00030)
	@ResponseBody
	public Map<String, String> GeneratingTwoDimensionalCode(String product_id , String wx_appid) {
		RestTemplate rest = new RestTemplate();
		InputStream inputStream = null;
		String token = ywProductService.getToken(wx_appid);
		System.out.println("---token值---"+ token);
		//生成二维码请求(可带参数)
		//String toCode_url = "https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token=" + token;
		//生成小程序码请求(可带参数)
//		String toCode_url = "https://api.weixin.qq.com/wxa/getwxacode?access_token=" + token;
		//生成小程序码请求(不可带参数)
		//https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + token;
		//封装商品对象
		JSONObject productData = new JSONObject();
		productData.put("product_flag", "1");
		productData.put("product_id", product_id);
		String product_path = "yw-product/products/product/product?productData=" + productData;
		Map<String, String> resultMap = new HashMap<String, String>();
		if(StringUtils.isNotEmpty(token)){
			
			try {
				// 发送请求
//				Map<String, Object> map = new HashMap<String, Object>();
//	            map.put("path", product_path);		//你二维码中跳向的地址
//	            map.put("width", "430");//图片大小	httpPost
//	            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
//	            HttpEntity requestEntity = new HttpEntity(map, headers);
//	            ResponseEntity<byte[]> entity = rest.exchange(toCode_url, HttpMethod.POST, requestEntity, byte[].class, new Object[0]);
////	            LOG.info("调用小程序生成微信永久小程序码URL接口返回结果:" + entity.getBody());
	            byte[] result = ywQrcodeService.getWXACode(wx_appid, product_path);
	            inputStream = new ByteArrayInputStream(result);
	            Map urlMap = pictureService.build_QRCode(inputStream);
	            String code_path = (String) urlMap.get("url");
				resultMap.put("code_path", code_path);
                System.out.println("===========访问路径==========="+code_path);
			} catch (Exception e) {
				throw new BizException("二维码生成失败");
			}finally {
	           IOUtils.close(inputStream);
	        }
		}
		return resultMap;
	}

}

