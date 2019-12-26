package com.yunwei.common.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import javax.crypto.Cipher;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 功能说明: RSA算法工具类<br>
 * 系统版本: v1.0<br>
 * 开发人员: @author zhangjh<br>
 * 开发时间: 2018年3月8日<br>
 */
public class RSAUtil {

    private static final Log logger = LogFactory.getLog(RSAUtil.class);

    /**
     * 通过私钥解密源数据
     * @param privateKeyPath
     * @param srcBytes
     * @return
     */
    public static byte[] decryptByPrivateKey(String privateKeyPath,byte[] srcBytes){
        File privateKeyFile = new File(privateKeyPath);
        PrivateKey privateKey=readPrivateKeyByFile(privateKeyPath);
        if(privateKey!=null){
            byte[] keyBytes=new byte[(int)privateKeyFile.length()];
            try{
                IOUtils.read(new FileInputStream(privateKeyFile),keyBytes);
                //Cipher负责完成加密或解密工作，基于RSA
                Cipher cipher = Cipher.getInstance("RSA");
                //根据公钥，对Cipher对象进行初始化
                cipher.init(Cipher.DECRYPT_MODE, privateKey);
                byte[] resultBytes = cipher.doFinal(srcBytes);
                return resultBytes;
            }catch (Exception e){
                logger.error("解密数据失败",e);
                return null;
            }
        }
        return null;
    }

    /**
     * 通过公钥加密数据
     * @param privateKeyPath
     * @param srcBytes
     * @return
     */
    public static byte[] encryptByPublicKey(String privateKeyPath,byte[] srcBytes){
        File publicKeyFile = new File(privateKeyPath);
        PublicKey publicKey=readPublicKeyByFile(privateKeyPath);
        if(publicKey!=null){
            byte[] keyBytes=new byte[(int)publicKeyFile.length()];
            try{
                IOUtils.read(new FileInputStream(publicKeyFile),keyBytes);
                //Cipher负责完成加密或解密工作，基于RSA
                Cipher cipher = Cipher.getInstance("RSA");
                //根据公钥，对Cipher对象进行初始化
                cipher.init(Cipher.ENCRYPT_MODE, publicKey);
                byte[] resultBytes = cipher.doFinal(srcBytes);
                return resultBytes;
            }catch (Exception e){
                logger.error("加密数据失败",e);
                return null;
            }
        }
        return null;
    }

    /**
     * 通过私钥解密base64数据
     * @param privateKeyPath
     * @param base64
     * @return
     */
    public static String decryptByPrivateKeyToString(String privateKeyPath,String base64){
        String str=null;
        try {
            byte[] sourceByte=Base64.decodeBase64(base64.getBytes());
            byte[] decryptByte=decryptByPrivateKey(privateKeyPath,sourceByte);
            str=new String(decryptByte,"UTF-8");
        } catch (Exception e) {
            logger.error("解密数据失败",e);
        }
        return str;
    }

    /**
     * 通过证书获取公钥PublicKey
     * @param certPath
     * @return
     */
    public static PublicKey readPublicKeyByCertPath(String certPath){
        PublicKey publicKey=null;
        File file=new File(certPath);
        if(file.exists()){
            try {
                FileInputStream fis = new FileInputStream(file);
                // 创建X509工厂类
                CertificateFactory cf = CertificateFactory.getInstance("X.509");
                // 创建证书对象
                X509Certificate oCert = (X509Certificate) cf.generateCertificate(fis);
                fis.close();
                publicKey=oCert.getPublicKey();
            } catch (IOException e) {
                logger.info("读取证书异常",e);
            }catch (CertificateException e) {
                logger.info("证书处理异常",e);
            }

        }else{
            logger.info("证书路径:"+certPath+"不存在");
        }
        return publicKey;
    }

    /**
     * 通过公钥文件路径读取公钥
     * @param publicKeyPath
     * @return
     */
    public static PublicKey readPublicKeyByFile(String publicKeyPath){
        PublicKey publicKey=null;
        File publicKeyFile = new File(publicKeyPath);
        if(publicKeyFile.exists()){
            byte[] keyBytes=new byte[(int)publicKeyFile.length()];
            try {
                IOUtils.read(new FileInputStream(publicKeyPath),keyBytes);
                X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                publicKey = keyFactory.generatePublic(keySpec);
            } catch (Exception e) {
                logger.info("读取公钥失败",e);
            }
        }
        return publicKey;
    }

    /**
     * 通过私钥文件路径读取PrivateKey
     * @param privateKeyPath
     * @return
     */
    public static PrivateKey readPrivateKeyByFile(String privateKeyPath){
        PrivateKey privateKey=null;
        File privateKeyFile = new File(privateKeyPath);
        if(privateKeyFile.exists()){
            byte[] keyBytes=new byte[(int)privateKeyFile.length()];
            try {
                IOUtils.read(new FileInputStream(privateKeyFile),keyBytes);
                PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                privateKey = keyFactory.generatePrivate(keySpec);
            } catch (Exception e) {
                logger.info("读取私钥失败",e);
            }
        }
        return privateKey;
    }

}
