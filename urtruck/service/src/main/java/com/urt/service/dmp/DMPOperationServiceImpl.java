package com.urt.service.dmp;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import com.urt.interfaces.dmp.DMPOperationService;
import com.urt.service.dmp.mqttutil.MQTTClint;
import com.urt.service.dmp.mqttutil.MqttConfig;
import com.urt.service.dmp.mqttutil.PublishManager;
@Service("dmpOperationService")
@Transactional(propagation = Propagation.REQUIRED)
public class DMPOperationServiceImpl implements DMPOperationService{

	@Override
	public boolean stopWifi(String imei) {
		String comment = MqttConfig.COMMAND_PREFIX+MqttConfig.CLOSE_WIFI;
		PublishManager manager = MQTTClint.publicCommand(imei, comment);
	    return manager.handleResult();
	}

	@Override
	public Boolean openWifi(String imei) {
		String comment = MqttConfig.COMMAND_PREFIX+MqttConfig.ENABLE_WIFI;
		PublishManager manager = MQTTClint.publicCommand(imei, comment);
	    return manager.handleResult();
	}

}
