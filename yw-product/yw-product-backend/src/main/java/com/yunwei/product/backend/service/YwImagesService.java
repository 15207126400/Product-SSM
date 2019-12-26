package com.yunwei.product.backend.service;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.serivce.IBaseSerivce;
import com.yunwei.product.common.model.YwImages;

/**
 * 云维字典
 * @author zhangjh
 *
 */
public interface YwImagesService extends IBaseSerivce<YwImages>{
	
	public List<YwImages> queryList(YwImages ywImages);
	
	public int update(YwImages ywImages);
	
	public int insert(YwImages ywImages);

	public List<YwImages> queryPage(Map<String, Object> map);

	public int queryTotals(YwImages ywImages);

	public YwImages query(YwImages ywImages);

	public int delete(YwImages ywImages);

	public int insertBatch(List<YwImages> list);
	
	public int insertBatchByMenDian(List<YwImages> list);
	

}
