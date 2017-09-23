package com.urt.Ability.ZheJiangCMC;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.urt.Ability.collect.UserTrafficQuery;
import com.urt.dto.traffic.TrafficQueryDetailsDto;
import com.urt.dto.traffic.TrafficQueryNowDto;
import com.urt.interfaces.ZheJiangCMC.MemberMethodZJ;
import com.urt.interfaces.ZheJiangCMC.QueryMethodZJ;
import com.urt.mapper.ext.IccidLibExtMapper;
import com.urt.mapper.ext.LaoUserExtMapper;
import com.urt.po.LaoUser;

/**
 * 浙江移动流量查询
 * 
 * @author
 *
 */
@Service("userTrafficQueryCMCCZJ")
public class UserTrafficQueryCMCCZJ extends UserTrafficQuery<SOAPMessage> {

	private Logger log = Logger.getLogger(getClass());

	@Autowired
	private QueryMethodZJ ecMethod;
	@Autowired
	private IccidLibExtMapper iccidLibExtMapper;

	@Autowired
	private MemberMethodZJ memberMethodZJ;

	@Autowired
	private LaoUserExtMapper laoUserDao;

	/**
	 * 日流量查询
	 */
	@Override
	protected UserTrafficQuery<SOAPMessage>.TrafficQueryResultMsg sendDayMessage(Object... args) {
		TrafficQueryResultMsg msg = new TrafficQueryResultMsg();
		List<TrafficQueryDetailsDto> list = new ArrayList<TrafficQueryDetailsDto>();
		TrafficQueryDetailsDto dayFlowQuery = new TrafficQueryDetailsDto();
		DecimalFormat decimalFormat = new DecimalFormat("#0.000");//格式化设置
		if (args == null || args.length == 0)
			throw new IllegalArgumentException();
		String iccids = (String) args[0];
		String date = (String) args[3];//YYYY-MM-dd
		LaoUser laoUser = laoUserDao.selectByIccid(iccids);
		String msisdn = "";
		if (laoUser != null) {
			msisdn = laoUser.getMsisdn();
		}
		dayFlowQuery.setSessionStartTime(date);
		boolean inDate = isInDate(date);
		date = date.replace("-", "");
		if (inDate) {
			// 对时间进行校验,ec只支持当前系统时间-1,7天内有效
			/*String[] split = iccids.split(",");
			StringBuilder sb = new StringBuilder();
			// TrafficQueryDetailsDto dto = new TrafficQueryDetailsDto();
			for (int i = 0; i < split.length; i++) {
				IccidLib selectByIccid = iccidLibExtMapper.selectByIccid(split[i]);
				sb.append(selectByIccid.getMsisdn() + "_");
			}
			String msisdns = sb.toString().substring(0, sb.toString().length() - 1);*/
			String batchgprsused = ecMethod.batchgprsused(msisdn, date);
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
						/*for (int i = 0; i < resultArray.size(); i++) {
							JSONObject resultJSONObj = (JSONObject) resultArray.get(i);
							TrafficQueryDetailsDto dayFlowQuery = new TrafficQueryDetailsDto();
							double parseDouble = Double.parseDouble(resultJSONObj.getString("GPRS"));
							dayFlowQuery.setDataVolume(String.valueOf(parseDouble / 1024));
							// dayFlowQuery.setDuration(duration);
							list.add(dayFlowQuery);
						}*/
						JSONObject resultJSONObj = (JSONObject) resultArray.get(0);
						double parseDouble = Double.parseDouble(resultJSONObj.getString("GPRS"));
						dayFlowQuery.setDataVolume(decimalFormat.format(parseDouble / 1024));
						list.add(dayFlowQuery);
					}
				} else {
					msg.setSuccess(false);
					msg.setOpeartorsDealCode(json.getString("message"));
					dayFlowQuery.setDataVolume("0");
					list.add(dayFlowQuery);
				}
			} else {
				msg.setSuccess(false);
				msg.setOpeartorsDealCode("失败");
				dayFlowQuery.setDataVolume("0");
				list.add(dayFlowQuery);
			}
		} else {
			// 不在ec的查询区间内,则查询浙江移动自己的物联网平台
			String result = memberMethodZJ.gprsFlowQueryInfo(msisdn, date, date);
			if (result.length() > 2000) {
				msg.setOutMessage(result.substring(0, 2000));
			} else {
				msg.setOutMessage(result);
			}
			log.info("(日查询)浙江移动自己的物联网平台返回--------------------result="+result);
			if (!StringUtils.isBlank(result)) {
				JSONObject parseObject = JSON.parseObject(result);
				JSONObject jsonObject = parseObject.getJSONObject("RESP_PARAM");
				JSONObject jsonObject2 = jsonObject.getJSONObject("BUSI_INFO");
				if (jsonObject2 != null) {
					JSONObject jsonObject3 = jsonObject2.getJSONObject("RETURN_LIST");
					if (jsonObject3 != null) {
						msg.setSuccess(true);
						msg.setOpeartorsDealCode("成功");
						JSONArray jsonArray = jsonObject3.getJSONArray("RETURN_INFO");
						if (jsonArray != null) {
							JSONObject  resultJson = (JSONObject) jsonArray.get(0);
							//String useDate = resultJson.getString("USE_DATE"); //日期
							String gprsNum = resultJson.getString("GPRS_NUM");  //单天流量使用汇总量（kb）
							double gprsDoub = Double.parseDouble(gprsNum);
							dayFlowQuery.setDataVolume(decimalFormat.format(gprsDoub / 1024));
						} else {
							dayFlowQuery.setDataVolume("0");
						}
						list.add(dayFlowQuery);
					} else {
						msg.setSuccess(false);
						msg.setOpeartorsDealCode("失败");
					}
				} else {
					msg.setSuccess(false);
					msg.setOpeartorsDealCode("失败");
				}
			} else {
				msg.setSuccess(false);
				msg.setOpeartorsDealCode("失败");
			}
		}
		msg.setListDto(list);
		return msg;
	}

	/**
	 * 
	 * 月流量查询
	 */
	@Override
	protected UserTrafficQuery<SOAPMessage>.TrafficQueryResultMsg sendMonthMessage(Object... args) {
		TrafficQueryResultMsg msg = new TrafficQueryResultMsg();
		List<TrafficQueryDetailsDto> listDto = new ArrayList<TrafficQueryDetailsDto>();
		if (args == null || args.length == 0)
			throw new IllegalArgumentException();
		String iccid = (String) args[0];
		String month = (String) args[3];//yyyy-MM-01
		String monthLast = endDate(month);//yyyy-MM-dd
		String startDate = month.replace("-", "");
		String endDate = monthLast.replace("-", "");
		DecimalFormat decimalFormat = new DecimalFormat("#0.000");//格式化设置
		LaoUser laoUser = laoUserDao.selectByIccid(iccid);
		String gprsFlowQueryInfo = memberMethodZJ.gprsFlowQueryInfo(laoUser.getMsisdn(), startDate, endDate);
		if (gprsFlowQueryInfo.length() > 2000) {
			msg.setOutMessage(gprsFlowQueryInfo.substring(0, 2000));
		} else {
			msg.setOutMessage(gprsFlowQueryInfo);
		}
		log.info("(月查询)浙江移动自己的物联网平台返回--------------------gprsFlowQueryInfo="+gprsFlowQueryInfo);
		if (!StringUtils.isBlank(gprsFlowQueryInfo)) {
			JSONObject parseObject = JSON.parseObject(gprsFlowQueryInfo);
			JSONObject jsonObject = parseObject.getJSONObject("RESP_PARAM");
			JSONObject jsonObject2 = jsonObject.getJSONObject("BUSI_INFO");
			if (jsonObject2 != null) {
				JSONObject jsonObject3 = jsonObject2.getJSONObject("RETURN_LIST");
				if (jsonObject3 != null) {
					msg.setSuccess(true);
					msg.setOpeartorsDealCode("成功");
					JSONArray jsonArray = jsonObject3.getJSONArray("RETURN_INFO");
					if (jsonArray != null) {
						for (int i = 0; i < jsonArray.size(); i++) {
							TrafficQueryDetailsDto dto = new TrafficQueryDetailsDto();
							JSONObject  resultJson=(JSONObject) jsonArray.get(i);
							String useDate = resultJson.getString("USE_DATE"); //日期
							String gprsNum = resultJson.getString("GPRS_NUM");  //单天流量使用汇总量（kb）
							double gprsDoub = Double.parseDouble(gprsNum);
							try {
								SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
								SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
								dto.setSessionStartTime(sdf2.format(sdf1.parse(useDate)));
							} catch (ParseException e) {
								e.printStackTrace();
							}
							dto.setDataVolume(decimalFormat.format(gprsDoub / 1024));
							listDto.add(dto);
						}
					}
				} else {
					msg.setSuccess(false);
					msg.setOpeartorsDealCode("失败");
				}
			} else {
				msg.setSuccess(false);
				msg.setOpeartorsDealCode("失败");
			}
		} else {
			msg.setSuccess(false);
			msg.setOpeartorsDealCode("失败");
		}
		listDto = addList(listDto, month);
		msg.setListDto(listDto);
		return msg;
	}

	/**
	 * 浙江移动剩余流量查询 调用ec查询
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
		trafficNowDto.setMonthSign("sign");
		trafficNowDto.setDataRemaining("0");
		try {
			//String reuslt = ecMethod.gprsrealtimeinfo(msisdn);
			//log.info("(剩余流量查询)EC返回--------------------reuslt="+reuslt);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -2);
			String begin_date = sdf.format(cal.getTime());
			String end_date = sdf.format(new Date());
			String reuslt = memberMethodZJ.gprsFlowQueryInfo(msisdn, begin_date, end_date);
			log.info("(已使用流量查询)浙江接口返回--------------------reuslt="+reuslt);
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
				if (!StringUtils.isBlank(reuslt)) {
					JSONObject parseObject = JSON.parseObject(reuslt);
					JSONObject jsonObject = parseObject.getJSONObject("RESP_PARAM");
					JSONObject jsonObject2 = jsonObject.getJSONObject("BUSI_INFO");
					if (jsonObject2 != null) {
						JSONObject jsonObject3 = jsonObject2.getJSONObject("RETURN_LIST");
						if (jsonObject3 != null) {
							msg.setSuccess(true);
							msg.setOpeartorsDealCode("成功");
							JSONArray jsonArray = jsonObject3.getJSONArray("RETURN_INFO");
							if (jsonArray != null) {
								for (int i = 0; i < jsonArray.size(); i++) {
									JSONObject resultJson = (JSONObject) jsonArray.get(i);
									String totalNum = resultJson.getString("GPRS_TOTAL_NUM");// 累计使用量
									double totalDou = Double.parseDouble(totalNum);
									if (leftFlow < totalDou) {
										leftFlow = totalDou;
									}
								}
							}
						}
					}
				}
				DecimalFormat decimalFormat = new DecimalFormat();
				trafficNowDto.setDataRemaining(decimalFormat.format(leftFlow / 1024));
				//double totalFlow = 0;
				//double usedFlow = 0;
//				JSONObject resultJSON = JSON.parseObject(reuslt);
//				if (resultJSON.containsKey("message") && "正确".equals(resultJSON.getString("message"))) {
//					msg.setSuccess(true);
//					msg.setOpeartorsDealRst("0");
//					JSONArray reusltArray = resultJSON.getJSONArray("result");
//					for (int i = 0; i < reusltArray.size(); i++) {
//						JSONObject object = (JSONObject) reusltArray.get(i);
//						JSONArray jsonArray = object.getJSONArray("gprs");
//						for (int j = 0; j < jsonArray.size(); j++) {
//							JSONObject jsonObject = jsonArray.getJSONObject(j);
//							String left = jsonObject.getString("left"); // 剩余流量 单位KB
//							leftFlow += Double.valueOf(left);
//							//String total = jsonObject.getString("total"); // 套餐总量 单位M
//							//totalFlow += Double.valueOf(total);
//							//String used = jsonObject.getString("used"); // 使用量 单位kB
//							//usedFlow += Double.valueOf(used);
//						}
//					}
//					DecimalFormat decimalFormat = new DecimalFormat("#0.000");//格式化设置
//					trafficNowDto.setDataRemaining(decimalFormat.format(leftFlow / 1024));
//					log.info("ec平台查询剩余流量返回>>>>>>>>leftFlow=" + leftFlow);
//				} else {
//					msg.setOpeartorsDealRst("1"); // 0成功1失败
//					trafficNowDto.setDataRemaining("0");
//					log.info("ec平台查询剩余流量异常>>>>>>>>");
//				}

			} else {
				msg.setOutMessage("null");
				msg.setOpeartorsDealCode("");
				msg.setOpeartorsDealRst("1");
				log.info("调用ec平台查询剩余流量返回结果为空>>>>>>>>");
			}
			// 在网，不在网 状态查询
			String reusltonandoff = ecMethod.onandoffrealsingle(msisdn);
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
			msg.setTrafficDto(trafficNowDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}

	/**
	 * 批量查询流量
	 */
	@Override
	public UserTrafficQuery<SOAPMessage>.TrafficQueryResultMsg sendBatchQueryMessage(Object... args) {

		return null;
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
			System.out.println(sdf.format(time) + "在此区间");
			return true;
		} else {
			System.out.println(sdf.format(time) + "不在此区间");
			return false;
		}
	}
	
	// datemonth =yyyy-MM-01
    private  static String endDate(String datemonth){
    	Calendar cal = Calendar.getInstance();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	Date date = null;
		try {
			date = sdf.parse(datemonth);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	cal.setTime(date);
    	int dayCount = cal.getActualMaximum(Calendar.DAY_OF_MONTH);  
    	
    	cal.add(Calendar.DATE,dayCount-1);//整数往后推,负数往前移动
    	Date dateEnd = cal.getTime();
    	return sdf.format(dateEnd);//yyyy-MM-dd
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
}
