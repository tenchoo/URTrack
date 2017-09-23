
package com.urt.msgProducter.trade;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.urt.msgProducter.util.BaseKafkaProducer;
import com.urt.msgProducter.util.TopicDefinition;

/**
 * Task消息发送类 需要在TopicDefinition定义消息队列名称 在setTopic设置消息队列名称
 * 在kafka.properties设置当前队列信息，参数前缀为消息队列名称加‘.’
 * 如：taskTestTopic.metadata.broker.list1=10.1.248.125:9092
 * 
 * @author fuhp
 * @date 2016年5月11日 下午6:12:44
 */

@Service("exceptionResProducer")
public class ExceptionResProducer extends BaseKafkaProducer {

	public void sendTask(List<Map<String, Object>> serviceMsg) {
		for(Map<String, Object> msg:serviceMsg){
			Gson gson=new Gson();
			String jsonStr=gson.toJson(msg);
			super.send(jsonStr);
		}
	}

	@Override
	public void setTopic() {
		super.topic = TopicDefinition.exceptionResTopic;
	}
}
