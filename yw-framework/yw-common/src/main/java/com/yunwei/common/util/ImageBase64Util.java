package com.yunwei.common.util;  

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 图片与BASE64编码互转工具类
 * @author wangwei
 */  
@SuppressWarnings("restriction")
public class ImageBase64Util {  

	private static Logger logger = LoggerFactory.getLogger(ImageBase64Util.class);
      
    /**
	 * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	 * @param imgFilePath 图片路径
	 * @return String
	 */  
    public static String GetImageStr(String imgFilePath) {  
        byte[] data = null;  
		// 读取图片字节数组
        try {  
            InputStream in = new FileInputStream(imgFilePath);  
            data = new byte[in.available()];  
            in.read(data);  
            in.close();
        } catch (IOException e) {  
			logger.error("图片转换失败", e);
        }  
		// 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();  
		return encoder.encode(data);// 返回Base64编码过的字节数组字符串
    }  
    
    /**
	 * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	 * @param imgFilePath 图片路径
	 * @return String
	 */  
    public static String GetImageStrByURL(String imgUrl) {  
        byte[] data = null;  
		// 读取图片字节数组
        try {
        	URL url = new URL(imgUrl);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();// 利用HttpURLConnection对象,我们可以从网络中获取网页数据.
			conn.setDoInput(true);
			conn.connect();
			InputStream in = conn.getInputStream(); // 得到网络返回的输入流
            data = new byte[in.available()];  
            in.read(data);  
            in.close();
        } catch (IOException e) {  
			logger.error("图片转换失败", e);
        }  
		// 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();  
		return encoder.encode(data);// 返回Base64编码过的字节数组字符串
    }  
    
    public static String GetImageStr(File file) {  
    	String res = "";
    	try{
    		byte[] data = null;  
			// 读取图片字节数组
            InputStream in = new FileInputStream(file);  
            data = new byte[in.available()];  
            in.read(data);  
            in.close();  
			// 对字节数组Base64编码
            BASE64Encoder encoder = new BASE64Encoder();  
			res = encoder.encode(data);// 返回Base64编码过的字节数组字符串
    	}catch(Exception e){
			logger.error("图片转换失败", e);
    	}
    	return res;
    } 
    
    /**
	 * 对字节数组字符串进行Base64解码并生成图片
	 * @param imgStr Base64字符串
	 * @param imgFilePath 生成图片保存路径
	 * @return boolean
	 */  
    public static boolean GenerateImage(String imgStr, String imgFilePath) {  
		if (imgStr == null)           // 图像数据为空
            return false;  
        BASE64Decoder decoder = new BASE64Decoder();  
        try {  
			// Base64解码
            byte[] bytes = decoder.decodeBuffer(imgStr);  
            for (int i = 0; i < bytes.length; ++i) {  
				if (bytes[i] < 0) {// 调整异常数据
                    bytes[i] += 256;  
                }  
            }  
			// 生成jpeg图片
            OutputStream out = new FileOutputStream(imgFilePath);  
            out.write(bytes);  
            out.flush();  
            out.close();  
            return true;  
        } catch (Exception e) {  
			logger.error("图片转换失败", e);
            return false;  
        }  
    } 
    
    public static String GetImageStr(byte[] data) {  
    	String res = "";
    	try{
			// 对字节数组Base64编码
            BASE64Encoder encoder = new BASE64Encoder();  
			res = encoder.encode(data);// 返回Base64编码过的字节数组字符串
    	}catch(Exception e){
			logger.error("图片转换失败", e);
    	}
    	return res;
    } 
      
    /**
	 * 对字节数组字符串进行Base64解码并生成图片
	 * @param imgStr 图片字符串
	 * @return byte[]
	 */  
    public static byte[] getStrToBytes(String imgStr) {   
		if (imgStr == null)           // 图像数据为空
            return null;  
        BASE64Decoder decoder = new BASE64Decoder();  
        try {  
			// Base64解码
            byte[] bytes = decoder.decodeBuffer(imgStr);  
            for (int i = 0; i < bytes.length; ++i) {  
				if (bytes[i] < 0) {// 调整异常数据
                    bytes[i] += 256;  
                }  
            }  
			// 生成jpeg图片
            return bytes;  
        } catch (Exception e) {  
			logger.error("图片转换失败", e);
            return null;  
        }  
    } 
}   
