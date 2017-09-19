package com.urt.service.dmp.mqttutil;

public class MqttPublishMain {
//	public static void main(String[] args) {
//        PublishManager mqttManager = new PublishManager();
//        
//        mqttManager.initMqtt("861811030031841");
//        mqttManager.startConnect();
//        String command = MqttConfig.COMMAND_PREFIX+MqttConfig.ENABLE_WIFI;
//        mqttManager.publish(command);
//        mqttManager.startListenResponse();
//        mqttManager.handleResult();
//	}
	
	
	public static void main(String[] args) {
		String commad=MqttConfig.COMMAND_PREFIX+MqttConfig.LOCATION;
        PublishManager mqttManager=MQTTClint.publicCommand("860988034684410", commad);
        boolean isok=mqttManager.handleResult();
        System.out.println(isok);
	}


}
