package com.yunwei.product.backend.sales.controller;

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

import com.yunwei.common.util.Base32;
import com.yunwei.common.util.MapUtil;
import com.yunwei.common.util.Paginator;
import com.yunwei.product.backend.service.YwDistributorWithdrawalrecordService;
import com.yunwei.product.backend.service.YwMemberService;
import com.yunwei.product.backend.service.YwWalletService;
import com.yunwei.product.common.backend.model.dto.YwDistributorWithdrawalrecordDto;
import com.yunwei.product.common.model.YwDistributorWithdrawalrecord;
import com.yunwei.product.common.model.YwMember;

/**
 * 
* @ClassName: YwDistributorWithdrawalrecordManage 
* @Description: 提现记录
* @author zhangjh
* @date 2018年3月30日 下午6:34:33 
*
 */
@Controller
@RequestMapping("/sales/ywDistributorWithdrawalrecord")
public class YwDistributorWithdrawalrecordManage {
	
	@Autowired
	private YwDistributorWithdrawalrecordService ywDistributorWithdrawalrecordService;
	@Autowired
	private YwWalletService ywWalletService;
	@Autowired
	private YwMemberService ywMemberService;

	@RequestMapping
	public String ywDistributorWithdrawalrecordList(Model model){
		return "/sales/distributorwithdrawalrecord/ywDistributorWithdrawalrecordList";
	}
	
	/**
	 * 查询字典集合
	 * @param gwproduct
	 * @return
	 */
	@RequestMapping("getList")
	@ResponseBody
	public Map<String, Object> getList(YwDistributorWithdrawalrecord ywDistributorWithdrawalrecord,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		List<YwDistributorWithdrawalrecordDto> ywDistributorWithdrawalrecordDtos = null;
		if (page <= 0) {
			page = 1;// 第一页
		}
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		map.put("begin", start);
		map.put("end", pageSize); // 设置每页显示几条数据
		map.putAll(MapUtil.toMap(ywDistributorWithdrawalrecord));
		ywDistributorWithdrawalrecordDtos = ywDistributorWithdrawalrecordService.queryUnionMemberList(map);
		List<YwDistributorWithdrawalrecordDto> list = new ArrayList<YwDistributorWithdrawalrecordDto>();
		for (YwDistributorWithdrawalrecordDto ywDistributorWithdrawalrecordDto : ywDistributorWithdrawalrecordDtos) {
			ywDistributorWithdrawalrecordDto.setNickname(Base32.decode(ywDistributorWithdrawalrecordDto.getNickname()));
			list.add(ywDistributorWithdrawalrecordDto);
			if(list.size() == pageSize) break;
		}
		
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
	public Paginator getPaginator(YwDistributorWithdrawalrecord ywDistributorWithdrawalrecord,@RequestParam(defaultValue = "10") int pageSize){
		int count = ywDistributorWithdrawalrecordService.queryTotals(ywDistributorWithdrawalrecord);
		return new Paginator(1, pageSize, count);
	}
	
	/**
	 * 新增或修改页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(YwDistributorWithdrawalrecord ywDistributorWithdrawalrecord,String op_type,Model model){
		model.addAttribute("op_type", op_type);
		if(StringUtils.equals(op_type, "2")){
			ywDistributorWithdrawalrecord = ywDistributorWithdrawalrecordService.query(ywDistributorWithdrawalrecord);
			model.addAttribute("ywDistributorWithdrawalrecord", ywDistributorWithdrawalrecord);
			YwMember ywMember = ywMemberService.queryByOpenid(ywDistributorWithdrawalrecord.getUser_id());
			ywMember.setNickname(Base32.decode(ywMember.getNickname()));
			model.addAttribute("ywMember",ywMember);
			return "/sales/distributorwithdrawalrecord/ywDistributorWithdrawalrecordEdit";
		} else if(StringUtils.equals(op_type, "3")){
			//ywDistributorWithdrawalrecord = ywDistributorWithdrawalrecordService.query(ywDistributorWithdrawalrecord);
			//model.addAttribute("ywDistributorWithdrawalrecord", ywDistributorWithdrawalrecord);
			return "/sales/distributorwithdrawalrecord/ywDistributorWithdrawalrecordRemark";
		}else {
			model.addAttribute("ywDistributorWithdrawalrecord", ywDistributorWithdrawalrecord);
			return "/sales/distributorwithdrawalrecord/ywDistributorWithdrawalrecordEdit";
		}
	}
	
	/**
	 * 新增或修改
	 * @return
	 */
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Map<String,Object> insertOrUpdate(YwDistributorWithdrawalrecord ywDistributorWithdrawalrecord,String op_type){
		Map<String,Object> map = new HashMap<String, Object>();
		if(StringUtils.equals(op_type, "1")){
			ywDistributorWithdrawalrecordService.insert(ywDistributorWithdrawalrecord);
			map.put("error_no", "0");
			map.put("error_info", "添加成功");
		} else {
			YwDistributorWithdrawalrecord distributorWithdrawalrecord = new YwDistributorWithdrawalrecord();
			distributorWithdrawalrecord.setDis_wid_id(ywDistributorWithdrawalrecord.getDis_wid_id());
			distributorWithdrawalrecord = ywDistributorWithdrawalrecordService.query(distributorWithdrawalrecord);
			distributorWithdrawalrecord.setDis_wid_updatetime(new Date());
			distributorWithdrawalrecord.setDis_wid_remark(ywDistributorWithdrawalrecord.getDis_wid_remark());
			distributorWithdrawalrecord.setDis_wid_status(ywDistributorWithdrawalrecord.getDis_wid_status());
			
			if(StringUtils.equals(ywDistributorWithdrawalrecord.getDis_wid_status(), "3")){
				// 不同意提现请求时 , 将之前钱包余额扣掉的金额返还
				int num = ywWalletService.walletBackCommissionCash(distributorWithdrawalrecord.getUser_id(), Double.valueOf(distributorWithdrawalrecord.getDis_wid_money()));
				if(num > 0){
					ywDistributorWithdrawalrecordService.update(distributorWithdrawalrecord);
					map.put("error_no", "0");
					map.put("error_info", "修改成功");
				}
			}else{
				ywDistributorWithdrawalrecordService.update(distributorWithdrawalrecord);
				map.put("error_no", "0");
				map.put("error_info", "修改成功");
			}
		}
		return map;
	}
	
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Map<String,Object> delete(YwDistributorWithdrawalrecord ywDistributorWithdrawalrecord){
		Map<String,Object> map = new HashMap<String, Object>();
		ywDistributorWithdrawalrecordService.delete(ywDistributorWithdrawalrecord);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping("deleteBatch")
	@ResponseBody
	public Map<String,Object> deleteBatch(YwDistributorWithdrawalrecord ywDistributorWithdrawalrecord,String dis_wid_ids){
		Map<String,Object> map = new HashMap<String, Object>();
		String[] strings = dis_wid_ids.split(",");
		Map<String, String[]> strMap = new HashMap<String, String[]>();
		strMap.put("dis_wid_id",strings);
		ywDistributorWithdrawalrecordService.deleteBatch(strMap);
		map.put("error_no", "0");
		map.put("error_info", "删除成功");
		return map;
	}	
}
