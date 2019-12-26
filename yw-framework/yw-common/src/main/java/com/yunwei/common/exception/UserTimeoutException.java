package com.yunwei.common.exception;


/**
 * 功能说明: <br>
 * 系统版本: v1.0<br>
 * 开发人员: @author zhangjh<br>
 * 开发时间: 2018年3月20日<br>
 */
public class UserTimeoutException extends BizException {

	public UserTimeoutException(String error_no, String error_info) {
		super(error_no, error_info);
	}

}
