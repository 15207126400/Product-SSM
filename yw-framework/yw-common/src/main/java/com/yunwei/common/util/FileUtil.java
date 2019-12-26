package com.yunwei.common.util;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

/**
 * 功能说明: File处理工具类<br>
 * 系统版本: v1.0<br>
 * 开发人员: @author chengwen<br>
 * 开发时间: 2015年8月25日<br>
 */
public class FileUtil extends FileUtils {

	static Logger logger = LoggerFactory.getLogger(FileUtil.class);

	public static final String GIF_IMAGE_EXT_NAME = ".gif";
	private static int fileNo = 0;

	/**
	 * 用正在复制的文件替换目标文件夹中的同名文件
	 */
	public static final short COPYING_REPLACE_DEST = 1;
	/**
	 * 停止复制，保留目标文件夹中的文件
	 */
	public static final short COPYING_CANCEL = 2;
	/**
	 * 重命名正在复制的文件，然后保存到目标文件夹
	 */
	public static final short COPYING_RENAME_SRC = 3;
	/**
	 * 重命名目标文件，然后保存到目标文件夹
	 */
	public static final short COPYING_RENAME_DEST = 4;

	/**
	 * 获取配置文件路径
	 * @param configKey 配置的字段名
	 * @param defaultConfigValue 配置的字段的默认值
	 * @return
	 */
	public static String getConfigPath(String configKey, String defaultConfigValue) {
		String path = "";
		if (!path.endsWith("/") && !path.endsWith("\\")) {
			path = path + "/";
		}
		return path;
	}

	/**
	 * 获取FILE_SYS_HOME_DIR
	 * 取文件系统根目录全路径
	 * @return
	 */
	public static String getFileSysHomeDir() {
		return getConfigPath("homedir.uploader.cdn", "/");
	}

	/**
	 * 取临时目录全路径
	 * @return
	 */
	public static String getTempPath() {
		String tempPath = getFileSysHomeDir() + getConfigPath("tmpdir.uploader.cdn", "temp/");
		File tempFile = new File(tempPath);
		if (!tempFile.exists()) {
			tempFile.mkdirs();
		}
		return tempPath;
	}

	/**
	 * 获取文件大小（单位：bytes）
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static long getFileSize(File file) {
		return file.length();
	}

	/**
	 * 取允许上传的最大图片大小（单位：bytes，默认5000000）
	 * @return
	 */
	public static long getImageLimitSize() {
		return 5000000;	// 5M
	}

	/**
	 * 取允许上传的最大GIF图片大小（单位：bytes，默认5000000）
	 * @return
	 */
	public static long getGifImgLimitSize() {
		return 5000000;	// 5M
	}

	/**
	 * 获取文件扩展名，如果没有扩展名，则返回.un
	 * @param fileName
	 * @return 如： .jpg
	 */
	public static String getExtention(String fileName) {
		if (fileName == null) {
			logger.error("文件名为空");
			return null;
		}
		String str = ".un";
		int pos = fileName.lastIndexOf(".");
		if (pos > -1) {
			str = fileName.substring(pos);
		}
		return str;
	}
	
	/**
	 * 获取文件扩展名，如果没有扩展名，则返回un
	 * @param fileName
	 * @return 如： jpg
	 */
	public static String getFileExt(String fileName) {
		if (fileName == null) {
			logger.error("文件名为空");
			return null;
		}
		String str = fileName;
		int pos = fileName.lastIndexOf(".");
		if (pos > -1) {
			str = fileName.substring(pos + 1);
		}
		return str;
	}


	/**
	 * 生成目标目录,相对目录
	 * @return 可用的全路径
	 *         eg: D:\filesys\201104211\
	 */
	public static String generateDstDir(String type) {
		// 是否分类存储文件
		int FILE_SYS_HOME_DIR_CLASSIFICATION = 0;
		int _fileNo = fileNo;
		File dstFile = null;
		int dstDirNo = 0;
		String todayStamp = DateUtil.format(new Date(), DateUtil.DATE_FORMAT_NO_DELIMITER);
		String year = todayStamp.substring(0, 4);
		String mm = todayStamp.substring(4, 6).trim();
		String dd = todayStamp.substring(6, 8).trim();
		int[] currDest = new int[2];
		if (currDest[0] == parseInt(todayStamp)) {
			dstDirNo = currDest[1];
		}
		String dstPathStr = null;
		while (true) {
			if (FILE_SYS_HOME_DIR_CLASSIFICATION == 1) { // 文件分类存储
				if ("video".equalsIgnoreCase(type)) {
					dstPathStr = getFileSysHomeDir() + getConfigPath("homedir.video.uploader.cdn", "/");
				} else if ("doc".equalsIgnoreCase(type)) {
					dstPathStr = getFileSysHomeDir() + "doc";
				} else {
					dstPathStr = getFileSysHomeDir() + getConfigPath("homedir.image.uploader.cdn", "/");
				}
				dstPathStr += File.separator + year + mm + dd;
			} else {
				dstPathStr = getFileSysHomeDir() + year + File.separator + mm + File.separator + dd + dstDirNo;
				if (StringUtils.isNotEmpty(type)) {
					dstPathStr = getFileSysHomeDir() + type + File.separator + year + File.separator + mm + File.separator + dd + dstDirNo;
				}
			}
			dstFile = new File(dstPathStr);
			if (dstFile.exists()) {		// 目录已存在
				if (dstFile.list().length < getFolderSize()) {// 目录已存在，文件数少于FOLDER_SIZE;
					break;
				} else {					// 目录已存在，文件数多于FOLDER_SIZE
					// _fileNo = 0;
					dstDirNo++;
					currDest[1] = dstDirNo;
					continue;
				}
			} else {					// 目录不存在
				_fileNo = 0;
				break;
			}
		}
		fileNo = _fileNo;
		currDest[0] = parseInt(todayStamp);
		return dstPathStr + File.separator;
	}

	public static String generateDstDir() {
		return generateDstDir("");
	}

	/**
	 * 解析整型数字
	 * @param value
	 * @return
	 */
	public static int parseInt(String value) {
		int intValue = 0;
		try {
			intValue = Integer.parseInt(value);
		} catch (Exception e) {
			logger.error("数字类型转换异常", e);
		}
		return intValue;
	}

	/**
	 * 取目录容量
	 * @return
	 */
	public static long getFolderSize() {
		return 10000;		// 10000
	}

	/**
	 * 复制文件
	 * @param src 源文件
	 * @param path 目标目录
	 * @param fileName 目标文件名
	 * @param whileExist 目标目录下同名时的策略
	 * @return 目标目录下可用的文件名，不含父路径
	 * @throws IOException
	 * @throws Exception
	 */
	public static String copy(File src, String path, String fileName, short whileExist) throws IOException {
		if (src == null) {
			logger.error("移动文件时，未找到源文件", new FileNotFoundException());
			return null;
		}
		String _fileName = null;
		File parent = new File(path);
		if (!parent.exists()) {
			logger.info("creating path [" + path + "]");
			parent.mkdirs();
		}
		_fileName = fileName;
		File dst = new File(path, fileName);
		if (!dst.exists()) {
			logger.info("creating file [" + fileName + "]");
			dst.createNewFile();
		} else {		// 目标文件夹中存在同名文件
			switch (whileExist) {
				case COPYING_REPLACE_DEST:		// 复制、替换
					break;
				case COPYING_CANCEL:			// 取消复制
					return _fileName;
				case COPYING_RENAME_SRC:			// 重命名正在复制的文件
				default:
					String extName = ".un";
					try {
						extName = getExtention(_fileName);
					} catch (Exception e) {
						e.printStackTrace();
					}
					_fileName = generateUseableFileName(path, getFileNameSequence() + extName);
					dst = new File(path, _fileName);
					break;
				case COPYING_RENAME_DEST:			// 重命名目标文件
					renameFile(path, fileName);
					break;
			}
		}
		FileUtils.copyFile(src, dst);
		return _fileName;
	}

	public static String getFileNameSequence() {
		String CURR_APP_NO = "u1";
		String stamp = CURR_APP_NO + new Date().getTime() + (fileNo++);
		return stamp;
	}

	/**
	 * 生成指定目录下可用的文件名
	 * @param parentPath 所在目录路径
	 * @param defName 预置文件名(必须包含扩展名)，若parentPath目录下不存在同名文件，则采用该文件名，否则自动生成一个
	 * @return 可用的文件名(不含目录路径)
	 */
	public static String generateUseableFileName(String parentPath, String defName) {
		if (!isAvailablePath(parentPath)) {
			return null;
		}
		File prentFile = new File(parentPath);
		if (!prentFile.exists()) {
			prentFile.mkdirs();
		}
		String name = null, extName = null;
		int index = 0;
		if (defName != null && (index = defName.indexOf('.')) > -1) {
			name = defName.substring(0, index);
			extName = defName.substring(index);
		}
		if (name == null || "".equals(name)) {
			name = getFileNameSequence();
		}
		String newName = name + extName;
		File destFile = new File(parentPath, newName);
		while (destFile.exists()) {
			name = getFileNameSequence();
			newName = name + extName;
			destFile = new File(parentPath, newName);
		}
		return newName;
	}

	/**
	 * 检查给定的路径是否可用（在 FILE_SYS_ROOT_NAME 中）
	 * @param path
	 * @return
	 */
	public static boolean isAvailablePath(String path) {
		if (path != null && !"".equals(path) && path.indexOf(getFileSysHomeDir()) >= 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 将给定文件名重命名为另外可用的文件名，并返回新文件名
	 * @param fileName 原文件全名
	 * @return 新文件名，不含目录
	 */
	public static String renameFile(String parentPath, String fileName) {
		String newName = fileName;
		File theFile = new File(parentPath, fileName);
		if (theFile.exists()) {
			File destFile = null;
			String preName = null;
			try {
				preName = getPrefixName(fileName);
			} catch (Exception e1) {
				logger.error("取文件名异常", e1);
			}
			String extName = null;
			try {
				extName = getExtention(fileName);
			} catch (Exception e) {
				extName = ".un";
				logger.error("取文件扩展名异常", e);
			}
			short no = 0;
			String STRING_SEPARATOR = "_";
			do {
				no++;
				newName = preName + STRING_SEPARATOR + no + extName;
				destFile = new File(parentPath, newName);
			} while (destFile.exists());
			theFile.renameTo(destFile);
		}
		return newName;
	}

	/**
	 * 获取除文件扩展名以外的文件名
	 * @param fileName
	 * @return
	 */
	public static String getPrefixName(String fileName) {
		if (fileName == null) {
			logger.error("文件名为空", new NullPointerException());
			return null;
		}
		int pos = fileName.lastIndexOf(".");
		if (pos > -1) {
			return fileName.substring(0, pos);
		}
		return fileName;
	}

	public final static Map<String, String> FILE_TYPE_MAP = new HashMap<String, String>();
	static {
		// 初始化文件类型信息
		FILE_TYPE_MAP.put("jpg", "FFD8FF"); // JPEG (jpg)
		FILE_TYPE_MAP.put("png", "89504E47");  // PNG (png)
		FILE_TYPE_MAP.put("gif", "47494638");  // GIF (gif)
		FILE_TYPE_MAP.put("tif", "49492A00");  // TIFF (tif)
		FILE_TYPE_MAP.put("bmp", "424D"); // Windows Bitmap (bmp)
		FILE_TYPE_MAP.put("dwg", "41433130"); // CAD (dwg)
		FILE_TYPE_MAP.put("html", "68746D6C3E");  // HTML (html)
		FILE_TYPE_MAP.put("rtf", "7B5C727466");  // Rich Text Format (rtf)
		FILE_TYPE_MAP.put("xml", "3C3F786D6C");
		FILE_TYPE_MAP.put("zip", "504B0304");
		FILE_TYPE_MAP.put("rar", "52617221");
		FILE_TYPE_MAP.put("psd", "38425053");  // Photoshop (psd)
		FILE_TYPE_MAP.put("eml", "44656C69766572792D646174653A");  // Email [thorough only] (eml)
		FILE_TYPE_MAP.put("dbx", "CFAD12FEC5FD746F");  // Outlook Express (dbx)
		FILE_TYPE_MAP.put("pst", "2142444E");  // Outlook (pst)
		FILE_TYPE_MAP.put("xls", "D0CF11E0");  // MS Word
		FILE_TYPE_MAP.put("doc", "D0CF11E0");  // MS Excel 注意：word 和 excel的文件头一样
		FILE_TYPE_MAP.put("mdb", "5374616E64617264204A");  // MS Access (mdb)
		FILE_TYPE_MAP.put("wpd", "FF575043"); // WordPerfect (wpd)
		FILE_TYPE_MAP.put("eps", "252150532D41646F6265");
		FILE_TYPE_MAP.put("ps", "252150532D41646F6265");
		FILE_TYPE_MAP.put("pdf", "255044462D312E");  // Adobe Acrobat (pdf)
		FILE_TYPE_MAP.put("qdf", "AC9EBD8F");  // Quicken (qdf)
		FILE_TYPE_MAP.put("pwl", "E3828596");  // Windows Password (pwl)
		FILE_TYPE_MAP.put("wav", "57415645");  // Wave (wav)
		FILE_TYPE_MAP.put("avi", "41564920");
		FILE_TYPE_MAP.put("ram", "2E7261FD");  // Real Audio (ram)
		FILE_TYPE_MAP.put("rm", "2E524D46");  // Real Media (rm)
		FILE_TYPE_MAP.put("mpg", "000001BA");  //
		FILE_TYPE_MAP.put("mov", "6D6F6F76");  // Quicktime (mov)
		FILE_TYPE_MAP.put("asf", "3026B2758E66CF11"); // Windows Media (asf)
		FILE_TYPE_MAP.put("mid", "4D546864");  // MIDI (mid)
	}

	/**
	 * 判断file的类型
	 * @see FileUtil#FILE_TYPE_MAP
	 * @param file
	 * @return 返回文件类型的扩展名(不含".")
	 */
	public static String getFileType(File file) {
		if (file == null || !file.exists()) {
			return null;
		}
		byte[] b = new byte[50];
		try {
			InputStream is = new FileInputStream(file);
			is.read(b);
			is.close();
		} catch (IOException e) {
			logger.error("读取文件出错", e);
			return null;
		}
		return getFileType(b);
	}

	/**
	 * 根据数组判断文件类型
	 * @param byteArray
	 * @return 返回文件类型的扩展名(不含".")
	 */
	public static String getFileType(byte[] byteArray) {
		if (byteArray == null || byteArray.length == 0) {
			return null;
		}
		String hexString = bytesToHexString(byteArray);
		Iterator<Entry<String, String>> entryiterator = FILE_TYPE_MAP.entrySet().iterator();
		while (entryiterator.hasNext()) {
			Entry<String, String> entry = entryiterator.next();
			String fileTypeHexValue = entry.getValue();
			if (hexString.toUpperCase().startsWith(fileTypeHexValue)) {
				return entry.getKey();
			}
		}
		return "unknown";
	}

	/**
	 * 获取图片文件实际类型,若不是图片则返回null
	 * <p>
	 * 根据ImageIO获取文件高宽来判断文件是否为图片,再获取图片真实类型
	 * </p>
	 * @param File
	 * @return fileType
	 */
	public final static String getImageFileType(File file) {
		if (isImage(file)) {
			try {
				ImageInputStream iis = ImageIO.createImageInputStream(file);
				Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
				if (!iter.hasNext()) {
					return null;
				}
				ImageReader reader = iter.next();
				iis.close();
				return reader.getFormatName();
			} catch (IOException e) {
				logger.error("使用ImageIO出现异常", e);
				return null;
			}
		}
		return null;
	}

	/**
	 * 根据ImageIO获取文件高宽来判断文件是否为图片
	 * @param file
	 * @return true 是 | false 否
	 */
	public static final boolean isImage(File file) {
		boolean flag = false;
		try {
			BufferedImage bufreader = ImageIO.read(file);
			int width = bufreader.getWidth();
			int height = bufreader.getHeight();
			if (width == 0 || height == 0) {
				flag = false;
			} else {
				flag = true;
			}
		} catch (IOException e) {
			flag = false;
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 将byte数组转成16进制字符串
	 * @param src byte数组
	 * @return 16进制字符串
	 */
	public final static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}


	
	
	/**
	 * 格式化文件大小
	 * @author yejg
	 * @param fileSize
	 * @param format 格式，例如“0.0”表示保留一位小数
	 * @return
	 */
	public static String formatFileSize(long fileSize, String format) {
		float GB = 1024 * 1024 * 1024;// 定义GB的计算常量
		float MB = 1024 * 1024;// 定义MB的计算常量
		float KB = 1024;// 定义KB的计算常量

		DecimalFormat df = new DecimalFormat(format);

		if (fileSize / GB >= 1) {
			return df.format(fileSize / GB) + "GB";
		} else if (fileSize / MB >= 1) {
			return df.format(fileSize / MB) + "MB";
		} else if (fileSize / KB >= 1) {
			return df.format(fileSize / KB) + "KB";
		} else {
			return df.format(fileSize) + "Byte";
		}
	}
	
	/**
     * 按指定高度 等比例缩放图片
     * 
     * @param imageFile
     * @param newPath
     * @param newWidth 新图的宽度
     * @throws IOException
     */
    public static void thumbnailScale(MultipartFile imageFile, String newPath, int newWidth) throws IOException {
         if(imageFile.isEmpty())
             return;
        BufferedImage bufferedImage = ImageIO.read(imageFile.getInputStream());
        if (null == bufferedImage) 
            return;
        
        int originalWidth = bufferedImage.getWidth();
        int originalHeight = bufferedImage.getHeight();
        double scale = (double)originalWidth / (double)newWidth;    // 缩放的比例
        
        int newHeight =  (int)(originalHeight / scale);

        zoomImageUtils(imageFile, newPath, bufferedImage, newWidth, newHeight);
    }
    
    /**
     * 按尺寸缩放图片
     * 
     * @param imageFile
     * @param newPath
     * @param times
     * @throws IOException
     */
    public static void thumbnail(MultipartFile imageFile, String newPath, int width, int height) throws IOException {
        if (imageFile != null && imageFile.isEmpty())
            return;
        BufferedImage bufferedImage = ImageIO.read(imageFile.getInputStream());
        if (null == bufferedImage) 
            return;

        zoomImageUtils(imageFile, newPath, bufferedImage, width, height);
    }
	
	private static void zoomImageUtils(MultipartFile imageFile, String newPath, BufferedImage bufferedImage, int width, int height)
            throws IOException{
        
         String suffix = StringUtils.substringAfterLast(imageFile.getOriginalFilename(), ".");
        
         // 处理 png 背景变黑的问题
        if(suffix != null && (suffix.trim().toLowerCase().endsWith("png") || suffix.trim().toLowerCase().endsWith("gif"))){
            BufferedImage to= new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); 
            Graphics2D g2d = to.createGraphics(); 
            to = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT); 
            g2d.dispose(); 
            
            g2d = to.createGraphics(); 
            Image from = bufferedImage.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING); 
            g2d.drawImage(from, 0, 0, null);
            g2d.dispose(); 
            
            ImageIO.write(to, suffix, new File(newPath));
        }else{
            // 高质量压缩，其实对清晰度而言没有太多的帮助
//            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//            tag.getGraphics().drawImage(bufferedImage, 0, 0, width, height, null);
//
//            FileOutputStream out = new FileOutputStream(newPath);    // 将图片写入 newPath
//            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//            JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
//            jep.setQuality(1f, true);    //压缩质量, 1 是最高值
//            encoder.encode(tag, jep);
//            out.close();
            
            BufferedImage newImage = new BufferedImage(width, height, bufferedImage.getType());
            Graphics g = newImage.getGraphics();
            g.drawImage(bufferedImage, 0, 0, width, height, null);
            g.dispose();
            ImageIO.write(newImage, suffix, new File(newPath));
        }
    }
}
