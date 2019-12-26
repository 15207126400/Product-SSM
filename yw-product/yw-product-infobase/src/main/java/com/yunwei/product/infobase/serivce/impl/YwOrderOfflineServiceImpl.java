package com.yunwei.product.infobase.serivce.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.common.util.OrderUtil;
import com.yunwei.context.payment.util.WxModelUtil;
import com.yunwei.product.common.dao.YwOrderOfflineDao;
import com.yunwei.product.common.infobase.model.form.YwOrderOfflineForm;
import com.yunwei.product.common.model.YwMember;
import com.yunwei.product.common.model.YwOrderOffline;
import com.yunwei.product.common.model.YwOrderPayment;
import com.yunwei.product.infobase.service.YwMemberService;
import com.yunwei.product.infobase.service.YwOrderOfflineService;
import com.yunwei.product.infobase.service.YwOrderPaymentService;

@Service
public class YwOrderOfflineServiceImpl extends IBaseServiceImpl<YwOrderOffline> implements YwOrderOfflineService {
	private static Logger logger = Logger.getLogger(YwOrderOfflineServiceImpl.class);
	@Autowired
	private YwOrderOfflineDao ywOrderOfflineDao;
	@Autowired
	private YwMemberService ywMemberService;
	@Autowired
	private YwOrderPaymentService ywOrderPaymentService;

	@Override
	protected IBaseDao<YwOrderOffline> getBaseDao() {
		return ywOrderOfflineDao;
	}

	@Override
	public String orderOfflineCreate(YwOrderOfflineForm ywOrderOfflineForm) throws Exception {
		YwOrderOffline ywOrderOffline = new YwOrderOffline();
		BeanUtils.copyProperties(ywOrderOffline, ywOrderOfflineForm);
		ywOrderOffline.setOrder_sn(OrderUtil.getOrderNo());
		ywOrderOffline.setCreate_datetime(new Date());
		ywOrderOffline.setOffline_status("0");
		// 查询客户信息
		YwMember ywMember  = ywMemberService.queryByOpenid(ywOrderOfflineForm.getOpenid());
		if(ywMember != null){
			ywOrderOffline.setNickname(ywMember.getNickname());
		}
		// 添加订单信息
		super.insert(ywOrderOffline);
		
		// 创建支付信息
		YwOrderPayment ywOrderPayment = new YwOrderPayment();
		ywOrderPayment.setOpen_id(ywOrderOffline.getOpenid());
		ywOrderPayment.setOrder_pay_price(ywOrderOffline.getOrder_totalprice());
		ywOrderPayment.setOrder_sn(ywOrderOffline.getOrder_sn());
		ywOrderPayment.setOrder_pay_createtime(new Date());
		ywOrderPayment.setOrder_pay_remark(ywOrderOffline.getOffline_remark());
		ywOrderPayment.setOrder_pay_returnUrl(WxModelUtil.notify_url);
		ywOrderPayment.setOrder_pay_status("0");
		ywOrderPayment.setOrder_pay_type(ywOrderOfflineForm.getOrder_pay_type());
		ywOrderPayment.setOrder_pay_method(ywOrderOfflineForm.getOrder_pay_method());
		ywOrderPayment.setBranch_id(ywOrderOffline.getBranch_id());
		ywOrderPaymentService.insert(ywOrderPayment);
		return ywOrderOffline.getOrder_sn();
	}
	
	

}

