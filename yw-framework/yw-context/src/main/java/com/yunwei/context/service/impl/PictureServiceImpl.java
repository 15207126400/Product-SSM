package com.yunwei.context.service.impl;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.impl.common.IOUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.util.IOUtils;
import com.yunwei.common.exception.BizException;
import com.yunwei.common.util.FtpUtil;
import com.yunwei.common.util.IDUtil;
import com.yunwei.common.util.SFtpUtil;
import com.yunwei.context.service.PictureService;
@Service
public class PictureServiceImpl implements PictureService {  
	  
	private static Logger logger = Logger.getLogger(PictureServiceImpl.class);
	
    /**
	 * 原图片链接前缀
	 */
	private static final String original_prefix = "https://www.ivan.group/files/";
	
	/**
	 * 小程序缩略图链接前缀
	 */
	//private static final String xcxthumbnail_prefix = "https://xcx.whywxx.com/yongyou/image/home/xcx";
	
	/**
	 * 后台缩略图链接前缀
	 */
	//private static final String backthumbnail_prefix = "https://xcx.whywxx.com/yongyou/image/home/back";
	
	/**
	 * 压缩图输出质量(默认输出最大质量)
	 */
	private static final float thumbnail_default_outputQuality = 1.0f; 
	

	@Override
	public String uploadOriginalImageBySftp(MultipartFile uploadFile) {
		SFtpUtil sftp = new SFtpUtil();
		String newFilePath = "";
		try {
			// 获取新文件名称
	    	String newFileName = getNewFileName(uploadFile);
	    	//因为是网络流, 将输入流加上BufferedInputStream, 提升上传速度
	    	BufferedInputStream input = new BufferedInputStream(new ByteArrayInputStream(uploadFile.getBytes()));
	        //上传的ftp文件路径前缀  
	        //String imagePath = getFtpFilePathPrefix();
	        //新的路径(访问)
	        newFilePath = original_prefix + newFileName;
	        sftp.upload(input, newFileName);
	        sftp.closeChannel();
		} catch (Exception e) {
			logger.info(e);
			throw new BizException(e);
		}
        
		return newFilePath;
	}
	
	@Override
	public String uploadOriginalImageBySftp(MultipartFile uploadFile,InputStream inputStream) {
		SFtpUtil sftp = new SFtpUtil();
		String newFilePath = "";
		try {
			// 获取新文件名称
	    	String newFileName = getNewFileName(uploadFile);
	    	
	        //新的路径(访问)
	        newFilePath = original_prefix + newFileName;
	        sftp.upload(inputStream, newFileName);
	        sftp.closeChannel();
		} catch (Exception e) {
			logger.info(e);
			throw new BizException(e);
		}
        
		return newFilePath;
	}
    
    /**
     * 微信小程序 二维码生成
     */
    @Override  
    public Map build_QRCode(InputStream inputStream) {  
        Map map = new HashMap(); 
        try {  
            String newFileName = IDUtil.genImageName();  
            String postfix = ".png";
            //新名字  
            newFileName = newFileName + postfix;
            
            //上传的ftp文件路径前缀  
            //String imagePath = getFtpFilePathPrefix();
            
            //调用方法，上传文件  
            boolean result = FtpUtil.uploadFile(newFileName,inputStream);  
            //判断是否上传成功  
            if (result) {
            	String newFilePath = original_prefix + newFileName;
            	logger.info("生成的访问文件路径：["+ newFilePath +"]");
            	map.put("url", newFilePath); 
            } else {
            	throw new BizException("ftp文件上传失败,请重新上传");
            }   
  
        } catch (Exception e) { 
        	throw new BizException(e);
        }  
        
        return map;
    }
    
    /**
     * 图片压缩
     * @param uploadFile
     * @return
     */
    private InputStream imageThumbnails(MultipartFile uploadFile,int width,int height){
    	ByteArrayOutputStream outputStream = null;
		try {
			// 将文件压缩后存到输出流
			outputStream = new ByteArrayOutputStream();
			// 高度和宽度为零，等比例压缩，图片质量减半
			if(width == 0 && height == 0){
				Thumbnails.of(uploadFile.getInputStream()).scale(1f).outputQuality(0.5f).toOutputStream(outputStream);
			} else {
				Thumbnails.of(uploadFile.getInputStream()).size(width, height).outputQuality(thumbnail_default_outputQuality).toOutputStream(outputStream);
			}
		} catch (IOException e) {
			throw new BizException("图片压缩失败：["+ e.getMessage() +"]");
		} finally {
			IOUtils.close(outputStream);
		}
		// 将文件输出流转换成输入流
		return new ByteArrayInputStream(outputStream.toByteArray());
    }

    /**
     * 生成新文件名称
     * @param uploadFile
     * @return
     */
    private String getNewFileName(MultipartFile uploadFile){
        // 获取旧的名字  
    	String oldName = "";
    	if(StringUtils.isNotEmpty(uploadFile.getOriginalFilename())){
    		oldName = uploadFile.getOriginalFilename();
    	}else{
    		oldName = uploadFile.getName();
    	}
        
    	// 生成随机名称前缀
        String namePrefix = IDUtil.genImageName();  
        // 生成文件名称
        String newFileName = namePrefix + oldName.substring(oldName.lastIndexOf("."));
        
        return newFileName;
    }
    
    /**
     * 生成ft文件路径前缀
     * @return
     */
    private String getFtpFilePathPrefix(){
    	SimpleDateFormat df = new SimpleDateFormat("/yyyy/MM/dd/");
        String imagePath = df.format(new Date());
        return imagePath;
    }
    
	@Override
	public String uploadOriginalImage(MultipartFile uploadFile) {
		
		return this.uploadOriginalImageBySftp(uploadFile);
	}
	
	@Override
	public String uploadXcxThumbnail(MultipartFile uploadFile) {
		
		return uploadXcxThumbnail(uploadFile, 0,0);
	}

	@Override
	public String uploadXcxThumbnail(MultipartFile uploadFile, int width,int height) {
        // 图片压缩，
		InputStream inputStream = this.imageThumbnails(uploadFile,width,height);
		
		// 压缩图片上传
		return uploadOriginalImageBySftp(uploadFile,inputStream);
	}

	@Override
	public String uploadBackThumbnail(MultipartFile uploadFile) {
		
		return uploadBackThumbnail(uploadFile, 0,0);
	}

	@Override
	public String uploadBackThumbnail(MultipartFile uploadFile, int width,int height) {
		// 图片压缩，
		InputStream inputStream = this.imageThumbnails(uploadFile,width,height);
		
		// 压缩图片上传
		return uploadOriginalImageBySftp(uploadFile,inputStream);
	}

    
}  
