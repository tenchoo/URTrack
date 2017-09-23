package com.urt.storm.reorder;

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

import com.urt.dto.LaoUserOperatorPlanDto;
import com.urt.dto.Goods.OperatorsDto;
import com.urt.dto.Trade.TradeDto;
import com.urt.interfaces.Goods.GoodsService;
import com.urt.interfaces.Goods.OperatorsService;
import com.urt.interfaces.Trade.TradeService;
import com.urt.interfaces.User.UserService;

public class ReorderBolt extends BaseRichBolt {
	private static Logger logger = LoggerFactory.getLogger(ReorderBolt.class);

	private static final long serialVersionUID = 1L;

	private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
			"classpath:applicationContext-dubbo-service.xml");

	private transient OutputCollector collector;

	private UserService userService;
	
	private TradeService tradeService;
	
	private GoodsService goodsService;
	
	private OperatorsService operatorsService;

	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		// TODO Auto-generated method stub
		this.collector = collector;
		this.userService = applicationContext.getBean(UserService.class);
		this.goodsService = applicationContext.getBean(GoodsService.class);
		this.operatorsService = applicationContext.getBean(OperatorsService.class);
	}

	@Override
	public void execute(Tuple input) {
		// TODO Auto-generated method stub
		// 执行逻辑
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>进入ReorderBolt的execute函数");
		String str = (String) input.getValue(0);
		if ("".equals(str) || str.length() < 15) {
			collector.ack(input);
			return;
		}
		logger.debug(str);
		JSONObject json=new JSONObject();
		Object object=null;
		String tradeId="";
		try {
			json = new JSONObject(str);
			tradeId=json.getString("tradeId");
			TradeDto tradeDto = tradeService.queryTradeId(Long.valueOf(tradeId));
			Integer operatorsId = goodsService.findByGoodsId(tradeDto.getGoodsId()).getOperatorsId();
			OperatorsDto operatorsDto = operatorsService.selectByPrimaryKey(operatorsId);
			if("0".equals(operatorsDto.getAsync())){
				String userArchiving = userService.userArchiving(tradeId);
			}else{
				//异步
			}
		}catch(Exception e){
			
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
