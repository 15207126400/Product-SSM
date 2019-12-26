package com.yunwei.context.config.configure;


import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunwei.common.util.EnvironmentUtils;
import com.yunwei.common.util.PropertiesUtils;
import com.yunwei.common.utils.encrypt.TripleDes;
import com.yunwei.context.config.configure.element.Config;
import com.yunwei.context.config.configure.parser.ConfigureParser;


public class XMLConfigPaser {

	private static Logger logger = LoggerFactory.getLogger(XMLConfigPaser.class);

	private static final String CONFIG_PATH = "configure";

	private static final String ORI_DATA_SEPERATOR = "\r\n####\r\n";
	

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
	}*/

	/**
	 * 获取配置HTML内容
	 * @param catatlogId
	 * @return
	 */
	public static String getConfigureHTML(String catatlogId, Map<Object, Object> configValues,Map<String, ConfigureCatalog> catalogs) {
		
		ConfigureCatalog catalog = catalogs.get(catatlogId);
		if (catalog != null) {
			Config config = paserConfigureTemplate(catalog.getConfig(), configValues);
			return config.toHTML();
		} else {
			return "没有找到此配置内容";
		}
	}

	public static void generateTemplateFile(final Map<String, String> params, String catalogId,Map<String, ConfigureCatalog> catalogs) {
		ConfigureCatalog catalog = catalogs.get(catalogId);
		if (catalog != null) {
			try {
//				Map<Object, Object> existsConfigData = loadConfigData(false);
//				existsConfigData.putAll(params);
//				saveOriDataToFile(existsConfigData);
//				logger.info("save original data to file success: " + catalogId);
			} catch (Exception e) {
				logger.error("save original data to file failed: " + catalogId, e);
			}

			List<Generate> generates = catalog.getTemplates();
			boolean failure = false;
			for (int i = 0; i < generates.size(); i++) {
				Generate generate = generates.get(i);
				boolean tranToUnicode = false;
				try {
					String template = generate.getTemplate();
					String templatePath = getTemplateFilePath(template);
					String templateContent = readFromFile(new File(templatePath));
					if (null == templateContent) {
						failure = true;
						continue;
					}
					if (template.endsWith("properties")) {
						tranToUnicode = true;
					}
					String targetFilePath = EnvironmentUtils.getRuntimeConfigPath() + template;
					
					String  mkDir = template.substring(0, template.substring(1).indexOf("/")+1);
					
					createFileAndDir(EnvironmentUtils.getRuntimeConfigPath() + mkDir,targetFilePath);
					
					String valuedContent = genValuedContent(templateContent, params, tranToUnicode);
					writeToFile(new File(targetFilePath), valuedContent);
					generate.setModifyTime(System.currentTimeMillis());
				} catch (Exception e) {
					logger.error("生成文件失败", e);
					failure = true;
				}
			}
			if (failure) {
				catalog.setStatus(1);
			} else {
				catalog.setStatus(2);
			}
		}
	}

	/**
	 * 生成文件夹、文件
	 *@author zhougz
	 * @param mkDir
	 * @param filePath
	 */
	private static void createFileAndDir(String mkDir,String filePath){

		//检查文件夹是否存在
		File dirFile =new File(mkDir);    
		if  (!dirFile .exists()  && !dirFile .isDirectory()) {
			//创建文件夹
			dirFile .mkdir();    
		}
		
		//判断文件是否存在
		File file = new File(filePath);
		if(!file.exists()){
			//创建文件
			try {
				file.createNewFile();
			} catch (IOException e) {
				logger.error("生成文件失败", e);
			}
		}
	}
	
	private static String getTemplateFilePath(String template) {
		return Thread.currentThread().getContextClassLoader().getResource("/").getPath() + "/" + CONFIG_PATH + "/" + template;
	}

	/*private static void saveOriDataToFile(Map<Object, Object> params) throws IOException {
		String filePath = EnvironmentUtils.getRuntimeConfigPath() + "/data";
		File file = new File(filePath);
		StringBuilder sBuilder = new StringBuilder(4096);
		sBuilder.append("解析时的entry分隔符为\\r\\n#####\\r\\n，文档尾部也有一个分隔符，如需手动编辑本文件，请勿编辑分隔符").append(ORI_DATA_SEPERATOR);
		for (Entry<Object, Object> paramEntry : params.entrySet()) {
			sBuilder.append(paramEntry.getKey()).append('=').append(paramEntry.getValue()).append(ORI_DATA_SEPERATOR);
		}
		String content = sBuilder.toString();
		
		writeToFile(file, content);
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

	private static String readFromFile(File file) {
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
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

	private static void writeToFile(File file, String content) throws IOException {
		File dir = file.getParentFile();
		if (!dir.isDirectory() && !dir.mkdirs()) {
			throw new IOException("create dir failed: " + dir.getAbsolutePath());
		}
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		try {
			fos = new FileOutputStream(file);
			osw = new OutputStreamWriter(fos, "UTF-8");
			osw.write(content);
			osw.flush();
		} finally {
			close(osw);
			close(fos);
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

	private static Map<String, String> tranToUnicodeMap(Map<String, String> params) {
		Map<String, String> newParams = new HashMap<String, String>();
		Iterator<Map.Entry<String, String>> iter = params.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, String> entry = iter.next();
			newParams.put(entry.getKey(),chinaToUnicode(entry.getValue().replace("＼＼", "\\").trim()));
		}
		return newParams;
	}

	private static String genValuedContent(String templateContent, Map<String, String> configData, boolean tranToUnicode) {
		if (tranToUnicode) {
			configData = tranToUnicodeMap(configData);
		}
		return StrSubstitutor.replace(templateContent, configData, "${", "}");
	}

	private static Config paserConfigureTemplate(String templatePath, Map<Object, Object> dataMap) {

		try {
			templatePath = getTemplateFilePath(templatePath);

			ConfigureParser parser = new ConfigureParser();
			parser.setDataStore(dataMap);

			Config config = parser.parserConfigureFromFullPath(templatePath);

			return config;
		} catch (Exception e) {
			logger.error("读取配置模板" + templatePath + "出错", e);
		}
		return null;
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
}
