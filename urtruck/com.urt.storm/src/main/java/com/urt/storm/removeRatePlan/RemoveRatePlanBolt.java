package com.urt.storm.removeRatePlan;

import java.util.Date;
import java.util.List;
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

import com.urt.dto.LaoBatchDatadetailDto;
import com.urt.interfaces.batch.BatchService;
import com.urt.interfaces.removeRatePlan.RemoveRatePlanService;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

/**
 * 类说明：归档统一storm的bolt
 * @author fuhp3
 * @date 2016年5月26日 下午6:49:40
 */
public class RemoveRatePlanBolt  extends BaseRichBolt{
	private static Logger logger = LoggerFactory.getLogger(RemoveRatePlanBolt.class);
	
	private BatchService batchService;
	
	private RemoveRatePlanService removeRatePlanService;
	
	/**   
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)    
	*/
	private static final long serialVersionUID = 1L;
	
	private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext-dubbo-service.xml");  
	
    private transient OutputCollector collector;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
    	this.collector = collector;
    	this.batchService=applicationContext.getBean(BatchService.class);
    	this.removeRatePlanService = applicationContext.getBean(RemoveRatePlanService.class);
    }

    @Override
    public void execute(Tuple input) {
    	logger.debug(">>>>>>>>>>>>>>>>>>>>>>>进入RemoveRatePlanBolt的execute函数");
		LaoBatchDatadetailDto dto=new LaoBatchDatadetailDto();
		
    	try{
    		String str = (String) input.getValue(0);
    		if("".equals(str)||str.length()<15){
    			collector.ack(input);
    			return ;
    		}
    		logger.debug(str);
    		
    		JSONObject  json=new JSONObject(str);
    		//处理从Kafka Producter生成topic主题的消息
    		String iccid = null;
    		String custid = null;
    		String ifAdmin = null;
    		if(json.has("iccid")){
				iccid=json.getString("iccid");
				dto.setIccid(iccid);
			}
			if(json.has("ifAdmin")){
				ifAdmin = json.getString("ifAdmin");
			}
			if(json.has("custId")){
				custid = json.getString("custId");
			}
			
			String batchId=json.getString("batchId");
			dto.setBatchId(Long.parseLong(batchId));
			
			//在批量详细表中查询，如果同一批次有记录，就不执行
			if(!batchService.duplicateRemoval(iccid, batchId)){
				collector.ack(input);
    			return ;
			}
			
		   List<String> removeRatePlan = removeRatePlanService.removeRatePlan(iccid, "");
			
		 //将结果处理记录到 批量处理详细表中
		   if(removeRatePlan != null && removeRatePlan.size() > 0){
			   dto.setRemark("ok");  
			   for (int i = 0; i < removeRatePlan.size(); i++) {
				  if(i == 0){
					  dto.setParaCode1(removeRatePlan.get(i));
				  }else if(i == 1){
					  dto.setParaCode2(removeRatePlan.get(i));
				  }else if(i == 2){
					  dto.setParaCode3(removeRatePlan.get(i));
				  }else if(i == 3){
					  dto.setParaCode4(removeRatePlan.get(i));
				  }else if(i == 4){
					  dto.setParaCode5(removeRatePlan.get(i));
				  }else if(i == 5){
					  dto.setParaCode6(removeRatePlan.get(i));
				  }else if(i == 6){
					  dto.setParaCode7(removeRatePlan.get(i));
				  }else if(i == 7){
					  dto.setParaCode8(removeRatePlan.get(i));
				  }else if(i == 8){
					  dto.setParaCode9(removeRatePlan.get(i));
				  }else if(i == 9){
					  dto.setParaCode10(removeRatePlan.get(i));
				  }else if(i == 10){
					  dto.setParaCode11(removeRatePlan.get(i));
				  }else if(i == 11){
					  dto.setParaCode12(removeRatePlan.get(i));
				  }else if(i == 12){
					  dto.setParaCode13(removeRatePlan.get(i));
				  }else{
					  dto.setParaCode30(removeRatePlan.get(i));
				  }
			}
		   }else{
			   dto.setRemark("没有删除资费计划"); 
		   }
		   dto.setDealTag("2");
			
		}catch(Exception e){
			e.printStackTrace();
			dto.setDealTag("3");
		    dto.setRemark(">>>>>>>>>>>>>>>>>>>>>>抛出异常");
		}finally{
			dto.setOperId("admin");
			dto.setTradeTypeCode((short)180);
			dto.setUpdateTime(new Date());
			dto.setRecvTime(new Date());
			dto.setDatadetailId(Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.TRADE_ID)));
			batchService.saveBatchDataDetail(dto);
        	collector.ack(input);
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
    	declarer.declare(new Fields("message"));
    }

}
