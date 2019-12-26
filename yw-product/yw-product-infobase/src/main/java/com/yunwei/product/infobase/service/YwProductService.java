package com.yunwei.product.infobase.service;

import java.util.List;
import java.util.Map;

import com.yunwei.common.base.serivce.IBaseSerivce;
import com.yunwei.product.common.model.YwProduct;

/**
 * 
* @ClassName: YwProductDao 
* @Description: TODO(商品详情接口) 
* @author 晏飞
* @date 2017年11月18日 下午5:09:49 
*
 */
public interface YwProductService extends IBaseSerivce<YwProduct>{

	//查询所有信息
	public List<YwProduct> queryList(Map<String, Object> map);
	
	//根据id查找商品
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
	
	/**
	 * 图片上传
	 */
	public Map<String, Object> productImgInsertBatch(YwProduct ywProduct);
	
	/**
	 * 生成二维码
	 */
	public String getToken(String wx_appid);		//获取token码
}

