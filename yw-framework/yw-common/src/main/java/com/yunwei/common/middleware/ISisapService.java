package com.yunwei.common.middleware;

import java.net.URL;
import java.util.Map;

import com.yunwei.common.user.SysUser;


public interface ISisapService {

	public byte[] callBinary(URL url, Map<String, Object> params);

	public byte[] callBinary(String functionId, Map<String, Object> params);

	public SisapResult call(String functionId, Map<String, Object> params);

	public SisapResult call(String functionId, Map<String, Object> params, SysUser user);

	public SisapResult call(String sisap_url, String functionId, Map<String, Object> params);

	public SisapResult call(String sisap_url, String functionId, Map<String, Object> params, SysUser user);

}
