package com.urt.storm.batCardQury;

import java.util.Date;
import java.util.HashMap;
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

import com.urt.Exc.BusiExceptionLog;
import com.urt.common.enumeration.ConstantEnum;
import com.urt.common.enumeration.ConstantIntEnum;
import com.urt.dto.IccidLibDto;
import com.urt.dto.LaoBatchDatadetailDto;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.Goods.GoodsReleaseDto;
import com.urt.dto.Trade.TradeDto;
import com.urt.dto.Trade.TradeFeeSubDto;
import com.urt.dto.unicom.PaymentDto;
import com.urt.interfaces.Goods.GoodsReleaseService;
import com.urt.interfaces.Goods.GoodsService;
import com.urt.interfaces.Trade.TradeFeeSubService;
import com.urt.interfaces.Trade.TradeService;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.authority.LaoSsAccountService;
import com.urt.interfaces.batch.BatchService;
import com.urt.interfaces.exception.BusiExceptionService;
import com.urt.interfaces.remain.RemainService;
import com.urt.interfaces.session.SessionOperService;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

import clojure.main;

/**
 * 类说明：归档统一storm的bolt
 * @author fuhp3
 * @date 2016�?�?6�?下午6:49:40
 */
public class CardStatusBolt  extends BaseRichBolt{
	private static Logger logger = LoggerFactory.getLogger(CardStatusBolt.class);
	
	private UserService userService;
	
	private BatchService batchService;
	
	private TradeService tradeService;
	
	private TradeFeeSubService tradeFeeSubService;
	
	private RemainService remainService;
	
	private GoodsService goodsService;
	
	private BusiExceptionService busiException;
	
	private GoodsReleaseService goodsReleaseService;
	
	private LaoSsAccountService accountService;
	
	//private UserFeeInfoService userFeeInfoService;
	
	private SessionOperService sessionOperService;
	/**   
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什�?    
	*/
	private static final long serialVersionUID = 1L;
	
	private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext-dubbo-service.xml");  
	
    private transient OutputCollector collector;
    
    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
    	this.collector = collector;
    	this.batchService=applicationContext.getBean(BatchService.class);
		this.userService=applicationContext.getBean(UserService.class);
		this.tradeService = applicationContext.getBean(TradeService.class);
		this.tradeFeeSubService = applicationContext.getBean(TradeFeeSubService.class);
		this.remainService = applicationContext.getBean(RemainService.class);
		this.goodsService = applicationContext.getBean(GoodsService.class);
		this.busiException = applicationContext.getBean(BusiExceptionService.class);
		this.goodsReleaseService = applicationContext.getBean(GoodsReleaseService.class);
		this.accountService = applicationContext.getBean(LaoSsAccountService.class);
		this.sessionOperService = applicationContext.getBean(SessionOperService.class);
	//	this.userFeeInfoService = applicationContext.getBean(UserFeeInfoService.class);
    }

    @Override
    public void execute(Tuple input) {
    	logger.debug(">>>>>>>>>>>>>>>>>>>>>>>进入CardStatusBolt的execute函数");
    	String orderId = null;
		String result = null;
		String tradeId = null;
		String iccid = null;
		LaoBatchDatadetailDto dto=new LaoBatchDatadetailDto();
		long startDate = System.currentTimeMillis();
		try{
			String str = (String) input.getValue(0);
			if("".equals(str)||str.length()<15){
				collector.ack(input);
				return ;
			}
			logger.debug(str);
			
			//处理从Kafka Producter生成topic主题的消�?
			String goodsId = null;
			//String iccid = null;
			String custid = null;
			String ifAdmin = null;
			String accountId = null;
			JSONObject  json=new JSONObject(str);
			String batchId=json.getString("batch_id");
			dto.setBatchId(Long.parseLong(batchId));
			if(json.has("iccid")){
				iccid=json.getString("iccid");
				dto.setIccid(iccid);
			}
			if(json.has("goodsId")){
				goodsId = json.getString("goodsId");
				dto.setGoodsId(Long.parseLong(goodsId));
			}
			if(json.has("ifAdmin")){
				ifAdmin = json.getString("ifAdmin");
			}
			if(json.has("accountId")){
				accountId = json.getString("accountId");
				dto.setOperId(accountId);
			}
			if(json.has("custId")){
				custid = json.getString("custId");
			}
			logger.info("此ICCID被处理开始时间--"+iccid+"--开始时间--"+startDate);
			String newStatus = json.getString("newStatus");
			String ifMaintenance = json.getString("ifMaintenance");
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+json.toString());
			LaoSsAccountDto user = accountService.queryAccountById(Long.valueOf(accountId));
			if(0==sessionOperService.tryCluLock(batchId,iccid, "iccid", 1, ConstantIntEnum.RELEATIME.getCode()))
			{//加锁失败的时候，直接返回					
				logger.info("tryCluLock failed iccid:"+iccid+"*************");
				collector.ack(input);
				return;
			}
			boolean flag = false;
			if(!("1").equals(ifAdmin)){//如果不是超级管理员，需要做以下判断
				//先判断用户是否拥有这张卡的操作权�?  然后判断用户是否可以订购这个产品 
				if(newStatus.equals("120")){
					flag = userService.hasPermission(iccid, custid);
					if(!flag){
						dto.setRemark(custid+"用户没有"+iccid+"操作权限");
					}
					//判断用户是否可以订购这个产品 
					flag = userService.ifOrderProduct(iccid, goodsId);
					if(!flag){
						dto.setRemark(custid+"使用"+iccid+"不能订购产品:"+goodsId);
					}
				}else{
					flag = userService.hasPermission(iccid, custid);
					if(!flag){
						dto.setRemark("用户没有"+iccid+"操作权限");
					}
				}
			}else{
				flag = true;
				dto.setFlowStatus("2");
				dto.setDealTag("3");
				dto.setRemark("admin操作"+iccid);
			}
			
			//判断这张卡是否入�?
			IccidLibDto iccidDto = userService.selectByIccid(iccid);
			if(iccidDto != null){
				custid = iccidDto.getCustid();
				dto.setCustId(Long.parseLong(custid));
			}else{
				dto.setDealTag("3");
				dto.setRemark(iccid+"库中没有录入");
				flag = false;
			}
			
			//判断这张卡iccid和goodid 查询到的trade是否完成  ，如果没有完成，就不执行下面的操�?
			boolean doned = tradeService.tradingFailure(iccid, newStatus);
			
			if(flag && doned){
				if(newStatus.equals("100")){//如果是激活，联通激活时候需要产�?
					if(iccidDto != null){
						goodsId = iccidDto.getInitproduct();
						dto.setGoodsId(Long.parseLong(goodsId));
					}
				}else if(!newStatus.equals("120")){ //停用
					goodsId = null;
				}
				
				GoodsReleaseDto goodsReleaseDto= null;
				int restFlag = 0;
				if(StringUtils.isNotBlank(goodsId)){
					goodsReleaseDto = goodsReleaseService.findBygoodsReleaseId(Integer.valueOf(goodsId));
					tradeId = tradeService.addTrade(user,custid, orderId, iccid,goodsReleaseDto.getGoodsId().toString(),goodsId, newStatus, ifMaintenance);
					//算费
					TradeFeeSubDto tradeFeeSubDto=null;
					TradeDto tradeDto = tradeService.queryTradeId(Long.parseLong(tradeId));
					if(goodsReleaseDto != null && goodsReleaseDto.getReleasePrice() != null && Double.parseDouble(goodsReleaseDto.getReleasePrice()) > 0){
						tradeFeeSubService.addTradeFeeSub(tradeDto);//算费
						/*tradeFeeSubDto = tradeFeeSubService.queryTradeFreeSubByTradeId(tradeId);
							String fee = tradeFeeSubDto.getFee().toString();
							tradeDto.setFee(tradeFeeSubDto.getFee());*/
						//收费
						PaymentDto payment = new PaymentDto();
						payment.setOrderId(tradeId);//订单�?
						payment.setPayType(0);
						tradeFeeSubService.changePayTag(tradeId, payment);
					}
					// 扣费
					tradeFeeSubDto = tradeFeeSubService.queryTradeFreeSubByTradeId(tradeId);
					if(tradeFeeSubDto!=null){
						HashMap<String,Object> paraMap = new HashMap<String,Object>();
						paraMap.put("channelCustId", custid);//渠道客户ID
						paraMap.put("tradeTypeCode", newStatus);//业务类型编码
						paraMap.put("recvFee", getMoney(tradeFeeSubDto.getFee()));//缴费金额
						paraMap.put("fee", getMoney(tradeFeeSubDto.getOldfee()));//应收费用
						paraMap.put("discntFee", getMoney(tradeFeeSubDto.getFee()));//优惠后费�?
						paraMap.put("realFee", getMoney(tradeFeeSubDto.getFee()));//实收费用
						paraMap.put("payTag", "0");//0：未缴费 1：用户已缴费 2：客户已缴费
						paraMap.put("tradeId", tradeId);
						paraMap.put("goodsId", tradeDto.getGoodsId());
						paraMap.put("userId", tradeDto.getUserId());
						restFlag = remainService.charge(paraMap);//扣费
					}
				}else{
					tradeId = tradeService.addTrade(user,custid, orderId, iccid,null,null, newStatus, ifMaintenance);
				}
				if(StringUtils.isBlank(tradeId)){
					dto.setDealTag("3");
					dto.setRemark("订单生成失败");
					collector.ack(input);
					return ;
				}
				
				if(restFlag == 0){
					if(orderId == null) orderId = tradeId;
					if (StringUtils.isBlank(tradeId)) {
						logger.info("用户归档 中的tradeId为空*******************************************");
					} else {
						result = userService.userArchiving(tradeId);
					}
				}else{
					dto.setDealTag("3");
					dto.setRemark("扣款失败");
				}
				
				//将结果处理记录到 批量处理详细表中
				dto.setTradeId(Long.parseLong(tradeId));
				dto.setResultInfo(result);
				dto.setFlowStatus("4");
				if(result != null){
					if(result.equals("ok")){
						dto.setDealTag("2");
						dto.setRemark("激活成");
					}else if(result.equals("activefailed")){
						dto.setDealTag("3");
						dto.setRemark("激活失");
					}
					else if(result.equals("orderfailed")){
						dto.setDealTag("3");
						dto.setRemark("订购失败");
					}
					else if(result.equals("operatorServiceFailed")){
						dto.setRemark("运营商操作类没有");
					}
				}
			}else{
				dto.setFlowStatus("2");
				dto.setDealTag("3");
				if(!flag)
					dto.setRemark(iccid+"没有权限");
				if(!doned)
					dto.setRemark(iccid+"订单未完");
				logger.info("用户"+iccid+"*******************************************");
			}
			
		}catch(BusiExceptionLog e){
			/*String msg = e.getMsgCode();
    		LaoBusiexcpLogDto exception = new LaoBusiexcpLogDto();
    		exception.setBatchdetailId(batchdetailId);
    		exception.setBatchId(batchId);
    		exception.setChannelCustId(channelCustId);
    		exception.setCustId(custId);
    		exception.setDealTag("0");
    		exception.setDoneTimes(Short.valueOf("0"));
    		exception.setExcpId(excpId);
    		exception.setExcpTypeCode(excpTypeCode);
    		exception.setGoodsId(goodsId);
    		exception.setIccid(iccid);
    		exception.setMsisdn(msisdn);
    		exception.setOperId(operId);
    		exception.setRecvTime(new Date());
    		exception.setRemark(e.getMessage());
    		exception.setTradeId(tradeId);
    		exception.setTradeTypeCode(tradeTypeCode);
    		busiException.insBusiExcpLog(exception);*/
			e.printStackTrace();
			dto.setDealTag("3");
			dto.setRemark(">>>>>>>>>>>>>>>>>>>>>>抛出异常");
		}catch(Exception e){
			e.printStackTrace();
			dto.setDealTag("3");
			dto.setRemark(">>>>>>>>>>>>>>>>>>>>>>抛出异常");
		}finally{
			dto.setUpdateTime(new Date());
			dto.setRecvTime(new Date());
			dto.setDatadetailId(Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.BATCH_ID)));
			batchService.saveBatchDataDetail(dto);
			collector.ack(input);
			logger.info("此iccid被处理时间--"+iccid+"--时间"+(System.currentTimeMillis()-startDate)/1000);
		}
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
    	declarer.declare(new Fields("message"));
    }

    /**
	 * 功能描述：元分转�?
	 * 
	 * @author sunhao
	 * @date 2017�?�?2�?上午10:32:44
	 * @param @param amount
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getMoney(String amount) {
		if (amount == null) {
			return "";
		}
		// 金额转化为分为单�?
		String currency = amount.replaceAll("\\$|\\￥|\\,", ""); // 处理包含, �?
																// 或�?的金�?
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
