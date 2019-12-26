package com.yunwei.product.infobase.member.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunwei.common.exception.BizException;
import com.yunwei.common.util.Base32;
import com.yunwei.common.util.DateUtil;
import com.yunwei.common.util.FastJsonUtil;
import com.yunwei.common.util.NumberUtil;
import com.yunwei.common.util.RedisClientUtil;
import com.yunwei.context.sys.usercenter.service.YwUserService;
import com.yunwei.product.common.model.YwCoupon;
import com.yunwei.product.common.model.YwCouponCollectiondetails;
import com.yunwei.product.common.model.YwDistributor;
import com.yunwei.product.common.model.YwMember;
import com.yunwei.product.common.model.YwMemberRole;
import com.yunwei.product.infobase.constant.ConstantFunctionsFront;
import com.yunwei.product.infobase.service.YwCouponCollectiondetailsService;
import com.yunwei.product.infobase.service.YwCouponService;
import com.yunwei.product.infobase.service.YwDistributorService;
import com.yunwei.product.infobase.service.YwMemberAddressService;
import com.yunwei.product.infobase.service.YwMemberRoleService;
import com.yunwei.product.infobase.service.YwMemberService;
import com.yunwei.product.infobase.service.YwProductCollectionService;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
/**
 * 
* @ClassName: TextOnLoginFromCode 
* @Description: TODO(请求微信服务器类) 
* @author 晏飞
* @date 2017年11月15日 下午3:31:01 
*
 */

@Controller
public class YwMemberController {
	@Autowired
	private YwMemberService ywMemberService;
	@Autowired
	private YwDistributorService ywDistributorService;	//分销商
	@Autowired
	private YwProductCollectionService ywProductCollectionService;	//收藏接口
	@Autowired
	private YwMemberAddressService ywMemberAddressService;	//地址接口
	@Autowired
	private YwMemberRoleService surService;
	@Autowired
	private YwCouponCollectiondetailsService ywCouponCollectiondetailsService;	//优惠券接口
	@Autowired
	private YwCouponService scfService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private YwUserService ywUserService;
	
	
	/**
	 * 向微信服务器发送请求 用户登录时产生的code码 换取用户唯一标示 openid
	 */
	@RequestMapping(ConstantFunctionsFront.YW_OPENID_00000)
	@ResponseBody
	public Map<String, Object> TextOnLoginFromCode(String wx_appid , String code){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map = ywMemberService.gainedByOpenid(wx_appid, code);
		
        return map;
	}
	
	/**
	 * 微信小程序用户授权保存昵称
	 */
	@RequestMapping(ConstantFunctionsFront.YW_OPENID_00001)
	@ResponseBody
	public Map<String, Object> addwxuser(YwMember ywMember){
		Map<String, Object> map = new HashMap<String, Object>();
		map = ywMemberService.saveByNickname(ywMember);
		
		return map;
	}
	
	/**
	 * 注册成为会员
	 */
	@RequestMapping(ConstantFunctionsFront.YW_OPENID_00002)
	@ResponseBody
	public Map<String, Object> reguser(YwMember ywMember , String auditing){
		Map<String, Object> map = new HashMap<String, Object>();
		int num = ywMemberService.registerByMember(ywMember, auditing);
		if(num > 0){
			map.put("msg", "提交成功");
		}else{
			map.put("msg", "提交失败");
		}
		
		return map;
	}
	
	/**
	 * 微信小程序查询展示用户身份信息
	 */
	@RequestMapping(ConstantFunctionsFront.YW_OPENID_00003)
	@ResponseBody
	public Map<String, Object> queryUserInfo(YwMember ywMember) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map = ywMemberService.queryUserInfo(ywMember);
		
		//推送条数
		/*Map<String, Object> codeMap = new HashMap<String, Object>();
		codeMap.put("createBy", ywMember.getOpenid());
		codeMap.put("openid", ywMember.getOpenid());
		//获取收藏信息推送条数
		List<YwProductCollection> isLikes = ywProductCollectionService.queryIslike(codeMap);
		if(isLikes.size() > 0){
			map.put("like_length", isLikes.size());
		}
		//获取地址信息推送条数
		List<YwMemberAddress> adds = ywMemberAddressService.queryAddress(codeMap);
		if(adds.size() > 0){
			map.put("add_length", adds.size());
		}
		//获取优惠券信息推送条数
		List<YwCouponCollectiondetails> coupons = ywCouponCollectiondetailsService.queryAll(codeMap);
		if(coupons.size() > 0){
			map.put("coupon_length", coupons.size());
		}*/
		
		return map;
	}

	
	/**
	 * 获取用户信息 , 计算折扣
	 */
	@RequestMapping(ConstantFunctionsFront.YW_OPENID_00004)
	@ResponseBody
	public Map<String,Object> arithmeticalDiscount(YwMember ywMember , Double total_price){
		Map<String, Object> map = new HashMap<String, Object>();
		ywMember = ywMemberService.queryByOpenid(ywMember.getOpenid());
		String new_total = NumberUtil.mul(total_price,Double.valueOf(ywMember.getDiscount()));
		map.put("new_total", new_total);
		
		return map;
	}
	
	/**
	 * 获取用户微信绑定手机号码
	*
	*@param iv			向量
	*@param encData		用户手机号信息密文
	*@param openid		用户openid
	*@return
	*@throws Exception
	*String
	 */
	@RequestMapping(ConstantFunctionsFront.YW_OPENID_00005)
	@ResponseBody
	public String getPhoneByWx(String iv, String encData , String openid) throws Exception{
		YwMember ywMember = ywMemberService.queryByOpenid(openid);
		String str = "";
		try {
			if(ywMember != null){
				String jsonStr = YwMemberController.decryptAES(encData, "UTF-8", ywMember.getSessionKey(), iv);
				str = JSONObject.fromObject(jsonStr).getString("phoneNumber");
				ywMember.setPhone(str);
				ywMemberService.update(ywMember);
				//插入缓存中
				String key = RedisClientUtil.getModelKeyAlias("YwMember", openid);
				RedisClientUtil.add(key, ywMember);
				System.out.println(str);
			}
			return str;
		}catch (Exception e) {
			throw new BizException(e);
		}
	}
	
	/**
	 * 修改用户基本信息
	*
	*@param ywMember
	*@return
	*String
	 */
	@RequestMapping(ConstantFunctionsFront.YW_OPENID_00006)
	@ResponseBody
	public void getPhoneByWx(YwMember ywMember) throws Exception{
		try {
			ywMemberService.update(ywMember);
			//插入缓存中
			RedisClientUtil.add(ywMember.getOpenid(), ywMember);
		} catch (Exception e) {
			throw new BizException(e);
		}
		
	}
	
    /**
     * 通过AES解密,获取用户绑定的微信手机号码信息
    *
    *@param sSrc				用户手机号信息密文
    *@param encodingFormat		编码格式(utf-8)
    *@param sKey				密钥session_key
    *@param ivParameter			向量
    *@return					JSON格式数据(用户手机号码信息)
    *@throws Exception
    *String
     */
    private static String decryptAES(String sSrc, String encodingFormat, String sKey, String ivParameter) throws Exception {
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] raw = decoder.decodeBuffer(sKey);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            IvParameterSpec iv = new IvParameterSpec(decoder.decodeBuffer(ivParameter));
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] myendicod = decoder.decodeBuffer(sSrc);
            byte[] original = cipher.doFinal(myendicod);
            String decryptStr = new String(original, encodingFormat);
            
            return decryptStr;
        } catch (Exception e) {
        	throw new BizException(e);
        }
    }
    
	
}
