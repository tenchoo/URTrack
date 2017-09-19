package com.urt.Ability.unicom;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.text.DecimalFormat;
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
import com.urt.Ability.collect.BatchTrafficQuery;
import com.urt.Ability.unicom.service.GetNetworkAccessConfigService;
import com.urt.Ability.unicom.service.GetTerminalDetailsService;
import com.urt.Ability.unicom.service.GetTerminalRatingService;
import com.urt.Ability.unicom.service.GetTerminalUsageDataDetailsService;
import com.urt.Ability.unicom.service.GetTerminalUsageService;
import com.urt.Ability.unicom.vo.AccessConfig;
import com.urt.Ability.unicom.vo.GetNetworkAccessConfigResponse;
import com.urt.Ability.unicom.vo.GetTerminalDetailsResponse;
import com.urt.Ability.unicom.vo.GetTerminalRatingResponse;
import com.urt.Ability.unicom.vo.GetTerminalUsageDataDetailsResponse;
import com.urt.Ability.unicom.vo.GetTerminalUsageResponse;
import com.urt.Ability.unicom.vo.TerminalDetail;
import com.urt.Ability.unicom.vo.TerminalRatingDetail;
import com.urt.Ability.unicom.vo.UsageDetail;
import com.urt.dto.traffic.LaoSimDateDetailDto;
import com.urt.dto.traffic.LaoTrafficDetailDto;
import com.urt.interfaces.traffic.BatchInsertTrafficQueryService;
import com.urt.mapper.LaoOperatordealLogMapper;
import com.urt.mapper.ext.LaoUserExtMapper;
import com.urt.mapper.ext.LaoUserOperatorPlanExtMapper;
import com.urt.mapper.ext.ServiceStatusExtMapper;
import com.urt.po.LaoOperatordealLog;
import com.urt.po.ServiceStatus;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

/**
 * 联通流量查询
 * 
 * @author lingfei
 * @date 2017年02月09日
 */
@Service("batchTrafficQueryCUCC")
public class BatchTrafficQueryCUCC extends BatchTrafficQuery<SOAPMessage> {

	Logger log = Logger.getLogger(getClass());

	@Autowired
	private LaoUserExtMapper laoUserDao;
	@Autowired
	private LaoOperatordealLogMapper laoOperatordealLogDAO;
	@Autowired
	private GetTerminalRatingService terminalRatingService;
	@Autowired
	private GetTerminalUsageDataDetailsService dataDetailsService;
	@Autowired
	private LaoUserOperatorPlanExtMapper laoUserOperatorPlanExtDao;
	@Autowired
	private BatchInsertTrafficQueryService batchInsertService;
	@Autowired
	private GetTerminalDetailsService getTerminalDetailsService;
	@Autowired
	private GetNetworkAccessConfigService getNetworkAccessConfigService;
	@Autowired
	private ServiceStatusExtMapper serviceStatusExtMapper;
	@Autowired
	private GetTerminalUsageService getTerminalUsageService;
	
	@Override
	protected void queryDayUseCount(LaoSimDateDetailDto laoSimDateDetailDto) {
		Map<String,Object> map0 = new HashMap<String,Object>();
		map0.put("iccid", laoSimDateDetailDto.getIccid());
		map0.put("dataCycle", laoSimDateDetailDto.getData().replace("-", ""));
		LaoTrafficDetailDto laoDetailDto =  batchInsertService.selectByIccidAndCycle(map0);
		if (laoDetailDto != null && laoDetailDto.getUseCount() != null) {
			laoSimDateDetailDto.setDayUseCount(laoSimDateDetailDto.getDayUseCount()+"");
			log.info("日使用流量表里已有数据,无需再调接口查询！");
			return;
		}
		SOAPMessage request = null;
		SOAPMessage response = null;
		String dateCycle = "";// 日期 yyyy-MM-dd
		String iccid = "0";
		if (laoSimDateDetailDto != null) {
			dateCycle = laoSimDateDetailDto.getData();
			iccid = laoSimDateDetailDto.getIccid();
		}
		LaoOperatordealLog logger = new LaoOperatordealLog();
		logger.setCreateDate(new Date());
		logger.setIccid(iccid);
		logger.setSuccess("1");
		logger.setOperatorId(laoSimDateDetailDto.getOperatorsId()+"");
		logger.setOperatorType("5");// 1订购2发短信3状态变更4查询卡状态5流量查询
		String messageId = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()).toString();
		Map<String,String> map = getDateUseCount(request,iccid,messageId,"1",dateCycle,logger,response);
		double UseCount = 0;
		if (map != null) {
			String totalBytesCnt = map.get("totalBytesCnt");
			UseCount = UseCount + Double.parseDouble(totalBytesCnt);
			String flag = map.get("flag");
			if (!"2".equals(flag)) {
				int totalPage = 0;
				if (map.get("totalPage") !=null) {
					totalPage = Integer.parseInt(map.get("totalPage"));
				}
				for (int page = 2; page < totalPage+1; page++) {
					Map<String,String> mapStr = getDateUseCount(request,iccid,messageId,page + "",dateCycle,logger,response);
					String totalBytesCntStr = mapStr.get("totalBytesCnt");
					UseCount = UseCount + Double.parseDouble(totalBytesCntStr);
					String flagStr = mapStr.get("flag");
					if ("2".equals(flagStr)) {
						break;
					}
				}
			}
		}
        DecimalFormat decimalFormat = new DecimalFormat("#0.0");//格式化设置  
		laoSimDateDetailDto.setDayUseCount(decimalFormat.format(UseCount));// 单位是KB
	}

	protected Map<String,String> getDateUseCount(SOAPMessage request, String iccid, String messageId, String page, String dateCycle, LaoOperatordealLog logger, SOAPMessage response){
		Map<String,String> map = new HashMap<String,String>();
		double totalBytesCnt = 0;
		String flag = "0";
		try {
			request = dataDetailsService.createRequest(iccid, messageId, page+"", dateCycle);//yyyy-MM-dd
			request = dataDetailsService.secureMessage(request,
					dataDetailsService.getUsername(),terminalRatingService.getPasswd());
			ByteArrayOutputStream bin = new ByteArrayOutputStream();
			request.writeTo(bin);
			logger.setInputParameters(bin.toString());
			System.out.println("Request: ");
			request.writeTo(System.out);
			SOAPConnection connection = dataDetailsService.getConnectionFactory().createConnection();
			response = connection.call(request,new URL(dataDetailsService.getUrl()));
			System.out.println("Response: ");
			response.writeTo(System.out);
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			response.writeTo(bout);
			if (bout.toString().length()>1500) {
				logger.setOutputParameters(bout.toString().substring(0, 1500));
			}else{
				logger.setOutputParameters(bout.toString());
			}
			List<UsageDetail> list = null;
			if (!response.getSOAPBody().hasFault()) {
				logger.setSuccess("0"); // 0成功1失败
				logger.setResultCode("");
				logger.setResultInfo("0"); // 0成功1失败
				if (!response.getSOAPBody().hasFault()) {
					GetTerminalUsageDataDetailsResponse ws = (GetTerminalUsageDataDetailsResponse) dataDetailsService.parseMessage(response);
					map.put("totalPage", ws.getTotalPages());
					list = ws.getList();
				}
			} else {
				SOAPFault fault = response.getSOAPBody().getFault();
				logger.setResultInfo("1"); // 0成功1失败
				if (fault.getFaultCode().equals("200200")) {
					logger.setResultInfo("0"); // 0成功1失败
				}
				logger.setResultCode(fault.getFaultCode());
				System.err.println("故障信息");
				System.err.println("SOAP Fault Code :" + fault.getFaultCode());
				System.err.println("SOAP Fault String :"+ fault.getFaultString());
				flag = "2";
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
					if (dateCycle.equals(newDate.substring(0, 10))) {
						double double2 = Double.parseDouble(detailsDto.getDataVolume());
						totalBytesCnt = totalBytesCnt + double2;
						flag = "1";
					} else {
						if ("1".equals(flag)) {
							flag = "2";
							break;
						}
					}
				}
			}
			map.put("totalBytesCnt", totalBytesCnt+"");
			map.put("flag", flag);
			return map;
		} catch (Exception e) {
			log.info("调用接口异常");
			logger.setResultInfo("调用接口异常");// 0成功1失败
			logger.setSuccess("1"); // 0成功1失败
			flag = "2";
			map.put("flag", flag);
			return map;
		} finally {
			try {
				// 记录传入传出参数
				logger.setId(Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID)));
				laoOperatordealLogDAO.insertSelective(logger);
			} catch (Exception e) {
				log.info("记录传入传出参数异常");
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void queryMonthUseCount(LaoSimDateDetailDto laoSimDateDetailDto) {
		SOAPMessage request = null;
		SOAPMessage response = null;
		String iccid = "0";
		if (laoSimDateDetailDto != null) {
			iccid = laoSimDateDetailDto.getIccid();
		}
		List<String> iccidList = new ArrayList<String>();
		iccidList.add(iccid);
		LaoOperatordealLog logger = new LaoOperatordealLog();
		logger.setCreateDate(new Date());
		logger.setIccid(iccid);
		logger.setSuccess("1");
		logger.setOperatorType("5");// 1订购2发短信3状态变更4查询卡状态5流量查询
		logger.setOperatorId(laoSimDateDetailDto.getOperatorsId()+"");
		String messageId = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()).toString();
		try {
			request = getTerminalDetailsService.createRequest(iccidList, messageId);
			request = getTerminalDetailsService.secureMessage(request,getTerminalDetailsService.getUsername(),
					getTerminalDetailsService.getPasswd());
			ByteArrayOutputStream bin = new ByteArrayOutputStream();
			request.writeTo(bin);
			logger.setInputParameters(bin.toString());
			System.out.println("Request: ");
			request.writeTo(System.out);
			SOAPConnection connection = getTerminalDetailsService.getConnectionFactory().createConnection();
			response = connection.call(request,new URL(getTerminalDetailsService.getUrl()));
			System.out.println("Response: ");
			response.writeTo(System.out);
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			response.writeTo(bout);
			List<TerminalDetail> listTerminal = null;
			if (!response.getSOAPBody().hasFault()) {
				logger.setSuccess("0"); // 0成功1失败
				logger.setResultCode("");
				logger.setResultInfo("0"); // 0成功1失败
				GetTerminalDetailsResponse ws = (GetTerminalDetailsResponse) getTerminalDetailsService.parseMessage(response);
				listTerminal = ws.getList();
			} else {
				SOAPFault fault = response.getSOAPBody().getFault();
				System.err.println("故障信息");
				System.err.println("SOAP Fault Code :" + fault.getFaultCode());
				System.err.println("SOAP Fault String :"+ fault.getFaultString());
				logger.setResultCode(fault.getFaultCode());
				logger.setResultInfo("1"); // 0成功1失败
			}
			if (bout.toString().length()>1500) {
				logger.setOutputParameters(bout.toString().substring(0, 1500));
			}else{
				logger.setOutputParameters(bout.toString());
			}
			double sum = 0;
			String status = "";
			if (listTerminal != null) {
					TerminalDetail terminalDetail = listTerminal.get(0);
					String monthUseCount = terminalDetail.getMonthToDateDataUsage();
					if (StringUtils.isBlank(monthUseCount)) {
						monthUseCount = "0";
						// 月付灵活共享 和 月付单卡 的只需要展示使用量  剩余量不要
						String accountId = terminalDetail.getAccountId();
						if(!"100273818".equals(accountId)){
							laoSimDateDetailDto.setMonthSign("sign");
						}
					}	
				sum = Double.parseDouble(monthUseCount);
				status = terminalDetail.getStatus();
				laoSimDateDetailDto.setImei(terminalDetail.getImei());// 入网编号
				laoSimDateDetailDto.setImsi(terminalDetail.getImsi());// 小卡卡号
				laoSimDateDetailDto.setDateActivated(terminalDetail.getDateActivated());// 激活时间
				laoSimDateDetailDto.setRenewalPolicy(terminalDetail.getRenewalPolicy());// 续约模式
				String dateActivated = terminalDetail.getDateActivated();
				if (!StringUtils.isBlank(dateActivated)) {
					laoSimDateDetailDto.setDateActivated(dateActivated.substring(0, 19).replace("T", " "));// 激活时间
				}
			}
	        DecimalFormat decimalFormat = new DecimalFormat("#0.0");//格式化设置  
			laoSimDateDetailDto.setMonthUseCount(decimalFormat.format(sum * 1024));// sum单位是MB
			if ("26".equals(laoSimDateDetailDto.getData().substring(8, 10))) {
				try {
					request = getTerminalUsageService.createRequest(iccid,messageId,laoSimDateDetailDto.getData());
					request = getTerminalUsageService.secureMessage(request, getTerminalUsageService.getUsername(), getTerminalUsageService.getPasswd());
					System.out.println("Request: ");
					request.writeTo(System.out);
					System.out.println("");
					connection = getTerminalUsageService.getConnectionFactory().createConnection();
					response = connection.call(request, new URL(getTerminalUsageService.getUrl()));
					System.out.println("Response: ");
					response.writeTo(System.out);
					System.out.println("");
					if (response.getSOAPBody().hasFault()) {
						SOAPFault fault = response.getSOAPBody().getFault();
						System.err.println("故障信息");
						System.err.println("SOAP Fault Code :" + fault.getFaultCode());
						System.err.println("SOAP Fault String :" + fault.getFaultString());
					} else {
						GetTerminalUsageResponse response2 =(GetTerminalUsageResponse) getTerminalUsageService.parseMessage(response);
						String totalDataVolume = response2.getTotalDataVolume();
						if (!StringUtils.isBlank(totalDataVolume)) {
							double totalData = Double.parseDouble(totalDataVolume);
							laoSimDateDetailDto.setMonthUseCount(decimalFormat.format(totalData * 1024));// totalData单位是MB
						} else {
							laoSimDateDetailDto.setMonthUseCount("0");// 单位是KB
						}
					}
				} catch (Exception e) {
					log.info("getTerminalUsageService查询月使用量失败！");
					e.printStackTrace();
				}
			}
			try {
				// 记录传入传出参数
				logger.setId(Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID)));
				laoOperatordealLogDAO.insertSelective(logger);
			} catch (Exception e) {
				log.info("记录传入传出参数异常");
				e.printStackTrace();
			}
			if (!StringUtils.isBlank(status)) {
				if ("ACTIVATED_NAME".equals(status)) {
					AccessConfig accessConfig = null;
					//调用apn接口
					request = getNetworkAccessConfigService.createRequest(iccidList,"lmh-test-terminal-detail");
					SOAPMessage createRequest = getNetworkAccessConfigService.createRequest(iccidList,messageId);
					request = getNetworkAccessConfigService.secureMessage(createRequest,getNetworkAccessConfigService.getUsername(),getNetworkAccessConfigService.getPasswd());
					bin=new ByteArrayOutputStream();
					request.writeTo(bin);
					logger.setInputParameters(bin.toString());
					System.out.println("Request: ");
					request.writeTo(System.out);
					System.out.println("");
					connection = getNetworkAccessConfigService.getConnectionFactory().createConnection();
					response = connection.call(request, new URL(getNetworkAccessConfigService.getUrl()));
					System.out.println("Response: ");
					response.writeTo(System.out);
					bout=new ByteArrayOutputStream();
					response.writeTo(bout);
					if (bout.toString().length()>1500) {
						logger.setOutputParameters(bout.toString().substring(0, 1500));
					} else {
						logger.setOutputParameters(bout.toString());
					}
					GetNetworkAccessConfigResponse responseResult = (GetNetworkAccessConfigResponse) getNetworkAccessConfigService.parseMessage(response);
					if (responseResult != null) {
						List<AccessConfig> list1 = responseResult.getList();
						if (list1 != null && list1.size() > 0){
							accessConfig = list1.get(0);
							if (accessConfig == null) {
								log.info("获取资费计划异常-----------");
							} else {
								String nowApnName = accessConfig.getApnNameByNacid();
								ServiceStatus selectByStatechangeId = serviceStatusExtMapper.selectByStatechangeId(nowApnName);
								if (selectByStatechangeId != null) {
									laoSimDateDetailDto.setState(selectByStatechangeId.getStateCode());
								} else {
									log.info("没有相匹配的状态==========nowApnName:"+nowApnName);
								}			
							}
							logger.setSuccess("0"); // 0成功1失败
							logger.setResultCode("");
							logger.setResultInfo("0"); // 0成功1失败
						}
					} else {
						logger.setSuccess("1"); // 0成功1失败
						logger.setResultCode("failed");
						logger.setResultInfo("1"); // 0成功1失败
						ServiceStatus selectByStatechangeId = serviceStatusExtMapper.selectByStatechangeId(status);
						if (selectByStatechangeId !=null) {
							laoSimDateDetailDto.setState(selectByStatechangeId.getStateCode());
						}
						log.info("调用apn接口出错------");						
					}
				} else {
					logger.setSuccess("0"); // 0成功1失败
					logger.setResultCode("");
					logger.setResultInfo("0"); // 0成功1失败
					ServiceStatus selectByStatechangeId = serviceStatusExtMapper.selectByStatechangeId(status);
					if(selectByStatechangeId != null){
						laoSimDateDetailDto.setState(selectByStatechangeId.getStateCode());
					}else{
						log.info("没有相匹配的状态");
					}
				}
				try {
					// 记录传入传出参数
					logger.setId(Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID)));
					laoOperatordealLogDAO.insertSelective(logger);
				} catch (Exception e) {
					log.info("记录传入传出参数异常");
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			log.info("调用接口异常");
			logger.setResultInfo("调用接口异常");// 0成功1失败
			logger.setSuccess("1"); // 0成功1失败
			try {
				// 记录传入传出参数
				logger.setId(Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID)));
				laoOperatordealLogDAO.insertSelective(logger);
			} catch (Exception e2) {
				log.info("记录传入传出参数异常");
				e2.printStackTrace();
			}
		}
		
	}
	@Override
	protected void queryMonthDataRemaining(LaoSimDateDetailDto laoSimDateDetailDto) {
		SOAPMessage request = null;
		SOAPMessage response = null;
		String iccid = "0";
		if (laoSimDateDetailDto != null) {
			iccid = laoSimDateDetailDto.getIccid();
		}
		LaoOperatordealLog logger = new LaoOperatordealLog();
		logger.setCreateDate(new Date());
		logger.setIccid(iccid);
		logger.setSuccess("1");
		logger.setId(Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID)));
		logger.setOperatorType("5");// 1订购2发短信3状态变更4查询卡状态5流量查询
		logger.setOperatorId(laoSimDateDetailDto.getOperatorsId()+"");
		String messageId = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()).toString();
		try {
			request = terminalRatingService.createRequest(iccid, messageId);
			request = terminalRatingService.secureMessage(request,terminalRatingService.getUsername(),
					terminalRatingService.getPasswd());
			ByteArrayOutputStream bin = new ByteArrayOutputStream();
			request.writeTo(bin);
			logger.setInputParameters(bin.toString());
			System.out.println("Request: ");
			request.writeTo(System.out);
			SOAPConnection connection = terminalRatingService.getConnectionFactory().createConnection();
			response = connection.call(request,new URL(terminalRatingService.getUrl()));
			System.out.println("Response: ");
			response.writeTo(System.out);
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			response.writeTo(bout);
			List<TerminalRatingDetail> listTerminal = null;
			if (!response.getSOAPBody().hasFault()) {
				logger.setSuccess("0"); // 0成功1失败
				logger.setResultCode("");
				logger.setResultInfo("0"); // 0成功1失败
				GetTerminalRatingResponse ws = (GetTerminalRatingResponse) terminalRatingService.parseMessage(response);
				listTerminal = ws.getList();
			} else {
				SOAPFault fault = response.getSOAPBody().getFault();
				System.err.println("故障信息");
				System.err.println("SOAP Fault Code :" + fault.getFaultCode());
				System.err.println("SOAP Fault String :"+ fault.getFaultString());
				logger.setResultCode(fault.getFaultCode());
				logger.setResultInfo("1"); // 0成功1失败
			}
			if (bout.toString().length()>1500) {
				logger.setOutputParameters(bout.toString().substring(0, 1500));
			}else{
				logger.setOutputParameters(bout.toString());
			}
			double sum = 0;
			if (listTerminal != null) {
				for (int i = 0; i < listTerminal.size(); i++) {
					TerminalRatingDetail terminalDetail = listTerminal.get(i);
					String dataRemaining = terminalDetail.getDataRemaining();
					if (dataRemaining == null || "".equals(dataRemaining)) {
						dataRemaining = "0";
					}
					sum = sum + Double.parseDouble(dataRemaining);
				}
			}
	        DecimalFormat decimalFormat = new DecimalFormat("#0.0");//格式化设置  
			laoSimDateDetailDto.setDataRemaining(decimalFormat.format(sum * 1024));// 单位是KB
		} catch (Exception e) {
			log.info("调用接口异常");
			logger.setResultInfo("调用接口异常");// 0成功1失败
			logger.setSuccess("1"); // 0成功1失败
			return ;
		} finally {
			try {
				// 记录传入传出参数
				laoOperatordealLogDAO.insertSelective(logger);
			} catch (Exception e) {
				log.info("记录传入传出参数异常");
				e.printStackTrace();
			}
		}
		
	}
	@Override
	protected void queryCardMsg(LaoSimDateDetailDto laoSimDateDetailDto) {
		
	}

}
