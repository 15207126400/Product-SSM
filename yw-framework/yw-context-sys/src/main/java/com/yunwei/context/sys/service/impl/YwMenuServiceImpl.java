package com.yunwei.context.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwei.context.sys.dao.YwMenuDao;
import com.yunwei.context.sys.model.YwMenu;
import com.yunwei.context.sys.service.YwMenuService;


@Service
public class YwMenuServiceImpl implements YwMenuService {
	
	@Autowired
	private YwMenuDao ywMenuDao;

	@Override
	public List<YwMenu> queryList(YwMenu ywMenu) {
		return ywMenuDao.queryList(ywMenu);
	}

	@Override
	public int update(YwMenu ywMenu) {
		ywMenuDao.update(ywMenu);
		return 0;
	}

	@Override
	public int insert(YwMenu YwMenu) {
		ywMenuDao.insert(YwMenu);
		return 0;
	}

	@Override
	public List<YwMenu> queryListPage(Map<String, Object> map) {
		return ywMenuDao.queryListPage(map);
	}

	@Override
	public int queryTotals(YwMenu ywMenu) {
		return ywMenuDao.queryTotals(ywMenu);
	}

	@Override
	public YwMenu query(YwMenu ywMenu) {
		List<YwMenu> list = ywMenuDao.queryList(ywMenu);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return new YwMenu();
	}

	@Override
	public int delete(YwMenu ywMenu) {
		ywMenuDao.delete(ywMenu);
		return 0;
	}
	
	/**
	 * 通过级别查询菜单
	 * @return
	 */
	@Override
	public List<YwMenu> queryByLevel(String level){
		YwMenu menu = new YwMenu();
		menu.setMu_level(level);
		return queryList(menu);
	}

	@Override
	public List<YwMenu> querySubMenu(String parentid) {
		YwMenu menu = new YwMenu();
		menu.setMu_parentid(parentid);
		return queryList(menu);
	}

	/**
	 * 批量删除
	 */
	@Override
	public int deleteBatch(Map<String, String[]> map) {
		
		return ywMenuDao.deleteBatch(map);
	}

	@Override
	public int deleteBatch(String[] batch) {
		Map<String, String[]> map  = new HashMap<String, String[]>();
		map.put("mu_id", batch);
		return deleteBatch(map);
	}
	

}
