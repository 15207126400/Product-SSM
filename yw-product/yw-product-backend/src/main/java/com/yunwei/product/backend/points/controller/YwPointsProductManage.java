package com.yunwei.product.backend.points.controller;

import java.util.Date;
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
import org.springframework.web.multipart.MultipartFile;

import com.yunwei.product.backend.service.YwCouponService;
import com.yunwei.product.backend.service.YwPointsProductService;
import com.yunwei.product.backend.service.YwProductService;
import com.yunwei.product.common.model.YwPointsProduct;
import com.yunwei.product.common.model.YwTeamActivity;
import com.yunwei.common.user.SysUser;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.common.exception.BizException;
import com.yunwei.context.service.PictureService;

/**
 * 积分商品控制层
* @ClassName: YwPointsProductManage 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年07月26日 下午15:50:53
*
 */
@Controller
@RequestMapping("/points/ywPointsProduct")
public class YwPointsProductManage {
	private static Logger logger = Logger.getLogger(YwPointsProductManage.class);
	@Autowired
	private YwPointsProductService ywPointsProductService;
	@Autowired
	private YwProductService ywProductService;
	@Autowired
	private YwCouponService ywCouponService;
	@Autowired
	private PictureService pictureService;
 
	/**
	 * 图片链接前缀
	 */
	private static final String FRONTPATH = "https://xcx.whywxx.com/yongyou/image/home/detail";
	
	@RequestMapping
	public String ywPointsProductList(Model model){

		return "/points/pointsproduct/ywPointsProductList";
	}
	
	/**
	 * 查询字典集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public List<Map<String, Object>> getList(SysUser sysUser,YwPointsProduct ywPointsProduct,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
		map.putAll(MapUtil.toMap(ywPointsProduct));
		List<YwPointsProduct> list = ywPointsProductService.queryPage(map);
		return MapUtil.toMapList(list);
	}
	
	/**
	 * 查询分页情况
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("getPaginator")
	@ResponseBody
	public Paginator getPaginator(YwPointsProduct ywPointsProduct,@RequestParam(defaultValue = "10") int pageSize){
		int count = ywPointsProductService.queryTotals(ywPointsProduct);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(SysUser sysUser,YwPointsProduct ywPointsProduct,String op_type,Model model){
		model.addAttribute("op_type", op_type);
		model.addAttribute("products",ywProductService.queryPage(null));
		model.addAttribute("coupons", ywCouponService.queryList());
		if(StringUtils.equals(op_type, "2")){
			model.addAttribute("ywPointsProduct", ywPointsProductService.query(ywPointsProduct));
		} else {
			model.addAttribute("ywPointsProduct", ywPointsProduct);
		}
		
		return "/points/pointsproduct/ywPointsProductEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(@RequestParam(value="file", required = false) MultipartFile file,YwPointsProduct ywPointsProduct,String op_type){
		Map<String,Object> resmap = new HashMap<String, Object>();
		try {
			// 判断该商品是否添加过
			if(StringUtils.equals(op_type, "1")){
				if(StringUtils.equals(ywPointsProduct.getPoints_pro_type(), "1")){
					YwPointsProduct pointsProduct = new YwPointsProduct();
					pointsProduct.setProduct_id(ywPointsProduct.getProduct_id());
					pointsProduct = ywPointsProductService.query(pointsProduct);
					if(pointsProduct != null){
						throw new BizException("您已经添加过该商品作为积分兑换商品！请重新选择");
					}
				}else if(StringUtils.equals(ywPointsProduct.getPoints_pro_type(), "2")){
					YwPointsProduct pointsProduct = new YwPointsProduct();
					pointsProduct.setCoupon_id(ywPointsProduct.getCoupon_id());
					pointsProduct = ywPointsProductService.query(pointsProduct);
					if(pointsProduct != null){
						throw new BizException("您已经添加过该优惠券作为积分兑换商品！请重新选择");
					}
				}
				
			}
			
			// 判断文件是否为空
			if(file != null && file.getSize() != 0){
				ywPointsProduct.setPoints_pro_img(ywPointsProductService.uploadPointsProductImage(file, ywPointsProduct));
			}
			
			if(StringUtils.equals(op_type, "1")){
				ywPointsProduct.setPoints_pro_createtime(new Date());
				ywPointsProductService.insert(ywPointsProduct);
				resmap.put("error_no", "0");
				resmap.put("error_info", "添加成功");
			} else {
				if(StringUtils.equals(ywPointsProduct.getPoints_pro_type(), "1")){
					ywPointsProduct.setCoupon_id("null");
				}else{
					ywPointsProduct.setProduct_id("null");
				}
				ywPointsProduct.setPoints_pro_updatetime(new Date());
				ywPointsProductService.update(ywPointsProduct);
				resmap.put("error_no", "0");
				resmap.put("error_info", "修改成功");
			}
		} catch (Exception e) {
			throw new BizException(e);
		}
		return resmap;
	}
	
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Map<String,Object> delete(YwPointsProduct ywPointsProduct){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			ywPointsProductService.delete(ywPointsProduct);
			map.put("error_no", "0");
			map.put("error_info", "删除成功");
		} catch (Exception e) {
			throw new BizException(e);
		}
		return map;
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping("deleteBatch")
	@ResponseBody
	public Map<String,Object> deleteBatch(YwPointsProduct ywPointsProduct){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			String[] points_pro_id = ywPointsProduct.getPoints_pro_id().split(",");
			Map<String, String[]> strMap = new HashMap<String, String[]>();
			strMap.put("points_pro_id",points_pro_id);
			ywPointsProductService.deleteBatch(strMap);
			map.put("error_no", "0");
			map.put("error_info", "删除成功");
		} catch (Exception e) {
			throw new BizException(e);
		}
		return map;
	}	
}
