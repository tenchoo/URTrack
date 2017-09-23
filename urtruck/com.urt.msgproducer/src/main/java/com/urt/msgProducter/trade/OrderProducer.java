package com.urt.msgProducter.trade;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.urt.msgProducter.util.BaseKafkaProducer;
import com.urt.msgProducter.util.TopicDefinition;

@Service("orderProducer")
public class OrderProducer extends BaseKafkaProducer{

	@Override
	public void setTopic() {
		// TODO Auto-generated method stub
		this.getTopic(TopicDefinition.orderToppic);
	}
	public void sendMessage(Map<String, Object> map) {
		Gson gson=new Gson();
		String jsonStr=gson.toJson(map);
		super.send(jsonStr);
	} 
}
