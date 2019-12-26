package com.yunwei.common.exception;

import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

/**
 * 功能说明: 单个入参校验不通过的专用异常<br>
 * 系统版本: v1.0<br>
 * 开发人员: @author yejg<br>
 * 开发时间: 2015年8月28日<br>
 */
public class StringArgBindException extends BindException {

	private static final long serialVersionUID = 1L;

	/*
	 * 说明：<pre>
	 * StringArgumentValidResolver中校验数据抛出的异常，在BindExceptionHandler中处理时，
	 * FiledError中的filed中存放的是StringValidBean对象中的“value”字符串，而不是真实的入参名。
	 * 真实的入参名存在于FiledError中的objectName中了。
	 * 
	 * 普通的Form校验时，出错的字段名字是存在于FiledError中的filed字段的。
	 * 
	 * 为了方便处理，在StringArgumentValidResolver中统一抛出StringArgBindException异常。
	 * </pre>
	 */
	public StringArgBindException(BindingResult bindingResult) {
		super(bindingResult);
	}
}
