package com.yunwei.product.common.dao;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.product.common.model.YwProductAttributename;
import com.yunwei.product.common.model.YwProductAttributevalue;


/**
 * 云维字典dao
 * @author zhangjh
 *
 */
public interface YwProductAttributenameDao extends IBaseDao<YwProductAttributename>{
	
    public List<YwProductAttributename> queryList(YwProductAttributename ywProductAttributename);
	
	public int update(YwProductAttributename ywProductAttributename);
	
	public int insert(YwProductAttributename ywProductAttributename);

	public List<YwProductAttributename> queryPage(Map<String,Object> map);

	public int queryTotals(YwProductAttributename ywProductAttributename);

	public int delete(YwProductAttributename ywProductAttributename);

	public int insertValueBatch(List<YwProductAttributevalue> attributevalues);

}
