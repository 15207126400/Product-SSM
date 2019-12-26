package com.yunwei.product.infobase.sales.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunwei.common.exception.BizException;
import com.yunwei.common.util.MapUtil;
import com.yunwei.product.common.model.YwDistributorWithdrawalrecord;
import com.yunwei.product.common.model.YwWallet;
import com.yunwei.product.infobase.constant.ConstantFunctionsFront;
import com.yunwei.product.infobase.constant.ContantErrorNo;
import com.yunwei.product.infobase.service.YwDistributorWithdrawalrecordService;
import com.yunwei.product.infobase.service.YwWalletService;

/**
 * 
* @ClassName: YwWalletController 
* @Description: 提现模块
* @author 晏飞
* @date 2018年4月16日 下午4:56:53 
*
 */
@Controller
public class YwWalletController {
	
	@Autowired
	private YwDistributorWithdrawalrecordService ywDistributorWithdrawalrecordService;
	@Autowired
	private YwWalletService ywWalletService;
	
	/**
	 * 初始页面默认查询全部订单
	 */
	@RequestMapping(ConstantFunctionsFront.YW_DIS_00900)
	@ResponseBody
	//@ResponseBodyFilter
	public List<Map<String, Object>> queryAllMingxi(@Valid @NotBlank String dis_id){
		List<Map<String, Object>> maps = new ArrayList<Map<String,Object>>();
		List<YwDistributorWithdrawalrecord> ywDistributorWithdrawalrecords = null;
		try {
			YwDistributorWithdrawalrecord distributorWithdrawalrecord = new YwDistributorWithdrawalrecord();
			distributorWithdrawalrecord.setUser_id(dis_id);
			distributorWithdrawalrecord.setDis_wid_type("1");
			ywDistributorWithdrawalrecords = ywDistributorWithdrawalrecordService.queryList(distributorWithdrawalrecord);
			if(ywDistributorWithdrawalrecords.size() == 0){
				throw new BizException(ContantErrorNo.ERROR_NO_100,ContantErrorNo.ERROR_INFO_100);
			}
			
		} catch (Exception e) {
			throw new BizException(e);
		}
		maps = MapUtil.toMapList(ywDistributorWithdrawalrecords);
		return maps;
	}
	
	/**
	 * 按指定状态查询提现明细
	 */
	@RequestMapping(ConstantFunctionsFront.YW_DIS_00901)
	@ResponseBody
	//@ResponseBodyFilter
	public List<Map<String, Object>> queryMingxiByType(@Valid @NotBlank String dis_id , @Valid @NotBlank String dis_wid_status){
		List<Map<String, Object>> maps = new ArrayList<Map<String,Object>>();
		List<YwDistributorWithdrawalrecord> ywDistributorWithdrawalrecords = null;
		try {
			YwDistributorWithdrawalrecord distributorWithdrawalrecord = new YwDistributorWithdrawalrecord();
			distributorWithdrawalrecord.setUser_id(dis_id);
			distributorWithdrawalrecord.setDis_wid_type("1");
			if(dis_wid_status.equals("0")){
				ywDistributorWithdrawalrecords = ywDistributorWithdrawalrecordService.queryList(distributorWithdrawalrecord);
			}else{
				distributorWithdrawalrecord.setDis_wid_status(dis_wid_status);
				ywDistributorWithdrawalrecords = ywDistributorWithdrawalrecordService.queryList(distributorWithdrawalrecord);
			}
			
			if(ywDistributorWithdrawalrecords.size() == 0){
				throw new BizException(ContantErrorNo.ERROR_NO_100,ContantErrorNo.ERROR_INFO_100);
			}
			
		} catch (Exception e) {
			throw new BizException(e);
		}
		maps = MapUtil.toMapList(ywDistributorWithdrawalrecords);
		return maps;
	}
	
	/**
	 * 查询钱包余额
	*
	*@return
	 */
	@RequestMapping(ConstantFunctionsFront.YW_DIS_01000)
	@ResponseBody
	public Map<String, Object> queryWallet(YwWallet ywWallet){
		Map<String, Object> map = new HashMap<String, Object>();
		ywWallet = ywWalletService.query(ywWallet);
		map.put("ywWallet", ywWallet);
		
		return map;
	}
}
