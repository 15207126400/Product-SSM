package com.yunwei.product.common.dao;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.product.common.model.YwDistributorSalestatus;
import com.yunwei.product.common.model.YwImages;


/**
 * 云维字典dao
 * @author zhangjh
 *
 */
public interface YwImagesDao extends IBaseDao<YwImages>{
	
    public List<YwImages> queryList(YwImages ywImages);
	
	public int update(YwImages ywImages);
	
	public int insert(YwImages ywImages);

	public List<YwImages> queryPage(Map<String,Object> map);

	public int queryTotals(YwImages ywImages);

	public int delete(YwImages ywImages);
	
	public int insertBatch(List<YwImages> list);
	
	public int insertBatchByMenDian(List<YwImages> list);

}
