package com.yunwei.context.config.controller;


import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunwei.common.cache.CacheManager;
import com.yunwei.common.user.SysUser;
import com.yunwei.common.util.PropertiesUtils;
import com.yunwei.common.utils.encrypt.TripleDes;
import com.yunwei.context.config.cache.ConfigPropertyCache;
import com.yunwei.context.config.configure.ConfigureCatalog;
import com.yunwei.context.config.configure.Generate;
import com.yunwei.context.config.configure.XMLConfigPaser;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/system/sysconfig")
public class ConfigAction {

	private final Logger logger = LoggerFactory.getLogger(ConfigAction.class);

	@Autowired
	CacheManager cachemanager;

	@Autowired
	ConfigPropertyCache configPropertyCache;

//	@Autowired
//	private IJourService jourService;

	/**
	 * 进入参数列表页面
	 * @return
	 */
	@RequestMapping("sysarg")
	public String configList(Model model) {
		model.addAttribute("configlist", configPropertyCache.getConfigData().values());
		return "/config/configureList";
	}

	/**
	 * 进入参数配置页面
	 * @param catalogId
	 * @param model
	 * @return
	 */
	@RequestMapping("sysargEdit")
	public String modifyConfig(String catalogId, Model model) {
		model.addAttribute("catalogId", catalogId);
		Map<String, ConfigureCatalog> catalogs = configPropertyCache.getConfigData();
		Map<Object, Object> configValues = configPropertyCache.readPropertiesData(catalogs, catalogId);
		model.addAttribute("html_data", XMLConfigPaser.getConfigureHTML(catalogId, configValues, catalogs));
		return "/config/configureModify";
	}

	/**
	 * 保存参数设置修改
	 * @return
	 */
	@ResponseBody
	@RequestMapping("sysargEditSave")
	public Map<String, Object> saveModify(SysUser sysUser, HttpServletRequest request, String catalogId, Model model, String db_driverClass) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> params = new HashMap<String, String>();
		Enumeration<String> strs = request.getParameterNames();
		while (strs.hasMoreElements()) {
			String ss = strs.nextElement();
			String value = request.getParameter(ss).trim();
			if (StringUtils.contains(value, "＼＼")) {
				value = StringUtils.replace(value, "＼＼", "/");
			} else if (StringUtils.contains(value, "＼")) {
				value = StringUtils.replace(value, "＼", "/");
			}
			params.put(ss, value);
		}
		request.getParameterNames();
		Set<String> key = params.keySet();
		for (String string : key) {
			params.put(string, toSemiangle(params.get(string)));
			if (PropertiesUtils.isPassword(string, params.get(string))) {
				String value = TripleDes.encode(params.get(string));
				params.put(string, value);
			}
		}
		catalogId = params.remove("catalogId");

		Map<String, ConfigureCatalog> catalogs = configPropertyCache.getConfigData();
		XMLConfigPaser.generateTemplateFile(params, catalogId, catalogs);
		updateConfigToRedis(catalogId, catalogs);
		cachemanager.refreshOne(catalogId); // 刷新文件配置缓存

//		SysconfigJour jour = new SysconfigJour();
//		jour.setConfig_name(catalogId);
//		JSONObject obj = JSONObject.fromObject(params);
//		jour.setBusiness_data(obj.toString().replaceAll("\"", "'"));
//		jourService.handle(jour, null, SysconfigJour.class, Constant.BUSINESS_FLAG_MOD, "CRH_USER.SYSCONFIGJOUR_SEQ.nextVal", crhUser.getUser_id());
		return map;
	}

	/**
	 * 全角转半角
	 * @param input String.
	 * @return 半角字符串
	 */
	public static String toSemiangle(String input) {
		char c[] = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == '\u3000') {
				c[i] = ' ';
			} else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
				c[i] = (char)(c[i] - 65248);
			}
		}
		return new String(c);
	}

	/**
	 * 将更新后的配置文件刷新到redis中
	 * @author zhougz
	 * @param catalogId
	 * @param catalogs
	 */
	private void updateConfigToRedis(String catalogId, Map<String, ConfigureCatalog> catalogs) {
		ConfigureCatalog catalog = catalogs.get(catalogId);
		if (catalog != null) {
			List<Generate> generates = catalog.getTemplates();
			for (int i = 0; i < generates.size(); i++) {
				Generate generate = generates.get(i);
				String fileName = generate.getTemplate();
				logger.info("文件名称:" + fileName);
//				cachemanager.loadFilesToRedis(fileName);
			}
		}
	}

	/**
	 * 进入加密密码
	 * @return
	 */
	@RequestMapping("encodePassword")
	public String goEncodePassword() {
		return "/config/encodePassword";
	}

	/**
	 * 获取加密密码
	 * @param password
	 * @return
	 */
	@RequestMapping("ajaxEncodePassword")
	@ResponseBody
	public Map<String, String> encodePassword(@Valid @NotBlank String password) {
		Map<String, String> map = new HashMap<String, String>();
//		map.put(Fields.PASSWORD, TripleDes.encode(password));
		return map;
	}
}
