package com.urt.Ability.unicom.util;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class ThreedesUtils {
	
	

	    public static byte[] encrypt3DES(byte[] datasource, String password) { 
	    	try{
	    		String algorithm = "DESede";
	    		byte[] bytePassword = password.getBytes();
	    		//生成密钥
	    		byte[] tripleDESKey = new byte[24];
	    		int k = 0;
	    		int i = 0;
	    		//初始化Key
	    		for(i = 0; i < 24; i++){
	    			if(k >= bytePassword.length){
	    				tripleDESKey[i] = 0;
	    			}
	    			else
	    				tripleDESKey[i] = bytePassword[k];
	    			k++;
	    		}
	            SecretKey deskey = new SecretKeySpec(tripleDESKey,algorithm);
	   
	            //加密  
	            Cipher c1 = Cipher.getInstance("DESede/ECB/PKCS5Padding");  
	            c1.init(Cipher.ENCRYPT_MODE, deskey);  
	            return c1.doFinal(datasource); 
	    	}catch(Throwable e){
	    		e.printStackTrace();
	    		throw new IllegalArgumentException(e);
	    	}
	    }
	    
	    
	    /**
	     * 解密
	     * @param src byte[]
	     * @param password String
	     * @return byte[]
	     * @throws Exception
	     */
	     public static byte[] decrypt3DES(byte[] datasource, String password){
	     try{
	    
	     String algorithm = "DESede";
	     byte[] bytePassword = password.getBytes();
	     //生成密钥
	     byte[] tripleDESKey = new byte[24];
	     int k = 0;
	     int i = 0;
	     //初始化Key
	     for(i = 0; i < 24; i++){
	     if(k >= bytePassword.length){
	     tripleDESKey[i] = 0;;
	     }
	     else
	     tripleDESKey[i] = bytePassword[k];
	     k++;
	     }
	             SecretKey deskey = new SecretKeySpec(tripleDESKey,algorithm);    
	             //加密  
	             Cipher c1 = Cipher.getInstance("DESede/ECB/PKCS5Padding");  
	             c1.init(Cipher.DECRYPT_MODE, deskey);  
	             return c1.doFinal(datasource); 
	     }catch(Throwable e){
	     e.printStackTrace();
	     throw new IllegalArgumentException(e);
	     }
	     }


	    
	    
	    public static void main(String[] args) throws UnsupportedEncodingException {
	    	
	    	byte [] aa = ThreedesUtils.encrypt3DES("320982199109190418".getBytes("utf-8"), "lYxYX8mR2SdsKnCo");
	    	
	    	System.out.println(Base64.encode(aa));
	    	
		}

}
