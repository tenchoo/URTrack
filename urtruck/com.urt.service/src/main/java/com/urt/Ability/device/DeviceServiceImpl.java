package com.urt.Ability.device;

import java.math.BigDecimal;
import java.net.Inet4Address;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.sql.visitor.functions.Insert;
import com.lenovo.game.cache.interceptor.annotation.LiveTime;
import com.lenovo.pay.utils.HttpResponseResult;
import com.lenovo.pay.utils.HttpUtil;
import com.urt.Ability.DongguanCMC.MemberMethodImpl;
import com.urt.Ability.http.util.ToolsUtil;
import com.urt.Ability.unicom.jsonbean.UnicomNotifyChangeBody;
import com.urt.Ability.unicom.jsonbean.UnicomNotifyChangeHead;
import com.urt.Ability.unicom.jsonbean.UnicomNotifyHead;
import com.urt.Ability.unicom.jsonbean.UnicomRateChangeBody;
import com.urt.Ability.unicom.jsonbean.UnicomRateNotifyBody;
import com.urt.Ability.unicom.service.GetTerminalRatingService;
import com.urt.Ability.unicom.vo.GetTerminalRatingResponse;
import com.urt.Ability.unicom.vo.NowRatePlanResponse;
import com.urt.Ability.unicom.vo.TerminalRatingDetail;
import com.urt.dto.IccidLibDto;
import com.urt.dto.LaoDeviceRelDto;
import com.urt.dto.LaoSsAccountRoleDto;
import com.urt.dto.Goods.LaoUserDto;
import com.urt.dto.device.Account;
import com.urt.dto.device.CMCFlow;
import com.urt.dto.device.FlowQueryCmc;
import com.urt.interfaces.device.DeviceService;
import com.urt.mapper.ImeiLibraryMapper;
import com.urt.mapper.LaoAccountRelPoMapper;
import com.urt.mapper.LaoCustPersonPoMapper;
import com.urt.mapper.LaoCustomerPoMapper;
import com.urt.mapper.LaoDeviceRelMapper;
import com.urt.mapper.LaoSsAccountPoMapper;
import com.urt.mapper.LaoSsAccountRolePoMapper;
import com.urt.mapper.LaoUserMapper;
import com.urt.mapper.OperatorPlanMapper;
import com.urt.mapper.ServiceStatusMapper;
import com.urt.mapper.ext.IccidLibExtMapper;
import com.urt.mapper.ext.LaoAccountRelPoExtMapper;
import com.urt.mapper.ext.LaoCustomerPoExtMapper;
import com.urt.mapper.ext.LaoDeviceRelExtMapper;
import com.urt.mapper.ext.LaoSsAccountPoExtMapper;
import com.urt.mapper.ext.LaoUserExtMapper;
import com.urt.mapper.ext.LaoUserOperatorPlanExtMapper;
import com.urt.modules.security.utils.Digests;
import com.urt.po.IccidLib;
import com.urt.po.ImeiLibrary;
import com.urt.po.LaoAccountRelPo;
import com.urt.po.LaoCustPersonPo;
import com.urt.po.LaoCustomerPo;
import com.urt.po.LaoDeviceRel;
import com.urt.po.LaoSsAccountPo;
import com.urt.po.LaoSsAccountRolePo;
import com.urt.po.LaoUser;
import com.urt.po.LaoUserOperatorPlan;
import com.urt.po.OperatorPlan;
import com.urt.po.ServiceStatus;
import com.urt.service.traffic.FlowInfoQuery;
import com.urt.service.unicom.UnicomNotifyChangeService;
import com.urt.service.unicom.UnicomNotifyService;
import com.urt.service.unicom.UnicomRateChangeService;
import com.urt.service.unicom.UnicomRateNotifyService;
import com.urt.utils.Encodes;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

@Service("deviceServiceImpl")
public class DeviceServiceImpl implements DeviceService {

	@Value("${passort.auth.url}")
	private String authUrl;
	@Value("${lenovoid.realm.device}")
	private String passportRealm;
	@Value("${lenovoid.realm}")
	private String passportRealmXd;
	@Value("${card.request.url}")
	private String cardRequestUrl;
	@Value("${before.flow.date}")
	private String beforeflowDate;
	private static final Logger log = Logger.getLogger(DeviceServiceImpl.class);
	
	private static final int SALT_SIZE = 8;
	public static final int HASH_INTERATIONS = 1024;
	
	@Autowired
	LaoSsAccountPoMapper laoSs;
	@Autowired
	ImeiLibraryMapper imei;
	@Autowired
	GetTerminalRatingService getTerminalRatingService;
	@Autowired
	ServiceStatusMapper serviceStatus;
	@Autowired
	UnicomNotifyService unicomNotifyService;
	@Autowired
	UnicomNotifyChangeService  unicomNotifyChangeService;
	@Autowired
	UnicomRateChangeService unicomRateChangeService;
	@Autowired
	UnicomRateNotifyService unicomRateNotifyService;
	@Autowired
	LaoUserExtMapper laoUserExtMapper;
	@Autowired
	LaoUserOperatorPlanExtMapper laoUserOperatorPlanExtMapper;
	@Autowired
	MemberMethodImpl memberMethodImpl;
	@Autowired
	private IccidLibExtMapper iccidLibExtDAO;
	@Autowired
	FlowInfoQuery flowInfoQuery;
	@Autowired
	OperatorPlanMapper  operatorPlanMapper;
	@Autowired
	LaoAccountRelPoExtMapper laoAccountRelPoExtMapper;
	@Autowired
	LaoCustomerPoMapper laoCustomerPoMapper;
	@Autowired
	LaoCustPersonPoMapper laoCustPersonPoMapper;
	@Autowired
	LaoAccountRelPoMapper laoAccountRelPoMapper;
	@Autowired
	LaoSsAccountRolePoMapper laoSsAccountRolePoMapper;
	@Autowired
	LaoUserMapper laoUserMapper;
	@Autowired
	LaoDeviceRelMapper laoDeviceRelMapper;
	@Autowired
	LaoDeviceRelExtMapper laoDeviceRelExtMapper;
	@Autowired
	LaoSsAccountPoExtMapper laoSsAccountPoExtMapper;
	
	public Account authSt(String authName) {
		try {
			log.info("调用LenovoId的参数加地址>>>>>>>>>"+authUrl + "lpsust=" + authName + "&realm=" + passportRealm);
			String xml = HttpUtil.sendGetHttps(authUrl + "lpsust=" + authName + "&realm=" + passportRealm);
			log.info("调用LenovoId的返回参数>>>>>>>>>>"+xml);
			return parseXML2Account(xml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Account xdswAuthSt(String authName) {
		try {
			log.info("调用LenovoId的参数加地址>>>>>>>>>"+authUrl + "lpsust=" + authName + "&realm=" + passportRealmXd);
			String xml = HttpUtil.sendGetHttps(authUrl + "lpsust=" + authName + "&realm=" + passportRealmXd);
			log.info("调用LenovoId的返回参数>>>>>>>>>>"+xml);
			return parseXML2Account(xml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean lenovoIdCheck(String accountId) {
		boolean flag = false;
		LaoAccountRelPo laoAccountRelPo = new LaoAccountRelPo();
		laoAccountRelPo.setRelAccount(accountId);
		laoAccountRelPo.setRelType("1000");
		List<LaoAccountRelPo> lao = laoAccountRelPoExtMapper.queryRelByRelType(laoAccountRelPo);
		if (null != lao && lao.size()>0) {
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}
	
	
	public Long queryCustId(String accountId) {
		LaoSsAccountPo laoSsAccount = laoSsAccountPoExtMapper.queryByLoginName(accountId);
		if (null!=laoSsAccount) {
			
			return laoSsAccount.getCustId();
		}
		return null;
	}
	
	
	@Transactional
	public String initPersonCustomer(String lenovoId,String lenovoLoginId){
		boolean flag = false;
		Long custId=0l;
		try{
			custId=Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.CUST_ID));
			Long accountId=Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID));
			Long relId=Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SERIAL_NUM));
			log.info("custId="+custId+":"+"accountId="+accountId+":"+"relId="+relId);
			//查询lao_ss_account表 判断是否之前已经初始化了
			LaoSsAccountPo laoSsAccount = laoSsAccountPoExtMapper.queryByLoginName(lenovoLoginId);
			if (null!=laoSsAccount && lenovoLoginId.equals(laoSsAccount.getLoginName())) {
				log.info(">>>laoSsAccount>>>>已经有数据存在");
			}else{
				//初始化账号管理表 
				LaoSsAccountPo laoSsAccountPo = new LaoSsAccountPo();
				byte [] sign = Digests.generateSalt(SALT_SIZE);
				byte [] hashPass = Digests.sha1("111111".getBytes(), sign, HASH_INTERATIONS);
				laoSsAccountPo.setAcconutId(accountId);
				laoSsAccountPo.setCreateDate(new Date());
				laoSsAccountPo.setCustId(custId);
				laoSsAccountPo.setNickname(ToolsUtil.getId());
				laoSsAccountPo.setPassword(Encodes.encodeHex(hashPass));
				laoSsAccountPo.setSalt(Encodes.encodeHex(sign));	
				laoSsAccountPo.setPlainPassword("111111");
				laoSsAccountPo.setLoginName(lenovoLoginId);
				laoSsAccountPo.setRelatedId(String.valueOf(relId));
				laoSsAccountPo.setStatus("0");
				laoSs.insert(laoSsAccountPo);
			}
			//初始化客户表lao_customer表 
			LaoCustomerPo laoCustomerPo = new LaoCustomerPo();
			laoCustomerPo.setCustId(custId);
			laoCustomerPo.setCustType("0");
			laoCustomerPo.setCustState("0");
			laoCustomerPo.setInDate(new Date());
			laoCustomerPo.setPsptTypeCode("1");
			laoCustomerPo.setPsptId("**");
			laoCustomerPo.setCustName(lenovoLoginId);
			laoCustomerPoMapper.insert(laoCustomerPo);
			//初始化个人客户表 lao_cust_person
			LaoCustPersonPo laoCustPersonPo = new LaoCustPersonPo();
			laoCustPersonPo.setCustId(custId);
			laoCustPersonPo.setCustName(lenovoLoginId);
			laoCustPersonPo.setPsptTypeCode("1");
			laoCustPersonPo.setPsptId("**");
			laoCustPersonPoMapper.insert(laoCustPersonPo);
			
			//初始化账号关联表 lao_account_rel
			LaoAccountRelPo LaoAccountRelPo = new LaoAccountRelPo();
			LaoAccountRelPo.setRelId(relId);
			LaoAccountRelPo.setRelAccount(lenovoId);//
			LaoAccountRelPo.setAccountId(accountId);
			LaoAccountRelPo.setRelLoginname(lenovoLoginId);
			LaoAccountRelPo.setRecvTime(new Date());
			LaoAccountRelPo.setRelType("1000"); //1000-lenovoID；1001-微信ID；1002-支付宝；1003-QQ；1004-微博
			laoAccountRelPoMapper.insert(LaoAccountRelPo);
			//初始化角色账户关联表
			LaoSsAccountRolePo  laoSsAccountRolePo = new LaoSsAccountRolePo();
			laoSsAccountRolePo.setUserId(accountId);
			laoSsAccountRolePo.setRoleId(Long.valueOf("4")); //游客
			laoSsAccountRolePoMapper.insert(laoSsAccountRolePo);
			flag = true;
		}catch(Exception e){
			flag = false;
			e.printStackTrace();
		}
		String custIdPerson = null;
		if(flag){
			custIdPerson = String.valueOf(custId);
		}

		return custIdPerson;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lenovo.LAOAPI.interfaces.device.DeviceService#getCurentPlan(java.lang
	 * .String) 获取剩余总流量、过期时间
	 */
	public Map<String,String> getCurentPlan(String iccid){
		Map<String, String> map = new HashMap<String, String>();
		NowRatePlanResponse nowRatePlanResponse = null;
		LaoUser laoUser = laoUserExtMapper.selectByIccid(iccid);
		DecimalFormat format = new DecimalFormat("0.000");
		List<TerminalRatingDetail> list=null;
		int count=0;
		if(1 == laoUser.getOperatorsId()){		
			try {
				String messageId = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()).toString();
				SOAPMessage soapMessage = getTerminalRatingService.call(iccid,messageId);
				GetTerminalRatingResponse ws = (GetTerminalRatingResponse) getTerminalRatingService.parseMessage(soapMessage);
				if (soapMessage != null) {
					nowRatePlanResponse = new NowRatePlanResponse();
					list= ws.getList();
					if (list != null && list.size() > 0) {
						
						BigDecimal remain = new BigDecimal("0");
						for (TerminalRatingDetail detail : list) {
							String queue = detail.getQueuePosition();
							String ratePlanName = detail.getRatePlanName();
							String dataRemaining = detail.getDataRemaining();
							if ("110WLW004085_MON-FIX_5120M-0S".equals(ratePlanName)) {
								count++;
							}
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
								nowRatePlanResponse.setDataRemaining(format.format(decimal.doubleValue()));
							}

							remain = remain.add(decimal);
						}

						nowRatePlanResponse.setTotalCapacithy(format.format(remain
								.doubleValue()));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			map.put("expirationDate", nowRatePlanResponse.getExpirationDate());
			map.put("capacity", nowRatePlanResponse.getTotalCapacithy());
			map.put("ratePlanName", nowRatePlanResponse.getRatePlanName());
			map.put("dataRemaining", nowRatePlanResponse.getDataRemaining());
			map.put("termLength", nowRatePlanResponse.getTermLength());
			map.put("packageCount",(list.size()-count)+"");
		}else if(2==laoUser.getOperatorsId()){
			
			//月套餐过期时间	
			LaoUserOperatorPlan laoUserOperatorPlan = laoUserOperatorPlanExtMapper.getCurrentMonthPlan(laoUserExtMapper.selectByIccid(iccid).getUserId());
			//当月使用总量
			IccidLib selectByIccid = iccidLibExtDAO.selectByIccid(iccid);
			FlowQueryCmc flowQueryCmc = flowInfoQuery.getFlowInfoQuery(selectByIccid.getMsisdn());
			List<CMCFlow> cmcFlowList = flowQueryCmc.getFlowInfo();
			double usedFlow=0;
			double surplusCapacityFlow =0;
			double availableFlow = 0;
			String usedresCount=null;
			for(CMCFlow flow : cmcFlowList){
				if(null != flow){
					if(!ToolsUtil.checkStringIsNull(flow.getUsedresCount())){
						usedresCount = flow.getUsedresCount();
						usedFlow = usedFlow+Double.parseDouble(usedresCount);
					}
				/*	if(!ToolsUtil.checkStringIsNull(flow.getIeavingsCount())){				
						surplusCapacityFlow = surplusCapacityFlow + Double.parseDouble(flow.getIeavingsCount());
					}
					if(!ToolsUtil.checkStringIsNull(flow.getResourcesCount())){
						availableFlow = availableFlow + Double.parseDouble(flow.getResourcesCount());
					}*/
						
				}
				
			}
			//基础套餐每月流量
			OperatorPlan operatorPlan =operatorPlanMapper.selectByPrimaryKey(laoUserOperatorPlan.getPlanId());
			
			double a=4*1024;
//			if("G".equals(operatorPlan.getQuantityUnit())){
//				a = operatorPlan.getQuantityMax()*1024;
//			}else{
//				a = operatorPlan.getQuantityMax();
//			}
			double userF=usedFlow/1024;
			userF=((int)(userF*100))/100;  
			Date endDate = laoUserOperatorPlan.getEndDate();
			Date d=new Date();   
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat df1=new SimpleDateFormat("yyyy-MM-dd");
			df.format((endDate.getTime()+60*60*24*1000*365));
			double shengyu=a-userF;
			if (shengyu<0) {
				shengyu=0;
			}
			if (null==usedresCount || "0".equals(usedresCount) || "0.0".equals(usedresCount)) {
				map.put("surplusCapacityFlow",Double.toString(shengyu));//当月剩余总流量
			    map.put("expirationDate", dateForm(endDate));//月套餐过期时间
				map.put("flowBaseSize", String.valueOf(a));//基础套餐每月流量
				map.put("availableFlow", Double.toString(a));//当月可用总流量
				map.put("usedFlow", null);//当月已使用流量
			}else{
				map.put("surplusCapacityFlow", Double.toString(shengyu));//当月剩余总流量
				map.put("expirationDate", dateForm(endDate));//月套餐过期时间
				map.put("flowBaseSize", String.valueOf(a));//基础套餐每月流量
				map.put("availableFlow", Double.toString(a));//当月可用总流量
				map.put("usedFlow", Double.toString(userF));//当月已使用流量
			}
			map.put("beforeflowDate", df1.format(d));
			
		}else if(3 == laoUser.getOperatorsId()){
			map.put("surplusCapacityFlow", "");//当月剩余总流量
			map.put("expirationDate", "");//月套餐过期时间
			map.put("flowBaseSize", "");//基础套餐每月流量
			map.put("availableFlow", "");//当月可用总流量
			map.put("usedFlow",  "");//当月已使用流量
		}else{
			
		}
		return map;
	
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lenovo.LAOAPI.interfaces.device.DeviceService#findImeiLibraryByIccid(
	 * java.lang.String) 获取imei表中是否存在iccid
	 */
	public boolean findImeiLibraryByIccid(String iccid) {
		boolean falg = false;
		ImeiLibrary imeiDto = imei.doQueryFirst(iccid);
		if (null != imeiDto) {
			falg = true;
		}
		return falg;
	}

	
	
	@LiveTime(time = 60* 60 *2, key = "ActivityCardService.getActivityCardDetail")
	public String getActivityCardDetail(String rom, String deviceId, String devicemodel, String version) {
		StringBuffer stringBuffer = new StringBuffer();
		if(StringUtils.isNotEmpty(rom))
			stringBuffer.append("rom=").append(rom).append("&");
		if(StringUtils.isNotEmpty(deviceId))
			stringBuffer.append("deviceid=").append(deviceId).append("&");
		if(StringUtils.isNotEmpty(devicemodel))
			stringBuffer.append("devicemodel=").append(devicemodel).append("&");
		if(StringUtils.isNotEmpty(version))
			stringBuffer.append("version=").append(version);
			 
		HttpResponseResult httpResponseResult = HttpUtil.sendPost(cardRequestUrl, stringBuffer.toString());
		if(httpResponseResult == null || httpResponseResult.getHttpCode() != 200) {
			return null;
		}
		return httpResponseResult.getMessage();
		
	}
	
	public Map<String,String> getUnicomNotify(Map<String,String> params,String flag){
		Map<String,String> result = new HashMap<String,String>();

		if("UnicomNotifyChange".equals(flag)){
			UnicomNotifyChangeHead head = unicomNotifyChangeService.parseHead(params);
			UnicomNotifyChangeBody	 body = unicomNotifyChangeService.parseBody(params.get("data"));
			result.put("eventId", head.getEventId());
			result.put("eventType", head.getEventType());
			result.put("signAtrure",head.getSignature());
			result.put("signAtrure2",head.getSignature2());
			result.put("timeStamp",head.getTimestamp());
			
			result.put("iccid", body.getIccid());
			result.put("ratePlanName", body.getRatePlanName());
			result.put("bodyName", body.getBodyName());
			result.put("totalActualUsage", body.getTotalActualUsage());
			result.put("totalIncludedUsage", body.getTotalIncludedUsage());
		}else if("unicomNotify".equals(flag)){
			UnicomNotifyChangeHead head = unicomNotifyChangeService.parseHead(params);
			UnicomNotifyChangeBody body = unicomNotifyChangeService.parseBody(params.get("data"));
			result.put("eventId", head.getEventId());
			result.put("eventType", head.getEventType());
			result.put("signAtrure",head.getSignature());
			result.put("signAtrure2",head.getSignature2());
			result.put("timeStamp",head.getTimestamp());
			
			result.put("iccid", body.getIccid());
			result.put("ratePlanName", body.getRatePlanName());
			result.put("bodyName", body.getBodyName());
			result.put("totalActualUsage", body.getTotalActualUsage());
			result.put("totalIncludedUsage", body.getTotalIncludedUsage());
		}else if("UnicomRateChange".equals(flag)){
			UnicomNotifyHead head = unicomRateChangeService.parseHead(params);
	        UnicomRateChangeBody body = unicomRateChangeService.parseBody(params.get("data"));
	        result.put("eventId", head.getEventId());
			result.put("eventType", head.getEventType());
			result.put("signAtrure",head.getSignature());
			result.put("signAtrure2",head.getSignature2());
			result.put("timeStamp",head.getTimestamp());
			
			result.put("iccid", body.getIccid());
			result.put("oldRatePlanName", body.getOldRatePlanName());
			result.put("newRatePlanName", body.getNewRatePlanName());
			result.put("dataChanged", body.getDataChanged());
		}else if("UnicomRateNotify".equals(flag)){
			   UnicomNotifyHead head = unicomRateNotifyService.parseHead(params);
		        UnicomRateNotifyBody body = unicomRateNotifyService.parseBody(params.get("data"));		        
		    	result.put("eventId", head.getEventId());
				result.put("eventType", head.getEventType());
				result.put("signAtrure",head.getSignature());
				result.put("signAtrure2",head.getSignature2());
				result.put("timeStamp",head.getTimestamp());				
				result.put("iccid", body.getIccid());
				result.put("dataUsage", body.getDataUsage());
	
		}
		
		
		
		return result;
		
	}
	private Account parseXML2Account(String xml) {
		Account account = null;
		// try {
		// XStream stream = new XStream();
		// stream.alias("IdentityInfo", Account.class);
		// account = (Account) stream.fromXML(xml);
		// } catch (Exception e) {
		// System.out.println(e.getMessage());
		// }
		// System.out.println(xml);
		String accountID = StringUtils.substringBetween(xml, "<AccountID>", "</AccountID>");
		String userName = StringUtils.substringBetween(xml, "<Username>", "</Username>");
		String deviceID = StringUtils.substringBetween(xml, "<DeviceID>", "</DeviceID>");
		String verified = StringUtils.substringBetween(xml, "<verified>", "</verified>");
		if (StringUtils.isNotBlank(accountID) && StringUtils.isNotBlank(userName)) {
			account = new Account();
			account.setAccountID(accountID);
			account.setUname(userName);
			account.setUsername(userName);
			account.setDeviceID(deviceID);
			account.setVerified(verified);
		}

		return account;
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lenovo.LAOAPI.interfaces.device.DeviceService#cardStatusChange(java.
	 * lang.String) 卡状态转换操作
	 */
	@Override
	public String cardStatusChange(String cardStatus, String iccid) {
		String statusResult="";
		//针对移动做处理
		//IccidLib lib = iccidLibExtDAO.selectByIccid(iccid);
		/*if("2".equals(lib.getOperators())){
			LaoUser laoUser = userExtDao.selectByIccid(iccid);
			if("19".equals(laoUser.getStateCode())){
				statusResult="APN2";
			}else if("20".equals(laoUser.getStateCode())){
				statusResult="APN1";
			}else{			
				statusResult="";
			}
		}else {
			ServiceStatus card = serviceStatus.selectByPrimaryKey(cardStatus);
 			if(null != card){
				statusResult = card.getOutsidestate();
				if("1".equals(statusResult)){
					statusResult="APN2";
				}else if("2".equals(statusResult)){
					statusResult="APN1";
				}else{
					statusResult="";
				}
			}
		}*/	
		if("1".equals(cardStatus)){
			statusResult="APN2";
		}else{
			statusResult="APN1";
		}
		
		
		return statusResult;
	}
	/**
	 * 
	 * @param lenovoId
	 * @return
	 * 
	 */
	public List<Map<String,Object>> queryIccidList(String lenovoId){
		LaoAccountRelPo acc = laoAccountRelPoMapper.selectByRelAccount(lenovoId);
		if(null ==acc){
			return null;
		}
		LaoSsAccountPo account = laoSs.selectByPrimaryKey(acc.getAccountId());
		if(null == account){
			return null;
		}
		List<Map<String,Object>> user  = laoUserExtMapper.getCustId(account.getCustId());	
		return  user;
	}

	@Override
	public LaoDeviceRelDto selectByUserId(Long userId) {
		LaoDeviceRel laoDeviceRel = laoDeviceRelExtMapper.selectByUserId(userId);
		if (null!=laoDeviceRel) {
			LaoDeviceRelDto laoDeviceRelDto = new LaoDeviceRelDto();
			laoDeviceRelDto.setDeviceId(laoDeviceRel.getDeviceId());
			laoDeviceRelDto.setIccid(laoDeviceRel.getIccid());
			laoDeviceRelDto.setRelId(laoDeviceRel.getRelId());
			laoDeviceRelDto.setRelType(laoDeviceRel.getRelType());
			laoDeviceRelDto.setUserId(laoDeviceRel.getUserId());
			return laoDeviceRelDto;
		}
		return null;
	}
	
	public boolean insertLaoDeviceRel(String userId,String iccid,String deviceid,String lenovoId,String lenovoLoginName){
		
		log.info("***********insertLaoDeviceRel>>>>>>>>>>"+userId+"----"+iccid+"-----"+deviceid+"-----"+lenovoId);
		LaoDeviceRel rel = new LaoDeviceRel();
		Long relId=Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.DEVICEREL_ID));
		rel.setRelId(relId);
		rel.setDeviceId(deviceid);
		rel.setIdType("IMEI");
		rel.setRelType("1000");
		rel.setValidTag("1");
		rel.setRelAccount(lenovoId);
		rel.setRecvTime(new Date());
		rel.setIccid(iccid);
		rel.setUserId(Long.valueOf(userId));
		rel.setRelLogingname(lenovoLoginName);
		
		int a = laoDeviceRelMapper.insert(rel);
		log.info("<<<<<<<<insertLaoDeviceRel>>>>>>>>>>"+a);
		if(a>0){
			return true;
		}
		
		return false;
	}
	public String checkLenovoIccid(Long userId,String iccid,String deviceid,String lenovoId,String lenovoLoginName){
		log.info("***********checkLenovoIccid>>>>>>>>>>"+userId);
		LaoDeviceRel la = 	laoDeviceRelExtMapper.selectByUserId(userId);
		String lenovo="";
		if(null != la){
			if(null == la.getRelAccount() || null == la.getRelLogingname()){
				laoDeviceRelMapper.deleteByPrimaryKey(la.getRelId());
				insertLaoDeviceRel(String.valueOf(userId),iccid,deviceid,lenovoId,lenovoLoginName);
				lenovo = lenovoLoginName;
			}else{
				lenovo = la.getRelLogingname();
			}
		}else{
			insertLaoDeviceRel(String.valueOf(userId),iccid,deviceid,lenovoId,lenovoLoginName);
			lenovo =lenovoLoginName;
		}
		
		log.info("***********checkLenovoIccid>>>>>>>>>>"+lenovoId);
		return lenovo;
	}
	public String   dateForm(Date todayDate) {
		java.text.Format formatter=new java.text.SimpleDateFormat("yyyy-MM-dd");    
		long afterTime=(todayDate.getTime()/1000)+60*60*24*365;    
		todayDate.setTime(afterTime*1000);    
		String afterDate=formatter.format(todayDate);  
		return afterDate;
      
	}

	@Override
	public boolean insertImeiLibrary(String imei1) {
		ImeiLibrary library = new ImeiLibrary();
		String idSeq = ZkGenerateSeq.getIdSeq(SeqID.DEVICEREL_ID);
		library.setId(Long.valueOf(idSeq));
		library.setImei(imei1);
		int insert = imei.insert(library);
		if (insert>0) {
			return true;
		}
		return false;
	}
	public static void main(String[] args) {
		String url="https://passport.lenovo.com/interserver/authen/1.2/getaccountid?lpsust=ZAgAAAAAAAGE9MTAwNzk5NjQwMTAmYj0yJmM9NCZkPTE2OTY0JmU9RDI1QjhGQUUyOUIwNThGQjNCOURBQUVGOEQxMjYyQUQxJmg9MTQ5MzY5MTE0MzIwOCZpPTQzMjAwJm89ODY4NjcyMDIwMDI1MDY3JnA9aW1laSZxPTAmdXNlcm5hbWU9MTUyMzI5MjE3MzcmaWw9Y27xKfxAqVHzG7CjUDgxeyhO&realm=mobileaccess.mvno.lenovo.com";
		String xml = HttpUtil.sendGetHttps(url);
		System.out.println(xml);
	}
	
}
