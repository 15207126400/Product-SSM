package com.yunwei.common.utils.encrypt;

import java.math.BigInteger;

/**
 * Kettle加密
 */
public class KettleEncrypt {

	private static final int RADIX = 16;
	private static final String SEED = "0933910847463829827159347601486730416058";
	public  static final String KETTLE_PASSWORD_ENCRYPTED_PREFIX = "Encrypted ";
	
	public static final String encryptPassword(String password){  
        if (password==null) return "";  
        if (password.length()==0) return "";  
          
        BigInteger bi_passwd = new BigInteger(password.getBytes());  
          
        BigInteger bi_r0  = new BigInteger(SEED);  
        BigInteger bi_r1  = bi_r0.xor(bi_passwd);  
          
        return KETTLE_PASSWORD_ENCRYPTED_PREFIX + bi_r1.toString(RADIX);  
    }  
  
    public static final String decryptPassword(String encrypted){  
        if (encrypted==null) return "";  
        if (encrypted.length()==0) return "";  
          
        BigInteger bi_confuse  = new BigInteger(SEED);  
          
        try{  
            BigInteger bi_r1 = new BigInteger(encrypted, RADIX);  
            BigInteger bi_r0 = bi_r1.xor(bi_confuse);  
              
            return new String(bi_r0.toByteArray());   
        }catch(Exception e){  
            return "";  
        }  
    }  
    
}