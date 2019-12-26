package com.yunwei.common.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;

/**
 * 功能说明: 图片处理工具类<br>
 * 系统版本: v1.0<br>
 * 开发人员: @author guanhui<br>
 * 开发时间: 2015-10-28<br>
 */
public class ImageUtil {

	private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

	/**
	 * 检查图片大小
	 * @param image
	 * @param extName
	 * @return
	 */
	public static boolean isOverSize(File image, String extName) {
		boolean overSize = false;
		try {
			long imgSize = FileUtil.getFileSize(image);
			if (".gif".equals(extName.toLowerCase()) && imgSize > FileUtil.getGifImgLimitSize()) {
				overSize = true;
			} else if (imgSize > FileUtil.getImageLimitSize()) {
				overSize = true;
			}
		} catch (Exception e) {
			overSize = false;
			logger.error("判断文件大小时异常", e);
		}
		return overSize;
	}

	/**
	 * 将多张图片合并成一张，上下排列
	 * @param src 源图片
	 * @param out 结果图片
	 * @param split 间距（像素）
	 */
	public static BufferedImage mergeImg(Image[] src, int split) {
		int height = 0;
		int width = 0;
		for (Image img : src) {
			height += img.getHeight(null) + split;
			int temp_width = img.getWidth(null);
			width = temp_width > width ? temp_width : width;
		}
		BufferedImage res = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = res.getGraphics();
		g.fillRect(0, 0, width, height);
		int h = 0;
		for (Image img : src) {
			g.drawImage(img, 0, h, img.getWidth(null), img.getHeight(null), null);
			h += img.getHeight(null) + split;
		}
		g.dispose();
		return res;
	}

	/**
	 * 字节转换为BufferedImage
	 * @param bytes
	 * @return
     */
	public static BufferedImage byteToBufferedImage(byte[] bytes){
		BufferedImage image=null;
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
		try {
			image = ImageIO.read(in);
		} catch (IOException e) {
			logger.info("byte转换image异常", e);
		}
		return image;
	}

}
