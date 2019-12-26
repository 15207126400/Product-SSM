package com.yunwei.context.jdbc.cache;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.yunwei.common.cache.ICache;
import com.yunwei.context.jdbc.model.YwDbconfig;
import com.yunwei.context.jdbc.service.YwDbconfigService;

@Component("ywDbconfigCache")
public class YwDbconfigCache implements ICache<YwDbconfig> {
	
	private static Map<String,YwDbconfig> configData= new LinkedHashMap<String, YwDbconfig>();
	
	@Autowired
	private YwDbconfigService ywDbconfigService;

	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public void refresh() throws Exception {
		List<YwDbconfig> dbconfigs = ywDbconfigService.queryList(new YwDbconfig());
		if(!CollectionUtils.isEmpty(dbconfigs)){
			for(YwDbconfig dbconfig : dbconfigs){
				configData.put(String.valueOf(dbconfig.getDb_id()), dbconfig);
			}
		}
	}

	@Override
	public String getId() {
		return "ywDbconfigCache";
	}
	
	/**
	 * 查询数据库缓存数据
	 * @param user_id 用户编号
	 * @param db_type 数据库类型
	 * @param db_version_type 数据库版本类型
	 * @return
	 */
	public YwDbconfig getYwDbconfig(String user_id,String db_type,String db_version_type){
		YwDbconfig dbconfig = null;
		if(!configData.isEmpty()){
			for(java.util.Map.Entry<String, YwDbconfig> entry : configData.entrySet()){
				YwDbconfig temp = entry.getValue();
				if(StringUtils.equals(temp.getUser_id(), user_id) && StringUtils.equals(temp.getDb_type(), db_type) && StringUtils.equals(String.valueOf(temp.getDb_sqlimport_versiontype()), db_version_type)){
					dbconfig = temp;
				}
			}
		}
		return dbconfig;
	}
	
	/**
	 * 查询数据库缓存数据
	 * @param user_id 用户编号
	 * @param db_type 数据库类型
	 * @return
	 */
	public YwDbconfig getYwDbconfig(String user_id,String db_type){
		YwDbconfig dbconfig = null;
		if(!configData.isEmpty()){
			for(java.util.Map.Entry<String, YwDbconfig> entry : configData.entrySet()){
				YwDbconfig temp = entry.getValue();
				if(StringUtils.equals(temp.getUser_id(), user_id) && StringUtils.equals(temp.getDb_type(), db_type)){
					dbconfig = temp;
				}
			}
		}
		return dbconfig;
	} 
	
	/**
	 * 查询数据库缓存数据
	 * @param wx_appid 小程序编号
	 * @param db_type 数据库类型
	 * @param db_version_type 数据库版本类型
	 * @return
	 */
	public YwDbconfig getYwDbconfigByWxAppid(String wx_appid,String db_type,String db_version_type){
		
		YwDbconfig dbconfig = null;
		if(!configData.isEmpty()){
			for(java.util.Map.Entry<String, YwDbconfig> entry : configData.entrySet()){
				YwDbconfig temp = entry.getValue();
				if(StringUtils.equals(temp.getWx_appid(), wx_appid) && StringUtils.equals(temp.getDb_type(), db_type) && StringUtils.equals(String.valueOf(temp.getDb_sqlimport_versiontype()), db_version_type)){
					dbconfig = temp;
				}
			}
		}
		
		// 判断userXcx是否为空(用于小程序调用)
		if(dbconfig == null){
			// 数据库查询小程序信息
			dbconfig = new YwDbconfig();
			dbconfig.setWx_appid(wx_appid);
			dbconfig.setDb_type(db_type);
			dbconfig.setDb_sqlimport_versiontype(db_version_type);
			dbconfig = ywDbconfigService.query(dbconfig);
			if(dbconfig != null){
				this.getConfigData().put(dbconfig.getDb_id(), dbconfig);
				return dbconfig;
			}
		}
		return dbconfig;
	}
	
	/**
	 * 查询数据库缓存数据
	 * @param wx_appid 小程序编号
	 * @param db_type 数据库类型
	 * @return
	 */
	public YwDbconfig getYwDbconfigByWxAppid(String wx_appid,String db_type){
		
		YwDbconfig dbconfig = null;
		if(!configData.isEmpty()){
			for(java.util.Map.Entry<String, YwDbconfig> entry : configData.entrySet()){
				YwDbconfig temp = entry.getValue();
				if(StringUtils.equals(temp.getWx_appid(), wx_appid) && StringUtils.equals(temp.getDb_type(), db_type)){
					dbconfig = temp;
				}
			}
		}
		
		// 判断userXcx是否为空(用于小程序调用)
		if(dbconfig == null){
			// 数据库查询小程序信息
			dbconfig = new YwDbconfig();
			dbconfig.setWx_appid(wx_appid);
			dbconfig.setDb_type(db_type);
			dbconfig = ywDbconfigService.query(dbconfig);
			if(dbconfig != null){
				this.getConfigData().put(dbconfig.getDb_id(), dbconfig);
				return dbconfig;
			}
		}
		return dbconfig;
	}
	/**
	 * 设置db配置缓存
	 * @param dbconfig
	 */
	public void setYwDbconfig(YwDbconfig dbconfig){
		this.getConfigData().put(dbconfig.getDb_id(), dbconfig);
	}

	@Override
	public Map<String, YwDbconfig> getConfigData() {
		return configData;
	}

}
