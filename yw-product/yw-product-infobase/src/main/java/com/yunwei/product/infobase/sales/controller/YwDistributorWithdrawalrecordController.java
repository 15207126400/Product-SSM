package com.yunwei.product.infobase.sales.controller;

import java.util.ArrayList;
import java.util.Date;
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

import com.yunwei.common.exception.BizException;
import com.yunwei.common.util.MapUtil;
import com.yunwei.product.common.backend.model.dto.YwDistributorWithdrawalrecordDto;
import com.yunwei.product.common.model.YwDistributorWithdrawalrecord;
import com.yunwei.product.common.model.YwTeamActivity;
import com.yunwei.product.common.model.YwWallet;
import com.yunwei.product.infobase.constant.ConstantFunctionsFront;
import com.yunwei.product.infobase.service.YwDistributorWithdrawalrecordService;
import com.yunwei.product.infobase.service.YwWalletService;

/**
 * 
* @ClassName: YwDistributorWithdrawalrecordManage 
* @Description: 提现记录表
* @author zhangjh
* @date 2018年3月30日 下午6:34:33 
*
 */
@Controller
public class YwDistributorWithdrawalrecordController {
	
	@Autowired
	private YwDistributorWithdrawalrecordService ywDistributorWithdrawalrecordService;
	@Autowired
	private YwWalletService ywWalletService;

	/**
	 * 提现记录列表查询
	 */
	@RequestMapping(ConstantFunctionsFront.YW_DIS_00902)
	@ResponseBody
	//@ResponseBodyFilter
	public List<Map<String, Object>> queryTeamProducts(YwDistributorWithdrawalrecord ywDistributorWithdrawalrecord,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int pageSize){
		List<Map<String, Object>> maps = new ArrayList<Map<String,Object>>();
		List<YwDistributorWithdrawalrecordDto> ywDistributorWithdrawalrecordDtos = null;
		try {
			if (page <= 0) {
				page = 1;// 第一页
			}
			int start = (page - 1) * pageSize;
			Map<String, Object> map = MapUtil.toMap(ywDistributorWithdrawalrecord);
			map.put("begin", start);
			map.put("end", pageSize); // 设置每页显示几条数据
			ywDistributorWithdrawalrecordDtos = ywDistributorWithdrawalrecordService.queryUnionMemberList(map);
		} catch (Exception e) {
			throw new BizException(e);
		}
		maps = MapUtil.toMapList(ywDistributorWithdrawalrecordDtos);
		return maps;
	}
	
	
	@RequestMapping(ConstantFunctionsFront.YW_DIS_00903)
	@ResponseBody
	public Map<String, Object> applicationForCash(YwDistributorWithdrawalrecord ywDistributorWithdrawalrecord){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 插入提现记录 , 状态设置为提现中
			ywDistributorWithdrawalrecord.setDis_wid_createtime(new Date());
			ywDistributorWithdrawalrecord.setDis_wid_status("1");
			ywDistributorWithdrawalrecord.setDis_wid_remark("提现申请");
			int num = ywDistributorWithdrawalrecordService.insert(ywDistributorWithdrawalrecord);
			if(num > 0){		// 若果修改成功 , 从钱包扣除提现的金额
				ywWalletService.walletByCommissionCash(ywDistributorWithdrawalrecord.getUser_id(), Double.valueOf(ywDistributorWithdrawalrecord.getDis_wid_money()));
			}
		} catch (Exception e) {
			throw new BizException("提现失败");
		}
		return map;
	}
}
