package com.urt.storm.chargeOff;

import java.math.BigDecimal;
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
import com.urt.dto.LaoUserOperatorPlanDto;
import com.urt.dto.chargeOff.LaoOperatorsBillDto;
import com.urt.dto.chargeOff.LaoOperatorsCycleDto;
import com.urt.interfaces.batch.BatchService;
import com.urt.interfaces.chargeOff.ChargeOffService;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

/**
 * 类说明：归档统一storm的bolt
 * @author fuhp3
 * @date 2016年5月26日 下午6:49:40
 */
public class ChargeOffBolt  extends BaseRichBolt{
	private static Logger logger = LoggerFactory.getLogger(ChargeOffBolt.class);
	
	private BatchService batchService;
	
	private ChargeOffService chargeOffService;
	
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
    }

    @Override
    public void execute(Tuple input) {
    	logger.debug(">>>>>>>>>>>>>>>>>>>>>>>进入ChargeOffBolt的execute函数");
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
			
    		String userId = null;
    		if(json.has("userId")){
    			userId = (String) json.get("userId");
    		}
    		int cycleId = 0;
    		if(json.has("cycleId")){
    			cycleId = json.getInt("cycleId");
    		}
    		String operatorId = null;
    		Integer opeId = null;
    		if(json.has("operatorId")){
    			operatorId = json.getString("operatorId");
    			opeId = json.getInt("operatorId");
    		}
    		
    		chargeOffService.chargeOffBefore(userId, cycleId, operatorId);
    		/*List<LaoOperatorsBillDto> operatorBills = null;
    		if(userId != null && cycleId != 0){
    			operatorBills = chargeOffService.getOperatorBills(cycleId,opeId, "0");//0-未对账；1-已平账；2-未平账
    			logger.debug(">>>>>>>>>>>>>>>>>>>>>>>查询到运营商账单信息");
    		}else{
    			collector.ack(input);
    			return ;
    		}
			
			List<LaoUserOperatorPlanDto> planList = null;
			List<Map<String, Object>> list = null;
			String totalCost = null;
			//处理出账逻辑开始
			if(userId != null && operatorBills != null && operatorBills.size() > 0){
				logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>处理出账逻辑开始:");	
				if(("1").equals(operatorId)){   //针对联通的出账处理逻辑
					planList = chargeOffService.getUserPlansByBillTag(Long.parseLong(userId), "0");//0-未出账；1-已出账
					list = chargeOffService.getCostTotalsOfUnicom(Long.parseLong(userId));
				}else if(("2").equals(operatorId) || ("3").equals(operatorId)){ //针对移动和电信的出账处理逻辑
					int planId =operatorBills.get(0).getPlanId();               //移动和电信的planId 是唯一的吗？
					planList = chargeOffService.getUserPlansByPlanId(Long.parseLong(userId), planId);
					list = chargeOffService.getCostTotals(Long.parseLong(userId));
				}
				*//**	  用user_id， plan_id匹配出来的费用REAL_FEE如果=lao_user_operator_plan中根据user_id, plan_id计算出来的费用cost_price
				 * 对账成功，更新LAO_OPERATORS_BILL对应记录的GLA_FEE=REAL_FEE，BALANCE_TAG=1已平账；否则，GLA_FEE= b.cost_price，BALANCE_TAG=2未平账；
				 * 	   如果是联通，更新lao_user_operator_plan的bill_tag=1；电信和移动不做变更；
				 *//*
				if(operatorBills != null && operatorBills.size()> 0){
					for (int i = 0; i < operatorBills.size(); i++) {
						LaoOperatorsBillDto operatorsBill = operatorBills.get(i);
						
						if(planList != null && planList.size()> 0){
							for (int j = 0; j < planList.size(); j++) {
								LaoUserOperatorPlanDto userOperatorPlan = planList.get(j);
								
								if(operatorsBill.getPlanId() == userOperatorPlan.getPlanId() && userOperatorPlan.getStartUseDate() != null ){//匹配成功
									if(operatorsBill.getRealFee() == Long.parseLong(getMoney(userOperatorPlan.getCostPrice())) && operatorsBill.getOperatorsPid().equals(userOperatorPlan.getOperatorsPid())){ 
										if(list != null && list.size() > 0){ 
											planList.remove(j);
											
											//出账处理
											for (Map<String, Object> map : list) {   //map 中的内容：{TOTAL_COST=60, TRADE_ID=1012005440000480}
												if(map != null){
													BigDecimal tradeId = (BigDecimal) map.get("TRADE_ID");
													BigDecimal cost = (BigDecimal) map.get("TOTAL_COST");
													if(tradeId.longValue() == userOperatorPlan.getTradeId()){
														totalCost = cost.toString();
														chargeOffService.chargeOff(userOperatorPlan, operatorsBill, totalCost, cycleId);
													}
												}
											}
										}
									}else{       //未平帐操作
										chargeOffService.updateOperatorBill(Long.parseLong(userOperatorPlan.getCostPrice()), "2", operatorsBill.getOperatorsBillId());
									}
									break;
								}
							}
						}else{
							chargeOffService.updateOperatorBill(-1l, "2", operatorsBill.getOperatorsBillId());
						}
					}
				}
				logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>处理出账中运营商账单为空");
				
				LaoOperatorsCycleDto operatorsCycle = chargeOffService.getOperatorsCycle(cycleId, operatorId, "0");//idType :0-运营商；1-企业客户；2-个人客户   
				if(planList != null && planList.size()> 0){//判断该记录是否是到期未激活，如果是到期未激活，则也需要出账计收
					for (LaoUserOperatorPlanDto laoUserOperatorPlanDto : planList) {
						if(laoUserOperatorPlanDto.getStartUseDate() == null && laoUserOperatorPlanDto.getEndDate() != null && laoUserOperatorPlanDto.getEndDate() != null){
							if(laoUserOperatorPlanDto.getEndDate().getTime() > operatorsCycle.getStartDate().getTime() && 
									laoUserOperatorPlanDto.getEndDate().getTime() < operatorsCycle.getEndDate().getTime()){
								//出账处理
								if(list != null && list.size() > 0){ 
									for (Map<String, Object> map : list) {   //map 中的内容：{TOTAL_COST=60, TRADE_ID=1012005440000480}
										BigDecimal tradeId = (BigDecimal) map.get("TRADE_ID");
										BigDecimal cost = (BigDecimal) map.get("TOTAL_COST");
										if(tradeId.longValue() == laoUserOperatorPlanDto.getTradeId()){
											totalCost = cost.toString();
											chargeOffService.chargeOff(laoUserOperatorPlanDto,null, totalCost, cycleId);
										}
									}
								}
							}
						}
					}
				}
				
			}*/
			
		   //将结果处理记录到 批量处理详细表中
		   dto.setDealTag("2");
			
		}catch(Exception e){
			e.printStackTrace();
			dto.setDealTag("3");
		    dto.setRemark(">>>>>>>>>>>>>>>>>>>>>>抛出异常");
		}finally{
			dto.setOperId("admin");
			dto.setTradeTypeCode((short)190);
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
