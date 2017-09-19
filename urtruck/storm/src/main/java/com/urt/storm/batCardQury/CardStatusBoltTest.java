package com.urt.storm.batCardQury;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.urt.interfaces.User.UserQueryCardStateTest;

/**
 * 类说明：归档统一storm的bolt
 * @author fuhp3
 * @date 2016年5月26日 下午6:49:40
 */
public class CardStatusBoltTest  extends BaseRichBolt{
	private static Logger logger = LoggerFactory.getLogger(CardStatusBoltTest.class);

	private UserQueryCardStateTest userQueryCardStateTest;
	/**   
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)    
	*/
	private static final long serialVersionUID = 1L;
	
	private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext-dubbo-service.xml");  
	
    private transient OutputCollector collector;
    
    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
    	this.collector = collector;
		this.userQueryCardStateTest=applicationContext.getBean(UserQueryCardStateTest.class);
    }

    @Override
    public void execute(Tuple input) {
    	System.out.println("进入ArchiveTotalBolt的execute函数");
    	logger.debug("进入ArchiveTotalBolt的execute函数");
    	try{
			String str = (String) input.getValue(4);
			if("".equals(str)||str.length()<15){
				collector.ack(input);
				return ;
			}
			//logger.debug(str);
			//ystem.out.println(str);
			userQueryCardStateTest.updateQueryCardState(str);
		}catch(RuntimeException e){


			e.printStackTrace();

        }finally{


        	collector.ack(input);


        }



    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
    	declarer.declare(new Fields("message"));
    }

}
