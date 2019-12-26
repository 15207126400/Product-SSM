package com.yunwei.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberUtil {
	      
    public static void main(String[] args) {  
        System.out.println(sub(100, 0.005));  
    }  
  
    public static String add(double v1, double v2) {// 加法  
        BigDecimal b1 = new BigDecimal(Double.toString(v1));  
        BigDecimal b2 = new BigDecimal(Double.toString(v2));  
        return decimalFormat(b1.add(b2).doubleValue());  
    }  
  
    public static String sub(double v1, double v2) {// 减法  
        BigDecimal b1 = new BigDecimal(Double.toString(v1));  
        BigDecimal b2 = new BigDecimal(Double.toString(v2));  
        return decimalFormat(b1.subtract(b2).doubleValue());  
    }  
  
    public static String mul(double v1, double v2) {// 乘法  
        BigDecimal b1 = new BigDecimal(Double.toString(v1));  
        BigDecimal b2 = new BigDecimal(Double.toString(v2));  
        return decimalFormat(toMathRound(b1.multiply(b2).doubleValue()));  
    }  
  
    public static String div(double v1, double v2) {// 除法  
        BigDecimal b1 = new BigDecimal(Double.toString(v1));  
        BigDecimal b2 = new BigDecimal(Double.toString(v2));  
        return decimalFormat(b1.divide(b2, 3, BigDecimal.ROUND_HALF_UP).doubleValue());  
    }  
  
    // 截取小数后N位
    public static Double round(BigDecimal bigDecimal, int i) {
    	return Double.valueOf(String.valueOf(bigDecimal.setScale(i, BigDecimal.ROUND_HALF_UP)));
    } 
    
    // 四舍五入保留小数两位
    public static double toMathRound(double d){
    	
    	return (double) Math.round(d * 100) / 100;
    }
    
    // 字符串转double并保留小数两位
    public static String round2(String v) {// 截取3位  
    	Double d= Double.parseDouble(v); 
    	DecimalFormat df = new DecimalFormat("#.00"); 
    	return df.format(d);
    }  
    
    public static String decimalFormat(String pattern, double value) {  
        return new DecimalFormat(pattern).format(value);  
    }  
  
    public static String decimalFormat(double value) {  
        return new DecimalFormat("0.00").format(value);  
    }  
  
    public static String decimalFormat(double value, String pattern) {  
        return new DecimalFormat(pattern).format(value);  
    }  
  
    public static String decimalBlankFormat(double value) {  
        return new DecimalFormat("0").format(value);  
    }  
  
    public static boolean isNumber(String value) { // 检查是否是数字  
        String patternStr = "^\\d+$";  
        Pattern p = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE); // 忽略大小写;  
        Matcher m = p.matcher(value);  
        return m.find();  
    }  
    
    // double四舍五入转int整数
    public static int getInt(double number){
        BigDecimal bd=new BigDecimal(number).setScale(0, BigDecimal.ROUND_HALF_UP);
        return Integer.parseInt(bd.toString()); 
    } 
    
}
