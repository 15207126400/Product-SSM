package com.yunwei.common.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import com.yunwei.common.exception.BizException;

public class FtpUtil {

	private static Logger logger = Logger.getLogger(FtpUtil.class);

	/*
	 * private static final String FTP_HOST = "111.230.177.61"; //获取ip地址 private
	 * static final int FTP_PORT = 21; //端口号 private static final String
	 * FTP_USERNAME = "yanfei"; //用户名 private static final String FTP_PASSWORD =
	 * "123456"; //密码 private static final String FTP_BASEPATH = "\\"; //基本路径
	 * private static final String IMAGE_BASE_URL = "http://111.230.177.61/ftp";
	 * //下载地址地基础url
	 */

	private static final String FTP_HOST = "39.100.96.124"; // 获取ip地址
	private static final int FTP_PORT = 21; // 端口号
	private static final String FTP_USERNAME = "yanfei"; // 用户名
	private static final String FTP_PASSWORD = "135555"; // 密码
	private static final String FTP_BASEPATH = "\\"; // 基本路径

	/**
	 * Description: 向FTP服务器上传图片文件
	 * 
	 * @param host FTP服务器hostname
	 * @param port FTP服务器端口号
	 * @param username FTP登录账号
	 * @param password FTP登录密码
	 * @param basePath FTP服务器基本目录
	 * @param filePath FTP服务器文件存放路径,例如分日期存放目录/2017/12/12。文件的路径为basePath+filePath
	 * @param filename 上传到FTP服务器上的文件名
	 * @param input 文件输入流
	 * @return 成功返回true，否则返回false
	 */
	public static boolean uploadFile(String fileName, InputStream input) {
		String filePath ="";		//临时
		boolean result = false;
		FTPClient ftp = new FTPClient();
		ftp.setControlEncoding("UTF-8");
		try {
			int reply;
			ftp.connect(FTP_HOST, FTP_PORT);// 连接FTP服务器
			// 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
			ftp.login(FTP_USERNAME, FTP_PASSWORD);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return result;
			}
			// 切换到上传目录
			if (!ftp.changeWorkingDirectory(FTP_BASEPATH + filePath)) {
				if(StringUtils.isNotBlank(filePath)){
					// 如果目录不存在创建新目录
					String[] dirs = filePath.split("/");
					String tempPath = FTP_BASEPATH;
					for (String dir : dirs) {
						if (null == dir || "".equals(dir))
							continue;
						tempPath += "/" + dir;
						if (!ftp.changeWorkingDirectory(tempPath)) {
							if (!ftp.makeDirectory(new String(tempPath
									.getBytes("UTF-8"), "iso-8859-1"))) {
								return result;
							} else {
								ftp.changeWorkingDirectory(tempPath);
							}
						}
					}
				}
			}
				
			// 设置上传文件的类型为二进制类型
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			ftp.setBufferSize(1024 * 1024); // 增大缓冲区
			ftp.setDataTimeout(60000); 		// 设置传输超时时间为60秒
			ftp.setConnectTimeout(60000); 	// 连接超时为60秒
			// 上传文件
			ftp.enterLocalPassiveMode();
			if (!ftp.storeFile(fileName, input)) {
				return result;
			}
			input.close();
			ftp.logout();
			result = true;
			String ftpFilePath = "ftp:/" + filePath + fileName;
			logger.info("ftp服务器图片访问路径：[" + ftpFilePath + "]");
		} catch (IOException e) {
			logger.info("文件上传失败：[" + e.getMessage() + "]");
			throw new BizException(e);
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return result;
	}

	/**
	 * 采用二进制流传输, 设置缓冲区, 上传速度快
	*
	* Description: 向FTP服务器上传视频文件
	* 
	* @param host FTP服务器hostname
	* @param port FTP服务器端口号
	* @param username FTP登录账号
	* @param password FTP登录密码
	* @param basePath FTP服务器基本目录
	* @param filePath FTP服务器文件存放路径,例如分日期存放目录/2017/12/12。文件的路径为basePath+filePath
	* @param filename 上传到FTP服务器上的文件名
	* @param input  因为是网络流, 将输入流加上BufferedInputStream, 提升上传速度
	* boolean 成功返回true，否则返回false
	 */
	public static boolean uploadVideoByBuffered(String filePath, String fileName, BufferedInputStream input) {
		boolean result = false;
		FTPClient ftp = new FTPClient();
		ftp.setControlEncoding("UTF-8");
		try {
			int reply;
			ftp.connect(FTP_HOST, FTP_PORT);// 连接FTP服务器
			// 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
			ftp.login(FTP_USERNAME, FTP_PASSWORD);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return result;
			}
			// 切换到上传目录
			if (!ftp.changeWorkingDirectory(FTP_BASEPATH + filePath)) {
				// 如果目录不存在创建新目录
				String[] dirs = filePath.split("/");
				String tempPath = FTP_BASEPATH;
				for (String dir : dirs) {
					if (null == dir || "".equals(dir))
						continue;
					tempPath += "/" + dir;
					if (!ftp.changeWorkingDirectory(tempPath)) {
						if (!ftp.makeDirectory(new String(tempPath
								.getBytes("UTF-8"), "iso-8859-1"))) {
							return result;
						} else {
							ftp.changeWorkingDirectory(tempPath);
						}
					}
				}
			}
			// 设置上传文件的类型为二进制类型
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			ftp.setBufferSize(1024 * 1024); // 增大缓冲区
			ftp.setDataTimeout(600000); 	// 设置传输超时时间为600秒
			ftp.setConnectTimeout(600000); 	// 连接超时为600秒
			// 上传文件
			ftp.enterLocalPassiveMode();
			if (!ftp.storeFile(fileName, input)) {
				return result;
			}
			input.close();
			ftp.logout();
			result = true;
			String ftpFilePath = "ftp:/" + filePath + fileName;
			logger.info("ftp服务器视频访问路径：[" + ftpFilePath + "]");
		} catch (IOException e) {
			logger.info("文件上传失败：[" + e.getMessage() + "]");
			throw new BizException(e);
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return result;
	}

	/**
	 * Description: 从FTP服务器下载文件
	 * 
	 * @param host
	 *            FTP服务器hostname
	 * @param port
	 *            FTP服务器端器
	 * @param username
	 *            FTP登录账号
	 * @param password
	 *            FTP登录密码
	 * @param remotePath
	 *            FTP服务器上的相对路径
	 * @param fileName
	 *            要下载的文件名称
	 * @param localPath
	 *            下载后保存到本地的路路径
	 * @return
	 */
	public static boolean downloadFile(String host, int port, String username,
			String password, String remotePath, String fileName,
			String localPath) {
		boolean result = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(host, port);
			// 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return result;
			}
			ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
			FTPFile[] fs = ftp.listFiles();
			for (FTPFile ff : fs) {
				if (ff.getName().equals(fileName)) {
					File localFile = new File(localPath + "/" + ff.getName());

					OutputStream is = new FileOutputStream(localFile);
					ftp.retrieveFile(ff.getName(), is);
					is.close();
				}
			}

			ftp.logout();
			result = true;
		} catch (IOException e) {
			logger.info("文件下载失败：[" + e.getMessage() + "]");
			throw new BizException(e);
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return result;
	}

	public static void main(String[] args) {
		try {
			FileInputStream in = new FileInputStream(new File(
					"E:\\upload\\yongyou_t3_001.jpg"));
			// boolean flag = uploadFile("111.230.177.61", 21, "yanfei",
			// "123456", "\\","/2017/12/12", "eason.jpg", in);
			// System.out.println(flag);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
