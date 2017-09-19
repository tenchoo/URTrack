package com.urt.Ability.http.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class ToolsUtil {
	public static void main(String[] args) {
		System.out.println(getId());
	}
	  public static boolean checkIp(String str)  
	    {  
	        String[] ipValue = str.split("\\.");  
	        if (ipValue.length != 4)  
	        {  
	            return false;  
	        }  
	        for (int i = 0; i < ipValue.length; i++)  
	        {  
	            String temp = ipValue[i];  
	            try  
	            {  
	                // java判断字串是否整数可以用此类型转换异常捕获方法，也可以用正则 var regu = /^\d+$/;  
	                Integer q = Integer.valueOf(ipValue[i]);  
	                if (q > 255)  
	                {  
	                    return false;  
	                }  
	            }  
	            catch (Exception e)  
	            {  
	                return false;  
	            }  
	        }  
	        return true;  
	    }
	  public static boolean checkStringIsNull(String str){
		  boolean flag = false;
		  if("".equals(str) || null == str || str.length() == 0){
			  flag = true;
		  }
		  return flag;
	  }
	  public static  String makeAppKey(int length) {  
          
	        String val = "";  
	        Random random = new Random();  
	          
	        //参数length，表示生成几位随机数  
	        for(int i = 0; i < length; i++) {  
	              
	            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";  
	            //输出字母还是数字  
	            if( "char".equalsIgnoreCase(charOrNum) ) {  
	                //输出是大写字母还是小写字母  
	                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;  
	                val += (char)(random.nextInt(26) + temp);  
	            } else if( "num".equalsIgnoreCase(charOrNum) ) {  
	                val += String.valueOf(random.nextInt(10));  
	            }  
	        }  
	        return val;  
	    } 
	  public static String getId(){
		  StringBuffer rom = new StringBuffer();
		 
		  for(int a=0;a<5;a++){
			  int x=(int)(Math.random()*10);
			  rom.append(x);
		  }
		  rom.append(new SimpleDateFormat("MMddHHmmss").format(new Date()));
		 
		  return rom.toString();
	  }
	  
	  public static String compress(String str) throws IOException {
			if (str == null || str.length() == 0) {
				return str;
			}

			BASE64Encoder base64encoder = new BASE64Encoder();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			GZIPOutputStream gzip = new GZIPOutputStream(out);
			gzip.write(str.getBytes(Constants.DEFAULT_CHARSET));
			gzip.close();
			return base64encoder.encode(out.toByteArray());
		}

		public static String uncompress(String srcStr) throws IOException {
			if (srcStr == null || srcStr.length() == 0) {
				return srcStr;
			}
			BASE64Decoder base64decoder = new BASE64Decoder();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] bytes = base64decoder.decodeBuffer(srcStr);
			ByteArrayInputStream in = new ByteArrayInputStream(bytes);
			GZIPInputStream gunzip = new GZIPInputStream(in);
			byte[] buffer = new byte[256];
			int n;
			while ((n = gunzip.read(buffer)) >= 0) {
				out.write(buffer, 0, n);
			}
			return out.toString(Constants.DEFAULT_CHARSET);
		}
}
