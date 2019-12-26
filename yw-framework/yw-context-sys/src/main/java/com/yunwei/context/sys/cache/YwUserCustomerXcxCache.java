package com.yunwei.context.sys.cache;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.yunwei.common.cache.ICache;
import com.yunwei.context.sys.usercenter.model.YwUserCustomerXcx;
import com.yunwei.context.sys.usercenter.service.YwUserCustomerXcxService;

@Component("ywUserCustomerXcxCache")
public class YwUserCustomerXcxCache implements ICache<YwUserCustomerXcx> {
	
	private static Map<String,YwUserCustomerXcx> configData= new LinkedHashMap<String, YwUserCustomerXcx>();
	
	@Autowired
	private YwUserCustomerXcxService ywUserCustomerXcxService;

	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public void refresh() throws Exception {
		List<YwUserCustomerXcx> UserCustomerXcxs = ywUserCustomerXcxService.queryList(new YwUserCustomerXcx());
		if(!CollectionUtils.isEmpty(UserCustomerXcxs)){
			configData.clear();
			for(YwUserCustomerXcx UserCustomerXcx : UserCustomerXcxs){
				configData.put(String.valueOf(UserCustomerXcx.getId()), UserCustomerXcx);
			}
		}
	}

	@Override
	public String getId() {
		return "ywUserCustomerXcxCache";
	}
	
	
	/**
	 * 查询数据库缓存数据
	 * @param user_id 用户编号
	 * @return
	 */
	public YwUserCustomerXcx getYwUserCustomerXcx(String user_id){
		YwUserCustomerXcx UserCustomerXcx = null;
		if(!configData.isEmpty()){
			for(java.util.Map.Entry<String, YwUserCustomerXcx> entry : configData.entrySet()){
				YwUserCustomerXcx temp = entry.getValue();
				if(StringUtils.equals(temp.getUser_id(), user_id)){
					UserCustomerXcx = temp;
				}
			}
		}
		return UserCustomerXcx;
	}
	
	/**
	 * 查询数据库缓存数据
	 * @param app_id 小程序编号
	 * @return
	 */
	public YwUserCustomerXcx getYwUserCustomerXcxByWxAppid(String app_id){
		
		YwUserCustomerXcx UserCustomerXcx = null;
		if(!configData.isEmpty()){
			for(java.util.Map.Entry<String, YwUserCustomerXcx> entry : configData.entrySet()){
				YwUserCustomerXcx temp = entry.getValue();
				if(StringUtils.equals(temp.getApp_id(), app_id)){
					UserCustomerXcx = temp;
				}
			}
		}
		
		// 判断UserCustomerXcx是否为空
		if(UserCustomerXcx == null){
			// 数据库查询小程序信息
			UserCustomerXcx = new YwUserCustomerXcx();
			UserCustomerXcx.setApp_id(app_id);
			UserCustomerXcx = ywUserCustomerXcxService.query(UserCustomerXcx);
			if(UserCustomerXcx != null){
				this.getConfigData().put(UserCustomerXcx.getId(), UserCustomerXcx);
				return UserCustomerXcx;
			}
		}
		return UserCustomerXcx;
	}
	
	@Override
	public Map<String, YwUserCustomerXcx> getConfigData() {
		return configData;
	}

}
