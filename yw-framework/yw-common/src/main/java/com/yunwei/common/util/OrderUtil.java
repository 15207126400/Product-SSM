package com.yunwei.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 订单工具类
* @ClassName: OrderUtil 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年4月27日 下午3:40:37 
*
 */
public class OrderUtil {
	
	private static long orderNum = 0l;    
	private static String date ;
	
	/**  
     * 生成订单编号  
     * @return  
     */    
    public static String getOrderNo() {    
        String str = new SimpleDateFormat("yyMMddHHmm").format(new Date());    
        if(date==null||!date.equals(str)){    
            date = str;    
            orderNum  = 0l;    
        }    
        orderNum ++;    
        long orderNo = Long.parseLong((date)) * 10000;    
        orderNo += orderNum;   
        return orderNo+"";    
    }
    
    /**  
     * 生成退款编号  
     * @return  
     */    
    public static String getRefundSn()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String format = dateFormat.format(new Date());
         int max=24;
         int min=3;
         Random random = new Random();
          int s = random.nextInt(max)%(max-min+1) + min;
          StringBuffer buffer =new StringBuffer();
          for(int i=0;i<s;i++)
          {
              Integer val = (int)(Math.random()*9+1);
              buffer.append(val.toString());
          }
        return format+buffer.toString();
    }
    
}
