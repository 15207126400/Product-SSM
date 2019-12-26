package com.yunwei.context.sys.cache;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.yunwei.common.cache.ICache;
import com.yunwei.context.sys.model.YwRole;
import com.yunwei.context.sys.service.YwRoleService;

@Component("ywRoleCache")
public class YwRoleCache implements ICache<YwRole> {

	private static Map<String,YwRole> configData= new LinkedHashMap<String, YwRole>();
	
	@Autowired
	private YwRoleService ywRoleService;
	
	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public void refresh() throws Exception {
		List<YwRole> roles = ywRoleService.queryList(new YwRole());
		if(!CollectionUtils.isEmpty(roles)){
			configData.clear();
			for(YwRole role : roles){
				configData.put(role.getRo_id(), role);
			}
		}
	}

	/**
	 * 根据角色id获取角色资源
	 * @param ro_id
	 * @return
	 */
	public YwRole roleResource(String ro_id){
		return configData.get(ro_id);
	}
	
	/**
	 * 判断是否有相应的角色权限
	 * @param ro_ids
	 * @return
	 */
	public boolean isHasRole(String ro_ids,String ro_id){
		String[] ids = ro_ids.split(",");
		boolean flag = false;
		if(ids.length > 0){
			for(String id: ids){
				if(StringUtils.equals(id, ro_id)){
					flag = true;
					break;
				}
			}
		}
		return flag;
	}
	
	/**
	 * 根据角色支字符串查询角色集合
	 * @param ro_ids
	 * @return
	 */
	public List<YwRole> getRolesList(String ro_ids){
		List<YwRole> roles = new ArrayList<YwRole>();
		if(StringUtils.isNotEmpty(ro_ids)){
			String[] ids = ro_ids.split(",");
			for(String id : ids){
				YwRole role = configData.get(id);
				roles.add(role);
			}
		}
		return roles;
	}
	
	@Override
	public String getId() {
		return "ywRoleCache";
	} 

	@Override
	public Map<String, YwRole> getConfigData() {
		return configData;
	}

}
