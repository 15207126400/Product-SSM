package com.yunwei.product.common.dao;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.product.common.model.YwOrder;
import com.yunwei.product.common.model.YwProduct;
/**
 * 
* @ClassName: YwProductDao 
* @Description: TODO(商品dao层接口) 
* @author 晏飞
* @date 2017年11月18日 下午5:09:49 
*
 */
public interface YwProductDao extends IBaseDao<YwProduct>{
	//查询所有商品
	public List<YwProduct> queryList(Map<String, Object> map);
	
	//根据id查询商品信息
	public YwProduct queryById(String id);
	
	//按照标题中带有某字符的条件搜索
	public List<YwProduct> queryByTitle(YwProduct ywProduct);
	
	//商品信息保存
	public int addYwProduct(YwProduct ywProduct);
	
	//删除商品信息
	public int deleteYwProduct(int id);
	
	//修改商品信息
	public int updateYwProduct(YwProduct ywProduct);
	
	//查询商品总数
	public int findsum(Map<String, Object> map);
	
	//查询所有商品信息(非关联查询)
	public List<YwProduct> queryAll(Map<String, Object> map);
	
	// 根据商品编码查找商品
	public YwProduct queryByProductCode(String product_code);
	
	// 导出excel表单数据关联查询
	public List<YwProduct> importExcelByProduct(Map<String, Object> map);
	

}
