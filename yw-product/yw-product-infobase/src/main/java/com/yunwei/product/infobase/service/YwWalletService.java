package com.yunwei.product.infobase.service;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.serivce.IBaseSerivce;
import com.yunwei.product.common.model.YwWallet;


/**
 * 云维字典
 * @author zhangjh
 *
 */
public interface YwWalletService extends IBaseSerivce<YwWallet>{
	
	public List<YwWallet> queryList(YwWallet ywWallet);
	
	public int update(YwWallet ywWallet);
	
	public int insert(YwWallet ywWallet);

	public List<YwWallet> queryPage(Map<String, Object> map);

	public int queryTotals(YwWallet ywWallet);

	public YwWallet query(YwWallet ywWallet);

	public int delete(YwWallet ywWallet);
	
	/**
	 * 钱包计入佣金
	*
	*@param user_id	用户标识
	*@param money	计入金额
	*@return	返回状态 
	*				0 计入失败
	*				1 计入成功
	 */
	public int walletAddCommission(String user_id,double money);
	
	/**
	 * 佣金提现 , 扣除钱包佣金余额
	*
	*@param user_id	用户标识
	*@param money	提现金额
	*@return	返回状态
	*				0 扣除余额成功
	*int			1 扣除余额失败
	 */
	public int walletByCommissionCash(String user_id,double money);
	
	
	/**
	 * 提现失败 , 返还扣除的钱包佣金
	*
	*@param user_id 用户标识
	*@param money	返还金额
	*@return	返回状态
	*				0 返还成功
	*				1 返还失败
	*int
	 */
	public int walletBackCommissionCash(String user_id,double money);

}
