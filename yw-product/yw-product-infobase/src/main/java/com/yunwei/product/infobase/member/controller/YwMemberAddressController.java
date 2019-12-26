package com.yunwei.product.infobase.member.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunwei.common.exception.BizException;
import com.yunwei.product.common.model.YwMemberAddress;
import com.yunwei.product.infobase.constant.ConstantFunctionsFront;
import com.yunwei.product.infobase.service.YwMemberAddressService;

/**
 * 
* @ClassName: YwMemberAddressController 
* @Description: TODO(地址信息) 
* @author 晏飞
* @date 2017年11月18日 下午4:59:58 
*
 */
@Controller
public class YwMemberAddressController {
	@Autowired
	private YwMemberAddressService saService;
	@Autowired
	private HttpServletRequest request;
	

	/**
	 * 地址展示页
	*
	*@param request
	*@param response
	*@return
	*JSONObject
	 */
	@RequestMapping(ConstantFunctionsFront.YW_ADDRESS_00002)
    @ResponseBody
    public List<YwMemberAddress> queryAddress(String openid){
		Map<String, Object> addmap = new HashMap<String, Object>();
		addmap.put("createBy", openid);
		List<YwMemberAddress> ywMemberAddress = saService.queryAddress(addmap);
		if(ywMemberAddress.size() == 0){
			throw new BizException("数据不存在");
		}
		return ywMemberAddress;
	}
	
	/**
	 * 按照指定id查询对应的地址信息
	 */
	
	@RequestMapping(ConstantFunctionsFront.YW_ADDRESS_00003)
	@ResponseBody
	public YwMemberAddress queryByAddress(String id){
		
		YwMemberAddress ywMemberAddress = saService.queryByAddress(Integer.parseInt(id));
		
		return ywMemberAddress;
	}
	
	/**
	 * 地址保存
	*
	*@param request
	*@param response
	*@return
	*JSONObject
	 */
	@RequestMapping(ConstantFunctionsFront.YW_ADDRESS_00004)
    @ResponseBody
    public JSONObject addAddress(YwMemberAddress ywMemberAddress){
		JSONObject obj = new JSONObject();
		Map<String, Object> addmap = new HashMap<String, Object>();
		addmap.put("createBy", ywMemberAddress.getCreateBy());
		List<YwMemberAddress> addresslist = saService.queryAddress(addmap);
		//当该地址为第一次添加的地址时 , 那么该地址就是默认地址
		if(addresslist.size()<1){
			ywMemberAddress.setDefaultAddress("1");
			saService.addAddress(ywMemberAddress);
		}else{
			ywMemberAddress.setDefaultAddress("0");
			saService.addAddress(ywMemberAddress);
		}
		return obj;
	}
	
	/**
	 * 修改地址信息
	 */
	@RequestMapping(ConstantFunctionsFront.YW_ADDRESS_00005)
    @ResponseBody
    public JSONObject updateAddress(String id){
		JSONObject obj = new JSONObject();
				
		String newname = request.getParameter("name");	
		String newtel = request.getParameter("tel");
		String newsex = request.getParameter("sex");
		String newaddress = request.getParameter("address");
		String newdoor = request.getParameter("door");
		int newid = Integer.parseInt(id);			
		YwMemberAddress YwMemberAddress = saService.queryByAddress(newid);
		YwMemberAddress.setName(newname);
		YwMemberAddress.setTel(newtel);
		YwMemberAddress.setSex(newsex);
		YwMemberAddress.setAddress(newaddress);
		YwMemberAddress.setDoor(newdoor);
		
		int num = saService.updateAddress(YwMemberAddress);
		if(num > 0){
			obj.put("msg", "修改成功");
		}else{
			obj.put("msg", "修改失败");
		}
		return obj;
	}
	
	/**
	 * 删除地址信息
	 */
	@RequestMapping(ConstantFunctionsFront.YW_ADDRESS_00006)
    @ResponseBody
    public JSONObject deleteAddress(String id){
		JSONObject obj = new JSONObject();
		int num = saService.deleteAddress(Integer.valueOf(id));
		if(num > 0){
			obj.put("msg", "删除成功");
		}else{
			obj.put("msg", "删除失败");
		}
		return obj;
	}
	
	/**
	 * 修改默认地址
	 */
	@RequestMapping(ConstantFunctionsFront.YW_ADDRESS_00007)
    @ResponseBody
    public List<YwMemberAddress> updateDefault(String id , String openid){
		
		JSONObject obj = new JSONObject();
		List<Object> list = new ArrayList<Object>();
		int sid = Integer.parseInt(id);
		Map<String, Object> addmap = new HashMap<String, Object>();
		addmap.put("createBy", openid);
		List<YwMemberAddress> addresslist = saService.queryAddress(addmap);
		for (YwMemberAddress addlist : addresslist) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			if(sid == addlist.getId()){
				YwMemberAddress ad = saService.queryByAddress(sid);
				ad.setDefaultAddress("1");
				saService.updateDefault(ad);
			}else{
				YwMemberAddress ywMemberAddress = saService.queryByAddress(addlist.getId());
				ywMemberAddress.setDefaultAddress("0");
				saService.updateDefault(ywMemberAddress);
			}
		}
		return addresslist;
	}
}	 

