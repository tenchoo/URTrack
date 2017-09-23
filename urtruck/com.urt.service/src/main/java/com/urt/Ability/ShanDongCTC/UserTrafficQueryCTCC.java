package com.urt.Ability.ShanDongCTC;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.eclipse.jetty.util.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.urt.Ability.ShanDongCTC.Utils.ConstantUtil;
import com.urt.Ability.ShanDongCTC.Utils.DesUtils;
import com.urt.Ability.collect.UserTrafficQuery;
import com.urt.dto.traffic.LaoTrafficDetailDto;
import com.urt.dto.traffic.PackagePlanDto;
import com.urt.dto.traffic.TrafficQueryDetailsDto;
import com.urt.dto.traffic.TrafficQueryNowDto;
import com.urt.mapper.ext.OperatorPlanExtMapper;
import com.urt.po.OperatorPlan;
import com.urt.utils.HttpClientUtil;

/**
 * 电信流量查询
 * 
 * @author lingfei
 * @date 2016年10月20日
 */
@Service("userTrafficQueryCTCC")
public class UserTrafficQueryCTCC extends UserTrafficQuery<SOAPMessage> {

	
	@Autowired
	private OperatorPlanExtMapper operatorPlanExtDao;
	/**
	 * 剩余流量实时查询
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected TrafficQueryResultMsg sendNowMessage(Object... args) {
		TrafficQueryResultMsg msg = new TrafficQueryResultMsg();
		TrafficQueryNowDto trafficDto = new TrafficQueryNowDto();
		String iccid = null;
		String dataRemaining = "0";
		List<PackagePlanDto> listPack = null;
		if (args.length > 0) {
			iccid = (String) args[0];
		}
		try {
			msg.setInputMessage("iccid"+iccid);
			String xmlStr = queryPakage(iccid, null, msg);
			if (xmlStr != null) {
				if (xmlStr.length() > 1000) {
					msg.setOutMessage(xmlStr.substring(0, 1000));
				} else {
					msg.setOutMessage(xmlStr);
				}
				if (StringUtils.isBlank(xmlStr) || xmlStr.equals("-1") || xmlStr.equals("-2") || xmlStr.equals("-3") || xmlStr.equals("-4")) {
					msg.setOpeartorsDealCode("failed");
					msg.setOpeartorsDealRst("1");
					trafficDto.setDataRemaining(dataRemaining);
					msg.setTrafficDto(trafficDto);
					return msg;
				}
				int in1 = xmlStr.indexOf("<web:");
				int in2 = xmlStr.indexOf(">", in1);
				String str1 = xmlStr.substring(in1, in2 + 1);
				int in3 = xmlStr.lastIndexOf("</web:");
				String str2 = xmlStr.substring(in3, in3 + 6);
				xmlStr = xmlStr.replace(str1, "").replace(str2, "");
				Map<String, Object> map = xmlToMapByNow(xmlStr,operatorPlanExtDao);
				dataRemaining = (String) map.get("dataRemaining");
				listPack = (List<PackagePlanDto>) map.get("listPack");
				if ("0".equals(map.get("IRESULT"))) {
					msg.setSuccess(true);
					msg.setOpeartorsDealCode("");
					msg.setOpeartorsDealRst("0"); // 0成功1失败
				} else {
					msg.setOpeartorsDealCode((String)map.get("IRESULT"));
					msg.setOpeartorsDealRst("1"); // 0成功1失败
				}
			} else {
				msg.setOpeartorsDealCode("");
				msg.setOpeartorsDealRst("1"); // 0成功1失败
				msg.setOutMessage("null");
			}
		} catch (Exception e) {
			e.printStackTrace();
			trafficDto.setDataRemaining(dataRemaining);
			msg.setTrafficDto(trafficDto);
			return msg;
		}
		trafficDto.setDataRemaining(dataRemaining);
		trafficDto.setListPack(listPack);
		msg.setTrafficDto(trafficDto);
		return msg;
	}

	@Override
	protected UserTrafficQuery<SOAPMessage>.TrafficQueryResultMsg sendDayMessage(
			Object... args) {
		TrafficQueryResultMsg msg = new TrafficQueryResultMsg();
		List<TrafficQueryDetailsDto> list = new ArrayList<TrafficQueryDetailsDto>();
		String iccid = null;
		// iccid,messageId,"0", date 20161027 2016-10-09
		String date = null;
		String startDate = null;
		if (args.length > 0) {
			iccid = (String) args[0];
		}
		if (args.length > 0) {
			date = (String) args[3];//YYYY-MM-dd
		}
		startDate = date.replace("-", "");
		try {
			String xmlStr = queryTrafficByDate(iccid, null, startDate,startDate, msg);
			if (xmlStr != null) {
				if (xmlStr.length() > 1000) {
					msg.setOutMessage(xmlStr.substring(0, 1000));
				} else {
					msg.setOutMessage(xmlStr);
				}
				msg.setOpeartorsDealCode("failed");
				msg.setOpeartorsDealRst("1");
				if (StringUtils.isBlank(xmlStr) || xmlStr.equals("-1") || xmlStr.equals("-2") || xmlStr.equals("-3") || xmlStr.equals("-4")) {
					TrafficQueryDetailsDto dto = new TrafficQueryDetailsDto();
					dto.setDataVolume("0");
					dto.setSessionStartTime(date);
					list.add(dto);
					msg.setListDto(list);
					return msg;
				}
				int in1 = xmlStr.indexOf("<web:");
				int in2 = xmlStr.indexOf(">", in1);
				String str1 = xmlStr.substring(in1, in2 + 1);
				int in3 = xmlStr.lastIndexOf("</web:");
				String str2 = xmlStr.substring(in3, in3 + 6);
				xmlStr = xmlStr.replace(str1, "").replace(str2, "");
				String iresult = xmlToMapByDay(xmlStr, list, startDate);
				msg.setListDto(list);
				if ("0".equals(iresult)) {
					msg.setSuccess(true);
					msg.setOpeartorsDealCode("");
					msg.setOpeartorsDealRst("0"); // 0成功1失败
				} else {
					msg.setOpeartorsDealCode(iresult);
					msg.setOpeartorsDealRst("1"); // 0成功1失败
				}
			} else {
				msg.setOpeartorsDealCode("");
				msg.setOpeartorsDealRst("1"); // 0成功1失败
				msg.setOutMessage("null");
			}
		} catch (Exception e) {
			e.printStackTrace();
			TrafficQueryDetailsDto dto = new TrafficQueryDetailsDto();
			dto.setDataVolume("0");
			dto.setSessionStartTime(date);
			list.add(dto);
			msg.setListDto(list);
			return msg;
		}
		return msg;
	}

	@Override
	protected UserTrafficQuery<SOAPMessage>.TrafficQueryResultMsg sendMonthMessage(Object... args) {
		TrafficQueryResultMsg msg = new TrafficQueryResultMsg();
		List<TrafficQueryDetailsDto> list = new ArrayList<TrafficQueryDetailsDto>();
		String iccid = null;
		// iccid,messageId,"0", date 20161027 2016-10-09   2016-09-01
		String date = null;
		String startDate = null;
		String endDate = null;
		if (args.length > 0) {
			iccid = (String) args[0];
		}
		if (args.length > 0) {
			date = (String) args[3];//YYYY-MM-01
		}
		startDate = date.replace("-", "");
		// 获取月初月末 2016-09-01  20160901 20160930
		endDate = this.getMonthDate(startDate);
		try {
			String xmlStr = queryTrafficByDate(iccid, null, startDate, endDate,msg);
			if (xmlStr != null) {
				if (xmlStr.length() > 1000) {
					msg.setOutMessage(xmlStr.substring(0, 1000));
				} else {
					msg.setOutMessage(xmlStr);
				}
				msg.setOpeartorsDealCode("");
				msg.setOpeartorsDealRst("1");
				if (StringUtils.isBlank(xmlStr) || xmlStr.equals("-1") || xmlStr.equals("-2") || xmlStr.equals("-3") || xmlStr.equals("-4")) {
					msg.setOpeartorsDealCode("");
					msg.setOpeartorsDealRst("1"); // 0成功1失败
					msg.setOutMessage(xmlStr);
					return msg;
				}
				int in1 = xmlStr.indexOf("<web:");
				int in2 = xmlStr.indexOf(">", in1);
				String str1 = xmlStr.substring(in1, in2 + 1);
				int in3 = xmlStr.lastIndexOf("</web:");
				String str2 = xmlStr.substring(in3, in3 + 6);
				xmlStr = xmlStr.replace(str1, "").replace(str2, "");
				String strResult = xmlToMapByMonth(xmlStr, list, date,msg);
				if ("0".equals(strResult)) {
					msg.setSuccess(true);
					msg.setOpeartorsDealCode("");
					msg.setOpeartorsDealRst("0"); // 0成功1失败
				} else {
					msg.setOpeartorsDealCode(strResult);
					msg.setOpeartorsDealRst("1"); // 0成功1失败
				}
			} else {
				msg.setOpeartorsDealCode("");
				msg.setOpeartorsDealRst("1"); // 0成功1失败
				msg.setOutMessage("null");
			}
		} catch (Exception e) {
			msg.setOpeartorsDealCode("");
			msg.setOpeartorsDealRst("1"); // 0成功1失败
			e.printStackTrace();
		}
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

	private String getMonthDate(String startDate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		String endDate = "";
		try {
			date = format.parse(startDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}   
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        // 获取该月的最后一天
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        endDate = format.format(cale.getTime());
        return endDate;
	}

	// 套餐流量查询
	@SuppressWarnings("deprecation")
	protected String queryPakage(String iccid, String tel,TrafficQueryResultMsg msg) {
		String method = "queryPakage";
		String code = "";
		if (iccid != null && iccid.trim().length() > 0) {
			code = iccid;
		} else {
			code = tel;
		}
		// 调用des加密工具类
		DesUtils des = new DesUtils();
		/*
		 * sign参数为method、tel、userId、password(没有加密的密码)等参数经过自然排序后拼接成
		 * 的以逗号分隔的字符串，在通过DES加密算法加密之后所得结果。
		 */
		String[] arr = { code, ConstantUtil.userIdValue,ConstantUtil.passWordValue, method };
		String resultStr = DesUtils.naturalOrdering(arr);
		// 密码加密结果
		String passwordEnc = des.strEnc(ConstantUtil.passWordValue,
				ConstantUtil.firstKey, ConstantUtil.secondKey,
				ConstantUtil.thirdKey);
		// sign加密结果
		String signEnc = des.strEnc(resultStr, ConstantUtil.firstKey,
				ConstantUtil.secondKey, ConstantUtil.thirdKey);
		// 拼接httpUrl
		String httpUrl = ConstantUtil.URL + "/query.do" + "?"
				+ ConstantUtil.method + "=" + method + "&"
				+ ConstantUtil.userId + "=" + ConstantUtil.userIdValue + "&"
				+ ConstantUtil.passWord + "=" + passwordEnc + "&"
				+ ConstantUtil.sign + "=" + signEnc + "&";
		msg.setInputMessage(httpUrl);
		if (iccid != null && iccid.trim().length() > 0) {
			httpUrl += ConstantUtil.iccid + "=" + iccid;
		} else {
			httpUrl += ConstantUtil.tel + "=" + tel;
		}
		Log.info(httpUrl);
		return HttpClientUtil.getInstance().sendHttpGet(httpUrl);

	}

	// 查询时间段流量使用情况
	@SuppressWarnings("deprecation")
	protected String queryTrafficByDate(String iccid, String tel,
			String startDate, String endDate,TrafficQueryResultMsg msg) {
		String code = "";
		if (iccid != null && iccid.trim().length() > 0) {
			code = iccid;
		} else {
			code = tel;
		}
		String method = "queryTrafficByDate";
		// 调用des加密工具类
		DesUtils des = new DesUtils();
		/*
		 * sign参数为method、tel、userId、password(没有加密的密码)等参数经过自然排序后拼接成
		 * 的以逗号分隔的字符串，在通过DES加密算法加密之后所得结果。
		 */
		String[] arr = { code, ConstantUtil.userIdValue,
				ConstantUtil.passWordValue, method };
		String resultStr = DesUtils.naturalOrdering(arr);
		// 密码加密结果
		String passwordEnc = des.strEnc(ConstantUtil.passWordValue,
				ConstantUtil.firstKey, ConstantUtil.secondKey,
				ConstantUtil.thirdKey);
		// sign加密结果
		String signEnc = des.strEnc(resultStr, ConstantUtil.firstKey,
				ConstantUtil.secondKey, ConstantUtil.thirdKey);
		// 拼接httpUrl
		String httpUrl = ConstantUtil.URL + "/query.do" + "?"
				+ ConstantUtil.method + "=" + method + "&"
				+ ConstantUtil.userId + "=" + ConstantUtil.userIdValue + "&"
				+ ConstantUtil.passWord + "=" + passwordEnc + "&"
				+ ConstantUtil.sign + "=" + signEnc + "&"
				+ ConstantUtil.startDate + "=" + startDate + "&"
				+ ConstantUtil.endDate + "=" + endDate + "&";
		msg.setInputMessage(httpUrl);
		if (iccid != null && iccid.trim().length() > 0) {
			httpUrl += ConstantUtil.iccid + "=" + iccid;
		} else {
			httpUrl += ConstantUtil.tel + "=" + tel;
		}
		Log.info(httpUrl);
		return HttpClientUtil.getInstance().sendHttpGet(httpUrl);
	}

	/**
	 * 
	 * @param xmlStr
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> xmlToMapByNow(String xmlStr,OperatorPlanExtMapper operatorPlanExtDao)throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String dataRemaining = "";
		if ("".equals(xmlStr) || xmlStr == null) {
			return map;
		}
		long total = 0;
		// 将xml格式的字符串转换成Document对象
		Document doc = DocumentHelper.parseText(xmlStr);
		// 获取根节点
		Element root = doc.getRootElement();
		// 获取根节点下的所有元素
		List<?> children = root.elements();
		// 循环所有子元素
        DecimalFormat decimalFormat = new DecimalFormat("#0.000");//格式化设置 
		if (children != null && children.size() > 0) {
			List<PackagePlanDto> listPack = new ArrayList<PackagePlanDto>();
			for (int i = 0; i < children.size(); i++) {
				Element child = (Element) children.get(i);
				if ("CumulRspList".equals(child.getName())) {
					String already = child.element("CUMULATION_LEFT").getTextTrim().replace("KB", "");
					total = total + Long.valueOf(already);
					/** 获取各个套餐信息  start **/
					String offerName = child.element("OFFER_NAME").getTextTrim();
					String count = child.element("CUMULATION_TOTAL").getTextTrim();
					String unitName = child.element("UNIT_NAME").getTextTrim();
					String startTime = child.element("START_TIME").getTextTrim().substring(0, 8);
//					String endTime = child.element("END_TIME").getTextTrim().substring(0, 8);
					long countLong = Long.valueOf(count);
					PackagePlanDto packDto = new PackagePlanDto();
					packDto.setPackageName(offerName);
					if ("KB".equals(unitName)) {
						long long1 = countLong / 1024;
						if (long1 > 1024) {
							long long2 = long1 / 1024;
							packDto.setPackageCount( long2+"G");
							packDto.setPackageRemain(decimalFormat.format(Long.valueOf(already) / 1024 / 1024)+"G");
						} else {
							packDto.setPackageCount(long1+"M");
							packDto.setPackageRemain(decimalFormat.format(Double.valueOf(already) / 1024)+"M");
						}
					} else if ("MB".equals(unitName)){
						packDto.setPackageCount(countLong / 1024 +"G");
						packDto.setPackageRemain(decimalFormat.format(Long.valueOf(already) / 1024 )+"G");
					} else if("GB".equals(unitName)){
						packDto.setPackageCount(countLong +"G");
						packDto.setPackageRemain(decimalFormat.format(Long.valueOf(already))+"G");
					}
					SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
					SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
//					packDto.setPackageEndTime(sdf2.format(sdf1.parse(endTime)));
					packDto.setPackageStartTime(sdf2.format(sdf1.parse(startTime)));
					OperatorPlan operDto = operatorPlanExtDao.queryByPlanName(offerName);
					if (operDto != null) {
						Integer timeLength = operDto.getTimeLength();
						String timeUnit = operDto.getTimeUnit();
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
					/** 获取各个套餐信息   end **/
				} else {
					map.put(child.getName(), child.getTextTrim());
				}
			}
			dataRemaining = decimalFormat.format(total / 1024);
			map.put("dataRemaining", dataRemaining);
			map.put("listPack", listPack);
		}
		return map;
	}

	/**
	 * 
	 * @param xmlStr
	 * @return
	 */
	private String xmlToMapByDay(String xmlStr,List<TrafficQueryDetailsDto> list,String startDate) throws Exception {
			String iresult = "";
			// 将xml格式的字符串转换成Document对象
			Document doc = DocumentHelper.parseText(xmlStr);
			// 获取根节点
			Element root = doc.getRootElement();
			// 获取根节点下的所有元素
			List<?> children = root.elements();
			// 循环所有子元素
			if (children != null && children.size() > 0) {
				String totalBytesCnt = "";
		        DecimalFormat decimalFormat = new DecimalFormat("#0.000");//格式化设置  
			for (int i = 0; i < children.size(); i++) {
				Element child = (Element) children.get(i);
				if ("NEW_DATA_TICKET_QRlist".equals(child.getName())) {
					String sessionStartTime = child.element("START_TIME").getTextTrim().substring(0, 19).replace("/", "-");
					String dataVolume = child.element("BYTES_CNT").getTextTrim().replace("KB", "");
					TrafficQueryDetailsDto dto1 = new TrafficQueryDetailsDto();
					double double2 = Double.parseDouble(dataVolume);
					String strDetails = decimalFormat.format(double2 / 1024);
					dto1.setDataVolume(strDetails);
					dto1.setSessionStartTime(sessionStartTime);
					list.add(dto1);
				} else if("TOTAL_BYTES_CNT".equals(child.getName())) {
					totalBytesCnt = child.getTextTrim();
				} else if("IRESULT".equals(child.getName())) {
					iresult = child.getTextTrim();
				}
			}
			// 添加该天总计流量记录
			TrafficQueryDetailsDto dtoTotal = new TrafficQueryDetailsDto();
			String str1 = startDate.substring(0, 4);
			String str2 = startDate.substring(4, 6);
			String str3 = startDate.substring(6, 8);
			dtoTotal.setSessionStartTime(str1+"-"+str2+"-"+str3);
			dtoTotal.setDataVolume(totalBytesCnt.replace("MB", ""));
			list.add(dtoTotal);
		}
		return iresult;
	}
	
	/**
	 * 
	 * @param xmlStr
	 * @param list 
	 * @return
	 */
	private String xmlToMapByMonth(String xmlStr, List<TrafficQueryDetailsDto> list,String date,TrafficQueryResultMsg msg) throws Exception {
		String iresult = "";
		// 将xml格式的字符串转换成Document对象
		Document doc = DocumentHelper.parseText(xmlStr);
		// 获取根节点
		Element root = doc.getRootElement();
		// 获取根节点下的所有元素
		List<?> children = root.elements();
		// 循环所有子元素
		if (children != null && children.size() > 0) {
			List<TrafficQueryDetailsDto> list1 = new ArrayList<TrafficQueryDetailsDto>();
//			String totalBytesCnt = "";
			for (int i = 0; i < children.size(); i++) {
				Element child = (Element) children.get(i);
				if ("NEW_DATA_TICKET_QRlist".equals(child.getName())) {
					String sessionStartTime = child.element("START_TIME").getTextTrim().substring(0, 10).replace("/", "-");
					String dataVolume = child.element("BYTES_CNT").getTextTrim().replace("KB", "");
					TrafficQueryDetailsDto dto1 = new TrafficQueryDetailsDto();
					dto1.setDataVolume(dataVolume);
					dto1.setSessionStartTime(sessionStartTime);
					list1.add(dto1);
//				} else if("TOTAL_BYTES_CNT".equals(child.getName())) {
//					totalBytesCnt = child.getTextTrim();
				} else if("IRESULT".equals(child.getName())) {
					iresult = child.getTextTrim();
				}
			}
			if (list1.size() > 0) {
				this.getNewList(list1, list);
				// 补充该月内没有数据的天数
				list = this.addList(list,date);
			} else {
				list = this.addList(list1,date);
			}
		/*	// 添加该月总计流量记录
			TrafficQueryDetailsDto dtoTotal = new TrafficQueryDetailsDto();
			dtoTotal.setSessionStartTime(date.substring(0,7));
			dtoTotal.setDataVolume(totalBytesCnt.replace("MB", ""));
			list.add(dtoTotal);*/
		}
		msg.setListDto(list);
		return iresult;
	}

	private void getNewList(
			List<TrafficQueryDetailsDto> list1, List<TrafficQueryDetailsDto> list) {
		// 添加第一个日期整合后的记录
		double double2 = 0;
		for (int j = 0; j < list1.size(); j++) {
			TrafficQueryDetailsDto detailsDto = list1.get(j);
			if (detailsDto.getSessionStartTime().equals(
					list1.get(0).getSessionStartTime())) {
				double dto2 = Double.parseDouble(detailsDto.getDataVolume());
				double2 = double2 + dto2;
			}
		}
		TrafficQueryDetailsDto details = new TrafficQueryDetailsDto();
		details.setSessionStartTime(list1.get(0).getSessionStartTime());
        DecimalFormat decimalFormat = new DecimalFormat("#0.000");//格式化设置  
		String strDetail = decimalFormat.format(double2 / 1024);
		details.setDataVolume(strDetail);
		list.add(details);
		// 添加不同日期 整合后的记录
		c: for (int i = 0; i < list1.size(); i++) {
			TrafficQueryDetailsDto detailsDto = list1.get(i);
			for (int j = 0; j < list.size(); j++) {
				TrafficQueryDetailsDto detailsDto1 = list.get(j);
				if (detailsDto.getSessionStartTime().equals(
						detailsDto1.getSessionStartTime())) {
					continue c;
				}
			}
			double d2 = 0;
			for (int k = 0; k < list1.size(); k++) {
				TrafficQueryDetailsDto detailsDto2 = list1.get(k);
				// 相同日期的记录 回话时长，消费流量 汇总
				if (detailsDto.getSessionStartTime().equals(
						detailsDto2.getSessionStartTime())) {
					double dto2 = Double.parseDouble(detailsDto2
							.getDataVolume());
					d2 = d2 + dto2;
				}
			}
			// 构造一个新的对象(某一天的总记录)
			TrafficQueryDetailsDto detailsDto3 = new TrafficQueryDetailsDto();
			detailsDto3.setSessionStartTime(detailsDto.getSessionStartTime());
			String strDetails = decimalFormat.format(d2 / 1024);
			detailsDto3.setDataVolume(strDetails);
			list.add(detailsDto3);
		}
	}

	@Override
	public TrafficQueryResultMsg sendBatchQueryMessage(Object... args) {
		TrafficQueryResultMsg msg = new TrafficQueryResultMsg();
		LaoTrafficDetailDto dto = new LaoTrafficDetailDto();
		String iccid = null ;
		if (args.length > 0) {
			iccid = (String) args[0];
		}
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		// 查询日期，昨天
		String startDate = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
		try {
			String xmlStr = queryTrafficByDate(iccid, null, startDate,
					startDate, msg);
			if (xmlStr != null) {
				if (xmlStr.length() > 1000) {
					msg.setOutMessage(xmlStr.substring(0, 1000));
				} else {
					msg.setOutMessage(xmlStr);
				}
				msg.setOpeartorsDealCode("");
				msg.setOpeartorsDealRst("1");
				if (StringUtils.isBlank(xmlStr) || xmlStr.equals("-1") || xmlStr.equals("-2") || xmlStr.equals("-3") || xmlStr.equals("-4")) {
					dto.setDataCycle(startDate);
					dto.setUseCount(new BigDecimal(0));
					msg.setLaoTrafficDetailDto(dto);
					return msg;
				}
				if (xmlStr != null) {
					int in1 = xmlStr.indexOf("<web:");
					int in2 = xmlStr.indexOf(">", in1);
					String str1 = xmlStr.substring(in1, in2 + 1);
					int in3 = xmlStr.lastIndexOf("</web:");
					String str2 = xmlStr.substring(in3, in3 + 6);
					xmlStr = xmlStr.replace(str1, "").replace(str2, "");

					// 将xml格式的字符串转换成Document对象
					Document doc = DocumentHelper.parseText(xmlStr);
					// 获取根节点
					Element root = doc.getRootElement();
					// 获取根节点下的所有元素
					List<?> children = root.elements();
					// 循环所有子元素
					if (children != null && children.size() > 0) {
						String totalBytesCnt = "0";
						for (int i = 0; i < children.size(); i++) {
							Element child = (Element) children.get(i);
							if ("TOTAL_BYTES_CNT".equals(child.getName())) {
								totalBytesCnt = child.getTextTrim()
										.replace("KB", "").replace("MB", "");
							}
						}
						dto.setUseCount(new BigDecimal(totalBytesCnt));
					}
					dto.setDataCycle(startDate);
				}
			} else {
				msg.setOpeartorsDealCode("");
				msg.setOpeartorsDealRst("1"); // 0成功1失败
				msg.setOutMessage("null");
			}
		} catch (Exception e) {
			dto.setDataCycle(startDate);
			dto.setUseCount(new BigDecimal(0));
			msg.setLaoTrafficDetailDto(dto);
			e.printStackTrace();
			return msg;
		}
		msg.setLaoTrafficDetailDto(dto);
		return msg;
	}
}
