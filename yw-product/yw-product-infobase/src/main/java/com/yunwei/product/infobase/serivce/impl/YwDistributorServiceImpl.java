package com.yunwei.product.infobase.serivce.impl;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.common.exception.BizException;
import com.yunwei.common.util.NumberUtil;
import com.yunwei.product.common.backend.model.dto.YwDistributorDto;
import com.yunwei.product.common.dao.YwDistributorDao;
import com.yunwei.product.common.model.YwDistributionProduct;
import com.yunwei.product.common.model.YwDistributor;
import com.yunwei.product.common.model.YwDistributorCommissionrecord;
import com.yunwei.product.common.model.YwDistributorRule;
import com.yunwei.product.common.model.YwDistributorSalestatus;
import com.yunwei.product.common.model.YwMember;
import com.yunwei.product.common.model.YwOrder;
import com.yunwei.product.infobase.service.YwDistributorCommissionrecordService;
import com.yunwei.product.infobase.service.YwDistributorRuleService;
import com.yunwei.product.infobase.service.YwDistributorSalestatusService;
import com.yunwei.product.infobase.service.YwDistributorService;
import com.yunwei.product.infobase.service.YwMemberService;
import com.yunwei.product.infobase.service.YwWalletService;

@Service
public class YwDistributorServiceImpl extends IBaseServiceImpl<YwDistributor> implements YwDistributorService {
	
	@Autowired
	private YwDistributorDao ywDistributorDao;
	@Autowired
	private YwDistributorRuleService ywDistributorRuleService;		//分销规则
	@Autowired
	private YwDistributorCommissionrecordService ywDistributorCommissionrecordService;	//佣金记录
	@Autowired
	private YwDistributorSalestatusService ywDistributorSalestatusService;		//销售记录
	@Autowired
	private YwWalletService ywWalletService;
	@Autowired
	private YwMemberService ywMemberService;

	@Override
	protected IBaseDao<YwDistributor> getBaseDao() {
		return ywDistributorDao;
	}
	
	public List<YwDistributorDto> queryUnionMemberList(Map<String,Object> paramMap){
		
		return ywDistributorDao.queryUnionMemberList(paramMap);
	}

	@Override
	public YwDistributorDto queryParent(YwDistributor ywDistributor) {
		YwDistributor distributor = this.query(ywDistributor);
		YwDistributorDto dto = new YwDistributorDto();
		if(distributor != null){
			YwMember ywMember = ywMemberService.queryByOpenid(distributor.getDis_parentid());
			try {
				// 拷贝对象时时间为空报错处理 
				ConvertUtils.register(new DateConverter(null), java.util.Date.class); 
				// 对象拷贝
				BeanUtils.copyProperties(dto, distributor);
				if(StringUtils.isNotBlank(ywMember.getNickname())){
					dto.setNickname(ywMember.getNickname());
				}
			} catch (Exception e) {
				throw new BizException(e);
			}
			
		}
		return dto;
	}

	@Override
	public YwDistributor queryChilds(YwDistributor ywDistributor) {
		return null;
	}
	
	public int createYwDisProduct(YwDistributor distributor, YwOrder ywOrder,YwDistributionProduct ywDistributionProduct){
		//判断分销商是否存在上级
		if(StringUtils.isBlank(distributor.getDis_parentid())){	// 没有上级
			// 计算一级分销商佣金
			double total_price = Double.valueOf(ywOrder.getOrder_totalprice());		//获取订单支付总金额 , 方便后续计算所得佣金
			double rule_scale_one = Double.valueOf(ywDistributionProduct.getDis_pro_onescale());	//获取佣金比例
			String proportion = NumberUtil.div(rule_scale_one, 100.00);			//将百分比转换为小数
			String dis_com_money = NumberUtil.mul(total_price, Double.valueOf(proportion));		//计算获得佣金
			
			//生成佣金记录
			YwDistributorCommissionrecord ywDistributorCommissionrecord = new YwDistributorCommissionrecord();
			ywDistributorCommissionrecord.setDis_com_type("1");		//佣金类型(默认为1,分销)
			ywDistributorCommissionrecord.setUser_id(distributor.getDis_id());		//用户编号
			ywDistributorCommissionrecord.setCreate_user_id(distributor.getDis_id());
			ywDistributorCommissionrecord.setOrder_id(ywOrder.getOrder_sn());		//订单编号
			ywDistributorCommissionrecord.setDis_com_money(String.valueOf(dis_com_money));
			ywDistributorCommissionrecord.setDis_com_createtime(new Date());
			ywDistributorCommissionrecord.setDis_com_status("0");
			int num = ywDistributorCommissionrecordService.insert(ywDistributorCommissionrecord);
			return num;
		}else{													// 有上级
			//计算当前分销商所得佣金
			double total_price = Double.valueOf(ywOrder.getOrder_totalprice());		//获取订单支付总金额 , 方便后续计算所得佣金
			double rule_scale_two = Double.valueOf(ywDistributionProduct.getDis_pro_twoscale());	//获取佣金比例
			String proportion_two = NumberUtil.div(rule_scale_two, 100.00);			//将百分比转换为小数
			String dis_com_money_two = NumberUtil.mul(total_price, Double.valueOf(proportion_two));	//计算获得佣金
			//计算上级分销商所得佣金
			double rule_scale_one = Double.valueOf(ywDistributionProduct.getDis_pro_onescale());
			String proportion_one = NumberUtil.div(rule_scale_one, 100.00);
			String dis_com_money_one = NumberUtil.mul(total_price, Double.valueOf(proportion_one));
				
			//生成二级分销商佣金记录
			YwDistributorCommissionrecord ywDistributorCommissionrecord_two = new YwDistributorCommissionrecord();
			ywDistributorCommissionrecord_two.setDis_com_type("1");		//佣金类型(默认为1,分销)
			ywDistributorCommissionrecord_two.setUser_id(distributor.getDis_id());		//用户编号
			ywDistributorCommissionrecord_two.setCreate_user_id(distributor.getDis_id());
			ywDistributorCommissionrecord_two.setOrder_id(ywOrder.getOrder_sn());		//订单编号
			ywDistributorCommissionrecord_two.setDis_com_money(String.valueOf(dis_com_money_two));
			ywDistributorCommissionrecord_two.setDis_com_createtime(new Date());
			ywDistributorCommissionrecord_two.setDis_com_status("0");
			ywDistributorCommissionrecordService.insert(ywDistributorCommissionrecord_two);
			//生成一级分销商佣金记录
			YwDistributorCommissionrecord ywDistributorCommissionrecord_one = new YwDistributorCommissionrecord();
			ywDistributorCommissionrecord_one.setDis_com_type("1");		//佣金类型(默认为1,分销)
			ywDistributorCommissionrecord_one.setUser_id(distributor.getDis_parentid());	//用户编号
			ywDistributorCommissionrecord_one.setCreate_user_id(distributor.getDis_id());
			ywDistributorCommissionrecord_one.setOrder_id(ywOrder.getOrder_sn());		//订单编号
			ywDistributorCommissionrecord_one.setDis_com_money(String.valueOf(dis_com_money_one));
			ywDistributorCommissionrecord_one.setDis_com_createtime(new Date());
			ywDistributorCommissionrecord_one.setDis_com_status("0");
			int num = ywDistributorCommissionrecordService.insert(ywDistributorCommissionrecord_one);
			
			return num;
		}
	}

	// 没有上级分销商的情况生成分销记录(分销规则表)
	@Override
	public int createNotDisPid(YwDistributor ywDistributor, YwOrder ywOrder) {
		//如果上级为空 , 说明没有上级分销商
		YwDistributorRule ywDistributorRule = new YwDistributorRule();
		ywDistributorRule.setRule_level_type("1");
		//获取分销商对应的分销规则
		YwDistributorRule distributorRule = ywDistributorRuleService.queryLast(ywDistributorRule);
		//进行佣金计算
		double total_price = Double.valueOf(ywOrder.getOrder_totalprice());		//获取订单支付总金额 , 方便后续计算所得佣金
		double rule_scale_one = Double.valueOf(distributorRule.getRule_scale_one());	//获取佣金比例
		String proportion = NumberUtil.div(rule_scale_one, 100.00);			//将百分比转换为小数
		String dis_com_money = NumberUtil.mul(total_price, Double.valueOf(proportion));		//计算获得佣金
		
		//生成佣金记录
		YwDistributorCommissionrecord ywDistributorCommissionrecord = new YwDistributorCommissionrecord();
		ywDistributorCommissionrecord.setDis_com_type("1");		//佣金类型(默认为1,分销)
		ywDistributorCommissionrecord.setUser_id(ywDistributor.getDis_id());		//用户编号
		ywDistributorCommissionrecord.setCreate_user_id(ywDistributor.getDis_id());
		ywDistributorCommissionrecord.setOrder_id(ywOrder.getOrder_sn());		//订单编号
		ywDistributorCommissionrecord.setDis_com_money(String.valueOf(dis_com_money));
		ywDistributorCommissionrecord.setDis_com_createtime(new Date());
		ywDistributorCommissionrecord.setDis_com_status("0");
		int num = ywDistributorCommissionrecordService.insert(ywDistributorCommissionrecord);
		
		return num;
	}


	// 有上级分销商的情况生成分销记录(分销规则表)
	@Override
	public int createDisPid(YwDistributor ywDistributor, YwOrder ywOrder) {
		//如果上级不为空 , 说明有上级分销商
		YwDistributorRule ywDistributorRule = new YwDistributorRule();
		ywDistributorRule.setRule_level_type("2");
		//获取分销商对应的分销规则
		YwDistributorRule distributorRule = ywDistributorRuleService.queryLast(ywDistributorRule);
		//计算当前分销商所得佣金
		double total_price = Double.valueOf(ywOrder.getOrder_totalprice());		//获取订单支付总金额 , 方便后续计算所得佣金
		double rule_scale_two = Double.valueOf(distributorRule.getRule_scale_two());	//获取佣金比例
		String proportion_two = NumberUtil.div(rule_scale_two, 100.00);			//将百分比转换为小数
		String dis_com_money_two = NumberUtil.mul(total_price, Double.valueOf(proportion_two));	//计算获得佣金
		//计算上级分销商所得佣金
		double rule_scale_one = Double.valueOf(distributorRule.getRule_scale_one());
		String proportion_one = NumberUtil.div(rule_scale_one, 100.00);
		String dis_com_money_one = NumberUtil.mul(total_price, Double.valueOf(proportion_one));
		
		//生成二级分销商佣金记录
		YwDistributorCommissionrecord ywDistributorCommissionrecord_two = new YwDistributorCommissionrecord();
		ywDistributorCommissionrecord_two.setDis_com_type("1");		//佣金类型(默认为1,分销)
		ywDistributorCommissionrecord_two.setUser_id(ywDistributor.getDis_id());		//用户编号
		ywDistributorCommissionrecord_two.setCreate_user_id(ywDistributor.getDis_id());
		ywDistributorCommissionrecord_two.setOrder_id(ywOrder.getOrder_sn());		//订单编号
		ywDistributorCommissionrecord_two.setDis_com_money(String.valueOf(dis_com_money_two));
		ywDistributorCommissionrecord_two.setDis_com_createtime(new Date());
		ywDistributorCommissionrecord_two.setDis_com_status("0");
		ywDistributorCommissionrecordService.insert(ywDistributorCommissionrecord_two);
		//生成一级分销商佣金记录
		YwDistributorCommissionrecord ywDistributorCommissionrecord_one = new YwDistributorCommissionrecord();
		ywDistributorCommissionrecord_one.setDis_com_type("1");		//佣金类型(默认为1,分销)
		ywDistributorCommissionrecord_one.setUser_id(ywDistributor.getDis_parentid());	//用户编号
		ywDistributorCommissionrecord_one.setCreate_user_id(ywDistributor.getDis_id());
		ywDistributorCommissionrecord_one.setOrder_id(ywOrder.getOrder_sn());		//订单编号
		ywDistributorCommissionrecord_one.setDis_com_money(String.valueOf(dis_com_money_one));
		ywDistributorCommissionrecord_one.setDis_com_createtime(new Date());
		ywDistributorCommissionrecord_one.setDis_com_status("0");
		int num = ywDistributorCommissionrecordService.insert(ywDistributorCommissionrecord_one);
		
		return num;
	}

	//生成销售记录
	@Override
	public int createDisSas(YwDistributor ywDistributor, YwOrder ywOrder) {
		//生成销售记录
		YwDistributorSalestatus ywDistributorSalestatus = new YwDistributorSalestatus();
		if(CollectionUtils.isNotEmpty(ywOrder.getProductForms())){
			ywDistributorSalestatus.setProduct_id(ywOrder.getProductForms().get(0).getId());
			ywDistributorSalestatus.setDis_st_amont(ywOrder.getProductForms().get(0).getCount());
		} else {
			ywDistributorSalestatus.setProduct_id(ywOrder.getYwOrderItems().get(0).getProduct_id());
			ywDistributorSalestatus.setDis_st_amont(ywOrder.getYwOrderItems().get(0).getOrder_ite_count());
		}
		
		ywDistributorSalestatus.setDis_id(ywDistributor.getDis_id());
		ywDistributorSalestatus.setDis_level(ywDistributor.getDis_level());
		ywDistributorSalestatus.setDis_st_moeny(ywOrder.getOrder_totalprice());
		ywDistributorSalestatus.setDis_st_datetime(new Date());
		ywDistributorSalestatus.setDis_st_buyer(ywOrder.getOpenid());
		int num = ywDistributorSalestatusService.insert(ywDistributorSalestatus);
		
		return num;
	}

	
	


}
