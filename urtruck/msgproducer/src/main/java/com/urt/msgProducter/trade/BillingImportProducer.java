package com.urt.msgProducter.trade;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.urt.msgProducter.util.BaseKafkaProducer;
import com.urt.msgProducter.util.TopicDefinition;

@Service(value = "billImportProducer")
public class BillingImportProducer extends BaseKafkaProducer {
	@Override
	public void setTopic() {
		this.getTopic(TopicDefinition.billImportTopic);
	}

	public void sendMessage(List<Map<String, String>> maps) {

		for (Map<String, String> map : maps) {
			Gson gson = new Gson();
			String jsonStr = gson.toJson(map);
			super.send(jsonStr);
		}
	}

}

