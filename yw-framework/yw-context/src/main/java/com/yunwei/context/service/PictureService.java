package com.yunwei.context.service;


import java.io.InputStream;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface PictureService {
	
	Map build_QRCode(InputStream inputStream);
	
	/**
	 * 原图上传(linux)		内部文件重命名并转换流
	 * @param uploadFile	文件
	 * @return
	 */
	public String uploadOriginalImageBySftp(MultipartFile uploadFile);
	
	/**
	 * 原图上传(linux)		内部重命名,外部接收流
	 * @param uploadFile	文件
	 * @return
	 */
	public String uploadOriginalImageBySftp(MultipartFile uploadFile, InputStream inputStream);
	
	/**
	 * 原图上传(window)
	 * @param uploadFile	(FTP)
	 * @return
	 */
	public String uploadOriginalImage(MultipartFile uploadFile);
	
	/**
	 * 小程序缩略图上传
	 * @param uploadFile
	 * @return
	 */
	public String uploadXcxThumbnail(MultipartFile uploadFile);
	
	/**
	 * 小程序缩略图上传
	 * @param uploadFile
	 * @param width
	 * @param height
	 * @return
	 */
	public String uploadXcxThumbnail(MultipartFile uploadFile,int width,int height);
	
	/**
	 * 后台缩略图上传
	 * @param uploadFile
	 * @return
	 */
	public String uploadBackThumbnail(MultipartFile uploadFile);
	
	/**
	 * 后台缩略图上传
	 * @param uploadFile
	 * @param width
	 * @param height
	 * @return
	 */
	public String uploadBackThumbnail(MultipartFile uploadFile,int width,int height);
	
	

}
