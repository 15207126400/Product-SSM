package com.yunwei.common.mvc.xss.filter;

import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 功能说明: XSS攻击防御<br>
 * 系统版本: v1.0<br>
 * 开发人员: @author kongdy<br>
 * 开发时间: 2015年10月8日<br>
 */
public class XSSHttpServletRequestWrapper extends HttpServletRequestWrapper {

	private HttpServletRequest request;

	public XSSHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
		this.request = request;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	/**
	 * Override the original getParameter() method ,
	 * so that it can filter all the parameter name and parameter value
	 * then use replace the special character that may cause XSS attack
	 */
	@Override
	public String getParameter(String name) {

		String value = super.getParameter(encodeXSS(name));

		if (value != null) {
			value = encodeXSS(value);
		}

		return value;
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> map = super.getParameterMap();
		Map<String, String[]> newMap = new LinkedHashMap<String, String[]>();
		for (Map.Entry<String, String[]> entry : map.entrySet()) {
			String[] arr = entry.getValue();
			if (ArrayUtils.isEmpty(arr)) {
				newMap.put(encodeXSS(entry.getKey()), arr);
			} else {
				String[] newArr = new String[arr.length];
				for (int i = 0; i < arr.length; i++) {
					newArr[i] = encodeXSS(arr[i]);
				}
				newMap.put(encodeXSS(entry.getKey()), newArr);
			}
		}
		return newMap;
	}

	@Override
	public Enumeration<String> getParameterNames() {
		Enumeration<String> names = super.getParameterNames();
		Set<String> newNames = new LinkedHashSet<String>();
		while (names.hasMoreElements()) {
			newNames.add(encodeXSS(names.nextElement()));
		}
		return Collections.enumeration(newNames);
	}

	@Override
	public String[] getParameterValues(String name) {
		String[] arr = super.getParameterValues(encodeXSS(name));
		if (ArrayUtils.isEmpty(arr)) {
			return arr;
		}
		String[] newArr = new String[arr.length];
		for (int i = 0; i < arr.length; i++) {
			newArr[i] = encodeXSS(arr[i]);
		}
		return newArr;
	}

	/**
	 * Override the original getHeader() method ,
	 * so that it can filter all the parameter name and parameter value
	 * then use replace the special character that may cause XSS attack
	 */
	@Override
	public String getHeader(String name) {

		String value = super.getHeader(encodeXSS(name));

		if (value != null) {
			value = encodeXSS(value);
		}
		return value;
	}

	/**
	 * replace all the characters that may cause XSS attack from half-width character
	 * to full-width character
	 * @param s
	 * @return
	 */
	private String encodeXSS(String s) {
		if (s == null || "".equals(s)) {
			return s;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {

			// handle the '<' and '>' which can be used for constructing <script> and </script>
				case '>':
					sb.append('＞');
					break;
				case '<':
					sb.append('＜');
					break;

				// since the html can support the characters using $#number format
				// so here also need to escape '#','&' and quote symbol
				case '\'':
					sb.append('‘');
					break;
				case '\"':
					sb.append('“');
					break;
				case '&':
					sb.append('＆');
					break;
				case '\\':
					sb.append('＼');
					break;
				case '#':
					sb.append('＃');
					break;

				// if not the special characters ,then output it directly
				default:
					sb.append(c);
					break;
			}
		}
		return sb.toString();
	}
}