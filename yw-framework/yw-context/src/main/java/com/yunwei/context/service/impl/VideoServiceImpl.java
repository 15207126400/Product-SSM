package com.yunwei.context.service.impl;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yunwei.common.exception.BizException;
import com.yunwei.common.util.FtpUtil;
import com.yunwei.common.util.IDUtil;
import com.yunwei.context.service.VideoService;

/**
 * 视频上传实现类
* @ClassName: VideoServiceImpl 
* @Description: TODO(TODO) 
* @author 晏飞
* @date 2019年8月9日 下午4:36:42 
*
 */
@Service
public class VideoServiceImpl implements VideoService {
	private static Logger logger = Logger.getLogger(PictureServiceImpl.class);
	
    /**
	 * 原图片链接前缀
	 */
	private static final String original_prefix = "https://www.qhzhlkj.com/files/image/qhz/upload";

	@Override
	public Map uploadVideo(MultipartFile uploadFile) {
		Map map = new HashMap(); 
        try {
        	// 获取新文件名称
        	String newFileName = getNewFileName(uploadFile);
            //上传的ftp文件路径前缀  
            String imagePath = getFtpFilePathPrefix();
            
            //因为是网络流, 将输入流加上BufferedInputStream, 提升上传速度
        	BufferedInputStream input = new BufferedInputStream(new ByteArrayInputStream(uploadFile.getBytes()));
        	
            //调用方法，上传文件  
            boolean result = FtpUtil.uploadVideoByBuffered(imagePath,newFileName, input); 
            
            //判断是否上传成功  
            if (result) {
            	String newFilePath = original_prefix + imagePath + newFileName;
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
	
}
