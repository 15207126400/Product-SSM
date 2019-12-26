package com.yunwei.common.util; 

import com.jcraft.jsch.*;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * 解释一下SFTP的整个调用过程，这个过程就是通过Ip、Port、Username、Password获取一个Session,
 * 然后通过Session打开SFTP通道（获得SFTP Channel对象）,再在建立通道（Channel）连接，最后我们就是
 * 通过这个Channel对象来调用SFTP的各种操作方法.总是要记得，我们操作完SFTP需要手动断开Channel连接与Session连接。
 * @author jiashubing
 * @since 2018/5/8
 */
public class SFtpUtil {
	
    private ChannelSftp channel;
    private Session session;

	/*
	 使用端口号、用户名、密码以连接SFTP服务器
	 */
    public SFtpUtil() {
    	 this.connectServer("39.106.89.216", 22, "root", "Qinoupp815");
    }
    
    public SFtpUtil(String ftpHost, int ftpPort, String ftpUserName, String ftpPassword) {
    	  this.connectServer(ftpHost, ftpPort, ftpUserName, ftpPassword);
    }


    public void connectServer(String ftpHost, int ftpPort, String ftpUserName, String ftpPassword) {
        try {
            // 创建JSch对象
            JSch jsch = new JSch();
            // 根据用户名，主机ip，端口获取一个Session对象
            session = jsch.getSession(ftpUserName, ftpHost, ftpPort);
            if (ftpPassword != null) {
                // 设置密码
                session.setPassword(ftpPassword);
            }
            Properties configTemp = new Properties();
            configTemp.put("StrictHostKeyChecking", "no");
            // 为Session对象设置properties
            session.setConfig(configTemp);
            // 设置timeout时间
            session.setTimeout(60000);
            session.connect();
            // 通过Session建立链接
            // 打开SFTP通道
            channel = (ChannelSftp) session.openChannel("sftp");
            // 建立SFTP通道的连接
            channel.connect();
            
        } catch (JSchException e) {
            //throw new RuntimeException(e);
        }
    }

    /**
     * 断开SFTP Channel、Session连接
     */
    public void closeChannel() {
        try {
            if (channel != null) {
                channel.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        } catch (Exception e) {
            //
        }
    }

    /**
     * 上传文件	(网络流传输)
     *
     * @param localFile  本地文件
     * @param remoteFile 远程文件
     */
    public void upload(BufferedInputStream input, String remoteFile) {
        try {
        	channel.cd("/home/ftp/files/");
            channel.put(input, remoteFile);
            channel.quit();
        } catch (SftpException e) {
            //e.printStackTrace();
        }
    }
    
    /**
     * 上传文件	(普通流传输)
     *
     * @param localFile  本地文件
     * @param remoteFile 远程文件
     */
    public void upload(InputStream input, String remoteFile) {
        try {
        	channel.cd("/home/ftp/files/");
            channel.put(input, remoteFile);
            channel.quit();
        } catch (SftpException e) {
            //e.printStackTrace();
        }
    }

    /**
     * 下载文件
     *
     * @param remoteFile 远程文件
     * @param localFile  本地文件
     */
    public void download(String remoteFile, String localFile) {
        try {
            channel.get(remoteFile, localFile);
            channel.quit();
        } catch (SftpException e) {
            //e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SFtpUtil sftp = new SFtpUtil();
        //上传测试
        String localfile = "E:/lalala/tt.xlsx";
        String remotefile = "/home/ftp/tt2.xlsx";
        //sftp.upload(localfile, remotefile);

        //下载测试
        sftp.download(remotefile, "E:/lalala/tt3.xlsx");

        sftp.closeChannel();
    }

}