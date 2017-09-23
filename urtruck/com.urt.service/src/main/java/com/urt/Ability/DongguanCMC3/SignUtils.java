package com.urt.Ability.DongguanCMC3;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SignUtils {

    /**
     * 使用<code>secret</code>对paramValues按以下算法进行签名： <br/>
     * uppercase(hex(sha1(secretkey1value1key2value2...secret))
     *
     * @param paramNames  需要签名的参数名
     * @param paramValues 参数列表
     * @param secret
     * @return
     */
	
	public String transID=null;
	
    public static String sign(Map<String, String> paramValues, String secret) {
        return sign(paramValues,null,secret);
    }

    /**
     * 对paramValues进行签名，其中ignoreParamNames这些参数不参与签名
     * @param paramValues
     * @param ignoreParamNames
     * @param secret
     * @return
     */
    public static String sign(Map<String, String> paramValues, List<String> ignoreParamNames,String secret) {
        try {
            StringBuilder sb = new StringBuilder();
            List<String> paramNames = new ArrayList<String>(paramValues.size());
            paramNames.addAll(paramValues.keySet());
            if(ignoreParamNames != null && ignoreParamNames.size() > 0){
                for (String ignoreParamName : ignoreParamNames) {
                    paramNames.remove(ignoreParamName);
                }
            }
            Collections.sort(paramNames);

            sb.append(secret);
            for (String paramName : paramNames) {
                sb.append(paramName).append(paramValues.get(paramName));
            }
            sb.append(secret);
            System.out.println(sb.toString());
            byte[] sha1Digest = getSHA1Digest(sb.toString());
            return byte2hex(sha1Digest);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }    

    public static String utf8Encoding(String value, String sourceCharsetName) {
        try {
            return new String(value.getBytes(sourceCharsetName), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static byte[] getSHA1Digest(String data) throws IOException {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            bytes = md.digest(data.getBytes("UTF-8"));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse);
        }
        return bytes;
    }

    /**
     * 二进制转十六进制字符串
     *
     * @param bytes
     * @return
     */
    private static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }
    public static Map<String,String> genComMap(){
    	ConstantsUtils3.dataString();
    	Map<String,String> comMap=new HashMap<String,String>();
    	comMap.put(ConstantsUtils3.appKey, ConstantsUtils3.appKeyVal);
    	comMap.put(ConstantsUtils3.format, ConstantsUtils3.formatVal);
    	comMap.put(ConstantsUtils3.locale, ConstantsUtils3.localeVal);
    	comMap.put(ConstantsUtils3.v, ConstantsUtils3.vVal);
    	return comMap;
    }
    public static String genComUtr(){
    	return  "?"+ConstantsUtils3.appKey+"="+ConstantsUtils3.appKeyVal+"&"
    			+ConstantsUtils3.format+"="+ConstantsUtils3.formatVal+"&"
    			+ConstantsUtils3.locale+"="+ConstantsUtils3.localeVal+"&"
    			+ConstantsUtils3.v+"="+ConstantsUtils3.vVal;
    	
    }
    
}
