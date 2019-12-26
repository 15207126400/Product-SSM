package com.yunwei.context.sys.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.context.sys.cache.YwMenuCache;
import com.yunwei.context.sys.dao.YwRoleDao;
import com.yunwei.context.sys.model.YwMenu;
import com.yunwei.context.sys.model.YwRole;
import com.yunwei.context.sys.service.YwMenuService;
import com.yunwei.context.sys.service.YwRoleService;

@Service
public class YwRoleServiceImpl extends IBaseServiceImpl<YwRole> implements YwRoleService {
	
	@Autowired
	private YwRoleDao ywRoleDao;
	@Autowired
	private YwMenuService ywMenuService;
	@Autowired
	private YwMenuCache ywMenuCache;
	
	@Override
	protected IBaseDao<YwRole> getBaseDao() {
		return ywRoleDao;
	}

	@Override
	public List<YwMenu> getMenus(YwRole ywRole) {
		// 查询一级菜单
		List<YwMenu> menus = ywMenuCache.getMenuResourceByLevel("1");
		if(!CollectionUtils.isEmpty(menus)){
			// 遍历子菜单
			iterateMenu(menus);
		}
		return menus;
	}
	
	/**
	 * 迭代菜单
	 * @param menus
	 */
	private void iterateMenu(List<YwMenu> menus){
		for(YwMenu m : menus){
			if(StringUtils.equals(m.getMu_issub_node(), "1")){
				// 查询子菜单
				List<YwMenu> ywMenus = ywMenuCache.getSubMenuResource(m.getMu_id());
				if(!CollectionUtils.isEmpty(menus)){
					m.setChild_menus(ywMenus);
					iterateMenu(ywMenus);
				}
			}
		}
	}

	

}
