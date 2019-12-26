package com.yunwei.common.util;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunwei.common.cache.PropertiesCache;
import com.yunwei.common.utils.encrypt.TripleDes;


public class PropertiesUtils {

	private static Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);
	
	private static Properties prop = new Properties();
	
	public static String[] pwdNameList = {"ifs_operator_pwd","livebos_pwd","zl_trust_store_pwd","hsipccweb_privatekey","sisap_identifier_pwd","sms_user_pwd","ca_beijingca_keyPassword"};

	public static synchronized void clear() {
		prop.clear();
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private static synchronized void init() {
		Map<String, PropertiesCache> caches = SpringContext.getBeansOfType(PropertiesCache.class);
		if (caches == null || caches.isEmpty()) {
			throw new RuntimeException("context初始化不正常，配置加载失败");
		}
		for (Map.Entry<String, PropertiesCache> entry : caches.entrySet()) {
			// 由cacheManager统一管理缓存刷新
			// entry.getValue().refresh();
			Map map = entry.getValue().getConfigData();
			if (map != null && !map.isEmpty()) {
				prop.putAll(map);
			}
		}
	}

	public static Properties getProp() {
		if (prop.isEmpty()) {
			init();
		}
		return prop;
	}

	public static String get(String key) {
		/*String value = "";
		if (getProp().containsKey(key)) value = getProp().getProperty(key);
		if(value == null || value.toLowerCase().equals("null") || (value.startsWith("${") && value.endsWith("}"))){
			value = "";
		}
		if(isPassword(key,value)&&value.matches("^[A-F0-9]+$") && value.length()%16==0){
			return TripleDes.decode(value);
		}
		if ("version".equalsIgnoreCase(key)) {
			return EnvironmentUtils.getApplicationVersion().get("ver_no");
		}*/
		return get(key,"");
	}

	public static String get(String key, String defaultValue) {
		String value = getProp().getProperty(key, defaultValue);

		if (null == value || value.toLowerCase().equals("null") || StringUtils.isBlank(value)
				|| (value.startsWith("${") && value.endsWith("}"))){
			value = defaultValue;
		}
		if(isPassword(key,value)&&value.matches("^[A-F0-9]+$") && value.length()%16==0){
			return TripleDes.decode(value);
		}
		if ("version".equalsIgnoreCase(key)) {
			return EnvironmentUtils.getApplicationVersion().get("ver_no");
		}
		if("ifs.operator.station".equals(key)){
			String alias = getProp().getProperty("security.alias");
//			if("cfzqwskh".equals(alias)||"cfzq".equals(alias)){
//				return CFZQPropertiesUtils.getIFSOpStationForCFZQ(value);
//			}
		}
		return value.trim();
	}

	public static int getInt(String key, int defaultValue) {
		String value = get(key, String.valueOf(defaultValue));
		if(isPassword(key,value)&&value.matches("^[A-F0-9]+$") && value.length()%16==0){
			return Integer.parseInt(TripleDes.decode(value));
		}
		return Integer.parseInt(value);
	}

	public static String getWithFormat(String key, String ... vals) {
		String msg = "";
		String format = getProp().getProperty(key);
		if (!StringUtils.isBlank(format)) {
			MessageFormat mf = new MessageFormat(format);
			msg = mf.format(vals);
		}
		return msg;
	}

	public static String getWithFormat(String key, Map<String, String> params) {
		String str = get(key, "");
		String regex = "\\$\\{([^)]*?)\\}";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		while (m.find()) {
			key = m.group(1);
			String value = params.get(key);
			str = str.replace("${" + key + "}", StringUtils.defaultIfEmpty(value, ""));
		}
		return str;
	}

	public static Integer get(String key, Integer def) {
		try {
			Integer res = def;
			String value = getProp().getProperty(key);
			if (null == value || StringUtils.isBlank(value)) {
				value = String.valueOf(def);
			}
			
			if(isPassword(key,value)&&value.matches("^[A-F0-9]+$") && value.length()%16==0){
				return Integer.parseInt(TripleDes.decode(value));
			}
			if (getProp().containsKey(key)){
				if(value == null || value.isEmpty() || value.toLowerCase().equals("null") 
						|| (value.startsWith("${") && value.endsWith("}"))){
					value = res.toString();
				}
				res = Integer.parseInt(value);
			}
			return res;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return def;
		}

	}

	public static Long get(String key, Long def) {
		try {
			Long res = def;
			String value = getProp().getProperty(key);
			if (null == value || StringUtils.isBlank(value)) {
				value = String.valueOf(def);
			}
			if(isPassword(key,value)&&value.matches("^[A-F0-9]+$") && value.length()%16==0){
				return Long.parseLong(TripleDes.decode(value));
			}
			if (getProp().containsKey(key)) res = Long.parseLong(value);
			return res;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return def;
		}

	}
	
	public static boolean isPassword(String key , String value){
		boolean flag = false;
		boolean flag2 = false;
		if(key!=null&&value!=null&&!"null".equals(value)){
			key = key.replace(".","_");
			for(String str:pwdNameList){
				if(str.equals(key.trim())){
					flag = true;
				}
			}
			if(key.trim().contains("password")&&!"db_password".equals(key.trim())){
				flag2 = true;
			}
		}
		return flag || flag2;
	}
	
	/**
	 * 获取key的值，见{@link #get(String, String)}。用指定的单位转换为Date并返回
	 * @author meijie
	 * @param key
	 * @param defaultValue
	 * @param unit
	 * @return 返回null，如果失败
	 */
	public static Date getDate(String key, String defaultValue, TimeUnit unit) {
		String value = get(key, defaultValue);
		Date date = null;
		try {
			int time = Integer.parseInt(value);
			date = new Date(TimeUnit.MILLISECONDS.convert(time, unit));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return date;
	}

}
