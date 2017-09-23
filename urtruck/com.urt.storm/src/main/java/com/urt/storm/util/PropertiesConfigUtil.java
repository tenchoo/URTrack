package com.urt.storm.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties; 


/**
 * 功能描述：读取配置文件
 * @author lipj
 * @date 2016年6月23日
 */
public class PropertiesConfigUtil {
    
    /**
     * 功能描述：读取配置文件工具类
     * @author lipj
     * @date 2016年6月23日 上午11:32:03
     * @param @param propertiesName 配置文件名称
     * @param @return 
     * @return Properties
     */
    public static Properties getProperties(String propertiesName){
    	//  验证文件名称
    	if (null != propertiesName && propertiesName.endsWith(".properties")) {
        	//  初始化配置文件变量
        	Properties prop = new Properties();     
            try{
                //读取属性文件*.properties
                InputStream in = new BufferedInputStream (new FileInputStream(PropertiesConfigUtil.class.getClassLoader().getResource(propertiesName).getPath()));
                prop.load(in); 
                in.close();
                return prop;
            }
            catch(Exception e){
            	return null;
            }
        	
		}else{
			return null;
		}
    	
    }
    
    
    public static void main(String[] args){
    	System.out.println(getProperties("kafkaStorm.properties"));
    }
    
}