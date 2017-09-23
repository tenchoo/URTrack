package com.urt.msgProducter.trade;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.urt.dto.grpnet.GrpnetImpbillDto;
import com.urt.msgProducter.util.BaseKafkaProducer;
import com.urt.msgProducter.util.TopicDefinition;

@Service(value="batchFtpImportProducer")
public class BatchFtpImportProducer extends BaseKafkaProducer{

	@Override
	public void setTopic() {
		this.getTopic(TopicDefinition.batchFtpImportProducer);
	}
	public void sendMessage(List<List<GrpnetImpbillDto>> listDto) {
		for (int i = 0; i < listDto.size(); i++) {
			List<GrpnetImpbillDto> list = listDto.get(i);
			Gson gson=new Gson();
			String jsonStr=gson.toJson(list);
			super.send(jsonStr);
		}
	} 
}
