package com.urt.storm.bathBillImport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
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
import com.urt.dto.Goods.OperatorPlanDto;
import com.urt.dto.chargeOff.LaoOperatorsBillDto;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.batch.BatchService;
import com.urt.interfaces.chargeOff.ChargeOffService;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

/**
 * 类说明：归档统一storm的bolt
 * @author fuhp3
 * @date 2016年5月26日 下午6:49:40
 */
public class BillingImportBolt  extends BaseRichBolt{
	private static Logger logger = LoggerFactory.getLogger(BillingImportBolt.class);
	
	private BatchService batchService;
	
	private ChargeOffService chargeOffService;
	
	private UserService userService;
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
    	this.chargeOffService = applicationContext.getBean(ChargeOffService.class);
    	this.userService = applicationContext.getBean(UserService.class);
    }

    @Override
    public void execute(Tuple input) {
    	logger.debug(">>>>>>>>>>>>>>>>>>>>>>>进入BillingImportBolt的execute函数");
		LaoBatchDatadetailDto dto=new LaoBatchDatadetailDto();
		
    	try{
    		String str = (String) input.getValue(0);
    		if("".equals(str)||str.length()<15){
    			collector.ack(input);
    			return ;
    		}
    		logger.debug(str);
    		JSONObject  json=new JSONObject(str);
    		String batchId=json.getString("batchId");
			dto.setBatchId(Long.parseLong(batchId));
			
    		String iccid = null;
    		if(json.has("ICCID")){
    			iccid = json.getString("ICCID");
    		}
    		String realFee = null;
    		if(json.has("REAL_FEE")){
    			realFee = json.getString("REAL_FEE");
    		}
    		String fee = null;
    		if(json.has("FEE")){
    			fee = json.getString("FEE");
    		}
    		String msisdn = null;
    		if(json.has("MSISDN")){
    			msisdn = json.getString("MSISDN");
    		}
    		String pid = null;
    		if(json.has("OPERATORS_PID")){
    			pid = json.getString("OPERATORS_PID");
    		}
    		Integer operators = null;
    		if(json.has("operators")){
    			operators = json.getInt("operators");
    		}
    		Integer cycleId = null;
    		if(json.has("cycleId")){
    			cycleId = json.getInt("cycleId");
    		}
    		
    		// 将对象插入数据库
    		List<LaoOperatorsBillDto> cast = new ArrayList<LaoOperatorsBillDto>();
			LaoOperatorsBillDto operatorBillDto = new LaoOperatorsBillDto();
			
			//根据iccid 或msisdn 查询得到user_id 
			LaoUserDto laoUserDto = userService.getLaoUserDtoByIccid(iccid);
			if(laoUserDto != null){
				operatorBillDto.setUserId(laoUserDto.getUserId());
				operatorBillDto.setChannelCustId(laoUserDto.getChannelCustId());
			}else{
				laoUserDto = userService.getLaoUserDtoByMsisdn(msisdn);
				if(laoUserDto != null){
					operatorBillDto.setUserId(laoUserDto.getUserId());
					operatorBillDto.setChannelCustId(laoUserDto.getChannelCustId());
				}
			}
			
			operatorBillDto.setIccid(iccid);
			if(StringUtils.isNotBlank(realFee)){
				realFee = getMoney(realFee.trim());
				operatorBillDto.setRealFee(Long.parseLong(realFee));
			}
			if(StringUtils.isNotBlank(fee)){
				fee = getMoney(fee.trim());
				operatorBillDto.setFee(Long.parseLong(fee));
				operatorBillDto.setGlaFee(Long.parseLong(fee));
			}
			operatorBillDto.setMsisdn(msisdn);
			operatorBillDto.setOperatorsPid(pid);
			operatorBillDto.setOperatorsBillId(Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.BILL_ID)));
			operatorBillDto.setOperatorsId(operators);
			operatorBillDto.setCycleId(cycleId);
			operatorBillDto.setBalanceTag("0");//0-未对账；1-已平账；2-未平账
			operatorBillDto.setRecvTime(new Date());
			
			OperatorPlanDto selectByPid = chargeOffService.selectByPid(pid);
			if(selectByPid != null){
				operatorBillDto.setPlanId(selectByPid.getPlanId());
				operatorBillDto.setProductName(selectByPid.getPlanName());
			}
			
			cast.add(operatorBillDto);
		    chargeOffService.batchInsert(cast);
		   //将结果处理记录到 批量处理详细表中
		   dto.setDealTag("2");
		   
		}catch(Exception e){
			e.printStackTrace();
			dto.setDealTag("3");
		    dto.setRemark(">>>>>>>>>>>>>>>>>>>>>>抛出异常");
		}finally{
			dto.setOperId("admin");
			dto.setTradeTypeCode((short)200);
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
    
    /**
   	 * 功能描述：元分转换
   	 * 
   	 * @author sunhao
   	 * @date 2017年1月12日 上午10:32:44
   	 * @param @param amount
   	 * @param @return
   	 * @return String
   	 * @throws
   	 */
   	public static String getMoney(String amount) {
   		if (amount == null) {
   			return "";
   		}
   		// 金额转化为分为单位
   		String currency = amount.replaceAll("\\$|\\￥|\\,", ""); // 处理包含, ￥
   																// 或者$的金额
   		int index = currency.indexOf(".");
   		int length = currency.length();
   		Long amLong = 0l;
   		if (index == -1) {
   			amLong = Long.valueOf(currency + "00");
   		} else if (length - index >= 3) {
   			amLong = Long.valueOf((currency.substring(0, index + 3)).replace(
   					".", ""));
   		} else if (length - index == 2) {
   			amLong = Long.valueOf((currency.substring(0, index + 2)).replace(
   					".", "") + 0);
   		} else {
   			amLong = Long.valueOf((currency.substring(0, index + 1)).replace(
   					".", "") + "00");
   		}
   		return amLong.toString();
   	}
}
