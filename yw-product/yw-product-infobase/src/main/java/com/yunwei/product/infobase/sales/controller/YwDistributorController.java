package com.yunwei.product.infobase.sales.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.yunwei.common.exception.BizException;
import com.yunwei.common.util.Base32;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.view.json.ResponseBodyFilter;
import com.yunwei.product.common.backend.model.dto.YwDistributorDto;
import com.yunwei.product.common.model.YwDistributionProduct;
import com.yunwei.product.common.model.YwDistributor;
import com.yunwei.product.common.model.YwDistributorSalestatus;
import com.yunwei.product.common.model.YwDistributorWithdrawalrecord;
import com.yunwei.product.common.model.YwMember;
import com.yunwei.product.common.model.YwOrder;
import com.yunwei.product.common.model.YwOrderItem;
import com.yunwei.product.common.model.YwWallet;
import com.yunwei.product.infobase.constant.ConstantFunctionsFront;
import com.yunwei.product.infobase.constant.ContantErrorNo;
import com.yunwei.product.infobase.service.YwDistributionProductService;
import com.yunwei.product.infobase.service.YwDistributorCommissionrecordService;
import com.yunwei.product.infobase.service.YwDistributorRuleService;
import com.yunwei.product.infobase.service.YwDistributorSalestatusService;
import com.yunwei.product.infobase.service.YwDistributorService;
import com.yunwei.product.infobase.service.YwDistributorWithdrawalrecordService;
import com.yunwei.product.infobase.service.YwMemberService;
import com.yunwei.product.infobase.service.YwOrderItemService;
import com.yunwei.product.infobase.service.YwOrderService;
import com.yunwei.product.infobase.service.YwWalletService;

/**
 * 
* @ClassName: YwCarouselController 
* @Description: 分销商信息 
* @author 晏飞
* @date 2017年11月18日 下午5:01:47 
*
 */
@Controller
public class YwDistributorController {
 
	@Autowired
	private YwDistributorService ywDistributorService;
	@Autowired
	private YwDistributorRuleService ywDistributorRuleService;		//分销规则
	@Autowired
	private YwDistributorCommissionrecordService ywDistributorCommissionrecordService;	//佣金记录
	@Autowired
	private YwDistributorSalestatusService ywDistributorSalestatusService;
	@Autowired
	private YwDistributionProductService ywDistributionProductService;
	@Autowired
	private YwMemberService ywMemberService;
	@Autowired
	private YwWalletService ywWalletService;
	@Autowired
	private YwOrderService ywOrderService;
	@Autowired
	private YwOrderItemService ywOrderItemService;
	@Autowired
	private YwDistributorWithdrawalrecordService ywDistributorWithdrawalrecordService;
	// 日志打印
	Logger logger = Logger.getLogger(YwDistributorController.class);

	/**
	 * 分销申请
	*
	*@param request
	*@param response
	*@param ywDistributor
	*@return
	*Map<String,Object>
	 */
	@RequestMapping(ConstantFunctionsFront.YW_DIS_00100)
    @ResponseBody
    public Map<String, Object> distribution(YwDistributor ywDistributor){
		Map<String, Object> map = new HashMap<String, Object>();
    	
		try {
			YwDistributor dis = new YwDistributor();
			dis.setDis_id(ywDistributor.getDis_id());
	    	YwDistributor distributor =  ywDistributorService.query(dis);
	    	if(distributor == null){											// 没有注册过分销商
	    		if(StringUtils.isBlank(ywDistributor.getDis_parentid())){
		    		//如果没有推荐人 , 则申请成为一级代理商
		    		ywDistributor.setDis_level("1");
		    	}else{
		    		// 如果有推荐人  , 获取推荐人信息
		    		YwDistributor parentDistributor = new YwDistributor();
		    		parentDistributor.setDis_id(ywDistributor.getDis_parentid());
		    		parentDistributor = ywDistributorService.query(parentDistributor);
		    		// 获取上级分销商的分销等级 , 自增加1
		    		int new_level = Integer.valueOf(parentDistributor.getDis_level()) + 1;
		    		ywDistributor.setDis_level(String.valueOf(new_level));
		    	}
	    		ywDistributor.setDis_status("1");	
		    	ywDistributor.setDis_apply_time(new Date());
	    		ywDistributorService.insert(ywDistributor);
	    		
	    		map.put("error_no", "0");
		    	map.put("error_info", "提交成功,请耐心等待审核");
	    	}else{																// 如果已经注册过分销商
	    		map.put("error_no", "0");
		    	map.put("error_info", "您已经是该平台分销商,请勿重复注册");
	    	}
	    	
		} catch (Exception e) {
			logger.info("错误信息:{}",e);
			throw new BizException("系统忙,请稍后再试",e);
		}
    	
    	return map;
    }
	
	/**
	 * 分销商信息查询
	 */
	@RequestMapping(ConstantFunctionsFront.YW_DIS_00101)
	@ResponseBody
	//@ResponseBodyFilter(value = { "dis_id","child_Distributors","parent_Distributor","nickname" })
	public Map<String, Object> queryDistribution(YwDistributor ywDistributor){
		Map<String, Object> map = new HashMap<String, Object>();
		YwDistributor distributor = null;
		try {
			distributor = ywDistributorService.query(ywDistributor);
			if(null == distributor){
				throw new BizException("没有分销商数据");
			}
			
		} catch (Exception e) {
			throw new BizException(e);
		}
		YwMember ywMember = ywMemberService.queryByOpenid(ywDistributor.getDis_id());
		ywMember.setNickname(Base32.decode(ywMember.getNickname()));
		YwWallet ywWallet = new YwWallet();
		ywWallet.setUser_id(ywDistributor.getDis_id());
		ywWallet.setWa_type("1");
		ywWallet = ywWalletService.query(ywWallet);
		map.put("ywMember", ywMember);			//会员对象
		map.put("distributor", distributor);	//分销商对象
		map.put("ywWallet", ywWallet);			//钱包对象
		
		return map;
	}
	
	
	/**
	 * 上级分销商信息查询
	 */
	@RequestMapping(ConstantFunctionsFront.YW_DIS_00102)
	@ResponseBody
	@ResponseBodyFilter(value = { "dis_id","nickname" })
	public Map<String, Object> queryParentDistribution(YwDistributor ywDistributor){
		Map<String, Object> map = new HashMap<String, Object>();
		YwDistributorDto ywDistributorDto = null;
		try {
			
			ywDistributorDto = ywDistributorService.queryParent(ywDistributor);
			
		} catch (Exception e) {
			throw new BizException(e);
		}
		map = MapUtil.toMap(ywDistributorDto);
		return map;
	}
	
	/**
	 * 下级分销商信息查询
	 */
	@RequestMapping(ConstantFunctionsFront.YW_DIS_00103)
	@ResponseBody
	//@ResponseBodyFilter
	public List<Map<String, Object>> queryChildDistribution(YwDistributor ywDistributor){
		List<Map<String, Object>> maps = new ArrayList<Map<String,Object>>();
		List<YwDistributor> distributors = null;
		try {
			YwDistributor distributor = new YwDistributor();
			distributor.setDis_parentid(ywDistributor.getDis_id());
			distributors = ywDistributorService.queryList(distributor);		//查询下级分销商
			if(distributors.size() > 0){
				for (YwDistributor ywDistributor2 : distributors) {
					Map<String, Object> map = new HashMap<String, Object>();
					YwDistributorSalestatus ywDistributorSalestatus = new YwDistributorSalestatus();
					ywDistributorSalestatus.setDis_id(ywDistributor2.getDis_id());
					List<YwDistributorSalestatus> ywDistributorSalestatuses = ywDistributorSalestatusService.queryList(ywDistributorSalestatus);
					YwMember ywMember = ywMemberService.queryByOpenid(ywDistributor2.getDis_id());
					map = MapUtil.toMap(ywMember);
					map.put("distributor", ywDistributor2);
					map.put("spread_length", ywDistributorSalestatuses.size());
					maps.add(map);
				}
			}else{
				throw new BizException(ContantErrorNo.ERROR_NO_100,ContantErrorNo.ERROR_INFO_100);
			}
			
		} catch (Exception e) {
			throw new BizException(e);
		}
			
			
		return maps;
	}
	
	
	/**
	 * 分销商佣金已提现金额 与 余额查询
	 */
	@RequestMapping(ConstantFunctionsFront.YW_DIS_00104)
	@ResponseBody
	@ResponseBodyFilter(value = {"use_moeny","wa_moeny"})	//use_moeny 已提现金额 , wa_moeny 余额
	public Map<String, Object> queryDistributionCommission(@Valid @NotBlank String dis_id){
		Map<String, Object> map = new HashMap<String, Object>();
		YwWallet ywWallet = null;
		try {
			// 查询钱包余额
			ywWallet = new YwWallet();
			ywWallet.setUser_id(dis_id);
			ywWallet.setWa_type("1");
			ywWallet = ywWalletService.query(ywWallet);
			map = MapUtil.toMap(ywWallet);
			// 查询已提现金额
			YwDistributorWithdrawalrecord ywDistributorWithdrawalrecord = new YwDistributorWithdrawalrecord();
			ywDistributorWithdrawalrecord.setUser_id(dis_id);
			ywDistributorWithdrawalrecord.setDis_wid_type("1");
			ywDistributorWithdrawalrecord.setDis_wid_status("2");// 提现成功
			double totals = ywDistributorWithdrawalrecordService.queryWithdrawalTotals(ywDistributorWithdrawalrecord);
			map.put("use_moeny", totals);
			
		} catch (Exception e) {
			throw new BizException(e);
		}
		
		return map;
	}
	
	/**
	 * 分销商订单总数
	 */
	@RequestMapping(ConstantFunctionsFront.YW_DIS_00105)
	@ResponseBody
	//@ResponseBodyFilter
	public Map<String, Object> queryDistributionOrderTotals(@Valid @NotBlank String dis_id){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			YwOrder ywOrder = new YwOrder();
			ywOrder.setOpenid(dis_id);
			ywOrder.setOrder_type("1");
			List<YwOrder> orders = ywOrderService.queryList(ywOrder);
			if(null != orders){
				map.put("totals", orders.size());
			}
		} catch (Exception e) {
			throw new BizException(e);
		}
		return map;
	}
	
	/**
	 * 按指定类型查询佣金订单
	 */
	@RequestMapping(ConstantFunctionsFront.YW_DIS_00108)
	@ResponseBody
	//@ResponseBodyFilter
	public List<Map<String, Object>> queryDistributionOrders(@Valid @NotBlank String dis_id , @Valid @NotBlank String order_status){
		List<Map<String, Object>> maps = new ArrayList<Map<String,Object>>();
		List<YwOrder> orders = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("openid", dis_id);
			map.put("order_type", "2");
			
			if(order_status.equals("0")){
				orders = ywOrderService.queryUnionList(map);
				if(orders.size() == 0){
					throw new BizException(ContantErrorNo.ERROR_NO_100,ContantErrorNo.ERROR_INFO_100);
				}
			}else{
				map.put("order_status", order_status);
				orders = ywOrderService.queryUnionList(map);
				if(orders.size() == 0){
					throw new BizException(ContantErrorNo.ERROR_NO_100,ContantErrorNo.ERROR_INFO_100);
				}
			}
			
		} catch (Exception e) {
			throw new BizException(e);
		}
		maps = MapUtil.toMapList(orders);
		return maps;
	}
	
	/**
	 * 初始页面默认查询全部订单
	 */
	@RequestMapping(ConstantFunctionsFront.YW_DIS_00110)
	@ResponseBody
	//@ResponseBodyFilter
	public List<Map<String, Object>> queryAllDingdan(@Valid @NotBlank String dis_id){
		List<Map<String, Object>> maps = new ArrayList<Map<String,Object>>();
		List<YwOrder> orders = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("openid", dis_id);
			map.put("order_type", "2");
			orders = ywOrderService.queryUnionList(map);
			if(orders.size() == 0){
				throw new BizException(ContantErrorNo.ERROR_NO_100,ContantErrorNo.ERROR_INFO_100);
			}
			
		} catch (Exception e) {
			throw new BizException(e);
		}
		maps = MapUtil.toMapList(orders);
		return maps;
	}
	
	
	/**
	 * 分销商提现记录总数
	 */
	@RequestMapping(ConstantFunctionsFront.YW_DIS_00106)
	@ResponseBody
	//@ResponseBodyFilter
	public Map<String, Object> queryDistributionWithdrawalrecordTotals(@Valid @NotBlank String dis_id){
		Map<String, Object> map = new HashMap<String, Object>();
		List<YwDistributorWithdrawalrecord> withdrawalrecords = null;
		try {
			YwDistributorWithdrawalrecord ywDistributorWithdrawalrecord = new YwDistributorWithdrawalrecord();
			ywDistributorWithdrawalrecord.setUser_id(dis_id);
			ywDistributorWithdrawalrecord.setDis_wid_type("1");
			withdrawalrecords = ywDistributorWithdrawalrecordService.queryList(ywDistributorWithdrawalrecord);
			if(null != withdrawalrecords){
				map.put("totals", withdrawalrecords.size());
			}
		} catch (Exception e) {
			throw new BizException(e);
		}
		return map;
	}
	
	/**
	 * 分销商提现记录
	 */
	@RequestMapping(ConstantFunctionsFront.YW_DIS_00107)
	@ResponseBody
	//@ResponseBodyFilter
	public List<Map<String, Object>> queryDistributionWithdrawalrecords(@Valid @NotBlank String dis_id){
		List<Map<String, Object>> maps = new ArrayList<Map<String,Object>>();
		List<YwDistributorWithdrawalrecord> withdrawalrecords = null;
		try {
			YwDistributorWithdrawalrecord ywDistributorWithdrawalrecord = new YwDistributorWithdrawalrecord();
			ywDistributorWithdrawalrecord.setUser_id(dis_id);
			ywDistributorWithdrawalrecord.setDis_wid_type("1");
			withdrawalrecords = ywDistributorWithdrawalrecordService.queryList(ywDistributorWithdrawalrecord);
		} catch (Exception e) {
			throw new BizException(e);
		}
		maps = MapUtil.toMapList(withdrawalrecords);
		return maps;
	}
	
	/**
	 * 分销商品支付成功后处理
	 */
	@RequestMapping(ConstantFunctionsFront.YW_DIS_00109)
	@ResponseBody
	public Map<String, Object> distributionSuccess(String distributorData,String orderData){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			YwDistributor ywDistributor = JSONObject.parseObject(distributorData, YwDistributor.class);
			YwOrder ywOrder = JSONObject.parseObject(orderData, YwOrder.class);
			YwDistributor dis = new YwDistributor();
			dis.setDis_id(ywDistributor.getDis_id());
			YwDistributor distributor = ywDistributorService.query(dis);
			if(StringUtils.equals("2", distributor.getDis_status())){		//如果是通过审核的分销商
				// 判断商品是否为特殊分销商品
				// 查到对应的订单项
				YwOrderItem ywOrderItem = new YwOrderItem();
				ywOrderItem.setOrder_sn(ywOrder.getOrder_sn());
				YwOrderItem orderitem = ywOrderItemService.query(ywOrderItem);
				// 通过商品编号查找是否有对应的特殊分销商品 
				YwDistributionProduct ywDistributionProduct = new YwDistributionProduct();
				ywDistributionProduct.setProduct_id(orderitem.getProduct_id());
				ywDistributionProduct = ywDistributionProductService.query(ywDistributionProduct);
				if(null != ywDistributionProduct){	// 说明该笔订单交易的为特殊分销商品 , 按照特殊分销商品佣金计算
					ywDistributorService.createYwDisProduct(distributor,ywOrder,ywDistributionProduct);
				}else{								// 说明该笔订单并非特殊分销商品 , 按照分销佣金规则计算
					//判断分销商是否存在上级
					if(StringUtils.isBlank(distributor.getDis_parentid())){
						ywDistributorService.createNotDisPid(distributor, ywOrder);
					}else{
						ywDistributorService.createDisPid(distributor, ywOrder);
					}
				}
				
				ywDistributorService.createDisSas(distributor, ywOrder);
			}
		} catch (Exception e) {
			logger.info("分销错误：",e);
			throw new BizException("分销信息存储失败",e);
		}
		return map;
	}
}