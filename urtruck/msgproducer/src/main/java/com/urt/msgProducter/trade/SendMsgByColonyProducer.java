package com.urt.msgProducter.trade;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.urt.msgProducter.util.BaseKafkaProducer;
import com.urt.msgProducter.util.TopicDefinition;

@Service(value="sendMsgByColonyProducer")
public class SendMsgByColonyProducer extends BaseKafkaProducer{

	@Override
	public void setTopic() {
		this.getTopic(TopicDefinition.sendMsgByColonyProducer);
	}
	public void sendMessage(List<Map<String, Object>> list) {
		
		for(Map<String, Object> map:list){
			Gson gson=new Gson();
			String jsonStr=gson.toJson(map);
			super.send(jsonStr);
		}
	} 

}
