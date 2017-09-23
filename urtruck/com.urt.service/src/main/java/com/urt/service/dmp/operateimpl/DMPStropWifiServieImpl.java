package com.urt.service.dmp.operateimpl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urt.interfaces.dmp.DMPOperateService;
import com.urt.service.dmp.mqttutil.MQTTClint;
import com.urt.service.dmp.mqttutil.MqttConfig;
import com.urt.service.dmp.mqttutil.PublishManager;
import com.urt.service.dmp.strategyimpl.DMPRangeAreaServieImpl;

@Service("stopWifiServie")
@Transactional(propagation = Propagation.REQUIRED)
public class DMPStropWifiServieImpl implements DMPOperateService {
	Logger log = Logger.getLogger(DMPStropWifiServieImpl.class);
	@Override
	public boolean OperateExecute(String imei,String custId) {
		log.info("进入停WiFi的接口");
		log.info("imei="+imei);
		log.info("custId="+custId);
		boolean b=false;
		try{
			String comment = MqttConfig.COMMAND_PREFIX+MqttConfig.CLOSE_WIFI;
			log.info("停WiFi的指令为comment="+comment);
			PublishManager manager = MQTTClint.publicCommand(imei, comment);
		    b=manager.handleResult();
		    log.info("b="+b);
		}catch(Exception e){
			e.printStackTrace();
			b=false;
		}
		 if(b)
		    	log.info("停WiFi成功");
		    else
		    	log.info("停WiFi失败");
		log.info("走出停WiFi的接口");
	    return b;
	}

	
}
