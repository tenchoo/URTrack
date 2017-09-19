package com.urt.Ability.http.server;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;


import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;
import org.eclipse.jetty.util.log.Log;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.urt.Ability.http.util.ToolsUtil;
import com.urt.Ability.unicom.service.GetTerminalDetailsService;
import com.urt.Ability.unicom.service.GetTerminalRatingService;
import com.urt.Ability.unicom.vo.GetTerminalDetailsResponse;
import com.urt.Ability.unicom.vo.GetTerminalRatingResponse;
import com.urt.common.util.JsonUtil;
import com.urt.dto.http.DayFlowQuery;
import com.urt.dto.http.FlowInfo;
import com.urt.dto.http.SurplusFlowQuery;
import com.urt.dto.http.SurplusFlowQueryAndPackageCountDto;
import com.urt.dto.http.TerminalDetailRatingDto;
import com.urt.dto.traffic.TrafficQueryDetailsDto;
import com.urt.dto.traffic.TrafficQueryNowDto;
import com.urt.interfaces.device.DeviceService;
import com.urt.interfaces.http.FlowQueryServer;
import com.urt.mapper.ext.LaoUserExtMapper;
import com.urt.modules.nosql.redis.JedisClusterFactory;
import com.urt.po.LaoUser;
import com.urt.service.traffic.TrafficQueryServiceImpl;

import net.sf.json.JSONArray;
import redis.clients.jedis.JedisCluster;

/**
 * 
 * @author wangxb20 流量查询
 */
@Service("flowQueryServer")
public class FlowQueryServerImpl implements FlowQueryServer {

	@Autowired
	TrafficQueryServiceImpl trafficQueryServiceImpl;
	@Autowired
	LaoUserExtMapper laoUserDao;
	@Autowired
	protected JedisClusterFactory jedisCluster;
    
	@Autowired
	private  GetTerminalRatingService ratingService;
	
	@Autowired
	private GetTerminalDetailsService detailsService;
	@Autowired
	private DeviceService deviceService;

	@Value("${flowQueryRedisTime}")
	private String flowQueryRedisTime;

	protected static final Logger logger = Logger.getLogger(FlowQueryServerImpl.class);
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lenovo.LAOAPI.interfaces.http.FlowQueryServer#surplusFlowQuery(java.
	 * util.Map) <p>剩余流量查询接口</p>
	 */
	@Override
	public SurplusFlowQuery surplusFlowQuery(Map<String, String> reqInfo) {
		SurplusFlowQuery s = new SurplusFlowQuery();
		String iccid = reqInfo.get("iccid");
		LaoUser iccidLib = laoUserDao.selectByIccid(iccid);
		TrafficQueryNowDto trafficQueryNowDto = null;
		if (null == iccidLib) {
			s.setRespCode("9999");
			s.setRespDesc("ICCID NOT EXIST");
			return s;
		}
		try {
			if ("2".equals(iccidLib.getOperatorsId().toString())) {

				JedisCluster jedisClust = jedisCluster.getObject();

				String dayData = jedisClust.get(iccid + "_" + "surplusFlowQuery");
				if (null == dayData) {
					trafficQueryNowDto = trafficQueryServiceImpl.doNowTrafficQuery(iccid);
					if (null != trafficQueryNowDto && null != trafficQueryNowDto.getDataRemaining()) {
						JSONArray fromObject = JSONArray.fromObject(trafficQueryNowDto);
						jedisClust.set(iccid + "_" + "surplusFlowQuery", fromObject.toString());
						// 设置count和数据的过期时间 12*60*60
						jedisClust.expire(iccid + "_" + "surplusFlowQuery", Integer.valueOf(flowQueryRedisTime));
					}
				} else {
					JSONArray jsonArray = JSONArray.fromObject(dayData);
					for (int i = 0; i < jsonArray.size(); i++) {
						net.sf.json.JSONObject jsonObject = jsonArray.getJSONObject(i);
						trafficQueryNowDto = JsonUtil.fromJson(jsonObject.toString(), TrafficQueryNowDto.class);

					}
				}

			} else {

				trafficQueryNowDto = trafficQueryServiceImpl.doNowTrafficQuery(iccid);
			}
			if (null != trafficQueryNowDto) {
				
				if (ToolsUtil.checkStringIsNull(trafficQueryNowDto.getExpMsg())) {
					
					//如果有次标记则返回已使用流量
					if ("sign".equals(trafficQueryNowDto.getMonthSign())) {
						
						s.setUserFlowValue(trafficQueryNowDto.getDataRemaining());
					}else{
						s.setSurplusFlowValue(trafficQueryNowDto.getDataRemaining());
					}
					s.setRespDesc("success");
					s.setRespCode("0000");
				} else {
					s.setRespCode("0001");
					s.setRespDesc("剩余流量查询报错");
					s.setSurplusFlowValue("");
				}

			} else {
				s.setRespCode("0001");
				s.setRespDesc("剩余流量查询报错");
				s.setSurplusFlowValue("");
			}
		} catch (Exception e) {
			s.setRespCode("0001");
			s.setRespDesc("剩余流量查询报错");
			s.setSurplusFlowValue("");
		}
		return s;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lenovo.LAOAPI.interfaces.http.FlowQueryServer#dayFlowQuery(java.util.
	 * Map) <p>日流量查询接口</p>
	 */
	@Override
	public DayFlowQuery dayFlowQuery(Map<String, String> reqInfo) {

		List<TrafficQueryDetailsDto> dayFlowInfo = null;
		DayFlowQuery dayFlowQuery = new DayFlowQuery();
		List<FlowInfo> d = new ArrayList<FlowInfo>();
		String iccid = reqInfo.get("iccid");
		String date = reqInfo.get("dayDate");

		LaoUser iccidLib = laoUserDao.selectByIccid(iccid);

		if (null == iccidLib) {
			dayFlowQuery.setRespCode("9999");
			dayFlowQuery.setRespDesc("ICCID NOT EXIST");
			return dayFlowQuery;
		}

		String str[] = date.split("-");
		if (ToolsUtil.checkStringIsNull(date) || str.length <= 1) {
			dayFlowQuery.setRespCode("9999");
			dayFlowQuery.setRespDesc("date is null or date formate error{2017-03-15}");
			return dayFlowQuery;
		}
		try {
			// 判断此卡状态是否未移动卡
			if ("2".equals(iccidLib.getOperatorsId().toString())) {
				String[] dt = date.split("-");
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < dt.length; i++) {
					sb.append(dt[i]);
				}
				JedisCluster jedisClust = jedisCluster.getObject();

				String dayData = jedisClust.get(iccid + "_" + sb.toString() + "_dayFlowQuery");
				if (null == dayData) {
					dayFlowInfo = trafficQueryServiceImpl.doDayTrafficQuery(iccid, date);
					if (null != dayFlowInfo && dayFlowInfo.size() > 0) {
						JSONArray fromObject = JSONArray.fromObject(dayFlowInfo);
						jedisClust.set(iccid + "_" + sb.toString() + "_dayFlowQuery", fromObject.toString());
						// 设置count和数据的过期时间 12*60*60
						jedisClust.expire(iccid + "_" + sb.toString() + "_dayFlowQuery",
								Integer.valueOf(flowQueryRedisTime));
					}
				} else {
					JSONArray jsonArray = JSONArray.fromObject(dayData);
					dayFlowInfo = jsonArray.toList(jsonArray, TrafficQueryDetailsDto.class);
				}

			} else {
				dayFlowInfo = trafficQueryServiceImpl.doDayTrafficQuery(iccid, date);
			}

			if (null != dayFlowInfo && dayFlowInfo.size() > 0) {
				for (int a = 0; a < dayFlowInfo.size(); a++) {
					FlowInfo day = new FlowInfo();
					day.setDatePoint(dayFlowInfo.get(a).getSessionStartTime());
					day.setFlowSize(dayFlowInfo.get(a).getDataVolume());
					d.add(day);
				}
				dayFlowQuery.setFlowInfo(d);
				dayFlowQuery.setRespCode("0000");
				dayFlowQuery.setRespDesc("success");
			} else {
				dayFlowQuery.setRespCode("0001");
				dayFlowQuery.setRespDesc("日流量查询失败");
			}

		} catch (Exception e) {
			dayFlowQuery.setRespCode("0001");
			dayFlowQuery.setRespDesc("日流量查询失败");
			e.printStackTrace();
		}
		return dayFlowQuery;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lenovo.LAOAPI.interfaces.http.FlowQueryServer#monthFlowQuery(java.
	 * util.Map) <p>月流量查询接口</p>
	 * 
	 */
	@Override
	public DayFlowQuery monthFlowQuery(Map<String, String> reqInfo) {
		String iccid = reqInfo.get("iccid");
		String date = reqInfo.get("dayDate");
		List<TrafficQueryDetailsDto> dayFlowInfo = null;
		DayFlowQuery monthFlowQuery = new DayFlowQuery();
		List<FlowInfo> d = new ArrayList<FlowInfo>();

		LaoUser iccidLib = laoUserDao.selectByIccid(iccid);
		if (null == iccidLib) {
			monthFlowQuery.setRespCode("9999");
			monthFlowQuery.setRespDesc("ICCID NOT EXIST");
			return monthFlowQuery;
		}
		String str[] = date.split("-");
		if (ToolsUtil.checkStringIsNull(date) || str.length <= 1) {
			monthFlowQuery.setRespCode("9999");
			monthFlowQuery.setRespDesc("date is null or date formate error{2017-03}");
			return monthFlowQuery;
		}
		try {

			// 判断此卡状态是否未移动卡
			if ("2".equals(iccidLib.getOperatorsId().toString())) {
				String[] dt = date.split("-");
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < dt.length; i++) {
					sb.append(dt[i]);
				}
				JedisCluster jedisClust = jedisCluster.getObject();

				String dayData = jedisClust.get(iccid + "_" + sb.toString() + "_monthQuery");
				if (null == dayData) {
					dayFlowInfo = trafficQueryServiceImpl.doMonthTrafficQuery(iccid, date);
					if (null != dayFlowInfo && dayFlowInfo.size() > 0) {
						JSONArray fromObject = JSONArray.fromObject(dayFlowInfo);
						jedisClust.set(iccid + "_" + sb.toString() + "_monthQuery", fromObject.toString());
						// 设置count和数据的过期时间 12*60
						jedisClust.expire(iccid + "_" + sb.toString() + "_monthQuery",
								Integer.valueOf(flowQueryRedisTime));
					}
				} else {
					JSONArray jsonArray = JSONArray.fromObject(dayData);
					dayFlowInfo = jsonArray.toList(jsonArray, TrafficQueryDetailsDto.class);
				}

			} else {
				dayFlowInfo = trafficQueryServiceImpl.doMonthTrafficQuery(iccid, date);
			}
			if (null != dayFlowInfo && dayFlowInfo.size() > 0) {
				for (int a = 0; a < dayFlowInfo.size(); a++) {
					FlowInfo month = new FlowInfo();
					month.setDatePoint(dayFlowInfo.get(a).getSessionStartTime());
					month.setFlowSize(dayFlowInfo.get(a).getDataVolume());
					d.add(month);
				}
				monthFlowQuery.setFlowInfo(d);
				monthFlowQuery.setRespCode("0000");
				monthFlowQuery.setRespDesc("success");
			} else {
				monthFlowQuery.setRespCode("0001");
				monthFlowQuery.setRespDesc("月流量查询失败");
			}

		} catch (Exception e) {
			monthFlowQuery.setRespCode("0001");
			monthFlowQuery.setRespDesc("月流量查询失败");
			e.printStackTrace();
		}
		return monthFlowQuery;
	}

	public static void main(String[] args) {
		String str = "201703";
		System.out.println(str.split("-").length);
	}

	/**
	 * 联通剩余流量加第一个包的到期时间和总包数
	 */
	@Override
	public SurplusFlowQueryAndPackageCountDto surplusFlowQueryAndPackageCount(Map<String, String> reqInfo) {
		String iccid = reqInfo.get("iccid");
		LaoUser iccidLib = laoUserDao.selectByIccid(iccid);
		SurplusFlowQueryAndPackageCountDto sc = new SurplusFlowQueryAndPackageCountDto();
		if (null == iccidLib) {
			sc.setRespCode("9999");

			sc.setRespDesc("ICCID NOT EXIST");
			return sc;
		}
		if (!"1".equals(iccidLib.getOperatorsId().toString())) {
			sc.setRespCode("7777");
			sc.setRespDesc("ICCID NOT CUCC");
			return sc;
		}
		try {
			Map<String, String> curentPlan = deviceService.getCurentPlan(iccid);
			System.out.println("返回的值>>>>>>>>>>>>>>>>>>>>>>" + curentPlan.toString());
			if (null != curentPlan) {
				sc.setRespCode("0000");
				sc.setRespDesc("success");
				sc.setSurplusFlowValue(curentPlan.get("capacity"));
				String string = curentPlan.get("expirationDate");
				if (null == string || "".equals(string)) {
					sc.setCurrentPackExpirationTime("");
				} else {
					String substring = string.substring(0, 10);
					sc.setCurrentPackExpirationTime(substring);
				}
				sc.setPackCount(curentPlan.get("packageCount"));
			} else {
				sc.setRespCode("0001");
				sc.setRespDesc("剩余流量,当前包过期时间和包数量查询失败");
			}
		} catch (Exception e) {
			sc.setRespCode("0001");
			sc.setRespDesc("剩余流量,当前包过期时间和包数量查询报错");
			sc.setSurplusFlowValue("");
			sc.setCurrentPackExpirationTime("");
			sc.setPackCount("");
		}
		return sc;
	}

	/**
	 * 当前包套餐档次,当前包的激活时间,当前包套餐截止时间,当前包已用流量,当前包剩余流量
	 * 
	 */
	public TerminalDetailRatingDto currentPackage(Map<String, String> reqInfo) {
		String iccid = reqInfo.get("iccid");
		LaoUser iccidLib = laoUserDao.selectByIccid(iccid);
		TerminalDetailRatingDto dto = new TerminalDetailRatingDto();
		if (null == iccidLib) {
			dto.setRespCode("9999");
			
			dto.setRespDesc("ICCID NOT EXIST");
		    return dto;
		}
		if (!"1".equals(iccidLib.getOperatorsId().toString())) {
			dto.setRespCode("7777");
			dto.setRespDesc("ICCID NOT CUCC");
			return dto;
		}
		String messageId = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()).toString();
		try {
			// 当前包套餐档次、当前包的激活时间、当前包套餐截止时间、当前包已用流量、当前包剩余流量。
			String currentSurplus="";
			List<String> list = new ArrayList<String>();
			list.add(iccid);
			SOAPMessage mesg = detailsService.call(list, messageId);
			GetTerminalDetailsResponse response = (GetTerminalDetailsResponse) detailsService.parseMessage(mesg);
			com.alibaba.dubbo.common.json.JSONObject jsonObject = (com.alibaba.dubbo.common.json.JSONObject) com.alibaba.dubbo.common.json.JSON
					.parse(response.toString());
			com.alibaba.dubbo.common.json.JSONArray array = jsonObject.getArray("list");
			for (int i = 0; i < array.length(); i++) {
				com.alibaba.dubbo.common.json.JSONObject jsonObj = (com.alibaba.dubbo.common.json.JSONObject) array
						.get(i);
				com.alibaba.dubbo.common.json.JSONArray jsonArray2 = jsonObj.getArray("rating");
				com.alibaba.dubbo.common.json.JSONObject resultJSon = (com.alibaba.dubbo.common.json.JSONObject) jsonArray2
						.get(0);
				String primaryDataRemaining = resultJSon.getString("primaryDataRemaining"); // 当前包已用流量
				String termStartDate = resultJSon.getString("termStartDate"); // 当前包的激活时间
				String termEndDate = resultJSon.getString("termEndDate"); // 当前包套餐截止时间
				String totalPrimaryIncludedData = resultJSon.getString("totalPrimaryIncludedData"); // 当前包总流量
				String renewalRatePlan = resultJSon.getString("renewalRatePlan"); // 当前包套餐档次
				if ("".equals(primaryDataRemaining) || "".equals(termStartDate)|| "".equals(termEndDate) ||"".equals(totalPrimaryIncludedData)) {
					dto.setRespCode("1004");
					dto.setRespDesc("no result");
					logger.info(" 联通当前包信息查询>>>>>>>>>>>>无结果");
					
				}else {
					termStartDate = BJDate(termStartDate);
					termEndDate = BJDate(termEndDate);
					Double valueOf2 = Double.valueOf(totalPrimaryIncludedData);
					Double valueOf = Double.valueOf(primaryDataRemaining);
					currentSurplus= String.valueOf(valueOf2 - valueOf);
					dto.setRespCode("0000");
					dto.setRespDesc("success");
					if ("".equals(renewalRatePlan)) {
						try {
							SOAPMessage soapMessage = ratingService.call(iccid,messageId);
							GetTerminalRatingResponse ws = (GetTerminalRatingResponse) ratingService.parseMessage(soapMessage);
							com.alibaba.dubbo.common.json.JSONObject jsonws = (com.alibaba.dubbo.common.json.JSONObject) com.alibaba.dubbo.common.json.JSON
									.parse(ws.toString());
							com.alibaba.dubbo.common.json.JSONArray arrayJson = jsonws.getArray("list");
							com.alibaba.dubbo.common.json.JSONObject  object = (com.alibaba.dubbo.common.json.JSONObject) arrayJson.get(0);
							renewalRatePlan=object.getString("ratePlanName");
							logger.info("查询>>>>>>>>>>>当前包套餐档次正常"+renewalRatePlan);
							
						} catch (Exception e) {
							e.printStackTrace();
							logger.info("查询>>>>>>>>>>>当前包套餐档次异常");
						}
						
					}
				}
                dto.setPrimaryDataRemaining(primaryDataRemaining);
                dto.setRenewalRatePlan(renewalRatePlan);
                dto.setTermEndDate(termEndDate);
                dto.setTermStartDate(termStartDate);
                dto.setTotalPrimaryIncludedData(totalPrimaryIncludedData);
                dto.setCurrentSurplus(currentSurplus);

			}

		} catch (Exception e) {
			dto.setRespCode("9999");
			dto.setRespDesc("other errors");
			logger.info("联通当前包信息查询>>>>>>>>>>>>失败");
		}

		return dto;
	}

	private static String BJDate(String dateParam) throws ParseException {
		String date = dateParam.substring(0, 19).replace('T', ' ');
		// 将世界时间转化为北京时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sdf.parse(date));
		calendar.add(Calendar.HOUR, 8);// 整数往后推,负数往前移动
		String newDate = sdf.format(calendar.getTime());

		return newDate;
	}

}
