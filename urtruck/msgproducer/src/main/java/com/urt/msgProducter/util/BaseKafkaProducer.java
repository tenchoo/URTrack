package com.urt.msgProducter.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

import javax.annotation.PostConstruct;

import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
/**
* 消息发送基类
* @author fuhp
* @date 2016年5月11日 下午6:12:44
*/
public abstract class BaseKafkaProducer {
 	private   kafka.javaapi.producer.Producer<Integer, String> producer;
    protected  String topic;
    private  static Properties props ;
    private final static String  serializerClass="serializer.class";
    private final static String  brokerList="metadata.broker.list";
    
    static{
    	props=parseProperties();
    }
    
    @PostConstruct
    public void init() {
        setTopic();
        Properties prop =new Properties();
        for (Object key : props.keySet()) {  
            if (((String) key).startsWith(this.topic+"."+serializerClass)) {  
            	prop.put(serializerClass, props.getProperty((String)key));  
            }  
            if (((String) key).startsWith(this.topic+"."+brokerList)) {  
            	prop.put(brokerList, props.getProperty((String)key));  
            }              
        }
    	producer = new kafka.javaapi.producer.Producer<Integer, String>(new ProducerConfig(prop));
       // this.topic = TopicDefinition.taskTestTopic;
    	
    }
    
    public void getTopic(String topic){
    	this.topic=topic;
    }
    protected void send(String Message) {
        producer.send(new KeyedMessage<Integer, String>(topic, Message));
    }
    public abstract void setTopic();
    
    private static Properties parseProperties()  {
        Properties prop = new Properties();  

        InputStream inputStream = BaseKafkaProducer.class.getClassLoader().getResourceAsStream("kafka.properties");   
        Properties p = new Properties();   
		try {
			p.load(inputStream);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
      
        return p;
    	
    }
    
}
