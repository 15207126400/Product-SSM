package com.yunwei.product.backend.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.common.base.serivce.impl.IBaseServiceImpl;
import com.yunwei.common.util.NumberUtil;
import com.yunwei.product.backend.service.YwWalletService;
import com.yunwei.product.common.dao.YwWalletDao;
import com.yunwei.product.common.model.YwWallet;


@Service
public class YwWalletImpl extends IBaseServiceImpl<YwWallet> implements YwWalletService {
	
	@Autowired
	private YwWalletDao ywWalletDao;

	@Override
	protected IBaseDao<YwWallet> getBaseDao() {
		return ywWalletDao;
	}
	
	@Override
	public List<YwWallet> queryList(YwWallet ywWallet) {
		return ywWalletDao.queryList(ywWallet);
	}

	@Override
	public int update(YwWallet ywWallet) {
		
		return ywWalletDao.update(ywWallet);
	}

	@Override
	public int insert(YwWallet ywWallet) {
		
		return ywWalletDao.insert(ywWallet);
	}

	@Override
	public List<YwWallet> queryPage(Map<String, Object> map) {
		return ywWalletDao.queryPage(map);
	}

	@Override
	public int queryTotals(YwWallet ywWallet) {
		return ywWalletDao.queryTotals(ywWallet);
	}

	@Override
	public YwWallet query(YwWallet ywWallet) {
		List<YwWallet> list = ywWalletDao.queryList(ywWallet);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public int delete(YwWallet ywWallet) {
		
		return ywWalletDao.delete(ywWallet);
	}
	
	/**
	 * 钱包计入佣金
	*
	*@param user_id	用户标识
	*@param money	计入金额
	*@return	返回状态 
	*				0 计入失败
	*				1 计入成功
	 */
	public int walletAddCommission(String user_id,double money){
		YwWallet ywWallet = new YwWallet();
		ywWallet.setUser_id(user_id);
		ywWallet.setWa_type("1");
		YwWallet wallet = this.query(ywWallet);
		if (wallet == null) {
			ywWallet.setUser_id(user_id);
			ywWallet.setWa_type("1");
			ywWallet.setWa_moeny(String.valueOf(money));
			ywWallet.setWa_createtime(new Date());
			int num = this.insert(ywWallet);
			
			return num;
		} else {
			// 之前钱包的佣金余额
			double oldWa_moeny = Double.valueOf(wallet.getWa_moeny());
			String newWa_moeny = NumberUtil.add(oldWa_moeny, money);
			wallet.setWa_moeny(newWa_moeny);
			wallet.setWa_updatetime(new Date());
			int num = this.update(wallet);
			
			return num;
		}
	}
	
	/**
	 * 佣金提现 , 扣除钱包佣金余额
	*
	*@param user_id	用户标识
	*@param money	提现金额
	*@return	返回状态
	*				0 扣除余额成功
	*int			1 扣除余额失败
	 */
	public int walletByCommissionCash(String user_id,double money){
		YwWallet ywWallet = new YwWallet();
		ywWallet.setUser_id(user_id);
		ywWallet.setWa_type("1");
		ywWallet = this.query(ywWallet);
		if(Double.valueOf(ywWallet.getWa_moeny()) > money){
			String sum = NumberUtil.sub(Double.valueOf(ywWallet.getWa_moeny()), money);
			ywWallet.setWa_moeny(sum);
			ywWallet.setWa_updatetime(new Date());
			int num = this.update(ywWallet);
			return num;
		}
		return 0;
	}
	
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
	public int walletBackCommissionCash(String user_id,double money){
		YwWallet ywWallet = new YwWallet();
		ywWallet.setUser_id(user_id);
		ywWallet.setWa_type("1");
		ywWallet = this.query(ywWallet);
		String sum = NumberUtil.add(Double.valueOf(ywWallet.getWa_moeny()), money);
		ywWallet.setWa_moeny(sum);
		ywWallet.setWa_updatetime(new Date());
		int num = this.update(ywWallet);
		
		return num;
	}

	

}
