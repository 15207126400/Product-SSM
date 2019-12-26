package com.yunwei.common.util;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.management.ReflectionException;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * 功能说明: <br>
 * 系统版本: v1.0<br>
 * 开发人员: @author zhangjh<br>
 * 开发时间: 2018年3月8日<br>
 * ===========修改记录===================
 * 修改单：M201408310005 20140925 lizc@hundsun.com
 * 优化获取本地服务ip的方法，使用NetworkInterface.getNetworkInterfaces遍历获取本地ip地址，解决InetAddress.getLocalHost()在Linux无法获取本地实际IP地址的问题
 */
public class WebServerUtil {

	private static Logger logger = LoggerFactory.getLogger(WebServerUtil.class);

	public static String getHttpPort() throws AttributeNotFoundException, InstanceNotFoundException, MBeanException, ReflectionException {
		return getServerPort(false);
	}

	public static String getHttpsPort() throws AttributeNotFoundException, InstanceNotFoundException, MBeanException, ReflectionException {
		return getServerPort(true);
	}
	
	/**
	 * 获取本地IP地址
	 * @return
	 * @throws UnknownHostException
	 */
	public static String getHostAddress() throws UnknownHostException {
		Enumeration<NetworkInterface> netInterfaces = null;
		List<InetAddress> tmpList = new ArrayList<InetAddress>();
		try {
			netInterfaces = NetworkInterface.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface ni = netInterfaces.nextElement();
				byte[] mac = ni.getHardwareAddress();
				if (mac == null || mac.length == 0) {
					continue;
				}
				Enumeration<InetAddress> ips = ni.getInetAddresses();
				while (ips.hasMoreElements()) {
					InetAddress ip = ips.nextElement();
					if (ip instanceof Inet6Address) {
						continue;
					}
					tmpList.add(ip);
				}
			}
			Map<String, String> iplev = new HashMap<String, String>();
			//Map<String, Object> configMap = RedisClientUtil.parseConfigXml();
			//Set<String> sentinels = (Set<String>)configMap.get("servers");
			for (InetAddress inetAddress : tmpList) {
				String localIp = inetAddress.getHostAddress();
				String[] ips = localIp.split("\\.");
//				for (String redisIp : sentinels) {
//					String[] rips = redisIp.split("\\:")[0].split("\\.");
//					for (int i = 0; i < rips.length; i++) {
//						if (!StringUtils.equals(ips[i], rips[i])) {
//							break;
//						} else {
//							iplev.put(i + "", localIp);
//						}
//					}
//				}
			}
			String realIp = "";
			for (int i = 3; i >= 0; i--) {
				realIp = MapUtils.getString(iplev, i + "", "");
				if (!"".equals(realIp)) {
					break;
				}
			}
			if (!"".equals(realIp)) {
				return realIp;
			}
			if (tmpList.size() > 0) {
				return tmpList.get(0).getHostAddress();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return InetAddress.getLocalHost().getHostAddress();
	}

	/**
	 * 获取服务端口
	 * @return
	 * @throws Exception
	 */
	public static String getServicePort() throws Exception {
		String port = getHttpPort();
		if (StringUtils.isEmpty(port)) {
			port = getHttpsPort();
		}
		return port;
	}

	/**
	 * 获取本地服务地址
	 * @return
	 * @throws Exception
	 */
	public static String getServiceAddress() throws Exception {
		return getServiceAddress(ContextLoader.getCurrentWebApplicationContext());
	}

	/**
	 * 获取本地服务地址
	 * @return
	 * @throws Exception
	 */
	public static String getServiceAddress(ApplicationContext context) throws Exception {
		String contextPath;
		if (context == null || !(context instanceof WebApplicationContext)) {
			contextPath = ContextLoader.getCurrentWebApplicationContext().getServletContext().getContextPath();
		} else {
			contextPath = ((WebApplicationContext)context).getServletContext().getContextPath();
		}
		String hostAddress = getHostAddress();
		String port = getHttpPort();
		if (StringUtils.isEmpty(port)) {
			port = WebServerUtil.getHttpsPort();
			return "https://" + hostAddress + ":" + port + contextPath;
		}
		return "http://" + hostAddress + ":" + port + contextPath;
	}

	/**
	 * 获取服务端口号
	 * @return 端口号
	 * @throws ReflectionException
	 * @throws MBeanException
	 * @throws InstanceNotFoundException
	 * @throws AttributeNotFoundException
	 */
	private static String getServerPort(boolean secure) throws AttributeNotFoundException, InstanceNotFoundException, MBeanException, ReflectionException {
		MBeanServer mBeanServer = null;
		if (MBeanServerFactory.findMBeanServer(null).size() > 0) {
			mBeanServer = (MBeanServer)MBeanServerFactory.findMBeanServer(null).get(0);
		}
		// 2014-9-3 yejg 修改findbugs检测出来的问题，修改单：M201408310008 ----- begin
		if (mBeanServer == null) {
			logger.debug("调用findMBeanServer查询到的结果为null");
			return "";
		}
		// 2014-9-3 yejg 修改findbugs检测出来的问题，修改单：M201408310008 ----- end
		Set<ObjectName> names = null;
		try {
			names = mBeanServer.queryNames(new ObjectName("Catalina:type=Connector,*"), null);
		} catch (Exception e) {
			return "";
		}
		Iterator<ObjectName> it = names.iterator();
		ObjectName oname = null;
		while (it.hasNext()) {
			oname = (ObjectName)it.next();
			String protocol = (String)mBeanServer.getAttribute(oname, "protocol");
			String scheme = (String)mBeanServer.getAttribute(oname, "scheme");
			Boolean secureValue = (Boolean)mBeanServer.getAttribute(oname, "secure");
			Boolean SSLEnabled = (Boolean)mBeanServer.getAttribute(oname, "SSLEnabled");
			if (SSLEnabled != null && SSLEnabled) {// tomcat6开始用SSLEnabled
				secureValue = true;// SSLEnabled=true但secure未配置的情况
				scheme = "https";
			}
			if (protocol != null && ("HTTP/1.1".equals(protocol) || protocol.contains("http"))) {
				if (secure && "https".equals(scheme) && secureValue) {
					return ((Integer)mBeanServer.getAttribute(oname, "port")).toString();
				} else if (!secure && !"https".equals(scheme) && !secureValue) {
					return ((Integer)mBeanServer.getAttribute(oname, "port")).toString();
				}
			}
		}
		return "";
	}
}
