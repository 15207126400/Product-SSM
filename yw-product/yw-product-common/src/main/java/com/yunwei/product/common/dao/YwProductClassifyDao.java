package com.yunwei.product.common.dao;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yunwei.common.base.dao.IBaseDao;
import com.yunwei.product.common.model.YwProductClassify;
/**
 * 
* @ClassName: ywProductClassifyDao 
* @Description: TODO(分类dao层接口) 
* @author 晏飞
* @date 2017年11月18日 下午5:09:23 
*
 */

public interface YwProductClassifyDao extends IBaseDao<YwProductClassify>{

	
}
