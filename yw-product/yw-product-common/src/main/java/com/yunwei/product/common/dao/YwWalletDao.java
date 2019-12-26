package com.yunwei.product.common.dao;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.product.common.model.YwWallet;


/**
 * 云维字典dao
 * @author zhangjh
 *
 */
public interface YwWalletDao extends IBaseDao<YwWallet>{
	
    public List<YwWallet> queryList(YwWallet ywWallet);
	
	public int update(YwWallet ywWallet);
	
	public int insert(YwWallet ywWallet);

	public List<YwWallet> queryPage(Map<String,Object> map);

	public int queryTotals(YwWallet ywWallet);

	public int delete(YwWallet ywWallet);

}
