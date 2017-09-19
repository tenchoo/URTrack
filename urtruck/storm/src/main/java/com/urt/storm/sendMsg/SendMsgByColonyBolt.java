package com.urt.storm.sendMsg;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

import com.urt.dto.LaoBatchDatadetailDto;
import com.urt.dto.Goods.LaoUserDto;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.batch.BatchService;
import com.urt.interfaces.grpnet.GrpNetExpBillService;
import com.urt.interfaces.sendMessage.SendMessageService;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

/**
 * 类说明：归档统一storm的bolt
 * @author fuhp3
 * @date 2016年5月26日 下午6:49:40
 */
public class SendMsgByColonyBolt  extends BaseRichBolt{
	private static Logger logger = LoggerFactory.getLogger(SendMsgByColonyBolt.class);

	private UserService userService;
	private BatchService batchService;
	private GrpNetExpBillService grpNetExpBillService;
	private SendMessageService sendMessageService;
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
		this.grpNetExpBillService=applicationContext.getBean(GrpNetExpBillService.class);
		this.sendMessageService=applicationContext.getBean(SendMessageService.class);
    }

    @Override
    public void execute(Tuple input) {
    	logger.debug("进入SendMsgByColonyBolt的execute函数");
    	
		String msisdn = null;
		String batchId = null;
		LaoUserDto laoUser = null;
		LaoBatchDatadetailDto dto = new LaoBatchDatadetailDto();
		try {
			String str = (String) input.getValue(0);
			if ("".equals(str) || str.length() < 15) {
				collector.ack(input);
				return;
			}
			logger.debug(str);
			JSONObject json = new JSONObject(str);
			if (!json.isNull("MSISDN")) {
				msisdn = json.getString("MSISDN");
			}
			if (!json.isNull("batchId")) {
				batchId = json.getString("batchId");
				dto.setBatchId(json.getLong("batchId"));
			}
			
			//在批量详细表中查询，如果同一批次有记录，就不执行
			if(!batchService.duplicateRemovalByMsisdn(msisdn, batchId)){
				logger.debug("--------------重复跑了 直接ack掉！-----------");
				collector.ack(input);
    			return ;
			}
			// 根据手机号查询用户
			laoUser = userService.getLaoUserDtoByMsisdn(msisdn);
			if (laoUser != null) {
				// 插入批量任务明细表
				dto.setIccid(laoUser.getIccid());
				dto.setMsisdn(laoUser.getMsisdn());
				dto.setChannelCustId(laoUser.getChannelCustId());
				dto.setUserId(laoUser.getUserId());
			} else {
				dto.setDealTag("3");
				dto.setRemark("user表无此用户！");
				return;
			}

			logger.debug("--------------查询短信参数selectBillSumByCycleIdAndNumber-----------");
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, -1);// 账期设置为上个月
			int cycleId = Integer.parseInt(new SimpleDateFormat("yyyyMM").format(cal.getTime()));
			Map<String, Object> map = grpNetExpBillService.selectBillSumByCycleIdAndNumber(cycleId, msisdn);
			if (map != null && map.size() > 0) {
				map.put("number", msisdn);
				map.put("month", cycleId);
				map.put("templateId", "1");// 集群网的短信模板ID
				map.put("sendTime", new Date());
				map.put("operId", "system");// 操作人员 (选填)
				// 调用公共发送短信接口
				logger.debug("--------------调用发送接口sendMessageService.smsSend-----------");
				sendMessageService.smsSend(map);
			} else {
				dto.setRemark("该用户上月无账单！");
			}
			dto.setFlowStatus("4");
			dto.setDealTag("2");
		} catch (Exception e) {
			dto.setDealTag("3");
			dto.setRemark("抛出异常");
		}finally{
			dto.setRecvTime(new Date());
			dto.setOperId("admin");
			dto.setUpdateTime(new Date());
			dto.setTradeTypeCode((short)1002);
			logger.info(">>>>>>>>>>>>>>>>>>>>>插入批量任务明细表");
			dto.setDatadetailId(Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.BATCHID)));
			batchService.saveBatchDataDetail(dto);
			collector.ack(input);
		}
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
    	declarer.declare(new Fields("message"));
    }

}
