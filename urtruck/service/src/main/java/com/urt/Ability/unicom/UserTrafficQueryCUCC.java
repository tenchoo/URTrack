package com.urt.Ability.unicom;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.urt.Ability.collect.UserTrafficQuery;
import com.urt.Ability.unicom.service.GetSessionInfoService;
import com.urt.Ability.unicom.service.GetTerminalDetailsService;
import com.urt.Ability.unicom.service.GetTerminalRatingService;
import com.urt.Ability.unicom.service.GetTerminalUsageDataDetailsService;
import com.urt.Ability.unicom.vo.GetSessionInfoResponse;
import com.urt.Ability.unicom.vo.GetTerminalDetailsResponse;
import com.urt.Ability.unicom.vo.GetTerminalRatingResponse;
import com.urt.Ability.unicom.vo.GetTerminalUsageDataDetailsResponse;
import com.urt.Ability.unicom.vo.Session;
import com.urt.Ability.unicom.vo.TerminalRatingDetail;
import com.urt.Ability.unicom.vo.UsageDetail;
import com.urt.dto.traffic.LaoTrafficDetailDto;
import com.urt.dto.traffic.PackagePlanDto;
import com.urt.dto.traffic.TrafficQueryDetailsDto;
import com.urt.dto.traffic.TrafficQueryNowDto;
import com.urt.mapper.ext.OperatorPlanExtMapper;
import com.urt.po.OperatorPlan;

import oracle.net.aso.i;

/**
 * 联通流量查询
 * 
 * @author lingfei
 * @date 2016年10月18日
 */
@Service("userTrafficQueryCUCC")
public class UserTrafficQueryCUCC extends UserTrafficQuery<SOAPMessage> {

	Logger log = Logger.getLogger(getClass());

	@Autowired
	private GetTerminalRatingService terminalRatingService;
	@Autowired
	private GetTerminalUsageDataDetailsService dataDetailsService;
	@Autowired
	private OperatorPlanExtMapper operatorPlanExtDao;
	@Autowired
	private GetTerminalDetailsService getTerminalDetailsService;
	@Autowired
	private GetSessionInfoService getSessionInfoService;
	
	private Map<String,String> getCountId(String iccid) throws Exception{
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
		String userFlow = responseFirst.getList().get(0).getMonthToDateUsage();
		Map<String,String> map = new HashMap<String,String>();
		map.put("accountId", accountId);
		map.put("userFlow", userFlow);
		return map;
	}
	
	
	/**
	 * 剩余流量实时查询
	 * 
	 */
	@Override
	protected TrafficQueryResultMsg sendNowMessage(Object... args) {
		SOAPMessage request = null;
		SOAPMessage response = null;
		TrafficQueryResultMsg msg = new TrafficQueryResultMsg();
		TrafficQueryNowDto trafficNowDto = new TrafficQueryNowDto();
		String iccid = String.valueOf(args[0]);
		Map<String,String> map = null;
		try {
			map = getCountId(iccid);
		} catch (Exception e1) {
			log.info("异常,iccid:"+iccid);
			e1.printStackTrace();
		}
		// 月付灵活共享 和 月付单卡 的只需要展示使用量  剩余量不要
		if(!"100273818".equals(map.get("accountId"))){
			trafficNowDto.setMonthSign("sign");// 月付灵活共享 和 月付单卡标识字段
			String useCount = "0";
			if (!StringUtils.isBlank(map.get("userFlow"))) {
				useCount = map.get("userFlow");
			}
			trafficNowDto.setDataRemaining(useCount);
			/*log.info("**************accountId*********************************"+map.get("accountId"));
			try {
				request = terminalRatingService.createRequest(args);
				request = terminalRatingService.secureMessage(request,terminalRatingService.getUsername(),
						terminalRatingService.getPasswd());
				ByteArrayOutputStream bin = new ByteArrayOutputStream();
				request.writeTo(bin);
				msg.setInputMessage(bin.toString());
				request.writeTo(System.out);
				SOAPConnection connection = terminalRatingService.getConnectionFactory().createConnection();
				response = connection.call(request,new URL(terminalRatingService.getUrl()));
				response.writeTo(System.out);
				ByteArrayOutputStream bout = new ByteArrayOutputStream();
				response.writeTo(bout);
				List<TerminalRatingDetail> listTerminal = null;
				if (!response.getSOAPBody().hasFault()) {
					msg.setSuccess(true);
					msg.setOpeartorsDealCode("");
					msg.setOpeartorsDealRst("0"); // 0成功1失败
					GetTerminalRatingResponse ws = (GetTerminalRatingResponse) terminalRatingService.parseMessage(response);
					listTerminal = ws.getList();
				} else {
					SOAPFault fault = response.getSOAPBody().getFault();
					System.err.println("故障信息");
					msg.setOpeartorsDealCode(fault.getFaultCode());
					msg.setOpeartorsDealRst("1"); // 0成功1失败
				}
				if (bout.toString().length()>1000) {
					msg.setOutMessage(bout.toString().substring(0, 1000));
				} else {
					msg.setOutMessage(bout.toString());
				}
				double sum = 0;
				if (listTerminal != null) {
					for (TerminalRatingDetail detail : listTerminal) {
						String dataRemaining = detail.getDataRemaining();
						if (StringUtils.isBlank(dataRemaining)) {
							String operatorsPid = detail.getRatePlanName();
							OperatorPlan operPlan = operatorPlanExtDao.queryByOperatorsPid(operatorsPid);
							String unit = operPlan.getQuantityUnit();
							if ("G".equals(unit)) {
								sum = sum + operPlan.getQuantityMax() * 1024;
							} else {
								sum = sum + operPlan.getQuantityMax();
							}
						} else {
							sum = sum + Double.parseDouble(dataRemaining);
						}
					}
				}
				sum = sum - Double.parseDouble(map.get("userFlow"));
				DecimalFormat decimalFormat = new DecimalFormat("#0.000");//格式化设置
				// 当剩余流量计算为负数时：
				if (sum < 0) {
					if ("100979618".equals(map.get("accountId"))) {
						// 月付灵活共享   正常展示负数  提示为流量池卡 （只限制联通卡）
						trafficNowDto.setDataRemaining(decimalFormat.format(sum)+"(流量池卡)"); 
					} else if("100979418".equals(map.get("accountId"))){
						// 月付单卡  如果剩余流量为负 展示成0
						trafficNowDto.setDataRemaining("0"); 
					}
				} else {
					trafficNowDto.setDataRemaining(decimalFormat.format(sum)); 
				}
			} catch (Exception e) {
				log.info("调用接口异常,accountId="+map.get("accountId"));
				trafficNowDto.setExpMsg("调用查询接口异常！");
				trafficNowDto.setDataRemaining("0");
			}*/
		} else {
			try {
				request = terminalRatingService.createRequest(args);
				request = terminalRatingService.secureMessage(request,terminalRatingService.getUsername(),
						terminalRatingService.getPasswd());
				ByteArrayOutputStream bin = new ByteArrayOutputStream();
				request.writeTo(bin);
				msg.setInputMessage(bin.toString());
				request.writeTo(System.out);
				SOAPConnection connection = terminalRatingService.getConnectionFactory().createConnection();
				response = connection.call(request,new URL(terminalRatingService.getUrl()));
				response.writeTo(System.out);
				ByteArrayOutputStream bout = new ByteArrayOutputStream();
				response.writeTo(bout);
				List<TerminalRatingDetail> listTerminal = null;
				if (!response.getSOAPBody().hasFault()) {
					msg.setSuccess(true);
					msg.setOpeartorsDealCode("");
					msg.setOpeartorsDealRst("0"); // 0成功1失败
					GetTerminalRatingResponse ws = (GetTerminalRatingResponse) terminalRatingService.parseMessage(response);
					listTerminal = ws.getList();
				} else {
					SOAPFault fault = response.getSOAPBody().getFault();
					System.err.println("故障信息");
					msg.setOpeartorsDealCode(fault.getFaultCode());
					msg.setOpeartorsDealRst("1"); // 0成功1失败
				}
				if (bout.toString().length()>1000) {
					msg.setOutMessage(bout.toString().substring(0, 1000));
				} else {
					msg.setOutMessage(bout.toString());
				}
				double sum = 0;
				List<PackagePlanDto> listPack = new ArrayList<PackagePlanDto>();
				if (listTerminal != null) {
					for (TerminalRatingDetail detail : listTerminal) {
						String queue = detail.getQueuePosition();
						String dataRemaining = detail.getDataRemaining();
						if (StringUtils.isBlank(dataRemaining)) {
							dataRemaining = "0";
						}
						/**为了将gla功能转接到h5上，添加的参数* @author sunhao* @date 2017年2月9日 下午3:50:23****/
						if ("0".equals(queue)) {
							trafficNowDto.setExpirationDate(detail.getExpirationDate());
							trafficNowDto.setRatePlanName(detail.getRatePlanName());
						}

						sum = sum + Double.parseDouble(dataRemaining);
						/**为了展示全部套餐信息,添加参数 *@author lingfei 2017年4月25日10:19:08*/
						String operatorsPid = detail.getRatePlanName();
						if (!"110WLW004085_MON-FIX_5120M-0S".equals(operatorsPid)) {
							OperatorPlan operPlan = operatorPlanExtDao.queryByOperatorsPid(operatorsPid);
							PackagePlanDto packDto = new PackagePlanDto();
							packDto.setPackageRemain(detail.getDataRemaining()+"M");//单位是M
							if (operPlan != null) {
								Integer timeLength = operPlan.getTimeLength();
								int timeAdd = timeLength;
								String timeUnit = operPlan.getTimeUnit();
								packDto.setPackageName(operPlan.getPlanName());
								if (timeLength != null && timeLength != 0) {
									if ("0".equals(timeUnit)) {
										timeAdd = timeLength;
										packDto.setPackageTimeLenth(timeLength+"天");
									} else if("1".equals(timeUnit)){
										timeAdd = timeLength * 30;
										packDto.setPackageTimeLenth(timeLength+"月");
									} else if("2".equals(timeUnit)){
										timeAdd = timeLength * 90;
										packDto.setPackageTimeLenth(timeLength+"季");
									} else if("3".equals(timeUnit)){
										timeAdd = timeLength * 360;
										packDto.setPackageTimeLenth(timeLength+"年");
									}
									packDto.setPackageCount(operPlan.getQuantityMax()+operPlan.getQuantityUnit());
									String endTime = detail.getExpirationDate();
									if (StringUtils.isBlank(endTime)) {
										packDto.setPackageEndTime("未使用");
										packDto.setPackageStartTime("未使用");
										packDto.setPackageRemain(packDto.getPackageCount());
									} else {
										// 截止时间减去对应套餐天数即为生效时间
										endTime = endTime.substring(0, 10);
										SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
										Calendar calendar = Calendar.getInstance();
										calendar.setTime(sdf.parse(endTime));
										calendar.add(Calendar.DATE,-timeAdd);//整数往后推,负数往前移动
										String startTime = sdf.format(calendar.getTime());
										packDto.setPackageEndTime(endTime);
										packDto.setPackageStartTime(startTime);
									}
									listPack.add(packDto);
								}
							}
						}
					}
				}
				DecimalFormat decimalFormat = new DecimalFormat("#0.000");//格式化设置
				trafficNowDto.setDataRemaining(decimalFormat.format(sum));
				trafficNowDto.setListPack(listPack);
			} catch (Exception e) {
				log.info("调用接口异常");
				trafficNowDto.setExpMsg("调用查询接口异常！");
				trafficNowDto.setDataRemaining("0");
				msg.setTrafficDto(trafficNowDto);
				return msg;
			}
		}
		/**
		 * 新增 卡是否在网状态 展示 
		 * lingfei
		 * 2017年6月12日17:43:56
		 */
		List<Session> listSession = null;
		try {
			request = getSessionInfoService.createRequest(iccid,"lmh-test-SessionIfno");
			request = getSessionInfoService.secureMessage(request,getSessionInfoService.getUsername(),
					getSessionInfoService.getPasswd());
			ByteArrayOutputStream bin = new ByteArrayOutputStream();
			request.writeTo(bin);
			msg.setInputMessage(bin.toString());
			request.writeTo(System.out);
			SOAPConnection connection = getSessionInfoService.getConnectionFactory().createConnection();
			response = connection.call(request,new URL(getSessionInfoService.getUrl()));
			response.writeTo(System.out);
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			response.writeTo(bout);
			if (!response.getSOAPBody().hasFault()) {
				GetSessionInfoResponse ws = (GetSessionInfoResponse) getSessionInfoService.parseMessage(response);
				listSession = ws.getSessionInfo();
			} else {
				SOAPFault fault = response.getSOAPBody().getFault();
				System.err.println("故障信息 fault:"+fault);
			}
		} catch (Exception e) {
			log.info("查询卡是否在网 异常");
			e.printStackTrace();
		}
		if (listSession != null && listSession.size() > 0) {
			trafficNowDto.setIsOnline(" (在网)");
		} else {
			trafficNowDto.setIsOnline(" (不在网)");
		}
		msg.setTrafficDto(trafficNowDto);
		return msg;
	}

	@Override
	protected UserTrafficQuery<SOAPMessage>.TrafficQueryResultMsg sendDayMessage(Object... args) {
		//iccid, messageId, "0", date
		SOAPMessage request = null;
		SOAPMessage response = null;
		TrafficQueryResultMsg msg = new TrafficQueryResultMsg();
		List<TrafficQueryDetailsDto> listDto = new ArrayList<TrafficQueryDetailsDto>();
		String iccid = "0";
		String messageId = "0";
		String cycleStartDate = "";
		if (args.length > 0) {
			iccid = (String) args[0];//ICCID
		}
		if (args.length > 0) {
			messageId = (String) args[1];//流水号
		}
		if (args.length > 3) {
			cycleStartDate = (String) args[3];//YYYY-MM-01
		}
		Map<String,String> map = getDateUseCount(msg,request,iccid,messageId,"1",cycleStartDate,response,listDto);
		if (map != null) {
			String flag = map.get("flag");
			if (!"2".equals(flag)) {
				int totalPage = 0;
				if (map.get("totalPage") != null) {
					totalPage = Integer.parseInt(map.get("totalPage"));
				}
				for (int page = 2; page < totalPage+1; page++) {
					Map<String,String> mapStr = getDateUseCount(msg,request,iccid,messageId,page + "",cycleStartDate,response,listDto);
					String flagStr = mapStr.get("flag");
					if ("2".equals(flagStr)) {
						break;
					}
				}
			}
		}
		// 添加该天总计流量记录
		double totalBytesCnt = 0;
		double totalDuration = 0;
		for (int i = 0; i < listDto.size(); i++) {
			double tesCnt = Double.parseDouble(listDto.get(i).getDataVolume());
			totalBytesCnt = totalBytesCnt + tesCnt;
			
			double duration = Double.parseDouble(listDto.get(i).getDuration());
			totalDuration = totalDuration + duration;
		}
		TrafficQueryDetailsDto dtoTotal = new TrafficQueryDetailsDto();
		dtoTotal.setSessionStartTime(cycleStartDate);
        DecimalFormat decimalFormat = new DecimalFormat("#0.000");//格式化设置  
		String strDetails = decimalFormat.format(totalBytesCnt);
		dtoTotal.setDataVolume(strDetails);
		
		//2017年6月2日15:40:16 孙昊 添加
		if (listDto.size()>0) {
			dtoTotal.setDuration(totalDuration+"");
			dtoTotal.setZone(listDto.get(0).getZone());
		}
		
		listDto.add(dtoTotal);
		msg.setListDto(listDto);
	/*	SOAPMessage request = null;
		SOAPMessage response = null;
		TrafficQueryResultMsg msg = new TrafficQueryResultMsg();
		List<TrafficQueryDetailsDto> listDto = new ArrayList<TrafficQueryDetailsDto>();
		String cycleStartDate = "";
		if (args.length > 3) {
			cycleStartDate = (String) args[3];
		}
		try { 
			request = dataDetailsService.createRequest(args);
			request = dataDetailsService.secureMessage(request,
					dataDetailsService.getUsername(),terminalRatingService.getPasswd());
			ByteArrayOutputStream bin = new ByteArrayOutputStream();
			request.writeTo(bin);
			msg.setInputMessage(bin.toString());
			System.out.println("Request: ");
			request.writeTo(System.out);
			System.out.println("");
			SOAPConnection connection = dataDetailsService.getConnectionFactory().createConnection();
			response = connection.call(request,new URL(dataDetailsService.getUrl()));
			System.out.println("Response: ");
			response.writeTo(System.out);
			System.out.println("");
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			response.writeTo(bout);
			if (bout.toString().length()>1000) {
				msg.setOutMessage(bout.toString().substring(0, 1000));
			}else{
				msg.setOutMessage(bout.toString());
			}
			List<UsageDetail> list = null;
			if (!response.getSOAPBody().hasFault()) {
				msg.setSuccess(true);
				msg.setOpeartorsDealCode("");
				msg.setOpeartorsDealRst("0"); // 0成功1失败
				if (!response.getSOAPBody().hasFault()) {
					GetTerminalUsageDataDetailsResponse ws = (GetTerminalUsageDataDetailsResponse) dataDetailsService.parseMessage(response);
					list = ws.getList();
				}
			} else {
				SOAPFault fault = response.getSOAPBody().getFault();
				System.err.println("故障信息");
				System.err.println("SOAP Fault Code :" + fault.getFaultCode());
				System.err.println("SOAP Fault String :"+ fault.getFaultString());
				msg.setOpeartorsDealCode(fault.getFaultCode());
				msg.setOpeartorsDealRst("1"); // 0成功1失败
			}
			if (list != null) {
				double totalBytesCnt = 0;
		        DecimalFormat decimalFormat = new DecimalFormat("#0.000");//格式化设置  
				for (int i = 0; i < list.size(); i++) {
					UsageDetail detailsDto = list.get(i);
					String date = detailsDto.getSessionStartTime().substring(0,19).replace('T', ' ');
					// 将世界时间转化为北京时间
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(sdf.parse(date));
					calendar.add(Calendar.HOUR,8);//整数往后推,负数往前移动
					String newDate = sdf.format(calendar.getTime());
					if (cycleStartDate.equals(newDate.substring(0, 10))) {
						TrafficQueryDetailsDto details = new TrafficQueryDetailsDto();
						details.setSessionStartTime(newDate);
						double double2 = Double.parseDouble(detailsDto.getDataVolume());
						totalBytesCnt = totalBytesCnt + double2;
						String strDetails = decimalFormat.format(double2 / 1024);
						details.setDataVolume(strDetails);
						listDto.add(details);
					}
				}
				// 添加该天总计流量记录
				TrafficQueryDetailsDto dtoTotal = new TrafficQueryDetailsDto();
				dtoTotal.setSessionStartTime(cycleStartDate);
				String strDetails = decimalFormat.format(totalBytesCnt / 1024);
				dtoTotal.setDataVolume(strDetails);
				listDto.add(dtoTotal);
			}
			msg.setListDto(listDto);
		} catch (Exception e) {
			log.info("调用接口异常");
			TrafficQueryDetailsDto dtoTotal = new TrafficQueryDetailsDto();
			dtoTotal.setDataVolume("0");
			dtoTotal.setSessionStartTime(cycleStartDate);
			listDto.add(dtoTotal);
			msg.setListDto(listDto);
			return msg;
		}*/
		return msg;
	}

	@Override
	protected UserTrafficQuery<SOAPMessage>.TrafficQueryResultMsg sendMonthMessage(Object... args) {
		//iccid, messageId, "0", date
		SOAPMessage request = null;
		SOAPMessage response = null;
		TrafficQueryResultMsg msg = new TrafficQueryResultMsg();
		List<TrafficQueryDetailsDto> listDtoMsg = new ArrayList<TrafficQueryDetailsDto>();
		List<TrafficQueryDetailsDto> listDto = new ArrayList<TrafficQueryDetailsDto>();
		String iccid = "0";
		String messageId = "0";
		String cycleStartDate = "";
		if (args.length > 0) {
			iccid = (String) args[0];//ICCID
		}
		if (args.length > 0) {
			messageId = (String) args[1];//流水号
		}
		if (args.length > 3) {
			cycleStartDate = (String) args[3];//YYYY-MM-01
		}
		Map<String,String> map = getDateUseCountByMonth(msg,request,iccid,messageId,"1",cycleStartDate,response,listDto);
		if (map != null) {
				int totalPage = 0;
				if (map.get("totalPage") !=null) {
					totalPage = Integer.parseInt(map.get("totalPage"));
				}
				for (int page = 2; page < totalPage+1; page++) {
					this.getDateUseCountByMonth(msg,request,iccid,messageId,page + "",cycleStartDate,response,listDto);
				}
			}
		
		listDtoMsg = addList(listDto, cycleStartDate);
		msg.setListDto(listDtoMsg);
/*		SOAPMessage request = null;
		SOAPMessage response = null;
		TrafficQueryResultMsg msg = new TrafficQueryResultMsg();
		List<TrafficQueryDetailsDto> listDto = new ArrayList<TrafficQueryDetailsDto>();
		String cycleStartDate = "";
		if (args.length > 3) {
			cycleStartDate = (String) args[3];//YYYY-MM-01
		}
		try {
			request = dataDetailsService.createRequest(args);
			request = dataDetailsService.secureMessage(request,
					dataDetailsService.getUsername(),terminalRatingService.getPasswd());
			ByteArrayOutputStream bin = new ByteArrayOutputStream();
			request.writeTo(bin);
			msg.setInputMessage(bin.toString());
			System.out.println("Request: ");
			request.writeTo(System.out);
			System.out.println("");
			SOAPConnection connection = dataDetailsService.getConnectionFactory().createConnection();
			response = connection.call(request,new URL(dataDetailsService.getUrl()));
			System.out.println("Response: ");
			response.writeTo(System.out);
			System.out.println("");
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			response.writeTo(bout);
			if (bout.toString().length()>1000) {
				msg.setOutMessage(bout.toString().substring(0, 1000));
			}else{
				msg.setOutMessage(bout.toString());
			}
			List<UsageDetail> list = null;
			if (!response.getSOAPBody().hasFault()) {
				msg.setSuccess(true);
				msg.setOpeartorsDealCode("");
				msg.setOpeartorsDealRst("0"); // 0成功1失败
				GetTerminalUsageDataDetailsResponse ws = (GetTerminalUsageDataDetailsResponse) dataDetailsService.parseMessage(response);
				list = ws.getList();
			} else {
				SOAPFault fault = response.getSOAPBody().getFault();
				System.err.println("故障信息");
				System.err.println("SOAP Fault Code :" + fault.getFaultCode());
				System.err.println("SOAP Fault String :"+ fault.getFaultString());
				msg.setOpeartorsDealCode(fault.getFaultCode());
				msg.setOpeartorsDealRst("1"); // 0成功1失败
			}
			if (list != null) {
//				double countDataVolume = 0;
				listDto = getNewList(list,listDto);
				listDto = addList(listDto, cycleStartDate);
				//该月使用总量
				String dataVolume = "0";
				for (int i = 0; i < listDto.size(); i++) {
					dataVolume = listDto.get(i).getDataVolume();
					countDataVolume = countDataVolume + Double.parseDouble(dataVolume);
				}
				TrafficQueryDetailsDto dto = new TrafficQueryDetailsDto();
				dto.setSessionStartTime(cycleStartDate.substring(0,7));
				String countStr = countDataVolume+"";
				if (countStr.length() > 6) {
					countStr = countStr.substring(0,7);
				}
				dto.setDataVolume(countStr);
				listDto.add(dto);
				msg.setListDto(listDto);
			}
		} catch (Exception e) {
			log.info("调用接口异常");
			return msg;
		}*/
		return msg;
	}

	protected Map<String,String> getDateUseCount(TrafficQueryResultMsg msg,SOAPMessage request, String iccid, String messageId, String page, String dateCycle, SOAPMessage response, List<TrafficQueryDetailsDto> listDto){
		Map<String,String> map = new HashMap<String,String>();
		String flag = "0";
		try {
			request = dataDetailsService.createRequest(iccid, messageId, page+"", dateCycle);//yyyy-MM-dd
			request = dataDetailsService.secureMessage(request,
					dataDetailsService.getUsername(),terminalRatingService.getPasswd());
			ByteArrayOutputStream bin = new ByteArrayOutputStream();
			request.writeTo(bin);
			msg.setInputMessage(bin.toString());
//			System.out.println("Request: ");
			request.writeTo(System.out);
			SOAPConnection connection = dataDetailsService.getConnectionFactory().createConnection();
			response = connection.call(request,new URL(dataDetailsService.getUrl()));
//			System.out.println("Response: ");
			response.writeTo(System.out);
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			response.writeTo(bout);
			if (bout.toString().length()>1500) {
				msg.setOutMessage(bout.toString().substring(0, 1500));
			}else{
				msg.setOutMessage(bout.toString());
			}
			List<UsageDetail> list = null;
			if (!response.getSOAPBody().hasFault()) {
				msg.setSuccess(true);
				msg.setOpeartorsDealCode("");
				msg.setOpeartorsDealRst("0成功"); // 0成功1失败
				if (!response.getSOAPBody().hasFault()) {
					GetTerminalUsageDataDetailsResponse ws = (GetTerminalUsageDataDetailsResponse) dataDetailsService.parseMessage(response);
					map.put("totalPage", ws.getTotalPages());
					list = ws.getList();
				}
			} else {
				SOAPFault fault = response.getSOAPBody().getFault();
				msg.setOpeartorsDealRst("1失败"); // 0成功1失败
				if (fault.getFaultCode().equals("200200")) {
					msg.setOpeartorsDealCode("200200");
				}
				msg.setOpeartorsDealCode(fault.getFaultCode());
				System.err.println("故障信息");
				System.err.println("SOAP Fault Code :" + fault.getFaultCode());
				System.err.println("SOAP Fault String :"+ fault.getFaultString());
				flag = "2";
			}
			if (list != null) {
		        DecimalFormat decimalFormat = new DecimalFormat("#0.000");//格式化设置  
				for (int i = 0; i < list.size(); i++) {
					UsageDetail detailsDto = list.get(i);
					String date = detailsDto.getSessionStartTime().substring(0,19).replace('T', ' ');
					// 将世界时间转化为北京时间
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(sdf.parse(date));
					calendar.add(Calendar.HOUR,8);//整数往后推,负数往前移动
					String newDate = sdf.format(calendar.getTime());
					if (dateCycle.equals(newDate.substring(0, 10))) {
						TrafficQueryDetailsDto dto = new TrafficQueryDetailsDto();
						dto.setSessionStartTime(newDate);
						double dataVol = Double.parseDouble(detailsDto.getDataVolume());
						dto.setDataVolume(decimalFormat.format(dataVol / 1024));
						
						//2017年6月2日15:40:16 孙昊 添加
						dto.setDuration(detailsDto.getDuration());
						dto.setZone(detailsDto.getZone());
						
						listDto.add(dto);
						flag = "1";
					} else {
						if ("1".equals(flag)) {
							flag = "2";
							break;
						}
					}
				}
			}
			map.put("flag", flag);
			return map;
		} catch (Exception e) {
			log.info("调用接口异常");
			msg.setSuccess(false);
			msg.setOpeartorsDealRst("调用接口异常"); // 0成功1失败
			flag = "2";
			map.put("flag", flag);
			return map;
		}
		
	}
	protected Map<String,String> getDateUseCountByMonth(TrafficQueryResultMsg msg,SOAPMessage request, String iccid, String messageId, String page, String dateCycle, SOAPMessage response, List<TrafficQueryDetailsDto> listDto){
		Map<String,String> map = new HashMap<String,String>();
		try {
			request = dataDetailsService.createRequest(iccid, messageId, page+"", dateCycle);//yyyy-MM-dd
			request = dataDetailsService.secureMessage(request,
					dataDetailsService.getUsername(),terminalRatingService.getPasswd());
			ByteArrayOutputStream bin = new ByteArrayOutputStream();
			request.writeTo(bin);
			msg.setInputMessage(bin.toString());
//			System.out.println("Request: ");
			request.writeTo(System.out);
			SOAPConnection connection = dataDetailsService.getConnectionFactory().createConnection();
			response = connection.call(request,new URL(dataDetailsService.getUrl()));
//			System.out.println("Response: ");
			response.writeTo(System.out);
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			response.writeTo(bout);
			if (bout.toString().length()>1500) {
				msg.setOutMessage(bout.toString().substring(0, 1500));
			}else{
				msg.setOutMessage(bout.toString());
			}
			List<UsageDetail> list = null;
			if (!response.getSOAPBody().hasFault()) {
				msg.setSuccess(true);
				msg.setOpeartorsDealCode("");
				msg.setOpeartorsDealRst("0成功"); // 0成功1失败
				if (!response.getSOAPBody().hasFault()) {
					GetTerminalUsageDataDetailsResponse ws = (GetTerminalUsageDataDetailsResponse) dataDetailsService.parseMessage(response);
					map.put("totalPage", ws.getTotalPages());
					list = ws.getList();
				}
			} else {
				SOAPFault fault = response.getSOAPBody().getFault();
				msg.setOpeartorsDealRst("1失败"); // 0成功1失败
				if (fault.getFaultCode().equals("200200")) {
					msg.setOpeartorsDealCode("200200");
					msg.setOpeartorsDealRst("0成功"); // 0成功1失败
				}
				msg.setOpeartorsDealCode(fault.getFaultCode());
				System.err.println("故障信息");
				System.err.println("SOAP Fault Code :" + fault.getFaultCode());
				System.err.println("SOAP Fault String :"+ fault.getFaultString());
			}
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					UsageDetail detailsDto = list.get(i);
					String date = detailsDto.getSessionStartTime().substring(0,19).replace('T', ' ');
					// 将世界时间转化为北京时间
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(sdf.parse(date));
					calendar.add(Calendar.HOUR,8);//整数往后推,负数往前移动
					String newDate = sdf.format(calendar.getTime());
					TrafficQueryDetailsDto dto = new TrafficQueryDetailsDto();
					dto.setSessionStartTime(newDate);
					dto.setDataVolume(detailsDto.getDataVolume());
					listDto.add(dto);
				}
			}
			return map;
		} catch (Exception e) {
			log.info("调用接口异常");
			msg.setSuccess(false);
			msg.setOpeartorsDealRst("调用接口异常"); // 0成功1失败
			return map;
		}
	}
	
	/**
	 * 整合每一天的数据
	 * 
	 * @param listDto
	 * @param list
	 */
/*	private List<TrafficQueryDetailsDto> getNewList(List<UsageDetail> list,List<TrafficQueryDetailsDto> listDto) {
		List<TrafficQueryDetailsDto> listDto1 = new ArrayList<TrafficQueryDetailsDto>();
		if (list != null && list.size() > 0) {
			// 把list转化到listDto1 并截掉日期
			for (int i = 0; i < list.size(); i++) {
				UsageDetail usageDetail = list.get(i);
				TrafficQueryDetailsDto detailsDto1 = new TrafficQueryDetailsDto();
				String date = usageDetail.getSessionStartTime().substring(0, 19).replace('T', ' ');
				// 将世界时间转化为北京时间
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Calendar calendar = Calendar.getInstance();
				try {
					calendar.setTime(sdf.parse(date));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				calendar.add(Calendar.HOUR,8);//整数往后推,负数往前移动
				String newDate = sdf.format(calendar.getTime());
				detailsDto1.setSessionStartTime(newDate.substring(0,10));
				detailsDto1.setDataVolume(usageDetail.getDataVolume());
				listDto1.add(detailsDto1);
			}
			// 添加第一个日期整合后的记录
			double double2 = 0;
			for (int j = 0; j < listDto1.size(); j++) {
				TrafficQueryDetailsDto detailsDto = listDto1.get(j);
				if (detailsDto.getSessionStartTime().equals(listDto1.get(0).getSessionStartTime())) {
					double dto2 = Double.parseDouble(detailsDto.getDataVolume());
					double2 = double2 + dto2;
				}
			}
	        DecimalFormat decimalFormat = new DecimalFormat("#0.000");//格式化设置 
			TrafficQueryDetailsDto details = new TrafficQueryDetailsDto();
			details.setSessionStartTime(listDto1.get(0).getSessionStartTime());
			String strDetail = decimalFormat.format(double2 / 1024);
			details.setDataVolume(strDetail);
			listDto.add(details);
			// 添加不同日期 整合后的记录
			c: for (int i = 0; i < listDto1.size(); i++) {
				TrafficQueryDetailsDto detailsDto = listDto1.get(i);
				for (int j = 0; j < listDto.size(); j++) {
					TrafficQueryDetailsDto detailsDto1 = listDto.get(j);
					if (detailsDto.getSessionStartTime().equals(detailsDto1.getSessionStartTime())) {
						continue c;
					}
				}
				double d2 = 0;
				for (int k = 0; k < listDto1.size(); k++) {
					TrafficQueryDetailsDto detailsDto2 = listDto1.get(k);
					// 相同日期的记录 回话时长，消费流量 汇总
					if (detailsDto.getSessionStartTime().equals(detailsDto2.getSessionStartTime())) {
						double dto2 = Double.parseDouble(detailsDto2.getDataVolume());
						d2 = d2 + dto2;
					}
				}
				// 构造一个新的对象(某一天的总记录)
				TrafficQueryDetailsDto detailsDto3 = new TrafficQueryDetailsDto();
				detailsDto3.setSessionStartTime(detailsDto.getSessionStartTime());
				String strDetails = decimalFormat.format(d2 / 1024);
				detailsDto3.setDataVolume(strDetails);
				listDto.add(detailsDto3);
			}
		}
		return listDto;
	}*/

	@Override
	public TrafficQueryResultMsg sendBatchQueryMessage(Object... args) {
		TrafficQueryResultMsg msg = new TrafficQueryResultMsg();
		LaoTrafficDetailDto laoDto = new LaoTrafficDetailDto();
		SOAPMessage request = null;
		SOAPMessage response = null;
		String iccid = "";
		if (args.length > 0) {
			iccid = (String) args[0];
		}
		String messageId = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()).toString();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		// 查询日期为昨天
		String cycleStartDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		try {
			request = dataDetailsService.createRequest(iccid, messageId, "0",cycleStartDate);
			request = dataDetailsService.secureMessage(request,
					dataDetailsService.getUsername(),
					terminalRatingService.getPasswd());
			ByteArrayOutputStream bin = new ByteArrayOutputStream();
			msg.setInputMessage(bin.toString());
			request.writeTo(bin);
			request.writeTo(System.out);
			SOAPConnection connection = dataDetailsService
					.getConnectionFactory().createConnection();
			response = connection.call(request,
					new URL(dataDetailsService.getUrl()));
			response.writeTo(System.out);
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			response.writeTo(bout);
			if (bout.toString().length()>1000) {
				msg.setOutMessage(bout.toString().substring(0, 1000));
			}else{
				msg.setOutMessage(bout.toString());
			}
			List<UsageDetail> list = null;
			if (!response.getSOAPBody().hasFault()) {
				msg.setSuccess(true);
				msg.setOpeartorsDealCode("");
				msg.setOpeartorsDealRst("0"); // 0成功1失败
				GetTerminalUsageDataDetailsResponse ws = (GetTerminalUsageDataDetailsResponse) dataDetailsService
						.parseMessage(response);
				list = ws.getList();
			} else {
				SOAPFault fault = response.getSOAPBody().getFault();
				System.err.println("故障信息");
				System.err.println("SOAP Fault Code :" + fault.getFaultCode());
				System.err.println("SOAP Fault String :"+ fault.getFaultString());
				msg.setOpeartorsDealCode(fault.getFaultCode());
				msg.setOpeartorsDealRst("1"); // 0成功1失败
				
				
				/*LaoBusiexcpLogDto exception = new LaoBusiexcpLogDto();
				exception.setIccid(iccid);
				exception.setExcpTypeCode(Short.valueOf("5"));
				exception.setRecvTime(new Date());
				exception.setResultInfo("批量查询流量业务失败");
				throw new BusiException(exception);*/
			}
			if (list != null) {
				double totalBytesCnt = 0;
				for (int i = 0; i < list.size(); i++) {
					UsageDetail detailsDto = list.get(i);
					String date = detailsDto.getSessionStartTime()
							.substring(0, 10);
					if (cycleStartDate.equals(date)) {
						double double2 = Double.parseDouble(detailsDto.getDataVolume());
						totalBytesCnt = totalBytesCnt + double2;
					}
				}
				laoDto.setDataCycle(cycleStartDate);
				laoDto.setUseCount(new BigDecimal(totalBytesCnt));
			}
		} catch (Exception e) {
			laoDto.setDataCycle(cycleStartDate);
			laoDto.setUseCount(new BigDecimal(0));
			msg.setLaoTrafficDetailDto(laoDto);
			e.printStackTrace();
			return msg;
		}
		if (laoDto.getUseCount() == null) {
			laoDto.setDataCycle(cycleStartDate.replace("-", "").substring(0,6));
			laoDto.setUseCount(new BigDecimal(0));
		}
		msg.setLaoTrafficDetailDto(laoDto);
		return msg;
	}
	
	/**
	 * 
	 * @param list
	 * @param date  yyyy-MM-01
	 * @return 
	 */
	private List<TrafficQueryDetailsDto> addList(List<TrafficQueryDetailsDto> list, String date) {
		List<TrafficQueryDetailsDto> listNew = new ArrayList<TrafficQueryDetailsDto>();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(date.substring(0, 4)));
		cal.set(Calendar.MONTH, Integer.parseInt(date.substring(5, 7)) - 2);
		cal.set(Calendar.DATE, 1);
		cal.roll(Calendar.DATE, -1);
		// 获取上个月总天数
		int maxDate = cal.get(Calendar.DATE);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date month = null;
		try {
			month = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(month);
		cal2.add(Calendar.MONTH, -1);
		cal2.set(Calendar.DAY_OF_MONTH, 26);// 从26号开始(26号中午12点)
		for (int i = 0; i < maxDate+1; i++, cal2.add(Calendar.DATE, 1)) {
			Date d = cal2.getTime();
			String df = sdf.format(d);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动
			String nowDate = sdf.format(calendar.getTime());
			if (df.equals(nowDate)) {
				break;
			}
			TrafficQueryDetailsDto dtoNew = new TrafficQueryDetailsDto();
			dtoNew.setSessionStartTime(df);
			double dataVolume = 0;
			for (int j = 0; j < list.size(); j++) {
				TrafficQueryDetailsDto dto = list.get(j);
				if (df.equals(dto.getSessionStartTime().substring(0, 10))) {
					dataVolume = dataVolume + Double.parseDouble(dto.getDataVolume());
					
				}
			}
	        DecimalFormat decimalFormat = new DecimalFormat("#0.000");//格式化设置 
			dtoNew.setDataVolume(decimalFormat.format(dataVolume / 1024));
			listNew.add(dtoNew);
		}
		return listNew;
	}
}
