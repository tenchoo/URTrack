package com.urt.service.dmp.baiduutil;

import net.sf.json.JSONObject;
/** 
 * 百度工具类 
 *  
 * @author 张森森 
 */  
public class BaiduUtil {
		private static String ak="yFEDv8FUDXLYM2lwQVGlLRvSu6Rti1i5";
    	/**
    	 * 根据地址信息json，获取具体地址信息
    	 * @param lat
    	 * @param lng
    	 * @return
    	 */
        public static String getAddress(String lat, String lng) {
        	JSONObject obj=getLocationInfo(lat, lng);
        	JSONObject result = obj.getJSONObject("result");
            String address =(String) result.get("formatted_address");
            return address;
        }  
        /**
         * 根据百度地图经纬度获取地址信息
         * @param lat
         * @param lng
         * @return
         */
        public static JSONObject getLocationInfo(String lat, String lng) {  
            String url = "http://api.map.baidu.com/geocoder/v2/?location=" + lat + ","  
                    + lng + "&output=json&ak="+ak+"&pois=0"; 
            String response = HttpUtil.getRequest(url);
            System.out.println("1===="+response);
            JSONObject obj = JSONObject.fromObject(response); 
            return obj;  
        }  
      
        public static void main(String[] args) {  
            System.out.println(BaiduUtil.getAddress("40.059994", "116.304849"));  
        }  
 
	
	
}
