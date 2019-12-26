package com.yunwei.context.sys.service;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.serivce.IBaseSerivce;
import com.yunwei.context.sys.model.YwMenu;
import com.yunwei.context.sys.model.YwRole;


/**
 * 云维字典
 * @author zhangjh
 *
 */
public interface YwRoleService extends IBaseSerivce<YwRole>{
	

	public List<YwMenu> getMenus(YwRole ywRole);

}
