package com.yunwei.product.infobase.sales.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.yunwei.common.exception.BizException;
import com.yunwei.common.util.DateUtil;
import com.yunwei.common.util.MapUtil;
import com.yunwei.product.common.backend.model.dto.YwSeckillActivityInfobaseDto;
import com.yunwei.product.common.infobase.model.form.YwSeckillRecordForm;
import com.yunwei.product.common.model.YwSeckillActivity;
import com.yunwei.product.common.model.YwSeckillRecord;
import com.yunwei.product.common.model.YwSeckillTime;
import com.yunwei.product.infobase.constant.ConstantFunctionsFront;
import com.yunwei.product.infobase.service.YwSeckillActivityService;
import com.yunwei.product.infobase.service.YwSeckillRecordService;
import com.yunwei.product.infobase.service.YwSeckillTimeService;

/**
 * 
* @ClassName: YwSeckillController 
* @Description: 秒杀(小程序端接口)
* @author 晏飞
* @date 2018年7月2日 下午4:35:00 
*
 */
@Controller
public class YwSeckillController {
	
	@Autowired
	private YwSeckillRecordService ywSeckillRecordService;		// 秒杀记录
	@Autowired
	private YwSeckillActivityService ywSeckillActivityInfobaseService;  // 秒杀活动
	@Autowired
	private YwSeckillTimeService ywSeckillTimeService;			// 秒杀时间配置
	
	
	/**
	 * 秒杀时间配置信息查询
	*
	*@param ywSeckillTime
	*@return
	*Map<String,Object>
	 */
	@RequestMapping(ConstantFunctionsFront.YW_SECKILL_00400)
    @ResponseBody
    public Map<String, Object> querySeckillTimeList(YwSeckillTime ywSeckillTime){
		Map<String, Object> maps = new HashMap<String, Object>();
		Map<String, Object> paramMap = MapUtil.toMap(ywSeckillTime);
		paramMap.put("db_name", "yw_images_xcxthumbnail");
		paramMap.put("thumbnail_type", "8");
		List<YwSeckillTime> YwSeckillTimeList = ywSeckillTimeService.queryList(paramMap);
		List<Object> list = new ArrayList<Object>();
		for (YwSeckillTime seckillList : YwSeckillTimeList) {
			String time_id = seckillList.getTime_id();
			String time_title_url = seckillList.getTime_title_url();
			Date seckill_date = seckillList.getSeckill_date();
			String seckill_starttime = seckillList.getSeckill_starttime();
			String seckill_endtime = seckillList.getSeckill_endtime();
			
			// 当前时间
			//String strDate = DateUtil.format(new Date(),DateUtil.DATE_FORMAT);
			Date nowDate = DateUtil.parse(DateUtil.format(new Date(),DateUtil.DATE_FORMAT),DateUtil.DATE_FORMAT);
			// 秒杀开始时间
			String start = DateUtil.format(seckill_date, DateUtil.DATE_FORMAT) + " " +seckill_starttime;
			Date startTime = DateUtil.parse(start,DateUtil.DATE_TIME_FORMAT_NO_SEC);
			// 秒杀结束时间
			Date endDate = DateUtil.addHours(startTime,Integer.valueOf(seckill_endtime));
			if(DateUtil.getIntervalDay(nowDate, seckill_date) == 0){		// 正在当天的
				if(DateUtil.getIntervalMinute(startTime, new Date()) >= 0 && DateUtil.getIntervalMinute(new Date(), endDate) > 0){
					Map<String, Object> map = new HashMap<String, Object>();
					// 说明在进行时间中
					map.put("time_id", time_id);
					map.put("time_title_url", time_title_url);
					map.put("seckill_date", DateUtil.format(seckill_date, DateUtil.DATE_FORMAT));
					map.put("seckill_starttime", seckill_starttime);
					map.put("seckill_endtime", DateUtil.format(endDate, DateUtil.TIME_FORMAT_NO_SEC));
					map.put("flag", "抢购中");
					map.put("code", 1);
					list.add(map);
				}else if(DateUtil.getIntervalMinute(startTime, new Date()) >= 0 && DateUtil.getIntervalMinute(new Date(), endDate) <= 0){
					Map<String, Object> map = new HashMap<String, Object>();
					// 说明在进行时间中
					map.put("time_id", time_id);
					map.put("time_title_url", time_title_url);
					map.put("seckill_date", DateUtil.format(seckill_date, DateUtil.DATE_FORMAT));
					map.put("seckill_starttime", seckill_starttime);
					map.put("seckill_endtime", DateUtil.format(endDate, DateUtil.TIME_FORMAT_NO_SEC));
					map.put("flag", "已结束");
					map.put("code", 0);
					list.add(map);
				}else {
					Map<String, Object> map = new HashMap<String, Object>();
					// 未开始
					map.put("time_id", time_id);
					map.put("time_title_url", time_title_url);
					map.put("seckill_date", DateUtil.format(seckill_date, DateUtil.DATE_FORMAT));
					map.put("seckill_starttime", seckill_starttime);
					map.put("seckill_endtime", DateUtil.format(endDate, DateUtil.TIME_FORMAT_NO_SEC));
					map.put("flag", "即将开抢");
					map.put("code", 3);
					list.add(map);
				}
			}else if(DateUtil.getIntervalDay(nowDate, seckill_date) == 1){		// 明天的
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("time_id", time_id);
				map.put("time_title_url", time_title_url);
				map.put("seckill_date", DateUtil.format(seckill_date, DateUtil.DATE_FORMAT));
				map.put("seckill_starttime", seckill_starttime);
				map.put("seckill_endtime", DateUtil.format(endDate, DateUtil.TIME_FORMAT_NO_SEC));
				map.put("flag", "明日预告");
				map.put("code", 4);
				list.add(map);
			}else if(DateUtil.getIntervalDay(nowDate, seckill_date) == -1){		// 昨天的
				if(DateUtil.getIntervalMinute(startTime, new Date()) >= 0 && DateUtil.getIntervalMinute(new Date(), endDate) > 0){
					Map<String, Object> map = new HashMap<String, Object>();
					// 说明在进行时间中
					map.put("time_id", time_id);
					map.put("time_title_url", time_title_url);
					map.put("seckill_date", DateUtil.format(seckill_date, DateUtil.DATE_FORMAT));
					map.put("seckill_starttime", seckill_starttime);
					map.put("seckill_endtime", DateUtil.format(endDate, DateUtil.TIME_FORMAT_NO_SEC));
					map.put("flag", "进行中");
					map.put("code", 2);
					list.add(map);
				}else{
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("time_id", time_id);
					map.put("time_title_url", time_title_url);
					map.put("seckill_date", DateUtil.format(seckill_date, DateUtil.DATE_FORMAT));
					map.put("seckill_starttime", seckill_starttime);
					map.put("seckill_endtime", DateUtil.format(endDate, DateUtil.TIME_FORMAT_NO_SEC));
					map.put("flag", "已结束");
					map.put("code", 0);
					list.add(map);
				}
			}
		}
		maps.put("list", list);
		
		return maps;
	}
	
	/**
	 * 根据时间点查询秒杀活动商品数据
	*
	*@param ywSeckillTime
	*@return
	*Map<String,Object>
	 */
	@RequestMapping(ConstantFunctionsFront.YW_SECKILL_00401)
    @ResponseBody
    public Map<String, Object> querySeckillList(YwSeckillActivity ywSeckillActivity){
		List<YwSeckillActivityInfobaseDto> ywSeckillActivityDtos = null;
		List<Object> list = new ArrayList<Object>();
		Map<String, Object> top = new HashMap<String, Object>();
		
		Map<String, Object> maps = new HashMap<String, Object>();
		ywSeckillActivityDtos = ywSeckillActivityInfobaseService.queryUnionSeckillTimeList(MapUtil.toMap(ywSeckillActivity));
		for (YwSeckillActivityInfobaseDto ywSeckillActivityDto : ywSeckillActivityDtos) {
			if(StringUtils.equals("1", ywSeckillActivityDto.getSeckill_istop())){
				if(top.size() == 0){
					String seckill_id = ywSeckillActivityDto.getSeckill_id();
					String seckill_type = ywSeckillActivityDto.getSeckill_type();
					String seckill_name = ywSeckillActivityDto.getSeckill_name();
					String seckill_url = ywSeckillActivityDto.getSeckill_url();
					String seckill_price = ywSeckillActivityDto.getSeckill_price();
					String seckill_stock = ywSeckillActivityDto.getSeckill_stock();
					String seckill_percent = ywSeckillActivityDto.getSales_percent();
					String seckill_sum = ywSeckillActivityDto.getSales_sum();
					String time_id = ywSeckillActivityDto.getTime_id();
					String product_id = ywSeckillActivityDto.getProduct_id();
					String sku_id = ywSeckillActivityDto.getSku_id();
					String sku_attr = ywSeckillActivityDto.getSku_attr();
					String seckill_limit = ywSeckillActivityDto.getSeckill_limit();
					String seckill_description = ywSeckillActivityDto.getSeckill_description();
					String seckill_istop = ywSeckillActivityDto.getSeckill_istop();
					String seckill_status = ywSeckillActivityDto.getSeckill_status();
					String price = ywSeckillActivityDto.getPrice();
					Date seckill_date = ywSeckillActivityDto.getSeckill_date();
					String starttime = ywSeckillActivityDto.getSeckill_starttime();
					String endtime = ywSeckillActivityDto.getSeckill_endtime();
					// 秒杀开始时间
					String start = DateUtil.format(seckill_date, DateUtil.DATE_FORMAT) + " " +starttime;
					Date startTime = DateUtil.parse(start,DateUtil.DATE_TIME_FORMAT_NO_SEC);
					// 秒杀结束时间
					Date endDate = DateUtil.addHours(startTime,Integer.valueOf(endtime));
					top.put("seckill_id", seckill_id);
					top.put("seckill_type", seckill_type);
					top.put("seckill_name", seckill_name);
					top.put("seckill_url", seckill_url);
					top.put("seckill_price", seckill_price);
					top.put("seckill_stock", seckill_stock);
					top.put("seckill_percent", seckill_percent);
					top.put("seckill_sum", seckill_sum);
					top.put("time_id", time_id);
					top.put("product_id", product_id);
					top.put("sku_attr", sku_attr);
					top.put("sku_id", sku_id);
					top.put("seckill_limit", seckill_limit);
					top.put("seckill_description", seckill_description);
					top.put("seckill_istop", seckill_istop);
					top.put("seckill_status", seckill_status);
					top.put("price", price);
					top.put("endDate", DateUtil.format(endDate, DateUtil.DATE_TIME_FORMAT));
					top.put("startDate", DateUtil.format(startTime, DateUtil.DATE_TIME_FORMAT));
				}
				if(top.size() > 0){
					boolean topHave = true;
					top.put("topHave", topHave);
				}else{
					boolean topHave = false;
					top.put("topHave", topHave);
				}
			}else{
				Map<String, Object> map = new HashMap<String, Object>();
				String seckill_id = ywSeckillActivityDto.getSeckill_id();
				String seckill_type = ywSeckillActivityDto.getSeckill_type();
				String seckill_name = ywSeckillActivityDto.getSeckill_name();
				String seckill_url = ywSeckillActivityDto.getSeckill_url();
				String seckill_price = ywSeckillActivityDto.getSeckill_price();
				String seckill_stock = ywSeckillActivityDto.getSeckill_stock();
				String seckill_percent = ywSeckillActivityDto.getSales_percent();
				String seckill_sum = ywSeckillActivityDto.getSales_sum();
				String time_id = ywSeckillActivityDto.getTime_id();
				String product_id = ywSeckillActivityDto.getProduct_id();
				String sku_id = ywSeckillActivityDto.getSku_id();
				String sku_attr = ywSeckillActivityDto.getSku_attr();
				String seckill_limit = ywSeckillActivityDto.getSeckill_limit();
				String seckill_description = ywSeckillActivityDto.getSeckill_description();
				String seckill_istop = ywSeckillActivityDto.getSeckill_istop();
				String seckill_status = ywSeckillActivityDto.getSeckill_status();
				String price = ywSeckillActivityDto.getPrice();
				Date seckill_date = ywSeckillActivityDto.getSeckill_date();
				String starttime = ywSeckillActivityDto.getSeckill_starttime();
				String endtime = ywSeckillActivityDto.getSeckill_endtime();
				// 秒杀开始时间
				String start = DateUtil.format(seckill_date, DateUtil.DATE_FORMAT) + " " +starttime;
				Date startTime = DateUtil.parse(start,DateUtil.DATE_TIME_FORMAT_NO_SEC);
				// 秒杀结束时间
				Date endDate = DateUtil.addHours(startTime,Integer.valueOf(endtime));
				map.put("seckill_id", seckill_id);
				map.put("seckill_type", seckill_type);
				map.put("seckill_name", seckill_name);
				map.put("seckill_url", seckill_url);
				map.put("seckill_price", seckill_price);
				map.put("seckill_stock", seckill_stock);
				map.put("seckill_percent", seckill_percent);
				map.put("seckill_sum", seckill_sum);
				map.put("time_id", time_id);
				map.put("product_id", product_id);
				map.put("sku_attr", sku_attr);
				map.put("sku_id", sku_id);
				map.put("seckill_limit", seckill_limit);
				map.put("seckill_description", seckill_description);
				map.put("seckill_istop", seckill_istop);
				map.put("seckill_status", seckill_status);
				map.put("price", price);
				map.put("endDate", DateUtil.format(endDate, DateUtil.DATE_TIME_FORMAT));
				map.put("startDate", DateUtil.format(startTime, DateUtil.DATE_TIME_FORMAT));
				list.add(map);
			}
			
		}
		maps.put("list", list);
		maps.put("top", top);
		
		return maps;
	}
	
	/**
	 * 秒杀活动记录信息修改
	 * @param ywSeckillRecord
	 * @return
	 */
	@RequestMapping(ConstantFunctionsFront.YW_SECKILL_00402)
	@ResponseBody
	public Map<String, Object> updateSeckillRecord(YwSeckillRecordForm ywSeckillRecordForm){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			YwSeckillRecord ywSeckillRecord = new YwSeckillRecord();
			ywSeckillRecord.setOrder_sn(ywSeckillRecordForm.getOrder_sn());
			ywSeckillRecord.setRecord_id(ywSeckillRecordForm.getRecord_id());
			ywSeckillRecord.setRecord_status(ywSeckillRecordForm.getRecord_status());
			ywSeckillRecord.setRecord_updatetime(new Date());
			ywSeckillRecordService.update(ywSeckillRecord);
		} catch (Exception e) {
			throw new BizException(e);
		}
		return resultMap;
	}
	
	/**
	 * 秒杀活动记录信息查询
	 * @param ywSeckillRecord
	 * @return
	 */
	@RequestMapping(ConstantFunctionsFront.YW_SECKILL_00403)
	@ResponseBody
	public Map<String, Object> querySeckillRecord(YwSeckillRecord ywSeckillRecord){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			ywSeckillRecord = ywSeckillRecordService.query(ywSeckillRecord);
			resultMap = MapUtil.toMap(ywSeckillRecord);
		} catch (Exception e) {
			throw new BizException(e);
		}
		return resultMap;
	}
	
	/**
	 * 秒杀活动信息查询
	 * @param seckill_id
	 * @return
	 */
	@RequestMapping(ConstantFunctionsFront.YW_SECKILL_00404)
	@ResponseBody
	public Map<String, Object> querySeckillAactivity(String seckill_id){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("seckill_id", seckill_id);
			YwSeckillActivityInfobaseDto dto = ywSeckillActivityInfobaseService.queryUnionSeckillTimeList(paramMap).get(0);
			if(dto == null){
				throw new BizException("秒杀活动信息不存在");
			}
			String starttime = dto.getSeckill_starttime();
			String endtime = dto.getSeckill_endtime();
			// 秒杀开始时间
			String start = DateUtil.format(dto.getSeckill_date(), DateUtil.DATE_FORMAT) + " " +starttime;
			Date startTime = DateUtil.parse(start,DateUtil.DATE_TIME_FORMAT_NO_SEC);
			// 秒杀结束时间
			Date endDate = DateUtil.addHours(startTime,Integer.valueOf(endtime));
			resultMap = MapUtil.toMap(dto);
			resultMap.put("endDate", DateUtil.format(endDate, DateUtil.DATE_TIME_FORMAT));
		} catch (Exception e) {
			throw new BizException(e);
		}
		return resultMap;
	}
}
