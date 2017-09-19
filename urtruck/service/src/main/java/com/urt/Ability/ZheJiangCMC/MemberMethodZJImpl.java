package com.urt.Ability.ZheJiangCMC;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.urt.Ability.ZheJiangCMC.utils.SecurityUtils;
import com.urt.interfaces.ZheJiangCMC.MemberMethodZJ;
import com.urt.utils.HttpClientUtil;

@Service("memberMethodZJ")
public class MemberMethodZJImpl implements MemberMethodZJ{
	protected static final Logger logger = Logger.getLogger(MemberMethodZJImpl.class);
	private static final String method = "method";
	private static final String  GROUP_ID="57171588518";
//	@Value("${zjcmcUrl}")
//	private String url;
//	@Value("${zjcmcAppKey}")
//	private String appKey;
//	@Value("${publicParam}")
//	private String publicParam;
	//GPRS关停
	public  String  stopGPRS(String msisdn){
		String url="http://dmzesbhttp.zj.chinamobile.com:20110/oppf?";
		String publicParam="&format=json&appId=501148&messageId=1&";
		String appKey="0eea1321e84af830070612e697091f38";
		String method_1="ABILITY_10000639";
		Map<String,Object> hashMap =new HashMap<String,Object>();
		hashMap.put("method", method_1);
		hashMap.put("appId", "501148");
		hashMap.put("format", "json");
		hashMap.put("messageId", "1");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("GROUP_ID", GROUP_ID);
		jsonObject.put("MSISDN", msisdn);
		String sign = SecurityUtils.getSign(hashMap, jsonObject, appKey);
		HttpClientUtil instance = HttpClientUtil.getInstance();
		String httpUrl=url+method+"="+method_1+publicParam+"sign"+"="+sign;
		System.out.println(httpUrl);
		String sendHttpPost = instance.sendHttpPost(httpUrl, jsonObject.toJSONString());
		return sendHttpPost;
	}
	//GPRS开启
	public  String  resetGPRS(String msisdn){
		String url="http://dmzesbhttp.zj.chinamobile.com:20110/oppf?";
		String publicParam="&format=json&appId=501148&messageId=1&";
		String appKey="0eea1321e84af830070612e697091f38";
		String method_1="ABILITY_10000629";
		Map<String,Object> hashMap =new HashMap<String,Object>();
		hashMap.put("method", method_1);
		hashMap.put("appId", "501148");
		hashMap.put("format", "json");
		hashMap.put("messageId", "1");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("GROUP_ID", GROUP_ID);
		jsonObject.put("MSISDN", msisdn);
		String sign = SecurityUtils.getSign(hashMap, jsonObject, appKey);
		HttpClientUtil instance = HttpClientUtil.getInstance();
		String httpUrl=url+method+"="+method_1+publicParam+"sign"+"="+sign;
		System.out.println(httpUrl);
		String sendHttpPost = instance.sendHttpPost(httpUrl, jsonObject.toJSONString());
		return sendHttpPost;
	}
	//GPRS停复机  T0022：营业停机K0022：营业复机
	public  String stopGPRS(String msisdn, String oper_type){
		String url="http://dmzesbhttp.zj.chinamobile.com:20110/oppf?";
		String publicParam="&format=json&appId=501148&messageId=1&";
		String appKey="0eea1321e84af830070612e697091f38";
		String method_1="ABILITY_10000600";
		Map<String,Object> hashMap =new HashMap<String,Object>();
		hashMap.put("method", method_1);
		hashMap.put("appId", "501148");
		hashMap.put("format", "json");
		hashMap.put("messageId", "1");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("GROUP_ID", GROUP_ID);
		jsonObject.put("MSISDN",msisdn);
		jsonObject.put("OPER_TYPE", oper_type);
		String sign = SecurityUtils.getSign(hashMap, jsonObject, appKey);
		HttpClientUtil instance = HttpClientUtil.getInstance();
		String httpUrl=url+method+"="+method_1+publicParam+"sign"+"="+sign;
		System.out.println(httpUrl);
		String sendHttpPost = instance.sendHttpPost(httpUrl, jsonObject.toJSONString());
		return sendHttpPost;
	}
	//物联网沉默期激活
	public  String cardActive(String msisdn){
		String url="http://dmzesbhttp.zj.chinamobile.com:20110/oppf?";
		String publicParam="&format=json&appId=501148&messageId=1&";
		String appKey="0eea1321e84af830070612e697091f38";
		String method_1="ABILITY_10000638";
		Map<String,Object> hashMap =new HashMap<String,Object>();
		hashMap.put("method", method_1);
		hashMap.put("appId", "501148");
		hashMap.put("format", "json");
		hashMap.put("messageId", "1");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("GROUP_ID", GROUP_ID);
		jsonObject.put("MSISDN",msisdn);
		String sign = SecurityUtils.getSign(hashMap, jsonObject, appKey);
		HttpClientUtil instance = HttpClientUtil.getInstance();
		String httpUrl=url+method+"="+method_1+publicParam+"sign"+"="+sign;
		System.out.println(httpUrl);
		String sendHttpPost = instance.sendHttpPost(httpUrl, jsonObject.toJSONString());
		return sendHttpPost;
	}
	//用户三户信息查询
	public  String msisdnInfo(String msisdn){
		String url="http://dmzesbhttp.zj.chinamobile.com:20110/oppf?";
		String publicParam="&format=json&appId=501148&messageId=1&";
		String appKey="0eea1321e84af830070612e697091f38";
		String method_1="ABILITY_10000645";
		Map<String,Object> hashMap =new HashMap<String,Object>();
		hashMap.put("method", method_1);
		hashMap.put("appId", "501148");
		hashMap.put("format", "json");
		hashMap.put("messageId", "1");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("GROUP_ID", GROUP_ID);
		jsonObject.put("MSISDN",msisdn);
		String sign = SecurityUtils.getSign(hashMap, jsonObject, appKey);
		HttpClientUtil instance = HttpClientUtil.getInstance();
		String httpUrl=url+method+"="+method_1+publicParam+"sign"+"="+sign;
		System.out.println(httpUrl);
		String sendHttpPost = instance.sendHttpPost(httpUrl, jsonObject.toJSONString());
		return sendHttpPost;
	}
	//物联网测试期延长和终止 oper_type	延长月数 0：立即停机 1：延长1月 2：延长2月
	public  String msisdnInfo(String msisdn,String oper_type){
		String url="http://dmzesbhttp.zj.chinamobile.com:20110/oppf?";
		String publicParam="&format=json&appId=501148&messageId=1&";
		String appKey="0eea1321e84af830070612e697091f38";
		String method_1="ABILITY_10000640";
		Map<String,Object> hashMap =new HashMap<String,Object>();
		hashMap.put("method", method_1);
		hashMap.put("appId", "501148");
		hashMap.put("format", "json");
		hashMap.put("messageId", "1");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("GROUP_ID", GROUP_ID);
		jsonObject.put("MSISDN",msisdn);
		jsonObject.put("OPER_TYPE",oper_type);
		String sign = SecurityUtils.getSign(hashMap, jsonObject, appKey);
		HttpClientUtil instance = HttpClientUtil.getInstance();
		String httpUrl=url+method+"="+method_1+publicParam+"sign"+"="+sign;
		System.out.println(httpUrl);
		String sendHttpPost = instance.sendHttpPost(httpUrl, jsonObject.toJSONString());
		return sendHttpPost;
	}
	//物联网沉默期转测试期  period of silence
	public  String msisdnPeriodOfSilence(String msisdn){
		String url="http://dmzesbhttp.zj.chinamobile.com:20110/oppf?";
		String publicParam="&format=json&appId=501148&messageId=1&";
		String appKey="0eea1321e84af830070612e697091f38";
		String method_1="ABILITY_10000638";
		Map<String,Object> hashMap =new HashMap<String,Object>();
		hashMap.put("method", method_1);
		hashMap.put("appId", "501148");
		hashMap.put("format", "json");
		hashMap.put("messageId", "1");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("GROUP_ID", GROUP_ID);
		jsonObject.put("MSISDN",msisdn);
		String sign = SecurityUtils.getSign(hashMap, jsonObject, appKey);
		HttpClientUtil instance = HttpClientUtil.getInstance();
		String httpUrl=url+method+"="+method_1+publicParam+"sign"+"="+sign;
		System.out.println(httpUrl);
		String sendHttpPost = instance.sendHttpPost(httpUrl, jsonObject.toJSONString());
		return sendHttpPost;
	}
	//用户余额查询
	public  String msisdnMoneyQuery(String msisdn){
		String url="http://dmzesbhttp.zj.chinamobile.com:20110/oppf?";
		String publicParam="&format=json&appId=501148&messageId=1&";
		String appKey="0eea1321e84af830070612e697091f38";
		String method_1="ABILITY_10000644";
		Map<String,Object> hashMap =new HashMap<String,Object>();
		hashMap.put("method", method_1);
		hashMap.put("appId", "501148");
		hashMap.put("format", "json");
		hashMap.put("messageId", "1");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("GROUP_ID", GROUP_ID);
		jsonObject.put("MSISDN",msisdn);
		String sign = SecurityUtils.getSign(hashMap, jsonObject, appKey);
		HttpClientUtil instance = HttpClientUtil.getInstance();
		String httpUrl=url+method+"="+method_1+publicParam+"sign"+"="+sign;
		System.out.println(httpUrl);
		String sendHttpPost = instance.sendHttpPost(httpUrl, jsonObject.toJSONString());
		return sendHttpPost;
	}
	//物联网用户状态信息实时查询
	public  String cardStatusQuery(String msisdn){
		String url="http://dmzesbhttp.zj.chinamobile.com:20110/oppf?";
		String publicParam="&format=json&appId=501148&messageId=1&";
		String appKey="0eea1321e84af830070612e697091f38";
		String method_1="ABILITY_10000664";
		Map<String,Object> hashMap =new HashMap<String,Object>();
		hashMap.put("method", method_1);
		hashMap.put("appId", "501148");
		hashMap.put("format", "json");
		hashMap.put("messageId", "1");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("GROUP_ID", GROUP_ID);
		jsonObject.put("MSISDN",msisdn);
		String sign = SecurityUtils.getSign(hashMap, jsonObject, appKey);
		HttpClientUtil instance = HttpClientUtil.getInstance();
		String httpUrl=url+method+"="+method_1+publicParam+"sign"+"="+sign;
		System.out.println(httpUrl);
		String sendHttpPost = instance.sendHttpPost(httpUrl, jsonObject.toJSONString());
		return sendHttpPost;
	}
	//物联网号码信息查询
	public  String msisdnQueryInfo(String msisdn){
		String url="http://dmzesbhttp.zj.chinamobile.com:20110/oppf?";
		String publicParam="&format=json&appId=501148&messageId=1&";
		String appKey="0eea1321e84af830070612e697091f38";
		String method_1="ABILITY_10000663";
		Map<String,Object> hashMap =new HashMap<String,Object>();
		hashMap.put("method", method_1);
		hashMap.put("appId", "501148");
		hashMap.put("format", "json");
		hashMap.put("messageId", "1");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("GROUP_ID", GROUP_ID);
		jsonObject.put("MSISDN",msisdn);
		String sign = SecurityUtils.getSign(hashMap, jsonObject, appKey);
		HttpClientUtil instance = HttpClientUtil.getInstance();
		String httpUrl=url+method+"="+method_1+publicParam+"sign"+"="+sign;
		System.out.println(httpUrl);
		String sendHttpPost = instance.sendHttpPost(httpUrl, jsonObject.toJSONString());
		return sendHttpPost;
	}
	//物联网卡开关机状态实时查询
	public  String msisdnQueryStopStart(String msisdn){
		String url="http://dmzesbhttp.zj.chinamobile.com:20110/oppf?";
		String publicParam="&format=json&appId=501148&messageId=1&";
		String appKey="0eea1321e84af830070612e697091f38";
		String method_1="ABILITY_10000662";
		Map<String,Object> hashMap =new HashMap<String,Object>();
		hashMap.put("method", method_1);
		hashMap.put("appId", "501148");
		hashMap.put("format", "json");
		hashMap.put("messageId", "1");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("GROUP_ID", GROUP_ID);
		jsonObject.put("MSISDN",msisdn);
		String sign = SecurityUtils.getSign(hashMap, jsonObject, appKey);
		HttpClientUtil instance = HttpClientUtil.getInstance();
		String httpUrl=url+method+"="+method_1+publicParam+"sign"+"="+sign;
		System.out.println(httpUrl);
		String sendHttpPost = instance.sendHttpPost(httpUrl, jsonObject.toJSONString());
		return sendHttpPost;
	}
	//物联网卡套餐内GPRS流量使用情况实时查询
	public  String msisdnQueryFlow(String msisdn){
		String url="http://dmzesbhttp.zj.chinamobile.com:20110/oppf?";
		String publicParam="&format=json&appId=501148&messageId=1&";
		String appKey="0eea1321e84af830070612e697091f38";
		String method_1="ABILITY_10000661";
		Map<String,Object> hashMap =new HashMap<String,Object>();
		hashMap.put("method", method_1);
		hashMap.put("appId", "501148");
		hashMap.put("format", "json");
		hashMap.put("messageId", "1");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("GROUP_ID", GROUP_ID);
		jsonObject.put("MSISDN",msisdn);
		String sign = SecurityUtils.getSign(hashMap, jsonObject, appKey);
		HttpClientUtil instance = HttpClientUtil.getInstance();
		String httpUrl=url+method+"="+method_1+publicParam+"sign"+"="+sign;
		System.out.println(httpUrl);
		String sendHttpPost = instance.sendHttpPost(httpUrl, jsonObject.toJSONString());
		return sendHttpPost;
	}
	//物联网卡在线信息实时查询
	public  String msisdnIsOnline(String msisdn){
		String url="http://dmzesbhttp.zj.chinamobile.com:20110/oppf?";
		String publicParam="&format=json&appId=501148&messageId=1&";
		String appKey="0eea1321e84af830070612e697091f38";
		String method_1="ABILITY_10000660";
		Map<String,Object> hashMap =new HashMap<String,Object>();
		hashMap.put("method", method_1);
		hashMap.put("appId", "501148");
		hashMap.put("format", "json");
		hashMap.put("messageId", "1");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("GROUP_ID", GROUP_ID);
		jsonObject.put("MSISDN",msisdn);
		String sign = SecurityUtils.getSign(hashMap, jsonObject, appKey);
		HttpClientUtil instance = HttpClientUtil.getInstance();
		String httpUrl=url+method+"="+method_1+publicParam+"sign"+"="+sign;
		System.out.println(httpUrl);
		String sendHttpPost = instance.sendHttpPost(httpUrl, jsonObject.toJSONString());
		return sendHttpPost;
	}
	//物联网用户状态信息实时查询
	public  String userStatus(String msisdn){
		String url="http://dmzesbhttp.zj.chinamobile.com:20110/oppf?";
		String publicParam="&format=json&appId=501148&messageId=1&";
		String appKey="0eea1321e84af830070612e697091f38";
		String method_1="ABILITY_10000664";
		Map<String,Object> hashMap =new HashMap<String,Object>();
		hashMap.put("method", method_1);
		hashMap.put("appId", "501148");
		hashMap.put("format", "json");
		hashMap.put("messageId", "1");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("GROUP_ID", GROUP_ID);
		jsonObject.put("MSISDN",msisdn);
		String sign = SecurityUtils.getSign(hashMap, jsonObject, appKey);
		HttpClientUtil instance = HttpClientUtil.getInstance();
		String httpUrl=url+method+"="+method_1+publicParam+"sign"+"="+sign;
		System.out.println(httpUrl);
		String sendHttpPost = instance.sendHttpPost(httpUrl, jsonObject.toJSONString());
		return sendHttpPost;
	}
	//物联网用户卡状态实时查询
	public  String carkStatusInfo(String msisdn){
		String url="http://dmzesbhttp.zj.chinamobile.com:20110/oppf?";
		String publicParam="&format=json&appId=501148&messageId=1&";
		String appKey="0eea1321e84af830070612e697091f38";
		String method_1="ABILITY_10000665";
		Map<String,Object> hashMap =new HashMap<String,Object>();
		hashMap.put("method", method_1);
		hashMap.put("appId", "501148");
		hashMap.put("format", "json");
		hashMap.put("messageId", "1");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("GROUP_ID", GROUP_ID);
		jsonObject.put("MSISDN",msisdn);
		String sign = SecurityUtils.getSign(hashMap, jsonObject, appKey);
		HttpClientUtil instance = HttpClientUtil.getInstance();
		String httpUrl=url+method+"="+method_1+publicParam+"sign"+"="+sign;
		System.out.println(httpUrl);
		String sendHttpPost = instance.sendHttpPost(httpUrl, jsonObject.toJSONString());
		return sendHttpPost;
	}
	//物联网客户流量池信息查询
	public  String flowPool(String msisdn,String query_month){
		String url="http://dmzesbhttp.zj.chinamobile.com:20110/oppf?";
		String publicParam="&format=json&appId=501148&messageId=1&";
		String appKey="0eea1321e84af830070612e697091f38";
		String method_1="ABILITY_10000666";
		Map<String,Object> hashMap =new HashMap<String,Object>();
		hashMap.put("method", method_1);
		hashMap.put("appId", "501148");
		hashMap.put("format", "json");
		hashMap.put("messageId", "1");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("GROUP_ID", GROUP_ID);
		jsonObject.put("MSISDN",msisdn);
		jsonObject.put("QUERY_MONTH",query_month);
		String sign = SecurityUtils.getSign(hashMap, jsonObject, appKey);
		HttpClientUtil instance = HttpClientUtil.getInstance();
		String httpUrl=url+method+"="+method_1+publicParam+"sign"+"="+sign;
		System.out.println(httpUrl);
		String sendHttpPost = instance.sendHttpPost(httpUrl, jsonObject.toJSONString());
		return sendHttpPost;
	}
	//短信GPRS资源使用量查询
	public  String gprsFlowQueryInfo(String msisdn,String begin_date,String end_date){
		String url="http://dmzesbhttp.zj.chinamobile.com:20110/oppf?";
		String publicParam="&format=json&appId=501148&messageId=1&";
		String appKey="0eea1321e84af830070612e697091f38";
		String method_1="ABILITY_10000637";
		Map<String,Object> hashMap =new HashMap<String,Object>();
		hashMap.put("method", method_1);
		hashMap.put("appId", "501148");
		hashMap.put("format", "json");
		hashMap.put("messageId", "1");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("GROUP_ID", GROUP_ID);
		jsonObject.put("MSISDN",msisdn);
		jsonObject.put("BEGIN_DATE",begin_date);
		jsonObject.put("END_DATE",end_date);
		String sign = SecurityUtils.getSign(hashMap, jsonObject, appKey);
		HttpClientUtil instance = HttpClientUtil.getInstance();
		String httpUrl=url+method+"="+method_1+publicParam+"sign"+"="+sign;
		System.out.println(httpUrl);
		String sendHttpPost = instance.sendHttpPost(httpUrl, jsonObject.toJSONString());
		return sendHttpPost;
	}
	//业务变更受理
	public static void main(String[] args) {
		MemberMethodZJImpl mm = new MemberMethodZJImpl();
		String resetGPRS = mm.gprsFlowQueryInfo("1064724351621","20170601","20170630");
		//String resetGPRS = mm.msisdnQueryInfo("1064724351621");
		System.out.println(resetGPRS);
	}
	
}
