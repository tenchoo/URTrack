package com.urt.service.unicom;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.xml.wss.XWSSecurityException;
import com.urt.Ability.unicom.service.EditNetworkAccessConfigService;
import com.urt.Ability.unicom.service.EditTerminalRatingService;
import com.urt.Ability.unicom.service.EditTerminalService;
import com.urt.Ability.unicom.service.GetNetworkAccessConfigService;
import com.urt.Ability.unicom.service.GetTerminalDetailsService;
import com.urt.Ability.unicom.service.GetTerminalRatingService;
import com.urt.Ability.unicom.service.QueueTerminalRatePlanService;
import com.urt.Ability.unicom.service.RemoveRatePlanFromQueueService;
import com.urt.Ability.unicom.vo.AccessConfig;
import com.urt.Ability.unicom.vo.EditNetworkAccessConfigResponse;
import com.urt.Ability.unicom.vo.EditTerminalRatingResponse;
import com.urt.Ability.unicom.vo.EditTerminalResponse;
import com.urt.Ability.unicom.vo.GetNetworkAccessConfigResponse;
import com.urt.Ability.unicom.vo.GetTerminalRatingResponse;
import com.urt.Ability.unicom.vo.NowRatePlanResponse;
import com.urt.Ability.unicom.vo.QueueTerminalRatePlanResponse;
import com.urt.Ability.unicom.vo.TerminalRatingDetail;
import com.urt.po.IccidDonateStep;
import com.urt.po.IccidLibrary;
import com.urt.po.TariffPlan;
import com.urt.po.UserInfo;
/**
 * 
 * 赠送流量正常流程
 * @author zhaoyf
 *
 */
@Service("tariffPlanNormalService")
@Transactional
public class TariffPlanNormalService {
	private static Log log = LogFactory.getLog(TariffPlanNormalService.class);

	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private GetNetworkAccessConfigService getNetworkAccessConfigService;
	@Autowired
	private EditNetworkAccessConfigService editNetworkAccessConfigService;
	@Autowired
	private GetTerminalRatingService getTerminalRatingService;
	@Autowired
	private GetTerminalDetailsService getTerminalDetailsService;
	@Autowired
	private EditTerminalService editTerminalService;
	@Autowired
	private RemoveRatePlanFromQueueService removeRatePlanFromQueueService;
	@Autowired
	private EditTerminalRatingService editTerminalRatingService;
	@Autowired
	private QueueTerminalRatePlanService queueTerminalRatePlanService;
	
	@Autowired
	private IccidLibraryService iccidLibraryService;
	@Autowired
	private ImeiLibraryService imeiLibraryService;
	@Autowired
	private IccidDonateStepService iccidDonateStepService;
	@Autowired
	private IccidLogService iccidLogService;
	
	
	
	/**默认资费计划**/
	private String DEFAULT_TERMINAL_RATE = "110WLW004085_MON-FIX_5120M-0S";
	
	/**500M**/
	private String GIVEN_THREE_MONTH_TERMINAL_RATE = "110WLW004085_PRE-IND_500M-0S";
	
	/** 500M **/
	public static String RATEPLAN_500M = "110WLW004085_PRE-IND_500M-0S";
	
	/**10G**/
	public static String RATEPLAN_10G = "110WLW004085	_PRE-IND_10240M-0S";
	
	/**1G**/
	public static String RATEPLAN_1G = "110WLW004085_PRE-IND_1024M";
	
	/**4G流量*/
	public static String RATEPLAN_4G = "110WLW004085_PRE-IND_4096M";
	
	/**赠送流量5M*/
	public static String RATEPLAN_5M = "110WLW004085_PRE-IND_5M";
	//赠送流量总额
	public static String TOTAL_48G = "48G"; 
	
	public static String TOTAL_44G = "44G"; 
	
	public static String TOTAL_24G = "24G"; 

	public static String TOTAL_20G = "20G"; 
	
	public static String TOTAL_12G = "12G"; 
	
	public static String TOTAL_11G = "11G"; 
	
	public static String TOTAL_8G = "8G"; 
	
	public static String TOTAL_4G = "4G";
	
	public static String TOTAL_3G = "3G"; 
		
	public static String TOTAL_1G = "1G";
	 	
	public static String TOTAL_5M = "5M"; 
	//流量充值计划
	public static String RATE_4G = "4G";
	
	public static String RATE_1G = "1G";
	//当前步骤
	public static Integer CURRENTSTEPT = 1; 
	//1. 赠送流量2. openInternet(切换apn为apn2)3. 充值
	public static Integer ACTION = 1;
		

	/**
	 * 获取当前的通信计划，调用联通
	 * 
	 * @param iccid
	 * @param realName TODO
	 * @param idNum TODO
	 * @param firstChange TODO
	 * @return
	 */
	public String getNowActiveTariffPlanName(String iccid, String deviceId,
			String imsi, String msisdn, String userName, String userId, String realName, String idNum, Integer firstChange ) {
		GetNetworkAccessConfigResponse response = null;
		AccessConfig accessConfig = null;
		try {
			List<String> list = new ArrayList<String>();
			list.add(iccid);
			SOAPMessage soapMessage = getNetworkAccessConfigService.call(list,
					userId);
			response = (GetNetworkAccessConfigResponse) getNetworkAccessConfigService
					.parseMessage(soapMessage);
		} catch (IOException | XWSSecurityException | SOAPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (response != null) {
			List<AccessConfig> list = response.getList();
			if (list != null && list.size() > 0)
				accessConfig = list.get(0);
		}
		if (accessConfig == null)
			return null;
		String nowApnName = accessConfig.getApnNameByNacid();
		if (nowApnName == null)
			return null;
		UserInfo userInfo = userInfoService.getUserInfoByIccidAndUserId(iccid,
				userId);
		Date now = new Date();
		if (userInfo != null) {
			if (!userInfo.getApntype().equalsIgnoreCase(nowApnName)) {
				userInfo.setModifydate(now);
				userInfo.setApntype(nowApnName);
				userInfoService.updateUserActiveStatus(iccid,
						userInfo.getUserid(), userInfo);
			}
		} else {
			userInfoService.saveUserInfo(nowApnName, deviceId, iccid, imsi,
					msisdn, userId, userName, realName, idNum, firstChange );
		}
		return nowApnName;

	}

	/**
	 * 激活用户赠送的三个月资费
	 * 
	 * @param sourceApn
	 * @param targetApn
	 * @return
	 */
	public boolean createUserDefaultTariffPlan(String sourceApn,
			String targetApn, String iccid, String userId, boolean isActiveOk) {
		
		UserInfo userInfo = userInfoService.getUserInfoByIccidAndUserId(iccid,userId);
		
		if (userInfo == null) {
			return false;
		}

//更新状态操作已移到open接口 (changAPN方法)
//		Date now = new Date();
//		userInfo.setModifyDate(now);
//		if (isActiveOk) {
//			userInfo.setApnType(targetApn);
//			userInfo.setApnType("apn2");
//			userInfo.setUserStatus(UserInfo.UserInfoStatus.ACTIVE
//					.getCodeValue());
//		} else {
//			userInfo.setUserStatus(UserInfo.UserInfoStatus.ACTIVE_FAIL
//					.getCodeValue());
//		}
//		userInfoService.updateUserActiveStatus(iccid, userId, userInfo); 
		TariffPlan tariffPlan = userInfoService.getDefaultTariffPlan(GIVEN_THREE_MONTH_TERMINAL_RATE);
		
		userInfoService.saveUserLinkedTariffPlan(iccid, userId,userInfo.getUsername(), tariffPlan, isActiveOk);
		
		return isActiveOk;
	}
	
	/**
	 * 白名单赠送流量
	 * @param iccid
	 * @param userId
	 * @return
	 */
	private boolean getWhiteListTraffic(String iccid,String userId){
		String stepId = UUID.randomUUID().toString();
		String totalFlowSize = TOTAL_5M;
		String plan = RATEPLAN_5M;
		Integer totalStep = Integer.parseInt(TOTAL_5M.substring(0,1))/Integer.parseInt(TOTAL_5M.substring(0,1))+1;
		Integer currentStep = CURRENTSTEPT;
		Integer remainStep = totalStep - currentStep;
		boolean res = false;
	
		try {

			log.info("--------白名单----用户：执行赠送5M start");
			SOAPMessage soapMessageEditTerminal = editTerminalService.call(iccid,userId, plan);
			EditTerminalResponse responseEditTerminal = (EditTerminalResponse) editTerminalService.parseMessage(soapMessageEditTerminal);	
			if(responseEditTerminal != null){
				log.info("白名单用户赠送5M成功");
				//日志
				iccidLogService.saveIccidLog(ACTION, 1, iccid, stepId, totalFlowSize, userId);//1执行成功，-1执行失败
				//步骤
				iccidDonateStepService.saveIccidDonateStep(stepId, userId, iccid, plan, totalFlowSize, totalStep, currentStep,remainStep);
				res = true;
			}else{
				log.info("白名单用户赠送5M失败");
				//日志
				iccidLogService.saveIccidLog(ACTION, -1, iccid, stepId, totalFlowSize, userId);//1执行成功，-1执行失败
				//步骤
				iccidDonateStepService.saveIccidDonateStep(stepId, userId, iccid, plan, totalFlowSize, totalStep, currentStep,remainStep);
				return false;
			}
			log.info("--------白名单-----用户：执行赠送5M end");
			log.info(" 队尾添加默认(包月)资费计划 start ");	
			
			
			iccidDonateStepService.updateStept(iccid, userId);
			
			SOAPMessage soapMessage = editTerminalRatingService.call(iccid, userId,DEFAULT_TERMINAL_RATE);
			EditTerminalRatingResponse responseEditTerminalRating = (EditTerminalRatingResponse) editTerminalRatingService.parseMessage(soapMessage);
			if (responseEditTerminalRating != null){
				log.info("队尾添加默认(包月)资费计划成功 ");
				//日志
				iccidLogService.saveIccidLog(ACTION, 1, iccid, stepId, totalFlowSize, userId);//1执行成功，-1执行失败
				//步骤
				iccidDonateStepService.updateSuccess(iccid,userId);
				res = true;

			}else{
				log.info("队尾添加默认(包月)资费计划失败!!!! ");
				//日志
				iccidLogService.saveIccidLog(ACTION, -1, iccid, stepId, totalFlowSize, userId);//1执行成功，-1执行失败
				//步骤
				iccidDonateStepService.updateFail(iccid,userId);
		
				return false;
			}				
			
			log.info(" 队尾添加默认(包月)资费计划 end ");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}
	

	/**
	 * 激活赠送流量
	 * 
	 * @param iccid
	 * @param targetApn 
	 * @return
	 */
	
	public boolean sendActiveRequest(String iccid, String userId,String username,String wtype) {
		
		boolean res = false;
		
		TariffPlan tariffPlan = userInfoService.getDefaultTariffPlan(GIVEN_THREE_MONTH_TERMINAL_RATE);
		if (tariffPlan == null)
			return false;
	//	boolean changeApnResult = this.changeAPNType("apn2", iccid, userId);
		try {
		//	if (changeApnResult) {
				//判断是否白名单
				boolean flag = userInfoService.isWhiteList(iccid, Integer.parseInt(wtype));
				
				if(flag){
					//白名单赠送5M流量 
					res = this.getWhiteListTraffic(iccid, userId);
					//更改数据库赠送状态
					UserInfo userInfo = userInfoService.getUserInfoByIccid(iccid);
					if(res){
						userInfo.setDonateflag(1);//修改状态，改为赠送成功
						userInfoService.updateDonateFlag(userInfo);
					}else{
						userInfo.setDonateflag(-1);//修改状态，改为赠送失败
						userInfoService.updateDonateFlag(userInfo);
					}
								
				}else{
					//根据iccid 判断 赠送流量的大小
					IccidLibrary iccidLibrary = iccidLibraryService.findIccidLibraryByIccid(iccid);
					
					if(null == iccidLibrary){
						log.info("赠送流量失败，非法iccid----------");
						return false;
					}
			
					if("MB".equals(iccidLibrary.getDevicetype()) && StringUtils.isBlank(iccidLibrary.getCardType()) && StringUtils.isBlank(iccidLibrary.getRate())){
						//赠送3G流量
						String deviceType = "MB";
						String totalFlowSize = TOTAL_3G;
						String plan = RATEPLAN_1G;
						Integer totalStep = 4;
						res =	getTraffic(deviceType,iccid, userId,totalFlowSize,plan,totalStep);
					}else if("MIFI".equals(iccidLibrary.getDevicetype()) && StringUtils.isBlank(iccidLibrary.getCardType()) && StringUtils.isBlank(iccidLibrary.getRate())) {
						//赠送12G流量
						String totalFlowSize = TOTAL_12G;
						String plan = RATEPLAN_4G;
						String deviceType = "MIFI";
						Integer totalStep = 4;
						res =	getTraffic(deviceType,iccid, userId,totalFlowSize,plan,totalStep);
					}else if("NB".equals(iccidLibrary.getDevicetype()) && StringUtils.isBlank(iccidLibrary.getCardType()) && StringUtils.isBlank(iccidLibrary.getRate())){
						//赠送48G流量
						String totalFlowSize = TOTAL_48G;
						String plan = RATEPLAN_4G;
						String deviceType = "NB";
						Integer totalStep = 13;
						res =	getTraffic(deviceType,iccid, userId,totalFlowSize,plan,totalStep);
					}else if("TB1".equals(iccidLibrary.getDevicetype()) && StringUtils.isBlank(iccidLibrary.getCardType()) && StringUtils.isBlank(iccidLibrary.getRate())){
						//赠送24G流量
						String totalFlowSize = TOTAL_24G;
						String plan = RATEPLAN_4G;
						String deviceType = "TB1";
						Integer totalStep = 7;
						res =	getTraffic(deviceType,iccid, userId,totalFlowSize,plan,totalStep);
					}else if("TB2".equals(iccidLibrary.getDevicetype()) && StringUtils.isBlank(iccidLibrary.getCardType()) && StringUtils.isBlank(iccidLibrary.getRate())){
						//赠送48G流量
						String totalFlowSize = TOTAL_48G;
						String plan = RATEPLAN_4G;
						String deviceType = "TB2";
						Integer totalStep = 13;
						res =	getTraffic(deviceType,iccid, userId,totalFlowSize,plan,totalStep);
					}else if("NB".equals(iccidLibrary.getDevicetype()) && "F12".equals(iccidLibrary.getRate()) && StringUtils.isBlank(iccidLibrary.getCardType())){
						//NB设备赠送12G流量套餐
						String totalFlowSize = TOTAL_12G;
						String plan = RATEPLAN_4G;
						//测试用5M
						//String plan = RATEPLAN_5M;
						Integer totalStep = 4;
						String deviceType = "NB-----------F-12G";
						res =	getTraffic(deviceType,iccid, userId,totalFlowSize,plan,totalStep);
					}else if("NB".equals(iccidLibrary.getDevicetype()) && "F04".equals(iccidLibrary.getRate()) && StringUtils.isBlank(iccidLibrary.getCardType())){
						//NB设备赠送4G流量套餐
						String totalFlowSize = TOTAL_4G;
						String plan = RATEPLAN_4G;
						//String plan = RATEPLAN_5M;
						Integer totalStep = 2;
						String deviceType = "NB-----------F-04G";
						res =	getTraffic(deviceType,iccid, userId,totalFlowSize,plan,totalStep);
					}else if("T01".equals(iccidLibrary.getRate()) && StringUtils.isBlank(iccidLibrary.getCardType())){
						//测试1G流量套餐
						String totalFlowSize = TOTAL_1G;
						String plan = RATEPLAN_1G;
						//String plan = RATEPLAN_5M;
						Integer totalStep = 2;
						String deviceType = "TEST-----------01G";
						res =	getTraffic(deviceType,iccid, userId,totalFlowSize,plan,totalStep);
					}else if("T04".equals(iccidLibrary.getRate()) && StringUtils.isBlank(iccidLibrary.getCardType())){
						//测试4G流量套餐
						String totalFlowSize = TOTAL_4G;
						String plan = RATEPLAN_4G;
						//String plan = RATEPLAN_5M;
						Integer totalStep = 2;
						String deviceType = "TEST-----------04G";
						res =	getTraffic(deviceType,iccid, userId,totalFlowSize,plan,totalStep);
					}else if("T08".equals(iccidLibrary.getRate()) && StringUtils.isBlank(iccidLibrary.getCardType())){
						//测试8G流量套餐
						String totalFlowSize = TOTAL_8G;
						String plan = RATEPLAN_4G;
						//String plan = RATEPLAN_5M;
						Integer totalStep = 3;
						String deviceType = "TEST-----------08G";
						res =	getTraffic(deviceType,iccid, userId,totalFlowSize,plan,totalStep);
					}else if("T20".equals(iccidLibrary.getRate()) && StringUtils.isBlank(iccidLibrary.getCardType())){
						//测试20G流量套餐
						String totalFlowSize = TOTAL_20G;
						String plan = RATEPLAN_4G;
						//String plan = RATEPLAN_5M;
						Integer totalStep = 6;
						String deviceType = "TEST-----------20G";
						res =	getTraffic(deviceType,iccid, userId,totalFlowSize,plan,totalStep);
					}else if("TB".equals(iccidLibrary.getDevicetype()) && "P12".equals(iccidLibrary.getRate()) && StringUtils.isBlank(iccidLibrary.getCardType())){
						//TB12G流量套餐
						String totalFlowSize = TOTAL_12G;
						String plan = RATEPLAN_1G;
						//String plan = RATEPLAN_5M;
						Integer totalStep = 13;
						String deviceType = "TB-----------P12G";
						res =	getTraffic(deviceType,iccid, userId,totalFlowSize,plan,totalStep);
					}else if("TB".equals(iccidLibrary.getDevicetype()) && "P48".equals(iccidLibrary.getRate()) && StringUtils.isBlank(iccidLibrary.getCardType())){
						//TB48G流量套餐
						String totalFlowSize = TOTAL_48G;
						String plan = RATEPLAN_4G;
						//String plan = RATEPLAN_5M;
						Integer totalStep = 13;
						String deviceType = "TB-----------48G";
						res =	getTraffic(deviceType,iccid, userId,totalFlowSize,plan,totalStep);
					}else if("A11".equals(iccidLibrary.getRate()) && StringUtils.isBlank(iccidLibrary.getCardType())){
						//自带1G赠送11G
						String totalFlowSize = TOTAL_11G;
						String plan = RATEPLAN_1G;
						//String plan = RATEPLAN_5M;
						Integer totalStep = 12;
						String deviceType = "add-----------11G";
						res =	getTrafficAdd(deviceType,iccid, userId,totalFlowSize,plan,totalStep);
					}else if("A44".equals(iccidLibrary.getRate()) && StringUtils.isBlank(iccidLibrary.getCardType())){
						//自带4G赠送44G
						String totalFlowSize = TOTAL_44G;
						String plan = RATEPLAN_4G;
						//String plan = RATEPLAN_5M;
						Integer totalStep = 12;
						String deviceType = "add-----------44G";
						res =	getTrafficAdd(deviceType,iccid, userId,totalFlowSize,plan,totalStep);
					}else{
						log.info("赠送流量失败，---非法设备类型-----");
					}
					//更改数据库赠送状态
					UserInfo userInfo = userInfoService.getUserInfoByIccid(iccid);
					if(res){
						userInfo.setDonateflag(1);//修改状态，改为赠送成功
						userInfoService.updateDonateFlag(userInfo);
					}else{
						userInfo.setDonateflag(-1);//修改状态，改为赠送失败
						userInfoService.updateDonateFlag(userInfo);
					}
				}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	/**
	 * 自带流量赠送流量
	 * @param deviceType
	 * @param iccid
	 * @param userId
	 * @param totalFlowSize
	 * @param plan
	 * @param totalStep
	 * @return
	 * @throws Exception
	 */
	private boolean getTrafficAdd(String deviceType, String iccid,
			String userId, String totalFlowSize, String plan, Integer totalStep) throws Exception {
		String stepId = UUID.randomUUID().toString();
		Integer currentStep = CURRENTSTEPT;	
		Integer remainStep = totalStep - currentStep;		
		boolean res = addRateExtra(stepId, userId, iccid, plan, totalFlowSize, totalStep, currentStep, deviceType, remainStep);
		
		return res;
	}

	/**
	 * 激活赠送流量
	 * @param deviceType
	 * @param iccid
	 * @param userId
	 * @param totalFlowSize
	 * @param plan
	 * @param totalStep
	 * @return
	 * @throws Exception
	 */
	private boolean getTraffic(String deviceType, String iccid, String userId,
			String totalFlowSize, String plan, Integer totalStep) throws Exception {
		String stepId = UUID.randomUUID().toString();
		Integer currentStep = CURRENTSTEPT;	
		Integer remainStep = totalStep - currentStep;		
		boolean res = addRate(stepId, userId, iccid, plan, totalFlowSize, totalStep, currentStep, deviceType, remainStep);
		return res;
	}

	/**
	 * 设备赠送流量
	 * @param iccid
	 * @param userId
	 * @param deviceType
	 * @param totalFlowSize
	 * @param plan
	 * @return
	 * @throws Exception
	 */
/*	public boolean getTraffic(String iccid,String userId, String deviceType, String totalFlowSize, String plan) throws Exception{
		String stepId = UUID.randomUUID().toString();
		if(10>Integer.parseInt(totalFlowSize)){
			Integer totalStep = Integer.parseInt(totalFlowSize.substring(0,2))/Integer.parseInt(plan.substring(0,1))+1;
			Integer currentStep = CURRENTSTEPT;	
			Integer remainStep = totalStep - currentStep;		
			boolean res = addRate(stepId, userId, iccid, plan, totalFlowSize, totalStep, currentStep, deviceType, remainStep);
			return res;
		}else{
			Integer totalStep = Integer.parseInt(totalFlowSize.substring(0,1))/Integer.parseInt(plan.substring(0,1))+1;
			Integer currentStep = CURRENTSTEPT;	
			Integer remainStep = totalStep - currentStep;		
			boolean res = addRate(stepId, userId, iccid, plan, totalFlowSize, totalStep, currentStep, deviceType, remainStep);
			return res;
		}
	}*/


	/**
	 * 
	 * @param stepId
	 * @param userId
	 * @param iccid
	 * @param plan
	 * @param totalFlowSize
	 * @param totalStep
	 * @param currentStep
	 * @param deviceType
	 * @param remainStep
	 * @return
	 * @throws Exception
	 */
	private boolean addRateExtra(String stepId, String userId, String iccid,
			String plan, String totalFlowSize, Integer totalStep,
			Integer currentStep, String deviceType, Integer remainStep) throws Exception {
		boolean flag = false;
		log.info("-####----非白名单---------自带流量赠送-----"+deviceType+"-----用户：执行赠送"+totalFlowSize+" start");	

			log.info("--------非白名单----自带流量赠送-----"+deviceType+"-----用户：editTerminalRatingService 执行赠送第1次"+plan+" start");
			//更新当前步骤
			SOAPMessage soapMessageEditTerminalRating = editTerminalRatingService.call(iccid, userId,plan);
			EditTerminalRatingResponse responseEditTerminalRating = (EditTerminalRatingResponse) editTerminalRatingService.parseMessage(soapMessageEditTerminalRating);
			if(responseEditTerminalRating != null){
				log.info(deviceType+"第一次赠送"+plan+"成功");
				//日志
				iccidLogService.saveIccidLog(ACTION, 1, iccid, stepId, totalFlowSize, userId);//1执行成功，-1执行失败
				//步骤
				iccidDonateStepService.saveIccidDonateStep(stepId, userId, iccid, plan, totalFlowSize, totalStep, currentStep,remainStep);
				flag = true;
			}else{
				log.info(deviceType+"第一次赠送"+plan+"失败");
				//日志
				iccidLogService.saveIccidLog(ACTION, -1, iccid, stepId, totalFlowSize, userId);//1执行成功，-1执行失败
				//步骤
				iccidDonateStepService.saveIccidDonateStep(stepId, userId, iccid, plan, totalFlowSize, totalStep, currentStep,remainStep);
				return false;
			}		
			log.info("--------非白名单----自带流量赠送-----"+deviceType+"-----用户：editTerminalRatingService执行赠送第1次"+plan+" end");
			
			//获得剩余充值数
			remainStep = iccidDonateStepService.getIccidDonateStepByIccidAndUserId(iccid, userId).getRemainstep();
			log.info("剩余步骤数为=============="+remainStep);
				log.info("---@@@@-----非白名单用户--自带流量赠送-----：执行赠送"+(remainStep-1)+"次"+plan+" start");
				for (int i = 1; i < remainStep; i++) {
					log.info("------赠送"+plan+"---queueTerminalRatePlanService-----第"+i+"次 ~~~~~~~start");
					iccidDonateStepService.updateStept(iccid, userId);	
					SOAPMessage soapMessageQueueTerminalRatePlan = queueTerminalRatePlanService.call(iccid,userId, plan);
					QueueTerminalRatePlanResponse responseQueueTerminalRatePlan = (QueueTerminalRatePlanResponse) queueTerminalRatePlanService.parseMessage(soapMessageQueueTerminalRatePlan);
					IccidDonateStep iccidDonateStepByStepId = iccidDonateStepService.getIccidDonateStepByIccidAndUserId(iccid, userId);
					currentStep = iccidDonateStepByStepId.getCurrentstep();
					if(responseQueueTerminalRatePlan != null){
						log.info(deviceType+"第"+currentStep+"次赠送"+plan+"成功");
						//日志
						iccidLogService.saveIccidLog(ACTION, 1, iccid, stepId, totalFlowSize, userId);//1执行成功，-1执行失败
						//步骤
						iccidDonateStepService.updateSuccess(iccid,userId);
						flag = true;
					}else{
						log.info(deviceType+"第"+currentStep+"次赠送"+plan+"失败");
						//日志
						iccidLogService.saveIccidLog(ACTION, -1, iccid, stepId, totalFlowSize, userId);//1执行成功，-1执行失败
						//步骤
						iccidDonateStepService.updateFail(iccid,userId);
						return false;
					}
					log.info("------赠送"+plan+"---queueTerminalRatePlanService-----第"+i+"次 ~~~~~~~end");
				}
				log.info("---@@@@-----非白名单用户--自带流量赠送-----：执行赠送"+(remainStep-1)+"次"+plan+" end");
				log.info("-####----非白名单----自带流量赠送-----"+deviceType+"-----用户：执行赠送"+totalFlowSize+" end");

				log.info(" 队尾添加默认(包月)资费计划 start ");
				iccidDonateStepService.updateStept(iccid, userId);	
				SOAPMessage soapMessage = queueTerminalRatePlanService.call(iccid, userId,DEFAULT_TERMINAL_RATE);
				QueueTerminalRatePlanResponse rateResponse = (QueueTerminalRatePlanResponse) queueTerminalRatePlanService.parseMessage(soapMessage);
				if (rateResponse != null){
					log.info(deviceType+"队尾添加默认(包月)资费计划成功 ");
					//日志
					iccidLogService.saveIccidLog(ACTION, 1, iccid, stepId, totalFlowSize, userId);	//1执行成功，-1执行失败	
					//步骤
					iccidDonateStepService.updateSuccess(iccid,userId);
					flag = true;
				}else{
					log.info(deviceType+"队尾添加默认(包月)资费计划失败!!!! ");
					//日志
					iccidLogService.saveIccidLog(ACTION, -1, iccid, stepId, totalFlowSize, userId);		//1执行成功，-1执行失败	
					//步骤
					iccidDonateStepService.updateFail(iccid,userId);			
					return false;
				}						
				log.info(" 队尾添加默认(包月)资费计划 end ");
				return flag;	
				
			}
	


	/**
	 * 
	 * @param stepId
	 * @param userId
	 * @param iccid
	 * @param plan
	 * @param totalFlowSize
	 * @param totalStep
	 * @param currentStep
	 * @param deviceType
	 * @param remainStep
	 * @return
	 * @throws Exception
	 */
	
	public boolean addRate(String stepId,String userId,String iccid,String plan,String totalFlowSize,Integer totalStep,Integer currentStep,String deviceType,Integer remainStep) throws Exception{

		boolean flag = false;
		log.info("-####----非白名单----"+deviceType+"-----用户：执行赠送"+totalFlowSize+" start");	
		log.info("--------非白名单----"+deviceType+"-----用户：editTerminalService 执行赠送第1次"+plan+" start");		
		SOAPMessage soapMessageEditTerminal = editTerminalService.call(iccid,userId, plan);
		EditTerminalResponse responseEditTerminal = (EditTerminalResponse) editTerminalService.parseMessage(soapMessageEditTerminal);	
		if(responseEditTerminal != null){
			log.info(deviceType+"第一次赠送"+plan+"成功");
			//日志
			iccidLogService.saveIccidLog(ACTION, 1, iccid, stepId, totalFlowSize, userId);//1执行成功，-1执行失败
			//步骤
			iccidDonateStepService.saveIccidDonateStep(stepId, userId, iccid, plan, totalFlowSize, totalStep, currentStep,remainStep);
			flag = true;
		}else{
			log.info(deviceType+"第一次赠送"+plan+"失败");
			//日志
			iccidLogService.saveIccidLog(ACTION, -1, iccid, stepId, totalFlowSize, userId);//1执行成功，-1执行失败
			//步骤
			iccidDonateStepService.saveIccidDonateStep(stepId, userId, iccid, plan, totalFlowSize, totalStep, currentStep,remainStep);
			return false;
		}
		log.info("--------非白名单----"+deviceType+"-----用户：editTerminalService执行赠送第1次"+plan+" end");
		
		//获得剩余充值数
		remainStep = iccidDonateStepService.getIccidDonateStepByIccidAndUserId(iccid, userId).getRemainstep();
		log.info("剩余步骤数为============"+remainStep);
		if(1<remainStep){
			log.info("--------非白名单----"+deviceType+"-----用户：editTerminalRatingService 执行赠送第2次"+plan+" start");
			//更新当前步骤
			iccidDonateStepService.updateStept(iccid, userId);	
			log.info("当前步骤++++++"+iccidDonateStepService.getIccidDonateStepByIccidAndUserId(iccid, userId).getCurrentstep());
			SOAPMessage soapMessageEditTerminalRating = editTerminalRatingService.call(iccid, userId,plan);
			EditTerminalRatingResponse responseEditTerminalRating = (EditTerminalRatingResponse) editTerminalRatingService.parseMessage(soapMessageEditTerminalRating);
			if(responseEditTerminalRating != null){
				log.info(deviceType+"第二次赠送"+plan+"成功");
				//日志
				iccidLogService.saveIccidLog(ACTION, 1, iccid, stepId, totalFlowSize, userId);//1执行成功，-1执行失败
				//步骤
				iccidDonateStepService.updateSuccess(iccid,userId);
				flag = true;
			}else{
				log.info(deviceType+"第二次赠送"+plan+"失败");
				//日志
				iccidLogService.saveIccidLog(ACTION, -1, iccid, stepId, totalFlowSize, userId);//1执行成功，-1执行失败
				//步骤
				iccidDonateStepService.updateFail(iccid,userId);
				return false;
			}		
			log.info("--------非白名单----"+deviceType+"-----用户：editTerminalRatingService执行赠送第2次"+plan+" end");
			
			//获得剩余充值数
			remainStep = iccidDonateStepService.getIccidDonateStepByIccidAndUserId(iccid, userId).getRemainstep();
			log.info("剩余步骤数为=============="+remainStep);
			if(1<remainStep){
				log.info("---@@@@-----非白名单用户：执行赠送"+(remainStep-1)+"次"+plan+" start");
				for (int i = 1; i < remainStep; i++) {
					log.info("------赠送"+plan+"---queueTerminalRatePlanService-----第"+i+"次 ~~~~~~~start");
					iccidDonateStepService.updateStept(iccid, userId);	
					SOAPMessage soapMessageQueueTerminalRatePlan = queueTerminalRatePlanService.call(iccid,userId, plan);
					QueueTerminalRatePlanResponse responseQueueTerminalRatePlan = (QueueTerminalRatePlanResponse) queueTerminalRatePlanService.parseMessage(soapMessageQueueTerminalRatePlan);
					IccidDonateStep iccidDonateStepByStepId = iccidDonateStepService.getIccidDonateStepByIccidAndUserId(iccid, userId);
					currentStep = iccidDonateStepByStepId.getCurrentstep();
					if(responseQueueTerminalRatePlan != null){
						log.info(deviceType+"第"+currentStep+"次赠送"+plan+"成功");
						//日志
						iccidLogService.saveIccidLog(ACTION, 1, iccid, stepId, totalFlowSize, userId);//1执行成功，-1执行失败
						//步骤
						iccidDonateStepService.updateSuccess(iccid,userId);
						flag = true;
					}else{
						log.info(deviceType+"第"+currentStep+"次赠送"+plan+"失败");
						//日志
						iccidLogService.saveIccidLog(ACTION, -1, iccid, stepId, totalFlowSize, userId);//1执行成功，-1执行失败
						//步骤
						iccidDonateStepService.updateFail(iccid,userId);
						return false;
					}
					log.info("------赠送"+plan+"---queueTerminalRatePlanService-----第"+i+"次 ~~~~~~~end");
				}
				log.info("---@@@@-----非白名单用户：执行赠送"+(remainStep-1)+"次"+plan+" end");
				log.info("-####----非白名单----"+deviceType+"-----用户：执行赠送"+totalFlowSize+" end");

				log.info(" 队尾添加默认(包月)资费计划 start ");
				iccidDonateStepService.updateStept(iccid, userId);	
				SOAPMessage soapMessage = queueTerminalRatePlanService.call(iccid, userId,DEFAULT_TERMINAL_RATE);
				QueueTerminalRatePlanResponse rateResponse = (QueueTerminalRatePlanResponse) queueTerminalRatePlanService.parseMessage(soapMessage);
				if (rateResponse != null){
					log.info(deviceType+"队尾添加默认(包月)资费计划成功 ");
					//日志
					iccidLogService.saveIccidLog(ACTION, 1, iccid, stepId, totalFlowSize, userId);	//1执行成功，-1执行失败	
					//步骤
					iccidDonateStepService.updateSuccess(iccid,userId);
					flag = true;
				}else{
					log.info(deviceType+"队尾添加默认(包月)资费计划失败!!!! ");
					//日志
					iccidLogService.saveIccidLog(ACTION, -1, iccid, stepId, totalFlowSize, userId);		//1执行成功，-1执行失败	
					//步骤
					iccidDonateStepService.updateFail(iccid,userId);			
					return false;
				}						
				log.info(" 队尾添加默认(包月)资费计划 end ");	
			}else{
				//赠送流量两次添加默认包月计划
				log.info(" 队尾添加默认(包月)资费计划 start ");
				iccidDonateStepService.updateStept(iccid, userId);		
				SOAPMessage soapMessage = queueTerminalRatePlanService.call(iccid, userId,DEFAULT_TERMINAL_RATE);
				QueueTerminalRatePlanResponse QrateResponse = (QueueTerminalRatePlanResponse) queueTerminalRatePlanService.parseMessage(soapMessage);
				if (QrateResponse != null){
					log.info("队尾添加默认(包月)资费计划成功 ");
					//日志
					iccidLogService.saveIccidLog(ACTION, 1, iccid, stepId, totalFlowSize, userId);//1执行成功，-1执行失败
					//步骤
					iccidDonateStepService.updateSuccess(iccid,userId);
					flag = true;
				}else{
					log.info("队尾添加默认(包月)资费计划失败!!!! ");
					//日志
					iccidLogService.saveIccidLog(ACTION, -1, iccid, stepId, totalFlowSize, userId);//1执行成功，-1执行失败
					//步骤
					iccidDonateStepService.updateFail(iccid,userId);			
					return false;
				}								
				log.info(" 队尾添加默认(包月)资费计划 end ");
			}		
		}else{
			//赠送流量一次添加默认包月计划
			log.info(" 队尾添加默认(包月)资费计划 start ");
			iccidDonateStepService.updateStept(iccid, userId);		
			SOAPMessage soapMessage = editTerminalRatingService.call(iccid, userId,DEFAULT_TERMINAL_RATE);
			EditTerminalRatingResponse responseEditTerminalRating = (EditTerminalRatingResponse) editTerminalRatingService.parseMessage(soapMessage);
			if (responseEditTerminalRating != null){
				log.info("队尾添加默认(包月)资费计划成功 ");
				//日志
				iccidLogService.saveIccidLog(ACTION, 1, iccid, stepId, totalFlowSize, userId);//1执行成功，-1执行失败
				//步骤
				iccidDonateStepService.updateSuccess(iccid,userId);
				flag = true;
			}else{
				log.info("队尾添加默认(包月)资费计划失败!!!! ");
				//日志
				iccidLogService.saveIccidLog(ACTION, -1, iccid, stepId, totalFlowSize, userId);//1执行成功，-1执行失败
				//步骤
				iccidDonateStepService.updateFail(iccid,userId);		
				return false;
			}							
			log.info(" 队尾添加默认(包月)资费计划 end ");
		}
		return flag;
	}
		
	/**
	 * 获取当前的资费计划相关信息，调用联通
	 * 
	 * @param iccid
	 */
	public NowRatePlanResponse getNowRatePlan(String iccid, String userId) {
		NowRatePlanResponse nowRatePlanResponse = null;
		DecimalFormat format = new DecimalFormat("0.000");
		try {
			SOAPMessage soapMessage = getTerminalRatingService.call(iccid,
					userId);
			GetTerminalRatingResponse response = (GetTerminalRatingResponse) getTerminalRatingService
					.parseMessage(soapMessage);
			if (response != null) {
				nowRatePlanResponse = new NowRatePlanResponse();
				List<TerminalRatingDetail> list = response.getList();
				if (list != null && list.size() > 0) {
					BigDecimal remain = new BigDecimal("0");
					for (TerminalRatingDetail detail : list) {
						String queue = detail.getQueuePosition();
						String dataRemaining = detail.getDataRemaining();
						if (StringUtils.isBlank(dataRemaining)) {
							dataRemaining = "0";
						}
						BigDecimal decimal = new BigDecimal(dataRemaining);
						if ("0".equals(queue)) {
							nowRatePlanResponse.setExpirationDate(detail
									.getExpirationDate());
							nowRatePlanResponse.setRatePlanName(detail
									.getRatePlanName());
							nowRatePlanResponse.setTermLength(detail
									.getTermLength());
							nowRatePlanResponse.setDataRemaining(format
									.format(decimal.doubleValue()));
						}

						remain = remain.add(decimal);
					}

					nowRatePlanResponse.setTotalCapacithy(format.format(remain
							.doubleValue()));
				}
				// nowRatePlanResponse.setUserLinkedTariffPlanId(111111l);//1111
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nowRatePlanResponse;
	}

	public static void main(String[] args) {
		BigDecimal remain = new BigDecimal("0");
		BigDecimal decimal1 = new BigDecimal("0");
		remain = remain.add(decimal1);
		BigDecimal decimal2 = new BigDecimal("0");
		remain = remain.add(decimal2);

		DecimalFormat ndf = new DecimalFormat("0.000");
		System.out.println(ndf.format(remain.doubleValue()));
	}

	/**
	 * 统计本地的剩余流量
	 * 
	 * @param iccid
	 * @param userId
	 * @param nowRatePlanResponse
	 * @return
	 */
	// public BigDecimal getUserRemainTotalCapacity(String iccid, String userId,
	// NowRatePlanResponse nowRatePlanResponse) {
	// List<UserLinkedTariffPlan> linkedList =
	// userInfoService.geAvailabletLinkedTariffPlan(nowRatePlanResponse.getUserLinkedTariffPlanId(),
	// iccid, userId);
	// BigDecimal totalCapacity = BigDecimal.ZERO;
	// if(linkedList != null)
	// for(UserLinkedTariffPlan userLinkedTariffPlan : linkedList) {
	// totalCapacity =
	// totalCapacity.add(userLinkedTariffPlan.getTariffPlanCapacity());
	// }
	// totalCapacity = totalCapacity.add(new
	// BigDecimal(nowRatePlanResponse.getDataRemaining()));
	// return totalCapacity;
	// }
	/**
	 * 修改通信计划，上线上网的开关，这里调用联通
	 * 
	 * @param nowApn
	 * @param targetApn
	 * @param iccid
	 * @return
	 */
	private boolean changeAPNType(String targetApn, String iccid, String userId) {
		String targetNacid = AccessConfig.getNacidByapnname(targetApn);
		log.info("changeApn-----targetNacid:   " + targetNacid);
		if (targetNacid == null)
			return false;
		try {
			Long beginTime = System.currentTimeMillis();
			SOAPMessage soapMessage = editNetworkAccessConfigService.call(
					iccid, userId, targetNacid);
			log.info("changeAPN cost: "
					+ (System.currentTimeMillis() - beginTime) / 1000.0);
			EditNetworkAccessConfigResponse response = (EditNetworkAccessConfigResponse) editNetworkAccessConfigService
					.parseMessage(soapMessage);
			if (response != null)
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 打开上网开关
	 * 
	 * @param iccid
	 * @param userId
	 * @return
	 */
	public boolean openInternet(String iccid, String userId) {
		UserInfo userInfo = userInfoService.getUserInfoByIccidAndUserId(iccid,
				userId);
		if (userInfo == null) {
			return false;
		}

		boolean isChangeOk = changeAPNType("apn2", iccid, userId);

		if (isChangeOk) {
			userInfo.setApntype("apn2");
			userInfo.setUserstatus(UserInfo.UserInfoStatus.ACTIVE.getCodeValue());//设置为已激活
			userInfo.setModifydate(new Date());
			userInfoService.updateUserActiveStatus(iccid, userId, userInfo);
		}else{
			userInfo.setUserstatus(UserInfo.UserInfoStatus.ACTIVE_FAIL.getCodeValue());//设置为激活失败
			userInfo.setModifydate(new Date());
			userInfoService.updateUserActiveStatus(iccid, userId, userInfo);
		}
		
		return isChangeOk;
	}

	/**
	 * 关闭上网服务
	 * 
	 * @param iccid
	 * @param userId
	 * @return
	 */
	public boolean closeInternet(String iccid, String userId) {
		UserInfo userInfo = userInfoService.getUserInfoByIccidAndUserId(iccid,
				userId);
		if (userInfo == null) {
			return false;
		}
		boolean isChangeOk = changeAPNType("apn1", iccid, userId);
		if (isChangeOk) {
			userInfo.setApntype("apn1");
			userInfo.setModifydate(new Date());
			userInfoService.updateUserActiveStatus(iccid, userId, userInfo);
		}
		return isChangeOk;
	}

	public Map<String, String> getRatePlan(String iccid, String userId) {
		Map<String, String> map = new HashMap<String, String>();
		String expirationDate = "";
		String dataRemaining = "0";
		try {
			SOAPMessage soapMessage = getTerminalRatingService.call(iccid,
					userId);
			GetTerminalRatingResponse response = (GetTerminalRatingResponse) getTerminalRatingService
					.parseMessage(soapMessage);
			if (response != null) {
				DecimalFormat format = new DecimalFormat("0.000");
				List<TerminalRatingDetail> list = response.getList();
				if (list != null && list.size() > 0) {
					BigDecimal remain = new BigDecimal(dataRemaining);
					for (TerminalRatingDetail detail : list) {
						String queue = detail.getQueuePosition();
						String addValue = detail.getDataRemaining();
						if (StringUtils.isBlank(addValue)) {
							addValue = "0";
						}
						BigDecimal decimal = new BigDecimal(addValue);
						if ("0".equals(queue)
								&& StringUtils.isNotBlank(detail
										.getExpirationDate())) {
							expirationDate = detail.getExpirationDate();
						}
						remain = remain.add(decimal);
						System.out.println("remain:" + remain.doubleValue());
					}
					dataRemaining = format.format(remain.doubleValue());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("expirationDate", expirationDate);
		map.put("dataRemaining", dataRemaining);
		return map;
	}

	/**
	 * 接收联通的资费计划变更的通知消息
	 * 
	 * @param iccid
	 * @param apnType
	 *            //
	 */
	// public void changeApnByUnionNotify(String iccid, String apnType) {
	// UserInfo userInfo = userInfoService.getUserInfoByIccid(iccid);
	// if(userInfo != null) {
	// userInfo.setApnType(apnType);
	// userInfo.setModifyDate(new Date());
	// userInfoService.updateUserActiveStatus(iccid, userInfo.getUserId(),
	// userInfo);
	// }else {
	// System.out.println("changeApnByUnionNotify---error: " + iccid);
	// }
	// }
	
	/**
	 * 删除用户资费计划
	 * @param iccid
	 */
	public void removeRatePlan(String iccid){
		try {
			String random = RandomStringUtils.random(6, true, true);
			SOAPMessage soapMessage = getTerminalRatingService.call(iccid, random);
			GetTerminalRatingResponse response = (GetTerminalRatingResponse) getTerminalRatingService.parseMessage(soapMessage);
			if (response != null && response.getList().size() > 0) {
				for (TerminalRatingDetail detail : response.getList()) {
					int queue = Integer.parseInt(detail.getQueuePosition());
					String ratePlanName = detail.getRatePlanName();
					if (queue > 0 && !DEFAULT_TERMINAL_RATE.equals(ratePlanName)) {
						removeRatePlanFromQueueService.call(iccid,random,"1",RATEPLAN_4G);
					}
				}
			}
		} catch (IOException | XWSSecurityException | SOAPException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 删除默认包月计划
	 * @param iccid
	 */
	public void removeDefaultRatePlan(String iccid){
		try {
			String random = RandomStringUtils.random(6, true, true);
			SOAPMessage soapMessage = getTerminalRatingService.call(iccid, random);
			GetTerminalRatingResponse response = (GetTerminalRatingResponse) getTerminalRatingService.parseMessage(soapMessage);
			if (response != null && response.getList().size() > 0) {
						int pos = response.getList().size()-1;
						removeRatePlanFromQueueService.call(iccid,random,pos+"",DEFAULT_TERMINAL_RATE);
			}
		} catch (IOException | XWSSecurityException | SOAPException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 增加指定资费计划
	 * 
	 * @param iccid
	 */
	public void addRatePlan(String iccid, String userId, String ratePlan) {
		try {
			String random = RandomStringUtils.random(6, true, true);
			SOAPMessage soapMessage = getTerminalRatingService.call(iccid, random);
			GetTerminalRatingResponse response = (GetTerminalRatingResponse) getTerminalRatingService.parseMessage(soapMessage);
			if(null != response && null != response.getList()){
				int planSize =  response.getList().size();
				if(planSize==1){
					//如果只有一个资费计划,也就是默认资费计划
					TerminalRatingDetail detail = response.getList().get(0);
					if(DEFAULT_TERMINAL_RATE.equals(detail.getRatePlanName())){
						//修改第0个
						editTerminalService.call(iccid, userId, ratePlan);
					}else{
						//修改第1个
						editTerminalRatingService.call(iccid, userId, ratePlan);
					}
				}else{
					queueTerminalRatePlanService.call(iccid, userId, ratePlan);
				}
				
			}
		
		} catch (IOException | XWSSecurityException | SOAPException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 追加默认资费计划
	 * 
	 * @param iccid
	 */
	public void addDefaultRatePlan(String iccid, String userId) {
		try {
			
			String random = RandomStringUtils.random(6, true, true);
			SOAPMessage soapMessage = getTerminalRatingService.call(iccid, random);
			GetTerminalRatingResponse response = (GetTerminalRatingResponse) getTerminalRatingService.parseMessage(soapMessage);
			if(null != response && null != response.getList()){
				int planSize =  response.getList().size();
				if(planSize==1){
					//只有第0个位置有资费计划的时候，用editTerminalRatingService修改成默认包月计划
					editTerminalRatingService.call(iccid, userId, DEFAULT_TERMINAL_RATE);
				}else{
					queueTerminalRatePlanService.call(iccid, userId, DEFAULT_TERMINAL_RATE);
				}
			}
			
		} catch (IOException | XWSSecurityException | SOAPException e) {
			e.printStackTrace();
		}
	}


}
