package com.yunwei.product.infobase.baseconfig.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yunwei.common.exception.BizException;
import com.yunwei.product.common.model.YwBaseconfig;
import com.yunwei.product.infobase.constant.ConstantFunctionsFront;
import com.yunwei.product.infobase.service.YwBaseconfigService;

/**
 * 
* @ClassName: YwBaseconfigController 
* @Description: TODO(TODO) 
* @author 晏飞
* @date 2018年9月26日 下午5:22:03 
*
 */
@Controller
public class YwBaseconfigController {
	
	@Autowired
	private YwBaseconfigService ywBaseconfigService;
	
	
	/**
	 * 查询基本配置列表
	*
	*@param ywBaseconfig		接收参数对象
	*@return					返回结果集
	 */
	@RequestMapping(ConstantFunctionsFront.YW_BASE_00000)
	@ResponseBody
	public List<YwBaseconfig> queryBaseconfig(YwBaseconfig ywBaseconfig){
		List<YwBaseconfig> ywBaseconfigList = new ArrayList<YwBaseconfig>();
		try {
			ywBaseconfigList = ywBaseconfigService.queryList(ywBaseconfig);
		} catch (Exception e) {
			throw new BizException(e);
		}
		
		return ywBaseconfigList;
	}
	
}
