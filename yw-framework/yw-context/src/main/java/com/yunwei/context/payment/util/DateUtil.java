package com.yunwei.context.payment.util;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * 
* @ClassName: DateUtil 
* @Description: 日期工具�?
* @author 晏飞
* @date 2017�?12�?26�? 下午2:38:56 
*
 */
public class DateUtil {

	public static SimpleDateFormat dateFormatMillisecond = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat dateFormatWithYMD = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat dateFormatWithYMDHM = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public static SimpleDateFormat dateFormatSSS = new SimpleDateFormat("yyyyMMddHHmmssSSS");

	public static String addDateMinut(Date date, int x) {
		/**
		 * 返回的是字符串型的时间，输入�?//是String	// day, int x
		 * 引号里面个格式也可以�? HH:mm:ss或�?�HH:mm等等，很随意的，不过在主函数调用时，要和输入的变量day格式�?�?
		 */
		if (date == null)
			return "";
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, x);// 24小时�?
		date = cal.getTime();
		cal = null;
		return dateFormat.format(date);
	}
	
	public static void main(String[] args) {
		//String date = addDateMinut(new Date(), 0);
		
		System.out.println((int)((Math.random()*9+1)*1000));
	}
	
	/** 
	 * 获取现在时间 
	 *  
	 * @return返回短时间格式 yyyy-MM-dd 
	 */  
	public static Date getNowDateShort(Date date) {  
	    Date currentTime = date;  
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
	    String dateString = formatter.format(currentTime);  
	    ParsePosition pos = new ParsePosition(8);  
	    Date currentTime_2 = formatter.parse(dateString, pos);  
	    return currentTime_2;  
	} 
	
	  /**  
     * 计算两个日期之间相差的天�?  
     * @param smdate 较小的时�? 
     * @param bdate  较大的时�? 
     * @return 相差天数 
     * @throws ParseException  
     */    
    public static int daysBetween(Date smdate,Date bdate) throws ParseException    
    {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        smdate=sdf.parse(sdf.format(smdate));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));           
    }   
    
    /** 
    *字符串的日期格式的计�? 
    */  
    public static int daysBetween(String smdate,String bdate) throws ParseException{  
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(sdf.parse(smdate));    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(sdf.parse(bdate));    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));     
    }  

	public static Date addDateHour(Date date, int x) {// 返回的是字符串型的时间，输入�?//是String
		// 引号里面个格式也可以�? HH:mm:ss或�?�HH:mm等等，很随意的，不过在主函数调用时，要和输入的变
		// 量day格式�?�?
		if (date == null)
			return new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, x);
		return cal.getTime();
	}
	public static String dateFormatWithData(Date data, SimpleDateFormat format) {
		return format.format(data);
	}
	public static Date dateFormatWithData(String data, SimpleDateFormat format) {
		try {
			return format.parse(data);
		} catch (ParseException e) {
			return null;
		}
	}
}
