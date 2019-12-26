package com.yunwei.product.infobase.product.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunwei.common.exception.BizException;
import com.yunwei.common.util.MapUtil;
import com.yunwei.product.common.model.OrderCountProcedure;
import com.yunwei.product.common.model.OrderPriceProcedure;
import com.yunwei.product.common.model.YwOrder;
import com.yunwei.product.common.model.YwOrderOffline;
import com.yunwei.product.infobase.constant.ConstantFunctionsFront;
import com.yunwei.product.infobase.service.YwOrderOfflineService;
import com.yunwei.product.infobase.service.YwOrderService;

/**
 * 机构订单管理
* @ClassName: YwOperatorOrderController 
* @Description: TODO(TODO) 
* @author 晏飞
* @date 2019年1月4日 下午7:19:14 
*
 */
@Controller
public class YwOperatorOrderController {
	@Autowired
	private YwOrderService ywOrderService;
	@Autowired
	private YwOrderOfflineService ywOrderOfflineService;
	
	/**
	 * 获取当前机构订单统计信息(线上及线下总成交量 , 总成交额 , 以及当天线上及线下总成交量 , 总成交额)
	*
	*@param branch_id
	*@return
	*Map<String,Object>
	 */
	@RequestMapping(ConstantFunctionsFront.YW_POT_00052)
	@ResponseBody
	public Map<String, Object> getOrderAndOfflineStatis(String branch_id){
		Map<String, Object> prarmMap = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Map<String, Object> orderMap = ywOrderService.getOrderAndOfflineStatis(branch_id);
			OrderPriceProcedure orderPriceList = (OrderPriceProcedure) orderMap.get("orderPriceList");				// 成交额对象
			List<OrderCountProcedure> orderCountList = (List<OrderCountProcedure>) orderMap.get("orderCountList");	// 成交量集合
			map.put("order_price", orderPriceList.getOrder_price());												// 线上订单总成交额
			map.put("today_order_price", orderPriceList.getToday_order_price());									// 线上订单今日成交额
			map.put("offline_price", orderPriceList.getOffline_price());											// 线下订单总成交额
			map.put("today_offline_price", orderPriceList.getToday_offline_price());								// 线下订单今日成交额
			map.put("today_orderCount", orderCountList.get(orderCountList.size() - 1).getOrderCount());				// 线上订单今日成交量
			map.put("today_offlineCount", orderCountList.get(orderCountList.size() - 1).getOfflineCount());			// 线下订单今日成交量
			YwOrder ywOrder = new YwOrder();
			ywOrder.setBranch_id(branch_id);
			int orderTotalLength = 0;
			List<YwOrder> ywOrders = ywOrderService.queryList(ywOrder);												
			for (YwOrder order : ywOrders) {
				if(!StringUtils.equals(order.getOrder_status(), "1")){
					orderTotalLength++;
				}
			}
			map.put("orderCount", orderTotalLength);																// 线上订单总成交量
			YwOrderOffline ywOrderOffline = new YwOrderOffline();
			ywOrderOffline.setBranch_id(branch_id);
			ywOrderOffline.setOffline_status("1");
			int offlineCount = ywOrderOfflineService.queryTotals(ywOrderOffline);									// 线下订单总成交量
			map.put("offlineCount", offlineCount);
			prarmMap = MapUtil.toMap(map);
		} catch (Exception e) {
			throw new BizException(e);
		}
		return prarmMap;
	}
	
}
