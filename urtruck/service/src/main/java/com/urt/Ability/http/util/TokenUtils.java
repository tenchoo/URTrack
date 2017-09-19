package com.urt.Ability.http.util;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
public class TokenUtils {
	protected static final Logger logger = Logger.getLogger(TokenUtils.class);
	 /**
     * 对paramValues进行签名，其中ignoreParamNames这些参数不参与签名
     * @param paramValues
     * @param ignoreParamNames
     * @param secret
     * @return
     */
    public static String md5Sign(Map<String, String> paramValues, List<String> ignoreParamNames,String secret) {
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
		logger.info("scort:***************"+sb.toString());
		return degistByMD5(sb.toString());
    }
	
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
}
