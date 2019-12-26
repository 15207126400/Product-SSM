package com.yunwei.common.view.freemarker;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;

import com.yunwei.common.util.RequestUtil;



/**
 * layout传值
* @ClassName: LayoutModel 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年6月13日 下午5:26:07 
*
 */
public class LayoutModel {

	public static void addAttribute(String key, Object value) {
		HttpServletRequest request = RequestUtil.getRequest();
		if (value instanceof List) {
			List list = (List)request.getAttribute(key);
			if (list != null) {
				value = CollectionUtils.union(list, (List)value);
			}
		}
		request.setAttribute(key, value);
	}
}
