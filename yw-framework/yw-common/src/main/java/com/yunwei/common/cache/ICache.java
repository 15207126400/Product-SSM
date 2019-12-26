package com.yunwei.common.cache;

import java.io.Serializable;
import java.util.Map;

import org.springframework.core.Ordered;

/**
 * 功能说明: 统一缓存<br>
 * 系统版本: v1.0<br>
 * 开发人员: @author zhangjh<br>
 * 开发时间: 2018年3月8日<br>
 */
public interface ICache<T> extends Serializable, Ordered {

	public void refresh() throws Exception;

	public String getId();

	public Map<String, T> getConfigData();
}
