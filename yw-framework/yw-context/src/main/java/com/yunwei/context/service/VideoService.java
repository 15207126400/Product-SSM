package com.yunwei.context.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

/**
 * 视频文件上传接口
* @ClassName: VideoService 
* @Description: TODO(TODO) 
* @author 晏飞
* @date 2019年8月9日 下午4:35:59 
*
 */
public interface VideoService {
	
	public Map uploadVideo(MultipartFile uploadFile);
}
