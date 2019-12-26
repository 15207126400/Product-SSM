package com.yunwei.product.backend.product.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.camel.Produce;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSONObject;
import com.yunwei.common.cache.CacheManager;
import com.yunwei.common.exception.BizException;
import com.yunwei.common.user.SysUser;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.common.view.json.ResponseBodyFilter;
import com.yunwei.context.service.PictureService;
import com.yunwei.context.sys.cache.YwDictionaryCache;
import com.yunwei.context.sys.model.YwDictionary;
import com.yunwei.product.backend.service.YwCouponService;
import com.yunwei.product.backend.service.YwImagesService;
import com.yunwei.product.backend.service.YwOrderPaymentService;
import com.yunwei.product.backend.service.YwProductAttributenameService;
import com.yunwei.product.backend.service.YwProductClassifyService;
import com.yunwei.product.backend.service.YwProductImageService;
import com.yunwei.product.backend.service.YwProductService;
import com.yunwei.product.backend.service.YwProductSkuService;
import com.yunwei.product.backend.service.impl.ExcelExportServiceImpl;
import com.yunwei.product.backend.websocket.serivce.YwUserCustomerWebSocketSerivce;
import com.yunwei.product.common.model.YwImages;
import com.yunwei.product.common.model.YwProduct;
import com.yunwei.product.common.model.YwProductClassify;
import com.yunwei.product.common.model.YwProductImage;
import com.yunwei.product.common.model.YwProductSku;


/**
 * 
 * @ClassName: BackstageSystemDetail
 * @Description: 后台商品信息类(重构包括数据库字段 , 后续继续修改部分内容)
 * @author 晏飞
 * @date 2017年12月1日 上午9:17:21
 *
 */

@Controller
@RequestMapping("/product/ywProduct")
public class YwProductManage {
	@Autowired  
    private PictureService pictureService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private YwProductService ywProductService;
	@Autowired
	private YwProductClassifyService ywProductClassifyService;
	@Autowired
	private YwOrderPaymentService spService;
	@Autowired
	private YwCouponService scfService;
	@Autowired
	private CacheManager cacheManager;
	@Autowired
	private YwImagesService ywImagesService;		//图片接口
	@Autowired
	private YwProductAttributenameService ywProductAttributenameService;
	@Autowired
	private YwProductSkuService ywProductSkuService;
	@Autowired
	private ExcelExportServiceImpl excelExportServiceImpl;
	@Autowired
	private YwDictionaryCache ywDictionaryCache;
	@Autowired
	private YwProductImageService ywProductImageService;
	@Autowired
	private YwUserCustomerWebSocketSerivce ywUserCustomerWebSocketSerivce;
	
	// 日志打印
	Logger logger = Logger.getLogger(YwProductManage.class);
	
	// 商品页面跳转
	@RequestMapping
	public String showProduct(Model model) {
		YwProductClassify ywProductClassify = new YwProductClassify();
		ywProductClassify.setClassify_status("1");
		model.addAttribute("ywProductClassifys", ywProductClassifyService.queryList(ywProductClassify));
		
		return "/product/product/ywProductList";
	}
	
	/**
	 * 查询商品集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public Map<String, Object> getList(SysUser sysUser,YwProduct ywProduct,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
//		ywProduct.setBranch_id(sysUser.getBranch_id());// 设置branch_id编号
		map.putAll(MapUtil.toMap(ywProduct));
		List<YwProduct> list = ywProductService.queryPage(map);
		map.put("error_no", "0");
		map.put("error_info", "查询成功");
		map.put("resultList", list);
		return map;
	}
	
	/**
	 * 查询分页情况
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("getPaginator")
	@ResponseBody
	public Paginator getPaginator(SysUser sysUser,YwProduct ywProduct,@RequestParam(defaultValue = "10") int pageSize){
//		ywProduct.setBranch_id(sysUser.getBranch_id());
		int count = ywProductService.queryTotals(ywProduct);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 获取分类数据
	*
	*@return
	*Map<String,Object>
	 */
	@RequestMapping("getClassify")
	@ResponseBody
	public Map<String, Object> getClassify(String parent_id){
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(parent_id)){
			YwProductClassify classify = new YwProductClassify();
			classify.setParent_id(parent_id);
			List<YwProductClassify> classifyList = ywProductClassifyService.queryList(classify);
			if(CollectionUtils.isNotEmpty(classifyList)){
				map.put("classifyList", classifyList);
				map.put("code", "001");
			}else{
				map.put("code", "000");
			}
		}else{
			map.put("code", "000");
		}
		
		return map;
	}
	
	/**
	 * 查询所有商品集合
	 * @param ywProduct
	 * @return
	 */
	@RequestMapping("getLists")
	@ResponseBody
	//@ResponseBodyFilter({"id","title","price"})
	public List<Map<String, Object>> getLists(YwProduct ywProduct){
		List<YwProduct> ywProducts = new ArrayList<YwProduct>();
		try {
			ywProducts = ywProductService.queryList();
		} catch (Exception e) {
			logger.error("查询商品集合失败", e);
			throw new BizException(e);
		}
		return MapUtil.toMapList(ywProducts);
	}

	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(SysUser sysUser,YwProduct ywProduct,Model model,String op_type){
		model.addAttribute("op_type", op_type);
		
		if(StringUtils.equals(op_type, "2")){
			// 返回1级分类菜单
			YwProductClassify ywProductClassify = new YwProductClassify();
			ywProductClassify.setClassify_status("1");
			ywProductClassify.setClassify_level("1");
			List<YwProductClassify> productClassifys = ywProductClassifyService.queryList(ywProductClassify);
			if(CollectionUtils.isNotEmpty(productClassifys)){
				model.addAttribute("productClassifys", productClassifys);
			}
			
			// 获取商品对象信息
			ywProduct = ywProductService.queryById(ywProduct.getId());
			
			// 返回当前分类的父级菜单
			YwProductClassify classify = new YwProductClassify();
			classify.setClassify_id(ywProduct.getShopType());
			classify = ywProductClassifyService.query(classify);
			if(classify != null){
				// 如果存在父级菜单
				if(StringUtils.isNotBlank(classify.getParent_id())){
					model.addAttribute("classify", classify);
					// 返回2级分类菜单
					YwProductClassify ywProductClassify2 = new YwProductClassify();
					ywProductClassify2.setClassify_status("1");
					ywProductClassify2.setClassify_level("2");
					ywProductClassify2.setParent_id(classify.getParent_id());
					List<YwProductClassify> productClassifys2 = ywProductClassifyService.queryList(ywProductClassify2);
					if(CollectionUtils.isNotEmpty(productClassifys2)){
						model.addAttribute("productClassifys2", productClassifys2);
					}
				}
			}
			model.addAttribute("ywProduct", ywProduct);
			
			//查询商品轮播图
			YwProductImage image = new YwProductImage();
			image.setProduct_id(String.valueOf(ywProduct.getId()));
			Map<String, Object> map = MapUtil.toMap(image);
			map.put("db_name", "yw_images_backthumbnail");
			map.put("thumbnail_type", "3");// 后台商品轮播图
			List<YwProductImage> images = ywProductImageService.queryList(map);
			model.addAttribute("imgList",images);
			// 图片兼容，查询图片表
			if(CollectionUtils.isEmpty(images)){
				YwImages ywImages = new YwImages();
				ywImages.setProduct_id(ywProduct.getId());
				List<YwImages> images2 = ywImagesService.queryList(ywImages);
				model.addAttribute("imgList",images2);
			}
			
		} else {
			YwProductClassify ywProductClassify = new YwProductClassify();
			ywProductClassify.setClassify_level("1");
			ywProductClassify.setClassify_status("1");
			model.addAttribute("productClassifys", ywProductClassifyService.queryList(ywProductClassify));
			model.addAttribute("ywProduct", new YwProduct());
		}
		return "/product/product/ywProductEdit";
	}

	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String, Object> insertOrUpdate(SysUser sysUser, @RequestParam(value = "file", required = false) MultipartFile file,YwProduct ywProduct,String op_type) {
		Map<String, Object> resMap = new HashMap<String, Object>();
      
		try {
			if(file != null && file.getSize() != 0){
				ywProduct.setUrl(ywProductService.uploadProductImage(file, ywProduct));
			}
			if(StringUtils.equals(op_type, "1")){
				// 判断机构信息是否存在
				if(StringUtils.isNotEmpty(sysUser.getBranch_id())){
					ywProduct.setBranch_id(sysUser.getBranch_id());
				}
				if(StringUtils.isNotBlank(ywProduct.getShopType2())){
					ywProduct.setShopType(ywProduct.getShopType2());
				}
				ywProductService.addYwProduct(ywProduct);
				
				resMap.put("error_no", "0");
				resMap.put("error_info", "添加成功");
			} else {
				if(StringUtils.isNotBlank(ywProduct.getShopType2())){
					ywProduct.setShopType(ywProduct.getShopType2());
				}
				ywProductService.updateProductAndOther(ywProduct);
				
				resMap.put("error_no", "0");
				resMap.put("error_info", "更新成功");
			}
			
		} catch (Exception e) {
			throw new BizException(e);
		}
		
		return resMap;
	}
	
	/**
	 * 列表数据单元格修改
	 * @return
	 */
	@RequestMapping("updateCell")
	@ResponseBody
	public Map<String, Object> updateCell(SysUser sysUser,YwProduct ywProduct) {
		Map<String, Object> resMap = new HashMap<String, Object>();
      
		try {
			ywProductService.update(ywProduct);
			resMap.put("error_no", "0");
			resMap.put("error_info", "更新成功");
			
		} catch (Exception e) {
			throw new BizException(e);
		}
		
		return resMap;
	}

	/**
	 * 删除
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Map<String,Object> delete(int id) {
		Map<String,Object> map = new HashMap<String, Object>();
		ywProductService.deleteYwProduct(id);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		
		return map;
		
	}
	
	
	
	/**
	 * 批量管理 获取勾选数据 返回页面(多数据修改为不同值)
	 */
	@RequestMapping("updateBatchToView")
	@ResponseBody
	public Map<String,Object> updateBatchToView(YwProduct ywProduct,String ids){
		Map<String,Object> map = new HashMap<String, Object>();
		List<YwProduct> productArray = new ArrayList<YwProduct>();
		String[] strings = ids.split(",");
		// 获取用户勾选的商品 , 打包商品数据
		for (int i = 0; i < strings.length; i++) {
			YwProduct product = ywProductService.queryById(strings[i]);
			if(product != null){
				productArray.add(product);
			}
		}
		map.put("productArray", productArray);
		
		return map;
		
	}
	
	/**
	 * 根据选择的修改项返回数据
	*
	*@return
	*Map<String,Object>
	 */
	@RequestMapping("changeValBackData")
	@ResponseBody
	public Map<String,Object> changeValBackData(String changeVal){
		Map<String,Object> map = new HashMap<String, Object>();
		
		if(StringUtils.isNotBlank(changeVal)){
			if(StringUtils.equals(changeVal, "1")){				// 表示选中修改项为商品分类
				YwProductClassify ywProductClassify1 = new YwProductClassify();
				ywProductClassify1.setClassify_level("1");
				ywProductClassify1.setClassify_status("1");
				map.put("ywProductClassify1", ywProductClassifyService.queryList(ywProductClassify1));
				
			}
			map.put("change_falg", changeVal);
		}
		
		return map;
	}
	
	/**
	 * 动态查询二级菜单数据
	*
	*@return
	*Map<String,Object>
	 */
	@RequestMapping("changeShopType")
	@ResponseBody
	public Map<String,Object> changeShopType(String shopType){
		Map<String,Object> map = new HashMap<String, Object>();
		
		if(StringUtils.isNotBlank(shopType)){
			// 查询该对象的所有子类(下级菜单)
			YwProductClassify ywProductClassify = new YwProductClassify();
			ywProductClassify.setParent_id(shopType);
			List<YwProductClassify> ywProductClassifys = ywProductClassifyService.queryList(ywProductClassify);
			map.put("ywProductClassifys", ywProductClassifys);
		} else {
			YwProductClassify ywProductClassify = new YwProductClassify();
			ywProductClassify.setParent_id("0");
			List<YwProductClassify> ywProductClassifys = ywProductClassifyService.queryList(ywProductClassify);
			map.put("ywProductClassifys", ywProductClassifys);
		}
		
		return map;
	}
	
	@RequestMapping("updateBatch")
	@ResponseBody
	public Map<String, Object> updateBatch(YwProduct ywProduct, String product_ids){
		Map<String, Object> map = new HashMap<String, Object>();
		String[] strings = product_ids.split(",");
		
		if(StringUtils.isNotBlank(ywProduct.getShopType()) && StringUtils.isNotBlank(ywProduct.getShopType2())){
			for (int i = 0; i < strings.length; i++) {
				YwProduct product = new YwProduct();
				product.setShopType(ywProduct.getShopType2());
				product.setId(strings[i]);
				ywProductService.updateByProduct(product);
			}
		} else if(StringUtils.isNotBlank(ywProduct.getShopType()) && StringUtils.isBlank(ywProduct.getShopType2())){
			for (int i = 0; i < strings.length; i++) {
				YwProduct product = new YwProduct();
				product.setShopType(ywProduct.getShopType());
				product.setId(strings[i]);
				ywProductService.updateByProduct(product);
			}
		} else if(StringUtils.isNotBlank(ywProduct.getStatus())){
			for (int i = 0; i < strings.length; i++) {
				YwProduct product = new YwProduct();
				product.setStatus(ywProduct.getStatus());
				product.setId(strings[i]);
				ywProductService.updateByProduct(product);
			}
		} else if(StringUtils.isNotBlank(ywProduct.getStock())){
			for (int i = 0; i < strings.length; i++) {
				YwProductSku ywProductSku = new YwProductSku();
				ywProductSku.setProduct_id(strings[i]);
				List<YwProductSku> ywProductSkus = ywProductSkuService.queryList(ywProductSku);
				for (YwProductSku ywProductSku2 : ywProductSkus) {
					ywProductSku2.setSku_stock(ywProduct.getStock());
					ywProductSkuService.update(ywProductSku2);
				}
			}
		}
		map.put("msg", "修改成功");
		
		return map;
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping("deleteBatch")
	@ResponseBody
	public Map<String,Object> deleteBatch(YwProduct ywProduct,String ids){
		Map<String,Object> map = new HashMap<String, Object>();
		String[] strings = ids.split(",");
		Map<String, String[]> strMap = new HashMap<String, String[]>();
		strMap.put("id",strings);
		ywProductService.deleteBatch(strMap);
		map.put("error_no", "0");
		map.put("error_info", "批量删除成功");
		return map;
	}	
	
	/**
	 * 商品导出Excel表格
	 * @param ywOrder
	 * @param response 
	 * @throws Exception
	 */
	@RequestMapping("product_exportExcel")
	public void btnSendOutGoods(SysUser sysUser,YwProduct ywProduct,HttpServletRequest request,HttpServletResponse response) throws Exception{
		JSONObject jsonObject = new JSONObject();
		String excel_name = "商品信息统计表单";
		String sheet_name = "商品信息";
		// 如果营销属性不为空 , 查询该数据字典对应的名称
		String market_code = "";
		// 查询商品对应的规格属性
		String sku_attr = "";
		long start = System.currentTimeMillis();
		List<String> columnNames = new ArrayList<String>();
		columnNames.add("商品编号");
		columnNames.add("商品标题");
		columnNames.add("商品规格");
		columnNames.add("价格");
		columnNames.add("虚拟价格");
		columnNames.add("商品分类");
		columnNames.add("特色描述");
		columnNames.add("已售件数");
		columnNames.add("虚拟销量");
		columnNames.add("商品库存");
		columnNames.add("营销属性");
		columnNames.add("排序");
		columnNames.add("运费");
		
		List<String[]> dataList = new ArrayList<String[]>();
		// 根据条件查询数据
		Map<String, Object> paramMap = MapUtil.toMap(ywProduct);
		List<YwProduct> products = ywProductService.importExcelByProduct(paramMap);
		if(products.size() > 0){
			for(int j=0 ; j< products.size() ; j++){
				YwProduct product = products.get(j);
				if(StringUtils.isNotBlank(product.getMarket_code())){
					YwDictionary dic_market_code = ywDictionaryCache.getDictionary("1040", product.getMarket_code());
					market_code = dic_market_code.getDic_subvalue();
				}
				
				if(CollectionUtils.isNotEmpty(product.getYwProductSkus())){
					for (int i = 0; i < product.getYwProductSkus().size(); i++) {
						sku_attr += product.getYwProductSkus().get(i).getSku_attr() + ",";
					}
				}
				
				String[] strings = {
					product.getId(),
					product.getTitle(),
					sku_attr,
					product.getPrice(),
					product.getProduct_virtualprice(),
					product.getShopType(),
					product.getKeyword(),
					product.getSold(),
					product.getProduct_virtualsales(),
					product.getStock(),
					market_code,
					product.getProduct_sort(),
					product.getProduct_carriage()
				};
				dataList.add(strings);
				jsonObject.put("rowCount", j+1);
				jsonObject.put("timeMillis",(System.currentTimeMillis() - start)/1000);
	        	ywUserCustomerWebSocketSerivce.sendMessage(sysUser.getUser_id(), jsonObject.toJSONString());
			}
			
        	jsonObject.put("exportFlag", 1);
			ywUserCustomerWebSocketSerivce.sendMessage(sysUser.getUser_id(), jsonObject.toJSONString());
		}
		excelExportServiceImpl.exportWithResponse(excel_name, sheet_name, columnNames, dataList, request , response);
	}	
	
	
	/**
	 * 商品轮播图上传
	 * @param file
	 * @param product_id
	 * @return
	 */
	@RequestMapping("uploadProductCarouselImage")
	@ResponseBody
	public Map<String,Object> uploadProductCarouselImage(MultipartFile file,YwProduct ywProduct){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(file != null && file.getSize() > 0){
				map.put("image_url", ywProductService.uploadProductCarouselImage(file, ywProduct));
			}
		} catch (Exception e) {
			throw new BizException(e);
		}
		
		return map;
	}
	
	/**
	 * 商品轮播图删除
	 * @param file
	 * @param product_id
	 * @return
	 */
	@RequestMapping("deleteProductCarouselImage")
	@ResponseBody
	public Map<String,Object> deleteProductCarouselImage(YwProductImage ywProductImage){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ywProductImageService.delete(ywProductImage);
			map.put("error_no", "0");
			map.put("error_info", "删除成功");
		} catch (Exception e) {
			throw new BizException(e);
		}
		
		return map;
	}
	
	/**
	 * 根据商品编号查询sku数据
	 * @param file
	 * @param product_id
	 * @return
	 */
	@RequestMapping("querySkus")
	@ResponseBody
	public List<Map<String,Object>> querySkus(String product_id){
		List<YwProductSku> ywProductSkus = null;
		try {
			YwProductSku ywProductSku = new YwProductSku();
			ywProductSku.setProduct_id(product_id);
			ywProductSkus = ywProductSkuService.queryList(ywProductSku);
		} catch (Exception e) {
			throw new BizException(e);
		}
		
		return MapUtil.toMapList(ywProductSkus);
	}
	
	/**
	 * 根据商品编号查询sku数据
	 * @param file
	 * @param product_id
	 * @return
	 */
	@RequestMapping("updateSkuCell")
	@ResponseBody
	public Map<String, Object> updateSkuCell(@Valid YwProductSku ywProductSku){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			 ywProductSkuService.update(ywProductSku);
			 map.put("error_info", "修改成功");
		} catch (Exception e) {
			throw new BizException(e);
		}
		
		return map;
	}
	
	/**
	 * 商品sku图片上传
	 * @param file
	 * @param product_id
	 * @return
	 */
	@RequestMapping("uploadProductSkuImage")
	@ResponseBody
	public Map<String,Object> uploadProductSkuImage(MultipartFile upfile,YwProduct ywProduct){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(upfile != null && upfile.getSize() > 0){
				map = ywProductService.uploadProductSkuImage(upfile, ywProduct);
				map.put("state","SUCCESS");
			}
		} catch (Exception e) {
			throw new BizException(e);
		}
		
		return map;
	}
	
}
