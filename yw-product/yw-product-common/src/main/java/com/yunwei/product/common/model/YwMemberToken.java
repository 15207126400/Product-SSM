package com.yunwei.product.common.model;
/**
 * 
* @ClassName: ShopToken 
* @Description: token数据实体类
* @author 晏飞
* @date 2017年11月27日 上午10:02:56 
*
 */
public class YwMemberToken {
	private int id;
	private String access_token;
	private String expires_in;
	private String createBy;
	private String startTime;
	private String endTime;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	
}
