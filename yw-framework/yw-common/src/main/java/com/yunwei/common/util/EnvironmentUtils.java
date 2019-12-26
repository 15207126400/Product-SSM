package com.yunwei.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class EnvironmentUtils {

	private static final Log log = LogFactory.getLog(EnvironmentUtils.class);

	private static final String CLASSPATH_PREFIX = "classpath:";

	/**
	 * 获取系统运行期配置文件路径
	 * @return
	 */
	public static String getRuntimeConfigPath() {
		String path = System.getenv().get("RUNTIME_CONFIG_ROOT");
		if (StringUtils.isBlank(path)) {
			String os = System.getProperty("os.name").toLowerCase();
			if (os.indexOf("win") >= 0) {
				path = "C:" + File.separator + "runtime_config_root";
			} else {
				path = "/usr/local/runtime_config_root";
			}
		}
		return path;
	}

	/**
	 * 获取WEB-INF Class 目录地址
	 * @return
	 */
	public static String getWebClassPath() {
		return Thread.currentThread().getContextClassLoader().getResource("/").getPath();
	}

	public static String getWebRootPath() {
		HttpServletRequest request = RequestUtil.getRequest();
		if (request != null) {
			return request.getServletContext().getRealPath("/");
		}
		String webClassPath = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
		File file = new File(webClassPath);
		return file.getParentFile().getParentFile().getAbsolutePath();
	}

	public static String getFileAbsolutePath(String filename) throws FileNotFoundException {
		if (filename.startsWith(CLASSPATH_PREFIX)) {
			filename = filename.substring(CLASSPATH_PREFIX.length());
		}
		if (filename.startsWith("/")) {
			filename = filename.substring(1);
			String fileUrl = EnvironmentUtils.getRuntimeConfigPath() + File.separator + filename;
			File file = new File(fileUrl);
			if (file.exists()) {
				return fileUrl;
			}
			fileUrl = getWebClassPath() + filename;
			file = new File(fileUrl);
			if (file.exists()) {
				return fileUrl;
			} else {
				throw new FileNotFoundException(filename + " Not Found In System ClassPath ...");
			}
		} else {
			String configRoot = EnvironmentUtils.getRuntimeConfigPath();
			List<File> list = new ArrayList<File>();
			findFile(new File(configRoot), filename, list);
			if (list.isEmpty()) {
				String fileUrl = getWebClassPath() + filename;
				File file = new File(fileUrl);
				if (file.exists()) {
					return fileUrl;
				} else {
					throw new FileNotFoundException(filename + " Not Found In System ClassPath ...");
				}
			} else {
				return list.get(0).getAbsolutePath();
			}
		}

	}

	/**
	 * 获取应用版本信息
	 * @return
	 */
	public static Map<String, String> getApplicationVersion() {
		Map<String, String> versionMap = new HashMap<String, String>();
		versionMap = getCairhAppVersion();
		// versionMap.putAll(getCairhAppJarVersion());
		return versionMap;
	}

	/**
	 * 获取应用依赖cairh jar版本
	 * @return
	 */
	private static Map<String, String> getCairhAppJarVersion() {
		Map<String, String> versionMap = new HashMap<String, String>();
		URL[] parentUrls = getURLClassLoader().getURLs();
		for (URL url : parentUrls) {
			if (!"file".equals(url.getProtocol())) {
				continue;
			}
			URI entryURI;
			try {
				entryURI = url.toURI();
			} catch (URISyntaxException e) {
				continue;
			}
			File entry = new File(entryURI);
			if (entry.getPath().toLowerCase().indexOf(".jar") <= 0 || entry.getName().toLowerCase().indexOf("cairh-") < 0) {
				continue;
			}
			try {
				JarFile jf = new JarFile(entry.getPath());
				Manifest mf = jf.getManifest();
				if (mf == null) {
					continue;
				}
				Attributes attrs = mf.getMainAttributes();
				if (attrs.getValue("Project-Version") != null) {
					log.info(entry.getName() + ":" + mf.getMainAttributes().getValue("Project-Version"));
					versionMap.put(entry.getName(), attrs.getValue("Project-Version") + "." + attrs.getValue("Build-Time"));
				}
			} catch (IOException e) {
				log.error("获取应用JAR MANIFEST.MF异常", e);
			}
		}
		return versionMap;
	}

	/**
	 * 获取应用版本
	 * @return
	 */
	private static Map<String, String> getCairhAppVersion() {
		Map<String, String> versionMap = new HashMap<String, String>();
		String path = EnvironmentUtils.getWebRootPath() + "/META-INF/version.xml";
		versionMap = readXml(path);
		return versionMap;
	}

	private static URLClassLoader getURLClassLoader() {
		URLClassLoader ucl = null;
		ClassLoader loader = Thread.currentThread().getContextClassLoader();

		if (!(loader instanceof URLClassLoader)) {
			loader = EnvironmentUtils.class.getClassLoader();
			if (loader instanceof URLClassLoader) {
				ucl = (URLClassLoader)loader;
			}
		} else {
			ucl = (URLClassLoader)loader;
		}

		return ucl;
	}

	private static void findFile(File file, String filename, List<File> list) {
		File[] fileList = file.listFiles();
		for (File subFile : fileList) {
			if(subFile.isDirectory()){
				findFile(subFile,filename,list);
			}else{
				if(subFile.getAbsolutePath().contains(filename)){
				list.add(subFile);
			}
			}
		}
	}

	/**
	 * @author zhougz
	 * @param filePath
	 * @return
	 */
	private static Map<String, String> readXml(String filePath) {
		Map<String, String> resultMap = new HashMap<String, String>();
		SAXReader reader = new SAXReader();
		reader.setEncoding("utf-8");
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				log.error("xml文件不存在：" + filePath);
				return resultMap;
			}
			Document doc = reader.read(file);
			for (Object obj : doc.getRootElement().elements()) {
				if (!(obj instanceof Element)) {
					continue;
				}
				Element ele = (Element)obj;
				if (!StringUtils.equals("1", ele.attributeValue("is_cur"))) {
					continue;
				}
				for (Object attr : ele.attributes()) {
					if (attr instanceof Attribute) {
						Attribute attr2 = (Attribute)attr;
						resultMap.put(attr2.getName(), attr2.getValue());
					}
				}
			}
		} catch (DocumentException e) {
			log.error("解析xml文件发生错误", e);
		}

		return resultMap;
	}

	/**
	 * 取文件系统根目录全路径
	 * @return
	 */
	public static String getFileHomePath() {
		String file_sys_home_dir = PropertiesUtils.get("homedir.uploader.cdn", "/");
		if (!file_sys_home_dir.endsWith("/") && !file_sys_home_dir.endsWith("\\")) {
			file_sys_home_dir = file_sys_home_dir + "/";
		}
		return file_sys_home_dir;
	}

}
