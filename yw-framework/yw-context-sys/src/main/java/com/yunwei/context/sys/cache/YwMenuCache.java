package com.yunwei.context.sys.cache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.yunwei.common.cache.ICache;
import com.yunwei.context.sys.model.YwMenu;
import com.yunwei.context.sys.service.YwMenuService;

@Component("ywMenuCache")
public class YwMenuCache implements ICache<YwMenu> {

	private static Map<String, YwMenu> configData = new LinkedHashMap<String, YwMenu>();

	@Autowired
	private YwMenuService ywMenuService;

	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public void refresh() throws Exception {
		List<YwMenu> menus = ywMenuService.queryList(new YwMenu());
		if (!CollectionUtils.isEmpty(menus)) {
			configData.clear();
			for (YwMenu menu : menus) {
				configData.put(menu.getMu_id(), menu);
			}
		}
	}

	/**
	 * 获取子菜单
	 * 
	 * @param mu_parentid
	 * @return
	 */
	public List<YwMenu> getSubMenuResource(String mu_parentid) {
		List<YwMenu> subList = new ArrayList<YwMenu>();
		for (Entry<String, YwMenu> entry : configData.entrySet()) {
			YwMenu res = entry.getValue();
			if (StringUtils.equals(res.getMu_parentid(), mu_parentid)) {
				subList.add(res);
			}
		}
		return subList;
	}

	/**
	 * 根据菜单资源获取菜单级别
	 * 
	 * @param mu_level
	 * @return
	 */
	public List<YwMenu> getMenuResourceByLevel(String mu_level) {
		List<YwMenu> subList = new ArrayList<YwMenu>();
		for (Entry<String, YwMenu> entry : configData.entrySet()) {
			YwMenu res = entry.getValue();
			if (StringUtils.equals(res.getMu_level(), mu_level)) {
				subList.add(res);
			}
		}
		return subList;
	}

	/**
	 * 根据菜单字符串判断是否包含父菜单
	 * 
	 * @param mu_parentid
	 *            父菜单id
	 * @param mu_strs
	 *            菜单字符串
	 * @return
	 */
	public boolean isContainsParentMenu(String mu_parentid, String mu_strs) {
		List<YwMenu> subList = getSubMenuResource(mu_parentid);
		String[] strings = mu_strs.split(",");
		Set<String> set = new HashSet<String>();
		set.addAll(Arrays.asList(strings));
		for (YwMenu menu : subList) {
			if (set.contains(menu.getMu_id())) {
				return true;
			}
		}
		return false;
	}

	public List<String> getMenuIds(String mu_ids) {
		String[] strings2 = mu_ids.split(",");
		return Arrays.asList(strings2);
	}

	@Override
	public String getId() {
		return "ywMenuCache";
	}

	@Override
	public Map<String, YwMenu> getConfigData() {
		return configData;
	}

}
