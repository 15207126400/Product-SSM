package com.yunwei.product.infobase.salon.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunwei.common.exception.BizException;
import com.yunwei.product.common.model.QhzSalonAccount;
import com.yunwei.product.common.model.QhzCurriculum;
import com.yunwei.product.common.model.QhzSmsDetail;
import com.yunwei.product.infobase.constant.ConstantFunctionsFront;
import com.yunwei.product.infobase.service.QhzSalonAccountService;
import com.yunwei.product.infobase.service.QhzCurriculumService;
import com.yunwei.product.infobase.service.QhzSmsDetailService;

/**
 * 启恒智沙龙注册用户接口
* @ClassName: QhzSalonAccountController 
* @Description: TODO(TODO) 
* @author 晏飞
* @date 2019年6月22日 上午9:02:52 
*
 */
@Controller
public class QhzSalonAccountController {

	@Autowired
	private QhzSalonAccountService qhzSalonAccountService;
	
	@Autowired
	private QhzCurriculumService qhzSalonCurriculumService;
	
	@Autowired
	private QhzSmsDetailService qhzSmsDetailService;
	
	/**
	 * 根据openid查询用户信息
	*
	*@param qhzSalonAccount
	*@return
	*Map<String,Object>
	 */
	@RequestMapping(ConstantFunctionsFront.QHZ_SL001)
	@ResponseBody
	public Map<String, Object> queryAccountByOpenid(QhzSalonAccount qhzSalonAccount){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			qhzSalonAccount = qhzSalonAccountService.query(qhzSalonAccount);
			if(qhzSalonAccount != null){
				//如果课程存在值,则获取课程信息
				if(StringUtils.isNotBlank(qhzSalonAccount.getExisting_ids())){
					String[] ids = qhzSalonAccount.getExisting_ids().split(",");
					StringBuffer sb = new StringBuffer();
					for(String id : ids){
						QhzCurriculum curriculum = qhzSalonCurriculumService.query(id);
						if(curriculum != null){
							sb.append(curriculum.getTitle() + ",");
						}
					}
					qhzSalonAccount.setExisting_names((sb.toString()).substring(0 , sb.length() - 1));
				}
				
				map.put("code", 1);
				map.put("account", qhzSalonAccount);
			} else {
				map.put("code", -1);
				map.put("account", new QhzSalonAccount());
			}
		} catch (Exception e) {
			throw new BizException("【根据openid查询用户信息】 : " + e);
		}
		
		return map;
	}
	
	/**
	 * 保存用户注册信息
	*
	*@param qhzSalonAccount
	*@return
	*Map<String,Object>
	 */
	@RequestMapping(ConstantFunctionsFront.QHZ_SL002)
	@ResponseBody
	public Map<String, Object> saveAccount(QhzSalonAccount qhzSalonAccount){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			//查询用户是否已经存在
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("openid",qhzSalonAccount.getOpenid());
			QhzSalonAccount account = qhzSalonAccountService.query(paramMap);
			if(account != null){
				map.put("code", "-1");
			} else {
				qhzSalonAccount.setCreate_datetime(new Date());
				qhzSalonAccount.setSc_price("0.00");				//初始账号奖学金默认为0.00
				qhzSalonAccountService.insert(qhzSalonAccount);
				map.put("code", "1");
				//发送短信
				QhzSmsDetail qhzSmsDetail = new QhzSmsDetail();
				qhzSmsDetail.setType("1");
				qhzSmsDetail.setMobile(qhzSalonAccount.getTel());
				//组装短信内容
				StringBuffer content = new StringBuffer();
				content.append("尊敬的" + qhzSalonAccount.getName());
				content.append(" ：您好 ! 恭喜您注册成功 , 非常感谢您对启恒智的支持 ! ");
				content.append("您今后的专属学习顾问 : " + qhzSalonAccount.getAdviser());
				qhzSmsDetail.setContent(content.toString());
				qhzSmsDetail.setCreateTime(new Date());
				String result = qhzSmsDetailService.sendSms(qhzSmsDetail);
				qhzSmsDetail.setMessage(result);
				qhzSmsDetailService.insert(qhzSmsDetail);
			}
			
			map.put("account", qhzSalonAccountService.query(paramMap));
		} catch (Exception e) {
			throw new BizException("【保存用户注册信息】 : " + e);
		}
		
		return map;
	}
	
	/**
	 * 报名提交账户信息(补全新的信息)
	*
	*void
	 */
	@RequestMapping(ConstantFunctionsFront.QHZ_SL003)
	@ResponseBody
	public void updateAccount(QhzSalonAccount qhzSalonAccount){
		Map<String, Object> accountMap = new HashMap<String , Object>();
		try {
			accountMap.put("openid", qhzSalonAccount.getOpenid());
			QhzSalonAccount account = qhzSalonAccountService.query(accountMap);
			account.setChild_name(qhzSalonAccount.getChild_name());
			account.setChild_card(qhzSalonAccount.getChild_card());
			//字符串合并,去重
			if(StringUtils.isNotBlank(account.getExisting_ids())){
				String existing_ids = mergeRemoval(qhzSalonAccount.getExisting_ids(),account.getExisting_ids());
				account.setExisting_ids(existing_ids);
			} else {
				account.setExisting_ids(qhzSalonAccount.getExisting_ids());
			}
			account.setUpdate_datetime(new Date());
			qhzSalonAccountService.update(account);
		} catch (Exception e) {
			throw new BizException("【报名提交账户信息】 : " + e);
		}
	}
	
	/**
	 * 字符串合并 , 去重
	 */
	public String mergeRemoval(String str1 , String str2){
		String str = "";
		try {
			String[] arrays1 = str1.split(",");
			String[] arrays2 = str2.split(",");
			Set<String> setData = new HashSet<String>();
			for (String array : arrays1) {
				setData.add(array);
			}
			
			for (String array : arrays2) {
				setData.add(array);
			}
			
			str = StringUtils.join(setData.toArray(), ",");
			System.out.println("set中的数据为:" + str);
		} catch (Exception e) {
			throw new BizException("【字符串合并 , 去重】 : " + e);
		}
		return str;
	}
	
}
