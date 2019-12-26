package com.yunwei.context.sys.service;

import java.util.List;
import java.util.Map;

import com.yunwei.context.sys.model.YwMenu;



/**
 * 系统菜单
 * @author zhangjh
 *
 */
public interface YwMenuService {
	
	public List<YwMenu> queryList(YwMenu ywMenu);
	
	public int update(YwMenu ywMenu);
	
	public int insert(YwMenu ywMenu);

	public List<YwMenu> queryListPage(Map<String, Object> map);

	public int queryTotals(YwMenu ywMenu);

	public YwMenu query(YwMenu ywMenu);

	public int delete(YwMenu ywMenu);
	
	public List<YwMenu> queryByLevel(String level);
	
	public List<YwMenu> querySubMenu(String parentid);
	
	public int deleteBatch(Map<String,String[]> map);
	
	public int deleteBatch(String[] batch);

}
