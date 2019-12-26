package com.yunwei.context.config.cache;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.yunwei.common.cache.ICache;
import com.yunwei.common.util.EnvironmentUtils;
import com.yunwei.common.util.PropertiesUtils;
import com.yunwei.common.utils.encrypt.TripleDes;
import com.yunwei.context.config.configure.ConfigureCatalog;
import com.yunwei.context.config.configure.Generate;

/**
 * 配置文件缓存
* @ClassName: ConfigPropertyCache 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年6月14日 下午1:40:11 
*
 */
@Component("configPropertyCache")
public class ConfigPropertyCache implements ICache<ConfigureCatalog> {

	private static Map<String, ConfigureCatalog> catalogs = new LinkedHashMap<String, ConfigureCatalog>();

	private static final long serialVersionUID = 1L;

	private static final String CONFIG_PATH = "configure";

	private static final String ORI_DATA_SEPERATOR = "\r\n####\r\n";

	private static Logger logger = LoggerFactory.getLogger(ConfigPropertyCache.class);

	public int getOrder() {
		return 0;
	}

	@SuppressWarnings("unchecked")
	public void refresh() throws Exception {
		URL url = this.getClass().getClassLoader().getResource(CONFIG_PATH + "/category.xml");
//		Resource resource = new ClassPathResource(CONFIG_PATH + "/category.xml");
		try {
			logger.info("catalog path:" + url.getPath());
			if (url != null) {
				SAXReader reader = new SAXReader();
				Document document = reader.read(url);
				Element root = document.getRootElement();
				Iterator<Element> catalogIterator = root.elementIterator("catalog");
				while (catalogIterator.hasNext()) {
					Element catalogElement = catalogIterator.next();
					ConfigureCatalog catalog = paserCatalogElement(catalogElement);
					catalogs.put(catalog.getId(), catalog);
				}
			} else {
				logger.debug("读取配置列表configure_catalog.xml出错");
			}
		} catch (Exception e) {
			logger.error("读取配置列表configure_catalog.xml出错", e);
		}

	}

	@SuppressWarnings("unchecked")
	private static ConfigureCatalog paserCatalogElement(Element catalogElement) {
		ConfigureCatalog catalog = new ConfigureCatalog();
		List<Generate> generates = new ArrayList<Generate>();
		catalog.setId(catalogElement.attributeValue("id", ""));
		catalog.setName(catalogElement.attributeValue("name", ""));
		catalog.setConfig(catalogElement.attributeValue("config", ""));
		catalog.setDescription(catalogElement.attributeValue("description", catalog.getName()));
		Iterator<Element> generateIterator = catalogElement.elementIterator("generate");
		while (generateIterator.hasNext()) {
			Element generateElement = generateIterator.next();
			Generate generate = paserGeneratorElment(generateElement);
			generates.add(generate);
		}
		catalog.setTemplates(generates);
		catalog.setStatus(diffRuntimeFileVSTemplateFile(generates));
		return catalog;
	}

	private static Generate paserGeneratorElment(Element generateElement) {
		String template = generateElement.attributeValue("template", "");
		Generate generate = new Generate();
		generate.setTemplate(template);
		createFileAndDir(EnvironmentUtils.getRuntimeConfigPath() + template);
		try {
			File file = new File(EnvironmentUtils.getFileAbsolutePath(template));
			generate.setModifyTime(file.lastModified());
		} catch (FileNotFoundException e) {
			generate.setModifyTime(0L);
		}
		return generate;
	}

	// 配置文件
	private static int diffRuntimeFileVSTemplateFile(List<Generate> generates) {
		for (int i = 0; i < generates.size(); i++) {
			Generate generate = generates.get(i);
			try {
				if (diffRuntimeFileVSTemplateFile(generate)) {
					return 1;
				}
			} catch (FileNotFoundException e) {
				logger.error(String.format("未找到配置文件[%s]", generate.getTemplate()));
				return 0;
			} catch (Exception e) {
				return 1;
			}
		}
		return 2;
	}
	private static boolean diffRuntimeFileVSTemplateFile(Generate generate) throws FileNotFoundException, IOException {
		String template = generate.getTemplate();
		String templateFilePath = getTemplateFilePath(template);
		String runtimeFilePath = EnvironmentUtils.getFileAbsolutePath(template);
		if (template.endsWith("properties")) {
			Properties rprop = new Properties();
			rprop.load(new FileInputStream(runtimeFilePath));
			Properties tprop = new Properties();
			tprop.load(new FileInputStream(templateFilePath));
			if (rprop.size() != tprop.size()) {
				return true;
			}
			Iterator<Object> iter = tprop.keySet().iterator();
			while (iter.hasNext()) {
				String key = (String)iter.next();
				if (!rprop.containsKey(key)) {
					return true;
				}
			}
			return false;
		} else {
			/*String templateContent = readFromFile(new File(templateFilePath));
			if (null == templateContent) {
				throw new FileNotFoundException("template file not found: " + templateFilePath);
			}
			Map configData = loadConfigData(true);
			String valuedContent = genValuedContent(templateContent, configData, false);

			String runtimeContent = readFromFile(new File(runtimeFilePath));

			return !valuedContent.equals(runtimeContent);*/
			return false;
		}
	}

	private static String getTemplateFilePath(String template) {
		return Thread.currentThread().getContextClassLoader().getResource("/").getPath() + "/" + CONFIG_PATH + "/" + template;
	}

	private static String readFromFile(File file) {
		if (!file.exists()) {
			return null;
		}
		FileInputStream fis = null;
		InputStreamReader isr = null;
		StringBuilder sBuilder = new StringBuilder(4096);
		char[] buffer = new char[4096];
		int len = 0;
		try {
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis, "UTF-8");
			while (-1 != (len = isr.read(buffer))) {
				sBuilder.append(buffer, 0, len);
			}
			return sBuilder.toString();
		} catch (Exception e) {
			logger.error("read from file failed: " + file.getAbsolutePath(), e);
			return null;
		} finally {
			close(isr);
			close(fis);
		}
	}

	/*private static Map<Object, Object> loadConfigData(boolean log) {
		Map<Object, Object> localConfigData = readOriDataFromFile();
		Map<Object, Object> configData = new ConfigureService().getAllConfig();
		if (null == localConfigData || localConfigData.isEmpty()) {
			if (log) {
				logger.info("use configure data in database");
			}
		} else {
			configData.putAll(localConfigData);
			if (log) {
				logger.info("use configure data in database merge with data in local data file");
			}
		}
		return configData;
	}

	private static String genValuedContent(String templateContent, Map<String, String> configData, boolean tranToUnicode) {
		if (tranToUnicode) {
			configData = tranToUnicodeMap(configData);
		}
		return StrSubstitutor.replace(templateContent, configData, "${", "}");
	}

	private static Map<String, String> tranToUnicodeMap(Map<String, String> params) {
		Map<String, String> newParams = new HashMap<String, String>();
		Iterator<Map.Entry<String, String>> iter = params.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, String> entry = iter.next();
			newParams.put(entry.getKey(), chinaToUnicode(entry.getValue()));
		}
		return newParams;
	}*/

	public static Map<Object, Object> readOriDataFromFile() {
		String filePath = EnvironmentUtils.getRuntimeConfigPath() + "/data";
		File file = new File(filePath);
		String content = readFromFile(file);
		if (null == content) {
			return null;
		}
		Map<Object, Object> data = new HashMap<Object, Object>();
		int sepLen = ORI_DATA_SEPERATOR.length();
		int startIdx = 0;
		int endIdx = 0;
		while (-1 != (endIdx = content.indexOf(ORI_DATA_SEPERATOR, startIdx))) {
			String entry = content.substring(startIdx, endIdx);
			int inEntrySepIdx = entry.indexOf('=');
			if (-1 != inEntrySepIdx) {
				String key = entry.substring(0, inEntrySepIdx);
				String value = entry.substring(inEntrySepIdx + 1);
				if (PropertiesUtils.isPassword(key, value) && value.matches("^[A-F0-9]+$") && value.length() % 16 == 0) {
					value = TripleDes.decode(value);
				}
				data.put(key, value);
			}
			startIdx = endIdx + sepLen;
		}
		if (startIdx < content.length()) {
			String entry = content.substring(startIdx);
			int inEntrySepIdx = entry.indexOf('=');
			if (-1 != inEntrySepIdx) {
				String key = entry.substring(0, inEntrySepIdx);
				String value = entry.substring(inEntrySepIdx + 1);
				if (PropertiesUtils.isPassword(key, value) && value.matches("^[A-F0-9]+$") && value.length() % 16 == 0) {
					value = TripleDes.decode(value);
				}
				data.put(key, value);
			}
		}
		return data;
	}

	/**
	 * 将包含中文的字符转换成Unicode
	 * @param str
	 * @return
	 */
	public static String chinaToUnicode(String str) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (isChinese(c)) {
				result.append("\\u" + Integer.toHexString(c));
			} else {
				result.append(c);
			}
		}
		return result.toString();
	}

	// 根据Unicode编码完美的判断中文汉字和符号
	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
			return true;
		}
		return false;
	}

	/**
	 * 生成文件夹、文件
	 * @author zhougz
	 * @param mkDir
	 * @param filePath
	 */
	private static void createFileAndDir(String filePath) {

		// 判断文件是否存在
		File file = new File(filePath);
		if (!file.exists()) {
			// 创建文件
			try {
				// 创建文件夹
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				file.createNewFile();
			} catch (IOException e) {
				logger.info("生成文件失败：" + filePath);
				logger.error("生成文件失败", e);
			}
		}
	}

	private static void close(Closeable io) {
		if (null != io) {
			try {
				io.close();
			} catch (Exception e) {
				logger.error("close io failed", e);
			}
		}
	}

	public String getId() {
		return "configPropertyCache";
	}

	public Map<String, ConfigureCatalog> getConfigData() {
		return catalogs;
	}

	/**
	 * 根据runtime_config_root下的properties的value值<br/>
	 * 与模板properties的参数进行解析，放入到Map中
	 * @author zhougz
	 * @param params 例如：${name}或${oos_webaddr_type}://${oos_cookie_domain}:${oos_webaddr_port}
	 * @param value 例如：cairenhui或http://www.cairenhui.com:80
	 * @param map 为xml文件的默认值
	 * @return
	 */
	public static Map<String, Object> analysisValue(String params, String value,Map<String,Object> map) {
		Map<String, Object> returnMap = new HashMap<String, Object>();

		// 获取参数是否是多个
		String[] number = StringUtils.split(params, "$");

		if (number.length > 1) {
			if(StringUtils.equals(number[0], "{server_staticfile_cdn_server}")){
				String regex = ".*?(?=\\/)";
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(value.replace("http://", ""));
				String str = "";
				while (matcher.find()) {
					str =matcher.group(0);
					break;
				}
				returnMap.put("server_staticfile_cdn_server", "http://" + str);
				returnMap.put("server_staticfile_cdn_serviceContext", value.replace("http://" + str, ""));
			}else{
			// 使用正则解析url
			returnMap.putAll(splitString(params, value));
			}
		} else {
			String key = params.trim();
			if (StringUtils.contains(key, "$")) {
				// 参数只有一个的情况
				key = params.substring(2, params.length() - 1);
			}
			if(StringUtils.isNotBlank(value) && value.substring(0, 1).equals("$")){
				value = value.replace("$", "");
			}
			//判断显示运行环境的properties文件里的key是否有值，没有值就从xml文件中取默认值
			if(StringUtils.isBlank(value)){
				value = (String)map.get(key);
			}
			returnMap.put(key, value);
		}

		return returnMap;
	}

	/**
	 * 根据runtime_config_root下的properties的value值<br/>
	 * 与模板properties的参数进行解析，此方法解析URL
	 * @author zhougz
	 * @param params 例如：${oos_webaddr_type}://${oos_cookie_domain}:${oos_webaddr_port}
	 * @param value 例如：http://www.cairenhui.com:80
	 * @return
	 */
	public static Map<String, Object> splitString(String params, String value) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<String> paramsList = new ArrayList<String>();
		String regex = "(.*?)://(.*?):(.*)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(params);
		while (matcher.find()) {
			paramsList.add(matcher.group(1));
			paramsList.add(matcher.group(2));
			paramsList.add(matcher.group(3));
		}

		// 特殊处理值
		List<String> valueList = new ArrayList<String>();
		String[] valueStr = StringUtils.split(value, ":");
		if (valueStr.length == 2 && !StringUtils.endsWith(value, ":")) {
			value = value + ":";
		}
		if (valueStr.length > 0) {
			Pattern patternValue = Pattern.compile(regex);
			Matcher matcherValue = patternValue.matcher(value);
			while (matcherValue.find()) {
				valueList.add(matcherValue.group(1));
				valueList.add(matcherValue.group(2));
				valueList.add(matcherValue.group(3));
			}
		} else {
			valueList.add("");
			valueList.add("");
			valueList.add("");
		}
		for (int i = 0; i < paramsList.size(); i++) {
			if (StringUtils.contains(paramsList.get(i), "$")) {
				String key = paramsList.get(i).trim();
				returnMap.put(key.substring(2, key.length() - 1), StringUtils.defaultIfBlank(valueList.get(i), ""));
			}
		}
		return returnMap;
	}

	/**
	 * @author zhougz
	 * @param catalogs
	 * @param catalogId
	 */
	public static Map<Object, Object> readPropertiesData(Map<String, ConfigureCatalog> catalogs, String catalogId) {
		Map<Object, Object> resultMap = new HashMap<Object, Object>();

		ConfigureCatalog catalog = catalogs.get(catalogId);
		List<Generate> list = catalog.getTemplates();
		for (Generate generate : list) {
			String template = generate.getTemplate();
			if (template.endsWith("properties")) {
				try {
					// 项目下的路径
					Resource resource = new ClassPathResource(CONFIG_PATH + template);
					String templateFilePath = resource.getURL().getPath();
					// windows的C盘或linux的usr/local下的路径
					String runtimeFilePath = EnvironmentUtils.getFileAbsolutePath(template);

					// 判断properties文件是否存在
					boolean flag = fileExists(runtimeFilePath);
					Resource configResource = new ClassPathResource(CONFIG_PATH + catalog.getConfig());
					String configPath = configResource.getURL().getPath();
					Map<String, Object> xmlReadMap = readXml(configPath);
					if (flag) {
						fileCopy(templateFilePath, runtimeFilePath);
						resultMap.putAll(xmlReadMap);
						break;
					} else {
						Properties rprop = new Properties();
						rprop.load(new FileInputStream(runtimeFilePath));

						Properties tprop = new Properties();
						tprop.load(new FileInputStream(templateFilePath));
						if(rprop.size() == 0){
							//resultMap.putAll(readXml(configPath));这样做很有可能把前面的覆盖了，改成原来没有存放进去，存在还是保持原样
							if(resultMap.isEmpty())
							{
								resultMap.putAll(xmlReadMap);
							}
							else
							{
								Map<String, Object> configMap = xmlReadMap;
								if(configMap != null && !configMap.isEmpty())
								{
									for(Entry<String, Object> entry : configMap.entrySet())
									{
										if(!resultMap.containsKey(entry.getKey()))
										{
											resultMap.put(entry.getKey(), entry.getValue());
										}
									}
								}
							}
						}else{
						Iterator<Object> iter = tprop.keySet().iterator();
						while (iter.hasNext()) {
							String key = (String)iter.next();
							String runValue = rprop.getProperty(key);
							if (StringUtils.isNotBlank(runValue) && PropertiesUtils.isPassword(key, runValue) && !StringUtils.contains(runValue, "{")) {
								runValue = TripleDes.decode(runValue);
						}
							String templateValue = tprop.getProperty(key);
							resultMap.putAll(analysisValue(templateValue, runValue,xmlReadMap));
						}
						}
					}
				} catch (IOException e) {
					logger.error("读取配置列表" + template + "出错", e);
				}
			}
		}
		return resultMap;
	}

	/**
	 * 判断文件是否存在
	 * @author zhougz
	 * @param filePath
	 */
	private static boolean fileExists(String filePath) {

		// 判断文件是否存在
		File file = new File(filePath);
		// 不存在返回true
		if (!file.exists()) {
			return true;
		}
		return false;
	}

	/**
	 * 复制文件操作
	 * @author zhougz
	 * @param original 原始文件
	 * @param targets 复制的后的目标文件
	 */
	public static void fileCopy(String original, String targets) {
		FileInputStream fi = null;
		FileOutputStream fo = null;
		FileChannel in = null;// 得到对应的文件通道
		FileChannel out = null;// 得到对应的文件通道
		try {
			File originalFile = new File(original);
			fi = new FileInputStream(originalFile);
			File targetsFile = new File(targets);
			fo = new FileOutputStream(targetsFile);
			in = fi.getChannel();// 得到对应的文件通道
			out = fo.getChannel();// 得到对应的文件通道
			in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
		} catch (IOException e) {
			logger.error("初始化properties配置文件失败！" + e);
		} finally {
			try {
				fi.close();
				in.close();
				fo.close();
				out.close();
			} catch (IOException e) {
				logger.error("properties配置文件初始化中失败！" + e);
			}
		}
	}

	/**
	 * 读取xml文件
	 * @author zhougz
	 * @param filePath xml文件路径
	 * @return
	 */
	public static Map<String, Object> readXml(String filePath) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		SAXReader sax = new SAXReader();// 创建一个SAXReader对象
		File xmlFile = new File(filePath);// 根据指定的路径创建file对象
		try {
			Document document = sax.read(xmlFile);
			// 获取document对象,如果文档无节点，则会抛出Exception提前结束
			Element root = document.getRootElement();// 获取根节点
			resultMap.putAll(getNodes(root, paramMap));// 从根节点开始遍历所有节点
		} catch (DocumentException e) {
			logger.error("xml:" + filePath);
			logger.error("读取xml文件失败!" + e);
		}
		return resultMap;
	}

	/**
	 * @author zhougz
	 * @param node xml节点
	 * @param map 返回map用的
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getNodes(Element node, Map<String, Object> map) {

		if (StringUtils.equals(node.getName(), "property")) {
			// 当前节点的所有属性的list
			List<Attribute> listAttr = node.attributes();
			String key = "";
			String value = "";
			// 遍历当前节点的所有属性
			for (Attribute attr : listAttr) {
				// 属性名称
				String attrName = attr.getName();
				// 属性的值
				String attrValue = attr.getValue();
				if (StringUtils.equals(attrName, "name")) {
					key = attrValue;
				}
				if (StringUtils.equals(attrName, "default")) {
					value = attrValue;
				}
			}
			if (StringUtils.isNotBlank(key)) {
				map.put(key, value);
			}
		}

		// 递归遍历当前节点所有的子节点,所有一级子节点的list
		List<Element> listElement = node.elements();
		// 遍历所有一级子节点
		for (Element e : listElement) {
			// 递归
			getNodes(e, map);
		}
		return map;
	}
}
