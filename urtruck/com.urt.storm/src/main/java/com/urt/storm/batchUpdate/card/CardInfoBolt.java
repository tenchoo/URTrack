package com.urt.storm.batchUpdate.card;

import java.util.Date;
import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;





import com.google.common.base.Ascii;
import com.google.gson.Gson;
import com.urt.dto.IccidLibDto;
import com.urt.dto.LaoBatchDatadetailDto;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.batch.BatchService;

/**
 * 类说明：归档统一storm的bolt
 * @author fuhp3
 * @date 2016年5月26日 下午6:49:40
 */
public class CardInfoBolt  extends BaseRichBolt{
	private static Logger logger = LoggerFactory.getLogger(CardInfoBolt.class);

	private UserService userService;
	private BatchService batchService;
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
		this.userService=applicationContext.getBean(UserService.class);
    }

    @Override
    public void execute(Tuple input) {
    	System.out.println("》》》》》》》》》》1查询运行商并修改数据");
    	logger.debug("进入CardInfoBolt的execute函数");
    	IccidLibDto iccid=new IccidLibDto();
		String str = (String) input.getValue(0);
		if("".equals(str)||str.length()<15){
			collector.ack(input);
			return ;
		}
		logger.debug(str);
		//ystem.out.println(str);
		Gson gson=new Gson();
		String batchId="";
		String iccidId="";
		String status="";
		String tel="";
		LaoBatchDatadetailDto dto=new LaoBatchDatadetailDto();
		try {
			JSONObject  json=new JSONObject(str);
			batchId=json.getString("batch_id");
			iccidId=json.getString("ICCID");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(">>>>>>>>>>>>>>>>>>>>>2json转换异常");
		}
		iccid=userService.selectByIccid(iccidId);
		iccid.setBatchId(batchId);
		StringBuffer sb=new StringBuffer();
		try {
			//卡状态变更
			status= batchService.queryCardStatus(iccid);
			if(status!=null && status.trim().length()>0){
				iccid.setCardstatus(status);
				userService.updateByPrimaryKey(iccid);
			}else{
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>没有查到数据");
				/*batchService.saveBatchDataDetail(dto);
				collector.ack(input);*/
				dto.setFlowStatus("5");
				sb.append("卡状态没有数据;");
			}
			//电话号码变更
			tel=batchService.queryCardTel(iccid);
			if(tel!=null){
				iccid.setMsisdn(tel);
				userService.updateByPrimaryKey(iccid);
			}else{
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>没有查到数据");
				sb.append("卡电话号码没有数据;");
				if(!dto.getFlowStatus().equals("5")){
					dto.setFlowStatus("7");
				}else{
					dto.setFlowStatus("6");
				}
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>查询运营商数据失败");
			dto.setIccid(iccid.getIccid());
			dto.setUpdateTime(new Date());
			dto.setBatchId(Long.valueOf(iccid.getBatchId()));
			dto.setDealTag("7");
			dto.setFlowStatus("2");
			dto.setRemark("运营商无此卡信息");
			dto.setOperId("admin");
			dto.setTradeTypeCode((short)1);
			batchService.saveBatchDataDetail(dto);
			collector.ack(input);
			return;
		}
		int result=userService.updateByPrimaryKey(iccid);
		//记录批量变更详情
		
		dto.setIccid(iccid.getIccid());
		dto.setUpdateTime(new Date());
		dto.setBatchId(Long.valueOf(iccid.getBatchId()));
		dto.setOperId("admin");
		dto.setTradeTypeCode((short)1);
		sb.append("处理成功");
		dto.setRemark(sb.toString());
		if(!"5".equals(dto.getFlowStatus()) && !"6".equals(dto.getFlowStatus()) && !"7".equals(dto.getFlowStatus())){
			dto.setFlowStatus("4");
		}
		if(result>0){
			dto.setDealTag("2");
		}else{
			dto.setDealTag("3");
		}
		try {
			batchService.saveBatchDataDetail(dto);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(">>>>>>>>>>>>>>>>>>批次明细保存失败");
			logger.info(">>>>>>>>>>>>>>>>>>批次明细保存失败");
		}
		collector.ack(input);
		


    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
    	declarer.declare(new Fields("message"));
    }

}
