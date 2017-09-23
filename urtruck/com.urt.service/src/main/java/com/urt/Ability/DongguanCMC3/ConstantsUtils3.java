package com.urt.Ability.DongguanCMC3;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ConstantsUtils3 {
	public static final String appKey="appKey";
	public static final String appKeyVal="43smp189a7";
	public static final String v="v";
	public static final String vVal="3.0";
	public static final String format="format";
	public static final String groupCode="2001219805";
	public static final String transID="transID";
	
	
	public static final String formatVal="json";
	public static final String locale="locale";
	public static final String localeVal="zh_cn";
	public static final String method="method";
	public static final String sign="sign";
	//public static final String URL="http://120.197.89.174:8094/openapi/router";
	public static final String URL="http://120.197.89.173:8081/openapi/router";
	public static final String ecCode="ecCode";
	public static final String secret="2d9ddc0dad1a25c1d4174b3b80f356a4";
	public static final String dateStr="dateStr";
	public static final String pageNo="pageNo";
	public static final String month="month";
	public static final String productCode="productCode";
	public static final String msisdn="msisdn";
	public static final String startDateStr="startDateStr";
	public static final String endDateStr="endDateStr";
	public static final String ncode="ncode";//操作类型:1-停机2-开机
	
	public static final String optType="optType";//服务类型:5-GPRS停,6-GPRS开
	public static final String prdOrdNum1="prdOrdNum1";//已订购产品编码
	public static final String prdOrdNum2="prdOrdNum2";//目标产品编码
	public static final String newPackage="newPackage";//目的套餐编码
	public static final String oldPackage="oldPackage";//已订购套餐编码（动力100卡必填
	
	public static String dataString(){
		Date date = new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String format = df.format(date);
		StringBuilder str=new StringBuilder();//定义变长字符串
		Random random=new Random();
		//随机生成数字，并添加到字符串
		for(int i=0;i<4;i++){
		    str.append(random.nextInt(10));
		}
		return ConstantsUtils3.groupCode+format+str.toString();
	}

}
