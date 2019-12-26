package com.yunwei.product.infobase.service;

import com.yunwei.common.base.serivce.IBaseSerivce;
import com.yunwei.product.common.infobase.model.form.YwOrderOfflineForm;
import com.yunwei.product.common.model.YwOrderOffline;

/**
 * 线下订单serivce
* @ClassName: YwOrderOfflineInfobaseService 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年11月09日 下午15:39:35
*
 */
public interface YwOrderOfflineService extends IBaseSerivce<YwOrderOffline>{

	/**
	 * 线下订单创建
	 * @param ywOrderOffline
	 * @return
	 */
   public String orderOfflineCreate(YwOrderOfflineForm ywOrderOfflineForm) throws Exception;
	

}

