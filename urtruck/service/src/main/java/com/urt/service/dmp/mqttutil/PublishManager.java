package com.urt.service.dmp.mqttutil;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


public class PublishManager {
	
	Logger log = Logger.getLogger(PublishManager.class);
	//发起连接用到的会话
	public String topic;
	 
	public String clientId = "1";
    private MqttConnectOptions options;
    private ScheduledExecutorService scheduler;
    private MqttClient client;
    private boolean responseArrived = false;
    private boolean publishSuccess = false;
    private volatile boolean finished = false;
    private String myResponseTopic;
    private String response;
    
	/**
     * 初始化和终端连接时的会话和发布消息的会话，初始化mqtt的客户端和参数
     * 初始化mqtt客户端对象client和配置对象options
     * 创建连接断开时的回调函数
     * 创建发布消息的回调函数
     * 发布订阅消息的回调函数
     */
	public void initMqtt(String imei){
		log.info("初始化mqtt与终端的会话开始，终端IMEI是------："+imei);
//		topic="room1/DeviceCommand_"+imei;//生产环境
		topic="DeviceCommand_"+imei;//测试环境
		myResponseTopic = topic+"_1";
		log.info("初始化mqtt与终端的会话结束，终端IMEI是------："+imei+"------创建连接的会话topic是-----："+topic+"------创建发布消息的会话myResponseTopic是------："+myResponseTopic);
		Date date = new Date();
		clientId = ""+date.getTime();
		log.info("初始化的终端idclientId------："+clientId);	
		
		
	    log.info("初始化mqtt客户端对象client和配置对象options开始");
		try {
            //host为主机名，test为clientid即连接MQTT的客户端ID，一般以客户端唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
            client = new MqttClient(MqttConfig.HOST, clientId,
                    new MemoryPersistence());
           
            //MQTT的连接设置
            options = new MqttConnectOptions();
            //设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
            options.setCleanSession(false);
            //设置连接的用户名	
            options.setUserName(MqttConfig.USERNAME);
            //设置连接的密码
            options.setPassword(MqttConfig.PASSWORD.toCharArray());
            //设置超时时间 单位为秒
            options.setConnectionTimeout(MqttConfig.MQTT_CONNECT_TIMEOUT);
            // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
            options.setKeepAliveInterval(20);
            //设置回调
            log.info("设置mqtt客户端client连接丢失后的回调函数");
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                	log.info("mqtt客户端client连接丢失，重连开始");
                    //连接丢失后，进行重连
                	try {
                		if(!client.isConnected())
                		{
                			if(!finished){
                				client.connect();
                			}
                		}
					} catch (MqttSecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (MqttException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                	log.info("mqtt客户端client连接丢失，重连成功");
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    //发布后会执行到这里
                	log.info("mqtt发布消息后，记录发布结果");
                	publishSuccess = true;
                	System.out.println("publish complete");
                	log.info("mqtt消息发布完成");
                }

				@Override
				public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
					responseArrived = true;
					response = arg1.toString();
					log.info("mqtt接收到终端设备的响应，相应结果为response-----："+response);
				}
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
		log.info("初始化mqtt客户端对象client和配置对象options结束");
	}
	
	public void startConnect() {
		if(!client.isConnected()) {
            connect();
        }
    }

    private void connect() {
    	System.out.println(Thread.currentThread().getId());
    	try {
    		log.info("mqtt和终端设备简历连接开始");
			client.connect(options);
			log.info("mqtt和终端设备简历连接成功");
	        
		} catch (MqttSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void publish(String content)
    {
    	try{
            //发布信息  参数说明（主题，内容，qos，是否保留）
    		log.info("向mqtt服务器发布消息开始");
            client.publish(this.topic,content.getBytes("utf-8"),2,false);
            log.info("向mqtt服务器发布消息完成，发布内容content----："+content);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //订阅指定客户端的消息，用于接收该终端设备的响应
    public void startListenResponse(){
    	log.info("向mqtt服务器订阅指定终端设备的消息，用于接收该终端设备的响应");
    	try {
    		if(client.isConnected()){  //一个终端的会话
			    client.subscribe(this.myResponseTopic, 2);
			    log.info("订阅成功，订阅消息的会话myResponseTopic----："+myResponseTopic);
    		}else{
    			log.info("与 mqtt断开连接，消息订阅失败");
    		}
		} catch (MqttException e) {
			System.out.println(""+e.getLocalizedMessage());
			e.printStackTrace();
		}
    	log.info("创建定时器，设定订阅成功后，延迟3秒，三秒后订阅状态改为true");
    	final Timer timer = new Timer();
    	TimerTask task = new TimerTask(){
    	    int timeCount = 0;
    		public void run(){
    			timeCount++;
    			System.out.println(timeCount+",threadId"+Thread.currentThread().getId());
    			if(timeCount == 10)
    			{
    				finished = true;
    				timer.cancel();
    				return;
    			}
    			if(responseArrived)
    			{
    				finished = true;
    				timer.cancel();
    			}
    		}
    	};
    	timer.schedule(task, 0,1000);
    	
    	while(!finished)
    	{
//    		log.info("未收到指定终端的响应");
    	}
    	log.info("向mqtt服务器订阅指定终端设备的消息成功");
    	try {
			client.disconnect();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    //处理回复结果
    public boolean handleResult()
    {
    	System.out.println("handleResult()");
    	//命令发送成功
    	if(this.publishSuccess){
    		//接收到回复，操作失败或者操作成功
    		if(this.responseArrived){
    			//操作成功
    			if(response.contains("ok")){
    				log.info("命令发送成功");
    				return true;
    			}else if(response.contains("fail")){
    				log.info("命令发送失败,收到未知的返回结果");
    				return false;
    			}else{
    				return false;
    			}
    		}else{
    			log.info("未收到回复，操作结果未知");
    			return false;
    		}
    	}else{//命令发送失败
    		log.info("命令发送失败");
    		return false;
    	}
    }
}
