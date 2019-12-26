package com.yunwei.common.utils.encrypt;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 各种格式的编码加码工具类.
 * 集成Commons-Codec,Commons-Lang及JDK提供的编解码方法.
 */
public class EncodeUtils {

	private final static Logger logger = LoggerFactory.getLogger(EncodeUtils.class);

	private static final String DEFAULT_URL_ENCODING = "UTF-8";
	public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";
	/** 国政通webservice请求回报加密key */
	public static final String DES_KEY = "12345678";

	/**
	 * Hex编码.
	 */
	public static String hexEncode(byte[] input) {
		return Hex.encodeHexString(input);
	}

	/**
	 * Hex解码.
	 */
	public static byte[] hexDecode(String input) {
		try {
			return Hex.decodeHex(input.toCharArray());
		} catch (DecoderException e) {
			throw new IllegalStateException("Hex Decoder exception", e);
		}
	}

	/**
	 * Base64编码.
	 */
	public static String base64Encode(byte[] input) {
		return new String(Base64.encodeBase64(input));
	}

	/**
	 * Base64编码, URL安全(将Base64中的URL非法字符如+,/=转为其他字符, 见RFC3548).
	 */
	public static String base64UrlSafeEncode(byte[] input) {
		return Base64.encodeBase64URLSafeString(input);
	}

	/**
	 * Base64解码.
	 */
	public static byte[] base64Decode(String input) {
		return Base64.decodeBase64(input);
	}

	/**
	 * Base64解码.
	 * @param input
	 * @return
	 */
	public static String base64Decode2String(String input) {
		try {
			return new String(Base64.decodeBase64(input), DEFAULT_URL_ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("BASE64 Decoder exception", e);
		}
	}

	/**
	 * URL 编码, Encode默认为UTF-8.
	 */
	public static String urlEncode(String input) {
		try {
			return URLEncoder.encode(input, DEFAULT_URL_ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("Unsupported Encoding Exception", e);
		}
	}

	/**
	 * URL 解码, Encode默认为UTF-8.
	 */
	public static String urlDecode(String input) {
		try {
			return URLDecoder.decode(input, DEFAULT_URL_ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("Unsupported Encoding Exception", e);
		}
	}

	/**
	 * Html 转码.
	 */
	public static String htmlEscape(String html) {
		return StringEscapeUtils.escapeHtml4(html);
	}

	/**
	 * Html 解码.
	 */
	public static String htmlUnescape(String htmlEscaped) {
		return StringEscapeUtils.unescapeHtml4(htmlEscaped);
	}

	/**
	 * Xml 转码.
	 */
	public static String xmlEscape(String xml) {
		return StringEscapeUtils.escapeXml(xml);
	}

	/**
	 * Xml 解码.
	 */
	public static String xmlUnescape(String xmlEscaped) {
		return StringEscapeUtils.unescapeXml(xmlEscaped);
	}

	/**
	 * DES算法，加密
	 * @param data 待加密字符串
	 * @param key 加密私钥，长度不能够小于8位
	 * @return 加密后的字节数组，一般结合Base64编码使用
	 * @throws CryptException 异常
	 */
	public static byte[] encode(String key, byte[] data) throws Exception {
		DESKeySpec dks = new DESKeySpec(key.getBytes());

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		// key的长度不能够小于8位字节
		Key secretKey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
		IvParameterSpec iv = new IvParameterSpec(DES_KEY.getBytes());
		AlgorithmParameterSpec paramSpec = iv;
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);

		return cipher.doFinal(data);
	}

	/**
	 * DES算法，加密
	 * @param data 待加密字符串
	 * @return 加密后的字节数组，一般结合Base64编码使用
	 * @throws CryptException 异常
	 */
	public static String encode(String data) throws Exception {
		return Base64.encodeBase64String(encode(DES_KEY, data.getBytes("GB18030")));
	}

	/**
	 * 获取编码后的值
	 * @param key
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String decodeValue(String data) {
		byte[] datas;
		String value = null;
		try {
			if (System.getProperty("os.name") != null && (System.getProperty("os.name").equalsIgnoreCase("sunos") || System.getProperty("os.name").equalsIgnoreCase("linux"))) {
				datas = decode(DES_KEY, Base64.decodeBase64(data));
			} else {
				datas = decode(DES_KEY, Base64.decodeBase64(data));
			}

			value = new String(datas, "GB18030");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			value = "";
		}
		return value;
	}

	/**
	 * DES算法，解密
	 * @param data 待解密字符串
	 * @param key 解密私钥，长度不能够小于8位
	 * @return 解密后的字节数组
	 * @throws Exception 异常
	 */
	public static byte[] decode(String key, byte[] data) throws Exception {
		// SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(key.getBytes());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		// key的长度不能够小于8位字节
		Key secretKey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
		IvParameterSpec iv = new IvParameterSpec(DES_KEY.getBytes());
		AlgorithmParameterSpec paramSpec = iv;
		cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
		return cipher.doFinal(data);
	}

	// public static void main(String[] args) throws Exception {
	// System.out.println("明：abc ；密：" + encode("12345678", "abc".getBytes("GB18030")));
	// System.out.println("明：ABC ；密：" + encode("12345678", "ABC".getBytes("GB18030")));
	// System.out.println("明：中国人 ；密：" + encode("12345678", "中国人".getBytes("GB18030")));
	// System.out.println("中国人=" + decodeValue("qMeKyoDWvsE="));
	// }
}
