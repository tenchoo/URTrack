package com.urt.service.unicom;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.urt.Ability.unicom.service.GetTerminalUsageDataDetailsService;
import com.urt.Ability.unicom.service.QueueTerminalRatePlanService;
import com.urt.Ability.unicom.service.RemoveRatePlanFromQueueService;
import com.urt.Ability.unicom.vo.AccessConfig;
import com.urt.Ability.unicom.vo.EditNetworkAccessConfigResponse;
import com.urt.Ability.unicom.vo.EditTerminalRatingResponse;
import com.urt.Ability.unicom.vo.GetNetworkAccessConfigResponse;
import com.urt.Ability.unicom.vo.GetTerminalRatingResponse;
import com.urt.Ability.unicom.vo.GetTerminalUsageDataDetailsResponse;
import com.urt.Ability.unicom.vo.NowRatePlanResponse;
import com.urt.Ability.unicom.vo.QueueTerminalRatePlanResponse;
import com.urt.Ability.unicom.vo.TerminalRatingDetail;
import com.urt.Ability.unicom.vo.TerminalUsageResponse;
import com.urt.Ability.unicom.vo.UsageDetail;
import com.urt.po.IccidLibrary;
import com.urt.po.TariffPlan;
import com.urt.po.UserInfo;

@Service("tariffPlanService")
@Transactional
public class TariffPlanService {
	private static Log log = LogFactory.getLog(TariffPlanService.class);

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
	private GetTerminalUsageDataDetailsService getTerminalUsageDataDetailsService;
	@Autowired
	private IccidLibraryService iccidLibraryService;
	@Autowired
	private ImeiLibraryService imeiLibraryService;
	
	
	
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
			String imsi, String msisdn, String userName, String userId, String realName, String idNum, Integer firstChange) {
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
					msisdn, userId, userName, realName, idNum, firstChange);
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
	
	
	
	private boolean getWhiteListTraffic(String iccid,String userId){
		
		boolean res = false;
		
		try {

			log.info("--------白名单----用户：执行赠送5M start");
			editTerminalService.call(iccid, userId, RATEPLAN_5M);
			log.info("--------白名单-----用户：执行赠送5M end");

			log.info(" --白名单用户-队尾添加默认(包月)资费计划 start ");
			SOAPMessage soapMessage = editTerminalRatingService.call(iccid,userId, DEFAULT_TERMINAL_RATE);
			EditTerminalRatingResponse rateResponse = (EditTerminalRatingResponse) editTerminalRatingService.parseMessage(soapMessage);
			if (rateResponse != null) {
				log.info("--白名单用户 队尾添加默认(包月)资费计划 end ");
				res = true;
			} else {
				log.info(" --白名单用户 队尾添加默认(包月)资费计划 失败!!!! ");
				res = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}
	

	/**
	 * 激活赠送流量
	 * 
	 * @param iccid
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
				
				}else{
					//根据iccid 判断 赠送流量的大小
					IccidLibrary iccidLibrary = 	iccidLibraryService.findIccidLibraryByIccid(iccid);
					
					if(null == iccidLibrary){
						log.info("赠送流量失败，非法iccid----------");
						return false;
					}
					
					//如果是mb,手机端
					if("MB".equals(iccidLibrary.getDevicetype())){
						//赠送3G流量
						res =	getPhoneTraffic(iccid, userId);
					}else if("MIFI".equals(iccidLibrary.getDevicetype())) {
						//赠送12G流量
						res = 	getMIFITraffic(iccid, userId);
					}else if("NB".equals(iccidLibrary.getDevicetype())){
						//赠送48G流量
						res =	getPCTraffic(iccid, userId);
					}else if("TB1".equals(iccidLibrary.getDevicetype())){
						//赠送24G流量
						res =	getTB1Traffic(iccid, userId);
					}else if("TB2".equals(iccidLibrary.getDevicetype())){
						//赠送48G流量
						res =	getTB2Traffic(iccid, userId);
					}else{
						log.info("赠送流量失败，---非法设备类型-----");
					}
				}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	/**
	 * 激活后赠送48G流量
	 */
	public boolean getPCTraffic(String iccid,String userId){
		
		boolean res = false;
		
		try {
		
				log.info("-####----非白名单----NB-----用户：执行赠送48G start");
				//队列 0
				log.info("--------非白名单用户：editTerminalService 执行赠送第1次4G start");
				editTerminalService.call(iccid,userId, RATEPLAN_4G);
				log.info("--------非白名单用户：editTerminalService执行赠送第2次4G end");
				//队列 1
				log.info("--------非白名单用户：editTerminalRatingService 执行赠送第2次4G start");
				editTerminalRatingService.call(iccid, userId,RATEPLAN_4G);
				log.info("--------非白名单用户：editTerminalRatingService执行赠送第2次4G end");
				
				log.info("---@@@@-----非白名单用户：执行赠送10次4G start");
				for (int i = 0; i < 10; i++) {
					log.info("------赠送4G---queueTerminalRatePlanService-----第"+i+"次 ~~~~~~~start");
					queueTerminalRatePlanService.call(iccid,userId, RATEPLAN_4G);
					log.info("------赠送4G---queueTerminalRatePlanService-----第"+i+"次 ~~~~~~~end");
				}
				log.info("-----@@@@---非白名----NB-----单用户：执行赠送10次4G end");
				
				log.info("----####----非白名单----NB-----用户：执行赠送48G end");
				
				
				log.info(" 队尾添加默认(包月)资费计划 start ");
				SOAPMessage soapMessage = queueTerminalRatePlanService.call(iccid, userId,DEFAULT_TERMINAL_RATE);
				QueueTerminalRatePlanResponse rateResponse = (QueueTerminalRatePlanResponse) queueTerminalRatePlanService.parseMessage(soapMessage);
				if (rateResponse != null){
					log.info(" 队尾添加默认(包月)资费计划 end ");
					res = true;
				}else{
					log.info(" 队尾添加默认(包月)资费计划 失败!!!! ");
					res = false;
				}
				
		
		} catch (IOException | XWSSecurityException | SOAPException e) {
			e.printStackTrace();
		}
		
		return res;
		
	}
	
	/**
	 * 激活后赠送24G流量
	 */
	public boolean getTB1Traffic(String iccid,String userId){
		
		boolean res = false;
		
		try {
			
			log.info("-####----非白名单----TB1-----用户：执行赠送24G start");
			//队列 0
			log.info("--------非白名单用户：editTerminalService 执行赠送第1次4G start");
			editTerminalService.call(iccid,userId, RATEPLAN_4G);
			log.info("--------非白名单用户：editTerminalService执行赠送第2次4G end");
			//队列 1
			log.info("--------非白名单用户：editTerminalRatingService 执行赠送第2次4G start");
			editTerminalRatingService.call(iccid, userId,RATEPLAN_4G);
			log.info("--------非白名单用户：editTerminalRatingService执行赠送第2次4G end");
			
			log.info("---@@@@-----非白名单用户：执行赠送4次4G start");
			for (int i = 0; i < 4; i++) {
				log.info("------赠送4G---queueTerminalRatePlanService-----第"+i+"次 ~~~~~~~start");
				queueTerminalRatePlanService.call(iccid,userId, RATEPLAN_4G);
				log.info("------赠送4G---queueTerminalRatePlanService-----第"+i+"次 ~~~~~~~end");
			}
			log.info("-----@@@@---非白名----TB1-----单用户：执行赠送4次4G end");
			
			log.info("----####----非白名单----TB1-----用户：执行赠送24G end");
			
			
			log.info(" 队尾添加默认(包月)资费计划 start ");
			SOAPMessage soapMessage = queueTerminalRatePlanService.call(iccid, userId,DEFAULT_TERMINAL_RATE);
			QueueTerminalRatePlanResponse rateResponse = (QueueTerminalRatePlanResponse) queueTerminalRatePlanService.parseMessage(soapMessage);
			if (rateResponse != null){
				log.info(" 队尾添加默认(包月)资费计划 end ");
				res = true;
			}else{
				log.info(" 队尾添加默认(包月)资费计划 失败!!!! ");
				res = false;
			}
			
			
		} catch (IOException | XWSSecurityException | SOAPException e) {
			e.printStackTrace();
		}
		
		return res;
		
	}
	/**
	 * 激活后赠送48G流量
	 */
	public boolean getTB2Traffic(String iccid,String userId){
		
		boolean res = false;
		
		try {
			
			log.info("-####----非白名单----TB2-----用户：执行赠送48G start");
			//队列 0
			log.info("--------非白名单用户：editTerminalService 执行赠送第1次4G start");
			editTerminalService.call(iccid,userId, RATEPLAN_4G);
			log.info("--------非白名单用户：editTerminalService执行赠送第2次4G end");
			//队列 1
			log.info("--------非白名单用户：editTerminalRatingService 执行赠送第2次4G start");
			editTerminalRatingService.call(iccid, userId,RATEPLAN_4G);
			log.info("--------非白名单用户：editTerminalRatingService执行赠送第2次4G end");
			
			log.info("---@@@@-----非白名单用户：执行赠送10次4G start");
			for (int i = 0; i < 10; i++) {
				log.info("------赠送4G---queueTerminalRatePlanService-----第"+i+"次 ~~~~~~~start");
				queueTerminalRatePlanService.call(iccid,userId, RATEPLAN_4G);
				log.info("------赠送4G---queueTerminalRatePlanService-----第"+i+"次 ~~~~~~~end");
			}
			log.info("-----@@@@---非白名----TB2-----单用户：执行赠送10次4G end");
			
			log.info("----####----非白名单----TB2-----用户：执行赠送48G end");
			
			
			log.info(" 队尾添加默认(包月)资费计划 start ");
			SOAPMessage soapMessage = queueTerminalRatePlanService.call(iccid, userId,DEFAULT_TERMINAL_RATE);
			QueueTerminalRatePlanResponse rateResponse = (QueueTerminalRatePlanResponse) queueTerminalRatePlanService.parseMessage(soapMessage);
			if (rateResponse != null){
				log.info(" 队尾添加默认(包月)资费计划 end ");
				res = true;
			}else{
				log.info(" 队尾添加默认(包月)资费计划 失败!!!! ");
				res = false;
			}
			
			
		} catch (IOException | XWSSecurityException | SOAPException e) {
			e.printStackTrace();
		}
		
		return res;
		
	}
	
	/**
	 * MIFI版激活后赠送12G流量
	 */
	public boolean getMIFITraffic(String iccid,String userId){
		
		boolean res = false;
		
		try {
		
				log.info("-####----非白名单----MIFI-----用户：执行赠送12G start");
				//队列 0
				log.info("--------非白名单----MIFI-----用户：editTerminalService 执行赠送第1次4G start");
				editTerminalService.call(iccid,userId, RATEPLAN_4G);
				log.info("--------非白名单----MIFI-----用户：editTerminalService执行赠送第2次4G end");
				//队列 1
				log.info("--------非白名单----MIFI-----用户：editTerminalRatingService 执行赠送第2次4G start");
				editTerminalRatingService.call(iccid, userId,RATEPLAN_4G);
				log.info("--------非白名单----MIFI-----用户：editTerminalRatingService执行赠送第2次4G end");
			
				log.info("------赠送4G---queueTerminalRatePlanService-----第1次 ~~~~~~~start");
				queueTerminalRatePlanService.call(iccid,userId, RATEPLAN_4G);
				log.info("------赠送4G---queueTerminalRatePlanService-----第1次 ~~~~~~~end");
				
				log.info("----####----非白名单----MIFI-----用户：执行赠送12G end");
				
				log.info(" 队尾添加默认(包月)资费计划 start ");
				SOAPMessage soapMessage = queueTerminalRatePlanService.call(iccid, userId,DEFAULT_TERMINAL_RATE);
				QueueTerminalRatePlanResponse rateResponse = (QueueTerminalRatePlanResponse) queueTerminalRatePlanService.parseMessage(soapMessage);
				if (rateResponse != null){
					log.info(" 队尾添加默认(包月)资费计划 end ");
					res = true;
				}else{
					log.info(" 队尾添加默认(包月)资费计划 失败!!!! ");
					res = false;
				}
				
			
		} catch (IOException | XWSSecurityException | SOAPException e) {
			e.printStackTrace();
		}
		
		return res;
		
	}
	/**
	 * 手机版激活后赠送3G流量
	 */
	public boolean getPhoneTraffic(String iccid,String userId){
		
		boolean res = false;
		
		try {
		
				log.info("-####----非白名单----MB-----用户：执行赠送3G start");
				//队列 0
				log.info("--------非白名单----MB-----用户：editTerminalService 执行赠送第1次1G start");
				editTerminalService.call(iccid,userId, RATEPLAN_1G);
				log.info("--------非白名单----MB-----用户：editTerminalService执行赠送第1次1G end");
				//队列 1
				log.info("--------非白名单----MB-----用户：editTerminalRatingService 执行赠送第2次1G start");
				editTerminalRatingService.call(iccid, userId,RATEPLAN_1G);
				log.info("--------非白名单----MB-----用户：editTerminalRatingService执行赠送第2次1G end");
				
				log.info("------赠送1G---queueTerminalRatePlanService-----第1次 ~~~~~~~start");
				queueTerminalRatePlanService.call(iccid,userId, RATEPLAN_1G);
				log.info("------赠送1G---queueTerminalRatePlanService-----第1次 ~~~~~~~end");
				
				log.info("----####----非白名单----MB-----用户：执行赠送3G end");
				
				log.info(" 队尾添加默认(包月)资费计划 start ");
				SOAPMessage soapMessage = queueTerminalRatePlanService.call(iccid, userId,DEFAULT_TERMINAL_RATE);
				QueueTerminalRatePlanResponse rateResponse = (QueueTerminalRatePlanResponse) queueTerminalRatePlanService.parseMessage(soapMessage);
				if (rateResponse != null){
					log.info(" 队尾添加默认(包月)资费计划 end ");
					res = true;
				}else{
					log.info(" 队尾添加默认(包月)资费计划 失败!!!! ");
					res = false;
				}
		
		} catch (IOException | XWSSecurityException | SOAPException e) {
			e.printStackTrace();
		}
		
		return res;
		
	}
	
	/**
	 * 资费详情
	 * @param iccid
	 * @param userId
	 * @param pageNumber
	 * @return
	 * @throws ReflectiveOperationException
	 */
	public List<TerminalUsageResponse> getTerminalUsageDataDetails(String iccid,String userId,String pageNumber)throws ReflectiveOperationException{

//		Map<String, String> map = new HashMap<String, String>();
		List<TerminalUsageResponse> listDetail = new ArrayList<TerminalUsageResponse>();
		TerminalUsageResponse terminalUsageResponse = null;
		String totalPages = "";
		String dataVolume = "";
		String billable = "";					
		String countryCode = "";
		String customer = "";
		String cycleStartDate = "";						
		String duration = "";
		String endConsumerId = "";
		String serviceType = "";
		String sessionStartTime = "";
		String terminalId = "";
		String zone = "";
		try {
			SOAPMessage soapMessage = getTerminalUsageDataDetailsService.call(iccid,userId,pageNumber);
			GetTerminalUsageDataDetailsResponse response = (GetTerminalUsageDataDetailsResponse) getTerminalUsageDataDetailsService.parseMessage(soapMessage);
			if(response != null){
				totalPages = response.getTotalPages();
				DecimalFormat format = new DecimalFormat("0.000");
				List<UsageDetail> list = response.getList();
				log.info("接收到的集合=========== "+list.size());
				if(list != null && list.size() > 0){					
					for (UsageDetail detail : list) {	
						terminalUsageResponse = new TerminalUsageResponse();
						dataVolume = detail.getDataVolume();
						BigDecimal remain = new BigDecimal(dataVolume);
						dataVolume = format.format(remain.doubleValue());
					    billable = detail.getBillable();						
						countryCode = detail.getCountryCode();
						customer = detail.getCustomer();
						cycleStartDate = detail.getCycleStartDate();						
						duration = detail.getDuration();
						endConsumerId = detail.getEndConsumerId();
						serviceType = detail.getServiceType();
						sessionStartTime = detail.getSessionStartTime();
						terminalId = detail.getTerminalId();
						zone = detail.getZone();
/*						map.put("totalPages", totalPages);
						map.put("dataVolume", dataVolume);
						map.put("billable", billable);
						map.put("countryCode", countryCode);
						map.put("customer", customer);
						map.put("cycleStartDate", cycleStartDate);
						map.put("duration", duration);
						map.put("endConsumerId", endConsumerId);
						map.put("serviceType", serviceType);
						map.put("sessionStartTime", sessionStartTime);
						map.put("terminalId", terminalId);
						map.put("zone", zone);*/
						terminalUsageResponse.setBillable(billable);
						terminalUsageResponse.setCountryCode(countryCode);
						terminalUsageResponse.setCustomer(customer);
						terminalUsageResponse.setCycleStartDate(cycleStartDate.replace("Z", ""));
						terminalUsageResponse.setDataVolume(dataVolume);
						terminalUsageResponse.setDuration(toMinutes(duration));
						terminalUsageResponse.setEndConsumerId(endConsumerId);
						terminalUsageResponse.setIccid(iccid);
						terminalUsageResponse.setServiceType(serviceType);
						terminalUsageResponse.setSessionStartTime(sessionStartTime.replace("T", " ").replace(".000Z", ""));
						terminalUsageResponse.setTerminalId(terminalId);
						terminalUsageResponse.setTotalPages(totalPages);
						terminalUsageResponse.setZone(zone);
						listDetail.add(terminalUsageResponse);
						
						
					}	
					
				}
	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		log.info("zou  listDetail"+listDetail.size());
		return listDetail;
		
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
		
		int time = 12126;
		time = time/60;
		int s = 12126 - time*60;
		System.out.println(time+""+s);
		
		NowRatePlanResponse nowRatePlanResponse = new TariffPlanService().getNowRatePlan("8986061501000889177","10070840727");
		System.out.println(nowRatePlanResponse);
		
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
	
	/**
	 * 查询剩余流量
	 * @param iccid
	 * @param userId
	 * @return
	 */
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
	
	/**
	 * 将得到的时间转换成分钟
	 * 如12126  ==》 202:06
	 * @param time
	 * @return
	 */
	public static String toMinutes(String day){
		int time= Integer.parseInt(day);
		int minute = time/60;
		time = time - (minute * 60);
		if(time < 10){
			return minute+":"+"0"+time;
		}
		return minute+":"+time;
	}
	
	

}
