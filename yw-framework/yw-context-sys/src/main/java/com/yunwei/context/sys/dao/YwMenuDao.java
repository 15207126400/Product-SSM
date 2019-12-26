package com.yunwei.context.sys.dao;

import java.util.List;
import java.util.Map;

import com.yunwei.context.sys.model.YwMenu;



/**
 * 数据库配置dao
 * @author zhangjh
 *
 */
public interface YwMenuDao {
	
    public List<YwMenu> queryList(YwMenu ywMenu);
	
	public int update(YwMenu ywMenu);
	
	public int insert(YwMenu ywMenu);

	public List<YwMenu> queryListPage(Map<String,Object> map);

	public int queryTotals(YwMenu ywMenu);

	public int delete(YwMenu ywMenu);

	public int deleteBatch(Map<String,String[]> map);
}
