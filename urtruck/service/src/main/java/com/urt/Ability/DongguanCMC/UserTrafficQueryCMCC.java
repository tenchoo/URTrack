package com.urt.Ability.DongguanCMC;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.urt.Ability.EcCMCC.utils.DigestUtils;
import com.urt.Ability.collect.UserTrafficQuery;
import com.urt.dto.traffic.DailydatausageBean;
import com.urt.dto.traffic.LaoTrafficDetailDto;
import com.urt.dto.traffic.OlMberFlows;
import com.urt.dto.traffic.PackagePlanDto;
import com.urt.dto.traffic.Totalinfo;
import com.urt.dto.traffic.TrafficQueryDetailsDto;
import com.urt.dto.traffic.TrafficQueryNowDto;
import com.urt.mapper.ext.LaoUserExtMapper;
import com.urt.mapper.ext.LaoUserOperatorPlanExtMapper;
import com.urt.mapper.ext.OperatorPlanExtMapper;
import com.urt.po.LaoUser;
import com.urt.po.OperatorPlan;
import com.urt.utils.HttpClientUtil;

/**
 * 移动流量查询
 * 
 * @author lingfei
 * @date 2016年12月01日
 */
@Service("userTrafficQueryCMCC")
public class UserTrafficQueryCMCC extends UserTrafficQuery<SOAPMessage> {

	Logger log = Logger.getLogger(getClass());

	private static final String url="http://183.230.96.66:8087/v2/";
	private static final String appid="CE8O2GB";
	private static final String passwd="LXDD85";
	@Autowired
	private LaoUserExtMapper laoUserDao;
	@Autowired
	private LaoUserOperatorPlanExtMapper laoUserOperatorPlanExtDao;
	@Autowired
	private OperatorPlanExtMapper operatorPlanExtDao;

	/**
	 * 剩余流量实时查询
	 * 
	 */
	@Override
	protected TrafficQueryResultMsg sendNowMessage(Object... args) {
		if (args == null || args.length == 0)
			throw new IllegalArgumentException();
		String iccid = (String) args[0];
		LaoUser laoUser = laoUserDao.selectByIccid(iccid);
		String msisdn = laoUser.getMsisdn();
		TrafficQueryResultMsg msg = new TrafficQueryResultMsg();
		TrafficQueryNowDto trafficNowDto = new TrafficQueryNowDto();
		List<PackagePlanDto> listPack = new ArrayList<PackagePlanDto>();
		try {
			// 根据iccid查询出套餐总流量 (单位KB)
			//double countFlow = laoUserOperatorPlanExtDao.getCountFlowByICCID(iccid);
			
			String method = "iot.member.datausage.query";
			Map<String, String> paramMap = EncryptUtils.genComMap();
			paramMap.put(ConstantsUntil.method, method);
			paramMap.put(ConstantsUntil.msisdn, msisdn);
			String secret = EncryptUtils.sign(paramMap, ConstantsUntil.secret);
			String httpUrl = ConstantsUntil.URL + "?" + EncryptUtils.genComUtr()
					+ "&" + ConstantsUntil.msisdn + "=" + msisdn + "&"
					+ ConstantsUntil.sign + "=" + secret + "&"
					+ ConstantsUntil.method + "=" + method;
			String reuslt =  HttpClientUtil.getInstance().sendHttpGet(httpUrl);
			if (reuslt != null) {
				// 记录参数
				if (reuslt.length() > 2000) {
					msg.setOutMessage(reuslt.substring(0, 2000));
				} else {
					msg.setOutMessage(reuslt);
				}
				if (StringUtils.isBlank(reuslt)) {
					msg.setOpeartorsDealRst("1"); // 0成功1失败
					trafficNowDto.setMonthSign("sign");
					trafficNowDto.setDataRemaining("0");// 查询失败，剩余流量填零
					msg.setTrafficDto(trafficNowDto);
					return msg;
				}
				JSONObject json = JSON.parseObject(reuslt);
				if (json.containsKey("resultCode")) {
					if (json.getString("resultCode").equals("1")) {
						msg.setOpeartorsDealRst("1"); // 0成功1失败
						msg.setOpeartorsDealCode(json.getString("errorMessage"));
						trafficNowDto.setMonthSign("sign");
						trafficNowDto.setDataRemaining("0");// 查询失败，剩余流量填零
					} else if (json.getString("resultCode").equals("0")) {
						msg.setSuccess(true);
						msg.setOpeartorsDealRst("0"); // 0成功1失败
						Gson gson = new Gson();
						DailydatausageBean dailyBean = gson.fromJson(reuslt,
								new TypeToken<DailydatausageBean>() {}.getType());
						List<OlMberFlows> listFlow = dailyBean.getTotalinfoList();
						//double dataRemaining = 0;
						double usedresCount = 0;
						if (listFlow != null && listFlow.size() > 0) {
							for (int i = 0; i < listFlow.size(); i++) {
								Totalinfo totalinfo = listFlow.get(i).getTotalinfo();
								usedresCount = usedresCount + Double.parseDouble(totalinfo.getUsedresCount());
							}
						}
				        DecimalFormat decimalFormat = new DecimalFormat("#0.000");//格式化设置 
				        // 东莞移动暂时展示已使用流量 2017-7-26 18:29:25
				        trafficNowDto.setMonthSign("sign");
						trafficNowDto.setDataRemaining(decimalFormat.format(usedresCount / 1024) +"");
					}
				} else {
					msg.setOpeartorsDealRst("1"); // 0成功1失败
					trafficNowDto.setMonthSign("sign");
					trafficNowDto.setDataRemaining("0");
				}
			} else {
				msg.setOutMessage("null");
				msg.setOpeartorsDealCode("");
				msg.setOpeartorsDealRst("1");
			}
			
		/*	String method="gprsrealtimeinfo";
			String ebid="0001000000083";
			String transid=appid+DigestUtils.dataString();
			String token = DigestUtils.sha256Hex(appid+passwd+transid);
			String httpUrl=url+method+"?"+"appid="+appid+"&"+"transid="
					+transid+"&"+"ebid="+ebid+"&"+"token="+token+"&msisdn="+msisdn;
			String reuslt = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
			log.info("(东莞移动剩余流量查询)EC返回--------------------reuslt="+reuslt);
			if (!StringUtils.isBlank(reuslt)) {
				// 记录参数
				if (reuslt.length() > 2000) {
					msg.setOutMessage(reuslt.substring(0, 2000));
				} else {
					msg.setOutMessage(reuslt);
				}
				if (StringUtils.isBlank(reuslt)) {
					msg.setOpeartorsDealRst("1"); // 0成功1失败
					trafficNowDto.setDataRemaining("0");// 查询失败，剩余流量填零
					msg.setTrafficDto(trafficNowDto);
					return msg;
				}
				double leftFlow = 0;
				//double totalFlow = 0;
				//double usedFlow = 0;
				JSONObject resultJSON = JSON.parseObject(reuslt);
				if (resultJSON.containsKey("message") && "正确".equals(resultJSON.getString("message"))) {
					msg.setSuccess(true);
					msg.setOpeartorsDealRst("0");
					JSONArray reusltArray = resultJSON.getJSONArray("result");
					for (int i = 0; i < reusltArray.size(); i++) {
						JSONObject object = (JSONObject) reusltArray.get(i);
						JSONArray jsonArray = object.getJSONArray("gprs");
						for (int j = 0; j < jsonArray.size(); j++) {
							JSONObject jsonObject = jsonArray.getJSONObject(j);
							String left = jsonObject.getString("left"); // 剩余流量 单位KB
							leftFlow += Double.valueOf(left);
							//String total = jsonObject.getString("total"); // 套餐总量 单位M
							//totalFlow += Double.valueOf(total);
							//String used = jsonObject.getString("used"); // 使用量 单位kB
							//usedFlow += Double.valueOf(used);
						}
					}
					DecimalFormat decimalFormat = new DecimalFormat("#0.000");//格式化设置
					trafficNowDto.setDataRemaining(decimalFormat.format(leftFlow / 1024));
					log.info("ec平台查询剩余流量返回>>>>>>>>leftFlow=" + leftFlow);
				} else {
					msg.setOpeartorsDealRst("1"); // 0成功1失败
					trafficNowDto.setDataRemaining("0");
					log.info("ec平台查询剩余流量异常>>>>>>>>");
				}

			} else {
				msg.setOutMessage("null");
				msg.setOpeartorsDealCode("");
				msg.setOpeartorsDealRst("1");
				log.info("调用ec平台查询剩余流量返回结果为空>>>>>>>>");
			}*/
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			msg.setOpeartorsDealRst("1"); // 0成功1失败
			trafficNowDto.setMonthSign("sign");
			trafficNowDto.setDataRemaining("0");// 查询失败，剩余流量填零
			msg.setTrafficDto(trafficNowDto);
			return msg;
		}
		// 在网，不在网 状态查询
		String method="onandoffrealsingle";
		String ebid="0001000000025";
		String transid=appid+DigestUtils.dataString();
		String token = DigestUtils.sha256Hex(appid+passwd+transid);
		String httpUrl=url+method+"?"+"appid="+appid+"&"+"transid="
				+transid+"&"+"ebid="+ebid+"&"+"token="+token+"&msisdn="+msisdn;
		String reusltonandoff = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		log.info("(在网，不在网 状态查询)EC返回--------------------reusltonandoff="+reusltonandoff);
		JSONObject resultJSON2 = JSON.parseObject(reusltonandoff);
		if (resultJSON2.containsKey("message") && "正确".equals(resultJSON2.getString("message"))) {
			JSONArray reusltArray = resultJSON2.getJSONArray("result");
			if (reusltArray != null) {
				JSONObject object = (JSONObject) reusltArray.get(0);
				String status = object.getString("status");
				if ("1".equals(status)) {
					trafficNowDto.setIsOnline(" (在网)");
				} else if("0".equals(status)){
					trafficNowDto.setIsOnline(" (不在网)");
				}
			}
		}
		
		/**
		 * 展示全部套餐明细
		 */
		String reuslt2 = "";
		try {
			reuslt2 = packageQuery(msisdn);
			if (reuslt2 != null) {
				if (!StringUtils.isBlank(reuslt2)) {
					Gson gson = new Gson();
					DailydatausageBean dailyBean = gson.fromJson(reuslt2,
							new TypeToken<DailydatausageBean>() {}.getType());
					List<OlMberFlows> packageVos = dailyBean.getPackageVos();
					if (packageVos != null && packageVos.size() > 0) {
						for (int i = 0; i < packageVos.size(); i++) {
							Totalinfo packageInfoVo = packageVos.get(i).getPackageInfoVo();
							String packageName = packageInfoVo.getPackageName();
							OperatorPlan operDto = operatorPlanExtDao.queryByPlanName(packageName);
							if (operDto != null) {
								PackagePlanDto packDto = new PackagePlanDto();
								packDto.setPackageName(packageName);
								packDto.setPackageCount(operDto.getQuantityMax()+operDto.getQuantityUnit());
								packDto.setPackageStartTime(packageInfoVo.getBeginTime().substring(0, 10));
//								packDto.setPackageEndTime(packageInfoVo.getEndTime().substring(0, 10));
								String timeUnit = operDto.getTimeUnit();
								Integer timeLength = operDto.getTimeLength();
								if (timeLength != null && timeLength != 0) {
									if ("0".equals(timeUnit)) {
										packDto.setPackageTimeLenth(timeLength+"天");
									} else if("1".equals(timeUnit)){
										packDto.setPackageTimeLenth(timeLength+"月");
									} else if("2".equals(timeUnit)){
										packDto.setPackageTimeLenth(timeLength+"季");
									} else if("3".equals(timeUnit)){
										packDto.setPackageTimeLenth(timeLength+"年");
									}
									listPack.add(packDto);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.info("成员套餐已订购信息实时查询接口异常==========================");
			log.info("接口返回reuslt2:=========================="+reuslt2);
			e.printStackTrace();
		}
		
		trafficNowDto.setListPack(listPack);
		msg.setTrafficDto(trafficNowDto);
		return msg;
	}
	// 成员套餐已订购信息实时查询
	public String packageQuery(String msisdn) {
		String method = "iot.member.package.query";
		Map<String, String> paramMap = EncryptUtils.genComMap();
		paramMap.put(ConstantsUntil.method, method);
		paramMap.put(ConstantsUntil.msisdn, msisdn);
		String secret = EncryptUtils.sign(paramMap, ConstantsUntil.secret);
		String httpUrl = ConstantsUntil.URL + "?" + EncryptUtils.genComUtr()
				+ "&" + ConstantsUntil.msisdn + "=" + msisdn + "&"
				+ ConstantsUntil.sign + "=" + secret + "&"
				+ ConstantsUntil.method + "=" + method;
		return HttpClientUtil.getInstance().sendHttpGet(httpUrl);
	}
	
	@Override
	protected UserTrafficQuery<SOAPMessage>.TrafficQueryResultMsg sendDayMessage(
			Object... args) {
		if (args == null || args.length == 0)
			throw new IllegalArgumentException();
		String iccid = (String) args[0];
		String dateStr = "yyyyMM";
		String strDate = "yyyy-MM-dd";
		if (args.length > 3) {
			strDate = (String) args[3];
			dateStr = strDate.replace("-", "").substring(0, 6);
		}
		TrafficQueryResultMsg msg = new TrafficQueryResultMsg();
		List<TrafficQueryDetailsDto> listDto = new ArrayList<TrafficQueryDetailsDto>();
		TrafficQueryDetailsDto dto = new TrafficQueryDetailsDto();
		dto.setSessionStartTime(strDate);
		try {
			LaoUser laoUser = laoUserDao.selectByIccid(iccid);
			String msisdn = laoUser.getMsisdn();
			
			boolean inDate = isInDate(strDate);
			strDate = strDate.replace("-", "");
			if (inDate) {
				DecimalFormat decimalFormat = new DecimalFormat("#0.000");//格式化设置
				
				String method="batchgprsusedbydate";
				String ebid="0001000000027";
				String transid=appid+DigestUtils.dataString();
				String token = DigestUtils.sha256Hex(appid+passwd+transid);
				String httpUrl=url+method+"?"+"appid="+appid+"&"+"transid="
						+transid+"&"+"ebid="+ebid+"&"+"token="+token+"&query_date="+strDate+"&msisdns="+msisdn;
				//System.out.println(httpUrl);
				String batchgprsused = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
				//String batchgprsused = ecMethod.batchgprsused(msisdn, strDate);
				log.info("(日查询)EC返回--------------------batchgprsused="+batchgprsused);
				if (batchgprsused.length() > 2000) {
					msg.setOutMessage(batchgprsused.substring(0, 2000));
				} else {
					msg.setOutMessage(batchgprsused);
				}
				if (!StringUtils.isBlank(batchgprsused)) {
					JSONObject json = JSON.parseObject(batchgprsused);
					if (json.containsKey("status") && "0".equals(json.getString("status"))) {
						msg.setSuccess(true);
						msg.setOpeartorsDealCode(json.getString("message"));
						JSONArray resultArray = json.getJSONArray("result");
						if (null != resultArray && resultArray.size() > 0) {
							JSONObject resultJSONObj = (JSONObject) resultArray.get(0);
							double parseDouble = Double.parseDouble(resultJSONObj.getString("GPRS"));
							dto.setDataVolume(decimalFormat.format(parseDouble / 1024));
						}
					} else {
						msg.setSuccess(false);
						msg.setOpeartorsDealCode(json.getString("message"));
						dto.setDataVolume("0");
					}
				} else {
					msg.setSuccess(false);
					msg.setOpeartorsDealCode("失败");
					dto.setDataVolume("0");
				}
			} else {
				String method = "iot.member.dailydatausage.query";
				Map<String, String> paramMap = EncryptUtils.genComMap();
				paramMap.put(ConstantsUntil.method, method);
				paramMap.put(ConstantsUntil.msisdn, msisdn);
				paramMap.put(ConstantsUntil.dateStr, dateStr);
				String secret = EncryptUtils.sign(paramMap, ConstantsUntil.secret);
				String httpUrl = ConstantsUntil.URL + "?"
						+ EncryptUtils.genComUtr() + "&" + ConstantsUntil.msisdn
						+ "=" + msisdn + "&" + ConstantsUntil.dateStr + "="
						+ dateStr + "&" + ConstantsUntil.sign + "=" + secret + "&"
						+ ConstantsUntil.method + "=" + method;
				msg.setInputMessage(httpUrl);
				String reuslt = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
				if (reuslt != null) {
					// 记录参数
					if (reuslt.length() > 2000) {
						msg.setOutMessage(reuslt.substring(0, 2000));
					} else {
						msg.setOutMessage(reuslt);
					}
					JSONObject json = JSON.parseObject(reuslt);
					if (json.containsKey("resultCode")) {
						if (json.getString("resultCode").equals("1")) {
							msg.setOpeartorsDealRst("1"); // 0成功1失败
							msg.setOpeartorsDealCode(json.getString("errorMessage"));
							dto.setDataVolume("0");// 接口调用失败是，日使用量填零
						} else if (json.getString("resultCode").equals("0")) {
							msg.setSuccess(true);
							msg.setOpeartorsDealRst("0"); // 0成功1失败
							Gson gson = new Gson();
							DailydatausageBean dailyBean = gson.fromJson(reuslt,
									new TypeToken<DailydatausageBean>() {}.getType());
							List<OlMberFlows> listOl = dailyBean.getOlMberDdFlows();
							if (listOl != null && listOl.size() > 0) {
								DecimalFormat decimalFormat = new DecimalFormat("#0.000");//格式化设置  
								for (int i = 0; i < listOl.size(); i++) {
									OlMberFlows olmber = listOl.get(i);
									if (strDate.equals(olmber.getDateStr().substring(0, 10).replace("-", ""))) {
										String flowSum = listOl.get(i).getFlowSum();
										double flowSumd = Double.parseDouble(flowSum);
										dto.setDataVolume(decimalFormat.format(flowSumd / 1024) +"");
									}
								}
							}
						}
					}else{
						msg.setOpeartorsDealRst("1"); // 0成功1失败
						dto.setDataVolume("0");
					}
				} else {
					msg.setOutMessage("null");
					msg.setOpeartorsDealCode("");
					msg.setOpeartorsDealRst("1");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg.setOpeartorsDealRst("1"); // 0成功1失败
			dto.setDataVolume("0");
			listDto.add(dto);
			msg.setListDto(listDto);
			return msg;
		}
		listDto.add(dto);
		msg.setListDto(listDto);
		return msg;
	}

	@Override
	protected UserTrafficQuery<SOAPMessage>.TrafficQueryResultMsg sendMonthMessage(
			Object... args) {
		if (args == null || args.length == 0)
			throw new IllegalArgumentException();
		String iccid = (String) args[0];
		String dateStr = "yyyyMM";
		String strDate = "yyyy-MM-dd";
		if (args.length > 3) {
			strDate = (String) args[3];
			dateStr = strDate.replace("-", "").substring(0,6);
		}
		TrafficQueryResultMsg msg = new TrafficQueryResultMsg();
		try {
			LaoUser laoUser = laoUserDao.selectByIccid(iccid);
			String msisdn = laoUser.getMsisdn();
			String method = "iot.member.dailydatausage.query";
			Map<String, String> paramMap = EncryptUtils.genComMap();
			paramMap.put(ConstantsUntil.method, method);
			paramMap.put(ConstantsUntil.msisdn, msisdn);
			paramMap.put(ConstantsUntil.dateStr, dateStr);
			String secret = EncryptUtils.sign(paramMap, ConstantsUntil.secret);
			String httpUrl = ConstantsUntil.URL + "?"
					+ EncryptUtils.genComUtr() + "&" + ConstantsUntil.msisdn
					+ "=" + msisdn + "&" + ConstantsUntil.dateStr + "="
					+ dateStr + "&" + ConstantsUntil.sign + "=" + secret + "&"
					+ ConstantsUntil.method + "=" + method;
			msg.setInputMessage(httpUrl);
			String reuslt = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
			if (reuslt != null) {
				// 记录参数
				if (reuslt.length() > 2000) {
					msg.setOutMessage(reuslt.substring(0, 2000));
				} else {
					msg.setOutMessage(reuslt);
				}
				JSONObject json = JSON.parseObject(reuslt);
				if (json.containsKey("resultCode")) {
					if (json.getString("resultCode").equals("1")) {
						msg.setOpeartorsDealRst("1"); // 0成功1失败
						msg.setOpeartorsDealCode(json.getString("errorMessage"));
					} else if (json.getString("resultCode").equals("0")) {
						List<TrafficQueryDetailsDto> listDto = new ArrayList<TrafficQueryDetailsDto>();
						msg.setSuccess(true);
						msg.setOpeartorsDealRst("0"); // 0成功1失败
						Gson gson = new Gson();
						DailydatausageBean dailyBean = gson.fromJson(reuslt,
								new TypeToken<DailydatausageBean>() {}.getType());
						List<OlMberFlows> listOl = dailyBean.getOlMberDdFlows();
//						double count = 0;
						if (listOl != null && listOl.size() > 0) {
					        DecimalFormat decimalFormat = new DecimalFormat("#0.000");//格式化设置  
							for (int i = 0; i < listOl.size(); i++) {
								TrafficQueryDetailsDto dto = new TrafficQueryDetailsDto();
								OlMberFlows olmber = listOl.get(i);
								String flowSum = olmber.getFlowSum();
								double flowSumd = Double.parseDouble(flowSum);
								dto.setDataVolume(decimalFormat.format(flowSumd / 1024) +"");
//								count = count + flowSumd;
								String date = olmber.getDateStr();
								dto.setSessionStartTime(date.substring(0,10));
								listDto.add(dto);
							}
							listDto = addList(listDto, strDate);
						} else {
							listDto = addList(new ArrayList<TrafficQueryDetailsDto>() , strDate);
						}
						/*TrafficQueryDetailsDto dto = new TrafficQueryDetailsDto();
						dto.setDataVolume(count + "");
						dto.setSessionStartTime(strDate.substring(0,7));
						listDto.add(dto);*/
						msg.setListDto(listDto);
					}
				} else {
					msg.setOpeartorsDealRst("1"); // 0成功1失败
				}
			} else {
				msg.setOutMessage("null");
				msg.setOpeartorsDealCode("");
				msg.setOpeartorsDealRst("1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg.setOpeartorsDealRst("1"); // 0成功1失败
			return msg;
		}
		return msg;
	}

	@Override
	public TrafficQueryResultMsg sendBatchQueryMessage(Object... args) {
		TrafficQueryResultMsg msg = new TrafficQueryResultMsg();
		LaoTrafficDetailDto laoDto = new LaoTrafficDetailDto();
		if (args == null || args.length == 0)
			throw new IllegalArgumentException();
		String iccid = (String) args[0];
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		// 查询日期为昨天
		String date = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		String dateStr = date.replace("-", "").substring(0, 6);
		try {
			LaoUser laoUser = laoUserDao.selectByIccid(iccid);
			String msisdn = laoUser.getMsisdn();
			String method = "iot.member.dailydatausage.query";
			Map<String, String> paramMap = EncryptUtils.genComMap();
			paramMap.put(ConstantsUntil.method, method);
			paramMap.put(ConstantsUntil.msisdn, msisdn);
			paramMap.put(ConstantsUntil.dateStr, dateStr);
			String secret = EncryptUtils.sign(paramMap, ConstantsUntil.secret);
			String httpUrl = ConstantsUntil.URL + "?"
					+ EncryptUtils.genComUtr() + "&" + ConstantsUntil.msisdn
					+ "=" + msisdn + "&" + ConstantsUntil.dateStr + "="
					+ dateStr + "&" + ConstantsUntil.sign + "=" + secret + "&"
					+ ConstantsUntil.method + "=" + method;
			msg.setInputMessage(httpUrl);
			String reuslt = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
			if (reuslt != null) {
				// 记录参数
				if (reuslt.length() > 2000) {
					msg.setOutMessage(reuslt.substring(0, 2000));
				} else {
					msg.setOutMessage(reuslt);
				}
				JSONObject json = JSON.parseObject(reuslt);
				if (json.getString("resultCode").equals("0")) {
					msg.setSuccess(true);
					msg.setOpeartorsDealRst("0"); // 0成功1失败
					msg.setOpeartorsDealCode("");
					Gson gson = new Gson();
					DailydatausageBean dailyBean = gson.fromJson(reuslt,
							new TypeToken<DailydatausageBean>() {}.getType());
					List<OlMberFlows> listOl = dailyBean.getOlMberDdFlows();
					if (listOl != null && listOl.size() > 0) {
						for (int i = 0; i < listOl.size(); i++) {
							OlMberFlows olmber = listOl.get(i);
							if (date.equals(olmber.getDateStr().substring(0, 10))) {
								String flow4G = listOl.get(i).getDdFlowNum4G();
								double dFlow4G = Double.parseDouble(flow4G);
								laoDto.setUseCount(new BigDecimal(dFlow4G));
							}
						}
					}
				} else {
					msg.setOpeartorsDealRst("1"); // 0成功1失败
					msg.setOpeartorsDealCode(json.getString("errorMessage"));
					laoDto.setUseCount(new BigDecimal(0));
				}
			} else {
				msg.setOutMessage("null");
				msg.setOpeartorsDealCode("");
				msg.setOpeartorsDealRst("1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg.setOpeartorsDealCode("");
			msg.setOpeartorsDealRst("1");
			laoDto.setUseCount(new BigDecimal(0));
			laoDto.setDataCycle(date.replace("-", ""));
		}
		laoDto.setDataCycle(date.replace("-", ""));
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
		cal.set(Calendar.MONTH, Integer.parseInt(date.substring(5, 7)) - 1);
		cal.set(Calendar.DATE, 1);
		cal.roll(Calendar.DATE, -1);
		// 获取该月总天数
		int maxDate = cal.get(Calendar.DATE);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date month = null;
		try {
			month = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(month);// month 为指定月份任意日期
		cal2.set(Calendar.DAY_OF_MONTH, 1);// 从一号开始
		c:
		for (int i = 0; i < maxDate; i++, cal2.add(Calendar.DATE, 1)) {
			Date d = cal2.getTime();
			String df = sdf.format(d);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动
			String nowDate = sdf.format(calendar.getTime());
			if (df.equals(nowDate)) {
				break;
			}
			for (int j = 0; j < list.size(); j++) {
				TrafficQueryDetailsDto dto = list.get(j);
				if (df.equals(dto.getSessionStartTime())) {
					listNew.add(dto);
					continue c;
				}
			}
			TrafficQueryDetailsDto dtoNew = new TrafficQueryDetailsDto();
			dtoNew.setSessionStartTime(df);
			dtoNew.setDataVolume("0");
			listNew.add(dtoNew);
		}
		return listNew;
	}
	
	// 判断时间区间
		public static boolean isInDate(String strDate) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
			Date date2 = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date2);
			calendar.add(Calendar.DAY_OF_WEEK, -1);
			Date dateBefor = calendar.getTime();
			calendar.add(Calendar.DAY_OF_WEEK, -6);
			Date dateAfter = calendar.getTime();
			Date time = null;
			try {
				time = sdf.parse(strDate);
				dateAfter = sdf.parse(sdf.format(dateAfter));
				dateBefor = sdf.parse(sdf.format(dateBefor));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if (time.before(dateBefor) && time.after(dateAfter) || time.equals(dateAfter) || time.equals(dateBefor)) {
				//System.out.println(sdf.format(time) + "在此区间");
				return true;
			} else {
				//System.out.println(sdf.format(time) + "不在此区间");
				return false;
			}
		}
		
}
