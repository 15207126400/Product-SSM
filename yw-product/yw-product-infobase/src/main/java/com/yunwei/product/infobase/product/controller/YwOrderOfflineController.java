package com.yunwei.product.infobase.product.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunwei.common.exception.BizException;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.OrderUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.context.payment.util.WxModelUtil;
import com.yunwei.product.common.infobase.model.form.YwOrderOfflineForm;
import com.yunwei.product.common.model.YwOrder;
import com.yunwei.product.common.model.YwOrderOffline;
import com.yunwei.product.common.model.YwOrderPayment;
import com.yunwei.product.common.model.YwProduct;
import com.yunwei.product.infobase.constant.ConstantFunctionsFront;
import com.yunwei.product.infobase.service.YwOrderOfflineService;
import com.yunwei.product.infobase.service.YwOrderPaymentService;

/**
 * 小程序线下订单支付功能
* @ClassName: YwOrderOfflineController 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年11月9日 下午5:24:32 
*
 */
@Controller
public class YwOrderOfflineController {
	
	@Autowired
	private YwOrderOfflineService ywOrderOfflineService;
	

	/**
	 * 创建线下订单
	 * @throws ParseException 
	 */
	@RequestMapping(ConstantFunctionsFront.YW_OFFLINE_00001)
	@ResponseBody
	public Map<String, Object> saveOrderDetail(YwOrderOfflineForm ywOrderOfflineForm) throws ParseException{
		Map<String, Object> map = new HashMap<String, Object>();
			
		try {
			// 线下订单创建
			String order_sn = ywOrderOfflineService.orderOfflineCreate(ywOrderOfflineForm);
			
			map.put("order_sn", order_sn);
		} catch (Exception e) {
			throw new BizException("订单提交失败，请稍后提交:{"+ e.getMessage() +"}");
		}
		return map;
	}
	
	/**
	 * 线下订单支付记录列表查询
	*
	*@param ywOrderOffline
	*@return
	*List<YwOrderOffline>
	 */
	@RequestMapping(ConstantFunctionsFront.YW_OFFLINE_00002)
	@ResponseBody
	public List<Map<String, Object>> queryOrderOfflineList(YwOrderOffline ywOrderOffline,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int pageSize){
		List<Map<String, Object>> maps = new ArrayList<Map<String,Object>>();
		List<YwOrderOffline> ywOrderOfflines = null;
		
		
		try {
			if (page <= 0) {
				page = 1;// 第一页
			}
			int start = (page - 1) * pageSize;
			Map<String, Object> map = new HashMap<String, Object>();
			map = MapUtil.toMap(ywOrderOffline);
			map.put("begin", start);
			map.put("end", pageSize); // 设置每页显示几条数据
			ywOrderOfflines = ywOrderOfflineService.queryPage(map);
		} catch (Exception e) {
			throw new BizException(e);
		}
		
		maps = MapUtil.toMapList(ywOrderOfflines);
		return maps;
	}
		
		/**
		 * 查询订单支付记录
		 * @param pageSize
		 * @return
		 */
		@RequestMapping(ConstantFunctionsFront.YW_OFFLINE_00003)
		@ResponseBody
		public Paginator getPaginator(YwOrderOffline ywOrderOffline,@RequestParam(defaultValue = "10") int pageSize){
			
			int count = ywOrderOfflineService.queryTotals(ywOrderOffline);
			return new Paginator(1, pageSize, count);
		}	
		
		
}
