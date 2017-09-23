package com.urt.service.dmp.mqttutil;

public class MQTTClint {
     public static PublishManager publicCommand(String imei,String command){
    	 PublishManager mqttManager = new PublishManager();
         
         mqttManager.initMqtt(imei);
         mqttManager.startConnect();
         mqttManager.publish(command);
         mqttManager.startListenResponse();
         return mqttManager;
     }
}
