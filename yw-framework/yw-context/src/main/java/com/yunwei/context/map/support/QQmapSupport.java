package com.yunwei.context.map.support;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yunwei.common.exception.BizException;
import com.yunwei.common.middleware.wx.SisapService;
import com.yunwei.common.middleware.wx.SisapServiceImpl;
import com.yunwei.common.util.FastJsonUtil;
import com.yunwei.common.util.HttpClientUtil;
import com.yunwei.context.map.BaseMap;

/**
 * 腾讯地图服务
* @ClassName: QQmapSupport 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年9月28日 下午5:08:34 
*
 */
@Component("qQmapSupport")
public class QQmapSupport implements BaseMap{
	
	private static Logger logger = Logger.getLogger(QQmapSupport.class);
	
	private final static String LocationToAddress =  "https://apis.map.qq.com/ws/geocoder/v1/";// 逆地址解析
	
	private final static String addressToLocation =  "https://apis.map.qq.com/ws/geocoder/v1/";// 地址解析
	
	private final static String getLocationDistance =  "https://apis.map.qq.com/ws/distance/v1/";// 距离计算
	
//	private final static String key =  "JR6BZ-SHK6X-C6U4M-7P2KR-KTCIH-RNFQV";// 地图调用key(个人调用)
	
	private final static String key =  "BCJBZ-UU2LJ-L26FZ-FBLMV-AGG3E-LAFKZ";// 地图调用key(企业调用)

    @Autowired
    private SisapService sisapService;
	
	@Override
	public Map<String,Object> addressToLocation(String address) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(addressToLocation);
		buffer.append("?");
		buffer.append("address=");
		buffer.append(address);
		buffer.append("&key=");
		buffer.append(key);
		Map<String,Object> resultMap =  sisapService.execute(buffer.toString());
		if(!resultMap.get("status").equals(0)){
			throw new BizException("地址解析错误");
		}
		return resultMap;
		
	}

	@Override
	public Map<String,Object> LocationToAddress(String location) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(LocationToAddress);
		buffer.append("?");
		buffer.append("location=");
		buffer.append(location);
		buffer.append("&key=");
		buffer.append(key);
		Map<String,Object> resultMap =  sisapService.execute(buffer.toString());
		if(!resultMap.get("status").equals(0)){
			throw new BizException("地址解析错误");
		}
		return resultMap;
	}

	@Override
	public Map<String,Object> getLocationDistance(String from, String to) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(getLocationDistance);
		buffer.append("?");
		buffer.append("from=");
		buffer.append(from);
		buffer.append("&to=");
		buffer.append(to);
		buffer.append("&key=");
		buffer.append(key);
		Map<String,Object> resultMap =  sisapService.execute(buffer.toString());
		if(!resultMap.get("status").equals(0)){
			throw new BizException("地址解析错误");
		}
		return resultMap;
	}

	public static void main(String[] args) {
		QQmapSupport qmapSupport = new QQmapSupport();
		qmapSupport.getLocationDistance("39.983171,116.308479", "39.949227,116.394310");
	}
	
}
