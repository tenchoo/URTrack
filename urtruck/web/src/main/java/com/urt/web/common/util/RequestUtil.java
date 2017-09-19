package com.urt.web.common.util;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {
	
	/****
	 * 获取并判断 request 中指定键的值。
	 * 当获取的值为 null 、"" 、长度为 0 、去除两边的空格后长度为 0，就返回设定的默认值
	 * @param q request 对象
	 * @param key 键
	 * @param default_val 设定的默认值
	 * @return
	 */
	public static String getParam(HttpServletRequest q,String key,String default_val){
		String val = q.getParameter(key);
		if(val != null && !val.equals("") && val.length() > 0 && val.trim().length() > 0){
			return getURLDecoder(val);
		}
		else{
			return default_val;
		}
	}
	/**
	 * 获取汉字字符串结果
	 * @param str
	 * @return
	 */
	public static String getURLDecoder(String str){
		if(str != null){
			try {
				str = URLDecoder.decode(str, "utf-8");
			} catch (Exception e) {
				//e.printStackTrace();
			}
		}
		return str;
	}
		 
	/****
	 * 获取并判断 request 中指定键的值。
	 * 当获取的值为 null 、"" 、长度为 0 时，或者不是整数时就返回设定的默认值
	 * @param q request 对象
	 * @param key 键
	 * @param default_val 默认值整数
	 * @return
	 */
	public static Integer getParam(HttpServletRequest q,String key,int default_val){
		String val = q.getParameter(key);
		Integer num=default_val;
		if(val != null && !val.equals("") && val.trim().length() > 0){
			try{
				num=Integer.valueOf(val.trim());
			}catch(Exception ex){
				
			}
		}
		return num;
	}
	
	/****
	 * 获取并判断 request 中指定键的值。
	 * 当获取的值为 null 、"" 、长度为 0 时，或者不是整数时就返回设定的默认值
	 * @param q request 对象
	 * @param key 键
	 * @param default_val 默认值 Double
	 * @return
	 */
	public static Double getParam(HttpServletRequest q,String key,double default_val){
		String val = q.getParameter(key);
		Double num = default_val;
		if(val != null && !val.equals("") && val.trim().length() > 0){
			try{
				num = Double.valueOf(val);
			}catch(Exception ex){
				
			}
		}
		return num;
	}
	
	/**zhangzf 20110603
	 * 获取ip地址
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) { 
		String ip = request.getHeader("X-Real-IP"); 
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	        ip = request.getHeader("x-forwarded-for"); 
	    } 
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	        ip = request.getHeader("Proxy-Client-IP"); 
	    } 
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	        ip = request.getHeader("WL-Proxy-Client-IP"); 
	    } 
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	        ip = request.getRemoteAddr(); 
	    } 
	    return ip; 
	}

	public static boolean getParam(HttpServletRequest q, String key,
			boolean b) {
		String val = q.getParameter(key);
		Boolean result = b;
		if(val != null && !val.equals("") && val.trim().length() > 0){
			try{
				result = Boolean.valueOf(val);
			}catch(Exception ex){
				
			}
		}
		return result;
	}

	public static Float getParam(HttpServletRequest q, String key,
			float f) {
		String val = q.getParameter(key);
		float result = f;
		if(val != null && !val.equals("") && val.trim().length() > 0){
			try{
				result = Float.valueOf(val);
			}catch(Exception ex){
				
			}
		}
		return result;
	}
}
