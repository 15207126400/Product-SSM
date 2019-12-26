package com.yunwei.product.backend.sales.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yunwei.common.cache.CacheManager;
import com.yunwei.common.exception.BizException;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.context.service.PictureService;
import com.yunwei.product.backend.product.controller.YwProductManage;
import com.yunwei.product.backend.service.YwDistributionProductService;
import com.yunwei.product.backend.service.YwProductService;
import com.yunwei.product.common.backend.model.form.YwDistributionProductForm;
import com.yunwei.product.common.model.YwDistributionProduct;
import com.yunwei.product.common.model.YwProduct;

/**
 * 分销个性商品表
 * @author zhangz
 *
 */
@Controller
@RequestMapping("/sales/ywDistributionProduct")
public class YwDistributionProductManage {
	
	@Autowired
	private YwDistributionProductService ywDistributionProductService;
	@Autowired
	private CacheManager cacheManager;
	@Autowired
	private YwProductService ywProductService;
	@Autowired  
    private PictureService pictureService;

	
	// 日志打印
    Logger logger = Logger.getLogger(YwProductManage.class);
	
	@RequestMapping
	public String ywDistributionProductList(Model model){
		return "/sales/distributionproduct/ywDistributionProductList";
	}
	
	/**
	 * 查询字典集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public Map<String, Object> getList(YwDistributionProduct ywDistributionProduct,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
//		map.put("begin", start);
//		map.put("end", pageSize); // 设置每页显示几条数据
//		map.putAll(MapUtil.toMap(ywDistributionProduct));
		List<YwDistributionProduct> list = ywDistributionProductService.queryListPage(ywDistributionProduct,start,end);
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
	public Paginator getPaginator(YwDistributionProduct ywDistributionProduct,@RequestParam(defaultValue = "10") int pageSize){
		int count = ywDistributionProductService.queryTotals(ywDistributionProduct);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(YwDistributionProduct ywDistributionProduct,String op_type,Model model){
		model.addAttribute("op_type", op_type);
		// 查询分销商品
		model.addAttribute("products", ywProductService.queryPage(null));
		if(StringUtils.equals(op_type, "2")){
			model.addAttribute("ywDistributionProduct", ywDistributionProductService.query(ywDistributionProduct));
		} else {
			model.addAttribute("ywDistributionProduct", ywDistributionProduct);
		}
		
		return "/sales/distributionproduct/ywDistributionProductEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(@RequestParam(value = "file", required = false) MultipartFile file,YwDistributionProduct ywDistributionProduct,String op_type){
		Map<String,Object> resMap = new HashMap<String, Object>();
		try {
			// 查询商品是否已经添加团购活动
			if(StringUtils.equals(op_type, "1")){
				YwDistributionProduct distributionProduct = new YwDistributionProduct();
				distributionProduct.setProduct_id(ywDistributionProduct.getProduct_id());
				distributionProduct = ywDistributionProductService.query(distributionProduct);
				if(distributionProduct != null){
					throw new BizException("您已经添加过商品为特殊分销商品！请重新选择商品");
				}
			}
			if(file != null && file.getSize() != 0){
				ywDistributionProduct.setDis_pro_showlogo(ywDistributionProductService.uploadDistributionProductImage(file, ywDistributionProduct));
			}
			// 查询商品
			YwProduct ywProduct = ywProductService.queryById(ywDistributionProduct.getProduct_id());
			if(ywProduct != null){
				// 设置商品名称
				ywDistributionProduct.setProduct_name(ywProduct.getTitle());
			}
			if(StringUtils.equals(op_type, "1")){
				ywDistributionProductService.insert(ywDistributionProduct);
				resMap.put("error_no", "0");
				resMap.put("error_info", "添加成功");
			} else {
				ywDistributionProductService.update(ywDistributionProduct);
				resMap.put("error_no", "0");
				resMap.put("error_info", "修改成功");
			}
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
	public Map<String,Object> delete(YwDistributionProduct ywDistributionProduct){
		Map<String,Object> map = new HashMap<String, Object>();
		ywDistributionProductService.delete(ywDistributionProduct);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		cacheManager.refreshAllCache();
		return map;
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping("deleteBatch")
	@ResponseBody
	public Map<String,Object> deleteBatch(YwDistributionProduct ywDistributionProduct,YwDistributionProductForm ywDistributionProductForm){
		Map<String,Object> map = new HashMap<String, Object>();
		String[] dis_pro_id = ywDistributionProductForm.getDis_pro_id().split(",");
		String[] product_id = ywDistributionProductForm.getProduct_id().split(",");
		Map<String, String[]> strMap = new HashMap<String, String[]>();
		strMap.put("dis_pro_id",dis_pro_id);
		strMap.put("product_id",product_id);
		ywDistributionProductService.deleteBatch(strMap);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}
}
