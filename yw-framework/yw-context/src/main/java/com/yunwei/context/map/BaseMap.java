package com.yunwei.context.map;

import java.util.Map;

/**
 * 基本地图map接口
* @ClassName: BaseMap 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年9月28日 下午4:55:27 
*
 */
public interface BaseMap {

	/**
	 * 地址解析(地址转坐标)
	 * @param address[address=北京市海淀区彩和坊路海淀西大街74号]
	 * @return
	 */
	public Map<String,Object> addressToLocation(String address);
	
	/**
	 * 逆地址解析(坐标位置描述)
	 * @param location[位置坐标，格式：location=lat<纬度>,lng<经度>]
	 * @return
	 */
	public Map<String,Object> LocationToAddress(String location);
	
	/**
	 * 距离计算
	 * @param from 起点坐标，格式：lat,lng;lat,lng... （经度与纬度用英文逗号分隔，坐标间用英文分号分隔）[from=39.071510,117.190091]
	 * @param to 终点坐标，格式：lat,lng;lat,lng... 
（经度与纬度用英文逗号分隔，坐标间用英文分号分隔）
注意：本服务支持单起点到多终点，或多起点到单终点，from和to参数仅可有一个为多坐标[to=39.071510,117.190091;40.007632,116.389160;39.840177,116.463318]
	 * @return
	 */
	public Map<String,Object> getLocationDistance(String from,String to);
}
