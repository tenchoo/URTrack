package com.urt.Ability.collect;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.sun.xml.wss.XWSSecurityException;
import com.urt.Ability.unicom.service.GetTerminalDetailsService;
import com.urt.Ability.unicom.service.GetTerminalRatingService;
import com.urt.Ability.unicom.service.RemoveRatePlanFromQueueService;
import com.urt.Ability.unicom.vo.GetTerminalDetailsResponse;
import com.urt.Ability.unicom.vo.GetTerminalRatingResponse;
import com.urt.dto.CardStatusDto;
import com.urt.interfaces.Trade.TradeService;
import com.urt.interfaces.User.UserService;
import com.urt.mapper.LaoOperatordealLogMapper;
import com.urt.mapper.LaoUserMapper;
import com.urt.mapper.LaoUserOperatorPlanMapper;
import com.urt.mapper.LaoUserSvcstateMapper;
import com.urt.mapper.OperatorPlanMapper;
import com.urt.mapper.OperatorsMapper;
import com.urt.mapper.ext.LaoUserOperatorPlanExtMapper;
import com.urt.po.EsimLenovoGoodsId;
import com.urt.po.LaoOperatordealLog;
import com.urt.po.LaoUser;
import com.urt.po.LaoUserOperatorPlan;
import com.urt.po.LaoUserSvcstate;
import com.urt.po.OperatorPlan;
import com.urt.po.Operators;
import com.urt.utils.SeqID;
import com.urt.utils.SpringContextUtils;
import com.urt.utils.ZkGenerateSeq;

/**
 * 产品订购处理类
 * 
 * @author sunhao
 *
 */
public abstract class UserOrderProduct <T> {
	Logger log = Logger.getLogger(getClass());
	@Autowired
	private LaoOperatordealLogMapper laoOperatordealLogDAO;

	private LaoOperatordealLog logger;

	@Autowired
	private LaoUserOperatorPlanExtMapper LaoUserOperatorPlanExDAO;
	
	@Autowired
	private LaoUserOperatorPlanMapper LaoUserOperatorPlanDAO;

	@Autowired
	private LaoUserMapper laoUserDAO; // 用户资料

	@Autowired
	private OperatorPlanMapper operatorPlanDAO; // 运营商套餐
	
	@Autowired
	private GetTerminalRatingService getTerminalRatingService;//联通的API
	
	@Autowired
	private RemoveRatePlanFromQueueService removeRatePlanFromQueueService;//联通的API
	
	@Autowired
	private OperatorsMapper operatorsDao;
	
	@Autowired
	private TradeService tradeService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	LaoUserSvcstateMapper  laoUserSvcstateMapper;

	@Autowired
	private GetTerminalDetailsService getTerminalDetailsService;
	
	/**默认资费计划**/
	private String DEFAULT_TERMINAL_RATE = "110WLW004085_MON-FIX_5120M-0S";

	/**
	 * 此方法用于推送参数,记录入参
	 *
	 * @param args
	 * @return
	 */
	protected abstract OrderProductResultMsg sendMessage(Object... args);

	/**
	 * 将做真正的请求
	 * 
	 * @param args
	 * @return
	 */
	public boolean orderGoods(String userId,String tradeId) {
		int pos = 0;
		Long trade = Long.parseLong(tradeId);
		Long id = Long.parseLong(userId);
		LaoUser laoUser = laoUserDAO.selectByPrimaryKey(id);
		String iccid = laoUser.getIccid();
		List<LaoUserOperatorPlan> userOpratorPlanList = LaoUserOperatorPlanExDAO.selectByTradeId(trade);
		boolean flag = true;
		
		// 有产品订购的时候 才执行下面的操作
		if (userOpratorPlanList != null && userOpratorPlanList.size() > 0) {
			
			Long beginTime = System.currentTimeMillis();
			log.info("................订购开始...................time:" + beginTime +"iccid:"+iccid+"----size--"+userOpratorPlanList.size()+"---trade："+trade);
			Map<String, String> accountInfo = null;
			if(userOpratorPlanList.get(0).getOperatorsId() == 1){
				
				try {
					accountInfo =	getCountId(iccid);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SOAPException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (XWSSecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//删除联通包月资费计划 同时返回除去默认计划外，剩下的计划数
			//wangxb20 20170622 增加只有预付单卡的时候才去删除5120这个资费
			if(userOpratorPlanList.get(0).getOperatorsId() == 1 && "100273818".equals(accountInfo.get("accountId"))) {
				pos = removeDefaultRatePlan(iccid);				
			}
			if(pos == -1) {
				log.info(iccid+"................中途订购有失败操作...................订购:查询当前资费包数和删除5120M 时候报错");
				return false;
			}
			//如果是移动的， 使用的是MsisDN 
			if(userOpratorPlanList.get(0).getOperatorsId() == 2) {
				iccid = laoUser.getMsisdn();
			}
			for (LaoUserOperatorPlan userOperatorPlan : userOpratorPlanList) {
				if(userOperatorPlan.getOpeartorsDealRst().equals("0")) continue;//0成功1失败
				OperatorPlan plan = operatorPlanDAO.selectByPrimaryKey(userOperatorPlan.getPlanId());
//				LaoUserOperatorPlanDAO.deleteByPrimaryKey(userOperatorPlan.getUserId(), userOperatorPlan.getPlanId(), userOperatorPlan.getStartDate(), userOperatorPlan.getGoodsIndex());
				log.info("...............orderGoods begin getGoodsIndex: "+ userOperatorPlan.getGoodsIndex()+" pos:"+pos);
				if(userOperatorPlan.getOperatorsId() == 1){ //查询联通包月资费计划
					userOperatorPlan.setGoodsIndex(userOperatorPlan.getGoodsIndex()+pos);		
				
				}
				log.info("...............orderGoods end getGoodsIndex: "+ userOperatorPlan.getGoodsIndex());
				if(plan == null){
					log.info(iccid+"................订购操作...................查询OperatorPlan失败:"+userOperatorPlan.getPlanId());
					continue;
				}
				UserOrderProduct<T>.OrderProductResultMsg resultMessage = sendMessage(iccid, plan, userOperatorPlan, laoUser);
				log.info(iccid+"................订购操作...................订购:"+plan.getPlanName()+".....是否成功：......"+resultMessage.success);
				//更新userOperatorPlan 记录调用服务结果
				userOperatorPlan.setOpeartorsDealCode(resultMessage.getOpeartorsDealCode());
				userOperatorPlan.setOpeartorsDealNum(userOperatorPlan.getOpeartorsDealNum() + 1);
				userOperatorPlan.setOpeartorsDealRst(resultMessage.getOpeartorsDealRst());
				if(resultMessage.isSuccess()){
					userOperatorPlan.setStartUseDate(new Date());
				}
				userOperatorPlan.setGoodsIndex(userOperatorPlan.getGoodsIndex()-pos);
				LaoUserOperatorPlanDAO.updateByPrimaryKeySelective(userOperatorPlan);
				
				//记录日志
				logger = new LaoOperatordealLog();
				String msgid = ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID);
				logger.setId(Long.valueOf(msgid));
				logger.setIccid(iccid);
				logger.setInputParameters(resultMessage.getInputMessage());
				logger.setOutputParameters(resultMessage.getOutMessage());
				logger.setResultInfo(resultMessage.getOpeartorsDealRst());
				logger.setResultCode(resultMessage.getOpeartorsDealCode());
				if(userOperatorPlan.getOperatorsId() != null){
					logger.setOperatorId(userOperatorPlan.getOperatorsId().toString());
				}
				logger.setOperatorType("1");//1订购2发短信3状态变更4查询卡状态
				if(resultMessage.isSuccess()){
					logger.setSuccess("0");    //0成功1失败
				}else{
					logger.setSuccess("1");    //0成功1失败
				}
				logger.setCreateDate(new Date());
				laoOperatordealLogDAO.insertSelective(logger);
				
				if(!resultMessage.isSuccess()){
					log.info(iccid+"................中途订购有失败操作...................订购:"+plan.getPlanName());
					flag = resultMessage.isSuccess();
					logger.setResultCode(iccid+"中途订购有失败操作订购:"+plan.getPlanName());
					break;
				}
			}
			log.info("................订购结束.......... Total cost: "+ (System.currentTimeMillis() - beginTime) / 1000.0);
		}
		return flag;
	}
	public boolean order(LaoUserOperatorPlan userOperatorPlan,LaoUser laoUser,String iccid) {
		
		int pos = 0;
		boolean flag = true;
		
		// 有产品订购的时候 才执行下面的操作
		Long beginTime = System.currentTimeMillis();
		log.info("................二次计费订购开始...................time:" + beginTime);
			
		//删除联通包月资费计划 同时返回除去默认计划外，剩下的计划数
		if(userOperatorPlan.getOperatorsId() == 1) {
			pos = removeDefaultRatePlan(iccid);
		}
		
		//如果是移动的， 使用的是MsisDN 
		if(userOperatorPlan.getOperatorsId() == 2) {
			iccid = laoUser.getMsisdn();
		}
		OperatorPlan plan = operatorPlanDAO.selectByPrimaryKey(userOperatorPlan.getPlanId());
		
		if(userOperatorPlan.getOperatorsId() == 1){ //查询联通包月资费计划
			userOperatorPlan.setGoodsIndex(userOperatorPlan.getGoodsIndex()+pos);
		}
				
		if(plan == null){
			log.info(iccid+"................二次计费订购操作...................查询OperatorPlan失败:"+userOperatorPlan.getPlanId());
		}
		//查询运营商卡状态  DEACTIVATED_NAME 状态变更
		UserOrderProduct<T>.OrderProductResultMsg resultMessage = sendMessage(iccid,plan,userOperatorPlan,laoUser);
		log.info(iccid+"................二次计费订购操作...................订购:"+plan.getPlanName()+".....是否成功：......"+resultMessage.success);
		//更新userOperatorPlan 记录调用服务结果
		userOperatorPlan.setOpeartorsDealCode(resultMessage.getOpeartorsDealCode());
		userOperatorPlan.setOpeartorsDealNum(userOperatorPlan.getOpeartorsDealNum() + 1);
		userOperatorPlan.setOpeartorsDealRst(resultMessage.getOpeartorsDealRst());
		if(resultMessage.isSuccess()){
			userOperatorPlan.setStartUseDate(new Date());
			/*String tradeId = tradeService.addTrade(null, laoUser.getCustId(), iccid, "", "", "130", "0");*/
		}
		userOperatorPlan.setGoodsIndex(userOperatorPlan.getGoodsIndex()-pos);
		LaoUserOperatorPlanDAO.updateByPrimaryKeySelective(userOperatorPlan);
		if(!resultMessage.isSuccess()){
			log.info(iccid+"................二次计费中途订购有失败操作...................订购:"+plan.getPlanName());
			flag = resultMessage.isSuccess();
		}
		log.info("................二次计费订购结束.......... Total cost: "+ (System.currentTimeMillis() - beginTime) / 1000.0);
		//
		
		Operators op = operatorsDao.selectByPrimaryKey(userOperatorPlan.getOperatorsId());

		UserQueryCardStatus userQueryCardStatus = (UserQueryCardStatus) SpringContextUtils.getBean(op.getQueryCardStatus());
		UserStateChange stateChange  = (UserStateChange) SpringContextUtils.getBean(op.getStatusDealClass());

		CardStatusDto cardStatusDto = userQueryCardStatus.queryCardStatus(iccid);
		
		try {
			if(cardStatusDto.getCardStatus().equals("5")){
				/*tradeId = tradeService.addTrade(null, laoUser.getCustId().toString(), iccid, "", "", "140", "0");
				String userArchiving = userService.userArchiving(tradeId);*/
				LaoUserSvcstate svcstate=laoUserSvcstateMapper.selectByUserId(laoUser.getUserId());
				flag = stateChange.stateChange(iccid, laoUser.getUserId(), 1, svcstate.getStartDate(), "6","");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return flag;
	}
	public boolean order(List<LaoUserOperatorPlan> userOperatorPlans,LaoUser laoUser,String iccid) {
		
		int pos = 0;
		boolean flag = true;
		
		// 有产品订购的时候 才执行下面的操作
		Long beginTime = System.currentTimeMillis();
		log.info("................二次计费订购开始...................time:" + beginTime);
		
		//删除联通包月资费计划 同时返回除去默认计划外，剩下的计划数
		if(userOperatorPlans.get(0).getOperatorsId() == 1) {
			pos = removeDefaultRatePlan(iccid);
		}
		//如果是移动的， 使用的是MsisDN 
		if(userOperatorPlans.get(0).getOperatorsId() == 2) {
			iccid = laoUser.getMsisdn();
		}
		for (LaoUserOperatorPlan userOperatorPlan : userOperatorPlans) {
			OperatorPlan plan = operatorPlanDAO.selectByPrimaryKey(userOperatorPlan.getPlanId());
			
			if(userOperatorPlan.getOperatorsId() == 1){ //查询联通包月资费计划
				userOperatorPlan.setGoodsIndex(userOperatorPlan.getGoodsIndex()+pos);
			}
			
			if(plan == null){
				log.info(iccid+"................二次计费订购操作...................查询OperatorPlan失败:"+userOperatorPlan.getPlanId());
			}
			//查询运营商卡状态  DEACTIVATED_NAME 状态变更
			UserOrderProduct<T>.OrderProductResultMsg resultMessage = sendMessage(iccid,plan,userOperatorPlan,laoUser);
			log.info(iccid+"................二次计费订购操作...................订购:"+plan.getPlanName()+".....是否成功：......"+resultMessage.success);
			//更新userOperatorPlan 记录调用服务结果
			userOperatorPlan.setOpeartorsDealCode(resultMessage.getOpeartorsDealCode());
			userOperatorPlan.setOpeartorsDealNum(userOperatorPlan.getOpeartorsDealNum() + 1);
			userOperatorPlan.setOpeartorsDealRst(resultMessage.getOpeartorsDealRst());
			if(resultMessage.isSuccess()){
				userOperatorPlan.setStartUseDate(new Date());
			}
			userOperatorPlan.setGoodsIndex(userOperatorPlan.getGoodsIndex()-pos);
			LaoUserOperatorPlanDAO.updateByPrimaryKeySelective(userOperatorPlan);
			if(!resultMessage.isSuccess()){
				log.info(iccid+"................二次计费中途订购有失败操作...................订购:"+plan.getPlanName());
				flag = resultMessage.isSuccess();
			}
			log.info("................二次计费订购结束.......... Total cost: "+ (System.currentTimeMillis() - beginTime) / 1000.0);
		}
		Operators op = operatorsDao.selectByPrimaryKey(userOperatorPlans.get(0).getOperatorsId());
		UserQueryCardStatus userQueryCardStatus = (UserQueryCardStatus) SpringContextUtils.getBean(op.getQueryCardStatus());
		UserStateChange stateChange  = (UserStateChange) SpringContextUtils.getBean(op.getStatusDealClass());
		CardStatusDto cardStatusDto = userQueryCardStatus.queryCardStatus(iccid);
		try {
			if(cardStatusDto.getCardStatus().equals("5")){
				/*tradeId = tradeService.addTrade(null, laoUser.getCustId().toString(), iccid, "", "", "140", "0");
				String userArchiving = userService.userArchiving(tradeId);*/
				LaoUserSvcstate svcstate=laoUserSvcstateMapper.selectByUserId(laoUser.getUserId());
				flag = stateChange.stateChange(iccid, laoUser.getUserId(), 1, svcstate.getStartDate(), "6","");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return flag;
	}
	
	
	/**
	 * 联通的删除默认包月计划
	 * @param iccid
	 */
	public int removeDefaultRatePlan(String iccid){
		int planSize  = 0;
		try {
			String random = ZkGenerateSeq.get6IdSeq(SeqID.REMOVE_ID);
			SOAPMessage soapMessage = getTerminalRatingService.call(iccid, random);
			GetTerminalRatingResponse response = (GetTerminalRatingResponse) getTerminalRatingService.parseMessage(soapMessage);
			if (response != null && response.getList().size() > 1) {//当前资费计划如果是5120M的时候，不删除
				planSize = response.getList().size() -1;					
						if(response.getList().get(planSize).getRatePlanName().equals(DEFAULT_TERMINAL_RATE)){
							removeRatePlanFromQueueService.call(iccid,random,planSize+"",DEFAULT_TERMINAL_RATE);
						}else{
							planSize = planSize +1;
						}					
			}else if(response.getList().size()==1 && !response.getList().get(planSize).getRatePlanName().equals(DEFAULT_TERMINAL_RATE)){
				planSize=1;
			}
		} catch (IOException | XWSSecurityException | SOAPException e) {
			e.printStackTrace();
			return -1;
		}
		if(planSize < 0)return 0;
		return planSize;
	}
	/**
	 * wangxb20 
	 * 月付单卡包个数查询和资费计划
	 * @param iccid
	 * @return
	 */
	public String RatePlanNum(String iccid){
		int planSize  = 0;
		String planName = null;
		try {
			String random = ZkGenerateSeq.get6IdSeq(SeqID.REMOVE_ID);
			SOAPMessage soapMessage = getTerminalRatingService.call(iccid, random);
			GetTerminalRatingResponse response = (GetTerminalRatingResponse) getTerminalRatingService.parseMessage(soapMessage);
			planSize =response.getList().size();
			planName =response.getList().get(0).getRatePlanName();
		}catch(Exception e){
			e.printStackTrace();
		}
		return planSize+"="+planName  ;
	}
	
	protected Map<String,String> getCountId(String iccid) throws IllegalArgumentException, SOAPException, IOException, XWSSecurityException{
		SOAPMessage request = null;		
		SOAPMessage response = null;
		GetTerminalDetailsResponse responseFirst = null;
		List<String> list = new ArrayList<String>();
		list.add(iccid);
		
		request= getTerminalDetailsService.createRequest(list,"lmh-test-terminal-detail");
		SOAPMessage createRequestFirst = getTerminalDetailsService.createRequest(list,"lmh-test-terminal-detail");
		request = getTerminalDetailsService.secureMessage(createRequestFirst, getTerminalDetailsService.getUsername(), getTerminalDetailsService.getPasswd());	
		SOAPConnection createConnection = getTerminalDetailsService.getConnectionFactory().createConnection();
		response = createConnection.call(request, new URL(getTerminalDetailsService.getUrl()));
		responseFirst = (GetTerminalDetailsResponse) getTerminalDetailsService.parseMessage(response);
		String accountId = responseFirst.getList().get(0).getAccountId();
		String status = responseFirst.getList().get(0).getStatus();
		//String userFlow = responseFirst.getList().get(0).getMonthToDateUsage();
		Map<String,String> map = new HashMap<String,String>();
		map.put("accountId", accountId);
		map.put("status", status);
		return map;
	}
	
	protected class OrderProductResultMsg {
		private boolean success = false; //返回执行结果
		private String opeartorsDealCode; //返回执行结果信息
		private String opeartorsDealRst;//返回数据集 0成功 1失败
		private String inputMessage; //入参
		private String outMessage; // 出参
		LaoUserOperatorPlan userOperatorPlan;
		public OrderProductResultMsg() {
		}
		/**
		 * @return the success
		 */
		public boolean isSuccess() {
			return success;
		}
		/**
		 * @param success the success to set
		 */
		public void setSuccess(boolean success) {
			this.success = success;
		}
		/**
		 * @return the opeartorsDealCode
		 */
		public String getOpeartorsDealCode() {
			return opeartorsDealCode;
		}
		/**
		 * @param opeartorsDealCode the opeartorsDealCode to set
		 */
		public void setOpeartorsDealCode(String opeartorsDealCode) {
			this.opeartorsDealCode = opeartorsDealCode;
		}
		
		/**
		 * @return the opeartorsDealRst
		 */
		public String getOpeartorsDealRst() {
			return opeartorsDealRst;
		}
		/**
		 * @param opeartorsDealRst the opeartorsDealRst to set
		 */
		public void setOpeartorsDealRst(String opeartorsDealRst) {
			this.opeartorsDealRst = opeartorsDealRst;
		}
		/**
		 * @return the inputMessage
		 */
		public String getInputMessage() {
			return inputMessage;
		}
		/**
		 * @param inputMessage the inputMessage to set
		 */
		public void setInputMessage(String inputMessage) {
			this.inputMessage = inputMessage;
		}
		/**
		 * @return the outMessage
		 */
		public String getOutMessage() {
			return outMessage;
		}
		/**
		 * @param outMessage the outMessage to set
		 */
		public void setOutMessage(String outMessage) {
			this.outMessage = outMessage;
		}
		/**
		 * @return the userOperatorPlan
		 */
		public LaoUserOperatorPlan getUserOperatorPlan() {
			return userOperatorPlan;
		}
		/**
		 * @param userOperatorPlan the userOperatorPlan to set
		 */
		public void setUserOperatorPlan(LaoUserOperatorPlan userOperatorPlan) {
			this.userOperatorPlan = userOperatorPlan;
		}
		
	}
	
}
