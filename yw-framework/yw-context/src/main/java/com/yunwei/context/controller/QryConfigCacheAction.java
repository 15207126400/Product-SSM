package com.yunwei.context.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunwei.common.util.PropertiesUtils;


/**
 * 功能说明: 查询系统配置action<p>有些组件如notice/video有自己缓存，其他组件或者项目想读取就不太方便，此action就是为解决此问题</p><br>
 * 系统版本: v1.0<br>
 * 开发人员: @author yejg<br>
 * 开发时间: 2016年8月22日<br>
 */
@Controller
public class QryConfigCacheAction {

	@RequestMapping("{modelName}/qryConfig")
	@ResponseBody
	public Map<String, String> qryConfig(@Valid @NotBlank String key, String default_value) {
		default_value = StringUtils.defaultIfEmpty(default_value, "");
		Map<String, String> rtnMap = new HashMap<String, String>();
		rtnMap.put(key, PropertiesUtils.get(key, default_value));
		return rtnMap;
	}

}
