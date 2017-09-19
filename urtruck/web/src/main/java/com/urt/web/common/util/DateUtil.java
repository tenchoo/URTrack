package com.urt.web.common.util;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public abstract class DateUtil {

	private static SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd"); 	
	private static SimpleDateFormat yrsdf = new SimpleDateFormat ("MM月dd日"); 	
	
	public static String formateDate(Date date){
		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.CHINA);
		return df.format(date);
	}
	
	/**
	 * 时间格式化
	 * @param date
	 * @return
	 * @author 
	 * @date 
	 */
	public static String dateStrToString(String date){
		try {
			if(date != null){
				if(date.equals(getNowTime())){
					date = "今天";
				}else if(date.equals(getYestoday())){
					date="昨天";
				}else{
					date = yrsdf.format(sdf.parse(date));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return date;
	}
	

	/**
	 *  把Long类型转换为String类型
	 * @param currentTime
	 * @return
	 * @author XiangBin
	 * @date 2014-1-17
	 */
 	public static String longToStrDate(long currentTime){
 		String sDateTime = "";
		Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
		sDateTime = sdf.format(dateOld); // 把date类型的时间转换为string
 		return sDateTime;
 	}
	
	
	/**
	 * 获得昨天日期
	 * @return
	 * @author 
	 * @date 
	 */
	public static String getYestoday() {
		Date date = new Date();
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return sdf.format(cal.getTime());
	}
	
	
	/**
	 * 获取当天时间
	 * 
	 * @param dateformat
	 * @return
	 */
	public static String getNowTime() {
		Date now = new Date();
		String time = sdf.format(now);
		return time;
	}
	
	/**
	 * 功能描述：根据String时间返回Date(yyyy-MM-dd) 
	 * @author wangfu
	 * @date 2016年5月31日 下午5:20:38
	 * @param @return 
	 * @return String
	 */
	public static Date getDate(String str) {
		if(str.equals(null)||str.equals(""))return new Date();
		Date date=new Date();
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 功能描述：根据string时间增加或者减少天数返回string日期
	 * @author liuxl7
	 * @date 2016年6月28日 下午2:31:08
	 * @param @param endDate
	 * @param @param day
	 * @param @return 
	 * @return String
	 */
	public static String addDay(String endDate,int day) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date=sdf.parse(endDate);
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.DAY_OF_YEAR, day);
			endDate=sdf.format(c.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return endDate;
	}
	
}

