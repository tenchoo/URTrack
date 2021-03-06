package com.urt.msgProducter.trade;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.urt.msgProducter.util.BaseKafkaProducer;
import com.urt.msgProducter.util.TopicDefinition;

@Service(value = "removeRatePlanProducer")
public class BatchRemoveRatePlanProducer extends BaseKafkaProducer {
	@Override
	public void setTopic() {
		this.getTopic(TopicDefinition.removeRatePlanTopic);
	}

	public void sendMessage(List<Map<String, Object>> iccids) {

		for (Map<String, Object> iccid : iccids) {
			Gson gson = new Gson();
			String jsonStr = gson.toJson(iccid);
			super.send(jsonStr);
		}
	}
}
