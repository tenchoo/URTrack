package com.urt.storm.order;

import java.util.Date;
import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.urt.dto.LaoOperatordealLogDto;
import com.urt.dto.LaoUserOperatorPlanDto;
import com.urt.dto.Goods.OperatorsDto;
import com.urt.dto.Trade.TradeDto;
import com.urt.interfaces.Goods.GoodsService;
import com.urt.interfaces.Goods.OperatorsService;
import com.urt.interfaces.Trade.TradeService;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.order.OrderService;

public class OrderBolt extends BaseRichBolt {
	private static Logger logger = LoggerFactory.getLogger(OrderBolt.class);

	private static final long serialVersionUID = 1L;

	private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
			"classpath:applicationContext-dubbo-service.xml");

	private transient OutputCollector collector;

	private UserService userService;
	
	private OrderService orderService;

	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		// TODO Auto-generated method stub
		this.collector = collector;
		this.userService = applicationContext.getBean(UserService.class);
		this.orderService = applicationContext.getBean(OrderService.class);
	}

	@Override
	public void execute(Tuple input) {
		// TODO Auto-generated method stub
		// 执行逻辑
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>进入OrderBolt的execute函数");
		String str = (String) input.getValue(0);
		if ("".equals(str) || str.length() < 15) {
			collector.ack(input);
			return;
		}
		logger.debug(str);
		JSONObject json=new JSONObject();
		String requestId="";
		try {
			json = new JSONObject(str);
			requestId=json.getString("requestId");
			LaoOperatordealLogDto dealLog = orderService.getDealLogByRequestId(requestId);
			//成功还是失败
			if("success".equals(dealLog.getSuccess())){
				//成功 生成用户信息
				userService.userArchivingByNio(dealLog.getTradeId().toString());
			}else{
				orderService.updateFailNumByRequestId(requestId);
			}
		}catch(Exception e){
			logger.info(e.getMessage()+";"+e.getLocalizedMessage()+";"+e.getStackTrace());
		}finally{
			collector.ack(input);
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer.declare(new Fields("message"));
	}

}
