package com.urt.Ability.EcCMCC;



import org.springframework.stereotype.Service;

import com.urt.Ability.EcCMCC.utils.DigestUtils;
import com.urt.interfaces.EcCMCC.EcBaseMethod;
import com.urt.utils.HttpClientUtil;

@Service("ecBaseMethod")
public class EcBaseMethodImpl implements EcBaseMethod {
   
	private static final String url="http://183.230.96.66:8087/v2/";
	protected  String appid="CE8O2GB";//SWT82AW(浙江);//CE8O2GB(东莞)//STQJ24N(浙江lbs)//
	protected  String passwd="LXDD85";//LXDD41;//LXDD85//LXDD11
	
	// 物联卡位置定位msisdn、iccid、imsi三者必须有且只有一项 //测试号898607B2111701192786
	// http://183.230.96.66:8087/v2/location_slis
	public String  locationSlis(String  iccid){
		String method="location_slis";
		String ebid="0001000000191";
		String transid=appid+DigestUtils.dataString();
		String token = DigestUtils.sha256Hex(appid+passwd+transid);
		String httpUrl=url+method+"?"+"appid="+appid+"&"+"transid="
				+transid+"&"+"ebid="+ebid+"&"+"token="+token+"&iccid="+iccid;
		System.out.println(httpUrl);
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		return result;
	}
	//当月gprs查询
	public String  queryGprsInfo(String  msisdn){
		
		String method="gprsusedinfosingle";
		String ebid="0001000000012";
		String transid=appid+DigestUtils.dataString();
		String token = DigestUtils.sha256Hex(appid+passwd+transid);
		String httpUrl=url+method+"?"+"appid="+appid+"&"+"transid="
		+transid+"&"+"ebid="+ebid+"&"+"token="+token+"&msisdn="+msisdn;
		System.out.println(httpUrl);
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		return result;
	}
	//短信批量查询
	public String  batchsmsused(String  msisdns,String date){
		
		String method="batchsmsusedbydate";
		String ebid="0001000000026";
		String transid=appid+DigestUtils.dataString();
		String token = DigestUtils.sha256Hex(appid+passwd+transid);
		String httpUrl=url+method+"?"+"appid="+appid+"&"+"transid="
				+transid+"&"+"ebid="+ebid+"&"+"token="+token+"&query_date="+date+"&msisdns="+msisdns;
		System.out.println(httpUrl);
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		return result;
	}
	//流量信息批量查询
	public String  batchgprsused(String  msisdns,String date){
		
		String method="batchgprsusedbydate";
		String ebid="0001000000027";
		String transid=appid+DigestUtils.dataString();
		String token = DigestUtils.sha256Hex(appid+passwd+transid);
		String httpUrl=url+method+"?"+"appid="+appid+"&"+"transid="
				+transid+"&"+"ebid="+ebid+"&"+"token="+token+"&query_date="+date+"&msisdns="+msisdns;
		System.out.println(httpUrl);
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		return result;
	}
	
	//用户余额信息实时查询
	public String  balancerealsingle(String  msisdn){
		
		String method="balancerealsingle";
		String ebid="0001000000035";
		String transid=appid+DigestUtils.dataString();
		String token = DigestUtils.sha256Hex(appid+passwd+transid);
		String httpUrl=url+method+"?"+"appid="+appid+"&"+"transid="
				+transid+"&"+"ebid="+ebid+"&"+"token="+token+"&msisdn="+msisdn;
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		return result;
	}
	
	//码号信息查询 type 0 1 2 msisdn imsi iccid 
	public String  cardinfo(String  card_info,String type){
		
		String method="cardinfo";
		String ebid="0001000000010";
		String transid=appid+DigestUtils.dataString();
		String token = DigestUtils.sha256Hex(appid+passwd+transid);
		String httpUrl=url+method+"?"+"appid="+appid+"&"+"transid="
				+transid+"&"+"ebid="+ebid+"&"+"token="+token+"&card_info="+card_info+"&type="+type;
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		return result;
	}
	//用户状态信息实时查询 
	public String  userstatusrealsingle(String  msisdn){
		
		String method="userstatusrealsingle";
		String ebid="0001000000009";
		String transid=appid+DigestUtils.dataString();
		String token = DigestUtils.sha256Hex(appid+passwd+transid);
		String httpUrl=url+method+"?"+"appid="+appid+"&"+"transid="
				+transid+"&"+"ebid="+ebid+"&"+"token="+token+"&msisdn="+msisdn;
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		return result;
	}
	
	//在线信息实时查询 
	public String  gprsrealsingle(String  msisdn){
		
		String method="gprsrealsingle";
		String ebid="0001000000008";
		String transid=appid+DigestUtils.dataString();
		String token = DigestUtils.sha256Hex(appid+passwd+transid);
		String httpUrl=url+method+"?"+"appid="+appid+"&"+"transid="
				+transid+"&"+"ebid="+ebid+"&"+"token="+token+"&msisdn="+msisdn;
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		return result;
	}
	//套餐内GPRS流量使用情况实时查询
	public String  gprsrealtimeinfo(String  msisdn){
		
		String method="gprsrealtimeinfo";
		String ebid="0001000000083";
		String transid=appid+DigestUtils.dataString();
		String token = DigestUtils.sha256Hex(appid+passwd+transid);
		String httpUrl=url+method+"?"+"appid="+appid+"&"+"transid="
				+transid+"&"+"ebid="+ebid+"&"+"token="+token+"&msisdn="+msisdn;
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		return result;
	}
	//用户短信使用查询
	public String  smsusedbydate(String  msisdn,String query_date){
		
		String method="smsusedbydate";
		String ebid="0001000000040";
		String transid=appid+DigestUtils.dataString();
		String token = DigestUtils.sha256Hex(appid+passwd+transid);
		String httpUrl=url+method+"?"+"appid="+appid+"&"+"transid="
				+transid+"&"+"ebid="+ebid+"&"+"token="+token+"&msisdn="+msisdn+"&query_date="+query_date;
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		return result;
	}
	
	//用户当月短信查询
	public String  smsusedinfosingle(String  msisdn){
		
		String method="smsusedinfosingle";
		String ebid="0001000000036";
		String transid=appid+DigestUtils.dataString();
		String token = DigestUtils.sha256Hex(appid+passwd+transid);
		String httpUrl=url+method+"?"+"appid="+appid+"&"+"transid="
				+transid+"&"+"ebid="+ebid+"&"+"token="+token+"&msisdn="+msisdn;
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		return result;
	}
	//短信状态重置
	public String  smsstatusreset(String  msisdn){
		
		String method="smsstatusreset";
		String ebid="0001000000034";
		String transid=appid+DigestUtils.dataString();
		String token = DigestUtils.sha256Hex(appid+passwd+transid);
		String httpUrl=url+method+"?"+"appid="+appid+"&"+"transid="
				+transid+"&"+"ebid="+ebid+"&"+"token="+token+"&msisdn="+msisdn;
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		return result;
	}
	
	//开关机信息实时查询
	public String  onandoffrealsingle(String  msisdn){
		
		String method="onandoffrealsingle";
		String ebid="0001000000025";
		String transid=appid+DigestUtils.dataString();
		String token = DigestUtils.sha256Hex(appid+passwd+transid);
		String httpUrl=url+method+"?"+"appid="+appid+"&"+"transid="
				+transid+"&"+"ebid="+ebid+"&"+"token="+token+"&msisdn="+msisdn;
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		return result;
	}
	
	//集团用户数查询
	public String  groupuserinfo(String  query_date){
		
		String method="groupuserinfo";
		String ebid="0001000000039";
		String transid=appid+DigestUtils.dataString();
		String token = DigestUtils.sha256Hex(appid+passwd+transid);
		String httpUrl=url+method+"?"+"appid="+appid+"&"+"transid="
				+transid+"&"+"ebid="+ebid+"&"+"token="+token+"&query_date="+query_date;
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		return result;
	}
	//短信状态重置
	
	
	
	public static void main(String[] args) {
		EcBaseMethodImpl imple = new EcBaseMethodImpl();
		//String queryGprsInfo = imple.queryGprsInfo("1064726593069");
		//String queryGprsInfo = imple.batchsmsused("1064726593069_1064726591547","20170520");
		//String queryGprsInfo = imple.balancerealsingle("1064726593069");
		//String queryGprsInfo = imple.cardinfo("1064726593069","0");
		//String queryGprsInfo = imple.userstatusrealsingle("1064726477205");
		//String queryGprsInfo = imple.gprsrealsingle("1064726593069");
		//String queryGprsInfo = imple.gprsrealtimeinfo("1064726593069");
		//String queryGprsInfo = imple.smsusedbydate("1064726593069","20170525");
		//String queryGprsInfo = imple.smsusedinfosingle("1064726593069");
		//String queryGprsInfo = imple.smsstatusreset("1064726593069");
		//String queryGprsInfo = imple.groupuserinfo("20170525");
		//String queryGprsInfo = imple.onandoffrealsingle("1064726593069");
		String queryGprsInfo = imple.locationSlis("898602B8191650540795");
		System.out.println(queryGprsInfo);
	}
	//联卡资费套餐查询物
	@Override
	public String cardPricingPackage(String msisdn) {
		
		
		return null;
	}
	

}
