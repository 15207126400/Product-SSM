package com.yunwei.common.util;
/**
 * 
* @ClassName: Page 
* @Description: 分页工具类 
* @author 晏飞
* @date 2017�?11�?29�? 下午4:56:12 
*
 */

public class Page {
	private int sum;//数据总条数
	private int current;//当前页数
	private int number;//每页显示条数
	private int sumPage;//总页数
	public int getSum() {
		return sum;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	public int getCurrent() {
		return current;
	}
	public void setCurrent(int current) {
		if(current<1){
			this.current=1;
		}else if(current>sumPage){
			this.current = sumPage;
		}else{
			this.current = current;
		}
		
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		if(number<=0){
			this.number=5;
		}
		this.number = number;
	}
	public void setSumPage() {
		int a=sum%number;
		if(a!=0){
			this.sumPage= sum/number+1;
		}else{
			this.sumPage= sum/number;
		}
	}
	public int getSumPage() {
		return this.sumPage;
	}
	
}
