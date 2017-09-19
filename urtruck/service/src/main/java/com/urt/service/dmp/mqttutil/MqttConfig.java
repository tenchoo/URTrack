package com.urt.service.dmp.mqttutil;
public class MqttConfig {
	//mqtt代理服务器的配置
//	public static String HOST = "tcp://223.202.25.86:1883";//生产环境，MQTT代理服务器IP地址与端口号
	public static String HOST = "tcp://223.203.201.240:1883";//测试环境，MQTT代理服务器IP地址与端口号
//    public static String HOST_IP = "223.202.25.86";//生产环境，MQTT代理服务器IP地址
    public static String HOST_IP = "223.203.201.240";//测试环境，MQTT代理服务器IP地址ַ
    public static String HOST_PORT = "1883";//MQTT代理服务器服务端口号
    public static String USERNAME = "admin";//MQTT代理服务器服务用户名，增加连接的安全性，mqtt自设机制
    public static String PASSWORD = "password";//MQTT代理服务器服务密码，增加连接的安全性，mqtt自设机制	
    public static int MQTT_CONNECT_TIMEOUT = 10;//单位为秒
    public static int MQTT_CONNECT_CYCLE_TIME = 10*1000;//单位为毫秒，此时间表示客户端每隔一段时间判断MqttClient是否连接，若没有来连接，发起连接
    
    
    //命令
	//命令语句的前缀
    public static String COMMAND_PREFIX = "device_command%%";
    //锁屏命令
    public static String LOCK_SCREEN = "lock_screen";
    //禁用摄像头
    public static String DISABLE_CAMERA =  "disable_camera";
    //启用摄像头
    public static String ENABLE_CAMERA =  "enable_camera";
    //关闭Wifi
    public static String CLOSE_WIFI = "close_wifi";
    //打开wifi	
    public static String ENABLE_WIFI = "enable_wifi";
    //改变终端探测地理位置的周期 device_command%%refresh_pos_period%%10
    public static String REFRESH_POS_PERIOD = "refresh_pos_period%%";//回复 ：refresh_pos_period_res_ok
    //是否在线
    public static String IS_ONLINE = "is_online";//回复is_online_ok
    //定位命令
    public static String LOCATION = "location";

    
    
}
