package com.yunwei.common.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 功能说明: 日志打印前过滤敏感信息<br>
 * 系统版本: v1.0<br>
 * 开发人员: @author yejg<br>
 * 开发时间: 2015年8月31日<br>
 */
public class DebugHelper {

	@SuppressWarnings({"unchecked", "rawtypes"})
	public static Map filterField(final Map map) {
		if (map == null) {
			return null;
		}
		Map filteredMap = new HashMap();
		filteredMap.putAll(map);

		// 通用查询菜单过滤resultList 和 listToPage
		if (filteredMap.get("resultList") != null) {
			filteredMap.put("resultList", "resultList");
		}

		if (filteredMap.get("listToPage") != null) {
			filteredMap.put("listToPage", "listToPage");
		}

		// 如果map中有字符串数组，需要自己拼接成字符串
		java.util.Set keySet = filteredMap.keySet();
		for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
			Object key = (Object)iterator.next();
			Object value = filteredMap.get(key);
			if (value != null && value.getClass().isArray()) {
				Object[] array = (Object[])value;
				// 逗号后面加个空格，和list打印的格式效果保持一致
				filteredMap.put(key, "[" + StringUtils.join(array, ", ") + "]");
			}
			if((key.toString()).indexOf("image_data")>-1){
				
				filteredMap.put(key, "图片信息");
			}
		} 
		
		

		// 密码
		if (filteredMap.get("password") != null) {
			filteredMap.put("password", replacePassword(String.valueOf(filteredMap.get("password"))));
		}
		if (filteredMap.get("password_old") != null) {
			filteredMap.put("password_old", replacePassword(String.valueOf(filteredMap.get("password_old"))));
		}
		if (filteredMap.get("password_t") != null) {
			filteredMap.put("password_t", replacePassword(String.valueOf(filteredMap.get("password_t"))));
		}
		if (filteredMap.get("bk_password") != null) {
			filteredMap.put("bk_password", replacePassword(String.valueOf(filteredMap.get("bk_password"))));
		}
		if (filteredMap.get("newPasswd") != null) {
			filteredMap.put("newPasswd", replacePassword(String.valueOf(filteredMap.get("newPasswd"))));
		}
		if (filteredMap.get("oldPasswd") != null) {
			filteredMap.put("oldPasswd", replacePassword(String.valueOf(filteredMap.get("oldPasswd"))));
		}
		if (filteredMap.get("bank_password") != null) {
			filteredMap.put("bank_password", replacePassword(String.valueOf(filteredMap.get("bank_password"))));
		}
		if (filteredMap.get("capital_password") != null) {
			filteredMap.put("capital_password", replacePassword(String.valueOf(filteredMap.get("capital_password"))));
		}
		if (filteredMap.get("transaction_password") != null) {
			filteredMap.put("transaction_password", replacePassword(String.valueOf(filteredMap.get("transaction_password"))));
		}
		if (filteredMap.get("connect_password") != null) {
			filteredMap.put("connect_password", replacePassword(String.valueOf(filteredMap.get("connect_password"))));
		}
		if (filteredMap.get("op_password") != null) {
			filteredMap.put("op_password", replacePassword(String.valueOf(filteredMap.get("op_password"))));
		}
		if (filteredMap.get("userpass") != null) {
			filteredMap.put("userpass", replacePassword(String.valueOf(filteredMap.get("userpass"))));
		}

		// 身份证
		if (filteredMap.get("id_no") != null) {
			filteredMap.put("id_no", encryptIdNo(String.valueOf(filteredMap.get("id_no"))));
		}
		if (filteredMap.get("identify_code") != null) {
			filteredMap.put("identify_code", encryptIdNo(String.valueOf(filteredMap.get("identify_code"))));
		}

		// 手机号
		if (filteredMap.get("mobile") != null) {
			filteredMap.put("mobile", encryptMobileTel(String.valueOf(filteredMap.get("mobile"))));
		}
		if (filteredMap.get("mobile_tel") != null) {
			filteredMap.put("mobile_tel", encryptMobileTel(String.valueOf(filteredMap.get("mobile_tel"))));
		}
		if (filteredMap.get("broker_mobile") != null) {
			filteredMap.put("broker_mobile", encryptMobileTel(String.valueOf(filteredMap.get("broker_mobile"))));
		}

		// 银行卡号
		if (filteredMap.get("bank_account") != null) {
			// bank_account可能传一个ArrayList，直接使用String强转会报错
			Object obj = filteredMap.get("bank_account");
			String encryptedBankNo = "";
			if (obj instanceof List) {
				List tmpList = (List)obj;
				for (int i = 0; i < tmpList.size(); i++) {
					encryptedBankNo += encryptBankAccount(String.valueOf(tmpList.get(i))) + ",";
				}
				if (encryptedBankNo.endsWith(",")) {
					encryptedBankNo = encryptedBankNo.substring(0, encryptedBankNo.length() - 1);
				}
				encryptedBankNo = "[" + encryptedBankNo + "]";
			} else {
				encryptedBankNo = encryptBankAccount(String.valueOf(obj));
			}
			filteredMap.put("bank_account", encryptedBankNo);
		}

		// 地址
		if (filteredMap.get("address") != null) {
			filteredMap.put("address", encryptAddress(String.valueOf(filteredMap.get("address"))));
		}
		if (filteredMap.get("id_address") != null) {
			filteredMap.put("id_address", encryptAddress(String.valueOf(filteredMap.get("id_address"))));
		}
		// 图片数据 image_data
		if (filteredMap.get("image_data") != null) {
			filteredMap.put("image_data", "image_data");
		}
		if (filteredMap.get("image") != null) {
			filteredMap.put("image", "image");
		}
		if (filteredMap.get("picture_data") != null) {
			filteredMap.put("picture_data", "picture_data");
		}
		if (filteredMap.get("file_base64") != null) {
			filteredMap.put("file_base64", "file_base64");
		}
		// 视频base64
		if (filteredMap.get("video_data") != null) {
			filteredMap.put("video_data", "video_data");
		}

		// 试题内容
		if (filteredMap.get("question_content") != null) {
			filteredMap.put("question_content", "question_content");
		}
		// 协议内容
		if (filteredMap.get("econtract_content") != null) {
			filteredMap.put("econtract_content", "econtract_content");
		}

		return filteredMap;
	}

	/**
	 * 将密码替换成6个星号
	 * @author yejg
	 * @param password
	 * @return
	 */
	public static String replacePassword(String password) {
		if (password == null || password.length() == 0) {
			return password;
		}
		return "******";
	}

	/**
	 * 加密身份证号
	 * @param id
	 * @return
	 */
	public static String encryptIdNo(String id_no) {
		return replace(id_no, '*', 6, 2);
	}

	/**
	 * 加密手机号码
	 * @param mobile_tel
	 * @return
	 */
	public static String encryptMobileTel(String mobile_tel) {
		return replace(mobile_tel, '*', 3, 4);
	}

	/**
	 * 加密地址
	 * @param address
	 * @return
	 */
	public static String encryptAddress(String address) {
		return replace(address, '*', 3, 4);
	}

	/**
	 * 加密姓名
	 * @param client_name
	 * @return
	 */
	public static String encryptClientName(String client_name) {
		return replace(client_name, '*', 0, 1);
	}

	/**
	 * 加密银行账号
	 * @param bank_account
	 * @return
	 */
	public static String encryptBankAccount(String bank_account) {
		return replace(bank_account, '*', 4, 4);
	}

	/**
	 * 敏感字符串"*"号处理
	 * @param string 待处理的字符串
	 * @param repChar 被替换的字符
	 * @param totalPrefix 前缀保留原始字符数量
	 * @param totalSuffix 后缀保留原始字符数量
	 * @return 处理结果字符串
	 */
	public static String replace(String string, char repChar, int totalPrefix, int totalSuffix) {
		if (string == null || string.length() == 0) {
			return string;
		}
		if (totalPrefix >= string.length() || totalSuffix >= string.length()) {
			return string;
		} else {
			char[] chr = new char[string.length()];
			for (int i = 0; i < string.length(); i++) {
				chr[i] = repChar;
			}
			if (totalPrefix != 0 && totalPrefix <= string.length()) {
				for (int i = 0; i < totalPrefix; i++) {
					chr[i] = string.charAt(i);
				}
			}
			if (totalSuffix != 0 && totalSuffix <= string.length()) {
				for (int i = string.length(); (string.length() - i) < totalSuffix; i--) {
					chr[i - 1] = string.charAt(i - 1);
				}
			}
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < chr.length; i++) {
				sb.append(chr[i]);
			}
			return sb.toString();
		}
	}

}
