package com.yunwei.product.infobase.sales.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunwei.common.cache.CacheManager;
import com.yunwei.common.exception.BizException;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.product.common.backend.model.dto.YwCommissionrecordDto;
import com.yunwei.product.common.model.YwDistributorCommissionrecord;
import com.yunwei.product.common.model.YwProduct;
import com.yunwei.product.infobase.constant.ConstantFunctionsFront;
import com.yunwei.product.infobase.service.YwDistributorCommissionrecordService;

/**
 * 分销佣金记录
 * @author zhangz
 *
 */
@Controller
public class YwDistributorCommissionrecordController {
	
	@Autowired
	private YwDistributorCommissionrecordService ywDistributorCommissionrecordService;
	@Autowired
	private CacheManager cacheManager;

	@RequestMapping(ConstantFunctionsFront.YW_DIS_00111)
	@ResponseBody
	public List<Map<String, Object>> queryCommission(YwDistributorCommissionrecord ywDistributorCommissionrecord
			,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int pageSize){
		List<Map<String, Object>> maps = new ArrayList<Map<String,Object>>();
		List<YwCommissionrecordDto> YwCommissionrecordDtos = null;
		try {
			if (page <= 0) {
				page = 1;// 第一页
			}
			int start = (page - 1) * pageSize;
			Map<String, Object> map = MapUtil.toMap(ywDistributorCommissionrecord);
			map.put("begin", start);
			map.put("end", pageSize); // 设置每页显示几条数据
			YwCommissionrecordDtos = ywDistributorCommissionrecordService.queryUnionMemberList(map);
		} catch (Exception e) {
			throw new BizException(e);
		}
		maps = MapUtil.toMapList(YwCommissionrecordDtos);
		return maps;
	}
	
	/**
	 * 查询数据分页信息
	 * 
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(ConstantFunctionsFront.YW_DIS_00113)
	@ResponseBody
	public Paginator getPaginator(YwDistributorCommissionrecord ywDistributorCommissionrecord,@RequestParam(defaultValue = "10") int pageSize) {

		int count = ywDistributorCommissionrecordService.queryTotals(ywDistributorCommissionrecord);
		return new Paginator(1, pageSize, count);
	}
}
