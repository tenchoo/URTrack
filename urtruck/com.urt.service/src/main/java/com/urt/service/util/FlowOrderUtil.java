package com.urt.service.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.urt.Ability.unicom.util.HttpClientUtil;
import com.urt.modules.utils.PropertiesLoader;

public class FlowOrderUtil {
	/**
	 * MD5加密
	 * @param str
	 * @return
	 */
	public static String degistByMD5(String str){
		StringBuffer buf = null;
		try{
		  MessageDigest md = MessageDigest.getInstance("MD5");  
          md.update(str.getBytes());  
          byte b[] = md.digest();  
          int i;  
          buf = new StringBuffer("");  
          for (int offset = 0; offset < b.length; offset++) {  
              i = b[offset];  
              if (i < 0)  
                  i += 256;  
              if (i < 16)  
                  buf.append("0");  
              buf.append(Integer.toHexString(i));  
          }  
         
      } catch (NoSuchAlgorithmException e) {  
      	 e.printStackTrace();
      }
		 return buf.toString();  
	}
	/**
	 * AES+base64加密
	 * @param sSrc 需要加密的字符串
	 * @param sKey 密钥
	 * @param sIV  初始向量
	 * @return
	 */
	@SuppressWarnings("restriction")
	public static String degistByAES(String sSrc,String sKey,String sIV){
		  if (sKey == null) {
	            System.out.print("Key为空null");
	            return null;
	        }
	        // 判断Key是否为16位
	        if (sKey.length() != 16) {
	            System.out.print("Key长度不是16位");
	            return null;
	        }
	        byte[] raw = sKey.getBytes();
	        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
	        byte[] encrypted = null;
			try {
				Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");//"算法/模式/补码方式"
				IvParameterSpec iv = new IvParameterSpec(sIV.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
				cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
				encrypted = cipher.doFinal(sSrc.getBytes());
			} catch (Exception e) {
				e.printStackTrace();
			} 
	        return new sun.misc.BASE64Encoder().encode(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
	}
   /**
    * 加密电话 base64+AES
    * @param Moble
    * @param pwd 接口密码
    * @return
    */
	public static String degistMobile(String moble,String pwd){
		String dpwd = degistByMD5(pwd);
		int dpwdlength=dpwd.length();
		String s=dpwd.substring(0, 16);//密钥
		String e=dpwd.substring(dpwdlength-16,dpwdlength);//向量
		String mobileStr=degistByAES(moble,s,e);
		return mobileStr;
	}
	public static void main(String[] args){
		PropertiesLoader loader=new PropertiesLoader("classpath:/application.properties");
		String testStr=loader.getProperty("jdbc.driver");
		System.out.println(testStr);
	}
}