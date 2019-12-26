package com.yunwei.common.exception;

import java.util.Map;

/**
 * 功能说明: 业务基础异常类<br>
 * 系统版本: v1.0<br>
 * 开发人员: @author zhangjh<br>
 * 开发时间: 2018年3月8日<br>
 */
public class BizException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String error_no;
	private String error_info;

	private Map<String, Object> infos;

	public BizException(String error_no, String error_info) {
		super(error_info);
		this.error_no = error_no;
		this.error_info = error_info;
	}

	public BizException(String error_no, String error_info, Exception e) {
		super(error_info, e);
		this.error_no = error_no;
		this.error_info = error_info;
	}

	public BizException(String message) {
		super(message);
		this.error_no = "-1";
		this.error_info = message;
	}

	public BizException(Exception e) {
		super(e);
		this.error_no = "-1";
		this.error_info = e.getLocalizedMessage();
	}

	public BizException(String message, Exception e) {
		super(message, e);
		this.error_no = "-1";
		this.error_info = message;
	}

	public String getError_no() {
		return error_no;
	}

	public String getError_info() {
		return error_info;
	}

	public Map<String, Object> getInfos() {
		return infos;
	}

	public void setInfos(Map<String, Object> infos) {
		this.infos = infos;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [error_no=" + error_no + "]" + " [error_info=" + error_info + "]";
	}
}
