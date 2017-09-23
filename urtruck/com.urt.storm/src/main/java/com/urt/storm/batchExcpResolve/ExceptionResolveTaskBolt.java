package com.urt.storm.batchExcpResolve;

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

import com.urt.dto.IccidLibDto;
import com.urt.dto.LaoBatchDatadetailDto;
import com.urt.dto.exception.LaoBusiexcpLogDto;
import com.urt.dto.exception.LaoExcpDefDto;
import com.urt.interfaces.Trade.TradeService;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.batch.BatchService;
import com.urt.interfaces.exception.BusiExceptionService;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

public class ExceptionResolveTaskBolt extends BaseRichBolt {
	private static Logger logger = LoggerFactory.getLogger(ExceptionResolveTaskBolt.class);
	private UserService userService;
	private BatchService batchService;
	private BusiExceptionService busiException;
	private TradeService tradeService;

	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = 1L;

	private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
			"classpath:applicationContext-dubbo-service.xml");

	private transient OutputCollector collector;

	@SuppressWarnings("rawtypes")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this.collector = collector;
    	this.userService=applicationContext.getBean(UserService.class);
    	this.batchService=applicationContext.getBean(BatchService.class);
    	this.busiException = applicationContext.getBean(BusiExceptionService.class);
    	this.tradeService = applicationContext.getBean(TradeService.class);
	}

	@Override
	public void execute(Tuple input) {
		logger.debug(">>>>>>>>>>>>>>>>>>>>>>>进入ExceptionResolveTaskBolt的execute函数");
		String str = (String) input.getValue(0);
		LaoBatchDatadetailDto dto=new LaoBatchDatadetailDto();
		if ("".equals(str) || str.length() < 15) {
			collector.ack(input);
			return;
		}
		try {
			JSONObject json = new JSONObject(str);
			String batchId = null;
			String iccid = null;
			String accountId = null;
			String custId = null;
			if(json.has("batch_id")){
				batchId=json.getString("batch_id");
				dto.setBatchId(Long.parseLong(batchId));
			}
			if(json.has("iccid")){
				iccid = json.getString("iccid");
				dto.setIccid(iccid);
			}
			if(json.has("accountId")){
				accountId = json.getString("accountId");
				dto.setOperId(accountId);
			}
			
			//在批量详细表中查询，如果同一批次有记录，就不执行
			if(batchService.duplicateRemoval(iccid, batchId)){
				collector.ack(input);
    			return ;
			}
			
			//判断这张卡是否入库
			IccidLibDto iccidDto = userService.selectByIccid(iccid);
			if(iccidDto != null){
				custId = iccidDto.getCustid();
				dto.setCustId(Long.parseLong(custId));
			}else{
				dto.setDealTag("3");
	    		dto.setRemark(iccid+"库中没有录入");
			}
			
			String custid = null;
			String goodsId = null;
			String serviceStatus = null;
			String result = null;
			String tradeId = null;
			String userId = null;
			// 1调用业务抛出异常  2返回结果错误 3返回结果正确 
			if(json.has("excp_id")){
				Long excpId=json.getLong("excp_id");
				LaoBusiexcpLogDto busiexcpLog = busiException.getBusiexcpLog(excpId);
				if(busiexcpLog != null){
					custid = busiexcpLog.getCustId().toString();
					goodsId = busiexcpLog.getGoodsId().toString();
					serviceStatus = busiexcpLog.getExcpTypeCode().toString();
					tradeId = busiexcpLog.getTradeId().toString();
					userId = busiexcpLog.getUserId().toString();
					LaoExcpDefDto laoExcpDefDto = busiException.getLaoExcpDefDto(busiexcpLog.getTradeTypeCode());
//					Object bean = SpringContextUtils.getBean(laoExcpDefDto.getDoFunc());
					if(laoExcpDefDto.getExcpTypeCode() == 1){     //激活状态变更失败
						serviceStatus = "140";
//						tradeId = tradeService.addTrade(custid, null, iccid,goodsId, serviceStatus, "0");
						result = userService.userArchiving(tradeId);
					}else if(laoExcpDefDto.getExcpTypeCode() == 2){//订购过程中有失败的
						result = userService.userArchiving(tradeId);
					}
				}
				
				Short doneTimes =0;
				if(("ok").equals(result)){
					busiexcpLog.setUpdateTime(new Date());
					doneTimes = (short) (busiexcpLog.getDoneTimes()+1);
					busiexcpLog.setDoneTimes(doneTimes);
					busiexcpLog.setDealTag("2");
				}else{
					busiexcpLog.setUpdateTime(new Date());
					doneTimes = (short) (busiexcpLog.getDoneTimes()+1);
					busiexcpLog.setDoneTimes(doneTimes);
					busiexcpLog.setDealTag("3");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
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
