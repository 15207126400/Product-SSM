package com.yunwei.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

/**
 * file md5加密
 * 功能说明: <br>
 * 系统版本: v1.0<br>
 * 开发人员: @author hufeng<br>
 * 开发时间: Dec 21, 2015<br>
 */
public class MD5Util {

	/**
	 * 默认的密码字符串组合，apache校验下载的文件的正确性用的就是默认的这个组合
	 */
	protected static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	protected static MessageDigest messagedigest = null;
	static {
		try {
			messagedigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException nsaex) {
			System.err.println(MD5Util.class.getName() + "初始化失败，MessageDigest不支持MD5Util。");
			nsaex.printStackTrace();
		}
	}


	/**
	 * 适用于上G大的文件
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String getFileMD5String(File file) throws IOException {
		FileInputStream in = null ;
		String strMD5 = " ";
		try {
			in = new FileInputStream(file);
			strMD5 = DigestUtils.md5Hex(IOUtils.toByteArray(in));  
		    IOUtils.closeQuietly(in);  
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(in!=null){
				in.close();
			}
		}
		return strMD5;
	}

	public static String getMD5String(String s) {
		return getMD5String(s.getBytes());
	}

	public static String getMD5String(byte[] bytes) {
		messagedigest.update(bytes);
		return bufferToHex(messagedigest.digest());
	}

	private static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			appendHexPair(bytes[l], stringbuffer);
		}
		return stringbuffer.toString();
	}

	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char c0 = hexDigits[(bt & 0xf0) >> 4];
		char c1 = hexDigits[bt & 0xf];
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}

	public static boolean checkPassword(String password, String md5PwdStr) {
		String s = getMD5String(password);
		return s.equals(md5PwdStr);
	}
	
	/**
	 * 只显示头部和尾部部分信息 , 中间内容以**号隐藏
	*
	*@param value	传入的字符串
	*@param begin	头部保留位数
	*@param end		尾部保留位数
	*@return
	*String
	 */
	public static String characterEncryption(String value , String begin , String end){
		String str = "";
		// {}里面的数字可以用来指定前后缀的长度
		String regex = "(\\w{3})(.*)(\\w{4})";
		String str1 = regex.replaceAll("\\{3\\}","{"+begin+"}");
		String str2 = str1.replaceAll("\\{4\\}","{"+end+"}");
 
		Matcher m = Pattern.compile(str2).matcher(value);
		if (m.find()) {
			String rep = m.group(2);
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < rep.length(); i++) {
				sb.append("*");
			}
 
			str = value.replaceAll(rep, sb.toString());
		}
		return str;
	}
	
}