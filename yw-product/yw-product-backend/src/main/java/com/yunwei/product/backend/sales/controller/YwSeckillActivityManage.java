package com.yunwei.product.backend.sales.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yunwei.common.exception.BizException;
import com.yunwei.common.user.SysUser;
import com.yunwei.common.util.DateUtil;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.context.service.PictureService;
import com.yunwei.product.backend.service.YwProductService;
import com.yunwei.product.backend.service.YwProductSkuService;
import com.yunwei.product.backend.service.YwSeckillActivityService;
import com.yunwei.product.backend.service.YwSeckillTimeService;
import com.yunwei.product.common.backend.model.dto.YwSeckillActivityDto;
import com.yunwei.product.common.model.YwProductSku;
import com.yunwei.product.common.model.YwSeckillActivity;
import com.yunwei.product.common.model.YwSeckillTime;

/**
 * 系统字典控制器
 * @author zhangz
 *
 */
@Controller
@RequestMapping("/sales/ywSeckillActivity")
public class YwSeckillActivityManage {
	
	@Autowired
	private YwSeckillActivityService ywSeckillActivityService;
	@Autowired
	private YwProductService ywProductService;
	@Autowired  
    private PictureService pictureService;
	@Autowired
	private YwSeckillTimeService ywSeckillTimeService;
	@Autowired
	private YwProductSkuService ywProductSkuService;

	@RequestMapping
	public String ywSeckillActivityList(Model model){
//		String string =  PropertiesUtils.get("func.org.locate.scope");
		return "/sales/seckillactivity/ywSeckillActivityList";
	}
	
	/**
	 * 查询字典集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public Map<String, Object> getList(SysUser sysUser,YwSeckillActivity ywSeckillActivity,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
		map.putAll(MapUtil.toMap(ywSeckillActivity));
		List<YwSeckillActivityDto> listDto = ywSeckillActivityService.queryUnionSeckillTimeAndProductPage(map);
		if(CollectionUtils.isNotEmpty(listDto)){
			for(YwSeckillActivityDto dto : listDto){
				dto.setStart_datetime(DateUtil.format(dto.getSeckill_date(), DateUtil.DATE_FORMAT)+" "+dto.getSeckill_starttime());
				dto.setSeckill_endtime(DateUtil.format(DateUtil.addHours(DateUtil.parse(DateUtil.format(dto.getSeckill_date(), DateUtil.DATE_FORMAT)+" "+dto.getSeckill_starttime(), DateUtil.DATE_TIME_FORMAT_NO_SEC), Integer.valueOf(dto.getSeckill_endtime()).intValue()), DateUtil.DATE_TIME_FORMAT_NO_SEC));
			}
		}
		map.put("error_no", "0");
		map.put("error_info", "查询成功");
		map.put("resultList", listDto);
		return map;
	}
	
	/**
	 * 查询分页情况
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("getPaginator")
	@ResponseBody
	public Paginator getPaginator(SysUser sysUser,YwSeckillActivity ywSeckillActivity,@RequestParam(defaultValue = "10") int pageSize){
		int count = ywSeckillActivityService.queryTotals(ywSeckillActivity);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(SysUser sysUser,YwSeckillActivity ywSeckillActivity,String op_type,Model model){
		model.addAttribute("products", ywProductService.queryPage(new HashMap<String, Object>()));
		// 查询秒杀活动开始日期
		model.addAttribute("seckillTimes", ywSeckillTimeService.querySeckillDates(null));
		model.addAttribute("op_type", op_type);
		if(StringUtils.equals(op_type, "2")){
			ywSeckillActivity = ywSeckillActivityService.query(ywSeckillActivity);
			if(ywSeckillActivity != null){
				// 查询秒杀活动时间
				YwSeckillTime ywSeckillTime = new YwSeckillTime();
				ywSeckillTime.setTime_id(ywSeckillActivity.getTime_id());
				ywSeckillTime = ywSeckillTimeService.query(ywSeckillTime);
				ywSeckillTime.setSeckill_endtime(DateUtil.format(DateUtil.addHours(DateUtil.parse(DateUtil.format(ywSeckillTime.getSeckill_date(), DateUtil.DATE_FORMAT)+" "+ywSeckillTime.getSeckill_starttime(), DateUtil.DATE_TIME_FORMAT_NO_SEC), Integer.valueOf(ywSeckillTime.getSeckill_endtime()).intValue()), DateUtil.DATE_TIME_FORMAT_NO_SEC));
				model.addAttribute("seckillTime", ywSeckillTime);
				
				// 查询秒杀活动商品sku
				YwProductSku ywProductSku = new YwProductSku();
				ywProductSku.setProduct_id(ywSeckillActivity.getProduct_id());
				model.addAttribute("productSkus", ywProductSkuService.queryList(ywProductSku));
			}
			model.addAttribute("ywSeckillActivity", ywSeckillActivity);
		} else {
			model.addAttribute("ywSeckillActivity", ywSeckillActivity);
		}
		
		return "/sales/seckillactivity/ywSeckillActivityEdit";
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(@RequestParam(value = "file", required = false) MultipartFile file, SysUser sysUser , YwSeckillActivity ywSeckillActivity,String op_type){
		Map<String,Object> resMap = new HashMap<String, Object>();
		try {
			// 查询商品是否已经添加团购活动
			if(StringUtils.equals(op_type, "1")){
				YwSeckillActivity seckillActivity = new YwSeckillActivity();
				seckillActivity.setProduct_id(ywSeckillActivity.getProduct_id());
				seckillActivity = ywSeckillActivityService.query(seckillActivity);
				if(seckillActivity != null){
					throw new BizException("您已经添加该商品为秒杀活动！请重新选择商品");
				}
			}
			
			// 判断文件是否为空
			if(file != null && file.getSize() != 0){
				ywSeckillActivity.setSeckill_url(ywSeckillActivityService.uploadSeckillActivityImage(file, ywSeckillActivity));
			}
			// 判断秒杀活动是否需要置顶
			if(StringUtils.equals(ywSeckillActivity.getSeckill_istop(), "1")){
				// 查询所有置顶的秒杀活动
				YwSeckillActivity activity = new YwSeckillActivity();
				activity.setSeckill_istop("1");
				activity.setTime_id(ywSeckillActivity.getTime_id());
				activity = ywSeckillActivityService.query(activity);
				
				if(activity != null){
					YwSeckillActivity activity2 = new YwSeckillActivity();
					activity2.setSeckill_istop("0");
					activity2.setSeckill_id(activity.getSeckill_id());
					ywSeckillActivityService.update(activity2);
				}
			}
			if(StringUtils.equals(op_type, "1")){
				ywSeckillActivityService.insert(ywSeckillActivity);
				resMap.put("error_no", "0");
				resMap.put("error_info", "添加成功");
			} else {
				ywSeckillActivityService.update(ywSeckillActivity);
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
	public Map<String,Object> delete(YwSeckillActivity ywSeckillActivity){
		Map<String,Object> map = new HashMap<String, Object>();
		ywSeckillActivityService.delete(ywSeckillActivity);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping("deleteBatch")
	@ResponseBody
	public Map<String,Object> deleteBatch(YwSeckillActivity ywSeckillActivity){
		Map<String,Object> map = new HashMap<String, Object>();
		String[] seckill_id = ywSeckillActivity.getSeckill_id().split(",");
		Map<String, String[]> strMap = new HashMap<String, String[]>();
		strMap.put("seckill_id",seckill_id);
		ywSeckillActivityService.deleteBatch(strMap);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}
	
	/**
	 * 秒杀活动商品sku查询
	 */
	@RequestMapping("getSeckillActivityProductSkuList")
	@ResponseBody
	public List<YwProductSku> getSeckillActivityProductSkuList(String product_id){
		List<YwProductSku> productSkus = new ArrayList<YwProductSku>();
		try {
			YwProductSku ywProductSku = new YwProductSku();
			ywProductSku.setProduct_id(product_id);
		    productSkus = ywProductSkuService.queryList(ywProductSku);
		} catch (Exception e) {
			throw new BizException(e);
		}
		
		return productSkus;
		
	}
}
