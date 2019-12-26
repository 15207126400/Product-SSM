package com.yunwei.product.common.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
* @ClassName: YwTeamActivity 
* @Description: 拼团活动配置表 
* @author zhangjh
* @date 2018年3月20日 下午4:26:12 
*
 */
public class YwTeamActivity {

	private String team_id     ;//
	private String act_name    ;//     '拼团活动标题',
	private String team_type   ;//      '拼团活动类型,0分享团1佣金团2抽奖团',
	private String time_limit  ;//      '成团有效期：单位（秒)',
	private String team_price  ;//   拼团价',
	private String needer      ;//      '需要成团人数',
	private String product_name;//   '商品名称',
	private String product_id  ;//     '商品id',
	private String item_id     ;//            '商品规格id',
	private String bonus       ;//  '团长佣金',
	private String stock_limit ;//    '抽奖限量',
	private String buy_limit   ;//  '单次团购买限制数0为不限制',
	private String sales_sum   ;//  '已拼多少件',
	private String virtual_num ;// '虚拟销售基数',
	private String share_title ;// '分享标题',
	private String share_desc  ;// '分享描述',
	private String share_img   ;//  '分享图片',
	private String sort        ;//  '排序',
	private String is_recommend;//  '是否推荐',
	private String status      ;//     '0关闭1正常',
	private String is_lottery  ;// '是否已经抽奖.1是，0否',
	private String product_ids ;// 商品id字符串
	/**
	 * 关联查询临时字段
	 */
	private String price;	// 临时商品价格
	private String url;		// 临时商品图片
	
	
	
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTeam_id() {
		return team_id;
	}
	public void setTeam_id(String team_id) {
		this.team_id = team_id;
	}
	public String getAct_name() {
		return act_name;
	}
	public void setAct_name(String act_name) {
		this.act_name = act_name;
	}
	public String getTeam_type() {
		return team_type;
	}
	public void setTeam_type(String team_type) {
		this.team_type = team_type;
	}

	public String getTeam_price() {
		return team_price;
	}
	public void setTeam_price(String team_price) {
		this.team_price = team_price;
	}
	public String getNeeder() {
		return needer;
	}
	public void setNeeder(String needer) {
		this.needer = needer;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getItem_id() {
		return item_id;
	}
	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}
	public String getBonus() {
		return bonus;
	}
	public void setBonus(String bonus) {
		this.bonus = bonus;
	}
	public String getStock_limit() {
		return stock_limit;
	}
	public void setStock_limit(String stock_limit) {
		this.stock_limit = stock_limit;
	}
	public String getBuy_limit() {
		return buy_limit;
	}
	public void setBuy_limit(String buy_limit) {
		this.buy_limit = buy_limit;
	}
	public String getSales_sum() {
		return sales_sum;
	}
	public void setSales_sum(String sales_sum) {
		this.sales_sum = sales_sum;
	}
	public String getVirtual_num() {
		return virtual_num;
	}
	public void setVirtual_num(String virtual_num) {
		this.virtual_num = virtual_num;
	}
	public String getShare_title() {
		return share_title;
	}
	public void setShare_title(String share_title) {
		this.share_title = share_title;
	}
	public String getShare_desc() {
		return share_desc;
	}
	public void setShare_desc(String share_desc) {
		this.share_desc = share_desc;
	}
	public String getShare_img() {
		return share_img;
	}
	public void setShare_img(String share_img) {
		this.share_img = share_img;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getIs_recommend() {
		return is_recommend;
	}
	public void setIs_recommend(String is_recommend) {
		this.is_recommend = is_recommend;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIs_lottery() {
		return is_lottery;
	}
	public void setIs_lottery(String is_lottery) {
		this.is_lottery = is_lottery;
	}
	public String getProduct_ids() {
		return product_ids;
	}
	public void setProduct_ids(String product_ids) {
		this.product_ids = product_ids;
	}
	public String getTime_limit() {
		return time_limit;
	}
	public void setTime_limit(String time_limit) {
		this.time_limit = time_limit;
	}
	
	
	
}
